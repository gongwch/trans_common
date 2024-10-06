package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * �v���O�����\���ݒ�̐V�K�^�u�ǉ����
 * 
 * @author AIS
 */
public class MG0250ProgramDisplayMasterTabDialog extends TDialog {

	/** serialVersionUID */
	private static final long serialVersionUID = 8557714357271090391L;

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** �^�u���� */
	public TLabelField ctrlProgramGroupName;

	/**
	 * @param controller
	 * @param parent
	 * @param mordal
	 */
	public MG0250ProgramDisplayMasterTabDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlProgramGroupName = new TLabelField();
	}

	@Override
	public void allocateComponents() {

		setSize(400, 120);

		// �V�K�^�u�o�^
		setTitle(getWord("C11179"));

		// �m��{�^��
		int x = 275 - 110 - HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x, HEADER_Y);
		btnSettle.setEnterFocusable(true);
		pnlHeader.add(btnSettle);

		// �߂�{�^��
		x = 275;
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x, HEADER_Y);
		pnlHeader.add(btnClose);

		pnlBody.setLayout(null);

		// �V�X�e���敪
		x = 10;
		int y = 10;

		// �^�u����
		ctrlProgramGroupName.setLabelSize(60);
		ctrlProgramGroupName.setFieldSize(300);
		ctrlProgramGroupName.setSize(365, 20);
		ctrlProgramGroupName.setLocation(x, y);
		ctrlProgramGroupName.setLabelText("C11180");
		ctrlProgramGroupName.setMaxLength(TransUtil.PROGRAM_NAME_LENGTH);
		pnlBody.add(ctrlProgramGroupName);

	}

	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlProgramGroupName.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}
