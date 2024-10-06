package jp.co.ais.trans2.model.slip;

import java.io.*;
import java.math.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.common.ledger.*;
import jp.co.ais.trans2.common.model.excel.*;
import jp.co.ais.trans2.common.model.report.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.aprvrole.*;
import jp.co.ais.trans2.model.balance.*;
import jp.co.ais.trans2.model.bs.*;
import jp.co.ais.trans2.model.calc.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.history.*;
import jp.co.ais.trans2.model.history.ApproveHistory.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.payment.*;
import jp.co.ais.trans2.model.slip.SWK_DTL.*;
import jp.co.ais.trans2.model.slip.panel.*;
import jp.co.ais.trans2.model.user.*;

/**
 * �`�[�}�l�[�W�� �`�[�֘A�̑���i�擾�E�o�^�E�폜�Ȃǁj�𐧌䂷��N���X����
 */
@SuppressWarnings("unused")
public class SlipManagerImpl extends TModel implements SlipManager {

	/** true:BS��������@�\�L�� */
	public static boolean isUseBS = ServerConfig.isFlagOn("trans.slip.use.bs");

	/** true:AP��������@�\���� */
	public static boolean isNotUseAP = ServerConfig.isFlagOn("trans.slip.disable.ap.zan");

	/** true:AR��������@�\���� */
	public static boolean isNotUseAR = ServerConfig.isFlagOn("trans.slip.disable.ar.zan");

	/** true:�t�@�C���Y�t�@�\�L�� */
	public static boolean isUseAttachment = ServerConfig.isFlagOn("trans.slip.use.attachment");

	/** true:���F�����@�\�L�� */
	public static boolean isUseApproveHistory = ServerConfig.isFlagOn("trans.slip.use.approve.history");

	/** true:�`�[�ԍ���擪�t����PDF�t�@�C���쐬�@�\�L�� */
	public static boolean isFileNameWithSlipNo = ServerConfig.isFlagOn("trans.slip.use.slipno.for.filename");

	/** �Ȗڔ��� */
	public Map<String, Item> itemMap = new HashMap<String, Item>();

	/** true:�tⳋ@�\�L�� */
	public static boolean isUseTag = ServerConfig.isFlagOn("trans.slip.use.tag");

	/** true:CM_FUND_DTL�o�^���s���� */
	public static boolean isUseCmFund = ServerConfig.isFlagOn("trans.use.cm.fund.entry");

	/** true:CM_FUND_DTL�o�^�Ń}�b�`���O�͌����ԍ����g�p���� */
	public static boolean isUseCmEntryAcctNo = ServerConfig.isFlagOn("trans.use.cm.entry.account.no");

	/** �v�Z���W�b�N */
	public TCalculator calculator = TCalculatorFactory.getCalculator();

	/** true:�t�֎d�󐶐����ɑ����Ђ̊���Ȗڂ�\������ */
	public static boolean isUseTransferItemName = ServerConfig.isFlagOn("trans.slip.use.transfer.item.name");

	/** ���F�O���[�v���X�g */
	protected List<AprvRoleGroup> grpList;

	/** ���F���[�����X�g */
	protected List<AprvRole> roleList;

	/** ���F���I�v�V���� : �\�Ȍ����܂ŏ��F */
	protected boolean isApproveAsMuchAsPossible = false;

	/**
	 * �`�[���\�z����.<br>
	 * �t�ցA����Ŏd��A�@�\�ʉݎd��
	 * 
	 * @param slip �`�[�N���X
	 * @return �\�z�����`�[(�t�֐�܂�)
	 * @throws TException
	 */
	public List<Slip> buildSlip(Slip slip) throws TException {
		SlipLogic logic = getSlipLogic(slip.getSlipType());

		// �o�^�`�[�\�z
		return logic.buildSlip(slip);
	}

	/**
	 * �`�[���N�[����
	 * 
	 * @param slip �`�[�N���X
	 * @throws TException
	 */
	public void entry(Slip slip) throws TException {

		if (slip != null) {
			restoreAPAR(slip.getSlipNo());
		}
		SWK_HDR h = slip.getHeader();
		if (getCompany().getAccountConfig().isUseWorkflowApprove() //
			&& Util.isNullOrEmpty(h.getSWK_APRV_GRP_CODE())) {
			// ���[�N�t���[���F�𗘗p ���� �`�[�ɃO���[�v���ݒ�
			String aprvGrpCode = getUser().getAprvRoleGroupCode();
			if (Util.isNullOrEmpty(aprvGrpCode)) {
				// ���[�U�[�ɂ����ݒ�
				throw new TException("���݃��O�C�����̃��[�U�[�ɏ��F�O���[�v��ݒ肵�Ă��������B");
			}
			h.setSWK_APRV_GRP_CODE(aprvGrpCode);
		}

		// AP��������@�\�L���̏ꍇ�AAP��������d��̏����敪�̐ݒ�
		if (!isNotUseAP && slip != null && slip.getDetails() != null) {
			boolean isExist = false;
			boolean isAP = false;

			for (SWK_DTL dtl : slip.getDetails()) {

				if (dtl.isAPErasing()) {
					// �������f�[�^�̏ꍇ
					AP_ZAN ap = dtl.getAP_ZAN();
					dtl.setSWK_APAR_KESI_KBN(SWK_DTL.APAR_KESI_KBN.AP_FORWARD);
					dtl.setSWK_APAR_DEN_NO(ap.getZAN_DEN_NO()); // �������̓`�[�ԍ�
					dtl.setSWK_APAR_GYO_NO(ap.getZAN_SWK_GYO_NO()); // �������̍s�ԍ�
					isExist = true;

				} else if (!Util.isNullOrEmpty(dtl.getSWK_APAR_DEN_NO())) {
					// �����`�[�쐬�ŏ������ݒ�ς�
					isExist = true;
				}

				if (dtl.getSWK_APAR_KESI_KBN() == SWK_DTL.APAR_KESI_KBN.AP_FORWARD) {
					isAP = true;
				}
			}

			if (isExist && isAP) {
				updateAPARInfo(slip, SWK_DTL.APAR_KESI_KBN.AP_BASE);
			}
		}

		// AR��������@�\�L���̏ꍇ�AAP��������d��̏����敪�̐ݒ�
		if (!isNotUseAR && slip != null && slip.getDetails() != null) {
			boolean isExist = false;
			boolean isAR = false;

			for (SWK_DTL dtl : slip.getDetails()) {

				if (dtl.isARErasing()) {
					// �������f�[�^�̏ꍇ
					AR_ZAN ar = dtl.getAR_ZAN();
					dtl.setSWK_APAR_KESI_KBN(SWK_DTL.APAR_KESI_KBN.AR_FORWARD);
					dtl.setSWK_APAR_DEN_NO(ar.getZAN_SEI_DEN_NO()); // �������̓`�[�ԍ�
					dtl.setSWK_APAR_GYO_NO(ar.getZAN_SEI_GYO_NO()); // �������̍s�ԍ�
					isExist = true;

				} else if (!Util.isNullOrEmpty(dtl.getSWK_APAR_DEN_NO())) {
					// �����`�[�쐬�ŏ������ݒ�ς�
					isExist = true;
				}

				if (dtl.getSWK_APAR_KESI_KBN() == SWK_DTL.APAR_KESI_KBN.AR_FORWARD) {
					isAR = true;
				}
			}
			if (isExist && isAR) {
				updateAPARInfo(slip, SWK_DTL.APAR_KESI_KBN.AR_BASE);
			}
		}

		// BS��������@�\�L���̏ꍇ�ABS��������d��̏����敪�̐ݒ�
		if (isUseBS && slip != null && slip.getDetails() != null) {
			for (SWK_DTL dtl : slip.getDetails()) {

				SWK_DTL bs = dtl.getBsDetail();

				if (bs == null) {
					continue;
				}

				// �����敪��2:������
				dtl.setSWK_KESI_KBN(SWK_DTL.KESI_KBN.FORWARD);
				dtl.setSWK_SOUSAI_DATE(bs.getSWK_DEN_DATE()); // �������̓`�[���t
				dtl.setSWK_SOUSAI_DEN_NO(bs.getSWK_DEN_NO()); // �������̓`�[�ԍ�
				dtl.setSWK_SOUSAI_GYO_NO(bs.getSWK_GYO_NO()); // �������̍s�ԍ�
			}
		}
		SlipLogic logic = getSlipLogic(slip.getSlipType());
		if (isSlipAP(slip.getHeader().getSWK_DATA_KBN())) {
			logic = getSlipLogic(slip.getSlipType(), slip.getHeader().getSWK_DATA_KBN());
		}

		logic.entry(slip);

		// BS��������@�\�L���̏ꍇ
		if (isUseBS) {
			// �o�^���BS��������d��̍X�V�������s��
			BSEraseManager bsManager = (BSEraseManager) getComponent(BSEraseManager.class);
			bsManager.updateBsBalance(slip);
		}

		// �t�@�C���Y�t�@�\�L���̏ꍇ
		if (isUseAttachment) {
			SlipAttachmentManager attachment = (SlipAttachmentManager) getComponent(SlipAttachmentManager.class);
			attachment.entry(slip);
		}

		if (isUseApproveHistory) {
			// ����o�^
			try {
				ApproveHistoryManager appr = (ApproveHistoryManager) getComponent(ApproveHistoryManager.class);
				int syoFlag = (slip.getHeader().getSWK_UPD_CNT() != 0) ? SYO_FLG.MODIFY : SYO_FLG.ENTRY;
				appr.entry(appr.getApproveHistory(slip, getUser().getEmployee(), syoFlag));
			} catch (TException e) {
				throw new TRuntimeException(e);
			}
		}
		// �tⳋ@�\�L���̏ꍇ
		if (isUseTag) {
			SlipTagManager tag = (SlipTagManager) getComponent(SlipTagManager.class);
			tag.entry(slip);
		}

		// CM_FUND_DTL�ɓo�^���s����
		if (isUseCmFund) {
			entryCmFundInfo(slip);
		}

	}

	/**
	 * ���v��`�[���ǂ������`�F�b�N
	 * 
	 * @param dataType
	 * @return boolean
	 */
	protected boolean isSlipAP(String dataType) {
		if (dataType.equals("23")) {
			return true;
		}
		return false;
	}

	/**
	 * �`�[���̑D�����Ð������̃`�F�b�N���s��
	 * 
	 * @param slip
	 * @return �G���[���b�Z�[�W�ꗗ
	 * @throws TException
	 */
	protected List<Message> checkSlipFleetConsistency(Slip slip) throws TException {
		SlipFleetMovementChecker checker = (SlipFleetMovementChecker) getComponent(SlipFleetMovementChecker.class);
		return checker.checkFleetMovement(slip);
	}

	/**
	 * �`�[���\�z���ēo�^����.<br>
	 * �t�ցA����Ŏd��A�@�\�ʉݎd��A�폜���� ����
	 * 
	 * @param motoSlip �`�[�N���X
	 * @return �\�z�����`�[(�t�֐�܂�)
	 * @throws TException
	 */
	public List<Slip> buildAndEntry(Slip motoSlip) throws TException {
		Slip slip = motoSlip.clone();

		// ��ɍ폜�B�X�V������
		if (!slip.isNew()) {
			delete(slip, true, false);
		}

		// �`�[�\�z
		List<Slip> slipList = buildSlip(slip);

		for (Slip tslip : slipList) {
			entry(tslip); // �o�^
		}

		return slipList;
	}

	/**
	 * �`�[�ԍ����̔Ԃ��A�`�[�ɃZ�b�g����
	 * 
	 * @param slip
	 * @throws TException
	 */
	public void setSlipNo(Slip slip) throws TException {
		SlipLogic logic = getSlipLogic(slip.getSlipType());
		String slipNo = logic.newSlipNo(slip);
		slip.setSlipNo(slipNo);
		slip.synchDetails();
	}

	/**
	 * �`�[�̃`�F�b�N
	 * 
	 * @param slip �`�[
	 * @return �G���[���b�Z�[�W���X�g
	 * @throws TException
	 */
	public List<Message> checkSlipError(Slip slip) throws TException {

		SlipLogic logic = getSlipLogic(slip.getSlipType());
		List<Message> messages = logic.checkSlipError(slip);
		if (slip.isChecksFleetMvmt()) {
			messages.addAll(checkSlipFleetConsistency(slip));
		}
		return messages;
	}

	/**
	 * �`�[���폜����
	 * 
	 * @param slip �Ώۓ`�[
	 */
	public void delete(Slip slip) {
		String dataKbn = slip.getHeader().getSWK_DATA_KBN();
		if (isSlipAP(dataKbn)) {
			this.delete(slip.getSlipNo(), slip.getSlipType(), dataKbn);
		}
		this.delete(slip.getSlipNo(), slip.getSlipType());
	}

	/**
	 * �`�[���폜����
	 * 
	 * @param slip �Ώۓ`�[
	 * @param isSaveHistory ������ۑ����邩�ǂ���
	 * @param isSaveDelHistory �폜������ۑ����邩�ǂ���
	 */
	public void delete(Slip slip, boolean isSaveHistory, boolean isSaveDelHistory) {
		this.delete(slip.getSlipNo(), slip.getSlipType(), isSaveHistory, isSaveDelHistory);
	}

	/**
	 * �`�[���폜����
	 * 
	 * @param slipNo �Ώۓ`�[�ԍ�
	 * @param slipType �Ώۓ`�[�ԍ��̓`�[���
	 */
	public void delete(String slipNo, String slipType) {
		this.delete(slipNo, slipType, true);
	}

	/**
	 * �`�[���폜����
	 * 
	 * @param slipNo �Ώۓ`�[�ԍ�
	 * @param slipType �Ώۓ`�[�ԍ��̓`�[���
	 * @param dataKbn
	 */
	public void delete(String slipNo, String slipType, String dataKbn) {
		this.delete(slipNo, slipType, dataKbn, true);
	}

	/**
	 * �`�[���폜����
	 * 
	 * @param slipNo �Ώۓ`�[�ԍ�
	 * @param slipType �Ώۓ`�[�ԍ��̓`�[���
	 * @param isSaveHistory ������ۑ����邩�ǂ���
	 */
	public void delete(String slipNo, String slipType, boolean isSaveHistory) {

		if (!Util.isNullOrEmpty(slipNo)) {

			// AP/AR�����@�\�L���̏ꍇ
			if (!isNotUseAP || !isNotUseAR) {
				// �폜�O��AP/AR�����d��̕����������s��
				try {
					restoreAPAR(slipNo);

				} catch (TException e) {
					throw new TRuntimeException(e);
				}
			}

			// BS��������@�\�L���̏ꍇ
			if (isUseBS) {
				// �폜�O��BS��������d��̕����������s��
				restoreBsBalance(slipNo);
			}

			// �t�@�C���Y�t�@�\�L���̏ꍇ
			if (isUseAttachment) {
				SlipAttachmentManager attachment = (SlipAttachmentManager) getComponent(SlipAttachmentManager.class);
				String companyCode = null;
				if (!getCompany().getAccountConfig().isUseGroupAccount()) {
					companyCode = getCompanyCode();
				}
				attachment.delete(companyCode, slipNo);
			}
			// �tⳋ@�\�L���̏ꍇ
			if (isUseTag) {
				SlipTagManager tag = (SlipTagManager) getComponent(SlipTagManager.class);
				String companyCode = null;
				if (!getCompany().getAccountConfig().isUseGroupAccount()) {
					companyCode = getCompanyCode();
				}
				tag.delete(companyCode, slipNo);
			}

			SlipLogic logic = getSlipLogic(slipType);
			logic.delete(slipNo, isSaveHistory);

			// CM_FUND_DTL�ɏ������s����
			if (isUseCmFund) {
				try {
					String companyCode = null;
					if (!getCompany().getAccountConfig().isUseGroupAccount()) {
						companyCode = getCompanyCode();
					}
					deleteCmFundInfo(companyCode, null, slipNo);

				} catch (TException e) {
					throw new TRuntimeException(e);
				}
			}

		}
	}

