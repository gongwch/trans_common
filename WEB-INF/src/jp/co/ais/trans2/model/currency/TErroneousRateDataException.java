package jp.co.ais.trans2.model.currency;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.common.exception.*;

/**
 * ���[�g�ꊇ�捞�s�����[�g�f�[�^Exception
 * 
 * @author AIS
 */
public class TErroneousRateDataException extends TWarningException {

	/** �G���[���X�g */
	protected List<ErrorContent> errorList = null;

	/** �G���[ */
	protected ErrorContent error = null;

	/** �G���[ */
	public class ErrorContent extends TransferBase {

		/**
		 * @param errorType
		 * @param detaType
		 * @param rows
		 */
		public ErrorContent(RateError errorType, RateError detaType, int rows) {
			setErrorType(errorType);
			setDetaType(detaType);
			setRows(rows);

		}

		/** �t�@�C������ */
		protected String fileName = null;

		/** �t�@�C���捞���� */
		protected Date taleInTime = null;

		/** �G���[��� */
		protected RateError errorType = null;

		/** �G���[�̔��������f�[�^�̎�� */
		protected RateError detaType = null;

		/** �G���[�̔����s */
		protected int rows = 0;

		/**
		 * �G���[��ނ�Ԃ�
		 * 
		 * @return errorType �G���[���
		 */
		public RateError getErrorType() {
			return this.errorType;
		}

		/**
		 * �G���[��ނ�ݒ肷��
		 * 
		 * @param errorType
		 */
		public void setErrorType(RateError errorType) {
			this.errorType = errorType;
		}

		/**
		 * �G���[�̔��������f�[�^�̎�ނ�Ԃ�
		 * 
		 * @return detaType �G���[�̔��������f�[�^�̎��
		 */
		public RateError getDetaType() {
			return this.detaType;
		}

		/**
		 * �G���[�̔��������f�[�^�̎�ނ�ݒ肷��
		 * 
		 * @param detaType
		 */
		public void setDetaType(RateError detaType) {
			this.detaType = detaType;
		}

		/**
		 * �t�@�C������Ԃ�
		 * 
		 * @return fileName
		 */
		public String getFileName() {
			return fileName;
		}

		/**
		 * �t�@�C������ݒ肷��
		 * 
		 * @param fileName
		 */
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		/**
		 * �捞������Ԃ�
		 * 
		 * @return taleInTime
		 */
		public Date getTaleInTime() {
			return taleInTime;
		}

		/**
		 * �捞������ݒ肷��
		 * 
		 * @param taleInTime
		 */
		public void setTaleInTime(Date taleInTime) {
			this.taleInTime = taleInTime;
		}

		/**
		 * �G���[�̔����s��Ԃ�
		 * 
		 * @return rows �G���[�̔����s
		 */
		public int getRows() {
			return this.rows;
		}

		/**
		 * �G���[�̔����s��ݒ肷��
		 * 
		 * @param rows
		 */
		public void setRows(int rows) {
			this.rows = rows;
		}

	}

	/**
	 * �G���[��Ԃ�
	 * 
	 * @return �G���[
	 */
	public ErrorContent getError() {
		return error;
	}

	/**
	 * �G���[���Z�b�g����
	 * 
	 * @param error �G���[���X�g
	 */
	public void setError(ErrorContent error) {
		this.error = error;
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

	/**
	 * ���[�g�̃G���[
	 */
	public enum RateError {

		// �G���[���
		/** �t�@�C���`�����s�� */
		FILE(0),

		/** �^���s�� */
		TYPE(1),

		/** �l���s�� */
		VALUE(2),

		/** ���ډߕs�� */
		NULL(3),

		/** �t�@�C�������̃f�[�^�̑��� */
		EXISTENCE_FILE(4),

		/** DB�����̃f�[�^�̑��� */
		EXISTENCE_DB(5),

		// �f�[�^���
		/** �敪 */
		DIVISION(6),

		/** �K�p�J�n�� */
		TERM_FROM(7),

		/** �ʉ� */
		CURRENCY(8),

		/** ���[�g */
		RATE(8);

		/** �l */
		public int value;

		/**
		 * �R���X�g���N�^.
		 * 
		 * @param value �l
		 */
		private RateError(int value) {
			this.value = value;
		}

		/**
		 * ���[�g�G���[��Ԃ�
		 * 
		 * @param error
		 * @return �l
		 */
		public static RateError getRateError(int error) {
			for (RateError em : values()) {
				if (em.value == error) {
					return em;
				}
			}

			return null;
		}

	}

}
