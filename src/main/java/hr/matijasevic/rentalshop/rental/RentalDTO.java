package hr.matijasevic.rentalshop.rental;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
public class RentalDTO {

    private Long id;
    private Long userId;
    private Long vhsId;
    private Date rentalDate;
    private Date returnDate;
    private Integer paid;
    private Status status;
    private Integer lateFee;

    public RentalDTO(Long id, Long userId, Long vhsId, Date rentalDate, Date returnDate, Integer paid, Status status, Integer lateFee) {
        this.id = id;
        this.userId = userId;
        this.vhsId = vhsId;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.paid = paid;
        this.status = status;
        this.lateFee = lateFee;
    }
}
