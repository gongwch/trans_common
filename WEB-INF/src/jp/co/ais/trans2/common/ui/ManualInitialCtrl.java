package jp.co.ais.trans2.common.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;

/**
 * �_�b�V���{�[�h�̃R���g���[���[
 * 
 * @author AIS
 */
public class ManualInitialCtrl extends TController implements TMainInitialInterface {

	/** �_�b�V���{�[�h��� */
	protected ManualInitial mainView = null;

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainInitialInterface#getName()
	 */
	public String getName() {
		return "Manual";
	}

	/**
	 * ��ʎ��ʎq
	 */
	@Override
	public String getRealUID() {
		return "Manual";
	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainInitialInterface#init()
	 */
	public void init() {
		try {

			// ��ʐ���
			createMainView();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainInitialInterface#afterCreate()
	 */
	public void afterCreate() {
		try {

			// �����Ȃ�

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainInitialInterface#getAddonButtonList()
	 */
	public List<TButton> getAddonButtonList() {
		List<TButton> btnList = new ArrayList<TButton>();
		btnList.add(mainView.btnManual);
		return btnList;
	}

	/**
	 * �_�b�V���{�[�h�̃t�@�N�g���B�C�x���g���`����B
	 */
	protected void createMainView() {
		mainView = new ManualInitial(getCompany(), TMainCtrl.instance.mainView, false);

		addMainViewEvent();
	}

	/**
	 * �_�b�V���{�[�h�̃C�x���g��`�B
	 */
	protected void addMainViewEvent() {

		// DASH�{�^������
		mainView.btnManual.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				TMainCtrl.getInstance().mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnManual_Click();
				TMainCtrl.getInstance().mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * ��ʂ�����������
	 */
	protected void initMainView() {
		// �����Ȃ�
	}

	/**
	 * �_�b�V���{�^��
	 */
	protected void btnManual_Click() {
		try {

			ManualAttachListDialogCtrl ctrl = new ManualAttachListDialogCtrl(TMainCtrl.getInstance().mainView.pnlLfAndTime);

			// TODO:�b��FAIS�y��admin�͕ҏW�\
			if (Util.equals(getUserCode().toUpperCase(), "AIS") //
				|| Util.equals(getUserCode().toLowerCase(), "admin")) {
				ctrl.dialog.btnAdd.setVisible(true);
				ctrl.dialog.btnDelete.setVisible(true);
			} else {
				ctrl.dialog.btnAdd.setVisible(false);
				ctrl.dialog.btnDelete.setVisible(false);
			}

			ctrl.show();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * Servlet�N���X��Ԃ�
	 * 
	 * @return DashManager
	 */
	protected Class getManager() {
		// return ManualManager.class;
		return null;
	}
}
