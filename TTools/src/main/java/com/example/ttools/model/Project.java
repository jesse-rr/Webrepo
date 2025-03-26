package com.example.ttools.model;

import com.example.ttools.ProjectRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity(name = "project")
@Table(name = "project")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(unique = true, nullable = false)
    private String name;
    private String description;
    @ElementCollection
    private Set<String> links;
    private String readMe;

    @ElementCollection
    private Set<PLanguage> programmingLanguages;
    @ElementCollection
    private Set<Framework> frameworks;
    @ElementCollection
    private Set<SudoPLanguage> sudoLanguages;
    @ElementCollection
    private Set<Database> databases;
    @ElementCollection
    private Set<WebDevTool> webDevTools;

    public static void toProject() {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.links = links;
        this.readMe = readMe;
        this.programmingLanguages = programmingLanguages;
        this.frameworks = frameworks;
        this.sudoLanguages = sudoLanguages;
        this.databases = databases;
        this.webDevTools = webDevTools;
    }
}
