package tests.android;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import org.junit.Test;
import test.UsagePatternTestingFramework;

public class ButtonTests extends UsagePatternTestingFramework{

    @Test
    public void buttonTest(){
        Activity context = new Activity();
        LinearLayout layout = new LinearLayout(context);

        Button button1 = new Button(context);
        String name = "rajiv";
        button1.setText(name);
        //button1.setId(20);

        layout.addView(button1);

        context.setContentView(layout);

    }

    @Test
    public void buttonRidTest(){
        Activity context = new Activity();
        Button button = new Button(context);
        int customResId = 1000;
        button.setId(customResId);
    }

    @Test
    public void buttonSetText(){
        Activity context = new Activity();
        Button button = new Button(context);
        int textResID = 1001;
        button.setText(textResID);
    }

    @Test
    public void buttonSetText2(){
        Activity context = new Activity();
        Button button = new Button(context);
        String text = "button test rajiv";
        button.setText(text);
    }

    @Test
    public void buttonSetText3(){
        Activity context = new Activity();
        Button button = new Button(context);
        String text = "button test rajiv";
        int begin = 0;
        int end = 5;
        button.setText(text.toCharArray(), begin, end);
    }

    @Test void buttonListeners(){
        Activity context = new Activity();
        Button button = new Button(context);
        button.setOnClickListener((View view) -> {});
        button.setOnFocusChangeListener((View view, boolean b) -> {});
        button.setOnKeyListener((View view, int i, KeyEvent keyEvent) -> false);
        button.setOnTouchListener((View view, MotionEvent motionEvent) -> false);
    }
}
