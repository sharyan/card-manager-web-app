package org.sharyan.project.cardwebapp.persistence.entity;


import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "payment_card_table")
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class PaymentCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NaturalId
    private String cardNumber;

    @Column(nullable = false)
    String cardName;

    @Basic(fetch = FetchType.LAZY)
    private int month;

    @Basic(fetch = FetchType.LAZY)
    private int year;
}
