package com.aoji.controller;

/**
 * @author yangsaixing
 * @description
 * @date Created in 上午10:54 2017/11/10
 */

import com.aoji.model.BasePageModel;
import com.aoji.model.PageParam;
import com.aoji.model.StudentInfo;
import com.aoji.model.SysUser;
import com.aoji.service.StudentService;
import com.aoji.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @Autowired
    StudentService studentService;

    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("/student")
    public String list(){
        return "list";
    }

    @RequestMapping(value="queryStus",method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel get(StudentInfo studentInfo, PageParam pageParam, BasePageModel basePageModel){
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart()/pageParam.getiDisplayLength()+1, pageParam.getiDisplayLength());
        String[] str=propList();
        studentService.getList(studentInfo);
        pageParam.setSortPro(str[pageParam.getiSortCol_0()]);
        basePageModel.setAaData(page);
        basePageModel.setiTotalDisplayRecords((int)page.getTotal());
        basePageModel.setiTotalRecords((int)page.getTotal());
        return basePageModel;
    }

    @RequestMapping(value="home")
    public String login(){
        return "login";
    }

    @RequestMapping(value = "checkLogin",method=RequestMethod.POST)
    public String checkLogin(String username, String password, HttpSession session,RedirectAttributes redirectAttributes){

        Subject currentUser= SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            logger.info("对用户[" + username + "]进行登录验证..验证开始");
            currentUser.login(token);
            token.clear();
            SysUser user=(SysUser) currentUser.getPrincipal();
            //根据用户查询所属角色
            //根据角色查询权限
            session.setAttribute("functions",userService.getFunctionByName(user.getUsername()));
            session.setAttribute("user",user);
            logger.info("对用户[" + username + "]进行登录验证..验证通过");
        }catch(UnknownAccountException uae){
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
            redirectAttributes.addFlashAttribute("message", "未知账户");
        }catch(IncorrectCredentialsException ice){
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
            redirectAttributes.addFlashAttribute("message", "密码不正确");
        }catch(LockedAccountException lae){
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
            redirectAttributes.addFlashAttribute("message", "账户已锁定");
        }catch(ExcessiveAttemptsException eae){
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
            redirectAttributes.addFlashAttribute("message", "用户名或密码错误次数过多");
        }catch(AuthenticationException ae){
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");
        }
        /*return ResponseEntity.status(HttpStatus.OK).body("success");*/
        //验证是否登录成功
        if(currentUser.isAuthenticated()){
            logger.info("用户[" + username + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
            return "redirect:/indexPage";
        }else{
            token.clear();
            return "redirect:/home";
        }
    }

    @RequestMapping(value = "indexPage")
    public String indexPage(){
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if(user==null){
            return "redirect:/home";
        }else{
            return "index";
        }
    }
    public String[] propList(){
        return new String[]{"id","name","systemNo","gender","address","mobile","qqNumber"};
    }
}
