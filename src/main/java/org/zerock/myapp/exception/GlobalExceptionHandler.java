package org.zerock.myapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

// 개발자 및 스프링 프레임워크에서 이미 만들어 놓은
// 모든 컨트롤러의 핸들러 메소드가 요청처리하다가 발생하는 예외를 처리하는
// "전역예외처리기" 빈 (Spring Context에 등록)입니다.
// 빈으로 등록해 놓으면, 어떤 컨트롤러의 어떤 핸들러에서 예외가 발생하면
// 자동으로 콜백되어 작동하게 됩니다.

//@Log4j2
@Slf4j

@NoArgsConstructor

@ControllerAdvice	// Spring AOP 기능으로 구현됨
public class GlobalExceptionHandler {
	
	// 예외처리용 핸들러(즉, 예외처리메소드)를 선언합니다.
	// 이때, @ExceptionHandler 어노테이션이 메소드에 붙습니다.
	
	/**
	 * -------------------------
	 * (1) 전역 예외 처리기
	 * -------------------------
	 * 참고로, 아래의 리턴타입인 ResponseEntity<T> 타입은
	 * 우리가 스프링부트에서 처음 Controller 를 배울 때, 사용했엇던 타입으로
	 * 단어의 의미대로, HTTP response(응답) 메시지의 상태코드/헤더/바디를 모두
	 * 조작가능하게 해주는, 스프링이 기본제공하는 참조타입입니다.
	 * 여기서, 타입파라미터 T는, HTTP 응답메시지의 Body에 들어갈 객체의 타입을
	 * 구체타입으로 지정하면 됩니다. 
	 * 
	 * 그것이 아래에 따로 선언한 ErrorDetails 로 지정하였습니다.
	 * 
	 * -------------------------
	 * (2) WebRequest:
	 * -------------------------	 * 
	 *  결국 요청을 처리하다 오류가 발생한 어떤 웹에서 발생한 요청을
	 *  중간에서 가로채서(intercept), 오류가 발생한 요청에 대한 다양한 정보를
	 *  접근하기 위해서 사용되는, 스프링이 HTTP request 표준 타입과는 별개로
	 *  만든 타입입니다. 아래에서 코드로 사용되는 걸 보시면 이해가 되실겁니다.
	 * -------------------------
	 */
	
	// 발생가능한 수많은 예외중에, 어떤 타입의 예외를 Catch해서 처리할지를
	// 어노테이션 속성으로 지정합니다.
	
//	@ExceptionHandler(Throwable.class)	// 1, 모든 Error, Exception을 잡아서 처리
	@ExceptionHandler(Exception.class)	// 2, 모든 Exception을 잡아서 처리
	ResponseEntity<ErrorDetails> handleAllExceptions(Exception e, WebRequest req) {
		/**
		 * *주의사항*:
		 * 	@Log4j2이든, @Slf4j이든, 아래의 호출로그에서 예외객체인 e를 출력하려고
		 * 	하면, 자동으로 예외의 스택트레이스(Stack Trace)가 출력되게 됩니다.
		 * 	그리고, 두번째 매개변수인 WebRequest 는 HTTP request 메시지를 따로
		 * 	스프링에서 Wrapping한 객체라고 보시면 됩니다.
		 */
		log.trace("handleAllExceptions({}, {}) invoked.", e, req);
		
		// VO 인스턴스 생성
		ErrorDetails errorDetails = new ErrorDetails(
				// (1) Catch 한 예외의 코드 생성 (500으로 생성)
				HttpStatus.INTERNAL_SERVER_ERROR.value(),	// 500 예외코드값 생성
				// (2) Catch 한 예외객체의 메시지
				e.getMessage(),
				// (3) WebRequest 매개변수를 통해, 예외가 발생한 요청을 보낸
				//     클라이언트에 대한 정보를 설명으로 포함해서 넣어줌
				req.getDescription(true)
			);	// VO 패턴 객체		
		
		// 개발자 마음대로, HTTP response 메시지의 상태코드/헤더/바디를 조작가능
		// (1) 응답메시지의 바디에는, 위에서 생성한 VO인스턴스를 넣고
		// (2) 응답행의 HTTP status code 값으로, 500 (Internal Server Error)설정
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	} // handleAllExceptions
	

} // end class


// Value Object (읽기전용, Immutable Object)에 해당됩니다.
// 이 VO 패턴 클래스가, 위 전역 예외처리핸들러 메소드의 구체타입으로
// 사용됩니다.
@Value
final class ErrorDetails {
	// 예외발생시, 에러코드(예: 404 Not Found, 500 Internal Server Error, ...)
	private Integer statusCode;		
	// 예외발생시, 예외메시지
	private String message;			
	// 예외발생시, 예외에 대한 추가적인 설명
	private String description;		
	
	
} // end class
