 package com.hmms.entity;
  
 public class Result
 {
   private int errorCode;
   private String msg;
   private int total;
   private Object result;

   private int atdID;  //20220714
  private int failLineNo;  //20220714


  


  public int getErrorCode() {
    return errorCode;
  }
  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }
  public String getMsg() {
    return msg;
  }
  public void setMsg(String msg) {
    this.msg = msg;
  }
  public int getTotal() {
    return total;
  }
  public void setTotal(int total) {
    this.total = total;
  }
  public Object getResult() {
    return result;
  }
  public void setResult(Object result) {
    this.result = result;
  }

  public int getAtdID() {
    return atdID;
  }
  public void setAtdID(int atdID) {
    this.atdID = atdID;
  }
  public int getFailLineNo() {
    return failLineNo;
  }
  public void setFailLineNo(int failLineNo) {
    this.failLineNo = failLineNo;
  }
    
 
}