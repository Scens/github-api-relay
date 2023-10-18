package brzezinski.rafal.githubapirelay.client;

import brzezinski.rafal.githubapirelay.dto.GithubUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "githubClient", url = "${github.url}")
public interface GithubClient {
    @GetMapping("/users/{login}")
    GithubUserDTO getUser(@PathVariable String login);
}
