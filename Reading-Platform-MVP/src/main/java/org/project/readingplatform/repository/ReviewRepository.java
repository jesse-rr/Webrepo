package org.project.readingplatform.repository;

import org.project.readingplatform.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewRepository extends JpaRepository<Review, Long> {
}
