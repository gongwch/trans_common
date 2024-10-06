package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.*;

/**
 * �Q�ƃt�B�[���h�̃R���g���[�����N���X�B
 * 
 * @author AIS
 */
public abstract class TReferenceController extends TController {

	/** �����_�C�A���O */
	protected TReferenceDialog dialog;

	/** �����t�B�[���h */
	protected TReference field;

	/** ���X�g�t�H�[�J�X���̃R�[�h���݃`�F�b�N true;�`�F�b�N���� */
	protected boolean checkExist = true;

	/** TCallBackListener */
	protected List<TCallBackListener> callBackListenerList = null;

	/** �I�𒆂�Entity */
	protected Object entity;

	/** ��O��Entity */
	protected TReferable oldEntity = null;

	/** 3�J�����̕\�� */
	protected boolean show3rdColumn = true;

	/** �Œ�{�^���̖����擾���܂� */
	protected String fixedButtonCaption = null;

	/** �Œ茟���_�C�A���O���擾���܂� */
	protected String fixedDialogCaption = null;

	/** �_�C�A���O�̃R�[�h�̏����l�\�����邩 */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/**
	 * �Œ茟���_�C�A���O���擾���܂�
	 * 
	 * @return fixedButtonCaption
	 */
	public String getFixedDialogCaption() {
		return fixedDialogCaption;
	}

	/**
	 * �Œ茟���_�C�A���O��ݒ肵�܂�
	 * 
	 * @param fixedDialogCaption
	 */
	public void setFixedDialogCaption(String fixedDialogCaption) {
		this.fixedDialogCaption = fixedDialogCaption;
	}

	/**
	 * 3�J�����̕\��
	 * 
	 * @return boolean
	 */
	public boolean isShow3rdColumn() {
		return show3rdColumn;
	}

	/**
	 * 3�J�����̕\��
	 * 
	 * @param show3rdColumn
	 */
	public void setShow3rdColumn(boolean show3rdColumn) {
		this.show3rdColumn = show3rdColumn;
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �����t�B�[���h
	 */
	public TReferenceController(TReference field) {
		this.field = field;
		init();
	}

	/**
	 * ��������
	 */
	public void init() {

		// �C�x���g��ǉ�����
		addEvent();

		// �R�[�h�Ɩ��̂��N���A����
		clear();

		// �{�^���̃L���v�V����
		setFixedButtonCaption(field.title);
		if (Util.isNullOrEmpty(getFixedButtonCaption())) {
			field.btn.setLangMessageID(getButtonCaption());
			field.lbl.setLangMessageID(getButtonCaption());
		} else {
			field.btn.setLangMessageID(getFixedButtonCaption());
			field.lbl.setLangMessageID(getFixedButtonCaption());
		}

	}

	/**
	 * �����t�B�[���h�Ŕ�������C�x���g��ǉ�����
	 */
	protected void addEvent() {

		// �{�^������
		field.btn.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btn_Click();
				field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}

		});

