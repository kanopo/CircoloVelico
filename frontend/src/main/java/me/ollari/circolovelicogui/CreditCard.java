package me.ollari.circolovelicogui;

public class CreditCard {
    private Integer cardNumber;
    private Integer month;
    private Integer year;
    private String owner;
    private Integer cvv;

    public CreditCard(Integer cn, Integer m, Integer y, String o, Integer c) {
        this.cardNumber = cn;
        this.month = m;
        this.year = y;
        this.owner = o;
        this.cvv = c;

    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    @Override
    public String toString() {
        return "me.ollari.circolonauticogui.CreditCard{" +
                "cardNumber=" + cardNumber +
                ", month=" + month +
                ", year=" + year +
                ", owner='" + owner + '\'' +
                ", cvv=" + cvv +
                '}';
    }
}
