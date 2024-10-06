package jp.co.ais.trans2.master.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.model.ui.TModelUIUtil;
import jp.co.ais.trans2.model.company.*;

/**
 * 科目マスタチェックボックスのコンポーネント
 */
public class TItemStatusUnit extends TPanel {

	/** 入金伝票入力フラグ */
	public TCheckBox chkglflg1;

	/** 出金伝票入力フラグ */
	public TCheckBox chkglflg2;

	/** 振替伝票入力フラグ */
	public TCheckBox chkglflg3;

	/** 経費精算伝票入力フラグ */
	public TCheckBox chkapflg1;

	/** 債務計上伝票入力フラグ */
	public TCheckBox chkapflg2;

	/** 債権計上伝票入力フラグ */
	public TCheckBox chkarflg1;

	/** 債権消込伝票入力フラグ */
	public TCheckBox chkarflg2;

	/** 資産計上伝票入力フラグ */
	public TCheckBox chkfaflg1;

	/** 支払依頼伝票入力フラグ */
	public TCheckBox chkfaflg2;

	/** 多通貨入力フラグ */
	public TCheckBox chkmcrflg;

	/** 社員入力フラグ */
	public TCheckBox chkempcodeflg;

	/** 管理１入力フラグ(船入力) */
	public TCheckBox chkknrflg1;

	/** 管理２入力フラグ(次号入力) */
	public TCheckBox chkknrflg2;

	/** 管理３入力フラグ(港入力) */
	public TCheckBox chkknrflg3;

	/** 管理４入力フラグ(積み地入力) */
	public TCheckBox chkknrflg4;

	/** 管理５入力フラグ(揚げ地入力) */
	public TCheckBox chkknrflg5;

	/** 管理６入力フラグ(整理地区分) */
	public TCheckBox chkknrflg6;

	/** 非会計１入力フラグ(数値入力) */
	public TCheckBox chkhmflg1;

	/** 非会計２入力フラグ(文字入力) */
	public TCheckBox chkhmflg2;

	/** 非会計３入力フラグ(日時入力) */
	public TCheckBox chkhmflg3;

	/** 売上課税入力フラグ */
	public TCheckBox chkurizeiflg;

	/** 仕入課税入力フラグ */
	public TCheckBox chksirzeiflg;

	/** 発生日フラグ */
	public TCheckBox chkOccurDate;

	/** 会社情報 */
	protected Company company = null;

	/**
	 * コンストラクタ
	 * 
	 * @param company
	 */
	public TItemStatusUnit(Company company) {
		this.company = company;
		initComponents();
		allocateComponents();
	}

	/**
	 * コンポーネントの初期化。主にインスタンスの生成を行います。
	 */
	public void initComponents() {

		chkglflg1 = new TCheckBox();
		chkglflg2 = new TCheckBox();
		chkglflg3 = new TCheckBox();
		chkapflg1 = new TCheckBox();
		chkapflg2 = new TCheckBox();
		chkarflg1 = new TCheckBox();
		chkarflg2 = new TCheckBox();
		chkfaflg1 = new TCheckBox();
		chkfaflg2 = new TCheckBox();
		chkmcrflg = new TCheckBox();
		chkempcodeflg = new TCheckBox();
		chkknrflg1 = new TCheckBox();
		chkknrflg2 = new TCheckBox();
		chkknrflg3 = new TCheckBox();
		chkknrflg4 = new TCheckBox();
		chkknrflg5 = new TCheckBox();
		chkknrflg6 = new TCheckBox();
		chkhmflg1 = new TCheckBox();
		chkhmflg2 = new TCheckBox();
		chkhmflg3 = new TCheckBox();
		chkurizeiflg = new TCheckBox();
		chksirzeiflg = new TCheckBox();
		chkOccurDate = new TCheckBox();
	}

