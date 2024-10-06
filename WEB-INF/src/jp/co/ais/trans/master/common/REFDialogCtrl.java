package jp.co.ais.trans.master.common;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.klg.jclass.table.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * REFDialog��Ctrl�N���X�B<br>
 * ���̃N���X�� jp.co.ais.trans.master.common.REFDisplayDialogCtrl ������P�����B <br>
 * �v���O�������L�́uREFKind�v���̂��̂�������āA�V�X�e���S�ʂ̔ėp��ڎw���B
 * 
 * @author ISFnet China: lipu
 */
public class REFDialogCtrl extends TAppletClientBase {

	/** �����_�C�A���O * */
	private REFDialog dialog;

	/** TButtonField �R���g���[�� * */
	private TButtonField buttonField;

	/** TButtonField �R���g���[�� * */
	private TButtonField passivityButtonField;

	/** ��� * */
	private Object parent;

	/** �v���O�������L�̋@�\�ɃT�|�[�g�ׂ̃��X�i�[ * */
	private REFListener listener;

	/** ���M���Servlet * */
	private String targetServlet = "";

	/** �\��ID * */
	private String titleID = "";

	/** �X�v���b�h�e�[�u���̑���̕\�� * */
	String columnLabel1 = "C00174";

	/** �X�v���b�h�e�[�u���̑���̕\�� * */
	String columnLabel2 = "C00548";

	/** �X�v���b�h�e�[�u���̑�O��̕\�� * */
	String columnLabel3 = "C00160";

	/** �G���[���A���b�Z�[�W��\�����邩�Hdefault = true * */
	private boolean showsMsgOnError = true;

	/** the last user inputted code. * */
	private String lastCode;

	/** lastCode is whether or not valid * */
	private boolean isLastCodeValid = true;

