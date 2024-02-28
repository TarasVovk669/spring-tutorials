package com.demo.security.springsecuritydemo.source_of_user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "deactivated_token")

@Data
@ToString
public class DeactivatedToken {

    @Id
    private UUID id;

    private LocalDateTime keepUntil;


    public DeactivatedToken() {
    }

    public DeactivatedToken(UUID id, LocalDateTime keepUntil) {
        this.id = id;
        this.keepUntil = keepUntil;
    }
}
