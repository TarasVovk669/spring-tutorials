package com.reactive.user.userreactiveservice.service.impl;

import com.reactive.user.userreactiveservice.dto.UserDto;
import com.reactive.user.userreactiveservice.repository.UserRepository;
import com.reactive.user.userreactiveservice.service.UserService;
import com.reactive.user.userreactiveservice.util.DtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

//@Service only for h2
@RequiredArgsConstructor
public class DataSetupService implements CommandLineRunner {

  private final R2dbcEntityTemplate entityTemplate;

  @Value("classpath:h2/init.sql")
  private Resource initSql;

  @Override
  public void run(String... args) throws Exception {
    String query = StreamUtils.copyToString(initSql.getInputStream(), StandardCharsets.UTF_8);

    this.entityTemplate.getDatabaseClient().sql(query).then().subscribe();
  }
}
