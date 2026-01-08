# Student-Management-System

Tables:
This system consists of 6 tables:
- student
- course
- semester
- enrollment
- phoneNumber
- address

Relations:
- A student can have multiple phones numbers and addresses (One-to-Many)
- Student -> Course (Many-to-Many) (Multiple students can take multiple courses, I have created a seperate enrollment table for that) 
- Student -> Semester (One-to-Many) (Multiple students can belong to one semester)
- Course -> Semester (One-to-Many) (One semester can have many courses)

Output Screenshot for Get Student By Id:
  
<img width="1027" height="837" alt="response getStudentById" src="https://github.com/user-attachments/assets/49e9a9c2-bb14-4aff-8d90-2bcee790984f" />
