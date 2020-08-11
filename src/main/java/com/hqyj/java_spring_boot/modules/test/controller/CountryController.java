package com.hqyj.java_spring_boot.modules.test.controller;

import com.hqyj.java_spring_boot.modules.test.entity.Country;
import com.hqyj.java_spring_boot.modules.test.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CountryController {

    @Autowired
    private CountryService countryService;

    /**
     * localhost/api/country/522 ---- get
     * 通过countryId查询国家
     */
    @GetMapping("/country/{countryId}")
    public Country getCountryByCountryId(@PathVariable int countryId){
        return countryService.getCountryByCountryId(countryId);
    }


    /**
     * localhost/api/country?countryName=China ---- get
     * 通过countryName查询国家
     */
    @GetMapping("/country")
    public Country getCountryByCountryName(@RequestParam String countryName){
        return countryService.getCountryByCountryName(countryName);
    }
}
