package com.fruitcat.bitcoin;

import com.google.bitcoin.core.Base58;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.Random;

/**
 * Unit tests
 *
 */
public class BIP38Test {

    String testPass = "TestingOneTwoThree";

    //EC multiply, no compression, no lot/sequence numbers
    @Test
    public void decryptECNoCompressionNoLot() throws Exception {
        String encryptedKey = "6PfQu77ygVyJLZjfvMLyhLMQbYnu5uguoJJ4kMCLqWwPEdfpwANVS76gTX";
        String key = "5K4caxezwjGCGfnoPTZ8tMcJBLB7Jvyjv4xxeacadhq8nLisLR2";
        String decryptedKey = BIP38.decrypt(testPass, encryptedKey);
        assertEquals(key, decryptedKey);
    }

    @Test
    public void decryptNoECNoCompression() throws Exception {
        String encryptedKey = "6PRVWUbkzzsbcVac2qwfssoUJAN1Xhrg6bNk8J7Nzm5H7kxEbn2Nh2ZoGg";
        String key = "5KN7MzqK5wt2TP1fQCYyHBtDrXdJuXbUzm4A9rKAteGu3Qi5CVR";
        String decryptedKey = BIP38.decrypt(testPass, encryptedKey);
        assertEquals(key, decryptedKey);

    }

    //round encrypt and decrypt with a random ascii password
    @Test
    public void randomRoundTripNoEC() throws Exception {
        byte[] r = new byte[16];
        (new Random()).nextBytes(r);
        String randomPass = new String(r, "ASCII");
        String key = "5KN7MzqK5wt2TP1fQCYyHBtDrXdJuXbUzm4A9rKAteGu3Qi5CVR";
        String encryptedKey = BIP38.encryptNoEC(key, randomPass, false);
        assertEquals(key, (BIP38.decrypt(randomPass, encryptedKey)));
    }

    //generate an encrypted key and make sure it looks ok.
    @Test
    public void generateEncryptedKey() throws Exception {
        String k = BIP38.generateEncryptedKey(testPass);
        String dk = BIP38.decrypt(testPass, k);
        assertEquals(dk.charAt(0), '5');
    }
}
