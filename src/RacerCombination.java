import java.util.Arrays;

public class RacerCombination {
    private int amountOfTimesRacedTogether;
    private Racer[] racers;

    public RacerCombination(Object... racerObjects) {
        this((Racer[]) racerObjects);
    }

    public RacerCombination(Racer... racers) {
        this.racers = racers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RacerCombination)) return false;
        RacerCombination that = (RacerCombination) o;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(racers, that.racers);
    }

    @Override
    public int hashCode() {
        return racers.hashCode();
    }

    int getAmountOfTimesRacedTogether() {
        return amountOfTimesRacedTogether;
    }

    void incrementAmountOfTimesRacedTogether() {
        amountOfTimesRacedTogether++;
    }

    public Racer[] getRacers() {
        return racers;
    }
}