package com.mongodb.healthcare.fhir.route;


import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.parser.LenientErrorHandler;
import com.codahale.metrics.MetricRegistry;
import com.mongodb.healthcare.fhir.db.MyMongoOperations;
import com.mongodb.healthcare.fhir.model.MyPatientModel;
import com.mongodb.healthcare.fhir.parser.R4BundleProcessor;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.metrics.routepolicy.MetricsRegistryService;
import org.apache.camel.component.metrics.routepolicy.MetricsRoutePolicyFactory;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.hl7.fhir.r4.model.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


@Component
public class FhirRoute extends RouteBuilder {

    // Logger component
    private static final Logger logger = LoggerFactory.getLogger(FhirRoute.class);

    // FHIR Version
    private static final String FHIR_VERSION_R4 = "R4";

    // Fhir Context for parser
    private static FhirContext fhirContext = FhirContext.forR4();

    // TODO Implement Camel metrics
    private int success = 0;
    private int error = 0;

    @Autowired
    private MyMongoOperations myMongoOperations;

    // Constructor
    public FhirRoute(){
        logger.info("======== Starting FhirRoute =========");
    }

    @Override
    public void configure() {

        // Exceptions
        onException(Exception.class)
                .log(LoggingLevel.ERROR, "!! Exception !! - ${exception.message} - unable to process")
                .process(exchange -> {
                    error++;
                    this.printStatus();
                })
                .handled(true)
                .useOriginalMessage()
                .to("{{file.endpoint.exceptions}}")
                .to("metrics:counter:error.counter?increment=1")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpStatus.BAD_REQUEST))
                .end();

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
                    success++;
                    this.printStatus();

                    exchange.getIn().setBody(message);

                })
                .to("metrics:counter:success.counter?increment=1")
                .log(LoggingLevel.INFO, logger, "Completed Writing to MongoDB.")
                .end();

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
                    parser.setParserErrorHandler(new LenientErrorHandler());

                    Bundle bundle = parser.parseResource(Bundle.class, myString);

                    exchange.getIn().setBody(bundle);
                })
                .to("direct:processFhirBundle");
    }

    @Bean
    MetricRegistry metricRegistry() {
        return new MetricRegistry();
    }

    @Bean
    CamelContextConfiguration contextConfiguration() {
        return new CamelContextConfiguration() {
            @Override
            public void beforeApplicationStart(CamelContext camelContext) {
                logger.info("Configuring Camel metrics on all routes.");
                MetricsRoutePolicyFactory metricsRoutePolicyFactory = new MetricsRoutePolicyFactory();
                metricsRoutePolicyFactory.setMetricsRegistry(metricRegistry());
                camelContext.addRoutePolicyFactory(metricsRoutePolicyFactory);
            }

            @Override
            public void afterApplicationStart(CamelContext camelContext) {
                //no-op
            }
        };
    }

    private void printStatus() {
        logger.info("=================");
        logger.info("Success count: " + success);
        logger.info("  Error count: " + error);
        MetricsRegistryService registryService = getContext().hasService(MetricsRegistryService.class);
        if(registryService != null) {
            logger.info(registryService.dumpStatisticsAsJson());
        }
        logger.info("=================");
    }
}