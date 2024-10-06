package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * ���s���O�Q�Ƃ̉�ʃ��C�A�E�g
 * 
 * @author AIS
 */
public class MG0028ExecutedLogPanel extends TMainPanel {

	/** �����{�^�� */
	public TImageButton btnSearch;

	/** �G�N�Z���{�^�� */
	public TImageButton btnExcel;

	/** �㕔 */
	public TPanel pnlBodyTop;

	/** �Ώ۔N�����i�J�n�j */
	public TLabelPopupCalendar ctrlDateFrom;

	/** �Ώ۔N�����i�I���j */
	public TLabelPopupCalendar ctrlDateTo;

	/** ���[�U�����p�l�� */
	public TPanel pnlUser;

	/** ���[�U�[�͈͌��� */
	public TUserReferenceRange ctrlUserRange;

	/** ���[�U�J�n���x�� */
	public TLabel lblStart;

	/** ���[�U�I�����x�� */
	public TLabel lblEnd;

	/** �v���O���������p�l�� */
	public TPanel pnlProgram;

	/** �v���O�����͈͌��� */
	public TProgramReferenceRange ctrlProgramRange;

	/** �v���O�����J�n���x�� */
	public TLabel lblProStart;

	/** �v���O�����I�����x�� */
	public TLabel lblProEnd;

	/** ���O�C���Q�ƃ`�F�b�N�{�b�N�X */
	public TCheckBox checkProgram;

	/** ���я��R���{�{�b�N�X */
	public TLabelComboBox ctrlSort;

	/** ���� */
	public TPanel pnlBodyBottom;

	/** ���b�N�A�E�g�Ǘ��}�X�^�ꗗ */
	public TTable tbl;

	/** �ꗗ�̃J������` */
	public enum SC {
		/** Entity **/
		ENTITY

		/** ���s���� */
		, EXE_DATE

		/** ���s���[�U�[�R�[�h */
		, USR_CODE

		/** ���s���[�U�[���� */
		, USR_NAME

		/** IP�A�h���X */
		, IP_ADDRESS

		/** �v���O�����R�[�h */
		, PR_CODE

		/** �v���O�������� */
		, PR_NAME

		/** �X�e�[�^�X */
		, STATE

	}

	@Override
	public void initComponents() {
		btnSearch = new TImageButton(IconType.SEARCH);
		btnExcel = new TImageButton(IconType.EXCEL);
		pnlBodyTop = new TPanel();
		ctrlDateFrom = new TLabelPopupCalendar();
		ctrlDateTo = new TLabelPopupCalendar();
		pnlUser = new TPanel();
		ctrlUserRange = new TUserReferenceRange();
		lblStart = new TLabel();
		lblEnd = new TLabel();
		pnlProgram = new TPanel();
		ctrlProgramRange = new TProgramReferenceRange();
		lblProStart = new TLabel();
		lblProEnd = new TLabel();
		checkProgram = new TCheckBox();
		ctrlSort = new TLabelComboBox();
		pnlBodyBottom = new TPanel();

		tbl = new TTable();
		tbl.addColumn(SC.ENTITY, "", -1);
		tbl.addColumn(SC.EXE_DATE, getWord("C00218") + getWord("C02906"), 140, SwingConstants.CENTER);
		tbl.addColumn(SC.USR_CODE, getWord("C00218") + getWord("C00589"), 140);
		tbl.addColumn(SC.USR_NAME, getWord("C00218") + getWord("C00691"), 140);
		tbl.addColumn(SC.IP_ADDRESS, getWord("C02907"), 120);
		tbl.addColumn(SC.PR_CODE, getWord("C00818"), 140);
		tbl.addColumn(SC.PR_NAME, getWord("C00819"), 140);
		tbl.addColumn(SC.STATE, getWord("C02908"), 120);

	}

