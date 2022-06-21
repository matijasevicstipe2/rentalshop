package hr.matijasevic.rentalshop.vhs;

import lombok.Data;

import java.io.Serializable;

@Data
public class VHSDTO implements Serializable {

    private Long id;
    private String name;
    private String info;
    private Integer releaseYear;
    private Rating rating;
    private Integer runtime;
    private Float score;
    private Integer price;

    public VHSDTO(Long id, String name, String info, Integer releaseYear, Rating rating, Integer runtime, Float score, Integer price) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.runtime = runtime;
        this.score = score;
        this.price = price;
    }
}
