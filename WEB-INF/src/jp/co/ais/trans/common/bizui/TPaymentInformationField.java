package jp.co.ais.trans.common.bizui;

import java.awt.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;

/**
 * 支払情報フィールド
 * 
 * @author ookawara
 */
public class TPaymentInformationField extends TPanel implements TInterfaceLangMessageID {

	/** シリアルUID */
	private static final long serialVersionUID = 6975993877695662983L;

	/** コントロールクラス */
	private TPaymentInformationFieldCtrl ctrl;

	/** 会社コード */
	private String companyCode;

	/** 有効期間のチェック */
	private String termBasisDate;

	/** デフォルト */
	public static final int TYPE_NORMAL = 0;

	/** 支払予定日なし */
	public static final int TYPE_CALENDER_NONE = 1;

	/**
	 * コンストラクタ
	 */
	public TPaymentInformationField() {
		this(TYPE_NORMAL);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type TYPE_NORMAL：カレンダー有り TYPE_CALENDER_NONE：カレンダー無し
	 */
	public TPaymentInformationField(int type) {
		super();

		// ログイン会社コードをセット
		this.companyCode = TClientLoginInfo.getInstance().getCompanyCode();

		ctrl = new TPaymentInformationFieldCtrl(this);

		switch (type) {
			case TYPE_CALENDER_NONE:
				initVariableComponents();
				break;

			default:
				initComponents();
				break;
		}

	}

	/**
	 * 画面構築
	 */
	private void initComponents() {
		GridBagConstraints gridBagConstraints;

		basePanel = new TPanel();
		paymentConditionField = new TPaymentConditionEnhancingField();
		paymentMethodField = new TPaymentMethodField();
		bankAccountField = new TBankAccountEnhancingField();
		calendar = new TLabelPopupCalendar();
		divisonComboBox = new TPaymentDivisionComboBox();
		// 値取得用BEAN
		parameter = new PaymentInformationParameter();

		paymentConditionField.setParameter(parameter);

		paymentConditionField.addCallControl(new CallBackListener() {

			public void after() {
				ctrl.paymentConditionScreenCtrl();
			}
		});

		paymentMethodField.addCallControl(new CallBackListener() {

			public void after() {
				ctrl.paymentMethodScreenCtrl();
			}
		});

		setLayout(new GridBagLayout());

		basePanel.setLayout(new GridBagLayout());
		gridBagConstraints = new GridBagConstraints();
		add(basePanel, new GridBagConstraints());

		// 初期値設定
		paymentConditionField.setTabEnabled(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(paymentConditionField, gridBagConstraints);

		paymentMethodField.setTabEnabled(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(paymentMethodField, gridBagConstraints);

		bankAccountField.getButton().setLangMessageID("C01634");
		bankAccountField.getButton().setEnabled(false);
		bankAccountField.getField().setEditableEnabled(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(bankAccountField, gridBagConstraints);

		calendar.setLabelHAlignment(1);
		calendar.setLabelSize(85);
		calendar.setLangMessageID("C01100");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(calendar, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 180, 0, 0);
		basePanel.add(divisonComboBox, gridBagConstraints);

	}

	/**
	 * 画面構築 支払予定日なし
	 */
	private void initVariableComponents() {
		GridBagConstraints gridBagConstraints;

		basePanel = new TPanel();
		paymentConditionField = new TPaymentConditionEnhancingField();
		paymentMethodField = new TPaymentMethodField();
		bankAccountField = new TBankAccountEnhancingField();
		calendar = new TLabelPopupCalendar();
		divisonComboBox = new TPaymentDivisionComboBox();
		// Label・Fieldサイズを変更
		divisonComboBox.setLabelSize(85);
		divisonComboBox.setComboSize(75);
		// 値取得用BEAN
		parameter = new PaymentInformationParameter();

		paymentConditionField.setParameter(parameter);

		paymentConditionField.addCallControl(new CallBackListener() {

			public void after() {
				ctrl.paymentConditionScreenCtrl();
			}
		});

		paymentMethodField.addCallControl(new CallBackListener() {

			public void after() {
				ctrl.paymentMethodScreenCtrl();
			}
		});

		setLayout(new GridBagLayout());

		basePanel.setLayout(new GridBagLayout());
		gridBagConstraints = new GridBagConstraints();
		add(basePanel, new GridBagConstraints());

		// 初期値設定
		paymentConditionField.setTabEnabled(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(paymentConditionField, gridBagConstraints);

		paymentMethodField.setTabEnabled(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(paymentMethodField, gridBagConstraints);

		bankAccountField.getButton().setLangMessageID("C01634");
		bankAccountField.getButton().setEnabled(false);
		bankAccountField.getField().setEditableEnabled(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(bankAccountField, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(divisonComboBox, gridBagConstraints);

	}

	/**
	 * パネル全体 タブ移動順番号を設定する.
	 * 
	 * @see TInterfaceTabControl#setTabControlNo(int)
	 * @param no 番号
	 */
	public void setTabControlNo(int no) {
		paymentConditionField.setTabControlNo(no);
		paymentMethodField.setTabControlNo(no);
		bankAccountField.setTabControlNo(no);
		calendar.setTabControlNo(no);
		divisonComboBox.setTabControlNo(no);
	}

	/**
	 * 会社コードを返す
	 * 
	 * @return companyCode 会社コード
	 */
	public String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * 会社コードの設定
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		paymentConditionField.setCompanyCode(companyCode);
		paymentMethodField.setCompanyCode(companyCode);
		bankAccountField.setCompanyCode(companyCode);
	}

	/**
	 * 基準日の取得
	 * 
	 * @return termBasisDate 基準日
	 */
	public String getTermBasisDate() {
		return termBasisDate;
	}

	/**
	 * 基準日の設定
	 * 
	 * @param termBasisDate
	 */
	public void setTermBasisDate(String termBasisDate) {
		this.termBasisDate = termBasisDate;
	}

	/**
	 * 取引先コードの取得
	 * 
	 * @return customerCode 取引先コード
	 */
	public String getCustomerCode() {
		return this.paymentConditionField.getCustomerCode();
	}

	/**
	 * 取引先コードの設定
	 * 
	 * @param customerCode
	 */
	public void setCustomerCode(String customerCode) {
		this.paymentConditionField.setCustomerCode(customerCode);
	}

	/**
	 * 通貨コードの取得
	 * 
	 * @return curCode 通貨コード
	 */
	public String getCurCode() {
		return this.paymentConditionField.getCurrencyCode();
	}

	/**
	 * 通貨コードのセット
	 * 
	 * @param curCode
	 */
	public void setCurCode(String curCode) {
		this.paymentConditionField.setCurrencyCode(curCode);
	}

	/**
	 * 支払先条件コンポーネントの取得
	 * 
	 * @return paymentConditionField 支払先条件コンポーネント
	 */
	public TPaymentConditionEnhancingField getPaymentConditionField() {
		return paymentConditionField;
	}

	/**
	 * 支払方法コンポーネントの取得
	 * 
	 * @return paymentMethodField 支払方法コンポーネント
	 */
	public TPaymentMethodField getPaymentMethodField() {
		return paymentMethodField;
	}

	/**
	 * 振出銀行コンポーネントの取得
	 * 
	 * @return bankAccountField 振出銀行コンポーネント
	 */
	public TBankAccountEnhancingField getBankAccountField() {
		return bankAccountField;
	}

	/**
	 * 支払区分コンポーネントの取得
	 * 
	 * @return divisonComboBox 支払区分コンポーネント
	 */
	public TPaymentDivisionComboBox getDivisonComboBox() {
		return divisonComboBox;
	}

	/**
	 * 支払日コンポーネントの取得
	 * 
	 * @return divisonComboBox 支払日コンポーネント
	 */
	public TLabelPopupCalendar getCalendar() {
		return calendar;
	}

	/**
	 * デフォルトの取引先条件をセットする
	 */
	public void setDefaultPaymentInformation() {
		this.paymentConditionField.setDefaultPaymentCondition();
		ctrl.paymentConditionScreenCtrl();
	}

	/**
	 * 値をクリアする
	 */
	public void clear() {
		paymentConditionField.setValue("");
		paymentMethodField.setValue("");
		bankAccountField.setValue("");
		calendar.setValue(null);
		divisonComboBox.getComboBox().setSelectedIndex(0);
	}

	private TPaymentConditionEnhancingField paymentConditionField;

	private TPaymentMethodField paymentMethodField;

	private TBankAccountEnhancingField bankAccountField;

	private TLabelPopupCalendar calendar;

	private TPaymentDivisionComboBox divisonComboBox;

	private TPanel basePanel;

	private PaymentInformationParameter parameter;

}
