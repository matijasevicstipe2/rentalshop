package hr.matijasevic.rentalshop.rental;

public enum Status {
    OUT("OUT"),
    RETURNED("RETURNED"),
    LATE("LATE");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
