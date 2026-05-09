package core;

import java.util.Optional;

public interface Parser<I, O> {
    Optional<O> parse(I input);
}

