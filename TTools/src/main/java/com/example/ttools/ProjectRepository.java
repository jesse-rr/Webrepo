package com.example.ttools;

import com.example.ttools.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProjectRepository extends JpaRepository<Project, Long> {


    @Modifying
    @Transactional
    @Query("UPDATE project p SET p.isRemoved = true WHERE p.productId= :productId")
    void updateProjectToDeleted(Long productId);
}
