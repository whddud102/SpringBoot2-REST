package com.community.rest.domain.projection;

import com.community.rest.domain.User;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "getOnlyName", types = {User.class}) // name : 프로젝션을 사용하기 위한 키 값
public interface UserOnlyContainName {
    String getName();
}
