package rocks.inspectit.ocelot.exporter;

import lombok.extern.slf4j.Slf4j;
import rocks.inspectit.ocelot.sdk.events.EventExporter;

import java.io.IOException;

@Slf4j
public class ElasticEventExporter {

    private static ElasticEventHandler handler;

    private static String serviceName;

    public static void createAndRegister(String name, String host, Integer port, String protocol) {
        serviceName = name;
        handler = new ElasticEventHandler();

        handler.openClient(host, port, protocol);
        EventExporter.registerHandler(serviceName, handler);
    }

    public static void unregister() throws IOException {
        EventExporter.unregisterHandler(serviceName);
        handler.closeClient();
    }
}
