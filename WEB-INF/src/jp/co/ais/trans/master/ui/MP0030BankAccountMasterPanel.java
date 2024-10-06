package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TTable;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * @author yanwei
 */
public class MP0030BankAccountMasterPanel extends jp.co.ais.trans.common.gui.TPanelBusiness {

	/** コントロールクラス */
	private MP0030BankAccountMasterPanelCtrl ctrl;

	/** スプレッドシートDataSource */
	JCVectorDataSource ds = new JCVectorDataSource();

	/**
	 * コンストラクタ.
	 * 
	 * @param ctrl コントロールクラス
	 */
	MP0030BankAccountMasterPanel(MP0030BankAccountMasterPanelCtrl ctrl) {

		this.ctrl = ctrl;

		// 画面の初期化
		initComponents();
		// スプレッドの初期化
		initSpreadSheet();

		setBtnLock(false);

		super.initPanel();
	}

	/**
	 * パネルでボタンの状態を設定する
	 * 
	 * @param bool true或false
	 */
	public void setBtnLock(Boolean bool) {

		this.btnEdit.setEnabled(bool);
		this.btnCopy.setEnabled(bool);
		this.btnDelete.setEnabled(bool);
	}

	protected void initSpreadSheet() {
		// SpreadSheet を init する
		String[] columnLabelMessageIDs = new String[] { "C00596", "C01879", "C02145", "C00665", "C00880", "C00779",
				"C00781", "C02055", "C02060", "C00858", "C10133", "C00860", "C00861", "C01326", "C00794", "C01117",
				"C01122", "C00571", "C00572", "C00602", "C00603", "C00055", "C00261" };
		// スプレッド列幅の初期化
		int[] columnWidths = new int[] { 0, 10, 20, 6, 20, 6, 20, 6, 20, 10, 20, 20, 20, 5, 10, 7, 7, 10, 10, 10, 10,
				6, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0

		};
		// 列、行表題のスタイル設定
		ssJournal.initSpreadSheet(columnLabelMessageIDs, columnWidths);

		// スプレッドイベントの初期化
		ssJournal.addSpreadSheetSelectChange(btnEdit);

		// Scroll位置設定
		ssJournal.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		ssJournal.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		// 初期表示データの構築
		ds.setNumColumns(columnWidths.length);
		ds.setNumRows(0);

		ssJournal.setDataSource(ds);
	}

