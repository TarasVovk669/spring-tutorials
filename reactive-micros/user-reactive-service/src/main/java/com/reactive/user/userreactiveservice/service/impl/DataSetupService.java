package com.reactive.user.userreactiveservice.service.impl;

import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.util.StreamUtils;

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
