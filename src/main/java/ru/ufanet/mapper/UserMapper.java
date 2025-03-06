package ru.ufanet.mapper;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.ufanet.dto.ClientCreateRequest;
import ru.ufanet.dto.ClientResponse;
import ru.ufanet.dto.SignUpRequest;
import ru.ufanet.models.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toUserEntity(SignUpRequest request);

    UserEntity toUserEntity(ClientCreateRequest clientCreateRequest);

    ClientResponse toClientResponse(UserEntity userEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromRequest(ClientCreateRequest request, @MappingTarget UserEntity user);
}
