import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.Assert;

import java.security.Key;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/15 17:13 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public class MainTest {

    public static void main(String[] args) {
        String str = "hello";
        String salt = "123";
        String md5Hash = new Md5Hash(str, salt).toBase64();
        SimpleHash simpleHash = new SimpleHash("SHA-1", str, salt);
        System.out.println(md5Hash + " : " + simpleHash);

        DefaultHashService hashService = new DefaultHashService();//默认算法SHA-512
        hashService.setHashAlgorithmName("SHA-512");
        hashService.setPrivateSalt(new SimpleByteSource("123"));//私盐【散列时自动与用户传入的私盐混合产生一个新盐】，默认无
        hashService.setGeneratePublicSalt(true);//在用户没有传入公盐的情况下是否生成公盐，默认false
        hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());//用于生成公盐，此为默认
        hashService.setHashIterations(1);//生成hash值迭代次数，默认1

        HashRequest request = new HashRequest.Builder()
                .setAlgorithmName("MD5")
                .setSource(ByteSource.Util.bytes("hello"))
                .setSalt(ByteSource.Util.bytes("123"))
                .setIterations(2).build();
        String hex = hashService.computeHash(request).toHex();
        System.err.println(hex);

        SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        randomNumberGenerator.setSeed("123".getBytes());
        String randomHex = randomNumberGenerator.nextBytes().toHex();
        System.out.println(randomHex);

        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setKeySize(128);//设置key长度
        Key key = aesCipherService.generateNewKey();//生成key
        String text = "hello";
        //加密
        String encryptText = aesCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
        //解密
        String decryptText = new String(aesCipherService.decrypt(Hex.decode(encryptText), key.getEncoded()).getBytes());
        Assert.assertEquals(text, decryptText);

        String algorithmName = "md5";
        String username = "yang";
        String password = "123";
        String salt1 = username;
        String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
        int hashIterations = 2;
        SimpleHash simpleHash1 = new SimpleHash(algorithmName, password, salt1 + salt2, hashIterations);
        String encodedPassword = simpleHash1.toHex();
        System.err.println(salt2+" : "+encodedPassword);
    }

}
