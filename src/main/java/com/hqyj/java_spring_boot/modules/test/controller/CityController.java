package com.hqyj.java_spring_boot.modules.test.controller;

import com.github.pagehelper.PageInfo;
import com.hqyj.java_spring_boot.modules.common.vo.Result;
import com.hqyj.java_spring_boot.modules.common.vo.SearchVo;
import com.hqyj.java_spring_boot.modules.test.entity.City;
import com.hqyj.java_spring_boot.modules.test.service.CityService;
import org.apache.ibatis.annotations.Delete;
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

    /**
     *脚本式多条件查询
     * localhost/api/cities
     * {"currentPage":"1","pageSize":"5","keyWord":"Sh","orderBy":"city_name","sort":"desc"}
     */
    @PostMapping(value = "/cities",consumes = "application/json")
    public PageInfo<City> getCitiesBySearchVo(@RequestBody SearchVo searchVo){
        return cityService.getCitiesBySearchVo(searchVo);
    }

    /**
     *添加
     * localhost/api/city ----POST
     * {"cityName":"test1","localCityName":"freeCity","countryId":"522",
     *        "district":"wwww","population":"123456"}
     */
    @PostMapping(value = "/city",consumes = "application/json")
    public Result<City> insertCity(@RequestBody City city){
        return cityService.insertCity(city);
    }

    /**
     *修改
     * localhost/api/city ---- PUT
     * {"localCityName":"freeCity","cityId":"2258"}
     */
    @PutMapping(value = "/city",consumes = "application/x-www-form-urlencoded")
    public Result<City> updateCity(@ModelAttribute City city){
        return cityService.updateCity(city);
    }

    /**
     *删除
     * localhost/api/city/2258 ---- DELETE,
     */
    @DeleteMapping(value = "/city/{cityId}")
    public Result<City> deleteCity(@PathVariable int cityId){
        return cityService.deleteCity(cityId);
    }
}
