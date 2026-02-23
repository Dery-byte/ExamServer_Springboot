package com.exam.service;

import com.exam.DTO.QuizTimerRequestDTO;
import com.exam.DTO.QuizTimerResponseDTO;
import com.exam.DTO.ViolationTimerResponseDTO;
import com.exam.DTO.VoilationTimerRequestDTO;
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




    @Transactional
    public ViolationTimerResponseDTO saveViolationDelayTime(
            Long quizId,
            Long userId,
            VoilationTimerRequestDTO requestDTO) {

        QuizTimer timer = quizTimerRepository.findByUserIdAndQuiz_qId(userId, quizId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "QuizTimer not found for userId: " + userId + " quizId: " + quizId
                        )
                );

        timer.setViolationDelayTime(requestDTO.getViolationDelayTime());
        timer.setUpdatedAt(LocalDateTime.now());

        QuizTimer saved = quizTimerRepository.save(timer);

        ViolationTimerResponseDTO response = new ViolationTimerResponseDTO();
        response.setViolationDelayTime(saved.getViolationDelayTime());

        return response;
    }




    @Transactional(readOnly = true)
    public ViolationTimerResponseDTO getViolationDelayTime(
            Long quizId,
            Long userId) {

        QuizTimer timer = quizTimerRepository.findByUserIdAndQuiz_qId(userId, quizId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "QuizTimer not found for userId: " + userId + " quizId: " + quizId
                        )
                );

        ViolationTimerResponseDTO response = new ViolationTimerResponseDTO();
        response.setViolationDelayTime(timer.getViolationDelayTime());

        return response;
    }



    public ViolationTimerResponseDTO saveViolationCount(Long quizId, Long userId, VoilationTimerRequestDTO request) {
        QuizTimer timer = quizTimerRepository.findByUserIdAndQuiz_qId(userId, quizId)
                .orElseGet(() -> {
                    QuizTimer t = new QuizTimer();
                    t.setUser(userRepository.findById(userId).orElseThrow());
                    t.setQuiz(quizRepository.findById(quizId).orElseThrow());
                    t.setRemainingTime(0);
                    return t;
                });
        timer.setTotalViolationCount(request.getTotalViolationCount());
        timer.setUpdatedAt(LocalDateTime.now());
        quizTimerRepository.save(timer);
        ViolationTimerResponseDTO response = new ViolationTimerResponseDTO();
        response.setTotalViolationCount(timer.getTotalViolationCount());
        return response;
    }

    public ViolationTimerResponseDTO getViolationCount(Long quizId, Long userId) {
        QuizTimer timer = quizTimerRepository.findByUserIdAndQuiz_qId(userId, quizId)
                .orElse(null);
        ViolationTimerResponseDTO response = new ViolationTimerResponseDTO();
        response.setTotalViolationCount(timer != null ? timer.getTotalViolationCount() : 0);
        return response;
    }

}