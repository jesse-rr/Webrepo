package com.handleGod.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EndpointInfo {
    private final String methods;
    private final String path;
    private final String controller;
    private final String handlerMethod;
}