	/**
	 * whether or not the focusLost event is fired by clicking an ignored button<br>
	 * (�Ⴆ��΁A�u�߂�v�{�^����������)
	 */
	private boolean isClickingIgnoredButton = false;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param buttonField TButtonField�R���g���[��
	 * @param parent ���
	 */
	public REFDialogCtrl(TButtonField buttonField, Object parent) {
		this.buttonField = buttonField;

		this.parent = parent;

		showsMsgOnError = true;

		// add listeners for the button
		REFButtonWatcher buttonWatcher = new REFButtonWatcher(this);
		this.buttonField.getButton().addActionListener(buttonWatcher);

	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param buttonField TButtonField�R���g���[��
	 * @param passivityButtonField TButtonField�R���g���[��
	 * @param parent ���
	 */
	public REFDialogCtrl(TButtonField buttonField, TButtonField passivityButtonField, Object parent) {
		this.buttonField = buttonField;
		this.passivityButtonField = passivityButtonField;

		this.parent = parent;

		showsMsgOnError = true;

		// add listeners for the button
		REFButtonWatcher buttonWatcher = new REFButtonWatcher(this);
		this.buttonField.getButton().addActionListener(buttonWatcher);

	}

	/**
	 * �v���O�������L�̋@�\�ɃT�|�[�g�ׂ̃��X�i�[
	 * 
	 * @param listener
	 */
	public void setREFListener(REFListener listener) {
		this.listener = listener;
	}

	/**
	 * ���M���Servlet
	 * 
	 * @param targetServlet
	 */
	public void setTargetServlet(String targetServlet) {
		this.targetServlet = targetServlet;
	}

	/**
	 * ���M���Servlet�̎擾
	 * 
	 * @return ���M���Servlet
	 */
	public String getTargetServlet() {
		return targetServlet;
	}

	/**
	 * �\��ID
	 * 
	 * @param titleID
	 */
	public void setTitleID(String titleID) {
		this.titleID = titleID;
	}

	/**
	 * �X�v���b�h�e�[�u���̊e��̕\��
	 * 
	 * @param columnLabel1 ����̕\��
	 * @param columnLabel2 ����̕\��A���̏ꍇ�unull�v����������
	 * @param columnLabel3 ��O��̕\��A���̏ꍇ�unull�v����������
	 */
	public void setColumnLabels(String columnLabel1, String columnLabel2, String columnLabel3) {
		this.columnLabel1 = columnLabel1;
		this.columnLabel2 = columnLabel2;
		this.columnLabel3 = columnLabel3;
	}

	/**
	 * �X�v���b�h�e�[�u���̊e��̕\��̒P��ID
	 * 
	 * @param columnLabelID1 ����̕\��̒P��ID
	 * @param columnLabelID2 ����̕\��̒P��ID�A���̏ꍇ�unull�v����������
	 * @param columnLabelID3 ��O��̕\��̒P��ID�A���̏ꍇ�unull�v����������
	 */
	public void setColumnLabelIDs(String columnLabelID1, String columnLabelID2, String columnLabelID3) {
		this.columnLabel1 = getWord(columnLabelID1);
		this.columnLabel2 = getWord(columnLabelID2);
		this.columnLabel3 = getWord(columnLabelID3);
	}

	/**
	 * �G���[���A���b�Z�[�W��\�����邩�H
	 * 
	 * @param showsMsgOnError
	 */
	public void setShowsMsgOnError(boolean showsMsgOnError) {
		this.showsMsgOnError = showsMsgOnError;
	}

	/**
	 * �u�߂�v���̃{�^�����������AREF��focusLost�𖳎����邽��
	 * 
	 * @param button �u�߂�v���̃{�^��
	 * @deprecated ���p�����
	 */
	public void addIgnoredButton(JButton button) {
		//
	}

	/**
	 * ���̕\���̋�������郊�t���b�V������
	 */
	public void refreshName() {
		try {
			refreshName(false);

		} catch (IOException ex) {
			errorHandler(dialog, ex);
		}
	}

	/**
	 * ���̓R�[�h�l�̌�������
	 * 
	 * @return ���̓R�[�h�̑��݉�
	 */
	public boolean isCodeValid() {
		return isLastCodeValid;
	}

	// ********** ����Button�̏��� **********

	/**
	 * This class is used for watching the button of the TButtonField.<brs> It listens both mouse and action events.
	 */
	private class REFButtonWatcher implements ActionListener {

		REFDialogCtrl dialogCtrl;

		REFButtonWatcher(REFDialogCtrl dialogCtrl) {
			this.dialogCtrl = dialogCtrl;
		}

		// when the button has been clicked
		public void actionPerformed(ActionEvent ev) {

			// if no dialog instance has been created, create one.
			dialog = null;
			String dialogTitleID = null;
			dialogTitleID = dialogCtrl.getWord(dialogCtrl.titleID) + dialogCtrl.getWord("C00010");

			if (parent instanceof Dialog) {
				dialog = new REFDialog((Dialog) parent, true, dialogCtrl, dialogTitleID);
			} else {
				dialog = new REFDialog((Frame) parent, true, dialogCtrl, dialogTitleID);
			}

			dialog.setSize(700, 430);
			initREFDialog();

			// clear the previous data
			Vector cells = new Vector();
			dialogSetDataList(cells);

			dialog.txtCode.setValue("");
			dialog.txtAbbreviationName.setValue("");
			dialog.txtNameForSearch.setValue("");

			dialog.txtCode.requestFocus();

			// ���ސݒ�A��������
			if (!Util.isNullOrEmpty(buttonField.getValue())) {
				dialog.txtCode.setValue(buttonField.getValue());
				dialogFinds(false);
			}

			dialog.setVisible(true);
		}
	}

	/**
	 * refresh the Name field (the TTextField on the right side)
	 * 
	 * @param requestsFocusOnError whether or not re-focus the field when error occurs
	 * @throws IOException
	 */
	protected void refreshName(boolean requestsFocusOnError) throws IOException {
		// if �u�߂�v-like buttons clicked, ignore it
		if (isClickingIgnoredButton) return;

		if (this.buttonField.getOldValue() != null) {
			lastCode = this.buttonField.getOldValue().trim();
		} else {
			lastCode = "";
		}

		this.buttonField.setValue(this.buttonField.getField().getText().trim());

		// first assuming code not changed
		boolean codeChanged = false;

		// if the input isn't changed, don't check DB.
		if (lastCode != null && lastCode.equals(this.buttonField.getField().getText())) {

			// if lastCode is empty, clear the name field ...
			if (Util.isNullOrEmpty(lastCode)) {
				isLastCodeValid = true;
				this.buttonField.getNotice().setEditable(true);
				this.buttonField.getNotice().setText("");
				this.buttonField.getNotice().setEditable(false);

				// fire the textCleared event of REFListener
				if (listener != null) {
					listener.textCleared();
				}

			} else if (isLastCodeValid) {

				// if lastCode is valid and is not empty, return
				if (!"".equals(this.buttonField.getNotice().getText())) {
					return;
				}
			} else if (!Util.isNullOrEmpty(lastCode)) {

				// if lastCode is not valid and is not empty, display an error and return
				codeNotExists(requestsFocusOnError);
				return;
			}
		} else {

			codeChanged = (lastCode != null);

			// record the current code
			lastCode = this.buttonField.getField().getText();
		}

		if (Util.isNullOrEmpty(lastCode)) {

			// code is empty, clear the name field ...
			isLastCodeValid = true;
			this.buttonField.getNotice().setEditable(true);
			this.buttonField.getNotice().setText("");
			this.buttonField.getNotice().setEditable(false);

			// ��ʉ�Ђ͉��������͍ς݂̏ꍇ�A��Љ����A���̏ꍇ�t
			boolean isEdit = !Util.isNullOrEmpty(buttonField.getNotice().getText());

			if (null != passivityButtonField) {

				this.passivityButtonField.setValue("");
				this.passivityButtonField.setNoticeValue("");

				this.passivityButtonField.getButton().setEnabled(isEdit);
				this.passivityButtonField.getField().setEditable(isEdit);
			}

			// fire the textCleared event of REFListener
			if (listener != null) {
				listener.textCleared();
			}

		} else {

			// DB�֌������āA�Y���R�[�h�����݂���ǂ����m�F������
			// ���M����p�����[�^��ݒ�
			addSendValues("flag", "findname");

			// langCode: some programs need to support multi-languages,
			// for example, �u��s�����}�X�^�vREF�̃f�[�^�͑�����\����Ή�����B
			addSendValues("langCode", super.getLoginLanguage());

			// code: to query for the name, which is matching this code in DB.
			addSendValues("code", lastCode);

			// append additional keys for querying ...
			if (listener != null) {
				Map keys = listener.primaryKeysAppending();
				if (keys != null) {
					Iterator iterator = keys.entrySet().iterator();
					while (iterator.hasNext()) {
						Map.Entry entry = (Map.Entry) iterator.next();
						addSendValues(entry.getKey().toString(), entry.getValue().toString());
					}
				}
			}

			if (!request(targetServlet)) {
				errorHandler(dialog);
				return;
			}

			List result = super.getResultList();

			if (result.size() == 0) {

				// �Y���R�[�h�͑��݂��܂���B
				// mark isLastCodeValid as false
				codeNotExists(requestsFocusOnError);
				isLastCodeValid = false;

			} else {

				// mark isLastCodeValid as true
				isLastCodeValid = true;

				// read the first record from the result.
				List inner = (List) result.get(0);

				// read the first item in the inner list and display it
				this.buttonField.getNotice().setEditable(true);
				this.buttonField.getNotice().setText(inner.get(0).toString());
				this.buttonField.getNotice().setEditable(false);

				if (null != passivityButtonField && !"".equals(this.buttonField.getField().getText())) {
					// ��ʉ�Ђ͉��������͍ς݂̏ꍇ�A��Љ����A���̏ꍇ�t
					boolean isEdit = !Util.isNullOrEmpty(buttonField.getNotice().getText());

					this.passivityButtonField.getButton().setEnabled(isEdit);
					this.passivityButtonField.getField().setEditable(isEdit);

					this.passivityButtonField.setValue("");
					this.passivityButtonField.setNoticeValue("");
				}

				// fire the codeChanged event
				if (requestsFocusOnError && codeChanged) {
					if (listener != null) {
						listener.codeChanged();
					}
				}

				// fire the goodCodeInputted event
				if (listener != null) {
					if (!buttonField.getField().isEditable() || !buttonField.getField().isEnabled()) {
						return;
					}

					listener.goodCodeInputted();
				}
			}
		}
	}

	/**
	 * @param prefixID
	 */
	public void setPrefixID(String prefixID) {

		if (prefixID != null) {
			this.columnLabel1 = prefixID + getWord("C00174");
			this.columnLabel2 = prefixID + getWord("C00548");
			this.columnLabel3 = prefixID + getWord("C00160");
		}
	}

	/**
	 * when name refreshing fails, run into this method
	 * 
	 * @param requestsFocusOnError whether or not re-focus the field when error occurs
	 */
	protected void codeNotExists(boolean requestsFocusOnError) {

		// clear the name field first
		this.buttonField.getNotice().setEditable(true);
		this.buttonField.getNotice().setText("");
		this.buttonField.getNotice().setEditable(false);

		if (showsMsgOnError) {

			// show error message
			showMessage(dialog, "W00081", lastCode);

			if (requestsFocusOnError) {
				// requestFocus need to wait for this method finishing running.
				buttonField.getField().requestFocus();
			}
		}

		// fire badCodeInputted event
		if (listener != null) {
			listener.badCodeInputted();
		}
	}

	// ********** REFDialog�̏��� **********

	/**
	 * ��ʏ�����
	 */
	protected void initREFDialog() {

		// ***** �P�D�K�v�ȃC�x���g���X�i�[�̓o�L *****

		// �u��ʃf�U�C���K��v�́u��ʍ��ڐ݌v�v�́u5�D ������ʂ̓����v�ɂ��A
		// Enter�L�[���������ꍇ�A�R�[�h��No.���A������ʂ̌Ăяo������ʂɕԂ��B
		dialog.ssJournal.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent ev) {
				// ENTER �������AbtnSettle����������
				if (ev.getKeyCode() == KeyEvent.VK_ENTER) {
					dialog.ssJournal.dispatchEvent(new KeyEvent(dialog.ssJournal, KeyEvent.KEY_PRESSED, 0, 0,
						KeyEvent.VK_UP, KeyEvent.CHAR_UNDEFINED));
					dialog.btnSettle.doClick();
				}
			}
		});

		// ***** �S�D���̑� *****

		// �_�C�A���O������A�u�߂�v�{�^���̉��������Ɠ���
		dialog.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent ev) {
				dialog.btnCancel.doClick();
			}
		});

		// ��ʃT�C�Y�Œ�
		dialog.setResizable(false);
	}

	/**
	 * ��������
	 */
	protected void dialogFinds() {
		dialogFinds(true);
	}

	/**
	 * ��������
	 * 
	 * @param msgFlg ���b�Z�[�W�\���t���O true�F�\�� false:�\�����Ȃ�
	 */
	protected void dialogFinds(boolean msgFlg) {
		try {
			this.lastCode = buttonField.getValue();
			String code = dialog.txtCode.getText();
			String name_S = dialog.txtAbbreviationName.getText();
			String name_K = dialog.txtNameForSearch.getText();

			dialog.btnSettle.setEnabled(false);

			// DB�֌���
			// ���M����p�����[�^��ݒ�
			addSendValues("flag", "findref");
			addSendValues("langCode", super.getLoginLanguage());

			addSendValues("code", StringUtil.convertPrm(code));
			addSendValues("name_S", StringUtil.convertPrm(name_S));
			addSendValues("name_K", StringUtil.convertPrm(name_K));

			// append additional keys for querying ...
			if (listener != null) {
				Map keys = listener.primaryKeysAppending();
				if (keys != null) {
					Iterator iterator = keys.entrySet().iterator();
					while (iterator.hasNext()) {
						Map.Entry entry = (Map.Entry) iterator.next();
						addSendValues(entry.getKey().toString(), entry.getValue().toString());
					}
				}
			}

			if (!request(targetServlet)) {
				errorHandler(dialog);
				return;
			}

			List result = super.getResultList();
			// �T�[�u���b�g���瑗���Ă������ʂ�z��ɃZ�b�g
			Vector<Vector> cells = new Vector<Vector>();

			Iterator recordIte = result.iterator();
			for (int row = 0; recordIte.hasNext(); row++) {
				Iterator dataIte = ((List) recordIte.next()).iterator();

				Vector colum = new Vector();

				for (int column = 0; dataIte.hasNext(); column++) {
					colum.add(column, dataIte.next());
				}
				cells.add(row, colum);
			}
			dialogSetDataList(cells);

			if (result.size() == 0) {
				if (msgFlg) {
					super.showMessage(dialog, "W00100");
				}
				dialog.txtCode.requestFocus();
			} else {
				dialog.ssJournal.requestFocus();
			}

		} catch (IOException e) {
			errorHandler(dialog, e, "E00009");
		}
	}

	/*
	 * �X�v���b�h�f�[�^�̐ݒ�
	 */
	protected void dialogSetDataList(Vector cells) {
		dialog.ds.setCells(cells);
		dialog.ds.setNumRows(cells.size());

		dialog.ssJournal.setDataSource(dialog.ds);
		dialog.btnSettle.setEnabled(cells.size() > 0);
	}

	/**
	 * �u�m��v�{�^��������
	 */
	protected void dialogSettle() {
		int nomRow = dialog.ssJournal.getCurrentRow();
		TableDataModel model = dialog.ssJournal.getDataView();

		dialog.selectedCode = (String) model.getTableDataItem(nomRow, 0); // �R�[�h
		dialog.selectedName_S = (String) model.getTableDataItem(nomRow, 1); // ����
		dialogClosed();
	}

	/**
	 * REFDialog�����
	 */
	protected void dialogClosed() {

		dialog.setVisible(false);

		if (dialog.selectedCode != null) {
			this.buttonField.setValue(dialog.selectedCode);
			this.buttonField.getNotice().setEditable(true);
			this.buttonField.getNotice().setText(dialog.selectedName_S);
			this.buttonField.getNotice().setEditable(false);
			this.isLastCodeValid = true;

			buttonField.getField().requestFocus();

			// fire goodCodeInputted event
			if (listener != null) {
				listener.goodCodeInputted();
			}

			if (!this.lastCode.equals(dialog.selectedCode)) {
				listener.codeChanged();
			}

		} else {
			// re-focus the code field
			buttonField.getField().requestFocus();
		}

		if (lastCode != null && !lastCode.equals(this.buttonField.getField().getText())
			&& !"".equals(this.buttonField.getField().getText())) {
			if (null != passivityButtonField) {
				// ��ʉ�Ђ͉��������͍ς݂̏ꍇ�A��Љ����A���̏ꍇ�t
				boolean isEdit = !Util.isNullOrEmpty(buttonField.getNotice().getText());

				this.passivityButtonField.getButton().setEnabled(isEdit);
				this.passivityButtonField.getField().setEditable(isEdit);

				this.passivityButtonField.setValue("");
				this.passivityButtonField.setNoticeValue("");
			}
		}
	}

	/**
	 * REF��ʂ̺��ނ�ݒ肷��
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		dialog.txtCode.setValue(code);
	}
}
