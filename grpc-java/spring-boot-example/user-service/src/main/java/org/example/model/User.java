package org.example.model;

import com.grpc.example.common.Genre;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "c_users")
@Data
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_Name")
    private String lastName;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "genre")
    @Enumerated(value = STRING)
    private Genre genre;
}
