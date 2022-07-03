package spw4.game2048;

import java.util.*;

public class RandomGeneratorStub extends RandomGenerator {
  private final Iterator<Integer> iterator;

  public RandomGeneratorStub(Iterable<Integer> values) {
    this.iterator = values.iterator();
  }

  public int getRandomIndex() {
    return iterator.next();
  }

  public int getTileValue() {
    return 2;
  }
}
