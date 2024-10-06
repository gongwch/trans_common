package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.model.format.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.slip.*;
import jp.co.ais.trans2.model.tag.*;

/**
 * ドリルダウンののヘッダーフィールド
 * 
 * @author AIS
 */
public class TDrillDownHeader extends TPanel {

	/** 伝票番号 */
	public TLabelField txSlipNo;

	/** 伝票修正回数 */
	public TLabelNumericField numDenUpdCount;

	/** 依頼部門コード */
	public TLabelField txDepartmentCode;

	/** 依頼部門略称 */
	public TLabelField txDepartmentNames;

	/** 決算区分 */
	public TLabelField txSettlementLevel;

	/** 伝票日付 */
	public TLabelField txSlipDate;

	/** 会社 */
	public TLabelField txCompany;

	/** 入力者コード */
	public TLabelField txEmployeeCode;

	/** 入力者略称 */
	public TLabelField txEmployeeNames;

	/** 更新区分 */
	public TLabelField txSlipState;

	/** 伝票種別 */
	public TLabelField txSlipType;

	/** 証憑番号 */
	public TLabelField txSNo;

	/** 摘要コード */
	public TLabelField txRemarkCode;

	/** 摘要 */
	public TLabelField txRemark;

	/** 言語コード */
	public String lang;

	/** 付箋保存ボタン */
	public TButton btnTag;

	/** 付箋1 */
	public TTagReference ctrlTag1;

	/** 付箋2 */
	public TTagReference ctrlTag2;
	
	/** 承認グループ */
	public TAprvRoleGroupReference ctrlAprvGroup;

	/**
	 * コンストラクタ
	 * 
	 * @param lang 言語コード
	 */
	public TDrillDownHeader(String lang) {
		this.lang = lang;

		initComponents();
		allocateComponents();
	}

	/**
	 * コンポーネントの初期設定
	 */
	public void initComponents() {
		txSlipNo = new TLabelField();
		numDenUpdCount = new TLabelNumericField();
		txDepartmentCode = new TLabelField();
		txDepartmentNames = new TLabelField();
		txSettlementLevel = new TLabelField();
		txSlipDate = new TLabelField();
		txCompany = new TLabelField();
		txEmployeeCode = new TLabelField();
		txEmployeeNames = new TLabelField();
		txSlipState = new TLabelField();
		txSlipType = new TLabelField();
		txSNo = new TLabelField();
		txRemarkCode = new TLabelField();
		txRemark = new TLabelField();

		// 付箋機能
		btnTag = new TButton();
		ctrlTag1 = new TTagReference();
		ctrlTag2 = new TTagReference();
		ctrlAprvGroup = new TAprvRoleGroupReference();

	}

