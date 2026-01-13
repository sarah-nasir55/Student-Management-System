package org.example.studentmanagementsystem.service;

import lombok.AllArgsConstructor;
import org.example.studentmanagementsystem.dto.SemesterDTO;
import org.example.studentmanagementsystem.exception.ResourceNotFoundException;
import org.example.studentmanagementsystem.mapper.SemesterMapper;
import org.example.studentmanagementsystem.models.Semester;
import org.example.studentmanagementsystem.repository.SemesterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class SemesterService {

    private final SemesterRepository repository;
    private final SemesterMapper mapper;

    public SemesterDTO createSemester(SemesterDTO dto) {
        Semester semester = mapper.toEntity(dto);
        repository.save(semester);
        return mapper.toDTO(semester);
    }

    @Transactional(readOnly = true)
    public List<SemesterDTO> getAllSemesters() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public SemesterDTO getSemesterById(String id) {
        Semester semester = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Semester not found with id: " + id
                        )
                );
        return mapper.toDTO(semester);
    }

    public SemesterDTO updateSemester(String id, SemesterDTO dto) {

        Semester semester = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Semester not found with id: " + id
                        )
                );

        semester.update(dto.getSemester());

        return mapper.toDTO(semester);
    }

    public void deleteSemester(String id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Semester not found with id: " + id
            );
        }
        repository.deleteById(id);
    }
}
