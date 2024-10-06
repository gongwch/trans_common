package jp.co.ais.trans.master.logic.impl;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * ユーザ認証マスタロジック
 */
public class UserAuthLogicImpl implements UserAuthLogic {

	/** パスワード管理マスタDao */
	private USR_AUTH_MSTDao dao;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao USR_AUTH_MSTDao
	 */
	public UserAuthLogicImpl(USR_AUTH_MSTDao dao) {
		this.dao = dao;
	}

	/**
	 * データ検索.<br>
	 * 該当データが存在しない場合は、nullが返る.
	 * 
	 * @param kaiCode 会社コード
	 * @return データ.無い場合はnull
	 */
	public USR_AUTH_MST find(String kaiCode) {

		return dao.findByKaiCode(kaiCode);
	}

	/**
	 * データを更新する.<br>
	 * 既にデータが存在する場合は、更新日時を入れて更新.<br>
	 * データが存在しない場合は、作成日時を入れて新規追加.
	 * 
	 * @param authDto 対象データ
	 */
	public void update(USR_AUTH_MST authDto) {

		// 存在チェック
		USR_AUTH_MST oldDto = find(authDto.getKAI_CODE());

		if (oldDto == null) {

			// 登録日付の設定
			authDto.setINP_DATE(Util.getCurrentDate());

			// 更新日付をNULLにする
			authDto.setUPD_DATE(null);

			dao.insert(authDto);

		} else {
			// 変わらない項目の値を設定する
			authDto.setINP_DATE(oldDto.getINP_DATE());

			// 更新日付の設定
			authDto.setUPD_DATE(Util.getCurrentDate());

			dao.update(authDto);
		}
	}
}
