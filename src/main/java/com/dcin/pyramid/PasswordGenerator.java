package com.dcin.pyramid;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Locale;

public class PasswordGenerator {
    public static void main(String[] args){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
        String[] countries = Locale.getISOCountries();
        System.out.println(Arrays.toString(countries));

    }
}
