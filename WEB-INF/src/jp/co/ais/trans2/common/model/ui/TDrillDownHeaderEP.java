package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.model.format.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �h�����_�E���w�b�_�[EP
 * 
 * @author AIS
 */
public class TDrillDownHeaderEP extends TDrillDownHeader {

	/** �Ј��R�[�h */
	public TLabelField txEmpCode;

	/** �Ј����� */
	public TLabelField txEmpNames;

	/** �o����v */
	public TLabelField txSettlementSum;

	/** �x���� */
	public TLabelField txPayDay;

	/** ���� �ʉ݃R�[�h */
	public TLabelField txTmpCurCode;

	/** ���� ���[�g */
	public TLabelField txTmpRate;

	/** ���� ���͋��z */
	public TLabelField txTmpInAmount;

	/** ���� �M�݋��z */
	public TLabelField txTmpKeyAmount;

	/** �x�����@�R�[�h */
	public TLabelField txPayMethodCode;

	/** �x�����@���� */
	public TLabelField txPayMethodNames;

	/** ���Z �ʉ݃R�[�h */
	public TLabelField txSettlementCurCode;

	/** ���Z ���[�g */
	public TLabelField txSettlementRate;

	/** ���Z ���͋��z */
	public TLabelField txSettlementInAmount;

	/** ���Z �M�݋��z */
	public TLabelField txSettlementKeyAmount;

	/** ��s�� */
	public TLabelField txBnkName;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param lang ����R�[�h
	 */
	public TDrillDownHeaderEP(String lang) {
		super(lang);
	}

	/**
	 * �R���|�[�l���g�����ݒ�
	 */
	@Override
	public void initComponents() {
		super.initComponents();

		txEmpCode = new TLabelField();
		txEmpNames = new TLabelField();
		txSettlementSum = new TLabelField();
		txPayDay = new TLabelField();
		txTmpCurCode = new TLabelField();
		txTmpRate = new TLabelField();
		txTmpInAmount = new TLabelField();
		txTmpKeyAmount = new TLabelField();
		txPayMethodCode = new TLabelField();
		txPayMethodNames = new TLabelField();
		txSettlementCurCode = new TLabelField();
		txSettlementRate = new TLabelField();
		txSettlementInAmount = new TLabelField();
		txSettlementKeyAmount = new TLabelField();
		txBnkName = new TLabelField();

	}

	/**
	 * �R���|�[�l���g�z�u
	 */
	@Override
	public void allocateComponents() {
		super.allocateComponents();
		int x = 10;
		int y = txRemarkCode.getY() + txRemarkCode.getHeight() + 10;

		setMaximumSize(new Dimension(0, 190));
		setMinimumSize(new Dimension(0, 190));
		setPreferredSize(new Dimension(0, 190));

		// �Ј��R�[�h
		txEmpCode.setEditable(false);
		txEmpCode.setLabelSize(65);
		txEmpCode.setFieldSize(80);
		txEmpCode.setLangMessageID("C00246");
		txEmpCode.setLocation(x, y);
		add(txEmpCode);

		// �Ј�����
		txEmpNames.setEditable(false);
		txEmpNames.setLabelSize(0);
		txEmpNames.setFieldSize(135);
		txEmpNames.setLocation(x + txEmpCode.getWidth() - 5, y);
		add(txEmpNames);

		// �o����v
		txSettlementSum.setEditable(false);
		txSettlementSum.setLabelSize(320);
		txSettlementSum.setFieldSize(80);
		txSettlementSum.setLangMessageID("C04282");
		txSettlementSum.setLocation(x + txEmpCode.getWidth() + txEmpNames.getWidth(), y);
		add(txSettlementSum);

		// �x����
		txPayDay.setEditable(false);
		txPayDay.setLabelSize(65);
		txPayDay.setFieldSize(80);
		txPayDay.setLangMessageID("C01098");
		txPayDay.setLocation(x, y + txEmpCode.getHeight() + 1);
		add(txPayDay);

		// ���� �ʉ݃R�[�h
		txTmpCurCode.setEditable(false);
		txTmpCurCode.setLabelSize(120);
		txTmpCurCode.setFieldSize(40);
		txTmpCurCode.setLangMessageID("C04239");
		txTmpCurCode.getField().setHorizontalAlignment(0);
		txTmpCurCode.setLocation(x + txEmpCode.getWidth() + txEmpNames.getWidth(), y + txEmpCode.getHeight() + 1);
		add(txTmpCurCode);

		// ���� ���[�g
		txTmpRate.setEditable(false);
		txTmpRate.setLabelSize(0);
		txTmpRate.setFieldSize(80);
		txTmpRate.setLocation(x + txEmpCode.getWidth() + txEmpNames.getWidth() + txTmpCurCode.getWidth() - 5, y
			+ txEmpCode.getHeight() + 1);
		add(txTmpRate);

		// ���� ���͋��z
		txTmpInAmount.setEditable(false);
		txTmpInAmount.setLabelSize(0);
		txTmpInAmount.setFieldSize(80);
		txTmpInAmount.setLocation(x + txEmpCode.getWidth() + txEmpNames.getWidth() + txTmpCurCode.getWidth()
			+ txTmpRate.getWidth() - 5 * 2, y + txEmpCode.getHeight() + 1);
		add(txTmpInAmount);

		// ���� �M�݋��z
		txTmpKeyAmount.setEditable(false);
		txTmpKeyAmount.setLabelSize(0);
		txTmpKeyAmount.setFieldSize(80);
		txTmpKeyAmount.setLocation(x + txEmpCode.getWidth() + txEmpNames.getWidth() + txTmpCurCode.getWidth()
			+ txTmpRate.getWidth() + txTmpInAmount.getWidth() - 5 * 3, y + txEmpCode.getHeight() + 1);
		add(txTmpKeyAmount);

		// �x�����@�R�[�h
		txPayMethodCode.setEditable(false);
		txPayMethodCode.setLabelSize(65);
		txPayMethodCode.setFieldSize(30);
		txPayMethodCode.setLangMessageID("C00233");
		txPayMethodCode.setLocation(x, y + txEmpCode.getHeight() * 2 + 1 * 2);
		add(txPayMethodCode);

		// �x�����@����
		txPayMethodNames.setEditable(false);
		txPayMethodNames.setLabelSize(0);
		txPayMethodNames.setFieldSize(185);
		txPayMethodNames.setLocation(x + txPayMethodCode.getWidth() - 5, y + txEmpCode.getHeight() * 2 + 1 * 2);
		add(txPayMethodNames);

		// ���Z �ʉ݃R�[�h
		txSettlementCurCode.setEditable(false);
		txSettlementCurCode.setLabelSize(120);
		txSettlementCurCode.setFieldSize(40);
		txSettlementCurCode.setLangMessageID("C01707");
		txSettlementCurCode.getField().setHorizontalAlignment(0);
		txSettlementCurCode.setLocation(x + txPayMethodCode.getWidth() + txPayMethodNames.getWidth(),
			y + txEmpCode.getHeight() * 2 + 1 * 2);
		add(txSettlementCurCode);

		// ���Z ���[�g
		txSettlementRate.setEditable(false);
		txSettlementRate.setLabelSize(0);
		txSettlementRate.setFieldSize(80);
		txSettlementRate.setLocation(
			x + txPayMethodCode.getWidth() + txPayMethodNames.getWidth() + txSettlementCurCode.getWidth() - 5, y
				+ txEmpCode.getHeight() * 2 + 1 * 2);
		add(txSettlementRate);

		// ���Z ���͋��z
		txSettlementInAmount.setEditable(false);
		txSettlementInAmount.setLabelSize(0);
		txSettlementInAmount.setFieldSize(80);
		txSettlementInAmount.setLocation(x + txPayMethodCode.getWidth() + txPayMethodNames.getWidth()
			+ txSettlementCurCode.getWidth() + txSettlementRate.getWidth() - 5 * 2, y + txEmpCode.getHeight() * 2 + 1
			* 2);
		add(txSettlementInAmount);

		// ���Z �M�݋��z
		txSettlementKeyAmount.setEditable(false);
		txSettlementKeyAmount.setLabelSize(0);
		txSettlementKeyAmount.setFieldSize(80);
		txSettlementKeyAmount.setLocation(x + txPayMethodCode.getWidth() + txPayMethodNames.getWidth()
			+ txSettlementCurCode.getWidth() + txSettlementRate.getWidth() + txSettlementInAmount.getWidth() - 5 * 3, y
			+ txEmpCode.getHeight() * 2 + 1 * 2);
		add(txSettlementKeyAmount);

		// �U�o��s ��s��
		txBnkName.setEditable(false);
		txBnkName.setLabelSize(65);
		txBnkName.setFieldSize(625);
		txBnkName.setLangMessageID("C01634");
		txBnkName.setLocation(x, y + txEmpCode.getHeight() * 3 + 3);
		add(txBnkName);

		// ���ߐ��Z�b�g
		txEmpCode.setOpaque(false);
		txEmpNames.setOpaque(false);
		txSettlementSum.setOpaque(false);
		txPayDay.setOpaque(false);
		txTmpCurCode.setOpaque(false);
		txTmpRate.setOpaque(false);
		txTmpInAmount.setOpaque(false);
		txTmpKeyAmount.setOpaque(false);
		txPayMethodCode.setOpaque(false);
		txPayMethodNames.setOpaque(false);
		txSettlementCurCode.setOpaque(false);
		txSettlementRate.setOpaque(false);
		txSettlementInAmount.setOpaque(false);
		txSettlementKeyAmount.setOpaque(false);
		txBnkName.setOpaque(false);

	}

	/**
	 * �`�[�w�b�_�[�Z�b�g
	 * 
	 * @param slip
	 */
	@Override
	public void setSlipHeader(Slip slip) throws TException {
		super.setSlipHeader(slip);
		
		// �`�[�w�b�_���擾
		SWK_HDR header = slip.getHeader();

		// �Ј��R�[�h
		txEmpCode.setValue(Util.avoidNull(header.getSWK_EMP_CODE()));
		// �Ј�����
		txEmpNames.setValue(Util.avoidNull(header.getSWK_EMP_NAME_S()));
		// �o����v
		txSettlementSum.setValue(NumberFormatUtil.formatNumber(header.getSWK_KEIHI_KIN()));
		// �x����
		txPayDay.setValue(DateUtil.toYMDString(header.getSWK_SIHA_DATE()));
		// �x�����@�R�[�h
		txPayMethodCode.setValue(Util.avoidNull(header.getSWK_HOH_CODE()));
		// �x�����@����
		txPayMethodNames.setValue(Util.avoidNull(header.getSWK_HOH_NAME()));

		// �`�[�̒ʉ݂���ʉ݂�
		boolean isKeyCurrency = header.getSWK_CUR_CODE().equals(header.getKEY_CUR_CODE());

		// �����`�[��
		if (slip.getSlipType().equals("021")) {
			
			if (!isKeyCurrency) {
				// �O�݂̏ꍇ
				// �����`�[ �ʉ݃R�[�h
				txTmpCurCode.setValue(Util.avoidNull(header.getSWK_CUR_CODE()));
				// �����`�[ ���[�g
				txTmpRate.setValue(FormatUtil.rateFormat(header.getSWK_CUR_RATE()));
				// �����`�[ ���͋��z
				txTmpInAmount.setValue(FormatUtil.foreingAmountFormat(header.getKEY_CUR_CODE(), header.getSWK_CUR_CODE(), header.getSWK_IN_SIHA_KIN(), header
					.getSWK_CUR_DEC_KETA()));
			} 
			
			// �����`�[ �M�݋��z
			txTmpKeyAmount.setValue(NumberFormatUtil.formatNumber(header.getSWK_SIHA_KIN(), header.getKEY_CUR_DEC_KETA()));
		
		} 
		// �����`�[�łȂ���ΐ��Z�`�[
		else {
			// ����������ꍇ�̂ݕ\��
			if (!DecimalUtil.isNullOrZero(header.getSWK_KARI_KIN())) {
				
				if (!header.getSWK_KARI_CUR_CODE().equals(header.getKEY_CUR_CODE())) {
					// �O�݂̏ꍇ
					// ���� �ʉ݃R�[�h
					txTmpCurCode.setValue(Util.avoidNull(header.getSWK_KARI_CUR_CODE()));
					// ���� ���[�g
					txTmpRate.setValue(FormatUtil.rateFormat(header.getSWK_KARI_CUR_RATE()));
					// ���� ���͋��z
					txTmpInAmount.setValue(FormatUtil.foreingAmountFormat(header.getKEY_CUR_CODE(), header.getSWK_KARI_CUR_CODE(), header.getSWK_IN_KARI_KIN(), header
						.getSWK_CUR_DEC_KETA()));
				} 
				// ���� �M�݋��z
				txTmpKeyAmount.setValue(NumberFormatUtil.formatNumber(header.getSWK_KARI_KIN(), header
					.getKEY_CUR_DEC_KETA()));
			}

			// ���Z���z 0�̏ꍇ��\��
			if (!DecimalUtil.isNullOrZero(header.getSWK_SIHA_KIN())) {
				
				if (!isKeyCurrency) {
					// �O�݂̏ꍇ
					// ���Z �ʉ݃R�[�h
					txSettlementCurCode.setValue(Util.avoidNull(header.getSWK_CUR_CODE()));
					// ���Z ���[�g
					txSettlementRate.setValue(FormatUtil.rateFormat(header.getSWK_CUR_RATE()));
					// ���Z ����
					txSettlementInAmount.setValue(FormatUtil.foreingAmountFormat(header.getKEY_CUR_CODE(), header.getSWK_CUR_CODE(), header.getSWK_IN_SIHA_KIN(), header
						.getSWK_CUR_DEC_KETA()));
				} 
				// ���Z �M��
				txSettlementKeyAmount.setValue(NumberFormatUtil.formatNumber(header.getSWK_SIHA_KIN(), header.getKEY_CUR_DEC_KETA()));
			}
		}

		// �U�o��s ��s��
		txBnkName.setValue(Util.avoidNull(header.getSWK_BNK_NAME_S()) + " "
			+ Util.avoidNull(header.getSWK_BNK_STN_NAME_S()) + " " // �U�o��s �x�X��
			+ Util.avoidNull(TModelUIUtil.getWord(DepositKind.getDepositKindName(header.getSWK_CBK_YKN_KBN()))) + " " // �U�o��s �a�����
			+ Util.avoidNull(header.getSWK_CBK_CODE())); // �U�o��s �U�o��s����

	}
}
