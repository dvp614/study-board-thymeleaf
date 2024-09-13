package org.zerock.myapp.controller;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@Log4j2
@Slf4j
@NoArgsConstructor

@RequestMapping("/B/")
@Controller
public class BController {

	
	@GetMapping("/SQL")
	void raiseSQL() throws SQLException {
		log.trace("raiseSQL() invoked.");
		
		throw new SQLException("TEST");
	} // raiseSQL

} // end class
