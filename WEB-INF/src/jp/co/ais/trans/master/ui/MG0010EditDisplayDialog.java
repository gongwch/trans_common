package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * 環境設定マスタDialog
 * 
 * @author liuchengcheng
 */
public class MG0010EditDisplayDialog extends TDialog {

	/** シリアルUID */
	private static final long serialVersionUID = 7320828094241597333L;

	/** コントロールクラス */
	protected MG0010EditDisplayDialogCtrl ctrl;

	/** 確定されたかどうか */
	boolean isSettle = false;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param modal モーダルダイアログ可否(true: モーダルモード)
	 * @param ctrl コントロールクラス
	 * @param titleID
	 */
	MG0010EditDisplayDialog(Frame parent, boolean modal, MG0010EditDisplayDialogCtrl ctrl, String titleID) {
		super(parent, modal);
		this.ctrl = ctrl;
		this.setResizable(false);
		initComponents();
		setLangMessageID(titleID);

		setSize(700, 400);

		super.initDialog();
	}

	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlButton = new TPanel();
		btnSettle = new TButton();
		btnReturn = new TButton();
		pnlDetail = new TPanel();
		ctrlCompanyCode = new TLabelField();
		ctrlCompanyName = new TLabelField();
		ctrlCompanyAbbreviatedName = new TLabelField();
		ctrlAddress1 = new TLabelField();
		ctrlAddress2 = new TLabelField();
		ctrlAddressKana = new TLabelField();
		ctrlPostcode = new TLabelField();
		ctrlTelephoneNumber = new TLabelField();
		ctrlFaxNumber = new TLabelField();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();
		btnBackgroundColor = new TButton();
		txtBackgroundColor = new TTextField();

		getContentPane().setLayout(new GridBagLayout());

