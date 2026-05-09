package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ClusterFinder<T extends Number & Comparable<T>> {

    private final Grid<Point<T>> grid;
    private int consecutivePoints = 4;
    private List<Direction> directions = List.of(Direction.values());
    private int stepCount = 1;

    private ClusterFinder(Grid<Point<T>> grid) {
        this.grid = grid;
    }

    public static <T extends Number & Comparable<T>> ClusterFinder<T> from(Grid<Point<T>> grid) {
        return new ClusterFinder<>(grid);
    }

    public ClusterFinder<T> withConsecutivePoints(int consecutivePoints) {
        this.consecutivePoints = consecutivePoints;
        return this;
    }

    public ClusterFinder<T> withDirections(List<Direction> directions) {
        this.directions = directions;
        return this;
    }

    public ClusterFinder<T> withStepCount(int stepCount) {
        this.stepCount = stepCount;
        return this;
    }

    public Stream<Cluster<T>> identifyClusters(Predicate<Cluster<T>> predicate) {
        return grid.stream()
                .flatMap(cell -> directions.stream()
                        .flatMap(direction -> ray(cell, direction).stream()))
                .filter(predicate);
    }

    public Stream<Cluster<T>> identifyClusters() {
        return identifyClusters(it -> true);
    }

    Optional<Cluster<T>> ray(Point<T> currentPoint, Direction direction) {
        var currentConsecutivePoints = 1;
        List<Point<T>> currentPoints = new ArrayList<>();
        currentPoints.add(currentPoint);

        while (currentConsecutivePoints < consecutivePoints) {
            currentConsecutivePoints += 1;
            var nextPoint = step(currentPoint, direction);
            if (nextPoint.isEmpty()) {
                return Optional.empty();
            }
            currentPoint =  nextPoint.get();
            currentPoints.add(currentPoint);
        }

        return Optional.of(new Cluster<>(currentPoints, direction));
    }

    Optional<Point<T>> step(Point<T> from, Direction direction) {
        var column = from.column() + (direction.directionX * stepCount);
        var row = from.row() + (direction.directionY * stepCount);
        if (row < 0 || row >= grid.getRowCount() || column < 0 || column >= grid.getColumnCount()) {
            return Optional.empty();
        }
        return Optional.of(grid.getCell(row, column));
    }
}
