package com.hmms.entity;

//Transaction Date	Payment Ref	Effective Date	Tx. Type	Tx. Subtype	DR/ CR	Debit	Credit	Comment
public class TransactionModel {

	private int rowIndex;

	private int htcId;

	private String txDate;

	private String payRef;

	private String effectiveDate;

	private String txType;

	private String txSubType;

	/**
	 * DR/CR
	 */
	private String drOrCr;

	private Double debit;

	private Double credit;

	private String comment;

	public int getHtcId() {
		return htcId;
	}

	public void setHtcId(int htcId) {
		this.htcId = htcId;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public String getTxDate() {
		return txDate;
	}

	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}

	public String getPayRef() {
		return payRef;
	}

	public void setPayRef(String payRef) {
		this.payRef = payRef;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getTxType() {
		return txType;
	}

	public void setTxType(String txType) {
		this.txType = txType;
	}

	public String getTxSubType() {
		return txSubType;
	}

	public void setTxSubType(String txSubType) {
		this.txSubType = txSubType;
	}

	public String getDrOrCr() {
		return drOrCr;
	}

	public void setDrOrCr(String drOrCr) {
		this.drOrCr = drOrCr;
	}

	public Double getDebit() {
		return debit;
	}

	public void setDebit(Double debit) {
		this.debit = debit;
	}

	public Double getCredit() {
		return credit;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