	/**
	 * コンポーネントの配置を行います。
	 */
	public void allocateComponents() {

		setLayout(null);
		setSize(700, 520);

		// 入金伝票入力フラグ
		chkglflg1.setLangMessageID("C01272");
		chkglflg1.setSize(180, 20);
		chkglflg1.setLocation(0, 0);
		add(chkglflg1);

		// 出金伝票入力フラグ
		chkglflg2.setLangMessageID("C01155");
		chkglflg2.setSize(180, 20);
		chkglflg2.setLocation(0, 20);
		add(chkglflg2);

		// 振替伝票入力フラグ
		chkglflg3.setLangMessageID("C01188");
		chkglflg3.setSize(180, 20);
		chkglflg3.setLocation(0, 40);
		add(chkglflg3);

		// 経費精算伝票入力フラグ
		chkapflg1.setLangMessageID("C01049");
		chkapflg1.setSize(180, 20);
		chkapflg1.setLocation(0, 60);
		add(chkapflg1);

		// 債務計上伝票入力フラグ
		chkapflg2.setLangMessageID("C01083");
		chkapflg2.setSize(180, 20);
		chkapflg2.setLocation(0, 80);
		add(chkapflg2);

		// 債権計上伝票入力フラグ
		chkarflg1.setLangMessageID("C01079");
		chkarflg1.setSize(180, 20);
		chkarflg1.setLocation(0, 100);
		add(chkarflg1);

		// 債権消込伝票入力フラグ
		chkarflg2.setLangMessageID("C01081");
		chkarflg2.setSize(180, 20);
		chkarflg2.setLocation(0, 120);
		add(chkarflg2);

		// 資産計上伝票入力フラグ
		chkfaflg1.setLangMessageID("C01102");
		chkfaflg1.setSize(180, 20);
		chkfaflg1.setLocation(0, 140);
		add(chkfaflg1);

		// 支払依頼伝票入力フラグ
		chkfaflg2.setLangMessageID("C01094");
		chkfaflg2.setSize(180, 20);
		chkfaflg2.setLocation(0, 160);
		add(chkfaflg2);

		// 多通貨入力フラグ
		chkmcrflg.setLangMessageID("C01223");
		chkmcrflg.setSize(180, 20);
		chkmcrflg.setLocation(0, 180);
		add(chkmcrflg);

		// 社員入力フラグ
		chkempcodeflg.setLangMessageID("C01120");
		chkempcodeflg.setSize(180, 20);
		chkempcodeflg.setLocation(0, 200);
		add(chkempcodeflg);

		// 管理１入力フラグ

		if (company.getAccountConfig().getManagement1Name() != null) {
			chkknrflg1.setLangMessageID(company.getAccountConfig().getManagement1Name()
				+ TModelUIUtil.getWord("C02386"));
		} else {
			chkknrflg1.setLangMessageID("C01026");
		}
		chkknrflg1.setSize(180, 20);
		chkknrflg1.setLocation(0, 220);
		add(chkknrflg1);

		// 管理２入力フラグ
		if (company.getAccountConfig().getManagement2Name() != null) {
			chkknrflg2.setLangMessageID(company.getAccountConfig().getManagement2Name()
				+ TModelUIUtil.getWord("C02386"));
		} else {
			chkknrflg2.setLangMessageID("C01028");
		}
		chkknrflg2.setSize(180, 20);
		chkknrflg2.setLocation(0, 240);
		add(chkknrflg2);

		// 管理３入力フラグ
		if (company.getAccountConfig().getManagement3Name() != null) {
			chkknrflg3.setLangMessageID(company.getAccountConfig().getManagement3Name()
				+ TModelUIUtil.getWord("C02386"));
		} else {
			chkknrflg3.setLangMessageID("C01030");
		}
		chkknrflg3.setSize(180, 20);
		chkknrflg3.setLocation(0, 260);
		add(chkknrflg3);

		// 管理４入力フラグ
		if (company.getAccountConfig().getManagement4Name() != null) {
			chkknrflg4.setLangMessageID(company.getAccountConfig().getManagement4Name()
				+ TModelUIUtil.getWord("C02386"));
		} else {
			chkknrflg4.setLangMessageID("C01032");
		}
		chkknrflg4.setSize(180, 20);
		chkknrflg4.setLocation(0, 280);
		add(chkknrflg4);

		// 管理５入力フラグ
		if (company.getAccountConfig().getManagement5Name() != null) {
			chkknrflg5.setLangMessageID(company.getAccountConfig().getManagement5Name()
				+ TModelUIUtil.getWord("C02386"));
		} else {
			chkknrflg5.setLangMessageID("C01034");
		}
		chkknrflg5.setSize(180, 20);
		chkknrflg5.setLocation(0, 300);
		add(chkknrflg5);

		// 管理６入力フラグ
		if (company.getAccountConfig().getManagement6Name() != null) {
			chkknrflg6.setLangMessageID(company.getAccountConfig().getManagement6Name()
				+ TModelUIUtil.getWord("C02386"));
		} else {
			chkknrflg6.setLangMessageID("C01036");
		}
		chkknrflg6.setSize(180, 20);
		chkknrflg6.setLocation(0, 320);
		add(chkknrflg6);

		// 非会計１入力フラグ
		if (company.getAccountConfig().getNonAccounting1Name() != null) {
			chkhmflg1.setLangMessageID(company.getAccountConfig().getNonAccounting1Name()
				+ TModelUIUtil.getWord("C02386"));
		} else {
			chkhmflg1.setLangMessageID("C01288");
		}
		chkhmflg1.setSize(180, 20);
		chkhmflg1.setLocation(0, 340);
		add(chkhmflg1);

		// 非会計２入力フラグ
		if (company.getAccountConfig().getNonAccounting2Name() != null) {
			chkhmflg2.setLangMessageID(company.getAccountConfig().getNonAccounting2Name()
				+ TModelUIUtil.getWord("C02386"));
		} else {
			chkhmflg2.setLangMessageID("C01289");
		}
		chkhmflg2.setSize(180, 20);
		chkhmflg2.setLocation(0, 360);
		add(chkhmflg2);

		// 非会計３入力フラグ
		if (company.getAccountConfig().getNonAccounting3Name() != null) {
			chkhmflg3.setLangMessageID(company.getAccountConfig().getNonAccounting3Name()
				+ TModelUIUtil.getWord("C02386"));
		} else {
			chkhmflg3.setLangMessageID("C01290");
		}
		chkhmflg3.setSize(180, 20);
		chkhmflg3.setLocation(0, 380);
		add(chkhmflg3);

		// 売上課税入力フラグ
		chkurizeiflg.setLangMessageID("C01282");
		chkurizeiflg.setSize(180, 20);
		chkurizeiflg.setLocation(0, 400);
		add(chkurizeiflg);

		// 仕入課税入力フラグ
		chksirzeiflg.setLangMessageID("C01088");
		chksirzeiflg.setSize(180, 20);
		chksirzeiflg.setLocation(0, 420);
		add(chksirzeiflg);

		if (ClientConfig.isFlagOn("trans.MG0080.use.occurdate")) {
			// 発生日フラグ
			chkOccurDate.setLangMessageID("C11619");
			chkOccurDate.setSize(180, 20);
			chkOccurDate.setLocation(0, 440);
			add(chkOccurDate);
		} else {
			chkOccurDate.setVisible(false);
		}
	}

