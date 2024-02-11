package com.zughead.reviewengine.mapper;

import com.zughead.reviewengine.model.UserResponseDTO;
import com.zughead.reviewengine.persistence.entity.User;
import org.mapstruct.Mapper;

@Mapper
public abstract class UserResponseDTOMapper {

  public abstract UserResponseDTO getUserResponseDTO(final User user);
}
