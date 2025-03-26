package com.example.ttools.model;

import lombok.Getter;

import java.util.List;

import static com.example.ttools.model.PLanguage.*;

@Getter
public enum Framework {
    SPRING_BOOT("Spring Boot", List.of(JAVA, KOTLIN), "Backend", 2014),
    DJANGO("Django", List.of(PYTHON), "Backend", 2005),
    EXPRESS_JS("Express.js", List.of(JAVASCRIPT, TYPESCRIPT), "Backend", 2010),
    LARAVEL("Laravel", List.of(PHP), "Backend", 2011),
    RAILS("Ruby on Rails", List.of(RUBY), "Backend", 2005),
    ASP_NET_CORE("ASP.NET Core", List.of(C_SHARP), "Backend", 2016),
    FASTAPI("FastAPI", List.of(PYTHON), "Backend", 2018),
    NEST_JS("NestJS", List.of(TYPESCRIPT), "Backend", 2017),
    GIN("Gin", List.of(GO), "Backend", 2014),
    ACTIX("Actix", List.of(RUST), "Backend", 2018),

    // Frontend Frameworks
    REACT("React", List.of(JAVASCRIPT, TYPESCRIPT), "Frontend", 2013),
    ANGULAR("Angular", List.of(TYPESCRIPT), "Frontend", 2016),
    VUE("Vue.js", List.of(JAVASCRIPT), "Frontend", 2014),
    SVELTE("Svelte", List.of(JAVASCRIPT), "Frontend", 2016),
    NEXT_JS("Next.js", List.of(JAVASCRIPT, TYPESCRIPT), "Frontend (SSR)", 2016),
    NUXT_JS("Nuxt.js", List.of(JAVASCRIPT), "Frontend (SSR)", 2016),

    // Full-Stack/Meta Frameworks
    NODE_JS("Node.js", List.of(JAVASCRIPT), "Backend/Runtime", 2009),
    DENO("Deno", List.of(TYPESCRIPT), "Backend/Runtime", 2020),
    REMIX("Remix", List.of(TYPESCRIPT), "Full-Stack", 2021),
    SVELTEKIT("SvelteKit", List.of(JAVASCRIPT), "Full-Stack", 2021),
    ASTRO("Astro", List.of(JAVASCRIPT), "Frontend/SSG", 2021);

    private final String name;
    private final List<PLanguage> languageList;
    private final String type;
    private final int year;

    Framework(String name, List<PLanguage> languageList, String type, int year) {
        this.name = name;
        this.languageList = languageList;
        this.type = type;
        this.year = year;
    }
}
