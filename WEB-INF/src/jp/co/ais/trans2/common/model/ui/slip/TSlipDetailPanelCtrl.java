package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.math.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.model.ui.slip.TSlipDetailPanel.SC;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.balance.*;
import jp.co.ais.trans2.model.bs.*;
import jp.co.ais.trans2.model.calc.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.remark.*;
import jp.co.ais.trans2.model.slip.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * �`�[���׃R���|�[�l���g�R���g���[��
 */
public class TSlipDetailPanelCtrl extends TController {

	/** true:�O���[�v��v��Client�� */
	public static boolean groupAccounting = ClientConfig.isFlagOn("trans.slip.group.accounting");

	/** true:�v�㕔�叉���u�����N��Client�� */
	public static boolean departmentDefaultBlank = ClientConfig.isFlagOn("trans.slip.department.default.blank");

	/** true:�Ј��̃f�t�H���g�l�u�����N��Client�� */
	public static boolean employeeDefaultBlank = ClientConfig.isFlagOn("trans.slip.employee.default.blank");

	/** BS��������@�\�g�����ǂ�����Client�� */
	public static boolean isUseBS = ClientConfig.isFlagOn("trans.slip.use.bs");

	/** true:BS����͌v���ЃR�[�h�̓��͒l�𗘗p��Client�� */
	public static boolean isBsUseKCompany = ClientConfig.isFlagOn("trans.slip.bs.use.kcompany");

	/** �s�}���@�\�g�����ǂ�����Client�� */
	public static boolean isUseRowInsert = ClientConfig.isFlagOn("trans.slip.use.rowinsert");

	/** �s�̃G�N�Z���_�E�����[�h / �A�b�v���[�h�@�\�𗘗p���邩��Client�� */
	public static boolean isUseRowExcel = ClientConfig.isFlagOn("trans.slip.use.row.excel");

	/** �s�̃G�N�Z���A�b�v���[�h���s���㏑�����邩��Client�� */
	public static boolean isOverwriteExcel = !ClientConfig.isFlagOn("trans.slip.row.excel.not.overwrite");

	/** true:IFRS�敪��蔭�����͉Ȗڂ̔������t���O�ɂ�萧��@�\�L����Client�� */
	public static boolean allowOccurDateBlank = ClientConfig
		.isFlagOn("trans.slip.detail.allow.occurdate.blank.when.noifrs");

	/** true:�������u�����N�̏ꍇ�A�f�t�H���g�l��o�^����@�\�L�� */
	public static boolean allowEntryDefaultOccurDate = ClientConfig
		.isFlagOn("trans.slip.detail.entry.default.occurdate");

	/** true:�v���ЕύX��薾�׍��ڍŐV�擾���s���A���݂��Ă��Ȃ��ꍇ�̂݃N���A��Client�� */
	public static boolean notClearByCompany = ClientConfig.isFlagOn("trans.slip.detail.change.company.no.clear");

	/** �c���R���o�[�^�[ */
	public static TDetailConverter detailConverter = null;

	/** ���׃��C�A�E�g���� */
	public static TDetailLayoutController detailLayoutController = null;

	/** ���c�������@�\�������ǂ�����Client�� */
	public static boolean isNotUseAp = ClientConfig.isFlagOn("trans.slip.disable.ap.zan");

	/** ���c�������@�\�������ǂ�����Client�� */
	public static boolean isNotUseAr = ClientConfig.isFlagOn("trans.slip.disable.ar.zan");

	/** true:�������̃f�t�H���g�l��`�[���t��ݒ肷��@�\�L�� */
	public static boolean allowDefaultOccurDate = ClientConfig.isFlagOn("trans.slip.detail.default.occurdate");

	/** true:�v�㕔��SSPC�@�\�L����Client�� */
	public static boolean isUseAllCompanyDepartment = ClientConfig
		.isFlagOn("trans.slip.detail.use.all.company.department");

	/** ���ד��͂ōs�m����������ɑ��{�^�����������Ƃ�warning���o�����ۂ���true:�x���\�� */
	public static boolean inputDetailCheckAlert = ClientConfig.isFlagOn("trans.slip.detail.input.check.alert");

	/** ����D�揇�ʃ��X�g */
	public static Map<String, String> depPriorityMap = null;

	/** true: 2023INVOICE���x�Ή����g�p���� */
	public static boolean isInvoiceTaxProperty = ClientConfig.isFlagOn("trans.slip.use.invoice.tax");

	static {

		try {
			// �c���R���o�[�^�[
			Class clazz = Class.forName(ClientConfig.getProperty("trans.slip.detail.converter"));
			detailConverter = (TDetailConverter) clazz.newInstance();
		} catch (Throwable e) {
			detailConverter = new TDetailConverter();
		}

		try {
			// ���׃��C�A�E�g����
			Class clazz = Class.forName(ClientConfig.getProperty("trans.slip.detail.layout.controller"));
			detailLayoutController = (TDetailLayoutController) clazz.newInstance();
		} catch (Throwable e) {
			// �����Ȃ�
		}

		try {
			depPriorityMap = new HashMap<String, String>();

			// �����D�悳�����ЃZ�b�g

			String[] depList = ClientConfig.getProperties("trans.slip.use.dep.priority.cmp");
			String sep = "#";
			for (String dep : depList) {
				String depCode = dep.split(sep)[0];
				String cmpCode = dep.split(sep)[1];
				depPriorityMap.put(depCode, cmpCode);
			}
		} catch (Throwable e) {
			// �����Ȃ�
		}

	}

	/** �p�l�� */
	protected TSlipDetailPanel panel;

	/** ��v�n�ݒ� */
	protected AccountConfig conf = getCompany().getAccountConfig();

	/** ��ʉ� */
	protected Currency keyCurrency = conf.getKeyCurrency();

	/** ��ʉݏ����_���� */
	protected int keyDigit = keyCurrency.getDecimalPoint();

	/** �v�Z���W�b�N */
	protected TCalculator calculator = TCalculatorFactory.getCalculator();

	/** �f�t�H���g������ */
	protected boolean defaultTaxInnerDivision = true;

	/** �ݎ؂̃f�t�H���g�l */
	protected Dc defaultDC = Dc.DEBIT;

	/** �`�[��� */
	protected SlipType slipType;

	/** �O���w�薾�� */
	protected SWK_DTL outerDetail;

	/** �O���w������ */
	protected Customer customer;

	/** �O���w��Ј� */
	protected Employee employee;

	/** ��������(���c���ꗗ�p) */
	protected Customer collectionCustomer;

	/** ���(�`�[���t) */
	protected Date baseDate;

	/** ���Z�d��(true:���Z�d��) */
	protected boolean isClosing;

	/** �`�[�`�[�ԍ�(�ҏW�̏ꍇ) */
	protected String slipNo;

	/** ��ʔ��f���Ă��閾�� */
	protected SWK_DTL dispatchDetail;

	/** �s�ҏW�� */
	protected boolean isEditedDetail = false;

	/** �I��O�̍s�ԍ���ޔ� */
	protected int beforeGyoNo = -1;

	/** �s�I�������� */
	protected boolean isProcess = false;

	/** true: 2023INVOICE���x�Ή����g�p����(��Џ��܂�) */
	public boolean isInvoice = false;

	/** �������`�F�b�N���g�p���邩 */
	protected boolean isUseHasDateChk = conf.isUseHasDateCheck();

	/** ڰăL���b�V���}�b�v */
	protected Map<String, BigDecimal> rateMap = new HashMap<String, BigDecimal>();

	
	/**
	 * �R���X�g���N�^.
	 * 
	 * @param panel
	 */
	public TSlipDetailPanelCtrl(TSlipDetailPanel panel) {

		// �C���{�C�X�g�p���邩�ǂ���
		if (isInvoiceTaxProperty) {
			initInvoiceFlg();
		}

		this.panel = panel;

		// ������ʐ���
		initView();

		// �C�x���g�o�^
		addViewEvent();

		// �����\��
		init();
	}

	/**
	 * @see jp.co.ais.trans.common.client.TPanelCtrlBase#getView()
	 */
	@Override
	public TSlipDetailPanel getView() {
		return panel;
	}

	/**
	 * ��ʕ\����������
	 */
	protected void initView() {

		beforeGyoNo = -1;

		// ������
		Date occurDate = panel.ctrlOccurDate.getValue();

		panel.ctrlTax.getSearchCondition().setValidTerm(occurDate);

		// �v���� ����
		panel.ctrlKCompany.getSearchCondition().setGroupAccountOnly(); // �O���[�v��Ђ̂�

		// �O���[�v��v����
		if (isGroupAccounting()) {
			panel.ctrlKCompany.getSearchCondition().setKeyCurrencyCode(null); // ��ʉ݂��قȂ��Ă��I���\
		} else {
			panel.ctrlKCompany.getSearchCondition().setKeyCurrencyCode(conf.getKeyCurrency().getCode()); // ��ʉ݂�����
		}

		// �E�v ����
		panel.ctrlRemark.getSearchCondition().setSlipRemark(false);
		panel.ctrlRemark.getSearchCondition().setSlipRowRemark(true);

		// �Ǘ��P�`6���p���邩
		panel.ctrlManage1.setVisible(conf.isUseManagement1());
		panel.ctrlManage2.setVisible(conf.isUseManagement2());
		panel.ctrlManage3.setVisible(conf.isUseManagement3());
		panel.ctrlManage4.setVisible(conf.isUseManagement4());
		panel.ctrlManage5.setVisible(conf.isUseManagement5());
		panel.ctrlManage6.setVisible(conf.isUseManagement6());

		// �R���|�[�l���g�̔�\���ɍ��킹�ē��̓t�B�[���h�p�l���̑傫���𒲐�
		if (!conf.isUseManagement1() && !conf.isUseNotAccounting3()) {
			int h = panel.ctrlEmployee.getY() + panel.ctrlEmployee.getHeight() + 2;
			TGuiUtil.setComponentSize(panel.pnlInput, new Dimension(0, h));

		} else if (!conf.isUseManagement2()) {
			int h = panel.ctrlManage1.getY() + panel.ctrlManage1.getHeight() + 2;
			TGuiUtil.setComponentSize(panel.pnlInput, new Dimension(0, h));
		}

		// �X�v���b�h�ݒ�
		initSpreadSheet();

		// ��ʉ݂̏����_�ݒ�
		panel.ctrlInputAmount.setDecimalPoint(keyDigit);
		panel.ctrlTaxAmount.setDecimalPoint(keyDigit);
		panel.ctrlKeyAmount.setDecimalPoint(keyDigit);

		panel.ctrlCrTotal.setDecimalPoint(keyDigit);
		panel.ctrlDrTotal.setDecimalPoint(keyDigit);
		panel.ctrlExchangeDiff.setDecimalPoint(keyDigit);

		panel.ctrlCrTaxTotal.setDecimalPoint(keyDigit);
		panel.ctrlDrTaxTotal.setDecimalPoint(keyDigit);
		panel.ctrlDiff.setDecimalPoint(keyDigit);

		// ���c���@�\����
		if (isNotUseAp) {
			panel.btnAP.setVisible(false);
		}

		// ���c���@�\����
		if (isNotUseAr) {
			panel.btnAR.setVisible(false);
		}

		// BS�������
		if (!isUseBS) {
			panel.btnBS.setVisible(false);
		}

		// �s�}���@�\
		if (!isUseRowInsert) {
			// �s�}���{�^����\��
			panel.btnRowInsert.setVisible(false);
		}

		// ���׃A�b�v�E�_�E�����[�h
		if (!isUseRowExcel) {
			panel.btnDownload.setVisible(false);
			panel.btnUpload.setVisible(false);
		}

		if (isUseAllCompanyDepartment) {
			notClearByCompany = true; // ��ЕύX���Ɍp������(�N���A���Ȃ�)�@�\�������ɗL���ɂ���
			panel.ctrlKDepartment.setAllCompanyMode(true);
		} else {
			panel.ctrlKDepartment.setAllCompanyMode(false);
		}

		// ���׃p�l���̃��C�A�E�g����
		if (detailLayoutController != null) {
			detailLayoutController.allocateComponents(panel);
		}

	}

	/**
	 * invoice�g�p���邩�ǂ���
	 */
	protected void initInvoiceFlg() {

		isInvoice = getCompany().isCMP_INV_CHK_FLG();
	}

	/**
	 * ���׃X�v���b�h�V�[�h�̏����ݒ�
	 */
	public void initSpreadSheet() {
		// �Ȗ�
		String kmkName = conf.getItemName();
		String hkmName = conf.getSubItemName();
		String ukmName = conf.getDetailItemName();
		int ukm = conf.isUseDetailItem() ? 100 : -1;

		// �Ǘ�1�`6
		String mng1Name = conf.getManagement1Name();
		String mng2Name = conf.getManagement2Name();
		String mng3Name = conf.getManagement3Name();
		String mng4Name = conf.getManagement4Name();
		String mng5Name = conf.getManagement5Name();
		String mng6Name = conf.getManagement6Name();
		int mng1 = conf.isUseManagement1() ? 100 : -1;
		int mng2 = conf.isUseManagement2() ? 100 : -1;
		int mng3 = conf.isUseManagement3() ? 100 : -1;
		int mng4 = conf.isUseManagement4() ? 100 : -1;
		int mng5 = conf.isUseManagement5() ? 100 : -1;
		int mng6 = conf.isUseManagement6() ? 100 : -1;

		// ���v����
		NonAccountingDivision nad1 = conf.getNonAccounting1();
		int hkm1 = 100; // ��
		int align1 = SwingConstants.LEFT; // �ʒu
		String hkm1Name = conf.getNonAccounting1Name(); // ����

		switch (nad1) {
			case NONE:
				hkm1 = -1;
				break;

			case NUMBER:
				align1 = SwingConstants.RIGHT;
				break;

			case YMD_DATE:
			case YMDHM_DATE:
				align1 = SwingConstants.CENTER;
				break;
		}

		NonAccountingDivision nad2 = conf.getNonAccounting2();
		int hkm2 = 100;
		int align2 = SwingConstants.LEFT;
		String hkm2Name = conf.getNonAccounting2Name();

		switch (nad2) {
			case NONE:
				hkm2 = -1;
				break;

			case NUMBER:
				align2 = SwingConstants.RIGHT;
				break;

			case YMD_DATE:
			case YMDHM_DATE:
				align2 = SwingConstants.CENTER;
				break;
		}

		NonAccountingDivision nad3 = conf.getNonAccounting3();
		int hkm3 = 100;
		int align3 = SwingConstants.LEFT;
		String hkm3Name = conf.getNonAccounting3Name();

		switch (nad3) {
			case NONE:
				hkm3 = -1;
				break;

			case NUMBER:
				align3 = SwingConstants.RIGHT;
				break;

			case YMD_DATE:
			case YMDHM_DATE:
				align3 = SwingConstants.CENTER;
				break;
		}

		// �J�������x��
		String lblCompanyCode = getWord("C00570"); // �v���ЃR�[�h
		String lblCompanyName = getWord("C01052"); // �v����
		String lblDeptCode = getWord("C00698"); // ����R�[�h
		String lblDeptName = getWord("C00467"); // ����
		String lblCode = getWord("C00174"); // �R�[�h
		String lblTaxDivision = getWord("C00312"); // ��
		String lblTaxCode = getWord("C00573"); // ����ŃR�[�h
		String lblTaxName = getWord("C00774"); // ����Ŗ���
		String lblTaxRate = getWord("C01554"); // �ŗ�
		String lblAmount = getWord("C00123"); // ���z
		String lblCurrency = getWord("C00665"); // �ʉ݃R�[�h
		String lblCurrencyRate = getWord("C01555"); // �ʉ݃��[�g
		String lblDrFAmount = getWord("C01556"); // �ؕ����z(�O��)
		String lblDrAmount = getWord("C01557"); // �ؕ����z
		String lblTaxFAmount = getWord("C10585"); // ����Ŋz(�O��)
		String lblTaxAmount = getWord("C00674"); // ����Ŋz
		String lblCrFAmount = getWord("C01558"); // �ݕ����z(�O��)
		String lblCrAmount = getWord("C01559"); // �ݕ����z
		String lblTekCode = getWord("C01560"); // �s�E�v�R�[�h
		String lblTek = getWord("C00119"); // �s�E�v
		String lblCustomerCode = getWord("C00786"); // �����R�[�h
		String lblCustomerName = getWord("C00408"); // �����
		String lblEmpCode = getWord("C00697"); // �Ј��R�[�h
		String lblEmpName = getWord("C00246"); // �Ј�
		String lblIssuerDay = getWord("C00431"); // ������
		String lblInputAmount = getWord("C00574"); // ���͋��z

		// �J����
		panel.tbl.addColumn(new TTableColumn(SC.bean, "", -1));
		panel.tbl.addColumn(new TTableColumn(SC.kCompCode, lblCompanyCode, 100)); // �v���ЃR�[�h
		panel.tbl.addColumn(new TTableColumn(SC.kCompName, lblCompanyName, 100)); // �v����
		panel.tbl.addColumn(new TTableColumn(SC.depCode, lblDeptCode, 100)); // ����R�[�h
		panel.tbl.addColumn(new TTableColumn(SC.depName, lblDeptName, 100)); // ����
		panel.tbl.addColumn(new TTableColumn(SC.itemCode, kmkName + lblCode, 100)); // �ȖڃR�[�h
		panel.tbl.addColumn(new TTableColumn(SC.itemName, kmkName, 100)); // �Ȗ�
		panel.tbl.addColumn(new TTableColumn(SC.subItemCode, hkmName + lblCode, 100)); // �⏕�R�[�h
		panel.tbl.addColumn(new TTableColumn(SC.subItemName, hkmName, 100)); // �⏕
		panel.tbl.addColumn(new TTableColumn(SC.dtlItemCode, ukmName + lblCode, ukm)); // ����ȖڃR�[�h
		panel.tbl.addColumn(new TTableColumn(SC.dtlItemName, ukmName, ukm)); // ����
		panel.tbl.addColumn(new TTableColumn(SC.taxDivision, lblTaxDivision, 100, SwingConstants.CENTER)); // ��
		panel.tbl.addColumn(new TTableColumn(SC.taxCode, lblTaxCode, 100)); // ����ŃR�[�h
		panel.tbl.addColumn(new TTableColumn(SC.taxName, lblTaxName, 100)); // ����Ŗ���
		panel.tbl.addColumn(new TTableColumn(SC.taxRate, lblTaxRate, 100, SwingConstants.RIGHT)); // �ŗ�
		panel.tbl.addColumn(new TTableColumn(SC.amount, lblAmount, 100, SwingConstants.RIGHT)); // ���z
		panel.tbl.addColumn(new TTableColumn(SC.currency, lblCurrency, 100)); // �ʉ݃R�[�h
		panel.tbl.addColumn(new TTableColumn(SC.currencyRate, lblCurrencyRate, 100, SwingConstants.RIGHT)); // �ʉ݃��[�g
		panel.tbl.addColumn(new TTableColumn(SC.drFAmount, lblDrFAmount, 100, SwingConstants.RIGHT)); // �ؕ����z(�O��)
		panel.tbl.addColumn(new TTableColumn(SC.drAmount, lblDrAmount, 100, SwingConstants.RIGHT)); // �ؕ����z
		panel.tbl.addColumn(new TTableColumn(SC.taxFAmount, lblTaxFAmount, 100, SwingConstants.RIGHT)); // ����Ŋz
		panel.tbl.addColumn(new TTableColumn(SC.taxAmount, lblTaxAmount, 100, SwingConstants.RIGHT)); // ����Ŋz
		panel.tbl.addColumn(new TTableColumn(SC.crFAmount, lblCrFAmount, 100, SwingConstants.RIGHT)); // �ݕ����z(�O��)
		panel.tbl.addColumn(new TTableColumn(SC.crAmount, lblCrAmount, 100, SwingConstants.RIGHT)); // �ݕ����z
		panel.tbl.addColumn(new TTableColumn(SC.tekCode, lblTekCode, 100)); // �s�E�v�R�[�h
		panel.tbl.addColumn(new TTableColumn(SC.tek, lblTek, 100)); // �s�E�v
		panel.tbl.addColumn(new TTableColumn(SC.customerCode, lblCustomerCode, 100)); // �����R�[�h
		panel.tbl.addColumn(new TTableColumn(SC.customerName, lblCustomerName, 100)); // �����
		panel.tbl.addColumn(new TTableColumn(SC.empCode, lblEmpCode, 100)); // �Ј��R�[�h
		panel.tbl.addColumn(new TTableColumn(SC.empName, lblEmpName, 100)); // �Ј�
		panel.tbl.addColumn(new TTableColumn(SC.mng1Code, mng1Name + lblCode, mng1)); // �Ǘ�1�R�[�h
		panel.tbl.addColumn(new TTableColumn(SC.mng1Name, mng1Name, mng1)); // �Ǘ�1
		panel.tbl.addColumn(new TTableColumn(SC.mng2Code, mng2Name + lblCode, mng2)); // �Ǘ�2�R�[�h
		panel.tbl.addColumn(new TTableColumn(SC.mng2Name, mng2Name, mng2)); // �Ǘ�2
		panel.tbl.addColumn(new TTableColumn(SC.mng3Code, mng3Name + lblCode, mng3)); // �Ǘ�3�R�[�h
		panel.tbl.addColumn(new TTableColumn(SC.mng3Name, mng3Name, mng3)); // �Ǘ�3
		panel.tbl.addColumn(new TTableColumn(SC.mng4Code, mng4Name + lblCode, mng4)); // �Ǘ�4�R�[�h
		panel.tbl.addColumn(new TTableColumn(SC.mng4Name, mng4Name, mng4)); // �Ǘ�4
		panel.tbl.addColumn(new TTableColumn(SC.mng5Code, mng5Name + lblCode, mng5)); // �Ǘ�5�R�[�h
		panel.tbl.addColumn(new TTableColumn(SC.mng5Name, mng5Name, mng5)); // �Ǘ�5
		panel.tbl.addColumn(new TTableColumn(SC.mng6Code, mng6Name + lblCode, mng6)); // �Ǘ�6�R�[�h
		panel.tbl.addColumn(new TTableColumn(SC.mng6Name, mng6Name, mng6)); // �Ǘ�6
		panel.tbl.addColumn(new TTableColumn(SC.issuerDay, lblIssuerDay, 100, SwingConstants.CENTER)); // ������
		panel.tbl.addColumn(new TTableColumn(SC.nonAcDtl1, hkm1Name, hkm1, align1)); // ���v����1
		panel.tbl.addColumn(new TTableColumn(SC.nonAcDtl2, hkm2Name, hkm2, align2)); // ���v����2
		panel.tbl.addColumn(new TTableColumn(SC.nonAcDtl3, hkm3Name, hkm3, align3)); // ���v����3
		panel.tbl.addColumn(new TTableColumn(SC.inputAmount, lblInputAmount, 100, SwingConstants.RIGHT)); // ���͋��z

		if (!inputDetailCheckAlert) {
			// ���I����Ԃł�FocusIN��1�s�ڂ�I��
			panel.tbl.addFocusListener(new FocusAdapter() {

				@Override
				public void focusGained(FocusEvent e) {
					if (panel.tbl.getRowCount() != 0 && panel.tbl.getSelectedRow() < 0) {
						panel.tbl.setRowSelection(0);
					}
				}
			});
		}
	}

	/**
	 * �C�x���g�o�^
	 */
	protected void addViewEvent() {

		// �V�K�s
		panel.btnRowNew.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doNew(true); // �A���[�g�`�F�b�N�s��
			}
		});

		// BS�������
		if (isUseBS) {
			panel.btnBS.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					doBS();
				}
			});
		}

		// �s�}���@�\
		if (isUseRowInsert) {
			// �s�}��
			panel.btnRowInsert.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					doInsert();
				}
			});

			// �X�v���b�h�s�ԍ��N���b�N����
			panel.tbl.setAdapter(new TTableAdapter() {

				@Override
				public void afterRowHeaderClicked() {
					clickTableRowHeader();
				}

			});
		}

		// �s����
		panel.btnRowCopy.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doCopy();
			}
		});

		// �s�폜
		panel.btnRowDelete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doDelete();
			}
		});

		// �s�m��
		panel.btnRowEntry.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doEntry();
			}
		});

		// �X�v���b�h�ŏ�ʍs�ړ��{�^��
		panel.btnRowTop.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doMoveTop();
			}
		});

		// �X�v���b�h��s�ړ��{�^��
		panel.btnRowUp.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doMoveUp();
			}
		});

		// �X�v���b�h���s�ړ��{�^��
		panel.btnRowDown.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doMoveDown();
			}
		});

		// �X�v���b�h�ŉ��ʍs�ړ��{�^��
		panel.btnRowUnder.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doMoveUnder();
			}
		});

		// �X�v���b�h�I���s�ύX
		panel.tbl.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {

				if (isProcess) {
					return;
				}

				try {

					if (e.getValueIsAdjusting()) {
						return;
					}

					if (beforeGyoNo != -1 && beforeGyoNo == panel.tbl.getSelectedRow()) {
						return;
					}

					isProcess = true;

					// ���ׂɕύX���������ꍇ�A�s���m�肹���ɏ������s����
					if (!rowSelectionDetailChanged()) {
						return;
					} else {
						selectedJornal();
					}
				} finally {
					isProcess = false;
				}

			}
		});

		// �v����
		panel.ctrlKCompany.addCallBackListener(new TCallBackListener() {

			@Override
			public boolean afterVerify(boolean flag) {
				if (!flag) {
					return false;
				}
				// �s�ҏW��
				isEditedDetail = true;

				return changedCompany();
			}
		});

		// �v�㕔��
		panel.ctrlKDepartment.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag) {
					return;
				}
				// �s�ҏW��
				isEditedDetail = true;

				changedDepartment();
			}
		});

		// �Ȗ�
		panel.ctrlItem.addCallBackListener(new TCallBackListener() {

			@Override
			public boolean afterVerify(boolean flag) {
				if (!flag) {
					return false;
				}
				// �s�ҏW��
				isEditedDetail = true;

				return changedItem();
			}
		});

		// ������
		panel.ctrlOccurDate.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (!panel.ctrlOccurDate.isValueChanged2()) {
					return true;
				}
				// �s�ҏW��
				isEditedDetail = true;

				changedOccurDate();
				return true;
			}
		});

		// �ʉ�
		panel.ctrlCurrency.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag) {
					return;
				}
				// �s�ҏW��
				isEditedDetail = true;

				changedCurrency();
			}
		});

		// �ʉ݃��[�g
		panel.ctrlRate.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (!panel.ctrlRate.isValueChanged2()) {
					return true;
				}
				// �s�ҏW��
				isEditedDetail = true;

				changedRate();
				return true;
			}
		});

		// �����
		panel.ctrlTax.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag) {
					return;
				}
				// �s�ҏW��
				isEditedDetail = true;

				changedTax();
			}
		});

		// �ŋ敪
		panel.ctrlTaxDivision.addItemListener(0, new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				// �s�ҏW��
				isEditedDetail = true;

				changedTaxDivision();
			}
		});

		// �ݎ�
		panel.ctrlDrCr.addItemListener(0, new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				// �s�ҏW��
				isEditedDetail = true;
				changedDC();
			}
		});

		// ���͋��z
		panel.ctrlInputAmount.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (!panel.ctrlInputAmount.isValueChanged2()) {
					return true;
				}
				// �s�ҏW��
				isEditedDetail = true;

				changedInputAmount();

				return true;
			}
		});

		// ����ŋ��z
		panel.ctrlTaxAmount.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (!panel.ctrlTaxAmount.isValueChanged2()) {
					return true;
				}
				// �s�ҏW��
				isEditedDetail = true;

				changedTaxAmount();

				return true;
			}
		});

		// ����z
		panel.ctrlKeyAmount.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (!panel.ctrlKeyAmount.isValueChanged2()) {
					return true;
				}
				// �s�ҏW��
				isEditedDetail = true;

				changedKeyAmount();

				return true;
			}
		});

		// �������{�^��
		panel.btnAP.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doAPErasing();
			}
		});

		// �������{�^��
		panel.btnAR.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doARErasing();
			}
		});

		// ���v���̒ʉ݃R���{�{�b�N�X
		panel.cbxCurrencyOnTotal.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				changedCurrencyOnTotal();
			}
		});

		if (isUseRowExcel) {
			// �_�E�����[�h�{�^��
			panel.btnDownload.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					doExcelDownload();
				}
			});

			// �A�b�v���[�h�{�^��
			panel.btnUpload.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					doExcelUpload();
				}
			});
		}

		// ����惊�t�@�����X
		panel.ctrlCustomer.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {

				if (!flag) {
					return;
				}
				if (isInvoice) {
					// �����}�X�^�Ŕ�K�i���������s���Ǝ҂Őݒ肳��Ă������ł�ݒ�
					setTaxNoInvReg(panel.ctrlTax.getEntity());
				}
			}

		});

		// �s�ҏW�Ď�
		if (inputDetailCheckAlert) {
			TCallBackListener callback = new TCallBackListener() {

				@Override
				public void after(boolean flag) {
					if (!flag) {
						return;
					}
					// �s�ҏW��
					isEditedDetail = true;

				}
			};

			TInputVerifier verifier = new TInputVerifier() {

				@Override
				public boolean verifyCommit(JComponent comp) {
					if (!panel.ctrlRemark.name.isValueChanged2()) {
						return true;
					}
					// �s�ҏW��
					isEditedDetail = true;
					return true;
				}
			};

			// �s�E�v
			panel.ctrlRemark.addCallBackListener(callback);
			// �s�E�v(����)
			panel.ctrlRemark.name.setInputVerifier(verifier);
			// �����ύX
			panel.ctrlCustomer.addCallBackListener(callback);
			// �Ј��ύX
			panel.ctrlEmployee.addCallBackListener(callback);
			// �Ǘ�1�ύX
			panel.ctrlManage1.addCallBackListener(callback);
			// �Ǘ�2�ύX
			panel.ctrlManage2.addCallBackListener(callback);
			// �Ǘ�3�ύX
			panel.ctrlManage3.addCallBackListener(callback);
			// �Ǘ�4�ύX
			panel.ctrlManage4.addCallBackListener(callback);
			// �Ǘ�5�ύX
			panel.ctrlManage5.addCallBackListener(callback);
			// �Ǘ�6�ύX
			panel.ctrlManage6.addCallBackListener(callback);
		}
	}

	/**
	 * �`�[���׍s���G�N�Z���o�͂���
	 */
	protected void doExcelDownload() {
		if (panel.tbl.getRowCount() == 0) {
			// ���ׂ�����܂���B
			showMessage("I00363");
			return;
		}
		try {
			List<SWK_DTL> list = getEntityList();
			if (outerDetail != null) {
				list.remove(outerDetail);
			}
			byte[] exl = (byte[]) request(getManagerClass(), "getDetailExcel", list);
			// "�`�[���׈ꗗ"

			// �o��
			TPrinter printer = new TPrinter();
			printer.preview(exl, getWord("C04293") + ".xls");
		} catch (Exception e) {
			// ���[�o�͂Ɏ��s���܂����B
			showMessage("W00112");
		}

	}

	/**
	 * �G�N�Z������`�[���׍s���A�b�v���[�h����
	 */
	protected void doExcelUpload() {
		try {
			// �t�@�C���I���_�C�A���O���J��
			TFileChooser fc = new TFileChooser();

			File dir = getPreviousFolder(".chooser");

			// �O��̃t�H���_�𕜌�
			if (dir != null) {
				fc.setCurrentDirectory(dir);
			}

			// �e�L�X�g�t�@�C��(MS-Excel�`��)�t�B���^�[
			TFileFilter filter = new TFileFilter(getWord("C04293"), ExtensionType.XLS, ExtensionType.XLSX);
			fc.setFileFilter(filter);

			if (TFileChooser.FileStatus.Selected != fc.show(panel)) {
				return;
			}

			// ����t�@�C�����ߋ��Ɏ�荞�܂�Ă���ꍇ�x�����b�Z�[�W��\��
			TFile file = fc.getSelectedTFile();
			file.setKey(getFileRecordKey());

			// �Ō�̎捞�t�H���_�ۑ�
			saveFolder(fc.getCurrentDirectory(), ".chooser");

			// �捞�t�@�C���`�F�b�N
			List<SWK_DTL> dtlList = new ArrayList<SWK_DTL>();
			// �R���o�[�g���T�[�o�[�Ƀ��N�G�X�g
			dtlList = (List<SWK_DTL>) request(getManagerClass(), "convertExcelToDetails", file.getFile(), this.slipType);

			if (isInvoice && dtlList != null && !dtlList.isEmpty()) {
				if (checkInvoiceItemTaxCode(dtlList)) {
					// �C���{�C�X�p:�����Ə���ł��K�i�A�K�i���`�F�b�N
					return;
				}
			}

			// �s�㏑���̏����̏ꍇ�O�s�N���A
			if (isOverwriteExcel) {
				panel.tbl.removeRow();
			}

			for (SWK_DTL dtl : dtlList) {
				// �s�̒ǉ�
				List<Object> list = getRow(dtl);
				panel.tbl.addRow(list);
			}
		} catch (Exception e) {
			errorHandler(e);
		}
		// ���v�̒ʉ�
		makeCurrencyComboBox();

		// ���v�v�Z
		summary();

		// �ޔ��s�̏�����
		beforeGyoNo = -1;

	}

	/**
	 * invoice�p �ȖڃR�[�h������ŉȖڂ��m�F
	 * 
	 * @param list
	 * @return ���s���邩�ǂ��� true:���s���Ȃ�
	 */
	protected boolean checkInvoiceItemTaxCode(List<SWK_DTL> list) {

		if (chkSlipTypeInvoice()) {
			return false;
		}
		List<Message> msgList = new ArrayList<Message>();
		int row = 0;
		int rowNo = 1;

		for (SWK_DTL dtl : list) {
			row++;
			if (Util.isNullOrEmpty(dtl.getSWK_KMK_CODE())) {
				continue;
			}

			String kmk = Util.avoidNull(dtl.getSWK_KMK_CODE());
			String hkm = Util.avoidNull(dtl.getSWK_HKM_CODE());
			String ukm = Util.avoidNull(dtl.getSWK_UKM_CODE());

			if (TLoginInfo.isTaxAutoItem(kmk, hkm, ukm)) {
				Message msg = new Message();
				msg.setMessage(getMessage("I01119", row));
				msg.setSubMessageID(Integer.toString(rowNo));
				msgList.add(msg);
				rowNo++;
				// n�s�ڂɏ���ŉȖڂ����͂���Ă��܂��B

			}
		}

		msgList = checkCustomerTaxInvReg(list, msgList, rowNo); // ��K�i���K�i��

		if (msgList.size() != 0) {
			if (ConfermMessageListDialog.OK_OPTION != showConfermMessageList(getView(), getMessage("I01111"), msgList)) {
				// ���b�Z�[�W�\�� ���s���܂����H
				return true;
			}
		}

		return false;

	}

	/**
	 * invoice�p upload�����f�[�^�̎����Ə���ł��K�i�A�K�i���`�F�b�N
	 * 
	 * @param list
	 * @param msgList ���b�Z�[�WList
	 * @param rowNo ���Ԕԍ�
	 * @return MessageList
	 */
	protected List<Message> checkCustomerTaxInvReg(List<SWK_DTL> list, List<Message> msgList, int rowNo) {

		int row = 0;

		for (SWK_DTL dtl : list) {
			row++;
			Message msg = new Message();

			if (Util.isNullOrEmpty(dtl.getSWK_TRI_CODE()) || Util.isNullOrEmpty(dtl.getSWK_ZEI_CODE())) {
				continue;
			}
			// �����A����ł̃G���e�B�e�B�擾
			Customer cus = dtl.getCustomer();
			ConsumptionTax tax = dtl.getTax();
			if (cus == null || tax == null) {
				continue;
			}

			if (cus.isNO_INV_REG_FLG()) {
				if (!tax.isNO_INV_REG_FLG()) {
					msg.setMessage(getMessage("I01112", row, "C12197", tax.getCode() + ":" + tax.getNames()));
					msg.setSubMessageID(Integer.toString(rowNo));
					msgList.add(msg);
					rowNo++;
					// x�s�� ��K�i���������s���Ǝ҂ɑ΂��āy{�����}�z���ݒ肳��Ă��܂��B
				}
			} else {
				if (tax.isNO_INV_REG_FLG()) {
					msg.setMessage(getMessage("I01112", row, "C12196", tax.getCode() + ":" + tax.getNames()));
					msg.setSubMessageID(Integer.toString(rowNo));
					msgList.add(msg);
					rowNo++;
					// x�s�� �K�i���������s���Ǝ҂ɑ΂��āy{�����}�z���ݒ肳��Ă��܂��B
				}
			}
		}

		return msgList;

	}

	/**
	 * INVOICE�p�F�`�[��ʂŃ`�F�b�N���x�g�p���邩
	 * 
	 * @return false:�g�p����
	 */
	protected boolean chkSlipTypeInvoice() {

		if (slipType == null || !slipType.isINV_SYS_FLG()) {
			return true;
		}

		if (slipType.getCode().equals("031") && Util.isNullOrEmpty(getCompany().getInvRegNo())) {
			// ���v�ォ��Ѓ}�X�^�ɓK�i���������s���ƎҔԍ������͂���Ă��Ȃ��ꍇ�̓G���[���b�Z�[�W�s�v
			return true;
		}
		return false;
	}

	/**
	 * �w��̃t�H���_����ۑ�����
	 * 
	 * @param type ���
	 * @param dir �t�H���_���
	 */
	protected void saveFolder(File dir, String type) {
		String path = SystemUtil.getAisSettingDir();
		FileUtil.saveObject(dir, path + File.separator + getFolderKeyName() + type);
	}

	/**
	 * �O��ۑ������t�H���_�����擾
	 * 
	 * @param type ���
	 * @return �O��ۑ������t�H���_���
	 */
	protected File getPreviousFolder(String type) {

		String path = SystemUtil.getAisSettingDir();
		File dir = (File) FileUtil.getObject(path + File.separator + getFolderKeyName() + type);

		return dir;
	}

	/**
	 * @return FolderKeyName���擾
	 */
	protected String getFolderKeyName() {
		return getProgramCode();
	}

	/**
	 * �t�@�C���Ăяo���_�C�A���O�̃L�[
	 * 
	 * @return �t�@�C���Ăяo���_�C�A���O�̃L�[
	 */
	protected String getFileRecordKey() {
		return "SLIP";
	}

	/**
	 * �������
	 */
	public void init() {
		this.dispatchDetail = null;
		this.slipNo = null;
		// �ޔ��s�̏�����
		this.beforeGyoNo = -1;
		// �s�ҏW��
		this.isEditedDetail = false;

		// ���׃N���A
		panel.tbl.removeRow();

		// �{�^������
		controllButtons();

		// ���̓N���A
		clearInput();
	}

	/**
	 * ���͕��̂ݏ������
	 */
	public void clearInput() {
		// �N���A
		panel.ctrlKCompany.clear();
		panel.ctrlKDepartment.clear();
		panel.ctrlItem.clear();
		panel.ctrlOccurDate.clear();
		panel.ctrlTaxDivision.setSelectON(defaultTaxInnerDivision ? 0 : 1);
		panel.ctrlRemark.clear();
		panel.ctrlDrCr.setDR(defaultDC == Dc.DEBIT);
		panel.ctrlInputAmount.clear();

		// �����l
		setCompany(getCompany());

		if (isDefaultBlankDepartment()) {
			// �v�㕔��̏����l��null�̏ꍇ�A���[�U���̐ݒ�s�v
			setDepartment(null);
		} else {
			// �f�t�H���g�v�㕔��
			setDepartment(getUser().getDepartment());
		}

		// ���͐���
		panel.ctrlKCompany.setEditable(conf.isUseGroupAccount()); // �O���[�v��vOFF���͓��͕s��
		panel.ctrlKDepartment.setEditable(true);
		panel.ctrlItem.ctrlItemReference.setEditable(!isDefaultBlankDepartment());
		panel.ctrlOccurDate.setEditable(true);
		panel.ctrlTaxDivision.setEnabled(true);
		panel.ctrlRemark.setEditable(true);
		panel.ctrlDrCr.setEnabled(true);
		panel.ctrlInputAmount.setEditable(true);

		// �ȖژA���n
		clearInputForItem();

		// ������
		Date occurDate = panel.ctrlOccurDate.getValue();

		panel.ctrlTax.getSearchCondition().setValidTerm(occurDate);

		// �������f�t�H���g�l�ݒ�
		if (allowDefaultOccurDate && panel.ctrlOccurDate.isVisible() && panel.ctrlOccurDate.isEditable()) {
			panel.ctrlOccurDate.setValue(baseDate);
			changedOccurDate();
		}

	}

	/**
	 * @return true:�v�㕔�叉���u�����N
	 */
	protected boolean isDefaultBlankDepartment() {
		return TSlipDetailPanelCtrl.departmentDefaultBlank;
	}

	/**
	 * �Ȗڊ֘A���͕��̂ݏ������
	 */
	public void clearInputForItem() {
		// �N���A
		panel.ctrlCurrency.clear();
		panel.ctrlRate.clear();
		panel.ctrlTax.clear();
		panel.ctrlTaxAmount.clear();
		panel.ctrlKeyAmount.clear();
		panel.ctrlCustomer.clear();
		panel.ctrlEmployee.clear();
		panel.ctrlManage1.clear();
		panel.ctrlManage2.clear();
		panel.ctrlManage3.clear();
		panel.ctrlManage4.clear();
		panel.ctrlManage5.clear();
		panel.ctrlManage6.clear();
		panel.ctrlNonAcDetails.clear();

		// ���͐���
		panel.ctrlCurrency.setEditable(false);
		panel.ctrlRate.setEditable(false);
		panel.ctrlTax.setEditable(false);
		panel.ctrlTaxAmount.setEditable(false);
		panel.ctrlKeyAmount.setEditable(false);
		panel.ctrlCustomer.setEditable(false);
		panel.ctrlEmployee.setEditable(false);
		panel.ctrlManage1.setEditable(false);
		panel.ctrlManage2.setEditable(false);
		panel.ctrlManage3.setEditable(false);
		panel.ctrlManage4.setEditable(false);
		panel.ctrlManage5.setEditable(false);
		panel.ctrlManage6.setEditable(false);
		panel.ctrlNonAcDetails.setEditable(false);

		// �����l
		// �w�b�_�[�ʉ�
		Currency hdrCur = getHeaderCurrency();
		if (hdrCur != null) {
			setCurrecy(hdrCur);
		} else {
			setCurrecy(keyCurrency);
		}

		summary();
	}

	/**
	 * �{�^���R���g���[��
	 */
	protected void controllButtons() {
		int count = panel.tbl.getRowCount();
		int row = panel.tbl.getSelectedRow();
		int starRow = panel.tbl.getRowHeaderStarIndex();

		// ���ʂ͏����s�̏ꍇ�A�����s��
		panel.btnRowCopy.setEnabled(row >= 0 && dispatchDetail != null && !dispatchDetail.isErasing());
		panel.btnRowDelete.setEnabled(row >= 0);
		panel.btnRowInsert.setEnabled(starRow >= 0);

		panel.btnRowTop.setEnabled(count != 0 && row >= 0 && row != 0);
		panel.btnRowUp.setEnabled(count != 0 && row >= 0 && row != 0);
		panel.btnRowDown.setEnabled(count != 0 && row >= 0 && row != count - 1);
		panel.btnRowUnder.setEnabled(count != 0 && row >= 0 && row != count - 1);
	}

	/**
	 * �f�t�H���g�����ł�
	 * 
	 * @param defaultTaxInnerDivision true:����
	 */
	public void setDefaultTaxInnerDivision(boolean defaultTaxInnerDivision) {
		this.defaultTaxInnerDivision = defaultTaxInnerDivision;
		panel.ctrlTaxDivision.setSelectON(defaultTaxInnerDivision ? 0 : 1);
	}

	/**
	 * �ݎ؂̃f�t�H���g�l
	 * 
	 * @param dc �ݎ�
	 */
	public void setDefaultDC(Dc dc) {
		this.defaultDC = dc;
		panel.ctrlDrCr.setDR(dc == Dc.DEBIT);
	}

	/**
	 * �`�[�`�[�ԍ�(�ҏW�̏ꍇ)
	 * 
	 * @param slipNo �`�[�`�[�ԍ�(�ҏW�̏ꍇ)
	 */
	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}

	/**
	 * �`�[��ʂ�ݒ�
	 * 
	 * @param type
	 */
	public void setSlipType(SlipType type) {
		this.slipType = type;
	}

	/**
	 * ����ݒ�(�L��������)
	 * 
	 * @param baseDate ���
	 */
	public void setBaseDate(Date baseDate) {
		this.baseDate = baseDate;

		// �L������
		panel.ctrlKCompany.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlKDepartment.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlItem.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlCurrency.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlTax.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlRemark.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlCustomer.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlEmployee.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlManage1.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlManage2.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlManage3.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlManage4.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlManage5.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlManage6.getSearchCondition().setValidTerm(baseDate);

		// ������
		Date occurDate = panel.ctrlOccurDate.getValue();
		if (occurDate != null) {
			panel.ctrlTax.getSearchCondition().setValidTerm(occurDate);
		}

		// �������f�t�H���g�l�ݒ�
		if (allowDefaultOccurDate && panel.ctrlOccurDate.isVisible() && panel.ctrlOccurDate.isEditable()) {
			panel.ctrlOccurDate.setValue(baseDate);
			changedOccurDate();
		}

		// �w�b�_�[������t�ύX
		if (beforeGyoNo == -1 && !isEditedDetail) {
			// �s�I������Ă�����
			isEditedDetail = false;
		} else {
			isEditedDetail = true;
		}

	}

	/**
	 * ���Z�d��ݒ�
	 * 
	 * @param isClosing true:���Z�d��Afalse:�ʏ�d��
	 */
	public void setClosingEntry(boolean isClosing) {
		this.isClosing = isClosing;
	}

	/**
	 * �O���w�薾��(���z���v�ŗ��p)
	 * 
	 * @param outherDetail �O���w�薾��
	 */
	public void setOuterDetail(SWK_DTL outherDetail) {
		this.outerDetail = outherDetail;
	}

	/**
	 * �O���w�������ݒ�(�Œ�\���p)
	 * 
	 * @param customer �O���w������
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * �O���w��Ј���ݒ�(�����\���p)
	 * 
	 * @param employee �Ј�
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/**
	 * ���������ݒ�(���c���ꗗ�p)
	 * 
	 * @param customer ��������
	 */
	public void setCollectionCustomer(Customer customer) {
		this.collectionCustomer = customer;
	}

	/**
	 * @return true:�O���[�v��v(��ʉ݈قȂ�v���Љ�)
	 */
	public static boolean isGroupAccounting() {
		return groupAccounting;
	}

	/**
	 * �p�~
	 * 
	 * @return true:��������\��
	 */
	@Deprecated
	public boolean isHideOccurDate() {
		return false;
	}

	/**
	 * @return true:�������u�����N�\
	 */
	public boolean isAllowOccurDateBlank() {
		return allowOccurDateBlank && !conf.isUseIfrs();
	}

	/**
	 * @param dtl
	 * @return true:�������u�����N�̏ꍇ�A�`�[���t��o�^����
	 */
	public boolean isAllowEntryDefaultOccurDate(SWK_DTL dtl) {
		if (dtl.getHAS_DATE() != null) {
			return false;
		}

		if (!dtl.isUseItemOccurDate()) {
			return false;
		}
		return allowEntryDefaultOccurDate;
	}

	/**
	 * ��Аݒ�
	 * 
	 * @param kcompany ��ЃR�[�h
	 */
	protected void setCompany(Company kcompany) {
		panel.ctrlKCompany.setEntity(kcompany);

		String code = kcompany.getCode();

		// �����ύX
		panel.ctrlKDepartment.getSearchCondition().setCompanyCode(code);
		panel.ctrlItem.getSearchCondition().setCompanyCode(code);

		// �O���[�v��v����
		if (isGroupAccounting()) {
			// �ʉݏ��̎擾�����̓��O�C����ЃR�[�h���
			panel.ctrlCurrency.getSearchCondition().setCompanyCode(getCompanyCode());
		} else {
			// �ʉݏ��̎擾�����͌v���ЃR�[�h���
			panel.ctrlCurrency.getSearchCondition().setCompanyCode(code);
		}

		panel.ctrlTax.getSearchCondition().setCompanyCode(code);
		panel.ctrlRemark.getSearchCondition().setCompanyCode(code);
		panel.ctrlCustomer.getSearchCondition().setCompanyCode(code);
		panel.ctrlEmployee.getSearchCondition().setCompanyCode(code);
		panel.ctrlManage1.getSearchCondition().setCompanyCode(code);
		panel.ctrlManage2.getSearchCondition().setCompanyCode(code);
		panel.ctrlManage3.getSearchCondition().setCompanyCode(code);
		panel.ctrlManage4.getSearchCondition().setCompanyCode(code);
		panel.ctrlManage5.getSearchCondition().setCompanyCode(code);
		panel.ctrlManage6.getSearchCondition().setCompanyCode(code);

		AccountConfig kconf = kcompany.getAccountConfig();

		// ���̃Z�b�g
		panel.ctrlItem.ctrlItemReference.btn.setText(kconf.getItemName());
		panel.ctrlItem.ctrlSubItemReference.btn.setText(kconf.getSubItemName());
		panel.ctrlItem.ctrlDetailItemReference.btn.setText(kconf.getDetailItemName());
		panel.ctrlManage1.btn.setText(kconf.getManagement1Name());
		panel.ctrlManage2.btn.setText(kconf.getManagement2Name());
		panel.ctrlManage3.btn.setText(kconf.getManagement3Name());
		panel.ctrlManage4.btn.setText(kconf.getManagement4Name());
		panel.ctrlManage5.btn.setText(kconf.getManagement5Name());
		panel.ctrlManage6.btn.setText(kconf.getManagement6Name());

		// ���p���邩
		panel.ctrlItem.ctrlDetailItemReference.setVisible(kconf.isUseDetailItem());
		panel.ctrlManage1.setVisible(kconf.isUseManagement1());
		panel.ctrlManage2.setVisible(kconf.isUseManagement2());
		panel.ctrlManage3.setVisible(kconf.isUseManagement3());
		panel.ctrlManage4.setVisible(kconf.isUseManagement4());
		panel.ctrlManage5.setVisible(kconf.isUseManagement5());
		panel.ctrlManage6.setVisible(kconf.isUseManagement6());

		// TODO:���v����

		// ���t���b�V���̏ꍇ�A�e�l�Ď擾
		if (isNotClearByCompany()) {

			boolean isBreak = false;

			panel.ctrlKDepartment.refleshEntity();
			if (panel.ctrlKDepartment.getEntity() == null) {
				changedDepartment();

				isBreak = true;

			} else if (isUseAllCompanyDepartment) {
				// ���Y��Ђ̕��傩�ǂ����A�`�F�b�N���s��

				Department dep = panel.ctrlKDepartment.getEntity();
				if (!Util.equals(dep.getCompanyCode(), code)) {
					panel.ctrlKDepartment.setEntity(null);
					panel.ctrlKDepartment.setCode(dep.getCode());

					try {
						panel.ctrlKDepartment.setAllCompanyMode(false);
						panel.ctrlKDepartment.getSearchCondition().setCompanyCode(code);
						panel.ctrlKDepartment.refleshEntity();
					} finally {
						panel.ctrlKDepartment.setAllCompanyMode(true);
					}

					changedDepartment();

					isBreak = true;
				}
			}

			if (!isBreak) {
				panel.ctrlItem.refleshGroupEntity();

				if (panel.ctrlItem.getEntity() == null) {
					isBreak = true;
				}

				changedItem();
			}

			if (!isBreak) {
				Remark oldRemark = (Remark) panel.ctrlRemark.getController().getUnspecifiedEntity();

				panel.ctrlRemark.refleshEntity();
				panel.ctrlCustomer.refleshEntity();
				panel.ctrlEmployee.refleshEntity();
				panel.ctrlCurrency.refleshEntity();
				panel.ctrlManage1.refleshEntity();
				panel.ctrlManage2.refleshEntity();
				panel.ctrlManage3.refleshEntity();
				panel.ctrlManage4.refleshEntity();
				panel.ctrlManage5.refleshEntity();
				panel.ctrlManage6.refleshEntity();
				panel.ctrlTax.refleshEntity();

				if (panel.ctrlRemark.getEntity() != null) {
					panel.ctrlRemark.setNames(panel.ctrlRemark.getEntity().getName());
				} else if (oldRemark != null && Util.isNullOrEmpty(oldRemark.getCode())) {
					panel.ctrlRemark.setEntity(oldRemark);
				}
				if (panel.ctrlCustomer.getEntity() != null) {
					panel.ctrlCustomer.setNames(panel.ctrlCustomer.getEntity().getNames());
				}
				if (panel.ctrlEmployee.getEntity() != null) {
					panel.ctrlEmployee.setNames(panel.ctrlEmployee.getEntity().getNames());
				}
				if (panel.ctrlCurrency.getEntity() != null) {
					panel.ctrlCurrency.setNames(panel.ctrlCurrency.getEntity().getNames());
				}
				if (panel.ctrlManage1.getEntity() != null) {
					panel.ctrlManage1.setNames(panel.ctrlManage1.getEntity().getNames());
				}
				if (panel.ctrlManage2.getEntity() != null) {
					panel.ctrlManage2.setNames(panel.ctrlManage2.getEntity().getNames());
				}
				if (panel.ctrlManage3.getEntity() != null) {
					panel.ctrlManage3.setNames(panel.ctrlManage3.getEntity().getNames());
				}
				if (panel.ctrlManage4.getEntity() != null) {
					panel.ctrlManage4.setNames(panel.ctrlManage4.getEntity().getNames());
				}
				if (panel.ctrlManage5.getEntity() != null) {
					panel.ctrlManage5.setNames(panel.ctrlManage5.getEntity().getNames());
				}
				if (panel.ctrlManage6.getEntity() != null) {
					panel.ctrlManage6.setNames(panel.ctrlManage6.getEntity().getNames());
				}
				if (panel.ctrlTax.getEntity() != null) {
					panel.ctrlTax.setNames(panel.ctrlTax.getEntity().getNames());
				}
			}
		}

	}

	/**
	 * �v�㕔��ݒ�
	 * 
	 * @param dept �v�㕔��
	 */
	protected void setDepartment(Department dept) {
		panel.ctrlKDepartment.setEntity(dept);

		if (dept == null) {
			panel.ctrlItem.setEntity(null);
			panel.ctrlItem.ctrlItemReference.setEditable(false);
			return;
		}

		// �Ȗڏ�����
		panel.ctrlItem.ctrlItemReference.setEditable(true);

		String code = dept.getCode();

		String oldDeptCode = panel.ctrlItem.getSearchCondition().getDepartmentCode();

		if (!code.equals(Util.avoidNull(oldDeptCode))) {
			// ����R�[�h���ύX�ɂȂ����ꍇ�A������ύX
			panel.ctrlItem.getSearchCondition().setDepartmentCode(code);

			// �����ύX�ɂ��A�������`�F�b�N��OK�Ȃ�c��
			if (!panel.ctrlItem.isEmpty()) {
				if (!isNotClearByCompany()) {
					panel.ctrlItem.refleshEntity();
				} else {
					panel.ctrlItem.refleshGroupEntity();
				}
			}
		}
	}

	/**
	 * �ʉݐݒ�
	 * 
	 * @param currency �ʉ�
	 */
	protected void setCurrecy(Currency currency) {

		panel.ctrlCurrency.setEntity(currency);

		// ���v���̒ʉ�
		makeCurrencyComboBox();

		if (currency == null) {
			panel.ctrlRate.clear();
			panel.ctrlRate.setEditable(false);
			panel.ctrlKeyAmount.clear();
			panel.ctrlKeyAmount.setEditable(false);
			return;
		}

		String currencyCode = currency.getCode();
		boolean isKey = keyCurrency.getCode().equals(currencyCode);

		// ���[�g
		panel.ctrlRate.setEditable(!isKey);
		panel.ctrlRate.setNumberValue(isKey ? BigDecimal.ONE : null);
		changedRate();

		// ����z
		panel.ctrlKeyAmount.setEditable(!isKey);

		// �����_�ύX
		int digit = currency.getDecimalPoint();

		// �e�R���|�[�l���g
		panel.ctrlInputAmount.setDecimalPoint(digit);
		panel.ctrlTaxAmount.setDecimalPoint(digit);
		changedTax();
	}

	/**
	 * ���v���̒ʉ݃R���{�{�b�N�X�\�z
	 */
	protected void makeCurrencyComboBox() {

		// �I�𒆂̒ʉ�(���v��)
		Currency selectedCurrency = (Currency) panel.cbxCurrencyOnTotal.getSelectedItemValue();

		Set<Currency> list = new LinkedHashSet<Currency>();

		// �w�b�_�̒ʉ�
		if (outerDetail != null) {
			list.add(outerDetail.getCurrency());
		}

		// ���ׂ̒ʉ�
		int row = panel.tbl.getSelectedRow(); // �I���s

		for (int i = 0; i < panel.tbl.getRowCount(); i++) {
			if (row == i) {
				continue;
			}

			SWK_DTL dtl = (SWK_DTL) panel.tbl.getRowValueAt(i, SC.bean);
			list.add(dtl.getCurrency());
		}

		// ��ʓ��͒��̒ʉ�
		list.add(panel.ctrlCurrency.getEntity());

		// �����Ēǉ�
		panel.cbxCurrencyOnTotal.removeAllItems();

		for (Currency currency : list) {
			if (currency == null) {
				continue;
			}

			String code = currency.getCode();
			boolean isKey = keyCurrency.getCode().equals(code);

			if (!isKey && !panel.cbxCurrencyOnTotal.containsText(code)) {
				panel.cbxCurrencyOnTotal.addTextValueItem(currency, code);
			}
		}

		if (selectedCurrency != null) {
			panel.cbxCurrencyOnTotal.setSelectedText(selectedCurrency.getCode());
		}
	}

	/**
	 * �v���ЕύX
	 * 
	 * @return false:�t�֐ݒ�NG
	 */
	protected boolean changedCompany() {
		try {
			if (panel.ctrlKCompany.isEmpty()) {
				panel.ctrlKCompany.setEntity(getCompany());
			}

			Company company = panel.ctrlKCompany.getEntity();

			if (company == null) {
				return false;
			}

			String code = company.getCode();

			// ��Њԕt�փ}�X�^���ݒ肳��Ă��܂���B
			if (!getCompanyCode().equals(code) && !isAppropriateCompanyReplace()) {
				showMessage("I00054");// ��Њԕt�փ}�X�^���ݒ肳��Ă��܂���B
				panel.ctrlKCompany.code.requestFocus();
				panel.ctrlKCompany.code.clearOldText();

				return false;
			}

			// ���t���b�V���̏ꍇ�A�N���A�s�v
			if (!isNotClearByCompany()) {

				// �N���A
				clearInput();

				setCompany(company);

				// �v�㕔��̓��O�C����ЈȊO�̓u�����N
				if (!getCompanyCode().equals(code)) {
					panel.ctrlKDepartment.clear();
				}

				changedDepartment();
			} else {

				// ��Аݒ�̂�
				setCompany(company);
			}

			return true;

		} catch (TException ex) {
			errorHandler(ex);
			return true;
		}
	}

	/**
	 * @return true:�v���ЕύX��薾�׍��ڍŐV�擾���s���A���݂��Ă��Ȃ��ꍇ�̂݃N���A
	 */
	protected boolean isNotClearByCompany() {
		return notClearByCompany;
	}

	/**
	 * �v��ȖڃR�[�h�̃`�F�b�N
	 * 
	 * @return false:NG
	 * @throws TException
	 */
	protected boolean isAppropriateCompanyReplace() throws TException {
		String kcompany = panel.ctrlKCompany.getCode();

		List<TransferConfig> list = (List<TransferConfig>) request(CompanyManager.class, "getTransferConfig",
			getCompanyCode(), kcompany);

		if (list == null) {
			return false;
		}

		return list.size() == 2;
	}

	/**
	 * �v�㕔��ύX
	 */
	protected void changedDepartment() {
		try {
			Department dept = panel.ctrlKDepartment.getEntity();

			if (isUseAllCompanyDepartment && dept != null) {
				// �SSPC����̏ꍇ
				String nowCompanyCode = panel.ctrlKCompany.getCode();
				String deptCompanyCode = dept.getCompanyCode();

				String depCode = dept.getCode();
				if (depPriorityMap.containsKey(depCode)) {
					deptCompanyCode = depPriorityMap.get(depCode);
					dept.setCompanyCode(deptCompanyCode);
				}

				if (!Util.isNullOrEmpty(deptCompanyCode) && !Util.equals(nowCompanyCode, deptCompanyCode)) {
					// �I����������̉�ЃR�[�h�ƌv���Ђ��s��v
					// �v���Ђ��Ď擾����
					Company kcompany = (Company) request(CompanyManager.class, "get", deptCompanyCode);
					panel.ctrlKCompany.setEntity(kcompany);

					changedCompany();
				}

			}

			// �ύX�O�ȖڃR�[�h
			String itemCode = panel.ctrlItem.ctrlItemReference.getCode();

			setDepartment(dept);

			// �ύX����������ȖڕύX�ʒm
			if (!itemCode.equals(panel.ctrlItem.ctrlItemReference.getCode())) {
				changedItem();
			}

		} catch (Exception ex) {
			errorHandler(ex);
		}
	}

	/**
	 * @return �w�b�_�[�ʉ�
	 */
	protected Currency getHeaderCurrency() {
		if (getView().parent != null) {

			if (getView().parent instanceof TSlipAddPartsPanel) {

				TSlipAddPartsPanel pnl = (TSlipAddPartsPanel) getView().parent;

				if (pnl.isUseHeaderDefaultCurreny() && pnl.ctrlCurrency != null) {
					return pnl.ctrlCurrency.getEntity();
				}

				return null;
			}

		}

		return null;
	}

	/**
	 * �ȖڕύX
	 * 
	 * @return false:NG
	 */
	protected boolean changedItem() {
		Item item = panel.ctrlItem.getEntity();

		if (item == null) {
			clearInputForItem();
			changedCurrency();
			return true;
		}

		// �⏕�A���󂪂���ꍇ�́A�������𔽉f
		if (item.getSubItem() != null) {
			item = item.getSubItem();
		}

		if (item.getDetailItem() != null) {
			item = item.getDetailItem();
		}

		// ���ʉݓ��̓t���O
		panel.ctrlCurrency.setEditable(item.isUseForeignCurrency());
		if (!item.isUseForeignCurrency()) {
			setCurrecy(keyCurrency);
			changedCurrency();
		} else {

			// �w�b�_�[�ʉ�
			Currency hdrCur = getHeaderCurrency();
			if (hdrCur != null) {
				setCurrecy(hdrCur);
				changedCurrency();
			}
		}

		// �����
		if (item.isUseSalesTaxation() || item.isUsePurchaseTaxation()) {
			// ����ېœ��́A�܂��́A�d���ېœ��͂̏ꍇ�A���͉�
			panel.ctrlTax.setEditable(true);
			panel.ctrlTax.setEntity(item.getConsumptionTax());
			panel.ctrlTax.getSearchCondition().setHasSales(item.isUseSalesTaxation());
			panel.ctrlTax.getSearchCondition().setHasPurcharse(item.isUsePurchaseTaxation());

		} else {
			panel.ctrlTax.getSearchCondition().setHasSales(false);
			panel.ctrlTax.getSearchCondition().setHasPurcharse(false);
			panel.ctrlTax.setEditable(false);
			panel.ctrlTax.setEntity(null);
		}

		// �������ς���Ă�̂Ő������`�F�b�N
		panel.ctrlTax.refleshEntity();
		changedTax();

		// �����
		CustomerType customerType = item.getClientType();
		panel.ctrlCustomer.setEditable(customerType != CustomerType.NONE);
		panel.ctrlCustomer.getSearchCondition().setType(customerType);

		if (customerType != CustomerType.NONE) {

			// ����斢�ݒ�̏ꍇ�͏����l�Ƃ��āA�O���ݒ�̎������w��
			if (Util.isNullOrEmpty(panel.ctrlCustomer.getCode()) && customer != null) {
				panel.ctrlCustomer.setEntity(customer);
			}

			if (isInvoice) {
				// �C���{�C�X ��K�i���������s���Ǝ�
				setTaxNoInvReg(item.getConsumptionTax());
			}
			panel.ctrlCustomer.refleshEntity();

		} else {
			panel.ctrlCustomer.clear();
		}

		// �Ј�
		panel.ctrlEmployee.setEditable(item.isUseEmployee());

		if (!item.isUseEmployee()) {
			panel.ctrlEmployee.clear();

		} else if (panel.ctrlEmployee.isEmpty() && !employeeDefaultBlank) {
			Company kcompany = panel.ctrlKCompany.getEntity();

			if (kcompany.getCode().equals(getCompanyCode())) {
				Employee refEmployee = employee == null ? getUser().getEmployee() : employee;
				panel.ctrlEmployee.setEntity(refEmployee);
			}
		}

		// �Ǘ��P�`6
		panel.ctrlManage1.setEditable(item.isUseManagement1());
		panel.ctrlManage2.setEditable(item.isUseManagement2());
		panel.ctrlManage3.setEditable(item.isUseManagement3());
		panel.ctrlManage4.setEditable(item.isUseManagement4());
		panel.ctrlManage5.setEditable(item.isUseManagement5());
		panel.ctrlManage6.setEditable(item.isUseManagement6());

		if (!item.isUseManagement1()) panel.ctrlManage1.clear();
		if (!item.isUseManagement2()) panel.ctrlManage2.clear();
		if (!item.isUseManagement3()) panel.ctrlManage3.clear();
		if (!item.isUseManagement4()) panel.ctrlManage4.clear();
		if (!item.isUseManagement5()) panel.ctrlManage5.clear();
		if (!item.isUseManagement6()) panel.ctrlManage6.clear();

		// ���v����1�`3
		panel.ctrlNonAcDetails.setEditable(1, item.isUseNonAccount1());
		panel.ctrlNonAcDetails.setEditable(2, item.isUseNonAccount2());
		panel.ctrlNonAcDetails.setEditable(3, item.isUseNonAccount3());

		if (!item.isUseNonAccount1()) panel.ctrlNonAcDetails.clear(1);
		if (!item.isUseNonAccount2()) panel.ctrlNonAcDetails.clear(2);
		if (!item.isUseNonAccount3()) panel.ctrlNonAcDetails.clear(3);

		if (isAllowOccurDateBlank()) {
			// �������u�����N�\�̏ꍇ�A�Ȗڃt���O�ɂ�萧��
			if (item.isUseOccurDate()) {
				panel.ctrlOccurDate.setEditable(true);
			} else {
				panel.ctrlOccurDate.setEditable(false);
				panel.ctrlOccurDate.clear();
			}
		}

		return true;
	}

	/**
	 * �ʉݕύX
	 */
	protected void changedCurrency() {
		Currency currency = panel.ctrlCurrency.getEntity();

		setCurrecy(currency);

		// ���[�g
		panel.ctrlRate.setNumberValue(getCurrencyRate());
		if (isUseHasDateChk) {
			Date hasDate = panel.ctrlOccurDate.getValue();
			panel.ctrlRate.setNumberValue(getCurrencyRateByOccurDate(hasDate));
		}

		changedRate();
	}

	/**
	 * �������̕ύX
	 */
	protected void changedOccurDate() {
		if (isProcess) {
			return;
		}

		// ���[�g�ύX
		BigDecimal old = panel.ctrlRate.getBigDecimal();
		BigDecimal nuw = getCurrencyRate();

		// ������
		Date occurDate = panel.ctrlOccurDate.getValue();

		if (occurDate == null) {
			occurDate = baseDate;
		}
		if (isUseHasDateChk) {
			nuw = getCurrencyRateByOccurDate(occurDate);
		}

		panel.ctrlTax.getSearchCondition().setValidTerm(occurDate);

		// �擾���[�g������
		if (nuw == null) {
			panel.ctrlRate.clear();
			return;
		}

		// ���[�g�l�ɕύX���Ȃ�
		if (old.compareTo(nuw) == 0) {
			return;
		}

		panel.ctrlRate.setNumberValue(nuw);
		changedRate();
	}

	/**
	 * �ʉ݃��[�g �ύX
	 */
	protected void changedRate() {
		// �M�݊��Z
		Currency currency = panel.ctrlCurrency.getEntity();

		if (currency == null || panel.ctrlInputAmount.isEmpty()) {
			return;
		}

		BigDecimal inAmount = panel.ctrlInputAmount.getBigDecimal();
		panel.ctrlKeyAmount.setNumber(convertKeyAmount(inAmount));

		// �����
		changedTax();
		summary();
	}

	/**
	 * ���͋��z�̕ύX
	 */
	protected void changedInputAmount() {

		// �����
		changedTax();
		summary();
	}

	/**
	 * ����Ŋz�̕ύX
	 */
	protected void changedTaxAmount() {
		summary();
	}

	/**
	 * ����z�̕ύX
	 */
	protected void changedKeyAmount() {
		summary();
	}

	/**
	 * ����/�O�� �ؑ�
	 */
	protected void changedTaxDivision() {
		changedTax();
	}

	/**
	 * ����ŕύX
	 */
	protected void changedTax() {
		try {
			// ����Ōv�Z
			Company kcompany = panel.ctrlKCompany.getEntity();
			Currency currency = panel.ctrlCurrency.getEntity();
			ConsumptionTax tax = panel.ctrlTax.getEntity();

			if (kcompany == null || tax == null || DecimalUtil.isNullOrZero(tax.getRate())) {
				panel.ctrlTaxAmount.clear();
				panel.ctrlTaxAmount.setEditable(false);
				return;
			}

			// �ʉ݂Ȃ�
			if (currency == null) {
				return;
			}

			AccountConfig kconf = kcompany.getAccountConfig();

			// ����A�d���̏ꍇ�͓��͉�
			switch (tax.getTaxType()) {
				case NOT:
					panel.ctrlTaxAmount.clear();
					panel.ctrlTaxAmount.setEditable(false);
					break;

				case SALES:
				case PURCHAESE:
					panel.ctrlTaxAmount.setEditable(true);

					BigDecimal inAmount = panel.ctrlInputAmount.getBigDecimal();

					if (DecimalUtil.isZero(inAmount)) {
						panel.ctrlTaxAmount.clear();
						break;
					}

					TTaxCalculation param = TCalculatorFactory.createTaxCalculation();
					param.setInside(panel.ctrlTaxDivision.isSelected(0)); // ����or�O��
					param.setAmount(inAmount); // �Ώۋ��z
					param.setTax(tax); // ����ŏ��
					param.setDigit(currency.getDecimalPoint()); // �����_����
					param.setReceivingFunction(kconf.getReceivingFunction()); // �؎�
					param.setPaymentFunction(kconf.getPaymentFunction()); // ����

					if (isInvoice && !chkSlipTypeInvoice()) {

						setInvoiceTaxAmount(param, inAmount);

					} else {
						// �C���{�C�X�ݒ�false
						panel.ctrlTaxAmount.setNumberValue(calculator.calculateTax(param));
					}

					break;
			}

		} finally {
			// �M�݊��Z
			BigDecimal inAmount = panel.ctrlInputAmount.getBigDecimal();
			panel.ctrlKeyAmount.setNumber(convertKeyAmount(inAmount));

			// ���v�v�Z
			summary();
		}
	}

	/**
	 * �ݎؐؑ�
	 */
	protected void changedDC() {

		// ���v�v�Z
		summary();
	}

	/**
	 * �V�K <br>
	 * �A���[�g�`�F�b�N���s��Ȃ�
	 */
	protected void doNew() {

		doNew(false);

		// �ޔ��s�̏�����
		beforeGyoNo = -1;
		// �s�ҏW����
		isEditedDetail = false;
	}

	/**
	 * �V�K
	 * 
	 * @param isAlert �A���[�g�`�F�b�N���s�����ۂ�
	 */
	protected void doNew(boolean isAlert) {

		// �s�m�肵�Ă��Ȃ��f�[�^�`�F�b�N
		if (isAlert && inputDetailCheckAlert) {
			if (!inputDetailCheckAlert()) {
				return;
			}
		}

		// �I���N���A
		panel.tbl.clearSelection();

		// �u*�v�N���A
		panel.tbl.setShowRowHeaderStar(false);

		// �sXX�{�^��
		controllButtons();

		// �N���A
		clearInput();

		// �t�H�[�J�X
		panel.ctrlKDepartment.requestTextFocus();

		// �ޔ��s�̏�����
		beforeGyoNo = -1;
		// �s�ҏW����
		isEditedDetail = false;

	}

	/**
	 * �}��
	 */
	protected void doInsert() {
		if (!checkInput()) {
			return;
		}

		if (!panel.tbl.isShowRowHeaderStar()) {
			// �u*�v�I���Ȃ�
			return;
		}

		SWK_DTL dtl = createSlipDetail();

		// ���͒l���f
		reflect(dtl);

		// �ޔ��s�̏��������Ɏ��{
		this.beforeGyoNo = -1;
		// �s�ҏW�ς��Ɏ��{
		this.isEditedDetail = false;

		// �ŏI�s�ɒǉ�
		List<Object> list = getRow(dtl);
		int insertRowIndex = panel.tbl.getRowHeaderStarIndex();
		panel.tbl.insertRow(insertRowIndex, list);

		// �N���A
		doNew();
	}

	/**
	 * �X�v���b�h�s�ԍ��N���b�N����
	 */
	protected void clickTableRowHeader() {

		// �����s�͍s�}���֎~
		if (dispatchDetail != null && !dispatchDetail.isErasing()) {
			if (panel.tbl.getSelectedRow() == panel.tbl.getRowHeaderStarIndex()) {
				panel.tbl.setShowRowHeaderStar(false);
				panel.btnRowInsert.setEnabled(false);
			} else {
				panel.tbl.setShowRowHeaderStar(true);
				panel.btnRowInsert.setEnabled(true);
			}
		} else {
			panel.tbl.setShowRowHeaderStar(false);
			panel.btnRowInsert.setEnabled(false);
		}

		{
			// �V�K�s���[�h

			// �I���N���A
			panel.tbl.clearSelection();

			// �sXX�{�^��
			controllButtons();

			// �N���A
			clearInput();

			// �t�H�[�J�X
			panel.ctrlKDepartment.requestTextFocus();
		}
	}

	/**
	 * ����
	 */
	protected void doCopy() {

		if (!checkInput()) {
			return;
		}

		SWK_DTL dtl = createSlipDetail();

		// ���͒l���f
		reflect(dtl);

		// �ŏI�s�ɒǉ�
		List<Object> list = getRow(dtl);
		panel.tbl.addRow(list);

		// �N���A
		doNew();
	}

	/**
	 * �폜
	 */
	protected void doDelete() {

		if (!showConfirmMessage(panel, FocusButton.NO, "Q00007")) {// �폜���܂����H
			return;
		}

		int row = panel.tbl.getSelectedRow();

		// �s�r������
		unlock((SWK_DTL) panel.tbl.getRowValueAt(row, SC.bean));

		// �ꗗ����폜
		panel.tbl.removeRow(row);

		// �N���A
		doNew();
	}

	/**
	 * �s�P�ʂ̔r������
	 * 
	 * @param dtl �Ώۖ���
	 */
	protected void unlock(SWK_DTL dtl) {

		try {
			// AP�s�r������
			AP_ZAN apZan = dtl.getAP_ZAN();
			if (apZan != null) {
				BalanceCondition condition = createBalanceCondition();
				condition.setCompanyCode(getCompanyCode());
				condition.setSlipNo(apZan.getZAN_DEN_NO());
				condition.setSlipLineNo(apZan.getZAN_GYO_NO());

				request(getBalanceManagerClass(), "unlockApBalance", condition);
			}

			// AR�s�r������
			AR_ZAN arZan = dtl.getAR_ZAN();
			if (arZan != null) {
				BalanceCondition condition = createBalanceCondition();
				condition.setCompanyCode(getCompanyCode());
				condition.setSlipNo(arZan.getZAN_SEI_DEN_NO());
				condition.setSlipLineNo(arZan.getZAN_SEI_GYO_NO());

				request(getBalanceManagerClass(), "unlockArBalance", condition);
			}

			if (isUseBS) {
				// BS�s�r������
				SWK_DTL bs = dtl.getBsDetail();
				if (bs != null) {
					BSEraseCondition condition = createBSEraseCondition();
					condition.setCompanyCode(getCompanyCode());
					condition.add(bs.getSWK_DEN_NO(), bs.getSWK_GYO_NO());

					request(getBSEraseManagerClass(), "unlock", condition);
				}
			}

		} catch (TException ex) {
			errorHandler(ex);
		}
	}

	/**
	 * @return ���A���c���}�l�[�W��
	 */
	protected Class getBalanceManagerClass() {
		return BalanceManager.class;
	}

	/**
	 * @return BS��������}�l�[�W��
	 */
	protected Class getBSEraseManagerClass() {
		return BSEraseManager.class;
	}

	/**
	 * @return �`�[���׍\�z�p�}�l�[�W��
	 */
	protected Class getManagerClass() {
		return SlipManager.class;
	}

	/**
	 * @return ���A���c�����o����
	 */
	protected BalanceCondition createBalanceCondition() {
		return new BalanceCondition();
	}

	/**
	 * @return BS����������o����
	 */
	protected BSEraseCondition createBSEraseCondition() {
		return new BSEraseCondition();
	}

	/**
	 * �m��
	 */
	protected void doEntry() {

		if (!checkInput()) {
			return;
		}

		// �I����Ԃ̏ꍇ�͍X�V
		boolean isUpdate = panel.tbl.getSelectedRow() != -1;

		SWK_DTL dtl = isUpdate ? (SWK_DTL) panel.tbl.getSelectedRowValueAt(SC.bean) : createSlipDetail();

		// ���͒l���f
		reflect(dtl);

		// �s�̒ǉ�
		List<Object> list = getRow(dtl);

		if (isUpdate) {
			panel.tbl.modifySelectedRow(list);

		} else {
			panel.tbl.addRow(list);
		}

		// ��ʃN���A
		doNew();
	}

	/**
	 * ���̓`�F�b�N
	 * 
	 * @return false:NG
	 */
	protected boolean checkInput() {

		// �v�㕔��
		if (!checkInputBlank(panel.ctrlKDepartment.code, panel.ctrlKDepartment.btn.getText())) {
			return false;
		}

		// �Ȗ�
		TItemGroup item = panel.ctrlItem;
		if (!checkInputBlank(item.ctrlItemReference.code, item.ctrlItemReference.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(item.ctrlSubItemReference.code, item.ctrlSubItemReference.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(item.ctrlDetailItemReference.code, item.ctrlDetailItemReference.btn.getText())) {
			return false;
		}

		// ������(�u�����N�\�̏ꍇ�A�`�F�b�N�s�v)
		boolean checkOccurDate = !isAllowOccurDateBlank();
		if (item.getEntity() != null) {
			Item itemBean = item.getEntity().getPreferedItem();
			if (itemBean.isUseOccurDate() && !Util.isNullOrEmpty(panel.ctrlCurrency.getCode())
				&& !keyCurrency.getCode().equals(panel.ctrlCurrency.getCode())) {
				// �u�����N�\�A����ʉ݈ȊO�̏ꍇ�͉Ȗڂ̃t���O�ɏ]���ă`�F�b�N
				checkOccurDate = true;
			}
		}

		if (checkOccurDate
			&& !checkInputBlank(panel.ctrlOccurDate.getCalendar(), panel.ctrlOccurDate.getLabel().getText())) {
			return false;
		}

		// �ʉ�
		if (!checkInputBlank(panel.ctrlCurrency.code, panel.ctrlCurrency.btn.getText())) {
			return false;
		}

		// ���[�g
		if (!checkInputBlank(panel.ctrlRate.getField(), panel.ctrlRate.getLabel().getText())) {
			return false;
		}

		// �ŋ敪
		if (!checkInputBlank(panel.ctrlTax.code, panel.ctrlTax.btn.getText())) {
			return false;
		}

		// ���͋��z
		BigDecimal inKin = panel.ctrlInputAmount.getBigDecimal();

		// ��ʉ݂ƈقȂ�ʉ݂̏ꍇ�́A0��F�߂�.
		if (keyCurrency.getCode().equals(panel.ctrlCurrency.getCode())) {
			if (DecimalUtil.isZero(inKin)) {
				showMessage("I00037", panel.ctrlInputAmount.getLabel().getText());
				panel.ctrlInputAmount.requestFocus();
				return false;
			}
		}

		// ����Ŋz (0�ł�OK)
		BigDecimal taxInKin = panel.ctrlTaxAmount.getBigDecimal();
		if (!DecimalUtil.isZero(taxInKin)) {

			if (!DecimalUtil.isZero(inKin) && inKin.signum() != taxInKin.signum()) {
				showMessage("I00126");// ���͋��z�Ə���Ŋz�̕������قȂ�܂��B
				panel.ctrlTaxAmount.requestFocus();
				return false;
			}

			if (inKin.abs().compareTo(taxInKin.abs()) <= 0) {
				showMessage("I00127");// ����Ŋz�͓��͋��z�����ł���K�v������܂��B
				panel.ctrlTaxAmount.requestFocus();
				return false;
			}
		}

		// �M�݋��z
		BigDecimal kin = panel.ctrlKeyAmount.getBigDecimal();
		if (DecimalUtil.isZero(kin)) {
			showMessage("I00037", panel.ctrlKeyAmount.getLabel().getText());// {0}����͂��Ă��������B
			panel.ctrlKeyAmount.requestFocus();

			return false;
		}

		if (!DecimalUtil.isZero(inKin) && inKin.signum() != kin.signum()) {
			showMessage("I00125");// ���͋��z�ƖM�݋��z�̕������قȂ�܂��B
			panel.ctrlKeyAmount.requestFocus();
			return false;
		}

		// �����
		if (!checkInputBlank(panel.ctrlCustomer.code, panel.ctrlCustomer.btn.getText())) {
			return false;
		}
		// �Ј�
		if (!checkInputBlank(panel.ctrlEmployee.code, panel.ctrlEmployee.btn.getText())) {
			return false;
		}
		// �Ǘ�1�`6
		if (!checkInputBlank(panel.ctrlManage1.code, panel.ctrlManage1.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(panel.ctrlManage2.code, panel.ctrlManage2.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(panel.ctrlManage3.code, panel.ctrlManage3.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(panel.ctrlManage4.code, panel.ctrlManage4.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(panel.ctrlManage5.code, panel.ctrlManage5.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(panel.ctrlManage6.code, panel.ctrlManage6.btn.getText())) {
			return false;
		}

		if (isUseHasDateChk && !chkHasDate()) {
			showMessage("I01161"); // �������ɑΉ�����ʉ݃��[�g���ݒ肳��Ă��܂���B
			panel.ctrlOccurDate.requestFocus();

			return false;
		}

		// �C���{�C�X��p
		if (isInvoice && !chkSlipTypeInvoice()) {

			// invoice�p����ŉȖڂ��ݒ肳��Ă���ꍇ�x�����b�Z�[�W
			if (!checkInvoiceItemTaxCode()) {
				return false;
			}

			String taxMsg = null;

			if (!panel.ctrlTax.isEmpty() && panel.ctrlTax.getEntity().isNO_INV_REG_FLG()
				&& Util.isNullOrEmpty(panel.ctrlCustomer.getCode())) {
				// �Z�b�g����Ă������ŃR�[�h����K�i���������s���Ǝ҂̏ꍇ�����̓��͕K�{
				showMessage("I01125", panel.ctrlCustomer.btn.getText());// ����悪���͕s�̉Ȗڂɂ́A��K�i���ƎҌ����̏���ŃR�[�h�͑I���ł��܂���B

				panel.requestFocusInWindow();

				return false;
			}

			// ����惊�t�@�����X���ݒ�̏ꍇnull
			if (panel.ctrlCustomer.isEmpty()) {
				return true;
			}

			// ���ׂ̎��������擾����B
			Customer bean = setCustomerEntity(panel.ctrlKCompany.getCode(), panel.ctrlCustomer.getCode());

			if (bean == null) {
				return true;

			} else if (bean.isNO_INV_REG_FLG()) {

				if (!panel.ctrlTax.isEmpty() && !panel.ctrlTax.getEntity().isNO_INV_REG_FLG()) {

					taxMsg = panel.ctrlTax.getEntity().getCode() + ":" + panel.ctrlTax.getEntity().getNames();
					if (!showConfirmMessage("I01107", "C12197", taxMsg)) {
						// ��K�i���������s���Ǝ҂ɑ΂��āy{�����}�z���ݒ肳��Ă��܂��B���s���܂����H
						return false;
					}
				}
			} else {

				if (!panel.ctrlTax.isEmpty() && panel.ctrlTax.getEntity().isNO_INV_REG_FLG()) {
					taxMsg = panel.ctrlTax.getEntity().getCode() + ":" + panel.ctrlTax.getEntity().getNames();
					if (!showConfirmMessage("I01107", "C12196", taxMsg)) {
						// �K�i���������s���Ǝ҂ɑ΂��āy{�����}�z���ݒ肳��Ă��܂��B���s���܂����H
						return false;
					}
				}

			}
		}

		return true;
	}

	/**
	 * 2023INVOICE���x�p�G���[���b�Z�[�W�F����ŉȖڂ����͂���Ă���ꍇ�G���[
	 * 
	 * @return false:NG
	 */
	protected boolean checkInvoiceItemTaxCode() {

		try {

			String kmk = Util.avoidNull(panel.ctrlItem.ctrlItemReference.getCode());
			String hkm = Util.avoidNull(panel.ctrlItem.ctrlSubItemReference.getCode());
			String ukm = Util.avoidNull(panel.ctrlItem.ctrlDetailItemReference.getCode());

			if (TLoginInfo.isTaxAutoItem(kmk, hkm, ukm)) {
				if (!showConfirmMessage("I01118")) {
					// ����ŉȖڂ����ړ��͂���Ă��܂��B���s���܂����H
					return false;
				}

			}

		} catch (Exception e) {
			errorHandler(e);
		}
		return true;

	}

	/**
	 * ���̓u�����N�`�F�b�N
	 * 
	 * @param field �Ώۃt�B�[���h
	 * @param name �G���[���̕\����
	 * @return false:NG
	 */
	protected boolean checkInputBlank(TTextField field, String name) {

		if (field.isVisible() && field.isEditable() && field.isEmpty()) {
			showMessage("I00037", name);// {0}����͂��Ă��������B
			field.requestFocusInWindow();
			return false;
		}

		return true;
	}

	/**
	 * �������ɑΉ�����ʉ݃��[�g���ݒ肳��邩
	 * 
	 * @return true�F�ݒ肳��� false�F�ݒ肳��Ȃ�
	 */
	protected boolean chkHasDate() {
		try {
			Date hasDate = panel.ctrlOccurDate.getValue();
			String curCode = panel.ctrlCurrency.getCode();

			// ��ʉ݂̏ꍇ
			if (curCode.equals(keyCurrency.getCode())) {
				return true;
			}

			if (hasDate == null && !panel.ctrlOccurDate.isEnabled() || isAllowOccurDateBlank()) {
				hasDate = baseDate;
			}

			// ���[�g���擾����
			BigDecimal rate = getCurrencyRateByOccurDate(hasDate);
			if (rate != null) {
				panel.ctrlRate.setNumberValue(rate);
				return true;
			}
		} catch (Exception e) {
			errorHandler(e);
		}
		return false;
	}

	/**
	 * �ŏ�ʂֈړ�
	 */
	protected void doMoveTop() {

		isProcess = true;

		try {

			if (!btnSelectDetailChanged()) {
				return;
			}
			panel.tbl.moveRow(panel.tbl.getSelectedRow(), 0);
			controllButtons();

			panel.btnRowDown.requestFocus();

			// �ޔ��s�̃Z�b�g
			beforeGyoNo = panel.tbl.getSelectedRow();
			// �s�ҏW����
			isEditedDetail = false;
		} finally {
			isProcess = false;
		}
	}

	/**
	 * ��ʂֈړ�
	 */
	protected void doMoveUp() {

		isProcess = true;

		try {

			if (!btnSelectDetailChanged()) {
				return;
			}
			panel.tbl.moveRow(panel.tbl.getSelectedRow(), panel.tbl.getSelectedRow() - 1);
			controllButtons();

			if (!panel.btnRowDown.isEnabled()) {
				panel.btnRowDown.requestFocus();
			}

			// �ޔ��s�̃Z�b�g
			beforeGyoNo = panel.tbl.getSelectedRow();
			// �s�ҏW����
			isEditedDetail = false;
		} finally {
			isProcess = false;
		}
	}

	/**
	 * ���ʂֈړ�
	 */
	protected void doMoveDown() {

		isProcess = true;

		try {

			if (!btnSelectDetailChanged()) {
				return;
			}
			panel.tbl.moveRow(panel.tbl.getSelectedRow(), panel.tbl.getSelectedRow() + 1);
			controllButtons();
			if (!panel.btnRowUnder.isEnabled()) {
				panel.btnRowUp.requestFocus();
			}

			// �ޔ��s�̃Z�b�g
			beforeGyoNo = panel.tbl.getSelectedRow();
			// �s�ҏW����
			isEditedDetail = false;
		} finally {
			isProcess = false;
		}
	}

	/**
	 * �ŉ��ʂֈړ�
	 */
	protected void doMoveUnder() {

		isProcess = true;

		try {

			if (!btnSelectDetailChanged()) {
				return;
			}
			panel.tbl.moveRow(panel.tbl.getSelectedRow(), panel.tbl.getRowCount() - 1);
			controllButtons();

			panel.btnRowUp.requestFocus();

			// �ޔ��s�̃Z�b�g
			beforeGyoNo = panel.tbl.getSelectedRow();
			// �s�ҏW����
			isEditedDetail = false;
		} finally {
			isProcess = false;
		}
	}

	/**
	 * �X�v���b�h�I���s�ύX
	 */
	protected void selectedJornal() {

		isProcess = true;

		try {

			int row = panel.tbl.getSelectedRow();

			if (row < 0) {
				dispatch(null);

			} else {
				SWK_DTL dtl = (SWK_DTL) panel.tbl.getRowValueAt(row, SC.bean);
				dispatch(dtl);

				// �I���s�̒ʉ݂ɐؑ�
				if (dtl.getCurrency() != null) {
					panel.cbxCurrencyOnTotal.setSelectedText(dtl.getCurrency().getCode());
				}
			}

			controllButtons();

			// �ޔ��s�̃Z�b�g
			beforeGyoNo = panel.tbl.getSelectedRow();
			isEditedDetail = false;
		} finally {
			isProcess = false;
		}
	}

	/**
	 * ��ʂɔ��f
	 * 
	 * @param dtl �Ώۖ���
	 */
	protected void dispatch(SWK_DTL dtl) {

		this.dispatchDetail = dtl;

		// �N���A
		clearInput();

		if (dtl == null) {
			return;
		}
		if (dtl.getAppropriateCompany() != null) {
			setCompany(dtl.getAppropriateCompany()); // �v����
		} else {
			panel.ctrlKCompany.setCode(dtl.getKAI_CODE());
			panel.ctrlKCompany.refleshAndShowEntity();
			if (panel.ctrlKCompany.isEmpty()) {
				setCompany(getCompany());
			}
		}
		if (dtl.getDepartment() != null) {
			setDepartment(dtl.getDepartment()); // �v�㕔��
		} else {
			panel.ctrlKDepartment.setCode(dtl.getSWK_DEP_CODE());
			panel.ctrlKDepartment.refleshAndShowEntity();
		}

		Item item = dtl.getItem();
		panel.ctrlItem.setEntity(item != null ? item.clone() : null); // �Ȗ�
		changedItem();

		setCurrecy(dtl.getCurrency()); // �ʉ݃R�[�h

		panel.ctrlTax.setEntity(dtl.getTax()); // ��
		changedTax();

		if (dtl.isErasing()) {
			// ���͕s��
			panel.btnRowCopy.setEnabled(false);

			panel.ctrlKCompany.setEditable(false);
			panel.ctrlKDepartment.setEditable(false);
			panel.ctrlItem.ctrlItemReference.setEditable(false);
			panel.ctrlItem.ctrlSubItemReference.setEditable(false);
			panel.ctrlItem.ctrlDetailItemReference.setEditable(false);
			panel.ctrlOccurDate.setEditable(false);
			panel.ctrlTaxDivision.setEnabled(false);
			panel.ctrlDrCr.setEnabled(false);

			panel.ctrlCurrency.setEditable(false);
			panel.ctrlRate.setEditable(false);
			panel.ctrlTax.setEditable(false);
			panel.ctrlTaxAmount.setEditable(false);
			panel.ctrlCustomer.setEditable(false);
			panel.ctrlEmployee.setEditable(false);
			panel.ctrlManage1.setEditable(false);
			panel.ctrlManage2.setEditable(false);
			panel.ctrlManage3.setEditable(false);
			panel.ctrlManage4.setEditable(false);
			panel.ctrlManage5.setEditable(false);
			panel.ctrlManage6.setEditable(false);
			panel.ctrlNonAcDetails.setEditable(false);

			if (dtl.isAPErasing() || (isUseBS && dtl.isBSErasing())) {
				// AP�ABS�̏ꍇ�͋��z���͕s��
				panel.ctrlInputAmount.setEditable(false);
				panel.ctrlKeyAmount.setEditable(false);
			}
		}

		// ��
		if (dtl.getSWK_ZEI_KBN() != SWK_DTL.ZEI_KBN.TAX_OUT) {
			panel.ctrlTaxDivision.setSelectON(0);

		} else {
			panel.ctrlTaxDivision.setSelectON(1);
		}

		panel.ctrlKeyAmount.setNumberValue(dtl.getSWK_KIN()); // ���z
		panel.ctrlRate.setNumberValue(dtl.getSWK_CUR_RATE()); // �ʉ݃��[�g
		panel.ctrlInputAmount.setNumberValue(dtl.getSWK_IN_KIN()); // ���͋��z

		// ���͏���Ŋz
		if (dtl.getTax() == null || DecimalUtil.isNullOrZero(dtl.getTax().getRate())) {
			panel.ctrlTaxAmount.clear();

		} else {
			panel.ctrlTaxAmount.setNumberValue(dtl.getSWK_IN_ZEI_KIN());
		}

		// ������
		Date occurDate = dtl.getHAS_DATE();
		panel.ctrlTax.getSearchCondition().setValidTerm(occurDate);

		panel.ctrlKeyAmount.setNumberValue(dtl.getSWK_KIN()); // ����z
		panel.ctrlRemark.setCode(dtl.getSWK_GYO_TEK_CODE()); // �s�E�v�R�[�h
		panel.ctrlRemark.setNames(dtl.getSWK_GYO_TEK()); // �s�E�v
		panel.ctrlDrCr.setDR(dtl.isDR()); // �ݎ�
		panel.ctrlCustomer.setCode(dtl.getSWK_TRI_CODE()); // �����R�[�h
		panel.ctrlCustomer.setNames(dtl.getSWK_TRI_NAME_S()); // �����
		panel.ctrlEmployee.setCode(dtl.getSWK_EMP_CODE()); // �Ј��R�[�h
		panel.ctrlEmployee.setNames(dtl.getSWK_EMP_NAME_S()); // �Ј�
		panel.ctrlManage1.setCode(dtl.getSWK_KNR_CODE_1()); // �Ǘ�1�R�[�h
		panel.ctrlManage1.setNames(dtl.getSWK_KNR_NAME_S_1()); // �Ǘ�1
		panel.ctrlManage2.setCode(dtl.getSWK_KNR_CODE_2()); // �Ǘ�2�R�[�h
		panel.ctrlManage2.setNames(dtl.getSWK_KNR_NAME_S_2()); // �Ǘ�2
		panel.ctrlManage3.setCode(dtl.getSWK_KNR_CODE_3()); // �Ǘ�3�R�[�h
		panel.ctrlManage3.setNames(dtl.getSWK_KNR_NAME_S_3()); // �Ǘ�3
		panel.ctrlManage4.setCode(dtl.getSWK_KNR_CODE_4()); // �Ǘ�4�R�[�h
		panel.ctrlManage4.setNames(dtl.getSWK_KNR_NAME_S_4()); // �Ǘ�4
		panel.ctrlManage5.setCode(dtl.getSWK_KNR_CODE_5()); // �Ǘ�5�R�[�h
		panel.ctrlManage5.setNames(dtl.getSWK_KNR_NAME_S_5()); // �Ǘ�5
		panel.ctrlManage6.setCode(dtl.getSWK_KNR_CODE_6()); // �Ǘ�6�R�[�h
		panel.ctrlManage6.setNames(dtl.getSWK_KNR_NAME_S_6()); // �Ǘ�6
		panel.ctrlOccurDate.setValue(getOccurDate(dtl)); // ������
		panel.ctrlNonAcDetails.setValue(1, dtl.getSWK_HM_1()); // ���v����1
		panel.ctrlNonAcDetails.setValue(2, dtl.getSWK_HM_2()); // ���v����2
		panel.ctrlNonAcDetails.setValue(3, dtl.getSWK_HM_3()); // ���v����3

		// ���v�v�Z
		summary();
	}

	/**
	 * ��ʂ֓\��t���鎞�������̒l
	 * 
	 * @param dtl
	 * @return ������
	 */
	protected Date getOccurDate(SWK_DTL dtl) {

		if (isAllowOccurDateBlank()) {

			if (dtl.getItem() != null && !dtl.isUseItemOccurDate()) {
				// �Ȗڂ����������g�p�̏ꍇ�A�������̓u�����N�ɂ���
				return null;
			}
		}

		return dtl.getHAS_DATE();
	}

	/**
	 * ���X�g�ϊ�
	 * 
	 * @param dtl ����
	 * @return ���X�g
	 */
	public List<Object> getRow(SWK_DTL dtl) {

		// �����_����
		int digit = getCompany().getAccountConfig().getKeyCurrency().getDecimalPoint();
		if (dtl.getCurrency() != null) {
			digit = dtl.getCurrency().getDecimalPoint();
		}

		List<Object> list = new ArrayList(SC.values().length);
		list.add(dtl); // Entity
		list.add(dtl.getSWK_K_KAI_CODE()); // �v���ЃR�[�h
		list.add(dtl.getSWK_K_KAI_NAME_S()); // �v����
		list.add(dtl.getSWK_DEP_CODE()); // ����R�[�h
		list.add(dtl.getSWK_DEP_NAME_S()); // ����
		list.add(dtl.getSWK_KMK_CODE()); // �ȖڃR�[�h
		list.add(dtl.getSWK_KMK_NAME_S()); // �Ȗ�
		list.add(dtl.getSWK_HKM_CODE()); // �⏕�R�[�h
		list.add(dtl.getSWK_HKM_NAME_S()); // �⏕
		list.add(dtl.getSWK_UKM_CODE()); // ����ȖڃR�[�h
		list.add(dtl.getSWK_UKM_NAME_S()); // ����

		String zkbn = "";
		switch (dtl.getSWK_ZEI_KBN()) {
			case 0:
				zkbn = getWord("C00337");// �O��
				break;
			case 1:
				zkbn = getWord("C00023");// ����
				break;
			case 2:
				zkbn = getWord("C01971");// ��ې�
				break;
		}
		list.add(getWord(zkbn)); // �ŋ敪
		list.add(dtl.getSWK_ZEI_CODE()); // ����ŃR�[�h
		list.add(dtl.getSWK_ZEI_NAME_S()); // ����Ŗ���
		list.add(DecimalUtil.isNullOrZero(dtl.getSWK_ZEI_RATE()) ? "" : NumberFormatUtil.formatNumber(
			dtl.getSWK_ZEI_RATE(), 1)
			+ "%"); // �ŗ�
		list.add(NumberFormatUtil.formatNumber(dtl.getSWK_KIN(), keyDigit)); // ���z
		list.add(dtl.getSWK_CUR_CODE()); // �ʉ݃R�[�h
		list.add(NumberFormatUtil.formatNumber(dtl.getSWK_CUR_RATE(), 4)); // �ʉ݃��[�g

		list.add(NumberFormatUtil.zeroToEmptyFormat(dtl.getDebitInputAmount(), digit)); // �ؕ����z(�O��)
		list.add(NumberFormatUtil.zeroToEmptyFormat(dtl.getDebitKeyAmount(), keyDigit)); // �ؕ����z
		list.add(NumberFormatUtil.zeroToEmptyFormat(dtl.getSWK_IN_ZEI_KIN(), digit)); // ����Ŋz(�O��)
		list.add(NumberFormatUtil.zeroToEmptyFormat(dtl.getSWK_ZEI_KIN(), keyDigit)); // ����Ŋz
		list.add(NumberFormatUtil.zeroToEmptyFormat(dtl.getCreditInputAmount(), digit)); // �ݕ����z(�O��)
		list.add(NumberFormatUtil.zeroToEmptyFormat(dtl.getCreditKeyAmount(), keyDigit)); // �ݕ����z
		list.add(dtl.getSWK_GYO_TEK_CODE()); // �s�E�v�R�[�h
		list.add(dtl.getSWK_GYO_TEK()); // �s�E�v
		list.add(dtl.getSWK_TRI_CODE()); // �����R�[�h
		list.add(dtl.getSWK_TRI_NAME_S()); // �����
		list.add(dtl.getSWK_EMP_CODE()); // �Ј��R�[�h
		list.add(dtl.getSWK_EMP_NAME_S()); // �Ј�
		list.add(dtl.getSWK_KNR_CODE_1()); // �Ǘ�1�R�[�h
		list.add(dtl.getSWK_KNR_NAME_S_1()); // �Ǘ�1
		list.add(dtl.getSWK_KNR_CODE_2()); // �Ǘ�2�R�[�h
		list.add(dtl.getSWK_KNR_NAME_S_2()); // �Ǘ�2
		list.add(dtl.getSWK_KNR_CODE_3()); // �Ǘ�3�R�[�h
		list.add(dtl.getSWK_KNR_NAME_S_3()); // �Ǘ�3
		list.add(dtl.getSWK_KNR_CODE_4()); // �Ǘ�4�R�[�h
		list.add(dtl.getSWK_KNR_NAME_S_4()); // �Ǘ�4
		list.add(dtl.getSWK_KNR_CODE_5()); // �Ǘ�5�R�[�h
		list.add(dtl.getSWK_KNR_NAME_S_5()); // �Ǘ�5
		list.add(dtl.getSWK_KNR_CODE_6()); // �Ǘ�6�R�[�h
		list.add(dtl.getSWK_KNR_NAME_S_6()); // �Ǘ�6
		list.add(DateUtil.toYMDString(getOccurDate(dtl))); // ������
		list.add(dtl.getSWK_HM_1()); // ���v����1
		list.add(dtl.getSWK_HM_2()); // ���v����2
		list.add(dtl.getSWK_HM_3()); // ���v����3
		list.add(NumberFormatUtil.formatNumber(dtl.getSWK_IN_KIN(), digit)); // ���͋��z

		return list;
	}

	/**
	 * ��ʓ��͒l���Z�b�g
	 * 
	 * @param dtl ����
	 */
	protected void reflect(SWK_DTL dtl) {
		dtl.setAppropriateCompany(panel.ctrlKCompany.getEntity()); // �v����
		dtl.setDepartment(panel.ctrlKDepartment.getEntity()); // �v�㕔��
		dtl.setItem(panel.ctrlItem.getEntity()); // �Ȗ�
		dtl.setTax(panel.ctrlTax.getEntity()); // ��
		dtl.setCurrency(panel.ctrlCurrency.getEntity()); // �ʉ�
		dtl.setCustomer(panel.ctrlCustomer.getEntity()); // �����

		// ��
		ConsumptionTax tax = dtl.getTax();
		if (tax == null || tax.getTaxType() == TaxType.NOT || DecimalUtil.isNullOrZero(tax.getRate())) {
			dtl.setSWK_ZEI_KBN(SWK_DTL.ZEI_KBN.TAX_NONE);

		} else {
			dtl.setSWK_ZEI_KBN(panel.ctrlTaxDivision.isSelected(0) ? SWK_DTL.ZEI_KBN.TAX_IN : SWK_DTL.ZEI_KBN.TAX_OUT); // ��
		}

		dtl.setSWK_CUR_RATE(panel.ctrlRate.getBigDecimal()); // �ʉ݃��[�g
		dtl.setSWK_IN_KIN(panel.ctrlInputAmount.getBigDecimal()); // ���͋��z

		BigDecimal inTaxAmount = panel.ctrlTaxAmount.getBigDecimal(); // ���͏����
		dtl.setSWK_IN_ZEI_KIN(inTaxAmount); // ���͏���Ŋz

		dtl.setSWK_ZEI_KIN(convertKeyTaxAmount(inTaxAmount)); // ����Ŋz(�M��)
		dtl.setSWK_KIN(panel.ctrlKeyAmount.getBigDecimal()); // ����z

		dtl.setSWK_GYO_TEK_CODE(panel.ctrlRemark.getCode()); // �s�E�v�R�[�h
		dtl.setSWK_GYO_TEK(panel.ctrlRemark.getNames()); // �s�E�v
		dtl.setDC(panel.ctrlDrCr.isDR() ? Dc.DEBIT : Dc.CREDIT); // �ݎ�
		dtl.setSWK_TRI_CODE(panel.ctrlCustomer.getCode()); // �����R�[�h
		dtl.setSWK_TRI_NAME(panel.ctrlCustomer.getEntity() == null ? "" : panel.ctrlCustomer.getEntity().getName());
		dtl.setSWK_TRI_NAME_S(panel.ctrlCustomer.getNames());
		dtl.setSWK_EMP_CODE(panel.ctrlEmployee.getCode()); // �Ј��R�[�h
		dtl.setSWK_EMP_NAME(panel.ctrlEmployee.getEntity() == null ? "" : panel.ctrlEmployee.getEntity().getName());
		dtl.setSWK_EMP_NAME_S(panel.ctrlEmployee.getNames());
		dtl.setSWK_KNR_CODE_1(panel.ctrlManage1.getCode()); // �Ǘ�1�R�[�h
		dtl.setSWK_KNR_NAME_1(panel.ctrlManage1.getEntity() == null ? "" : panel.ctrlManage1.getEntity().getName());
		dtl.setSWK_KNR_NAME_S_1(panel.ctrlManage1.getNames());
		dtl.setSWK_KNR_CODE_2(panel.ctrlManage2.getCode()); // �Ǘ�2�R�[�h
		dtl.setSWK_KNR_NAME_2(panel.ctrlManage2.getEntity() == null ? "" : panel.ctrlManage2.getEntity().getName());
		dtl.setSWK_KNR_NAME_S_2(panel.ctrlManage2.getNames());
		dtl.setSWK_KNR_CODE_3(panel.ctrlManage3.getCode()); // �Ǘ�3�R�[�h
		dtl.setSWK_KNR_NAME_3(panel.ctrlManage3.getEntity() == null ? "" : panel.ctrlManage3.getEntity().getName());
		dtl.setSWK_KNR_NAME_S_3(panel.ctrlManage3.getNames());
		dtl.setSWK_KNR_CODE_4(panel.ctrlManage4.getCode()); // �Ǘ�4�R�[�h
		dtl.setSWK_KNR_NAME_4(panel.ctrlManage4.getEntity() == null ? "" : panel.ctrlManage4.getEntity().getName());
		dtl.setSWK_KNR_NAME_S_4(panel.ctrlManage4.getNames());
		dtl.setSWK_KNR_CODE_5(panel.ctrlManage5.getCode()); // �Ǘ�5�R�[�h
		dtl.setSWK_KNR_NAME_5(panel.ctrlManage5.getEntity() == null ? "" : panel.ctrlManage5.getEntity().getName());
		dtl.setSWK_KNR_NAME_S_5(panel.ctrlManage5.getNames());
		dtl.setSWK_KNR_CODE_6(panel.ctrlManage6.getCode()); // �Ǘ�6�R�[�h
		dtl.setSWK_KNR_NAME_6(panel.ctrlManage6.getEntity() == null ? "" : panel.ctrlManage6.getEntity().getName());
		dtl.setSWK_KNR_NAME_S_6(panel.ctrlManage6.getNames());
		dtl.setSWK_HM_1(panel.ctrlNonAcDetails.getValue(1)); // ���v����1
		dtl.setSWK_HM_2(panel.ctrlNonAcDetails.getValue(2)); // ���v����2
		dtl.setSWK_HM_3(panel.ctrlNonAcDetails.getValue(3)); // ���v����3

		// ������
		Date occurDate = panel.ctrlOccurDate.getValue();
		dtl.setHAS_DATE(occurDate); // ������
	}

	/**
	 * ���v���̒ʉݕύX
	 */
	protected void changedCurrencyOnTotal() {
		summaryInAmount();
	}

	/**
	 * ���v�\��
	 */
	protected void summary() {
		BigDecimal dr = BigDecimal.ZERO;
		BigDecimal cr = BigDecimal.ZERO;
		BigDecimal drTax = BigDecimal.ZERO;
		BigDecimal crTax = BigDecimal.ZERO;

		int row = panel.tbl.getSelectedRow();

		for (int i = 0; i < panel.tbl.getRowCount(); i++) {
			if (row == i) {
				continue;
			}

			SWK_DTL dtl = (SWK_DTL) panel.tbl.getRowValueAt(i, SC.bean);

			dr = dr.add(dtl.getDebitKeyAmount());
			cr = cr.add(dtl.getCreditKeyAmount());
			drTax = drTax.add(dtl.getDebitTaxAmount());
			crTax = crTax.add(dtl.getCreditTaxAmount());

			// �O�ł͍��v�ɏ���Ńv���X
			if (!dtl.isTaxInclusive()) {
				dr = dr.add(dtl.getDebitTaxAmount());
				cr = cr.add(dtl.getCreditTaxAmount());
			}
		}

		// ���͕�(��ʕ\���ŕҏW��)������
		BigDecimal inAmount = panel.ctrlInputAmount.getBigDecimal();
		BigDecimal inTaxAmount = panel.ctrlTaxAmount.getBigDecimal();

		BigDecimal keyAmount = panel.ctrlKeyAmount.getBigDecimal();
		BigDecimal taxAmount = convertKeyTaxAmount(inTaxAmount); // ����ł̊���z

		// �O�ł͍��v�ɏ���Ńv���X
		if (!panel.ctrlTaxDivision.isSelected(0)) {
			inAmount = inAmount.add(inTaxAmount);
			keyAmount = keyAmount.add(taxAmount);
		}

		if (panel.ctrlDrCr.isDR()) {
			dr = dr.add(keyAmount);
			drTax = drTax.add(taxAmount);

		} else {
			cr = cr.add(keyAmount);
			crTax = crTax.add(taxAmount);
		}

		// �O���w�肠��Α���
		if (outerDetail != null) {
			dr = dr.add(outerDetail.getDebitKeyAmount());
			cr = cr.add(outerDetail.getCreditKeyAmount());
			drTax = drTax.add(outerDetail.getDebitTaxAmount());
			crTax = crTax.add(outerDetail.getCreditTaxAmount());

			// �O�ł͍��v�ɏ���Ńv���X
			if (!outerDetail.isTaxInclusive()) {
				dr = dr.add(outerDetail.getDebitTaxAmount());
				cr = cr.add(outerDetail.getCreditTaxAmount());
			}
		}

		panel.ctrlDrTotal.setNumberValue(dr);
		panel.ctrlCrTotal.setNumberValue(cr);
		panel.ctrlDrTaxTotal.setNumberValue(drTax);
		panel.ctrlCrTaxTotal.setNumberValue(crTax);

		// �O�ݍ��v
		summaryInAmount();
	}

	/**
	 * �O�ݍ��v(summary()�Ă�ł���ĂԑO��)
	 */
	protected void summaryInAmount() {
		Map<String, BigDecimal[]> map = getForeignAmountMap();

		// �f�t�H���g
		panel.ctrlFcDrTotal.setValue(null);
		panel.ctrlFcCrTotal.setValue(null);
		panel.ctrlFcDiff.setValue(null);
		panel.ctrlExchangeDiff.setValue(null);

		// �I��ʉ�
		Currency selectCurrency = (Currency) panel.cbxCurrencyOnTotal.getSelectedItemValue();

		if (selectCurrency != null) {
			int digit = selectCurrency.getDecimalPoint();
			panel.ctrlFcDrTotal.setDecimalPoint(digit);
			panel.ctrlFcCrTotal.setDecimalPoint(digit);
			panel.ctrlFcDiff.setDecimalPoint(digit);

			BigDecimal[] dec = map.get(selectCurrency.getCode());
			if (dec != null) {
				panel.ctrlFcDrTotal.setNumberValue(dec[0]);
				panel.ctrlFcCrTotal.setNumberValue(dec[1]);
				panel.ctrlFcDiff.setNumberValue(dec[0].subtract(dec[1]));
			}
		}

		BigDecimal dr = panel.ctrlDrTotal.getBigDecimal();
		BigDecimal cr = panel.ctrlCrTotal.getBigDecimal();

		// �ב֍�
		if (panel.cbxCurrencyOnTotal.getItemCount() != 0 && !map.isEmpty()) {
			boolean isFBalance = false;
			for (BigDecimal[] dec : map.values()) {
				if (DecimalUtil.isZero(dec[0]) && DecimalUtil.isZero(dec[1])) {
					continue; // ����0/0�͊܂߂Ȃ�
				}
				isFBalance = dec[0].compareTo(dec[1]) == 0;

				if (!isFBalance) {
					break;
				}
			}

			if (isFBalance) {
				panel.ctrlExchangeDiff.setNumberValue(dr.subtract(cr));
			}
		}

		panel.ctrlDiff.setNumberValue(panel.ctrlExchangeDiff.getBigDecimal().subtract(dr.subtract(cr)));
	}

	/**
	 * �O�ݍ��v
	 * 
	 * @return �O��Map
	 */
	protected Map<String, BigDecimal[]> getForeignAmountMap() {

		String keyCode = keyCurrency.getCode();

		Map<String, BigDecimal[]> map = new TreeMap<String, BigDecimal[]>();

		int row = panel.tbl.getSelectedRow();

		for (int i = 0; i < panel.tbl.getRowCount(); i++) {
			if (row == i) {
				continue;
			}

			SWK_DTL dtl = (SWK_DTL) panel.tbl.getRowValueAt(i, SC.bean);

			// �ʉ�
			String code = dtl.getSWK_CUR_CODE();

			if (Util.isNullOrEmpty(code) || keyCode.equals(code)) {
				continue;
			}

			BigDecimal[] dec = map.get(code);
			if (dec == null) {
				dec = new BigDecimal[] { BigDecimal.ZERO, BigDecimal.ZERO };
				map.put(code, dec);
			}

			dec[0] = dec[0].add(dtl.getDebitInputAmount());
			dec[1] = dec[1].add(dtl.getCreditInputAmount());

			// �O�ł͍��v�ɏ���Ńv���X
			if (!dtl.isTaxInclusive()) {
				dec[0] = dec[0].add(dtl.getDebitTaxInputAmount());
				dec[1] = dec[1].add(dtl.getCreditTaxInputAmount());
			}
		}

		// ���͕�(��ʕ\���ŕҏW��)������
		String panelCode = panel.ctrlCurrency.getCode();
		if (!keyCode.equals(panelCode)) {
			BigDecimal inAmount = panel.ctrlInputAmount.getBigDecimal();
			BigDecimal inTaxAmount = panel.ctrlTaxAmount.getBigDecimal();

			// �O�ł͍��v�ɏ���Ńv���X
			if (!panel.ctrlTaxDivision.isSelected(0)) {
				inAmount = inAmount.add(inTaxAmount);
			}

			BigDecimal[] dec = map.get(panelCode);
			if (dec == null) {
				dec = new BigDecimal[] { BigDecimal.ZERO, BigDecimal.ZERO };
				map.put(panelCode, dec);
			}

			if (panel.ctrlDrCr.isDR()) {
				dec[0] = dec[0].add(inAmount); // DR
			} else {
				dec[1] = dec[1].add(inAmount); // CR
			}
		}

		// �O���w�肠��Α���
		if (outerDetail != null && !Util.isNullOrEmpty(outerDetail.getSWK_CUR_CODE())
			&& !keyCode.equals(outerDetail.getSWK_CUR_CODE())) {
			String otherCode = outerDetail.getSWK_CUR_CODE();

			BigDecimal[] dec = map.get(otherCode);
			if (dec == null) {
				dec = new BigDecimal[] { BigDecimal.ZERO, BigDecimal.ZERO };
				map.put(otherCode, dec);
			}

			dec[0] = dec[0].add(outerDetail.getDebitInputAmount()); // DR
			dec[1] = dec[1].add(outerDetail.getCreditInputAmount()); // CR

			// �O�ł͍��v�ɏ���Ńv���X
			if (!outerDetail.isTaxInclusive()) {
				dec[0] = dec[0].add(outerDetail.getDebitTaxInputAmount());
				dec[1] = dec[1].add(outerDetail.getCreditTaxInputAmount());
			}
		}

		return map;
	}

	/**
	 * ��ʓ��͏������Ɋ���z�Ɋ��Z
	 * 
	 * @param amount ���͋��z
	 * @return ��ʉ݋��z
	 */
	protected BigDecimal convertKeyAmount(BigDecimal amount) {

		if (DecimalUtil.isNullOrZero(amount)) {
			return BigDecimal.ZERO;
		}

		BigDecimal rate = panel.ctrlRate.getBigDecimal();
		Company kcompany = panel.ctrlKCompany.getEntity();
		Currency currency = panel.ctrlCurrency.getEntity();

		if (kcompany == null || currency == null) {
			return BigDecimal.ZERO;
		}

		// ���Z
		TExchangeAmount param = TCalculatorFactory.createExchangeAmount();
		param.setExchangeFraction(kcompany.getAccountConfig().getExchangeFraction());
		param.setConvertType(conf.getConvertType());
		param.setDigit(keyCurrency.getDecimalPoint());
		param.setForeignAmount(amount);
		param.setRate(rate);
		param.setRatePow(currency.getRatePow());

		return calculator.exchangeKeyAmount(param);
	}

	/**
	 * ��ʓ��͏������Ɋ����Ŋz�Ɋ��Z
	 * 
	 * @param taxAmount ���͏���Ŋz
	 * @return ��ʉݏ���Ŋz
	 */
	protected BigDecimal convertKeyTaxAmount(BigDecimal taxAmount) {

		if (DecimalUtil.isNullOrZero(taxAmount)) {
			return BigDecimal.ZERO;
		}

		Company kcompany = panel.ctrlKCompany.getEntity();
		Currency currency = panel.ctrlCurrency.getEntity();
		ConsumptionTax tax = panel.ctrlTax.getEntity();
		BigDecimal rate = panel.ctrlRate.getBigDecimal();

		if (kcompany == null || currency == null || tax == null) {
			return BigDecimal.ZERO;
		}

		TExchangeAmount param = TCalculatorFactory.createExchangeAmount();
		param.setExchangeFraction(kcompany.getAccountConfig().getExchangeFraction());
		param.setConvertType(conf.getConvertType());
		param.setDigit(keyCurrency.getDecimalPoint());
		param.setForeignAmount(taxAmount);
		param.setRate(rate);
		param.setRatePow(currency.getRatePow());

		return calculator.exchangeKeyAmount(param);
	}

	/**
	 * �ʉ݃��[�g�擾
	 * 
	 * @return ���[�g
	 */
	protected BigDecimal getCurrencyRate() {
		try {

			Currency currency = panel.ctrlCurrency.getEntity();

			if (currency == null) {
				return null;
			}

			if (currency.getCode().equals(keyCurrency.getCode())) {
				return BigDecimal.ONE;
			}

			Date occurDate = panel.ctrlOccurDate.getValue();

			// �������u�����N�\���u�����N�̏ꍇ�A�����ݒ肷��
			if (isAllowOccurDateBlank() && occurDate == null) {
				occurDate = baseDate;
			}

			if (occurDate == null) {
				return null;
			}

			return (BigDecimal) request(RateManager.class, isClosing ? "getSettlementRate" : "getRate", currency,
				occurDate);

		} catch (TException ex) {
			errorHandler(ex);
			return null;
		}
	}

	/**
	 * �ʉ݃��[�g�擾
	 * 
	 * @param hasDate ������
	 * 
	 * @return ���[�g
	 */
	protected BigDecimal getCurrencyRateByOccurDate(Date hasDate) {
		BigDecimal rate = null;
		try {

			Currency currency = panel.ctrlCurrency.getEntity();

			if (currency == null) {
				return null;
			}

			if (currency.getCode().equals(keyCurrency.getCode())) {
				return BigDecimal.ONE;
			}
			String key = currency.getCode() + "<>" + DateUtil.toYMDString(hasDate);
			if (rateMap.containsKey(key) && rateMap.get(key) != null) {
				rate = rateMap.get(key);
			} else {
				rate = (BigDecimal) request(RateManager.class,
						isClosing ? "getSettlementRateByOccurDate" : "getRateByOccurDate", currency.getCode(), hasDate);
				rateMap.put(key, rate);
			}

		} catch (TException ex) {
			errorHandler(ex);
			return null;
		}
		return rate;
	}

	/**
	 * AP�c������
	 */
	public void doAPErasing() {
		TApBalanceListDialogCtrl dialog = createAPBalanceListDialogCtrl();
		dialog.setProgramInfo(getProgramInfo());
		dialog.setNowSlipNo(this.slipNo); // �ҏW���`�[�ԍ�

		if (collectionCustomer != null) {
			dialog.setCustomer(collectionCustomer);

			dialog.dialog.ctrlClientRange.getFieldFrom().setEditable(true);
			dialog.dialog.ctrlClientRange.getFieldTo().setEditable(true);
		}

		// �ҏW�������f�[�^
		List<AP_ZAN> eraseList = new ArrayList<AP_ZAN>();
		for (SWK_DTL dtl : getEntityList()) {
			if (dtl.getAP_ZAN() != null) {
				eraseList.add(dtl.getAP_ZAN());
			}
		}

		dialog.setNowEraseList(eraseList);

		if (dialog.show() != TApBalanceListDialogCtrl.OK_OPTION) {
			return;
		}

		// �d��s�ǉ�
		List<AP_ZAN> list = getSelectedAPBalanceList(dialog);

		for (AP_ZAN zan : list) {

			// �s�̒ǉ�
			panel.tbl.addRow(getRow(toDetail(zan)));
		}

		// ���v�̒ʉ�
		makeCurrencyComboBox();

		summary();
	}

	/**
	 * @param dialog
	 * @return �I�����c�����X�g
	 */
	protected List<AP_ZAN> getSelectedAPBalanceList(TApBalanceListDialogCtrl dialog) {
		return detailConverter.getSelectedAPBalanceList(dialog);
	}

	/**
	 * �c�����X�g�ϊ�
	 * 
	 * @param zan ���c��
	 * @return ���X�g
	 */
	protected SWK_DTL toDetail(AP_ZAN zan) {
		return detailConverter.toDetail(zan);
	}

	/**
	 * AR�c������
	 */
	public void doARErasing() {
		TArBalanceListDialogCtrl dialog = createARBalanceListDialogCtrl();
		dialog.setProgramInfo(getProgramInfo());
		dialog.setNowSlipNo(this.slipNo); // �ҏW���`�[�ԍ�

		if (collectionCustomer != null) {
			dialog.setCollectionCustomer(collectionCustomer);
		}

		// �ҏW�������f�[�^
		List<AR_ZAN> eraseList = new ArrayList<AR_ZAN>();
		for (SWK_DTL dtl : getEntityList()) {
			if (dtl.getAR_ZAN() != null) {
				eraseList.add(dtl.getAR_ZAN());
			}
		}

		dialog.setNowEraseList(eraseList);

		if (dialog.show() != TApBalanceListDialogCtrl.OK_OPTION) {
			return;
		}

		// �d��s�ǉ�
		List<AR_ZAN> list = getSelectedARBalanceList(dialog);

		for (AR_ZAN zan : list) {

			// �s�̒ǉ�
			panel.tbl.addRow(getRow(toDetail(zan)));
		}

		// ���v�̒ʉ�
		makeCurrencyComboBox();

		summary();
	}

	/**
	 * @param dialog
	 * @return �I�����c�����X�g
	 */
	protected List<AR_ZAN> getSelectedARBalanceList(TArBalanceListDialogCtrl dialog) {
		return detailConverter.getSelectedARBalanceList(dialog);
	}

	/**
	 * �c�����X�g�ϊ�
	 * 
	 * @param zan ���c��
	 * @return ���X�g
	 */
	protected SWK_DTL toDetail(AR_ZAN zan) {
		return detailConverter.toDetail(zan);
	}

	/**
	 * �d��̃G���e�B�e�B���[���I�ɃZ�b�g.
	 * 
	 * @param dtl �d��
	 * @return �d��
	 */
	@Deprecated
	protected SWK_DTL buildDetail(SWK_DTL dtl) {
		return detailConverter.buildDetail(dtl, getCompany());
	}

	/**
	 * �d��̃G���e�B�e�B���[���I�ɃZ�b�g.
	 * 
	 * @param dtl �d��
	 * @param kcompany �v���ЃR�[�h
	 * @return �d��
	 */
	@Deprecated
	protected SWK_DTL buildDetail(SWK_DTL dtl, Company kcompany) {
		return detailConverter.buildDetail(dtl, kcompany);
	}

	/**
	 * BS����{�^������
	 */
	protected void doBS() {
		BSEraseDialogCtrl dialog = createBSDialog();

		dialog.setBaseDate(this.baseDate); // ��`�[���t
		dialog.setCurrentSlipNo(this.slipNo); // ����ҏW���̓`�[�ԍ�
		if (this.customer != null) {
			dialog.setCustomer(this.customer); // �w�b�_�[�����ݒ�
		} else if (collectionCustomer != null) {
			dialog.setCustomer(collectionCustomer); // ��������ݒ�
		}

		// �ҏW�������f�[�^
		List<SWK_DTL> eraseList = new ArrayList<SWK_DTL>();
		for (SWK_DTL dtl : getEntityList()) {
			if (dtl.getBsDetail() != null) {
				eraseList.add(dtl.getBsDetail());
			}
		}

		dialog.setNowEraseList(eraseList);

		if (dialog.show() != BSEraseDialogCtrl.OK_OPTION) {
			return;
		}

		// �d��s�ǉ�
		List<SWK_DTL> list = getSelectedBSList(dialog);

		for (SWK_DTL bs : list) {

			// �s�̒ǉ�
			panel.tbl.addRow(getRow(toDetail(bs)));
		}

		// ���v�̒ʉ�
		makeCurrencyComboBox();

		summary();
	}

	/**
	 * @param dialog
	 * @return �I��BS���胊�X�g
	 */
	protected List<SWK_DTL> getSelectedBSList(BSEraseDialogCtrl dialog) {
		return detailConverter.getSelectedBSList(dialog);
	}

	/**
	 * BS��������_�C�A���O�̍쐬
	 * 
	 * @return BS��������_�C�A���O
	 */
	protected BSEraseDialogCtrl createBSDialog() {

		if (isBsUseKCompany) {

			// �v���Ўw��

			Company kcompany = panel.ctrlKCompany.getEntity();
			if (kcompany == null) {
				kcompany = getCompany();
			}

			return new BSEraseDialogCtrl(panel, getProgramInfo(), kcompany);
		}
		return new BSEraseDialogCtrl(panel, getProgramInfo());

	}

	/**
	 * BS�����ϊ�
	 * 
	 * @param bs BS����
	 * @return �d��W���[�i��
	 */
	protected SWK_DTL toDetail(SWK_DTL bs) {
		return detailConverter.toDetail(bs, isBsUseKCompany ? panel.ctrlKCompany.getEntity() : null);
	}

	/**
	 * @return ���׎d��
	 */
	protected SWK_DTL createSlipDetail() {
		return new SWK_DTL();
	}

	/**
	 * ���ׂ�Entity�`���ŃZ�b�g����.
	 * 
	 * @param dtlList �f�[�^
	 */
	public void setEntityList(List<SWK_DTL> dtlList) {
		panel.tbl.removeRow();

		for (SWK_DTL dtl : dtlList) {

			// �s�̒ǉ�
			List<Object> list = getRow(dtl);
			panel.tbl.addRow(list);
		}

		// �X�N���[���o�[�̈ʒu�������
		panel.tbl.getHorizontalScrollBar().setValue(0);
		panel.tbl.getVerticalScrollBar().setValue(0);

		// �N���A
		clearInput();

		// �㉺�{�^��
		controllButtons();

		// ���v�̒ʉ�
		makeCurrencyComboBox();

		// ���v
		summary();
	}

	/**
	 * ���ׂ�Entity�`���ŕԂ�.
	 * 
	 * @return Entity���X�g
	 */
	public List<SWK_DTL> getEntityList() {

		List<SWK_DTL> list = new ArrayList<SWK_DTL>();

		for (int i = 0; i < panel.tbl.getRowCount(); i++) {
			SWK_DTL dtl = (SWK_DTL) panel.tbl.getRowValueAt(i, SC.bean);

			if (isAllowEntryDefaultOccurDate(dtl)) {
				dtl.setHAS_DATE(baseDate); // ���������`�[���t
			}

			list.add(dtl);
		}

		// �O���w�薾�ׂ��Z�b�g
		if (outerDetail != null) {
			list.add(outerDetail);
		}

		return list;
	}

	/**
	 * @return AP�c���ꗗ�_�C�A���OCtrl
	 */
	protected TApBalanceListDialogCtrl createAPBalanceListDialogCtrl() {
		return new TApBalanceListDialogCtrl(panel);
	}

	/**
	 * @return AR�c���ꗗ�_�C�A���OCtrl
	 */
	protected TArBalanceListDialogCtrl createARBalanceListDialogCtrl() {
		return new TArBalanceListDialogCtrl(panel);
	}

	/**
	 * ���ד��e���ύX���ꂽ���ǂ����x���\��
	 * 
	 * @return true:�ύX��
	 */
	protected boolean inputDetailCheckAlert() {
		// �s�ҏW��
		if (isEditedDetail) {
			// ���׍s���ύX����Ă��܂��B�s�m����������Ȃ��ƕҏW���̓��e�̓N���A����܂�����낵���ł����H
			return showConfirmMessage("I01055");
		}
		isEditedDetail = false;
		return true;
	}

	/**
	 * �I���s����̏���
	 * 
	 * @return boolean
	 */
	protected boolean rowSelectionDetailChanged() {

		// �s�m�肵�Ă��Ȃ��f�[�^�`�F�b�N
		int row = panel.tbl.getSelectedRow();
		if (row >= 0 && row != beforeGyoNo && inputDetailCheckAlert) {
			if (!inputDetailCheckAlert()) {
				if (beforeGyoNo == -1) {
					// �I���N���A
					panel.tbl.clearSelection();
				} else {
					panel.tbl.setRowSelection(beforeGyoNo);
				}
				return false;
			}
		}
		isEditedDetail = false;
		return true;

	}

	/**
	 * �I���s����̏���
	 * 
	 * @return boolean
	 */
	protected boolean btnSelectDetailChanged() {

		// �s�m�肵�Ă��Ȃ��f�[�^�`�F�b�N
		if (inputDetailCheckAlert) {
			if (!inputDetailCheckAlert()) {
				if (beforeGyoNo == -1) {
					// �I���N���A
					panel.tbl.clearSelection();
				} else {
					panel.tbl.setRowSelection(beforeGyoNo);
				}
				return false;
			}
		}
		isEditedDetail = false;
		return true;

	}

	/**
	 * �C���{�C�X ON �����}�X�^�Ŕ�K�i���������s���ƎҐݒ肳��Ă������ŃR�[�h���Z�b�g
	 * 
	 * @param tax ����ŏ��
	 */
	protected void setTaxNoInvReg(ConsumptionTax tax) {

		if (chkSlipTypeInvoice()) {// �`�[��ʃ`�F�b�N
			return;
		}

		if (tax == null || tax.getTaxType().value != TaxType.PURCHAESE.value
			|| Util.isNullOrEmpty(panel.ctrlCustomer.getCode())) {
			// �Ȗڂɐݒ肳��Ă������ŃR�[�h���d���ȊO�܂��͎����R�[�h�������͂̏ꍇ�̓R�[�h�Z�b�g���Ȃ�
			return;
		}

		Customer bean = panel.ctrlCustomer.getEntity();

		if (bean == null) {
			return;
		}

		if (!Util.isNullOrEmpty(bean.getNO_INV_REG_ZEI_CODE())) {
			panel.ctrlTax.code.setValue(bean.getNO_INV_REG_ZEI_CODE());
		} else {
			panel.ctrlTax.code.setValue(tax.getCode());
		}
		panel.ctrlTax.refleshAndShowEntity();

		// ����ŕύX����
		changedTax();

	}

	/**
	 * �C���{�C�X �����Entity�擾
	 * 
	 * @param cmpCode ��ЃR�[�h
	 * @param code �����R�[�h
	 * @return bean
	 */
	protected Customer setCustomerEntity(String cmpCode, String code) {

		List<Customer> list = new ArrayList<Customer>();
		try {

			CustomerSearchCondition condition = new CustomerSearchCondition();
			condition.setCompanyCode(cmpCode);
			condition.setCode(code);

			list = (List<Customer>) request(CustomerManager.class, "get", condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

		} catch (Exception e) {
			errorHandler(e);
		}

		return list.get(0);
	}

	/**
	 * �C���{�C�X�p����ŃZ�b�g
	 * 
	 * @param param
	 * @param inAmount
	 */
	protected void setInvoiceTaxAmount(TTaxCalculation param, BigDecimal inAmount) {

		Currency currency = panel.ctrlCurrency.getEntity();
		ConsumptionTax tax = panel.ctrlTax.getEntity();

		if (tax == null) {
			return;
		}

		// 2023INVOICE���x�F�v�Z
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal amountDr = BigDecimal.ZERO;
		BigDecimal amountCr = BigDecimal.ZERO;

		BigDecimal taxDr = BigDecimal.ZERO;
		BigDecimal taxCr = BigDecimal.ZERO;
		BigDecimal taxAmountSum = BigDecimal.ZERO;
		BigDecimal swkKin = BigDecimal.ZERO;
		BigDecimal zei = BigDecimal.ZERO;

		int row = 0;
		int zeiKbn = TaxCalcType.IN.value;

		boolean isDr = panel.ctrlDrCr.isSelected(Dc.DEBIT.value);

		if (!panel.ctrlTaxDivision.isSelected(0)) {
			// �O��
			zeiKbn = TaxCalcType.OUT.value;
		}

		TTable tbl = getTable();

		for (int i = 0; i < tbl.getRowCount(); i++) {

			SWK_DTL dtl = (SWK_DTL) tbl.getRowValueAt(i, SC.bean);

			if (isInvoiceTaxSum(i, tbl.getSelectedRow(), dtl)) {
				continue;
			}

			swkKin = getSwkKin(dtl);

			// key:����Ń��[�g�A�ʉ݃R�[�h�A����ŋ敪
			if (dtl.getSWK_ZEI_RATE().equals(tax.getRate()) && currency.getCode().equals(dtl.getSWK_CUR_CODE())
				&& zeiKbn == dtl.getSWK_ZEI_KBN()) {

				zei = dtl.getSWK_IN_ZEI_KIN();

				if (isCredit(dtl)) {
					amountCr = amountCr.add(swkKin);
					taxCr = taxCr.add(zei);
				} else {
					amountDr = amountDr.add(swkKin);
					taxDr = taxDr.add(zei);
				}

				row++;
			}
		}

		// ���v���閾�ׂ�1�s�ł����݂����ꍇ
		if (row > 0) {

			// ��ʒl�̋��z�𑫂�
			if (isDr) {
				amountDr = amountDr.add(inAmount);
			} else {
				amountCr = amountCr.add(inAmount);
			}
			// ���Z
			amountCr = amountCr.negate();
			amount = amountDr.add(amountCr);

			taxCr = taxCr.negate();
			taxAmountSum = taxDr.add(taxCr);

			param.setAmount(amount); // �Ώۋ��z
			amount = calculator.calculateTax(param);

			taxAmountSum = amount.subtract(taxAmountSum);

			if ((taxAmountSum.signum() == -1 && inAmount.signum() >= 0)
				|| (taxAmountSum.signum() >= 0 && inAmount.signum() == -1)) {

				if (isDr) {
					// �ؕ��F�l�����͋��z�ƈقȂ鐳���̏ꍇ�[��
					taxAmountSum = BigDecimal.ZERO;
				} else {
					// �ݕ��F�l�����͋��z�ƈقȂ鐳���̏ꍇ�������t�ɂ���
					taxAmountSum = taxAmountSum.negate();
				}

			}

			panel.ctrlTaxAmount.setNumberValue(taxAmountSum);

		} else {
			// �I���s�ȊO�ɍ��v���閾�ׂȂ��ꍇ
			panel.ctrlTaxAmount.setNumberValue(calculator.calculateTax(param));
		}
	}

	/**
	 * invoice�p �d����擾
	 * 
	 * @param dtl
	 * @return �d����z
	 */
	protected BigDecimal getSwkKin(SWK_DTL dtl) {
		return DecimalUtil.avoidNull(dtl.getSWK_IN_KIN());
	}

	/**
	 * invoice�p ���׃e�[�u���̎擾
	 * 
	 * @return tbl
	 */
	protected TTable getTable() {
		return panel.tbl;
	}

	/**
	 * invoice�p ����Ōv�Z���閾�ׂ����f
	 * 
	 * @param row
	 * @param selectRow
	 * @param dtl
	 * @return true continue����
	 */
	protected boolean isInvoiceTaxSum(int row, int selectRow, SWK_DTL dtl) {

		if (row == selectRow || Util.isNullOrEmpty(dtl.getSWK_ZEI_CODE())
			|| DecimalUtil.isNullOrZero(dtl.getSWK_ZEI_RATE()) || Util.isNullOrEmpty(dtl.getSWK_CUR_CODE())) {
			// �I���s �܂��� �ŃR�[�h�A�Ń��[�g�A�ʉ݃R�[�h��null�̏ꍇ �X���[
			return true;
		}
		return false;
	}

	/**
	 * invoice�p ���ׂ�DrCr�擾
	 * 
	 * @param dtl
	 * @return dtl
	 */
	protected boolean isCredit(SWK_DTL dtl) {
		return !dtl.isDR();
	}

}
