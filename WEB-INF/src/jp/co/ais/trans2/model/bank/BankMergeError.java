package jp.co.ais.trans2.model.bank;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.common.exception.*;

/**
 * ��s���p���G���[���e
 */
public class BankMergeError extends TWarningException {

	/** �G���[���X�g */
	protected List<ErrorContent> errorList = null;
	
	/** �G���[�����s */
	protected List<ErrorContent> errorLine = null;

	/** �G���[ */
	public class ErrorContent extends TransferBase {

		/**
		 * @param line
		 * @param str
		 */
		public ErrorContent(String line, String str) {

			setLine(line);
			setError(str);

		}

		/** �s */
		protected String line;

		/** �G���[���e */
		protected String error = null;

		/**
		 * �G���[���e��Ԃ�
		 * 
		 * @return error
		 */
		public String getError() {
			return error;
		}

		/**
		 * �G���[���e��ݒ肷��
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
	 * �G���[���X�g��Ԃ�
	 * 
	 * @return �G���[���X�g
	 */
	public List<ErrorContent> getErrorList() {
		return errorList;
	}

	/**
	 * �G���[���X�g���Z�b�g����
	 * 
	 * @param errorList �G���[���X�g
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
	 * �G���[��ǉ�����
	 * 
	 * @param content �G���[
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
