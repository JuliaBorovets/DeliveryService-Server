package ua.training.api.mapper;

import org.mapstruct.Mapper;
import ua.training.api.dto.UserDto;
import ua.training.domain.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToUserDto(User user);

//    @Mappings({
//            @Mapping(target = "orders", ignore = true),
//            @Mapping(target = "receipts", ignore = true),
//            @Mapping(target = "cards", ignore = true)
//    })
    User userDtoToUser(UserDto userDto);
}
