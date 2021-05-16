package com.wlp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.wlp.bean.UserInfo;
import com.wlp.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping(value = "/user")
public class UserController {

    //  http://localhost/Z+rb_SSM_System_war_exploded/

    // 用户表服务层实例
    @Autowired
    UserInfoService userService;

    @Autowired
    ObjectMapper mapper;

    /**
     * 根据用户id获取角色
     * @Date: 15:23 2021/4/27
     * @param: id
     * @return: java.lang.String
     **/
    @ResponseBody
    @RequestMapping(value = "/getRole/{userId}",produces = "application/json; charset=utf-8")
    public String getRole(@PathVariable("userId") String id) throws JsonProcessingException {
        System.err.println(id);
        if (id != null){
            return mapper.writeValueAsString(userService.getRole(Integer.parseInt(id)));
        }
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/getRouter/{userId}",produces = "application/json; charset=utf-8")
    public String getRouter(@PathVariable("userId") int id) throws JsonProcessingException {

        if (id != 0){
           return mapper.writeValueAsString(userService.getRouter(id));
        }

        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/getRoleInfo",produces = "application/json; charset=utf-8")
    public String getRoleAndResourceByUserId(int userId) throws JsonProcessingException {

        // 如果用户id为空直接返回
        if (userId == 0){
            return null;
        }
        // 返回用户角色资源信息
        return mapper.writeValueAsString(userService.getRoleByUserId(userId));
    }

    /**
     * 更新用户信息
     * @Date: 10:29 2021/4/29
     * @param: userInfo
     * @return: int
     **/
    @RequestMapping("/upUserInfo")
    public @ResponseBody int upUserInfo(UserInfo userInfo){
        return userService.updateByPrimaryKeySelective(userInfo);
    }

    /**
     * 新增一位用户
     * @Date: 18:01 2021/4/28
     * @param: userInfo
     * @return: int
     **/
    @RequestMapping(value = "/inUser")
    public @ResponseBody int addUserInfo(UserInfo userInfo){
        int i = userService.insertSelective(userInfo);

        if (i > 0){
            return userService.getUserIdMax();
        }else {
            return i;
        }
    }

    /**
     * 删除一位用户
     * @Date: 10:28 2021/4/29
     * @param: userId
     * @return: int
     **/
    @RequestMapping("/deUser/{userId}")
    public @ResponseBody int deUserInfo(@PathVariable("userId") int userId){
        if (userId > 0) {
            return userService.deleteByPrimaryKey(userId);
        }else {
            return 0;
        }
    }

    /**
     * 查询所有用户信息
     * @Date: 10:30 2021/4/29
     * @param: pageIndex
     * @param: pageSize
     * @return: java.util.List<com.wlp.bean.UserInfo>
     **/
    @RequestMapping("/getInfoAll")
    public @ResponseBody Map<String,Object> getUserAll(@RequestParam("page") Integer pageIndex,
                                                   @RequestParam("limit") Integer pageSize){

        PageInfo<UserInfo> pageInfo = userService.selectAll(pageIndex, pageSize);

        Map<String,Object> map = new HashMap<>();

        map.put("items",pageInfo.getList());
        map.put("total",pageInfo.getTotal());

        return map;
    }

    /**
     * 获取所有用户id
     * @Date: 22:20 2021/4/27
     * @param:
     * @return: java.util.List<com.wlp.bean.UserInfo>
     **/
    @RequestMapping("/getUser")
    public @ResponseBody List<UserInfo> getUserId(){
        return userService.getUserId();
    }
}
