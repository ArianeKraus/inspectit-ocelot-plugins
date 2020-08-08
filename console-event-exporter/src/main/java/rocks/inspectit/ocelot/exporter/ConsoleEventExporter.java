package rocks.inspectit.ocelot.exporter;

import rocks.inspectit.ocelot.sdk.events.EventRegistryService;

public class ConsoleEventExporter {

    private static ConsoleEventHandler handler;

    private static String serviceName;

    public static void createAndRegister(String name) {
        serviceName = name;
        handler = new ConsoleEventHandler();
        EventRegistryService.registerHandler(serviceName, handler);
    }

    public static void unregister() {
        EventRegistryService.unregisterHandler(serviceName);
    }
}
