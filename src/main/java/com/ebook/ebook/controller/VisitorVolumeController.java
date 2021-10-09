package com.ebook.ebook.controller;

import com.ebook.ebook.service.VisitorVolumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VisitorVolumeController {
    @Autowired
    private VisitorVolumeService visitorVolumeService;

    @RequestMapping("/visit")
    public String visit() {
        return visitorVolumeService.visit().toString();
    }

    @RequestMapping("/getVisitorVolume")
    public String getVisitorVolume() {
        return visitorVolumeService.getVisitorVolume().toString();
    }
}
