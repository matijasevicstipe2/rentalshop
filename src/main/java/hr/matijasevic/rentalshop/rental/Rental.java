package hr.matijasevic.rentalshop.rental;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hr.matijasevic.rentalshop.security.domain.User;
import hr.matijasevic.rentalshop.vhs.VHS;

import javax.persistence.*;
import java.io.Serializable;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "rental")
public class Rental implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rental_date")
    private Date rentalDate;

    @Column(name = "return_date")
    private Date returnDate;

    private Integer paid;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "vhs_id")
    @JsonIgnore
    private VHS vhs;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Rental(){

    }

    public Rental(Date rentalDate, Date returnDate, Integer paid, Status status, VHS vhs, User user) {
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.paid = paid;
        this.status = status;
        this.vhs = vhs;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public VHS getVhs() {
        return vhs;
    }

    public void setVhs(VHS vhs) {
        this.vhs = vhs;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rental rental = (Rental) o;
        return id.equals(rental.id) && rentalDate.equals(rental.rentalDate) && returnDate.equals(rental.returnDate) && paid.equals(rental.paid) && status == rental.status && vhs.equals(rental.vhs) && user.equals(rental.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rentalDate, returnDate, paid, status, vhs, user);
    }
}
