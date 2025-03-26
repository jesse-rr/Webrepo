package com.example.ttools;

import com.example.ttools.model.Database;
import com.example.ttools.model.Framework;
import com.example.ttools.model.SudoPLanguage;
import com.example.ttools.model.WebDevTool;

import java.util.List;

public record ProjectRequestDTO(
        String name,
        String description,
        String links,
        String readMe,
        List<Framework> frameworks,
        List<SudoPLanguage> sudoLanguages,
        List<Database> databases,
        List<WebDevTool> webDevTools
) {
}
