package brzezinski.rafal.githubapirelay.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestCount {
    @Id
    @Column(nullable = false, unique = true, updatable = false)
    private String login;

    private int requestCount;
}
