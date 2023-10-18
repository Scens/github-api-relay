package brzezinski.rafal.githubapirelay.service;

import brzezinski.rafal.githubapirelay.dto.GithubUserDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class UserCalculationsService {
    public static BigDecimal getCalculationsValue(GithubUserDTO githubUser, BigDecimal defaultCalculationsValue, int addendForPublicRepos, BigDecimal numerator) {
        if (githubUser.getFollowers() == 0) {
            return defaultCalculationsValue;
        }

        BigDecimal divisor = BigDecimal.valueOf(githubUser.getFollowers());
        BigDecimal multiplicand = BigDecimal.valueOf(addendForPublicRepos + githubUser.getPublicRepos());

        return numerator
                .divide(divisor, 10, RoundingMode.HALF_UP)
                .multiply(multiplicand)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
