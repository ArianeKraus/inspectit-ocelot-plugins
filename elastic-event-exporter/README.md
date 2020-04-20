# Elastic Exporter Plugin for ingesting events into Elastic

#TODO-THESIS: Change everything below
Test exporter for Ocelot Events which will write received events to the console.

The plugin offers the following configuration options, which can be configured just like any configuration option of inspectIT Ocelot:
```
inspectit:
  plugins:
    console-events:
      enabled: (true / false, defaults to true)
      service-name: (string, defaults to console-Exporter})
```

After you have configured your stuff, e.g. by providing the following environment variable:
```
INSPECTIT_PLUGINS_CONSOLE-EVENT_ACCESSTOKEN=...
```
the exporter will be started.