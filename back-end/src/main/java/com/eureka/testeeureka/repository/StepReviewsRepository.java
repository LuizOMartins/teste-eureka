package com.eureka.testeeureka.repository;

import java.util.Optional;
import com.eureka.testeeureka.model.StepReviews;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface StepReviewsRepository extends JpaRepository<StepReviews, Long> {
    Optional<StepReviews> findByStepIdAndUserId(Long stepId, Long userId);
}
