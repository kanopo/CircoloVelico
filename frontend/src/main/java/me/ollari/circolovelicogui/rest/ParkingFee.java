package me.ollari.circolovelicogui.rest;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "price",
        "start",
        "end"
})

public class ParkingFee {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("price")
    private Double price;
    @JsonProperty("start")
    private String start;
    @JsonProperty("end")
    private String end;

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

    @JsonProperty("price")
    public Double getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(Double price) {
        this.price = price;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
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

    @Override
    public String toString() {
        return "\n\tParkingFee\n{" +
                "\t\tid=" + id + ",\n" +
                "\t\tprice=" + price + ",\n" +
                "\t\tstart='" + start + ",\n" +
                "\t\tend='" + end + ",\n" +
                "\t\tadditionalProperties=" + additionalProperties +
                "\t}\n";
    }
}