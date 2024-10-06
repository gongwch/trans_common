package jp.co.ais.trans2.master.ui;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * ��s�����}�X�^<br>
 * IBAN�����ǉ���
 * 
 * @author AIS
 */
public class MP0031BankAccountMasterPanel extends MP0030BankAccountMasterPanel {

	/** �ꗗ�̃J������` */
	public enum SC {
		/** ��s�����R�[�h */
		code,
		/** ��s�������� */
		name,
		/** �ʉ݃R�[�h */
		curCode,
		/** ��s�R�[�h */
		bankCode,
		/** ��s���� */
		bankName,
		/** ��s���́i�p���j */
		bankName_e,
		/** �x�X�R�[�h */
		stnCode,
		/** �x�X���� */
		stnName,
		/** �x�X���́i�p���j */
		stnName_e,
		/** �U���˗��l�R�[�h */
		iraiCode,
		/** �U���˗��l���i�Łj */
		iraiName,
		/** �U���˗��l���i�����j */
		iraiName_j,
		/** �U���˗��l���i�p���j */
		iraiName_e,
		/** �a����� */
		yokinKbn,
		/** �����ԍ� */
		kouzaNo,
		/** �Ј�FB�敪 */
		empFB,
		/** �ЊOFB�敪 */
		syagaiFB,
		/** �v�㕔��R�[�h */
		depCode,
		/** �v�㕔�嗪�� */
		depNames,
		/** �ȖڃR�[�h */
		itemCode,
		/** �Ȗڗ��� */
		itemNames,
		/** �⏕�ȖڃR�[�h */
		subItemCode,
		/** �⏕�Ȗڗ��� */
		subItemNames,
		/** ����ȖڃR�[�h */
		detailItemCode,
		/** ����Ȗڗ��� */
		detailItemNames,
		/** ��s���ʎq */
		bankIndentify,
		/** �������ʎq */
		bankAccountIndentify,
		/** IBAN�R�[�h */
		iBan,
		/** SWIFT�R�[�h */
		swift,
		/** BANK COUNTRY */
		bankCountry,
		/** �Z��1 */
		bnkAdr1,
		/** �Z��1�i�p���j */
		bnkAdr1_e,
		/** �Z��2 */
		bnkAdr2,
		/** �Z��2�i�p���j */
		bnkAdr2_e,
		/** �J�n�� */
		dateFrom,
		/** �I���� */
		dateTo

	}

	/**
	 * �R���X�g���N�^.
	 */
	public MP0031BankAccountMasterPanel() {
		super();
	}

	/**
	 * ��ʃR���|�[�l���g�̍\�z
	 */
	@Override
	public void initComponents() {

		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnListOutput = new TImageButton(IconType.EXCEL);
		ctrlRange = new TBankAccountReferenceRange(true);
		chkOutputTermEnd = new TCheckBox();

		pnlSearchCondition = new TPanel();
		tbl = new TTable();
		tbl.addColumn(SC.code, getWord("C01879"), 100);
		tbl.addColumn(SC.name, getWord("C02145"), 200);
		tbl.addColumn(SC.curCode, getWord("C00665"), 80, SwingConstants.CENTER);
		tbl.addColumn(SC.bankCode, getWord("C00779"), 100);
		tbl.addColumn(SC.bankName, getWord("C00781"), 200);
		tbl.addColumn(SC.bankName_e, getWord("C11891"), 200); // ��s����(�p��)
		tbl.addColumn(SC.stnCode, getWord("C02055"), 100);
		tbl.addColumn(SC.stnName, getWord("C02060"), 200);
		tbl.addColumn(SC.stnName_e, getWord("C11892"), 200); // �x�X����(�p��)
		tbl.addColumn(SC.iraiCode, getWord("C00858"), 110);
		tbl.addColumn(SC.iraiName, getWord("C00859"), 200);
		tbl.addColumn(SC.iraiName_j, getWord("C00860"), 200);
		tbl.addColumn(SC.iraiName_e, getWord("C00861"), 200);
		tbl.addColumn(SC.yokinKbn, getWord("C01326"), 100, SwingConstants.CENTER);
		tbl.addColumn(SC.kouzaNo, getWord("C00794"), 100);
		tbl.addColumn(SC.empFB, getWord("C01117"), 100, SwingConstants.CENTER);
		tbl.addColumn(SC.syagaiFB, getWord("C01122"), 100, SwingConstants.CENTER);
		tbl.addColumn(SC.depCode, getWord("C00571"), 100);
		tbl.addColumn(SC.depNames, getWord("C00724"), 200);
		tbl.addColumn(SC.itemCode, getWord("C00572"), 100);
		tbl.addColumn(SC.itemNames, getWord("C00730"), 200);
		tbl.addColumn(SC.subItemCode, getWord("C00602"), 100);
		tbl.addColumn(SC.subItemNames, getWord("C00739"), 200);
		tbl.addColumn(SC.detailItemCode, getWord("C00603"), 100);
		tbl.addColumn(SC.detailItemNames, getWord("C01594"), 200);
		tbl.addColumn(SC.bankIndentify, getWord("C11457"), 100); // ��s���ʎq
		tbl.addColumn(SC.bankAccountIndentify, getWord("C11458"), 200); // �������ʎq
		tbl.addColumn(SC.iBan, getWord("C11456"), 200); // IBAN�R�[�h
		tbl.addColumn(SC.swift, getWord("C10414"), 100); // SWIFT�R�[�h
		tbl.addColumn(SC.bankCountry, getWord("C11425"), 100, SwingConstants.CENTER); // BANK COUNTRY
		tbl.addColumn(SC.bnkAdr1, getWord("C00687"), 200); // �Z��1
		tbl.addColumn(SC.bnkAdr1_e, getWord("C11893"), 200); // �Z��1(�p��)
		tbl.addColumn(SC.bnkAdr2, getWord("C00688"), 200); // �Z��2
		tbl.addColumn(SC.bnkAdr2_e, getWord("C11894"), 200); // �Z��2(�p��)
		tbl.addColumn(SC.dateFrom, getWord("C00055"), 100, SwingConstants.CENTER);
		tbl.addColumn(SC.dateTo, getWord("C00261"), 100, SwingConstants.CENTER);
	}

}
