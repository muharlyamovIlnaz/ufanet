package com.effective.mobile.tskmngmntsystm.mapper;

import com.effective.mobile.tskmngmntsystm.dto.SignUpRequest;
import com.effective.mobile.tskmngmntsystm.models.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toUserEntity(SignUpRequest request);
}
