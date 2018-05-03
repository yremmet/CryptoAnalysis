package tests.pattern;

public class LinearLayoutManager {
    private AppCompatActivity appCompatActivity;
    private boolean reverse;
    private int orientation;

    public LinearLayoutManager(AppCompatActivity appCompatActivity, boolean reverse, int orientation) {
        this.appCompatActivity = appCompatActivity;
        this.reverse = reverse;
        this.orientation = orientation;
    }
}
