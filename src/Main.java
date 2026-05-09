import core.Cluster;
import core.Direction;
import core.ClusterFinder;
import core.GridParser;

void main() {
    var GRID_FILEPATH = Path.of("data/grid3.txt");
    var SEARCH_DIRECTIONS = List.of(Direction.RIGHT, Direction.DOWN, Direction.DOWN_LEFT, Direction.DOWN_RIGHT);
    var CONSECUTIVE_POINTS = 4;
    var STEP_COUNT_IN_SEARCH = 1;

    var grid = readLines(GRID_FILEPATH)
            .flatMap(lines -> GridParser.of(Integer::parseInt).parse(lines))
            .orElseThrow(() -> new RuntimeException("Could not parse the grid file properly."));

    var maxCluster= ClusterFinder.from(grid)
            .withConsecutivePoints(CONSECUTIVE_POINTS)
            .withDirections(SEARCH_DIRECTIONS)
            .withStepCount(STEP_COUNT_IN_SEARCH)
            .identifyClusters()
            .max(Comparator.comparingDouble(Cluster::product))
            .orElseThrow(() -> new RuntimeException("Could not find cluster"));


    for (var point : maxCluster.points()) {
        IO.println(String.format("Point[%d, %d] @ %s", point.column() + 1, point.row() + 1, point.value()));
    }

    var indices = maxCluster.points().stream()
            .map(p -> List.of(p.column(), p.row()))
            .toList();

    IO.println(String.format("The maximum cluster holds the product of %.0f in direction %s with the indices %s",
            maxCluster.product(), maxCluster.direction(), indices));
}

private Optional<List<String>> readLines(Path path) {
    try {
        return Optional.of(Files.readAllLines(path));
    } catch (IOException e) {
        return Optional.empty();
    }
}