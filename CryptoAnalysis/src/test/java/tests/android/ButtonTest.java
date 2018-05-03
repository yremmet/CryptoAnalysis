package tests.android;

import android.app.Activity;
import android.widget.Button;
import android.widget.LinearLayout;
import org.junit.Test;
import test.UsagePatternTestingFramework;

public class ButtonTest extends UsagePatternTestingFramework{

    @Test
    public void buttonTest(){
        Activity context = new Activity();
        LinearLayout layout = new LinearLayout(context);
        Button button1 = new Button(context);
        button1.setText(10);
        button1.setId(20);

        layout.addView(button1);

        context.setContentView(layout);

    }
}
