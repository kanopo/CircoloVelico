package me.ollari.circolovelicogui.rest;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "raceFeeId",
        "memberId",
        "raceId",
        "boatId",
        "raceFeePrice",
        "raceFeePaymentDate"
})

public class RaceFeeVisualizationEmployee {

    @JsonProperty("raceFeeId")
    private Integer raceFeeId;
    @JsonProperty("memberId")
    private Integer memberId;
    @JsonProperty("raceId")
    private Integer raceId;
    @JsonProperty("boatId")
    private Integer boatId;
    @JsonProperty("raceFeePrice")
    private Double raceFeePrice;
    @JsonProperty("raceFeePaymentDate")
    private String raceFeePaymentDate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("raceFeeId")
    public Integer getRaceFeeId() {
        return raceFeeId;
    }

    @JsonProperty("raceFeeId")
    public void setRaceFeeId(Integer raceFeeId) {
        this.raceFeeId = raceFeeId;
    }

    @JsonProperty("memberId")
    public Integer getMemberId() {
        return memberId;
    }

    @JsonProperty("memberId")
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    @JsonProperty("raceId")
    public Integer getRaceId() {
        return raceId;
    }

    @JsonProperty("raceId")
    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    @JsonProperty("boatId")
    public Integer getBoatId() {
        return boatId;
    }

    @JsonProperty("boatId")
    public void setBoatId(Integer boatId) {
        this.boatId = boatId;
    }

    @JsonProperty("raceFeePrice")
    public Double getRaceFeePrice() {
        return raceFeePrice;
    }

    @JsonProperty("raceFeePrice")
    public void setRaceFeePrice(Double raceFeePrice) {
        this.raceFeePrice = raceFeePrice;
    }

    @JsonProperty("raceFeePaymentDate")
    public String getRaceFeePaymentDate() {
        return raceFeePaymentDate;
    }

    @JsonProperty("raceFeePaymentDate")
    public void setRaceFeePaymentDate(String raceFeePaymentDate) {
        this.raceFeePaymentDate = raceFeePaymentDate;
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
