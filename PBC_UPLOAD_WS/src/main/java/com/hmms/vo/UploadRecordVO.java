package com.hmms.vo;

public class UploadRecordVO {
	String id;
    String runId;
    String status;
    String message;
    String fileSysPath;
    String fileOrgName;
    String fileSysName;

	//20220324
	String uploadDate;
	String uploadBy;

	//20220728
	String total;
	String success;
	String fail;


	
	
	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getFail() {
		return fail;
	}

	public void setFail(String fail) {
		this.fail = fail;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getUploadBy() {
		return uploadBy;
	}

	public void setUploadBy(String uploadBy) {
		this.uploadBy = uploadBy;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRunId() {
		return this.runId;
	}

	public void setRunId(String runId) {
		this.runId = runId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFileSysPath() {
		return this.fileSysPath;
	}

	public void setFileSysPath(String fileSysPath) {
		this.fileSysPath = fileSysPath;
	}

	public String getFileOrgName() {
		return this.fileOrgName;
	}

	public void setFileOrgName(String fileOrgName) {
		this.fileOrgName = fileOrgName;
	}

	public String getFileSysName() {
		return this.fileSysName;
	}

	public void setFileSysName(String fileSysName) {
		this.fileSysName = fileSysName;
	}


}
