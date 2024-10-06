package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.awt.event.*;
import java.math.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.balance.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.item.ItemSearchCondition.SlipInputType;
import jp.co.ais.trans2.model.slip.*;

/**
 * �d�󖾍׃R���g���[��
 * 
 * @author AIS
 */
public class TFormSlipDetailPanelCtrl extends TSlipDetailPanelCtrl {

	/** �I�������s�ԍ� */
	protected int selectRowIndex;

	/** �d�󖾍ד��̓p�l�����X�g */
	protected List<TFormDCInputPanel> dcList;

	/** �ʉ݃��X�g */
	protected List<Currency> currencyList = null;

	/** �w�b�_�̒ʉ� */
	protected Currency headerCurrency;

	/** �f�t�H���g������ */
	protected String dataType;

	/** ������ */
	protected Date occurDate;

	/** �`�[���̓^�C�v */
	protected SlipInputType slipInputType = null;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param panel �p�l��
	 */
	protected TFormSlipDetailPanelCtrl(TFormSlipDetailPanel panel) {
		super(panel);
	}

	@Override
	public TFormSlipDetailPanel getView() {
		return (TFormSlipDetailPanel) panel;
	}

	/**
	 * ��ʕ\����������
	 */
	@Override
	protected void initView() {

		panel.ctrlFcDrTotal.setEditable(false);
		panel.ctrlDrTotal.setEditable(false);
		panel.ctrlFcDiff.setEditable(false);
		panel.ctrlDiff.setEditable(false);
		panel.ctrlFcCrTotal.setEditable(false);
		panel.ctrlCrTotal.setEditable(false);

		panel.cbxCurrencyOnTotal.setEnabled(true);

		panel.ctrlDrTotal.setDecimalPoint(keyDigit);
		panel.ctrlDiff.setDecimalPoint(keyDigit);
		panel.ctrlCrTotal.setDecimalPoint(keyDigit);
	}

