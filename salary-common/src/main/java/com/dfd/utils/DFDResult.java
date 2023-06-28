package com.dfd.utils;

import com.dfd.enums.DYDResultEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yyy
 * @Title: DYDResult.java
 * @Package com.dyd.utils
 * @Description: 自定义响应数据结构
 * 本类可提供给 H5/ios/安卓/公众号/小程序 使用
 * 前端接受此类数据（json object)后，可自行根据业务去实现相关功能
 * <p>
 * 200：表示成功
 * 500：表示错误，错误信息在msg字段中
 * 501：bean验证错误，不管多少个错误都以map形式返回
 * 502：拦截器拦截到用户token出错
 * 555：异常抛出信息
 * 556: 用户qq校验异常
 * @date 2023/3/20 10:10
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel
public class DFDResult<T> implements Serializable {

    // 响应业务状态
    @ApiModelProperty(value = "响应业务状态", name = "status")
    private Integer status;

    // 响应消息
    @ApiModelProperty(value = "响应消息", name = "msg")
    private String msg;

    // 响应中的数据
    @ApiModelProperty(value = "响应中的数据", name = "data")
    private T data;

    public static <T> DFDResult<T> sucess(T data) {

        return new DFDResult<T>(DYDResultEnum.SUCCESS.getCode(),data);
    }

    public static <T> DFDResult sucess() {

        return new DFDResult<T>(DYDResultEnum.SUCCESS.getCode(),null);
    }

    public static <T> DFDResult errorMsg(String msg) {

        return new DFDResult<T>(DYDResultEnum.ERROR.getCode(), msg, null);
    }

    public static <T> DFDResult errorMap(T data) {

        return new DFDResult<T>(DYDResultEnum.ERROR.getCode(), "error", data);
    }

    public static <T> DFDResult<T> error(Integer status, String msg) {

        return new DFDResult<T>(status, msg, null);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public DFDResult(T data) {
        this.status = DYDResultEnum.SUCCESS.getCode();
        this.msg = "OK";
        this.data = data;
    }

    public DFDResult(String msg, T data) {
        this.status = DYDResultEnum.SUCCESS.getCode();
        this.msg = msg;
        this.data = data;
    }

    public DFDResult(Integer status, T data) {
        this.status = status;
        this.msg = null;
        this.data = data;
    }
}
