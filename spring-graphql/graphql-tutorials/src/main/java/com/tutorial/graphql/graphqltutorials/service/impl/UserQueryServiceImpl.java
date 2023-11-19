package com.tutorial.graphql.graphqltutorials.service.impl;

import com.tutorial.graphql.graphqltutorials.dao.UserzRepository;
import com.tutorial.graphql.graphqltutorials.domain.Userz;
import com.tutorial.graphql.graphqltutorials.service.UserQueryService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final UserzRepository userzRepository;

    public UserQueryServiceImpl(UserzRepository userzRepository) {
        this.userzRepository = userzRepository;
    }

    @Override
    public Optional<Userz> findUserzByToken(String token) {
        return userzRepository.findUserByToken(token);
    }
}