		pnlButton.setLayout(new GridBagLayout());
		pnlButton.setMaximumSize(new Dimension(700, 40));
		pnlButton.setMinimumSize(new Dimension(700, 40));
		pnlButton.setPreferredSize(new Dimension(700, 40));

		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setTabControlNo(14);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints.anchor = GridBagConstraints.SOUTH;
		gridBagConstraints.insets = new Insets(15, 395, 5, 10);
		pnlButton.add(btnSettle, gridBagConstraints);
		btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				isSettle = true;
				ctrl.disposeDialog();
			}
		});

		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setTabControlNo(15);
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setPreferredSize(new Dimension(110, 25));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(15, 0, 5, 0);
		pnlButton.add(btnReturn, gridBagConstraints);
		btnReturn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				isSettle = false;
				ctrl.disposeDialog();
			}
		});
		btnReturn.setForClose(true);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 0, 0);
		getContentPane().add(pnlButton, gridBagConstraints);

		pnlDetail.setLayout(new GridBagLayout());

		pnlDetail.setMaximumSize(new Dimension(660, 250));
		pnlDetail.setMinimumSize(new Dimension(660, 250));
		pnlDetail.setPreferredSize(new Dimension(660, 250));
		ctrlCompanyCode.setFieldSize(120);
		ctrlCompanyCode.setLabelSize(70);
		ctrlCompanyCode.setLangMessageID("C00596");
		ctrlCompanyCode.getField().setAllowedSpace(false);
		ctrlCompanyCode.setMaxLength(10);
		ctrlCompanyCode.setTabControlNo(1);
		ctrlCompanyCode.setImeMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(10, 5, 5, 0);
		pnlDetail.add(ctrlCompanyCode, gridBagConstraints);

		ctrlCompanyName.setFieldSize(450);
		ctrlCompanyName.setLabelSize(65);
		ctrlCompanyName.setLangMessageID("C00685");
		ctrlCompanyName.setMaxLength(40);
		ctrlCompanyName.setTabControlNo(2);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 5, 0);
		pnlDetail.add(ctrlCompanyName, gridBagConstraints);

		ctrlCompanyAbbreviatedName.setFieldSize(230);
		ctrlCompanyAbbreviatedName.setLabelSize(65);
		ctrlCompanyAbbreviatedName.setLangMessageID("C00686");
		ctrlCompanyAbbreviatedName.setMaxLength(20);
		ctrlCompanyAbbreviatedName.setTabControlNo(3);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 5, 0);
		pnlDetail.add(ctrlCompanyAbbreviatedName, gridBagConstraints);

		ctrlAddress1.setFieldSize(560);
		ctrlAddress1.setLabelSize(65);
		ctrlAddress1.setLangMessageID("C00687");
		ctrlAddress1.setMaxLength(50);
		ctrlAddress1.setTabControlNo(4);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 5, 0);
		pnlDetail.add(ctrlAddress1, gridBagConstraints);

		ctrlAddress2.setFieldSize(560);
		ctrlAddress2.setLabelSize(65);
		ctrlAddress2.setLangMessageID("C00688");
		ctrlAddress2.setMaxLength(50);
		ctrlAddress2.setTabControlNo(5);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 5, 0);
		pnlDetail.add(ctrlAddress2, gridBagConstraints);

		ctrlAddressKana.setFieldSize(560);
		ctrlAddressKana.setLabelSize(65);
		ctrlAddressKana.setLangMessageID("C00258");
		ctrlAddressKana.setMaxLength(80);
		ctrlAddressKana.setTabControlNo(6);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.gridwidth = 5;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 5, 20);
		pnlDetail.add(ctrlAddressKana, gridBagConstraints);

		ctrlPostcode.setFieldSize(75);
		ctrlPostcode.setLabelHAlignment(2);
		ctrlPostcode.setLabelSize(65);
		ctrlPostcode.setLangMessageID("C00527");
		ctrlPostcode.setMaxLength(10);
		ctrlPostcode.setTabControlNo(7);

		ctrlPostcode.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 5, 0);
		pnlDetail.add(ctrlPostcode, gridBagConstraints);

		ctrlTelephoneNumber.setFieldSize(90);
		ctrlTelephoneNumber.setLabelHAlignment(2);
		ctrlTelephoneNumber.setLabelSize(65);
		ctrlTelephoneNumber.setLangMessageID("C00393");
		ctrlTelephoneNumber.setMaxLength(12);
		ctrlTelephoneNumber.setTabControlNo(8);

		ctrlTelephoneNumber.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 7;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 5, 0);
		pnlDetail.add(ctrlTelephoneNumber, gridBagConstraints);

		ctrlFaxNumber.setFieldSize(90);
		ctrlFaxNumber.setLabelHAlignment(2);
		ctrlFaxNumber.setLabelSize(70);
		ctrlFaxNumber.setLangMessageID("C00690");
		ctrlFaxNumber.setMaxLength(12);
		ctrlFaxNumber.setTabControlNo(9);
		ctrlFaxNumber.setImeMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 7;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 15, 5, 0);
		pnlDetail.add(ctrlFaxNumber, gridBagConstraints);

		dtBeginDate.setLabelHAlignment(3);
		dtBeginDate.setLabelSize(70);
		dtBeginDate.setLangMessageID("C00055");
		dtBeginDate.setTabControlNo(10);
		dtBeginDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtBeginDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 8;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		pnlDetail.add(dtBeginDate, gridBagConstraints);

		dtEndDate.setLabelHAlignment(3);
		dtEndDate.setLabelSize(70);
		dtEndDate.setLangMessageID("C00261");
		dtEndDate.setTabControlNo(11);
		dtEndDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtEndDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 8;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 15, 0, 0);
		pnlDetail.add(dtEndDate, gridBagConstraints);

		btnBackgroundColor.setLangMessageID("C00428");
		btnBackgroundColor.setTabControlNo(12);
		btnBackgroundColor.setMaximumSize(new Dimension(85, 20));
		btnBackgroundColor.setMinimumSize(new Dimension(85, 20));
		btnBackgroundColor.setPreferredSize(new Dimension(85, 20));
		btnBackgroundColor.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				MG0010BackgroundColorDialogCtrl dialogCtrl = new MG0010BackgroundColorDialogCtrl(
					MG0010EditDisplayDialog.this);
				dialogCtrl.setColor(txtBackgroundColor.getBackground());
				dialogCtrl.show();
				if (dialogCtrl.isSettle()) {
					Color color = dialogCtrl.getColor();
					txtBackgroundColor.setBackground(color);
					String text = String.format("#%1$02X%2$02X%3$02X", color.getRed(), color.getGreen(), color
						.getBlue());
					txtBackgroundColor.setEditable(true);
					txtBackgroundColor.setValue(text);
					txtBackgroundColor.setEditable(false);
				}
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 9;
		gridBagConstraints.insets = new Insets(5, 10, 15, 0);
		pnlDetail.add(btnBackgroundColor, gridBagConstraints);

		txtBackgroundColor.setTabControlNo(13);
		txtBackgroundColor.setMaximumSize(new Dimension(75, 20));
		txtBackgroundColor.setMinimumSize(new Dimension(75, 20));
		txtBackgroundColor.setPreferredSize(new Dimension(75, 20));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 9;
		gridBagConstraints.insets = new Insets(6, 0, 15, 10);
		pnlDetail.add(txtBackgroundColor, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(5, 0, 15, 0);
		getContentPane().add(pnlDetail, gridBagConstraints);
		dtBeginDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		dtBeginDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 1, 1));
		dtBeginDate.setValue(dtBeginDate.getCalendar().getMinimumDate());

		dtEndDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		dtEndDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 1, 1));
		dtEndDate.setValue(dtEndDate.getCalendar().getMaximumDate());

		pack();
	}

	TButton btnBackgroundColor;

	TButton btnReturn;

	TButton btnSettle;

	TLabelField ctrlAddress1;

	TLabelField ctrlAddress2;

	TLabelField ctrlAddressKana;

	TLabelField ctrlCompanyAbbreviatedName;

	TLabelField ctrlCompanyCode;

	TLabelField ctrlCompanyName;

	TLabelField ctrlFaxNumber;

	TLabelField ctrlPostcode;

	TLabelField ctrlTelephoneNumber;

	TLabelPopupCalendar dtBeginDate;

	TLabelPopupCalendar dtEndDate;

	TPanel pnlButton;

	TPanel pnlDetail;

	TTextField txtBackgroundColor;
}
