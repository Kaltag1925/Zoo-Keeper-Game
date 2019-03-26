package CustomSwing;

public class LabelResizeWithNumber extends LabelResize {
    private int integer;

    public LabelResizeWithNumber(String name, String text, int horizontalAlignment, double textScale, int integer) {
        super(name, text, horizontalAlignment, textScale);
        this.integer = integer;
    }

    public LabelResizeWithNumber(String text, int horizontalAlignment, double textScale, int integer) {
        super(text, horizontalAlignment, textScale);
        this.integer = integer;
    }

    public int getInteger(){
        return integer;
    }
}
