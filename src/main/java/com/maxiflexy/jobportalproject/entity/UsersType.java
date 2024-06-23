package com.maxiflexy.jobportalproject.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users_type")
public class UsersType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userTypeId;

    private String userTypeName;

    /*
    targetEntity = Users.class: Specifies the target entity of the relationship, which is the Users class.

    mappedBy = "userTypeId": Indicates that the userTypeId field in the Users class owns the relationship.

    cascade = CascadeType.ALL:
    Applies all cascading operations (persist, merge, remove, refresh, detach)
    from the current entity to the Users entity.
     */
    @OneToMany(targetEntity = Users.class, mappedBy = "userTypeId", cascade = CascadeType.ALL)
    private List<Users> users;


}
