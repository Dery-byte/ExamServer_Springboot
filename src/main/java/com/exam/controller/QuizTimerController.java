package com.exam.controller;

import com.exam.DTO.QuizTimerRequestDTO;
import com.exam.DTO.QuizTimerResponseDTO;
import com.exam.model.User;
import com.exam.service.QuizTimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth/quiz-timer")
public class QuizTimerController {

    @Autowired
    private final QuizTimerService quizTimerService;

    @Autowired
    private final UserDetailsService userDetailsService;

    public QuizTimerController(QuizTimerService quizTimerService, UserDetailsService userDetailsService) {
        this.quizTimerService = quizTimerService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/getRemainingTime/{quizId}")
    public ResponseEntity<QuizTimerResponseDTO> getQuizTimer(
            @PathVariable Long quizId,
             Principal principal) {
        User user = (User) this.userDetailsService.loadUserByUsername(principal.getName());
        QuizTimerResponseDTO timer = quizTimerService.getQuizTimer(user.getId(), quizId);
        if (timer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(timer);
    }

    @PostMapping("/saveRemainingTime/{quizId}")
    public ResponseEntity<QuizTimerResponseDTO> saveQuizTimer(
            @PathVariable Long quizId,
            @RequestBody QuizTimerRequestDTO request,
            Principal principal) {

        User user = (User) this.userDetailsService.loadUserByUsername(principal.getName());
        QuizTimerResponseDTO timer = quizTimerService.saveQuizTimer(user.getId(), quizId, request);

        return ResponseEntity.ok(timer);
    }

    @DeleteMapping("/deleteRemainingTime/{quizId}")
    public ResponseEntity<Void> deleteQuizTimer(
            @PathVariable Long quizId,
            Principal principal) {
        User user = (User) this.userDetailsService.loadUserByUsername(principal.getName());
        quizTimerService.deleteQuizTimer(user.getId(), quizId);
        return ResponseEntity.noContent().build();
    }



}