	/**
	 * �`�[���폜����
	 * 
	 * @param slipNo �Ώۓ`�[�ԍ�
	 * @param slipType �Ώۓ`�[�ԍ��̓`�[���
	 * @param dataKbn �f�[�^�敪
	 * @param isSaveHistory ������ۑ����邩�ǂ���
	 */
	public void delete(String slipNo, String slipType, String dataKbn, boolean isSaveHistory) {

		if (!Util.isNullOrEmpty(slipNo)) {

			// AP/AR�����@�\�L���̏ꍇ
			if (!isNotUseAP || !isNotUseAR) {
				// �폜�O��AP/AR�����d��̕����������s��
				try {
					restoreAPAR(slipNo);

				} catch (TException e) {
					throw new TRuntimeException(e);
				}
			}

			// BS��������@�\�L���̏ꍇ
			if (isUseBS) {
				// �폜�O��BS��������d��̕����������s��
				restoreBsBalance(slipNo);
			}

			// �t�@�C���Y�t�@�\�L���̏ꍇ
			if (isUseAttachment) {
				SlipAttachmentManager attachment = (SlipAttachmentManager) getComponent(SlipAttachmentManager.class);
				String companyCode = null;
				if (!getCompany().getAccountConfig().isUseGroupAccount()) {
					companyCode = getCompanyCode();
				}
				attachment.delete(companyCode, slipNo);
			}

			SlipLogic logic = getSlipLogic(slipType, dataKbn);
			logic.delete(slipNo, isSaveHistory);

			// CM_FUND_DTL�ɏ������s����
			if (isUseCmFund) {
				try {
					String companyCode = null;
					if (!getCompany().getAccountConfig().isUseGroupAccount()) {
						companyCode = getCompanyCode();
					}
					deleteCmFundInfo(companyCode, null, slipNo);

				} catch (TException e) {
					throw new TRuntimeException(e);
				}
			}
		}
	}

	/**
	 * �`�[���폜����
	 * 
	 * @param slipNo �Ώۓ`�[�ԍ�
	 * @param slipType �Ώۓ`�[�ԍ��̓`�[���
	 * @param isSaveHistory ������ۑ����邩�ǂ���
	 * @param isSaveDelHistory �폜������ۑ����邩�ǂ���
	 */
	public void delete(String slipNo, String slipType, boolean isSaveHistory, boolean isSaveDelHistory) {

		if (!Util.isNullOrEmpty(slipNo)) {

			// BS��������@�\�L���̏ꍇ
			if (isUseBS) {
				// �폜�O��BS��������d��̕����������s��
				restoreBsBalance(slipNo);
			}

			// �t�@�C���Y�t�@�\�L���̏ꍇ
			if (isUseAttachment) {
				SlipAttachmentManager attachment = (SlipAttachmentManager) getComponent(SlipAttachmentManager.class);
				String companyCode = null;
				if (!getCompany().getAccountConfig().isUseGroupAccount()) {
					companyCode = getCompanyCode();
				}
				attachment.delete(companyCode, slipNo);
			}
			// �tⳋ@�\�L���̏ꍇ
			if (isUseTag) {
				SlipTagManager tag = (SlipTagManager) getComponent(SlipTagManager.class);
				String companyCode = null;
				if (!getCompany().getAccountConfig().isUseGroupAccount()) {
					companyCode = getCompanyCode();
				}
				tag.delete(companyCode, slipNo);
			}

			SlipLogic logic = getSlipLogic(slipType);
			logic.delete(slipNo, isSaveHistory, isSaveDelHistory);

			// CM_FUND_DTL�ɏ������s����
			if (isUseCmFund) {
				try {
					String companyCode = null;
					if (!getCompany().getAccountConfig().isUseGroupAccount()) {
						companyCode = getCompanyCode();
					}
					deleteCmFundInfo(companyCode, null, slipNo);

				} catch (TException e) {
					throw new TRuntimeException(e);
				}
			}
		}
	}

	/**
	 * �폜�O��BS��������d��̕�������
	 * 
	 * @param slipNo �Ώۓ`�[�ԍ�
	 */
	protected void restoreBsBalance(String slipNo) {
		SlipCondition condition = createSlipCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setSlipNo(slipNo);
		condition.setProgramCode(getProgramCode());
		condition.setUserCode(getUserCode());

		// �폜�O��BS��������d��̕����������s��
		BSEraseManager bsManager = (BSEraseManager) getComponent(BSEraseManager.class);
		bsManager.restoreBsBalance(condition);
	}

	/**
	 * �폜�O��AP/AR�����d��̕�������
	 * 
	 * @param slipNo �Ώۓ`�[�ԍ�
	 * @throws TException
	 */
	protected void restoreAPAR(String slipNo) throws TException {

		SlipCondition condition = createSlipCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setSlipNo(slipNo);
		condition.setProgramCode(getProgramCode());
		condition.setUserCode(getUserCode());

		restoreAPAR(condition);
	}

	/**
	 * �`�[�����̍쐬
	 * 
	 * @return �`�[����
	 */
	protected SlipCondition createSlipCondition() {
		return new SlipCondition();
	}

	/**
	 * �`�[�r��
	 * 
	 * @param slip �`�[
	 * @throws TException
	 */
	public void lock(Slip slip) throws TException {
		SlipLogic logic = getSlipLogic(slip.getSlipType());
		logic.lock(logic.getHeaderDao(), slip);
	}

	/**
	 * �`�[�r������
	 * 
	 * @param slip �`�[
	 */
	public void unlock(Slip slip) {
		SlipLogic logic = getSlipLogic(slip.getSlipType());
		logic.unlock(logic.getHeaderDao(), slip);
	}

	/**
	 * �`�[�r������
	 * 
	 * @param slipType �`�[���
	 */
	public void unlockAll(String slipType) {
		SlipLogic logic = getSlipLogic(slipType);
		logic.unlockAll(logic.getHeaderDao());
	}

	/**
	 * �`�[���\�z����.<br>
	 * �e��l�ݒ�A����Ŏd��
	 * 
	 * @param slip �`�[�N���X
	 * @return �\�z�����`�[�p�^�[��
	 * @throws TException
	 */
	public List<Slip> buildSlipPattern(Slip slip) throws TException {
		SlipLogic logic = getSlipLogic(slip.getSlipType());

		// �`�[�p�^�[���\�z
		return logic.buildSlipPattern(slip);
	}

	/**
	 * �`�[�p�^�[�����N�[����
	 * 
	 * @param slip �`�[�N���X
	 */
	public void entryPattern(Slip slip) {
		SlipLogic logic = getSlipLogic(slip.getSlipType());
		logic.entryPattern(slip);
	}

	/**
	 * �`�[�p�^�[�����\�z���ēo�^����.<br>
	 * ����Ŏd��
	 * 
	 * @param motoSlip �`�[�N���X
	 * @return �\�z�����`�[(�t�֐�܂�)
	 * @throws TException
	 */
	public List<Slip> buildAndEntryPattern(Slip motoSlip) throws TException {
		Slip slip = motoSlip.clone();

		// ��ɍ폜
		if (!slip.isNew()) {
			deletePattern(slip);
		}

		// �`�[�\�z
		List<Slip> slipList = buildSlipPattern(slip);

		for (Slip tslip : slipList) {
			entryPattern(tslip); // �o�^
		}

		return slipList;
	}

	/**
	 * �`�[�p�^�[�����폜����
	 * 
	 * @param slip �Ώۓ`�[
	 */
	public void deletePattern(Slip slip) {
		SlipLogic logic = getSlipLogic(slip.getSlipType());
		logic.deletePattern(slip);
	}

	/**
	 * �`�[�p�^�[���r��
	 * 
	 * @param slip �`�[
	 * @throws TException
	 */
	public void lockPattern(Slip slip) throws TException {
		SlipLogic logic = getSlipLogic(slip.getSlipType());
		logic.lock(logic.getPatternHeaderDao(), slip);
	}

	/**
	 * �`�[�p�^�[���r������
	 * 
	 * @param slip �`�[
	 */
	public void unlockPattern(Slip slip) {
		SlipLogic logic = getSlipLogic(slip.getSlipType());
		logic.unlock(logic.getPatternHeaderDao(), slip);
	}

	/**
	 * �`�[�p�^�[���r������
	 * 
	 * @param slipType �`�[���
	 */
	public void unlockPatternAll(String slipType) {
		SlipLogic logic = getSlipLogic(slipType);
		logic.unlockAll(logic.getPatternHeaderDao());
	}

	/**
	 * �w�b�_�f�[�^�擾
	 * 
	 * @param slipType �`�[���
	 * @param param ����
	 * @return �w�b�_���X�g
	 */
	public List<SWK_HDR> getHeader(String slipType, SlipCondition param) {
		SlipLogic logic = getSlipLogic(slipType);
		return logic.getHeader(param);
	}

	/**
	 * �w�b�_�f�[�^�擾
	 * 
	 * @param param ����
	 * @return �w�b�_���X�g
	 */
	public List<SWK_HDR> getHeader(SlipCondition param) {
		// �����擾�pDao
		SlipDao dao = (SlipDao) getComponent(SlipDao.class);
		return dao.findByCondition(param);
	}

	/**
	 * �w�b�_�f�[�^�̃G�N�Z���擾
	 * 
	 * @param param ����
	 * @return �w�b�_���X�g�̃G�N�Z��
	 * @throws TException
	 */
	public byte[] getHeaderExcel(SlipCondition param) throws TException {
		List<SWK_HDR> list = getHeader(param);

		if (list == null || list.isEmpty()) {
			return null;
		}

		SlipHeaderExcel excel = new SlipHeaderExcel(getUser().getLanguage());

		byte[] data = excel.getExcel(list);
		return data;
	}

	/**
	 * ���׃f�[�^�擾
	 * 
	 * @param param ����
	 * @return �d�󃊃X�g
	 */
	public List<SWK_DTL> getDetails(SlipCondition param) {
		SWK_DTLDao dao = (SWK_DTLDao) getComponent("NEW-SWK_DTLDao");
		return dao.findByCondition(param.toSQL());
	}

	/**
	 * �w�b�_�f�[�^�擾
	 * 
	 * @param param ����
	 * @return �w�b�_���X�g
	 */
	public List<SWK_HDR> getPatternHeader(SlipCondition param) {
		// �����擾�pDao
		SlipDao dao = (SlipDao) getComponent(SlipDao.class);

		return dao.findPatternByCondition(param);
	}

	/**
	 * ���׃f�[�^�擾
	 * 
	 * @param param ����
	 * @return �d�󃊃X�g
	 */
	public List<SWK_DTL> getPatternDetails(SlipCondition param) {
		SWK_PTN_DTLDao dao = (SWK_PTN_DTLDao) getComponent("NEW-SWK_PTN_DTLDao");
		return dao.findByCondition(param.toSQL());
	}

	/**
	 * �w�b�_�f�[�^�擾
	 * 
	 * @param param ����
	 * @return �w�b�_���X�g
	 */
	public List<SWK_HDR> getHistoryHeader(SlipCondition param) {
		// �����擾�pDao
		SlipDao dao = (SlipDao) getComponent(SlipDao.class);
		return dao.findHistoryByCondition(param);
	}

	/**
	 * ���׃f�[�^�擾
	 * 
	 * @param param ����
	 * @return �d�󃊃X�g
	 */
	public List<SWK_DTL> getHistoryDetails(SlipCondition param) {
		SWK_DEL_DTLDao dao = (SWK_DEL_DTLDao) getComponent("NEW-SWK_DEL_DTLDao");
		return dao.findByCondition(param.toSQL());
	}

	/**
	 * �`�[�擾
	 * 
	 * @param condition ����
	 * @return �`�[���X�g
	 */
	public List<Slip> getSlip(SlipCondition condition) {

		SlipDao dao = (SlipDao) getComponent(SlipDao.class);
		List<Slip> slips = dao.findSlipByCondition(condition);
		return slips;

	}

	/**
	 * �w��w�b�_���`�[���擾����.
	 * 
	 * @param hdr �`�[
	 * @param condition
	 * @return �`�[
	 * @throws TException
	 */
	public Slip getSlip(SWK_HDR hdr, SlipCondition condition) throws TException {

		// �`�[�\�z
		Slip slip = new Slip(hdr);
		slip.setDetails(getDetails(condition));

		// �����ێ�Entity�Ȃǂ�ݒ�.
		SlipLogic logic = getSlipLogic(slip.getSlipType());
		logic.setupSlip(slip);

		return slip;
	}

	/**
	 * �w������̒�����擾����.
	 * 
	 * @param condition ����
	 * @return ���냊�X�g
	 */
	public List<SlipBooks> getSlipBooks(SlipCondition condition) {
		List<Slip> slipList = getSlip(condition);

		List<SlipBooks> list = new ArrayList<SlipBooks>(slipList.size());

		for (Slip slip : slipList) {
			list.add(new SlipBooks(slip));
		}

		return list;
	}

	/**
	 * �w������̒�����擾����.
	 * 
	 * @param condition ����
	 * @return ���냊�X�g
	 */
	public List<SlipDen> getSlipDen(SlipCondition condition) {
		List<Slip> slipList = getSlip(condition);

		List<SlipDen> list = new ArrayList<SlipDen>(slipList.size());

		for (Slip slip : slipList) {
			SlipDen slipDen = new SlipDen();
			slipDen.setSWK_DEN_NO(slip.getSlipNo());
			list.add(slipDen);
		}

		return list;
	}

	/**
	 * SWK_HDR��SlipDen
	 * 
	 * @param hdr
	 * @return SlipDen
	 */
	protected SlipDen convertToSlipDen(SWK_HDR hdr) {
		SlipDen bean = new SlipDen();
		bean.setKAI_CODE(hdr.getKAI_CODE());
		bean.setSWK_DEN_DATE(hdr.getSWK_DEN_DATE());
		bean.setSWK_DEN_NO(hdr.getSWK_DEN_NO());
		bean.setSWK_BEFORE_DEN_NO(hdr.getSWK_BEFORE_DEN_NO());
		bean.setSWK_BEFORE_UPD_KBN(hdr.getSWK_UPD_KBN());
		bean.setSWK_DATA_KBN(hdr.getSWK_DATA_KBN());
		bean.setSWK_UPD_KBN(hdr.getSWK_UPD_KBN());
		bean.setSWK_DEN_DATE(hdr.getSWK_DEN_DATE());

		bean.setSWK_IRAI_EMP_CODE(hdr.getSWK_IRAI_EMP_CODE());
		bean.setSWK_TEK(hdr.getSWK_TEK());
		bean.setSWK_DEN_SYU(hdr.getSWK_DEN_SYU());
		bean.setSWK_CUR_CODE(hdr.getSWK_CUR_CODE());
		bean.setSWK_UPD_CNT(hdr.getSWK_UPD_CNT());
		bean.setSWK_KSN_KBN(hdr.getSWK_KSN_KBN());

		// KAI_CODE String ��ЃR�[�h
		// SWK_DEN_NO String �`�[�ԍ�
		// SWK_DEN_DATE Date �`�[���t
		// SWK_BEFORE_DEN_NO String BEFORE�`�[�ԍ�
		// SWK_BEFORE_UPD_KBN int �O��X�V�敪
		// SWK_DATA_KBN String �f�[�^�敪
		// SWK_UPD_KBN int �X�V�敪
		// SWK_SYO_EMP_CODE String ���F�҃R�[�h
		// SWK_SYO_EMP_NAME String ���F�Җ���
		// SWK_SYO_EMP_NAME_S String ���F�җ���
		// SWK_SYO_DATE Date ���F��
		// SWK_IRAI_EMP_CODE String �˗���
		// SWK_TEK String �`�[�E�v
		// SWK_DEN_SYU String �`�[���
		// SWK_CUR_CODE String �ʉ݃R�[�h
		// SWK_UPD_CNT int �C����

		return bean;
	}

	/**
	 * �w��ԍ��̒�����擾����.
	 * 
	 * @param compCode ��ЃR�[�h
	 * @param slipNo �`�[�ԍ�
	 * @return ����
	 */
	public SlipBooks getSlipBooks(String compCode, String slipNo) {
		SlipCondition param = new SlipCondition();
		param.setCompanyCode(compCode);
		param.setSlipNo(slipNo);

		List<Slip> list = getSlip(param);

		if (list.isEmpty()) {
			return null;
		}

		return new SlipBooks(list.get(0));
	}

	/**
	 * �w��w�b�_���`�[�p�^�[�����擾����.
	 * 
	 * @param hdr �`�[
	 * @return �`�[
	 * @throws TException
	 */
	public Slip getPatternSlip(SWK_HDR hdr) throws TException {

		// �`�[�\�z
		Slip slip = new Slip(hdr);

		SlipCondition param = new SlipCondition();
		param.setCompanyCode(slip.getCompanyCode());
		param.setSlipNo(slip.getSlipNo());
		slip.setDetails(getPatternDetails(param));

		// �����ێ�Entity�Ȃǂ�ݒ�.
		SlipLogic logic = getSlipLogic(slip.getSlipType());
		logic.setupSlip(slip);

		// �Y�t�t�@�C�����N���A
		slip.getHeader().setAttachments(null);
		// �tⳋ@�\���N���A
		slip.getHeader().setSwkTags(null);

		// ���ׂ̕R�t��������
		for (SWK_DTL dtl : slip.getDetails()) {
			dtl.setSWK_DEN_NO(null);
			dtl.setSWK_KESI_KBN(SWK_DTL.KESI_KBN.NOMAL);
			dtl.setSWK_KESI_DATE(null); // �����`�[���t
			dtl.setSWK_KESI_DEN_NO(null); // �����`�[�ԍ�
			dtl.setSWK_SOUSAI_DATE(null); // ���E�`�[���t
			dtl.setSWK_SOUSAI_DEN_NO(null); // ���E�`�[�ԍ�
			dtl.setSWK_SOUSAI_GYO_NO(null); // ���E�s�ԍ�

			dtl.setSWK_APAR_KESI_KBN(SWK_DTL.KESI_KBN.NOMAL);// AP/AR�����敪
			dtl.setSWK_APAR_DEN_NO(null);// AP/AR�����`�[�ԍ�
			dtl.setSWK_APAR_GYO_NO(null);// AP/AR�����s�ԍ�

			dtl.setAP_ZAN(null);
			dtl.setAR_ZAN(null);
			dtl.setBsDetail(null);
		}

		return slip;
	}

