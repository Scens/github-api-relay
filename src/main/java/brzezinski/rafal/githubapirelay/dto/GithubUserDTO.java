package brzezinski.rafal.githubapirelay.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class GithubUserDTO {
    private String login;
    private long id;
    private String name;
    private String type;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    @JsonProperty("created_at")
    private Instant createdAt;
    @JsonProperty("followers")
    private int followers;
    @JsonProperty("public_repos")
    private int publicRepos;
}
