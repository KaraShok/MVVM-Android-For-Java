package com.karashok.library.common.base;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name BaseEntity
 * DESCRIPTION 最外层包裹的Entity
 * @date 2018/06/16/下午6:18
 */
public class BaseEntity<T> {

    /**
     * sample里GankIO用的
     */
    private boolean error;

    private List<T> results;

    /**
     * succeed : true
     * message : null
     * detail : null
     * result : {"groups":null,"phone":"18512435266","mail":"zhangyaozhong@icourt.cc","calendarPKid":null,"resultCode":"1","photo":null,"pic":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIFB7OgFB8sgfe5YddAdiaTLibIBqMOLEric2AlyrN7vFBick08SRnyBsfsWJsgeCvqxlPAa4yibBRtYJw/132","userId":"6F7A1810049611E894A4446A2ED9E475","officeId":"4d792e316a0511e6aa7600163e162add","officename":"iCourt","href":null,"username":"zhangyuezhong,zhangyaozhong","resultMess":"","token":"eyJhbGciOiJIUzI1NiJ9.eyJvZmZpY2VfaWQiOiI0ZDc5MmUzMTZhMDUxMWU2YWE3NjAwMTYzZTE2MmFkZCIsImRldmljZVR5cGUiOiJhbmRyb2lkTm90ZSIsIm9mZmljZV9uYW1lIjoiaUNvdXJ0IiwidXNlcl9pZCI6IjZGN0ExODEwMDQ5NjExRTg5NEE0NDQ2QTJFRDlFNDc1IiwibG9naW5UeXBlIjoiMSIsInVzZXJfbmFtZSI6IuW8oOiAgOS4rSIsImlzcyI6ImlMYXcuY29tIiwiZXhwIjoxNTIwOTk0NzU5MzY1LCJpYXQiOjE1MjAzODk5NTkzNjV9.6NCjMKXi5RF3rdvWs8fbJ0MtT2hJy7lb58oYejEiXGM","refreshToken":"eyJhbGciOiJIUzI1NiJ9.eyJvZmZpY2VfaWQiOiI0ZDc5MmUzMTZhMDUxMWU2YWE3NjAwMTYzZTE2MmFkZCIsImRldmljZVR5cGUiOiJhbmRyb2lkTm90ZSIsIm9mZmljZV9uYW1lIjoiaUNvdXJ0IiwidXNlcl9pZCI6IjZGN0ExODEwMDQ5NjExRTg5NEE0NDQ2QTJFRDlFNDc1IiwibG9naW5UeXBlIjoiMSIsInVzZXJfbmFtZSI6IuW8oOiAgOS4rSIsImlzcyI6ImlMYXcuY29tIiwiZXhwIjoxNTIyOTgxOTU5MzY1LCJpYXQiOjE1MjAzODk5NTkzNjV9.Gb-kQdmK1WKEH-_Q5ZgqDC-SQktAFM3c1N8m8mk042A","name":"张耀中"}
     */

    @SerializedName(value = "succeed",alternate = {"isSuccess"})
    private boolean succeed;
    private Object message;
    private Object detail;
    @SerializedName(value = "result",alternate = {"data"})
    private T result;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Object getDetail() {
        return detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "error=" + error +
                ", results=" + results +
                ", succeed=" + succeed +
                ", message=" + message +
                ", detail=" + detail +
                ", result=" + result +
                '}';
    }
}
