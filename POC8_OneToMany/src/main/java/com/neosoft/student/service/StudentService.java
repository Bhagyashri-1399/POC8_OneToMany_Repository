package com.neosoft.student.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neosoft.student.model.ProjectDTO;
import com.neosoft.student.model.StudentDTO;
import com.neosoft.student.repository.ProjectRepository;
import com.neosoft.student.repository.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository repo;
	
	@Autowired
	private ProjectRepository proRepo;
	
	public StudentDTO getStudentById(Long id) 
	{
		Optional<StudentDTO> student1 = repo.findById(id);
		    if (student1.isPresent()) {
		    	return student1.get();
			}
		    else {
				return null;
			}

			
	}
	
	public List<StudentDTO> getUsers() {
		List<StudentDTO> users = new ArrayList<StudentDTO>();
		repo.findAll().forEach(users::add);
		return users;
	}
	
	 public Optional<StudentDTO> getUser(Long id) { return repo.findById(id); }
	 
	 public String deleteUser(Long id) {
			repo.deleteById(id);
			return "User deleted with id:"+id;
	 }
	 public Long addUser(StudentDTO user) {
		 repo.save(user);
		 return user.getId();
	}
	
	
	  public Long addProject(ProjectDTO user) { proRepo.save(user); return
	  user.getId(); }
	 
	
	

}
