package org.example.studentmanagementsystem.mapper;

import org.example.studentmanagementsystem.dto.*;
import org.example.studentmanagementsystem.models.Address;
import org.example.studentmanagementsystem.models.PhoneNumber;
import org.example.studentmanagementsystem.models.Semester;
import org.example.studentmanagementsystem.models.Student;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StudentMapper {

    public Student toEntity(StudentRequestDTO dto, Semester semester) {

        Student student = new Student(
                UUID.randomUUID().toString(),
                dto.getName(),
                semester
        );
        // addresses
        dto.getAddresses().forEach(a ->
                student.addAddress(
                        new Address(
                                a.getAddress(),
                                student
                        )
                )
        );
        // phone numbers
        dto.getPhoneNumbers().forEach(p ->
                student.addPhoneNumber(
                        new PhoneNumber(
                                p.getPhone(),
                                student
                        )
                )
        );

        return student;
    }

    public StudentResponseDTO toDTO(Student student) {

        StudentResponseDTO dto = new StudentResponseDTO();
        dto.setId(student.id());
        dto.setName(student.name());

        dto.setSemesterId(student.semester().id());
        dto.setSemesterName(student.semester().semester());

        // addresses
        dto.setAddresses(
                student.addresses()
                        .stream()
                        .map(a -> {
                            AddressDTO d = new AddressDTO();
                            d.setAddress(a.address());
                            return d;
                        })
                        .toList()
        );

        // phone numbers
        dto.setPhoneNumbers(
                student.phoneNumbers()
                        .stream()
                        .map(p -> {
                            PhoneNumberDTO d = new PhoneNumberDTO();
                            d.setPhone(p.phone());
                            return d;
                        })
                        .toList()
        );

        dto.setEnrollments(
                student.enrollments()
                        .stream()
                        .map(e -> {
                            EnrollmentResponseDTO ed = new EnrollmentResponseDTO();
                            ed.setEnrollmentId(e.id());

                            ed.setCourseId(e.course().id()); //means e.getCourse().getId()
                            ed.setCourseName(e.course().name());

                            ed.setSemesterId(e.semester().id());
                            ed.setSemesterName(e.semester().semester());

                            return ed;
                        })
                        .toList()
        );
        return dto;
    }
}
