package com.beta.limited.model.routing;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class Objective{
    public String type;
    public String value;
}
