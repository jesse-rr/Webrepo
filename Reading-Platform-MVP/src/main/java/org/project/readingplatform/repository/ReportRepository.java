package org.project.readingplatform.repository;

import jakarta.transaction.Transactional;
import org.project.readingplatform.models.Report;
import org.project.readingplatform.models.enums.ReportPriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE report r SET r.priority = :priority WHERE r.reportId = :id")
    void updateReportByPriority(ReportPriority priority, Long id);

    List<Report> findAllByPriority(ReportPriority priority);
}
