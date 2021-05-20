package com.neosoft.student.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student_poc8")
// Causes Lombok to generate toString(), equals(), hashCode(), getter() & setter(), and Required arguments constructor in one go.
@Data
// Causes Lombok to implement the Builder design pattern for the Pojo class.
// Usage can be seen in DefaultResidentsLoader.java -> createNewResident() method.
//@Builder
// Causes Lombok to generate a constructor with no parameters.
@NoArgsConstructor
// Causes Lombok to generate a constructor with 1 parameter for each field in your class.
@AllArgsConstructor
@Component
public class StudentDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(max = 100)
	private String firstName;
	
	@Size(max = 65)
	private String lastName;
	
	@NotNull
	@Email
	@Size(max = 100)
	@Column(unique = true)
	private String email;
	
	@Size(max = 15)
	private String phoneNumber;
	
	@Column(nullable = true)
	private String photos;
	
	
	  @OneToMany(cascade = CascadeType.ALL,mappedBy = "student", fetch =FetchType.LAZY) 
	  List <ProjectDTO>project = new ArrayList<> ();
	  
	    @Transient
	    public String getPhotosImagePath() {
	        if (photos == null || id == null) return null;
	         
	        return "/student-photos/" + id + "/" + photos;
	    }
	  
	  public void addProject(String projectName) {
			this.project.add(new ProjectDTO(projectName,this));
		}
	    
	 
	  

}
