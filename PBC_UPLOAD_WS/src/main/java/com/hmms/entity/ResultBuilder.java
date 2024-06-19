package com.hmms.entity;

public class ResultBuilder {
	public static Result buildResult() {
		Result result = new Result();
		return result;
	}

	public static Result buildResult(int ErrorCode, String msg) {
		Result result = new Result();
		result.setErrorCode(ErrorCode);
		result.setMsg(msg);
		return result;
	}

	public static Result buildResult(String msg) {
		Result result = new Result();
		result.setMsg(msg);
		return result;
	}

	public static Result buildSuccessResult() {
		Result result = new Result();
		result.setErrorCode(1200);
		return result;
	}

	public static Result buildSuccessResult(Object data) {
		Result result = new Result();
		// result.setErrorCode(1200).setResult(data);
		result.setErrorCode(1200);
		result.setResult(data);
		return result;
	}

	public static Result buildSuccessResult(Object data, int total) {
		Result result = new Result();
		// result.setErrorCode(1200).setResult(data).setTotal(total);
		result.setResult(data);
		result.setErrorCode(1200);
		result.setTotal(total);
		return result;
	}

	public static Result buildServerFailResult(String msg) {
		Result result = new Result();
		// result.setErrorCode(1500).setMsg(msg);
		result.setErrorCode(1500);
		result.setMsg(msg);
		return result;
	}


	//20220714   add
	public static Result buildServerFailResult(int atdID, String msg, int failLineNo, int total) {
		Result result = new Result();
		result.setErrorCode(1500);
		result.setAtdID(atdID);
		result.setMsg(msg);
		result.setFailLineNo(failLineNo);
		result.setTotal(total);
		return result;
	}

	//20220714   add
	public static Result buildSuccessResult(int atdID, String msg, int total) {
		Result result = new Result();
		// result.setErrorCode(1200).setResult(data).setTotal(total);
		result.setErrorCode(1200);
		result.setAtdID(atdID);
		result.setMsg(msg);
		result.setTotal(total);
		return result;
	}


	//20220721 add
	public static Result buildServerFail1600Result(String msg) {
		Result result = new Result();
		result.setErrorCode(1600);
		result.setMsg(msg);
		return result;
	}
}