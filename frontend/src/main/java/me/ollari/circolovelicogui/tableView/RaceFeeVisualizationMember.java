package me.ollari.circolovelicogui.tableView;

import me.ollari.circolovelicogui.rest.Boat;
import me.ollari.circolovelicogui.rest.Race;
import me.ollari.circolovelicogui.rest.RaceFee;

import java.time.LocalDate;

public class RaceFeeVisualizationMember {

    private Long memberId;
    private Long id;
    private String raceName;
    private String boatName;
    private LocalDate date;
    private LocalDate paymentDate;
    private Double raceFeePrice;


    public RaceFeeVisualizationMember(Long memberId, Race r, Boat b, RaceFee rf) {
        this.memberId = memberId;
        this.raceName = r.getName();
        this.boatName = b.getName();
        this.date = LocalDate.parse(r.getDate());
        this.paymentDate = LocalDate.parse(rf.getPaymentDate());
        this.raceFeePrice = rf.getPrice();
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    public String getBoatName() {
        return boatName;
    }

    public void setBoatName(String boatName) {
        this.boatName = boatName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getRaceFeePrice() {
        return raceFeePrice;
    }

    public void setRaceFeePrice(Double raceFeePrice) {
        this.raceFeePrice = raceFeePrice;
    }
}
