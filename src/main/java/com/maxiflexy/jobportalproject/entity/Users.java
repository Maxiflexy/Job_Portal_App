package com.maxiflexy.jobportalproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(unique = true)
    private String email;

    @NotEmpty
    private String password;

    private boolean isActive;

    @DateTimeFormat(pattern = "dd-MM-YYYY")
    private Date registrationDate;

    @ManyToOne(cascade = CascadeType.ALL) // means that all operations (persist, merge, remove, refresh, detach) applied to this entity will also be applied to the UsersType entity.This can be useful when you want changes in this entity to automatically propagate to the related UsersType entity.
    @JoinColumn(name = "userTypeId", referencedColumnName = "userTypeId")  // Indicates that the userTypeId column in the current entity’s table is a foreign key that references the userTypeId column in the UsersType entity’s table.
    private UsersType userTypeId;


}
