package org.project.readingplatform.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ESClient {

    @Bean
    public ElasticsearchClient getElasticSearchClient() {
        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "https"));

        RestClientBuilder.HttpClientConfigCallback httpClientConfigCallback = new HttpClientConfigImpl();
        builder.setHttpClientConfigCallback(httpClientConfigCallback);

        RestClient restClient = builder.build();

        RestClientTransport restClientTransport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        return new ElasticsearchClient(restClientTransport);
    }

//    Previously we discussed how to configure the Elasticsearch client to establish a secure and authenticated connection with the Elasticsearch cluster.
//    However, to fully integrate Elasticsearch into our Spring Boot application,
//    we need to create a bean that encapsulates the client configuration and provides an instance of the ElasticsearchClient class.
//    The ESClient class, annotated with @Component, serves this purpose. Let’s break down the getElasticsearchClient method within this class:
//
//          - Initializing the RestClientBuilder: The RestClientBuilder is initialized with an HttpHost object, which specifies the hostname, port, and protocol for connecting to the Elasticsearch cluster.
//                                                  In this example, the client is configured to connect to a local Elasticsearch instance running on http://localhost:9200.
//          - Setting the HttpClientConfigCallback: The HttpClientConfigCallback implementation, HttpClientConfigImpl, is instantiated and set on the RestClientBuilder.
//                                                  This callback is responsible for configuring the HttpAsyncClientBuilder with the necessary credentials and SSL/TLS settings, as discussed in the previous section.
//          - Building the RestClient: The RestClient instance is created using the configured RestClientBuilder.
//          - Creating the RestClientTransport: The RestClientTransport is a wrapper around the RestClient that provides a low-level transport layer for communicating with the Elasticsearch cluster.
//                                              It is initialized with the RestClient instance and a JacksonJsonpMapper for handling JSON serialization and deserialization.
//          - Instantiating the ElasticsearchClient: Finally, an ElasticsearchClient instance is created using the RestClientTransport.
//                                                   This client provides a high-level API for interacting with the Elasticsearch cluster, allowing developers to perform various operations such as indexing documents,
//                                                   executing searches, and managing indices.

}
