package com.example.ttools;

import com.example.ttools.model.*;

import java.util.Set;

public record ProjectRequestDTO(
        String name,
        String description,
        Set<String> links,
        String readMe,
        Set<PLanguage> programmingLanguages,
        Set<Framework> frameworks,
        Set<SudoPLanguage> sudoPLanguages,
        Set<Database> databases,
        Set<WebDevTool> webDevTools
) {
}
