package me.ollari.CVbackend.RaceFee;

import java.time.LocalDate;


public class RaceFeeVisualization {

    private Long raceFeeId;
    private Long memberId;
    private Long raceId;
    private Long boatId;
    private Double raceFeePrice;
    private LocalDate raceFeePaymentDate;


    public RaceFeeVisualization(RaceFee rf) {
        this.raceFeeId = rf.getId();
        this.memberId = rf.getMembersRaceFee().getId();
        this.boatId = rf.getBoatsRaceFee().getId();
        this.raceId = rf.getRacesRaceFee().getId();
        this.raceFeePrice = rf.getPrice();
        this.raceFeePaymentDate = rf.getPaymentDate();
    }

    public Long getRaceFeeId() {
        return raceFeeId;
    }

    public void setRaceFeeId(Long raceFeeId) {
        this.raceFeeId = raceFeeId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getRaceId() {
        return raceId;
    }

    public void setRaceId(Long raceId) {
        this.raceId = raceId;
    }

    public Long getBoatId() {
        return boatId;
    }

    public void setBoatId(Long boatId) {
        this.boatId = boatId;
    }

    public Double getRaceFeePrice() {
        return raceFeePrice;
    }

    public void setRaceFeePrice(Double raceFeePrice) {
        this.raceFeePrice = raceFeePrice;
    }

    public LocalDate getRaceFeePaymentDate() {
        return raceFeePaymentDate;
    }

    public void setRaceFeePaymentDate(LocalDate raceFeePaymentDate) {
        this.raceFeePaymentDate = raceFeePaymentDate;
    }
}
