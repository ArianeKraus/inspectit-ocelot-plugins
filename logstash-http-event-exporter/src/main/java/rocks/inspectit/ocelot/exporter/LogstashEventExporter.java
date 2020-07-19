package rocks.inspectit.ocelot.exporter;

import lombok.extern.slf4j.Slf4j;
import rocks.inspectit.ocelot.sdk.events.EventRegistryService;

import java.net.MalformedURLException;

@Slf4j
public class LogstashEventExporter {

    private static LogstashEventHandler handler;

    private static String serviceName;

    public static void createAndRegister(String name, String host, Integer port) throws MalformedURLException {
        serviceName = name;
        handler = new LogstashEventHandler(host, port);
        EventRegistryService.registerHandler(serviceName, handler);
    }

    public static void unregister() {
        EventRegistryService.unregisterHandler(serviceName);
        handler = null;
        serviceName = null;
    }
}
