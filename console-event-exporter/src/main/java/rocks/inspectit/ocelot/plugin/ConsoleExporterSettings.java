package rocks.inspectit.ocelot.plugin;

import lombok.Data;

/**
 * The plugin configuration.
 */
@Data
public class ConsoleExporterSettings {
    /**
     * Whether this exporter is enabled or not.
     */
    private boolean enabled;

    /**
     * The service name under which the exporter registers at the exporterService
     */
    private String serviceName;
}