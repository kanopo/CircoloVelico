package me.ollari.circolonauticogui.tableView;

import me.ollari.circolonauticogui.rest.Boat;
import me.ollari.circolonauticogui.rest.ParkingFee;

public class ParkingFeeVisualization {
    private String boatName;
    private String parkingFeeStart;
    private String parkingFeeEnd;
    private Double parkingFeePrice;

    public ParkingFeeVisualization(Boat b, ParkingFee pf) {
        this.boatName = b.getName();
        this.parkingFeeStart = pf.getStart();
        this.parkingFeeEnd = pf.getEnd();
        this.parkingFeePrice = pf.getPrice();

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