	/**
	 * �`�[�p�^�[���擾
	 * 
	 * @param condition ����
	 * @return �`�[���X�g
	 */
	public List<Slip> getPatternSlip(SlipCondition condition) {

		SlipDao dao = (SlipDao) getComponent(SlipDao.class);
		return dao.findSlipPatternByCondition(condition);

	}

	/**
	 * �`�[�擾
	 * 
	 * @param condition ����
	 * @return �`�[���X�g
	 */
	public List<Slip> getHistorySlip(SlipCondition condition) {

		SlipDao dao = (SlipDao) getComponent(SlipDao.class);
		return dao.findSlipHistoryByCondition(condition);

	}

	/**
	 * �w������̒�����擾����.(����p)
	 * 
	 * @param condition ����
	 * @return ���냊�X�g
	 */
	public List<SlipBooks> getHistorySlipBooks(SlipCondition condition) {
		List<Slip> slipList = getHistorySlip(condition);

		List<SlipBooks> list = new ArrayList<SlipBooks>(slipList.size());

		for (Slip slip : slipList) {
			list.add(new SlipBooks(slip));
		}

		return list;
	}

	/**
	 * �ꎞ�`�[���[��Ԃ�
	 * 
	 * @param tempSlip �ꎞ�`�[
	 * @return �ꎞ�`�[���[
	 * @throws TException
	 */
	public byte[] getTempSlipReport(Slip tempSlip) throws TException {

		// �ꎞ�`�[�ԍ���PREVIEW
		tempSlip.setSlipNo("PREVIEW");
		tempSlip.setAvoidReuiredItemNULL(true); // build���`�F�b�N�s�v

		// ����Ŏd��ǉ�
		List<Slip> list = buildSlip(tempSlip);

		for (Slip slip : list) {
			// �^�C�g����(PREVIEW)�󎚒ǉ�
			slip.getHeader().setSWK_DEN_SYU_NAME_K(slip.getHeader().getSWK_DEN_SYU_NAME_K() + "(PREVIEW)");
			slip.setDetails(slip.getDetails(CurrencyType.KEY));
		}

		return getReport(list);
	}

	/**
	 * �`�[���[��Ԃ�
	 * 
	 * @param slips �`�[(�t�֐���܂߂��f�[�^)
	 * @return �`�[���[
	 * @throws TException
	 */
	public byte[] getReport(List<Slip> slips) throws TException {
		if (slips.isEmpty()) {
			return new byte[0];
		}

		// PDF�ɑ����Ђ̊���Ȗڂ�\�����邩
		if (isUseTransferItemName) {
			// �t�։Ȗڂ̎擾
			Map<String, TransferConfig> confMap = new TreeMap<String, TransferConfig>();
			CompanyManager manager = (CompanyManager) getComponent(CompanyManager.class);

			// �����}�b�v�쐬
			Map<String, SWK_DTL> dtlMap = new TreeMap<String, SWK_DTL>();

			for (Slip slip : slips) {
				for (SWK_DTL dtl : slip.getDetails()) {
					// �������׍s���ۂ� ����Ŗ��ׂ���Ȃ�
					// ���O�C����� �� �t�֋敪=0������ōs����Ȃ�
					// ���O�C����ЈȊO �� �����d��s����Ȃ�
					if ((dtl.getKAI_CODE().equals(getCompanyCode()) && dtl.getSWK_TUKE_KBN() == 0) //
						|| (!dtl.getKAI_CODE().equals(getCompanyCode()) && !dtl.isAutoDetail())) {
						// �e��Ўd��擾���邽�߁A�}�b�v�Ɉꎞ�ۑ� // key :��ЃR�[�h
						String key = dtl.getKAI_CODE();

						// �ŏ��s�ԍ��̂݃Z�b�g�����Ƀ}�b�v�ɑ��݂���ꍇ�̓Z�b�g���Ȃ�
						if (!dtlMap.containsKey(key)) {
							dtlMap.put(key, dtl);
						}
					}

					// ��ЃR�[�h�ƌv���ЃR�[�h���قȂ�ꍇ�AMAP����
					if (!Util.equals(dtl.getKAI_CODE(), dtl.getSWK_K_KAI_CODE())) {
						// Key=��ЃR�[�h�ƌv���ЃR�[�h
						String key = dtl.getKAI_CODE() + "<>" + dtl.getSWK_K_KAI_CODE();
						// Map�ɑ��݂��Ȃ��ꍇ�A�f�[�^�擾
						if (!confMap.containsKey(key)) {
							List<TransferConfig> list = manager.getTransferConfig(dtl.getKAI_CODE(),
								dtl.getSWK_K_KAI_CODE());
							if (list == null || list.isEmpty()) {
								continue;
							}
							// 1�s�����̂͂�
							for (TransferConfig bean : list) {
								String from = bean.getCompanyCode();
								String to = bean.getTransferCompanyCode();
								confMap.put(from + "<>" + to, bean);
							}
						}
					}
				}
			}

			// �����}�b�v�Ǝd��W���[�i���ɂ���Ē����������s��
			for (Slip slip : slips) {
				for (SWK_DTL dtl : slip.getDetails()) {
					// Key=��ЃR�[�h�ƌv���ЃR�[�h
					String mapKey = dtl.getKAI_CODE() + "<>" + dtl.getSWK_K_KAI_CODE();
					TransferConfig bean = confMap.get(mapKey);

					// �����d��s�ŕt�։Ȗڂ̏ꍇ�͓���ɖ��̃Z�b�g
					if (dtl.isAutoDetail() //
						&& bean != null //
						&& (Util.equals(dtl.getSWK_KMK_CODE(), bean.getTransferItemCode()) //
							&& Util.equals(dtl.getSWK_HKM_CODE(), bean.getTransferSubItemCode()) //
							&& Util.equals(dtl.getSWK_UKM_CODE(), bean.getTransferDetailItemCode()) //
						)) {

						String key = dtl.getSWK_K_KAI_CODE();
						// �}�b�v�����݂���
						if (dtlMap.containsKey(key)) {
							// �Ώۖ��ׂ��擾
							SWK_DTL baseDtl = dtlMap.get(key);
							// �����Ђ̊���Ȗږ���(���T����)�{�⏕�Ȗږ��̂ɏ㏑��
							String name = "(" + StringUtil.leftBX(baseDtl.getSWK_KMK_NAME(), 10);
							// �⏕���̂����݂���ꍇ�u�E�v�� ��؂�
							if (!Util.isNullOrEmpty(baseDtl.getSWK_HKM_NAME())) {
								name += "�E" + baseDtl.getSWK_HKM_NAME();
							}
							name += ")";

							// ����Ȗڂɖ��̃Z�b�g���`�[PDF�\���̔���p�ɃR�[�h��NULL
							dtl.setSWK_UKM_CODE(null);
							dtl.setSWK_UKM_NAME(name);
						}
					}
				}
			}
		}

		List<LedgerBookAndLayout> books = new ArrayList<LedgerBookAndLayout>();
		SlipLayoutDefine define = (SlipLayoutDefine) getComponent(SlipLayoutDefine.class);

		for (Slip slip : slips) {
			// ���[
			LedgerBook book = getReportBook(slip);
			// ���C�A�E�g
			SlipLogic slipLogic = getSlipLogic(slip.getSlipType());
			SlipLayout layout = slipLogic.getLayout(getUser().getLanguage());

			// �`�[��ʏ��ݒ�
			define.setSlipType(slip.getSlipType()); // �`�[��ʃR�[�h
			define.setSlipKind(slip.getSlipKind()); // �f�[�^�敪Enum
			define.setDataDivision(slip.getHeader().getSWK_DATA_KBN()); // �f�[�^�敪�̃R�[�h

			// �`�[���C�A�E�g�̒�`�̐ݒ�
			layout.setDefine(define);

			books.add(new LedgerBookAndLayout(book, layout));
		}

		// �y�[�W�ԍ��Ή�
		setupPageNo(books);

		// ���C�A�E�g�𐶐����A�o�C�i���ŕԂ�
		DefautlSlipLayout layout = new DefautlSlipLayout(getUser().getLanguage());
		byte[] data = layout.getMultiReport(books);
		data = setupSlipNoBytes(slips.get(0), data);
		return data;
	}

	/**
	 * @param books
	 */
	protected void setupPageNo(List<LedgerBookAndLayout> books) {

		// �y�[�W�ԍ��t�ւ���ВP�ʂ��ǂ���(true:�`�[�ԍ��P��)
		if (!ServerConfig.isFlagOn("trans.slip.transfer.page.no")) {
			return;
		}

		// �J�n�y�[�W�ԍ�����
		Map<String, Integer> countMap = new HashMap<String, Integer>();

		for (int i = 0; i < books.size(); i++) {

			SlipBook slipBook = (SlipBook) books.get(i).getBook();
			SlipBookHeader header = (SlipBookHeader) slipBook.getSheetAt(0).getHeader();
			String currentKey = header.getSlipNo();

			// �O��̔ԍ��Ő؂�ւ�
			if (countMap.containsKey(currentKey)) {
				slipBook.setStartPageNo(countMap.get(currentKey));
			}

			// ���y�[�W���������߁A�W�߂�
			if (countMap.containsKey(currentKey)) {
				int totalSheetCount = countMap.get(currentKey) + slipBook.getSheetCount();
				countMap.put(currentKey, totalSheetCount);
			} else {
				countMap.put(currentKey, slipBook.getSheetCount());
			}

		}

		// ���y�[�W������
		for (int i = 0; i < books.size(); i++) {
			SlipBook slipBook = (SlipBook) books.get(i).getBook();
			SlipBookHeader header = (SlipBookHeader) slipBook.getSheetAt(0).getHeader();
			String currentKey = header.getSlipNo();
			slipBook.setTotalPageCount(countMap.get(currentKey));
		}
	}

	/**
	 * �`�[�ԍ��ݒ�
	 * 
	 * @param slip �`�[
	 * @param data ���o�C�g�z��
	 * @return �V�o�C�g�z��
	 */
	protected byte[] setupSlipNoBytes(Slip slip, byte[] data) {
		if (!isFileNameWithSlipNo || data == null || slip == null || Util.isNullOrEmpty(slip.getSlipNo())) {
			return data;
		}

		byte[] slipNo = ("<SlipNo>" + StringUtil.fill(slip.getSlipNo(), 20, ' ') + "</SlipNo>").getBytes();
		byte[] newdata = new byte[data.length + slipNo.length];
		StringUtil.arraycopy(data, 0, newdata, 0, data.length);
		StringUtil.arraycopy(slipNo, 0, newdata, data.length, slipNo.length);
		return newdata;
	}

	/**
	 * �`�[Book��Ԃ�
	 * 
	 * @param slip
	 * @return �`�[Book
	 * @throws TException
	 */
	public SlipBook getReportBook(Slip slip) throws TException {

		// �`�[���W�b�N�擾
		SlipLogic slipLogic = getSlipLogic(slip.getSlipType());

		// ���{
		SlipBook book = slipLogic.toBook(slip);

		return book;

	}

	/**
	 * �`�[���[��Ԃ�
	 * 
	 * @param slip �`�[
	 * @return �`�[���[
	 * @throws TException
	 */
	public byte[] getReport(Slip slip) throws TException {

		// ���{
		SlipBook book = getReportBook(slip);

		// ���C�A�E�g�𐶐����A�o�C�i���ŕԂ�
		SlipLogic slipLogic = getSlipLogic(slip.getSlipType());
		SlipLayout layout = slipLogic.getLayout(getUser().getLanguage());
		SlipLayoutDefine define = (SlipLayoutDefine) getComponent(SlipLayoutDefine.class);

		// �`�[��ʏ��ݒ�
		define.setSlipType(slip.getSlipType()); // �`�[��ʃR�[�h
		define.setSlipKind(slip.getSlipKind()); // �f�[�^�敪Enum
		define.setDataDivision(slip.getHeader().getSWK_DATA_KBN()); // �f�[�^�敪�̃R�[�h

		// �`�[���C�A�E�g�̒�`�̐ݒ�
		layout.setDefine(define);

		byte[] data = layout.getReport(book);
		data = setupSlipNoBytes(slip, data);
		return data;
	}

	/**
	 * �w���ЁA�`�[�ԍ��̓`�[���[��Ԃ��B<BR>
	 * �������������Ԃ����A�������낪�����ꍇ��IFRS����`�[��Ԃ��B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param slipNo �`�[�ԍ�
	 * @return �`�[���[
	 * @throws TException
	 */
	public byte[] getReport(String companyCode, String slipNo) throws TException {
		List<String> list = new ArrayList<String>(1);
		list.add(slipNo);

		return getReport(companyCode, list);
	}

	/**
	 * �w���ЁA�`�[�ԍ��̓`�[���[��Ԃ��B<BR>
	 * �������������Ԃ����A�������낪�����ꍇ��IFRS����`�[��Ԃ��B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param slipNoList �`�[�ԍ����X�g
	 * @return �`�[���[
	 * @throws TException
	 */
	public byte[] getReport(String companyCode, List<String> slipNoList) throws TException {

		// �w��̓`�[�𒊏o
		SlipCondition condition = new SlipCondition();
		condition.setCompanyCode(companyCode);
		condition.setSlipNoList(slipNoList.toArray(new String[slipNoList.size()]));

		List<SlipBooks> list = getSlipBooks(condition);

		if (list == null || list.isEmpty()) {
			return null;
		}

		List<Slip> slipList = getSlipList(list);

		return getReport(slipList);
	}

	/**
	 * �w��`�[�ԍ�(����)�̓`�[���[��Ԃ��B<BR>
	 * �������������Ԃ����A�������낪�����ꍇ��IFRS����`�[��Ԃ��B <BR>
	 * �t�֎d��̏ꍇ�A���ЁE�����З����̓`�[���o�͂���B
	 * 
	 * @param slipNoList �`�[�ԍ����X�g
	 * @return �`�[���[
	 * @throws TException
	 */
	public byte[] getReportBySlipNos(List<String> slipNoList) throws TException {

		// �w��̓`�[�𒊏o
		SlipCondition condition = new SlipCondition();

		if (!getCompany().getAccountConfig().isUseGroupAccount()) {
			condition.setCompanyCode(getCompanyCode());
		}

		condition.setSlipNoList(slipNoList.toArray(new String[slipNoList.size()]));

		// �t�֎d��ɑΉ�
		condition.addOrder(SlipCondition.SORT_SLIP_DATE);
		condition.addOrder(SlipCondition.SORT_SLIP_NO);

		if (ServerConfig.isFlagOn("trans.slip.transfer.page.no")) {
			// ���O�C����Ђ͍ŗD��
			SQLCreator sql = new SQLCreator();
			sql.add(" CASE WHEN hdr.KAI_CODE = ? THEN 0 ELSE 1 END ", getCompanyCode());
			condition.addOrder(sql.toSQL());
		}

		condition.addOrder(SlipCondition.SORT_COMPANY_CODE);
		condition.addOrder(SlipCondition.SORT_LINE_NO);

		List<SlipBooks> list = getSlipBooks(condition);

		if (list == null || list.isEmpty()) {
			return new byte[0];
		}

		List<Slip> slipList = getSlipList(list);

		return getReport(slipList);
	}

	/**
	 * ������̃f�[�^���猴�����������Ԃ����A�������낪�����ꍇ��IFRS����`�[��Ԃ��B
	 * 
	 * @param list ������̓`�[�f�[�^�̃��X�g
	 * @return ��������`�[�i��������`�[���Ȃ��ꍇ��IFRS����`�[�j
	 */
	protected List<Slip> getSlipList(List<SlipBooks> list) {
		List<Slip> slipList = new ArrayList<Slip>(list.size());

		for (SlipBooks slipBook : list) {
			Slip slip = slipBook.getOwnBookSlip();

			if (slip == null || slip.getDetails() == null || slip.getDetails().isEmpty()) {
				slip = slipBook.getIFRSBookSlip();

				if (slip == null || slip.getDetails() == null || slip.getDetails().isEmpty()) {
					slip = slipBook.getIFRSFuncSlip();

					if (slip == null || slip.getDetails() == null || slip.getDetails().isEmpty()) {
						continue;
					}
				}
			}

			slipList.add(slip);
		}
		return slipList;
	}

