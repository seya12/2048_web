package spw4.game2048;

import java.util.concurrent.*;

public class RandomGenerator {

  public int getRandomIndex(){
    return ThreadLocalRandom.current().nextInt(0, 4);
  }

  public int getTileValue(){
    int random = ThreadLocalRandom.current().nextInt(0, 100);

    return random >= 90 ? 4 : 2;
  }
}
