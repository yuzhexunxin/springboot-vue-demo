package com.example.demo.common;

public class Result<Object> {
    private  String code;
    private  String msg;
    private  Object data;
    public String getCode() {return code;}
    public void setCode(String code) {this.code = code;}
    public String getMsg() {return msg;}
    public void setMsg(String msg) {this.msg = msg;}
    public Object getData() {return data;}
    public void setData(Object data) {this.data = data;}
    public Result() {
    }
    public Result(Object data) { this.data = data;}
    public static Result success() {
        Result result = new Result<>();
        result.setCode("0");
        result.setMsg("成功");
        return result;
    }
    public static <Object> Result<Object> success(Object data) {
        Result<Object> result = new Result<>(data);
        result.setCode("0");
        result.setMsg("成功");
        return result;
    }
    public static Result error (String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
