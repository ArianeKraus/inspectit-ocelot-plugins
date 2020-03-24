package rocks.inspectit.ocelot.plugin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import rocks.inspectit.ocelot.config.model.InspectitConfig;
import rocks.inspectit.ocelot.exporter.ConsoleEventExporter;
import rocks.inspectit.ocelot.sdk.ConfigurablePlugin;
import rocks.inspectit.ocelot.sdk.OcelotPlugin;

import java.util.Objects;

@Slf4j
@OcelotPlugin(value = "console-event", defaultConfig = "default.yml")
public class ConsoleExporter implements ConfigurablePlugin<ConsoleExporterSettings> {

    /**
     * Whether the exporter has been enabled.
     */
    private boolean enabled = false;

    /**
     * The currently used settings.
     */
    private ConsoleExporterSettings activeSettings;

    @Override
    public void start(InspectitConfig inspectitConfig, ConsoleExporterSettings exporterSettings) {
        update(inspectitConfig, exporterSettings);
    }

    @Override
    public void update(InspectitConfig inspectitConfig, ConsoleExporterSettings settings) {
        boolean enable = inspectitConfig.getEvents().isEnabled()
                && settings.isEnabled()
                && !StringUtils.isEmpty(settings.getServiceName());

        //we force a restart if the access token has changed
        if (enabled && (!enable || !Objects.equals(activeSettings, settings))) {
            stop();
        }
        if (!enabled && enable) {
            start(settings);
        }
    }

    @Override
    public Class<ConsoleExporterSettings> getConfigurationClass() {
        return ConsoleExporterSettings.class;
    }

    /**
     * Starts the exporter.
     *
     * @param settings the settings to use
     */
    private void start(ConsoleExporterSettings settings) {
        try {
            log.info("Starting Console Debug Exporter");
            ConsoleEventExporter.createAndRegister(settings.getServiceName());
        } catch (Throwable t) {
            log.error("Error creating Console Debug exporter", t);
        }
        enabled = true;
        activeSettings = settings;
    }

    /**
     * Stops the exporter.
     */
    private void stop() {
        log.info("Stopping Console Event Exporter");
        try {
            ConsoleEventExporter.unregister();
        } catch (Throwable t) {
            log.error("Error disabling Console Debug exporter", t);
        }
        enabled = false;
    }
}
