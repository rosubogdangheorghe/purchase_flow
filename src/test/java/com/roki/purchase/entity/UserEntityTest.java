package com.roki.purchase.entity;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserEntityTest {

    private static UserEntity userEntity;

    private Date passwordChangeTime;

    @Before
    public void setUp() {
        userEntity = new UserEntity();
    }

    @Test
    public void isCredentialsNonExpired() {

        passwordChangeTime = null;

        userEntity.setPasswordChangeTime(passwordChangeTime);

        final Boolean result = userEntity.isCredentialsNonExpired();

        assertFalse(result);
    }


    @Test
    public void isCredentialNonExpiredFalse() throws ParseException {
        passwordChangeTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse("01/06/2021 10:13:18.0");
        userEntity.setPasswordChangeTime(passwordChangeTime);
        final Boolean result = userEntity.isCredentialsNonExpired();

        assertFalse(result);

    }
    @Test
    public void isCredentialNonExpired() {
        try {

            passwordChangeTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse("15/07/2021 10:13:18.0");
            userEntity.setPasswordChangeTime(passwordChangeTime);
            final Boolean result = userEntity.isCredentialsNonExpired();

            assertTrue(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

}

