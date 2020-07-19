package rocks.inspectit.ocelot.plugin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import rocks.inspectit.ocelot.config.model.InspectitConfig;
import rocks.inspectit.ocelot.exporter.LogstashEventExporter;
import rocks.inspectit.ocelot.sdk.ConfigurablePlugin;
import rocks.inspectit.ocelot.sdk.OcelotPlugin;

import java.util.Objects;

@Slf4j
@OcelotPlugin(value = "logstash-event-export", defaultConfig = "default.yml")
public class LogstashExporter implements ConfigurablePlugin<LogstashExporterSettings> {

    /**
     * Whether the exporter has been enabled.
     */
    private boolean enabled = false;

    /**
     * The currently used settings.
     */
    private LogstashExporterSettings activeSettings;

    @Override
    public void start(InspectitConfig inspectitConfig, LogstashExporterSettings exporterSettings) {
        update(inspectitConfig, exporterSettings);
    }

    @Override
    public void update(InspectitConfig inspectitConfig, LogstashExporterSettings settings) {
        boolean enable = inspectitConfig.getEvents().isEnabled()
                && settings.isEnabled()
                && !StringUtils.isEmpty(settings.getServiceName())
                && !StringUtils.isEmpty(settings.getHost())
                && !StringUtils.isEmpty(settings.getPort());

        //we force a restart if the access token has changed
        if (enabled && (!enable || !Objects.equals(activeSettings, settings))) {
            stop();
        }
        if (!enabled && enable) {
            start(settings);
        }
    }

    @Override
    public Class<LogstashExporterSettings> getConfigurationClass() {
        return LogstashExporterSettings.class;
    }

    /**
     * Starts the exporter.
     *
     * @param settings the settings to use
     */
    private void start(LogstashExporterSettings settings) {
        try {
            log.info("Starting Elastic event exporter.");
            LogstashEventExporter.createAndRegister(
                    settings.getServiceName(),
                    settings.getHost(),
                    settings.getPort());

            enabled = true;
            activeSettings = settings;
        } catch (Throwable t) {
            log.error("Error creating Logstash http event exporter.", t);
            enabled = false;
        }
    }

    /**
     * Stops the exporter.
     */
    private void stop() {
        log.info("Stopping Logstash http event exporter");
        try {
            LogstashEventExporter.unregister();
        } catch (Throwable t) {
            log.error("Error disabling Logstash http event exporter", t);
        }
        enabled = false;
    }
}
