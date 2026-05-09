package core;

public record Point<I extends Number>(I value, int column, int row) {
    public Point {
        if (column < 0 || row < 0) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        return String.format("(%d, %d, @%s)", column, row, value);
    }
}
