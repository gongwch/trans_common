package jp.co.ais.trans.common.client;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.print.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.common.ui.*;

/**
 * Applet��ʃR���g���[���̃x�[�X�N���X.<br>
 * ��ʑ���̋��ʏ���
 */
public class TAppletClientBase extends TRequestBase {

	/** �_�E�����[�h�p�t�@�C���`���[�U�[ */
	private static JFileChooser openChooser = new JFileChooser();

	/** JOptionPane�́u�����v�{�^�����b�Z�[�W�I�v�V���� */
	private static String okOption;

	/** JOptionPane�́u�͂��v�u�������v�{�^�����b�Z�[�W�I�v�V���� */
	private static String[] yesNoOptions;

	/** �}���`�X���b�h�u���b�N�p�̃_�~�[�C���X�^���X */
	private static String dummy = "";

	static {
		openChooser.setMultiSelectionEnabled(false);
		openChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		openChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		openChooser.setAcceptAllFileFilterUsed(false);

		okOption = ClientConfig.getOkButtonText();
		yesNoOptions = ClientConfig.getYesNoButtonWords();
	}

	/** �t�@�C���A�b�v���[�h����PATH */
	private String filePath;

	/**
	 * ���O�C�����[�UID�擾
	 * 
	 * @return ���O�C�����[�UID
	 */
	protected String getLoginUserID() {
		if (TLoginInfo.getUser() != null && !Util.isNullOrEmpty(TLoginInfo.getUser().getCode())) {
			return TLoginInfo.getUser().getCode();
		}
		return TClientLoginInfo.getInstance().getUserCode();
	}

	/**
	 * ���O�C�����[�U�̌���R�[�h
	 * 
	 * @return ����R�[�h
	 */
	protected String getLoginLanguage() {
		String lang = null;
		if (TLoginInfo.getUser() != null && !Util.isNullOrEmpty(TLoginInfo.getUser().getLanguage())) {
			lang = TLoginInfo.getUser().getLanguage();
		} else {
			lang = TClientLoginInfo.getInstance().getUserLanguage();
		}

		if (Util.isNullOrEmpty(lang)) {
			return "ja";
		}

		return lang;
	}

	/**
	 * ���O�C�����[�U��ЃR�[�h�擾
	 * 
	 * @return ��ЃR�[�h
	 */
	protected String getLoginUserCompanyCode() {
		if (TLoginInfo.getCompany() != null && !Util.isNullOrEmpty(TLoginInfo.getCompany().getCode())) {
			return TLoginInfo.getCompany().getCode();
		}
		return TClientLoginInfo.getInstance().getCompanyCode();
	}

	/**
	 * ���O�C�����[�U���擾
	 * 
	 * @return ���[�U��
	 */
	protected String getLoginUserName() {
		if (TLoginInfo.getUser() != null && !Util.isNullOrEmpty(TLoginInfo.getUser().getName())) {
			return TLoginInfo.getUser().getName();
		}
		return TClientLoginInfo.getInstance().getUserName();
	}

	/**
	 * �A�b�v���[�h���̃t�@�C���p�X��Ԃ�
	 * 
	 * @return �t�@�C���p�X
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * ��ʎ擾�ioverride�p�j<br>
	 * �R���g���[��������Panel or Dialog��Ԃ�.<br>
	 * 
	 * @return ���
	 */
	public Container getView() {
		return getPanel();
	}

	/**
	 * ���C���p�l����TPanelCtrlBase�N���X���p�����邱��<br>
	 * <s>�p�l���擾�ioverride�p�j</s>
	 * 
	 * @return �p�l��
	 * @deprecated ���C���p�l����TPanelCtrlBase�N���X���p�����邱��
	 */
	public JPanel getPanel() {
		return null;
	}

	/**
	 * �ʐM���ʂ̃G���[�l�p�G���[�n���h�����O. <br>
	 * getView()��e�R���|�[�l���g�Ƃ��Ĉ���.
	 */
	public void errorHandler() {
		errorHandler(getView());
	}

	/**
	 * �G���[�n���h�����O<br>
	 * getView()��e�R���|�[�l���g�Ƃ��Ĉ���.
	 * 
	 * @param ex ����Exception
	 */
	public void errorHandler(Exception ex) {
		errorHandler(getView(), ex);
	}

	/**
	 * �G���[�n���h�����O<br>
	 * getView()��e�R���|�[�l���g�Ƃ��Ĉ���.
	 * 
	 * @param ex ����Exception
	 * @param messageID ���b�Z�[�WID
	 * @param bindIds ���b�Z�[�W�o�C���h�����̒P��ID
	 */
	public void errorHandler(Exception ex, String messageID, Object... bindIds) {
		errorHandler(getView(), ex, messageID, bindIds);
	}

