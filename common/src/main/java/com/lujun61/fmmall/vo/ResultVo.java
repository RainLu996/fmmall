package com.lujun61.fmmall.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

// 当接⼝参数和返回值为对象类型时，在实体类中添加注解说明
@ApiModel(value = "ResultVo对象", description = "封装接口返回给前端的数据")
public class ResultVo {

    //响应给前端的状态码
    @ApiModelProperty(value = "响应状态码", dataType = "String")
    private String code;

    //响应给前端的提示信息
    @ApiModelProperty(value = "响应提示信息", dataType = "String")
    private String msg;

    //响应给前端的数据
    @ApiModelProperty(value = "响应数据", dataType = "Object")
    private Object data;

    public ResultVo() {
    }

    public ResultVo(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
