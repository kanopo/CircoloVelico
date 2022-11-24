package me.ollari.circolonauticogui.tableView;

import me.ollari.circolonauticogui.rest.AnnualFee;

public class AnnualFeeVisualization {
    private static Long annualFeeId;
    private static Long memberId;

    private static String transactionDate;
    private static String endSubscriptionDate;
    private static Double price;
    private static Boolean toPay;


    public AnnualFeeVisualization(AnnualFee af) {
        annualFeeId = af.getId();
        memberId = af.getMember().getId();
        transactionDate = af.getStart();
        endSubscriptionDate = af.getEnd();
        price = af.getPrice();
    }

    public static Long getAnnualFeeId() {
        return annualFeeId;
    }

    public static Long getMemberId() {
        return memberId;
    }

    public static String getTransactionDate() {
        return transactionDate;
    }

    public static String getEndSubscriptionDate() {
        return endSubscriptionDate;
    }

    public static Double getPrice() {
        return price;
    }

    public static Boolean getToPay() {
        return toPay;
    }
}
