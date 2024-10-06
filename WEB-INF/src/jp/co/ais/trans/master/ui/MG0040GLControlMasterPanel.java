package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.gui.*;

/**
 * @author ISFnet China
 */
public class MG0040GLControlMasterPanel extends TPanelBusiness {

	JCVectorDataSource ds = new JCVectorDataSource();

	/** コントロールクラス */
	protected MG0040GLControlMasterPanelCtrl ctrl;

	/**
	 * Creates new form MG0040GLCodeRoleMasterPanel
	 * 
	 * @param ctrl
	 */
	public MG0040GLControlMasterPanel(MG0040GLControlMasterPanelCtrl ctrl) {
		this.ctrl = ctrl;
		// 画面の初期化
		initComponents();
		// スプレッドの初期化
		initSpreadSheet();

		this.ssGLCodeRoleList.addSelectListener(new JCTableListenerForMG0040());
		ssGLCodeRoleList.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyChar() == KeyEvent.VK_ENTER) {
					deleteLock();
				}
			}

			public void keyReleased(KeyEvent arg0) {
				//
			}

			public void keyTyped(KeyEvent arg0) {
				//
			}

		});
		super.initPanel();

	}

	/**
	 * 
	 */
	public class JCTableListenerForMG0040 implements JCSelectListener {

		public void afterSelect(JCSelectEvent ev) {
			EventQueue.invokeLater(new Runnable() {

				public void run() {
					deleteLock();
				}
			});
		}

		public void beforeSelect(JCSelectEvent ev) {
			//
		}

		public void select(JCSelectEvent ev) {
			//
		}
	}

	/**
	 * 
	 */
	public void deleteLock() {
		// 当前行を取得する
		int nomRow = ssGLCodeRoleList.getCurrentRow();
		// データ集を取得する
		TableDataModel model = ssGLCodeRoleList.getDataSource();
		String kaiCode = (String) model.getTableDataItem(nomRow, 0);
		btnDelete.setEnabled(ctrl.checkCode(kaiCode));
	}

	protected void initSpreadSheet() {
		// SpreadSheet を init する
		String[] columnLabelMessageIDs = new String[] { "C00596", "C00145", "C01056", "C00524", "C00454" };
		// スプレッド列幅の初期化
		int[] columnWidths = new int[] { 10, 8, 10, 12, 10, 0, 0, 0, 0, 0, 0, 0, 0 };
		// 列、行表題のスタイル設定
		ssGLCodeRoleList.initSpreadSheet(columnLabelMessageIDs, columnWidths);
		// スプレッドイベントの初期化
		ssGLCodeRoleList.addSpreadSheetSelectChange(btnEdit);

		// Scroll位置設定
		ssGLCodeRoleList.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		ssGLCodeRoleList.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		// 初期表示データの構築
		ds.setNumColumns(5);
		ds.setNumRows(0);

		ssGLCodeRoleList.setDataSource(ds);

	}

	/**
	 * スプレッドデータの設定
	 * 
	 * @param cells スプレッドデータ
	 */
	void setDataList(Vector cells) {
		// 数値を右寄せする
		CellStyleModel defaultStyle = ssGLCodeRoleList.getDefaultCellStyle();
		JCCellStyle centerStyle = new JCCellStyle(defaultStyle);
		centerStyle.setHorizontalAlignment(CellStyleModel.CENTER);
		ssGLCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 1, centerStyle);
		ssGLCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 2, centerStyle);
		ssGLCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 3, centerStyle);
		ssGLCodeRoleList.setCellStyle(JCTableEnum.ALLCELLS, 4, centerStyle);

		this.btnListOutput.setEnabled(cells.size() > 0);
		this.btnCopy.setEnabled(cells.size() > 0);
		this.btnDelete.setEnabled(cells.size() > 0);
		this.btnEdit.setEnabled(cells.size() > 0);
		ds.setCells(cells);
		ds.setNumRows(cells.size());
		ssGLCodeRoleList.setDataSource(ds);
	}

	/** シリアルUID */
	protected static final long serialVersionUID = 6975993877695662983L;

	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlButton = new TPanel();
		btnNew = new TButton();
		btnNew.setVisible(false);
		btnEdit = new TButton();
		btnCopy = new TButton();
		btnCopy.setVisible(false);
		btnDelete = new TButton();
		btnListOutput = new TButton();
		pnlDetail = new TPanel();
		ssGLCodeRoleList = new TTable();

		setLayout(new GridBagLayout());
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(800, 40));
		pnlButton.setMinimumSize(new Dimension(800, 40));
		pnlButton.setPreferredSize(new Dimension(800, 40));

		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setMaximumSize(new Dimension(110, 25));
		btnNew.setMinimumSize(new Dimension(110, 25));
		btnNew.setPreferredSize(new Dimension(110, 25));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 5, 0);
		pnlButton.add(btnNew, gridBagConstraints);

		btnEdit.setLangMessageID("C00481");
		btnEdit.setShortcutKey(KeyEvent.VK_F3);
		btnEdit.setMaximumSize(new Dimension(110, 25));
		btnEdit.setMinimumSize(new Dimension(110, 25));
		btnEdit.setPreferredSize(new Dimension(110, 25));
		btnEdit.setTabControlNo(1);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnEdit, gridBagConstraints);
		btnEdit.addActionListener(new ActionListener() {

			// 編集ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.update();
			}
		});

		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setMaximumSize(new Dimension(110, 25));
		btnCopy.setMinimumSize(new Dimension(110, 25));
		btnCopy.setPreferredSize(new Dimension(110, 25));
		// btnCopy.setTabControlNo(2);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnCopy, gridBagConstraints);

		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setMaximumSize(new Dimension(110, 25));
		btnDelete.setMinimumSize(new Dimension(110, 25));
		btnDelete.setPreferredSize(new Dimension(110, 25));
		btnDelete.setTabControlNo(2);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnDelete, gridBagConstraints);
		btnDelete.addActionListener(new ActionListener() {

			// 削除ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.delete();
			}
		});

		btnListOutput.setLangMessageID("C03084");
		btnListOutput.setShortcutKey(KeyEvent.VK_F6);
		btnListOutput.setTabControlNo(3);
		btnListOutput.setMaximumSize(new Dimension(120, 25));
		btnListOutput.setMinimumSize(new Dimension(120, 25));
		btnListOutput.setPreferredSize(new Dimension(120, 25));
		btnListOutput.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnListOutput.setTabControlNo(3);
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnListOutput, gridBagConstraints);
		btnListOutput.addActionListener(new ActionListener() {

			// リスト出力ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.outptExcel();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		add(pnlButton, gridBagConstraints);

		pnlDetail.setLayout(new BoxLayout(pnlDetail, BoxLayout.X_AXIS));

		pnlDetail.setMaximumSize(new Dimension(780, 545));
		pnlDetail.setMinimumSize(new Dimension(780, 545));
		pnlDetail.setPreferredSize(new Dimension(780, 545));
		pnlDetail.add(ssGLCodeRoleList);
		ssGLCodeRoleList.setTabControlNo(4);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 5, 10, 5);
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(pnlDetail, gridBagConstraints);

	}

	TButton btnCopy;

	TButton btnDelete;

	TButton btnEdit;

	TButton btnListOutput;

	TButton btnNew;

	TPanel pnlButton;

	TPanel pnlDetail;

	TTable ssGLCodeRoleList;

}
