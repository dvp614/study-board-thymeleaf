package org.zerock.myapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.zerock.myapp.domain.BoardDTO;
import org.zerock.myapp.entity.Board;
import org.zerock.myapp.util.RandomNumberGenerator;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)

@AutoConfigureMockMvc
@SpringBootTest
public class BoardServiceTests {
//	@Autowired private MockMvc mockMvc;
	@Autowired private BoardService service;
	
	
	// 필수 빈 주입여부 확인
	
	
//	@Disabled
	@Order(1)
	@Test
//	@RepeatedTest(1)
	@DisplayName("1. testFindAllBoards")
	@Timeout(value=3L, unit=TimeUnit.SECONDS)
	void testFindAllBoards() {
		log.trace("testFindAllBoards() invoked.");
		
		// 페이징 처리(***)
		final int currPage = RandomNumberGenerator.generateOneInt(1, 30);
		final int pageSize = RandomNumberGenerator.generateOneInt(10, 21);
		
		BoardDTO dto = new BoardDTO();
		dto.setCurrPage(currPage);
		dto.setPageSize(pageSize);
		
		Page<Board> page = this.service.findAllBoards(dto);
		
		assert page != null && page.getSize() <= pageSize;
		
		page.forEach(b -> log.info("\t+ Found board: {}", b));
	} // testFindAllBoards
	
	
//	@Disabled
	@Order(2)
	@Test
//	@RepeatedTest(1)
	@DisplayName("2. testRegisterBoard")
	@Timeout(value=3L, unit=TimeUnit.SECONDS)
	void testRegisterBoard() {
		log.trace("testRegisterBoard() invoked.");
		
		// Step1. UI에서 사용자가 입력한 전송파라미터를 DTO로 수집
		BoardDTO dto = new BoardDTO();
		dto.setTitle("NEW_TITLE");
		dto.setWriter("Yoseph");
		dto.setContent("NEW_CONTENT");
		
		// Step2. DTO를 매개변수의 값으로 전달하면서, 서비스 메소드 테스트 수행
		boolean isSucceed = this.service.registerBoard(dto);
		
//		assertEquals(기대값, 실제값);	// 비교1
//		assertTrue(실제값);				// 비교2 : 검증메소드이름에 기대값이 포함됨
		
		Assertions.assertTrue(isSucceed);		
		log.info("\t+ isSucceed: {}", isSucceed);
	} // testRegisterBoard
	
	
//	@Disabled
	@Order(3)
	@Test
//	@RepeatedTest(1)
	@DisplayName("3. testFindBoard")
	@Timeout(value=3L, unit=TimeUnit.SECONDS)
	void testFindBoard() {
		log.trace("testFindBoard() invoked.");
		
		Long randomId = RandomNumberGenerator.generateOneLong(1, 500);
		log.info("\t+ randomId: {}", randomId);
		
		BoardDTO dto = new BoardDTO();
		dto.setId(randomId);
		
		Board foundBoard = this.service.findBoard(dto);
		
		Objects.requireNonNull(foundBoard);					// 검증1
		assertEquals(randomId, foundBoard.getId());			// 검증2
		
		log.info("\t+ foundBoard: {}", foundBoard);			// Eye-Checking
	} // testFindBoard
	
	
//	@Disabled
	@Order(4)
	@Test
//	@RepeatedTest(1)
	@DisplayName("4. testModifyBoard")
	@Timeout(value=3L, unit=TimeUnit.SECONDS)
	void testModifyBoard() {
		log.trace("testModifyBoard() invoked.");
		
		// Step1. 무작위로 수정할 게시글을 검색할 PK값 생성
		Long randomId = RandomNumberGenerator.generateOneLong(1, 500);
		log.info("\t+ randomId: {}", randomId);
		
		// Step2. 수정할 게시글을 먼저 찾아옵니다.
		BoardDTO dto = new BoardDTO();
		dto.setId(randomId);
		
		Board foundBoard = this.service.findBoard(dto);
		
		// Step3. Step2 에서 찾은 엔티티를 기반으로 수정된 DTO 생성
		Objects.requireNonNull(foundBoard);
		
		BoardDTO updateDTO = new BoardDTO();
		updateDTO.setId(foundBoard.getId());
		updateDTO.setTitle("MODIFIED_TITLE");
		updateDTO.setWriter("Trinity");
		updateDTO.setContent(foundBoard.getContent());
		updateDTO.setCnt(foundBoard.getCnt());
		
		// Step4. Step3 에서 만든 BoardDTO로 테스트할 메소드 수행
		boolean isSucceed = this.service.modifyBoard(updateDTO);
		
		assertTrue(isSucceed);
		log.info("\t+ isSucceed: {}", isSucceed);
	} // testModifyBoard

} // end class
