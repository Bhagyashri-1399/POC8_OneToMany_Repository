package com.neosoft.student.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import com.neosoft.student.model.ProjectDTO;

public interface ProjectRepository extends JpaRepository<ProjectDTO, Long>{
	List<ProjectDTO> findByStudentId(Long student_id);

}
