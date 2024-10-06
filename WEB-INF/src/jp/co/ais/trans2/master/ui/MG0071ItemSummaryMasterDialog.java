package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.item.summary.*;

/**
 * MG0070ItemSummaryMaster - �ȖڏW�v�}�X�^ - Dialog Class
 * 
 * @author AIS
 */
public class MG0071ItemSummaryMasterDialog extends TDialog {

	/** �c���Œ�l */
	protected final int BUTTON_HEIGHT = 25;

	/** �����Œ�l */
	protected final int BUTTON_WIDTH = 110;

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** �Ȗڑ̌n�R�[�h */
	public NBReference ctrlKmtCode;

	/** �ȖڃR�[�h */
	public NBReference ctrlKmkCode;

	/** ���\�Ȗږ��� */
	public TLabelField ctrlKokName;

	/** �W�v�����ȖڃR�[�h */
	public NBReference ctrlSumCode;

	/** �Ȗڏo�͏��� */
	public TLabelNumericField ctrlOdr;

	/** �\���敪�p�l�� */
	public TPanel pnlHyjKbn;

	/** �\���敪:�O���[�v */
	public ButtonGroup ctrlHyjKbnGrp;

	/** �\���敪:�\�� */
	public TRadioButton ctrlHyjKbnVisible;

	/** �\���敪:��\�� */
	public TRadioButton ctrlHyjKbnInvisible;

	/** �J�n�N���� */
	public TLabelPopupCalendar ctrlBeginDate;

	/** �I���N���� */
	public TLabelPopupCalendar ctrlEndDate;

	/** �ȖڏW�v��� */
	public ItemSummary itemSummary = null;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0071ItemSummaryMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlKmtCode = new NBReference();
		ctrlKmkCode = new NBReference();
		ctrlKokName = new TLabelField();
		ctrlSumCode = new NBReference();
		ctrlOdr = new TLabelNumericField();
		pnlHyjKbn = new TPanel();
		ctrlHyjKbnGrp = new ButtonGroup();
		ctrlHyjKbnVisible = new TRadioButton();
		ctrlHyjKbnInvisible = new TRadioButton();
		ctrlBeginDate = new TLabelPopupCalendar();
		ctrlEndDate = new TLabelPopupCalendar();
	}

	@Override
	public void allocateComponents() {

		setSize(500, 380);

		pnlHeader.setLayout(null);

		// �m��{�^����`
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(BUTTON_HEIGHT, BUTTON_WIDTH);
		btnSettle.setLocation(getWidth() - 245, HEADER_Y);
		pnlHeader.add(btnSettle);

		// �߂�{�^��
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(BUTTON_HEIGHT, BUTTON_WIDTH);
		btnClose.setLocation(getWidth() - 130, HEADER_Y);
		pnlHeader.add(btnClose);

		// Body������
		pnlBody.setLayout(null);

		// �Ȗڑ̌n
		ctrlKmtCode.lbl.setLangMessageID("C00609");
		ctrlKmtCode.setSize(450, 20);
		ctrlKmtCode.code.setAlignmentX(0);
		ctrlKmtCode.name.setAlignmentX(0);
		ctrlKmtCode.code.setMaximumSize(new Dimension(50, 20));
		ctrlKmtCode.code.setMinimumSize(new Dimension(50, 20));
		ctrlKmtCode.code.setPreferredSize(new Dimension(50, 20));
		ctrlKmtCode.name.setMaximumSize(new Dimension(280, 20));
		ctrlKmtCode.name.setMinimumSize(new Dimension(280, 20));
		ctrlKmtCode.name.setPreferredSize(new Dimension(280, 20));
		ctrlKmtCode.setLocation(33, 20);
		pnlBody.add(ctrlKmtCode);

		// �Ȗ�
		ctrlKmkCode.lbl.setLangMessageID("C00077");
		ctrlKmkCode.setSize(450, 20);
		ctrlKmkCode.code.setMaximumSize(new Dimension(100, 20));
		ctrlKmkCode.code.setMinimumSize(new Dimension(100, 20));
		ctrlKmkCode.code.setPreferredSize(new Dimension(100, 20));
		ctrlKmkCode.name.setMaximumSize(new Dimension(230, 20));
		ctrlKmkCode.name.setMinimumSize(new Dimension(230, 20));
		ctrlKmkCode.name.setPreferredSize(new Dimension(230, 20));
		ctrlKmkCode.setLocation(33, 50);
		pnlBody.add(ctrlKmkCode);

		// ���\�Ȗږ���
		ctrlKokName.setSize(460, 20);
		ctrlKokName.setLangMessageID("C00624");
		ctrlKokName.setLabelSize(130);
		ctrlKokName.setFieldSize(330);
		ctrlKokName.setMaxLength(40);
		ctrlKokName.setLocation(0, 80);
		pnlBody.add(ctrlKokName);

		// �W�v�����Ȗ�
		ctrlSumCode.lbl.setLangMessageID("C00625");
		ctrlSumCode.setSize(455, 20);
		ctrlSumCode.lbl.setMaximumSize(new Dimension(120, 20));
		ctrlSumCode.lbl.setMinimumSize(new Dimension(120, 20));
		ctrlSumCode.lbl.setPreferredSize(new Dimension(120, 20));
		ctrlSumCode.code.setMaximumSize(new Dimension(100, 20));
		ctrlSumCode.code.setMinimumSize(new Dimension(100, 20));
		ctrlSumCode.code.setPreferredSize(new Dimension(100, 20));
		ctrlSumCode.name.setMaximumSize(new Dimension(230, 20));
		ctrlSumCode.name.setMinimumSize(new Dimension(230, 20));
		ctrlSumCode.name.setPreferredSize(new Dimension(230, 20));
		ctrlSumCode.setLocation(10, 110);
		pnlBody.add(ctrlSumCode);

		// �o�͏���
		ctrlOdr.setSize(450, 20);
		ctrlOdr.setLangMessageID("C00776");
		ctrlOdr.setLabelSize(120);
		ctrlOdr.setFieldSize(100);
		ctrlOdr.setMaxLength(10);
		ctrlOdr.setPositiveOnly(true);
		ctrlOdr.setNumericFormat("#,###,###,###");
		ctrlOdr.setLocation(10, 140);
		pnlBody.add(ctrlOdr);

		// �\���敪�p�l��
		pnlHyjKbn.setLayout(null);
		pnlHyjKbn.setSize(210, 50);
		pnlHyjKbn.setLangMessageID("C01300");
		pnlHyjKbn.setLocation(140, 170);
		pnlBody.add(pnlHyjKbn);

		// �\���敪:�\��
		ctrlHyjKbnVisible.setSize(60, 15);
		ctrlHyjKbnVisible.setLangMessageID("C00432");
		ctrlHyjKbnVisible.setIndex(1);
		ctrlHyjKbnVisible.setLocation(30, 20);
		pnlHyjKbn.add(ctrlHyjKbnVisible);

		// �\���敪:��\��
		ctrlHyjKbnInvisible.setSize(80, 15);
		ctrlHyjKbnInvisible.setLangMessageID("C01297");
		ctrlHyjKbnInvisible.setIndex(0);
		ctrlHyjKbnInvisible.setLocation(100, 20);
		pnlHyjKbn.add(ctrlHyjKbnInvisible);

		// Radio Button Group
		ctrlHyjKbnGrp.add(ctrlHyjKbnVisible);
		ctrlHyjKbnGrp.add(ctrlHyjKbnInvisible);
		ctrlHyjKbnGrp.setSelected(ctrlHyjKbnVisible.getModel(), true);

		// �J�n�N����
		ctrlBeginDate.setSize(450, 20);
		ctrlBeginDate.setLabelSize(120);
		ctrlBeginDate.setLangMessageID("C00055");
		ctrlBeginDate.setValue(ctrlBeginDate.getCalendar().getMinimumDate());
		ctrlBeginDate.setLocation(15, 230);
		pnlBody.add(ctrlBeginDate);

		// �I���N����
		ctrlEndDate.setSize(450, 20);
		ctrlEndDate.setLabelSize(120);
		ctrlEndDate.setLangMessageID("C00261");
		ctrlEndDate.setValue(ctrlEndDate.getCalendar().getMaximumDate());
		ctrlEndDate.setLocation(15, 260);
		pnlBody.add(ctrlEndDate);
	}

