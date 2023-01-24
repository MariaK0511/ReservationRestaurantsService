package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.exception.PhoneNumberNotFoundException;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CorrectionPhoneNumberTest {

    @InjectMocks
    private CorrectionPhoneNumber correctionPhoneNumber;

    @Test
    void checkIfPatternCoincidesWithPhoneNumberWithPlusMoreThanOne() {
        assertTrue(correctionPhoneNumber.isPhoneContainsMoreThanOnePlus("+++3754456776567"));
    }

    @Test
    void checkPhoneNumberIfBeginWithPlus() {
        String phoneNumber = "37544455656";
        assertEquals("+37544455656", correctionPhoneNumber.correctPhoneNumber(phoneNumber));
    }

    @Test
    void changePhoneNumberWithMoreThanOnePlus() {
        String checkedPhoneNumber = correctionPhoneNumber.correctPhoneNumber("+++++3756667887");
        assertEquals("+37555664783", checkedPhoneNumber);
    }

    @Test
    void checkIfPhoneNumberHasPlus() {
        String checkedPhoneNumber = "+37544776547";
        assertEquals("+37555664783", correctionPhoneNumber.correctPhoneNumber(checkedPhoneNumber));
    }

    @Test
    void checkPhoneNumberIsNotNull() {
        String checkedPhoneNumber = null;
        assertThrows(PhoneNumberNotFoundException.class, () -> correctionPhoneNumber.correctPhoneNumber(checkedPhoneNumber));
    }
}