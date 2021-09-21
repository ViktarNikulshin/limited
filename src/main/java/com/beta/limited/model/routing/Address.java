package com.beta.limited.model.routing;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
public class Address{
    public String location_id;
    public double lon;
    public double lat;
}
