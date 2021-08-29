package com.mabu.MabuWebStore.controller;

import java.util.List;


import com.mabu.MabuWebStore.entity.Feedback;
import com.mabu.MabuWebStore.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/feedback")
    public List<Feedback> getAllFeedback(){
        List<Feedback> allFeedbacks = feedbackService.findAllFeedback();
        return allFeedbacks;
    }

    @GetMapping("/feedback/search")
    public ResponseEntity<Feedback> getFeedbackById(@RequestParam("id") Integer id){
        Feedback feedBackById = feedbackService.findFeedbackById(id);
        return new ResponseEntity<Feedback>(feedBackById, HttpStatus.OK);
    }

    @PostMapping("/feedback")
    public ResponseEntity<?> createFeedback(@RequestBody Feedback feedback){
        Feedback newFeedback = feedbackService.createFeedback(feedback);
        return new ResponseEntity<>(newFeedback, HttpStatus.OK);
    }

    @PutMapping("/feedback/update/{id}")
    public ResponseEntity<?> updateFeedback(@RequestBody Feedback feedback, @PathVariable("id") Integer id){
        feedback.setFeedBackId(id);
        Feedback updateFeedback = feedbackService.updateFeedback(feedback);
        return new ResponseEntity<>(updateFeedback ,HttpStatus.OK);
    }

    @DeleteMapping("/feedback/delete/{id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable(value = "id") Integer id){
        feedbackService.deleteFeedback(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

