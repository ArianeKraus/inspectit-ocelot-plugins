package rocks.inspectit.ocelot.exporter;

import java.util.Collection;

public class ConsoleEventHandler extends OcelotEventPluginHandler {

    @Override
    public void export(Collection<Object> events){
        System.out.println("Exporter Printing Events...");
        for(Object event : events){
            System.out.println(event);
        }
    }
}
