package org.sharyan.project.cardwebapp.persistence.entity;

import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="user_table")
@Setter
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NaturalId
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection
    List<String> roles;
}
