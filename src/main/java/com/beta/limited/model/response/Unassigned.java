package com.beta.limited.model.response;
import lombok.Data;

import java.util.List;
@Data
public class Unassigned{
    public List<Object> services;
    public List<Object> shipments;
    public List<Object> breaks;
    public List<Object> details;
}
