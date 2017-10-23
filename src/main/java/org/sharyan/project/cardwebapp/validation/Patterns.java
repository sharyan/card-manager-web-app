package org.sharyan.project.cardwebapp.validation;

import java.util.regex.Pattern;

public class Patterns {

    private Patterns() {}

    public static Pattern VALID_CREDIT_CARD_PATTERN = Pattern.compile("[0-9]{13,19}");
    public static Pattern VALID_SEARCH_CARD_PATTERN = Pattern.compile("[0-9].*");
}
