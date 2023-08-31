package com.exam.controller;

import com.exam.model.User;
import com.exam.model.exam.Questions;
import com.exam.model.exam.Quiz;
import com.exam.model.exam.Report;
import com.exam.repository.ReportRepository;
import com.exam.service.QuestionsService;
import com.exam.service.QuizService;
import com.exam.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;
@RestController
//@RequestMapping("/questions")
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
public class QuestionsController {
    @Autowired
    private QuestionsService questionsService;
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private ReportService reportService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private final UserDetailsService userDetailsService;

    public QuestionsController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // get questions of any quiz
    @GetMapping("question/quiz/all/{qid}")
    public  ResponseEntity<?> getQuestionsOfQuizAdmin(@PathVariable("qid") Long qid){
        Quiz quiz = new Quiz();
        quiz.setqId(qid);
        Set<Questions> questionsOfQuiz =this.questionsService.getQuestionsOfQuiz(quiz);
        return ResponseEntity.ok(questionsOfQuiz);
//        return ResponseEntity.ok(list);
    }
    //Get the specific questions from any quiz
    // get questions of any quiz


    //GET RANDOM QUESTIONS AND LIMITED
    @GetMapping("/random-records")
    public ResponseEntity<List<Questions>> getRandomRecords() {

        return this.questionsService.getRandomRecords();
    }

//GET LIMITED QUESTIONS
//@GetMapping("/limited-records")
//public Page<Questions> getLimitedRecords(
//        @RequestParam(defaultValue = "0") int page,
//        @RequestParam(defaultValue = "10") int size
//) {
//    return questionsService.getLimitedRecords(page, size);
//}



    @GetMapping("question/{quesId}")
    public  Questions getSpecificQuestionsOfQuizAdmin(@PathVariable("quesId") Long quesId ){
       return this.questionsService.getQuestions(quesId);
//        return ResponseEntity.ok(list);
    }

    //add question
    @PostMapping("question/add")
    public ResponseEntity<Questions> add(@RequestBody Questions questions){
        return ResponseEntity.ok(this.questionsService.addQuestions(questions));
    }

    //Delete Question
    @DeleteMapping("/question/{quesId}")
    public void deleteQuestion(@PathVariable("quesId") Long quesId){
        this.questionsService.deleteQuestion(quesId);
    }



    //update Questions
    @PutMapping("/question/updateQuestions")
    public Questions updateQuestion(@RequestBody Questions questions){
        return  this.questionsService.updateQuestions(questions);

    }

//    //evaluate Quiz
    @PostMapping("question/eval-quiz/{qid}")
    public  ResponseEntity<?> evalQuiz(@RequestBody List<Questions> questions, Principal principal, @PathVariable("qid") Long qid) {
        System.out.println(questions);
                User user = (User) this.userDetailsService.loadUserByUsername(principal.getName());
        Quiz quiz = this.quizService.getQuiz(qid);


        double marksGot = 0.0;
        int correctAnswers = 0;
        int attempted = 0;
//        double maxMarks =0;
        double maxMarks = 0.0;
        for (Questions q : questions) {
//            System.out.println(questions1.getGivenAnswer());
//             maxMarks =  Double.parseDouble(questions.get(0).getQuiz().getMaxMarks());
             maxMarks = questions.get(0).getQuiz().getMaxMarks();
            //Single question
            Questions question = this.questionsService.get(q.getQuesId());
            if (question.getAnswer().equals(q.getGivenAnswer())) {
                //correct
                correctAnswers++;
                double marksSingle =  (Double.parseDouble(questions.get(0).getQuiz().getMaxMarks()) /  questions.size());
//                double marksSingle =  questions.get(0).getQuiz().getMaxMarks() /  (double) questions.size();

                //this.questions[0].quiz.maxMarks/this.questions.length;
                marksGot += marksSingle;
            }
            if (q.getGivenAnswer() != "") {
                attempted++;

            }
        }
        ;
Report report = new Report();
        report.setQuiz(quiz);
        report.setUser(user);
        report.setMarks(marksGot);
        questionsService.AddReport(report);

//        report.setMarks((long) marksGot);
//reportService.AddReport(report);
        Map<String, Object> map = Map.of("marksGot", marksGot, "correctAnswers", correctAnswers, "attempted", attempted, "maxMarks", maxMarks);
        return ResponseEntity.ok(map);
    }
//    @PostMapping("/addReports/{qid}")
//    public ResponseEntity<Report> addReports(@RequestBody Report report, Principal principal, @PathVariable("qid") Long qid, @PathVariable("ques_id") Long ques_id){
//        User user = (User) this.userDetailsService.loadUserByUsername(principal.getName());
////        List<Questions> questions = (List<Questions>) this.questionsService.getQuestions(ques_id);
//        Quiz quiz = this.quizService.getQuiz(qid);
//        //        report.setMarks(90);
//        report.setQuiz(quiz);
//        report.setUser(user);
//
////         report.setMarks(user.getId());
//        return ResponseEntity.ok(this.reportService.AddReport(report));
//    }














































//
//    @Autowired
//    private QuizService quizService;
//
//    //add question
//    @PostMapping("/add")
//    public ResponseEntity<Questions> add(@RequestBody Questions questions){
//        return ResponseEntity.ok(this.questionsService.addQuestions(questions));
//    }
//
//    // update question
//    @PutMapping("/")
//    public ResponseEntity<Questions> update(@RequestBody Questions questions){
//        return ResponseEntity.ok(this.questionsService.updateQuestions(questions));
//    }
//
//    // get questions of any quiz
//    @GetMapping("/quiz/{qid}")
//    public  ResponseEntity<?> getQuestionsOfQuiz(@PathVariable("qid") Long qid){
////
////        Quiz quiz = new Quiz();
////        quiz.setqId(qid);
////        Set<Questions> questionsOfQuiz =this.questionsService.getQuestionsOfQuiz(quiz);
////        return ResponseEntity.ok(questionsOfQuiz);
//
//Quiz quiz = this.quizService.getQuiz(qid);
//Set<Questions> questions = quiz.getQuestions();
//List<Questions> list = new ArrayList<>(questions);
//
//if(list.size()>Integer.parseInt(quiz.getNumberOfQuestions())){
//    list = list.subList(0, Integer.parseInt(quiz.getNumberOfQuestions()+1));
//}
//
//
//list.forEach((q)->{
//    q.setAnswer("");
//});
//        Collections.shuffle(list);
//return ResponseEntity.ok(list);
//    }
//
//    //get suingle question
//    @GetMapping("/{quesId}")
//    public Questions get(@PathVariable("quesId") Long quesId )
//    {
//        return this.questionsService.getQuestions(quesId);
//    }
//
//    @DeleteMapping("/{quesId}")
//    public void delete(@PathVariable("quesId") Long quesId){
//        this.questionsService.deleteQuestion(quesId);
//    }
//
//    //evaluate Quiz
//    @PostMapping("/eval-quiz")
//    public  ResponseEntity<?> evalQuiz(@RequestBody List<Questions> questions){
//        System.out.println(questions);
//        return ResponseEntity.ok("Got questions with answers!");
//
//    }




}
