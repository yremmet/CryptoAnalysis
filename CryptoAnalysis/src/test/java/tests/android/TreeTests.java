package tests.android;

import android.app.Activity;
import android.widget.LinearLayout;
import org.junit.Test;
import test.UsagePatternTestingFramework;
import test.assertions.Assertions;

public class TreeTests  extends UsagePatternTestingFramework {
    @Test
    public void singleLevelInheritance(){
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
