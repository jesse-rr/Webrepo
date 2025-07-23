package org.project.readingplatform.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.project.readingplatform.dto.ReportDTO;
import org.project.readingplatform.models.Report;
import org.project.readingplatform.models.enums.ReportPriority;
import org.project.readingplatform.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<String> sendReport(
            @RequestBody @Valid ReportDTO request
    ) {
        return ResponseEntity.ok(reportService.sendReport(request));
    }

    @PostMapping("/{report-id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> alterPriority(
            @PathVariable("report-id") Long reportId,
            @RequestParam @NotNull ReportPriority priority
    ) {
        reportService.alterPriority(reportId, priority);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Report>> findAllReportsByPriority(
            @RequestParam @NotNull ReportPriority priority
    ) {
        return ResponseEntity.ok(reportService.findAllReportsByPriority(priority));
    }
}
