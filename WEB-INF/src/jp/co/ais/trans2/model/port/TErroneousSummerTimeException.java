package jp.co.ais.trans2.model.port;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.common.exception.*;

/**
 * サマータイムマスタチェックエラー
 */
public class TErroneousSummerTimeException extends TWarningException {

	/** エラーリスト */
	protected List<ErrorContent> errorList = null;

	/**
	 * エラー内容
	 */
	public class ErrorContent extends TransferBase {

		/** データ */
		protected OP_SMR_TIME_MST smr;

		/** ファイル取込日時 */
		protected Date taleInTime = null;

		/** 行番号 */
		protected int rowNo = -1;

		/** セルID */
		protected String cellID = null;

		/** エラー内容 */
		protected String error = null;

		/**
		 * コンストラクタ.
		 * 
		 * @param smr データ
		 * @param dt ファイル取込日時
		 * @param error エラー内容
		 */
		public ErrorContent(OP_SMR_TIME_MST smr, Date dt, String error) {
			this.smr = smr;

			setTaleInTime(dt);
			setError(error);
		}

		/**
		 * データ
		 * 
		 * @return データ
		 */
		public OP_SMR_TIME_MST getOP_SMR_TIME_MST() {
			return smr;
		}

		/**
		 * ファイル取込日時
		 * 
		 * @return ファイル取込日時
		 */
		public Date getTaleInTime() {
			return taleInTime;
		}

		/**
		 * ファイル取込日時
		 * 
		 * @param taleInTime ファイル取込日時
		 */
		public void setTaleInTime(Date taleInTime) {
			this.taleInTime = taleInTime;
		}

		/**
		 * 行番号の取得
		 * 
		 * @return rowNo 行番号
		 */
		public int getRowNo() {
			return rowNo;
		}

		/**
		 * 行番号の設定
		 * 
		 * @param rowNo 行番号
		 */
		public void setRowNo(int rowNo) {
			this.rowNo = rowNo;
		}

		/**
		 * セルIDの取得
		 * 
		 * @return cellID セルID
		 */
		public String getCellID() {
			return cellID;
		}

		/**
		 * セルIDの設定
		 * 
		 * @param cellID セルID
		 */
		public void setCellID(String cellID) {
			this.cellID = cellID;
		}

		/**
		 * エラー内容
		 * 
		 * @return エラー内容
		 */
		public String getError() {
			return error;
		}

		/**
		 * エラー内容
		 * 
		 * @param error エラー内容
		 */
		public void setError(String error) {
			this.error = error;
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

}
