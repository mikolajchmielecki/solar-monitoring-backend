/*
 * SolarMonitoring
 * Copyright (C) 2022 Mikołaj Chmielecki
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * LICENSE file in root directory contains a copy of the GNU General Public License.
 */

package pl.edu.pwr.solarmonitoring.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class EncryptionUtilsTest {

    @BeforeAll
    public static void setup() {
        EncryptionUtils.setKey("1234rsrfsfsfbwer");
    }

    @Test
    public void checkEncryptionTest() {
        String text = "1234";
        String encrypted = EncryptionUtils.encrypt(text);
        String decrypted = EncryptionUtils.decrypt(encrypted);
        Assertions.assertEquals(text, decrypted, "Decrypted text doesn't match to original");
    }

}