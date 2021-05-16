package com.wlp.controller;

import com.github.pagehelper.PageInfo;
import com.wlp.bean.RoleInfo;
import com.wlp.service.RoleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/role") // 通过这里配置使下面的映射都在/Resource下
public class RoleController {

    // 资源表服务层实例
    @Autowired
    RoleInfoService role;

    /**
     * 新增角色信息
     * @Date: 15:14 2021/4/30
     * @param: roleInfo
     * @return: int
     **/
    @RequestMapping(value = "/inRole", method = RequestMethod.POST)
    public @ResponseBody int addClientData(RoleInfo roleInfo){

        if (roleInfo != null){

            if (role.insert(roleInfo) > 0){

                return role.getMax();
            }else {
                return 0;
            }
        }else {
            return 0;
        }
    }

    /**
     * 分页获取所有角色
     * @Date: 15:18 2021/4/30
     * @param: pageIndex
     * @param: pageSize
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     **/
    @RequestMapping("/getRoleAll")
    public @ResponseBody
    Map<String,Object> getClientInfo(@Nullable @RequestParam("page") Integer pageIndex,
                                     @Nullable @RequestParam("limit") Integer pageSize){
        Map<String,Object> map = new HashMap<>();

        PageInfo<RoleInfo> pageInfo = role.selectAll(pageIndex, pageSize);
        map.put("items",pageInfo.getList());
        map.put("total",pageInfo.getTotal());

        return map;
    }
    /**
     * 删除角色
     * @Date: 15:15 2021/4/30
     * @param: id
     * @return: int
     **/
    @RequestMapping("/deRole/{roleId}")
    public @ResponseBody int deClient(@PathVariable("roleId") int id){
        if (id > 0 ){
            return role.deleteByPrimaryKey(id);
        }else {
            return 0;
        }
    }

    /**
     * 更新一条客户信息
     * @Date: 17:59 2021/4/29
     * @param: clientInfo
     * @return: int
     **/
    @RequestMapping("/upRole")
    public @ResponseBody int upClient(RoleInfo roleInfo){

        if (roleInfo != null){
            return role.updateByPrimaryKeySelective(roleInfo);
        }else {
            return 0;
        }
    }
}
