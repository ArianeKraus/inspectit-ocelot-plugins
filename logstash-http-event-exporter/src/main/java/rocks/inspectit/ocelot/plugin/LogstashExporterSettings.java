package rocks.inspectit.ocelot.plugin;

import lombok.Data;

/**
 * The plugin configuration.
 */
@Data
public class LogstashExporterSettings {
    /**
     * Whether this exporter is enabled or not.
     */
    private boolean enabled;

    /**
     * The service name under which the exporter registers at the Ocelot exporterService
     */
    private String serviceName;

    /**
     * The hostname under which the logstash http input plugin is running.
     * Currently only for one node a client can be configured.
     */
    private String host;

    /**
     * The exposed port for logstash http input plugin where events will be send to.
     */
    private Integer port;
}