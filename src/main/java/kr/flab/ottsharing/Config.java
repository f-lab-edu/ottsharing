package kr.flab.ottsharing;

import org.springframework.context.annotation.Configuration;

import kr.flab.ottsharing.repository.UserRepository;

@Configuration
public class Config {

    private final UserRepository userRepository;

    public Config(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
