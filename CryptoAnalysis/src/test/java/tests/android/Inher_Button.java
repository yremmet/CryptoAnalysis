package tests.android;

import android.content.Context;
import android.widget.Button;

public class Inher_Button extends Button{
    public Inher_Button(Context context) {
        super(context);
    }

    public void setSomeText(){
        this.setText(0);
    }
}