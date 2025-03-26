package com.example.ttools;

import com.example.ttools.model.Project;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProjectControllerService {

    private final ProjectRepository projectRepository;

    @PostMapping("/add")
    public ResponseEntity<Project> addProject(
            @RequestParam(required = false) String projectName
    ) {
        return ResponseEntity.ok(projectRepository.save(Project.builder()
                .name(genRandomProjectName(projectName))
                .build())
        );
    }

    private String genRandomProjectName(String projectName) {
        if (projectName != null && !projectName.isBlank()) { return projectName; };
        return "p-"+ String.valueOf(LocalDateTime.now().getNano()).replaceAll("0", String.valueOf(Math.round(Math.random())));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Project> getProjectById(
            @PathVariable(value = "id") Long projectId
    ) {
        return ResponseEntity.ok(projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("PROJECT NOT FOUND WITH ID :: "+projectId)));
    }

    @PutMapping("/alter/{id}")
    public ResponseEntity<Project> alterProjectById(
            @PathVariable(value = "id") Long projectId,
            @RequestBody ProjectRequestDTO requestDTO
    ) {
        Project project = getProjectById(projectId).getBody();
        Project.(project, requestDTO);
        return ResponseEntity.ok(project);
    }
}
