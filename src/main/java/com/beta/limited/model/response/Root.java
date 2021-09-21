package com.beta.limited.model.response;
import lombok.Data;

import java.util.List;
@Data
public class Root{
    public List<String> copyrights;
    public String job_id;
    public String status;
    public int waiting_time_in_queue;
    public int processing_time;
    public Solution solution;
}
