package jp.co.ais.trans.common.bizui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.Util;

/**
 * 科目ユニット範囲指定フィールドです。
 * @author AIS
 *
 */
public class TTAccountItemUnitRangeField extends TPanel {
	
	/** シリアルID */
	private static final long serialVersionUID = 915374992893282388L;

	/** 科目体系コード */
	protected String itemSystem = "";

	/** 科目ユニット開始 */
	public TAccountItemUnit beginField;

	/** 科目ユニット終了 */
	public TAccountItemUnit endField;

	public TTAccountItemUnitRangeField() {
		initComponents();
	}

	/**
	 * コンポーネントの初期生成
	 *
	 */
	protected void initComponents() {
		
		GridBagConstraints gridBagConstraints;

		setLayout(new GridBagLayout());

		beginField = new TAccountItemUnit();
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		add(beginField, gridBagConstraints);

		endField = new TAccountItemUnit();
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		add(endField, gridBagConstraints);

	}

	/**
	 * 開始 <= 終了の場合true、それ以外はfalseを返します。
	 * @return
	 */
	public boolean isEndValueLargerThanBeginValue() {

		// 開始科目、終了科目のいずれかが未入力の場合はチェックなし
		if (Util.isNullOrEmpty(beginField.getItemCode()) || Util.isNullOrEmpty(endField.getItemCode())) {
			return true;

		// 開始科目 < 終了科目の場合OK
		} else if (endField.getItemCode().compareTo(beginField.getItemCode()) >= 0) {
			return true;

		// 開始科目 = 終了科目の場合、補助科目を比較する
		} else if (endField.getItemCode().compareTo(beginField.getItemCode()) == 0) {

			// 開始補助科目、終了補助科目のいずれかが未入力の場合はチェックなし
			if (Util.isNullOrEmpty(beginField.getSubItemCode()) || Util.isNullOrEmpty(endField.getSubItemCode())) {
				return true;

			// 開始補助科目 < 終了補助科目の場合OK
			} else if (endField.getSubItemCode().compareTo(beginField.getSubItemCode()) >= 0) {
				return true;

			// 開始補助科目 = 終了補助科目の場合、内訳科目を比較する
			} else if (endField.getSubItemCode().compareTo(beginField.getSubItemCode()) == 0) {

				// 開始内訳科目、終了内訳科目のいずれかが未入力の場合はチェックなし
				if (Util.isNullOrEmpty(beginField.getBreakDownItemCode()) ||
						Util.isNullOrEmpty(endField.getBreakDownItemCode())) {
					return true;

				// 開始内訳科目 < 終了内訳科目の場合OK
				} else if (endField.getBreakDownItemCode().compareTo(beginField.getBreakDownItemCode()) >= 0) {
					return true;
				}

			}

		}

		return false;
	}

}
