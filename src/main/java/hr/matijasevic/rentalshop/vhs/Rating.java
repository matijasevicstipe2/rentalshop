package hr.matijasevic.rentalshop.vhs;

public enum Rating {
    G("G"),
    PG("PG"),
    PG13("PG-13"),
    R("R"),
    NC17("NC-17");

    private final String rating;

    Rating(String rating) {
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }
}
