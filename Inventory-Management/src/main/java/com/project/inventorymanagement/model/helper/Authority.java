package com.project.inventorymanagement.model.helper;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public enum Authority {
    CATEGORY_READ("category:read"),
    CATEGORY_CREATE("category:create"),
    CATEGORY_UPDATE("category:update"),
    CATEGORY_DELETE("category:delete"),

    PRODUCT_READ("product:read"),
    PRODUCT_CREATE("product:create"),
    PRODUCT_UPDATE("product:update"),
    PRODUCT_DELETE("product:delete"),

    INVENTORY_READ("inventory:read"),
    INVENTORY_UPDATE("inventory:update"),
    INVENTORY_ADJUST("inventory:adjust"),

    SUPPLIER_READ("supplier:read"),
    SUPPLIER_CREATE("supplier:create"),
    SUPPLIER_UPDATE("supplier:update"),
    SUPPLIER_DELETE("supplier:delete"),

    ORDER_READ("order:read"),
    ORDER_CREATE("order:create"),
    ORDER_UPDATE("order:update"),
    ORDER_APPROVE("order:approve"),

    USER_READ("user:read"),
    USER_CREATE("user:create"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:delete"),
    USER_ROLE_MANAGE("user:role_manage"),

    REPORT_VIEW("report:view"),
    REPORT_GENERATE("report:generate"),

    SYSTEM_CONFIG_UPDATE("system:config_update");

    private final String authority;

    Authority(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public static class Defaults {
        public static final Set<Authority> ADMIN = Set.of(Authority.values());

        public static final Set<Authority> MANAGER = Collections.unmodifiableSet(
                Arrays.stream(new Authority[]{
                        CATEGORY_READ, CATEGORY_CREATE, CATEGORY_UPDATE,
                        PRODUCT_READ, PRODUCT_CREATE, PRODUCT_UPDATE,
                        INVENTORY_READ, INVENTORY_UPDATE, INVENTORY_ADJUST,
                        SUPPLIER_READ, SUPPLIER_CREATE, SUPPLIER_UPDATE,
                        ORDER_READ, ORDER_CREATE, ORDER_UPDATE, ORDER_APPROVE,
                        USER_READ,
                        REPORT_VIEW, REPORT_GENERATE
                }).collect(Collectors.toSet())
        );

        public static final Set<Authority> STAFF = Collections.unmodifiableSet(
                Arrays.stream(new Authority[]{
                        PRODUCT_READ,
                        INVENTORY_READ,
                        SUPPLIER_READ,
                        ORDER_READ, ORDER_CREATE,
                        REPORT_VIEW
                }).collect(Collectors.toSet())
        );

        public static final Set<Authority> NON_AUTHORIZED = Collections.emptySet();
    }
}