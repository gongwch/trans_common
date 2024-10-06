package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.Timer;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * JDialog���p�����AmessageID�C���^�[�t�F�[�X����������Dialog.
 */
public class TDialog extends JDialog implements TInterfaceLangMessageID, TInterfaceTabPolicy {

	/** function key max no. */
	public static final int FUNCTION_KEY_NUM = 15;

	/**  */
	private String messageID = null;

	/** tab�ԍ�. */
	private List<TNameComponent> listTabControlNo = new ArrayList<TNameComponent>();

	/** tab��component */
	private List<Component> listTabComponent = new ArrayList<Component>();

	/** focus policy */
	private TFocusPolicy focusPolicy = null;

	/**  */
	private TFocusPolicyEnter focusPolicyEnter = null;

	/**  */
	private boolean modeEnter = false;

	/**  */
	private Container parent = null;

	/** function key -> action �Ή��t��table. */
	private Map<Integer, JButton[]> functionKeyToActionMap = new HashMap<Integer, JButton[]>();

	/** ���O�ɃA�N�Z�X���ꂽ�R���|�[�l���g */
	private KeyEvent keyEventBefore = null;

	/**  */
	private boolean isVerifierEnabled = true;

	/**
	 * �R���X�g���N�^
	 */
	public TDialog() {
		super();
		this.init();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param frame �e�t���[��
	 * @param modal ���[�_���t���O
	 */
	public TDialog(Frame frame, boolean modal) {
		super(frame, modal);
		this.parent = null;
		this.init();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param dialog �e�_�C�A���O
	 * @param modal ���[�_���t���O
	 */
	public TDialog(Dialog dialog, boolean modal) {
		super(dialog, modal);
		this.parent = dialog;
		this.init();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param win �e�_�C�A���O
	 */
	public TDialog(Window win) {
		super(win);
		this.parent = win;
		this.init();
	}

	/**
	 * component������
	 */
	private void init() {
		functionKeyToActionMap.put(TGuiUtil.WITH_NO_KEY, new JButton[FUNCTION_KEY_NUM]);
		functionKeyToActionMap.put(TGuiUtil.WITH_CTRL_KEY, new JButton[FUNCTION_KEY_NUM]);
		functionKeyToActionMap.put(TGuiUtil.WITH_SHIFT_KEY, new JButton[FUNCTION_KEY_NUM]);
		functionKeyToActionMap.put(TGuiUtil.WITH_ALT_KEY, new JButton[FUNCTION_KEY_NUM]);

		this.initListener();
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		super.setResizable(false);
		super.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
	}

	/**
	 * Listener�̏����ݒ�. <br>
	 * �t�@���N�V�����L�[�̎擾�̂��߂̏������B<br>
	 * KeyListener��ݒ肷��B<br>
	 * Dialog�����Ƃ��ɁA���̓G���[��\�������Ȃ����߂� WindowListener��ݒ肷��B
	 */
	private void initListener() {

		this.addWindowListener(new TWindowListener((Window) this.parent));

		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent evt) {
				paneKeyPressed(evt);
			}
		});
	}

	/**
	 * key���͂̏���
	 * 
	 * @param e
	 */
	private void paneKeyPressed(KeyEvent e) {

		int fkey = TGuiUtil.checkFunctionKeyEvent(e);
		int mkey = TGuiUtil.checkWithKeyEvent(e);

		if (0 < fkey) {
			// this.handleFunctionKey(fkey);
			this.handleFunctionKey(fkey, mkey);
		}
		return;
	}

	/**
	 * ���������L�[�����ɁA�t�@���N�V�����L�[�ƃ{�^���̑Ή��t���e�[�u����Ԃ��B
	 * 
	 * @param withKey ���������L�[ WITH_NO_KEY, WITH_SHIFT_KEY, WITH_CTRL_KEY or WITH_ALT_KEY
	 * @return �t�@���N�V�����L�[�ƃ{�^���Ή��t���e�[�u��
	 */
	private JButton[] getFunctionKeyActionTable(int withKey) {

		if (this.functionKeyToActionMap == null) {
			return null;
		}

		return this.functionKeyToActionMap.get(withKey);
	}

	/**
	 * �t�@���N�V�����L�[�̏������s���B<br>
	 * �o�^���ꂽ�t�@���N�V�����L�[�ƃ{�^���̑Ή��t���e�[�u���ɏ]���A�{�^�����������̏������s���B<br>
	 * �t�@���N�V�����L�[�ƃ{�^���̑Ή��t���e�[�u����o�^�����A<br>
	 * �Ǝ��������s���Ƃ��́Aoverride����B
	 * 
	 * @param fkey �t�@���N�V�����L�[�ԍ� F1 -> F12
	 * @param mkey �����L�[
	 */
	public void handleFunctionKey(int fkey, int mkey) {

		if (this.functionKeyToActionMap == null) {
			return;
		}

		if (fkey <= 0 || FUNCTION_KEY_NUM < fkey) {
			return;
		}

		JButton[] functionKeyToActionTable = this.getFunctionKeyActionTable(mkey);

		if (functionKeyToActionTable == null) {
			return;
		}

		if (functionKeyToActionTable.length < fkey) {
			return;
		}

		JButton trgt = functionKeyToActionTable[fkey - 1];
		if (trgt == null) {
			return;
		}

		// ������Ԃł���Ύ��s���Ȃ��B
		if (!trgt.isEnabled() || !TGuiUtil.isActive(trgt) || !TGuiUtil.getParentFrameOrDialog(trgt).isEnabled()) {
			return;
		}

		// �{�^�������C�x���g���s

		if (trgt.getVerifyInputWhenFocusTarget()) {
			// Verifiy�L���̏ꍇ�̂݃t�H�[�J�X�ړ�
			trgt.requestFocus();
		}

		// �^�C�}�[�ݒ�
		Timer tim = new Timer(300, null);
		tim.setRepeats(false);
		tim.addActionListener(new DoClickListener(tim, trgt));
		tim.start();

		// �^�C�}�[�����s����Ă���Ԃ͑��삪����
		TGuiUtil.getParentFrameOrDialog(trgt).setEnabled(false);
	}

	/**
	 * �V���[�g�J�b�g�L�[���s�̂��߂̃^�C�}�[�C�x���g �x�����s�N���X
	 */
	private class DoClickListener implements ActionListener {

		/** �����{�^�� */
		private JButton btn;

		/** �x�����s�̂��߂�Timer */
		private Timer tim;

		/**
		 * constructor.
		 * 
		 * @param tim
		 * @param btn
		 */
		public DoClickListener(Timer tim, JButton btn) {
			this.tim = tim;
			this.btn = btn;
		}

		/**
		 * �^�C�}�[�������smethod
		 */
		public void actionPerformed(ActionEvent evt) {
			this.tim.stop();

			// ���앜��
			TGuiUtil.getParentFrameOrDialog(btn).setEnabled(true);

			// requestFocus()��A��莞�Ԃ������āA�܂�focus�������Ă����Ƃ�
			if (!this.btn.getVerifyInputWhenFocusTarget() || this.btn.hasFocus()) {
				this.btn.doClick();
			}
		}
	}

	/**
	 * �{�^�����̂ɓo�^���邱��<br>
	 * <s> �t�@���N�V�����L�[�ƃ{�^���̑Ή��t���e�[�u����o�^����B<br>
	 * �t�@���N�V�����L�[���������ꂽ�Ƃ��Atbl[FUNCTION_KEY_NUM]�œo�^���ꂽ�{�^����doClick()���Ăяo��.<br>
	 * F[n]���������ꂽ�Ƃ��Atbl[n-1].doClick()�����s�����B<br>
	 * �Ή�����{�^�����Ȃ��Ƃ���null��ݒ肷��. </s>
	 * 
	 * @deprecated �{�^�����̂ɓo�^���邱��
	 * @param tbl [TPanelBusiness.FUNCTION_KEY_NUM]
	 */
	public void setFunctionKeyToActionTable(JButton[] tbl) {

		this.setFunctionKeyToActionTable(tbl, TGuiUtil.WITH_NO_KEY);
	}

	/**
	 * �{�^�����̂ɓo�^���邱��<br>
	 * <s> �t�@���N�V�����L�[�ƃ{�^���Ɠ��������L�[�̑Ή��t���e�[�u����o�^����B<br>
	 * �t�@���N�V�����L�[���������ꂽ�Ƃ��Atbl[FUNCTION_KEY_NUM]�œo�^���ꂽ�{�^����doClick()���Ăяo��.<br>
	 * F[n]���������ꂽ�Ƃ��Atbl[n-1].doClick()�����s�����B<br>
	 * �Ή�����{�^�����Ȃ��Ƃ���null��ݒ肷��. </s>
	 * 
	 * @deprecated �{�^�����̂ɓo�^���邱��
	 * @param tbl [TPanelBusiness.FUNCTION_KEY_NUM]
	 * @param mkey WITH_SHIFT_KEY | WITH_CTRL_KEY | WIDTH_ALT_KEY�̑g�ݍ��킹
	 */
	public void setFunctionKeyToActionTable(JButton[] tbl, int mkey) {

		this.functionKeyToActionMap.put(mkey, tbl);
	}

	/**
	 * �e�t���[������.
	 * 
	 * @return �e�t���[��
	 */
	public Frame getParentFrame() {
		for (Container p = this.getParent(); p != null; p = p.getParent()) {
			if (p instanceof Frame) {
				return (Frame) p;
			}
		}
		return null;
	}

	/**
	 * �e�_�C�A���O����.
	 * 
	 * @return �e�_�C�A���O
	 */
	public Dialog getParentDialog() {
		for (Container p = this.getParent(); p != null; p = p.getParent()) {
			if (p instanceof Dialog) {
				return (Dialog) p;
			}
		}
		return null;
	}

	/**
	 * Dialog��UI������������������. <br>
	 * ���b�Z�[�W�̕ϊ��\���B �^�u���̐ݒ�B ���b�Z�[�WID�̐ݒ�A�^�u���ԍ��̐ݒ肪���������瑦����method���Ăяo���B
	 */
	protected void initDialog() {

		// �R���|�[�l���g�̕���
		restoreComponent();

		// ���b�Z�[�W�̕ϊ�.
		this.translateLangMessageID();

		// �V���[�g�J�b�g�L�[�o�^(�{�^���o�^���̒ǉ�)
		this.registShortcutKey();

		// tab-key���̐ݒ�.

		// Tab���̎��W
		this.collectTabControlNo();

		// Tab���̓o�^
		TGuiUtil.changeTabControlNo(listTabControlNo, listTabComponent);

		// Tab���̔��f
		// this.setFocusTraversalPolicy(this.focusPolicy);
		this.focusPolicy = new TFocusPolicy(this.listTabComponent);
		this.focusPolicyEnter = new TFocusPolicyEnter(this.listTabComponent);

		this.setTabPolicy();
	}

	/**
	 * �^�u�ړ��̐ݒ�B<br>
	 * applet�����container�K�w�\�z��ɌĂяo���B
	 */
	public void setTabPolicy() {
		this.focusPolicy = new TFocusPolicy(this.listTabComponent);
		Container p = TGuiUtil.getParentRoot(this);
		this.modeEnter = false;
		p.setFocusTraversalPolicy(this.focusPolicy);
	}

	/**
	 * enter-key�ł̃^�u�ړ��̐ݒ�B<br>
	 * 
	 * @param b true:enter-key�ł̈ړ� false:�ʏ�ړ�
	 */
	public void setModeEnter(boolean b) {

		this.modeEnter = b;
		Container p = TGuiUtil.getParentRoot(this);
		p.setFocusTraversalPolicy(b ? this.focusPolicyEnter : this.focusPolicy);
	}

	/**
	 * enter-key�ł̃^�u�ړ��̐ݒ�B<br>
	 * 
	 * @return b true:enter-key�ł̈ړ� false:�ʏ�ړ�
	 */
	public boolean getModeEnter() {

		return this.modeEnter;
	}

	/**
	 * �p�l���̃A�C�e���̃��b�Z�[�WID��ϊ�����.
	 */
	protected void translateLangMessageID() {

		// Dialog�̃^�C�g�����������邽�߁Aroot��container�ȍ~���w�肷��.
		TGuiUtil.translateLangMessageID(this, TClientLoginInfo.getInstance().getUserLanguage());
	}

	/**
	 * �V���[�g�J�b�g�L�[�o�^.
	 */
	protected void registShortcutKey() {
		TGuiUtil.recursiveCollectFunctionKey(this, functionKeyToActionMap, 0);
	}

	/**
	 * tabNo���W.
	 */
	protected void collectTabControlNo() {
		listTabControlNo.clear();
		TGuiUtil.collectTabControlNo(this, this.listTabControlNo);
	}

	/**
	 * interface ����
	 */

	/**
	 * message ID�̐ݒ�
	 * 
	 * @param messageID ���b�Z�[�WID
	 */
	public void setLangMessageID(String messageID) {
		this.messageID = messageID;
	}

	/**
	 * message ID�̎擾
	 * 
	 * @return messageID
	 */
	public String getLangMessageID() {
		return this.messageID;
	}

	// �t�H�[�J�X�ړ��̎��R�x���グ�邽��method �ǉ�

	/**
	 * ���O�ɃA�N�Z�X���ꂽ�R���|�[�l���g setter.
	 * 
	 * @param evt
	 */
	public void setEventBefore(KeyEvent evt) {

		this.keyEventBefore = evt;
	}

	/**
	 * ���O�ɃA�N�Z�X���ꂽKeyEvent getter.
	 * 
	 * @return KeyEvent
	 */
	public KeyEvent getEventBefore() {

		return this.keyEventBefore;
	}

	/**
	 * �z���R���|�[�l���g��Verifier�L���^����
	 * 
	 * @param flag true:�L��
	 */
	public void setVerifierEnabled(boolean flag) {
		TGuiUtil.setComponentsVerifierEnabled(this, flag);
		this.isVerifierEnabled = flag;
	}

	/**
	 * �z���R���|�[�l���g��Verifier�L���^����
	 * 
	 * @return true:�L��
	 */
	public boolean isVerifierEnabled() {
		return this.isVerifierEnabled;
	}

	/**
	 * ���O�C�����[�U�̌���R�[�h�ɑ΂���A���b�Z�[�W��Ԃ�. �w��P��ID�A�܂��͒P����o�C���h.
	 * 
	 * @param messageId ���b�Z�[�WID
	 * @param bindIds �P��ID�A�܂��́A�P��̃��X�g
	 * @return �ϊ����ꂽ���b�Z�[�W
	 */
	protected String getMessage(String messageId, Object... bindIds) {

		return MessageUtil.convertMessage(getLoginLanguage(), messageId, bindIds);
	}

	/**
	 * ���O�C�����[�U�̌���R�[�h�ɑ΂���A�P�ꕶ����Ԃ�.
	 * 
	 * @param wordID �P��ID
	 * @return �P�ꕶ��
	 */
	public String getWord(String wordID) {

		return MessageUtil.getWord(getLoginLanguage(), wordID);
	}

	/**
	 * ���O�C�����[�U�̌���R�[�h�ɑ΂���A�P�ꕶ�����X�g��Ԃ�.
	 * 
	 * @param wordIDs �P��ID���X�g
	 * @return �P�ꕶ�����X�g
	 */
	public String[] getWordList(String[] wordIDs) {

		return MessageUtil.getWordList(getLoginLanguage(), wordIDs);
	}

	/**
	 * ���O�C�����[�U�̌���R�[�h
	 * 
	 * @return ����R�[�h
	 */
	protected String getLoginLanguage() {
		return TClientLoginInfo.getInstance().getUserLanguage();
	}

	/**
	 * Visible:false�̏ꍇ�Adispose()���s���ă������J������.
	 * 
	 * @see java.awt.Component#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);

		if (!isVisible()) {
			this.dispose();
		}
	}

	/**
	 * �f�t�H���g�J�[�\����ݒ�
	 */
	public void setDefaultCursor() {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * WAIT�J�[�\����ݒ�
	 */
	public void setWaitCursor() {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}

	/**
	 * �R���|�[�l���g�𕜌�����
	 */
	protected void restoreComponent() {

		try {
			Class c = this.getClass();
			Field[] fields = c.getFields();

			for (Field field : fields) {
				Object obj = field.get(this);
				if (obj instanceof TStorable) {
					TStorable storable = (TStorable) obj;
					TStorableKey key = storable.getKey();
					if (key == null) {
						key = new TDefaultStorableKey(c, field);
					}
					storable.restoreComponent(key);
				}
			}
		} catch (Exception e) {
			ClientErrorHandler.handledException(e);
		}
	}

	/**
	 * ���b�Z�[�W(����)�擾
	 * 
	 * @param messageId ���b�Z�[�WID
	 * @return �ϊ���̃��b�Z�[�W
	 */
	public String getShortWord(String messageId) {
		return MessageUtil.getShortWord(getLoginLanguage(), messageId);
	}

	/**
	 * @return listTabControlNo
	 */
	public List<TNameComponent> getListTabControlNo() {
		return this.listTabControlNo;
	}

	/**
	 * @return listTabComponent
	 */
	public List<Component> getListTabComponent() {
		return this.listTabComponent;
	}
}
