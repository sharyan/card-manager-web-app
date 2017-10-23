package org.sharyan.project.cardwebapp.dto;


import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.Month;
import java.time.Year;

@Getter
public class CardExpiry {

    private final Month month;
    private final Year year;

    @NotBlank(message = "Card expiry date must not be empty")
    @Pattern(regexp = "\\d{4}-\\d{2}", message = "Card expiry date is not valid")
    public CardExpiry(String dateInput) {
        year = Year.of(Integer.valueOf(dateInput.split("-")[0]));
        month = Month.of(Integer.valueOf(dateInput.split("-")[1]));
    }
}
