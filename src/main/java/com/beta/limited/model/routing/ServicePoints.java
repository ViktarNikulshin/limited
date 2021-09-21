package com.beta.limited.model.routing;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
@Data
@Builder
public class ServicePoints {
    public String id;
    public String name;
    public Address address;
    @JsonIgnore
    public List<Integer> size;
    @JsonIgnore
    public List<TimeWindow> time_windows;
}
