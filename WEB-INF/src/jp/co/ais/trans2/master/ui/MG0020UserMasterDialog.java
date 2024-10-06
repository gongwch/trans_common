package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ユーザーマスタの編集画面
 * 
 * @author AIS
 */
public class MG0020UserMasterDialog extends TDialog {

	/** アクセス権限設定フラグを使う */
	protected static final boolean USE_ACCESS_FLAG = ClientConfig.isFlagOn("trans.user.mst.use.access.flag");

	/** サイナー系情報を使う */
	protected static final boolean USE_BL_SIGNER = ClientConfig.isFlagOn("trans.user.mst.use.bl.signer");

	/** サイナー画像を使う */
	protected static final boolean USE_SIGNER_ATTACH = ClientConfig.isFlagOn("trans.user.mst.use.signer.attach");

	/** ダッシュボード権限を使う */
	protected static final boolean DASH_BOARD_CONDITION = ClientConfig.isFlagOn("trans.user.master.use.dashboard");

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** ユーザーコード */
	public TLabelField ctrlCode;

	/** ユーザー名称 */
	public TLabelField ctrlName;

	/** ユーザー略称 */
	public TLabelField ctrlNames;

	/** ユーザー検索名称 */
	public TLabelField ctrlNamek;

	/** パスワード */
	public TLabelPasswordField ctrlPassword;

	/** プログラムロール */
	public TProgramRoleReference ctrlProgramRole;

	/** 参照ロール */
	public TUserRoleReference ctrlUserRole;

	/** 承認権限グループ */
	public TAprvRoleGroupReference ctrlAprvGroup;

	/** INV.SIGNER DEPT */
	public TLabelField ctrlSignerDept;

	/** INV.SIGNER TITLE */
	public TLabelField ctrlSignerTitle;

	/** INV.SIGNER NAME */
	public TLabelField ctrlSignerName;

	/** INV.SIGN FILE ボタン */
	public TSignAttachButton btnSignAttach;

	/** INV.SIGN FILE ボタン */
	public TLabelField ctrlSignAttach;

	/** E-MAIL */
	public TLabelField ctrlEMailAddress;

	/** 更新権限レベル */
	public TLabelComboBox cboUpdateAuthorityLevel;

	/** 経理担当者区分 */
	public TLabelComboBox cboAccountingPerson;

	/** 社員フィールド */
	public TEmployeeReference ctrlEmployeeReference;

	/** 部門フィールド */
	public TDepartmentReference ctrlDepartmentReference;

	/** 言語フィールド */
	public TLanguageComboBox ctrlLangcode;

	/** 開始年月日 */
	public TLabelPopupCalendar dtBeginDate;

	/** 終了年月日 */
	public TLabelPopupCalendar dtEndDate;

	/** アクセス権限 */
	public TLabelComboBox[] ctrlAccessPermission;

	/** システム区分一覧 */
	public TTable tblSysState;

	/** ダッシュボード権限ラベル */
	public TLabel lblSys;

	/** チェック */
	public TCheckBox chkCheck;

	/**
	 * システム区分列名列挙体
	 */
	public enum SC {
		/** システム区分 */
		sysKbn,
		/** チェック */
		chk,
		/** システム区分名称 */
		sysKbnName;
	}

	/**
	 * @param parent
	 * @param mordal
	 */
	public MG0020UserMasterDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

	/**
	 * コンポーネント初期化
	 */
	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlCode = new TLabelField();
		ctrlName = new TLabelField();
		ctrlNames = new TLabelField();
		ctrlNamek = new TLabelField();
		ctrlPassword = new TLabelPasswordField();
		ctrlProgramRole = new TProgramRoleReference();
		ctrlUserRole = new TUserRoleReference();
		ctrlSignerDept = new TLabelField();
		ctrlSignerTitle = new TLabelField();
		ctrlSignerName = new TLabelField();
		ctrlEMailAddress = new TLabelField();
		cboUpdateAuthorityLevel = new TLabelComboBox();
		cboAccountingPerson = new TLabelComboBox();
		ctrlEmployeeReference = new TEmployeeReference();
		ctrlDepartmentReference = new TDepartmentReference();
		ctrlAprvGroup = new TAprvRoleGroupReference();
		ctrlLangcode = new TLanguageComboBox();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();
		lblSys = new TLabel();
		tblSysState = new TTable();
		chkCheck = new TCheckBox();

		{
			tblSysState.addColumn(SC.sysKbn, "", -1);
			tblSysState.addColumn(SC.chk, "", 30, chkCheck);
			tblSysState.addColumn(SC.sysKbnName, "C00980", 110);// システム区分
			tblSysState.setRowLabelNumber(false);
		}