	/**
	 * �ʐM���ʂ̃G���[�l�p�G���[�n���h�����O. <br>
	 * �G���[���ʂ́A�����I�Ɏ擾.
	 * 
	 * @param parent �e�R���|�[�l���g
	 */
	public void errorHandler(Component parent) {

		String key = getErrorCode();
		String notice = getErrorMessage();

		if (!Util.isNullOrEmpty(notice)) {
			int category = MessageUtil.getCategory(getLoginLanguage(), key);
			showMessageDialog(parent, notice, category);

		} else if (!Util.isNullOrEmpty(key)) {

			showMessage(parent, key);
		} else {

			showMessage(parent, "E00009");
		}
	}

	/**
	 * �G���[�n���h�����O<br>
	 * TException�̏ꍇ�́A�ێ����Ă��郁�b�Z�[�W��\��.<br>
	 * ����ȊO�́u����ɏ�������܂���ł����v�̃��b�Z�[�W��\������.
	 * 
	 * @param parent �e�R���|�[�l���g
	 * @param ex ����Exception
	 */
	public void errorHandler(Component parent, Throwable ex) {

		if (ex instanceof TRequestException) {
			errorHandler(parent);

		} else if (ex instanceof TException) {
			TException tex = (TException) ex;
			TRuntimeException trex = null;
			ClientLogger.debug(tex);

			while (tex != null) {
				if (tex.getCause() instanceof TException) {
					tex = (TException) tex.getCause();
					continue;
				} else if (tex.getCause() instanceof TRuntimeException) {
					trex = (TRuntimeException) tex.getCause();
					break;
				}
				break;
			}

			if (trex == null) {
				showMessage(parent, tex.getMessageID(), tex.getMessageArgs());
			} else {
				showMessage(parent, trex.getMessageID(), trex.getMessageArgs());
			}

		} else if (ex instanceof TRuntimeException) {
			TRuntimeException trex = (TRuntimeException) ex;
			ClientLogger.debug(trex);

			Throwable twe = trex.getCause();

			while (twe != null) {
				if (!(twe.getCause() instanceof TException || twe.getCause() instanceof TRuntimeException)) {
					break;
				}

				twe = twe.getCause();
			}

			if (twe != null) {
				if (twe instanceof TException) {
					TException tex = (TException) twe;

					showMessage(parent, tex.getMessageID(), tex.getMessageArgs());
					return;

				} else if (twe instanceof TRuntimeException) {
					trex = (TRuntimeException) twe;
				}
			}

			showMessage(parent, trex.getMessageID(), trex.getMessageArgs());

		} else {
			errorHandler(parent, ex, "E00009");
		}
	}

	/**
	 * �G���[�n���h�����O
	 * 
	 * @param parent �e�R���|�[�l���g
	 * @param ex ����Exception
	 * @param messageID ���b�Z�[�WID
	 * @param bindIds ���b�Z�[�W�o�C���h�����̒P��ID
	 */
	public void errorHandler(Component parent, Throwable ex, String messageID, Object... bindIds) {
		ClientErrorHandler.handledException(ex);
		showMessage(parent, messageID, bindIds);
	}

	/**
	 * �G���[�n���h�����O
	 * 
	 * @param parent �e�R���|�[�l���g
	 * @param messageID ���b�Z�[�WID
	 * @param bindIds ���b�Z�[�W�o�C���h�����̒P��ID
	 */
	public void errorHandler(Component parent, String messageID, Object... bindIds) {
		this.showMessage(parent, messageID, bindIds);
	}

	/**
	 * �w�肳�ꂽID�ɑΉ����郁�b�Z�[�W��\��
	 * 
	 * @param parent �e�R���|�[�l���g
	 * @param messageID ���b�Z�[�WID
	 * @param bindIds ���b�Z�[�W�o�C���h�����̒P��ID
	 */
	public void showMessage(Component parent, String messageID, Object... bindIds) {

		if (Util.isNullOrEmpty(messageID)) {
			messageID = "E00009";
		}

		int category = MessageUtil.getCategory(getLoginLanguage(), messageID);
		String message = getMessage(messageID, bindIds);

		showMessageDialog(parent, message, category);
	}

	/**
	 * �w�肳�ꂽID�ɑΉ����郁�b�Z�[�W��\��.<br>
	 * �e�R���|�[�l���g�� getView() �𗘗p
	 * 
	 * @param messageID ���b�Z�[�WID
	 * @param bindIds ���b�Z�[�W�o�C���h�����̒P��ID
	 */
	public void showMessage(String messageID, Object... bindIds) {
		showMessage(getView(), messageID, bindIds);
	}

	/**
	 * @param parent
	 * @param message
	 * @param category
	 */
	public void showMessageDialog(Component parent, String message, int category) {
		Component target = getTargetParent(parent);

		if (okOption == null) {
			JOptionPane.showMessageDialog(target, message, "", category);

		} else {
			JOptionPane.showOptionDialog(target, message, "", JOptionPane.DEFAULT_OPTION, category, null,
				new String[] { okOption }, okOption);
		}
	}

	/**
	 * �w�肳�ꂽ���b�Z�[�W���ꗗ�ŕ\������
	 * 
	 * @param comp �Ăяo�����R���|�[�l���g
	 * @param msgs ���b�Z�[�W
	 */
	public void showListMessage(Component comp, String... msgs) {
		this.showListMessage(comp, Arrays.asList(msgs));
	}

