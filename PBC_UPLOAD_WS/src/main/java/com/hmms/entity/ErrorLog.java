package com.hmms.entity;

public class ErrorLog {
	private int rowNo;

	private String rowSummary;

	private String errorMsg;
 
	public String getRowSummary() {
		return rowSummary;
	}

	public void setRowSummary(String rowSummary) {
		this.rowSummary = rowSummary;
	}

	public int getRowNo() {
		return this.rowNo;
	}

	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
