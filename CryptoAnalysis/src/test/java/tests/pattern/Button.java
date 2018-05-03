package tests.pattern;

public class Button extends View{
    private String label;

    public Button(Context context) {
        super();
    }

    public Button() {

    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
