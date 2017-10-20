package org.sharyan.project.cardwebapp.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@Table(name="role_table")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    private String name;

//    public Role() {
//        super();
//    }
//
//    public Role(final String name) {
//        super();
//        this.name = name;
//    }
}