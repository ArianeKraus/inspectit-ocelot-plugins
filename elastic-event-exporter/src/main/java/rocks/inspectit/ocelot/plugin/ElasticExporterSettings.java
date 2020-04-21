package rocks.inspectit.ocelot.plugin;

import lombok.Data;

/**
 * The plugin configuration.
 */
@Data
public class ElasticExporterSettings {
    /**
     * Whether this exporter is enabled or not.
     */
    private boolean enabled;

    /**
     * The service name under which the exporter registers at the exporterService
     */
    private String serviceName;

    /**
     * The hostname under which the elasticsearch node is running.
     * TODO-THESIS: ? Currently only for one node a client can be configured. It should actually be multiple/all nodes?
     * Not necessarily needed for the thesis.
     */
    private String host;

    /**
     * The exposed port for elastic where events will be send to.
     */
    private Integer port;

    /**
     * The protocol to use for the Elastic client. Http or Https.
     */
    private String protocol;

    /**
     * The Elasticsearch index under which the events will be stored. Used by Elasticsearch to categorize documents.
     */
    private String index;
}