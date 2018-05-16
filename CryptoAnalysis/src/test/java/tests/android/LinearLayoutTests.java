package tests.android;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import org.junit.Test;

public class LinearLayoutTests {

    @Test
    public void linearLayoutAddViewTest(){
        Activity context = new Activity();
        LinearLayout layout = new LinearLayout(context);
        Button button = new Button(context);
        layout.addView(button);

    }
    @Test
    public void linearLayoutAddViewTest2(){
        Activity context = new Activity();
        LinearLayout layout = new LinearLayout(context);
        Button button = new Button(context);
        int position = 40;
        layout.addView(button, position);
    }

    @Test
    public void linearLayoutAddViewTest3(){
        Activity context = new Activity();
        LinearLayout layout = new LinearLayout(context);
        Button button = new Button(context);
        int width = 200;
        int height = 300;
        layout.addView(button, width, height);
    }

    @Test
    public void linearLayoutSetCustomResId(){
        Activity context = new Activity();
        LinearLayout layout = new LinearLayout(context);
        int customResId = 500;
        layout.setId(customResId);
    }

    @Test void linearLayoutListeners(){
        Activity context = new Activity();
        LinearLayout layout = new LinearLayout(context);
        layout.setOnClickListener((View view) -> {});
        layout.setOnFocusChangeListener((View view, boolean b) -> {});
        layout.setOnKeyListener((View view, int i, KeyEvent keyEvent) -> false);
        layout.setOnTouchListener((View view, MotionEvent motionEvent) -> false);
    }
}
