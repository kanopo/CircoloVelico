package me.ollari.circolonauticogui.rest;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "participationPrice",
        "award",
        "date"
})
public class Race {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("participationPrice")
    private Double participationPrice;
    @JsonProperty("award")
    private Double award;
    @JsonProperty("date")
    private String date;
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

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("participationPrice")
    public Double getParticipationPrice() {
        return participationPrice;
    }

    @JsonProperty("participationPrice")
    public void setParticipationPrice(Double participationPrice) {
        this.participationPrice = participationPrice;
    }

    @JsonProperty("award")
    public Double getAward() {
        return award;
    }

    @JsonProperty("award")
    public void setAward(Double award) {
        this.award = award;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
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