package jp.co.ais.trans2.model.port;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.common.exception.*;

/**
 * �T�}�[�^�C���}�X�^�`�F�b�N�G���[
 */
public class TErroneousSummerTimeException extends TWarningException {

	/** �G���[���X�g */
	protected List<ErrorContent> errorList = null;

	/**
	 * �G���[���e
	 */
	public class ErrorContent extends TransferBase {

		/** �f�[�^ */
		protected OP_SMR_TIME_MST smr;

		/** �t�@�C���捞���� */
		protected Date taleInTime = null;

		/** �s�ԍ� */
		protected int rowNo = -1;

		/** �Z��ID */
		protected String cellID = null;

		/** �G���[���e */
		protected String error = null;

		/**
		 * �R���X�g���N�^.
		 * 
		 * @param smr �f�[�^
		 * @param dt �t�@�C���捞����
		 * @param error �G���[���e
		 */
		public ErrorContent(OP_SMR_TIME_MST smr, Date dt, String error) {
			this.smr = smr;

			setTaleInTime(dt);
			setError(error);
		}

		/**
		 * �f�[�^
		 * 
		 * @return �f�[�^
		 */
		public OP_SMR_TIME_MST getOP_SMR_TIME_MST() {
			return smr;
		}

		/**
		 * �t�@�C���捞����
		 * 
		 * @return �t�@�C���捞����
		 */
		public Date getTaleInTime() {
			return taleInTime;
		}

		/**
		 * �t�@�C���捞����
		 * 
		 * @param taleInTime �t�@�C���捞����
		 */
		public void setTaleInTime(Date taleInTime) {
			this.taleInTime = taleInTime;
		}

		/**
		 * �s�ԍ��̎擾
		 * 
		 * @return rowNo �s�ԍ�
		 */
		public int getRowNo() {
			return rowNo;
		}

		/**
		 * �s�ԍ��̐ݒ�
		 * 
		 * @param rowNo �s�ԍ�
		 */
		public void setRowNo(int rowNo) {
			this.rowNo = rowNo;
		}

		/**
		 * �Z��ID�̎擾
		 * 
		 * @return cellID �Z��ID
		 */
		public String getCellID() {
			return cellID;
		}

		/**
		 * �Z��ID�̐ݒ�
		 * 
		 * @param cellID �Z��ID
		 */
		public void setCellID(String cellID) {
			this.cellID = cellID;
		}

		/**
		 * �G���[���e
		 * 
		 * @return �G���[���e
		 */
		public String getError() {
			return error;
		}

		/**
		 * �G���[���e
		 * 
		 * @param error �G���[���e
		 */
		public void setError(String error) {
			this.error = error;
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
	 * �G���[��ǉ�����
	 * 
	 * @param content �G���[
	 */
	public void addErrorContent(ErrorContent content) {
		if (errorList == null) {
			errorList = new ArrayList<ErrorContent>();
		}
		errorList.add(content);
	}

}
