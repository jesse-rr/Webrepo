package com.example.ttools.model;

import static com.example.ttools.model.PLanguage.*;
import lombok.Getter;

import java.util.List;

@Getter
public enum WebDevTool {
    // Version Control
    GIT("Git", "Version Control", "Code collaboration/history", List.of()),

    // Package Managers
    NPM("npm", "Package Manager", "JavaScript dependencies", List.of(JAVASCRIPT, TYPESCRIPT)),
    YARN("Yarn", "Package Manager", "Faster JS dependency management", List.of(JAVASCRIPT, TYPESCRIPT)),
    PIP("pip", "Package Manager", "Python libraries", List.of(PYTHON)),
    MAVEN("Maven", "Package Manager", "Java dependencies", List.of(JAVA, KOTLIN, SCALA)),

    // Build Tools
    WEBPACK("Webpack", "Bundler", "Bundle JS/CSS/assets", List.of(JAVASCRIPT, TYPESCRIPT)),
    VITE("Vite", "Bundler", "Ultra-fast modern bundler", List.of(JAVASCRIPT, TYPESCRIPT)),
    GRADLE("Gradle", "Build Tool", "Java/Kotlin project builds", List.of(JAVA, KOTLIN, GROOVY)),

    // Deployment
    DOCKER("Docker", "Containerization", "Package apps in containers", List.of()),
    KUBERNETES("Kubernetes", "Orchestration", "Manage containerized apps", List.of()),
    VERCEL("Vercel", "Hosting", "Frontend deployment", List.of(JAVASCRIPT, TYPESCRIPT)),
    NETLIFY("Netlify", "Hosting", "Static site deployment", List.of(JAVASCRIPT, TYPESCRIPT)),

    // Testing
    JEST("Jest", "Testing", "JavaScript unit tests", List.of(JAVASCRIPT, TYPESCRIPT)),
    CYPRESS("Cypress", "Testing", "E2E web testing", List.of()),
    POSTMAN("Postman", "API Testing", "API development/testing", List.of()),

    // Design/Prototyping
    FIGMA("Figma", "Design", "UI/UX prototyping", List.of()),
    ADOBE_XD("Adobe XD", "Design", "Interactive prototypes", List.of()),
    STORYBOOK("Storybook", "Component Library", "UI component isolation", List.of(JAVASCRIPT, TYPESCRIPT)),

    // Performance
    LIGHTHOUSE("Lighthouse", "Auditing", "Website performance metrics", List.of()),
    PAGESPEED("PageSpeed Insights", "Optimization", "Google's performance tool", List.of()),

    // CMS
    WORDPRESS("WordPress", "CMS", "Content management", List.of(PHP)),
    STRAPI("Strapi", "Headless CMS", "API-first content", List.of(JAVASCRIPT, TYPESCRIPT)),
    SANITY("Sanity", "Headless CMS", "Structured content", List.of(JAVASCRIPT, TYPESCRIPT)),

    // Analytics
    GOOGLE_ANALYTICS("Google Analytics", "Analytics", "User tracking", List.of()),
    HOTJAR("Hotjar", "Analytics", "Heatmaps/session recordings", List.of()),

    // Security
    SONARQUBE("SonarQube", "Code Quality", "Security/technical debt", List.of()),
    SNYK("Snyk", "Security", "Dependency vulnerability scanning", List.of());

    private final String name;
    private final String category;
    private final String purpose;
    private final List<PLanguage> compatibleWith;

    WebDevTool(String name, String category, String purpose, List<PLanguage> compatibleWith) {
        this.name = name;
        this.category = category;
        this.purpose = purpose;
        this.compatibleWith = compatibleWith;
    }
}