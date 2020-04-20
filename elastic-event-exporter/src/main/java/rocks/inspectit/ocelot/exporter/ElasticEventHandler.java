package rocks.inspectit.ocelot.exporter;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import rocks.inspectit.ocelot.sdk.events.EventHandler;

import java.io.IOException;
import java.util.Collection;

@Slf4j
public class ElasticEventHandler extends EventHandler {

    RestHighLevelClient client;

    public void openClient(String host, Integer port, String protocol) {
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(host, port, protocol)
                )
        );
        System.out.println("Elastic Event Exporter created an elastic client");
    }

    public void closeClient() throws IOException {
        client.close();
        System.out.println("Elastic Event Exporter closed elastic client");
    }

    @Override
    public void export(Collection<Object> events) {
        System.out.println("Exporter Printing Events...");
        for(Object event : events){
            System.out.println(event);
        }

        /**
         * As of now. The Elastic client should be created and closed accordingly...
         * TODO-THESIS-Find out what happens when wrong host/port is used to create elastic client and catch the error
         * Exporter functions still only logs
         */
    }
}
