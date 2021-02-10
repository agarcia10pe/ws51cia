package com.hache.appentrepatas.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {
    @SerializedName("msg_code")
    @Expose
    private short codigo;

    @SerializedName("data")
    @Expose
    private T data;

    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        codigo = codigo;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        data = data;
    }
}
