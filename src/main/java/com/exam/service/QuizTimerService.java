package com.exam.service;

import com.exam.DTO.QuizTimerRequestDTO;
import com.exam.DTO.QuizTimerResponseDTO;
import com.exam.helper.ResourceNotFoundException;
import com.exam.model.QuizTimer;
import com.exam.model.User;
import com.exam.model.exam.Quiz;
import com.exam.repository.QuizRepository;
import com.exam.repository.QuizTimerRepository;
import com.exam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class QuizTimerService {

    @Autowired
    private QuizTimerRepository quizTimerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizRepository quizRepository;

    public QuizTimerResponseDTO getQuizTimer(Long userId, Long quizId) {
        Optional<QuizTimer> timer = quizTimerRepository.findByUserIdAndQuiz_qId(userId, quizId);

        if (timer.isPresent()) {
            QuizTimer qt = timer.get();
            QuizTimerResponseDTO response = new QuizTimerResponseDTO();
            response.setRemainingTime(qt.getRemainingTime());
            response.setUpdatedAt(qt.getUpdatedAt());
            return response;
        }

        return null;
    }
    public QuizTimerResponseDTO saveQuizTimer(Long userId, Long quizId, QuizTimerRequestDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found"));
        QuizTimer timer = quizTimerRepository.findByUserIdAndQuiz_qId(userId, quizId)
                .orElse(new QuizTimer());
        timer.setUser(user);
        timer.setQuiz(quiz);
        timer.setRemainingTime(request.getRemainingTime());
        timer.setUpdatedAt(LocalDateTime.now());
        QuizTimer saved = quizTimerRepository.save(timer);
        QuizTimerResponseDTO response = new QuizTimerResponseDTO();
        response.setRemainingTime(saved.getRemainingTime());
        response.setUpdatedAt(saved.getUpdatedAt());
        return response;
    }

    public void deleteQuizTimer(Long userId, Long quizId) {
        quizTimerRepository.deleteByUserIdAndQuiz_qId(userId, quizId);
    }
}