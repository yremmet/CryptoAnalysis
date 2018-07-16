package test;

import com.google.common.collect.Lists;
import crypto.analysis.ClassSpecification;
import crypto.rules.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockedUsagePatternTestingFramework extends UsagePatternTestingFramework {
    @Override
    protected List<CryptSLRule> getRules(){

        List<CryptSLRule> listOfMockedRules = Lists.newArrayList();

        listOfMockedRules.add(getMockedRule("java.lang.Object"));
        listOfMockedRules.add(getMockedRule("javax.crypto.spec.SecretKeySpec"));
        listOfMockedRules.add(getMockedRule("javax.crypto.spec.DHGenParameterSpec"));
        listOfMockedRules.add(getMockedRule("java.security.KeyStore"));
        listOfMockedRules.add(getMockedRule("javax.crypto.spec.GCMParameterSpec"));
        listOfMockedRules.add(getMockedRule("java.security.spec.DSAParameterSpec"));
        listOfMockedRules.add(getMockedRule("android.widget.TextView"));
        listOfMockedRules.add(getMockedRule("javax.crypto.spec.IvParameterSpec"));
        listOfMockedRules.add(getMockedRule("javax.crypto.KeyGenerator"));
        listOfMockedRules.add(getMockedRule("javax.crypto.KeyGenerator"));
        listOfMockedRules.add(getMockedRule("android.widget.TextView"));
        listOfMockedRules.add(getMockedRule("javax.crypto.SecretKey"));
        listOfMockedRules.add(getMockedRule("javax.crypto.SecretKeyFactory"));
        listOfMockedRules.add(getMockedRule("java.security.spec.DSAGenParameterSpec"));
        listOfMockedRules.add(getMockedRule("javax.crypto.spec.PBEKeySpec"));
        listOfMockedRules.add(getMockedRule("com.google.common.base.Stopwatch"));
        listOfMockedRules.add(getMockedRule("java.security.Signature"));
        listOfMockedRules.add(getMockedRule("javax.crypto.Mac"));
        listOfMockedRules.add(getMockedRule("java.security.spec.RSAKeyGenParameterSpec"));
        listOfMockedRules.add(getMockedRule("java.security.KeyPair"));
        listOfMockedRules.add(getMockedRule("javax.crypto.spec.PBEParameterSpec"));
        listOfMockedRules.add(getMockedRule("javax.xml.crypto.dsig.spec.HMACParameterSpec"));
        listOfMockedRules.add(getMockedRule("javax.crypto.spec.DHParameterSpec"));
        listOfMockedRules.add(getMockedRule("java.security.KeyPairGenerator"));
        listOfMockedRules.add(getMockedRule("javax.crypto.Cipher"));
        listOfMockedRules.add(getMockedRule("java.security.MessageDigest"));
        listOfMockedRules.add(getMockedRule("java.security.SecureRandom"));
        listOfMockedRules.add(getMockedRule("android.widget.Button"));
        listOfMockedRules.add(getMockedRule("java.io.File"));
        listOfMockedRules.add(getMockedRule("android.app.Activity"));
        listOfMockedRules.add(getMockedRule("java.security.AlgorithmParameters"));

        return listOfMockedRules;
    }

    private CryptSLRule getMockedRule(String className){
        // Mocked class
        CryptSLRule mockedRule = mock(CryptSLRule.class);
        // State machine graph is required to get the method name from the initial transition, creating a valid graph to
        // allow the creation of a class specification.
        StateMachineGraph smg = new StateMachineGraph();
        StateNode initNode = new StateNode("-1",true,false);
        smg.addNode(initNode);
        StateNode secondNode = new StateNode("0", false,true);
        smg.addNode(secondNode);
        // A list of methods is required to be passed to the edge.
        CryptSLMethod mockedCryptSLMethod = mock(CryptSLMethod.class);
        List<CryptSLMethod> listOfMethods = new ArrayList<>();
        listOfMethods.add(mockedCryptSLMethod);
        // Name of the method that will be mocked. wait() should always be available.
        when(mockedCryptSLMethod.getMethodName()).thenReturn(className + ".wait()");
        smg.addEdge(new TransitionEdge(listOfMethods,initNode,secondNode));
        // The usage pattern that will be mocked.
        when(mockedRule.getUsagePattern()).thenReturn(smg);
        // For debugging
        when(mockedRule.getClassName()).thenReturn(className);

        return mockedRule;
    }
}
