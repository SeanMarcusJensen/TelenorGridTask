package core;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

public class GridParser<T extends Number> implements Parser<List<String>, Grid<Point<T>>> {

    private final Function<String, T> converter;
    private final String regex;

    public GridParser(Function<String, T> converter, String regex) {
        this.converter = converter;
        this.regex = regex;
    }

    public GridParser(Function<String, T> converter) {
        this(converter, "\\s+");
    }

    public static <T extends Number> GridParser<T> of(Function<String, T> converter) {
        return new GridParser<>(converter);
    }

    @Override
    public Optional<Grid<Point<T>>> parse(List<String> input) {
        try {
            var cells = IntStream.range(0, input.size())
                    .mapToObj(rowIndex -> getPointsFromRow(input.get(rowIndex), rowIndex))
                    .toList();

            return Optional.of(new Grid<>(cells));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private List<Point<T>> getPointsFromRow(String input, int rowIndex) {
        var rowPoints = input.split(regex);
        return IntStream.range(0, rowPoints.length)
                .mapToObj(columnIndex -> {
                    var value = converter.apply(rowPoints[columnIndex]);
                    return new Point<>(value, columnIndex, rowIndex);
                })
                .toList();

    }
}
