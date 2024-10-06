package jp.co.ais.trans2.model.save;

import java.io.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;

/**
 * オブジェクト保存
 */
public class OBJ_SAVE extends TransferBase {

	/** 会社コード */
	protected String kAI_CODE;

	/** ユーザID */
	protected String uSR_ID;

	/** プログラムID */
	protected String pRG_ID;

	/** プログラム枝番 */
	protected Integer sEQ;

	/** 保存キー */
	protected String kEY;

	/** 保存オブジェクト */
	protected byte[] oBJECT;

	/** オブジェクト */
	protected Object saveObject;

	/**
	 * 会社コード
	 * 
	 * @return 会社コード
	 */
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	/**
	 * 会社コード
	 * 
	 * @param kai_code 会社コード
	 */
	public void setKAI_CODE(String kai_code) {
		kAI_CODE = kai_code;
	}

	/**
	 * 保存キー
	 * 
	 * @return 保存キー
	 */
	public String getKEY() {
		return kEY;
	}

	/**
	 * 保存キー
	 * 
	 * @param key 保存キー
	 */
	public void setKEY(String key) {
		kEY = key;
	}

	/**
	 * プログラムID
	 * 
	 * @return プログラムID
	 */
	public String getPRG_ID() {
		return pRG_ID;
	}

	/**
	 * プログラムID
	 * 
	 * @param prg_id プログラムID
	 */
	public void setPRG_ID(String prg_id) {
		pRG_ID = prg_id;
	}

	/**
	 * プログラム枝番
	 * 
	 * @return プログラム枝番
	 */
	public Integer getSEQ() {
		return sEQ;
	}

	/**
	 * プログラム枝番
	 * 
	 * @param seq プログラム枝番
	 */
	public void setSEQ(Integer seq) {
		sEQ = seq;
	}

	/**
	 * ユーザID
	 * 
	 * @return ユーザID
	 */
	public String getUSR_ID() {
		return uSR_ID;
	}

	/**
	 * ユーザID
	 * 
	 * @param usr_id ユーザID
	 */
	public void setUSR_ID(String usr_id) {
		uSR_ID = usr_id;
	}

	/**
	 * オブジェクト(文字列)
	 * 
	 * @return oBJECT オブジェクト(文字列)
	 */
	public byte[] getOBJECT() {
		return oBJECT;
	}

	/**
	 * オブジェクト(文字列)
	 * 
	 * @param object オブジェクト(文字列)
	 */
	public void setOBJECT(byte[] object) {
		oBJECT = object;

		try {
			if (object != null) {
				this.saveObject = ResourceUtil.toObject(ResourceUtil.toBinaryInZip(object));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * オブジェクトの取得
	 * 
	 * @return オブジェクト
	 */
	public Object getSaveObject() {
		return saveObject;
	}

	/**
	 * オブジェクトセット
	 * 
	 * @param obj オブジェクト
	 */
	public void setSaveObject(Object obj) {
		this.saveObject = obj;

		try {
			if (obj != null) {
				this.oBJECT = ResourceUtil.zipBinary("bytes", ResourceUtil.toBinarry(obj));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
