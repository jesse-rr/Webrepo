package org.project.readingplatform.config;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.io.File;

@Configuration
public class HttpClientConfigImpl implements RestClientBuilder.HttpClientConfigCallback {


    @Override
    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
        try {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            UsernamePasswordCredentials usernamePasswordCredentials = new UsernamePasswordCredentials("elastic", "password");
            credentialsProvider.setCredentials(AuthScope.ANY, usernamePasswordCredentials);
            httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);

            String trustLocationStore = "/home/jrr/elasticsearch-8.8.2/config/certs/truststore.p12"; // TODO ALTER DEPENDING ON YOUR LOCATION
            File trustLocationFile = new File(trustLocationStore);

            SSLContextBuilder sslContextBuilder = SSLContexts.custom()
                    .loadTrustMaterial(
                            trustLocationFile,
                            "password".toCharArray()
                    );
            SSLContext sslContext = sslContextBuilder.build();
            httpAsyncClientBuilder.setSSLContext(sslContext);
        } catch (Exception e) {
        }
        return httpAsyncClientBuilder;
    }
//    The HttpClientConfigImpl class is a Spring configuration class that implements the HttpClientConfigCallback interface.
//    This interface allows developers to customize the HttpAsyncClientBuilder, which is responsible for creating the client used to communicate with Elasticsearch.
//    The HttpClientConfigImpl class performs the following tasks:
//      - Authentication Setup: The class creates a CredentialsProvider object and sets the username and password credentials required for authentication with the Elasticsearch cluster.
//      - SSL/TLS Configuration: To establish a secure connection with the Elasticsearch cluster, the class loads an SSL/TLS truststore from a specified file path.
//                               The truststore is a file that contains trusted Certificate Authorities (CAs) used for verifying the server’s identity during the SSL/TLS handshake.
//                               The class creates an SSLContext object using the SSLContextBuilder and loads the truststore file into it, using the provided password
//      - Client Configuration: Finally, the class sets the CredentialsProvider and SSLContext on the HttpAsyncClientBuilder.
//                              This builder is then used to create the Elasticsearch client, ensuring that the client can authenticate with the Elasticsearch cluster
//                              using the provided credentials and establish a secure connection using the specified truststore
}
