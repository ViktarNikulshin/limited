package com.beta.limited.model.response;
import lombok.Data;

import java.util.List;
@Data
public class Solution{
    public int costs;
    public int distance;
    public int time;
    public int transport_time;
    public int completion_time;
    public int max_operation_time;
    public int waiting_time;
    public int service_duration;
    public int preparation_time;
    public int no_vehicles;
    public int no_unassigned;
    public List<Route> routes;
    public Unassigned unassigned;
}
