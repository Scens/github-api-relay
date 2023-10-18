package brzezinski.rafal.githubapirelay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GithubApiRelayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GithubApiRelayApplication.class, args);
    }

}
