package ru.tsavaph.cargotransportationuserinfoservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_info_table")
@SequenceGenerator(name = "user_info_table_id_seq", allocationSize = 1)
public class UserInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,  generator="user_info_table_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

}
