package com.project.inventorymanagement.model.helper;

import java.util.Set;

public enum Role {
    ADMIN("ROLE_ADMIN", Authority.Defaults.ADMIN),
    MANAGER("ROLE_MANAGER", Authority.Defaults.MANAGER),
    STAFF("ROLE_STAFF", Authority.Defaults.STAFF),
    NON_AUTHORIZED("ROLE_NON_AUTHORIZED", Authority.Defaults.NON_AUTHORIZED);

    private final String roleName;
    private final Set<Authority> defaultAuthorities;

    Role(String roleName, Set<Authority> defaultAuthorities) {
        this.roleName = roleName;
        this.defaultAuthorities = defaultAuthorities;
    }

    public String getRoleName() {
        return roleName;
    }

    public Set<Authority> getDefaultAuthorities() {
        return defaultAuthorities;
    }
}
