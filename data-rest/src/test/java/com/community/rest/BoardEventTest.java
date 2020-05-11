package com.community.rest;

import com.community.rest.domain.Board;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

/**
 * BoardEventHandler 테스트 코드,
 * 스프링 시큐리티 설정이 포함 된 DataRestApplication 클래스를 주입,
 * 포트 번호는 지정된(8081) 포트 사용,
 * H2를 테스트 DB로 사용
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataRestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase
public class BoardEventTest {
    // 파라미터로 전달 된 유저 정보로 권한 인증이 통과
    private TestRestTemplate testRestTemplate = new TestRestTemplate("havi", "test");
    
    @Test
    public void test_CreatedDate() {
        Board createdBoard = createBoard();
        assertNotNull(createdBoard.getCreatedDate());
    }

    private Board createBoard() {
        Board board = Board.builder().title("저장 이벤트 테스트").build();
        return testRestTemplate.postForObject("http://127.0.0.1:8081/api/boards", board, Board.class);
    }

    @Test
    public void test_UpdatedDate() {
        Board createdBoard = createBoard();
        Board updatedBoard = updateBoard(createdBoard);
        assertNotNull(updatedBoard.getUpdatedDate());
    }

    private Board updateBoard(Board createdBoard) {
        String updateUri = "http://127.0.0.1:8081/api/boards/1";
        testRestTemplate.put(updateUri, createdBoard);
        return testRestTemplate.getForObject(updateUri, Board.class);
    }

}
