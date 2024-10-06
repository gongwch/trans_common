package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �Y�t�{�^���̃R���g���[��
 */
public class TAttachButtonCtrl extends TController {

	/** �Y�t�{�^�� */
	protected TAttachButton btn = null;

	/** �ő�t�@�C���T�C�Y(MB) */
	public static int maxFileSize = 4;

	/** �g���q */
	public static String[] supportFileExts = null;

	/** �Y�t�t�@�C�� */
	protected List<SWK_ATTACH> attachments = null;

	static {
		try {
			maxFileSize = Util.avoidNullAsInt(ClientConfig.getProperty("trans.slip.use.attachment.max.size"));
		} catch (Throwable e) {
			// �����Ȃ�
		}

		try {
			supportFileExts = ClientConfig.getProperties("trans.slip.attachment.support.file.exts");
		} catch (Throwable e) {
			supportFileExts = new String[] { ExtensionType.PDF.value, ExtensionType.XLS.value, ExtensionType.XLSX.value };
		}

	}

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param btn
	 */
	public TAttachButtonCtrl(TAttachButton btn) {

		this.btn = btn;

		// �C�x���g������
		addEvents();

		// ������
		init();

		// �c�[���`�b�v�̕\���x��0.1�b
		ToolTipManager.sharedInstance().setInitialDelay(100);

	}

	/**
	 * �C�x���g������
	 */
	protected void addEvents() {

		btn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// �Y�t�ǉ�
				attachFile();
			}
		});
	}

	/**
	 * ������
	 */
	public void init() {
		// �t�@�C���}�b�v�̏�����
		attachments = new ArrayList<SWK_ATTACH>();

		// �ŐV���f
		refresh();
	}

	/**
	 * �Y�t�ǉ�
	 */
	protected void attachFile() {
		try {
			TAttachListDialogCtrl ctrl = createAttachListDialogCtrl();
			ctrl.setData(getAttachments());
			ctrl.show();
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * @return �Y�t�ꗗ�_�C�A���OCtrl
	 */
	protected TAttachListDialogCtrl createAttachListDialogCtrl() {
		if (btn.parent != null) {
			return new TAttachListDialogCtrl(btn.parent, getProgramInfo(), btn);
		} else {
			return new TAttachListDialogCtrl(btn.dialog, getProgramInfo(), btn);
		}
	}

	/**
	 * �Y�t�t�@�C���̎擾
	 * 
	 * @return files �Y�t�t�@�C��
	 */
	public List<SWK_ATTACH> getAttachments() {
		return attachments;
	}

	/**
	 * �Y�t�t�@�C���̐ݒ�
	 * 
	 * @param attachments �Y�t�t�@�C�����
	 */
	public void setAttachments(List<SWK_ATTACH> attachments) {

		// ���[�J���ɕۑ�����
		if (attachments != null) {

			try {
				TPrinter printer = new TPrinter(true);

				for (SWK_ATTACH bean : attachments) {
					if (bean.getFileData() != null) {
						byte[] data = (byte[]) bean.getFileData();

						String tempFileName = printer.createResultFile(bean.getFILE_NAME(), data);
						File file = new File(tempFileName);
						bean.setFile(file);
						bean.setFileData(null); // �o�C�i�����N���A
					}
				}
			} catch (Exception ex) {
				errorHandler(ex);
			}
		}

		this.attachments = attachments;
		refresh();
	}

	/**
	 * �Y�t�t�@�C���̍폜
	 * 
	 * @param list �Y�t�t�@�C�����
	 */
	public void removeAttachment(List<SWK_ATTACH> list) {

		if (this.attachments != null) {
			for (SWK_ATTACH bean : list) {
				this.attachments.remove(bean);
			}
		}
		refresh();
	}

	/**
	 * �t�@�C���ǉ�
	 * 
	 * @param filename �L�[
	 * @param file �t�@�C��
	 */
	public void addFile(String filename, File file) {

		if (this.attachments == null) {
			// �t�@�C���}�b�v�̏�����
			attachments = new ArrayList<SWK_ATTACH>();
		}

		this.attachments.add(createEntity(filename, file));
		refresh();
	}

	/**
	 * �Y�t�G���e�B�e�B�̍쐬
	 * 
	 * @param filename
	 * @param file
	 * @return �Y�t
	 */
	protected SWK_ATTACH createEntity(String filename, File file) {
		SWK_ATTACH bean = createBean();
		if (getUser() != null && getUser().getEmployee() != null) {
			bean.setEMP_CODE(getUser().getEmployee().getCode());
			bean.setEMP_NAME(getUser().getEmployee().getName());
			bean.setEMP_NAME_S(getUser().getEmployee().getNames());
		}
		bean.setINP_DATE(null); // �V�K
		bean.setFILE_NAME(filename);
		bean.setFile(file);

		return bean;
	}

	/**
	 * �t�@�C���ǉ�<br>
	 * File���ڑ��M���g���Ă�������
	 * 
	 * @param filename �L�[
	 * @param data �o�C�i��
	 */
	@Deprecated
	public void addFile(String filename, byte[] data) {

		if (this.attachments == null) {
			// �t�@�C���}�b�v�̏�����
			attachments = new ArrayList<SWK_ATTACH>();
		}

		this.attachments.add(createEntity(filename, data));
		refresh();
	}

	/**
	 * �Y�t�G���e�B�e�B�̍쐬<br>
	 * File���ڑ��M���g���Ă�������
	 * 
	 * @param filename
	 * @param data
	 * @return �Y�t
	 */
	@Deprecated
	protected SWK_ATTACH createEntity(String filename, byte[] data) {
		SWK_ATTACH bean = createBean();
		if (getUser() != null && getUser().getEmployee() != null) {
			bean.setEMP_CODE(getUser().getEmployee().getCode());
			bean.setEMP_NAME(getUser().getEmployee().getName());
			bean.setEMP_NAME_S(getUser().getEmployee().getNames());
		}
		bean.setINP_DATE(null); // �V�K
		bean.setFILE_NAME(filename);
		bean.setFILE_DATA(data);

		return bean;
	}

	/**
	 * @return �G���e�B�e�B�쐬
	 */
	protected SWK_ATTACH createBean() {
		return new SWK_ATTACH();
	}

	/**
	 * �ŐV���f����
	 */
	public void refresh() {

		// �摜���f
		if (attachments != null && !attachments.isEmpty()) {
			btn.setIconType(IconType.ATTACHMENT_COMPLETE);
		} else {
			btn.setIconType(IconType.ATTACHMENT);
		}

		// �c�[���`�b�v���f
		setToolTipText();
	}

	/**
	 * �N���A����
	 */
	public void clear() {
		init();
	}

	/**
	 * ToolTip�̐ݒ�
	 */
	public void setToolTipText() {
		String noneWord = getWord("C11611"); // �Y�t�t�@�C���Ȃ�
		String tooltip = "<html><font color=red><b>" + noneWord + "</b></font></html>"; // �Y�t�t�@�C���Ȃ�

		if (attachments != null && !attachments.isEmpty()) {

			String rightWord = getWord("C11613"); // �Y�t�t�@�C��

			StringBuilder sb = new StringBuilder();
			sb.append("<html>");
			sb.append("<b>" + rightWord + "</b>"); // �Y�t�t�@�C��
			sb.append("<br>");

			int max = 50;

			for (SWK_ATTACH bean : attachments) {
				String key = bean.getFILE_NAME();
				max = Math.max(max, key.getBytes().length + 1);
			}

			int i = 1;

			for (SWK_ATTACH bean : attachments) {
				String key = bean.getFILE_NAME();
				String no = StringUtil.fillHtmlSpace(Integer.toString(i++), 4);
				String filename = StringUtil.fillHtmlSpace(key, max);

				sb.append("<b>" + no + "</b>");
				sb.append("<font color=blue>" + filename + "</font>");
				sb.append("<br>");
			}

			sb.append("</html>");

			tooltip = sb.toString();
		}

		btn.setToolTipText(tooltip);
	}

	/**
	 * @return FolderKeyName��߂��܂��B
	 */
	protected String getFolderKeyName() {
		if (btn.parent != null) {
			return btn.parent.getClass().getName() + ".slip.attachment";
		} else if (btn.dialog != null) {
			return btn.dialog.getClass().getName() + ".slip.attachment";
		}
		return "slip.attachment";
	}

	/**
	 * �O��ۑ������t�H���_����Ԃ��B
	 * 
	 * @param type ���
	 * @return �O��ۑ������t�H���_���
	 */
	protected File getPreviousFolder(String type) {

		String path = SystemUtil.getAisSettingDir();
		File dir = (File) FileUtil.getObject(path + File.separator + getFolderKeyName() + type);

		return dir;
	}

	/**
	 * �w��̃t�H���_����ۑ�����
	 * 
	 * @param dir �t�H���_���
	 */
	protected void saveFolder(File dir) {
		String path = SystemUtil.getAisSettingDir();
		FileUtil.saveObject(dir, path + File.separator + getFolderKeyName() + ".chooser");
	}

}
