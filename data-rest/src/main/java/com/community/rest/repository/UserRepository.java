package com.community.rest.repository;

import com.community.rest.domain.User;
import com.community.rest.domain.projection.UserOnlyContainName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = UserOnlyContainName.class) // 내부적으로 정의된 로직을 따라 도메인의 매핑 정보를 REST API로 제공
public interface UserRepository extends JpaRepository<User, Long> {
}
