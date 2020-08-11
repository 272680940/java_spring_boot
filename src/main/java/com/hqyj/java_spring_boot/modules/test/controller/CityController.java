package com.hqyj.java_spring_boot.modules.test.controller;

import com.github.pagehelper.PageInfo;
import com.hqyj.java_spring_boot.modules.common.vo.SearchVo;
import com.hqyj.java_spring_boot.modules.test.entity.City;
import com.hqyj.java_spring_boot.modules.test.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CityController {
    @Autowired
    private CityService cityService;

    /**
     * 通过 countryId 查询所有的城市
     * @param countryId
     * @return
     * localhost/api/cities/522 ---- GET
     */
    @GetMapping("/cities/{countryId}")
    public List<City> getCitiesByCountryId(@PathVariable int countryId){
        return cityService.getCitiesByCountryId(countryId);
    }

    /**
     * 分页查询所有城市信息
     * @param countryId
     * @param searchVo
     * @return
     * localhost/api/cities/522 ---- POST
     * {"currentPage":1,"pageSize":5}
     */
    @PostMapping(value = "/cities/{countryId}",consumes = "application/json")
    public PageInfo<City> getCitiesBySearchVo(@PathVariable int countryId
            , @RequestBody SearchVo searchVo){
        return cityService.getCitiesBySearchVo(countryId,searchVo);
    }
}
