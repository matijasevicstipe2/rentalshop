package hr.matijasevic.rentalshop.rental;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.sql.Date;

public class RentalCommand {

    @NotNull(message = "{user.id.null}")
    @Positive(message = "{user.id.positive}")
    private Long userId;

    @NotNull(message = "{vhs.id.null}")
    @Positive(message = "{vhs.id.positive}")
    private Long vhsId;

    @NotNull(message = "{rental.date.null}")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date rentalDate;

    @NotNull(message = "{return.date.null}")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date returnDate;

    @NotNull(message = "{status.null}")
    private Status status;


    public RentalCommand(Long userId, Long vhsId, Date rentalDate, Date returnDate, Status status) {
        this.userId = userId;
        this.vhsId = vhsId;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getVhsId() {
        return vhsId;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public Status getStatus() {
        return status;
    }
}
