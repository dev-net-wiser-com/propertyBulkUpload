package com.hmms.model;

import java.util.Date;

public class AttachmentDetailBean {

	private Long id;
	
	private String refCode;

    private Long atcId;

    private String fileDisplayName;

    private String fileSysName;

    private String fileDownloadLink;

    private String remark;

    private Short isDeletedByUser;

    private String deletedByUser;

    private Date deletedByDate;

    private Date deletedBySysDate;

    private String createdBy;

    private Date createdDate;

    private String modifiedBy;

    private Date modifiedDate;


    public Long getId() {
		return id;
	}


	public Long getAtcId() {
		return atcId;
	}


	public void setAtcId(Long atcId) {
		this.atcId = atcId;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public String getFileDisplayName() {
        return fileDisplayName;
    }

    public void setFileDisplayName(String fileDisplayName) {
        this.fileDisplayName = fileDisplayName;
    }

    public String getFileSysName() {
        return fileSysName;
    }

    public void setFileSysName(String FileSysName) {
        this.fileSysName = FileSysName;
    }

    public String getFileDownloadLink() {
        return fileDownloadLink;
    }

    public void setFileDownloadLink(String FileDownloadLink) {
        this.fileDownloadLink = FileDownloadLink;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Short getIsDeletedByUser() {
        return isDeletedByUser;
    }

    public void setIsDeletedByUser(Short isDeletedByUser) {
        this.isDeletedByUser = isDeletedByUser;
    }

    public String getDeletedByUser() {
        return deletedByUser;
    }

    public void setDeletedByUser(String deletedByUser) {
        this.deletedByUser = deletedByUser;
    }

    public Date getDeletedByDate() {
        return deletedByDate;
    }

    public void setDeletedByDate(Date deletedByDate) {
        this.deletedByDate = deletedByDate;
    }

    public Date getDeletedBySysDate() {
        return deletedBySysDate;
    }

    public void setDeletedBySysDate(Date DeletedBySysDate) {
        this.deletedBySysDate = DeletedBySysDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

	public String getRefCode() {
		return refCode;
	}

	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}
}