	/**
	 * コンポーネントの配置を行います。
	 */
	public void allocateComponents() {

		setLayout(null);

		setMaximumSize(new Dimension(0, 105));
		setMinimumSize(new Dimension(0, 105));
		setPreferredSize(new Dimension(0, 105));

		// 伝票番号
		txSlipNo.setEditable(false);
		txSlipNo.setFieldSize(145);
		txSlipNo.setLabelSize(65);
		txSlipNo.setLangMessageID("C00605");
		txSlipNo.setLocation(10, 10);
		add(txSlipNo);

		// 修正回数
		numDenUpdCount.setEditable(false);
		numDenUpdCount.setFieldSize(35);
		numDenUpdCount.setLabelSize(65);
		numDenUpdCount.setLangMessageID("C01618");
		numDenUpdCount.setLocation(225, 10);
		add(numDenUpdCount);

		// 依頼部門コード
		txDepartmentCode.setEditable(false);
		txDepartmentCode.setFieldSize(75);
		txDepartmentCode.setLabelSize(65);
		txDepartmentCode.setLangMessageID("C00994");
		txDepartmentCode.setLocation(330, 10);
		add(txDepartmentCode);

		// 依頼部門略称
		txDepartmentNames.setEditable(false);
		txDepartmentNames.setLangMessageID("");
		txDepartmentNames.setLabelSize(0);
		txDepartmentNames.setFieldSize(135);
		txDepartmentNames.setLocation(470, 10);
		add(txDepartmentNames);

		// 決算区分
		txSettlementLevel.setEditable(false);
		txSettlementLevel.setFieldSize(80);
		txSettlementLevel.setLabelSize(65);
		txSettlementLevel.setLangMessageID("C00610");
		txSettlementLevel.setLocation(620, 10);
		add(txSettlementLevel);

		// 伝票日付
		txSlipDate.setEditable(false);
		txSlipDate.setFieldSize(75);
		txSlipDate.setLabelSize(65);
		txSlipDate.setLangMessageID("C00599");
		txSlipDate.setLocation(10, 31);
		add(txSlipDate);

		// 会社
		txCompany.setVisible(false);
		txCompany.setEditable(false);
		txCompany.setFieldSize(110);
		txCompany.setLabelSize(65);
		txCompany.setLangMessageID("C00053");
		txCompany.setLocation(150, 31);
		add(txCompany);

		// 入力者コード
		txEmployeeCode.setEditable(false);
		txEmployeeCode.setFieldSize(75);
		txEmployeeCode.setLabelSize(65);
		txEmployeeCode.setLangMessageID("C01278");
		txEmployeeCode.setLocation(330, 31);
		add(txEmployeeCode);

		// 入力者略称
		txEmployeeNames.setEditable(false);
		txEmployeeNames.setLangMessageID("");
		txEmployeeNames.setLabelSize(0);
		txEmployeeNames.setFieldSize(135);
		txEmployeeNames.setLocation(470, 31);
		add(txEmployeeNames);

		// 更新区分
		txSlipState.setEditable(false);
		txSlipState.setFieldSize(80);
		txSlipState.setLabelSize(65);
		txSlipState.setLangMessageID("C01069");
		txSlipState.setLocation(620, 31);
		add(txSlipState);

		// 伝票種別
		txSlipType.setEditable(false);
		txSlipType.setFieldSize(150);
		txSlipType.setLabelSize(65);
		txSlipType.setLangMessageID("C00917");
		txSlipType.setLocation(10, 52);
		add(txSlipType);

		// 証憑番号
		txSNo.setEditable(false);
		txSNo.setFieldSize(150);
		txSNo.setLabelSize(65);
		txSNo.setLangMessageID("C01178");
		txSNo.setLocation(330, 52);
		add(txSNo);

		// 摘要コード
		txRemarkCode.setEditable(false);
		txRemarkCode.setFieldSize(75);
		txRemarkCode.setLabelSize(65);
		txRemarkCode.setLangMessageID("C00569");
		txRemarkCode.setLocation(10, 73);
		add(txRemarkCode);

		// 摘要
		txRemark.setEditable(false);
		txRemark.setFieldSize(560);
		txRemark.setLabelSize(0);
		txRemark.setLangMessageID("");
		txRemark.setLocation(150, 73);
		add(txRemark);

		// 付箋機能
		ctrlTag1.setLocation(txSettlementLevel.getX() + txSettlementLevel.getWidth() + 50, txSettlementLevel.getY());
		ctrlTag1.setLangMessageID("C12055");
		add(ctrlTag1);
		ctrlTag2.setLocation(ctrlTag1.getX(), ctrlTag1.getY() + ctrlTag1.getHeight());
		ctrlTag2.setLangMessageID("C12056");
		add(ctrlTag2);
		btnTag.setLocation(ctrlTag2.getX() + ctrlTag2.getWidth() - 80, ctrlTag2.getY() + ctrlTag2.getHeight() + 5);
		btnTag.setSize(20, 80);
		btnTag.setLangMessageID("C03766");
		add(btnTag);
		// 承認グループ
		ctrlAprvGroup.setLocation(ctrlTag2.getX() , btnTag.getY() + btnTag.getHeight());
		ctrlAprvGroup.setEditable(false);
		add(ctrlAprvGroup);

		// (Color.WHITE);
		txSlipNo.setOpaque(false);
		numDenUpdCount.setOpaque(false);
		txDepartmentCode.setOpaque(false);
		txDepartmentNames.setOpaque(false);
		txSettlementLevel.setOpaque(false);
		txSlipDate.setOpaque(false);
		txCompany.setOpaque(false);
		txEmployeeCode.setOpaque(false);
		txEmployeeNames.setOpaque(false);
		txSlipState.setOpaque(false);
		txSlipType.setOpaque(false);
		txRemarkCode.setOpaque(false);
		txRemark.setOpaque(false);
	}

	/**
	 * ヘッダーに値をセット
	 * 
	 * @param slip
	 * @throws TException
	 */
	public void setSlipHeader(Slip slip) throws TException {

		// 伝票番号
		txSlipNo.setValue(slip.getSlipNo());
		// 伝票修正回数
		numDenUpdCount.setNumberValue(slip.getHeader().getSWK_UPD_CNT());
		// 依頼部門コード
		txDepartmentCode.setValue(slip.getHeader().getSWK_IRAI_DEP_CODE());
		// 依頼部門略称
		txDepartmentNames.setValue(slip.getHeader().getSWK_IRAI_DEP_NAME_S());
		// 決算区分
		txSettlementLevel.setValue(FormatUtil.settlementLevelFormat(slip.getHeader().getSWK_KSN_KBN(), lang));
		// 伝票日付
		txSlipDate.setValue(DateUtil.toYMDString(slip.getSlipDate()));
		// 会社
		txCompany.setValue(slip.getHeader().getKAI_NAME_S());
		// 入力者コード
		txEmployeeCode.setValue(slip.getHeader().getSWK_IRAI_EMP_CODE());
		// 入力者略称
		txEmployeeNames.setValue(slip.getHeader().getSWK_IRAI_EMP_NAME_S());
		// 更新区分
		txSlipState.setValue(MessageUtil.getWord(lang, SlipState.getSlipStateName(slip.getHeader().getSWK_UPD_KBN())));
		// 伝票種別
		txSlipType.setValue(slip.getHeader().getSWK_DEN_SYU_NAME_S());
		// 証憑番号
		txSNo.setValue(slip.getHeader().getSWK_SEI_NO());
		// 摘要コード
		txRemarkCode.setValue(slip.getHeader().getSWK_TEK_CODE());
		// 摘要
		txRemark.setValue(slip.getHeader().getSWK_TEK());
		// 付箋機能
		if (slip.getHeader().getSwkTags() != null) {
			for (SWK_TAG tag : slip.getHeader().getSwkTags()) {
				Tag bean = new Tag();
				bean.setCompanyCode(tag.getKAI_CODE());
				bean.setCode(tag.getTAG_CODE());
				bean.setColor(tag.getTAG_COLOR());
				bean.setTitle(tag.getTAG_TITLE());

				if (tag.getSEQ() == 1) {
					ctrlTag1.setEntity(bean);
				} else if (tag.getSEQ() == 2) {
					ctrlTag2.setEntity(bean);
				}
			}
		}
		// 承認グループ
		ctrlAprvGroup.setEntity(slip.getHeader().getAprRoleGroup());
	}
}
