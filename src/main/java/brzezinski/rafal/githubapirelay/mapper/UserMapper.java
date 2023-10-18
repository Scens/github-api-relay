package brzezinski.rafal.githubapirelay.mapper;

import brzezinski.rafal.githubapirelay.dto.GithubUserDTO;
import brzezinski.rafal.githubapirelay.dto.UserDTO;
import brzezinski.rafal.githubapirelay.service.UserCalculationsService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO githubResponseToUser(GithubUserDTO githubUser);

    @AfterMapping
    default void afterMapping(@MappingTarget UserDTO user, GithubUserDTO githubUser) {
        if (githubUser == null) {
            return;
        }

        BigDecimal calculationsValue = UserCalculationsService.getCalculationsValue(githubUser, UserMapperConsts.DEFAULT_CALCULATIONS_VALUE, UserMapperConsts.ADDEND_FOR_PUBLIC_REPOS, UserMapperConsts.NUMERATOR);

        user.setCalculations(calculationsValue);
    }
}
