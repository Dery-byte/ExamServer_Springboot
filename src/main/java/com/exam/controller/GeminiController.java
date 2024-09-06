package com.exam.controller;

import com.exam.model.exam.GeminiRequest;
import com.exam.service.QuizGeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
public class GeminiController {

    @Autowired
    private QuizGeminiService quizGeminiService;

    @PostMapping("/quizEval")
    public List<String> chat(@RequestBody GeminiRequest geminiRequest) {
        return quizGeminiService.evaluateQuiz(geminiRequest);
    }
}
