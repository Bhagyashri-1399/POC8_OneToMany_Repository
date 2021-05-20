package com.neosoft.student.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neosoft.student.controller.FileUploadUtil;
import com.neosoft.student.controller.StudentController;
import com.neosoft.student.model.ProjectDTO;
import com.neosoft.student.model.StudentDTO;
import com.neosoft.student.repository.ProjectRepository;
import com.neosoft.student.repository.StudentRepository;
import com.neosoft.student.service.StudentService;

@WebMvcTest(value = StudentController.class)
class MainAppTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private StudentRepository repo;
	
	@MockBean
	private ProjectRepository proRepo;
	
	@MockBean
	private StudentService service;
	
	
	ObjectMapper om = new ObjectMapper();
	
	public static List<StudentDTO> setUp() throws IOException  {
		StudentDTO user = new StudentDTO();
		user.setId(1L);
		user.setFirstName("steve");
		user.setLastName("huj");
		user.setPhoneNumber("8909990000");
		user.setEmail("st@gmail.com");
		user.setPhotos("stud1.png");
		user.addProject("ML");
		
		String uploadDir = "student-photos/" + user.getId();
		MultipartFile multipartFile = new MockMultipartFile("sourceFile.tmp", "Hello World".getBytes());
			FileUploadUtil.saveFile(uploadDir, "stud1.png",multipartFile);
		
		
		StudentDTO user1 = new StudentDTO();
		user1.setId(2L);
		user1.setFirstName("Mathew");
		user1.setLastName("uhhu");
		user1.setPhoneNumber("8909990000");
		user1.setEmail("mw@gmail.com");
		user1.setPhotos("stud2.png");
		user1.addProject("AI");
		
		List<StudentDTO> listUser = new ArrayList<StudentDTO>();
		listUser.add(user);
		listUser.add(user1);
		
		
		user1.addProject("Security");
		
		return listUser;
	}
	
	public static List<ProjectDTO> setUp1() {
		StudentDTO user = new StudentDTO();
		user.setId(3L);
		user.setFirstName("steve");
		user.setLastName("huj");
		user.setPhoneNumber("8909990000");
		user.setEmail("st@gmail.com");
		user.setPhotos("stud1.png");
		user.addProject("Mobile");
		
		
		
		ProjectDTO pro = new ProjectDTO();
		pro.setId(1L);
		pro.setProjectName("Java");
		pro.setStudent(user);
		
		List<ProjectDTO> listUser = new ArrayList<ProjectDTO>();
		listUser.add(pro);
		StudentDTO user1 = new StudentDTO(4L,"John","huio","John@gmail.com","8899008899","stud3.png",listUser);
		return listUser;
	}
	

	
	@Test
    void getAllStudentsTest() throws Exception {         //get all user test method
		List<StudentDTO> lst = MainAppTest.setUp();
		String response = om.writeValueAsString(lst);
		Mockito.when(service.getUsers()).thenReturn(lst);
		MvcResult result = mockMvc.perform(get("/testStudent")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		String userRes = result.getResponse().getContentAsString();
		assertEquals(response, userRes);
		
				
	}
	
	@Test
	void getStudentbyIdTest() throws Exception {
		String response=om.writeValueAsString(MainAppTest.setUp().get(0));
		Mockito.when(service.getUser(1L)).thenReturn(java.util.Optional.of(MainAppTest.setUp().get(0)));
		MvcResult result = mockMvc.perform(get("/testStudent/1").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		int response1 = result.getResponse().getStatus();
		assertEquals(200, response1);
		
	}
	
	@Test
	void deleteStudentTest() throws Exception {
		
		MvcResult result = mockMvc.perform(delete("/testStudent/1").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		int response = result.getResponse().getStatus();
		assertEquals(200, response);
	}
	
	@Test
	void addStudentTest() throws Exception {               //add user test method
		Mockito.when(service.addUser(MainAppTest.setUp().get(0))).thenReturn(1L);   // return particular value when particular method is called
		String payload = om.writeValueAsString(MainAppTest.setUp().get(0));        // Method that can be used to serialize any Java value asa String
		MvcResult result = mockMvc
				.perform(post("/studentsTest").content(payload).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated()).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(201, status);
		
	}
	
	@Test
	void addProjectTest() throws Exception {               //add user test method
		Mockito.when(service.addProject(MainAppTest.setUp1().get(0))).thenReturn(3L);   // return particular value when particular method is called
		String payload = om.writeValueAsString(MainAppTest.setUp1().get(0));        // Method that can be used to serialize any Java value asa String
		MvcResult result = mockMvc
				.perform(post("/projectTest").content(payload).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated()).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(201, status);
		
	}
	@Test
    void testEquals_Symmetric() {
	    StudentDTO st1 = new StudentDTO();
	    st1.setId(5L);
	    st1.setFirstName("Ghhjj");
	    st1.setLastName("ghjj");
	    st1.setEmail("gyjki@gmail.com");
	    st1.setPhoneNumber("8888888888");
	    st1.setPhotos("stu1.png");
	    StudentDTO st2 = new StudentDTO();
	    st2.setId(5L);
	    st2.setFirstName("Ghhjj");
	    st2.setLastName("ghjj");
	    st2.setEmail("gyjki@gmail.com");
	    st2.setPhoneNumber("8888888888");
	    st2.setPhotos("stu1.png");
	    Assert.assertTrue(st1.equals(st2) && st2.equals(st1));
	    Assert.assertTrue(st1.hashCode() == st2.hashCode());
	}
	
	  @Test
	      void testEqual2() {
		  StudentDTO st1 = new StudentDTO();
		    st1.setId(5L);
		    st1.setFirstName("Ghhjj");
		    st1.setLastName("ghjj");
		    st1.setEmail("gyjki@gmail.com");
		    st1.setPhoneNumber("8888888888");
		    st1.setPhotos("stu1.png");
		    StudentDTO st2 = new StudentDTO();
		    st2.setId(5L);
		    st2.setFirstName("Ghhjj");
		    st2.setLastName("ghjj");
		    st2.setEmail("gyjki@gmail.com");
		    st2.setPhoneNumber("8888888888");
		    st2.setPhotos("stu1.png");
	        assertEquals(true, st1.equals(st2));
	        
	    }
	
	
	
	
	
	

}
