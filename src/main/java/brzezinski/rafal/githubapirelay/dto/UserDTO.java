package brzezinski.rafal.githubapirelay.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class UserDTO {
    private long id;
    private String login;
    private String name;
    private String type;
    private String avatarUrl;
    private Instant createdAt;
    private BigDecimal calculations;
}
