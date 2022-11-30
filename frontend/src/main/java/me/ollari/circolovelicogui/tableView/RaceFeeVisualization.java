package me.ollari.circolovelicogui.tableView;

import java.time.LocalDate;

public class RaceFeeVisualization {

    private Long id;
    private String raceName;
    private String boatName;
    private LocalDate date;
    private LocalDate paymentDate;
    private Double raceFeePrice;



    public RaceFeeVisualization() {
    }

    public Long getId() {
        return id;
    }

    public String getRaceName() {
        return raceName;
    }

    public String getBoatName() {
        return boatName;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public Double getRaceFeePrice() {
        return raceFeePrice;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    public void setBoatName(String boatName) {
        this.boatName = boatName;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setRaceFeePrice(Double raceFeePrice) {
        this.raceFeePrice = raceFeePrice;
    }
}
