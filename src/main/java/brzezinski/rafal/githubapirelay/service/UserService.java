package brzezinski.rafal.githubapirelay.service;

import brzezinski.rafal.githubapirelay.dto.GithubUserDTO;
import brzezinski.rafal.githubapirelay.dto.UserDTO;
import brzezinski.rafal.githubapirelay.mapper.UserMapper;
import brzezinski.rafal.githubapirelay.model.UserRequestCount;
import brzezinski.rafal.githubapirelay.repository.UserRequestCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final GithubService githubService;
    private final UserRequestCountRepository userRequestCountRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserDTO getUser(String login) {
        GithubUserDTO githubUser = githubService.getGithubUser(login);

        UserDTO user = userMapper.githubResponseToUser(githubUser);

        incrementRequestCount(login);

        return user;
    }

    private void incrementRequestCount(String login) {
        Optional<UserRequestCount> optionalUserRequestCount = userRequestCountRepository.findById(login);
        UserRequestCount userRequestCount = optionalUserRequestCount.orElse(new UserRequestCount(login, 0));

        userRequestCount.setRequestCount(userRequestCount.getRequestCount() + 1);

        userRequestCountRepository.save(userRequestCount);
    }
}
