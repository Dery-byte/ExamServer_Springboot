package com.exam.controller;

import com.exam.model.User;
import com.exam.model.exam.Questions;
import com.exam.model.exam.Quiz;
import com.exam.model.exam.Report;
import com.exam.repository.QuizRepository;
import com.exam.repository.ReportRepository;
import com.exam.service.QuestionsService;
import com.exam.service.QuizService;
import com.exam.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;

@RestController
//@RequestMapping("/questions")
@CrossOrigin(origins="https://assessmentapp-e1d04.web.app")
@RequestMapping("/api/v1/auth")
public class QuestionsController {
    @Autowired
    @Lazy
    private QuestionsService questionsService;
    @Autowired
    @Lazy
    private ReportRepository reportRepository;
    @Autowired
    @Lazy
    private ReportService reportService;


    @Autowired
    @Lazy
    private QuizRepository quizRepository;
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
        List<Questions> list = new ArrayList<>(questionsOfQuiz);

Collections.shuffle(list);


//        return ResponseEntity.ok(questionsOfQuiz);
        return ResponseEntity.ok(list);
    }


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

//    logout the person automatically if token expires

//    THIS METHOD SHOULD BE PUT NOT POST, NOTE YOU HAVE TO UPDATE THE UNFINISHED STATE IF THE SUBMIT BUTTUN IS CLICKED
//    @PostMapping("question/eval-quiz/{qid}")
//    public  ResponseEntity<?> evalQuiz(@RequestBody List<Questions> questions, Principal principal, @PathVariable("qid") Long qid) {
////        System.out.println(questions);
//        User user = (User) this.userDetailsService.loadUserByUsername(principal.getName());
//        Quiz quiz = this.quizService.getQuiz(qid);
//        double marksGot = 0.0;
//        int correctAnswers = 0;
//        int attempted = 0;
////        double maxMarks =0;
//        double maxMarks = 0;
//        for (Questions q : questions) {
////            System.out.println(questions1.getGivenAnswer());
//             maxMarks =  Double.parseDouble(questions.get(0).getQuiz().getMaxMarks());
//            //Single question
//            Questions question = this.questionsService.get(q.getQuesId());
//
//
//            //CHAT GPT SUGGUESTION
//            List<String> correctAnswersList = Arrays.asList(q.getcorrect_answer());
//            List<String> givenAnswersList = Arrays.asList(q.getGivenAnswer());
//
//            if (correctAnswersList.equals(givenAnswersList)) {
//                System.out.println(correctAnswersList);
//                System.out.println(givenAnswersList);
//                // correct
//                correctAnswers++;
//                double marksSingle = (Double.parseDouble(questions.get(0).getQuiz().getMaxMarks()) / (double) questions.size());
//                marksGot += marksSingle;
//            }
//
//
//            // CHAT GPT SUGGUESTION ENDS HERE
//
////            if (Arrays.asList(question.getcorrect_answer()).contains(q.getGivenAnswer())) {
////                //correct
////                correctAnswers++;
////                double marksSingle =  (Double.parseDouble(questions.get(0).getQuiz().getMaxMarks()) / (double) questions.size());
////                //this.questions[0].quiz.maxMarks/this.questions.length;
////                marksGot += marksSingle;
////            }
//            if (q.getGivenAnswer() != null ) {
//                attempted++;
//            }
//
//
//        };
//
////        Report report = (Report) this.reportRepository.findByUserAndQuiz(Optional.ofNullable(user), Optional.ofNullable(quiz));
////        int userid =report.getUser().getId();
////        Long quizId = report.getQuiz().getqId();
////
////        if(report.getUser().getId().equals(user.getId()) && report.getQuiz().getqId().equals(quiz.getqId())){
////            report.setQuiz(quiz);
////            report.setUser(user);
////            report.setProgress("Completed");
////            report.setMarks(BigDecimal.valueOf(Double.parseDouble(String.valueOf(((marksGot))))));
////            reportService.addReport(report);
////        }
//
//
//
//
//
//
//
//        Report report = new Report();
//        report.setQuiz(quiz);
//        report.setUser(user);
//        report.setProgress("Completed");
//        report.setMarks(BigDecimal.valueOf(Double.parseDouble(String.valueOf(((marksGot))))));
////        report.setMarks(Double.parseDouble(String.valueOf(marksGot)));
//        reportService.addReport(report);
//        Map<String, Object> map = Map.of("marksGot", marksGot, "correctAnswers", correctAnswers, "attempted", attempted, "maxMarks", maxMarks);
//        return ResponseEntity.ok(map);
//    }

