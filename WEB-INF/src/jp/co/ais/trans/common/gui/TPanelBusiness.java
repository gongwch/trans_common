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
import jp.co.ais.trans.common.gui.event.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * TPanel���p�������A�Ɩ��p�l���̊�ՂƂȂ�Panel.
 */
public class TPanelBusiness extends TPanel implements TInterfaceTabPolicy {

	/** function key max no. */
	public static final int FUNCTION_KEY_NUM = 15;

	/** tab�ԍ�. */
	protected List<TNameComponent> listTabControlNo = new ArrayList<TNameComponent>();

	/** tab��component */
	protected List<Component> listTabComponent = new ArrayList<Component>();

	/** focus policy */
	protected TFocusPolicy focusPolicy = null;

	/** �^�u���t�H�[�J�X�ړ��|���V�[(Enter key�̂Ƃ�) */
	protected TFocusPolicyEnter focusPolicyEnter = null;

	/** true:enter-key�ł̈ړ� */
	protected boolean modeEnter = false;

	/** function key -> action �Ή��t��table. */
	protected Map<Integer, JButton[]> functionKeyToActionMap = new HashMap<Integer, JButton[]>();

	/** Verifier�L��/���� */
	protected boolean isVerifierEnabled = true;

	/** ���O�ɃA�N�Z�X���ꂽ�R���|�[�l���g */
	protected KeyEvent keyEventBefore = null;

	/** �v���O�����R�[�h */
	protected String programCode = null;

	/**
	 * �R���X�g���N�^
	 */
	public TPanelBusiness() {
		super();
		this.initListener();

		functionKeyToActionMap.put(TGuiUtil.WITH_NO_KEY, new JButton[FUNCTION_KEY_NUM]);
		functionKeyToActionMap.put(TGuiUtil.WITH_CTRL_KEY, new JButton[FUNCTION_KEY_NUM]);
		functionKeyToActionMap.put(TGuiUtil.WITH_SHIFT_KEY, new JButton[FUNCTION_KEY_NUM]);
		functionKeyToActionMap.put(TGuiUtil.WITH_ALT_KEY, new JButton[FUNCTION_KEY_NUM]);
	}

