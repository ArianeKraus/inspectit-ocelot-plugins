package rocks.inspectit.ocelot.exporter;

import lombok.extern.slf4j.Slf4j;
import rocks.inspectit.ocelot.sdk.events.OcelotEventPluginHandler;
import rocks.inspectit.ocelot.sdk.events.Event;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

/**
 * Receives EventObjects from inspectIT ocelot and attempts to export them to Elasticsearch.
 */
@Slf4j
public class LogstashEventHandler extends OcelotEventPluginHandler {

    URL url;

    HttpURLConnection connection;

    public LogstashEventHandler(String host, Integer port) throws MalformedURLException {
        url = new URL("http://" + host + ":" + port);
    }

    @Override
    public void export(Collection<Event> events) {
        try {
            /** Create Connection */
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Content-Type", "application/json");

//            connection.setUseCaches(false); // Allows protocol to use caching?
            connection.setDoInput(true); // Not intending to read data.
            connection.setDoOutput(true);

            /** Send Request */
            DataOutputStream outstream = new DataOutputStream(connection.getOutputStream());
            outstream.writeBytes("{\"test-ocelot\": 30}");
            outstream.flush ();
            outstream.close();


            /** Only get any Error Response */
            int statusCode = connection.getResponseCode();
            Reader streamReader = null;
            if(statusCode > 299) {
                streamReader = new InputStreamReader(connection.getErrorStream());
            }

            if(streamReader != null) {
                BufferedReader buffReader = new BufferedReader(streamReader);
                String line = null;
                while ( (line = buffReader.readLine()) != null ) {
                    log.error("Request towards Logstash returned an error", line);
                }
            } else {
                System.out.println("Request to logstash successful");
            }

        } catch (Throwable t) {
            log.error("Sending request to Logstash http input plugin threw an exception", t);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
