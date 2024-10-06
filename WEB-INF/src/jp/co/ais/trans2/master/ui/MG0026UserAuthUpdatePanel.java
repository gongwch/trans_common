package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * ユーザーマスタの指示画面レイアウト
 * 
 * @author AIS
 */
public class MG0026UserAuthUpdatePanel extends TMainPanel {

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** ロックアウト最大回数 */
	public TLabelComboBox rockOutMaxCount;

	/** ロックアウト最大回数ラベル */
	public TLabel rockOutMaxLabel;

	/** ロックアウト後解放時間 */
	public TLabelNumericField rockOutLatTime;

	/** ロックアウト後解放時間ラベル */
	public TLabel rockOutLatLabel;

	/** 最低パスワード長 */
	public TLabelComboBox minPasslength;

	/** 最低パスワード長のラベル */
	public TLabel minPassLabel;

	/** パスワード期間 */
	public TLabelNumericField passTerm;

	/** パスワード期間のラベル */
	public TLabel passTermLabel;

	/** 複雑レベル */
	public TLabelComboBox difficultLevel;

	/** 履歴保持数 */
	public TLabelComboBox histryCount;

	/** 期間切れメッセージ通知 */
	public TLabelNumericField messagePshu;

	/** 期間切れメッセージ通知のラベル */
	public TLabel messagePshuLabel;

	@Override
	public void initComponents() {

		btnSettle = new TImageButton(IconType.SETTLE);
		rockOutMaxCount = new TLabelComboBox();
		rockOutMaxLabel = new TLabel();
		rockOutLatTime = new TLabelNumericField();
		rockOutLatLabel = new TLabel();
		minPasslength = new TLabelComboBox();
		minPassLabel = new TLabel();
		passTerm = new TLabelNumericField();
		passTermLabel = new TLabel();
		difficultLevel = new TLabelComboBox();
		histryCount = new TLabelComboBox();
		messagePshu = new TLabelNumericField();
		messagePshuLabel = new TLabel();

	}

	@Override
	public void allocateComponents() {

		// 上部
		pnlBody.setLayout(null);
		pnlBody.setMaximumSize(new Dimension(100, 1000));
		pnlBody.setMinimumSize(new Dimension(100, 1000));
		pnlBody.setPreferredSize(new Dimension(100, 1000));

		// 確定ボタン
		int x = 400;
		int y = 600;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(HEADER_LEFT_X, HEADER_Y);
		pnlHeader.add(btnSettle);

		// ロックアウト最大回数
		rockOutMaxCount.setLabelSize(200);
		rockOutMaxCount.setComboSize(100);
		rockOutMaxCount.setSize(330, 20);
		rockOutMaxCount.setLocation(x - 400, y - 580);
		pnlBody.add(rockOutMaxCount);
		// ロックアウト最大回数の回のラベル
		rockOutMaxLabel.setSize(30, 20);
		rockOutMaxLabel.setLocation(x + -70, y - 580);
		rockOutMaxLabel.setLangMessageID("C01761");
		pnlBody.add(rockOutMaxLabel);

		// ロックアウト後解放時間
		rockOutLatTime.setLabelSize(200);
		rockOutLatTime.setFieldSize(100);
		rockOutLatTime.setSize(350, 20);
		rockOutLatTime.setLocation(x - 410, y - 550);
		rockOutLatTime.setMaxLength(4);
		rockOutLatTime.setNumericFormat("####");
		rockOutLatTime.setValue("0");

		rockOutLatTime.setAllowedSpace(false);
		pnlBody.add(rockOutLatTime);
		// ロックアウト後解放時間のラベル
		rockOutLatLabel.setSize(30, 20);
		rockOutLatLabel.setLocation(x - 70, y - 550);
		rockOutLatLabel.setLangMessageID("C02778");
		pnlBody.add(rockOutLatLabel);

		// 最低パスワード長
		minPasslength.setLabelSize(200);
		minPasslength.setComboSize(100);
		minPasslength.setSize(330, 20);
		minPasslength.setLocation(x - 400, y - 520);
		minPasslength.setLangMessageID("C02775");
		pnlBody.add(minPasslength);
		// 最低パスワード長のラベル
		minPassLabel.setSize(30, 20);
		minPassLabel.setLocation(x - 70, y - 520);
		minPassLabel.setLangMessageID("C02161");
		pnlBody.add(minPassLabel);

		// パスワード期間
		passTerm.setLabelSize(200);
		passTerm.setFieldSize(100);
		passTerm.setSize(350, 20);
		passTerm.setLocation(x - 410, y - 490);
		passTerm.setMaxLength(3);
		passTerm.setNumericFormat("###");
		passTerm.setValue("0");
		passTerm.setAllowedSpace(false);

		pnlBody.add(passTerm);
		// パスワード期間のラベル
		passTermLabel.setSize(30, 20);
		passTermLabel.setLocation(x - 70, y - 490);
		passTermLabel.setLangMessageID("C02779");
		pnlBody.add(passTermLabel);

		// 複雑レベル
		difficultLevel.setLabelSize(200);
		difficultLevel.setComboSize(100);
		difficultLevel.setSize(330, 20);
		difficultLevel.setLocation(x - 400, y - 460);

		pnlBody.add(difficultLevel);

		// 履歴保持数
		histryCount.setLabelSize(200);
		histryCount.setComboSize(100);
		histryCount.setSize(330, 20);
		histryCount.setLangMessageID("C02777");
		histryCount.setLocation(x - 400, y - 430);
		pnlBody.add(histryCount);

		// 期限切れメッセージ通知
		messagePshu.setLabelSize(200);
		messagePshu.setFieldSize(100);
		messagePshu.setSize(350, 20);
		messagePshu.setLocation(x - 410, y - 400);
		messagePshu.setMaxLength(3);
		messagePshu.setLangMessageID("C04285");
		messagePshu.setValue("0");
		messagePshu.setNumericFormat("###");
		messagePshu.setAllowedSpace(false);

		pnlBody.add(messagePshu);

		// 期限切れメッセージ通知のラベル
		messagePshuLabel.setSize(30, 20);
		messagePshuLabel.setLocation(x - 70, y - 400);
		messagePshuLabel.setLangMessageID("C04286");
		pnlBody.add(messagePshuLabel);

	}

	@Override
	public void setTabIndex() {
		int i = 0;
		rockOutMaxCount.setTabControlNo(i++);
		rockOutLatTime.setTabControlNo(i++);
		minPasslength.setTabControlNo(i++);
		passTerm.setTabControlNo(i++);
		difficultLevel.setTabControlNo(i++);
		histryCount.setTabControlNo(i++);
		messagePshu.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
	}

}
