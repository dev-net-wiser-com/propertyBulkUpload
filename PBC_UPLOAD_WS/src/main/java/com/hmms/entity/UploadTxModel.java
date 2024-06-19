package com.hmms.entity;

import java.util.ArrayList;
import java.util.List;

public class UploadTxModel {

	private List<String> errorMsgList;
	
	private String batchRef;
	private List<TransactionModel> txList = new ArrayList<TransactionModel>();

	public String getBatchRef() {
		return batchRef;
	}

	public void setBatchRef(String batchRef) {
		this.batchRef = batchRef;
	}

	public List<TransactionModel> getTxList() {
		return txList;
	}

	public void setTxList(List<TransactionModel> txList) {
		this.txList = txList;
	}

	public List<String> getErrorMsgList() {
		return errorMsgList;
	}

	public void setErrorMsgList(List<String> errorMsgList) {
		this.errorMsgList = errorMsgList;
	}
 
}
