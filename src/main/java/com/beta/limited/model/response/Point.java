package com.beta.limited.model.response;
import lombok.Data;

import java.util.List;
@Data
public class Point{
    public List<List<Double>> coordinates;
    public String type;
}
