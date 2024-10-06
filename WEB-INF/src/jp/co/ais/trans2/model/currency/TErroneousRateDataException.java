package jp.co.ais.trans2.model.currency;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.common.exception.*;

/**
 * レート一括取込不正レートデータException
 * 
 * @author AIS
 */
public class TErroneousRateDataException extends TWarningException {

	/** エラーリスト */
	protected List<ErrorContent> errorList = null;

	/** エラー */
	protected ErrorContent error = null;

	/** エラー */
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

		/** ファイル名称 */
		protected String fileName = null;

		/** ファイル取込日時 */
		protected Date taleInTime = null;

		/** エラー種類 */
		protected RateError errorType = null;

		/** エラーの発生したデータの種類 */
		protected RateError detaType = null;

		/** エラーの発生行 */
		protected int rows = 0;

		/**
		 * エラー種類を返す
		 * 
		 * @return errorType エラー種類
		 */
		public RateError getErrorType() {
			return this.errorType;
		}

		/**
		 * エラー種類を設定する
		 * 
		 * @param errorType
		 */
		public void setErrorType(RateError errorType) {
			this.errorType = errorType;
		}

		/**
		 * エラーの発生したデータの種類を返す
		 * 
		 * @return detaType エラーの発生したデータの種類
		 */
		public RateError getDetaType() {
			return this.detaType;
		}

		/**
		 * エラーの発生したデータの種類を設定する
		 * 
		 * @param detaType
		 */
		public void setDetaType(RateError detaType) {
			this.detaType = detaType;
		}

		/**
		 * ファイル名を返す
		 * 
		 * @return fileName
		 */
		public String getFileName() {
			return fileName;
		}

		/**
		 * ファイル名を設定する
		 * 
		 * @param fileName
		 */
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		/**
		 * 取込日時を返す
		 * 
		 * @return taleInTime
		 */
		public Date getTaleInTime() {
			return taleInTime;
		}

		/**
		 * 取込日時を設定する
		 * 
		 * @param taleInTime
		 */
		public void setTaleInTime(Date taleInTime) {
			this.taleInTime = taleInTime;
		}

		/**
		 * エラーの発生行を返す
		 * 
		 * @return rows エラーの発生行
		 */
		public int getRows() {
			return this.rows;
		}

		/**
		 * エラーの発生行を設定する
		 * 
		 * @param rows
		 */
		public void setRows(int rows) {
			this.rows = rows;
		}

	}

	/**
	 * エラーを返す
	 * 
	 * @return エラー
	 */
	public ErrorContent getError() {
		return error;
	}

	/**
	 * エラーをセットする
	 * 
	 * @param error エラーリスト
	 */
	public void setError(ErrorContent error) {
		this.error = error;
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
	 * エラーを追加する
	 * 
	 * @param content エラー
	 */
	public void addErrorContent(ErrorContent content) {
		if (errorList == null) {
			errorList = new ArrayList<ErrorContent>();
		}
		errorList.add(content);
	}

	/**
	 * レートのエラー
	 */
	public enum RateError {

		// エラー種類
		/** ファイル形式が不正 */
		FILE(0),

		/** 型が不正 */
		TYPE(1),

		/** 値が不正 */
		VALUE(2),

		/** 項目過不足 */
		NULL(3),

		/** ファイル内部のデータの存在 */
		EXISTENCE_FILE(4),

		/** DB内部のデータの存在 */
		EXISTENCE_DB(5),

		// データ種類
		/** 区分 */
		DIVISION(6),

		/** 適用開始日 */
		TERM_FROM(7),

		/** 通貨 */
		CURRENCY(8),

		/** レート */
		RATE(8);

		/** 値 */
		public int value;

		/**
		 * コンストラクタ.
		 * 
		 * @param value 値
		 */
		private RateError(int value) {
			this.value = value;
		}

		/**
		 * レートエラーを返す
		 * 
		 * @param error
		 * @return 値
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
