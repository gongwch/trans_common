package jp.co.ais.trans2.model.bank;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.common.exception.*;

/**
 * 銀行統廃合エラー内容
 */
public class BankMergeError extends TWarningException {

	/** エラーリスト */
	protected List<ErrorContent> errorList = null;
	
	/** エラー発生行 */
	protected List<ErrorContent> errorLine = null;

	/** エラー */
	public class ErrorContent extends TransferBase {

		/**
		 * @param line
		 * @param str
		 */
		public ErrorContent(String line, String str) {

			setLine(line);
			setError(str);

		}

		/** 行 */
		protected String line;

		/** エラー内容 */
		protected String error = null;

		/**
		 * エラー内容を返す
		 * 
		 * @return error
		 */
		public String getError() {
			return error;
		}

		/**
		 * エラー内容を設定する
		 * 
		 * @param error
		 */
		public void setError(String error) {
			this.error = error;
		}

		/**
		 * @return taleInTime
		 */
		public String getLine() {
			return line;
		}

		/**
		 * @param line
		 */
		public void setLine(String line) {
			this.line = line;
		}
	}

	/**
	 * エラーリストを返す
	 * 
	 * @return エラーリスト
	 */
	public List<ErrorContent> getErrorList() {
		return errorList;
	}

	/**
	 * エラーリストをセットする
	 * 
	 * @param errorList エラーリスト
	 */
	public void setErrorList(List<ErrorContent> errorList) {
		this.errorList = errorList;
	}

	/**
	 * @return errorLine
	 */
	public List<ErrorContent> getErrorLine(){
		return errorLine;
	}
	
	/**
	 * @param errorLine
	 */
	public void setErrorLine(List<ErrorContent> errorLine){
		this.errorLine = errorLine;
	}
	/**
	 * エラーを追加する
	 * 
	 * @param content エラー
	 */
	public void addErrorContent(ErrorContent content) {
		if (errorList == null) {
			errorList = new ArrayList<ErrorContent>();
		}else if(errorLine == null){
			errorLine = new ArrayList<ErrorContent>();
		}
		errorList.add(content);
	}


}
