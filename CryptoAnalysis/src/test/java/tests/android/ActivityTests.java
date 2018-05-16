package tests.android;

import android.app.Activity;
import android.content.Intent;
import android.widget.LinearLayout;
import org.junit.Test;
import test.UsagePatternTestingFramework;


public class ActivityTests  extends UsagePatternTestingFramework {

    @Test
    public void activityTitleTest(){
        Activity context = new Activity();
        int titleResId = 10;
        context.setTitle(titleResId);
    }

    @Test
    public void activityTitleTest2(){
        Activity context = new Activity();
        String title = "rajiv";
        context.setTitle(title);
    }

    @Test
    public void activityContentViewTest(){
        Activity context = new Activity();
        LinearLayout layout = new LinearLayout(context);
        context.setContentView(layout);
    }

    @Test
    public void activityContextViewTest2(){
        Activity context = new Activity();
        int layoutResId = 20;
        context.setContentView(layoutResId);
    }

    @Test
    public void activityFindViewByIdTest(){
        Activity context = new Activity();
        int viewResId = 30;
        context.findViewById(viewResId);
    }

    @Test
    public void activityTest(){
        Activity context = new Activity();
        Intent intent = new Intent();
        context.setIntent(intent);
    }
}
