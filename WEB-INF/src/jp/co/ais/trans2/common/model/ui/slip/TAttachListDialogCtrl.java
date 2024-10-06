package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.TransferHandler.TransferSupport;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.model.ui.slip.TAttachListDialog.SC;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �Y�t�_�C�A���OCtrl
 */
public class TAttachListDialogCtrl extends TController {

	/** �e�p�l�� */
	protected TPanel parent;

	/** �e�_�C�A���O */
	protected TDialog parentDialog;

	/** �Y�t�_�C�A���O */
	protected TAttachListDialog dialog = null;

	/** �Y�t�{�^�� */
	protected TAttachButton attachButton;

	/** �`�[[�_�C�A���O����] */
	protected Slip slip = null;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param parent �e�R���|�[�l���g
	 * @param prgInfo �v���O�������
	 */
	public TAttachListDialogCtrl(TPanel parent, TClientProgramInfo prgInfo) {
		this(parent, prgInfo, null);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param parent �e�R���|�[�l���g
	 * @param prgInfo �v���O�������
	 * @param attachButton �Y�t�{�^��
	 */
	public TAttachListDialogCtrl(TPanel parent, TClientProgramInfo prgInfo, TAttachButton attachButton) {
		this.parent = parent;
		this.prgInfo = prgInfo;
		this.attachButton = attachButton;

		// �_�C�A���O������
		this.dialog = createView();

		// �w����ʂ�����������
		initView();

		// �C�x���g�ݒ�
		addEvents();
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param parentDialog �e�_�C�A���O
	 * @param prgInfo �v���O�������
	 */
	public TAttachListDialogCtrl(TDialog parentDialog, TClientProgramInfo prgInfo) {
		this(parentDialog, prgInfo, null);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param parentDialog �e�_�C�A���O
	 * @param prgInfo �v���O�������
	 * @param attachButton �Y�t�{�^��
	 */
	public TAttachListDialogCtrl(TDialog parentDialog, TClientProgramInfo prgInfo, TAttachButton attachButton) {
		this.parentDialog = parentDialog;
		this.prgInfo = prgInfo;
		this.attachButton = attachButton;

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
	protected TAttachListDialog createView() {
		if (parent != null) {
			return new TAttachListDialog(getCompany(), parent.getParentFrame());
		} else {
			return new TAttachListDialog(getCompany(), parentDialog);
		}
	}

	@Override
	public TAttachListDialog getView() {
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
			TFileFilter filter = new TFileFilter(getWord("C11613"), TAttachButtonCtrl.supportFileExts);

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
	 * @throws IOException
	 */
	protected boolean addAllFiles(TFile[] tFiles) throws IOException {
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
	 * @throws IOException
	 */
	protected boolean addAllFiles(List<File> list) throws IOException {
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
	 * @throws IOException
	 */
	protected boolean addAllFiles(File[] tFiles) throws IOException {
		if (tFiles == null || tFiles.length == 0) {
			return false;
		}

		for (int i = 0; i < tFiles.length; i++) {
			File file = tFiles[i];

			String path = file.getPath();
			String filename = file.getName();

			byte[] data = null;

			try {
				data = ResourceUtil.zipBinary(filename, ResourceUtil.readBinarry(path));
			} catch (FileNotFoundException e) {
				showMessage(e.getMessage());
				return false;
			}

			if (data != null && data.length > 1024 * 1024 * TAttachButtonCtrl.maxFileSize) {
				// �t�@�C���T�C�Y��4MB�𒴂��Ă��܂��B
				showMessage("I00446", TAttachButtonCtrl.maxFileSize);
				return false;
			}

			if (data == null) {
				// �t�@�C�������݂��ĂȂ��ꍇ�A�����s�v
				return false;
			}
            // ���ꕄ���`�F�b�N
            String chk = new String(filename.getBytes(), "SHIFT-JIS");
            if (chk.contains("?")) { 
                String c = chk.replaceAll("\\?", "<b><font color=red>?</font></b>");
                // "<html>�t�@�C�����ɓ��ꕶ�����܂܂�Ă��܂��B<br>" + c + "<html>"
                showMessage("I01135", c);
                return false;
            }

			addFile(filename, file);

			if (i == 0) {
				// �t�H���_�L��
				saveFolder(file);
			}
		}

		return true;
	}

	/**
	 * @param filename
	 * @param file
	 */
	protected void addFile(String filename, File file) {
		try {
			if (attachButton != null) {
				attachButton.addFile(filename, file);

				setData(attachButton.getAttachments());
			} else {
				// DB���f
				SWK_ATTACH bean = createEntity(filename, file);

				// �`�[�ɔ��f���ēo�^�������s��
				slip.getHeader().getAttachments().add(bean);
				request(getModelClass(), "entry", bean);

				// ��ʍĔ��f
				setData(slip.getHeader().getAttachments());

			}
		} catch (Exception e) {
			errorHandler(e);
		}
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
		bean.setINP_DATE(Util.getCurrentDate()); // �V�K
		bean.setFILE_NAME(filename);
		bean.setFile(file);

		// �ǉ��������Ƀ��[�J��temp�t�H���_�֊i�[�����
		byte[] data = null;
		String path = file.getPath();
		try {
			data = ResourceUtil.zipBinary(filename, ResourceUtil.readBinarry(path));
		} catch (IOException ex) {
			//
		}
		bean.setFileData(data);

		// �K�{���
		bean.setKAI_CODE(slip.getCompanyCode());
		bean.setSWK_DEN_NO(slip.getSlipNo());
		bean.setPRG_ID(getProgramCode());
		bean.setUSR_ID(getUserCode());
		bean.setSEQ(dialog.tbl.getRowCount() + 1);

		return bean;
	}

	/**
	 * �t�@�C������<br>
	 * File���ڑ��M���g���Ă�������
	 * 
	 * @param filename
	 * @param data
	 */
	@Deprecated
	protected void addFile(String filename, byte[] data) {
		try {
			if (attachButton != null) {
				attachButton.addFile(filename, data);

				setData(attachButton.getAttachments());
			} else {
				// DB���f
				SWK_ATTACH bean = createEntity(filename, data);

				// �`�[�ɔ��f���ēo�^�������s��
				slip.getHeader().getAttachments().add(bean);
				request(getModelClass(), "entry", bean);

				// ��ʍĔ��f
				setData(slip.getHeader().getAttachments());

			}
		} catch (Exception e) {
			errorHandler(e);
		}
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
		bean.setINP_DATE(Util.getCurrentDate()); // �V�K
		bean.setFILE_NAME(filename);
		bean.setFILE_DATA(data);

		// �K�{���
		bean.setKAI_CODE(slip.getCompanyCode());
		bean.setSWK_DEN_NO(slip.getSlipNo());
		bean.setPRG_ID(getProgramCode());
		bean.setUSR_ID(getUserCode());
		bean.setSEQ(dialog.tbl.getRowCount() + 1);

		return bean;
	}

	/**
	 * @return �G���e�B�e�B�쐬
	 */
	protected SWK_ATTACH createBean() {
		return new SWK_ATTACH();
	}

	/**
	 * @return FolderKeyName��߂��܂��B
	 */
	protected String getFolderKeyName() {
		if (parent != null) {
			return parent.getClass().getName() + ".slip.attachment";
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

	/**
	 * �Ɖ�{�^������
	 */
	protected void btnDrillDown_Click() {
		try {

			boolean hasSelectedRows = false;

			// �`�F�b�N���ꂽ�s���Ɖ��
			for (int row : dialog.tbl.getSelectedRows()) {

				SWK_ATTACH bean = getBean(row);

				if (bean.getFILE_DATA() != null) {
					TPrinter printer = new TPrinter(true);
					printer.previewAttach((byte[]) bean.getFILE_DATA(), bean.getFILE_NAME());

				} else if (bean.getFile() != null) {
					SystemUtil.executeFile(bean.getFile().getPath());
				}

				hasSelectedRows = true;
			}

			if (!hasSelectedRows) {
				// �ꗗ����f�[�^��I�����Ă��������B
				showMessage(dialog, "I00043");
				return;
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

			List<SWK_ATTACH> list = new ArrayList<SWK_ATTACH>();

			// �`�F�b�N���ꂽ�s���폜����
			for (int row : dialog.tbl.getSelectedRows()) {
				SWK_ATTACH bean = getBean(row);
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

			if (attachButton != null) {

				attachButton.removeAttachment(list);
				setData(attachButton.getAttachments());

			} else {
				// DB�폜
				request(getModelClass(), "delete", list);

				// �ŐV�ݒ�
				List<SWK_ATTACH> attachs = (List<SWK_ATTACH>) request(getModelClass(), "get", slip.getCompanyCode(),
					slip.getSlipNo());

				slip.getHeader().setAttachments(attachs);

				setData(slip.getHeader().getAttachments());
			}

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * @return �}�l�[�W��
	 */
	protected Class getModelClass() {
		return SlipAttachmentManager.class;
	}

	/**
	 * ����{�^������
	 */
	protected void btnClose_Click() {
		dialog.setVisible(false);
	}

	/**
	 * �`�[�ݒ�
	 * 
	 * @param drilldownSlip
	 */
	public void setSlip(Slip drilldownSlip) {
		this.slip = drilldownSlip;

		// �f�[�^�ݒ�
		setData(slip.getHeader().getAttachments());
	}

	/**
	 * �f�[�^�ݒ�
	 * 
	 * @param list
	 */
	public void setData(List<SWK_ATTACH> list) {

		dialog.tbl.removeRow();

		if (list == null) {
			return;
		}

		// ���[�J���ɕۑ�����
		try {
			TPrinter printer = new TPrinter(true);

			for (SWK_ATTACH bean : list) {
				if (bean.getFileData() != null) {
					byte[] data = (byte[]) bean.getFileData();

					String tempFileName = printer.createResultFile(bean.getFILE_NAME(), data, true);
					File file = new File(tempFileName);
					bean.setFile(file);
					bean.setFileData(null); // �o�C�i�����N���A
				}
			}
		} catch (Exception ex) {
			errorHandler(ex);
		}

		for (SWK_ATTACH bean : list) {
			dialog.tbl.addRow(getRow(bean));
		}

		if (dialog.tbl.getRowCount() > 0) {
			dialog.tbl.setRowSelection(0);
			dialog.tbl.requestFocus();
		}

	}

	/**
	 * �\��
	 */
	public void show() {
		// �\��
		dialog.setVisible(true);
	}

	/**
	 * @param bean
	 * @return �s�f�[�^
	 */
	protected List getRow(SWK_ATTACH bean) {

		List list = new ArrayList();
		list.add(bean);
		list.add(bean.getEMP_CODE());
		list.add(bean.getEMP_NAME());
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
	protected SWK_ATTACH getBean(int row) {
		return (SWK_ATTACH) getView().tbl.getRowValueAt(row, SC.bean);
	}

	/**
	 * �C���|�[�g����
	 * 
	 * @param support
	 * @return true:IMPORT�\
	 */
	protected boolean canImportAttach(TransferSupport support) {
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

				for (String accept : TAttachButtonCtrl.supportFileExts) {
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
			return canImportAttach(support);
		}

		@Override
		public boolean importData(TransferSupport support) {
			return importDataAttach(support);
		}

	}
}
