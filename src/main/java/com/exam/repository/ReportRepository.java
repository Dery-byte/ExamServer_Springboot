package com.exam.repository;

import com.exam.model.exam.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
//    Report findByQuizId(Long qid);
//    Report findByqId(Long qid);
//    Report findByqId(Long qid);
//    List<Report> findByQuizId(Long quiz_Id);
//@Query("SELECT r.marks, r.progress FROM Report r WHERE r.qId = :qId")
//List<Object[]> findMarksAndProgressByQId(@Param("qId") Long qId);



}
