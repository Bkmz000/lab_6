import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.spec.InvalidKeySpecException
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec


object PasswordUtils {

        fun isExpectedPassword(password: String, expectedHash: String): Boolean {
            val exp = expectedHash
            val pwdHash = getMd5(password)
            return exp == pwdHash
        }


    fun getMd5(input: String): String? {
        return try {

            // Static getInstance method is called with hashing MD5
            val md = MessageDigest.getInstance("MD5")

            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            val messageDigest = md.digest(input.toByteArray())

            // Convert byte array into signum representation
            val no = BigInteger(1, messageDigest)

            // Convert message digest into hex value
            var hashtext = no.toString(16)
            while (hashtext.length < 32) {
                hashtext = "0$hashtext"
            }
            hashtext
        } // For specifying wrong message digest algorithms
        catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }
    }

}