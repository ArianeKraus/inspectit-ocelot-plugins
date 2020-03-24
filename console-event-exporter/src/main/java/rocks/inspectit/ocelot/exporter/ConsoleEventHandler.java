package rocks.inspectit.ocelot.exporter;

import rocks.inspectit.ocelot.sdk.events.EventHandler;

import java.util.Collection;

public class ConsoleEventHandler extends EventHandler {

    @Override
    public void export(Collection<Object> events){
        System.out.println("Exporter Printing Events...");
        for(Object event : events){
            System.out.println(event);
        }
    }
}
