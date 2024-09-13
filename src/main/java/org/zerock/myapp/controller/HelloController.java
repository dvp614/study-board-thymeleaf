package org.zerock.myapp.controller;

import java.util.List;
import java.util.Vector;
import java.util.stream.IntStream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@Controller

public class HelloController {
	@GetMapping("/hello")
	String hello(Model model) {
		log.trace("hello() invoked.");
		
		model.addAttribute("GREETING", "Hello,  Thymleaf");
		
		return "hello";
	} // hello
	
	@GetMapping("/hello2")
	String hello2(Model model) {
		log.trace("hello2() invoked.");
	
		Person person = new Person();
		
		person.setName("Yoon");
		person.setAge(23);
		
		model.addAttribute("PERSON", person);
		
		return "hello2";
	} // hello2
	
	@GetMapping("/hello3")
	void hello3(Model model) {
		log.trace("hello3() invoked.");
		
		List<Person> list = new Vector<>();
		
		IntStream.rangeClosed(1, 7).forEachOrdered(seq -> {
			Person person = new Person();
			person.setName("NAME_" + seq);
			person.setAge(20 + seq);
			
			list.add(person);
		}); // .forEachOrdered

		model.addAttribute("LIST", list);
	} // hello3
	
	@GetMapping("/hello4")
	void hello4(Model model) {
		log.trace("hello4() invoked.");
		
//		boolean isAuthenticated = true;
		boolean isAuthenticated = false;
		model.addAttribute("isAuthenticated", isAuthenticated);
	} // hello4
	
	@GetMapping("/hello5")
	void hello5(Model model) {
		log.trace("hello5() invoked.");

		
	} // hello5
	
	@GetMapping("/hello6")
	void hello6(Model model) {
		log.trace("hello6() invoked.");

		Person newPerson = new Person();
		
		newPerson.setName("Yoon");
		newPerson.setAge(23);
		
		model.addAttribute("PERSON", newPerson);
	} // hello6
} // end class


@Data
class Person {
	private String name;
	private Integer age;
} // Person
