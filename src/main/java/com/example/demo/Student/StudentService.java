package com.example.demo.Student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }


    public void addNewStudent(Student student) {
        Optional<Student> studentByName = studentRepository.findStudentByName(student.getName());
        if(studentByName.isPresent()){
            throw new IllegalStateException("name taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists){
            throw  new IllegalStateException("student with id" + studentId + "does not exists");
        }
        studentRepository.deleteById(studentId);
    }
    @Transactional
    public void putStudent(Long studentId,String name) {
    Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException(studentId + "does not eixts"));
    if(name !=null && name.length()>0 && !Objects.equals(student.getName(),name)){
    student.setName(name);
    }
    }
}
