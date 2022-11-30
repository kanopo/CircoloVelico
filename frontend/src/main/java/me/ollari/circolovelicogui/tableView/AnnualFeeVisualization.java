package me.ollari.circolovelicogui.tableView;

import me.ollari.circolovelicogui.rest.AnnualFee;

public class AnnualFeeVisualization {
    private static Long annualFeeId;
    private static String transactionDate;
    private static String endSubscriptionDate;
    private static Double price;


    public AnnualFeeVisualization(AnnualFee af) {
        annualFeeId = af.getId();
        transactionDate = af.getStart();
        endSubscriptionDate = af.getEnd();
        price = af.getPrice();
    }

    public static Long getAnnualFeeId() {
        return annualFeeId;
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

}
