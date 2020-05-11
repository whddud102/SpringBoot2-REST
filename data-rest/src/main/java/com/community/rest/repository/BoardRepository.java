package com.community.rest.repository;

import com.community.rest.domain.Board;
import com.community.rest.domain.projection.BoardOnlyContainTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * 내부적으로 정의된 로직을 따라 도메인의 매핑 정보를 REST API로 제공,
 * 게시글 제목만 출력하도록 프로젝션을 적용(excertProjection으로 적용 시, 단일 리소스 참조 시에는 적용 x)
 */
@RepositoryRestResource(excerptProjection = BoardOnlyContainTitle.class)  
public interface BoardRepository extends JpaRepository<Board, Long> {
    
    
    @Override   // JpaRepository 인터페이스의 메서드를 오버라이딩
    @PreAuthorize("hasRole('ROLE_ADMIN')")   // 저장 기능을 ADMIN 권한에만 허용
    <S extends Board> S save(S entity);
}
