package com.example.vpn.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExtractionMethod {
    SRC("src"),
    HREF("href"),
    ALT("alt"),
    TITLE("title"),
    TEXT("text"),
    CLASS("class"),
    ID("id"),
    STYLE("style"),
    VALUE("value"),
    DISABLED("disabled"),
    CHECKED("checked"),
    PLACEHOLDER("placeholder"),
    ACTION("action"),
    METHOD("method"),
    TARGET("target"),
    REL("rel"),
    NAME("name"),
    TYPE("type"),
    ALL("all"),
    USER_CHOICE("");

    private final String value;
}
