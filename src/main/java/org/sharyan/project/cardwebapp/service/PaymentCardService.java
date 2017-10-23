package org.sharyan.project.cardwebapp.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.sharyan.project.cardwebapp.dto.PaymentCardDto;
import org.sharyan.project.cardwebapp.error.UserNotFoundException;
import org.sharyan.project.cardwebapp.persistence.dao.PaymentCardRepository;
import org.sharyan.project.cardwebapp.persistence.dao.UserRepository;
import org.sharyan.project.cardwebapp.persistence.entity.PaymentCard;
import org.sharyan.project.cardwebapp.persistence.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class PaymentCardService {

    @Autowired
    private PaymentCardRepository paymentCardRepository;

    @Autowired
    private UserRepository userRepository;

    private final LoadingCache<String, User> inMemoryuserCache;

    public PaymentCardService() {
        inMemoryuserCache = CacheBuilder.newBuilder()
                .expireAfterAccess(1, TimeUnit.HOURS)
                .initialCapacity(100)
                .maximumSize(1000)
                .build(new CacheLoader<String, User>() {
                    @Override
                    public User load(String username) throws Exception {
                        return userRepository.findByUsername(username);
                    }
                });
    }

    @Transactional
    @Secured(value = {"ROLE_USER"})
    public void addOrUpdatePaymentCard(Authentication auth, PaymentCardDto paymentCardDto) {
        User requestingUser = getUser(auth.getName());

        if (requestingUser == null) {
           return;
        }

        // check if payment card already exists
        PaymentCard existingPaymentCard = paymentCardRepository.findByCardNumberAndOwner_Id(paymentCardDto.getCardNumber(), requestingUser.getId());

        if (existingPaymentCard != null) {
            // delete card, and update with new details
            paymentCardRepository.deleteById(existingPaymentCard.getId());
        }

        paymentCardRepository.save(PaymentCard.builder()
                .cardNumber(paymentCardDto.getCardNumber())
                .cardName(paymentCardDto.getCardName())
                .month(paymentCardDto.getCardExpiry().getMonth().getValue())
                .year(paymentCardDto.getCardExpiry().getYear().getValue())
                .owner(requestingUser)
                .build());
    }

    @Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
    public List<PaymentCard> searchForPaymentCards(Authentication auth, String cardNumber) {
        if (hasAdminAuthority(auth)) {
            // admin search
            return paymentCardRepository.findAllByCardNumberLike(cardNumber);
        } else if(hasUserAuthority(auth)) {
            org.springframework.security.core.userdetails.User currentUser = (org.springframework.security.core.userdetails.User)auth.getPrincipal();
            User requestingUser = getUser(currentUser.getUsername());
            if (requestingUser == null) {
                return Collections.emptyList();
            }
            return paymentCardRepository.findAllByCardNumberLikeAndOwnerIdEquals(cardNumber, requestingUser.getId());
        } else {
            return Collections.emptyList();
        }
    }

    private User getUser(String username) {
        try {
            return inMemoryuserCache.get(username);
        } catch (ExecutionException e) {
            throw new UserNotFoundException("Could not find user with username " + username);
        }
    }

    private boolean hasAuthority(Authentication auth, String role) {
        return auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(s -> s.equals(role));
    }

    private boolean hasAdminAuthority(Authentication auth) {
        return hasAuthority(auth, "ROLE_ADMIN");
    }

    private boolean hasUserAuthority(Authentication auth) {
        return hasAuthority(auth, "ROLE_USER");
    }
}
