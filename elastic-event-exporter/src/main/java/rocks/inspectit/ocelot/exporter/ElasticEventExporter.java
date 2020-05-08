package rocks.inspectit.ocelot.exporter;

import lombok.extern.slf4j.Slf4j;
import rocks.inspectit.ocelot.sdk.events.EventExporterService;

import java.io.IOException;

@Slf4j
public class ElasticEventExporter {

    private static ElasticEventHandler handler;

    private static String serviceName;

    public static void createAndRegister(String name, String host, Integer port, String protocol, String index) {
        serviceName = name;
        if(index.matches("[a-z\\d-]*")){
            handler = new ElasticEventHandler(index);
        } else {
            log.info("The given index for exportering events towards Elastic does not match the requirements and " +
                    "Default \"elastic-event-exporter\" is used. Only lowercase letters, numbers and - are allowed.");
            handler = new ElasticEventHandler("elastic-event-exporter");
        }

        handler.openClient(host, port, protocol);
        EventExporterService.registerHandler(serviceName, handler);
    }

    public static void unregister() throws IOException {
        EventExporterService.unregisterHandler(serviceName);
        handler.closeClient();
    }
}
