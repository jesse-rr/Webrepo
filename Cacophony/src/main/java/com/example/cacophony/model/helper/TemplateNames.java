package com.example.cacophony.model.helper;

public enum TemplateNames {
    EMAIL_VERIFICATION("email-verification"),
    PASSWORD_RESET("password-reset"),
    SUSPICIOUS_LOGIN("suspicious-login"),

    ACCOUNT_SUSPENDED("account-suspended"),
    ACCOUNT_DELETION("account-deletion"),
    ACCOUNT_UPDATE("account-update"),

    COMMUNITY_CREATION("community-creation"),
    COMMUNITY_DELETION("community-deletion"),
    COMMUNITY_OWNERSHIP_TRANSFER("community-ownership-transfer"),
    SERVER_INVITATION("server-invitation"),
    ROLE_ASSIGNMENT("role-assignment");

    private final String name;

    TemplateNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
