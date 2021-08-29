package com.mabu.MabuWebStore.repository;

import java.util.Optional;
import com.mabu.MabuWebStore.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    Optional<Feedback> findByFeedBackId(Integer id);
}
