package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.exception.PhoneNumberNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class CorrectionPhoneNumber {

    public boolean isPhoneContainsMoreThanOnePlus(String checkedPhoneNumber) {
        Pattern p = Pattern.compile("[\\+]{2,}+[\\d]+");
        Matcher m = p.matcher(checkedPhoneNumber);
        return m.matches();
    }

    public String correctPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            throw new PhoneNumberNotFoundException("such phone number wasn't found!");
        }
        if (isPhoneContainsMoreThanOnePlus(phoneNumber)) {
            phoneNumber = phoneNumber.replaceAll("[\\+]{2,}", "+");
        }
        String plus = "+";
        if (phoneNumber.startsWith(plus)) {
            return phoneNumber;
        } else {
            return plus.concat(phoneNumber);
        }
    }
}