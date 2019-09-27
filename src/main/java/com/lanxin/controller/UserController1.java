package com.lanxin.controller;

import com.lanxin.util.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2019/9/26 0026.
 */
@RestController
public class UserController1
{
    @RequestMapping(value = "/login")
    public Result login(String username,String pass){


        Subject subject= SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,pass);

        try {
            subject.login(usernamePasswordToken);

        } catch (UnknownAccountException e) {

            return Result.failed(10001,"账号错误");
        }catch(IncorrectCredentialsException e2){

            return Result.failed(10002,"密码错误");

        }catch (AuthenticationException e3){

            return Result.failed(10003,"认证错误");
        }
        return Result.ok("登录成功");

    }

    @RequestMapping(value = "/logout")
    public Result logout(){


        Subject subject=SecurityUtils.getSubject();

        subject.logout();

        return Result.ok();

    }

    @RequestMapping(value = "/add")
    @RequiresRoles(value = {"system"})
    public Result add(){

        return Result.ok("添加用户成功1");

    }
    @RequiresRoles(value = {"editor","system"},logical = Logical.OR)
    @RequestMapping(value = "/update")
    public Result update(){

        return Result.ok("修改用户成功");

    }
    @RequestMapping(value = "/delete")
    @RequiresRoles(value = {"system"})
    public Result delete(){

        return Result.ok("删除用户成功");

    }
    @RequiresRoles(value = {"editor","system"},logical = Logical.OR)
    @RequestMapping(value = "/select")
    public Result select(){

        return Result.ok("查询用户成功");

    }

    @RequestMapping(value = "/notlogin")
    public  Result notlogin(){

        return Result.failed(10000,"您还没有登录");
    }

}