	/**
	 * スプレッドデータの設定
	 * 
	 * @param cells スプレッドデータ
	 */
	void setDataList(Vector cells) {
		ds.setCells(cells);

		// Set the number of rows in the data source.
		ds.setNumRows(cells.size());

		ssJournal.setDataSource(ds);

		// 数値を右寄せする
		CellStyleModel defaultStyle = ssJournal.getDefaultCellStyle();
		JCCellStyle centerStyle = new JCCellStyle(defaultStyle);
		centerStyle.setHorizontalAlignment(CellStyleModel.CENTER);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, 3, centerStyle);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, 15, centerStyle);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, 16, centerStyle);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, 21, centerStyle);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, 22, centerStyle);

		// データがあると、ボタンの状態を使用可能になる
		setBtnLock(cells.size() > 0);
	}

	/**
	 * 画面コンポーネントの構築
	 */
	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlBusiness = new TPanel();
		pnlButton = new TMainHeaderPanel();
		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnEdit = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnListOutput = new TImageButton(IconType.EXCEL);
		pnlHeader = new TPanel();
		pnlRangeSpecification = new TPanel();
		ctrlBeginBankAccount = new TButtonField();
		ctrlEndBankAccount = new TButtonField();
		lblBegin = new TLabel();
		lblEnd = new TLabel();
		txtBeginDepositTypeAccountNumber = new TTextField();
		txtEndDepositTypeAccountNumber = new TTextField();
		pnlJournal = new TPanel();
		ssJournal = new TTable();

		setLayout(new GridBagLayout());
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));

		pnlBusiness.setLayout(new GridBagLayout());
		pnlBusiness.setMaximumSize(new Dimension(800, 600));
		pnlBusiness.setMinimumSize(new Dimension(800, 600));
		pnlBusiness.setPreferredSize(new Dimension(800, 600));
		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(800, 40));
		pnlButton.setMinimumSize(new Dimension(800, 40));
		pnlButton.setPreferredSize(new Dimension(800, 40));

		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setMaximumSize(new Dimension(110, 25));
		btnNew.setMinimumSize(new Dimension(110, 25));
		btnNew.setPreferredSize(new Dimension(110, 25));
		btnNew.setTabControlNo(6);
		btnNew.addActionListener(new ActionListener() {

			// 新規ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.insert();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 5, 0);
		pnlButton.add(btnNew, gridBagConstraints);

		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setMaximumSize(new Dimension(110, 25));
		btnSearch.setMinimumSize(new Dimension(110, 25));
		btnSearch.setPreferredSize(new Dimension(110, 25));
		btnSearch.setTabControlNo(7);
		btnSearch.addActionListener(new ActionListener() {

			// 検索ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.find();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnSearch, gridBagConstraints);

		btnEdit.setLangMessageID("C00481");
		btnEdit.setShortcutKey(KeyEvent.VK_F3);
		btnEdit.setMaximumSize(new Dimension(110, 25));
		btnEdit.setMinimumSize(new Dimension(110, 25));
		btnEdit.setPreferredSize(new Dimension(110, 25));
		btnEdit.setTabControlNo(8);
		btnEdit.addActionListener(new ActionListener() {

			// 編集ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.update();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnEdit, gridBagConstraints);

		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setMaximumSize(new Dimension(110, 25));
		btnCopy.setMinimumSize(new Dimension(110, 25));
		btnCopy.setPreferredSize(new Dimension(110, 25));
		btnCopy.setTabControlNo(9);
		btnCopy.addActionListener(new ActionListener() {

			// 複写ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.copy();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnCopy, gridBagConstraints);

		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setMaximumSize(new Dimension(110, 25));
		btnDelete.setMinimumSize(new Dimension(110, 25));
		btnDelete.setPreferredSize(new Dimension(110, 25));
		btnDelete.setTabControlNo(10);
		btnDelete.addActionListener(new ActionListener() {

			// 削除ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.delete();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnDelete, gridBagConstraints);

		btnListOutput.setLangMessageID("C01545");
		btnListOutput.setShortcutKey(KeyEvent.VK_F6);
		btnListOutput.setMaximumSize(new Dimension(130, 25));
		btnListOutput.setMinimumSize(new Dimension(130, 25));
		btnListOutput.setPreferredSize(new Dimension(130, 25));
		btnListOutput.setTabControlNo(11);
		btnListOutput.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// リスト出力ボタンを押下
				ctrl.outptExcel();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnListOutput, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		pnlBusiness.add(pnlButton, gridBagConstraints);

		pnlHeader.setLayout(new GridBagLayout());

		pnlHeader.setMaximumSize(new Dimension(800, 83));
		pnlHeader.setMinimumSize(new Dimension(800, 83));
		pnlHeader.setPreferredSize(new Dimension(800, 83));

		pnlRangeSpecification.setLayout(new GridBagLayout());
		pnlRangeSpecification.setLangMessageID("C00433");
		pnlRangeSpecification.setMaximumSize(new Dimension(780, 80));
		pnlRangeSpecification.setMinimumSize(new Dimension(780, 80));
		pnlRangeSpecification.setPreferredSize(new Dimension(780, 80));

		ctrlBeginBankAccount.setButtonSize(85);
		ctrlBeginBankAccount.setFieldSize(120);
		ctrlBeginBankAccount.setLangMessageID("C00857");
		ctrlBeginBankAccount.setMaxLength(10);
		ctrlBeginBankAccount.setMaximumSize(new Dimension(420, 20));
		ctrlBeginBankAccount.setMinimumSize(new Dimension(420, 20));
		ctrlBeginBankAccount.setNoticeSize(350);
		ctrlBeginBankAccount.setTabControlNo(1);
		ctrlBeginBankAccount.setImeMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlRangeSpecification.add(ctrlBeginBankAccount, gridBagConstraints);

		ctrlEndBankAccount.setButtonSize(85);
		ctrlEndBankAccount.setFieldSize(120);
		ctrlEndBankAccount.setLangMessageID("C00857");
		ctrlEndBankAccount.setMaxLength(10);
		ctrlEndBankAccount.setNoticeSize(350);
		ctrlEndBankAccount.setTabControlNo(3);
		ctrlEndBankAccount.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 10, 0);
		pnlRangeSpecification.add(ctrlEndBankAccount, gridBagConstraints);

		lblBegin.setLangMessageID("C01497");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 5, 0, 5);
		pnlRangeSpecification.add(lblBegin, gridBagConstraints);

		lblEnd.setLangMessageID("C01503");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 5, 0, 5);
		pnlRangeSpecification.add(lblEnd, gridBagConstraints);

		txtBeginDepositTypeAccountNumber.setEnabled(false);
		txtBeginDepositTypeAccountNumber.setMaxLength(40);
		txtBeginDepositTypeAccountNumber.setMaximumSize(new Dimension(130, 20));
		txtBeginDepositTypeAccountNumber.setMinimumSize(new Dimension(130, 20));
		txtBeginDepositTypeAccountNumber.setPreferredSize(new Dimension(130, 20));
		txtBeginDepositTypeAccountNumber.setTabControlNo(2);
		txtBeginDepositTypeAccountNumber.setTabEnabled(false);
		txtBeginDepositTypeAccountNumber.setEditable(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		pnlRangeSpecification.add(txtBeginDepositTypeAccountNumber, gridBagConstraints);

		txtEndDepositTypeAccountNumber.setEnabled(false);
		txtEndDepositTypeAccountNumber.setMaxLength(40);
		txtEndDepositTypeAccountNumber.setMaximumSize(new Dimension(130, 20));
		txtEndDepositTypeAccountNumber.setMinimumSize(new Dimension(130, 20));
		txtEndDepositTypeAccountNumber.setPreferredSize(new Dimension(130, 20));
		txtEndDepositTypeAccountNumber.setTabControlNo(4);
		txtEndDepositTypeAccountNumber.setTabEnabled(false);
		txtEndDepositTypeAccountNumber.setEditable(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(5, 0, 10, 0);
		pnlRangeSpecification.add(txtEndDepositTypeAccountNumber, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		pnlHeader.add(pnlRangeSpecification, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		pnlBusiness.add(pnlHeader, gridBagConstraints);

		pnlJournal.setLayout(new javax.swing.BoxLayout(pnlJournal, javax.swing.BoxLayout.X_AXIS));

		pnlJournal.setMaximumSize(new Dimension(780, 460));
		pnlJournal.setMinimumSize(new Dimension(780, 460));
		pnlJournal.setPreferredSize(new Dimension(780, 460));
		ssJournal.setTabControlNo(5);
		pnlJournal.add(ssJournal);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 5, 10, 5);
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		pnlBusiness.add(pnlJournal, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(pnlBusiness, gridBagConstraints);
	}

	TImageButton btnCopy;

	TImageButton btnDelete;

	TImageButton btnEdit;

	TImageButton btnListOutput;

	TImageButton btnNew;

	TImageButton btnSearch;

	TButtonField ctrlBeginBankAccount;

	TButtonField ctrlEndBankAccount;

	TLabel lblBegin;

	TLabel lblEnd;

	TMainHeaderPanel pnlButton;

	TPanel pnlHeader;

	TPanel pnlJournal;

	TPanel pnlRangeSpecification;

	TTable ssJournal;

	TPanel pnlBusiness;

	TTextField txtBeginDepositTypeAccountNumber;

	TTextField txtEndDepositTypeAccountNumber;

}
