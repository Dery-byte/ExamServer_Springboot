package com.exam.controller;

import com.exam.DTO.QuizProgressRequest;
import com.exam.DTO.QuizProgressResponse;
import com.exam.DTO.UserQuizProgressResponse;
import com.exam.model.User;
import com.exam.service.QuizProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth/quiz-progress")
public class QuizProgressController {

    @Autowired
    private QuizProgressService service;

    @Autowired
    private final UserDetailsService userDetailsService;

    public QuizProgressController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/update")
    public ResponseEntity<QuizProgressResponse> updateAnswer(
            Principal principal,
            @RequestBody QuizProgressRequest request) {
        User user = (User) this.userDetailsService.loadUserByUsername(principal.getName());
        QuizProgressResponse response = service.updateAnswer(user.getId(), request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<UserQuizProgressResponse> getAllAnswers(Principal principal) {
        User user = (User) this.userDetailsService.loadUserByUsername(principal.getName());
        UserQuizProgressResponse response = service.getAllAnswers(user.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<UserQuizProgressResponse> getAnswersByQuiz(
            Principal principal,
            @PathVariable Long quizId) {

        User user = (User) this.userDetailsService.loadUserByUsername(principal.getName());

        UserQuizProgressResponse response = service.getAnswersByQuiz(user.getId(), quizId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/quiz/{quizId}")
    public ResponseEntity<Void> clearQuizAnswers(
            Principal principal,
            @PathVariable Long quizId) {
        User user = (User) this.userDetailsService.loadUserByUsername(principal.getName());
        service.clearAnswers(user.getId(), quizId);
        return ResponseEntity.noContent().build();
    }

}