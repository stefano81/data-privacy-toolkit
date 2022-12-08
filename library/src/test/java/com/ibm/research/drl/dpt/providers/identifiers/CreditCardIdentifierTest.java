/*******************************************************************
 *                                                                 *
 * Copyright IBM Corp. 2020                                        *
 *                                                                 *
 *******************************************************************/
package com.ibm.research.drl.dpt.providers.identifiers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreditCardIdentifierTest {
    private CreditCardIdentifier identifier;

    @BeforeEach
    public void setUp() throws Exception {
        identifier = new CreditCardIdentifier();
    }

    @Test
    public void testAMEX() throws Exception {
        assertTrue(identifier.isOfThisType("370000992821860"));

        /*
        for (int i = 0; i < 100; ++i) {
            String ccn = JDefaultBusiness.creditCardNumber(JDefaultCreditCardType.AMEX);
            assertTrue(identifier.isOfThisType(ccn));
        }
        */
    }

    @Test
    public void testDC() throws Exception {
        assertTrue(identifier.isOfThisType("30000099611752"));
    }

    @Test
    public void testDISC() throws Exception {
        assertTrue(identifier.isOfThisType("6011009285752817"));

        /*
        for (int i = 0; i < 100; ++i) {
            String ccn = JDefaultBusiness.creditCardNumber(JDefaultCreditCardType.DISCOVER);
            assertTrue(identifier.isOfThisType(ccn));
        }*/
    }

    @Disabled
    @Test
    public void testJBC() throws Exception {
        assertTrue(identifier.isOfThisType("3088009773563374"));
    }

    @Test
    public void testMC() throws Exception {
        assertTrue(identifier.isOfThisType("5500009337062017"));

        /*
        for (int i = 0; i < 100; ++i) {
            String ccn = JDefaultBusiness.creditCardNumber(JDefaultCreditCardType.MASTERCARD);
            assertTrue(identifier.isOfThisType(ccn));
        }
        */
    }

    @Test
    public void testVisa() throws Exception {
        assertTrue(identifier.isOfThisType("4111119762378756")); // VISA

        /*
        for (int i = 0; i < 100; ++i) {
            String ccn = JDefaultBusiness.creditCardNumber(JDefaultCreditCardType.VISA);
            assertTrue(identifier.isOfThisType(ccn));
        }
        */
    }

    @Test
    public void testError() throws Exception {
        assertFalse(identifier.isOfThisType("fjadlsjfal;sf"));
        assertFalse(identifier.isOfThisType("1234567890"));
    }

    @Test
    @Disabled
    public void testQuickCheckBenefit() {
        CreditCardIdentifier identifier = new CreditCardIdentifier();
        int runs = 1000000;

        String value = "this is definitely not a match";

        long start = System.currentTimeMillis();
        int matches = 0;
        for(int i = 0; i < runs; i++) {
            if (identifier.isOfThisType(value)) {
                matches++;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("time : " + (end - start));
        System.out.println("matches: " + matches);

        start = System.currentTimeMillis();
        matches = 0;
        for(int i = 0; i < runs; i++) {
            if (quickCheck(value)) {
                if (identifier.isOfThisType(value)) {
                    matches++;
                }
            }
        }
        end = System.currentTimeMillis();
        System.out.println("time : " + (end - start));
        System.out.println("matches: " + matches);
    }

    private boolean quickCheck(String value) {
        for(int i = 0; i < value.length(); i++) {
            if (Character.isDigit(value.charAt(i))) {
                return true;
            }
        }

        return false;
    }
}
