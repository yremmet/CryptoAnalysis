package tests.pattern;

import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;
import java.security.GeneralSecurityException;

public class UsagePatternTestRajiv extends Activity {
//    private Button button1;
//    private Button button2;
//    private Button button3;
    @Test
    public void testCipherKey() throws GeneralSecurityException {
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        //Assertions.extValue(0);
        keygen.init(128);
        //Assertions.extValue(0);
        SecretKey key = keygen.generateKey();
        //Assertions.hasEnsuredPredicate(key);
        //Assertions.mustBeInAcceptingState(keygen);
        Cipher cCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        //Assertions.extValue(0);
        cCipher.init(Cipher.ENCRYPT_MODE, key);

        //Assertions.extValue(0);
        byte[] encText = cCipher.doFinal("".getBytes());
        //Assertions.hasEnsuredPredicate(encText);
        //Assertions.mustBeInAcceptingState(cCipher);
        cCipher.getIV();
    }
//    @Test
//    public void onCreate() {
//        //setContentView(R.layout.activity);
//
//        LinearLayout ll = new LinearLayout(this);
//        //Assertions.extValue(0);
//
//        Button button1;
//        button1 = new Button();
//        ll.addView(button1);
//        //Assertions.extValue(0);
//        button1.setLabel("1");
//        Assertions.extValue(0);
//        //Assertions.hasEnsuredPredicate(button1);
//
//        Button button2;
//        button2 = new Button();
//        ll.addView(button2);
//        //Assertions.extValue(0);
//        button2.setLabel("2");
//        Assertions.extValue(0);
//        //Assertions.hasEnsuredPredicate(button2);
//
//
//        Button button3;
//        button3 = new Button();
//        ll.addView(button3);
//        //Assertions.extValue(0);
//        button3.setLabel("3");
//        Assertions.extValue(0);
//       // Assertions.hasEnsuredPredicate(button3);
//        //Assertions.mustBeInAcceptingState(keygen);
//        setContentView(ll);
//        //Assertions.extValue(0);
//    }
    @Test
    public void testButton(){

        Button button3;
        button3 = new Button();
        String label = "1";
        if (label == "1"){
            button3.setLabel(label);
            //Assertions.extValue(0);
        }

    }

    @Test
    public void testFile(){
        File testFile = new File("");
        boolean fileExists = testFile.exists();
        File newFile = new File("");
        testFile.renameTo(newFile);
    }
}
