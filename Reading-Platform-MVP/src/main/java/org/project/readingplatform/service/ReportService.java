package org.project.readingplatform.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.readingplatform.dto.ReportDTO;
import org.project.readingplatform.models.Report;
import org.project.readingplatform.models.enums.ReportPriority;
import org.project.readingplatform.models.enums.ReportType;
import org.project.readingplatform.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.project.readingplatform.models.enums.ReportPriority.UNDEFINED;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {

    private final ReportRepository reportRepository;
    private final AccountRepository accountRepository;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;

    public String sendReport(ReportDTO request) {
        log.info("ADDING REPORT WITH TYPE <{}> AND INFO <{}>",request.type(), request.userInfo());
        var account = accountRepository.findById(request.accountUUID())
                .orElseThrow(() -> new EntityNotFoundException(String.format("ACCOUNT NOT FOUND WITH ID :: <%s>",request.accountUUID())));

        var report = Report.builder()
                .account(account)
                .userInfo(request.userInfo())
                .createdAt(LocalDateTime.now())
                .priority(UNDEFINED)
                .reportNumber(UUID.randomUUID().toString().substring(0,8))
                .type(request.type())
                .build();

        switch (request.type()) {
            case COMMENT:
                report.setComment(commentRepository.findById(request.commentId())
                        .orElseThrow(() -> new EntityNotFoundException(String.format("COMMENT NOT FOUND WITH ID :: <%s>",request.commentId()))));
                break;
            case REVIEW:
                report.setReview(reviewRepository.findById(request.reviewId())
                        .orElseThrow(() -> new EntityNotFoundException(String.format("REVIEW NOT FOUND WITH ID :: <%s>",request.reviewId()))));
                break;
            case CHAPTER_MISSING:
                report.setBook(bookRepository.findById(request.bookId())
                        .orElseThrow(() -> new EntityNotFoundException(String.format("BOOK NOT FOUND WITH ID :: <%s>",request.bookId()))));
                break;
            case INTERACTIVE, OTHER:
                log.info("PLEASE DESCRIBE THE ERROR");
                break;
            default:
                throw new RuntimeException("NOT AN OPTION FOR REPORT TYPE");
        }
        reportRepository.save(report);

        return "REPORT HAS BEEN SEND WITH NUMBER :: "+report.getReportNumber();
    }

    public void alterPriority(Long reportId, ReportPriority priority) {
        log.info("UPDATING REPORT PRIORITY WITH ID :: <{}>",reportId);
        reportRepository.updateReportByPriority(priority, reportId);
    }

    public List<Report> findAllReportsByPriority(ReportPriority priority) {
        log.info("FINDING ALL REPORTS WITH PRIORITY :: <{}>",priority);
        return reportRepository.findAllByPriority(priority);
    }
}
