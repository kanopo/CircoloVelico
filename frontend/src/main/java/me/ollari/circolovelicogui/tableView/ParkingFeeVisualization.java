package me.ollari.circolovelicogui.tableView;

import me.ollari.circolovelicogui.rest.Boat;
import me.ollari.circolovelicogui.rest.ParkingFee;

public class ParkingFeeVisualization {
    private String boatName;
    private Long boatId;
    private Long memberId;
    private String parkingFeeStart;
    private String parkingFeeEnd;
    private Double parkingFeePrice;

    public ParkingFeeVisualization(Long memberId, Boat b, ParkingFee pf) {
        this.boatName = b.getName();
        this.parkingFeeStart = pf.getStart();
        this.parkingFeeEnd = pf.getEnd();
        this.parkingFeePrice = pf.getPrice();
        this.boatId = b.getId();
        this.memberId = memberId;

    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getBoatId() {
        return boatId;
    }

    public void setBoatId(Long boatId) {
        this.boatId = boatId;
    }

    public String getBoatName() {
        return boatName;
    }

    public void setBoatName(String boatName) {
        this.boatName = boatName;
    }

    public String getParkingFeeStart() {
        return parkingFeeStart;
    }

    public void setParkingFeeStart(String parkingFeeStart) {
        this.parkingFeeStart = parkingFeeStart;
    }

    public String getParkingFeeEnd() {
        return parkingFeeEnd;
    }

    public void setParkingFeeEnd(String parkingFeeEnd) {
        this.parkingFeeEnd = parkingFeeEnd;
    }

    public Double getParkingFeePrice() {
        return parkingFeePrice;
    }

    public void setParkingFeePrice(Double parkingFeePrice) {
        this.parkingFeePrice = parkingFeePrice;
    }
}


