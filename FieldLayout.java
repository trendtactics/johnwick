package pcflayout;

public class FieldLayout {
    private int startPosition;
    private int length;
    private String name;

    public FieldLayout(int startPosition, int length, String name) {
        this.startPosition = startPosition;
        this.length = length;
        this.name = name;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }
}
