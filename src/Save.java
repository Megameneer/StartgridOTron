import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Save {
    private File location;
    private LinkedHashSet<RacingCategory> racingCategories;
    private LinkedHashSet<Racer> racers;
    private ArrayList<ArrayList<RacingCategory>> manches;

    Save () {
        this(new LinkedHashSet<>(), new LinkedHashSet<>(), new ArrayList<>());
    }

    private Save(
            LinkedHashSet<RacingCategory> racingCategories,
            LinkedHashSet<Racer> racers,
            ArrayList<ArrayList<RacingCategory>> manches
    ) {
        this(null, racingCategories, racers, manches);
    }

    Save(
            File location,
            LinkedHashSet<RacingCategory> racingCategories,
            LinkedHashSet<Racer> racers,
            ArrayList<ArrayList<RacingCategory>> manches
    ) {
        this.location = location;
        this.racingCategories = racingCategories;
        this.racers = racers;
        this.manches = manches;
    }

    File getLocation() {
        return location;
    }

    void setLocation(File location) {
        this.location = location;
    }

    LinkedHashSet<RacingCategory> getRacingCategories() {
        return racingCategories;
    }

    LinkedHashSet<Racer> getRacers() {
        return racers;
    }

    public void addRacingClass(RacingCategory racingCategory) {
        racingCategories.add(racingCategory);
    }

    public void addRacer(Racer racer) {
        racers.add(racer);
    }

    void removeRacer(Racer racer) {
        racers.remove(racer);
    }

    public ArrayList<ArrayList<RacingCategory>> getManches() {
        return manches;
    }

    public void setManches(ArrayList<ArrayList<RacingCategory>> manches) {
        this.manches = manches;
    }

    void addHeat(RacingCategory racingCategory) {
        manches.forEach(manche -> manche.add(racingCategory));
    }
}