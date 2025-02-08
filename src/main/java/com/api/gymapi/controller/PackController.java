package com.api.gymapi.controller;

import com.api.gymapi.models.Pack;
import com.api.gymapi.service.Packservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/offers")
public class PackController {
    @Autowired
    private Packservice packservice;

    @PostMapping("/")
    public Pack addPack(@RequestBody Pack pack) {
        return packservice.savePack(pack);
    }

}
