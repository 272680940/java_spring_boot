package com.hqyj.java_spring_boot.modules.account.dao;

import com.hqyj.java_spring_boot.modules.account.entity.User;
import com.hqyj.java_spring_boot.modules.common.vo.SearchVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserDao {

    //注册用户
    @Insert("insert into user(user_name, password, user_img, create_date)" +
            "values(#{userName}, #{password}, #{userImg}, #{createDate})")
    @Options(useGeneratedKeys = true, keyColumn = "user_id", keyProperty = "userId")
    void insertUser(User user);

    //通过userName获取User对象
    @Select("select * from user where user_name=#{userName}")
    @ResultMap(value = "userResultsMap")
    User getUserByUserName(String userName);

    //脚本式多条件查询
    @Select("<script>" +
            "select * from user "
            + "<where> "
            + "<if test='keyWord != \"\" and keyWord != null'>"
            + " and (user_name like '%${keyWord}%') "
            + "</if>"
            + "</where>"
            + "<choose>"
            + "<when test='orderBy != \"\" and orderBy != null'>"
            + " order by ${orderBy} ${sort}"
            + "</when>"
            + "<otherwise>"
            + " order by user_id desc"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<User> getUsersBySearchVo(SearchVo searchVo);

    //修改
    @Update("update user set user_name = #{userName},user_img = #{userImg} " +
            "where user_id = #{userId}")
    void updateUser(User user);

    //删除
    @Delete("delete from user where user_id = #{userId}")
    void deleteUser(Integer userId);

    //查询user中单条数据，并携带role的信息,使用组合查询
    @Select("select * from user where user_id = #{userId}")
    @Results(id = "userResultsMap",value = {
            @Result(column = "user_id",property = "userId"),
            @Result(column = "user_id",property = "roles",javaType = List.class,
            many = @Many(select = "com.hqyj.java_spring_boot.modules.account.dao.RoleDao.getRolesByUserId"))
    })
    User getUserByUserId(Integer userId);
}
