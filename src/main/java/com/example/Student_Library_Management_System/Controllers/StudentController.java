package com.example.Student_Library_Management_System.Controllers;

import com.example.Student_Library_Management_System.DTOs.StudentUpdateMobNoRequestDto;
import com.example.Student_Library_Management_System.Models.Student;
import com.example.Student_Library_Management_System.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/create")
    public String createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }


    @GetMapping("/get-student")
    public String getNameByEmail(@RequestParam("email") String email) {
        return studentService.findNameByEmail(email);
    }

//    @PutMapping("/update-mob")
//    public String updateMobNo(@RequestBody Student student) {
//        return studentService.updateMobNo(student);
//    }

    @PutMapping("/update-mob")
    public String updateMobNo(@RequestBody StudentUpdateMobNoRequestDto studentReq) {
        return studentService.updateMobNo(studentReq);
    }
}
