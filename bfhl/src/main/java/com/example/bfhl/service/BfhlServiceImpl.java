package com.example.bfhl.service;

import com.example.bfhl.dto.BfhlRequest;
import com.example.bfhl.dto.BfhlResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BfhlServiceImpl implements BfhlService {

    private static final String FULL_NAME   = "shikhi_parmar";
    private static final String DOB         = "26062004";
    private static final String USER_ID     = FULL_NAME + "_" + DOB;   // shikhi_parmar_26062004
    private static final String EMAIL       = "shikhiparmar230715@acropolis.in";
    private static final String ROLL_NUMBER = "0827CS231243";

    @Override
    public BfhlResponse processData(BfhlRequest request) {
        List<String> oddNumbers       = new ArrayList<>();
        List<String> evenNumbers      = new ArrayList<>();
        List<String> alphabets        = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();
        long sum = 0;

        StringBuilder concatAccumulator = new StringBuilder();

        if (request != null && request.getData() != null) {
            for (String item : request.getData()) {
                if (item == null) continue;

                if (item.matches("\\d+")) {
                    // Numeric only → odd/even + sum
                    long val = Long.parseLong(item);
                    sum += val;
                    if (val % 2 == 0) {
                        evenNumbers.add(item);
                    } else {
                        oddNumbers.add(item);
                    }
                } else if (item.matches("[a-zA-Z]+")) {
                    // Alphabetic only → uppercase + collect for concat
                    alphabets.add(item.toUpperCase());
                    concatAccumulator.append(item);
                } else {
                    // Mixed / special → special_characters
                    specialCharacters.add(item);
                }
            }
        }

        String concatString = buildConcatString(concatAccumulator.toString());

        // Note: builder field name is 'success' (maps to "is_success" via @JsonProperty)
        return BfhlResponse.builder()
                .success(true)
                .userId(USER_ID)
                .email(EMAIL)
                .rollNumber(ROLL_NUMBER)
                .oddNumbers(oddNumbers)
                .evenNumbers(evenNumbers)
                .alphabets(alphabets)
                .specialCharacters(specialCharacters)
                .sum(String.valueOf(sum))
                .concatString(concatString)
                .build();
    }

    /**
     * Reverses all alphabetical characters accumulated from alphabetic-only inputs,
     * then applies alternating caps starting with UPPERCASE at index 0.
     *
     * Example: "aRDOE" reversed = "EODRa" → "EoDrA"
     */
    private String buildConcatString(String input) {
        if (input == null || input.isEmpty()) return "";
        String reversed = new StringBuilder(input).reverse().toString();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < reversed.length(); i++) {
            char c = reversed.charAt(i);
            result.append(i % 2 == 0 ? Character.toUpperCase(c) : Character.toLowerCase(c));
        }
        return result.toString();
    }
}
