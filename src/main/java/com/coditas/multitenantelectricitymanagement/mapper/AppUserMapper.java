package com.coditas.multitenantelectricitymanagement.mapper;

import com.coditas.multitenantelectricitymanagement.dto.UserRequest;
import com.coditas.multitenantelectricitymanagement.dto.UserResponse;
import com.coditas.multitenantelectricitymanagement.entity.AppUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    AppUser toEntity(UserRequest userRequest);

    UserResponse toDTO(AppUser user);
}
