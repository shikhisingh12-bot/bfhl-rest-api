package com.example.bfhl.service;

import com.example.bfhl.dto.BfhlRequest;
import com.example.bfhl.dto.BfhlResponse;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BfhlServiceTest {

    private final BfhlService service = new BfhlServiceImpl();

    private static final String EXPECTED_USER_ID     = "shikhi_parmar_26062004";
    private static final String EXPECTED_EMAIL       = "shikhiparmar230715@acropolis.in";
    private static final String EXPECTED_ROLL_NUMBER = "0827CS231243";

    private void verifyCommonFields(BfhlResponse response) {
        assertTrue(response.isSuccess());
        assertEquals(EXPECTED_USER_ID, response.getUserId());
        assertEquals(EXPECTED_EMAIL, response.getEmail());
        assertEquals(EXPECTED_ROLL_NUMBER, response.getRollNumber());
    }

    @Test
    void testExampleA() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("a", "1", "334", "4", "R", "$"));
        BfhlResponse response = service.processData(request);

        verifyCommonFields(response);
        assertEquals(Collections.singletonList("1"), response.getOddNumbers());
        assertEquals(Arrays.asList("334", "4"), response.getEvenNumbers());
        assertEquals(Arrays.asList("A", "R"), response.getAlphabets());
        assertEquals(Collections.singletonList("$"), response.getSpecialCharacters());
        assertEquals("339", response.getSum());
        assertEquals("Ra", response.getConcatString());
    }

    @Test
    void testExampleB() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("2", "a", "y", "4", "&", "-", "*", "5", "92", "b"));
        BfhlResponse response = service.processData(request);

        verifyCommonFields(response);
        assertEquals(Collections.singletonList("5"), response.getOddNumbers());
        assertEquals(Arrays.asList("2", "4", "92"), response.getEvenNumbers());
        assertEquals(Arrays.asList("A", "Y", "B"), response.getAlphabets());
        assertEquals(Arrays.asList("&", "-", "*"), response.getSpecialCharacters());
        assertEquals("103", response.getSum());
        assertEquals("ByA", response.getConcatString());
    }

    @Test
    void testExampleC() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("A", "ABCD", "DOE"));
        BfhlResponse response = service.processData(request);

        verifyCommonFields(response);
        assertEquals(Collections.emptyList(), response.getOddNumbers());
        assertEquals(Collections.emptyList(), response.getEvenNumbers());
        assertEquals(Arrays.asList("A", "ABCD", "DOE"), response.getAlphabets());
        assertEquals(Collections.emptyList(), response.getSpecialCharacters());
        assertEquals("0", response.getSum());
        assertEquals("EoDdCbAa", response.getConcatString());
    }

    @Test
    void testAlphanumericInput() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("abc123", "12a", "#"));
        BfhlResponse response = service.processData(request);

        verifyCommonFields(response);
        assertEquals(Collections.emptyList(), response.getOddNumbers());
        assertEquals(Collections.emptyList(), response.getEvenNumbers());
        assertEquals(Collections.emptyList(), response.getAlphabets());
        assertEquals(Arrays.asList("abc123", "12a", "#"), response.getSpecialCharacters());
        assertEquals("0", response.getSum());
        assertEquals("", response.getConcatString());
    }

    @Test
    void testEmptyData() {
        BfhlRequest request = new BfhlRequest(Collections.emptyList());
        BfhlResponse response = service.processData(request);

        verifyCommonFields(response);
        assertEquals(Collections.emptyList(), response.getOddNumbers());
        assertEquals(Collections.emptyList(), response.getEvenNumbers());
        assertEquals(Collections.emptyList(), response.getAlphabets());
        assertEquals(Collections.emptyList(), response.getSpecialCharacters());
        assertEquals("0", response.getSum());
        assertEquals("", response.getConcatString());
    }
}
