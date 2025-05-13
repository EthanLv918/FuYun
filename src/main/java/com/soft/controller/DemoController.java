package com.soft.controller;

import com.soft.dto.DemoDto;
import com.soft.service.DemoService;
import com.soft.common.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
@Slf4j
public class DemoController {

    @Autowired
    private DemoService demoService;

    @PostMapping("mungsDemo")
    public ServerResponse mungsDemo(@RequestBody DemoDto demoDto){
        ServerResponse serverResponse = new ServerResponse();
        demoService.mungsDemo(demoDto);
        return serverResponse;
    }
}
