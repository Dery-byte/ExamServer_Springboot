package com.exam.service;

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

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public Questions updateQuestions(Questions questions){
        return this.questionsRepository.save(questions);
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

//    public Set<Questions> getQuestionsQuiz(Quiz quiz){
//        return this.questionsRepository.findByQuiz(quiz);
//    }

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
