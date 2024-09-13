package org.zerock.myapp.common;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


//@Log4j2
@Slf4j

@NoArgsConstructor

@RequestMapping("/api/")

@RestController
public class CommonApiController {
	@Autowired RequestMappingHandlerMapping handlerMapping;
	
	
	@PostConstruct
	void postConstruct() {
		log.trace("postConstruct() invoked.");
		
		Objects.requireNonNull(this.handlerMapping);
		log.info("\t+ this.handlerMapping: {}", this.handlerMapping);
	} // postConstruct
	
	@GetMapping("/mappings")
	Map<String, String> mappings() {
		log.trace("mappings() invoked.");
	
		return this.handlerMapping
				    // HandlerMapping 빈이 가지고 있는 모든 컨트롤러의 모든 핸들러에 대한 정보를 요청
				   	// 리턴타입: Map <RequestMappingInfo, HandlerMethod> 
				   	.getHandlerMethods()
				   	// 이전 메소드가 반환한 Map 컬렉션의 모든 Map.Entry 타입의 요소를 다 끄집어내서,
				   	// Set 컬렉션에 넣어서 반환
				   	.entrySet()
				   	// Set 컬렉션 -> Stream 객체로 변환
				   	.stream()
				   	// Stream 객체가 지원하는 collect 메소드로 자유롭게 개발자가 원하는 형태의 자료구조로
				   	// 변환: Stream -> Map<K, V> -> Map<String, String>
				   	.collect(Collectors.toMap(
						   		entry -> entry.getKey().toString(),
						   		entry -> entry.getValue().toString()));
	} // mappings
	
} // end class
