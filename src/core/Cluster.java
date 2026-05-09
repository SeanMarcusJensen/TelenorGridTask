package core;

import java.util.List;

public record Cluster<T extends Number>(List<Point<T>> points, Direction direction) {
    public double calculateProduct() {
        return points.stream()
                .mapToDouble(rp -> rp.value().doubleValue())
                .reduce(1.0, (a,b) -> a * b);

    }
}
