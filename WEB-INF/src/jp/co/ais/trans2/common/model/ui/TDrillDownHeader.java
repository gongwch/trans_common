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
 * �h�����_�E���̂̃w�b�_�[�t�B�[���h
 * 
 * @author AIS
 */
public class TDrillDownHeader extends TPanel {

	/** �`�[�ԍ� */
	public TLabelField txSlipNo;

	/** �`�[�C���� */
	public TLabelNumericField numDenUpdCount;

	/** �˗�����R�[�h */
	public TLabelField txDepartmentCode;

	/** �˗����嗪�� */
	public TLabelField txDepartmentNames;

	/** ���Z�敪 */
	public TLabelField txSettlementLevel;

	/** �`�[���t */
	public TLabelField txSlipDate;

	/** ��� */
	public TLabelField txCompany;

	/** ���͎҃R�[�h */
	public TLabelField txEmployeeCode;

	/** ���͎җ��� */
	public TLabelField txEmployeeNames;

	/** �X�V�敪 */
	public TLabelField txSlipState;

	/** �`�[��� */
	public TLabelField txSlipType;

	/** �؜ߔԍ� */
	public TLabelField txSNo;

	/** �E�v�R�[�h */
	public TLabelField txRemarkCode;

	/** �E�v */
	public TLabelField txRemark;

	/** ����R�[�h */
	public String lang;

	/** �tⳕۑ��{�^�� */
	public TButton btnTag;

	/** �t�1 */
	public TTagReference ctrlTag1;

	/** �t�2 */
	public TTagReference ctrlTag2;
	
	/** ���F�O���[�v */
	public TAprvRoleGroupReference ctrlAprvGroup;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param lang ����R�[�h
	 */
	public TDrillDownHeader(String lang) {
		this.lang = lang;

		initComponents();
		allocateComponents();
	}

	/**
	 * �R���|�[�l���g�̏����ݒ�
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

		// �tⳋ@�\
		btnTag = new TButton();
		ctrlTag1 = new TTagReference();
		ctrlTag2 = new TTagReference();
		ctrlAprvGroup = new TAprvRoleGroupReference();

	}

	/**
	 * �R���|�[�l���g�̔z�u���s���܂��B
	 */
	public void allocateComponents() {

		setLayout(null);

		setMaximumSize(new Dimension(0, 105));
		setMinimumSize(new Dimension(0, 105));
		setPreferredSize(new Dimension(0, 105));

		// �`�[�ԍ�
		txSlipNo.setEditable(false);
		txSlipNo.setFieldSize(145);
		txSlipNo.setLabelSize(65);
		txSlipNo.setLangMessageID("C00605");
		txSlipNo.setLocation(10, 10);
		add(txSlipNo);

		// �C����
		numDenUpdCount.setEditable(false);
		numDenUpdCount.setFieldSize(35);
		numDenUpdCount.setLabelSize(65);
		numDenUpdCount.setLangMessageID("C01618");
		numDenUpdCount.setLocation(225, 10);
		add(numDenUpdCount);

		// �˗�����R�[�h
		txDepartmentCode.setEditable(false);
		txDepartmentCode.setFieldSize(75);
		txDepartmentCode.setLabelSize(65);
		txDepartmentCode.setLangMessageID("C00994");
		txDepartmentCode.setLocation(330, 10);
		add(txDepartmentCode);

		// �˗����嗪��
		txDepartmentNames.setEditable(false);
		txDepartmentNames.setLangMessageID("");
		txDepartmentNames.setLabelSize(0);
		txDepartmentNames.setFieldSize(135);
		txDepartmentNames.setLocation(470, 10);
		add(txDepartmentNames);

		// ���Z�敪
		txSettlementLevel.setEditable(false);
		txSettlementLevel.setFieldSize(80);
		txSettlementLevel.setLabelSize(65);
		txSettlementLevel.setLangMessageID("C00610");
		txSettlementLevel.setLocation(620, 10);
		add(txSettlementLevel);

		// �`�[���t
		txSlipDate.setEditable(false);
		txSlipDate.setFieldSize(75);
		txSlipDate.setLabelSize(65);
		txSlipDate.setLangMessageID("C00599");
		txSlipDate.setLocation(10, 31);
		add(txSlipDate);

		// ���
		txCompany.setVisible(false);
		txCompany.setEditable(false);
		txCompany.setFieldSize(110);
		txCompany.setLabelSize(65);
		txCompany.setLangMessageID("C00053");
		txCompany.setLocation(150, 31);
		add(txCompany);

		// ���͎҃R�[�h
		txEmployeeCode.setEditable(false);
		txEmployeeCode.setFieldSize(75);
		txEmployeeCode.setLabelSize(65);
		txEmployeeCode.setLangMessageID("C01278");
		txEmployeeCode.setLocation(330, 31);
		add(txEmployeeCode);

		// ���͎җ���
		txEmployeeNames.setEditable(false);
		txEmployeeNames.setLangMessageID("");
		txEmployeeNames.setLabelSize(0);
		txEmployeeNames.setFieldSize(135);
		txEmployeeNames.setLocation(470, 31);
		add(txEmployeeNames);

		// �X�V�敪
		txSlipState.setEditable(false);
		txSlipState.setFieldSize(80);
		txSlipState.setLabelSize(65);
		txSlipState.setLangMessageID("C01069");
		txSlipState.setLocation(620, 31);
		add(txSlipState);

		// �`�[���
		txSlipType.setEditable(false);
		txSlipType.setFieldSize(150);
		txSlipType.setLabelSize(65);
		txSlipType.setLangMessageID("C00917");
		txSlipType.setLocation(10, 52);
		add(txSlipType);

		// �؜ߔԍ�
		txSNo.setEditable(false);
		txSNo.setFieldSize(150);
		txSNo.setLabelSize(65);
		txSNo.setLangMessageID("C01178");
		txSNo.setLocation(330, 52);
		add(txSNo);

		// �E�v�R�[�h
		txRemarkCode.setEditable(false);
		txRemarkCode.setFieldSize(75);
		txRemarkCode.setLabelSize(65);
		txRemarkCode.setLangMessageID("C00569");
		txRemarkCode.setLocation(10, 73);
		add(txRemarkCode);

		// �E�v
		txRemark.setEditable(false);
		txRemark.setFieldSize(560);
		txRemark.setLabelSize(0);
		txRemark.setLangMessageID("");
		txRemark.setLocation(150, 73);
		add(txRemark);

		// �tⳋ@�\
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
		// ���F�O���[�v
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
	 * �w�b�_�[�ɒl���Z�b�g
	 * 
	 * @param slip
	 * @throws TException
	 */
	public void setSlipHeader(Slip slip) throws TException {

		// �`�[�ԍ�
		txSlipNo.setValue(slip.getSlipNo());
		// �`�[�C����
		numDenUpdCount.setNumberValue(slip.getHeader().getSWK_UPD_CNT());
		// �˗�����R�[�h
		txDepartmentCode.setValue(slip.getHeader().getSWK_IRAI_DEP_CODE());
		// �˗����嗪��
		txDepartmentNames.setValue(slip.getHeader().getSWK_IRAI_DEP_NAME_S());
		// ���Z�敪
		txSettlementLevel.setValue(FormatUtil.settlementLevelFormat(slip.getHeader().getSWK_KSN_KBN(), lang));
		// �`�[���t
		txSlipDate.setValue(DateUtil.toYMDString(slip.getSlipDate()));
		// ���
		txCompany.setValue(slip.getHeader().getKAI_NAME_S());
		// ���͎҃R�[�h
		txEmployeeCode.setValue(slip.getHeader().getSWK_IRAI_EMP_CODE());
		// ���͎җ���
		txEmployeeNames.setValue(slip.getHeader().getSWK_IRAI_EMP_NAME_S());
		// �X�V�敪
		txSlipState.setValue(MessageUtil.getWord(lang, SlipState.getSlipStateName(slip.getHeader().getSWK_UPD_KBN())));
		// �`�[���
		txSlipType.setValue(slip.getHeader().getSWK_DEN_SYU_NAME_S());
		// �؜ߔԍ�
		txSNo.setValue(slip.getHeader().getSWK_SEI_NO());
		// �E�v�R�[�h
		txRemarkCode.setValue(slip.getHeader().getSWK_TEK_CODE());
		// �E�v
		txRemark.setValue(slip.getHeader().getSWK_TEK());
		// �tⳋ@�\
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
		// ���F�O���[�v
		ctrlAprvGroup.setEntity(slip.getHeader().getAprRoleGroup());
	}
}
