package com.beta.limited.model.response;
import lombok.Data;

import java.util.List;
@Data
public class Route{
    public String vehicle_id;
    public int distance;
    public int transport_time;
    public int completion_time;
    public int waiting_time;
    public int service_duration;
    public int preparation_time;
    public List<Point> points;
    public List<Activity> activities;
}
