package com.wlp.controller;

import com.wlp.bean.Resources;
import com.wlp.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/resource") // 通过这里配置使下面的映射都在/Resource下
public class ResourceController {

  // 资源表服务层实例
  @Autowired ResourceService resService;

  /**
   * 查询
   *
   * @return
   */
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public List<Resources> getResourceList() {
    // 处理"/Resource/"的GET请求，用来获取用户列表
    // 还可以通过@RequestParam从页面中传递参数来进行查询条件或者翻页信息的传递
    List<Resources> r = resService.selectAll();
    //    for (Resources resource : r) {
    //      System.out.println(resource);
    //    }
    return r;
  }

  /**
   * 新增
   *
   * @param res
   * @return
   */
  @RequestMapping(value = "/", method = RequestMethod.POST)
  public String postResource(@RequestBody Resources res) {
    // 处理"/Resource/"的POST请求，用来创建Resource
    // 除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数
    //    System.out.println(666);
    resService.insertSelective(res);
    return "success";
  }

  /**
   * 更新前跳转页面
   *
   * @param id
   * @return
   */
  @RequestMapping(value = "/jump/{id}", method = RequestMethod.POST)
  public ModelAndView postResource2(@PathVariable int id, String resName, String resUrl) {
    // 处理"/Resource/"的POST请求，用来创建Resource
    // 除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数
    //    System.out.println("zaiz");
    return new ModelAndView("res-add").addObject("res", new Resources(id, resName, resUrl));
  }

  /**
   * 更新
   *
   * @param id
   * @param
   * @return
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public String putResource(@PathVariable int id, @RequestBody Resources res) {
    // 处理"/Resource/{id}"的PUT请求，用来更新Resource信息
    //    System.out.println(id);
    res.setResId(id);
    resService.updateByPrimaryKeySelective(res);
    return "success";
  }

  /**
   * 删除
   *
   * @param id
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public String deleteResource(@PathVariable int id) {
    // 处理"/Resource/{id}"的DELETE请求，用来删除Resource
    //    System.out.println("删除Id"+id);
    resService.deleteByPrimaryKey(id);
    return "success";
  }
}