	/**
	 * �w�肳�ꂽ���b�Z�[�W���ꗗ�ŕ\������
	 * 
	 * @param comp �Ăяo�����R���|�[�l���g
	 * @param msgList ���b�Z�[�W���X�g
	 */
	public void showListMessage(Component comp, List<String> msgList) {

		MessageListDialog dialog;
		if (comp instanceof Frame) {
			dialog = new MessageListDialog((Frame) comp);

		} else if (comp instanceof Dialog) {
			dialog = new MessageListDialog((Dialog) comp);

		} else if (comp instanceof TPanel) {
			dialog = new MessageListDialog(((TPanel) comp).getParentFrame());

		} else {
			Component parent = TGuiUtil.getParentFrameOrDialog2(comp);

			if (parent instanceof Frame) {
				dialog = new MessageListDialog((Frame) parent);

			} else if (parent instanceof Dialog) {
				dialog = new MessageListDialog((Dialog) parent);

			} else {
				throw new IllegalArgumentException("Wrong parent window");
			}
		}

		dialog.setMessageList(msgList);

		// ���b�Z�[�W�ꗗ�\���_�C�A���O��\������
		dialog.setVisible(true);
	}

	/**
	 * �w�肳�ꂽID�ɑΉ�����m�F���b�Z�[�W��\��.<br>
	 * �e�R���|�[�l���g�� getView() �𗘗p
	 * 
	 * @param messageID ���b�Z�[�WID
	 * @param bindIds ���b�Z�[�W�o�C���h�����̒P��ID
	 * @return Yes���������ꂽ�ꍇtrue���Ԃ�
	 */
	public boolean showConfirmMessage(String messageID, Object... bindIds) {
		return showConfirmMessage(getView(), messageID, bindIds);
	}

	/**
	 * �w�肳�ꂽID�ɑΉ�����m�F���b�Z�[�W��\��
	 * 
	 * @param parent �e�R���|�[�l���g
	 * @param messageID ���b�Z�[�WID
	 * @param bindIds ���b�Z�[�W�o�C���h�����̒P��ID
	 * @return Yes���������ꂽ�ꍇtrue���Ԃ�
	 */
	public boolean showConfirmMessage(Component parent, String messageID, Object... bindIds) {
		return showConfirmMessage(parent, FocusButton.YES, messageID, bindIds);
	}

	/**
	 * �w�肳�ꂽID�ɑΉ�����m�F���b�Z�[�W��\��.<br>
	 * �e�R���|�[�l���g�� getView()�ugetPanel()�v �𗘗p
	 * 
	 * @param btn �����t�H�[�J�X 0:�͂� 1:������
	 * @param messageID ���b�Z�[�WID
	 * @param bindIds ���b�Z�[�W�o�C���h�����̒P��ID
	 * @return Yes���������ꂽ�ꍇtrue���Ԃ�
	 */
	protected boolean showConfirmMessage(FocusButton btn, String messageID, Object... bindIds) {
		return showConfirmMessage(getView(), btn, messageID, bindIds);
	}

	/**
	 * �w�肳�ꂽID�ɑΉ�����m�F���b�Z�[�W��\��
	 * 
	 * @param parent �e�R���|�[�l���g
	 * @param btn �����t�H�[�J�X 0:�͂� 1:������
	 * @param messageID ���b�Z�[�WID
	 * @param bindIds ���b�Z�[�W�o�C���h�����̒P��ID
	 * @return Yes���������ꂽ�ꍇtrue���Ԃ�
	 */
	protected boolean showConfirmMessage(Component parent, FocusButton btn, String messageID, Object... bindIds) {

		Component target = getTargetParent(parent);

		String message = getMessage(messageID, bindIds);

		FocusButton def = btn;

		if (!FocusButton.YES.equals(btn) && !FocusButton.NO.equals(btn)) {

			def = FocusButton.YES;
		}

		int res = TOptionPane.showOptionDialog(target, message, "", JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE, null, yesNoOptions, def.value);

		return res == JOptionPane.YES_OPTION;
	}

	/**
	 * ���[�_������ΏۂƂȂ�(���)�R���|�[�l���g���擾
	 * 
	 * @param comp �ΏۃR���|�[�l���g
	 * @return �ΏۃR���|�[�l���g
	 */
	protected Component getTargetParent(Component comp) {

		if (comp == null) {
			return TAppletMain.instance;
		}
		return TGuiUtil.getParentFrameOrDialog2(comp);
	}

