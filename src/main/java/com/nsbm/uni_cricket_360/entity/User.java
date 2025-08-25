package com.nsbm.uni_cricket_360.entity;

import com.nsbm.uni_cricket_360.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_role", discriminatorType = DiscriminatorType.STRING)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String password;

    /*
    * This way, Hibernate will read the discriminator column directly into the field.
    * You cannot update it (Hibernate controls it).
    * */
    @Column(name = "user_role", insertable = false, updatable = false)
    private String user_role;
}
