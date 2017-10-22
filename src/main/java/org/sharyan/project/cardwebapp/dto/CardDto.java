package org.sharyan.project.cardwebapp.dto;

import lombok.Builder;
import lombok.Value;

import java.time.Month;
import java.time.Year;

// TODO: add validation
@Value(staticConstructor = "createNewCard")
@Builder
public class CardDto {

    private final String cardNumber;
    private final String cardName;
    private final Month expiryMonth;
    private final Year expiryYear;
}