	/**
	 * Listener�̏����ݒ�. <br>
	 * �t�@���N�V�����L�[�̎擾�̂��߂̏������B<br>
	 * KeyListener��ݒ肷��B<br>
	 */
	private void initListener() {

		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent evt) {
				paneKeyPressed(evt);
			}
		});
	}

	/**
	 * Panel��UI������������������. <br>
	 * ���b�Z�[�W�̕ϊ��\���B �^�u���̐ݒ�B<br>
	 * ���b�Z�[�WID�̐ݒ�A�^�u���ԍ��̐ݒ肪���������瑦����method���Ăяo���B
	 */
	public void initPanel() {

		// �R���|�[�l���g�̕���
		restoreComponent();

		// ���b�Z�[�W�̕ϊ�.
		this.translateLangMessageID();

		// �V���[�g�J�b�g�L�[�o�^(�{�^���o�^���̒ǉ�)
		this.registShortcutKey();

		// tab-key���̐ݒ�.

		// �^�u�ړ��ŏ�ʃt�H�[�J�X�T�C�N���Ƀt�H�[�J�X��߂��Ȃ�
		// super.setFocusCycleRoot(true);

		// Tab���̎��W
		this.collectTabControlNo();

		// Tab���̓o�^
		TGuiUtil.changeTabControlNo(listTabControlNo, listTabComponent);

		// Tab���̔��f
		this.focusPolicy = new TFocusPolicy(this.listTabComponent);
		this.focusPolicyEnter = new TFocusPolicyEnter(this.listTabComponent);

		TGuiUtil.getParentRoot(this).setFocusTraversalPolicy(this.focusPolicy);

	}

	/**
	 * �^�u�ړ��̐ݒ�B<br>
	 * applet�����container�K�w�\�z��ɌĂяo���B
	 */
	public void setTabPolicy() {
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
	 * key���͂̏���
	 * 
	 * @param e
	 */
	private void paneKeyPressed(KeyEvent e) {

		int fkey = TGuiUtil.checkFunctionKeyEvent(e);
		int mkey = TGuiUtil.checkWithKeyEvent(e);

		if (0 < fkey) {
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
	 * TButton.setShortcutKey()�𗘗p���Ă�������. <br>
	 * <s> �t�@���N�V�����L�[�ƃ{�^���̑Ή��t���e�[�u����o�^����B<br>
	 * �t�@���N�V�����L�[���������ꂽ�Ƃ��Atbl[FUNCTION_KEY_NUM]�œo�^���ꂽ�{�^����doClick()���Ăяo��.<br>
	 * F[n]���������ꂽ�Ƃ��Atbl[n-1].doClick()�����s�����B<br>
	 * �Ή�����{�^�����Ȃ��Ƃ���null��ݒ肷��. </s>
	 * 
	 * @deprecated �V���[�g�J�b�g�̓{�^���ɓo�^
	 * @param tbl TPanelBusiness.FUNCTION_KEY_NUM
	 */
	public void setFunctionKeyToActionTable(JButton[] tbl) {

		this.setFunctionKeyToActionTable(tbl, TGuiUtil.WITH_NO_KEY);
	}

	/**
	 * TButton.setShortcutKey()�𗘗p���Ă�������. <br>
	 * <s> �t�@���N�V�����L�[�ƃ{�^���Ɠ��������L�[�̑Ή��t���e�[�u����o�^����B<br>
	 * �t�@���N�V�����L�[���������ꂽ�Ƃ��Atbl[FUNCTION_KEY_NUM]�œo�^���ꂽ�{�^����doClick()���Ăяo��.<br>
	 * F[n]���������ꂽ�Ƃ��Atbl[n-1].doClick()�����s�����B<br>
	 * �Ή�����{�^�����Ȃ��Ƃ���null��ݒ肷��. </s>
	 * 
	 * @deprecated �V���[�g�J�b�g�̓{�^���ɓo�^
	 * @param tbl TPanelBusiness.FUNCTION_KEY_NUM
	 * @param mkey WITH_SHIFT_KEY | WITH_CTRL_KEY | WIDTH_ALT_KEY�̑g�ݍ��킹
	 */
	public void setFunctionKeyToActionTable(JButton[] tbl, int mkey) {

		this.functionKeyToActionMap.put(mkey, tbl);
	}

	/**
	 * for MouseListener
	 */
	public void panelRequestFocus() {
		this.requestFocus(); // �L�[�{�[�h�t�H�[�J�X�̐ݒ�
	}

	// �t�H�[�J�X�ړ��̎��R�x���グ�邽��method �ǉ�

	/**
	 * ���O�ɃA�N�Z�X���ꂽKeyEvent setter.
	 * 
	 * @param evt KeyEvent
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
	 * �擪�̓��̓A�C�e���Ƀt�H�[�J�X���ړ�����B
	 */
	public void transferFocusTopItem() {

		Component comp = focusPolicyEnter.getDefaultComponent(null);

		// �L���Ȑ擪�u���́v�A�C�e�����Ȃ���΁ATAB�ړ��̐擪�A�C�e����
		// �t�H�[�J�X����B

		if (comp == null) {
			comp = focusPolicy.getDefaultComponent(null);
		}

		if (comp != null) {
			comp.requestFocus();
		}
	}

	/**
	 * �z���R���|�[�l���g��Verifier�L���^����
	 * 
	 * @param flag
	 */
	public void setVerifierEnabled(boolean flag) {
		TGuiUtil.setComponentsVerifierEnabled(this.getParentFrame(), flag);
		this.isVerifierEnabled = flag;
	}

	/**
	 * �z���R���|�[�l���g��Verifier�L���^����
	 * 
	 * @return �L���^����
	 */
	public boolean isVerifierEnabled() {
		return this.isVerifierEnabled;
	}

	/**
	 * ���݂̃J�[�\����Ԃ�WAIT���ǂ����𔻒�
	 * 
	 * @return WAIT��Ԃł���ꍇtrue
	 */
	public boolean isWaitCursorNow() {
		return Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR).equals(this.getCursor());
	}

	/**
	 * ���O�C�����[�U�̌���R�[�h�ɑ΂���A���b�Z�[�W��Ԃ�. �w��P��ID�A�܂��͒P����o�C���h.
	 * 
	 * @param messageID ���b�Z�[�WID
	 * @param bindIds �P��ID�A�܂��́A�P��̃��X�g
	 * @return �ϊ����ꂽ���b�Z�[�W
	 */
	protected String getMessage(String messageID, Object... bindIds) {

		return MessageUtil.convertMessage(getLoginLanguage(), messageID, bindIds);
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
	 * ���O�C�����[�U�̌���R�[�h�ɑ΂���A�P�ꕶ��(����)��Ԃ�.
	 * 
	 * @param wordID �P��ID
	 * @return �P�ꕶ��
	 */
	public String getShortWord(String wordID) {

		return MessageUtil.getShortWord(getLoginLanguage(), wordID);
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
	 * �t�@���N�V�����L�[�C�x���g��o�^����.<br>
	 * �f�t�H���gF1�L�[
	 * 
	 * @param listener �t�@���N�V�����L�[�C�x���g
	 */
	protected void addFunctionKeyListener(final FunctionKeyListener listener) {
		addFunctionKeyListener(listener, KeyEvent.VK_F1);
	}

	/**
	 * �t�@���N�V�����L�[�C�x���g��o�^����.
	 * 
	 * @param listener �t�@���N�V�����L�[�C�x���g
	 * @param keyCode �L�[�R�[�h(KeyEvent.VK_F1�`F12)
	 */
	protected void addFunctionKeyListener(final FunctionKeyListener listener, final int keyCode) {

		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == keyCode) {
					listener.setRepeats(false);
					listener.start();
				}
			}
		});
	}

	/**
	 * �v���O����ID
	 * 
	 * @return �v���O����ID
	 */
	public String getProgramCode() {
		return programCode;
	}

	/**
	 * �v���O����ID
	 * 
	 * @param programCode �v���O����ID
	 */
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	/**
	 * �R���|�[�l���g�𕜌�����
	 */
	protected void restoreComponent() {
		try {
			restoreComponent(this.getClass());
		} catch (Exception e) {
			ClientErrorHandler.handledException(e);
		}
	}

	/**
	 * �w��N���X�̃t�B�[���h�̃R���|�[�l���g�𕜌�����
	 * 
	 * @param c �w��N���X
	 * @throws Exception
	 */
	protected void restoreComponent(Class c) throws Exception {
		if (c == null || TPanelBusiness.class.equals(c)) {
			return;
		}

		// �e�N���X�̕���
		restoreComponent(c.getSuperclass());

		Field[] fields = c.getDeclaredFields(); // public�̂݁�private���܂�

		for (Field field : fields) {
			field.setAccessible(true); // �A�N�Z�X�\
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
	}

	/**
	 * @param list
	 */
	public void setListTabControlNo(List<TNameComponent> list) {
		this.listTabControlNo = list;
	}

	/**
	 * @param list
	 */
	public void setListTabComponent(List<Component> list) {
		this.listTabComponent = list;

		this.focusPolicy = new TFocusPolicy(list);
		this.focusPolicyEnter = new TFocusPolicyEnter(list);
	}
}
