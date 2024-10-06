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
import jp.co.ais.trans2.common.model.ui.*;

/**
 * JFrame���p�����AmessageID�C���^�[�t�F�[�X����������Frame.
 */
// TODO Delegater�ɓZ�߂�
public class TFrame extends JFrame implements TInterfaceLangMessageID, TInterfaceTabPolicy {

	/** function key max no. */
	public static final int FUNCTION_KEY_NUM = 15;

	private String messageID = null;

	/** tab�ԍ�. */
	private List<TNameComponent> listTabControlNo = new ArrayList<TNameComponent>();

	/** tab��component */
	private List<Component> listTabComponent = new ArrayList<Component>();

	/** focus policy */
	private TFocusPolicy focusPolicy = null;

	private TFocusPolicyEnter focusPolicyEnter = null;

	private boolean modeEnter = false;

	/** function key -> action �Ή��t��table. */
	private Map<Integer, JButton[]> functionKeyToActionMap = new HashMap<Integer, JButton[]>();

	/** ���O�ɃA�N�Z�X���ꂽ�R���|�[�l���g */
	private KeyEvent keyEventBefore = null;

	/** VerifierEnabled */
	private boolean isVerifierEnabled = true;

	/** �e�R���|�[�l���g */
	private Component parent;

	/** ���[�_���u���b�N�t���O */
	private boolean blocking;

	/**
	 * �R���X�g���N�^.
	 */
	public TFrame() {
		this(null, "");
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param titleID �^�C�g��
	 */
	public TFrame(String titleID) {
		this(null, titleID);
	}

	/**
	 * �R���X�g���N�^(���[�_�����[�h)
	 * 
	 * @param cont �Ăяo�����R���e�i
	 */
	public TFrame(Container cont) {
		this(cont, "");
	}

	/**
	 * �R���X�g���N�^(���[�_�����[�h)
	 * 
	 * @param cont �Ăяo�����R���e�i
	 * @param titleID �^�C�g��
	 */
	public TFrame(Container cont, String titleID) {
		super();

		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		this.setTitle(getWord(titleID));

		this.init();

		if (cont != null) {
			this.parent = TGuiUtil.getParentFrameOrDialog2(cont);

			if (parent == null) {
				// �e�E�B���h�E�����݂��܂���B
				throw new IllegalArgumentException(getMessage("I00252"));
			}
		}

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				modalEnd();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				modalEnd();
			}
		});
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
	}

	/**
	 * Listener�̏����ݒ�. <br>
	 * �t�@���N�V�����L�[�̎擾�̂��߂̏������B<br>
	 * KeyListener��ݒ肷��B<br>
	 * Dialog�����Ƃ��ɁA���̓G���[��\�������Ȃ����߂� WindowListener��ݒ肷��B
	 */
	private void initListener() {

		this.addWindowListener(new TWindowListener(null));

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
	 * Dialog��UI������������������. <br>
	 * ���b�Z�[�W�̕ϊ��\���B �^�u���̐ݒ�B ���b�Z�[�WID�̐ݒ�A�^�u���ԍ��̐ݒ肪���������瑦����method���Ăяo���B
	 */
	protected void initFrame() {

		// �R���|�[�l���g�̕���
		restoreComponent();

		// ���b�Z�[�W�̕ϊ�.

		// message�̐ݒ�
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

	// interface ����

	/**
	 * message ID�̐ݒ�
	 * 
	 * @param messageID ���b�Z�[�WID
	 */
	public void setLangMessageID(String messageID) {
		this.messageID = messageID;

		this.setTitle(getWord(messageID));
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
	public void setVisible(final boolean b) {
		super.setVisible(b);

		if (parent != null) {
			if (isVisible()) {
				modalStart();
			} else {
				modalEnd();
			}
		}
	}

	/**
	 * ���[�_���J�n
	 */
	private void modalStart() {
		if (parent == null) return;

		parent.setEnabled(false);

		if (parent instanceof Window) {
			((Window) parent).setFocusableWindowState(false);
		}

		blocking = true;
	}

	/**
	 * ���[�_���I��
	 */
	private void modalEnd() {
		if (parent == null || !blocking) return;

		blocking = false;

		if (parent instanceof Window) {
			((Window) parent).setFocusableWindowState(true);
		}

		parent.setEnabled(true);
		parent.setVisible(true);
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
		 * 
		 * @param evt
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
}
