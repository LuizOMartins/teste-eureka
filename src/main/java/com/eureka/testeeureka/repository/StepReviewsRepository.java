package com.eureka.testeeureka.repository;
import com.eureka.testeeureka.model.StepReviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepReviewsRepository extends JpaRepository<com.eureka.testeeureka.model.StepReviews, Long> {
}
