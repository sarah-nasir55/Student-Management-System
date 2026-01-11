package org.example.studentmanagementsystem.mapper;

import org.example.studentmanagementsystem.dto.SemesterDTO;
import org.example.studentmanagementsystem.models.Semester;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SemesterMapper {

    public Semester toEntity(SemesterDTO dto) {
        return new Semester(
                UUID.randomUUID().toString(),
                dto.getSemester()
        );
    }

    public SemesterDTO toDTO(Semester semester) {
        SemesterDTO dto = new SemesterDTO();
        dto.setId(semester.id());
        dto.setSemester(semester.semester());
        return dto;
    }
}