	/**
	 * コンポーネントのタブ順の設定を行います。
	 * 
	 * @param tabControlNo
	 */
	public void setTabControlNo(int tabControlNo) {
		chkglflg1.setTabControlNo(tabControlNo);
		chkglflg2.setTabControlNo(tabControlNo);
		chkglflg3.setTabControlNo(tabControlNo);
		chkapflg1.setTabControlNo(tabControlNo);
		chkapflg2.setTabControlNo(tabControlNo);
		chkarflg1.setTabControlNo(tabControlNo);
		chkarflg2.setTabControlNo(tabControlNo);
		chkfaflg1.setTabControlNo(tabControlNo);
		chkfaflg2.setTabControlNo(tabControlNo);
		chkmcrflg.setTabControlNo(tabControlNo);
		chkempcodeflg.setTabControlNo(tabControlNo);
		chkknrflg1.setTabControlNo(tabControlNo);
		chkknrflg2.setTabControlNo(tabControlNo);
		chkknrflg3.setTabControlNo(tabControlNo);
		chkknrflg4.setTabControlNo(tabControlNo);
		chkknrflg5.setTabControlNo(tabControlNo);
		chkknrflg6.setTabControlNo(tabControlNo);
		chkhmflg1.setTabControlNo(tabControlNo);
		chkhmflg2.setTabControlNo(tabControlNo);
		chkhmflg3.setTabControlNo(tabControlNo);
		chkurizeiflg.setTabControlNo(tabControlNo);
		chksirzeiflg.setTabControlNo(tabControlNo);
	}
}
