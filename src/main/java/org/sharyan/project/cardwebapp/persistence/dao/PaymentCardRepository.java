package org.sharyan.project.cardwebapp.persistence.dao;

import org.sharyan.project.cardwebapp.persistence.entity.PaymentCard;
import org.sharyan.project.cardwebapp.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentCardRepository extends JpaRepository<PaymentCard, Long> {

    PaymentCard findByCardNumber(String cardNumber);

    List<PaymentCard> findAllByCardNumberEquals(String cardNumber);

    PaymentCard findAllByOwner(User owner);

    void deleteById(Long paymentCardId);
}