	/**
	 * �d�󖾍ד��̓p�l���̃C�x���g�o�^
	 */
	@Override
	protected void addViewEvent() {

		// �X�v���b�h�ŏ�ʍs�ړ�
		panel.btnRowTop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doRowTop();
			}
		});

		// �X�v���b�h��s�ړ�
		panel.btnRowUp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doRowUp();
			}
		});

		// �X�v���b�h���s�ړ��{�^��
		panel.btnRowDown.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doRowDown();
			}
		});

		// �X�v���b�h�ŉ��ʍs�ړ��{�^��
		panel.btnRowUnder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doRowUnder();
			}
		});

		// ���v���̒ʉ݃R���{�{�b�N�X
		panel.cbxCurrencyOnTotal.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				changedCurrencyOnTotal();
			}
		});

		// �������{�^��
		panel.btnAP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doAPErasing();
			}
		});

		// �������{�^��
		panel.btnAR.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doARErasing();
			}
		});

		// BS�������
		if (isUseBS) {
			panel.btnBS.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					doBS();
				}
			});
		}
	}

	/**
	 * �C�x���g�o�^
	 * 
	 * @param dcPane �d�󖾍ד��̓p�l��
	 */
	protected void addDcEvent(final TFormDCInputPanel dcPane) {

		// ���v�v�Z
		dcPane.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {

				// ���v�v�Z
				summary();
			}
		});

		// �s����
		dcPane.btnRowCopy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doCopy(dcList.indexOf(dcPane));
			}
		});

		// ���]����
		dcPane.btnRowCopyReverse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doCopyReverse(dcList.indexOf(dcPane));
			}
		});

		// �s�ǉ�
		dcPane.btnRowNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doNew(dcList.indexOf(dcPane));
			}
		});

		// �s�폜
		dcPane.btnRowDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doDelete(dcList.indexOf(dcPane));
			}
		});

		// �s�ړ��w��
		dcPane.ctrlMoveCheck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doMoveCheck(dcList.indexOf(dcPane));
			}
		});

		dcPane.ctrlItemDebit.ctrlItemReference.code.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				setScrollPosition(dcList.indexOf(dcPane), false);
			}
		});

		dcPane.ctrlItemCredit.ctrlItemReference.code.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				setScrollPosition(dcList.indexOf(dcPane), false);
			}
		});

		dcPane.ctrlMoveCheck.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				setScrollPosition(dcList.indexOf(dcPane), false);
			}
		});
	}

	/**
	 * �����\��
	 */
	@Override
	public void init() {
		dcList = new ArrayList<TFormDCInputPanel>();
	}

	/**
	 * �����\��
	 * 
	 * @param detailCount
	 */
	public void init(int detailCount) {
		dcList = new ArrayList<TFormDCInputPanel>();

		// ���ׂ̏����\��:2�s
		for (int i = 0; i < detailCount; i++) {
			TFormDCInputPanel dcPane = createDcPane();
			dcList.add(dcPane);
			addDcEvent(dcPane);
		}

		// ���ׂ��o�C���h����
		bindList();

		selectRowIndex = -1;

		// �s�ړ��w��{�^���̏����\��
		panel.btnRowTop.setEnabled(false);
		panel.btnRowUp.setEnabled(false);
		panel.btnRowDown.setEnabled(false);
		panel.btnRowUnder.setEnabled(false);

		// ���z���v���̏����\��
		panel.ctrlFcDrTotal.clear();
		panel.ctrlDrTotal.setNumberValue(BigDecimal.ZERO);
		panel.ctrlFcDiff.clear();
		panel.ctrlDiff.setNumberValue(BigDecimal.ZERO);
		panel.ctrlFcCrTotal.clear();
		panel.ctrlCrTotal.setNumberValue(BigDecimal.ZERO);
		panel.cbxCurrencyOnTotal.removeAllItems();

		// ���v�l�Čv�Z
		summary();

		// Tab����ύX
		changedTabIndex();
	}

	/**
	 * ���ׂ��o�C���h����
	 */
	protected void bindList() {

		// �s�ԍ����Z�b�g
		refreshRowNo();

		getView().pnlDetail.removeAll();

		int x = getView().X_POINT;

		int y = getView().Y_POINT;

		for (TFormDCInputPanel dcPane : dcList) {

			dcPane.setLocation(x, y);
			getView().pnlDetail.add(dcPane);

			x = dcPane.getX();
			y = dcPane.getY() + dcPane.getHeight() + getView().MARGIN_Y;
		}

		int size = getView().pnlDetail.getComponentCount();
		int width = 1003;
		int height = dcList.get(0).getHeight() * size;
		height += getView().MARGIN_Y * size;

		TGuiUtil.setComponentSize(getView().pnlDetail, width, height);

		getView().pnlDetail.repaint();
	}

	/**
	 * ���׍ŏ�ʍs�ړ�
	 */
	protected void doRowTop() {

		TFormDCInputPanel dcPane = dcList.remove(selectRowIndex);
		selectRowIndex = 0;
		dcList.add(selectRowIndex, dcPane);

		// ���ׂ��o�C���h����
		bindList();

		// �s�ړ��{�^���̐�����s��
		setMoveButtonState();

		if (!panel.btnRowTop.isEnabled()) {

			panel.btnRowDown.requestFocus();
		}

		// Tab����ύX
		changedTabIndex();

		// �X�N���[���̈ʒu��ݒ�
		setScrollPosition(selectRowIndex, false);
	}

	/**
	 * ���׏�s�ړ�
	 */
	protected void doRowUp() {

		TFormDCInputPanel dcPane = dcList.remove(selectRowIndex);
		selectRowIndex--;
		dcList.add(selectRowIndex, dcPane);

		// ���ׂ��o�C���h����
		bindList();

		// �s�ړ��{�^���̐�����s��
		setMoveButtonState();

		// Tab����ύX
		changedTabIndex();

		// �X�N���[���̈ʒu��ݒ�
		setScrollPosition(selectRowIndex, false);
	}

	/**
	 * ���׉��s�ړ�
	 */
	protected void doRowDown() {

		TFormDCInputPanel dcPane = dcList.remove(selectRowIndex);
		selectRowIndex++;
		dcList.add(selectRowIndex, dcPane);

		// ���ׂ��o�C���h����
		bindList();

		// �s�ړ��{�^���̐�����s��
		setMoveButtonState();

		if (!panel.btnRowUnder.isEnabled()) {
			panel.cbxCurrencyOnTotal.requestFocus();
		}

		// Tab����ύX
		changedTabIndex();

		// �X�N���[���̈ʒu��ݒ�
		setScrollPosition(selectRowIndex, false);
	}

	/**
	 * ���׍ŉ��ʍs�ړ�
	 */
	protected void doRowUnder() {

		TFormDCInputPanel dcPane = dcList.remove(selectRowIndex);
		selectRowIndex = dcList.size();
		dcList.add(selectRowIndex, dcPane);

		// ���ׂ��o�C���h����
		bindList();

		// �s�ړ��{�^���̐�����s��
		setMoveButtonState();

		// Tab����ύX
		changedTabIndex();

		// �X�N���[���̈ʒu��ݒ�
		setScrollPosition(selectRowIndex, false);
	}

	/**
	 * ���v���̒ʉݕύX
	 */
	@Override
	protected void changedCurrencyOnTotal() {

		summaryInAmount();
	}

	/**
	 * �s����
	 * 
	 * @param rowIndex �s�ԍ�
	 */
	protected void doCopy(int rowIndex) {

		TFormDCInputPanel dcPane = createDcPane();

		SWK_DTL dtl = dcList.get(rowIndex).getEntity();

		if (dtl != null) {

			// �N���[��
			dtl = dtl.clone();

			// AR,AP,BS���N���A
			dtl.setAR_ZAN(null);
			dtl.setAP_ZAN(null);
			dtl.setBsDetail(null);
		}

		dcPane.setEntity(dtl);

		addDcEvent(dcPane);

		dcList.add(rowIndex + 1, dcPane);

		// ���ׂ��o�C���h����
		bindList();

		if (rowIndex < selectRowIndex) {

			selectRowIndex++;
		}

		// �s�ړ��{�^���̐�����s��
		setMoveButtonState();

		// Tab����ύX
		changedTabIndex();

		// ���v�l�Čv�Z
		summary();

		// �X�N���[���̈ʒu��ݒ�
		setScrollPosition(rowIndex + 1, true);
	}

	/**
	 * ���]����
	 * 
	 * @param rowIndex �s�ԍ�
	 */
	protected void doCopyReverse(int rowIndex) {

		TFormDCInputPanel dcPane = createDcPane();

		SWK_DTL dtl = dcList.get(rowIndex).getEntity();

		if (dtl != null) {

			// �N���[��
			dtl = dtl.clone();

			dtl.setDC(dtl.isDR() ? Dc.CREDIT : Dc.DEBIT);

			// AR,AP,BS���N���A
			dtl.setAR_ZAN(null);
			dtl.setAP_ZAN(null);
			dtl.setBsDetail(null);
		}

		dcPane.setEntity(dtl);

		addDcEvent(dcPane);

		dcList.add(rowIndex + 1, dcPane);

		// ���ׂ��o�C���h����
		bindList();

		if (rowIndex < selectRowIndex) {

			selectRowIndex++;
		}

		// �s�ړ��{�^���̐�����s��
		setMoveButtonState();

		// Tab����ύX
		changedTabIndex();

		// ���v�l�Čv�Z
		summary();

		// �X�N���[���̈ʒu��ݒ�
		setScrollPosition(rowIndex + 1, true);
	}

	/**
	 * �s�ǉ�
	 * 
	 * @param rowIndex �s�ԍ�
	 */
	protected void doNew(int rowIndex) {

		TFormDCInputPanel dcPane = createDcPane();
		addDcEvent(dcPane);

		dcList.add(rowIndex + 1, dcPane);

		// ���ׂ��o�C���h����
		bindList();

		if (rowIndex < selectRowIndex) {

			selectRowIndex++;
		}

		// �s�ړ��{�^���̐�����s��
		setMoveButtonState();

		// Tab����ύX
		changedTabIndex();

		// ���v�l�Čv�Z
		summary();

		// �X�N���[���̈ʒu��ݒ�
		setScrollPosition(rowIndex + 1, true);
	}

	/**
	 * �s�폜
	 * 
	 * @param rowIndex �s�ԍ�
	 */
	protected void doDelete(int rowIndex) {

		if (!showConfirmMessage("�s�̍폜���s���܂���?")) {
			return;
		}

		SWK_DTL bean = dcList.get(rowIndex).getEntity();

		if (bean != null) {
			// �s�r������
			unlock(bean);
		}

		if (getView().pnlDetail.getComponentCount() == 1) {
			dcList.get(rowIndex).init();
		} else {
			dcList.remove(rowIndex);
		}

		// ���ׂ��o�C���h����
		bindList();

		if (rowIndex < selectRowIndex) {

			selectRowIndex--;

		} else if (rowIndex == selectRowIndex) {
			selectRowIndex = -1;
		}

		// �s�ړ��{�^���̐�����s��
		setMoveButtonState();

		// Tab����ύX
		changedTabIndex();

		// ���v�l�Čv�Z
		summary();
	}

	/**
	 * �s�ړ��w��
	 * 
	 * @param rowIndex �s�ԍ�
	 */
	protected void doMoveCheck(int rowIndex) {

		TFormDCInputPanel dcPane = (TFormDCInputPanel) getView().pnlDetail.getComponent(rowIndex);

		if (dcPane.ctrlMoveCheck.isSelected()) {

			selectRowIndex = rowIndex;

			for (int i = 0; i < getView().pnlDetail.getComponentCount(); i++) {

				if (i != rowIndex) {

					dcPane = (TFormDCInputPanel) getView().pnlDetail.getComponent(i);
					dcPane.ctrlMoveCheck.setSelected(false);
				}
			}

		} else {

			selectRowIndex = -1;
		}

		// �s�ړ��{�^���̐�����s��
		setMoveButtonState();

		// Tab����ύX
		changedTabIndex();
	}

	/**
	 * �s�ړ��{�^���̐�����s��
	 */
	protected void setMoveButtonState() {
		int count = getView().pnlDetail.getComponentCount();

		panel.btnRowTop.setEnabled(count != 0 && selectRowIndex >= 0 && selectRowIndex != 0);
		panel.btnRowUp.setEnabled(count != 0 && selectRowIndex >= 0 && selectRowIndex != 0);
		panel.btnRowDown.setEnabled(count != 0 && selectRowIndex >= 0 && selectRowIndex != count - 1);
		panel.btnRowUnder.setEnabled(count != 0 && selectRowIndex >= 0 && selectRowIndex != count - 1);
	}

	/**
	 * �ʉݐݒ�
	 * 
	 * @param currency �ʉ݃R�[�h
	 */
	@Override
	protected void setCurrecy(Currency currency) {
		headerCurrency = currency;

		for (TFormDCInputPanel dcPane : dcList) {

			dcPane.setCurrecy(currency);
		}

		// ���v�l�Čv�Z
		summary();
	}

	/**
	 * ����ݒ�
	 * 
	 * @param baseDate ���
	 */
	@Override
	public void setBaseDate(Date baseDate) {
		this.baseDate = baseDate;

		for (TFormDCInputPanel dcPane : dcList) {
			dcPane.setBaseDate(baseDate);
		}
	}

	/**
	 * ���Z�d��ݒ�
	 * 
	 * @param isClosing true:���Z�d��Afalse:�ʏ�d��
	 */
	@Override
	public void setClosingEntry(boolean isClosing) {
		this.isClosing = isClosing;

		for (TFormDCInputPanel dcPane : dcList) {
			dcPane.setClosingEntry(isClosing);
		}

		// ���v�l�Čv�Z
		summary();
	}

	/**
	 * �O���w�������ݒ�(�Œ�\���p)
	 * 
	 * @param customer �O���w������
	 */
	@Override
	public void setCustomer(Customer customer) {
		this.customer = customer;

		for (TFormDCInputPanel dcPane : dcList) {
			dcPane.setCustomer(customer);
		}
	}

	/**
	 * �O���w��Ј���ݒ�(�����\���p)
	 * 
	 * @param employee �Ј�
	 */
	@Override
	public void setEmployee(Employee employee) {
		this.employee = employee;

		for (TFormDCInputPanel dcPane : dcList) {
			dcPane.setEmployee(employee);
		}
	}

	/**
	 * @see jp.co.ais.trans2.common.model.ui.slip.TSlipDetailPanel#setDefaultDC(jp.co.ais.trans2.define.Dc)
	 */
	@Override
	public void setDefaultDC(Dc dc) {
		// �����Ȃ�
	}

	/**
	 * �f�t�H���g�����ł�
	 * 
	 * @param defaultTaxInnerDivision true:����
	 */
	@Override
	public void setDefaultTaxInnerDivision(boolean defaultTaxInnerDivision) {
		this.defaultTaxInnerDivision = defaultTaxInnerDivision;

		for (TFormDCInputPanel dcPane : dcList) {
			dcPane.setDefaultTaxInnerDivision(defaultTaxInnerDivision);
		}

		// ���v�l�Čv�Z
		summary();
	}

	/**
	 * �f�t�H���g���f�[�^�敪��
	 * 
	 * @param dataType �f�[�^�敪
	 */
	protected void setDataType(String dataType) {
		this.dataType = dataType;

		for (TFormDCInputPanel dcPane : dcList) {
			dcPane.ctrlRemark.getSearchCondition().setDataType(dataType);
		}
	}

	/**
	 * ���ׂ�Entity�`���ŃZ�b�g����.
	 * 
	 * @param details �f�[�^
	 */
	@Override
	public void setEntityList(List<SWK_DTL> details) {
		dcList = new ArrayList<TFormDCInputPanel>();

		for (SWK_DTL dtl : details) {

			TFormDCInputPanel dcPane = createDcPane();
			dcList.add(dcPane);
			addDcEvent(dcPane);

			dcPane.setEntity(dtl);
		}

		if (dcList.size() == 0) {
			TFormDCInputPanel dcPane = createDcPane();
			dcList.add(dcPane);
			addDcEvent(dcPane);
		}

		// ���ׂ��o�C���h����
		bindList();

		selectRowIndex = -1;

		// Tab����ύX
		changedTabIndex();

		// ���v�l�Čv�Z
		summary();

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				getView().scrollPane.getVerticalScrollBar().setValue(0);
			}
		});
	}

	/**
	 * ���ׂ�Entity�`���Ŏ擾����
	 * 
	 * @return SWK_DTL���X�g
	 */
	@Override
	public List<SWK_DTL> getEntityList() {

		List<SWK_DTL> list = new ArrayList<SWK_DTL>();

		for (TFormDCInputPanel dcPane : dcList) {

			SWK_DTL dtl = dcPane.getEntity();

			if (dtl == null) {
				continue;
			}

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
	 * ���ׂ�Entity�`���Ŏ擾����
	 * 
	 * @return SWK_DTL���X�g
	 */
	public int getDetailRowCount() {
		return getEntityList().size();
	}

	/**
	 * ���̓`�F�b�N
	 * 
	 * @return false:NG
	 */
	@Override
	protected boolean checkInput() {

		for (int i = 0; i < dcList.size(); i++) {
			if (!dcList.get(i).checkInput()) {
				setScrollPosition(i, false);
				return false;
			}
		}
		return true;
	}

	/**
	 * ���v���̒ʉ݃R���{�{�b�N�X�\�z
	 */
	@Override
	protected void makeCurrencyComboBox() {

		// �I�𒆂̒ʉ�(���v��)
		Currency selectedCurrency = (Currency) panel.cbxCurrencyOnTotal.getSelectedItemValue();

		Set<Currency> list = new LinkedHashSet<Currency>();

		// �w�b�_�̒ʉ�
		if (outerDetail != null) {
			list.add(outerDetail.getCurrency());
		}

		// ���ׂ̒ʉ�
		for (TFormDCInputPanel dcPane : dcList) {

			list.add(dcPane.getDebitCurrency());
			list.add(dcPane.getCreditCurrency());
		}

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
	 * ���v�l�Čv�Z
	 */
	@Override
	protected void summary() {

		BigDecimal dr = BigDecimal.ZERO;
		BigDecimal cr = BigDecimal.ZERO;
		BigDecimal drTax = BigDecimal.ZERO;
		BigDecimal crTax = BigDecimal.ZERO;

		for (TFormDCInputPanel dcPane : dcList) {

			SWK_DTL dtl = dcPane.getEntity();

			if (dtl == null) {
				continue;
			}

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

		// ���v��
		makeCurrencyComboBox();

		// �O�ݍ��v
		summaryInAmount();

	}

	/**
	 * �O�ݍ��v(summary()�Ă�ł���ĂԑO��)
	 */
	@Override
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
	@Override
	protected Map<String, BigDecimal[]> getForeignAmountMap() {

		String keyCode = keyCurrency.getCode();

		Map<String, BigDecimal[]> map = new TreeMap<String, BigDecimal[]>();

		for (TFormDCInputPanel dcPane : dcList) {

			SWK_DTL dtl = dcPane.getEntity();

			if (dtl == null) {
				continue;
			}

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
	 * Tab����ύX
	 */
	protected void changedTabIndex() {

		int tabControlNo = getView().firstTabIndex;

		for (TFormDCInputPanel pnl : dcList) {
			tabControlNo = pnl.setTabControlNo(tabControlNo++);
		}

		// �p�l���ēx������
		getView().parent.resetPanelTabIndex();
	}

	/**
	 * �d�󖾍ד��̓p�l�����쐬����
	 * 
	 * @return �d�󖾍ד��̓p�l��
	 */
	protected TFormDCInputPanel createDcPane() {

		TFormDCInputPanel dcPane = createTFormDCInputPanel();

		dcPane.setBaseDate(baseDate);
		dcPane.setDefaultTaxInnerDivision(defaultTaxInnerDivision);
		dcPane.setClosingEntry(isClosing);

		dcPane.setCurrecyList(currencyList);

		dcPane.ctrlItemDebit.getItemSearchCondition().setSlipInputType(slipInputType);
		dcPane.ctrlItemDebit.getSubItemSearchCondition().setSlipInputType(slipInputType);
		dcPane.ctrlItemDebit.getDetailItemSearchCondition().setSlipInputType(slipInputType);

		dcPane.ctrlItemCredit.getItemSearchCondition().setSlipInputType(slipInputType);
		dcPane.ctrlItemCredit.getSubItemSearchCondition().setSlipInputType(slipInputType);
		dcPane.ctrlItemCredit.getDetailItemSearchCondition().setSlipInputType(slipInputType);

		dcPane.ctrlRemark.getSearchCondition().setDataType(dataType);

		return dcPane;
	}

	/**
	 * @return T-Form ����Panel
	 */
	protected TFormDCInputPanel createTFormDCInputPanel() {
		return new TFormDCInputPanel();
	}

	/**
	 * �X�N���[���̈ʒu��ݒ�
	 * 
	 * @param rowIndex �s�ԍ�
	 * @param isAddRow �s�𑝉����܂���
	 */
	protected void setScrollPosition(int rowIndex, boolean isAddRow) {

		JScrollBar bar = getView().scrollPane.getVerticalScrollBar();

		int eachSize = 0;

		if (isAddRow) {
			eachSize = bar.getMaximum() / (dcList.size() - 1);
			bar.setMaximum(bar.getMaximum() + eachSize);

		} else {
			eachSize = bar.getMaximum() / dcList.size();
		}

		int barValue = bar.getValue();

		// �p�l���̕\���͈�
		int startY = bar.getValue();
		int endY = startY + 4 * eachSize;

		int currentMinY = dcList.get(rowIndex).getY() - getView().Y_POINT;
		int currentMaxY = currentMinY + eachSize;

		if (currentMinY < startY) {
			barValue = rowIndex * eachSize;

		} else if (currentMaxY > endY) {
			barValue = (rowIndex - 3) * eachSize;
		}

		if (barValue < 0) {
			bar.setValue(0);

		} else if (barValue > bar.getMaximum()) {
			bar.setValue(bar.getMaximum());

		} else {
			bar.setValue(barValue);
		}
	}

	/**
	 * �`�[���̓^�C�v
	 * 
	 * @param slipInputType SlipInputType
	 */
	public void setSlipInputType(SlipInputType slipInputType) {
		this.slipInputType = slipInputType;

		// �`�[��ʐݒ�
		for (TFormDCInputPanel pnl : dcList) {
			pnl.ctrlItemDebit.getItemSearchCondition().setSlipInputType(slipInputType);
			pnl.ctrlItemDebit.getSubItemSearchCondition().setSlipInputType(slipInputType);
			pnl.ctrlItemDebit.getDetailItemSearchCondition().setSlipInputType(slipInputType);

			pnl.ctrlItemCredit.getItemSearchCondition().setSlipInputType(slipInputType);
			pnl.ctrlItemCredit.getSubItemSearchCondition().setSlipInputType(slipInputType);
			pnl.ctrlItemCredit.getDetailItemSearchCondition().setSlipInputType(slipInputType);

			pnl.ctrlRemark.getSearchCondition().setDataType(dataType);
		}
	}

	/**
	 * ���׃f�[�^�̒ʉ݃��X�g�쐬
	 * 
	 * @param list �ʉ݃��X�g
	 */
	protected void setCurrecyList(List<Currency> list) {
		currencyList = list;

		for (TFormDCInputPanel dcPane : dcList) {
			dcPane.setCurrecyList(list);
		}
	}

	/**
	 * �s�ԍ��Đݒ�
	 */
	protected void refreshRowNo() {
		int i = 1;

		for (TFormDCInputPanel dcPane : dcList) {
			dcPane.setRowNo(i++);
		}
	}

	/**
	 * AP�c������
	 */
	@Override
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
			int lastIndex = dcList.size() - 1;
			doNew(lastIndex);

			dcList.get(lastIndex + 1).setEntity(toDetail(zan));
		}

		// ���v�̒ʉ�
		makeCurrencyComboBox();

		summary();
	}

	/**
	 * AR�c������
	 */
	@Override
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
			int lastIndex = dcList.size() - 1;
			doNew(lastIndex);

			dcList.get(lastIndex + 1).setEntity(toDetail(zan));
		}

		// ���v�̒ʉ�
		makeCurrencyComboBox();

		summary();
	}

	/**
	 * BS����{�^������
	 */
	@Override
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
			int lastIndex = dcList.size() - 1;
			doNew(lastIndex);

			dcList.get(lastIndex + 1).setEntity(toDetail(bs));
		}

		// ���v�̒ʉ�
		makeCurrencyComboBox();

		summary();
	}
}
