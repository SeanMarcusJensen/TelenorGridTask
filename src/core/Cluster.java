package core;

import java.util.List;

public record Cluster<T extends Number>(List<Point<T>> points, Direction direction) {
    public double product() {
        return points.stream()
                .mapToDouble(rp -> rp.value().doubleValue())
                .reduce(1.0, (a,b) -> a * b);
    }

    public List<List<Integer>> getIndices(Boolean zeroBased) {
        var first = startingPoint();
        var last = points.getLast();
        if (zeroBased) {
            return List.of(List.of(first.row(), first.column()), List.of(last.row(), last.column()));
        }
        return List.of(List.of(first.row() + 1, first.column() + 1), List.of(last.row() + 1, last.column() + 1));
    }

    public Point<T> startingPoint() {
        return points.getFirst();
    }
}
