package core;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public record Grid<T>(List<List<T>> grid) implements Iterable<T> {
    public int getRowCount() { return grid.size(); }
    public int getColumnCount() { return grid.getFirst().size(); }
    public T getCell(int row, int column) throws IndexOutOfBoundsException { return grid.get(row).get(column); }

    public Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    @Override
    public Iterator<T> iterator() {
        return new GridIterator();
    }

    private class GridIterator implements Iterator<T> {
        private int currentRow = 0;
        private int currentColumn = 0;

        public boolean hasNext() {
            return currentRow < getRowCount();
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();

            var cell = getCell(currentRow, currentColumn);

            currentColumn++;
            if (currentColumn >= getColumnCount()) {
                currentColumn = 0;
                currentRow++;
            }

            return cell;
        }
    }
}
