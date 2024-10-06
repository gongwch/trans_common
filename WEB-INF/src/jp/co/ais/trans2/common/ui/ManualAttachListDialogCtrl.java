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
 * 添付ダイアログCtrl
 */
public class ManualAttachListDialogCtrl extends TController {

	/** 親パネル */
	protected TPanel parent;

	/** 添付ダイアログ */
	protected ManualAttachListDialog dialog = null;

	/** 最大ファイルサイズ(MB) */
	public static int maxFileSize = 40;

	/** 拡張子 */
	public static String[] supportFileExts = null;

	/** 添付ファイル */
	protected List<MANUAL_ATTACH> attachments = null;

	static {
		try {
			maxFileSize = Util.avoidNullAsInt(ClientConfig.getProperty("trans.manul.use.attachment.max.size"));
		} catch (Throwable e) {
			// 処理なし
		}

		try {
			supportFileExts = ClientConfig.getProperties("trans.manul.attachment.support.file.exts");
		} catch (Throwable e) {
			supportFileExts = new String[] { ExtensionType.PDF.value, ExtensionType.XLS.value, ExtensionType.XLSX.value, "ppt", "pptx" };
		}

	}

	/**
	 * コンストラクタ.
	 * 
	 * @param parent 親コンポーネント
	 */
	public ManualAttachListDialogCtrl(TPanel parent) {
		this.parent = parent;

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
	protected ManualAttachListDialog createView() {
		return new ManualAttachListDialog(getCompany(), parent.getParentFrame());
	}

	@Override
	public ManualAttachListDialog getView() {
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
			TFileFilter filter = new TFileFilter(getWord("C11613"), supportFileExts);

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
	 * 指定ファイルを添付する
	 * 
	 * @param list
	 * @return true 追加成功
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
	 * 指定ファイルを添付する
	 * 
	 * @param tFiles
	 * @return true 追加成功
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
			// // ファイルサイズが4MBを超えています。
			// showMessage("I00446", maxFileSize);
			// return false;
			// }

			// if (data == null) {
			// // ファイルが存在してない場合、処理不要
			// return false;
			// }

			MANUAL_ATTACH bean = createEntity(filename, file);
			list.add(bean);

			if (i == 0) {
				// フォルダ記憶
				saveFolder(file);
			}
		}

		if (!list.isEmpty()) {
			// マニュアルに反映して登録処理を行う
			List<MANUAL_ATTACH> newList = (List<MANUAL_ATTACH>) request(getModelClass(), "entryManual", list);
			setData(newList);
		}

		return true;
	}

	/**
	 * 添付エンティティの作成
	 * 
	 * @param filename
	 * @param file
	 * @return 添付
	 */
	protected MANUAL_ATTACH createEntity(String filename, File file) {
		MANUAL_ATTACH bean = createBean();
		bean.setINP_DATE(Util.getCurrentDate()); // 新規
		bean.setFILE_NAME(filename);
		bean.setFile(file);

		// 必須情報
		bean.setUSR_ID(getUserCode());
		bean.setSEQ(dialog.tbl.getRowCount() + 1);

		return bean;
	}

	/**
	 * @return エンティティ作成
	 */
	protected MANUAL_ATTACH createBean() {
		return new MANUAL_ATTACH();
	}

	/**
	 * @return FolderKeyNameを戻します。
	 */
	protected String getFolderKeyName() {
		if (parent != null) {
			return parent.getClass().getName() + ".manual.attachment";
		}
		return "manual.attachment";
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

			List<MANUAL_ATTACH> list = new ArrayList<MANUAL_ATTACH>();

			int[] selectedRows = dialog.tbl.getSelectedRows();

			// チェックされた行を削除する
			for (int row : selectedRows) {
				MANUAL_ATTACH bean = getBean(row);
				list.add(bean);
			}

			if (list.isEmpty()) {
				// 一覧からデータを選択してください。
				showMessage(dialog, "I00043");
				return;
			}

			// チェックされた行を照会する
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
	 * 削除ボタン押下
	 */
	protected void btnDelete_Click() {
		try {

			List<MANUAL_ATTACH> list = new ArrayList<MANUAL_ATTACH>();

			int[] selectedRows = dialog.tbl.getSelectedRows();

			// チェックされた行を削除する
			for (int row : selectedRows) {
				MANUAL_ATTACH bean = getBean(row);
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

			// DB削除
			request(getModelClass(), "deleteManual", list);

			// 最新設定
			for (int i = selectedRows.length - 1; i >= 0; i--) {
				dialog.tbl.removeRow(selectedRows[i]);
			}

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * @return マネージャ
	 */
	protected Class getModelClass() {
		return SaveManager.class;
	}

	/**
	 * 閉じるボタン押下
	 */
	protected void btnClose_Click() {
		dialog.setVisible(false);
	}

	/**
	 * データ設定
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
	 * 表示
	 * 
	 * @throws TException
	 */
	public void show() throws TException {

		// データ取得
		List<MANUAL_ATTACH> list = (List<MANUAL_ATTACH>) request(getModelClass(), "getManual");

		setData(list);

		// 表示
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}

	/**
	 * @param bean
	 * @return 行データ
	 */
	protected List getRow(MANUAL_ATTACH bean) {

		List list = new ArrayList();
		list.add(bean);
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
	protected MANUAL_ATTACH getBean(int row) {
		return (MANUAL_ATTACH) getView().tbl.getRowValueAt(row, MANUAL_SC.bean);
	}

	/**
	 * インポート判定
	 * 
	 * @param support
	 * @return true:IMPORT可能
	 */
	@SuppressWarnings("unused")
	protected boolean canImporManualAttach(TransferSupport support) {
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
			return canImporManualAttach(support);
		}

		@Override
		public boolean importData(TransferSupport support) {
			return importDataAttach(support);
		}

	}
}
