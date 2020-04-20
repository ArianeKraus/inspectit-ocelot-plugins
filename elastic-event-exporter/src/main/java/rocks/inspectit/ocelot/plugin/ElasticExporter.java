package rocks.inspectit.ocelot.plugin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import rocks.inspectit.ocelot.config.model.InspectitConfig;
import rocks.inspectit.ocelot.exporter.ElasticEventExporter;
import rocks.inspectit.ocelot.sdk.ConfigurablePlugin;
import rocks.inspectit.ocelot.sdk.OcelotPlugin;

import java.util.Objects;

@Slf4j
@OcelotPlugin(value = "elastic-event", defaultConfig = "default.yml")
public class ElasticExporter implements ConfigurablePlugin<ElasticExporterSettings> {

    /**
     * Whether the exporter has been enabled.
     */
    private boolean enabled = false;

    /**
     * The currently used settings.
     */
    private ElasticExporterSettings activeSettings;

    @Override
    public void start(InspectitConfig inspectitConfig, ElasticExporterSettings exporterSettings) {
        update(inspectitConfig, exporterSettings);
    }

    @Override
    public void update(InspectitConfig inspectitConfig, ElasticExporterSettings settings) {
        boolean enable = inspectitConfig.getEvents().isEnabled()
                && settings.isEnabled()
                && !StringUtils.isEmpty(settings.getServiceName())
                && !StringUtils.isEmpty(settings.getHost())
                && !StringUtils.isEmpty(settings.getProtocol())
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
    public Class<ElasticExporterSettings> getConfigurationClass() {
        return ElasticExporterSettings.class;
    }

    /**
     * Starts the exporter.
     *
     * @param settings the settings to use
     */
    private void start(ElasticExporterSettings settings) {
        try {
            log.info("Starting Elastic event exporter.");
            ElasticEventExporter.createAndRegister(
                    settings.getServiceName(),
                    settings.getHost(),
                    settings.getPort(),
                    settings.getProtocol());

            enabled = true;
            activeSettings = settings;
        } catch (Throwable t) {
            log.error("Error creating Elastic event exporter.", t);
            enabled = false;
        }
//        enabled = true;
//        activeSettings = settings;
    }

    /**
     * Stops the exporter.
     */
    private void stop() {
        log.info("Stopping Elastic event exporter");
        try {
            ElasticEventExporter.unregister();
        } catch (Throwable t) {
            log.error("Error disabling Elastic event exporter", t);
        }
        enabled = false;
    }
}
