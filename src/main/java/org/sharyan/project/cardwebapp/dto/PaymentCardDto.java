package org.sharyan.project.cardwebapp.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.*;
import java.time.Month;
import java.time.Year;

@Value(staticConstructor = "createNewCard")
@Builder
public class PaymentCardDto {

    @NotBlank(message = "Card number cannot be empty")
    @Pattern(regexp = "[0-9]{13,19}", message = "Card number must only contain numbers") // basic check for card numbers, this would be more elaborate for a 'real' payment system
    private final String cardNumber;

    @NotBlank(message = "Card name cannot be empty")
    @Pattern(regexp = "[A-Za-z0-9]*", message = "Card namne must be made up of valid alphanumeric characters")
    @Size(min = 3, max = 30)
    private final String cardName;

    private final Month expiryMonth;

    @FutureOrPresent(message = "Expiry date must be set in the future")
    private final Year expiryYear;
}
