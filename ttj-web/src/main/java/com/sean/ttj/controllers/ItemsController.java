package com.sean.ttj.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by sean on 2016/7/19.
 */
@Controller
@RequestMapping("/items")
public class ItemsController {

    @RequestMapping("summary/{type}")
    public String women(@PathVariable("type") String type, ModelMap mp){
        //mp.put("type", type);
        return "items/summary";
    }

    @RequestMapping("{id}")
    public String detail(@PathVariable("id") Long id, ModelMap mp){
        mp.put("id", id);
        return "items/detail";
    }
}
