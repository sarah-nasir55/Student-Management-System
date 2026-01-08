package org.example.studentmanagementsystem.models;
import java.util.List;

public class Semester {

    private String id;
    private String semester;

    // Foreign keys
    private List<Student> students;
    private List<Enrollment> enrollments;

    public String getId(){
        return this.id;
    }
    public void setId(String id){
        this.id = id;
    }
    public void setSemester(String semester){
        this.semester = semester;
    }
    public String getSemester(){
        return this.semester;
    }
    public List<Student> getStudents() {return this.students; }
    public List<Enrollment> getEnrollments() { return this.enrollments;}
    public void setStudents(List<Student> students){
        this.students = students;
    }
    public void setEnrollments(List<Enrollment> enrollments){
        this.enrollments = enrollments;
    }
}
