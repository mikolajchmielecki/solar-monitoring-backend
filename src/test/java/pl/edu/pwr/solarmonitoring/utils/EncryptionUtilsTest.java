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
    public void checkEncryption() {
        String text = "1234";
        String encrypted = EncryptionUtils.encrypt(text);
        String decrypted = EncryptionUtils.decrypt(encrypted);
        Assertions.assertEquals(text, decrypted, "Decrypted text doesn't match to original");
    }

}