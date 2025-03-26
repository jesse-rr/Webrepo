package com.example.ttools.model;

import lombok.Getter;

@Getter
public enum Database {
    // Relational (SQL)
    POSTGRESQL("PostgreSQL", "Relational", "SQL", "General-purpose, ACID compliant"),
    MYSQL("MySQL", "Relational", "SQL", "Web apps, transactional systems"),
    SQL_SERVER("Microsoft SQL Server", "Relational", "SQL", "Enterprise systems, Windows ecosystems"),
    ORACLE("Oracle DB", "Relational", "SQL", "Large-scale enterprise systems"),
    SQLITE("SQLite", "Relational", "SQL", "Embedded systems, mobile apps"),

    // NoSQL
    MONGODB("MongoDB", "Document", "NoSQL", "JSON storage, flexible schemas"),
    COUCHDB("CouchDB", "Document", "NoSQL", "Offline-first apps, sync capabilities"),
    REDIS("Redis", "Key-Value", "NoSQL", "Caching, real-time systems"),
    CASSANDRA("Cassandra", "Wide-Column", "NoSQL", "Time-series, high write throughput"),
    NEO4J("Neo4j", "Graph", "NoSQL", "Relationship-heavy data (social networks, fraud detection)"),

    // NewSQL/Cloud
    COCKROACHDB("CockroachDB", "NewSQL", "SQL", "Distributed SQL, cloud-native"),
    FIRESTORE("Firestore", "Document", "NoSQL", "Serverless, real-time updates (Firebase)"),
    DYNAMODB("DynamoDB", "Key-Value", "NoSQL", "Serverless, AWS ecosystem"),

    // Time-Series
    INFLUXDB("InfluxDB", "Time-Series", "NoSQL", "Metrics, IoT data"),
    TIMESCALE("TimescaleDB", "Time-Series", "SQL", "PostgreSQL extension for time-series"),

    // Search
    ELASTICSEARCH("Elasticsearch", "Search", "NoSQL", "Full-text search, log analytics"),
    SOLR("Apache Solr", "Search", "NoSQL", "Text-heavy search applications");

    private final String name;
    private final String category;
    private final String queryLanguage;
    private final String useCase;

    Database(String name, String category, String queryLanguage, String useCase) {
        this.name = name;
        this.category = category;
        this.queryLanguage = queryLanguage;
        this.useCase = useCase;
    }

}