package brzezinski.rafal.githubapirelay.service;

import brzezinski.rafal.githubapirelay.client.GithubClient;
import brzezinski.rafal.githubapirelay.dto.GithubUserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class GithubServiceTest {

    @InjectMocks
    private GithubService githubService;

    @Mock
    private GithubClient githubClient;

    @Test
    void getGithubUser_returnsGithubUser() {
        String testLogin = "testLogin";

        GithubUserDTO expectedUser = new GithubUserDTO();
        expectedUser.setLogin(testLogin);

        Mockito.when(githubClient.getUser(testLogin)).thenReturn(expectedUser);

        GithubUserDTO result = githubService.getGithubUser(testLogin);

        assertNotNull(result);
        assertEquals(expectedUser.getLogin(), result.getLogin());
    }
}

