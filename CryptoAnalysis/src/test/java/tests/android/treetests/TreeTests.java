package tests.android.treetests;

import org.junit.Test;
import test.MockedUsagePatternTestingFramework;
import test.assertions.Assertions;

public class TreeTests  extends MockedUsagePatternTestingFramework {

    @Test
    public void singleLevelInheritance(){
        Assertions.classesToMock("java.lang.Object,java.io.File");
    }

    @Test
    // TODO see why this fails.
    public void singleLevelInheritanceMinusObject(){
        Assertions.classesToMock("java.io.File,tests.android.treetests.Inher_File");
    }

    @Test
    public void twoLevelInheritance(){
        Assertions.classesToMock("java.lang.Object,java.io.File,tests.android.treetests.Inher_File");
    }

    @Test
    public void linearInheritanceObjectNotFirst(){
        Assertions.classesToMock("java.io.File,java.lang.Object,tests.android.treetests.Inher_File");
    }

    @Test
    public void subsetOfAvailableRules(){
        Assertions.classesToMock("java.lang.Object,javax.crypto.spec.SecretKeySpec,javax.crypto.spec.DHGenParameterSpec,java.security.KeyStore," +
                "javax.crypto.spec.GCMParameterSpec,java.security.spec.DSAParameterSpec,android.widget.TextView," +
                "javax.crypto.spec.IvParameterSpec,javax.crypto.KeyGenerator,javax.crypto.KeyGenerator,android.widget.TextView," +
                "javax.crypto.SecretKey,javax.crypto.SecretKeyFactory,java.security.spec.DSAGenParameterSpec," +
                "javax.crypto.spec.PBEKeySpec,com.google.common.base.Stopwatch,java.security.Signature,javax.crypto.Mac," +
                "java.security.spec.RSAKeyGenParameterSpec,java.security.KeyPair,javax.crypto.spec.PBEParameterSpec," +
                "javax.xml.crypto.dsig.spec.HMACParameterSpec,javax.crypto.spec.DHParameterSpec,java.security.KeyPairGenerator," +
                "javax.crypto.Cipher,java.security.MessageDigest,java.security.SecureRandom,android.widget.Button," +
                "java.io.File,android.app.Activity,java.security.AlgorithmParameters");
    }

    /*
        Following tests are based on the this class hierarchy:
                    Object
                  /        \
                One         Two
               /   \       /   \
           Three  Four  Five  Six
                 /  \          |
             Seven   Eight    Nine

     */

    @Test
    // Classes placed according to the defined hierarchy
    public void baseHierarchy(){
        Assertions.classesToMock("java.lang.Object,tests.android.treetests.One,tests.android.treetests.Three,tests.android.treetests.Four," +
                "tests.android.treetests.Seven,tests.android.treetests.Eight,tests.android.treetests.Two,tests.android.treetests.Five," +
                "tests.android.treetests.Six,tests.android.treetests.Nine");
    }

    @Test
    // Classes placed according to the numerical sequence
    public void baseHierarchyMixed(){
        Assertions.classesToMock("java.lang.Object,tests.android.treetests.One,tests.android.treetests.Two,tests.android.treetests.Three," +
                "tests.android.treetests.Four,tests.android.treetests.Five,tests.android.treetests.Six,tests.android.treetests.Seven," +
                "tests.android.treetests.Eight,tests.android.treetests.Nine");
    }

    @Test
    // Exchanged six and nine
    public void exchangeAtLeafNode(){
        Assertions.classesToMock("java.lang.Object,tests.android.treetests.One,tests.android.treetests.Three,tests.android.treetests.Four," +
                "tests.android.treetests.Seven,tests.android.treetests.Eight,tests.android.treetests.Two,tests.android.treetests.Five," +
                "tests.android.treetests.Nine,tests.android.treetests.Six");
    }

    @Test
    // Removed three, moved one after four
    public void exchangeWithinTree(){
        Assertions.classesToMock("java.lang.Object,tests.android.treetests.Four,tests.android.treetests.One," +
                "tests.android.treetests.Seven,tests.android.treetests.Eight,tests.android.treetests.Two,tests.android.treetests.Five," +
                "tests.android.treetests.Six,tests.android.treetests.Nine");
    }

    @Test
    // Keeping three, moved one after four
    // TODO invalid dot file
    public void exchangeWithinTree2(){
        Assertions.classesToMock("java.lang.Object,tests.android.treetests.Three,tests.android.treetests.Four,tests.android.treetests.One," +
                "tests.android.treetests.Seven,tests.android.treetests.Eight,tests.android.treetests.Two,tests.android.treetests.Five," +
                "tests.android.treetests.Six,tests.android.treetests.Nine");
    }

    @Test
    // Object is the last in the list.
    public void baseHierarchyObjectLast(){
        Assertions.classesToMock("tests.android.treetests.One,tests.android.treetests.Three,tests.android.treetests.Four," +
                "tests.android.treetests.Seven,tests.android.treetests.Eight,tests.android.treetests.Two,tests.android.treetests.Five," +
                "tests.android.treetests.Six,tests.android.treetests.Nine,java.lang.Object");
    }
}
