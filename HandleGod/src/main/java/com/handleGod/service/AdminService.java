package com.handleGod.service;

import com.handleGod.model.dto.EndpointInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    public Set<EndpointInfo> getAllUserEndpoints() {
        log.info("RETRIEVING ALL USER ENDPOINTS AT :: {}", Instant.now().toString());

        return requestMappingHandlerMapping.getHandlerMethods().entrySet().stream()
                .filter(entry -> !entry.getValue().getBeanType().getName().contains("org.springframework.boot.actuator"))
                .map(this::mapToEndpointInfo)
                .collect(Collectors.toSet());
    }

    private EndpointInfo mapToEndpointInfo(Map.Entry<RequestMappingInfo, HandlerMethod> request) {
        return new EndpointInfo(
                request.getKey().getMethodsCondition().getMethods().stream()
                        .map(method -> method.asHttpMethod().name())
                        .collect(Collectors.joining(",")),
                request.getKey().getPatternValues().stream().findFirst().orElse(""),
                request.getValue().getBeanType().getSimpleName(),
                request.getValue().getMethod().getName()
        );
    }
}
