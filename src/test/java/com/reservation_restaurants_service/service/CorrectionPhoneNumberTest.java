package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.exception.PhoneNumberNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CorrectionPhoneNumberTest {

    @InjectMocks
    private CorrectionPhoneNumber correctionPhoneNumber;

    @Test
    void checkIfPatternCoincidesWithPhoneNumberWithPlusMoreThanOne() {
        assertTrue(correctionPhoneNumber.isValid("+++3754456776567"));
    }

    @Test
    void checkPhoneNumberIfBeginWithPlus() {
        String phoneNumber = "+37544455656";
        String plus = "+";
        if (phoneNumber.startsWith(plus)) {
            assertTrue(true);
        }
    }

    @Test
    void changePhoneNumberWithMoreThanOnePlus() {
        String phoneNumber = "++++37544455656";
        if (correctionPhoneNumber.isValid(phoneNumber)) {
            phoneNumber = phoneNumber.replaceAll("[\\+]{2,}", "+");
        }
    }


    @Test
    void checkIfPhoneNumberHasPlus() {
        String plus = "+";
        String checkedPhoneNumber = "37544776547";
        if (!checkedPhoneNumber.startsWith(plus)) {
            plus.concat(checkedPhoneNumber);
        }
    }

    @Test
    void checkPhoneNumberIsNotNull() {
        String checkedPhoneNumber = null;
        assertNotNull(correctionPhoneNumber.correctPhoneNumber("+375447899807"));
        assertThrows(PhoneNumberNotFoundException.class, () -> correctionPhoneNumber.correctPhoneNumber(checkedPhoneNumber));
    }
}