		// �R�[�h�t�B�[���h�ŃX�y�[�X����
		field.code.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				code_KeyPressed(e);
			}

		});

		// �R�[�h�t�B�[���h��verify�C�x���g
		field.code.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				try {

					// �l�̕ύX���Ȃ�
					if (!field.code.isValueChanged2()) {
						return true;
					}

					// ��Entity��ޔ�
					oldEntity = createEntity();
					if (entity != null && entity instanceof TReferable) {
						oldEntity.setCode(((TReferable) entity).getCode());
					}

					field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					if (callBackListenerList != null && !callBackListenerList.isEmpty()) {
						for (TCallBackListener listener : callBackListenerList) {
							listener.before();
						}
					}

					boolean rt = code_Verify(comp);

					if (callBackListenerList != null && !callBackListenerList.isEmpty()) {
						for (TCallBackListener listener : callBackListenerList) {
							listener.after();
							listener.after(rt);

							if (!listener.afterVerify(rt)) {
								return false;
							}
						}
					}

					return rt;

				} finally {
					field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}

		});

	}

	/**
	 * �R�[�h�Ɨ��̂�����������
	 */
	public void clear() {
		field.code.setText(null);
		field.name.setText(null);
		entity = null;
	}

	/**
	 * �t�B�[���h[�{�^��]����
	 */
	public void btn_Click() {

		try {
			// ���X�g�t�H�[�J�X���`�F�b�N���邩�̏���ޔ�
			boolean tempCheckExist = isCheckExist();

			// �����_�C�A���O�𐶐�
			dialog = createDialog();
			dialog.setShow3rdColumn(this.show3rdColumn);
			// �����_�C�A���O�ɃC�x���g�ǉ�
			addDialogEvent();

			dialog.btnSettle.setEnabled(false);

			if (!field.code.isEmpty()) {
				// �R�[�h�����͂��ꂽ��ԂŃ_�C�A���O���\�����ꂽ�ꍇ�A�G���[���b�Z�[�W��\�����Ȃ��悤�ɂ���B
				setCheckExist(false);
				if (isShowDefaultCode()) {
					dialog.code.setText(field.code.getText());
					dialog.controller.btnSearch_Click();
					dialog.btnSettle.setEnabled(true);
				}
			}

			// �����������ʁA�f�[�^�����݂��Ȃ��ꍇ�A�G���[���b�Z�[�W��\������悤�ɂ���B
			setCheckExist(true);
			// mordal�ŊJ��
			dialog.setModal(true);
			dialog.setVisible(true);

			// �_�C�A���O�������I���������߁A�`�F�b�N�t���O�����ɖ߂��B
			setCheckExist(tempCheckExist);
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �t�B�[���h[�R�[�h]�ŃX�y�[�X�����Ō����_�C�A���O���J��
	 * 
	 * @param e
	 */
	public void code_KeyPressed(KeyEvent e) {

		// �X�y�[�X�����Ō����_�C�A���O���J��
		if (KeyEvent.VK_SPACE == e.getKeyCode()) {

			// �ҏW�s�̏ꍇ�͉������Ȃ�
			if (!field.code.isEditable() || !field.code.isEnabled()) {
				return;
			}

			// �R�[�h�l�������Ȃ��悤�ɂ���
			field.code.select(0, 0);

			// �����_�C�A���O���J��
			btn_Click();
		}

	}

	/**
	 * �t�B�[���h[�R�[�h]��verify
	 * 
	 * @param comp �R���|�[�l���g
	 * @return verify����
	 */
	public abstract boolean code_Verify(JComponent comp);

	/**
	 * �����_�C�A���O�Ŕ�������C�x���g��ǉ�����
	 */
	protected void addDialogEvent() {

		// �����{�^������
		dialog.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btnSearch_Click();
				after_btnSearch_Click();
			}
		});

		// �m��{�^������
		dialog.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btnSettle_Click(e);
			}
		});

		// �߂�{�^������
		dialog.btnBack.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btnBack_Click();
			}
		});

		// �R�[�h������Enter�Ō������s
		dialog.code.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				dialog_code_keyPressed(e);
			}
		});

		// ���̏�����Enter�Ō������s
		dialog.names.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				dialog_code_keyPressed(e);
			}
		});

		// �������̂�Enter�Ō������s
		dialog.namek.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				dialog_code_keyPressed(e);
			}
		});
	}

	/**
	 * �����_�C�A���O[����]�{�^������
	 */
	public abstract void btnSearch_Click();

	/**
	 * �����_�C�A���O[�m��]�{�^������
	 * 
	 * @param e
	 */
	public void btnSettle_Click(@SuppressWarnings("unused") ActionEvent e) {

		// ��Entity��ޔ�
		oldEntity = createEntity();
		if (oldEntity != null) {
			oldEntity.setCode(field.code.getText());
		}

		btnSettle_Click();
	}

	/**
	 * �����_�C�A���O[�m��]�{�^������
	 */
	public abstract void btnSettle_Click();

	/**
	 * �����_�C�A���O[�߂�]�{�^������
	 */
	public void btnBack_Click() {
		// �����_�C�A���O�����
		dialog.setVisible(false);
		dialog.dispose();
	}

	/**
	 * �����_�C�A���O�̌����L�[�t�B�[���h��Enter�������ꂽ�猟������B
	 * 
	 * @param e
	 */
	public void dialog_code_keyPressed(KeyEvent e) {
		if (KeyEvent.VK_ENTER == e.getKeyCode()) {
			btnSearch_Click();
			after_btnSearch_Click();
		}
	}

	/**
	 * �����_�C�A���O�̌����L�[�t�B�[���h��Enter�������ꂽ�猟�����������B
	 */
	public void after_btnSearch_Click() {
		if (ClientConfig.isFlagOn("trans.ref.table.focusable") && dialog.tbl.getRowCount() > 0) {
			EventQueue.invokeLater(new Runnable() {

				public void run() {
					dialog.tbl.requestFocus();
				}
			});
		}
	}

	/**
	 * �����_�C�A���O�̃C���X�^���X�𐶐����Ԃ�
	 * 
	 * @return �����_�C�A���O
	 */
	protected TReferenceDialog createDialog() {
		return new TReferenceDialog(this);
	}

	/**
	 * �R�[�h�̑��݃`�F�b�N�����邩��Ԃ��܂�
	 * 
	 * @return true ����
	 */
	public boolean isCheckExist() {
		return checkExist;
	}

	/**
	 * �R�[�h�̑��݃`�F�b�N�����邩�ݒ肵�܂�
	 * 
	 * @param checkExist
	 */
	public void setCheckExist(boolean checkExist) {
		this.checkExist = checkExist;
	}

	/**
	 * �����_�C�A���O�̃L���v�V������Ԃ�
	 * 
	 * @return �����_�C�A���O�̃L���v�V����
	 */
	public abstract String getDialogCaption();

	/**
	 * �{�^���̃L���v�V�������擾���܂�
	 * 
	 * @return �{�^���̃L���v�V����
	 */
	public abstract String getButtonCaption();

	/**
	 * �Œ�{�^���̖����擾���܂�
	 * 
	 * @return fixedButtonCaption
	 */
	public String getFixedButtonCaption() {
		return fixedButtonCaption;
	}

	/**
	 * �Œ�{�^���̖���ݒ肵�܂�
	 * 
	 * @param fixedButtonCaption
	 */
	public void setFixedButtonCaption(String fixedButtonCaption) {
		this.fixedButtonCaption = fixedButtonCaption;
	}

	/**
	 * �����_�C�A���O�̃^�C�g����Ԃ�
	 * 
	 * @return �^�C�g��
	 */
	public String getDialogTitle() {
		if (Util.isNullOrEmpty(getFixedDialogCaption())) {
			return getWord(getDialogCaption()) + getWord("C00010"); // XX�ꗗ
		} else {
			return getWord(getFixedDialogCaption()) + getWord("C00010");
		}
	}

	/**
	 * �R�[�h�̃L���v�V�������擾���܂�
	 * 
	 * @return �R�[�h�̃L���v�V����
	 */
	public String getCodeCaption() {
		if (Util.isNullOrEmpty(getFixedDialogCaption())) {
			return getWord(getDialogCaption()) + getWord("C00174"); // XX�R�[�h
		} else {
			return getWord(getFixedDialogCaption()) + getWord("C00174");
		}
	}

	/**
	 * ���̂̃L���v�V�������擾���܂�
	 * 
	 * @return ���̂̃L���v�V����
	 */
	public String getNamesCaption() {
		if (Util.isNullOrEmpty(getFixedDialogCaption())) {
			return getWord(getDialogCaption()) + getWord("C00548"); // XX����
		} else {
			return getWord(getFixedDialogCaption()) + getWord("C00548");
		}
	}

	/**
	 * ���̂̃L���v�V�������擾���܂�
	 * 
	 * @return ���̂̃L���v�V����
	 */
	public String getNameCaption() {
		if (Util.isNullOrEmpty(getFixedDialogCaption())) {
			return getWord(getDialogCaption()) + getWord("C00518"); // ����
		} else {
			return getWord(getFixedDialogCaption()) + getWord("C00518");
		}
	}

	/**
	 * �������̂̃L���v�V�������擾���܂�
	 * 
	 * @return �������̂̃L���v�V����
	 */
	public String getNamekCaption() {
		if (Util.isNullOrEmpty(getFixedDialogCaption())) {
			return getWord(getDialogCaption()) + getWord("C00160"); // ��������
		} else {
			return getWord(getFixedDialogCaption()) + getWord("C00160");
		}
	}

	/**
	 * �R�[�h�̃J�����T�C�Y���擾���܂�
	 * 
	 * @return �R�[�h�̃J�����T�C�Y
	 */
	public int getCodeColumnSize() {
		return 100;
	}

	/**
	 * ���̂̃J�����T�C�Y���擾���܂�
	 * 
	 * @return ���̂̃J�����T�C�Y
	 */
	public int getNamesColumnSize() {
		return 160;
	}

	/**
	 * �������̂̃J�����T�C�Y���擾���܂�
	 * 
	 * @return �������̂̃J�����T�C�Y
	 */
	public int getNamekColumnSize() {
		return 160;
	}

	/**
	 * ���̂̊񂹂��擾���܂�
	 * 
	 * @return ���̂̊�
	 */
	public int getNamesColumnAlignment() {
		return SwingConstants.LEFT;
	}

	/**
	 * �������̂̊񂹂��擾���܂�
	 * 
	 * @return �������̂̊�
	 */
	public int getNamekColumnAlignment() {
		return SwingConstants.LEFT;
	}

	/**
	 * �����_�C�A���O�̃e�[�u���ۑ��L�[��Ԃ�
	 * 
	 * @return �����_�C�A���O�̃e�[�u���ۑ��L�[
	 */
	public abstract String getTableKeyName();

	/**
	 * @return callBackListener��߂��܂��B
	 */
	public List<TCallBackListener> getCallBackListenerList() {
		return callBackListenerList;
	}

	/**
	 * @param callBackListenerList callBackListener��ݒ肵�܂��B
	 */
	public void setCallBackListenerList(List<TCallBackListener> callBackListenerList) {
		this.callBackListenerList = callBackListenerList;
	}

	/**
	 * @param callBackListener callBackListener��ǉ�����
	 */
	public void addCallBackListener(TCallBackListener callBackListener) {
		if (callBackListenerList == null) {
			callBackListenerList = new ArrayList<TCallBackListener>();
		}
		callBackListenerList.add(callBackListener);
	}

	/**
	 * �ҏW�E�s����
	 * 
	 * @param edit
	 */
	public void setEditable(boolean edit) {
		field.btn.setEnabled(edit);
		field.code.setEditable(edit);
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	public Object getEntity() {
		// ���݃`�F�b�N������ꍇ�A�܂���Entity�����݂����ꍇ�A���݃`�F�b�N���󂯂�Entity��Ԃ�
		if (checkExist || entity != null) {
			return entity;
		}
		// ���݃`�F�b�N�����Ȃ��ꍇ�A���͓r���̖��m��Entity�ł��Ԃ��B�������R�[�h�������͂̏ꍇ��null
		if (Util.isNullOrEmpty(field.code.getText())) {
			return null;
		}
		return getUnspecifiedEntity();

	}

	/**
	 * ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity��Ԃ�
	 * 
	 * @return ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity
	 */
	public abstract Object getUnspecifiedEntity();

	/**
	 * Entity�̃C���X�^���X�t�@�N�g��
	 * 
	 * @return �V�KTReferable
	 */
	public TReferable createEntity() {
		// TODO ���abstract�ɕύX
		return null;
	}

	/**
	 * �l���ς��������Ԃ��B<br>
	 * �����ŁA�u�l���ς�����v= �Ō�ɃR���|�[�l���g���ޔ�����entity��<br>
	 * ���߂�verify��(CallbackListener��after���O)��entity�̔�r�ł���B
	 * 
	 * @return AIS
	 */
	public boolean isValueChanged() {

		if ((oldEntity != null && entity == null) || (oldEntity == null && entity != null)) {
			return true;
		}

		// ���R�[�h
		String oldCode = "";
		if (oldEntity != null) {
			oldCode = Util.avoidNull(oldEntity.getCode());
		}

		String newCode = "";
		if (entity != null) {
			newCode = Util.avoidNull(((TReferable) entity).getCode());
		}

		return !oldCode.equals(newCode);
	}

	/**
	 * �_�C�A���O�̃R�[�h�̏����l�\�����邩�̎擾
	 * 
	 * @return showDefaultCode �_�C�A���O�̃R�[�h�̏����l�\�����邩
	 */
	public boolean isShowDefaultCode() {
		return showDefaultCode;
	}

	/**
	 * �_�C�A���O�̃R�[�h�̏����l�\�����邩�̐ݒ�
	 * 
	 * @param showDefaultCode �_�C�A���O�̃R�[�h�̏����l�\�����邩
	 */
	public void setShowDefaultCode(boolean showDefaultCode) {
		this.showDefaultCode = showDefaultCode;
	}

	/**
	 * ���t�����r
	 * 
	 * @param a
	 * @param b
	 * @return true:����
	 */
	public static boolean equals(Date a, Date b) {
		if (a == null && b != null) {
			return false;
		}
		if (a != null && b == null) {
			return false;
		}
		if (a == null && b == null) {
			return true;
		}

		return DateUtil.equals(a, b);
	}

	/**
	 * �����񓯈��r
	 * 
	 * @param a
	 * @param b
	 * @return true:����
	 */
	public static boolean equals(String a, String b) {
		return Util.avoidNullNT(a).equals(Util.avoidNullNT(b));
	}
}
