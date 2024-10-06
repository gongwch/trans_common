package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.plaf.basic.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * ユーザ認証管理マスタパネル
 * 
 * @author roh
 */
public class MG0026UserAuthUpdatePanel extends TPanelBusiness {

	/** UID */
	private static final long serialVersionUID = 2882409920106616855L;

	/** コントロール */
	protected MG0026UserAuthUpdatePanelCtrl ctrl;

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 上位ボタン配置パネル */
	public TMainHeaderPanel pnlButton;

	/** 詳細フィールド配置パネル */
	public TPanel pnlDetail;

	/** フッタパネル */
	public TPanel pnlFooter;

	/** 失敗許容回数フィールド */

	public TLabelComboBox lockOutArrField;

	/** 開放時間フィールド */
	public TLabelNumericField lockOutReleaseField;

	/** 最低文字桁フィールド */
	public TLabelComboBox pwdMinField;

	/** 有効期間フィールド */
	public TLabelNumericField pwdTermField;

	/** 有効範囲フィールド */
	public TLabelComboBox complicateLvlField;

	/** 履歴保持数フィールド */
	public TLabelComboBox historyMaxField;

	/** 期限切れメッセージ通知期間フィールド 追加 */
	public TLabelNumericField pwdTimeLimitMsgField;

	/** 失敗許容回数ラベル */
	public TLabel lockOutArrLabel;

	/** 開放時間ラベル */
	public TLabel lockOutReleaseLabel;

	/** 最低文字桁ラベル */
	public TLabel pwdMinLabel;

	/** 有効期間ラベル */
	public TLabel pwdTermLabel;

	/** 有効範囲ラベル */
	public TLabel complicateLvlLabel;

	/** 期限切れメッセージ通知期間ラベル 追加 */
	public TLabel pwdTimeLimitMsgLabel;

	/**
	 * コンストラクタ
	 * 
	 * @param ctrl コントロール
	 */
	public MG0026UserAuthUpdatePanel(MG0026UserAuthUpdatePanelCtrl ctrl) {
		this.ctrl = ctrl;

		initComponents();

		super.initPanel();
	}

	/**
	 * 構築
	 */
	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlButton = new TMainHeaderPanel();
		btnSettle = new TImageButton(IconType.SETTLE);
		pnlDetail = new TPanel();
		pnlFooter = new TPanel();
		lockOutArrField = new TLabelComboBox();
		lockOutReleaseField = new TLabelNumericField();
		pwdMinField = new TLabelComboBox();
		pwdTermField = new TLabelNumericField();
		complicateLvlField = new TLabelComboBox();
		historyMaxField = new TLabelComboBox();

