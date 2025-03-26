package com.example.ttools.model;

import lombok.Getter;

@Getter
public enum SudoPLanguage {
    HTML("HTML", "HyperText Markup Language", 1993, "Markup", "Structure web content"),
    XML("XML", "eXtensible Markup Language", 1998, "Markup", "Data representation and exchange"),
    MARKDOWN("Markdown", "Lightweight Markup", 2004, "Markup", "Formatted text documentation"),

    // Style/Design
    CSS("CSS", "Cascading Style Sheets", 1996, "Stylesheet", "Web page styling"),
    SASS("SASS", "Syntactically Awesome Stylesheets", 2006, "Stylesheet", "CSS preprocessor"),
    SCSS("SCSS", "Sassy CSS", 2006, "Stylesheet", "CSS superset with nesting"),

    // Query Languages
    GQL("GQL", "Graph Query Language", 2020, "Query", "Graph database queries (Neo4j)"),
    CQL("CQL", "Cassandra Query Language", 2010, "Query", "Apache Cassandra queries"),

    // Config/Serialization
    JSON("JSON", "JavaScript Object Notation", 2001, "Data", "Lightweight data interchange"),
    YAML("YAML", "YAML Ain't Markup Language", 2001, "Data", "Human-readable configuration"),
    TOML("TOML", "Tom's Obvious Minimal Language", 2013, "Data", "Configuration files"),

    // Templating
    JSX("JSX", "JavaScript XML", 2013, "Templating", "React component markup"),
    HTMX("HTMX", "Hypertext Markup eXtensions", 2020, "Templating", "Modern HTML extensions"),

    // Other
    REGEX("Regex", "Regular Expressions", 1951, "Pattern", "Text pattern matching"),
    BASH("Bash", "Bourne-Again Shell", 1989, "Scripting", "Unix shell scripting"),
    DOCKERFILE("Dockerfile", "Docker Configuration", 2013, "Config", "Container build instructions");

    private final String name;
    private final String fullName;
    private final int year;
    private final String category;
    private final String description;

    SudoPLanguage(String name, String fullName, int year, String category, String description) {
        this.name = name;
        this.fullName = fullName;
        this.year = year;
        this.category = category;
        this.description = description;
    }
}