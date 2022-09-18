package pl.edu.pwr.solarmonitoring.utils;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class EncryptionUtilsTest {

    @BeforeAll
    public static void setup() {
        EncryptionUtils.setKey("1234rsrfsfsfbwer");
    }

    @Test
    public void checkEncryption() {
        String text = "mikolaj1234";
        String encrypted = EncryptionUtils.encrypt(text);
        String decrypted = EncryptionUtils.decrypt(encrypted);
        Assert.assertEquals("Decrypted text doesn't match to original", text, decrypted);
    }

}