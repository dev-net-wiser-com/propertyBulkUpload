package com.hmms;

public class Constants {
	 
	public enum SUB_TYPE{
		ADC,
		ADJ,
		DRA,
		DRP,
		DRS,
		HBI,
		HBN,
		HBO,
		HBP,
		HBR,
		HBS,
		HBU,
		PAY,
		REP,
		SCR,
		SPO,
		SPS,
		SPU,
		VDA,
		VDL,
		VDS,
		TRFR,
		REDSO;
		
		public static boolean isValidSubType(String type) {
			for(int i=0;i<SUB_TYPE.values().length;i++) {
				if(SUB_TYPE.values()[i].equals(type)) {
					return true;
				}
			}
			return false;
		}
	}
}
