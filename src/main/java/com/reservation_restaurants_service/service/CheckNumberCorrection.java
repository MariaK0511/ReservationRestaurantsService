package com.reservation_restaurants_service.service;

import org.apache.el.stream.Stream;

import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

public class CheckNumberCorrection {
    @Pattern(regexp = "\\+375[\\d]+")
    private String phoneNumber;
    private String plus = "+";

    private String checkCorrectionPhoneNumber(String phoneNumber) {
        if (phoneNumber.startsWith("\\W{2,n}")) {
            phoneNumber = phoneNumber.replace("\\W{2,n}", "+");
        }
        if (phoneNumber.startsWith(plus)) {
            return phoneNumber;
        } else {
            return plus.concat(phoneNumber);
        }
    }
}
