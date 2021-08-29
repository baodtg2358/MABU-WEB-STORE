package com.mabu.MabuWebStore.service;

import com.mabu.MabuWebStore.entity.Feedback;
import java.util.List;


public interface FeedbackService {
    List<Feedback> findAllFeedback();

    Feedback findFeedbackById(Integer id);

    Feedback createFeedback(Feedback feedback);

    Feedback updateFeedback(Feedback feedback);

    void deleteFeedback(Integer id);
}