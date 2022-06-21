package hr.matijasevic.rentalshop.vhs;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;

public class VHSCommand {
    @NotEmpty(message = "{vhs.name.null}")
    private String name;

    @NotEmpty(message = "{vhs.info.null}")
    private String info;

    @NotNull(message = "{vhs.release_year.null}")
    @Min(value = 1930,message = "{vhs.release_year.min.message}")
    private Integer releaseYear;

    @NotNull(message = "{vhs.rating.null}")
    private Rating rating;

    @NotNull(message = "{vhs.runtime.null}")
    @Positive(message = "{vhs.runtime.positive}")
    private Integer runtime;

    @NotNull(message = "{vhs.score.null}")
    @Min(value = 1,message = "{vhs.score.min.message}")
    @Max(value = 10,message = "{vhs.score.max.message}")
    private Float score;

    @NotNull(message = "{vhs.price.null}")
    @Positive(message = "{vhs.price.positive}")
    private Integer price;

    public VHSCommand(String name, String info, Integer releaseYear, Rating rating, Integer runtime, Float score, Integer price) {
        this.name = name;
        this.info = info;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.runtime = runtime;
        this.score = score;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public Rating getRating() {
        return rating;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public Float getScore() {
        return score;
    }

    public Integer getPrice() {
        return price;
    }
}
