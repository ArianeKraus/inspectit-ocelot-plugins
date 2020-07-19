package rocks.inspectit.ocelot.exporter;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import rocks.inspectit.ocelot.sdk.events.OcelotEventPluginHandler;
import rocks.inspectit.ocelot.sdk.events.Event;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Receives EventObjects from inspectIT ocelot and attempts to export them to Elasticsearch.
 */
@Slf4j
public class ElasticEventHandler extends OcelotEventPluginHandler {

    RestHighLevelClient client;

    String elasticIndex;

    public ElasticEventHandler(String index) {
        elasticIndex = index;
    }

    public void openClient(String host, Integer port, String protocol) {
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(host, port, protocol)
                )
        );
        System.out.println("Elastic Event Exporter created an elastic client. Host:" + host + "/Port:" + port);
    }

    public void closeClient() throws IOException {
        client.close();
        System.out.println("Elastic Event Exporter closed elastic client");
    }

    @Override
    public void export(Collection<Event> events) {
        try{
            BulkRequest request = new BulkRequest(elasticIndex);

            for(Event event : events) {
                Map<String, Object> jsonMap = new HashMap<>();
                jsonMap.put("event-name", event.getName());
                jsonMap.put("timestamp", event.getTimestamp());
                jsonMap.put("attributes", event.getAttributes());

                request.add(new IndexRequest().source(jsonMap, XContentType.JSON));
            }

            client.bulkAsync(request, RequestOptions.DEFAULT, new ActionListener<BulkResponse>() {
                @Override
                public void onResponse(BulkResponse bulkResponse) {
                    if(bulkResponse.hasFailures()){
                        log.info("Bulk request to elastic has failed: " + bulkResponse.buildFailureMessage());
                    } else {
                        // Do nothing
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    log.error("Events could not be sent to Elasticsearch", e);
                }
            });
        } catch (Throwable t) {
            log.error("Sending bulk request to Elasticsearch threw an exception", t);
        }
    }
}
