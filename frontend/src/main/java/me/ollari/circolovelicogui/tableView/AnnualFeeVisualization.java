package me.ollari.circolovelicogui.tableView;

import me.ollari.circolovelicogui.rest.AnnualFee;
import me.ollari.circolovelicogui.rest.Member;

public class AnnualFeeVisualization {
    private Long annualFeeId;
    private Long memberId;
    private String start;
    private String end;
    private Double price;

    public AnnualFeeVisualization(Member m, AnnualFee af) {
        this.annualFeeId = af.getId();
        this.memberId = m.getId();
        this.start = af.getStart();
        this.end = af.getEnd();
        this.price = af.getPrice();
    }

    public Long getAnnualFeeId() {
        return annualFeeId;
    }

    public void setAnnualFeeId(Long annualFeeId) {
        this.annualFeeId = annualFeeId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
