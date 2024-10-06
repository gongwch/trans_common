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
 * 添付ダイアログCtrl
 */
public class TAttachListDialogCtrl extends TController {

	/** 親パネル */
	protected TPanel parent;

	/** 親ダイアログ */
	protected TDialog parentDialog;

	/** 添付ダイアログ */
	protected TAttachListDialog dialog = null;

	/** 添付ボタン */
	protected TAttachButton attachButton;

	/** 伝票[ダイアログ持つ] */
	protected Slip slip = null;

	/**
	 * コンストラクタ.
	 * 
	 * @param parent 親コンポーネント
	 * @param prgInfo プログラム情報
	 */
	public TAttachListDialogCtrl(TPanel parent, TClientProgramInfo prgInfo) {
		this(parent, prgInfo, null);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param parent 親コンポーネント
	 * @param prgInfo プログラム情報
	 * @param attachButton 添付ボタン
	 */
	public TAttachListDialogCtrl(TPanel parent, TClientProgramInfo prgInfo, TAttachButton attachButton) {
		this.parent = parent;
		this.prgInfo = prgInfo;
		this.attachButton = attachButton;

		// ダイアログ初期化
		this.dialog = createView();

		// 指示画面を初期化する
		initView();

		// イベント設定
		addEvents();
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param parentDialog 親ダイアログ
	 * @param prgInfo プログラム情報
	 */
	public TAttachListDialogCtrl(TDialog parentDialog, TClientProgramInfo prgInfo) {
		this(parentDialog, prgInfo, null);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param parentDialog 親ダイアログ
	 * @param prgInfo プログラム情報
	 * @param attachButton 添付ボタン
	 */
	public TAttachListDialogCtrl(TDialog parentDialog, TClientProgramInfo prgInfo, TAttachButton attachButton) {
		this.parentDialog = parentDialog;
		this.prgInfo = prgInfo;
		this.attachButton = attachButton;

		// ダイアログ初期化
		this.dialog = createView();

		// 指示画面を初期化する
		initView();

		// イベント設定
		addEvents();
	}

	/**
	 * ダイアログ生成
	 * 
	 * @return ダイアログ
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
	 * 画面を初期化する
	 */
	protected void initView() {
		// 特になし

		// 複数行選択可能モード
		getView().tbl.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	/**
	 * イベント追加
	 */
	protected void addEvents() {

		// 追加ボタン
		dialog.btnAdd.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// 添付追加
				btnAdd_Click();
			}
		});

		// 照会ボタン
		dialog.btnDrillDown.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// 照会
				btnDrillDown_Click();
			}
		});

		// 削除ボタン
		dialog.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// 削除
				btnDelete_Click();
			}
		});

		// 閉じるボタン
		dialog.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnClose_Click();
			}
		});

		dialog.setTransferHandler(new DnDHandler());
	}

	/**
	 * 追加ボタン押下
	 */
	protected void btnAdd_Click() {

		try {

			// ファイル選択ダイアログを開く
			TFileChooser fc = new TFileChooser();
			fc.setAcceptAllFileFilterUsed(false); // 全てのファイルは選択不可
			fc.setMultiSelectionEnabled(true); // 複数選択可能

			File dir = getPreviousFolder(".chooser");

			// 前回のフォルダを復元
			if (dir != null) {
				fc.setCurrentDirectory(dir);
			}

			// 添付ファイルフィルター
			TFileFilter filter = new TFileFilter(getWord("C11613"), TAttachButtonCtrl.supportFileExts);

			fc.setFileFilter(filter);
			if (TFileChooser.FileStatus.Selected != fc.show(dialog)) {
				return;
			}

			TFile[] tFiles = fc.getSelectedTFiles();

			// 指定ファイルを添付する
			addAllFiles(tFiles);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 指定ファイルを添付する
	 * 
	 * @param tFiles
	 * @return true 追加成功
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
	 * 指定ファイルを添付する
	 * 
	 * @param list
	 * @return true 追加成功
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
	 * 指定ファイルを添付する
	 * 
	 * @param tFiles
	 * @return true 追加成功
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
				// ファイルサイズが4MBを超えています。
				showMessage("I00446", TAttachButtonCtrl.maxFileSize);
				return false;
			}

			if (data == null) {
				// ファイルが存在してない場合、処理不要
				return false;
			}
            // 特殊符号チェック
            String chk = new String(filename.getBytes(), "SHIFT-JIS");
            if (chk.contains("?")) { 
                String c = chk.replaceAll("\\?", "<b><font color=red>?</font></b>");
                // "<html>ファイル名に特殊文字が含まれています。<br>" + c + "<html>"
                showMessage("I01135", c);
                return false;
            }

			addFile(filename, file);

			if (i == 0) {
				// フォルダ記憶
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
				// DB反映
				SWK_ATTACH bean = createEntity(filename, file);

				// 伝票に反映して登録処理を行う
				slip.getHeader().getAttachments().add(bean);
				request(getModelClass(), "entry", bean);

				// 画面再反映
				setData(slip.getHeader().getAttachments());

			}
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 添付エンティティの作成
	 * 
	 * @param filename
	 * @param file
	 * @return 添付
	 */
	protected SWK_ATTACH createEntity(String filename, File file) {
		SWK_ATTACH bean = createBean();
		if (getUser() != null && getUser().getEmployee() != null) {
			bean.setEMP_CODE(getUser().getEmployee().getCode());
			bean.setEMP_NAME(getUser().getEmployee().getName());
			bean.setEMP_NAME_S(getUser().getEmployee().getNames());
		}
		bean.setINP_DATE(Util.getCurrentDate()); // 新規
		bean.setFILE_NAME(filename);
		bean.setFile(file);

		// 追加処理時にローカルtempフォルダへ格納する為
		byte[] data = null;
		String path = file.getPath();
		try {
			data = ResourceUtil.zipBinary(filename, ResourceUtil.readBinarry(path));
		} catch (IOException ex) {
			//
		}
		bean.setFileData(data);

		// 必須情報
		bean.setKAI_CODE(slip.getCompanyCode());
		bean.setSWK_DEN_NO(slip.getSlipNo());
		bean.setPRG_ID(getProgramCode());
		bean.setUSR_ID(getUserCode());
		bean.setSEQ(dialog.tbl.getRowCount() + 1);

		return bean;
	}

	/**
	 * ファイル持つ<br>
	 * File直接送信を使ってください
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
				// DB反映
				SWK_ATTACH bean = createEntity(filename, data);

				// 伝票に反映して登録処理を行う
				slip.getHeader().getAttachments().add(bean);
				request(getModelClass(), "entry", bean);

				// 画面再反映
				setData(slip.getHeader().getAttachments());

			}
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 添付エンティティの作成<br>
	 * File直接送信を使ってください
	 * 
	 * @param filename
	 * @param data
	 * @return 添付
	 */
	@Deprecated
	protected SWK_ATTACH createEntity(String filename, byte[] data) {
		SWK_ATTACH bean = createBean();
		if (getUser() != null && getUser().getEmployee() != null) {
			bean.setEMP_CODE(getUser().getEmployee().getCode());
			bean.setEMP_NAME(getUser().getEmployee().getName());
			bean.setEMP_NAME_S(getUser().getEmployee().getNames());
		}
		bean.setINP_DATE(Util.getCurrentDate()); // 新規
		bean.setFILE_NAME(filename);
		bean.setFILE_DATA(data);

		// 必須情報
		bean.setKAI_CODE(slip.getCompanyCode());
		bean.setSWK_DEN_NO(slip.getSlipNo());
		bean.setPRG_ID(getProgramCode());
		bean.setUSR_ID(getUserCode());
		bean.setSEQ(dialog.tbl.getRowCount() + 1);

		return bean;
	}

	/**
	 * @return エンティティ作成
	 */
	protected SWK_ATTACH createBean() {
		return new SWK_ATTACH();
	}

	/**
	 * @return FolderKeyNameを戻します。
	 */
	protected String getFolderKeyName() {
		if (parent != null) {
			return parent.getClass().getName() + ".slip.attachment";
		}
		return "slip.attachment";
	}

	/**
	 * 前回保存したフォルダ情報を返す。
	 * 
	 * @param type 種類
	 * @return 前回保存したフォルダ情報
	 */
	protected File getPreviousFolder(String type) {

		String path = SystemUtil.getAisSettingDir();
		File dir = (File) FileUtil.getObject(path + File.separator + getFolderKeyName() + type);

		return dir;
	}

	/**
	 * 指定のフォルダ情報を保存する
	 * 
	 * @param dir フォルダ情報
	 */
	protected void saveFolder(File dir) {
		String path = SystemUtil.getAisSettingDir();
		FileUtil.saveObject(dir, path + File.separator + getFolderKeyName() + ".chooser");
	}

	/**
	 * 照会ボタン押下
	 */
	protected void btnDrillDown_Click() {
		try {

			boolean hasSelectedRows = false;

			// チェックされた行を照会する
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
				// 一覧からデータを選択してください。
				showMessage(dialog, "I00043");
				return;
			}

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 削除ボタン押下
	 */
	protected void btnDelete_Click() {
		try {

			List<SWK_ATTACH> list = new ArrayList<SWK_ATTACH>();

			// チェックされた行を削除する
			for (int row : dialog.tbl.getSelectedRows()) {
				SWK_ATTACH bean = getBean(row);
				list.add(bean);
			}

			if (list.size() == 0) {
				// 一覧からデータを選択してください。
				showMessage(dialog, "I00043");
				return;
			}

			// 選択した行を削除してよろしいですか？
			if (!showConfirmMessage("Q00016")) {
				return;
			}

			if (attachButton != null) {

				attachButton.removeAttachment(list);
				setData(attachButton.getAttachments());

			} else {
				// DB削除
				request(getModelClass(), "delete", list);

				// 最新設定
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
	 * @return マネージャ
	 */
	protected Class getModelClass() {
		return SlipAttachmentManager.class;
	}

	/**
	 * 閉じるボタン押下
	 */
	protected void btnClose_Click() {
		dialog.setVisible(false);
	}

	/**
	 * 伝票設定
	 * 
	 * @param drilldownSlip
	 */
	public void setSlip(Slip drilldownSlip) {
		this.slip = drilldownSlip;

		// データ設定
		setData(slip.getHeader().getAttachments());
	}

	/**
	 * データ設定
	 * 
	 * @param list
	 */
	public void setData(List<SWK_ATTACH> list) {

		dialog.tbl.removeRow();

		if (list == null) {
			return;
		}

		// ローカルに保存する
		try {
			TPrinter printer = new TPrinter(true);

			for (SWK_ATTACH bean : list) {
				if (bean.getFileData() != null) {
					byte[] data = (byte[]) bean.getFileData();

					String tempFileName = printer.createResultFile(bean.getFILE_NAME(), data, true);
					File file = new File(tempFileName);
					bean.setFile(file);
					bean.setFileData(null); // バイナリをクリア
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
	 * 表示
	 */
	public void show() {
		// 表示
		dialog.setVisible(true);
	}

	/**
	 * @param bean
	 * @return 行データ
	 */
	protected List getRow(SWK_ATTACH bean) {

		List list = new ArrayList();
		list.add(bean);
		list.add(bean.getEMP_CODE());
		list.add(bean.getEMP_NAME());
		if (bean.getINP_DATE() == null) {
			list.add(getWord("C02929")); // 新規登録
		} else {
			list.add(DateUtil.toYMDHMSString(bean.getINP_DATE()));
		}
		list.add(bean.getFILE_NAME());
		return list;
	}

	/**
	 * @param row
	 * @return 添付情報
	 */
	protected SWK_ATTACH getBean(int row) {
		return (SWK_ATTACH) getView().tbl.getRowValueAt(row, SC.bean);
	}

	/**
	 * インポート判定
	 * 
	 * @param support
	 * @return true:IMPORT可能
	 */
	protected boolean canImportAttach(TransferSupport support) {
		//
		// System.out.println("test");
		// Transferable t = support.getTransferable();
		// try {
		// List<File> files = (List<File>) support.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
		// } catch (UnsupportedFlavorException e) {
		// // TODO 自動生成された catch ブロック
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO 自動生成された catch ブロック
		// e.printStackTrace();
		// }

		return true;
	}

	/**
	 * インポート処理
	 * 
	 * @param support
	 * @return true:処理成功
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

			// サポートファイルのみ処理する
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
	 * ドラッグ＆ドロップ処理
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
