package com.reservation_restaurants_service.service;

import com.reservation_restaurants_service.exception.PhoneNumberNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class CorrectionPhoneNumber {
    private String plus = "+";
    public  boolean isValid(String checkedPhoneNumber) {
        Pattern p = Pattern.compile("[\\+]{2,}+[\\d]+");
        Matcher m = p.matcher(checkedPhoneNumber);
        return (m.matches());
    }

    public String correctPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            throw new PhoneNumberNotFoundException("such phone number wasn't found!");
        }
        if (isValid(phoneNumber)) {
            phoneNumber = phoneNumber.replaceAll("[\\+]{2,}", "+");
        }
        if (phoneNumber.startsWith(plus)) {
            return phoneNumber;
        } else {
            return plus.concat(phoneNumber);
        }
    }
}