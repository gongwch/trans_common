package jp.co.ais.trans2.common.model.ui;

import javax.swing.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * 検索コンポーネント(ブランク)のコントローラ
 * 
 * @author AIS
 */
public class TNoneReferenceController extends TReferenceController {

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public TNoneReferenceController(TReference field) {
		super(field);
	}

	@Override
	public void init() {
		super.init();
		setEditable(false);
	}

	@Override
	public boolean code_Verify(@SuppressWarnings("unused") JComponent comp) {
		return false;
	}

	@Override
	public void btnSearch_Click() {
		//		
	}

	@Override
	public void btnSettle_Click() {
		//
	}

	@Override
	public String getDialogCaption() {
		return null;
	}

	@Override
	public String getButtonCaption() {
		return null;
	}

	@Override
	public String getTableKeyName() {
		return null;
	}

	/**
	 * 存在チェックをしない場合の入力途中未確定Entityを返す
	 * @return 存在チェックをしない場合の入力途中未確定Entity
	 */
	public Object getUnspecifiedEntity() {
		return null;
	}
}
