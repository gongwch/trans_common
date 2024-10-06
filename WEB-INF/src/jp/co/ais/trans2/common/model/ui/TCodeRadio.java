package jp.co.ais.trans2.common.model.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Map.Entry;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.code.*;

/**
 * コードRadio
 */
public class TCodeRadio extends TPanel {

	/** ラジオボタン */
	public Map<String, TRadioButton> radios = new HashMap<String, TRadioButton>();

	/** entities */
	public Map<String, OP_CODE_MST> entities = new HashMap<String, OP_CODE_MST>();

	/** ボタングループ */
	protected ButtonGroup btngrpClass = new ButtonGroup();

	/** 共通設定 */
	protected GridBagConstraints gc = new GridBagConstraints();

	/** 描画方向 */
	public int alignment = SwingConstants.HORIZONTAL;

	/** 左余白 */
	public int leftMargin = 0;

	/** ラジオボタンの高さ(デフォルトは16で、16より不足分) */
	public int height = 0;

	/** ラジオボタンの幅 */
	public int width = 0;

	/** true:文字幅自動調整 */
	public boolean autoAdjustment = false;

	/** コントローラ */
	protected TCodeRadioController controller;

	/** true:内航モード、false:外航モード */
	protected boolean local = false;

	/** 区分 */
	protected OPCodeDivision div = null;

	/** コード指定 */
	protected String[] codes = null;

	/**
	 * コンストラクター
	 * 
	 * @param width 幅
	 * @param div 区分
	 * @param codes コード指定
	 */
	public TCodeRadio(int width, OPCodeDivision div, String... codes) {
		this(SwingConstants.HORIZONTAL, 0, 0, width, false, div, codes);
	}

	/**
	 * コンストラクター
	 * 
	 * @param autoAdjustment true:文字幅自動調整
	 * @param div 区分
	 * @param codes コード指定
	 */
	public TCodeRadio(boolean autoAdjustment, OPCodeDivision div, String... codes) {
		this(SwingConstants.HORIZONTAL, 0, 0, 0, autoAdjustment, div, codes);
	}

	/**
	 * コンストラクター
	 * 
	 * @param local
	 * @param autoAdjustment true:文字幅自動調整
	 * @param div 区分
	 * @param codes コード指定
	 */
	public TCodeRadio(boolean local, boolean autoAdjustment, OPCodeDivision div, String... codes) {
		this(local, SwingConstants.HORIZONTAL, 0, 0, 0, autoAdjustment, div, codes);
	}

	/**
	 * コンストラクター
	 * 
	 * @param alignment 横:SwingConstants.HORIZONTAL 縦:SwingConstants.VERTICAL
	 * @param leftMargin 左余白
	 * @param height ラジオボタンの高さ(デフォルトは16で、16より不足分)
	 * @param width 幅
	 * @param autoAdjustment true:文字幅自動調整
	 * @param div 区分
	 * @param codes コード指定
	 */
	public TCodeRadio(int alignment, int leftMargin, int height, int width, boolean autoAdjustment, OPCodeDivision div,
		String... codes) {
		this(false, alignment, leftMargin, height, width, autoAdjustment, div, codes);
	}

	/**
	 * コンストラクター
	 * 
	 * @param local true:内航モード、false:外航モード
	 * @param alignment 横:SwingConstants.HORIZONTAL 縦:SwingConstants.VERTICAL
	 * @param leftMargin 左余白
	 * @param height ラジオボタンの高さ(デフォルトは16で、16より不足分)
	 * @param width 幅
	 * @param autoAdjustment true:文字幅自動調整
	 * @param div 区分
	 * @param codes コード指定
	 */
	public TCodeRadio(boolean local, int alignment, int leftMargin, int height, int width, boolean autoAdjustment,
		OPCodeDivision div, String... codes) {

		this.local = local;
		this.alignment = alignment;
		this.leftMargin = leftMargin;
		this.height = height;
		this.width = width;
		this.autoAdjustment = autoAdjustment;
		this.div = div;
		this.codes = codes;

		// 初期化
		init();
	}

