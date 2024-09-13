package org.zerock.myapp;

import java.util.Arrays;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


//@Log4j2
@Slf4j

@NoArgsConstructor

// ================
// 아래의 여러 어노테이션은 스프링부트의 실행클래스에 기본적으로 붙여주어야 할 어노테이션 목록과 설명입니다.
//================

// 1. Top-level package 밑으로 흩어져 있는 모든 패키지를 스캔해서, 엔티티 클래스 식별.
@EntityScan

// 2. Spring Data JPA repository 인터페이스를 Scan해서, Proxy객체로 만들고, 빈으로 등록.
@EnableJpaRepositories

// 3. ConfigurationProperties 클래스를 Scan해서, 설정값을 채우고, 빈으로 등록
@ConfigurationPropertiesScan	// 1st. method
//@EnableConfigurationProperties	// 2nd. method

// 4. Servlet Components(Servle/Filter/Listener 등) 클래스를 Scan해서, DD(web.xml파일)에 등록
@ServletComponentScan

// 5. Entity 클래스에, 정보통신망법에 따른, 최초등록일시/최종수정일시 저장용속성에
//    지정가능한 @CreateDate, @LastModifiedDate 같은 어노테이션이 자동으로, 
//    날짜/시간정보 생성하도록하려면, 아래의 어노테이션을 추가해야 합니다.
@EnableJpaAuditing		// JPA 감사(Audit) 기능 활성화


// ---------------
// @SpringBootApplication = @SpringBootConfiguration + @EnableAutoConfiguration + @ComponentScan
//                        = @Configuration + @EnableAutoConfiguration + @ComponentScan
//---------------
// (1) @SpringBootConfiguration, @Configuration : 이 클래스가 자바설정클래스임을 표시하고, 빈으로 등록
// (2) @EnableAutoConfiguration : 스프링부트의 "자동설정" 기능 활성화
// (3) @ComponentScan: @Controller/@RestController, @Service, @Respository, @Component 같은 스테레오 타입
//                     어노테이션이 붙어있는 모든 클래스를 찾아서, 빈으로 등록
//---------------

@SpringBootApplication
public class StudyBoardApp extends ServletInitializer {

	public static void main(String[] args) {
//		SpringApplication.run(StudyBoardApp.class, args);	// 1st. method: 자동생성코드
		
		// -------
		
		SpringApplication app = new SpringApplication(StudyBoardApp.class);	// 2nd. method: Customizing
		
		// 여기서, 내장형 WAS (Embeded Tomcat)를 구동시킬지 말지를 결정해도,
		// 무조건 Spring Boot의 설정파일인 application.properties 설정파일의
		// 이 설정항목의 값이 우선적으로 적용됩니다: 
		//   spring.main.web-application-type={ servlet | none | reactive }
		
		app.setWebApplicationType(WebApplicationType.SERVLET);
//		app.setWebApplicationType(WebApplicationType.NONE);
		
		// 배너모드 - 콘솔 출력여부 설정
		// 이 코드도, 스프링부트 설정파일에 설정이 있으면, 무조건 설정이 우선적용됩니다:
		// 	spring.main.banner-mode=console
		app.setBannerMode(Mode.CONSOLE);
		
		// ----------------
		// ApplicationListener<ApplicationEvent> 구현객체를 만드는 방법 2가지:
		// ----------------
		
		// (1) 1st. method: 익명구현객체를 이용하는 방법
//		app.addListeners(new ApplicationListener<ApplicationEvent>() {
//			@Override
//			public void onApplicationEvent(ApplicationEvent event) {
//				log.trace("onApplicationEvent({})", event.getClass().getSimpleName());
//			} // onApplicationEvent			
//		});	// .addListeners
		
		// (2) 2nd. method: 람다식을 이용하는 방법
		app.addListeners(e -> log.trace("onApplicationEvent({})", e.getClass().getSimpleName()));
		
		// ----------------	
		// 스프링부트 APP 구동
		// ----------------
		app.run(args);		// 1st. method: main(args) 메소드의 실행인자를 사용해야 하는 경우
//		app.run();			// 2nd. method: main(args) 메소드의 실행인자가 필요없는 경우
		
		// main(args) 메소드를 호출로그는 아래와 같이 맨 마지막에 수행해야 로그로 출력됩니다.
		// 스프링부트가 실행되지 않고서는, lombok의 @Log4j2 or @Slf4j 설정파일이 적용될 수가 없기 때문에
		// 그러합니다.
		log.trace("main({}) invoked.", Arrays.toString(args));
	} // main

} // end class


