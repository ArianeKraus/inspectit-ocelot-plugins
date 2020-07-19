# Elastic Exporter Plugin for ingesting events into Elastic

Exporter for Ocelot Events which will ingest received events to Elasticsearch.

The plugin offers the following configuration options, which can be configured just like any configuration option of inspectIT Ocelot:
```
inspectit:
  plugins:
    elastic-event-exporter:
      enabled: (true / false, defaults to true)
      service-name: (string, defaults to elastic-event-exporter})
      host: (string, defaults to localhost)
      port: (int, defaults to 9200)
      protocol: (string, defaults to http)
      index: (string, defaults to inspectit-events)
```

After you have configured your stuff, e.g. by providing the following environment variable:
```
INSPECTIT_PLUGINS_ELASTIC-EVENT-EXPORTER_SERVICE-NAME=...
```
the exporter will be started.

## Notes
* Even tough events can contain any kind of Java object as attribute value, Elasticsearch can only handle primitive types (similar to JSON types) like String, Long or Boolean. 

* Consider creating your preferred index in Elasticsearch beforehand and setting the timestamp attribute as elastics timestamp datatype.
 