	/**
	 * true:内航モード、false:外航モードの取得
	 * 
	 * @return local true:内航モード、false:外航モード
	 */
	public boolean isLocal() {
		return local;
	}

	/**
	 * true:内航モード、false:外航モードの設定
	 * 
	 * @param local true:内航モード、false:外航モード
	 */
	public void setLocal(boolean local) {
		this.local = local;
	}

	/**
	 * @return コード区分
	 */
	public OPCodeDivision getCodeDivision() {
		return div;
	}

	/**
	 * コード区分指定
	 * 
	 * @param codeDiv 区分
	 */
	public void setCodeDivision(OPCodeDivision codeDiv) {

		boolean isChanged = codeDiv != this.div;

		this.div = codeDiv;

		if (isChanged) {
			// 変更あり→再度初期化
			getController().init();
		}
	}

	/**
	 * @return 指定コード
	 */
	public String[] getCodes() {
		return codes;
	}

	/**
	 * コード指定
	 * 
	 * @param arr 指定コード
	 */
	public void setCodes(String... arr) {
		this.codes = arr;

		// 再度初期化
		getController().init();
	}

	/**
	 * 初期化
	 */
	protected void init() {

		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents();

		controller = createController();

	}

	/**
	 * @return コントローラ
	 */
	protected TCodeRadioController createController() {
		return new TCodeRadioController(this);
	}

	/**
	 * 
	 */
	protected void initComponents() {
		//
	}

	/**
	 * 
	 */
	protected void allocateComponents() {

		this.setLayout(new GridBagLayout());

		gc.insets = new Insets(0, 0, 0, 0);
		if (alignment == SwingConstants.HORIZONTAL) {
			// 横
			gc.gridy = 0;
			gc.weightx = 1.0d;
			gc.anchor = GridBagConstraints.CENTER;
		} else {
			// 縦
			gc.gridx = 0;
			gc.anchor = GridBagConstraints.NORTHWEST;
		}
	}

	/**
	 * @return controller
	 */
	public TCodeRadioController getController() {
		return controller;
	}

	/**
	 * クリア
	 */
	protected void clear() {
		radios.clear();
		btngrpClass = new ButtonGroup();
		this.removeAll();

		System.gc();
	}

	/**
	 * ラジオボタン追加
	 * 
	 * @param bean
	 * @param title 表示文字
	 */
	public void addRadio(OP_CODE_MST bean, String title) {
		addRadio(bean, title, this.width);
	}

	/**
	 * ラジオボタン追加
	 * 
	 * @param bean
	 * @param title 表示文字
	 * @param w 指定幅
	 */
	public void addRadio(OP_CODE_MST bean, String title, int w) {

		TRadioButton rdo = new TRadioButton(title);
		TGuiUtil.setComponentSize(rdo, new Dimension(w, 16 + height));
		rdo.setHorizontalAlignment(SwingConstants.LEFT);
		rdo.setVerticalAlignment(SwingConstants.CENTER);
		rdo.setMargin(new Insets(0, leftMargin, 0, 0));

		if (radios.isEmpty()) {
			rdo.setSelected(true);
		}

		btngrpClass.add(rdo);
		radios.put(bean.getCode(), rdo);
		entities.put(bean.getCode(), bean);

		if (alignment == SwingConstants.HORIZONTAL) {
			// 横
			gc.gridx = radios.size();
			this.add(rdo, gc);

			int twidth = 0;
			for (TRadioButton inrdo : radios.values()) {
				twidth += inrdo.getWidth();
			}

			setWidth(leftMargin + twidth + 10);
		} else {
			// 縦
			gc.gridy = radios.size();
			this.add(rdo, gc);

			int twidth = 0;
			int theight = 0;
			for (TRadioButton inrdo : radios.values()) {
				twidth = Math.max(inrdo.getWidth(), twidth);
				theight += inrdo.getHeight();
			}

			setWidth(leftMargin + twidth + 10);
			setHeight(theight + 30);
		}
	}

	/**
	 * 土台パネルの幅変更
	 * 
	 * @param width 幅サイズ
	 */
	public void setWidth(int width) {
		TGuiUtil.setComponentWidth(this, width);
	}

