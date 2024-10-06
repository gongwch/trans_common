package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.awt.event.*;
import java.math.*;
import java.util.*;
import java.util.List;

import javax.swing.event.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TDialog;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.slip.BSEraseDialog.SC;
import jp.co.ais.trans2.model.bs.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * BS�����p�_�C�A���O�R���g���[��
 */
public class BSEraseDialogCtrl extends TController {

	/** �uCancel�v���I�����ꂽ�ꍇ�ɃN���X���\�b�h����Ԃ����l */
	public static final int CANCEL_OPTION = 0;

	/** �u�m��v���I�����ꂽ�ꍇ�ɃN���X���\�b�h����Ԃ����l */
	public static final int OK_OPTION = 1;

	/** true:BS����͌������𗘗p��Client�� */
	public static boolean isBsTermLastDay = ClientConfig.isFlagOn("trans.slip.bs.term.lastday");

	/** �w����� */
	public BSEraseDialog dialog;

	/** �eContainer */
	protected Container parent;

	/** ��ʉ݃R�[�h */
	protected String keyCurrency = null;

	/** ��ʉ� �����_���� */
	protected int keyDigit = 0;

	/** ���� */
	protected int option = CANCEL_OPTION;

	/** ���ݑI�𒆒ʉ݃R�[�h */
	protected String nowCurrencyCode = null;

	/** ���ɏ����I�����Ă���f�[�^ */
	protected List<SWK_DTL> nowEraseList = new ArrayList<SWK_DTL>();

	/** ���ɏ����I�����Ă���f�[�^�̃L�[ */
	protected List<String> nowEraseKeyList = new ArrayList<String>();

	/** ��`�[���t */
	protected Date baseDate;

	/** ����ҏW���`�[�ԍ� */
	protected String currentSlipNo;

	/** ��Џ�� */
	protected Company company = null;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param parent �e�R���|�[�l���g
	 * @param prgInfo �v���O�������
	 */
	public BSEraseDialogCtrl(Container parent, TClientProgramInfo prgInfo) {
		this(parent, prgInfo, null);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param parent �e�R���|�[�l���g
	 * @param prgInfo �v���O�������
	 * @param company ��Џ��
	 */
	public BSEraseDialogCtrl(Container parent, TClientProgramInfo prgInfo, Company company) {
		this.parent = parent;
		this.prgInfo = prgInfo;
		this.company = company;

		// �_�C�A���O������
		this.dialog = createView();

		// �w����ʂ�����������
		initView();

		// �C�x���g�ݒ�
		addDialogEvent();
	}

	/**
	 * �_�C�A���O����
	 * 
	 * @return �_�C�A���O
	 */
	protected BSEraseDialog createView() {
		if (parent instanceof TPanel) {
			return new BSEraseDialog(getCompany(), ((TPanel) parent).getParentFrame());
		} else if (parent instanceof TDialog) {
			return new BSEraseDialog(getCompany(), ((TDialog) parent));
		} else if (parent instanceof TFrame) {
			return new BSEraseDialog(getCompany(), ((TFrame) parent));
		}

		return null;
	}

	@Override
	public BSEraseDialog getView() {
		return dialog;
	}

	/**
	 * ��ЃR�[�h��߂��܂��B<br>
	 * �v���Ўw�肳�ꂽ�ꍇ�A�v���ЃR�[�h��Ԃ�
	 * 
	 * @return ��ЃR�[�h
	 */
	@Override
	public String getCompanyCode() {
		if (company != null) {
			return company.getCode();
		}
		return super.getCompanyCode();
	}

	/**
	 * ��ʂ�����������
	 */
	protected void initView() {

		// �v����
		Company kcompany = company != null ? company : getCompany();

		// �`�[���t(�󔒕s��)
		dialog.ctrlSlipDate.setAllowableBlank(false);

		// ��ʉ݃R�[�h
		keyCurrency = kcompany.getAccountConfig().getKeyCurrency().getCode();

		// ��ʉ� �����_����
		keyDigit = kcompany.getAccountConfig().getKeyCurrency().getDecimalPoint();

		// ���v�l
		dialog.ctrlDrInputTotal.setNumber(BigDecimal.ZERO);
		dialog.ctrlCrInputTotal.setNumber(BigDecimal.ZERO);
		dialog.ctrlDrInputTotal.setDecimalPoint(keyDigit);
		dialog.ctrlCrInputTotal.setDecimalPoint(keyDigit);

		dialog.ctrlDrTotal.setNumber(BigDecimal.ZERO);
		dialog.ctrlCrTotal.setNumber(BigDecimal.ZERO);
		dialog.ctrlDrTotal.setDecimalPoint(keyDigit);
		dialog.ctrlCrTotal.setDecimalPoint(keyDigit);

		// �v����(�g�p�s��)
		dialog.ctrlKCompany.setEntity(kcompany);
		dialog.ctrlKCompany.setEditable(false);

		// �Ȗڏ��� BS��������敪=�����Ώ�
		dialog.ctrlItemGroup.getSearchCondition().getItemCondition().setBsCalculateErase(true);

		// ������������
		dialog.ctrlDepartment.getSearchCondition().setCompanyCode(getCompanyCode());
		dialog.ctrlItemGroup.getSearchCondition().setCompanyCode(getCompanyCode());
		dialog.ctrlCustomer.getSearchCondition().setCompanyCode(getCompanyCode());
		dialog.ctrlEmployee.getSearchCondition().setCompanyCode(getCompanyCode());
		dialog.ctrlManage1.getSearchCondition().setCompanyCode(getCompanyCode());
		dialog.ctrlManage2.getSearchCondition().setCompanyCode(getCompanyCode());
		dialog.ctrlManage3.getSearchCondition().setCompanyCode(getCompanyCode());
		dialog.ctrlManage4.getSearchCondition().setCompanyCode(getCompanyCode());
		dialog.ctrlManage5.getSearchCondition().setCompanyCode(getCompanyCode());
		dialog.ctrlManage6.getSearchCondition().setCompanyCode(getCompanyCode());

		// �Ǘ��P�`�U����
		AccountConfig conf = kcompany.getAccountConfig();

		// ���̃Z�b�g
		dialog.ctrlItemGroup.ctrlItemReference.btn.setText(conf.getItemName());
		dialog.ctrlItemGroup.ctrlSubItemReference.btn.setText(conf.getSubItemName());
		dialog.ctrlItemGroup.ctrlDetailItemReference.btn.setText(conf.getDetailItemName());
		dialog.ctrlManage1.btn.setText(conf.getManagement1Name());
		dialog.ctrlManage2.btn.setText(conf.getManagement2Name());
		dialog.ctrlManage3.btn.setText(conf.getManagement3Name());
		dialog.ctrlManage4.btn.setText(conf.getManagement4Name());
		dialog.ctrlManage5.btn.setText(conf.getManagement5Name());
		dialog.ctrlManage6.btn.setText(conf.getManagement6Name());

		dialog.ctrlManage1.setVisible(conf.isUseManagement1());
		dialog.ctrlManage2.setVisible(conf.isUseManagement2());
		dialog.ctrlManage3.setVisible(conf.isUseManagement3());
		dialog.ctrlManage4.setVisible(conf.isUseManagement4());
		dialog.ctrlManage5.setVisible(conf.isUseManagement5());
		dialog.ctrlManage6.setVisible(conf.isUseManagement6());
	}

	/**
	 * �w����ʂ̃C�x���g��`�B
	 */
	protected void addDialogEvent() {

		// ����
		dialog.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				doSearch();
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// �m��
		dialog.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				doSettle();
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// ���
		dialog.btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doCancel();
			}
		});

		dialog.tbl.setAdapter(new TTableAdapter() {

			/**
			 * �l���ύX���ꂽ�ꍇ�ɌĂяo�����
			 * 
			 * @param before �ύX�O�̒l
			 * @param after �ύX��̒l
			 * @param row �s
			 * @param column ��
			 */
			@Override
			public void changedValueAt(Object before, Object after, int row, int column) {

				if (column != SC.CHECK.ordinal()) {
					// �`�F�b�N�{�b�N�X��ȊO�A�����Ȃ�
					return;
				}

				// �I���s�ɉ����āA���v�s���Čv�Z����B
				summary();

			}
		});

		dialog.chk.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// �I���s�ɉ����āA���v�s���Čv�Z����B
				summary();
			}

		});

	}

	/**
	 * �N���X��Ԃ�
	 * 
	 * @return �N���X
	 */
	protected Class getManagerClass() {
		return BSEraseManager.class;
	}

	/**
	 * �\��
	 * 
	 * @return ����
	 */
	public int show() {
		// �\��
		dialog.setVisible(true);

		return option;
	}

	/**
	 * ��������
	 */
	protected void doSearch() {
		try {
			if (!checkInput()) {
				return;
			}

			// �e�[�u��������
			dialog.tbl.removeRow();

			// �ؕ����z���v�A�ݕ����z���v������
			dialog.ctrlDrInputTotal.setNumber(BigDecimal.ZERO);
			dialog.ctrlCrInputTotal.setNumber(BigDecimal.ZERO);
			dialog.ctrlDrTotal.setNumber(BigDecimal.ZERO);
			dialog.ctrlCrTotal.setNumber(BigDecimal.ZERO);

			// �f�[�^���o
			BSEraseCondition condition = getCondition();

			List<SWK_DTL> list = (List<SWK_DTL>) request(getManagerClass(), "get", condition);

			if (list == null || list.isEmpty()) {
				showMessage(dialog, "I00022");
				dialog.ctrlSlipDate.requestFocus();
				return;
			}

			// �f�[�^�����݂���ꍇ�A�X�v���b�h�Ɏ擾�f�[�^��ݒ�
			for (SWK_DTL bean : list) {
				if (isSkip(bean)) {
					continue;
				}

				dialog.tbl.addRow(getRow(bean));
			}

			if (dialog.tbl.getRowCount() == 0) {
				showMessage(dialog, "I00022");
				dialog.ctrlSlipDate.requestFocus();
				return;
			}

			// �s�I��
			dialog.tbl.requestFocus();
			dialog.tbl.setRowSelection(0);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �X�v���b�h���f�ΏۊO���ǂ���
	 * 
	 * @param bean
	 * @return true:���ɕҏW���d��A���O
	 */
	protected boolean isSkip(SWK_DTL bean) {

		for (SWK_DTL eraseDtl : nowEraseList) {
			if (Util.equals(getKey(eraseDtl), getKey(bean))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ���̓`�F�b�N
	 * 
	 * @return false:NG
	 */
	protected boolean checkInput() {

		Date termDate = getTermDate(baseDate);

		// �`�[���t�𒴂�����t�`�F�b�N
		if (baseDate != null && !Util.isSmallerThenByYMD(dialog.ctrlSlipDate.getValue(), termDate)) {
			// I00090:�`�[���t ���� {1} �ɂ��Ă��������B
			showMessage("I00090", "C00599", DateUtil.toYMDString(termDate));
			dialog.ctrlSlipDate.requestFocus();
			return false;
		}

		return true;
	}

	/**
	 * BS����\�̊������̎擾
	 * 
	 * @param value
	 * @return ������
	 */
	protected Date getTermDate(Date value) {
		if (isBsTermLastDay) {
			return DateUtil.getLastDate(value);
		}
		return value;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	protected BSEraseCondition getCondition() {
		BSEraseCondition condition = createCondition();
		condition.setCompanyCode(getCompanyCode()); // ��ЃR�[�h
		condition.setSlipDate(dialog.ctrlSlipDate.getValue()); // �`�[���t
		condition.setItem(dialog.ctrlItemGroup.getEntity()); // �Ȗ�
		condition.setCustomerCode(dialog.ctrlCustomer.getCode()); // �����
		condition.setDepartmentCode(dialog.ctrlDepartment.getCode());// ����
		condition.setEmployeeCode(dialog.ctrlEmployee.getCode()); // �Ј�
		condition.setManegement1Code(dialog.ctrlManage1.getCode()); // �Ǘ�1�R�[�h
		condition.setManegement2Code(dialog.ctrlManage2.getCode()); // �Ǘ�2�R�[�h
		condition.setManegement3Code(dialog.ctrlManage3.getCode()); // �Ǘ�3�R�[�h
		condition.setManegement4Code(dialog.ctrlManage4.getCode()); // �Ǘ�4�R�[�h
		condition.setManegement5Code(dialog.ctrlManage5.getCode()); // �Ǘ�5�R�[�h
		condition.setManegement6Code(dialog.ctrlManage6.getCode()); // �Ǘ�6�R�[�h
		condition.setHm1(dialog.ctrlNonAcDetails.getValue(1)); // ���v����1
		condition.setHm2(dialog.ctrlNonAcDetails.getValue(2)); // ���v����2
		condition.setHm3(dialog.ctrlNonAcDetails.getValue(3)); // ���v����3
		condition.setExcludeSlipNo(currentSlipNo); // ����ҏW���`�[�ԍ�
		condition.setCurrencyCode(nowCurrencyCode); // ���ݑI�𒆒ʉ݃R�[�h

		// �ҏW���̏����`�[�ԍ��A�s�ԍ�
		for (SWK_DTL bs : nowEraseList) {
			condition.add(bs.getSWK_DEN_NO(), bs.getSWK_GYO_NO());
		}
		return condition;
	}

	/**
	 * ������������
	 * 
	 * @return condition
	 */
	protected BSEraseCondition createCondition() {
		return new BSEraseCondition();
	}

	/**
	 * �e�[�u���ɐݒ肷��z��f�[�^���擾����
	 * 
	 * @param bean
	 * @return �s�f�[�^
	 */
	protected List<Object> getRow(SWK_DTL bean) {
		// �����_����
		int digit = bean.getCUR_DEC_KETA();

		List<Object> list = new ArrayList<Object>(SC.values().length);

		String inputAmount = NumberFormatUtil.formatNumber(bean.getSWK_IN_KIN(), digit);
		String rate = NumberFormatUtil.formatNumber(bean.getSWK_CUR_RATE(), 4);
		String amount = NumberFormatUtil.formatNumber(bean.getSWK_KIN(), keyDigit);

		list.add(bean);
		list.add(false);
		list.add(bean.getSWK_TRI_CODE());
		list.add(bean.getSWK_TRI_NAME_S());
		list.add(bean.getSWK_DEP_CODE());
		list.add(bean.getSWK_DEP_NAME_S());
		list.add(bean.getSWK_KMK_CODE());
		list.add(bean.getSWK_KMK_NAME_S());
		list.add(bean.getSWK_HKM_CODE());
		list.add(bean.getSWK_HKM_NAME_S());
		list.add(bean.getSWK_UKM_CODE());
		list.add(bean.getSWK_UKM_NAME_S());
		list.add(DateUtil.toYMDString(bean.getSWK_DEN_DATE()));
		list.add(bean.getSWK_DEN_NO());
		list.add(bean.getSWK_GYO_NO());
		list.add(bean.getSWK_CUR_CODE());
		list.add(SC.SWK_CUR_RATE.ordinal(), rate);
		list.add(bean.isDR() ? inputAmount : null);
		list.add(bean.isDR() ? amount : null);
		list.add(bean.isDR() ? null : inputAmount);
		list.add(bean.isDR() ? null : amount);
		list.add(bean.getSWK_GYO_TEK());

		list.add(bean.getSWK_EMP_CODE()); // �Ј��R�[�h
		list.add(bean.getSWK_EMP_NAME_S()); // �Ј�����
		list.add(bean.getSWK_KNR_CODE_1()); // �Ǘ��P�R�[�h
		list.add(bean.getSWK_KNR_NAME_S_1()); // �Ǘ��P����
		list.add(bean.getSWK_KNR_CODE_2()); // �Ǘ��Q�R�[�h
		list.add(bean.getSWK_KNR_NAME_S_2()); // �Ǘ��Q����
		list.add(bean.getSWK_KNR_CODE_3()); // �Ǘ��R�R�[�h
		list.add(bean.getSWK_KNR_NAME_S_3()); // �Ǘ��R����
		list.add(bean.getSWK_KNR_CODE_4()); // �Ǘ��S�R�[�h
		list.add(bean.getSWK_KNR_NAME_S_4()); // �Ǘ��S����
		list.add(bean.getSWK_KNR_CODE_5()); // �Ǘ��T�R�[�h
		list.add(bean.getSWK_KNR_NAME_S_5()); // �Ǘ��T����
		list.add(bean.getSWK_KNR_CODE_6()); // �Ǘ��U�R�[�h
		list.add(bean.getSWK_KNR_NAME_S_6()); // �Ǘ��U����
		list.add(bean.getSWK_HM_1()); // ���v���ׂP
		list.add(bean.getSWK_HM_2()); // ���v���ׂQ
		list.add(bean.getSWK_HM_3()); // ���v���ׂR

		return list;
	}

	/**
	 * �m�菈��
	 */
	protected void doSettle() {

		try {

			List<SWK_DTL> list = getSelectedList();

			if (list.isEmpty()) {
				// I00043:�ꗗ����f�[�^��I�����Ă��������B
				showMessage("I00043");
				dialog.tbl.requestFocus();
				return;
			}

			// �r��
			BSEraseCondition condition = new BSEraseCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setKCompany(dialog.ctrlKCompany.getEntity());
			condition.setExcludeSlipNo(currentSlipNo); // ����ҏW���`�[�ԍ�
			for (SWK_DTL bs : list) {
				condition.add(bs.getSWK_DEN_NO(), bs.getSWK_GYO_NO());
			}

			request(getManagerClass(), "lock", condition);

			// ����
			this.option = OK_OPTION;

			dialog.dispose();

		} catch (TException ex) {
			errorHandler(ex);
		}
	}

	/**
	 * ����{�^������
	 */
	protected void doCancel() {
		this.option = CANCEL_OPTION;

		dialog.dispose();
	}

	/**
	 * ���v���v�Z����
	 */
	protected void summary() {

		// �ϐ�������
		BigDecimal drInput = BigDecimal.ZERO; // DR���͋��z���v
		BigDecimal crInput = BigDecimal.ZERO; // CR���͋��z���v
		int digit = 0;
		BigDecimal dr = BigDecimal.ZERO; // DR���v
		BigDecimal cr = BigDecimal.ZERO; // CR���v

		List<SWK_DTL> list = getSelectedList();

		// �I���s�̎擾
		for (SWK_DTL dtl : list) {
			// ���͋��z�|�����ϋ��z(���͒ʉ�) : �P�����Z
			drInput = drInput.add(dtl.getDebitInputAmount());
			crInput = crInput.add(dtl.getCreditInputAmount());
			digit = Math.max(digit, dtl.getCUR_DEC_KETA());
			// ����z�|�����ϋ��z(��ʉ�)
			dr = dr.add(dtl.getDebitKeyAmount());
			cr = cr.add(dtl.getCreditKeyAmount());
		}

		// ���v�l�̐ݒ�
		dialog.ctrlDrInputTotal.setNumber(drInput);
		dialog.ctrlCrInputTotal.setNumber(crInput);
		dialog.ctrlDrInputTotal.setDecimalPoint(digit);
		dialog.ctrlCrInputTotal.setDecimalPoint(digit);
		dialog.ctrlDrTotal.setNumber(dr);
		dialog.ctrlCrTotal.setNumber(cr);

	}

	/**
	 * �I���s�f�[�^�̎擾
	 * 
	 * @return BS�����Ώۃ��X�g
	 */
	public List<SWK_DTL> getSelectedList() {
		List<SWK_DTL> list = new ArrayList<SWK_DTL>();

		for (int i = 0; i < dialog.tbl.getRowCount(); i++) {

			Boolean isChecked = (Boolean) dialog.tbl.getRowValueAt(i, SC.CHECK);

			if (isChecked) {
				list.add((SWK_DTL) dialog.tbl.getRowValueAt(i, SC.BEAN));
			}
		}

		return list;
	}

	/**
	 * ���ɏ����I�����Ă����s���Z�b�g
	 * 
	 * @param eraseList �����s���
	 */
	public void setNowEraseList(List<SWK_DTL> eraseList) {
		this.nowEraseList = eraseList;

		if (eraseList != null) {
			this.nowEraseKeyList.clear();
			for (SWK_DTL dtl : eraseList) {
				nowEraseKeyList.add(getKey(dtl));
			}
		}
	}

	/**
	 * �d��̔���L�[�̎擾
	 * 
	 * @param dtl
	 * @return �d��̔���L�[
	 */
	protected String getKey(SWK_DTL dtl) {
		return dtl.getSWK_DEN_NO() + "<>" + dtl.getSWK_GYO_NO();
	}

	/**
	 * �`�[���t��ݒ肷��B�i�����l�j
	 * 
	 * @param slipDate �`�[���t
	 */
	public void setBaseDate(Date slipDate) {
		this.baseDate = slipDate;
		dialog.ctrlSlipDate.setValue(slipDate);
	}

	/**
	 * �Ȗ�-�⏕-�����ݒ肷��B�i�����l�j
	 * 
	 * @param bean
	 */
	public void setItem(Item bean) {
		dialog.ctrlItemGroup.setEntity(bean);
	}

	/**
	 * ������ݒ肷��B�i�����l�j
	 * 
	 * @param bean
	 */
	public void setCustomer(Customer bean) {
		dialog.ctrlCustomer.setEntity(bean);
	}

	/**
	 * �����ݒ肷��B�i�����l�j
	 * 
	 * @param bean
	 */
	public void setDepartment(Department bean) {
		dialog.ctrlDepartment.setEntity(bean);
	}

	/**
	 * ����ҏW���`�[�ԍ��̐ݒ�
	 * 
	 * @param slipNo �`�[�ԍ�
	 */
	public void setCurrentSlipNo(String slipNo) {
		this.currentSlipNo = slipNo;
	}

	/**
	 * ����ҏW���ʉ݃R�[�h�̐ݒ�
	 * 
	 * @param currencyCode �ʉ݃R�[�h
	 */
	public void setCurrentCurrencyCode(String currencyCode) {
		this.nowCurrencyCode = currencyCode;
	}

}
