package brzezinski.rafal.githubapirelay.service;

import brzezinski.rafal.githubapirelay.dto.GithubUserDTO;
import brzezinski.rafal.githubapirelay.dto.UserDTO;
import brzezinski.rafal.githubapirelay.mapper.UserMapper;
import brzezinski.rafal.githubapirelay.model.UserRequestCount;
import brzezinski.rafal.githubapirelay.repository.UserRequestCountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private GithubService githubService;

    @Mock
    private UserRequestCountRepository userRequestCountRepository;

    @Mock
    private UserMapper userMapper;

    private static final String TEST_LOGIN = "testLogin";

    @Test
    void getUser_returnsMappedUser_andIncrementsRequestCount() {
        GithubUserDTO githubUser = createGithubUserDTO();

        UserDTO expectedUser = createUserDTO();

        String testLogin = TEST_LOGIN;
        UserRequestCount userRequestCount = new UserRequestCount(testLogin, 5);

        Mockito.when(githubService.getGithubUser(testLogin)).thenReturn(githubUser);
        Mockito.when(userMapper.githubResponseToUser(githubUser)).thenReturn(expectedUser);
        Mockito.when(userRequestCountRepository.findById(testLogin)).thenReturn(Optional.of(userRequestCount));

        UserDTO result = userService.getUser(testLogin);

        assertEquals(expectedUser, result);

        ArgumentCaptor<UserRequestCount> captor = ArgumentCaptor.forClass(UserRequestCount.class);
        Mockito.verify(userRequestCountRepository).save(captor.capture());

        UserRequestCount savedUserRequest = captor.getValue();
        assertEquals(6, savedUserRequest.getRequestCount());
    }

    @Test
    void getUser_noUserRequestCountEntry() {
        GithubUserDTO githubUser = createGithubUserDTO();

        UserDTO expectedUser = createUserDTO();

        Mockito.when(githubService.getGithubUser(TEST_LOGIN)).thenReturn(githubUser);
        Mockito.when(userMapper.githubResponseToUser(githubUser)).thenReturn(expectedUser);
        Mockito.when(userRequestCountRepository.findById(TEST_LOGIN)).thenReturn(Optional.empty());

        UserDTO result = userService.getUser(TEST_LOGIN);

        assertNotNull(result);
        assertEquals(expectedUser, result);

        ArgumentCaptor<UserRequestCount> captor = ArgumentCaptor.forClass(UserRequestCount.class);
        Mockito.verify(userRequestCountRepository).save(captor.capture());

        UserRequestCount savedUserRequest = captor.getValue();
        assertEquals(1, savedUserRequest.getRequestCount());
    }

    @Test
    void getUser_githubFetchFailed() {
        String invalidLogin = "invalidLogin";
        Mockito.when(githubService.getGithubUser(invalidLogin)).thenThrow(new RuntimeException("User not found"));

        assertThrows(RuntimeException.class, () -> userService.getUser(invalidLogin));
    }

    private GithubUserDTO createGithubUserDTO() {
        GithubUserDTO dto = new GithubUserDTO();
        dto.setLogin(TEST_LOGIN);
        dto.setId(1L);
        dto.setName("Test Name");
        dto.setType("User");
        dto.setAvatarUrl("http://example.com/avatar");
        dto.setCreatedAt(Instant.now());
        dto.setFollowers(5);
        dto.setPublicRepos(10);
        return dto;
    }

    private UserDTO createUserDTO() {
        UserDTO dto = new UserDTO();
        dto.setLogin(TEST_LOGIN);
        dto.setId(1L);
        dto.setName("Test Name");
        dto.setType("User");
        dto.setAvatarUrl("http://example.com/avatar");
        dto.setCreatedAt(Instant.now());
        return dto;
    }
}

