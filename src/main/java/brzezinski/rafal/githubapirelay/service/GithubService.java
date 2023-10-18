package brzezinski.rafal.githubapirelay.service;

import brzezinski.rafal.githubapirelay.client.GithubClient;
import brzezinski.rafal.githubapirelay.dto.GithubUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GithubService {
    private final GithubClient githubClient;

    public GithubUserDTO getGithubUser(String login) {
        return this.githubClient.getUser(login);
    }
}
