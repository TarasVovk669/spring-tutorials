package com.tutorial.graphql.graphqltutorials.service.impl;

import com.tutorial.graphql.graphqltutorials.dao.UserzRepository;
import com.tutorial.graphql.graphqltutorials.dao.UserzTokenRepository;
import com.tutorial.graphql.graphqltutorials.domain.Userz;
import com.tutorial.graphql.graphqltutorials.domain.UserzToken;
import com.tutorial.graphql.graphqltutorials.exception.ProblemzAuthException;
import com.tutorial.graphql.graphqltutorials.service.UserCommandService;
import com.tutorial.graphql.graphqltutorials.util.HashUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserzRepository userzRepository;
    private final UserzTokenRepository userzTokenRepository;

    public UserCommandServiceImpl(UserzRepository userzRepository, UserzTokenRepository userzTokenRepository) {
        this.userzRepository = userzRepository;
        this.userzTokenRepository = userzTokenRepository;
    }

    @Override
    public UserzToken login(String username, String password) {
        var user = userzRepository.findByUsernameIgnoreCase(username);

        if (user.isEmpty() || !HashUtil.isCryptMatch(password, user.get().getPassword())) {
            throw new ProblemzAuthException();
        }

        var authToken = RandomStringUtils.randomAlphanumeric(40);

        return refreshToken(user.get().getId(),authToken);
    }

    @Override
    public Userz createUser(Userz userz){
        return userzRepository.save(userz);
    }

    @Override
    public Optional<Userz> activateUser(String userName, boolean isActivate){
        userzRepository.activateUser(userName,isActivate);

        return userzRepository.findByUsernameIgnoreCase(userName).stream().findFirst();
    }



    private UserzToken refreshToken(UUID userId, String authToken){
        var userzToken = new UserzToken();
        userzToken.setUserId(userId);
        userzToken.setToken(authToken);
        userzToken.setCreationDateTime(LocalDateTime.now());
        userzToken.setExpirationDateTime(LocalDateTime.now().plusHours(2));

        return userzTokenRepository.save(userzToken);
    }


}
