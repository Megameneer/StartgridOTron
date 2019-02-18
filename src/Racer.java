public class Racer {
    private int startNumber;
    private String firstName;
    private String lastName;
    private RacingCategory racingCategory;

    Racer(int startNumber, String firstName, String lastName, RacingCategory racingCategory) {
        this.startNumber = startNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.racingCategory = racingCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var racer = (Racer) o;
        return racingCategory.equals(racer.racingCategory) && ((startNumber == racer.startNumber) ||
                        firstName.equals(racer.firstName) && lastName.equals(racer.lastName));
    }

    int getStartNumber() {
        return this.startNumber;
    }

    String getFirstName() {
        return this.firstName;
    }

    String getLastName() {
        return this.lastName;
    }

    RacingCategory getRacingCategory() {
        return racingCategory;
    }

    @Override
    public String toString() {
        return "Nummer " + startNumber + ": " + firstName + " " + lastName + " (" + racingCategory + ")";
    }
}