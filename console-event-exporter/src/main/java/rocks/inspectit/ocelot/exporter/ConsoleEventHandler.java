package rocks.inspectit.ocelot.exporter;

import rocks.inspectit.ocelot.sdk.events.Event;
import rocks.inspectit.ocelot.sdk.events.OcelotEventPluginHandler;

import java.util.Collection;

public class ConsoleEventHandler extends OcelotEventPluginHandler {


    @Override
    public void export(Collection<Event> collection) {
        System.out.println("Exporter Printing Events...");
        for(Object event : collection){
            System.out.println(event);
        }
    }
}
