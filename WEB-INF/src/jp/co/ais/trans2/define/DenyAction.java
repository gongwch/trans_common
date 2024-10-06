package jp.co.ais.trans2.define;

import jp.co.ais.trans2.common.gui.*;

/**
 * 否認時アクション
 */
public enum DenyAction implements TEnumRadio {

	/** 通常(ひとつ前) */
	BACK_ONE,
	/** 最初に戻る */
	BACK_FIRST;

	/**
	 * 名称取得
	 */
	public String getName() {
		switch (this) {
			case BACK_FIRST:
				return "初め";
			case BACK_ONE:
				return "ひとつ前";
		}
		return null;
	}

}
