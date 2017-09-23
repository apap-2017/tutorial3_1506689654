package com.example.tutorial3.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.tutorial3.service.InMemoryStudentService;
import com.example.tutorial3.service.StudentService;
import com.example.tutorial3.model.StudentModel;

@Controller
public class StudentController {
	private final StudentService studentService;
	
	public StudentController() {
		studentService = new InMemoryStudentService();
	}
	
	@RequestMapping("/student/add")
	public String add(@RequestParam(value = "npm", required = true) String npm,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "gpa", required = true) double gpa) {
		StudentModel student = new StudentModel(npm, name, gpa);
		studentService.addStudents(student);
		return "add";
	}
	
	/**@RequestMapping("/student/view")
	public String view(Model model, @RequestParam(value = "npm", required = true) String npm) {
		StudentModel student = studentService.selectStudent(npm);
		model.addAttribute("student", student);
		return "view";
	}**/
	
	@RequestMapping("/student/viewall")
	public String viewAll(Model model) {
		List<StudentModel> students = studentService.selectAllStudents();
		model.addAttribute("students", students);
		return "viewall";
	}
	
	@RequestMapping(value = {"/student/view","/student/view/{npm}"})
	public String view(Model model, @PathVariable Optional<String> npm) {
		String npmTmp = "";
		if(npm.isPresent()) {
			npmTmp = npm.get();
			StudentModel student = studentService.selectStudent(npmTmp);
			if(student != null) {
				model.addAttribute("student", student);
				return "view";
			}
			npmTmp= npmTmp + " ";
		}
		model.addAttribute("npm", npmTmp);
		return "errorview";
	}
	
	@RequestMapping(value = {"/student/delete","/student/delete/{npm}"})
	public String delete(Model model, @PathVariable Optional<String> npm) {
		String npmTmp = "";
		if(npm.isPresent()) {
			npmTmp = npm.get();
			StudentModel student = studentService.selectStudent(npmTmp);
			if(student != null) {
				studentService.deleteStudents(student);
				model.addAttribute("npm", npmTmp + " ");
				return "delete";
			}
			model.addAttribute("npm", npmTmp + " ");
			return "errordelete";
		}
		
		npmTmp= npmTmp + " ";
		return "errordelete";
	}
	
	
}
