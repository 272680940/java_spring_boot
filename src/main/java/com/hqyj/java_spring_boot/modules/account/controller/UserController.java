package com.hqyj.java_spring_boot.modules.account.controller;

import com.github.pagehelper.PageInfo;
import com.hqyj.java_spring_boot.modules.account.entity.User;
import com.hqyj.java_spring_boot.modules.account.service.UserService;
import com.hqyj.java_spring_boot.modules.common.vo.Result;
import com.hqyj.java_spring_boot.modules.common.vo.SearchVo;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     *注册用户
     * {"userName":"zhangsan","password":"123456"}
     * localhost/api/user ---- POST
     */
    @PostMapping(value = "/user",consumes = "application/json")
    public Result<User> insertUser(@RequestBody User user){
        return userService.insertUser(user);
    }

    /**
     * 登录
     * {"userName":"zhangsan","password":"123456"}
     * localhost/api/login ---- POST
     */
    @PostMapping(value = "/login",consumes = "application/json")
    public Result<User> getUserByUserNameAndPassword(@RequestBody User user){
        return userService.getUserByUserNameAndPassword(user);
    }

    /**
     * 脚本式多条件分页、查询
     * {"currentPage":1,"pageSize":5"keyWord":"adm"}
     * localhost/api/users ---- POST
     */
    @PostMapping(value = "/users",consumes = "application/json")
    public PageInfo<User> getUsersBySearchVo(@RequestBody SearchVo searchVo){
        return userService.getUsersBySearchVo(searchVo);
    }

    /**
     * 修改
     * {"userName":"zhaoliu","userImg":"/12.png","userId":"1"}
     * localhost/api/user --- PUT
     */
    @PutMapping(value = "user",consumes = "application/json")
    public Result<User> updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    /**
     * 删除
     * localhost/api/user/6 --- DELETE
     */
    @DeleteMapping("/user/{userId}")
    @RequiresPermissions(value="/api/user")
    public Result<Object> deleteUser(@PathVariable Integer userId){
        return userService.deleteUser(userId);
    }

    //查询user中单条数据，并携带role的信息
    @GetMapping("/user/{userId}")
    public User getUserByUserId(@PathVariable Integer userId){
        return userService.getUserByUserId(userId);
    }

    /**
     * 上传图片
     * localhost/api/userImg --- POST
     */
    @PostMapping(value = "/userImg", consumes = "multipart/form-data")
    public Result<String> uploadFile(@RequestParam MultipartFile file){
        return userService.uploadUserImg(file);
    }

    /**
     * 修改图片路径
     * localhost/api/profile --- POST
     */
    @PutMapping(value = "/profile", consumes = "application/json")
    public Result<User> updateUserProfile(@RequestBody User user) {
        return userService.updateUserProfile(user);
    }
}
