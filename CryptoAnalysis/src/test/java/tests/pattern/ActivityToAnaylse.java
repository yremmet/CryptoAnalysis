package tests.pattern;

public class ActivityToAnaylse extends Activity {

    private Button button1;
    private Button button2;
    private Button button3;
    @Override
    public void onCreate() {
        //super.onCreate();
        setContentView(R.layout.activity);

        LinearLayout ll = new LinearLayout(this);

        button1 = (Button) findViewById(R.button1);

        ll.addView(button1);

        button1.setLabel("1");
        //Assertions.extValue(0);
        button2 = (Button) findViewById(R.button2);
        ll.addView(button2);
        button2.setLabel("2");

        button3 = new Button(this);
        ll.addView(button3);
        button3.setLabel("3");

        setContentView(ll);
    }

    public void test(){

    }

}