	/**
	 * ���O�C�����[�U�̌���R�[�h�ɑ΂���A���b�Z�[�W��Ԃ�. �w��P��ID�A�܂��͒P����o�C���h.
	 * 
	 * @param messageID ���b�Z�[�WID
	 * @param bindIds �P��ID�A�܂��́A�P��̃��X�g
	 * @return �ϊ����ꂽ���b�Z�[�W
	 */
	public String getMessage(String messageID, Object... bindIds) {

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
	 * @return �P�ꕶ��(����)
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
	 * �t�@�C���_�E�����[�h
	 * 
	 * @param cont ���R���|�[�l���g
	 * @param servletName �Ώ�Servlet��
	 * @return true:����
	 */
	protected boolean download(Container cont, String servletName) {
		return download(cont, servletName, Collections.EMPTY_MAP);
	}

	/**
	 * �t�@�C���_�E�����[�h���\��
	 * 
	 * @param cont ���R���|�[�l���g
	 * @param servletName �Ώ�Servlet��
	 * @param funcArgs ���n���p�����[�^
	 * @return true:����
	 */
	protected boolean download(Container cont, String servletName, Map funcArgs) {
		try {

			downloadNative(servletName, funcArgs);

			// �V�������@�Ńt�@�C���o��
			preview();

			return true;

		} catch (TRequestException e) {
			errorHandler(cont);
			return false;

		} catch (TException ex) {
			errorHandler(cont, ex);
			return false;
		}
	}

	/**
	 * �t�@�C���_�E�����[�h.(�쐬�̂�)
	 * 
	 * @param cont ���R���|�[�l���g
	 * @param servletName �Ώ�Servlet��
	 * @param funcArgs ���n���p�����[�^
	 * @param dirPath �t�@�C���ۑ��f�B���N�g���p�X
	 * @return true:����
	 */
	protected boolean downloadFileCreateOnly(Container cont, String servletName, Map funcArgs, String dirPath) {

		try {
			// �T�[�o����
			downloadNative(servletName, funcArgs);

			// �t�@�C���W�J.
			createResultFile(dirPath);

			return true;

		} catch (TRequestException e) {
			errorHandler(cont);
			return false;

		} catch (IOException ex) {
			errorHandler(cont, ex, "W01079", ex.getMessage());
			return false;

		} catch (TException ex) {
			errorHandler(cont, ex);
			return false;
		}
	}

	/**
	 * �t�@�C���_�E�����[�h�����.<br>
	 * ���[�U�̃f�t�H���g�v�����^���d�l����.
	 * 
	 * @param cont ���R���|�[�l���g
	 * @param servletName �Ώ�Servlet��
	 * @return true:����
	 */
	protected boolean downloadPDFAndPrint(Container cont, String servletName) {

		return this.downloadPDFAndPrint(cont, servletName, Collections.EMPTY_MAP);
	}

	/**
	 * �t�@�C���_�E�����[�h�����.<br>
	 * ���[�U�̃f�t�H���g�v�����^���d�l����.
	 * 
	 * @param cont ���R���|�[�l���g
	 * @param servletName �Ώ�Servlet��
	 * @param funcArgs ���n���p�����[�^
	 * @return true:����
	 */
	protected boolean downloadPDFAndPrint(Container cont, String servletName, Map funcArgs) {

		return this.downloadPDFAndPrint(cont, servletName, funcArgs, null);
	}

	/**
	 * �t�@�C���_�E�����[�h�����.<br>
	 * �N���C�A���g���̃v�����^�ݒ肩��v�����^�����ʂ���.
	 * 
	 * @param cont ���R���|�[�l���g
	 * @param servletName �Ώ�Servlet��
	 * @return true:����
	 */
	protected boolean downloadPDFAndPrintByPrinterSet(Container cont, String servletName) {

		return this.downloadPDFAndPrintByPrinterSet(cont, servletName, Collections.EMPTY_MAP);
	}

	/**
	 * �t�@�C���_�E�����[�h�����.<br>
	 * �N���C�A���g���̃v�����^�ݒ肩��v�����^�����ʂ���.
	 * 
	 * @param cont ���R���|�[�l���g
	 * @param servletName �Ώ�Servlet��
	 * @param funcArgs ���n���p�����[�^
	 * @return true:����
	 */
	protected boolean downloadPDFAndPrintByPrinterSet(Container cont, String servletName, Map funcArgs) {
		PrintService service = TClientLoginInfo.getPrintService();

		return this.downloadPDFAndPrint(cont, servletName, funcArgs, service == null ? null : service.getName());
	}

	/**
	 * �t�@�C���_�E�����[�h�����
	 * 
	 * @param cont ���R���|�[�l���g
	 * @param servletName �Ώ�Servlet��
	 * @param funcArgs ���n���p�����[�^
	 * @param printerName �v�����^��
	 * @return true:����
	 */
	protected boolean downloadPDFAndPrint(Container cont, String servletName, Map funcArgs, String printerName) {
		try {
			downloadNative(servletName, funcArgs);

			// �t�@�C���W�J
			String fileName = createResultFile();

			if (!Util.isNullOrEmpty(printerName)) {
				SystemUtil.printPDF(fileName, printerName);
			} else {
				SystemUtil.printPDF(fileName);
			}

			return true;

		} catch (TRequestException e) {
			errorHandler(cont);
			return false;

		} catch (IOException ex) {
			errorHandler(cont, ex, "W01079", ex.getMessage());
			return false;

		} catch (TException ex) {
			errorHandler(cont, ex);
			return false;
		}
	}

	/**
	 * �t�@�C���_�E�����[�h����.<br>
	 * ���ʏ���
	 * 
	 * @param servletName �Ώ�Servlet��
	 * @param funcArgs ���n���p�����[�^
	 * @throws TException
	 */
	protected void downloadNative(String servletName, Map funcArgs) throws TException {

		try {

			ClientLogger.memory("download start");

			// �T�[�o����
			this.addSendValues(funcArgs);

			if (!request(servletName)) {
				throw new TRequestException();
			}

			ClientLogger.memory("download end");

		} catch (IOException ex) {
			throw new TException(ex, "E00009");
		}
	}

	/**
	 * �t�@�C���_�E�����[�h���ʂ̏o��
	 * 
	 * @param cont ���R���|�[�l���g
	 * @return true:����
	 */
	protected boolean executeResultFile(Container cont) {

		try {
			String dir = createResultFile();

			// ���ڈ��
			if (TClientLoginInfo.getCompanyInfo().getDirectPrintKbn() == CMP_MST.DIRECT_PRINT) {

				// PDF�v�����g
				if ("pdf".equals(ResourceUtil.getExtension(new File(dir)))) {
					SystemUtil.printPDF(dir);
					return true;
				}
			}
			// �t�@�C���N��
			SystemUtil.executeFile(dir);

			return true;

		} catch (IOException ex) {
			errorHandler(cont, ex, "W01079", ex.getMessage());

			return false;
		} catch (TException ex) {
			errorHandler(cont, ex);

			return false;
		}
	}

	/**
	 * �t�@�C���_�E�����[�h���ʂ̏o��(TEMP�t�H���_�ۑ�)
	 * 
	 * @return �o�̓t�@�C����
	 * @throws IOException
	 */
	protected String createResultFile() throws IOException {

		// �ꎞTemp�t�@�C���ꏊ
		String dirPath = SystemUtil.getTemporaryDir();

		// �t�@�C���W�J.�W�J���ꂽ�t�@�C�������߂�
		return createResultFile(dirPath);
	}

	/**
	 * �t�@�C���_�E�����[�h���ʂ̏o��(�w��t�H���_�ۑ�)
	 * 
	 * @param dirPath �t�@�C���o�͏ꏊ
	 * @return �o�̓t�@�C����
	 * @throws IOException
	 */
	protected String createResultFile(String dirPath) throws IOException {

		// �o�C�i�����ʎ擾
		Map<String, Object> dataSet = super.getResultBinaryDataSet();

		String name = (String) dataSet.get(TRequestBase.RESULT_BYTE_FILENAME); // �t�@�C����
		String extension = (String) dataSet.get(TRequestBase.RESULT_BYTE_EXTENSION); // �g���q

		String fileName = name + "." + extension;

		// �t�@�C�����J����Ă���ꍇ�A�ʖ���t���ĕۑ�����
		for (int i = 1; !ResourceUtil.canWrite(dirPath + fileName); i++) {
			fileName = name + "[" + i + "]." + extension;

			if (i == 100) {
				// �p�X�w��~�X���̖������[�v���
				throw new TRuntimeException("File count over100." + fileName);
			}
		}

		return createResultFileNative(dirPath, fileName);
	}

	/**
	 * �t�@�C���_�E�����[�h���ʂ̏o��(�w��t�H���_�A�t�@�C�����ۑ�).<br>
	 * ���ʏ���
	 * 
	 * @param dirPath �t�@�C���o�͏ꏊ
	 * @param fileName �t�@�C����(�g���q�t��)
	 * @return �o�̓t�@�C����
	 * @throws IOException
	 */
	protected String createResultFileNative(String dirPath, String fileName) throws IOException {

		// �o�C�i�����ʎ擾
		Map<String, Object> dataSet = super.getResultBinaryDataSet();

		byte[] binary = (byte[]) dataSet.get(TRequestBase.RESULT_BYTE_DATA); // �f�[�^

		String fullPath = dirPath + fileName;

		ClientLogger.info("zip file binary=" + binary.length);
		ClientLogger.memory("write binay start");

		ResourceUtil.writeBinaryInZip(fullPath, binary);

		ClientLogger.memory("write binary end");
		ClientLogger.debug("create fileName=" + fullPath);

		return fullPath;
	}

	/**
	 * �����f�[�^�t�@�C���A�b�v���[�h
	 * 
	 * @param cont �p�l��(Panel�R���|�[�l���g)
	 * @param servletName ����Servlet��
	 * @param para �p�����[�^
	 * @return true:�����Afalse:���s�A�܂��́A�L�����Z��
	 */
	protected boolean uploadDAT(Container cont, String servletName, Map<String, Object> para) {

		// �����t�@�C���t�B���^�[
		TFileFilter datFilter = new TFileFilter(new String[] { "txt", "dat" }, "C10103");

		return executeUpload(cont, servletName, para, datFilter);
	}

	/**
	 * CSV�t�@�C���A�b�v���[�h
	 * 
	 * @param cont �p�l��(Panel�R���|�[�l���g)
	 * @param servletName ����Servlet��
	 * @param para �p�����[�^
	 * @return true:�����Afalse:���s�A�܂��́A�L�����Z��
	 */
	protected boolean uploadCSV(Container cont, String servletName, Map<String, Object> para) {

		// CSV�t�@�C���t�B���^�[
		TFileFilter csvFilter = new TFileFilter("csv", "C02770");

		return executeUpload(cont, servletName, para, csvFilter);
	}

	/**
	 * �G�N�Z���t�@�C���A�b�v���[�h
	 * 
	 * @param cont �p�l��(Panel�R���|�[�l���g)
	 * @param servletName ����Servlet��
	 * @param para �p�����[�^
	 * @return true:�����Afalse:���s�A�܂��́A�L�����Z��
	 */
	protected boolean uploadXLS(Container cont, String servletName, Map<String, Object> para) {

		// Excel�t�@�C���t�B���^�[
		TFileFilter xlsFilter = new TFileFilter("xls", "C02315");

		return executeUpload(cont, servletName, para, xlsFilter);
	}

	/**
	 * �e�L�X�g�t�@�C���A�b�v���[�h
	 * 
	 * @param cont �p�l��(Panel�R���|�[�l���g)
	 * @param servletName ����Servlet��
	 * @param para �p�����[�^
	 * @return true:�����Afalse:���s�A�܂��́A�L�����Z��
	 */
	protected boolean uploadTXT(Container cont, String servletName, Map<String, Object> para) {

		// �e�L�X�g�t�@�C��(CSV�`��)�t�B���^�[
		TFileFilter txtFilter = new TFileFilter(new String[] { "csv", "txt" }, "C02771");

		return executeUpload(cont, servletName, para, txtFilter);
	}

	/**
	 * �t�@�C���A�b�v���[�h(�p�l���p)
	 * 
	 * @param cont �p�l��(Panel�R���|�[�l���g)
	 * @param servletName ����Servlet��
	 * @param para �p�����[�^
	 * @param filter �t�@�C���t�B���^�[
	 * @return true:�����Afalse:���s�A�܂��́A�L�����Z��
	 */
	private boolean executeUpload(Container cont, String servletName, Map<String, Object> para, FileFilter filter) {
		try {

			// �t�B���^�[�Z�b�g
			openChooser.resetChoosableFileFilters();
			openChooser.setFileFilter(filter);
			openChooser.updateUI();

			if (JFileChooser.APPROVE_OPTION != openChooser.showOpenDialog(cont)) {
				return false;
			}

			File file = openChooser.getSelectedFile();

			// �u*.*�v�Ɠ��͂���Ƒ��̃t�@�C�����I���\�ȈׁA�ēx�`�F�b�N
			if (!filter.accept(file)) {
				// �������t�@�C����I�����Ă�������
				showMessage(cont, "W00278", "C01988");
				return false;
			}

			ClientLogger.debug(file.getPath());

			// �t���p�X��
			filePath = file.getPath();

			// �ʐM
			addSendValues("flag", "upload");
			addSendValues(para);

			if (!request(servletName, file)) {
				errorHandler(cont);
				return false;
			}

		} catch (IOException ex) {
			ClientLogger.error(getMessage("E00009", ex.getMessage()), ex);
			errorHandler(cont, "E00009", ex.getMessage());

			return false;
		}

		return true;
	}

	/**
	 * TEXT�t�@�C���A�b�v���[�h
	 * 
	 * @param cont �p�l��(Panel�R���|�[�l���g)
	 * @param servletName ����Servlet��
	 * @param listener
	 * @deprecated TAsyncRequest�𗘗p���邱��
	 */
	protected void uploadTXTAsync(Container cont, String servletName, TAsyncListener listener) {

		// �e�L�X�g�t�@�C��(CSV�`��)�t�B���^�[
		TFileFilter filter = new TFileFilter(new String[] { "csv", "txt" }, "C02771");

		// �t�B���^�[�Z�b�g
		openChooser.resetChoosableFileFilters();
		openChooser.setFileFilter(filter);
		openChooser.updateUI();

		if (JFileChooser.APPROVE_OPTION != openChooser.showOpenDialog(cont)) {
			return;
		}

		File file = openChooser.getSelectedFile();

		// �u*.*�v�Ɠ��͂���Ƒ��̃t�@�C�����I���\�ȈׁA�ēx�`�F�b�N
		if (!filter.accept(file)) {
			// �������t�@�C����I�����Ă�������
			showMessage(cont, "W00278", "C01988");
			return;
		}

		ClientLogger.debug(file.getPath());

		// �t���p�X��
		filePath = file.getPath();

		// �ʐM
		addSendValues("flag", "upload");

		List<File> list = new ArrayList<File>(1);
		list.add(file);

		requestAsync(servletName, list, listener);
	}

	/**
	 * �t�@�C���A�b�v���[�h
	 * 
	 * @param cont �p�l��(Panel�R���|�[�l���g)
	 * @param servletName ����Servlet��
	 * @param files ���M�t�@�C�����X�g
	 * @return true:�����Afalse:���s�A�܂��́A�L�����Z��
	 */
	protected boolean uploadFiles(Container cont, String servletName, List<File> files) {
		return uploadFiles(cont, servletName, files, Collections.EMPTY_MAP);
	}

	/**
	 * �t�@�C���A�b�v���[�h
	 * 
	 * @param cont �p�l��(Panel�R���|�[�l���g)
	 * @param servletName ����Servlet��
	 * @param files ���M�t�@�C�����X�g
	 * @param para �p�����[�^
	 * @return true:�����Afalse:���s�A�܂��́A�L�����Z��
	 */
	protected boolean uploadFiles(Container cont, String servletName, List<File> files, Map<String, Object> para) {
		try {

			// �ʐM
			addSendValues("flag", "upload");
			addSendValues(para);

			if (!request(servletName, files)) {
				errorHandler(cont);
				return false;
			}

		} catch (IOException ex) {
			ClientLogger.error(getMessage("E00009", ex.getMessage()), ex);
			errorHandler(cont, "E00009", ex.getMessage());

			return false;
		}

		return true;
	}

	/**
	 * ���M(�񓯊�����).<br>
	 * getView()���������Ă��邱�Ƃ��O��.
	 * 
	 * @param path ���s�p�X
	 * @param listener ���N�G�X�g��̏������X�i�[
	 * @deprecated TAsyncRequest�𗘗p���邱��
	 */
	protected void requestAsync(final String path, final TAsyncListener listener) {
		requestAsync(path, Collections.EMPTY_LIST, listener);
	}

	/**
	 * ���M(�񓯊�����).<br>
	 * getView()���������Ă��邱�Ƃ��O��.
	 * 
	 * @param path ���s�p�X
	 * @param files �t�@�C�����X�g
	 * @param listener ���N�G�X�g��̏������X�i�[
	 * @deprecated TAsyncRequest�𗘗p���邱��
	 */
	protected void requestAsync(final String path, final List<File> files, final TAsyncListener listener) {

		try {
			synchronized (dummy) {

				locked(true);

				new Thread() {

					@Override
					public void run() {
						try {
							final boolean result = request(path, files);

							locked(false);

							SwingUtilities.invokeLater(new Runnable() {

								public void run() {

									if (listener != null) {
										listener.after(result);
									}

								}
							});

						} catch (NullPointerException e) {
							ClientLogger.error("null error.", e);

						} catch (Exception e) {
							locked(false);
							errorHandler(e);

						} finally {
							locked(false);
						}
					}
				}.start();
			}

		} catch (TRuntimeException ex) {
			locked(false);

			throw ex;
		}
	}

	/**
	 * �t�@�C���_�E�����[�h���\��(�񓯊�����).<br>
	 * getView()���������Ă��邱�Ƃ��O��.
	 * 
	 * @param cont ���R���|�[�l���g
	 * @param servletName �Ώ�Servlet��
	 * @deprecated TAsyncRequest�𗘗p���邱��
	 */
	protected void downloadAsync(final Container cont, final String servletName) {
		this.downloadAsync(cont, servletName, Collections.EMPTY_MAP);
	}

	/**
	 * �t�@�C���_�E�����[�h���\��(�񓯊�����).<br>
	 * getView()���������Ă��邱�Ƃ��O��.
	 * 
	 * @param cont ���R���|�[�l���g
	 * @param servletName �Ώ�Servlet��
	 * @param funcArgs ���n���p�����[�^
	 * @deprecated TAsyncRequest�𗘗p���邱��
	 */
	protected void downloadAsync(final Container cont, final String servletName, final Map funcArgs) {

		try {
			synchronized (dummy) {

				locked(true);

				new Thread() {

					@Override
					public void run() {
						try {

							downloadNative(servletName, funcArgs);

							// // �C�x���g�f�B�X�p�b�`�X���b�h��
							SwingUtilities.invokeLater(new Runnable() {

								public void run() {

									locked(false);

									// �t�@�C���W�J&���s
									executeResultFile(cont);
								}
							});

						} catch (NullPointerException e) {
							ClientLogger.error("null error.", e);

						} catch (TRequestException e) {
							locked(false);
							errorHandler(cont);

						} catch (TException ex) {
							locked(false);
							errorHandler(cont, ex);

						} finally {
							locked(false);
						}
					}
				}.start();
			}

		} catch (TRuntimeException ex) {
			locked(false);

			throw ex;
		}
	}

	/**
	 * �Ǘ����Ă����ʂ𑀍쐧�䂷��
	 * 
	 * @param isLocked true:���삳���Ȃ�
	 */
	protected void locked(boolean isLocked) {
		Container cont = getView();

		if (cont == null) {
			ClientLogger.error("getView() doesn't suffice. " + this.getClass().getName());
			return;
		}

		Container root = TGuiUtil.getParentFrameOrDialog(cont);

		if (root != null) {
			root.setEnabled(!isLocked);

		} else {
			cont.setEnabled(!isLocked);
		}
	}

	/**
	 * �f�t�H���g�J�[�\����ݒ�
	 * 
	 * @param comp �ΏۃR���e�i
	 */
	protected void setDefaultCursor(Container comp) {
		Component parent = TGuiUtil.getPanelBusiness(comp);

		if (parent == null) {
			parent = comp;
		}

		parent.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * WAIT�J�[�\����ݒ�
	 * 
	 * @param comp �ΏۃR���e�i
	 */
	protected void setWaitCursor(Container comp) {
		Component parent = TGuiUtil.getPanelBusiness(comp);

		if (parent == null) {
			parent = comp;
		}

		parent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}

	/**
	 * ���݂̃J�[�\����Ԃ�WAIT���ǂ����𔻒�
	 * 
	 * @param comp �ΏۃR���e�i
	 * @return WAIT��Ԃł���ꍇtrue
	 */
	protected boolean isWaitCursorNow(Container comp) {
		Component parent = TGuiUtil.getPanelBusiness(comp);

		if (parent == null) {
			parent = comp;
		}

		return Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR).equals(parent.getCursor());
	}

	/**
	 * FileChooser�p�t�B���^�[
	 */
	private class TFileFilter extends FileFilter {

		/** �\���g���q���X�g */
		private String[] extentions;

		/** ���� */
		private String description;

		/**
		 * �R���X�g���N�^
		 * 
		 * @param extention �\���g���q
		 * @param descriptionID �����̒P��ID
		 */
		public TFileFilter(String extention, String descriptionID) {
			this(new String[] { extention }, descriptionID);
		}

		/**
		 * �R���X�g���N�^
		 * 
		 * @param extentions �\���g���q���X�g
		 * @param descriptionID �����̒P��ID
		 */
		public TFileFilter(String[] extentions, String descriptionID) {
			this.extentions = extentions;

			StringBuilder asterExt = new StringBuilder();
			for (String extention : extentions) {
				if (asterExt.length() != 0) {
					asterExt.append(", ");
				}
				asterExt.append("*.").append(extention);
			}

			this.description = getWord(descriptionID) + " (" + asterExt.toString() + ")";
		}

		/**
		 * �g���q����
		 */
		@Override
		public boolean accept(File f) {
			if (f.isDirectory()) {
				return true;
			}

			String ext = ResourceUtil.getExtension(f);

			boolean result = false;
			for (String extention : extentions) {
				result |= ext.equals(extention);
			}

			return result;
		}

		/**
		 * ������
		 */
		@Override
		public String getDescription() {
			return description;
		}

	}

	/**
	 * �t�@�C���_�E�����[�h
	 * 
	 * @param servletName �T�[�u���b�g��
	 * @throws TException
	 */
	protected void download(String servletName) throws TException {
		download(servletName, Collections.EMPTY_MAP);
	}

	/**
	 * �t�@�C���_�E�����[�h
	 * 
	 * @param servletName
	 * @param funcArgs
	 * @throws TException
	 */
	protected void download(String servletName, Map funcArgs) throws TException {

		downloadNative(servletName, funcArgs);

		// �V�������@�Ńt�@�C���o��
		preview();

	}

	/**
	 * �V�������@�Ńt�@�C���o��
	 * 
	 * @throws TException
	 */
	protected void preview() throws TException {
		// �V�������@
		// �o�C�i�����ʎ擾
		Map<String, Object> dataSet = super.getResultBinaryDataSet();
		byte[] data = (byte[]) dataSet.get(TRequestBase.RESULT_BYTE_DATA); // �f�[�^

		String name = (String) dataSet.get(TRequestBase.RESULT_BYTE_FILENAME); // �t�@�C����
		String extension = (String) dataSet.get(TRequestBase.RESULT_BYTE_EXTENSION); // �g���q

		String fileName = name + "." + extension;

		TPrinter printer = new TPrinter(true);

		// ���ڈ��
		if (TClientLoginInfo.getCompanyInfo().getDirectPrintKbn() == CMP_MST.DIRECT_PRINT
			&& "pdf".equals(Util.avoidNull(extension).toLowerCase())) {
			printer.print(data, fileName);
		} else {
			printer.preview(data, fileName);
		}
	}

	/**
	 * �t�@�C���W�J&���s
	 * 
	 * @throws TException
	 */
	protected void executeResultFile() throws TException {

		try {

			String dir = createResultFile();

			// ���ڈ��
			if (TClientLoginInfo.getCompanyInfo().getDirectPrintKbn() == CMP_MST.DIRECT_PRINT) {

				// PDF�v�����g
				if ("pdf".equals(ResourceUtil.getExtension(new File(dir)))) {
					SystemUtil.printPDF(dir);
					return;
				}
			}

			// �t�@�C���N��
			SystemUtil.executeFile(dir);

		} catch (Exception e) {
			throw new TException(e);
		}

	}

}
