package com.exam.model.exam;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name="category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cid;

    private  String title;
    private String CourseCode;

    private String description;
    private String level;

    @OneToMany(mappedBy = "category",cascade = CascadeType.MERGE)
    @JsonIgnore
    private Set<Quiz> quizzes = new LinkedHashSet<>();

    @JsonCreator
    public Category(Long cid, String level, String title, String CourseCode, String description, Set<Quiz> quizzes) {
        this.cid = cid;
        this.title = title;
        this.CourseCode=CourseCode;
        this.description = description;
        this.quizzes = quizzes;
        this.level = level;
    }

    public Category() {
    }

//    public Category( Long cid, String title, String description) {
//        this.cid = cid;
//        this.title = title;
//        this.description = description;
//    }

    public Long getCid() {
        return cid;
    }

    public String getCourseCode() {
        return CourseCode;
    }


    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setCourseCode(String courseCode) {
        CourseCode = courseCode;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
