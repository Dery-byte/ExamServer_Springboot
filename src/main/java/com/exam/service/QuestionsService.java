package com.exam.service;

import com.exam.DTO.QuestionDTO;
import com.exam.DTO.UpdateQuestionDTO;
import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.exam.Questions;
import com.exam.model.exam.Quiz;
import com.exam.repository.QuestionsRepository;
import com.exam.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.*;

@Service
public class QuestionsService {

    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    @Lazy
    private ReportRepository reportRepository;

    public Questions addQuestions(Questions questions){
        return this.questionsRepository.save(questions);
    }






























//    public Questions updateQuestions(Questions questions){
//        return this.questionsRepository.save(questions);
//    }

    @Transactional
    public QuestionDTO updateQuestion(UpdateQuestionDTO dto) {

        Questions question = questionsRepository.findById(dto.getQuesId())
                .orElseThrow(() -> new RuntimeException("Question not found"));
        question.setContent(dto.getContent());
        question.setImage(dto.getImage());
        question.setOption1(dto.getOption1());
        question.setOption2(dto.getOption2());
        question.setOption3(dto.getOption3());
        question.setOption4(dto.getOption4());
        question.setcorrect_answer(dto.getCorrect_answer());
        Questions updated = questionsRepository.save(question);

        return toDTO(updated); // RETURN DTO
    }



    private QuestionDTO toDTO(Questions question) {

        QuestionDTO dto = new QuestionDTO();
        dto.setQuesId(question.getQuesId());
        dto.setContent(question.getContent());
        dto.setImage(question.getImage());

        dto.setOption1(question.getOption1());
        dto.setOption2(question.getOption2());
        dto.setOption3(question.getOption3());
        dto.setOption4(question.getOption4());

        dto.setCorrect_answer(question.getcorrect_answer());

//        if (question.getQuiz() != null) {
//            dto.setQuizId(question.getQuiz().getqId());
//        }

        return dto;
    }












    public Set<Questions> getQuestions(){
        return new HashSet<>(this.questionsRepository.findAll());
    }

    public Questions getQuestions(Long quesId){
        return this.questionsRepository.findById(quesId).get();
    }


    public Questions UpdateQuestion(Questions questions){
        return  this.questionsRepository.save(questions);
    }
//    public void deleteQuestions(Long quesId){
//        Questions questions = new Questions();
//        questions.setQuesId(quesId);
//        this.questionsRepository.delete(quesId);
//
//    }

    public Set<Questions> getQuestionsOfQuiz(Quiz quiz){
        return this.questionsRepository.findByQuiz(quiz);
    }




    public List<Questions> getQuestionsForMyQuiz(Long quizId, Principal principal) {
        String username = principal.getName();
        return questionsRepository.findByQuiz_qIdAndQuiz_Category_User_Username(quizId, username);
    }







    //limited Questions
public Page<Questions> getLimitedRecords(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return this.questionsRepository.findAll(pageRequest);
    }

    public void deleteQuestion(Long quesId){
        Questions questions = new Questions();
        questions.setQuesId(quesId);
        this.questionsRepository.delete(questions);
    }


    public  Questions get (Long questionId){
        return this.questionsRepository.getOne(questionId);
    }





////    uploading the questions
//public List<Questions> saveAllQuestions(List<Questions> questions) {return questionsRepository.saveAll(questions);
//}
////    uploading the questions


    //    uploading the questions
    public List<Questions> saveAllQuestions(List<Questions> questions) {return questionsRepository.saveAll(questions);
    }
//    uploading the questions







































    // Get specified questions
    public ResponseEntity<List<Questions>> getRandomRecords() {
        List<Questions> allRecords = questionsRepository.findAll();
        // Shuffle the records randomly
        Collections.shuffle(allRecords);
        // Get the first 15 records
        List<Questions> randomRecords = allRecords.subList(0, Math.min(2, allRecords.size()));
        return ResponseEntity.ok(randomRecords);
    }




//    public Questions get(Long quesId) {
//        return questionsRepository.findById(quesId).orElse(null);
//    }

    // 🔴 ADD THIS METHOD
    public List<Questions> getQuestionsByQuizId(Long quizId) {
        List<Questions> list = questionsRepository.findByQuiz_qId(quizId);
        System.out.println("Questions found for quiz " + quizId + " = " + list.size());
        return list;
    }



//    public Report AddReport(Report report){
//        return reportRepository.save(report);
//    }

//    public Report getReportByQuid(Long qid){
//        return reportRepository.findByQuizId(qid);
//    }



    // Get specified questions
//    public ResponseEntity<List<Questions>> getRandomRecords() {
//        List<Questions> allRecords = questionsRepository.findAll();
//
//        // Shuffle the records randomly
//        Collections.shuffle(allRecords);
//
//        // Get the first 15 records
//        List<Questions> randomRecords = allRecords.subList(0, Math.min(2, allRecords.size()));
//
//        return ResponseEntity.ok(randomRecords);
//    }











}
