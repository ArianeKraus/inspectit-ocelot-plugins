# Console-Test Exporter Plugin for Events

Test exporter for Ocelot Events which will write received events to the console.

The plugin offers the following configuration options, which can be configured just like any configuration option of inspectIT Ocelot:
```
inspectit:
  plugins:
    console-events:
      enabled: (true / false, defaults to true)
      service-name: (string, defaults to console-Exporter})
```

Exporter will start using the default configuration if nothing is provided.