package org.zerock.myapp.util;


// 컨트롤러의 핸들러에서, Model 에 비지니스 데이터 저장할 때에,
// 재사용될 공유속성명을 static final 상수로 지정
public interface SharedAttributes {
	// (1) 지정된 페이지번호에 해당되는 Page<T> 객체를 모델로 저장하는 속성명
	public static final String CURRENT_PAGE = "_CURR_PAGE_";	
	
	// (2) 신규 게시물 등록처리결과를 저장하는 모델의 속성명
	public static final String IS_REGISTERED = "_IS_REGISTERED_";

	// (3) 상세조회된 게시물을 저장하는 모델의 속성명
	public static final String BOARD = "_BOARD_";
	
	// (4) 수정완료된 게시물의 수정결과를 저장하는 모델의 속성명
	public static final String IS_MODIFIED = "_IS_MODIFIED_";
	
	// (5) 삭제완료된 게시물의 삭제결과를 저장하는 모델의 속성명
	public static final String IS_REMOVED = "_IS_REMOVED_";
	
	// (6) 페이징처리를 위하여 생성한 Pagination DTO에 지정할 모델 속성명
	public static final String PAGINATION = "_PAGINATION_";
	
	// (7) 리다이렉션을 위해, 임시상자(RedirectAttributes)안에 현재페이지번호 속성명
	public static final String CURRPAGE = "currPage";	// GET 방식, 전송파라미터명이 된다!
	

} // end class
