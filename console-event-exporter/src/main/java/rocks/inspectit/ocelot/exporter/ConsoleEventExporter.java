package rocks.inspectit.ocelot.exporter;

import rocks.inspectit.ocelot.sdk.events.EventExporter;

public class ConsoleEventExporter {

    private static ConsoleEventHandler handler;

    private static String serviceName;

    public static void createAndRegister(String name) {
        serviceName = name;
        handler = new ConsoleEventHandler();
        EventExporter.registerHandler(serviceName, handler);
    }

    public static void unregister() {
        EventExporter.unregisterHandler(serviceName);
    }
}