//    @PutMapping("addtheoryMarks/{qid}")
//    public  ResponseEntity<?> addmarks(@RequestBody Report report, Principal principal, @PathVariable("qid") Long qid){
//        if (principal == null || qid == null) {
//            return ResponseEntity.badRequest().body("Principal or quiz ID is null");
//        }
//        User user = (User) this.userDetailsService.loadUserByUsername(principal.getName());
//        Quiz quiz = this.quizService.getQuiz(qid);
//
//        if (user == null || quiz == null) {
//            return ResponseEntity.badRequest().body("User or quiz not found");
//        }
//        report.setUser(user);
//        report.setQuiz(quiz);
//        report.setMarksB(report.getMarksB());
//        Report reports = reportRepository.save(report);
//
//        return ResponseEntity.ok(reports);
//    };



    @PostMapping("question/eval-quiz/{qid}")
    public ResponseEntity<?> evalQuiz(@RequestBody List<Questions> questions, Principal principal, @PathVariable("qid") Long qid) {
        // Ensure the principal and qid are not null
        if (principal == null || qid == null) {
            return ResponseEntity.badRequest().body("Principal or quiz ID is null");
        }

        User user = (User) this.userDetailsService.loadUserByUsername(principal.getName());
        Quiz quiz = this.quizService.getQuiz(qid);

        if (user == null || quiz == null) {
            return ResponseEntity.badRequest().body("User or quiz not found");
        }

        double marksGot = 0.0;
        int correctAnswers = 0;
        int attempted = 0;
        double maxMarks = 0;

        if (questions == null || questions.isEmpty()) {
            return ResponseEntity.badRequest().body("No questions provided");
        }

        maxMarks = Double.parseDouble(questions.get(0).getQuiz().getMaxMarks());

        for (Questions q : questions) {
            if (q == null) continue; // Skip null questions

            Questions question = this.questionsService.get(q.getQuesId());
            if (question == null) continue; // Skip if the question is not found

            List<String> correctAnswersList = q.getcorrect_answer() != null ? Arrays.asList(q.getcorrect_answer()): null;
            List<String> givenAnswersList = q.getGivenAnswer() != null ? Arrays.asList(q.getGivenAnswer()) : null;

            if (correctAnswersList != null && givenAnswersList != null) {
                Collections.sort(correctAnswersList);
                Collections.sort(givenAnswersList);
                if (correctAnswersList.equals(givenAnswersList)) {
                    // correct
                    correctAnswers++;
                    double marksSingle = (Double.parseDouble(questions.get(0).getQuiz().getMaxMarks()) / (double) questions.size());
                    marksGot += marksSingle;
                }
            }

            if (isGivenAnswerAttempted(givenAnswersList)) {
                attempted++;
            }
        }

        Report report = new Report();
        report.setQuiz(quiz);
        report.setUser(user);
        report.setProgress("Completed");
        report.setMarks(BigDecimal.valueOf(marksGot));
        report.setMarksB(BigDecimal.valueOf(0));

        reportService.addReport(report);

        Map<String, Object> map = Map.of(
                "marksGot", marksGot,
                "correctAnswers", correctAnswers,
                "attempted", attempted,
                "maxMarks", maxMarks
        );

        return ResponseEntity.ok(map);
    }

    private boolean isGivenAnswerAttempted(List<String> givenAnswersList) {
        if (givenAnswersList != null && !givenAnswersList.isEmpty()) {
            for (String answer : givenAnswersList) {
                if (answer != null && !answer.trim().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }


//  UPLOADING QUESTIONS
@PostMapping("/upload/{quizId}")
public ResponseEntity<String> uploadQuestions(
        @PathVariable Long quizId,
        @RequestBody List<Questions> questions) {
    // Get the quiz entity from the database using the quizId
    Optional<Quiz> optionalQuiz = quizRepository.findById(quizId);
    if (optionalQuiz.isPresent()) {
        Quiz quiz = optionalQuiz.get();
        int numberOfQuestions = Integer.parseInt(quiz.getNumberOfQuestions());
        // Set the quiz for each question
        questions.forEach(question -> question.setQuiz(quiz));
        if(questions.size()<= numberOfQuestions)
        {
            // Save questions
            List<Questions> savedQuestions = questionsService.saveAllQuestions(questions);
            return new ResponseEntity<>("Uploaded " + savedQuestions.size() + " questions to the quiz with ID: " + quizId, HttpStatus.CREATED);
        }
        else{
            return ResponseEntity.status( HttpStatus.BAD_REQUEST).body("Number of questions should be " + numberOfQuestions+ " but you provided " + questions.size());
        }
   } else {
        return new ResponseEntity<>("Quiz with ID " + quizId +  " not found.", HttpStatus.NOT_FOUND);
    }
}

//    UPLOADING QUESTIONS
























// THIS FUNCTION SET THE PROGRESS TO UNFINISHED AFTER CONFIRMING A CORRECT PASSWORD
//    @PostMapping("question/add-quizUserId/{qid}")
//    public  String addUserIdAndQuizId(Principal principal, @PathVariable("qid") Long qid) {
////        System.out.println(questions);
//        User user = (User) this.userDetailsService.loadUserByUsername(principal.getName());
//        Quiz quiz = this.quizService.getQuiz(qid);
//        double marksGot = 0.0;
////        int correctAnswers = 0;
////        int attempted = 0;
//////        double maxMarks =0;
////        double maxMarks = 0;
////        for (Questions q : questions) {
//////            System.out.println(questions1.getGivenAnswer());
////            maxMarks =  Double.parseDouble(questions.get(0).getQuiz().getMaxMarks());
////            //Single question
////            Questions question = this.questionsService.get(q.getQuesId());
////            if (question.getAnswer().equals(q.getGivenAnswer())) {
////                //correct
////                correctAnswers++;
////                double marksSingle =  (Double.parseDouble(questions.get(0).getQuiz().getMaxMarks()) / (double) questions.size());
////                //this.questions[0].quiz.maxMarks/this.questions.length;
////                marksGot += marksSingle;
////            }
////            if (q.getGivenAnswer() != "") {
////                attempted++;
////            }
////        };
//        Report report = new Report();
//        report.setQuiz(quiz);
//        report.setUser(user);
//        report.setProgress("Unfinished");
//        report.setMarks(BigDecimal.valueOf(Double.parseDouble(String.valueOf(((marksGot))))));
////        report.setMarks(marksGot);
////        report.setMarks(Double.parseDouble(String.valueOf(marksGot)));
//        reportService.addReport(report);
////        Map<String, Object> map = Map.of("marksGot", marksGot, "correctAnswers", correctAnswers, "attempted", attempted, "maxMarks", maxMarks);
//        return "User ID and Quiz ID saved";
//    }








//    @PostMapping("/addReports/{qid}")
//    public ResponseEntity<Report> addReports(@RequestBody Report report, Principal principal, @PathVariable("qid") Long qid, @PathVariable("ques_id") Long ques_id){
//        User user = (User) this.userDetailsService.loadUserByUsername(principal.getName());
////        List<Questions> questions = (List<Questions>) this.questionsService.getQuestions(ques_id);
//        Quiz quiz = this.quizService.getQuiz(qid);
//        //        report.setMarks(90);
//        report.setQuiz(quiz);
//        report.setUser(user);
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