	/**
	 * �`�[�����F����
	 * 
	 * @param slipList ���F����`�[�̃��X�g
	 * @throws TException
	 */
	public List<SlipDen> approveSlip(List<SlipDen> slipList) throws TException {

		// ���F�Ј�
		Employee employee = getUser().getEmployee();

		// �`�[�֘A�e�[�u�����b�N
		lockSlipTable();

		// �۔F��̓`�[�ꗗ
		List<SlipDen> afterSlipList = new ArrayList<SlipDen>();

		// �Ώۓ`�[����������
		for (SlipDen dtl : slipList) {
			// ���F�r�W�l�X���W�b�N�擾
			SlipLogic slipLogic = getSlipLogic(dtl.getSWK_DEN_SYU());
			SlipApprove sa = slipLogic.getSlipApprove();

			if (getCompany().getAccountConfig().isUseWorkflowApprove()) {
				// ���F
				if (dtl.isACCT_APRV_FLG()) {
					sa.approveSlip(dtl, employee);
				} else {
					sa.approveSlipForFieldState(dtl, employee);
				}
			} else {
				sa.approveSlip(dtl, employee);
			}

			// �t�ւ�����ꍇ�A�t�֐�̓`�[�������ɏ��F����B
			if (getCompany().getAccountConfig().isUseGroupAccount()) {
				List<SWK_HDR> hdrList = getSubsidiaryCompanySlips(dtl.getSWK_DEN_NO(), dtl.getSWK_DEN_DATE());
				if (hdrList != null && !hdrList.isEmpty()) {
					for (SWK_HDR hdr : hdrList) {
						sa.approveSlip(convertToSlipDen(hdr), employee);
					}
				}
			}
			afterSlipList.add(dtl);
		}

		return afterSlipList;
	}

	/**
	 * �`�[��۔F����
	 * 
	 * @param slipList �۔F����`�[�̃��X�g
	 * @return �۔F��̓`�[
	 * @throws TException
	 */
	public List<SlipDen> denySlip(List<SlipDen> slipList) throws TException {

		// �`�[�֘A�e�[�u�����b�N
		lockSlipTable();

		// �۔F��̓`�[�ꗗ
		List<SlipDen> afterSlipList = new ArrayList<SlipDen>();

		// �Ώۓ`�[����������
		for (SlipDen dtl : slipList) {
			// �۔F�r�W�l�X���W�b�N�擾
			SlipLogic slipLogic = getSlipLogic(dtl.getSWK_DEN_SYU());
			SlipApprove sa = slipLogic.getSlipApprove();

			// �۔F
			sa.denySlip(dtl);

			// �t�ւ�����ꍇ�A�t�֐�̓`�[�������ɔ۔F����B
			if (getCompany().getAccountConfig().isUseGroupAccount()) {
				List<SWK_HDR> hdrList = getSubsidiaryCompanySlips(dtl.getSWK_DEN_NO(), dtl.getSWK_DEN_DATE());
				if (hdrList != null && !hdrList.isEmpty()) {
					for (SWK_HDR hdr : hdrList) {
						sa.denySlip(convertToSlipDen(hdr));
					}
				}

			}

			afterSlipList.add(dtl);
		}

		return afterSlipList;
	}

	/**
	 * �`�[���F��������
	 * 
	 * @param slipList ���F���������`�[�̃��X�g
	 * @return ���F�����̓`�[
	 * @throws TException
	 */
	public List<SlipDen> cancelApproveSlip(List<SlipDen> slipList) throws TException {

		// ���ꏳ�F���g����
		boolean isUseFieldApprove = getCompany().getAccountConfig().isUseFieldApprove();

		// �`�[�֘A�e�[�u�����b�N
		lockSlipTable();

		// ���F�����̓`�[�ꗗ
		List<SlipDen> afterSlipList = new ArrayList<SlipDen>();

		// �Ώۓ`�[����������
		for (SlipDen dtl : slipList) {
			// ���F����r�W�l�X���W�b�N�擾
			SlipLogic slipLogic = getSlipLogic(dtl.getSWK_DEN_SYU());
			SlipApprove sa = slipLogic.getSlipApprove();

			if (getCompany().getAccountConfig().isUseWorkflowApprove()) {
				// ���F
				if (dtl.isACCT_APRV_FLG()) {
					// ���F���
					sa.cancelApproveSlip(dtl, isUseFieldApprove);
				} else {
					// ���F���
					sa.cancelApproveSlipForFieldState(dtl, isUseFieldApprove);
				}
			} else {
				// ���F���
				sa.cancelApproveSlip(dtl, isUseFieldApprove);
			}

			// �t�ւ�����ꍇ�A�t�֐�̓`�[�������ɏ��F�������B
			if (getCompany().getAccountConfig().isUseGroupAccount()) {
				List<SWK_HDR> hdrList = getSubsidiaryCompanySlips(dtl.getSWK_DEN_NO(), dtl.getSWK_DEN_DATE());
				if (hdrList != null && !hdrList.isEmpty()) {
					for (SWK_HDR hdr : hdrList) {
						sa.cancelApproveSlip(convertToSlipDen(hdr), isUseFieldApprove);
					}
				}

			}

			afterSlipList.add(dtl);
		}

		return afterSlipList;
	}

	/**
	 * �`�[�����F�i���ꏳ�F�j����
	 * 
	 * @param slipList ���F����`�[�̃��X�g
	 * @throws TException
	 */
	public List<SlipDen> approveSlipForFieldState(List<SlipDen> slipList) throws TException {

		// ���F�Ј�
		Employee employee = getUser().getEmployee();

		// �`�[�֘A�e�[�u�����b�N
		lockSlipTable();

		// ���F��̓`�[�ꗗ
		List<SlipDen> afterSlipList = new ArrayList<SlipDen>();

		// �Ώۓ`�[����������
		for (SlipDen dtl : slipList) {
			// ���F�r�W�l�X���W�b�N�擾
			SlipLogic slipLogic = getSlipLogic(dtl.getSWK_DEN_SYU());
			SlipApprove sa = slipLogic.getSlipApprove();

			// ���F
			sa.approveSlipForFieldState(dtl, employee);

			// �t�ւ�����ꍇ�A�t�֐�̓`�[�������ɏ��F����B
			if (getCompany().getAccountConfig().isUseGroupAccount()) {
				List<SWK_HDR> hdrList = getSubsidiaryCompanySlips(dtl.getSWK_DEN_NO(), dtl.getSWK_DEN_DATE());
				if (hdrList != null && !hdrList.isEmpty()) {
					for (SWK_HDR hdr : hdrList) {
						sa.approveSlipForFieldState(convertToSlipDen(hdr), employee);
					}
				}

			}

			afterSlipList.add(dtl);
		}

		return afterSlipList;
	}

	/**
	 * �`�[��۔F�i����۔F�j����
	 * 
	 * @param slipList �۔F����`�[�̃��X�g
	 * @return �۔F��̓`�[
	 * @throws TException
	 */
	public List<SlipDen> denySlipForFieldState(List<SlipDen> slipList) throws TException {

		// �`�[�֘A�e�[�u�����b�N
		lockSlipTable();

		// �۔F��̓`�[�ꗗ
		List<SlipDen> afterSlipList = new ArrayList<SlipDen>();

		// �Ώۓ`�[����������
		for (SlipDen dtl : slipList) {
			// �۔F�r�W�l�X���W�b�N�擾
			SlipLogic slipLogic = getSlipLogic(dtl.getSWK_DEN_SYU());
			SlipApprove sa = slipLogic.getSlipApprove();

			// �۔F
			sa.denySlipForFieldState(dtl);

			// �t�ւ�����ꍇ�A�t�֐�̓`�[�������ɔ۔F����B
			if (getCompany().getAccountConfig().isUseGroupAccount()) {
				List<SWK_HDR> hdrList = getSubsidiaryCompanySlips(dtl.getSWK_DEN_NO(), dtl.getSWK_DEN_DATE());
				if (hdrList != null && !hdrList.isEmpty()) {
					for (SWK_HDR hdr : hdrList) {
						sa.denySlipForFieldState(convertToSlipDen(hdr));
					}
				}

			}

			afterSlipList.add(dtl);
		}

		return afterSlipList;
	}

	/**
	 * �`�[���F�i���ꏳ�F�j��������
	 * 
	 * @param slipList ���F���������`�[�̃��X�g
	 * @return ���F�����̓`�[
	 * @throws TException
	 */
	public List<SlipDen> cancelApproveSlipForFieldState(List<SlipDen> slipList) throws TException {

		// ���ꏳ�F���g����
		boolean isUseFieldApprove = getCompany().getAccountConfig().isUseFieldApprove();

		// �`�[�֘A�e�[�u�����b�N
		lockSlipTable();

		// ���F�����̓`�[�ꗗ
		List<SlipDen> afterSlipList = new ArrayList<SlipDen>();

		// �Ώۓ`�[����������
		for (SlipDen dtl : slipList) {
			// ���F����r�W�l�X���W�b�N�擾
			SlipLogic slipLogic = getSlipLogic(dtl.getSWK_DEN_SYU());
			SlipApprove sa = slipLogic.getSlipApprove();

			// ���F���
			sa.cancelApproveSlipForFieldState(dtl, isUseFieldApprove);

			// �t�ւ�����ꍇ�A�t�֐�̓`�[�������ɏ��F�������B
			if (getCompany().getAccountConfig().isUseGroupAccount()) {
				List<SWK_HDR> hdrList = getSubsidiaryCompanySlips(dtl.getSWK_DEN_NO(), dtl.getSWK_DEN_DATE());
				if (hdrList != null && !hdrList.isEmpty()) {
					for (SWK_HDR hdr : hdrList) {
						sa.cancelApproveSlipForFieldState(convertToSlipDen(hdr), isUseFieldApprove);
					}
				}

			}

			afterSlipList.add(dtl);
		}

		return afterSlipList;
	}

