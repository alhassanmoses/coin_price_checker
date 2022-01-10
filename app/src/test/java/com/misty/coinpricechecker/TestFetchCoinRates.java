package com.misty.coinpricechecker;

import org.junit.Test;
import static org.junit.Assert.assertFalse;

public class TestFetchCoinRates {

    @Test
    public void test_getConversionRates() {

        assertFalse(ValidAmountChecker.isValidAmount("10s"));

    }

}