	@Override
	public void allocateComponents() {

		// �����{�^��
		int x = HEADER_LEFT_X;
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F1);
		btnSearch.setSize(25, 110);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// �G�N�Z���{�^��
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnExcel.setSize(25, 130);
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F9);
		btnExcel.setLocation(x, HEADER_Y);
		pnlHeader.add(btnExcel);

		// �㕔
		pnlBodyTop.setLayout(null);
		pnlBodyTop.setMaximumSize(new Dimension(0, 150));
		pnlBodyTop.setMinimumSize(new Dimension(0, 150));
		pnlBodyTop.setPreferredSize(new Dimension(0, 150));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// �Ώ۔N����
		int Y = 20;
		ctrlDateFrom.setLabelText(getWord("C00347") + getWord("C02909"));// �Ώہ{�N����
		ctrlDateFrom.getCalendar().setCalendarType(TPopupCalendar.TYPE_YMD);
		ctrlDateFrom.setLabelSize(100);
		ctrlDateFrom.setLocation(25, Y);
		pnlBodyTop.add(ctrlDateFrom);

		// �`
		ctrlDateTo.setLangMessageID("C01333");
		ctrlDateTo.getCalendar().setCalendarType(TPopupCalendar.TYPE_YMD);
		ctrlDateTo.setLabelSize(100);
		ctrlDateTo.setLocation(130, Y);
		pnlBodyTop.add(ctrlDateTo);

		// ���[�U�[�����p�l��
		pnlUser.setLayout(null);
		pnlUser.setSize(400, 75);
		pnlUser.setLangMessageID(getWord("C00347") + getWord("C00528"));// �Ώ�+���[�U�[
		pnlUser.setLocation(30, Y + ctrlDateFrom.getHeight() + 5);
		pnlBodyTop.add(pnlUser);

		// ���[�U�͈͌���
		ctrlUserRange.ctrlUserReferenceFrom.btn.setLangMessageID("C00528");// ���[�U�[
		ctrlUserRange.ctrlUserReferenceTo.btn.setLangMessageID("C00528");
		ctrlUserRange.setLocation(70, Y);

		pnlUser.add(ctrlUserRange);

		// ���[�U�����J�n���x��
		lblStart.setLangMessageID("C01012");// �J�n
		lblStart.setSize(60, 20);
		lblStart.setLocation(20, Y);
		pnlUser.add(lblStart);

		// ���[�U�����I�����x��
		lblEnd.setLangMessageID("C01143");// �I��
		lblEnd.setSize(60, 20);
		lblEnd.setLocation(20, Y + 20);
		pnlUser.add(lblEnd);

		// �v���O���������p�l��
		pnlProgram.setLayout(null);
		pnlProgram.setSize(410, 100);
		pnlProgram.setLangMessageID(getWord("C00347") + getWord("C00477"));// �Ώہ{�v���O����
		pnlProgram.setLocation(pnlUser.getX() + pnlUser.getWidth() + 10, pnlUser.getY());
		pnlBodyTop.add(pnlProgram);

		// ���O�C���E���O�A�E�g��Ώۃ`�F�b�N�{�b�N�X
		checkProgram.setLangMessageID("C02910");
		checkProgram.setSize(200, 20);
		checkProgram.setLocation(60, Y);
		pnlProgram.add(checkProgram);

		// �v���O�����͈͌���
		ctrlProgramRange.ctrlProgramReferenceFrom.btn.setLangMessageID("C00477");// �v���O����
		ctrlProgramRange.ctrlProgramReferenceTo.btn.setLangMessageID("C00477");
		ctrlProgramRange.setLocation(checkProgram.getX(), checkProgram.getY() + 25);
		pnlProgram.add(ctrlProgramRange);

		// �v���O�����J�n���x��
		lblProStart.setLangMessageID("C01012");
		lblProStart.setSize(60, 20);
		lblProStart.setLocation(10, ctrlProgramRange.getY());
		pnlProgram.add(lblProStart);

		// �v���O�����I�����x��
		lblProEnd.setLangMessageID("C01143");// �I��
		lblProEnd.setSize(60, 20);
		lblProEnd.setLocation(10, lblProStart.getY() + 20);
		pnlProgram.add(lblProEnd);

		// ���я��R���{�{�b�N�X
		ctrlSort.setLangMessageID("C02839");// ���я�
		ctrlSort.setLabelSize(100);
		ctrlSort.setComboSize(110);
		ctrlSort.setSize(230, 20);
		ctrlSort.setLocation(40, pnlUser.getY() + 80);
		initComboBox(ctrlSort.getComboBox());
		pnlBodyTop.add(ctrlSort);

		// ����
		pnlBodyBottom.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlBody.add(pnlBodyBottom, gc);

		// �ꗗ
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.insets = new Insets(10, 30, 10, 30);
		pnlBodyBottom.add(tbl, gc);

	}

	/**
	 * �R���{�{�b�N�X���g��`
	 * 
	 * @param comboBox �������Ώۂ̃R���{�{�b�N�X
	 */
	protected void initComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem(1, getWord("C00218") + getWord("C02906"));// ���s�{����
		comboBox.addTextValueItem(2, getWord("C00528"));
		comboBox.addTextValueItem(3, getWord("C00477"));

	}

	@Override
	public void setTabIndex() {
		int i = 1;
		ctrlDateFrom.setTabControlNo(i++);
		ctrlDateTo.setTabControlNo(i++);
		ctrlUserRange.setTabControlNo(i++);
		checkProgram.setTabControlNo(i++);
		ctrlProgramRange.setTabControlNo(i++);
		ctrlSort.setTabControlNo(i++);
		tbl.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);

	}
}
