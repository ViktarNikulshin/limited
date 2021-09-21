package com.beta.limited.controller.api;

import com.beta.limited.model.response.Root;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "jplaceholder", url = "https://graphhopper.com/api/1")
public interface Geo {

    @RequestMapping(method = RequestMethod.POST, value = "/vrp")
    Root getRout(@RequestParam("key") String key, @RequestBody com.beta.limited.model.routing.Root requestData);
}
