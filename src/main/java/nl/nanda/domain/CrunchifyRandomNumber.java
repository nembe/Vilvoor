package nl.nanda.domain;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Generates unique random number for ID's.
 *
 */
public class CrunchifyRandomNumber {

    public static Integer generateRandomNumber() {
        // cryptographically strong random number generator. Options: NativePRNG
        // or SHA1PRNG
        SecureRandom crunchifyPRNG = null;
        Integer crunchifyRandomNumber = null;
        try {
            crunchifyPRNG = SecureRandom.getInstance("SHA1PRNG");
            // generate a random number
            crunchifyRandomNumber = new Integer(crunchifyPRNG.nextInt(1000000000) + 1);
        } catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return crunchifyRandomNumber;
    }
}
