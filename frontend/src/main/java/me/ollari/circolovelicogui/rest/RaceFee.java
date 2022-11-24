package me.ollari.circolovelicogui.rest;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "member",
        "race",
        "boat",
        "price",
        "paymentDate",
        "paymentMethod",
        "toPay"
})


public class RaceFee {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("member")
    private Member member;
    @JsonProperty("race")
    private Race race;
    @JsonProperty("boat")
    private Boat boat;
    @JsonProperty("price")
    private Double price;
    @JsonProperty("paymentDate")
    private String paymentDate;
    @JsonProperty("paymentMethod")
    private String paymentMethod;

    @JsonProperty("toPay")
    private Boolean toPay;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("member")
    public Member getMember() {
        return member;
    }

    @JsonProperty("member")
    public void setMember(Member member) {
        this.member = member;
    }

    @JsonProperty("race")
    public Race getRace() {
        return race;
    }

    @JsonProperty("race")
    public void setRace(Race race) {
        this.race = race;
    }

    @JsonProperty("boat")
    public Boat getBoat() {
        return boat;
    }

    @JsonProperty("boat")
    public void setBoat(Boat boat) {
        this.boat = boat;
    }

    @JsonProperty("price")
    public Double getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(Double price) {
        this.price = price;
    }

    @JsonProperty("paymentDate")
    public String getPaymentDate() {
        return paymentDate;
    }

    @JsonProperty("paymentDate")
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    @JsonProperty("paymentMethod")
    public String getPaymentMethod() {
        return paymentMethod;
    }

    @JsonProperty("paymentMethod")
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @JsonProperty("toPay")
    public Boolean getToPay() {
        return toPay;
    }

    @JsonProperty("toPay")
    public void setToPay(Boolean toPay) {
        this.toPay = toPay;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}