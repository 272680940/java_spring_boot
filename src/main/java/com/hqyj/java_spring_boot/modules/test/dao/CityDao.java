package com.hqyj.java_spring_boot.modules.test.dao;

import com.hqyj.java_spring_boot.modules.common.vo.SearchVo;
import com.hqyj.java_spring_boot.modules.test.entity.City;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Repository
public interface CityDao {

    @Select("select * from m_city where country_id = #{countryId}")
    //通过countryId 查询所有的城市
    List<City> getCitiesByCountryId(int countryId);

    //脚本式多条件查询
    @Select("<script>" +
            "select * from m_city "
            + "<where> "
            + "<if test='keyWord != \"\" and keyWord != null'>"
            + " and (city_name like '%${keyWord}%' or local_city_name like '%${keyWord}%') "
            + "</if>"
            + "</where>"
            + "<choose>"
            + "<when test='orderBy != \"\" and orderBy != null'>"
            + " order by ${orderBy} ${sort}"
            + "</when>"
            + "<otherwise>"
            + " order by city_id desc"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<City> getCitiesBySearchVo(SearchVo searchVo);

    /* 添加 */
    @Insert("insert into m_city(city_name,local_city_name,country_id,district,population) " +
            "values(#{cityName},#{localCityName},#{countryId},#{district},#{population})")
    //将添加数据后的主键 city_id 的值返回到City对象中
    @Options(useGeneratedKeys = true,keyColumn = "city_id",keyProperty = "cityId")
    void insertCity(City city);


    /* 修改 */
    @Update("update m_city set local_city_name = #{localCityName} where city_id = #{cityId}")
    void updateCity(City city);

    /* 删除 */
    @Delete("delete from m_city where city_id = #{cityId}")
    void deleteCity(int cityId);
}
