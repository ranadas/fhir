package com.mongodb.healthcare.fhir.route;


import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.parser.StrictErrorHandler;
import com.mongodb.healthcare.fhir.db.MyMongoOperations;
import com.mongodb.healthcare.fhir.model.MyPatientModel;
import com.mongodb.healthcare.fhir.parser.R4BundleProcessor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.hl7.fhir.r4.model.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

@Component
public class FhirRoute extends RouteBuilder {

    // Logger component
    private static final Logger logger = LoggerFactory.getLogger(FhirRoute.class);

    // FHIR Version
    private static final String FHIR_VERSION_R4 = "R4";

    // Fhir Context for parser
    private static FhirContext fhirContext = FhirContext.forR4();

    @Autowired
    private MyMongoOperations myMongoOperations;

    // Constructor
    public FhirRoute(){

    }

    @Override
    public void configure() {

        // Exceptions
        onException(Exception.class)
                .log(LoggingLevel.ERROR, "!! Exception !! - ${exception.message} - unable to process")
                .handled(true)
                .useOriginalMessage()
                .to("{{file.endpoint.exceptions}}");

        // Rest service configuration
        restConfiguration()
                .component("restlet")
                .host("{{camel.rest.server}}")
                .port("{{camel.rest.port}}");

        // Rest service path
        rest("{{camel.rest.path}}")
                .post().consumes("application/json")
                .to("direct:postFhirMessage");


        // Process FHIR bundle route
        from("direct:processFhirBundle")
                .routeId("RouteId - FHIRBundleProcessor")
                .log(LoggingLevel.INFO, logger, "Process Bundle")
                .process(exchange ->  {
                    Bundle bundle = exchange.getIn().getBody(Bundle.class);

                    R4BundleProcessor bp = new R4BundleProcessor(bundle);
                    exchange.getIn().setBody(bp.getMyPatientModel());
                })
                .log("Completed parsing.  Persist to MongoDB.")
                .process(exchange -> {
                    MongoOperations mongoOps =
                            myMongoOperations.getMongoOperations();

                    MyPatientModel insertedPatientModel
                        = mongoOps.insert(exchange.getIn().getBody(MyPatientModel.class));

                    String message = "Inserted MyPatientModel with _id: " + insertedPatientModel.getId();
                    logger.info(message);

                    exchange.getIn().setBody(message);

                })
                .log(LoggingLevel.INFO, logger, "Completed Writing to MongoDB.");

        // File Route
        from("{{file.endpoint}}")
                .routeId("RouteId - FileProcessor")
                .log("==> Received File.  Unmarshal to JSON.")
                .unmarshal().fhirJson(FhirRoute.FHIR_VERSION_R4)
                .to("direct:processFhirBundle");


        // Rest route
        from("direct:postFhirMessage")
                .routeId("RouteId - RestProcessor")
                .log("==> Received HTTP Message.")
                .process(exchange -> {
                    String myString = exchange.getIn().getBody(String.class);
                    IParser parser = this.fhirContext.newJsonParser();
                    parser.setParserErrorHandler(new StrictErrorHandler());

                    Bundle bundle = parser.parseResource(Bundle.class, myString);

                    exchange.getIn().setBody(bundle);
                })
                .to("direct:processFhirBundle");
    }
}