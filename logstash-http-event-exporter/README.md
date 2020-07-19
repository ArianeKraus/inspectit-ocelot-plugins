# Exporter for sending Ocelot Events to Logstash HTTP Input Plugin

Exporter for sending Ocelot events towards [Logstash HTTP input plugin](https://github.com/logstash-plugins/logstash-input-http).

The plugin offers the following configuration options, which can be configured just like any configuration option of inspectIT Ocelot:

```
inspectit:
  plugins:
    logstash-event-exporter:
      enabled: (true / false, defaults to true)
      service-name: (string, defaults to logstash-event-exporter)
      host: (string, defaults to localhost)
      port: (int, defaults to 8080)
```

Build this project and [tell Ocelot where to find your the Jar](https://inspectit.github.io/inspectit-ocelot/docs/configuration/plugin-configuration) to start the exporter.
Configure the exporter, e.g., by providing the following environment variable:
```
INSPECTIT_PLUGINS_LOGSTASH-EVENT-EXPORTER_SERVICE-NAME=...
```

