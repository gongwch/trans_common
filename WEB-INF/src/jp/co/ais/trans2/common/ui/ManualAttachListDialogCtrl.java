package jp.co.ais.trans2.common.ui;

import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.TransferHandler.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.common.ui.ManualAttachListDialog.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.save.*;

/**
 * �Y�t�_�C�A���OCtrl
 */
public class ManualAttachListDialogCtrl extends TController {

	/** �e�p�l�� */
	protected TPanel parent;

	/** �Y�t�_�C�A���O */
	protected ManualAttachListDialog dialog = null;

	/** �ő�t�@�C���T�C�Y(MB) */
	public static int maxFileSize = 40;

	/** �g���q */
	public static String[] supportFileExts = null;

	/** �Y�t�t�@�C�� */
	protected List<MANUAL_ATTACH> attachments = null;

	static {
		try {
			maxFileSize = Util.avoidNullAsInt(ClientConfig.getProperty("trans.manul.use.attachment.max.size"));
		} catch (Throwable e) {
			// �����Ȃ�
		}

		try {
			supportFileExts = ClientConfig.getProperties("trans.manul.attachment.support.file.exts");
		} catch (Throwable e) {
			supportFileExts = new String[] { ExtensionType.PDF.value, ExtensionType.XLS.value, ExtensionType.XLSX.value, "ppt", "pptx" };
		}

	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param parent �e�R���|�[�l���g
	 */
	public ManualAttachListDialogCtrl(TPanel parent) {
		this.parent = parent;

		// �_�C�A���O������
		this.dialog = createView();

		// �w����ʂ�����������
		initView();

		// �C�x���g�ݒ�
		addEvents();
	}

	/**
	 * �_�C�A���O����
	 * 
	 * @return �_�C�A���O
	 */
	protected ManualAttachListDialog createView() {
		return new ManualAttachListDialog(getCompany(), parent.getParentFrame());
	}

	@Override
	public ManualAttachListDialog getView() {
		return dialog;
	}

	/**
	 * ��ʂ�����������
	 */
	protected void initView() {
		// ���ɂȂ�

		// �����s�I���\���[�h
		getView().tbl.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	/**
	 * �C�x���g�ǉ�
	 */
	protected void addEvents() {

		// �ǉ��{�^��
		dialog.btnAdd.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// �Y�t�ǉ�
				btnAdd_Click();
			}
		});

		// �Ɖ�{�^��
		dialog.btnDrillDown.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// �Ɖ�
				btnDrillDown_Click();
			}
		});

		// �폜�{�^��
		dialog.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// �폜
				btnDelete_Click();
			}
		});

		// ����{�^��
		dialog.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnClose_Click();
			}
		});

		dialog.setTransferHandler(new DnDHandler());
	}

	/**
	 * �ǉ��{�^������
	 */
	protected void btnAdd_Click() {

		try {

			// �t�@�C���I���_�C�A���O���J��
			TFileChooser fc = new TFileChooser();
			fc.setAcceptAllFileFilterUsed(false); // �S�Ẵt�@�C���͑I��s��
			fc.setMultiSelectionEnabled(true); // �����I���\

			File dir = getPreviousFolder(".chooser");

			// �O��̃t�H���_�𕜌�
			if (dir != null) {
				fc.setCurrentDirectory(dir);
			}

			// �Y�t�t�@�C���t�B���^�[
			TFileFilter filter = new TFileFilter(getWord("C11613"), supportFileExts);

			fc.setFileFilter(filter);
			if (TFileChooser.FileStatus.Selected != fc.show(dialog)) {
				return;
			}

			TFile[] tFiles = fc.getSelectedTFiles();

			// �w��t�@�C����Y�t����
			addAllFiles(tFiles);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �w��t�@�C����Y�t����
	 * 
	 * @param tFiles
	 * @return true �ǉ�����
	 * @throws Exception
	 */
	protected boolean addAllFiles(TFile[] tFiles) throws Exception {
		if (tFiles == null || tFiles.length == 0) {
			return false;
		}

		File[] files = new File[tFiles.length];
		for (int i = 0; i < tFiles.length; i++) {
			files[i] = tFiles[i].getFile();
		}

		return addAllFiles(files);
	}

	/**
	 * �w��t�@�C����Y�t����
	 * 
	 * @param list
	 * @return true �ǉ�����
	 * @throws Exception
	 */
	protected boolean addAllFiles(List<File> list) throws Exception {
		if (list == null || list.isEmpty()) {
			return false;
		}

		File[] files = new File[list.size()];
		for (int i = 0; i < list.size(); i++) {
			files[i] = list.get(i);
		}

		return addAllFiles(files);
	}

	/**
	 * �w��t�@�C����Y�t����
	 * 
	 * @param tFiles
	 * @return true �ǉ�����
	 * @throws Exception
	 */
	protected boolean addAllFiles(File[] tFiles) throws Exception {
		if (tFiles == null || tFiles.length == 0) {
			return false;
		}

		List<MANUAL_ATTACH> list = new ArrayList<MANUAL_ATTACH>();

		for (int i = 0; i < tFiles.length; i++) {
			File file = tFiles[i];

			// String path = file.getPath();
			String filename = file.getName();

			// byte[] data = null;

			// try {
			// data = ResourceUtil.readBinarry(path);
			// } catch (FileNotFoundException e) {
			// showMessage(e.getMessage());
			// return false;
			// }
			//
			// if (data != null && data.length > 1024 * 1024 * maxFileSize) {
			// // �t�@�C���T�C�Y��4MB�𒴂��Ă��܂��B
			// showMessage("I00446", maxFileSize);
			// return false;
			// }

			// if (data == null) {
			// // �t�@�C�������݂��ĂȂ��ꍇ�A�����s�v
			// return false;
			// }

			MANUAL_ATTACH bean = createEntity(filename, file);
			list.add(bean);

			if (i == 0) {
				// �t�H���_�L��
				saveFolder(file);
			}
		}

		if (!list.isEmpty()) {
			// �}�j���A���ɔ��f���ēo�^�������s��
			List<MANUAL_ATTACH> newList = (List<MANUAL_ATTACH>) request(getModelClass(), "entryManual", list);
			setData(newList);
		}

		return true;
	}

	/**
	 * �Y�t�G���e�B�e�B�̍쐬
	 * 
	 * @param filename
	 * @param file
	 * @return �Y�t
	 */
	protected MANUAL_ATTACH createEntity(String filename, File file) {
		MANUAL_ATTACH bean = createBean();
		bean.setINP_DATE(Util.getCurrentDate()); // �V�K
		bean.setFILE_NAME(filename);
		bean.setFile(file);

		// �K�{���
		bean.setUSR_ID(getUserCode());
		bean.setSEQ(dialog.tbl.getRowCount() + 1);

		return bean;
	}

	/**
	 * @return �G���e�B�e�B�쐬
	 */
	protected MANUAL_ATTACH createBean() {
		return new MANUAL_ATTACH();
	}

	/**
	 * @return FolderKeyName��߂��܂��B
	 */
	protected String getFolderKeyName() {
		if (parent != null) {
			return parent.getClass().getName() + ".manual.attachment";
		}
		return "manual.attachment";
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

	/**
	 * �Ɖ�{�^������
	 */
	protected void btnDrillDown_Click() {
		try {

			List<MANUAL_ATTACH> list = new ArrayList<MANUAL_ATTACH>();

			int[] selectedRows = dialog.tbl.getSelectedRows();

			// �`�F�b�N���ꂽ�s���폜����
			for (int row : selectedRows) {
				MANUAL_ATTACH bean = getBean(row);
				list.add(bean);
			}

			if (list.isEmpty()) {
				// �ꗗ����f�[�^��I�����Ă��������B
				showMessage(dialog, "I00043");
				return;
			}

			// �`�F�b�N���ꂽ�s���Ɖ��
			for (MANUAL_ATTACH bean : list) {

				byte[] data = (byte[]) request(getModelClass(), "drilldownManual", bean);

				if (data != null && data.length != 0) {
					TPrinter printer = new TPrinter(false);
					printer.preview(data, bean.getFILE_NAME());
				}
			}

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �폜�{�^������
	 */
	protected void btnDelete_Click() {
		try {

			List<MANUAL_ATTACH> list = new ArrayList<MANUAL_ATTACH>();

			int[] selectedRows = dialog.tbl.getSelectedRows();

			// �`�F�b�N���ꂽ�s���폜����
			for (int row : selectedRows) {
				MANUAL_ATTACH bean = getBean(row);
				list.add(bean);
			}

			if (list.size() == 0) {
				// �ꗗ����f�[�^��I�����Ă��������B
				showMessage(dialog, "I00043");
				return;
			}

			// �I�������s���폜���Ă�낵���ł����H
			if (!showConfirmMessage("Q00016")) {
				return;
			}

			// DB�폜
			request(getModelClass(), "deleteManual", list);

			// �ŐV�ݒ�
			for (int i = selectedRows.length - 1; i >= 0; i--) {
				dialog.tbl.removeRow(selectedRows[i]);
			}

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * @return �}�l�[�W��
	 */
	protected Class getModelClass() {
		return SaveManager.class;
	}

	/**
	 * ����{�^������
	 */
	protected void btnClose_Click() {
		dialog.setVisible(false);
	}

	/**
	 * �f�[�^�ݒ�
	 * 
	 * @param list
	 */
	public void setData(List<MANUAL_ATTACH> list) {

		dialog.tbl.removeRow();

		if (list == null) {
			return;
		}

		for (MANUAL_ATTACH bean : list) {
			dialog.tbl.addRow(getRow(bean));
		}

		if (dialog.tbl.getRowCount() > 0) {
			dialog.tbl.setRowSelection(0);
			dialog.tbl.requestFocus();
		}

	}

	/**
	 * �\��
	 * 
	 * @throws TException
	 */
	public void show() throws TException {

		// �f�[�^�擾
		List<MANUAL_ATTACH> list = (List<MANUAL_ATTACH>) request(getModelClass(), "getManual");

		setData(list);

		// �\��
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}

	/**
	 * @param bean
	 * @return �s�f�[�^
	 */
	protected List getRow(MANUAL_ATTACH bean) {

		List list = new ArrayList();
		list.add(bean);
		if (bean.getINP_DATE() == null) {
			list.add(getWord("C02929")); // �V�K�o�^
		} else {
			list.add(DateUtil.toYMDHMSString(bean.getINP_DATE()));
		}
		list.add(bean.getFILE_NAME());
		return list;
	}

	/**
	 * @param row
	 * @return �Y�t���
	 */
	protected MANUAL_ATTACH getBean(int row) {
		return (MANUAL_ATTACH) getView().tbl.getRowValueAt(row, MANUAL_SC.bean);
	}

	/**
	 * �C���|�[�g����
	 * 
	 * @param support
	 * @return true:IMPORT�\
	 */
	@SuppressWarnings("unused")
	protected boolean canImporManualAttach(TransferSupport support) {
		//
		// System.out.println("test");
		// Transferable t = support.getTransferable();
		// try {
		// List<File> files = (List<File>) support.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
		// } catch (UnsupportedFlavorException e) {
		// // TODO �����������ꂽ catch �u���b�N
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO �����������ꂽ catch �u���b�N
		// e.printStackTrace();
		// }

		return true;
	}

	/**
	 * �C���|�[�g����
	 * 
	 * @param support
	 * @return true:��������
	 */
	protected boolean importDataAttach(TransferSupport support) {
		Transferable t = support.getTransferable();
		try {
			List<File> files = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);

			List<File> filterList = new ArrayList<File>();
			for (File f : files) {
				if (f.isDirectory()) {
					continue;
				}

				String ext = ResourceUtil.getExtension(f);
				boolean isSupport = false;

				for (String accept : supportFileExts) {
					if (Util.equals(accept, ext)) {
						isSupport = true;
					}
				}

				if (!isSupport) {
					continue;
				}

				filterList.add(f);
			}

			// �T�|�[�g�t�@�C���̂ݏ�������
			if (!filterList.isEmpty()) {
				addAllFiles(filterList);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * �h���b�O���h���b�v����
	 */
	protected class DnDHandler extends TransferHandler {

		@Override
		public boolean canImport(TransferSupport support) {
			return canImporManualAttach(support);
		}

		@Override
		public boolean importData(TransferSupport support) {
			return importDataAttach(support);
		}

	}
}
