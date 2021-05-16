package com.wlp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wlp.bean.UserInfo;
import com.wlp.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
@CrossOrigin
public class MainController {

  //
  @Autowired private UserInfoService uService;

  /**
   * 登录跳转主页面
   *
   * @return
   */
  @RequestMapping("/index")
  public String login() {
    return "index";
  }

  @RequestMapping("/vue-element-admin/article/list")
  @ResponseBody
  public String article(){

      return null;
  }

  @GetMapping("user/info")
  @CrossOrigin
  @ResponseBody
  public String getUserInfo(String token,HttpSession session,HttpServletRequest request) throws JsonProcessingException {

      System.err.println(token);

      UserInfo attribute = (UserInfo) session.getAttribute(token);

      System.out.println(attribute);

      if (attribute != null){
          return new ObjectMapper().writeValueAsString(attribute);
      }else {
          return null;
      }
  }


    /**
     * 登录验证
     *
     * @param loginName
     * @param userPwd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
    public UserInfo loginCheck(String loginName, String userPwd, HttpServletRequest request) {

        // 输出用户名密码
        //    System.out.println(userName + ":" + userPwd);
        UserInfo verify = uService.verify(new UserInfo(loginName, userPwd));


        if (verify != null){

            // 生成7一个token
            String token = UUID.randomUUID() + "";
            System.out.println(token);

            verify.setToken(token);

            verify.setCode(20000);

            return verify;
        }

        return verify;
    }

  /** iframe展示首页 */
  @RequestMapping("/main")
  public String showMain() {
    return "main";
  }
}
