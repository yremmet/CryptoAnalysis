package tests.pattern;

public class Activity extends Context {
    public void startActivity(Intent var1) {

    }
    public void onCreate() {

    }

    protected void onStart() {

    }

    protected void onRestart() {

    }

    protected void onResume() {

    }

    protected void onPause() {

    }

    protected void onStop() {

    }

    protected void onDestroy() {

    }

    public void setContentView(int layoutResId){

    }
    public View findViewById(int viewId){
        switch (viewId){
            case 1: return new Button();
            case 2: return new Button();
            case 3: break;
            case 4: break;
        }
        return new Button();
    }


    public void setContentView(View ll) {
    }
}
