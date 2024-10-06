package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.attach.verify.*;
import jp.co.ais.trans2.model.item.*;

/**
 * �Y�t�t�@�C�����،��ʃG�N�Z��
 */
public class AttachmentVerifyExcel extends TExcel {

	/** �ҏW���V�[�g */
	TExcelSheet sheet;

	/**
	 * @param lang
	 */
	public AttachmentVerifyExcel(String lang) {
		super(lang);
	}

	/**
	 * Excel�擾
	 * 
	 * @param list
	 * @return excel
	 * @throws TException
	 */
	public byte[] getReport(List<AttachmentVerifyResult> list) throws TException {
		try {
			// �V�[�g������
			initSheet();
			// �^�C�g���s�L�q
			drawTitleRow();
			// ���׍s�L�q
			drawDetailRow(list);

			return super.getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}

	}

	/**
	 * 
	 */
	protected void drawTitleRow() {
		// �J�����ݒ�
		sheet.addColumn(getWord("��ЃR�[�h"), 3200);
		sheet.addColumn(getWord("�f�[�^���"), 6400);
		sheet.addColumn(getWord("�L�[���"), 6400);
		sheet.addColumn(getWord("�ǉ����"), 6400);
		sheet.addColumn(getWord("�t�@�C����"), 6400);
		sheet.addColumn(getWord("�T�[�o�[�t�@�C����"), 6400);
		sheet.addColumn(getWord("���b�Z�[�W"), 4200);
	}

	/**
	 * @param list
	 */
	protected void drawDetailRow(List<AttachmentVerifyResult> list) {
		// ���ו`��
		for (AttachmentVerifyResult bean : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(bean.getKAI_CODE());
			newRow.addCell(bean.getTYPE().toString());
			newRow.addCell(bean.getKEY1());
			newRow.addCell(bean.getKEY2());
			newRow.addCell(bean.getKEY3());
			newRow.addCell(bean.getKEY4());
			newRow.addCell(bean.getFILE_NAME());
			newRow.addCell(bean.getSRV_FILE_NAME());
			newRow.addCell(bean.getMESSAGE());
		}

	}

	/**
	 * 
	 */
	protected void initSheet() {
		// �V�[�g�ǉ�
		sheet = addSheet(getWord("C00911"));
	}
}
