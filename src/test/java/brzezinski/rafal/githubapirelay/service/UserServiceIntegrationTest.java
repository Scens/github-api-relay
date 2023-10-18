package brzezinski.rafal.githubapirelay.service;

import brzezinski.rafal.githubapirelay.client.GithubClient;
import brzezinski.rafal.githubapirelay.dto.GithubUserDTO;
import brzezinski.rafal.githubapirelay.dto.UserDTO;
import brzezinski.rafal.githubapirelay.mapper.UserMapperConsts;
import brzezinski.rafal.githubapirelay.model.UserRequestCount;
import brzezinski.rafal.githubapirelay.repository.UserRequestCountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @MockBean
    private GithubClient githubClient;

    @Autowired
    private UserRequestCountRepository userRequestCountRepository;

    private static final String TEST_LOGIN = "testLogin";
    private static final String TEST_LOGIN_2 = "testLogin2";

    @BeforeEach
    void setUp() {
        userRequestCountRepository.deleteAll();
    }

    @Test
    void getUserIntegrationTest_firstRequest() {
        GithubUserDTO githubUserDTO = createGithubUserDTO();
        Mockito.when(githubClient.getUser(TEST_LOGIN)).thenReturn(githubUserDTO);

        UserDTO result = userService.getUser(TEST_LOGIN);

        assertUsersAreEqual(githubUserDTO, result);
        Optional<UserRequestCount> userRequestCountOpt = userRequestCountRepository.findById(TEST_LOGIN);
        assertTrue(userRequestCountOpt.isPresent());
        assertEquals(1, userRequestCountOpt.get().getRequestCount());
    }

    @Test
    void getUserIntegrationTest_thirdRequest() {
        GithubUserDTO githubUserDTO = createGithubUserDTO();
        Mockito.when(githubClient.getUser(TEST_LOGIN)).thenReturn(githubUserDTO);
        userRequestCountRepository.save(new UserRequestCount(TEST_LOGIN, 2));

        UserDTO result = userService.getUser(TEST_LOGIN);

        assertUsersAreEqual(githubUserDTO, result);
        Optional<UserRequestCount> userRequestCountOpt = userRequestCountRepository.findById(TEST_LOGIN);
        assertTrue(userRequestCountOpt.isPresent());
        assertEquals(3, userRequestCountOpt.get().getRequestCount());
    }

    @Test
    void getUserIntegrationTest_secondUser() {
        GithubUserDTO secondUserDTO = createSecondGithubUserDTO();
        Mockito.when(githubClient.getUser(TEST_LOGIN_2)).thenReturn(secondUserDTO);

        UserDTO result = userService.getUser(TEST_LOGIN_2);

        assertUsersAreEqual(secondUserDTO, result);
        Optional<UserRequestCount> userRequestCountOpt = userRequestCountRepository.findById(TEST_LOGIN_2);
        assertTrue(userRequestCountOpt.isPresent());
        assertEquals(1, userRequestCountOpt.get().getRequestCount());
    }

    @Test
    void getUserIntegrationTest_noFollowers() {
        GithubUserDTO githubUserDTO = createGithubUserDTO();
        githubUserDTO.setFollowers(0);
        Mockito.when(githubClient.getUser(TEST_LOGIN)).thenReturn(githubUserDTO);

        UserDTO result = userService.getUser(TEST_LOGIN);

        assertUsersAreEqual(githubUserDTO, result);
        assertEquals(result.getCalculations(), UserMapperConsts.DEFAULT_CALCULATIONS_VALUE);
    }

    @Test
    void getUserIntegrationTest_githubClientError() {
        Mockito.when(githubClient.getUser(TEST_LOGIN)).thenThrow(new RuntimeException("Simulated exception"));

        assertThrows(RuntimeException.class, () -> userService.getUser(TEST_LOGIN));
    }

    private static GithubUserDTO createGithubUserDTO() {
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

    private static GithubUserDTO createSecondGithubUserDTO() {
        GithubUserDTO dto = new GithubUserDTO();
        dto.setLogin(TEST_LOGIN_2);
        dto.setId(2L);
        dto.setName("Second Test Name");
        dto.setType("User");
        dto.setAvatarUrl("http://example.com/avatar2");
        dto.setCreatedAt(Instant.now().minusSeconds(5000));
        dto.setFollowers(15);
        dto.setPublicRepos(20);
        return dto;
    }

    private static void assertUsersAreEqual(GithubUserDTO githubUserDTO, UserDTO userDTO) {
        assertEquals(githubUserDTO.getId(), userDTO.getId());
        assertEquals(githubUserDTO.getLogin(), userDTO.getLogin());
        assertEquals(githubUserDTO.getName(), userDTO.getName());
        assertEquals(githubUserDTO.getType(), userDTO.getType());
        assertEquals(githubUserDTO.getAvatarUrl(), userDTO.getAvatarUrl());
        assertEquals(githubUserDTO.getCreatedAt(), userDTO.getCreatedAt());
        assertNotNull(userDTO.getCalculations());
    }
}

