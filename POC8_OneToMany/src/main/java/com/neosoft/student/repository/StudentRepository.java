package com.neosoft.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neosoft.student.model.StudentDTO;

@Repository
public interface StudentRepository extends JpaRepository<StudentDTO, Long>{
}

/**public Student getStudentById(Long id) throws RecordNotFoundException 
	{
		Optional<Student> Student = repository.findById(id);
		
		if(Student.isPresent()) {
			return Student.get();
		} else {
			throw new RecordNotFoundException("No Student record exist for given id");
		}
	} **/
