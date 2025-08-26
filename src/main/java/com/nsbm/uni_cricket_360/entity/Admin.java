package com.nsbm.uni_cricket_360.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@NoArgsConstructor
@Data
@ToString
@Entity
@DiscriminatorValue("ADMIN")
@PrimaryKeyJoinColumn(name = "id")
public class Admin extends User{

    public Admin(Long id) {
       super(id);
    }
}
