package com.mabu.MabuWebStore.serviceImpl;

import java.util.List;

import com.mabu.MabuWebStore.entity.Feedback;
import com.mabu.MabuWebStore.repository.FeedbackRepository;
import com.mabu.MabuWebStore.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackRepository feedBackRepository;

    @Override
    public List<Feedback> findAllFeedback() {
        return feedBackRepository.findAll();
    }

    @Override
    public Feedback findFeedbackById(Integer id) {
        return feedBackRepository.findByFeedBackId(id).isPresent()?feedBackRepository.findByFeedBackId(id).get():null;
    }

    @Override
    public Feedback createFeedback(Feedback feedback) {
        return feedBackRepository.save(feedback);
    }

    @Override
    public Feedback updateFeedback(Feedback feedback) {
        return feedBackRepository.saveAndFlush(feedback);
    }

    @Override
    public void deleteFeedback(Integer id) {
        feedBackRepository.deleteById(id);
    }

}
