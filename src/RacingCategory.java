public class RacingCategory {
    private String name;

    RacingCategory(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RacingCategory)) return false;
        RacingCategory that = (RacingCategory) o;
        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
}