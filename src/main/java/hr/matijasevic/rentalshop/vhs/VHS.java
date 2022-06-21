package hr.matijasevic.rentalshop.vhs;

import hr.matijasevic.rentalshop.rental.Rental;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "vhs")
public class VHS implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String info;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Enumerated(EnumType.STRING)
    private Rating rating;
    private Integer runtime;
    private Float score;
    private Integer price;

    @OneToMany(mappedBy = "vhs", fetch = FetchType.EAGER)
    private List<Rental> rentals;

    public VHS(){

    }

    public VHS(Long id, String name, String info, Integer releaseYear, Rating rating, Integer runtime, Float score, Integer price) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.runtime = runtime;
        this.score = score;
        this.price = price;
    }

    public VHS(String name, String info, Integer releaseYear, Rating rating, Integer runtime, Float score, Integer price) {
        this.name = name;
        this.info = info;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.runtime = runtime;
        this.score = score;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }


    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VHS vhs = (VHS) o;
        return id.equals(vhs.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "VHS{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", releaseYear=" + releaseYear +
                ", rating=" + rating +
                ", runtime=" + runtime +
                ", score=" + score +
                ", price=" + price +
                ", rentals=" + rentals +
                '}';
    }
}
