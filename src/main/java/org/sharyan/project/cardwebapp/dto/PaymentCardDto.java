package org.sharyan.project.cardwebapp.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.*;
import java.time.Month;
import java.time.Year;

// TODO: add validation
@Value(staticConstructor = "createNewCard")
@Builder
public class PaymentCardDto {

    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "[0-9]{13,19}") // basic check for card numbers, this would be more elaborate for a 'real' payment system
    private final String cardNumber;

    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "[A-Za-z0-9]*")
    @Size(min = 3, max = 30)
    private final String cardName;

    @FutureOrPresent
    private final Month expiryMonth;

    @FutureOrPresent
    private final Year expiryYear;
}
