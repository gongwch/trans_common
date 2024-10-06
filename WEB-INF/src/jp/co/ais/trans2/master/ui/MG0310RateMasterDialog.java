package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * �ʉ݃��[�g�}�X�^�̕ҏW���
 * 
 * @author AIS
 */
public class MG0310RateMasterDialog extends TDialog {

	/** serialVersionUID */
	private static final long serialVersionUID = -6136083150579331779L;

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** ���[�g�敪 */
	public TLabelComboBox cboRateType;

	/** �ʉ� */
	public TCurrencyReference ctrlCurrency;

	/** �K�p�J�n���t */
	public TLabelPopupCalendar dtBeginDate;

	/** ���[�g */
	public TLabelNumericField ctrlRate;

	/**
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0310RateMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		cboRateType = new TLabelComboBox();
		ctrlCurrency = new TCurrencyReference();
		dtBeginDate = new TLabelPopupCalendar();
		ctrlRate = new TLabelNumericField();
	}

	@Override
	public void allocateComponents() {

		setSize(400, 200);

		// �m��{�^��
		int x = 160 - HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSettle);

		// �߂�{�^��
		x = 270;
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x, HEADER_Y);
		pnlHeader.add(btnClose);

		pnlBody.setLayout(null);

		// ���[�g�敪
		cboRateType.setLabelSize(90);
		cboRateType.setComboSize(200);
		cboRateType.setSize(295, 20);
		cboRateType.setLocation(10, 10);
		cboRateType.setLangMessageID("C00883");
		cboRateType.getComboBox().addTextValueItem(0, getShortWord("C11167"));// �В背�[�g
		cboRateType.getComboBox().addTextValueItem(1, getShortWord("C02820"));// ���Z�����[�g
		cboRateType.getComboBox().addTextValueItem(2, getShortWord("C11216"));// �В背�[�g�i�@�\�ʉ݁j
		cboRateType.getComboBox().addTextValueItem(3, getShortWord("C11217"));// ���Z�����[�g�i�@�\�ʉ݁j
		pnlBody.add(cboRateType);

		// �ʉ�
		ctrlCurrency.setLocation(25, 35);
		pnlBody.add(ctrlCurrency);

		// �K�p�J�n��
		dtBeginDate.setLabelSize(90);
		dtBeginDate.setSize(110 + dtBeginDate.getCalendarSize() + 5, 20);
		dtBeginDate.setLocation(0, 60);
		dtBeginDate.setLangMessageID("C03741");
		pnlBody.add(dtBeginDate);

		// ���[�g
		ctrlRate.setLabelSize(90);
		ctrlRate.setFieldSize(100);
		ctrlRate.setSize(195, 20);
		ctrlRate.setLocation(10, 85);
		ctrlRate.setPositiveOnly(true);
		ctrlRate.setDecimalPoint(4);
		ctrlRate.setNumericFormat("#,##0.0000");
		ctrlRate.setLabelText(getWord("C00556"));
		pnlBody.add(ctrlRate);
	}

	@Override
	public void setTabIndex() {
		int i = 1;
		cboRateType.setTabControlNo(i++);
		ctrlCurrency.setTabControlNo(i++);
		dtBeginDate.setTabControlNo(i++);
		ctrlRate.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}