		{
			// アクセス権限
			int accessCount = User.ACCESS_FLAG_COUNT;

			ctrlAccessPermission = new TLabelComboBox[accessCount];
			for (int i = 0; i < accessCount; i++) {
				ctrlAccessPermission[i] = new TLabelComboBox();
			}
		}

		btnSignAttach = new TSignAttachButton();
		ctrlSignAttach = new TLabelField();

	}

	/**
	 * コンポーネント配置
	 */
	@Override
	public void allocateComponents() {
		int sizeX;
		int sizeY;
		int sysKbnY;
		int num = 0;
		boolean flg = false;

		if (USE_ACCESS_FLAG && DASH_BOARD_CONDITION) {
			// アクセス権限コンボボックスの数量取得
			num = getBoxNum(num);
		}

		sizeX = 720;
		if (USE_BL_SIGNER || (DASH_BOARD_CONDITION && num >= 7)) {
			sizeY = 700;
		} else {
			sizeY = 580;
		}
		setSize(sizeX, sizeY);

		// 確定ボタン
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(getWidth() - 240 - HEADER_MARGIN_X, HEADER_Y);
		pnlHeader.add(btnSettle);

		// 戻るボタン
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(getWidth() - 130, HEADER_Y);
		pnlHeader.add(btnClose);

		pnlBody.setLayout(null);

		int x = 20;
		int y = 10;

		// ユーザーコード
		ctrlCode.setFieldSize(80);
		ctrlCode.setLocation(x, y);
		ctrlCode.setMaxLength(10);
		ctrlCode.setImeMode(false);
		ctrlCode.setAllowedSpace(false);
		ctrlCode.setLangMessageID("C00589");
		pnlBody.add(ctrlCode);

		// ユーザー名称
		y += ctrlCode.getHeight() + 10;
		ctrlName.setFieldSize(300);
		ctrlName.setLocation(x, y);
		ctrlName.setLangMessageID("C00691");
		ctrlName.setMaxLength(40);
		pnlBody.add(ctrlName);

		// ユーザー略称
		y += ctrlName.getHeight() + 10;
		ctrlNames.setFieldSize(300);
		ctrlNames.setLocation(x, y);
		ctrlNames.setLangMessageID("C00692");
		ctrlNames.setMaxLength(20);
		pnlBody.add(ctrlNames);

		// ユーザー検索名称;
		y += ctrlNames.getHeight() + 10;
		ctrlNamek.setFieldSize(300);
		ctrlNamek.setLocation(x, y);
		ctrlNamek.setLangMessageID("C00693");
		ctrlNamek.setMaxLength(40);
		pnlBody.add(ctrlNamek);

		// パスワード
		y += ctrlNamek.getHeight() + 10;
		ctrlPassword.setFieldSize(80);
		ctrlPassword.setLocation(x, y);
		ctrlPassword.setLangMessageID("C00597");
		ctrlPassword.setMaxLength(10);
		pnlBody.add(ctrlPassword);

		// プログラムロール
		y += ctrlPassword.getHeight() + 10;
		ctrlProgramRole.setLocation(x + 25, y);
		pnlBody.add(ctrlProgramRole);

		if (!USE_BL_SIGNER && USE_SIGNER_ATTACH) {
			aprvRole(x, y);
			flg = true;
		}

		// 参照ロール
		y += ctrlProgramRole.getHeight() + 10;
		ctrlUserRole.setLocation(x + 25, y);
		pnlBody.add(ctrlUserRole);

		if (!flg) {
			aprvRole(x, y);
		}

		if (USE_BL_SIGNER) {
			// INV.SIGNER DEPT
			y += ctrlUserRole.getHeight() + 10;
			ctrlSignerDept.setFieldSize(300);
			ctrlSignerDept.setLocation(x, y);
			ctrlSignerDept.setLangMessageID("CM0074");
			ctrlSignerDept.setMaxLength(40);
			pnlBody.add(ctrlSignerDept);

			// INV.SIGNER TITLE
			y += ctrlSignerDept.getHeight() + 10;
			ctrlSignerTitle.setFieldSize(300);
			ctrlSignerTitle.setLocation(x, y);
			ctrlSignerTitle.setLangMessageID("CM0075");
			ctrlSignerTitle.setMaxLength(40);
			pnlBody.add(ctrlSignerTitle);

			// INV.SIGNER NAME
			y += ctrlSignerTitle.getHeight() + 10;
			ctrlSignerName.setFieldSize(300);
			ctrlSignerName.setLocation(x, y);
			ctrlSignerName.setLangMessageID("CM0076");
			ctrlSignerName.setMaxLength(40);
			pnlBody.add(ctrlSignerName);

		}
		if (USE_SIGNER_ATTACH) {

			if (USE_BL_SIGNER) {
				x = ctrlSignerName.getX() + ctrlSignerName.getWidth() + 10;
			} else {
				x = ctrlUserRole.getX() + ctrlUserRole.getWidth() + 10;
			}

			// INV.SIGN(FileName)
			ctrlSignAttach.setLabelSize(40);
			ctrlSignAttach.setFieldSize(150);
			ctrlSignAttach.setLocation(x, y);
			ctrlSignAttach.setLangMessageID("SIGN"); // TODO
			pnlBody.add(ctrlSignAttach);

			// INV.SIGN(Btn)
			x = ctrlSignAttach.getX() + ctrlSignAttach.getWidth();
			btnSignAttach.setLocation(x, y);
			btnSignAttach.setSize(20, 20);
			pnlBody.add(btnSignAttach);
		}

		// E-MAIL
		if (USE_BL_SIGNER) {
			x = ctrlSignerName.getX();
			y += ctrlSignerName.getHeight() + 10;
		} else {
			x = ctrlPassword.getX();
			y += ctrlUserRole.getHeight() + 10;
		}
		ctrlEMailAddress.setFieldSize(300);
		ctrlEMailAddress.setLocation(x, y);
		ctrlEMailAddress.setLangMessageID("COP065");
		ctrlEMailAddress.setMaxLength(200);
		pnlBody.add(ctrlEMailAddress);

		// 更新権限レベル
		y += ctrlEMailAddress.getHeight() + 10;
		sysKbnY = y;
		cboUpdateAuthorityLevel.setComboSize(110);
		cboUpdateAuthorityLevel.setLocation(0, y);
		cboUpdateAuthorityLevel.setLangMessageID("C00170");
		initUpdLevelComboBox(cboUpdateAuthorityLevel.getComboBox());
		pnlBody.add(cboUpdateAuthorityLevel);

		// 経理担当者以外
		y += cboUpdateAuthorityLevel.getHeight() + 10;
		cboAccountingPerson.setComboSize(120);
		cboAccountingPerson.setLocation(0, y);
		cboAccountingPerson.setLangMessageID("C01056");
		initSettlementSlipComboBox(cboAccountingPerson.getComboBox());
		pnlBody.add(cboAccountingPerson);

		// 社員
		y += cboAccountingPerson.getHeight() + 10;
		ctrlEmployeeReference.setLocation(x + 25, y);
		pnlBody.add(ctrlEmployeeReference);

		// 部門
		y += ctrlEmployeeReference.getHeight() + 10;
		ctrlDepartmentReference.setLocation(x + 25, y);
		pnlBody.add(ctrlDepartmentReference);

		// 言語
		y += ctrlDepartmentReference.getHeight() + 10;
		ctrlLangcode.setLabelSize(80);
		ctrlLangcode.setComboSize(85);
		ctrlLangcode.setLocation(x + 20, y);
		pnlBody.add(ctrlLangcode);

		// 開始年月日
		y += ctrlLangcode.getHeight() + 10;
		dtBeginDate.setLangMessageID("C00055");
		dtBeginDate.setLabelSize(85);
		dtBeginDate.setLocation(x + 15, y);
		pnlBody.add(dtBeginDate);

		// 終了年月日
		y += dtBeginDate.getHeight() + 10;
		dtEndDate.setLangMessageID("C00261");
		dtEndDate.setLabelSize(85);
		dtEndDate.setLocation(x + 15, y);
		pnlBody.add(dtEndDate);

		// アクセス権限
		if (USE_ACCESS_FLAG) {
			initAccessFlagComboBoxs();
		}

		// システム区分一覧制限
		if (DASH_BOARD_CONDITION) {
			y = sysKbnY;
			int z = 32;

			if (num >= 5 && !USE_BL_SIGNER) {
				for (int i = 5; i <= num; i++) {
					y = y + z;
				}
			} else if (num >= 8 && USE_BL_SIGNER) {
				for (int i = 8; i <= num; i++) {
					y = y + z;
				}
			}

			// ダッシュボード権限
			lblSys.setSize(150, 20);
			lblSys.setLocation(x + 430, y);
			lblSys.setText(getWord("C12163"));
			pnlBody.add(lblSys);

			tblSysState.setSize(160, 140);
			tblSysState.setLocation(x + 430, y + 20);
			pnlBody.add(tblSysState);

		}

	}

	/**
	 * 更新権限レベルコンボボックスの初期化
	 * 
	 * @param comboBox 初期化対象のコンボボックス
	 */
	protected void initUpdLevelComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem(SlipRole.ALL, getWord(SlipRole.getSlipRoleName(SlipRole.ALL)));
		comboBox.addTextValueItem(SlipRole.SELF, getWord(SlipRole.getSlipRoleName(SlipRole.SELF)));
		comboBox.addTextValueItem(SlipRole.DEPARTMENT,
			TModelUIUtil.getShortWord(SlipRole.getSlipRoleName(SlipRole.DEPARTMENT)));
	}

	/**
	 * 決算伝票入力区分コンボボックスの初期化
	 * 
	 * @param comboBox
	 */
	protected void initSettlementSlipComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem(UserAccountRole.OTHER,
			getWord(UserAccountRole.getUserAccountRoleName(UserAccountRole.OTHER)));
		comboBox.addTextValueItem(UserAccountRole.ACCOUNT,
			getWord(UserAccountRole.getUserAccountRoleName(UserAccountRole.ACCOUNT)));
	}

	/**
	 * アクセス権限コンボボックスの初期化
	 */
	protected void initAccessFlagComboBoxs() {

		int x = 425;
		int y = 130;
		// アクセス権限
		for (int i = 0; i < User.ACCESS_FLAG_COUNT; i++) {

			boolean isVisible = ClientConfig.isFlagOn("trans.user.mst.access.flag.visible." + (i + 1));
			if (!isVisible) {
				continue;
			}
			String labelWord = ClientConfig.getProperty("trans.user.mst.access.flag.name." + (i + 1));

			TLabelComboBox cbo = ctrlAccessPermission[i];
			cbo.setComboSize(110);
			cbo.setLangMessageID(labelWord);
			initAccessFlagComboBox(i, cbo.getComboBox());

			cbo.setLocation(x, y);
			pnlBody.add(cbo);

			y += cbo.getHeight() + 10;

		}
	}

	/**
	 * アクセス権限コンボボックスの数量
	 * 
	 * @param num
	 * @return num
	 */
	protected int getBoxNum(int num) {

		// アクセス権限
		for (int i = 0; i < User.ACCESS_FLAG_COUNT; i++) {

			boolean isVisible = ClientConfig.isFlagOn("trans.user.mst.access.flag.visible." + (i + 1));
			if (!isVisible) {
				continue;
			}

			num++;
		}
		return num;
	}

	/**
	 * アクセス権限コンボボックスの初期化
	 * 
	 * @param index
	 * @param cbo
	 */
	protected void initAccessFlagComboBox(int index, TComboBox cbo) {

		// TODO
		// index=0, TRANS-OP権限用
		// 現状は全て同じ処理ロジック
		if (index == 0) {
			//
		}

		cbo.addTextValueItem(UserAccessFlag.NONE, getWord(UserAccessFlag.NONE.getName()));
		cbo.addTextValueItem(UserAccessFlag.VIEWER, getWord(UserAccessFlag.VIEWER.getName()));
		cbo.addTextValueItem(UserAccessFlag.EDITOR, getWord(UserAccessFlag.EDITOR.getName()));
		cbo.addTextValueItem(UserAccessFlag.ALL, getWord(UserAccessFlag.ALL.getName()));
	}

	/**
	 * 承認権限ロール
	 * 
	 * @param x
	 * @param y
	 */
	protected void aprvRole(int x, int y) {
		// 承認権限ロール
		ctrlAprvGroup.setLocation(x + 350, y);
		// ctrlAprvGroup.setLangMessageID("C11943"); // 次の承認者
		pnlBody.add(ctrlAprvGroup);
	}

	/**
	 * Tab Index Setting
	 */
	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlCode.setTabControlNo(i++);
		ctrlName.setTabControlNo(i++);
		ctrlNames.setTabControlNo(i++);
		ctrlNamek.setTabControlNo(i++);
		ctrlPassword.setTabControlNo(i++);
		ctrlProgramRole.setTabControlNo(i++);
		ctrlUserRole.setTabControlNo(i++);
		// 承認権限ロール
		ctrlAprvGroup.setTabControlNo(i++);
		if (USE_BL_SIGNER) {
			ctrlSignerDept.setTabControlNo(i++);
			ctrlSignerTitle.setTabControlNo(i++);
			ctrlSignerName.setTabControlNo(i++);
		}
		if (USE_SIGNER_ATTACH) {
			btnSignAttach.setTabControlNo(i++);
		}

		ctrlEMailAddress.setTabControlNo(i++);
		cboUpdateAuthorityLevel.setTabControlNo(i++);
		cboAccountingPerson.setTabControlNo(i++);
		ctrlEmployeeReference.setTabControlNo(i++);
		ctrlDepartmentReference.setTabControlNo(i++);
		ctrlLangcode.setTabControlNo(i++);
		dtBeginDate.setTabControlNo(i++);
		dtEndDate.setTabControlNo(i++);

		if (USE_ACCESS_FLAG) {
			for (TLabelComboBox cbo : ctrlAccessPermission) {
				cbo.setTabControlNo(i++);
			}
		}

		if (DASH_BOARD_CONDITION) {
			tblSysState.setTabControlNo(i++);
		}

		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}
}