package com.beta.limited.model.response;
import lombok.Data;

import java.util.List;
@Data
public class Activity{
    public String type;
    public String location_id;
    public Address address;
    public int end_time;
    public Object end_date_time;
    public int distance;
    public int driving_time;
    public int preparation_time;
    public int waiting_time;
    public List<Integer> load_after;
    public String id;
    public int arr_time;
    public Object arr_date_time;
    public List<Integer> load_before;
}
