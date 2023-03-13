import java.util.ArrayList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class token {

    private static final Pattern IDENTIFIER_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]*$");
    private static final Pattern INTEGER_PATTERN = Pattern.compile("^[0-9]+$");
    private static final Pattern OPERATOR_PATTERN = Pattern.compile("^[+\\-*/()]$");

    private String input;
    private int index;

    public token(String input) {
        this.input = input;
        this.index = 0;
    }

    public boolean hasNext() {
        return index < input.length();
    }

    public String nextToken() throws InvalidTokenException {
        if (!hasNext()) {
            return null;
        }

        // Try to match an identifier
        Matcher identifierMatcher = IDENTIFIER_PATTERN.matcher(input.substring(index));
        if (identifierMatcher.find()) {
            String identifier = identifierMatcher.group();
            index += identifier.length();
            return identifier;
        }

        // Try to match an integer
        Matcher integerMatcher = INTEGER_PATTERN.matcher(input.substring(index));
        if (integerMatcher.find()) {
            String integer = integerMatcher.group();
            index += integer.length();
            return integer;
        }

        // Try to match an operator
        Matcher operatorMatcher = OPERATOR_PATTERN.matcher(input.substring(index, index + 1));
        if (operatorMatcher.matches()) {
            String operator = operatorMatcher.group();
            index++;
            return operator;
        }

        // If no match found, throw an exception
        throw new InvalidTokenException("Invalid token at index " + index);
    }

    public static void main(String[] args) {
        String input = "a + 3 * (b - c)";
        token tokenizer = new token(input);

        while (tokenizer.hasNext()) {
            try {
                System.out.println(tokenizer.nextToken());
            } catch (InvalidTokenException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

class InvalidTokenException extends Exception {
    public InvalidTokenException(String message) {
        super(message);
    }
}

