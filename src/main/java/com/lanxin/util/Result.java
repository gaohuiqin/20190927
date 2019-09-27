package com.lanxin.util;

/**
 * Created by Administrator on 2019/8/19 0019.
 */
public class Result {
    private int a;
    private int b;
    private Integer code;

    private String msg;

    private Object data;

    public static Result ok(){  //ok成功状态
        Result result=new Result();
        result.setCode(200);
        result.setMsg("操作成功");
        return result;
    }

    public static Result ok(String msg){  //ok成功状态
        Result result=new Result();
        result.setCode(200);
        result.setMsg(msg);
        return result;
    }

    public static Result ok(Object data){  //ok成功状态
        Result result=new Result();
        result.setCode(200);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    public static Result failed(int code,String msg){  //ok成功状态
        Result result=new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result failed(){  //ok成功状态
        Result result=new Result();
        result.setCode(500);
        result.setMsg("系统内部异常");
        return result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
