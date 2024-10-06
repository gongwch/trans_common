package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.code.*;

/**
 * コードコンボボックスのコントローラ
 * 
 * @author AIS
 */
public class TCodeComboBoxController extends TController {

	/** コンボボックス */
	protected TCodeComboBox comboBox;

	/** OP_CODE_MSTリスト */
	protected List<OP_CODE_MST> codeList;

	/**
	 * @param comboBox フィールド
	 */
	public TCodeComboBoxController(TCodeComboBox comboBox) {

		this.comboBox = comboBox;

		init();

	}

	/**
	 * 初期化
	 */
	protected void init() {

		// コンボボックス生成
		getList();

	}

	/**
	 * コンボボックスのリストを返す
	 */
	protected void getList() {

		try {

			comboBox.getComboBox().removeAllItems();

			List<OP_CODE_MST> list = OPLoginUtil.getCodeMstList(comboBox.isLocal(), comboBox.getCodeDivision(),
				comboBox.getCodes());

			if (this.comboBox.isAddBlank()) {
				comboBox.getComboBox().addTextValueItem(null, "");
			}
			if (list != null) {
				for (OP_CODE_MST bean : list) {
					addItem(bean);
				}
			}
			// 退避
			codeList = list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * コンボボックス選択肢を追加する
	 * 
	 * @param bean
	 */
	protected void addItem(OP_CODE_MST bean) {
		comboBox.getComboBox().addTextValueItem(bean, bean.getCODE_NAME());
	}

	/**
	 * コードからコンボボックス選択肢を追加する<br>
	 * 該当コードが非存在の場合追加しない
	 * 
	 * @param code
	 */
	public void addItem(String code) {
		OP_CODE_MST bean = OPLoginUtil.getCodeMst(comboBox.isLocal(), comboBox.getCodeDivision(), code);
		if (bean != null) {
			addItem(bean);
		}
	}

	/**
	 * @return ServletのClassを返す
	 */
	protected Class getModelClass() {
		return CodeManager.class;
	}

	/**
	 * @see jp.co.ais.trans.common.client.TPanelCtrlBase#getPanel()
	 */
	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * OPコード情報を返す
	 * 
	 * @param code コード
	 * @return OP_CODE_MST
	 */
	protected OP_CODE_MST getCodeMst(String code) {
		if (codeList == null) {
			return null;
		}
		if (comboBox.isLocal()) {
			for (OP_CODE_MST bean : codeList) {
				if (bean.getCODE_NAME().equals(Util.avoidNull(code))) {
					return bean;
				}
			}
		}
		
		for (OP_CODE_MST bean : codeList) {
			if (bean.getCODE().equals(Util.avoidNull(code))) {
				return bean;
			}
		}

		return null;
	}

}