	/**
	 * �\���p�t�B�[���h
	 */
	protected class NBReference extends TReference {

		/**
		 * �R���X�g���N�^�[
		 */
		public NBReference() {
			super(TYPE.LABEL);

			code.setEditable(false);

			setSize(350, 20);
		}

		@Override
		protected TReferenceController getController() {
			return new NBReferenceController(this);
		}

		/**
		 * �R�[�h�Ɩ��̂̐ݒ�
		 * 
		 * @param c
		 * @param n
		 */
		public void setValue(String c, String n) {
			code.setText(c);
			name.setText(n);
		}
	}

	/**
	 * �\���pCtrl
	 */
	protected class NBReferenceController extends TReferenceController {

		/**
		 * @param field
		 */
		public NBReferenceController(TReference field) {
			super(field);
		}

		@Override
		public boolean code_Verify(JComponent comp) {
			return false;
		}

		@Override
		public void btnSearch_Click() {
			// �����Ȃ�
		}

		@Override
		public void btnSettle_Click() {
			// �����Ȃ�
		}

		@Override
		public String getDialogCaption() {
			return null;
		}

		@Override
		public String getButtonCaption() {
			return null;
		}

		@Override
		public String getTableKeyName() {
			return null;
		}

		@Override
		public Object getUnspecifiedEntity() {
			return null;
		}

	}

	/**
	 * �ȖڏW�v���̎擾
	 * 
	 * @return itemSummary �ȖڏW�v���
	 */
	public ItemSummary getItemSummary() {
		return itemSummary;
	}

	/**
	 * �ȖڏW�v���̐ݒ�
	 * 
	 * @param itemSummary �ȖڏW�v���
	 */
	public void setItemSummary(ItemSummary itemSummary) {
		this.itemSummary = itemSummary;
	}

	@Override
	/**
	 * Tab����`
	 */
	public void setTabIndex() {
		int i = 1;
		ctrlKmtCode.setTabControlNo(i++);
		ctrlKmkCode.setTabControlNo(i++);
		ctrlKokName.setTabControlNo(i++);
		ctrlSumCode.setTabControlNo(i++);
		ctrlOdr.setTabControlNo(i++);
		ctrlHyjKbnVisible.setTabControlNo(i++);
		ctrlHyjKbnInvisible.setTabControlNo(i++);
		ctrlBeginDate.setTabControlNo(i++);
		ctrlEndDate.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}
}