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
    private boolean isRemoved;

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

    public void updateFromDTO(ProjectRequestDTO dto) {
        this.name = dto.name();
        this.description = dto.description();
        this.links = dto.links();
        this.readMe = dto.readMe();
        this.webDevTools = dto.webDevTools();
        this.programmingLanguages = dto.programmingLanguages();
        this.databases = dto.databases();
        this.sudoLanguages = dto.sudoPLanguages();
        this.frameworks = dto.frameworks();
    }
}
