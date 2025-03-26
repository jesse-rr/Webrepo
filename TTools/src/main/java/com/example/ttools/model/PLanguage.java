package com.example.ttools.model;

import lombok.Getter;

@Getter
public enum PLanguage {
    JAVA("Java", 1995, "Object-Oriented, Imperative", true),
    PYTHON("Python", 1991, "Multi-paradigm (OOP, Functional)", false),
    C("C", 1972, "Procedural, Imperative", true),
    C_SHARP("C#", 2000, "Object-Oriented, Functional, Imperative", true),
    C_PLUS_PLUS("C++", 1985, "Object-Oriented, Generic", true),
    JAVASCRIPT("JavaScript", 1995, "Multi-paradigm (OOP, Functional)", false),
    KOTLIN("Kotlin", 2011, "Object-Oriented, Functional", true),
    GO("Go", 2009, "Procedural, Concurrent", true),
    RUST("Rust", 2010, "Multi-paradigm (Functional, OOP)", true),
    SWIFT("Swift", 2014, "Object-Oriented, Functional", true),
    RUBY("Ruby", 1995, "Object-Oriented, Scripting", false),
    PHP("PHP", 1995, "Procedural, OOP", false),
    TYPESCRIPT("TypeScript", 2012, "Object-Oriented, Functional", false),
    LUA("Lua", 1993, "Scripting, Imperative", false),
    DART("Dart", 2011, "Object-Oriented, Functional", true),
    ZIG("Zig", 2016, "Systems, Imperative", true),
    ELIXIR("Elixir", 2012, "Functional, Concurrent", false),
    JULIA("Julia", 2012, "Multi-paradigm (Scientific Computing)", false),
    R("R", 1993, "Statistical, Functional", false),
    HASKELL("Haskell", 1990, "Purely Functional", true),
    SCALA("Scala", 2004, "Object-Oriented, Functional", true),
    PERL("Perl", 1987, "Procedural, Scripting", false),
    GROOVY("Groovy", 2003, "Object-Oriented, Scripting", false),
    COBOL("COBOL", 1959, "Procedural, Business-Oriented", true),
    FORTRAN("Fortran", 1957, "Procedural, Scientific", true),
    OCAML("OCaml", 1996, "Functional, Imperative", true),
    CLOJURE("Clojure", 2007, "Functional, Lisp", false),
    ERLANG("Erlang", 1986, "Functional, Concurrent", true),
    BASH("Bash", 1989, "Scripting, Imperative", false),
    POWERSHELL("PowerShell", 2006, "Scripting, Object-Oriented", false),
    SQL("SQL", 1974, "Declarative, Query Language", false),
    ASSEMBLY("Assembly", 1949, "Low-Level, Imperative", true);

    private final String name;
    private final int year;
    private final String paradigm;
    private final boolean isCompiled;

    PLanguage(String name, int year, String paradigm, boolean isCompiled) {
        this.name = name;
        this.year = year;
        this.paradigm = paradigm;
        this.isCompiled = isCompiled;
    }
}
