package com.neosoft.student.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.neosoft.student.model.ProjectDTO;
import com.neosoft.student.model.StudentDTO;
import com.neosoft.student.repository.ProjectRepository;
import com.neosoft.student.repository.StudentRepository;
import com.neosoft.student.service.StudentService;



@Controller
@RequestMapping("/")
//@RestController
public class StudentController {
	
	@Autowired
	private StudentRepository studRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired 
	private StudentService service;
	
	
	
	private static final String ACTION_1 = "redirect:/";
	
	
	  @GetMapping({"/","/students"})
	  public String getAllStudents(Model model) {
	  List<StudentDTO> list = studRepository.findAll(); 
	  model.addAttribute("students",list); return "list-students"; }
	  
	  @RequestMapping(value="add")
		public String addStudent(Model model) {
			model.addAttribute("student",new StudentDTO());
	 
			return "add-student";
		}
	  @GetMapping(path = {"/add/{id}"})
		public String addProjectById(Model model, @PathVariable("id") Optional<Long> id,ProjectDTO project) 
		{
			if (id.isPresent()) {
				StudentDTO entity = service.getStudentById(id.get());
				model.addAttribute("User", entity);
			} else {
				model.addAttribute("User", new ProjectDTO());
			}
			return "add-project";
		}

	
	  @PostMapping(value = "addStudents") 
	  public String createStudent(StudentDTO student , @RequestParam("image") MultipartFile multipartFile, RedirectAttributes redirectAttributes, HttpServletRequest request,ProjectDTO project) throws IOException
	  {    

		  String mf = multipartFile.getOriginalFilename();
		  if(mf == null) {
			  return null;
		  }
		  else {
		  String fileName = StringUtils.cleanPath(mf);
		  student.setPhotos(fileName);
		  String[] projectNames=request.getParameterValues("projectName");
	    	
	    	
	    	for(int i=0;i<projectNames.length;i++) {
	    		student.addProject(projectNames[i]);
	    	}

	        StudentDTO savedStudent = studRepository.save(student);
	        
	        String uploadDir = "student-photos/" + savedStudent.getId();
	 
	        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		  
	        return ACTION_1;
		  }
	   }
	  
	  @RequestMapping(path = "/delete/{id}")
		public String deleteUserById(Model model, @PathVariable("id") Long id) 
		{
			studRepository.deleteById(id);
			return ACTION_1;
		}
	  
	  @PostMapping("/students/{id}/projects")
		 public String saveProject(@PathVariable Long id,@RequestParam String projectName) {
			 StudentDTO student= studRepository.getOne(id);
			 ProjectDTO p=new ProjectDTO(projectName,student);
			 projectRepository.save(p);
			 return ACTION_1;
			 
		 }
	  
	
	  @GetMapping("/searchStudent")
		 public String getStudentById(Model model,@RequestParam Long id) {
		 
			 StudentDTO student=studRepository.getOne(id);
			 if(student == null) {
				 return "error";
			 }
			 model.addAttribute("students", student);
			 return "list-students";
			
		 }
	  @GetMapping("/testStudent")    
		public List<StudentDTO> getUsers() {
			return service.getUsers();
		}
	  @RequestMapping("/testStudent/{id}")
		public Optional<StudentDTO> getUser(@PathVariable Long id) {
			return service.getUser(id);
		}
	  @DeleteMapping(value = "/testStudent/{id}")
		public String deleteUser(@PathVariable Long id) {
			service.deleteUser(id);
			return "User deleted with id:"+id;
		}
	  
	  @PostMapping("/studentsTest")
		public ResponseEntity<StudentDTO> addUser(@RequestBody StudentDTO user) {
			service.addUser(user);
			return new ResponseEntity<StudentDTO>(user, HttpStatus.CREATED);
		}
		
	  @PostMapping("/projectTest")
		public ResponseEntity<ProjectDTO> addProject(@RequestBody ProjectDTO user) {
			//service.addProject(user);
		    projectRepository.save(user);
			return new ResponseEntity<ProjectDTO>(user, HttpStatus.CREATED);
		}
		

}
