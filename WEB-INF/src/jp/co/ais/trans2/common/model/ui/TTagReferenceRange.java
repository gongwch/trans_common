package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.model.tag.*;

/**
 * 付箋リファレンス検索フィールド
 * 
 * @author AIS
 */
public class TTagReferenceRange extends TPanel {

	/** 付箋1フィールド */
	public TTagReference ctrlTagReference1;

	/** 付箋2フィールド */
	public TTagReference ctrlTagReference2;

	/**
	 *
	 *
	 */
	public TTagReferenceRange() {
		initComponents();
		allocateComponents();
	}

	/**
	 * 初期化
	 */
	public void initComponents() {
		ctrlTagReference1 = new TTagReference();
		ctrlTagReference2 = new TTagReference();
	}

	/**
	 * コンポーネントの配置を行う。
	 */
	public void allocateComponents() {

		setLayout(null);
		setOpaque(false);

		getField1().setLocation(0, 0);
		// 付箋1リファレンス
		getField1().btn.setLangMessageID("CM0084");
		add(getField1());
		getField2().setLocation(0, getField2().getHeight());
		// 付箋2リファレンス
		getField2().btn.setLangMessageID("CM0085");
		add(getField2());

		reSize();

		getField1().setCheckExist(false);
		getField2().setCheckExist(false);
	}

	/**
	 * サイズの再反映
	 */
	public void reSize() {
		setSize(getField1().getWidth() + 50, getField1().getHeight() + getField2().getHeight());

	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo
	 */
	public void setTabControlNo(int tabControlNo) {
		getField1().setTabControlNo(tabControlNo);
		getField2().setTabControlNo(tabControlNo);
	}

	/**
	 * 入力された付箋1コードを返す
	 * 
	 * @return 入力された付箋1コード
	 */
	public String getCode1() {
		return getField1().code.getText();
	}

	/**
	 * 入力された付箋2コードを返す
	 * 
	 * @return 入力された付箋2コード
	 */
	public String getCode2() {
		return getField2().code.getText();
	}

	/**
	 * 付箋1コードのセット
	 * 
	 * @param txt 付箋1コード
	 */
	public void setCode1(String txt) {
		getField1().code.setText(txt);
	}

	/**
	 * 付箋2コードのセット
	 * 
	 * @param txt 付箋2コード
	 */
	public void setCode2(String txt) {
		getField2().code.setText(txt);
	}

	/**
	 * 付箋1コンポのentity初期化
	 */
	public void refleshEntity1() {
		getField1().refleshAndShowEntity();
	}

	/**
	 * 付箋2コンポのentity初期化
	 */
	public void refleshEntity2() {
		getField2().refleshAndShowEntity();
	}

	/**
	 * クリア
	 */
	public void clear() {
		getField1().clear();
		getField2().clear();
	}

	/**
	 * Editableの設定
	 * 
	 * @param editable
	 */
	public void setEditable(boolean editable) {
		getField1().setEditable(editable);
		getField2().setEditable(editable);
	}

	/**
	 * コードの存在チェックをするか設定します
	 * 
	 * @param checkExist true:チェックする
	 */
	public void setCheckExist(boolean checkExist) {
		getField1().setCheckExist(checkExist);
		getField2().setCheckExist(checkExist);
	}

	/**
	 * @return ctrlTagReference1
	 */
	public TTagReference getField1() {
		return ctrlTagReference1;
	}

	/**
	 * @return ctrlTagReference2
	 */
	public TTagReference getField2() {
		return ctrlTagReference2;
	}

	/**
	 * 付箋1フィールドで選択された科目Entityを返す
	 * 
	 * @return 付箋1フィールドで選択された科目Entity
	 */
	public Tag getEntity1() {
		return ctrlTagReference1.getEntity();
	}

	/**
	 * 付箋2フィールドで選択された科目Entityを返す
	 * 
	 * @return 付箋2フィールドで選択された科目Entity
	 */
	public Tag getEntity2() {
		return ctrlTagReference2.getEntity();
	}

}