		lockOutReleaseLabel = new TLabel();
		pwdMinLabel = new TLabel();
		pwdTermLabel = new TLabel();
		complicateLvlLabel = new TLabel();
		lockOutArrLabel = new TLabel();
		pwdTimeLimitMsgField = new TLabelNumericField();
		pwdTimeLimitMsgLabel = new TLabel();
		setLayout(new GridBagLayout());
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));

		pnlButton.setLayout(new GridBagLayout());
		pnlButton.setMaximumSize(new Dimension(800, 40));
		pnlButton.setMinimumSize(new Dimension(800, 40));
		pnlButton.setPreferredSize(new Dimension(800, 40));

		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(8);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 5, 0);
		pnlButton.add(btnSettle, gridBagConstraints);
		btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				ctrl.update();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		add(pnlButton, gridBagConstraints);

		pnlDetail.setLayout(new GridBagLayout());

		pnlDetail.setMaximumSize(new Dimension(1500, 280));
		pnlDetail.setMinimumSize(new Dimension(1500, 280));
		pnlDetail.setPreferredSize(new Dimension(1000, 280));

		// 失敗許容回数
		lockOutArrField.setLabelSize(140);
		lockOutArrField.setComboSize(75);
		lockOutArrField.setTabControlNo(1);
		((BasicComboBoxRenderer) lockOutArrField.getComboBox().getRenderer())
			.setHorizontalAlignment(SwingConstants.RIGHT);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		pnlDetail.add(lockOutArrField, gridBagConstraints);

		lockOutArrLabel.setMaximumSize(new Dimension(400, 20));
		lockOutArrLabel.setPreferredSize(new Dimension(400, 20));
		lockOutArrLabel.setLangMessageID("C01761");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		pnlDetail.add(lockOutArrLabel, gridBagConstraints);

		// 解除分数
		lockOutReleaseField.setPositiveOnly(true);

		lockOutReleaseField.setMaxLength(4);
		lockOutReleaseField.setEnabled(true);
		lockOutReleaseField.setLabelSize(160);
		lockOutReleaseField.setFieldSize(75);
		lockOutReleaseField.setFieldEnabled(true);
		lockOutReleaseField.setTabControlNo(2);
		lockOutReleaseField.setNumericFormat("#");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(15, -20, 0, 0);
		pnlDetail.add(lockOutReleaseField, gridBagConstraints);

		lockOutReleaseLabel.setMaximumSize(new Dimension(400, 20));
		lockOutReleaseLabel.setPreferredSize(new Dimension(400, 20));
		lockOutReleaseLabel.setLangMessageID("C02778");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(15, 0, 0, 0);
		pnlDetail.add(lockOutReleaseLabel, gridBagConstraints);

		// 最低文字数
		pwdMinField.setLangMessageID("C02775");
		pwdMinField.setLabelSize(140);
		pwdMinField.setComboSize(75);
		pwdMinField.setTabControlNo(3);
		((BasicComboBoxRenderer) pwdMinField.getComboBox().getRenderer()).setHorizontalAlignment(SwingConstants.RIGHT);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(15, 0, 0, 0);
		pnlDetail.add(pwdMinField, gridBagConstraints);

		pwdMinLabel.setMaximumSize(new Dimension(400, 20));
		pwdMinLabel.setPreferredSize(new Dimension(400, 20));
		pwdMinLabel.setLangMessageID("C02161");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(15, 0, 0, 0);
		pnlDetail.add(pwdMinLabel, gridBagConstraints);

		// 有効期間
		pwdTermField.setPositiveOnly(true);

		pwdTermField.setMaxLength(3);
		pwdTermField.setEnabled(true);
		pwdTermField.setLabelSize(140);
		pwdTermField.setFieldSize(75);
		pwdTermField.setFieldEnabled(true);
		pwdTermField.setTabControlNo(4);
		pwdTermField.setNumericFormat("#");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.insets = new Insets(15, 0, 0, 0);
		pnlDetail.add(pwdTermField, gridBagConstraints);

		pwdTermLabel.setMaximumSize(new Dimension(400, 20));
		pwdTermLabel.setPreferredSize(new Dimension(400, 20));
		pwdTermLabel.setLangMessageID("C02779");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.insets = new Insets(15, 0, 0, 0);
		pnlDetail.add(pwdTermLabel, gridBagConstraints);

		// 複雑レベル
		complicateLvlField.setLabelSize(140);
		complicateLvlField.setComboSize(100);
		complicateLvlField.setTabControlNo(5);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.insets = new Insets(15, 25, 0, 0);
		pnlDetail.add(complicateLvlField, gridBagConstraints);

		// 履歴数
		historyMaxField.getLabel().setLangMessageID("C02777");
		historyMaxField.setLabelSize(140);
		historyMaxField.setComboSize(75);
		historyMaxField.setTabControlNo(6);
		((BasicComboBoxRenderer) historyMaxField.getComboBox().getRenderer())
			.setHorizontalAlignment(SwingConstants.RIGHT);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.insets = new Insets(15, 0, 0, 0);
		pnlDetail.add(historyMaxField, gridBagConstraints);

		// 期限切れメッセージ通知期間
		pwdTimeLimitMsgField.setLangMessageID("C04285");
		pwdTimeLimitMsgField.setPositiveOnly(true);
		pwdTimeLimitMsgField.setMaxLength(3);
		pwdTimeLimitMsgField.setEnabled(true);
		pwdTimeLimitMsgField.setLabelSize(180);
		pwdTimeLimitMsgField.setFieldSize(75);
		pwdTimeLimitMsgField.setFieldEnabled(true);
		pwdTimeLimitMsgField.setTabControlNo(7);
		pwdTimeLimitMsgField.setNumericFormat("#");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(15, 0, 0, 40);
		pnlDetail.add(pwdTimeLimitMsgField, gridBagConstraints);

		pwdTimeLimitMsgLabel.setMaximumSize(new Dimension(400, 20));
		pwdTimeLimitMsgLabel.setPreferredSize(new Dimension(400, 20));
		pwdTimeLimitMsgLabel.setLangMessageID("C04286");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.insets = new Insets(15, 0, 0, 0);
		pnlDetail.add(pwdTimeLimitMsgLabel, gridBagConstraints);

		// 詳細パネル設定
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		add(pnlDetail, gridBagConstraints);

		// フッタパネル設定
		pnlFooter.setLayout(new GridBagLayout());

		pnlFooter.setMaximumSize(new Dimension(800, 100));
		pnlFooter.setMinimumSize(new Dimension(800, 100));
		pnlFooter.setPreferredSize(new Dimension(800, 100));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(pnlFooter, gridBagConstraints);
	}
}
