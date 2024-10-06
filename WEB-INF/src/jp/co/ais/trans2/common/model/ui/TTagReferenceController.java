package jp.co.ais.trans2.common.model.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.tag.*;

/**
 * �Q�ƃt�B�[���h�̃R���g���[�����N���X�B
 * 
 * @author AIS
 */
public class TTagReferenceController extends TController {

	/** �������� */
	protected TagSearchCondition condition;

	/** �����_�C�A���O */
	protected TTagReferenceDialog dialog;

	/** �����t�B�[���h */
	protected TTagReference field;

	/** ���X�g�t�H�[�J�X���̃R�[�h���݃`�F�b�N true;�`�F�b�N���� */
	protected boolean checkExist = true;

	/** TCallBackListener */
	protected List<TCallBackListener> callBackListenerList = null;

	/** �I�𒆂�Entity */
	protected Tag entity;

	/** ��O��Entity */
	protected TReferable oldEntity = null;

	/** �Œ�{�^���̖����擾���܂� */
	protected String fixedButtonCaption = null;

	/** �Œ茟���_�C�A���O���擾���܂� */
	protected String fixedDialogCaption = null;

	/** �_�C�A���O�̃R�[�h�̏����l�\�����邩 */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/** �w�i�J���[ */
	protected Color colorBackColor = null;

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
	 * �R���X�g���N�^
	 * 
	 * @param field �����t�B�[���h
	 */
	public TTagReferenceController(TTagReference field) {
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

		field.btn.setLangMessageID(getButtonCaption1());
		field.btn.setLangMessageID(getButtonCaption2());

		initSearchCondition();

		colorBackColor = field.color.getBackground();
	}

	/**
	 * �����t�B�[���h�Ŕ�������C�x���g��ǉ�����
	 */
	protected void addEvent() {

		// �{�^������
		field.btn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
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
	 * �\�����e������������
	 */

	public void clear() {
		field.code.setText(null);
		field.color.setBackground(colorBackColor);
		field.name.setText(null);
		field.name.setEditable(false);
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
	 * ���������̃C���X�^���X�𐶐�
	 * 
	 * @return �V�K���������̃C���X�^���X
	 */
	protected TagSearchCondition createSearchCondition() {
		return new TagSearchCondition();
	}

	/**
	 * ���������̏�����
	 */

	protected void initSearchCondition() {
		condition = createSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
	}

	/**
	 * �t�B�[���h[�R�[�h]��verify
	 * 
	 * @param comp �R���|�[�l���g
	 * @return verify����
	 */
	public boolean code_Verify(@SuppressWarnings("unused") JComponent comp) {

		// �l���u�����N�̏ꍇ�A���̂�������
		if (Util.isNullOrEmpty(field.getCode())) {
			entity = null;
			field.color.setBackground(colorBackColor);
			field.name.setText(null);
			field.name.setEditable(false);
			return true;
		}
		// �l�̕ύX���Ȃ����̓f�[�^���擾���Ȃ�
		if (!field.code.isValueChanged()) {
			return true;
		}

		// ���͂��ꂽ�R�[�h�ɑ΂��A�R�t���f�[�^���擾����
		entity = getInputtedEntity();

		// �l������΃Z�b�g

		if (entity != null) {

			field.code.setText(getEntity().getCode());
			field.color.setBackground(getEntity().getColor());
			field.name.setText(getEntity().getTitle());
			field.name.setEditable(true);

			return true;

		}
		field.name.setText(null);
		if (checkExist) {
			showMessage(field, "I00123");// �Y���R�[�h�͑��݂��܂���
			field.code.requestFocus();
			field.code.clearOldText();
			return false;
		}
		return true;

	}

	/**
	 * �����_�C�A���O�Ŕ�������C�x���g��ǉ�����
	 */
	protected void addDialogEvent() {

		// �����{�^������
		dialog.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnSearch_Click();
				after_btnSearch_Click();
			}
		});

		// �m��{�^������
		dialog.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnSettle_Click(e);
			}
		});

		// �߂�{�^������
		dialog.btnBack.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
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

		// �������̂�Enter�Ō������s
		dialog.title.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				dialog_code_keyPressed(e);
			}
		});
	}

	/**
	 * �����_�C�A���O[����]�{�^������
	 */
	public void btnSearch_Click() {

		try {

			// �m��{�^���������s�ɂ���
			dialog.btnSettle.setEnabled(false);

			// �ꗗ���N���A����
			dialog.tbl.removeRow();

			// �f�[�^�𒊏o����
			TagSearchCondition condition_ = getCondition().clone();
			// �R�[�h�B������
			condition_.setCodeLike(dialog.code.getText());

			// �������̞B������
			condition_.setTitleLike(dialog.title.getText());

			List<Tag> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");// �f�[�^��������܂���B
				}
				return;
			}

			// �ꗗ�ɃZ�b�g
			for (Tag bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getCode(), " ", bean.getTitle() });
			}

			// �m��{�^���������\�ɂ���
			dialog.btnSettle.setEnabled(true);

			// 1�s�ڂ�I��
			dialog.tbl.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �����_�C�A���O[�m��]�{�^������
	 * 
	 * @param e
	 */

	// �I���f�[�^���ꗗ�ɔ��f
	public void btnSettle_Click(@SuppressWarnings("unused") ActionEvent e) {

		// ��Entity��ޔ�
		oldEntity = createEntity();
		if (oldEntity != null) {
			oldEntity.setCode(field.code.getText());
		}
		btnSettle_Click();
	}

	/**
	 * Class��Ԃ�
	 * 
	 * @return Class
	 */
	// �C���^�[�t�F�C�X�Ƃ̌q����
	protected Class getModelClass() {
		return TagManager.class;
	}

	/**
	 * ���͂��ꂽ�tⳏ���Ԃ�
	 * 
	 * @return Entity
	 */

	protected Tag getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			TagSearchCondition condition_ = this.condition.clone();
			condition_.setTagCode(field.getCode());
			condition_.setTagTitle(field.getName());

			List<Tag> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				return null;
			}
			return list.get(0);

		} catch (Exception e) {
			errorHandler(e);
		}

		return null;
	}

	/**
	 * ���������ɊY������tⳃ��X�g��Ԃ�
	 * 
	 * @param condition_ ��������
	 * @return ���������ɊY������tⳃ��X�g
	 */

	protected List<Tag> getList(TagSearchCondition condition_) {

		try {

			List<Tag> list = (List<Tag>) request(getModelClass(), "get", condition_);

			return list;

		} catch (Exception e) {
			errorHandler(e);
		}

		return null;
	}

	/**
	 * @return �����������擾
	 */
	public TagSearchCondition getCondition() {
		return condition;
	}

	/**
	 * �I�����ꂽ�tⳂ�Ԃ�
	 * 
	 * @return �I�����ꂽ�t�
	 */
	protected Tag getSelectedEntity() {
		return (Tag) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	/**
	 * �����_�C�A���O[�m��]�{�^������
	 */
	public void btnSettle_Click() {
		try {

			// �ꗗ�őI�����ꂽEntity���擾����
			entity = getSelectedEntity();

			// Entity�����݂���΁A�����_�C�A���O�Ăяo�����ɃZ�b�g

			if (entity != null) {
				field.code.setText(getEntity().getCode());
				field.color.setBackground(getEntity().getColor());
				field.name.setText(getEntity().getTitle());
				field.name.setEditable(true);
				
				field.code.pushOldText();// ���̃R���g���[���[�ł�code�̂�

			}

			// �_�C�A���O�����
			dialog.setVisible(false);
			dialog.dispose();

			// �Ăяo�����̃R�[�h�t�B�[���h�Ƀt�H�[�J�X
			field.code.requestFocus();

			if (callBackListenerList != null && !callBackListenerList.isEmpty()) {
				for (TCallBackListener listener : callBackListenerList) {
					listener.after();
					listener.after(entity != null);
					listener.afterVerify(entity != null);
				}
			}

		} catch (Exception e) {
			errorHandler(e);
		}
	}

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
	protected TTagReferenceDialog createDialog() {
		return new TTagReferenceDialog(this);
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
	public String getDialogCaption() {
		return "C12054";
	}

	/**
	 * �{�^���̃L���v�V�������擾���܂�
	 * 
	 * @return �{�^���̃L���v�V����
	 */
	public String getButtonCaption1() {
		return "C12055";
	}

	/**
	 * �{�^���̃L���v�V�������擾���܂�
	 * 
	 * @return �{�^���̃L���v�V����
	 */
	public String getButtonCaption2() {
		return "C12056";
	}

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
	 * �tⳃR�[�h�̃L���v�V�������擾���܂�
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
	 * �tⳐF�̃L���v�V�������擾���܂�
	 * 
	 * @return �tⳐF�̃L���v�V����
	 */
	public String getColorCaption() {
		if (Util.isNullOrEmpty(getFixedDialogCaption())) {
			return getWord(getDialogCaption()) + getWord("C12053"); // �tⳐF
		} else {
			return getWord(getFixedDialogCaption()) + getWord("C12053");
		}
	}

	/**
	 * �tⳃ^�C�g���̃L���v�V�������擾���܂�
	 * 
	 * @return �tⳃ^�C�g���̃L���v�V����
	 */
	public String getTitleCaption() {
		if (Util.isNullOrEmpty(getFixedDialogCaption())) {
			return getWord(getDialogCaption()) + getWord("COP1299"); // �tⳃ^�C�g��
		} else {
			return getWord(getFixedDialogCaption()) + getWord("COP1299");
		}
	}

	/**
	 * �tⳃR�[�h�̃J�����T�C�Y���擾���܂�
	 * 
	 * @return �tⳃR�[�h�̃J�����T�C�Y
	 */
	public int getCodeColumnSize() {
		return 100;
	}

	/**
	 * �tⳐF�̃J�����T�C�Y���擾���܂�
	 * 
	 * @return �tⳐF�̃J�����T�C�Y
	 */
	public int getColorColumnSize() {
		return 100;
	}

	/**
	 * �tⳃ^�C�g���̃J�����T�C�Y���擾���܂�
	 * 
	 * @return �tⳃ^�C�g���̃J�����T�C�Y
	 */
	public int getTitleColumnSize() {
		return 250;
	}

	/**
	 * �tⳃR�[�h�̊񂹂��擾���܂�
	 * 
	 * @return �tⳃR�[�h�̊�
	 */
	public int getCodeColumnAlignment() {
		return SwingConstants.LEFT;
	}

	/**
	 * �tⳐF�̊񂹂��擾���܂�
	 * 
	 * @return �tⳐF�̊�
	 */
	public int getColorColumnAlignment() {
		return SwingConstants.LEFT;
	}

	/**
	 * �tⳃ^�C�g���̊񂹂��擾���܂�
	 * 
	 * @return �tⳃ^�C�g���̊�
	 */
	public int getTitleColumnAlignment() {
		return SwingConstants.LEFT;
	}

	/**
	 * �����_�C�A���O�̃e�[�u���ۑ��L�[��Ԃ�
	 * 
	 * @return �����_�C�A���O�̃e�[�u���ۑ��L�[
	 */
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

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
	public Tag getEntity() {
		// ���݃`�F�b�N������ꍇ�A�܂���Entity�����݂����ꍇ�A���݃`�F�b�N���󂯂�Entity��Ԃ�
		if (checkExist || entity != null) {
			return entity;
		}
		// ���݃`�F�b�N�����Ȃ��ꍇ�A���͓r���̖��m��Entity�ł��Ԃ��B�������R�[�h�������͂̏ꍇ��null
		if (Util.isNullOrEmpty(field.code.getText())) {
			return null;
		}
		return (Tag) getUnspecifiedEntity();

	}

	/**
	 * ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity��Ԃ�
	 * 
	 * @return ���݃`�F�b�N�����Ȃ��ꍇ�̓��͓r�����m��Entity
	 */

	public Object getUnspecifiedEntity() {
		Tag entity_ = new Tag();
		entity_.setCode(field.code.getText());
		return entity_;
	}

	/**
	 * Entity���Z�b�g����
	 * 
	 * @param tag
	 */
	// �I�����������ꗗ��ʂɃZ�b�g
	public void setEntity(Tag tag) {
		if (tag == null) {
			clear();
		} else {
			field.code.setText(tag.getCode());
			field.color.setBackground(tag.getColor());
			field.name.setText(tag.getTitle());
			field.name.setEditable(true);
			entity = tag;
		}

		field.code.pushOldText();
		field.color.clear();
		field.name.pushOldText();
	}

	/**
	 * Entity�̃C���X�^���X�t�@�N�g��
	 * 
	 * @return �V�KTReferable
	 */
	public TReferable createEntity() {
		return null; // return��̊m�F
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	public void refleshEntity() {
		Tag bean = getInputtedEntity();
		setEntity(bean);
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

}
