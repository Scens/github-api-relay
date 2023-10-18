package brzezinski.rafal.githubapirelay.mapper;

import brzezinski.rafal.githubapirelay.dto.GithubUserDTO;
import brzezinski.rafal.githubapirelay.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void githubResponseToUser_githubUserIsNull() {
        UserDTO userDTO = userMapper.githubResponseToUser(null);
        assertNull(userDTO);
    }

    @Test
    void githubResponseToUser_validGithubUser() {
        GithubUserDTO githubUser = createGithubUserDTO();
        UserDTO userDTO = userMapper.githubResponseToUser(githubUser);

        assertEquals(githubUser.getLogin(), userDTO.getLogin());
        assertEquals(githubUser.getName(), userDTO.getName());
        assertNotNull(userDTO.getCalculations());
    }

    private GithubUserDTO createGithubUserDTO() {
        GithubUserDTO dto = new GithubUserDTO();
        dto.setLogin("testLogin");
        dto.setId(1L);
        dto.setName("Test Name");
        dto.setType("User");
        dto.setAvatarUrl("http://example.com/avatar");
        dto.setCreatedAt(Instant.now());
        dto.setPublicRepos(10);
        dto.setFollowers(5);
        return dto;
    }
}