	/**
	 * �`�[�֘A�e�[�u�����b�N
	 * 
	 * @throws TException
	 */
	public void lockSlipTable() throws TException {

		Connection conn = DBUtil.getConnection();

		// �e�[�u�����b�N�ɂ�葼�g�����U�N�V�������u���b�N
		try {
			DBUtil.execute(conn, "LOCK TABLE GL_SWK_HDR IN SHARE ROW EXCLUSIVE MODE NOWAIT");
			DBUtil.execute(conn, "LOCK TABLE AP_SWK_HDR IN SHARE ROW EXCLUSIVE MODE NOWAIT");
			DBUtil.execute(conn, "LOCK TABLE AR_SWK_HDR IN SHARE ROW EXCLUSIVE MODE NOWAIT");
			DBUtil.execute(conn, "LOCK TABLE SWK_DTL    IN SHARE ROW EXCLUSIVE MODE NOWAIT");
		} catch (TException e) {
			throw new TException("W01133");
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * �`�[���W�b�N�̎擾
	 * 
	 * @param slipType �`�[���
	 * @return �`�[���W�b�N
	 */
	public SlipLogic getSlipLogic(String slipType) {
		SlipLogicFactory slipLogicFactory = (SlipLogicFactory) getComponent(SlipLogicFactory.class);
		return slipLogicFactory.getLogic(slipType);
	}

	/**
	 * �`�[���W�b�N�̎擾
	 * 
	 * @param slipType �`�[���
	 * @param dataType
	 * @return �`�[���W�b�N
	 */
	public SlipLogic getSlipLogic(String slipType, String dataType) {
		SlipLogicFactory slipLogicFactory = (SlipLogicFactory) getComponent(SlipLogicFactory.class);
		return slipLogicFactory.getLogic(slipType, dataType);
	}

	/**
	 * �q��Ђ̓`�[��Ԃ�
	 * 
	 * @param slipNo
	 * @param slipDate
	 * @return �q��Ђ̓`�[
	 */
	protected List<SWK_HDR> getSubsidiaryCompanySlips(String slipNo, Date slipDate) {

		SlipCondition condition = new SlipCondition();
		condition.setGroupAccountDivision(1);
		condition.setSlipDateFrom(slipDate);
		condition.setSlipDateTo(slipDate);
		condition.setSlipNo(slipNo);

		List<SWK_HDR> hdrList = getHeader(condition);
		return hdrList;
	}

	/**
	 * �`�[���ׂ��\�z����.
	 * 
	 * @param slip �`�[
	 * @throws TException
	 */
	public Slip setupDetails(Slip slip) throws TException {
		// �����ێ�Entity�Ȃǂ�ݒ�.
		SlipLogic logic = getSlipLogic(slip.getSlipType());
		logic.setupDetails(slip);

		return slip;
	}

	/**
	 * �`�[���ׂ��\�z����.
	 * 
	 * @param slipList �����`�[��ʑz��
	 * @param includeBalance true:AP/AR/BS�c���Z�b�g�A�b�v
	 * @throws TException
	 */
	public List<Slip> setupDetails(List<Slip> slipList, boolean includeBalance) throws TException {

		if (slipList == null || slipList.isEmpty()) {
			return slipList;
		}

		// �����ێ�Entity�Ȃǂ�ݒ�.
		SlipLogic logic = getSlipLogic(slipList.get(0).getSlipType());
		logic.setupDetails(slipList, includeBalance);

		return slipList;
	}

	/**
	 * �`�[���ׂ��\�z����<br>
	 * ���ڐ���Ɋ֌W�̂Ȃ��Ǘ��P�`�Ǘ��U��A���������Z�b�g<br>
	 * �܂��A����Ŋz��M�݋��z����̏ꍇ���̒l���₤
	 * 
	 * @param slip �`�[
	 * @throws TException
	 */
	public Slip setupDetailsOptional(Slip slip) throws TException {
		return setupDetailsOptional(slip, true);
	}

	/**
	 * �`�[���ׂ��\�z����<br>
	 * ���ڐ���Ɋ֌W�̂Ȃ��Ǘ��P�`�Ǘ��U��A���������Z�b�g<br>
	 * �܂��A�t���O�ɂ���ď���Ŋz��M�݋��z����̏ꍇ���̒l���₤
	 * 
	 * @param slip
	 * @param recalc �K�v�ȏꍇ�̂ݍČv�Z
	 * @return �`�[
	 * @throws TException
	 */
	public Slip setupDetailsOptional(Slip slip, boolean recalc) throws TException {
		return setupDetailsOptional(slip, recalc, true);
	}

	/**
	 * �`�[���ׂ��\�z����<br>
	 * ���ڐ���Ɋ֌W�̂Ȃ��Ǘ��P�`�Ǘ��U��A���������Z�b�g<br>
	 * �܂��A�t���O�ɂ���ď���Ŋz��M�݋��z����̏ꍇ���̒l���₤
	 * 
	 * @param slip
	 * @param recalc �K�v�ȏꍇ�̂ݍČv�Z
	 * @param includeBalance true:AP/AR/BS�c���Z�b�g�A�b�v
	 * @return �`�[
	 * @throws TException
	 */
	public Slip setupDetailsOptional(Slip slip, boolean recalc, boolean includeBalance) throws TException {
		try {
			// �����ێ�Entity�Ȃǂ�ݒ�.
			SlipLogic logic = getSlipLogic(slip.getSlipType());
			logic.setupDetailsOptional(slip, recalc, includeBalance);

			return slip;
		} catch (TException e) {
			throw new TException(e);
		}
	}

	/**
	 * �`�[���ׂ��\�z����<br>
	 * ���ڐ���Ɋ֌W�̂Ȃ��Ǘ��P�`�Ǘ��U��A���������Z�b�g<br>
	 * �܂��A�t���O�ɂ���ď���Ŋz��M�݋��z����̏ꍇ���̒l���₤
	 * 
	 * @param slipList �����`�[��ʑz��
	 * @param recalc �K�v�ȏꍇ�̂ݍČv�Z
	 * @param includeBalance true:AP/AR/BS�c���Z�b�g�A�b�v
	 * @return �`�[
	 * @throws TException
	 */
	public List<Slip> setupDetailsOptional(List<Slip> slipList, boolean recalc, boolean includeBalance)
		throws TException {
		try {

			if (slipList == null || slipList.isEmpty()) {
				return slipList;
			}

			// �����ێ�Entity�Ȃǂ�ݒ�.
			// ���ӁF���ׂē����`�[��ʑz��
			SlipLogic logic = getSlipLogic(slipList.get(0).getSlipType());
			logic.setupDetailsOptional(slipList, recalc, includeBalance);

			return slipList;
		} catch (TException e) {
			throw new TException(e);
		}
	}

	/**
	 * �����d��Ȗڂ̎擾
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param types �Ȗڐ���敪
	 * @return �����d��Ȗ�
	 * @throws TException
	 */
	public AutoJornalAccount getAutoJornalAccount(String companyCode, AutoJornalAccountType types) throws TException {

		AutoJornalAccount entity = getAutoJornalAccount(companyCode, types.value);
		return entity;
	}

	/**
	 * �����d��Ȗڂ̎擾
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param type �Ȗڐ���敪
	 * @return �����d��Ȗ�
	 * @throws TException
	 */
	public AutoJornalAccount getAutoJornalAccount(String companyCode, int type) throws TException {

		List<AutoJornalAccount> list = getAutoJornalAccounts(companyCode, type);

		if (list.isEmpty()) {
			return null;
		}

		return list.get(0);
	}

	/**
	 * �����d��Ȗڂ̎擾(�����w��)
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param types �Ȗڐ���敪
	 * @return �����d��Ȗ�
	 * @throws TException
	 */
	public List<AutoJornalAccount> getAutoJornalAccounts(String companyCode, AutoJornalAccountType... types)
		throws TException {

		int[] kinds = new int[types.length];
		for (int i = 0; i < types.length; i++) {
			kinds[i] = types[i].value;
		}

		List<AutoJornalAccount> list = getAutoJornalAccounts(companyCode, kinds);

		return list;
	}

	/**
	 * �����d��Ȗڂ̎擾(�����w��)
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param types �Ȗڐ���敪
	 * @return �����d��Ȗ�
	 * @throws TException
	 */
	public List<AutoJornalAccount> getAutoJornalAccounts(String companyCode, int... types) throws TException {

		Connection conn = DBUtil.getConnection();
		Statement statement = null;

		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ").append("\n");
			sql.append("  skmk.KAI_CODE, ").append("\n");
			sql.append("  skmk.KMK_CNT, ").append("\n");
			sql.append("  skmk.KMK_CNT_NAME, ").append("\n");
			sql.append("  skmk.KMK_CODE, ").append("\n");
			sql.append("  skmk.HKM_CODE, ").append("\n");
			sql.append("  skmk.UKM_CODE, ").append("\n");
			sql.append("  skmk.DEP_CODE, ").append("\n");
			sql.append("  dep.DEP_NAME, ").append("\n");
			sql.append("  dep.DEP_NAME_S, ").append("\n");
			sql.append("  kmk.KMK_NAME, ").append("\n");
			sql.append("  kmk.KMK_NAME_S, ").append("\n");
			sql.append("  hkm.HKM_NAME, ").append("\n");
			sql.append("  hkm.HKM_NAME_S, ").append("\n");
			sql.append("  ukm.UKM_NAME, ").append("\n");
			sql.append("  ukm.UKM_NAME_S ").append("\n");
			sql.append("FROM SWK_KMK_MST skmk").append("\n");
			sql.append(" LEFT OUTER JOIN BMN_MST dep ").append("\n");
			sql.append("   ON skmk.KAI_CODE = dep.KAI_CODE ").append("\n");
			sql.append("  AND skmk.DEP_CODE = dep.DEP_CODE ").append("\n");
			sql.append(" LEFT OUTER JOIN KMK_MST kmk ").append("\n");
			sql.append("   ON skmk.KAI_CODE = kmk.KAI_CODE ").append("\n");
			sql.append("  AND skmk.KMK_CODE = kmk.KMK_CODE ").append("\n");
			sql.append(" LEFT OUTER JOIN HKM_MST hkm ").append("\n");
			sql.append("   ON skmk.KAI_CODE = hkm.KAI_CODE ").append("\n");
			sql.append("  AND skmk.KMK_CODE = hkm.KMK_CODE ").append("\n");
			sql.append("  AND skmk.HKM_CODE = hkm.HKM_CODE ").append("\n");
			sql.append(" LEFT OUTER JOIN UKM_MST ukm ").append("\n");
			sql.append("   ON skmk.KAI_CODE = ukm.KAI_CODE ").append("\n");
			sql.append("  AND skmk.KMK_CODE = ukm.KMK_CODE ").append("\n");
			sql.append("  AND skmk.HKM_CODE = ukm.HKM_CODE ").append("\n");
			sql.append("  AND skmk.UKM_CODE = ukm.UKM_CODE ").append("\n");
			sql.append("WHERE skmk.KAI_CODE = " + SQLUtil.getParam(companyCode)).append("\n");
			sql.append("  AND skmk.KMK_CNT IN " + SQLUtil.getIntInStatement(types));
			sql.append("  AND skmk.KMK_CODE IS NOT NULL ");

			statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql.toString());

			List<AutoJornalAccount> list = new ArrayList<AutoJornalAccount>();

			while (rs.next()) {
				list.add(mappingAutoJornalAccount(rs));
			}

			DBUtil.close(rs);

			return list;

		} catch (Exception e) {
			throw new TException(e);

		} finally {
			DBUtil.close(statement);
			DBUtil.close(conn);
		}

	}

	/**
	 * �����d��Ȗ� O/R�}�b�s���O
	 * 
	 * @param rs
	 * @return �����d��Ȗ�
	 * @throws Exception
	 */
	protected AutoJornalAccount mappingAutoJornalAccount(ResultSet rs) throws Exception {
		AutoJornalAccount bean = new AutoJornalAccount();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setKind(rs.getInt("KMK_CNT"));
		bean.setKindName(rs.getString("KMK_CNT_NAME"));
		bean.setDepertmentCode(rs.getString("DEP_CODE"));
		bean.setDepertmentName(rs.getString("DEP_NAME"));
		bean.setDepertmentNames(rs.getString("DEP_NAME_S"));
		bean.setItemCode(rs.getString("KMK_CODE"));
		bean.setItemName(rs.getString("KMK_NAME"));
		bean.setItemNames(rs.getString("KMK_NAME_S"));
		bean.setSubItemCode(rs.getString("HKM_CODE"));
		bean.setSubItemName(rs.getString("HKM_NAME"));
		bean.setSubItemNames(rs.getString("HKM_NAME_S"));
		bean.setDetailItemCode(rs.getString("UKM_CODE"));
		bean.setDetailItemName(rs.getString("UKM_NAME"));
		bean.setDetailItemNames(rs.getString("UKM_NAME_S"));

		return bean;
	}

	/**
	 * �폜�`�[���X�g�f�[�^��Ԃ�
	 * 
	 * @param condition ��������
	 * @return DeleteSlipListGetterBook
	 * @throws TException
	 */
	public DeleteSlipListBook getDeletedSlipListBook(SlipCondition condition) throws TException {

		DeleteSlipListGetter dao = (DeleteSlipListGetter) getComponent(DeleteSlipListGetter.class);
		DeleteSlipListBook book = dao.get(condition);
		return book;

	}

	/**
	 * �폜�`�[���X�g�f�[�^(���[)��Ԃ�
	 * 
	 * @param condition ��������
	 * @return byte
	 * @throws TException
	 */
	public byte[] getDeletedSlipListReport(SlipCondition condition) throws TException {

		DeleteSlipListBook book = getDeletedSlipListBook(condition);
		if (book == null || book.isEmpty()) {
			return null;
		}

		DeleteSlipListLayout layout = new DeleteSlipListLayout(getUser().getLanguage());
		return layout.getReport(book);

	}

	/**
	 * �폜�`�[���X�g�f�[�^(�G�N�Z��)��Ԃ�
	 * 
	 * @param condition ��������
	 * @return byte
	 * @throws TException
	 */
	public byte[] getDeletedSlipListExcel(SlipCondition condition) throws TException {

		DeleteSlipListBook book = getDeletedSlipListBook(condition);
		if (book == null || book.isEmpty()) {
			return null;
		}
		DeleteSlipListExcel layout = new DeleteSlipListExcel(getUser().getLanguage());
		return layout.getExcel(book);

	}

	/**
	 * �폜�����̍폜
	 * 
	 * @param slip
	 * @throws TException
	 */
	public void deleteDelHistory(Slip slip) throws TException {
		SlipLogic logic = getSlipLogic(slip.getSlipType());
		logic.deleteDelHistory(slip);
	}

	/**
	 * �폜�����̓o�^
	 * 
	 * @param slip
	 * @throws TException
	 */
	public void entryDelHistory(Slip slip) throws TException {
		SlipLogic logic = getSlipLogic(slip.getSlipType());

		List<Slip> list = new ArrayList<Slip>();
		list.add(slip);

		logic.entryDeleteHistory(list);
	}

	/**
	 * AP/AR�������̍X�V
	 * 
	 * @param slip
	 * @param kesiKbn
	 * @throws TException
	 */
	public void updateAPARInfo(Slip slip, int kesiKbn) throws TException {

		for (SWK_DTL dtl : slip.getDetails()) {

			if (Util.isNullOrEmpty(dtl.getSWK_APAR_DEN_NO())) {
				continue;
			}

			SQLCreator sql = new SQLCreator();
			sql.add(" UPDATE SWK_DTL ");
			sql.add(" SET    SWK_APAR_KESI_KBN = ? ", kesiKbn);
			sql.add("       ,SWK_APAR_DEN_NO   = ? ", dtl.getSWK_DEN_NO());
			sql.add("       ,SWK_APAR_GYO_NO   = ? ", dtl.getSWK_GYO_NO());
			sql.adt("       ,UPD_DATE          = ? ", getNow());
			sql.add("       ,PRG_ID            = ? ", getProgramCode());
			sql.add("       ,USR_ID            = ? ", getUserCode());
			sql.add(" WHERE  KAI_CODE   = ? ", dtl.getKAI_CODE());
			sql.add("   AND  SWK_DEN_NO = ? ", dtl.getSWK_APAR_DEN_NO());
			sql.add("   AND  SWK_GYO_NO = ? ", dtl.getSWK_APAR_GYO_NO());
			sql.add("   AND  SWK_APAR_DEN_NO IS NULL ");
			sql.add("   AND  SWK_BOOK_NO = 1 ");
			sql.add("   AND  SWK_ADJ_KBN IN (0, 1) ");
			DBUtil.execute(sql);

		}
	}

	/**
	 * AP/AR�����d��̕�������
	 * 
	 * @param condition
	 * @throws TException
	 */
	public void restoreAPAR(SlipCondition condition) throws TException {

		SQLCreator sql = new SQLCreator();
		sql.add(" UPDATE SWK_DTL ");
		sql.add(" SET    SWK_APAR_KESI_KBN = ? ", SWK_DTL.APAR_KESI_KBN.NOMAL);
		sql.add("       ,SWK_APAR_DEN_NO   = NULL ");
		sql.add("       ,SWK_APAR_GYO_NO   = NULL ");
		sql.add(" WHERE  KAI_CODE        = ? ", condition.getCompanyCode());
		sql.add("   AND  SWK_APAR_DEN_NO = ? ", condition.getSlipNo());

		DBUtil.execute(sql);
	}

	/**
	 * @see jp.co.ais.trans2.model.slip.SlipManager#getDetailExcel(java.util.List)
	 */
	public byte[] getDetailExcel(List<SWK_DTL> list) throws TException {
		SlipDetailExternalExcel excel = getExcelClass();
		try {
			return excel.getExcel(list);
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �G�N�Z�����׃��X�g��bean�ɕϊ����ĕԋp
	 * 
	 * @param file
	 * @param slipType
	 * @return ����bean���X�g
	 * @throws TException
	 */
	public List<SWK_DTL> convertExcelToDetails(File file, SlipType slipType) throws TException {
		SlipDetailExternalExcel excel = getExcelClass();
		try {
			CompanyManager comMana = get(CompanyManager.class);
			CompanySearchCondition comCon = new CompanySearchCondition();
			List<Company> companys = comMana.get(comCon);
			List<SWK_DTL> dtlList = excel.convertToEntityList(file, companys);
			for (SWK_DTL dtl : dtlList) {
				if (Util.isNullOrEmpty(dtl.getSWK_K_KAI_CODE())) {
					dtl.setSWK_K_KAI_CODE(getCompanyCode());
				}
				if (Util.isNullOrEmpty(dtl.getSWK_KMK_CODE())) {
					dtl.setSWK_HKM_CODE(null);
					dtl.setSWK_UKM_CODE(null);
				}
				if (Util.isNullOrEmpty(dtl.getSWK_HKM_CODE())) {
					dtl.setSWK_UKM_CODE(null);
				}
				if (Util.isNullOrEmpty(dtl.getSWK_ZEI_CODE())) {
					// ����Ŕ񑶍ݎ���ې�
					dtl.setSWK_ZEI_KBN(ZEI_KBN.TAX_NONE);
					dtl.setSWK_ZEI_KIN(BigDecimal.ZERO);
					dtl.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);
				}
			}
			Slip slip = new Slip();
			slip.setSlipType(slipType);
			slip.setDetails(dtlList);
			slip = setupDetailsOptional(slip);
			if (slip == null) {
				return null;
			}
			return slip.getDetails();
		} catch (TException e) {
			throw e;
		} catch (Exception e) {
			ServerLogger.error("error", e);
			// �t�@�C���̓ǂݍ��݂Ɏ��s���܂����B
			throw new TException("E00021");
		}
	}

	/**
	 * �G�N�Z�����ד��o�̓N���X���擾����
	 * 
	 * @return �G�N�Z�����ד��o�̓N���X
	 */
	public SlipDetailExternalExcel getExcelClass() {
		return new SlipDetailExternalExcel(getUser().getLanguage(), getCompany());
	}

	/**
	 * <<<<<<< HEAD �`�[�G�N�X�|�[�g�G�N�Z���𐶐����擾(�t�֎d��̏ꍇ�A���ЁE�����З����̓`�[���o�͂���B)
	 * 
	 * @param slipNoList
	 * @return ���׃��X�g�G�N�Z��
	 * @throws TException
	 */
	public byte[] getExportSlipExcelBySlipNos(List<String> slipNoList) throws TException {
		// �w��̓`�[�𒊏o
		SlipCondition condition = new SlipCondition();

		if (!getCompany().getAccountConfig().isUseGroupAccount()) {
			condition.setCompanyCode(getCompanyCode());
		}

		condition.setSlipNoList(slipNoList.toArray(new String[slipNoList.size()]));

		// �t�֎d��ɑΉ�
		condition.addOrder(SlipCondition.SORT_SLIP_DATE);
		condition.addOrder(SlipCondition.SORT_SLIP_NO);
		condition.addOrder(SlipCondition.SORT_COMPANY_CODE);
		condition.addOrder(SlipCondition.SORT_LINE_NO);

		List<SlipBooks> list = getSlipBooks(condition);

		if (list == null || list.isEmpty()) {
			return null;
		}

		List<Slip> slipList = getSlipList(list);

		// �G�N�X�|�[�g���וϊ����s�ԍ��̍Đ���
		List<SlipInstructionDtl> dtlList = convertToExportData(slipList);

		// �G�N�X�|�[�g�G�N�Z�� ����
		SlipInstructionExternalExcel excel = getInstructionExcelClass();
		try {
			return excel.getExcel(dtlList);
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �`�[�G�N�X�|�[�g�G�N�Z���𐶐����擾
	 * 
	 * @param companyCode
	 * @param slipNoList
	 * @return ���׃��X�g�G�N�Z��
	 * @throws TException
	 */
	public byte[] getExportSlipExcel(String companyCode, List<String> slipNoList) throws TException {

		// �w��̓`�[�𒊏o
		SlipCondition condition = new SlipCondition();
		condition.setCompanyCode(companyCode);
		condition.setSlipNoList(slipNoList.toArray(new String[slipNoList.size()]));

		List<SlipBooks> list = getSlipBooks(condition);

		if (list == null || list.isEmpty()) {
			return null;
		}

		List<Slip> slipList = getSlipList(list);

		// �G�N�X�|�[�g���וϊ����s�ԍ��̍Đ���
		List<SlipInstructionDtl> dtlList = convertToExportData(slipList);

		// �G�N�X�|�[�g�G�N�Z�� ����
		SlipInstructionExternalExcel excel = getInstructionExcelClass();
		try {
			return excel.getExcel(dtlList);
		} catch (Exception e) {
			throw new TException(e);
		}

	}

	/**
	 * @param slipList
	 * @return List<Slip>
	 * @throws TException
	 */
	public List<SlipInstructionDtl> convertToExportData(List<Slip> slipList) throws TException {

		List<SlipInstructionDtl> list = new ArrayList<SlipInstructionDtl>();
		try {

			// �����d��敪�iSWK_AUTO_KBN�j���u1�v�̏ꍇ
			// �ȖڃR�[�h�ŉȖڃ}�X�^�}�X�^���Q�Ƃ��A���L�����̏ꍇ �s�ԍ��Ɂu1�v��ݒ�
			// GL�Ȗڐ���敪=�u04�v�܂��́AAP�Ȗڐ���敪���u01�v�܂��́AAR�Ȗڐ���敪���u01�v
			// ��L�ȊO �s�ԍ� �{ 1 ��ݒ�
			for (Slip slip : slipList) {
				String dataKbn = slip.getHeader().getSWK_DATA_KBN(); // �`�[��ʂ̔���p
				boolean isFirstRow = false; // 1�s�ڂ̔���p

				for (SWK_DTL detail : slip.getDetails()) {

					if (SlipKind.get(dataKbn) == SlipKind.TRANSFER) {
						// �U��/�U�߂͂��̂܂܃Z�b�g
						continue;
					}

					Item item = getItem(detail.getKAI_CODE(), detail.getSWK_KMK_CODE());
					// �u�����N�͏����I��
					if (item == null) {
						// �Ȗڏ�񂪂���܂���
						new TException("I00484");
					}

					if (SlipKind.get(dataKbn) == SlipKind.INPUT_CASH_FLOW
						|| SlipKind.get(dataKbn) == SlipKind.OUTPUT_CASH_FLOW) {
						// ����/�o���́A�����d��敪�iSWK_AUTO_KBN�j���u1�v����GL�Ȗڐ���敪=04:�����Ȗڂ̏ꍇ�ɍs�ԍ�1
						isFirstRow = (detail.isAutoDetail() && GLType.FUND == item.getGlType());

					} else if (SlipKind.get(dataKbn) == SlipKind.PURCHASE) {
						// ���v��́A�����d��敪�iSWK_AUTO_KBN�j���u1�v����AP�Ȗڐ���敪=01:���Ȗڂ̏ꍇ�ɍs�ԍ�1
						isFirstRow = (detail.isAutoDetail() && APType.DEBIT == item.getApType());

					} else if (SlipKind.get(dataKbn) == SlipKind.SALES) {
						// ���v��́A�����d��敪�iSWK_AUTO_KBN�j���u1�v����AR�Ȗڐ���敪=01:���Ȗڂ̏ꍇ�ɍs�ԍ�1
						isFirstRow = (detail.isAutoDetail() && ARType.AR == item.getArType());

					}

					if (isFirstRow) {
						// �s�ԍ�1
						list.add(mappingSlipExportInstruction(detail, slip.getHeader(), true));

					}
				}

				for (SWK_DTL detail : slip.getDetails()) {
					if (SlipKind.get(dataKbn) == SlipKind.TRANSFER) {
						// �U��/�U�߂͂��̂܂܃Z�b�g
						list.add(mappingSlipExportInstruction(detail, slip.getHeader(), false));
						continue;
					}

					Item item = getItem(detail.getKAI_CODE(), detail.getSWK_KMK_CODE());
					// �u�����N�͏����I��
					if (item == null) {
						// �Ȗڏ�񂪂���܂���
						new TException("I00484");
					}

					if (SlipKind.get(dataKbn) == SlipKind.INPUT_CASH_FLOW
						|| SlipKind.get(dataKbn) == SlipKind.OUTPUT_CASH_FLOW) {
						// ����/�o���́A�����d��敪�iSWK_AUTO_KBN�j���u1�v����GL�Ȗڐ���敪=04:�����Ȗڂ̏ꍇ�ɍs�ԍ�1
						isFirstRow = (detail.isAutoDetail() && GLType.FUND == item.getGlType());

					} else if (SlipKind.get(dataKbn) == SlipKind.PURCHASE) {
						// ���v��́A�����d��敪�iSWK_AUTO_KBN�j���u1�v����AP�Ȗڐ���敪=01:���Ȗڂ̏ꍇ�ɍs�ԍ�1
						isFirstRow = (detail.isAutoDetail() && APType.DEBIT == item.getApType());

					} else if (SlipKind.get(dataKbn) == SlipKind.SALES) {
						// ���v��́A�����d��敪�iSWK_AUTO_KBN�j���u1�v����AR�Ȗڐ���敪=01:���Ȗڂ̏ꍇ�ɍs�ԍ�1
						isFirstRow = (detail.isAutoDetail() && ARType.AR == item.getArType());

					}

					if (isFirstRow) {
						continue;
					} else {
						list.add(mappingSlipExportInstruction(detail, slip.getHeader(), false));
					}

				}

			}

			// �s�ԍ��̐U�蒼��
			String kaiCode = null;
			String denNo = null;
			int gyoNo = 0;
			for (SlipInstructionDtl dtl : list) {
				gyoNo++;
				// Key���ς�����珉����
				if (!Util.equals(kaiCode, dtl.getKAI_CODE()) || !Util.equals(denNo, dtl.getSWK_DEN_NO())) {
					gyoNo = 1;
					kaiCode = dtl.getKAI_CODE();
					denNo = dtl.getSWK_DEN_NO();
				}
				dtl.setSWK_GYO_NO(gyoNo);

			}

		} catch (Exception e) {
			throw new TException(e);
		}

		return list;
	}

	/**
	 * �Ȗڃ}�X�^���擾����
	 * 
	 * @param kaiCode
	 * @param kmkCode
	 * @return �Ȗڃ}�X�^
	 */
	public Item getItem(String kaiCode, String kmkCode) {

		String key = kaiCode + "<>" + kmkCode;

		if (itemMap.containsKey(key)) {
			return itemMap.get(key);
		}

		// �Ȗ�
		ItemManager itemMng = (ItemManager) getComponent(ItemManager.class);
		Item item = null;
		try {
			item = itemMng.getItem(kaiCode, kmkCode, null, null);
			itemMap.put(key, item);

		} catch (TException e) {
			throw new TRuntimeException(e);
		}
		return item;
	}

	/**
	 * �`�[�G�N�X�|�[�g�G�N�Z�� O/R�}�b�s���O
	 * 
	 * @param hdr
	 * @param detail
	 * @param isFirst
	 * @return �`�[�G�N�X�|�[�g�G�N�Z��
	 * @throws Exception
	 */
	public SlipInstructionDtl mappingSlipExportInstruction(SWK_DTL detail, SWK_HDR hdr, boolean isFirst)
		throws Exception {
		SlipInstructionDtl bean = new SlipInstructionDtl();

		bean.setKAI_CODE(detail.getKAI_CODE());
		bean.setSWK_DEN_DATE(DateUtil.toYMDPlainString(detail.getSWK_DEN_DATE()));
		bean.setSWK_DEN_NO(detail.getSWK_DEN_NO());
		if (isFirst) {
			// �w�b�_�[�s���ׂ�1�s��
			bean.setSWK_GYO_NO(1);
		} else {
			bean.setSWK_GYO_NO(detail.getSWK_GYO_NO());
		}
		bean.setSWK_UKE_DEP_CODE(hdr.getSWK_UKE_DEP_CODE());
		bean.setSWK_TEK_CODE(hdr.getSWK_TEK_CODE());
		bean.setSWK_TEK(hdr.getSWK_TEK());
		bean.setSWK_SYO_EMP_CODE(hdr.getSWK_SYO_EMP_CODE());
		bean.setSWK_SYO_DATE(DateUtil.toYMDPlainString(hdr.getSWK_SYO_DATE()));
		bean.setSWK_IRAI_EMP_CODE(hdr.getSWK_IRAI_EMP_CODE());
		bean.setSWK_IRAI_DEP_CODE(hdr.getSWK_IRAI_DEP_CODE());
		bean.setSWK_IRAI_DATE(DateUtil.toYMDPlainString(hdr.getSWK_IRAI_DATE()));
		bean.setSWK_SYS_KBN(hdr.getSWK_SYS_KBN());
		bean.setSWK_DEN_SYU(hdr.getSWK_DEN_SYU());
		bean.setSWK_UPD_KBN(hdr.getSWK_UPD_KBN());
		bean.setSWK_KSN_KBN(hdr.getSWK_KSN_KBN());
		bean.setSWK_KMK_CODE(detail.getSWK_KMK_CODE());
		bean.setSWK_HKM_CODE(detail.getSWK_HKM_CODE());
		bean.setSWK_UKM_CODE(detail.getSWK_UKM_CODE());
		bean.setSWK_DEP_CODE(detail.getSWK_DEP_CODE());
		bean.setSWK_TRI_CODE(detail.getSWK_TRI_CODE());
		bean.setSWK_EMP_CODE(detail.getSWK_EMP_CODE());
		bean.setSWK_CUR_CODE(detail.getSWK_CUR_CODE());
		bean.setSWK_CUR_RATE(detail.getSWK_CUR_RATE());
		if (DecimalUtil.isNullOrZero(bean.getSWK_CUR_RATE())) {
			// ZERO or NULL��������1���Z�b�g
			bean.setSWK_CUR_RATE(BigDecimal.ONE);
		}
		bean.setSWK_DC_KBN(detail.getSWK_DC_KBN());
		bean.setSWK_ZEI_KBN(detail.getSWK_ZEI_KBN());
		bean.setSWK_ZEI_KIN(detail.getSWK_ZEI_KIN());
		bean.setSWK_ZEI_CODE(detail.getSWK_ZEI_CODE());
		bean.setSWK_GYO_TEK_CODE(detail.getSWK_GYO_TEK_CODE());
		bean.setSWK_GYO_TEK(detail.getSWK_GYO_TEK());
		bean.setSWK_KNR_CODE_1(detail.getSWK_KNR_CODE_1());
		bean.setSWK_KNR_CODE_2(detail.getSWK_KNR_CODE_2());
		bean.setSWK_KNR_CODE_3(detail.getSWK_KNR_CODE_3());
		bean.setSWK_KNR_CODE_4(detail.getSWK_KNR_CODE_4());
		bean.setSWK_KNR_CODE_5(detail.getSWK_KNR_CODE_5());
		bean.setSWK_KNR_CODE_6(detail.getSWK_KNR_CODE_6());
		bean.setSWK_HM_1(detail.getSWK_HM_1());
		bean.setSWK_HM_2(detail.getSWK_HM_2());
		bean.setSWK_HM_3(detail.getSWK_HM_3());
		bean.setSWK_AUTO_KBN(detail.getSWK_AUTO_KBN());
		bean.setHAS_DATE(DateUtil.toYMDPlainString(detail.getHAS_DATE()));
		bean.setSWK_AT_KMK_CODE(detail.getSWK_AT_KMK_CODE());
		bean.setSWK_AT_HKM_CODE(detail.getSWK_AT_HKM_CODE());
		bean.setSWK_AT_UKM_CODE(detail.getSWK_AT_UKM_CODE());
		bean.setSWK_AT_DEP_CODE(detail.getSWK_AT_DEP_CODE());
		bean.setSWK_K_KAI_CODE(detail.getSWK_K_KAI_CODE());
		bean.setSWK_SEI_NO(detail.getSWK_SEI_NO());
		bean.setSWK_KIN(detail.getSWK_KIN());
		bean.setSWK_IN_KIN(detail.getSWK_IN_KIN());
		if (isFirst) {
			bean.setSWK_SIHA_KBN(hdr.getSWK_SIHA_KBN());
			bean.setSWK_SIHA_DATE(DateUtil.toYMDPlainString(hdr.getSWK_SIHA_DATE()));
			bean.setSWK_HOH_CODE(hdr.getSWK_HOH_CODE());
			bean.setSWK_HORYU_KBN(hdr.getSWK_HORYU_KBN());
			bean.setSWK_TJK_CODE(hdr.getSWK_TJK_CODE());
			bean.setSWK_AR_DATE(DateUtil.toYMDPlainString(hdr.getSWK_AR_DATE()));
			bean.setSWK_CBK_CODE(hdr.getSWK_CBK_CODE());
			bean.setSWK_TUKE_KBN(hdr.getSWK_TUKE_KBN());
		}
		bean.setSWK_IN_ZEI_KIN(detail.getSWK_IN_ZEI_KIN());

		return bean;

	}

	/**
	 * �G�N�Z�����ד��o�̓N���X���擾����
	 * 
	 * @return �G�N�Z�����ד��o�̓N���X
	 */
	public SlipInstructionExternalExcel getInstructionExcelClass() {
		return new SlipInstructionExternalExcel(getUser().getLanguage(), getCompany());
	}

	/**
	 * �`�[��ʃN���X���擾���邷��
	 */
	public Class getSlipPanel(String prgCode, String denSyuMst) {

		// �r�W�l�X���W�b�N�擾
		SlipPanelLogic slipPanelLogic = getSlipPanelLogic(denSyuMst);
		Class sc = slipPanelLogic.getSlipPanelClass(prgCode);

		return sc;
	}

	/**
	 * �`�[���W�b�N�̎擾
	 * 
	 * @param slipType �`�[���
	 * @return �`�[���W�b�N
	 */
	public SlipPanelLogic getSlipPanelLogic(String slipType) {
		SlipPanelLogicFactory SlipPanelLogicFactory = (SlipPanelLogicFactory) getComponent(SlipPanelLogicFactory.class);
		return SlipPanelLogicFactory.getLogic(slipType);
	}

	/**
	 * �`�[�ԍ�/�C���񐔂œ`�[�����݂��Ă��邩�`�F�b�N
	 * 
	 * @param kaiCode
	 * @param slipNo
	 * @param slipUpdCnt
	 * @throws TException
	 */
	public void checkSlipInfo(String kaiCode, String slipNo, int slipUpdCnt) throws TException {

		Connection conn = null;

		try {

			conn = DBUtil.getConnection();
			SQLCreator where = new SQLCreator();
			where.add("         AND    KAI_CODE = ? ", kaiCode);
			where.add("         AND    SWK_DEN_NO = ? ", slipNo);
			if (slipUpdCnt >= 0) {
				where.add("         AND    SWK_UPD_CNT = ? ", slipUpdCnt);
			}

			SQLCreator sql = new SQLCreator();
			sql.add(" SELECT SUM(CNT) ");
			sql.add(" FROM   (SELECT COUNT(*) cnt ");
			sql.add("         FROM   GL_SWK_HDR ");
			sql.add("         WHERE  1 = 1 ");
			sql.add(where.toSQL());
			sql.add("         UNION ALL ");
			sql.add("         SELECT COUNT(*) cnt ");
			sql.add("         FROM   AP_SWK_HDR ");
			sql.add("         WHERE  1 = 1 ");
			sql.add(where.toSQL());
			sql.add("         UNION ALL ");
			sql.add("         SELECT COUNT(*) cnt ");
			sql.add("         FROM   AR_SWK_HDR ");
			sql.add("         WHERE  1 = 1 ");
			sql.add(where.toSQL());
			sql.add("        ) s ");
			int count = DBUtil.selectOneInt(conn, sql.toSQL());
			if (count == 0) {
				// �w��̓`�[�͑��[���ōX�V����Ă��܂��B{0}
				throw new TRuntimeException("I00070", slipNo);
			}
		} catch (Exception e) {
			throw new TRuntimeException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * �ؓ����̍X�V
	 * 
	 * @param slip
	 * @param status
	 * @throws TException
	 */
	public void updateLMInfo(Slip slip, int status) throws TException {
		SWK_HDR hdr = slip.getHeader();
		SQLCreator sql = new SQLCreator();
		sql.add(" UPDATE GL_SWK_HDR");
		sql.add(" SET    SWK_CNF_STATUS = ? ", status);
		sql.add(" WHERE  KAI_CODE   = ? ", hdr.getKAI_CODE());
		sql.add("   AND  SWK_DEN_NO = ? ", hdr.getSWK_DEN_NO());
		sql.add("   AND  SWK_DEN_DATE = ? ", hdr.getSWK_DEN_DATE());

		DBUtil.execute(sql);

		sql = new SQLCreator();
		sql.add(" UPDATE AP_SWK_HDR");
		sql.add(" SET    SWK_CNF_STATUS = ? ", status);
		sql.add(" WHERE  KAI_CODE   = ? ", hdr.getKAI_CODE());
		sql.add("   AND  SWK_DEN_NO = ? ", hdr.getSWK_DEN_NO());
		sql.add("   AND  SWK_DEN_DATE = ? ", hdr.getSWK_DEN_DATE());

		DBUtil.execute(sql);

		sql = new SQLCreator();
		sql.add(" UPDATE AR_SWK_HDR");
		sql.add(" SET    SWK_CNF_STATUS = ? ", status);
		sql.add(" WHERE  KAI_CODE   = ? ", hdr.getKAI_CODE());
		sql.add("   AND  SWK_DEN_NO = ? ", hdr.getSWK_DEN_NO());
		sql.add("   AND  SWK_DEN_DATE = ? ", hdr.getSWK_DEN_DATE());

		DBUtil.execute(sql);

	}

	/**
	 * CM_FUND_DTL���̍폜
	 * 
	 * @param kaiCode
	 * @param slipDate
	 * @param slipNo
	 * @throws TException
	 */
	public void deleteCmFundInfo(String kaiCode, Date slipDate, String slipNo) throws TException {

		// �폜����
		VCreator sql = new VCreator();
		sql.add(" DELETE FROM CM_FUND_DTL ");
		sql.add(" WHERE 1 = 1 ");
		if (kaiCode != null) {
			sql.add(" AND   KAI_CODE     = ? ", kaiCode);
		}
		if (slipDate != null) {
			sql.add(" AND   KEY_DEN_DATE = ? ", slipDate);
		}
		sql.add(" AND   KEY_DEN_NO   = ? ", slipNo);
		sql.add(" AND   DATA_TYPE    = 0 ");
		DBUtil.execute(sql);

		sql = new VCreator();
		sql.add(" DELETE FROM CM_FUND_DTL ");
		sql.add(" WHERE 1 = 1 ");
		if (kaiCode != null) {
			sql.add(" AND   KAI_CODE     = ? ", kaiCode);
		}
		if (slipDate != null) {
			sql.add(" AND   KEY_DEN_DATE = ? ", slipDate);
		}
		sql.add(" AND   KEY_DEN_NO   = ? ", slipNo);
		sql.add(" AND   DATA_TYPE    = 1 ");
		DBUtil.execute(sql);

	}

	/**
	 * CM_FUND_DTL���̍X�V
	 * 
	 * @param slip
	 * @throws TException
	 */
	public void entryCmFundInfo(Slip slip) throws TException {

		String kaiCode = slip.getCompanyCode();
		Date slipDate = slip.getSlipDate();
		String slipNo = slip.getSlipNo();

		SWK_HDR hdr = slip.getHeader();

		CompanyManager cmpMn = get(CompanyManager.class);
		Company cmp = cmpMn.get(kaiCode);
		String keyCurCode = cmp.getAccountConfig().getKeyCurrency().getCode();

		BigDecimal inKin = BigDecimal.ZERO;
		BigDecimal kin = BigDecimal.ZERO;
		String tek = hdr.getSWK_TEK();
		String curCode = null;
		Date baseDate = null;

		RateManager rateMn = get(RateManager.class);
		BankAccount bnkAcc = null;
		// ��s�����擾
		BankAccountManager baMn = get(BankAccountManager.class);
		BankAccountSearchCondition baCondition = null;

		CurrencyManager curMn = get(CurrencyManager.class);
		CurrencySearchCondition curCondition = null;

		VCreator sql = null;

		String denSyuCode = hdr.getSWK_DEN_SYU();
		String denSyuName = hdr.getSWK_DEN_SYU_NAME();
		if (Util.isNullOrEmpty(denSyuName)) {
			SlipTypeManager stMn = get(SlipTypeManager.class);
			SlipType slipType = stMn.get(kaiCode, denSyuCode);
			denSyuName = slipType == null ? "" : slipType.getName();

		}
		String sysKbn = hdr.getSWK_SYS_KBN();

		CustomerManager cusMn = get(CustomerManager.class);
		CustomerSearchCondition cusCnd = null;
		DepartmentManager depMn = get(DepartmentManager.class);
		DepartmentSearchCondition depCnd = null;

		// AP/AR�̓w�b�_�[�o�^
		// ��s�����R�[�h<>NULL���t��=0�A�f�[�^�敪23,31,32 �� APAR�Ώ�
		if (!Util.isNullOrEmpty(hdr.getSWK_CBK_CODE()) && hdr.getSWK_TUKE_KBN() == 0 //
			&& (Util.equals(hdr.getSWK_DATA_KBN(), "23") || //
				Util.equals(hdr.getSWK_DATA_KBN(), "31") || //
				Util.equals(hdr.getSWK_DATA_KBN(), "32"))) {

			// ���̎��O�Z�b�g
			String triName = hdr.getSWK_TRI_NAME();
			if (!Util.isNullOrEmpty(hdr.getSWK_TRI_CODE()) && Util.isNullOrEmpty(triName)) {
				cusCnd = new CustomerSearchCondition();
				cusCnd.setCompanyCode(kaiCode);
				cusCnd.setCode(hdr.getSWK_TRI_CODE());
				List<Customer> cusList = cusMn.get(cusCnd);
				if (cusList.size() > 0) {
					triName = cusList.get(0).getName();
				}
			}
			String knrName = hdr.getSWK_DEP_NAME();
			if (!Util.isNullOrEmpty(hdr.getSWK_DEP_CODE()) && Util.isNullOrEmpty(knrName)) {
				depCnd = new DepartmentSearchCondition();
				depCnd.setCompanyCode(kaiCode);
				depCnd.setCode(hdr.getSWK_DEP_CODE());
				List<Department> depList = depMn.get(depCnd);
				if (depList.size() > 0) {
					knrName = depList.get(0).getName();
				}
			}
			// �w�b�_�[�����ʉ݂���ʉݎ擾
			curCode = hdr.getSWK_CBK_CODE();
			// ��s�����ʉݎ擾
			if (!Util.isNullOrEmpty(curCode)) {
				baCondition = new BankAccountSearchCondition();
				baCondition.setCompanyCode(kaiCode);
				baCondition.setCode(curCode);
				List<BankAccount> list = baMn.get(baCondition);
				if (list.size() > 0) {
					bnkAcc = list.get(0);
				}
			}
			curCode = bnkAcc != null ? bnkAcc.getCurrencyCode() : hdr.getSWK_CUR_CODE();

			if (Util.equals(hdr.getSWK_DATA_KBN(), "31") || Util.equals(hdr.getSWK_DATA_KBN(), "32")) {
				// AR�n�͓����\���
				baseDate = hdr.getSWK_AR_DATE() != null ? hdr.getSWK_AR_DATE() : slipDate;
				inKin = hdr.getSWK_IN_KIN();
				kin = hdr.getSWK_KIN();
			} else {
				// AP�n�͎x���\���
				baseDate = hdr.getSWK_SIHA_DATE() != null ? hdr.getSWK_SIHA_DATE() : slipDate;
				// �x���͋��z�������]
				inKin = DecimalUtil.avoidNull(hdr.getSWK_IN_SIHA_KIN()).negate();
				kin = DecimalUtil.avoidNull(hdr.getSWK_SIHA_KIN()).negate();
			}

			if (Util.equals(curCode, hdr.getSWK_CUR_CODE())) {
				// �����ʉ݂Ǝ���ʉ݂����� �͂��̂܂�

			} else if (Util.equals(curCode, keyCurCode)) {
				// �����ʉ݂Ɗ�ʉ݂�����
				inKin = kin;
			} else {
				// �ȊO�͌v�Z��苁�߂�
				BigDecimal rate = rateMn.getRate(curCode, baseDate);

				Currency currency = null;
				curCondition = new CurrencySearchCondition();
				curCondition.setCompanyCode(kaiCode);
				curCondition.setCode(curCode);
				List<Currency> curList = curMn.get(curCondition);
				if (curList.size() > 0) {
					currency = curList.get(0);
				}

				inKin = convertToForeign(kin, rate, currency.getRatePow(), currency.getDecimalPoint(), cmp);
			}

			// �w�b�_�[�o�^
			sql = new VCreator();
			sql.add(" INSERT INTO CM_FUND_DTL (");
			sql.add("     KAI_CODE ");
			sql.add("    ,DEN_DATE ");
			sql.add("    ,DEN_NO ");
			sql.add("    ,TRI_CODE ");
			sql.add("    ,TRI_NAME ");
			sql.add("    ,KNR_CODE ");
			sql.add("    ,KNR_NAME ");
			sql.add("    ,TEK ");
			sql.add("    ,DEN_SYU_CODE ");
			sql.add("    ,DEN_SYU_NAME ");
			sql.add("    ,CUR_CODE ");
			sql.add("    ,ZAN_KIN ");
			sql.add("    ,ZAN_IN_KIN ");
			sql.add("    ,CBK_CODE ");
			sql.add("    ,DATA_KBN ");
			sql.add("    ,SYS_KBN ");
			sql.add("    ,DATA_TYPE ");
			sql.add("    ,KEY_DEN_DATE ");
			sql.add("    ,KEY_DEN_NO ");
			sql.add("    ,INP_DATE ");
			sql.add("    ,UPD_DATE ");
			sql.add("    ,PRG_ID ");
			sql.add("    ,USR_ID ");
			sql.add(" ) VALUES ( ");
			sql.add("     ? ", kaiCode);
			sql.add("    ,? ", baseDate);
			sql.add("    ,? ", slipNo);
			sql.add("    ,? ", hdr.getSWK_TRI_CODE());
			sql.add("    ,? ", triName);
			sql.add("    ,? ", hdr.getSWK_DEP_CODE());
			sql.add("    ,? ", knrName);
			sql.add("    ,? ", tek);
			sql.add("    ,? ", denSyuCode);
			sql.add("    ,? ", denSyuName);
			sql.add("    ,? ", curCode);
			sql.add("    ,? ", kin);
			sql.add("    ,? ", inKin);
			sql.add("    ,? ", hdr.getSWK_CBK_CODE());
			sql.add("    ,0 "); // DATA_KBN
			sql.add("    ,? ", hdr.getSWK_SYS_KBN());
			sql.add("    ,0 "); // DATA_TYPE
			sql.add("    ,? ", slipDate);
			sql.add("    ,? ", slipNo);
			sql.adt("    ,? ", Util.isNullOrEmpty(hdr.getSWK_INP_DATE()) ? hdr.getINP_DATE() : hdr.getSWK_INP_DATE());
			sql.adt("    ,? ", hdr.getUPD_DATE());
			sql.add("    ,? ", hdr.getPRG_ID());
			sql.add("    ,? ", hdr.getUSR_ID());
			sql.add(" ) ");

			DBUtil.execute(sql);
		}

		// ���׋��ʕ��\�z
		VCreator sqlIns = new VCreator();
		sqlIns.add(" INSERT INTO CM_FUND_DTL ( ");
		sqlIns.add("      KAI_CODE ");
		sqlIns.add("     ,DEN_DATE ");
		sqlIns.add("     ,DEN_NO ");
		sqlIns.add("     ,TRI_CODE ");
		sqlIns.add("     ,TRI_NAME ");
		sqlIns.add("     ,KNR_CODE ");
		sqlIns.add("     ,KNR_NAME ");
		sqlIns.add("     ,TEK ");
		sqlIns.add("     ,DEN_SYU_CODE ");
		sqlIns.add("     ,DEN_SYU_NAME ");
		sqlIns.add("     ,CUR_CODE ");
		sqlIns.add("     ,ZAN_KIN ");
		sqlIns.add("     ,ZAN_IN_KIN ");
		sqlIns.add("     ,CBK_CODE ");
		sqlIns.add("     ,DATA_KBN ");
		sqlIns.add("     ,SYS_KBN ");
		sqlIns.add("     ,DATA_TYPE ");
		sqlIns.add("     ,KEY_DEN_DATE ");
		sqlIns.add("     ,KEY_DEN_NO ");
		sqlIns.add("     ,KEY_GYO_NO ");
		sqlIns.add("     ,INP_DATE ");
		sqlIns.add("     ,UPD_DATE ");
		sqlIns.add("     ,PRG_ID ");
		sqlIns.add("     ,USR_ID ");
		sqlIns.add(" ) VALUES ( ");

		// ��s�����擾
		Map<String, BankAccount> bnkAccMap = new HashMap<String, BankAccount>();

		// �Ȗ�Key
		String key = null;

		// ���
		baseDate = slipDate;
		for (SWK_DTL dtl : slip.getDetails()) {
			// BOOK_NO=2�̓X���[
			if (dtl.getSWK_BOOK_NO() == 2) {
				continue;
			}
			// �����Ȗڂ���Ȃ�������߂�
			if (dtl.getItem() != null && dtl.getItem().getGlType() != GLType.FUND) {
				continue;
			}
			// �Ȗ�Key����
			if (!isUseCmEntryAcctNo) {
				// �a���ԍ���r�͖���擾������
				key = dtl.getSWK_KMK_CODE() + "<>" + Util.avoidNull(dtl.getSWK_HKM_CODE()) + "<>"
					+ Util.avoidNull(dtl.getSWK_UKM_CODE());
			}

			// ������
			bnkAcc = null;
			// ��s�����擾
			if (key != null && bnkAccMap.containsKey(key)) {
				bnkAcc = bnkAccMap.get(key);

			} else {
				// �ȖڂŃ}�b�`���O������
				baCondition = new BankAccountSearchCondition();
				baCondition.setCompanyCode(dtl.getKAI_CODE());
				baCondition.setItemCode(dtl.getSWK_KMK_CODE());
				if (isUseCmEntryAcctNo) {
					baCondition.setAccountNo(StringUtil.leftBX(Util.avoidNull(dtl.getSWK_HKM_CODE()), 7));
				} else {
					baCondition.setSubItemCode(Util.avoidNull(dtl.getSWK_HKM_CODE()));
				}
				baCondition.setDetailItemCode(Util.avoidNull(dtl.getSWK_UKM_CODE()));
				bnkAcc = baMn.getBankAccount(baCondition);
				if (key != null && bnkAcc != null) {
					bnkAccMap.put(key, bnkAcc);
				}
			}
			// ������������΃X���[
			if (bnkAcc == null) {
				continue;
			}
			// ��s���� �ʉ�
			curCode = bnkAcc.getCurrencyCode();

			inKin = dtl.getSWK_IN_KIN();
			kin = dtl.getSWK_KIN();

			if (Util.equals(curCode, dtl.getSWK_CUR_CODE())) {
				// �����ʉ݂Ǝ���ʉ݂����� �� ���̂܂�

			} else if (Util.equals(curCode, keyCurCode)) {
				// ����ʉ݂Ɗ�ʉ݂�����
				inKin = kin;
			} else {
				// �ȊO�͌v�Z��苁�߂�
				BigDecimal rate = rateMn.getRate(curCode, baseDate);

				inKin = convertToForeign(kin, rate, bnkAcc.getCurrency().getRatePow(),
					bnkAcc.getCurrency().getDecimalPoint(), cmp);
			}

			// ���̐ݒ�
			String triName = dtl.getSWK_TRI_NAME();
			if (!Util.isNullOrEmpty(dtl.getSWK_TRI_CODE()) && Util.isNullOrEmpty(triName)) {
				cusCnd = new CustomerSearchCondition();
				cusCnd.setCompanyCode(kaiCode);
				cusCnd.setCode(dtl.getSWK_TRI_CODE());
				List<Customer> cusList = cusMn.get(cusCnd);
				if (cusList.size() > 0) {
					triName = cusList.get(0).getName();
				}
			}
			String knrName = dtl.getSWK_DEP_NAME();
			if (!Util.isNullOrEmpty(dtl.getSWK_DEP_CODE()) && Util.isNullOrEmpty(knrName)) {
				depCnd = new DepartmentSearchCondition();
				depCnd.setCompanyCode(kaiCode);
				depCnd.setCode(dtl.getSWK_DEP_CODE());
				List<Department> depList = depMn.get(depCnd);
				if (depList.size() > 0) {
					knrName = depList.get(0).getName();
				}
			}

			// �o�^����
			sql = new VCreator();
			sql.add(sqlIns);

			sql.add("     ? ", kaiCode);
			sql.add("    ,? ", slipDate);
			sql.add("    ,? ", slipNo);
			sql.add("    ,? ", dtl.getSWK_TRI_CODE());
			sql.add("    ,? ", triName);
			sql.add("    ,? ", dtl.getSWK_DEP_CODE());
			sql.add("    ,? ", knrName);
			sql.add("    ,? ", dtl.getSWK_GYO_TEK() != null ? dtl.getSWK_GYO_TEK() : tek);
			sql.add("    ,? ", denSyuCode);
			sql.add("    ,? ", denSyuName);
			sql.add("    ,? ", bnkAcc.getCurrencyCode());
			if (dtl.isDR()) {
				sql.add("    ,? ", kin);
				sql.add("    ,? ", inKin);
			} else {
				sql.add("    ,? * -1 ", kin);
				sql.add("    ,? * -1 ", inKin);
			}
			sql.add("    , ? ", bnkAcc.getCode());
			sql.add("    , 1 "); // DATA_KBN
			sql.add("    , ?", sysKbn);
			sql.add("    , 1 "); // DATA_TYPE
			sql.add("    ,? ", slipDate);
			sql.add("    ,? ", slipNo);
			sql.add("    ,? ", dtl.getSWK_GYO_NO());
			sql.adt("    ,? ", dtl.getINP_DATE());
			sql.adt("    ,? ", dtl.getUPD_DATE());
			sql.add("    ,? ", dtl.getPRG_ID());
			sql.add("    ,? ", dtl.getUSR_ID());
			sql.add(" ) ");
			DBUtil.execute(sql);

		}

	}

	/**
	 * ��ʉ݁����͋��z
	 * 
	 * @param keyAmount ��ʉ݋��z
	 * @param rate ���[�g
	 * @param ratePow �O�ݒʉ݃��[�g�W��
	 * @param decimalPoints �O�ݒʉݏ����_�ȉ�����
	 * @param company
	 * @return ���͋��z
	 */
	public BigDecimal convertToForeign(BigDecimal keyAmount, BigDecimal rate, int ratePow, int decimalPoints,
		Company company) {

		if (rate == null) {
			return null;
		}

		if (keyAmount == null) {
			return null;
		}

		if (DecimalUtil.isNullOrZero(rate)) {
			return BigDecimal.ZERO;
		}

		if (DecimalUtil.isNullOrZero(keyAmount)) {
			return BigDecimal.ZERO;
		}

		AccountConfig conf = company.getAccountConfig();
		ExchangeFraction frac = conf.getExchangeFraction();

		// ���Z
		TExchangeAmount param = TCalculatorFactory.createExchangeAmount();
		param.setExchangeFraction(frac);
		param.setConvertType(conf.getConvertType());
		param.setDigit(decimalPoints);
		param.setKeyAmount(keyAmount);
		param.setRate(rate);
		param.setRatePow(ratePow);

		return calculator.exchangeForeignAmount(param);
	}

	/**
	 * SQL�p
	 */
	protected class VCreator extends SQLCreator {

		/**
		 * �R���X�g���N�^�[
		 */
		public VCreator() {
			crlf = " ";
		}
	}

	/**
	 * �`�[���`�F�b�N���āA���F
	 * 
	 * @param den
	 * @param isAsMuchAsPossible
	 * @return ���F��`�[
	 * @throws TException
	 */
	public SlipDen checkAndApproveSlip(SlipDen den, Boolean isAsMuchAsPossible) throws TException {

		isApproveAsMuchAsPossible = isAsMuchAsPossible;
		// �`�[�֘A�e�[�u�����[�N
		lockSlipTable();

		// �T�[�o�[��SWK_HDR���擾
		SlipCondition condition = createSlipCondition();
		condition.setSlipNo(den.getSWK_DEN_NO());
		condition.setCompanyCode(getCompanyCode());
		AccountConfig ac = getCompany().getAccountConfig();
		condition.setSearchWorkFlow(ac.isUseWorkflowApprove());
		List<SWK_HDR> lists = getHeader(condition);
		if (lists == null || lists.isEmpty()) {
			// �Ώۓ`�[���� : �����[�U�[���폜�܂��͏��F�ɂ��
			// �񑶍݂������͌�����ΏۂɂȂ��Ă���
			// �`�[�ԍ�{0}�͑����[�U�[���ҏW���A�܂��͓����X�V�ɂ�菳�F�ł��܂���ł����B
			throw new TException("I00191", den.getSWK_DEN_NO());
		}
		SWK_HDR hdr = lists.get(0);

		// �`�[�敪�����X�V(4)�ɂȂ��Ă���B
		if (hdr.getSWK_UPD_KBN() == SlipState.UPDATE.value) {
			// �w��̓`�[�͍X�V����Ă��܂��B
			throw new TException("W00126");
		}
		// ��ʓ`�[�敪�ƃT�[�o�[���`�[�敪������Ă���B
		if (!(hdr.getSWK_UPD_KBN() == den.getSWK_UPD_KBN())) {
			// ���̃��[�U�[�ɂ��X�V����Ă��܂��B�ēx�������ĉ�ʂ��J�������Ă��������B
			throw new TException("I01076");
		}

		SWK_SYO_CTL syo_CTL = hdr.getSyoCtl();

		// NEXT_APRV_ROLE_CODE��NULL
		if (syo_CTL.getNEXT_APRV_ROLE_CODE() == null) {
			throw new TException("I01076");
		}
		AprvRoleManager dao = (AprvRoleManager) getComponent(AprvRoleManager.class);
		AprvRoleSearchCondition aprvCon = new AprvRoleSearchCondition();
		aprvCon.setCompanyCode(getCompanyCode());
		aprvCon.setUserCode(getUserCode());
		aprvCon.setCode(syo_CTL.getNEXT_APRV_ROLE_CODE());
		List<AprvRole> role = dao.get(aprvCon);
		if (Util.isNullOrEmpty(role)) {
			throw new TException("I01076");
		}

		// ���F���Ԏ擾
		SlipDen toApproveSlipDen = getDen(hdr, den, SYO_FLG.APPROVE);

		// ���F
		List<SlipDen> tempDens = new ArrayList<SlipDen>();
		tempDens.add(toApproveSlipDen);
		List<SlipDen> beans = approveSlip(tempDens);

		return beans.get(0);
	}

	/**
	 * �`�[���`�F�b�N���āA���
	 * 
	 * @param den
	 * @return ���F�����̓`�[
	 * @throws TException
	 */
	public SlipDen checkAndCancelApprovedSlip(SlipDen den) throws TException {
		// �`�[�֘A�e�[�u�����[�N
		lockSlipTable();

		// �T�[�o�[��SWK_HDR���擾
		SlipCondition condition = createSlipCondition();
		condition.setSlipNo(den.getSWK_DEN_NO());
		condition.setCompany(getCompany());
		AccountConfig ac = getCompany().getAccountConfig();
		condition.setSearchWorkFlow(ac.isUseWorkflowApprove());
		List<SWK_HDR> lists = getHeader(condition);
		if (lists == null || lists.isEmpty()) {
			// �Ώۓ`�[���� : �����[�U�[���폜�܂��͏��F�ɂ��
			// �񑶍݂������͌�����ΏۂɂȂ��Ă���
			// �w��̓`�[�͑��[���ŏ������̂��ߍX�V�ł��܂���B{0}
			throw new TException("I00069");
		}
		SWK_HDR hdr = lists.get(0);

		// �`�[�敪�����X�V(4)�ɂȂ��Ă���
		if (hdr.getSWK_UPD_KBN() == SlipState.UPDATE.value) {
			// �w��̓`�[�͍X�V����Ă��܂��B
			throw new TException("W00126");
		}

		// ��ʓ`�[�敪�ƃT�[�o�[���`�[�敪������Ă���B
		if (!(hdr.getSWK_UPD_KBN() == den.getSWK_UPD_KBN())) {
			// ���̃��[�U�[�ɂ��X�V����Ă��܂��B�ēx�������ĉ�ʂ��J�������Ă��������B
			throw new TException("I01076");
		}

		// ����������邩�ǂ���
		SWK_SYO_CTL syo_CTL = hdr.getSyoCtl();
		AprvRoleManager dao = (AprvRoleManager) getComponent(AprvRoleManager.class);
		AprvRoleSearchCondition aprvCon = new AprvRoleSearchCondition();
		aprvCon.setCompanyCode(getCompanyCode());
		aprvCon.setUserCode(getUserCode());
		aprvCon.setCode(syo_CTL.getAPRV_ROLE_CODE());
		List<AprvRole> role = dao.get(aprvCon);
		if (Util.isNullOrEmpty(role)) {
			throw new TException("I01076");
		}

		// ������Ԃ��擾
		SlipDen tocancelApproveSlipDen = getDen(hdr, den, SYO_FLG.CANCEL);

		// ���
		List<SlipDen> slipDens = new ArrayList<SlipDen>();
		slipDens.add(tocancelApproveSlipDen);
		List<SlipDen> beans = cancelApproveSlip(slipDens);

		return beans.get(0);

	}

	/**
	 * �w��s�̓`�[���̎擾<br>
	 * �Ώۓ`�[�s�ɑ΂��ď��F�ҏ����X�V<br>
	 * �ݒ肳��Ă��鏳�F�O���[�v���ŁA���̏��F���[����NEXT_ROLE�ɐݒ�
	 * 
	 * @param hdr
	 * @param den
	 * @param flg
	 * @return �`�[���
	 */
	protected SlipDen getDen(SWK_HDR hdr, SlipDen den, int flg) {

		SWK_SYO_CTL ctl = hdr.getSyoCtl();
		if (ctl == null) {
			return den;
		}
		if (flg == SYO_FLG.APPROVE) {
			String nextRoleCode = getNextAprvRoleCode(hdr.getSWK_APRV_GRP_CODE(), ctl.getNEXT_APRV_ROLE_CODE());
			String roleCode = getPrevAprvRoleCode(hdr.getSWK_APRV_GRP_CODE(), nextRoleCode);
			den.setAPRV_ROLE_CODE(roleCode);
			den.setNEXT_APRV_ROLE_CODE(nextRoleCode);
			if (Util.isNullOrEmpty(nextRoleCode)) {
				den.setACCT_APRV_FLG(true);
			}
		} else if (flg == SYO_FLG.CANCEL) {
			String roleCode;
			String nextRoleCode;
			if (getCompany().getAccountConfig().isBackFirstWhenWorkflowDeny()) {
				nextRoleCode = getFirstAprvRoleCode(hdr.getSWK_APRV_GRP_CODE());
				roleCode = null;
			} else {
				// �ʏ�i���F����߂��ꍇ
				nextRoleCode = ctl.getAPRV_ROLE_CODE();
				roleCode = getPrevAprvRoleCode(hdr.getSWK_APRV_GRP_CODE(), nextRoleCode);
			}
			if (!Util.isNullOrEmpty(roleCode)) {
				// ���F�r���̏ꍇ�A�o�����F�����ꏳ�F�Ɛ��䂳���邽��
				den.setACCT_APRV_FLG(true);
			}
			den.setAPRV_ROLE_CODE(roleCode);
			den.setNEXT_APRV_ROLE_CODE(nextRoleCode);
		}
		return den;
	}

	/**
	 * �w��O���[�v / �w�胍�[���R�[�h���ł̎��̏��F���[���R�[�h���擾����
	 * 
	 * @param grpCode
	 * @param roleCode
	 * @return ���̃��[���R�[�h
	 */
	protected String getNextAprvRoleCode(String grpCode, String roleCode) {
		try {
			AprvRoleGroup grp = getAprvRoleGroup(grpCode);
			if (grp == null) {
				return null;
			}
			if (roleCode == null) {
				return null;
			}
			int nowLevel = 0;
			for (AprvRoleGroupDetail dtl : grp.getDetailList()) {
				if (Util.equals(dtl.getAPRV_ROLE_CODE(), roleCode)) {
					nowLevel = dtl.getAPRV_LEVEL();
				}
			}
			AprvRoleGroupDetail nextRole = null;
			for (AprvRoleGroupDetail dtl : grp.getDetailList()) {
				if (nowLevel >= dtl.getAPRV_LEVEL()) {
					// ���݃��x��������O�F�ΏۊO
					continue;
				}
				nextRole = dtl;
				if (!isApproveAsMuchAsPossible) {
					// �ʏ�̐i�ߕ��̏ꍇ�A�ŏ��ɊY���������[���R�[�h
					break;
				}
				// �i�߂���Ƃ���܂Ői�ޏꍇ
				if (!hasPermission(dtl.getAPRV_ROLE_CODE())) {
					// ������ێ����Ȃ��ꍇ�I��
					break;
				}
				// �ċA����
				return getNextAprvRoleCode(grpCode, nextRole.getAPRV_ROLE_CODE());
			}
			if (nextRole == null) {
				return null;
			}
			return nextRole.getAPRV_ROLE_CODE();
		} catch (TException e) {
			return null;
		}
	}

	/**
	 * �R�[�h�Ŏw��̏��F�O���[�v���擾
	 * 
	 * @param code
	 * @return ���F�O���[�v
	 * @throws TException
	 */
	protected AprvRoleGroup getAprvRoleGroup(String code) throws TException {
		List<AprvRoleGroup> list = getAllGroup();
		if (list == null) {
			return null;
		}
		for (AprvRoleGroup grp : list) {
			if (Util.equals(grp.getAPRV_ROLE_GRP_CODE(), code)) {
				return grp;
			}
		}
		return null;
	}

	/**
	 * ���݂��邷�ׂĂ̏��F�O���[�v���擾
	 * 
	 * @return ���ׂĂ̏��F�O���[�v
	 * @throws TException
	 */
	protected List<AprvRoleGroup> getAllGroup() throws TException {
		if (grpList != null) {
			return grpList;
		}
		AprvRoleGroupSearchCondition con = new AprvRoleGroupSearchCondition();
		con.setCompanyCode(getCompanyCode());
		AprvRoleGroupManager dao = (AprvRoleGroupManager) getComponent(AprvRoleGroupManager.class);
		grpList = dao.get(con);

		return grpList;
	}

	/**
	 * �w��̃��[���ɑ΂��Č�����ێ����邩
	 * 
	 * @param roleCode ���F���[���R�[�h
	 * @return true:������ێ����Ă���
	 */
	protected boolean hasPermission(String roleCode) {
		try {
			AprvRole role = getAprvRole(roleCode);
			if (role == null) {
				return false;
			}
			for (User usr : role.getUserList()) {
				// ���[���ۗL�̃��[�U�[�ƃ��O�C�����[�U�[����v�̏ꍇ�����ۗL
				if (Util.equals(getUserCode(), usr.getCode())) {
					return true;
				}
			}
		} catch (TException e) {
			//
			return false;
		}
		return false;
	}

	/**
	 * ���݂��邷�ׂĂ̏��F���[�����擾
	 * 
	 * @return ���ׂĂ̏��F���[��
	 * @throws TException
	 */
	protected List<AprvRole> getAllRole() throws TException {
		if (roleList != null) {
			return roleList;
		}
		AprvRoleSearchCondition con = new AprvRoleSearchCondition();
		con.setCompanyCode(getCompanyCode());
		AprvRoleManager dao = (AprvRoleManager) getComponent(AprvRoleManager.class);
		roleList = dao.get(con);
		return roleList;
	}

	/**
	 * �R�[�h�Ŏw��̏��F���[�����擾
	 * 
	 * @param code
	 * @return ���F���[��
	 * @throws TException
	 */
	protected AprvRole getAprvRole(String code) throws TException {
		List<AprvRole> list = getAllRole();
		if (list == null) {
			return null;
		}
		for (AprvRole role : list) {
			if (Util.equals(role.getAPRV_ROLE_CODE(), code)) {
				return role;
			}
		}
		return null;
	}

	/**
	 * �w��O���[�v / �w�胍�[���R�[�h���̂P�O�̏��F���[���R�[�h���擾����
	 * 
	 * @param grpCode
	 * @param roleCode
	 * @return ���̃��[���R�[�h
	 */
	protected String getPrevAprvRoleCode(String grpCode, String roleCode) {
		try {
			AprvRoleGroup grp = getAprvRoleGroup(grpCode);
			if (grp == null) {
				return null;
			}
			int nowLevel = 99;
			for (AprvRoleGroupDetail dtl : grp.getDetailList()) {
				if (Util.equals(dtl.getAPRV_ROLE_CODE(), roleCode)) {
					nowLevel = dtl.getAPRV_LEVEL();
				}
			}
			AprvRoleGroupDetail prevRole = null;
			for (AprvRoleGroupDetail dtl : grp.getDetailList()) {
				if (nowLevel <= dtl.getAPRV_LEVEL()) {
					// ���݃��x�����傫���F�ΏۊO
					continue;
				}
				prevRole = dtl;
				if (prevRole.getAPRV_LEVEL() < dtl.getAPRV_LEVEL()) {
					prevRole = dtl;
				}
			}
			if (prevRole == null) {
				return null;
			}
			return prevRole.getAPRV_ROLE_CODE();
		} catch (TException e) {
			return null;
		}
	}

	/**
	 * �w�肵�����F�O���[�v�̐擪�̃R�[�h���擾
	 * 
	 * @param grpCode
	 * @return �w�肵�����F�O���[�v�̐擪�̃R�[�h
	 */
	protected String getFirstAprvRoleCode(String grpCode) {
		try {
			AprvRoleGroup grp = getAprvRoleGroup(grpCode);
			if (grp == null) {
				return null;
			}
			int nowLevel = 99;
			AprvRoleGroupDetail prevRole = null;
			for (AprvRoleGroupDetail dtl : grp.getDetailList()) {
				if (nowLevel <= dtl.getAPRV_LEVEL()) {
					// ���݃��x�����傫���F�ΏۊO
					continue;
				}
				if (nowLevel > dtl.getAPRV_LEVEL()) {
					prevRole = dtl;
					nowLevel = dtl.getAPRV_LEVEL();
				}
			}
			if (prevRole == null) {
				return null;
			}
			return prevRole.getAPRV_ROLE_CODE();
		} catch (TException e) {
			return null;
		}
	}

}
