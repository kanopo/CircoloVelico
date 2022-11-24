package me.ollari.circolovelicogui.tableView;

import me.ollari.circolovelicogui.rest.RaceFee;

public class RaceFeeVisualization {
    private final Long raceFeeId;
    private final Long raceId;
    private final String raceName;
    private final Long boatId;
    private final String boatName;
    private final String raceDate;
    private final double raceFeePrice;

    private final String raceFeePaymentDate;
    private final String racePrepaymentMethod;

    private final Long memberId;

    private final String memberUsername;


    /**
     * @param raceFee
     */
    public RaceFeeVisualization(RaceFee raceFee) {
        this.raceFeeId = raceFee.getId();

        this.memberId = raceFee.getMember().getId();
        this.memberUsername = raceFee.getMember().getUsername();


        this.raceId = raceFee.getRace().getId();
        this.raceName = raceFee.getRace().getName();
        this.raceDate = raceFee.getRace().getDate();

        this.boatId = raceFee.getBoat().getId();
        this.boatName = raceFee.getBoat().getName();

        this.raceFeePrice = raceFee.getPrice();
        this.raceFeePaymentDate = raceFee.getPaymentDate();
        this.racePrepaymentMethod = raceFee.getPaymentMethod();


    }

    public Long getRaceFeeId() {
        return raceFeeId;
    }

    public Long getRaceId() {
        return raceId;
    }

    public String getRaceDate() {
        return raceDate;
    }

    public String getRaceName() {
        return raceName;
    }

    public Long getBoatId() {
        return boatId;
    }

    public String getBoatName() {
        return boatName;
    }

    public double getRaceFeePrice() {
        return raceFeePrice;
    }

    public String getRaceFeePaymentDate() {
        return raceFeePaymentDate;
    }

    public String getRacePrepaymentMethod() {
        return racePrepaymentMethod;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getMemberUsername() {
        return memberUsername;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\traceFeeId: " + raceFeeId + "\n" +
                "\tmemberId: " + memberId + "\n" +
                "\tmemberUsername: " + memberUsername + "\n" +
                "\traceId: " + raceId + "\n" +
                "\traceName: " + raceName + "\n" +
                "\traceDate: " + raceDate + "\n" +
                "\tboatId: " + boatId + "\n" +
                "\tboatName: " + boatName + "\n" +
                "\traceFeePrice: " + raceFeePrice + "\n" +
                "\traceFeePaymentDate: " + raceFeePaymentDate + "\n" +
                "}";
    }

}
