package com.hqyj.java_spring_boot.modules.test.dao;

import com.hqyj.java_spring_boot.modules.test.entity.Country;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CountryDao {

    //通过countryId查询国家，并携带该国家的城市数据
    @Select("select * from m_country where country_id = #{countryId}")
    @Results(id = "countryResults", // 将此结果集做一个唯一标识，方便调用
            value = {
            //再次映射countryId,若无此代码，则查询的结果countryId没有值
            @Result(column = "country_id",property = "countryId"),
            //通过countryId查询城市信息，并将其封装到Country类中的属性cities中
            @Result(column = "country_id",property = "cities",javaType = List.class,
            // many表示返回多条数据，单条数据用one
            many = @Many(select = "com.hqyj.java_spring_boot.modules.test.dao.CityDao.getCitiesByCountryId"))
    })
    Country getCountryByCountryId(int countryId);

    //通过countryName查询国家
    @Select("select * from m_country where country_name = #{countryName}")
    @ResultMap(value = "countryResults")
    Country getCountryByCountryName(String countryName);
}
