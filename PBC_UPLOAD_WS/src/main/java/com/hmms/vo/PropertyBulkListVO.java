package com.hmms.vo;

public class PropertyBulkListVO {

        private String rn;
        private String proRefNo;
        private String eleCode;
        private String eleValueType;
        private String eleValue;
        private String isChargeAble;
        
        private String eleType;
        private String eleDescription;
        private String attCode;
        private String dateValue;
        private String numericValue;
        private String chargeableInd;
        private String startDate;
        
        private String endDate;
        private String comment;
        
        private String isChange;   //Y-Changed   null or N - unchange
        
        public String getIsChange() {
            return isChange;
        }
        public void setIsChange(String isChange) {
            this.isChange = isChange;
        }
    
        public String getComment() {
            return comment;
        }
    
        public void setComment(String comment) {
            this.comment = comment;
        }
    
        public String getRn() {
            return rn;
        }
    
        public String getProRefNo() {
            return proRefNo;
        }
    
        public void setProRefNo(String proRefNo) {
            this.proRefNo = proRefNo;
        }
    
        public void setRn(String rn) {
            this.rn = rn;
        }
    
    
        public String getEleCode() {
            return eleCode;
        }
    
        public void setEleCode(String eleCode) {
            this.eleCode = eleCode;
        }
    
        public String getEleValueType() {
            return eleValueType;
        }
    
        public void setEleValueType(String eleValueType) {
            this.eleValueType = eleValueType;
        }
    
        public String getEleValue() {
            return eleValue;
        }
    
        public void setEleValue(String eleValue) {
            this.eleValue = eleValue;
        }
    
        public String getIsChargeAble() {
            return isChargeAble;
        }
    
        public void setIsChargeAble(String isChargeAble) {
            this.isChargeAble = isChargeAble;
        }
    
        public String getEleType() {
            return eleType;
        }
    
        public void setEleType(String eleType) {
            this.eleType = eleType;
        }
    
        public String getEleDescription() {
            return eleDescription;
        }
    
        public void setEleDescription(String eleDescription) {
            this.eleDescription = eleDescription;
        }
    
        public String getAttCode() {
            return attCode;
        }
    
        public void setAttCode(String attCode) {
            this.attCode = attCode;
        }
    
        public String getDateValue() {
            return dateValue;
        }
    
        public void setDateValue(String dateValue) {
            this.dateValue = dateValue;
        }
    
        public String getNumericValue() {
            return numericValue;
        }
    
        public void setNumericValue(String numericValue) {
            this.numericValue = numericValue;
        }
    
        public String getChargeableInd() {
            return chargeableInd;
        }
    
        public void setChargeableInd(String chargeableInd) {
            this.chargeableInd = chargeableInd;
        }
    
        public String getStartDate() {
            return startDate;
        }
    
        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }
    
        public String getEndDate() {
            return endDate;
        }
    
        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }
    
        
        
        
    //	pe.setRn(rs.getString(1));
    //	pe.setProRefNo(rs.getString(2));
    //	pe.setEleCode(rs.getString(3));
    //	pe.setEleValueType(rs.getString(4));
    //	pe.setEleValue(rs.getString(5));
    //	pe.setIsChargeAble(rs.getString(6));
    //	
    //	pe.setEleType(rs.getString(7));
    //	pe.setEleDescription(rs.getString(8));
    //	
    //	
    //	pe.setAttCode(rs.getString(9));
    //	pe.setDateValue(rs.getString(10));
    //	pe.setNumericValue(rs.getString(11));
    //	pe.setChargeableInd(rs.getString(12));
    //	pe.setStartDate(rs.getString(13));
    //	pe.setEndDate(rs.getString(14));
    
    
}
