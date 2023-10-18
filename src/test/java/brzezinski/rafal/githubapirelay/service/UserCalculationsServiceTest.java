package brzezinski.rafal.githubapirelay.service;

import brzezinski.rafal.githubapirelay.dto.GithubUserDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserCalculationsServiceTest {

    @Test
    void getCalculationsValue_followersZero() {
        GithubUserDTO githubUser = new GithubUserDTO();
        githubUser.setFollowers(0);

        BigDecimal result = UserCalculationsService.getCalculationsValue(githubUser, BigDecimal.valueOf(10), 5, BigDecimal.valueOf(30));

        assertEquals(BigDecimal.valueOf(10), result);
    }

    @Test
    void getCalculationsValue_noPublicRepos() {
        GithubUserDTO githubUser = new GithubUserDTO();
        githubUser.setFollowers(7);
        githubUser.setPublicRepos(0);

        BigDecimal result = UserCalculationsService.getCalculationsValue(githubUser, BigDecimal.valueOf(20), 4, BigDecimal.valueOf(40));

        BigDecimal expected = BigDecimal.valueOf(22.86);
        assertEquals(expected, result);
    }

    @Test
    void getCalculationsValue_withPublicRepos1() {
        GithubUserDTO githubUser = new GithubUserDTO();
        githubUser.setFollowers(6);
        githubUser.setPublicRepos(2);

        BigDecimal result = UserCalculationsService.getCalculationsValue(githubUser, BigDecimal.valueOf(15), 3, BigDecimal.valueOf(45));

        BigDecimal expected = BigDecimal.valueOf(37.50).setScale(2, RoundingMode.HALF_UP);
        assertEquals(expected, result);
    }

    @Test
    void getCalculationsValue_withPublicRepos2() {
        GithubUserDTO githubUser = new GithubUserDTO();
        githubUser.setFollowers(3);
        githubUser.setPublicRepos(1);

        BigDecimal result = UserCalculationsService.getCalculationsValue(githubUser, BigDecimal.valueOf(4.23), 8, BigDecimal.valueOf(0.1));

        BigDecimal expected = BigDecimal.valueOf(0.30).setScale(2, RoundingMode.HALF_UP);
        assertEquals(expected, result);
    }

    @Test
    void getCalculationsValue_addendAndPublicReposAreZeros() {
        GithubUserDTO githubUser = new GithubUserDTO();
        githubUser.setFollowers(3);
        githubUser.setPublicRepos(0);
        
        BigDecimal result = UserCalculationsService.getCalculationsValue(githubUser, BigDecimal.valueOf(4), 0, BigDecimal.valueOf(5.22));

        BigDecimal expected = BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP);
        assertEquals(expected, result);
    }
}

