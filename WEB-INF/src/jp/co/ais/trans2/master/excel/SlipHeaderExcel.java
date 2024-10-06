package jp.co.ais.trans2.master.excel;

import java.awt.Color;
import java.util.List;

import javax.swing.*;

import org.apache.poi.ss.usermodel.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.common.util.DateUtil;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.common.model.format.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �`�[�w�b�_�[�G�N�Z��
 */
public class SlipHeaderExcel extends TExcel {

	/** true:�t�@�C���Y�t�@�\�L�� */
	protected static boolean isUseAttachment = ServerConfig.isFlagOn("trans.slip.use.attachment");

	/** true:�tⳋ@�\�L�� */
	protected static boolean isUseTag = ServerConfig.isFlagOn("trans.slip.use.tag");

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public SlipHeaderExcel(String lang) {
		super(lang);
	}

	/**
	 * �`�[�ꗗ���G�N�Z���ɕԂ�
	 * 
	 * @param list
	 * @return list
	 * @throws TException
	 */
	public byte[] getExcel(List<SWK_HDR> list) throws TException {
		try {
			createReport(list);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �G�N�Z�����o�͂���
	 * 
	 * @param list
	 */
	public void createReport(List<SWK_HDR> list) {

		// �V�[�g�ǉ�
		TExcelSheet sheet = addSheet(getWord("C03662")); // �`�[�Ɖ�

		// �J�����ݒ�
		sheet.addColumn(getWord("C00605"), 4800); // �`�[�ԍ�
		sheet.addColumn(getWord("C01618"), 2700); // �C����
		sheet.addColumn(getWord("C00599"), 4200); // �`�[���t
		sheet.addColumn(getWord("C01178"), 4500); // �؜ߔԍ�
		sheet.addColumn(getWord("C01280"), 6500); // ����
		sheet.addColumn(getWord("C01278"), 6500); // ���͎�
		sheet.addColumn(getWord("C00569"), 7200); // �E�v
		sheet.addColumn(getWord("C00917"), 5500); // �`�[���
		sheet.addColumn(getWord("C01069"), 4800); // �X�V�敪
		sheet.addColumn(getWord("C00610"), 4800); // ���Z�i�K
		sheet.addColumn(getWord("C00408"), 6500); // �����
		sheet.addColumn(getWord("C00246"), 6500); // �Ј�
		sheet.addColumn(getWord("C11081"), 4800); // �ʉ݃R�[�h
		sheet.addColumn(getWord("C01620"), 4800); // �x�����z
		sheet.addColumn(getWord("C00600"), 4800); // �x����
		sheet.addColumn(getWord("C00857"), 7200); // ��s����
		sheet.addColumn(getWord("C00233"), 4800); // �x�����@

		if (isUseAttachment) {
			sheet.addColumn(getWord("C11613"), 4200); // �Y�t����
		}
		if (isUseTag) {
			sheet.addColumn(getWord("C12055"), 7200); // �t�1 
			sheet.addColumn(getWord("C12056"), 7200); // �t�2 
		}

		// ���ו`��
		for (SWK_HDR hdr : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(hdr.getSWK_DEN_NO());
			newRow.addCell(hdr.getSWK_UPD_CNT());
			newRow.addCell(DateUtil.toYMDString(hdr.getSWK_DEN_DATE()), SwingConstants.CENTER);
			newRow.addCell(hdr.getSWK_SEI_NO());
			newRow.addCell(hdr.getSWK_IRAI_DEP_NAME_S());
			newRow.addCell(hdr.getSWK_IRAI_EMP_NAME_S());
			newRow.addCell(hdr.getSWK_TEK());
			newRow.addCell(hdr.getSWK_DEN_SYU_NAME_S());
			newRow.addCell(getWord(SlipState.getSlipStateName(hdr.getSWK_UPD_KBN())), SwingConstants.CENTER);
			newRow.addCell(FormatUtil.settlementLevelFormat(hdr.getSWK_KSN_KBN(), lang), SwingConstants.CENTER);

			newRow.addCell(hdr.getSWK_TRI_NAME_S());
			newRow.addCell(hdr.getSWK_EMP_NAME_S());
			newRow.addCell(hdr.getSWK_CUR_CODE(), SwingConstants.CENTER);

			if (SlipKind.PURCHASE == hdr.getSlipKind()) {
				// �x�����z
				newRow.addCell(NumberFormatUtil.formatNumber(hdr.getSWK_IN_SIHA_KIN(), hdr.getSWK_CUR_DEC_KETA()));
				// �x���\���
				newRow.addCell(DateUtil.toYMDString(hdr.getSWK_SIHA_DATE()), SwingConstants.CENTER);
				// �U�o��s����
				newRow.addCell(hdr.getSWK_CBK_NAME());

			} else if (SlipKind.SALES == hdr.getSlipKind()) {
				// �������z
				newRow.addCell(NumberFormatUtil.formatNumber(hdr.getSWK_IN_KIN(), hdr.getSWK_CUR_DEC_KETA()));
				// �����\���
				newRow.addCell(DateUtil.toYMDString(hdr.getSWK_AR_DATE()), SwingConstants.CENTER);
				// ������s����
				newRow.addCell(hdr.getSWK_CBK_NAME());

			} else if (SlipKind.INPUT_CASH_FLOW == hdr.getSlipKind()) {// �����`�[
				// �������z
				newRow.addCell(NumberFormatUtil.formatNumber(hdr.getSWK_IN_KIN(), hdr.getSWK_CUR_DEC_KETA()));
				// �����\���
				newRow.addCell(DateUtil.toYMDString(hdr.getSWK_AR_DATE()), SwingConstants.CENTER);
				// ������s����
				newRow.addCell(hdr.getSWK_CBK_NAME());

			} else if (SlipKind.OUTPUT_CASH_FLOW == hdr.getSlipKind()) {// �o���`�[
				// �x�����z
				newRow.addCell(NumberFormatUtil.formatNumber(hdr.getSWK_IN_KIN(), hdr.getSWK_CUR_DEC_KETA()));
				// �x���\���
				newRow.addCell(DateUtil.toYMDString(hdr.getSWK_SIHA_DATE()), SwingConstants.CENTER);
				// �U�o��s����
				newRow.addCell(hdr.getSWK_CBK_NAME());

			} else if (SlipKind.EMPLOYEE == hdr.getSlipKind()) {// �Ј��x���`�[
				// �x�����z
				newRow.addCell(NumberFormatUtil.formatNumber(hdr.getSWK_IN_SIHA_KIN(), hdr.getSWK_CUR_DEC_KETA()));
				// �x���\���
				newRow.addCell(DateUtil.toYMDString(hdr.getSWK_SIHA_DATE()), SwingConstants.CENTER);
				// �U�o��s����
				newRow.addCell(hdr.getSWK_CBK_NAME());

			} else {
				// �x��/�������z
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
			}

			// �x�����@
			newRow.addCell(hdr.getSWK_HOH_NAME());

			if (isUseAttachment) {
				// �Y�t�t�@�C��
				// C00006">����
				// C00412">�Ȃ�
				newRow.addCell(getWord(hdr.isExistAttachment() ? "C00006" : "C00412"), SwingConstants.CENTER);
			}
			if (isUseTag) {
				// �t�
				SWK_TAG tag1 = null;
				SWK_TAG tag2 = null;
				for (SWK_TAG bean : hdr.getSwkTags()) {
					if (bean.getSEQ() == 1) {
						tag1 = bean;
					} else if (bean.getSEQ() == 2) {
						tag2 = bean;
					}
				}
				if (tag1 != null) {
					CellStyle tag1Style = getCellStyle(tag1.getTAG_COLOR());
					newRow.addCell(tag1.getTAG_TITLE(), tag1Style);
				} else {
					newRow.addCell("");
				}
				if (tag2 != null) {
					CellStyle tag2Style = getCellStyle(tag2.getTAG_COLOR());
					newRow.addCell(tag2.getTAG_TITLE(), tag2Style);
				} else {
					newRow.addCell("");
				}
			}
		}

	}

	/**
	 * @param fillColor
	 * @return �X�^�C��
	 */
	protected CellStyle getCellStyle(Color fillColor) {

		CellStyle style = workBook.createCellStyle();
		style.cloneStyleFrom(getCellStyleManager().getLeftStyle());
		if (fillColor != null) {
			setFillColor(style, fillColor);
		}
		// �����I��style�ύX�iTExcelOutput.setFillColor�ŕύX����Ă���ׁj
		style.setWrapText(false);
		style.setAlignment(CellStyle.ALIGN_LEFT);

		return style;
	}

	/**
	 * �w�i�F�ݒ�
	 * 
	 * @param style
	 * @param color
	 */
	protected void setFillColor(CellStyle style, Color color) {
		TExcelOutput.setFillColor(workBook, style, color);
	}
}