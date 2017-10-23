package org.sharyan.project.cardwebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentCardDto {

    @NotBlank(message = "Card number cannot be empty")
    @Pattern(regexp = "[0-9]{13,19}", message = "Card number must only contain numbers") // basic check for card numbers, this would be more elaborate for a 'real' payment system
    private String cardNumber;

    @NotBlank(message = "Card name cannot be empty")
    @Pattern(regexp = "[A-Za-z0-9]*", message = "Card namne must be made up of valid alphanumeric characters")
    @Size(min = 3, max = 30)
    private String cardName;

    private CardExpiry cardExpiry;
}