	/**
	 * 土台パネルの高さ変更
	 * 
	 * @param height 高さサイズ
	 */
	public void setHeight(int height) {
		TGuiUtil.setComponentHeight(this, height);
	}

	/**
	 * 水平寄せの設定
	 * 
	 * @param alignment 寄せ
	 */
	public void setHorizontalAlignment(int alignment) {
		for (TRadioButton rdo : radios.values()) {
			rdo.setHorizontalAlignment(alignment);
		}
	}

	/**
	 * true:文字幅自動調整の取得
	 * 
	 * @return autoAdjustment true:文字幅自動調整
	 */
	public boolean isAutoAdjustment() {
		return autoAdjustment;
	}

	/**
	 * 表示文字個別指定
	 * 
	 * @param code
	 * @param word 表示文字
	 */
	public void setLangMessageID(String code, String word) {
		TRadioButton rdo = getRadio(code);
		if (rdo != null) {
			rdo.setLangMessageID(word);
		}
	}

	/**
	 * @param code
	 * @return Radio
	 */
	public TRadioButton getRadio(String code) {
		TRadioButton rdo = radios.get(code);
		if (rdo != null) {
			return rdo;
		}
		return null;
	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		for (TRadioButton rdo : radios.values()) {
			rdo.setTabControlNo(tabControlNo);
		}
	}

	/**
	 * 指定Indexボタンの選択状態をONにする.
	 * 
	 * @param code
	 */
	public void setSelectON(String code) {
		for (Entry<String, TRadioButton> entry : radios.entrySet()) {
			entry.getValue().setSelected(entry.getKey().equals(code));
		}
	}

	/**
	 * 指定Indexボタンの選択状態をONにする.
	 * 
	 * @param num
	 */
	public void setSelectON(int num) {
		String code = Integer.toString(num);
		setSelectON(code);
	}

	/**
	 * 選択中Radioの戻す
	 * 
	 * @return 選択中Radio(null可能)
	 */
	public OP_CODE_MST getSelected() {
		for (Entry<String, TRadioButton> entry : radios.entrySet()) {
			if (entry.getValue().isSelected()) {
				return entities.get(entry.getKey());
			}
		}
		return null;
	}

	/**
	 * 選択中Radioの戻す
	 * 
	 * @return 選択中Radio(null可能)
	 */
	public String getCode() {
		OP_CODE_MST bean = getSelected();
		if (bean != null) {
			return bean.getCode();
		}
		return null;
	}

	/**
	 * 選択中Radioの戻す<br>
	 * 有効な数値の場合、数値を返す
	 * 
	 * @return 選択中Radio(nullの場合は-1)
	 */
	public int getInt() {
		OP_CODE_MST bean = getSelected();
		if (bean != null && !Util.isNullOrEmpty(bean.getCode()) && Util.isNumber(bean.getCode())) {
			return DecimalUtil.toInt(bean.getCode());
		}
		return -1;
	}

	/**
	 * ItemListenerセット<br>
	 * 全ラジオボタン実装
	 * 
	 * @param listener リスナー
	 */
	public void addItemListener(ItemListener listener) {
		for (TRadioButton rdo : radios.values()) {
			rdo.addItemListener(listener);
		}
	}

	/**
	 * ItemListenerセット
	 * 
	 * @param code
	 * @param listener リスナー
	 */
	public void addItemListener(String code, ItemListener listener) {
		radios.get(code).addItemListener(listener);
	}

	@Override
	public void setEnabled(boolean enabled) {
		for (TRadioButton radio : radios.values()) {
			radio.setEnabled(enabled);
		}
	}

	/**
	 * サイズリセット
	 */
	public void resizePanel() {
		if (radios.size() > 0) {
			int totalWidth = radios.size() * this.width;
			TGuiUtil.setComponentSize(this, totalWidth, 20 + this.height);
		}
	}

	/**
	 * サイズリセット
	 * 
	 * @param totalWidth
	 */
	public void resizePanel(int totalWidth) {
		TGuiUtil.setComponentSize(this, totalWidth, 20 + this.height);
	}
}
