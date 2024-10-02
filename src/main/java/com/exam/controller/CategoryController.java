package com.exam.controller;

import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;
import com.exam.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
//@RequestMapping("/category")
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
public class CategoryController {

    //add category
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(@RequestBody Category category){
        Category category1 = this.categoryService.addCategory(category);
        return ResponseEntity.ok(category1);
    }
    @GetMapping("/getCategories")
    public ResponseEntity<?> getCategories(){
        return ResponseEntity.ok(this.categoryService.getCategories());
    }

    //getCategory
    @GetMapping("/category/{categoryId}")
    public Category getCategory(@PathVariable("categoryId") Long categoryId){
        return this.categoryService.getCategory(categoryId);
    }
    //update Categories
    @PutMapping("/category/updateCategory")
    public Category updateCategory(@RequestBody Category category){
        return  this.categoryService.UpdateCategory(category);
    }
        //delete category
    @DeleteMapping("/category/{categoryId}")
    public void deleteCategory(@PathVariable("categoryId") Long categoryId){
        this.categoryService.deleteCategory(categoryId);
    }


//Get element by Course Name
private List<Quiz> itemList = new ArrayList<>();
    @GetMapping("/byCourse/{cid}")
    public List<Quiz> getItemsByCourse(@PathVariable Long cid) {
        List<Quiz> itemsByCourse = itemList.stream()
                .filter(item -> item.getCategory().getCid()!= null && item.getCategory().getCid().equals(cid))
                .collect(Collectors.toList());
        System.out.println(itemsByCourse);
        return itemsByCourse;
    }















































//
////    @PostMapping("/add")
////    public ResponseEntity<Category> addCategory(@RequestBody Category category){
////        Category category1 = this.categoryService.addCategory(category);
////        return ResponseEntity.ok(category1);
////    }
//
//    //getCategory
//
//    @GetMapping("/{categoryId}")
//    public Category getCategory(@PathVariable("categoryId") Long categoryId){
//        return this.categoryService.getCategory(categoryId);
//    }
//
//
//
//    //get All Categories
//
//    @GetMapping("/getCategories")
//    public ResponseEntity<?> getCategories(){
//        return ResponseEntity.ok(this.categoryService.getCategories());
//    }
//
//    //update Categories
//    @PutMapping("/updateCategory")
//    public Category updateCategory(@RequestBody Category category){
//        return  this.categoryService.UpdateCategory(category);
//
//    }
//    //delete category
//    @DeleteMapping("/{categoryId}")
//    public void deleteCategory(@PathVariable("categoryId") Long categoryId){
//        this.categoryService.deleteCategory(categoryId);
//    }


}
