package com.hqyj.java_spring_boot.modules.test.dao;

import com.hqyj.java_spring_boot.modules.test.entity.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CityDao {

    @Select("select * from m_city where country_id = #{countryId}")
    //通过countryId 查询所有的城市
    List<City> getCitiesByCountryId(int countryId);
}
