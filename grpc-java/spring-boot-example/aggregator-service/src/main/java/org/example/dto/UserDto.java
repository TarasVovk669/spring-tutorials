package org.example.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@Builder
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
}
