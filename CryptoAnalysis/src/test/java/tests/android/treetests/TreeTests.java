package tests.android.treetests;

import android.app.Activity;
import android.widget.LinearLayout;
import org.junit.Test;
import test.MockedUsagePatternTestingFramework;
import test.UsagePatternTestingFramework;
import test.assertions.Assertions;
import tests.android.Inher_Button;

public class TreeTests  extends MockedUsagePatternTestingFramework {
    @Test
    public void singleLevelInheritance(){
        Assertions.classesToMock("java.lang.Object,javax.crypto.spec.SecretKeySpec");

        Activity context = new Activity();
        LinearLayout layout = new LinearLayout(context);
        //Assertions.extValue(0);
        Inher_Button button1 = new Inher_Button(context);
        //Assertions.extValue(0);
        String name = "rajiv";
        button1.setText(name);
        Assertions.extValue(0);
        //button1.setId(20);

        layout.addView(button1);
        //Assertions.extValue(0);

        context.setContentView(layout);
        //Assertions.extValue(0);
    }
}
