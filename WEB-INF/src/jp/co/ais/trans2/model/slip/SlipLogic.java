package jp.co.ais.trans2.model.slip;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.util.*;
import jp.co.ais.trans2.common.model.report.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.balance.*;
import jp.co.ais.trans2.model.bill.*;
import jp.co.ais.trans2.model.bs.*;
import jp.co.ais.trans2.model.calc.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.management.*;
import jp.co.ais.trans2.model.payment.*;
import jp.co.ais.trans2.model.remark.*;
import jp.co.ais.trans2.model.slip.SWK_DTL.ZEI_KBN;
import jp.co.ais.trans2.model.slip.parts.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * �`�[����
 */
public abstract class SlipLogic extends TModel {

	/** true:�O���[�v��v<Server> */
	public static boolean groupAccounting = ServerConfig.isFlagOn("trans.slip.group.accounting");

	/** true:BS��������@�\�L��<Server> */
	public static boolean isUseBS = ServerConfig.isFlagOn("trans.slip.use.bs");

	/** true:�t�@�C���Y�t�@�\�L��<Server> */
	public static boolean isUseAttachment = ServerConfig.isFlagOn("trans.slip.use.attachment");

	/** true:BS����͌v���ЃR�[�h�̓��͒l�𗘗p<Server> */
	public static boolean isBsUseKCompany = ServerConfig.isFlagOn("trans.slip.bs.use.kcompany");

	/** true:�tⳋ@�\�L�� */
	public static boolean isUseTag = ServerConfig.isFlagOn("trans.slip.use.tag");

	/** true:�t�֊���̒ʉ݂𑊎��Ђ̎���ʉ݂ɂ���<Server> */
	public static boolean isUseOppositCompanysCurrency = ServerConfig
		.isFlagOn("trans.slip.group.accounting.different.key.currency");

	/** �����ێ��p�Z�p���[�^ */
	protected static final String KEY_SEP = "<>";

	/** �`�[��� */
	protected String slipType;

	/** �f�[�^�敪 */
	protected String dataType;

	/**
	 * �`�[���
	 * 
	 * @param slipType �`�[���
	 */
	public void setSlipType(String slipType) {
		this.slipType = slipType;
	}

	/**
	 * �`�[��ʎ擾
	 * 
	 * @return �`�[���
	 * @throws TException
	 */
	public SlipType getSlipType() throws TException {
		return getSlipType(this.slipType);
	}

	/**
	 * �`�[���
	 * 
	 * @param typeNo �`�[��ʔԍ�
	 * @return �`�[���
	 * @throws TException
	 */
	public SlipType getSlipType(String typeNo) throws TException {
		SlipTypeManager slipTypeManager = (SlipTypeManager) getComponent(SlipTypeManager.class);
		SlipType type = slipTypeManager.get(getCompanyCode(), typeNo);

		if (type == null) {
			// �`�[���[{0}]���ݒ肳��Ă��܂���B
			throw new TException("I00128", typeNo);
		}

		return type;
	}

	/**
	 * �f�[�^�敪
	 * 
	 * @param dataKind �f�[�^�敪
	 */
	public void setDataType(String dataKind) {
		this.dataType = dataKind;
	}

	/**
	 * �`�[���\�z����.<br>
	 * �`�[�ԍ��̔ԁA�e��l�ݒ�A��Њԕt�ցA����Ŏd�󔭐��A�@�\�ʉݎd��쐬
	 * 
	 * @param slip �`�[�N���X
	 * @return �\�z�����`�[(�t�֐�܂�)
	 * @throws TException
	 */
	public List<Slip> buildSlip(Slip slip) throws TException {

		// �x�[�X�ƂȂ�`�[�����`��(�`�[�ԍ��̔ԁA�e��l�ݒ�)
		buildBaseSlip(slip);

		// �t�ւ�����ꍇ ��Њԕt�֎d����쐬����
		List<Slip> slipList = transferCompanySlip(slip);

		// �t���ւ���Ќׂ��ōs�ԍ����A�ԂȂ̂ŁA�ǉ��̏���Ŏd����A�Ԃ�
		int maxRowNum = 0;
		for (Slip tslip : slipList) {
			maxRowNum += tslip.getDetails().size();
		}

		// �t�։�Ђ���
		for (Slip tslip : slipList) {

			// �@�\�ʉݎd��(�`�[)�쐬
			List<SWK_DTL> funcList = createFunctionalJournal(tslip);

			// ���� ����Ŏd��
			if (!tslip.isJournalizedTax()) {
				maxRowNum = addTaxJournal(tslip, maxRowNum);

			} else if (slip.isFromOtherSystem()) {
				// ���V�X�e���̏ꍇ�͌o�ߑ[�u����ǉ�
				maxRowNum = addTransferTaxJournal(tslip, maxRowNum);
			}

			// �@�\�ʉݎd���ǉ�
			tslip.getDetails().addAll(funcList);

			// ����Ȗڂ�ݒ�
			tslip.matchingItem();
		}

		for (Slip tslip : slipList) {
			// �N�x�A���x
			int fiscalYear = BizUtil.getFiscalYear(tslip.getSlipDate(), tslip.getCompanyCode());
			int fiscalMonth = BizUtil.getFiscalMonth(tslip.getSlipDate(), tslip.getCompanyCode());

			// ���ׂƂ̓������킹
			tslip.synchDetails(fiscalYear, fiscalMonth);
		}

		return slipList;
	}

	/**
	 * �`�[���\�z����.<br>
	 * �`�[�ԍ��̔ԁA�e��l�ݒ�
	 * 
	 * @param slip �`�[�N���X
	 * @throws TException
	 */
	public void buildBaseSlip(Slip slip) throws TException {

		// �`�[���`
		SWK_HDR hdr = slip.getHeader();
		boolean isNew = false;

		// �`�[�ԍ����u�����N�̏ꍇ�A�`�[�ԍ����̔�
		if (Util.isNullOrEmpty(hdr.getSWK_DEN_NO())) {

			// TODO �b��Ή�
			if (Util.isNullOrEmpty(hdr.getSWK_IRAI_DEP_CODE())) {
				slip.setIssuer(getUser());
			}

			String slipNo = this.newSlipNo(slip);

			// �`�[�ԍ���20����������G���[
			if (slipNo.length() > 20) {
				throw new TException("W00159");
			}

			hdr.setSWK_DEN_NO(slipNo);// �`�[�ԍ�
			isNew = true;
		}

		slip.synchDetails();

		// �K�{�`�F�b�N
		if (!slip.isAvoidReuiredItemNULL()) {
			checkNull(slip);
		}

		// �V�K���ǂ���
		if (isNew) {
			hdr.setINP_DATE(getNow()); // �o�^��
			hdr.setSWK_INP_DATE(getNow()); // �o�^��

			if (hdr.getSWK_IRAI_DATE() == null) {
				hdr.setSWK_IRAI_EMP_CODE(getUser().getEmployee().getCode()); // �˗���
				hdr.setSWK_IRAI_DEP_CODE(getUser().getDepartment().getCode()); // �˗�����R�[�h
				hdr.setSWK_IRAI_DATE(getNow()); // �˗���- ���ݓ��t
			}

		} else {
			// �X�V�f�[�^�ύX
			hdr.setSWK_UPD_CNT(hdr.getSWK_UPD_CNT() + 1); // �C����
			hdr.setUPD_DATE(getNow()); // �X�V��
		}

		// �X�V�敪 = ���F��ԈȊO�͒l��������
		if (!hdr.getSlipState().equals(SlipState.APPROVE)) {
			hdr.setSWK_SYO_EMP_CODE(null); // ���F��
			hdr.setSWK_SYO_DATE(null); // ���F��
		}

		hdr.setUSR_ID(getUserCode()); // ���[�UID
		hdr.setPRG_ID(getProgramCode()); // �v���O����ID

		// �X�V���N���A
		for (SWK_DTL dtl : slip.getDetails()) {
			dtl.setSWK_KESI_DATE(null); // �����`�[���t
			dtl.setSWK_KESI_DEN_NO(null); // �����`�[�ԍ�
			dtl.setSWK_SOUSAI_DATE(null); // ���E�`�[���t
			dtl.setSWK_SOUSAI_DEN_NO(null); // ���E�`�[�ԍ�
		}
	}

	/**
	 * �`�[���N�[����
	 * 
	 * @param slip �`�[�N���X
	 */
	public void entry(Slip slip) {
		setEntryInfo(slip);

		SlipEntry logic = getSlipEntry();
		logic.setHeaderDao(getHeaderDao());
		logic.setDetailDao(getDetailDao());
		logic.entry(slip);
	}

	/**
	 * SlipEntry
	 * 
	 * @return SlipEntry
	 */
	protected abstract SlipEntry getSlipEntry();

	/**
	 * �o�^���(�^�C���X�^���v�A�v���O����ID�A���[�U�[ID)���Z�b�g
	 * 
	 * @param slip
	 */
	protected void setEntryInfo(Slip slip) {
		SWK_HDR header = slip.getHeader();

		if (header.getINP_DATE() == null) {
			header.setINP_DATE(getNow());
		}

		if (Util.isNullOrEmpty(header.getPRG_ID())) {
			header.setPRG_ID(getProgramCode());
		}

		if (Util.isNullOrEmpty(header.getUSR_ID())) {
			header.setUSR_ID(getUserCode());
		}

		for (SWK_DTL detail : slip.getDetails()) {
			if (detail.getINP_DATE() == null) {
				detail.setINP_DATE(getNow());
			}

			if (Util.isNullOrEmpty(detail.getPRG_ID())) {
				detail.setPRG_ID(getProgramCode());
			}

			if (Util.isNullOrEmpty(detail.getUSR_ID())) {
				detail.setUSR_ID(getUserCode());
			}
		}
	}

	/**
	 * �`�[�̃`�F�b�N
	 * 
	 * @param slip �`�[
	 * @return �G���[���b�Z�[�W���X�g
	 * @throws TException
	 */
	public List<Message> checkSlipError(Slip slip) throws TException {

		// �w�b�_
		List<Message> list = getSlipHeaderChecker().checkHeader(slip);

		// ����
		list.addAll(getJournalDetailChecker().checkDetail(slip));

		return list;
	}

	/**
	 * �w�b�_�`�F�b�N���W�b�N
	 * 
	 * @return �w�b�_�`�F�b�N���W�b�N
	 */
	protected HeaderCheck getSlipHeaderChecker() {
		return (HeaderCheck) getComponent(HeaderCheck.class);
	}

	/**
	 * ���׃`�F�b�N���W�b�N
	 * 
	 * @return ���׃`�F�b�N���W�b�N
	 */
	public JournalDetailCheck getJournalDetailChecker() {
		return (JournalDetailCheck) getComponent(JournalDetailCheck.class);
	}

	/**
	 * NULL�s���ڂ�null/�u�����N�ɂȂ��Ă��Ȃ����ǂ����̃`�F�b�N.<br>
	 * �s���̏ꍇ�AException�𔭐�.
	 * 
	 * @param slip �Ώۓ`�[
	 * @throws TException
	 */
	protected void checkNull(Slip slip) throws TException {
		{
			String column = slip.getHeader().isReuiredItemNULL();
			if (!Util.isNullOrEmpty(column)) {
				// �`�[��{0}���s�����Ă��܂��B
				throw new TException("I00164", column);
			}
		}

		for (SWK_DTL detail : slip.getDetails()) {
			String column = detail.isRequiredItemNULL();
			if (!Util.isNullOrEmpty(column)) {
				// �`�[���ׂ�{0}���s�����Ă��܂��B
				throw new TException("I00165", column);
			}
		}
	}

	/**
	 * �`�[�ԍ����擾����
	 * 
	 * @param slip �`�[���
	 * @return �`�[�ԍ�
	 */
	protected String newSlipNo(Slip slip) {
		SlipNoCreator creator = (SlipNoCreator) getComponent(SlipNoCreator.class);
		return creator.newSlipNo(slip);
	}

	/**
	 * ����ۑ�
	 * 
	 * @param slipList �Ώۓ`�[���X�g
	 */
	public void entryHistory(List<Slip> slipList) {
		SWK_HDRDao dhdao = getDeleteHeaderDao();
		SWK_DTLDao dddao = getDeleteDetailDao();

		for (Slip slip : slipList) {
			dhdao.insert(slip.getHeader());

			for (SWK_DTL dtl : slip.getDetails()) {
				dtl.setSWK_UPD_CNT(slip.getHeader().getSWK_UPD_CNT());
			}

			dddao.insert(slip.getDetails());
		}
	}

	/**
	 * �폜����ۑ�
	 * 
	 * @param slipList �Ώۓ`�[���X�g
	 */
	public void entryDeleteHistory(List<Slip> slipList) {

		DEL_DTLDao dao = getDeleteSlipDao();

		for (Slip slip : slipList) {
			SWK_HDR hdr = slip.getHeader();

			if (slip.isTransferSlip()) {
				continue;
			}

			DEL_DTL dtl = new DEL_DTL();
			dtl.setKAI_CODE(hdr.getKAI_CODE());
			dtl.setDEL_DEN_NO(hdr.getSWK_DEN_NO());
			dtl.setDEL_DEN_DATE(hdr.getSWK_DEN_DATE());
			dtl.setINP_DATE(Util.getCurrentDate());
			dtl.setPRG_ID(getProgramCode());
			dtl.setUSR_ID(getUserCode());

			dao.insert(dtl);
		}
	}

	/**
	 * �폜�����̍폜
	 * 
	 * @param slip �Ώۓ`�[
	 */
	protected void deleteDelHistory(Slip slip) {

		DEL_DTLDao dao = getDeleteSlipDao();

		SWK_HDR hdr = slip.getHeader();

		DEL_DTL dtl = new DEL_DTL();
		dtl.setKAI_CODE(hdr.getKAI_CODE());
		dtl.setDEL_DEN_NO(hdr.getSWK_DEN_NO());
		dtl.setDEL_DEN_DATE(hdr.getSWK_DEN_DATE());
		dtl.setINP_DATE(Util.getCurrentDate());
		dtl.setPRG_ID(getProgramCode());
		dtl.setUSR_ID(getUserCode());

		dao.delete(dtl);
	}

	/**
	 * �`�[���폜����
	 * 
	 * @param slipNo �`�[�ԍ�
	 */
	public void delete(String slipNo) {
		delete(slipNo, true);
	}

	/**
	 * �`�[���폜����
	 * 
	 * @param slipNo �`�[�ԍ�
	 * @param isSaveHistory ������ۑ����邩�ǂ���
	 */
	public void delete(String slipNo, boolean isSaveHistory) {
		delete(slipNo, isSaveHistory, isSaveHistory);
	}

	/**
	 * �`�[���폜����
	 * 
	 * @param slipNo �`�[�ԍ�
	 * @param isSaveHistory ������ۑ����邩�ǂ���
	 * @param isSaveDelHistory �폜������ۑ����邩�ǂ���
	 */
	public void delete(String slipNo, boolean isSaveHistory, boolean isSaveDelHistory) {

		// ����ۑ�
		if (isSaveHistory) {

			// �폜�f�[�^�̎擾
			List<Slip> list = getSlip(slipNo);

			// ����
			entryHistory(list);

			if (isSaveDelHistory) {
				// �폜����
				entryDeleteHistory(list);
			}
		}

		SWK_HDRDao hdao = getHeaderDao();
		SWK_DTLDao ddao = getDetailDao();

		// �w�b�_�폜
		SlipCondition hparam = new SlipCondition();

		if (!getCompany().getAccountConfig().isUseGroupAccount()) {
			hparam.setCompanyCode(getCompanyCode());
		}

		hparam.setSlipNo(slipNo);
		hdao.deleteByCondition(hparam);

		// �Y�����ׂ̑S�폜
		SlipCondition dparam = new SlipCondition();

		if (!getCompany().getAccountConfig().isUseGroupAccount()) {
			dparam.setCompanyCode(getCompanyCode());
		}

		dparam.setSlipNo(slipNo);
		ddao.deleteByCondition(dparam);
	}

	/**
	 * �`�[���擾����.(�t�֊܂�)
	 * 
	 * @param denNo �`�[�ԍ�
	 * @return �`�[���X�g
	 */
	public List<Slip> getSlip(String denNo) {

		SlipCondition hparam = new SlipCondition();

		if (!getCompany().getAccountConfig().isUseGroupAccount()) {
			hparam.setCompanyCode(getCompanyCode());
		}

		hparam.setSlipNo(denNo);

		List<SWK_HDR> hlist = getHeader(hparam);

		List<Slip> list = new ArrayList<Slip>(hlist.size());

		for (SWK_HDR hdr : hlist) {
			SlipCondition dparam = new SlipCondition();
			dparam.setCompanyCode(hdr.getKAI_CODE());
			dparam.setSlipNo(denNo);
			List<SWK_DTL> dlist = getDetailDao().findByCondition(dparam);

			Slip slip = createSlip(hdr);
			slip.setDetails(dlist);

			list.add(slip);
		}

		return list;
	}

	/**
	 * �V�K�`�[�̍쐬
	 * 
	 * @return �`�[
	 */
	public abstract Slip createSlip();

	/**
	 * �V�K�`�[�̍쐬
	 * 
	 * @param hdr �`�[�w�b�_
	 * @return �`�[
	 */
	public Slip createSlip(SWK_HDR hdr) {
		Slip slip = createSlip();
		slip.setHeader(hdr);
		return slip;
	}

	/**
	 * �`�[�w�b�_���擾����
	 * 
	 * @param param
	 * @return �`�[�w�b�_
	 */
	public List<SWK_HDR> getHeader(SlipCondition param) {
		return getHeaderDao().findByCondition(param.toSQL());
	}

	/**
	 * �`�[�w�b�_���擾����
	 * 
	 * @param param
	 * @return �`�[�w�b�_
	 */
	public List<SWK_HDR> getPatternHeader(SlipCondition param) {
		return getPatternHeaderDao().findByCondition(param.toSQL());
	}

	/**
	 * �`�[�w�b�_���擾����
	 * 
	 * @param param
	 * @return �`�[�w�b�_
	 */
	public List<SWK_HDR> getHistoryHeader(SlipCondition param) {
		return getDeleteHeaderDao().findByCondition(param.toSQL());
	}

	/**
	 * �w�b�_�����ɓ`�[���\�z����.
	 * 
	 * @param hdr �`�[�w�b�_
	 * @return �d�󃊃X�g
	 */
	public Slip getSlip(SWK_HDR hdr) {
		Slip slip = createSlip(hdr);

		SlipCondition param = new SlipCondition();
		param.setCompanyCode(hdr.getKAI_CODE());
		param.setSlipNo(hdr.getSWK_DEN_NO());
		slip.setDetails(getDetailDao().findByCondition(param));

		return slip;
	}

	/**
	 * @return true:�O���[�v��v(��ʉ݈قȂ�v���Љ�)
	 */
	public static boolean isGroupAccounting() {
		return groupAccounting;
	}

	/**
	 * ��Њԕt�֏���
	 * 
	 * @param slip �`�[
	 * @return �t�ւ���̓`�[���X�g
	 * @throws TException
	 */
	protected List<Slip> transferCompanySlip(Slip slip) throws TException {
		if (isGroupAccounting()) {
			if (isUseOppositCompanysCurrency) {
				// �O���[�v��v�Ŏ���ʉ݂��قȂ�ꍇ
				GroupCompanyTransferDifferentCurrency compTransfer = (GroupCompanyTransferDifferentCurrency) getComponent(
					GroupCompanyTransferDifferentCurrency.class);
				return compTransfer.transfer(slip);
			} else {
				// �O���[�v��v�̏ꍇ
				GroupCompanyTransfer compTransfer = (GroupCompanyTransfer) getComponent(GroupCompanyTransfer.class);
				return compTransfer.transfer(slip);
			}
		} else {
			CompanyTransfer compTransfer = (CompanyTransfer) getComponent(CompanyTransfer.class);
			return compTransfer.transfer(slip);
		}
	}

	/**
	 * �@�\�ʉݎd��(�`�[)�̍쐬
	 * 
	 * @param slip ���`�[
	 * @return �쐬���ꂽ�`�[
	 * @throws TException
	 */
	protected List<SWK_DTL> createFunctionalJournal(Slip slip) throws TException {
		if (!getCompany().getAccountConfig().isUseIfrs()) {
			return Collections.EMPTY_LIST; // IFRS�����̏ꍇ�́A��炸�ɕԂ�
		}

		FunctionalCurrency logic;

		if (slip.isClosingSlip()) {
			// ���Z���[�g�x�[�X
			logic = (FunctionalCurrency) getComponent("FunctionalSettlementCurrency");

		} else {
			// �В背�[�g�x�[�X
			logic = (FunctionalCurrency) getComponent("FunctionalCurrency");
		}

		return logic.create(slip).getDetails();
	}

	/**
	 * �`�[�ɏ���Ŏd���ǉ��B<br>
	 * �{�̊���̏���ŃR�[�h�A����Ŋz�����Ƃɏ���Ŋ�����쐬���A�`�[�ɃZ�b�g�B
	 * 
	 * @param slip �`�[
	 * @throws TException
	 */
	public void addTaxJournal(Slip slip) throws TException {
		this.addTaxJournal(slip, slip.getDetails().size());
	}

	/**
	 * �`�[�ɏ���Ŏd���ǉ��B<br>
	 * �{�̊���̏���ŃR�[�h�A����Ŋz�����Ƃɏ���Ŋ�����쐬���A�`�[�ɃZ�b�g�B<br>
	 * ��Ђ��ׂ��ōs�ԍ���A�Ԃɂ���ꍇ�́A�ő�s�ԍ����Z�b�g����.
	 * 
	 * @param slip �`�[
	 * @param maxRowNum �ő�s�ԍ�
	 * @return �ő�s�ԍ�
	 * @throws TException
	 */
	protected int addTaxJournal(Slip slip, int maxRowNum) throws TException {
		TaxJournalIssuer logic = (TaxJournalIssuer) getComponent(TaxJournalIssuer.class);
		return logic.addJournal(slip, maxRowNum);
	}

	/**
	 * �`�[�ɏ���Ŏd��(�o�ߑ[�u����)��ǉ��B<br>
	 * �{�̊���̏���ŃR�[�h�A����Ŋz�����Ƃɏ���Ŋ���𗘗p���āA�o�ߑ[�u������s���A����p�ֈ����A�`�[�ɃZ�b�g�B<br>
	 * ��Ђ��ׂ��ōs�ԍ���A�Ԃɂ���ꍇ�́A�ő�s�ԍ����Z�b�g����.
	 * 
	 * @param slip �`�[
	 * @param maxRowNum �ő�s�ԍ�
	 * @return �ő�s�ԍ�
	 * @throws TException
	 */
	protected int addTransferTaxJournal(Slip slip, int maxRowNum) throws TException {
		TaxJournalIssuer logic = (TaxJournalIssuer) getComponent(TaxJournalIssuer.class);
		return logic.addTransferJournal(slip, maxRowNum);
	}

	/**
	 * �`�[�p�^�[�����\�z����.<br>
	 * �e��l�ݒ�
	 * 
	 * @param slip �`�[�N���X
	 * @return �\�z�����`�[(�t�֐�܂�)
	 * @throws TException
	 */
	public List<Slip> buildSlipPattern(Slip slip) throws TException {

		// �`�[�w�b�_
		SWK_HDR hdr = slip.getHeader();

		// �K�{�`�F�b�N(��O�p)
		if (Util.isNullOrEmpty(hdr.getKAI_CODE())) {
			// �K�{����[��ЃR�[�h]���ݒ肳��Ă��܂���B
			throw new TException("I00166", "C00596");
		}

		if (Util.isNullOrEmpty(hdr.getSWK_DEN_NO())) {
			// �K�{����[�p�^�[���ԍ�]���ݒ肳��Ă��܂���B
			throw new TException("I00166", "C00987");
		}

		if (slip.isNew()) {
			// �V�K
			hdr.setINP_DATE(getNow()); // �o�^��
			hdr.setSWK_INP_DATE(getNow()); // �o�^��

		} else {
			// �C��
			hdr.setSWK_UPD_CNT(hdr.getSWK_UPD_CNT() + 1); // �C����
			hdr.setUPD_DATE(getNow()); // �X�V���t
		}

		if (hdr.getSWK_IRAI_DATE() == null) {
			hdr.setSWK_IRAI_EMP_CODE(getUser().getEmployee().getCode()); // �˗���
			hdr.setSWK_IRAI_DEP_CODE(getUser().getDepartment().getCode()); // �˗�����R�[�h
			hdr.setSWK_IRAI_DATE(getNow()); // �˗���- ���ݓ��t
		}
		hdr.setUSR_ID(getUserCode()); // ���[�UID
		hdr.setPRG_ID(getProgramCode()); // �v���O����ID

		// �`�[���p�^�[���̍ۂɕs�v�ȍ��ڂ̓N���A
		// TODO ���ɐ􂢏o��
		hdr.setSWK_SYO_EMP_CODE(null); // ���F��
		hdr.setSWK_SYO_DATE(null); // ���F��

		for (SWK_DTL dtl : slip.getDetails()) {
			dtl.setSWK_KESI_DATE(null); // �����`�[���t
			dtl.setSWK_KESI_DEN_NO(null); // �����`�[�ԍ�
			dtl.setSWK_SOUSAI_DATE(null); // ���E�`�[���t
			dtl.setSWK_SOUSAI_DEN_NO(null); // ���E�`�[�ԍ�
		}

		// �s�ԍ�
		slip.setRowNo();

		// ���ׂƂ̓������킹
		slip.synchDetails();

		List<Slip> slipList = new ArrayList<Slip>(1);
		slipList.add(slip);

		return slipList;
	}

	/**
	 * �`�[�p�^�[�����N�[����
	 * 
	 * @param slip �`�[�N���X
	 */
	public void entryPattern(Slip slip) {
		setEntryInfo(slip);

		SlipEntry logic = getSlipEntry();
		logic.setHeaderDao(getPatternHeaderDao());
		logic.setDetailDao(getPatternDetailDao());
		logic.entry(slip);
	}

	/**
	 * �`�[�p�^�[�����폜����
	 * 
	 * @param slip �`�[
	 */
	public void deletePattern(Slip slip) {
		SWK_HDRDao hdao = getPatternHeaderDao();
		SWK_DTLDao ddao = getPatternDetailDao();

		// �w�b�_�폜
		SlipCondition hparam = new SlipCondition();
		hparam.setCompanyCode(slip.getCompanyCode());
		hparam.setSlipNo(slip.getSlipNo());
		hdao.deleteByCondition(hparam);

		// �Y�����ׂ̑S�폜
		SlipCondition dparam = new SlipCondition();
		dparam.setCompanyCode(slip.getCompanyCode());
		dparam.setSlipNo(slip.getSlipNo());
		ddao.deleteByCondition(dparam);
	}

	/**
	 * �r��
	 * 
	 * @param dao �w�b�_�[Dao
	 * @param slip �`�[
	 * @throws TException
	 */
	public void lock(SWK_HDRDao dao, Slip slip) throws TException {

		// SWK_SHR_KBN �� SWK_UPD_KBN�̂ݎg�����߁A�œK������
		String companyCode = slip.getCompanyCode();
		if (Util.isNullOrEmpty(companyCode)) {
			companyCode = getCompanyCode();
		}
		SWK_HDR hdr = dao.getLockInfo(companyCode, slip.getSlipNo());

		if (hdr == null) {
			// �w��̃f�[�^�͊��ɍ폜����Ă��܂��B
			throw new TException("I00167");
		}

		// ���Ƀ��b�N����Ă���ꍇ
		if (hdr.getSWK_SHR_KBN() == Slip.SHR_KBN.LOCKED) {
			throw new TException("W00123");
		}

		switch (hdr.getSlipState()) {
			case UPDATE:
				// �X�V����Ă�ꍇ
				throw new TException("W00126");

			case APPROVE:
			case FIELD_APPROVE:
				// ���F����Ă�ꍇ
				throw new TException("W00127");
		}

		SlipCondition condition = new SlipCondition();
		condition.setCompanyCode(companyCode); // ��ЃR�[�h
		condition.setSlipNo(slip.getSlipNo());
		condition.setLockState(Slip.SHR_KBN.LOCKED); // �r���t���O �P�F�X�V��
		condition.setProgramCode(getProgramCode()); // �v���O�����h�c
		condition.setUserCode(getUserCode()); // ���[�U�[�h�c

		dao.updateLockState(condition);
	}

	/**
	 * �r������
	 * 
	 * @param dao �w�b�_�[Dao
	 * @param slip �`�[
	 */
	public void unlock(SWK_HDRDao dao, Slip slip) {
		SlipCondition condition = new SlipCondition();
		condition.setCompanyCode(slip.getCompanyCode()); // ��ЃR�[�h
		condition.setSlipNo(slip.getSlipNo()); // �`�[�ԍ�
		condition.setLockState(Slip.SHR_KBN.NON_LOCKED); // �r���t���O 0:���X�V
		condition.setProgramCode(getProgramCode()); // �v���O�����h�c
		condition.setUserCode(getUserCode()); // ���[�U�[�h�c

		dao.updateLockState(condition);
	}

	/**
	 * ���[�U�̑S�r������
	 * 
	 * @param dao �w�b�_�[Dao
	 */
	public void unlockAll(SWK_HDRDao dao) {
		SlipCondition condition = new SlipCondition();
		condition.setLockState(Slip.SHR_KBN.NON_LOCKED); // �r���t���O 0:���X�V
		condition.setCompanyCode(getCompanyCode()); // ��ЃR�[�h
		condition.setProgramCode(getProgramCode()); // �v���O�����h�c
		condition.setUserCode(getUserCode()); // ���[�U�[�h�c

		dao.updateLockState(condition);
	}

	/**
	 * �`�[�w�b�_Dao
	 * 
	 * @return �w�b�_Dao
	 */
	public abstract SWK_HDRDao getHeaderDao();

	/**
	 * �폜�`�[�w�b�_Dao
	 * 
	 * @return �폜�`�[�w�b�_Dao
	 */
	public abstract SWK_HDRDao getDeleteHeaderDao();

	/**
	 * �`�[�w�b�_�p�^�[��Dao
	 * 
	 * @return �`�[�w�b�_�p�^�[��Dao
	 */
	public abstract SWK_HDRDao getPatternHeaderDao();

	/**
	 * �`�[����Dao
	 * 
	 * @return �`�[����Dao
	 */
	public SWK_DTLDao getDetailDao() {
		return (SWK_DTLDao) getComponent("NEW-SWK_DTLDao");
	}

	/**
	 * �폜�`�[�f�[�^Dao
	 * 
	 * @return �폜�`�[�f�[�^Dao
	 */
	public DEL_DTLDao getDeleteSlipDao() {
		return (DEL_DTLDao) getComponent("NEW-DEL_DTLDao");
	}

	/**
	 * �폜�`�[����Dao
	 * 
	 * @return �폜�`�[����Dao
	 */
	public SWK_DTLDao getDeleteDetailDao() {
		return (SWK_DTLDao) getComponent("NEW-SWK_DEL_DTLDao");
	}

	/**
	 * �`�[���׃p�^�[��Dao
	 * 
	 * @return �`�[���׃p�^�[��Dao
	 */
	public SWK_DTLDao getPatternDetailDao() {
		return (SWK_DTLDao) getComponent("NEW-SWK_PTN_DTLDao");
	}

	/**
	 * �`�[������Entity���\�z����
	 * 
	 * @param slip �`�[
	 * @throws TException
	 */
	public void setupSlip(Slip slip) throws TException {
		setupHeader(slip);
		setupDetails(slip);
	}

	/**
	 * �`�[�w�b�_���\�z����.
	 * 
	 * @param slip �`�[
	 * @throws TException
	 */
	public void setupHeader(Slip slip) throws TException {

		SWK_HDR hdr = slip.getHeader();

		// �v�㕔��
		if (!Util.isNullOrEmpty(hdr.getSWK_DEP_CODE())) {

			// �����̐ݒ�
			DepartmentSearchCondition condition = new DepartmentSearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setCode(hdr.getSWK_DEP_CODE());

			// �Ώۃf�[�^�̎擾
			DepartmentManager logic = (DepartmentManager) getComponent(DepartmentManager.class);
			List<Department> list = logic.get(condition);

			// �Ώۃf�[�^�����݂���ꍇ�A�w�b�_�[�ɃG���e�B�e�B���Z�b�g
			if (!list.isEmpty()) {
				hdr.setDepartment(list.get(0));
			}
		}

		// �Ȗ�
		ItemManager itemMng = (ItemManager) getComponent(ItemManager.class);
		String kmk = hdr.getSWK_KMK_CODE();
		String hkm = hdr.getSWK_HKM_CODE();
		String ukm = hdr.getSWK_UKM_CODE();

		if (!Util.isNullOrEmpty(ukm)) {// ����܂ł���ꍇ

			// ���������̐ݒ�
			DetailItemSearchCondition condition = new DetailItemSearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setItemCode(kmk);
			condition.setSubItemCode(hkm);
			condition.setCode(ukm);

			// �Ώۃf�[�^�����݂���ꍇ�A�w�b�_�[�ɃG���e�B�e�B���Z�b�g
			List<Item> items = itemMng.get(condition);

			if (!items.isEmpty()) {
				hdr.setItem(items.get(0));
			}

		} else if (!Util.isNullOrEmpty(hkm)) {// �⏕�܂ł���ꍇ

			// ���������̐ݒ�
			SubItemSearchCondition hkmCon = new SubItemSearchCondition();
			hkmCon.setCompanyCode(hdr.getKAI_CODE());
			hkmCon.setItemCode(kmk);
			hkmCon.setCode(hkm);

			// �Ώۃf�[�^�����݂���ꍇ�A�w�b�_�[�ɃG���e�B�e�B���Z�b�g
			List<Item> items = itemMng.get(hkmCon);

			if (!items.isEmpty()) {
				hdr.setItem(items.get(0));
			}

		} else if (!Util.isNullOrEmpty(kmk)) {

			// �Ȗڂ݂̂̏ꍇ
			if (hdr.getItem() == null && !Util.isNullOrEmpty(kmk)) {

				// ���������̐ݒ�
				ItemSearchCondition kmkCon = new ItemSearchCondition();
				kmkCon.setCompanyCode(hdr.getKAI_CODE());
				kmkCon.setCode(kmk);

				// �Ώۃf�[�^�����݂���ꍇ�A�w�b�_�[�ɃG���e�B�e�B���Z�b�g
				List<Item> items = itemMng.get(kmkCon);

				if (!items.isEmpty()) {
					hdr.setItem(items.get(0));
				}
			}
		}

		// �ʉ�
		if (!Util.isNullOrEmpty(hdr.getSWK_CUR_CODE())) {

			// �����̐ݒ�
			CurrencySearchCondition condition = new CurrencySearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setCode(hdr.getSWK_CUR_CODE());

			// �Ώۃf�[�^�̎擾
			CurrencyManager logic = (CurrencyManager) getComponent(CurrencyManager.class);// �ʉ�
			List<Currency> list = logic.get(condition);

			// �Ώۃf�[�^�����݂���ꍇ�A�w�b�_�[�ɃG���e�B�e�B���Z�b�g
			if (!list.isEmpty()) {
				hdr.setCurrency(list.get(0));
			}
		}

		// �����
		if (!Util.isNullOrEmpty(hdr.getSWK_TRI_CODE())) {

			// �����̐ݒ�
			CustomerSearchCondition condition = new CustomerSearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setCode(hdr.getSWK_TRI_CODE());

			// �Ώۃf�[�^�̎擾
			CustomerManager logic = (CustomerManager) getComponent(CustomerManager.class);
			List<Customer> list = logic.get(condition);

			// �Ώۃf�[�^�����݂���ꍇ�A�w�b�_�[�ɃG���e�B�e�B���Z�b�g
			if (!list.isEmpty()) {
				hdr.setCustomer(list.get(0));
			}
		}

		// �x�����@
		if (!Util.isNullOrEmpty(hdr.getSWK_HOH_CODE())) {

			// �����̐ݒ�
			PaymentMethodSearchCondition condition = new PaymentMethodSearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setCode(hdr.getSWK_HOH_CODE());

			// �Ώۃf�[�^�̎擾
			PaymentMethodManager logic = (PaymentMethodManager) getComponent(PaymentMethodManager.class);
			List<PaymentMethod> list = logic.get(condition);

			// �Ώۃf�[�^�����݂���ꍇ�A�w�b�_�[�ɃG���e�B�e�B���Z�b�g
			if (!list.isEmpty()) {
				hdr.setPaymentMethod(list.get(0));
			}
		}

		// ��s����
		if (!Util.isNullOrEmpty(hdr.getSWK_CBK_CODE())) {

			// �����̐ݒ�
			BankAccountSearchCondition condition = new BankAccountSearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setCode(hdr.getSWK_CBK_CODE());

			// �Ώۃf�[�^�̎擾
			BankAccountManager logic = (BankAccountManager) getComponent(BankAccountManager.class);
			List<BankAccount> list = logic.get(condition);

			// �Ώۃf�[�^�����݂���ꍇ�A�w�b�_�[�ɃG���e�B�e�B���Z�b�g
			if (!list.isEmpty()) {
				hdr.setBankAccount(list.get(0));
			}
		}

		// ��������
		if (!Util.isNullOrEmpty(hdr.getSWK_TJK_CODE())) {

			// �����̐ݒ�
			PaymentSettingSearchCondition condition = new PaymentSettingSearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setCustomerCode(hdr.getSWK_TRI_CODE());
			condition.setCode(hdr.getSWK_TJK_CODE());

			// �Ώۃf�[�^�̎擾
			PaymentSettingManager logic = (PaymentSettingManager) getComponent(PaymentSettingManager.class);
			List<PaymentSetting> list = logic.get(condition);

			// �Ώۃf�[�^�����݂���ꍇ�A�֘A�����Z�b�g���A�w�b�_�[�ɃG���e�B�e�B���Z�b�g
			if (!list.isEmpty()) {
				PaymentSetting bean = list.get(0);
				bean.setBankAccount(hdr.getBankAccount());
				bean.setPaymentMethod(hdr.getPaymentMethod());
				bean.setPaymentDateType(PaymentDateType.getPaymentDateType(hdr.getSWK_SIHA_KBN()));
				hdr.setPaymentSetting(bean);
				if (hdr.getCustomer() != null) {
					hdr.getCustomer().setPaymentSetting(bean);
				}
			}
		}

		// �����敪
		if (!Util.isNullOrEmpty(hdr.getSWK_SEI_KBN())) {

			// �����̐ݒ�
			BillTypeSearchCondition condition = new BillTypeSearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setCode(hdr.getSWK_SEI_KBN());

			// �Ώۃf�[�^�̎擾
			BillTypeManager logic = (BillTypeManager) getComponent(BillTypeManager.class);
			List<BillType> list = logic.get(condition);

			// �Ώۃf�[�^�����݂���ꍇ�A�w�b�_�[�ɃG���e�B�e�B���Z�b�g
			if (!list.isEmpty()) {
				hdr.setBillType(list.get(0));
			}
		}

		// �Ј�
		if (!Util.isNullOrEmpty(hdr.getSWK_EMP_CODE())) {

			// �����̐ݒ�
			EmployeeSearchCondition condition = new EmployeeSearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setCode(hdr.getSWK_EMP_CODE());

			// �Ώۃf�[�^�̎擾
			EmployeeManager logic = (EmployeeManager) getComponent(EmployeeManager.class);
			List<Employee> list = logic.get(condition);

			// �Ώۃf�[�^�����݂���ꍇ�A�w�b�_�[�ɃG���e�B�e�B���Z�b�g
			if (!list.isEmpty()) {
				hdr.setEmployee(list.get(0));
			}
		}

		// �t�@�C���Y�t�@�\�L���̏ꍇ
		if (isUseAttachment) {
			// �`�[�Y�t�̐ݒ�
			setupAttachments(slip);
		}
		// �tⳋ@�\�L���̏ꍇ
		if (isUseTag) {
			setupTags(slip);
		}

	}

	/**
	 * �`�[�Y�t�̐ݒ�
	 * 
	 * @param slip
	 * @throws TException
	 */
	public void setupAttachments(Slip slip) throws TException {
		SlipAttachmentManager attachment = (SlipAttachmentManager) getComponent(SlipAttachmentManager.class);
		slip.getHeader().setAttachments(attachment.get(slip));
	}

	/**
	 * �`�[�tⳂ̐ݒ�
	 * 
	 * @param slip
	 * @throws TException
	 */
	public void setupTags(Slip slip) throws TException {
		SlipTagManager tag = (SlipTagManager) getComponent(SlipTagManager.class);
		slip.getHeader().setSwkTags(tag.get(slip));
	}

	/**
	 * �`�[���ׂ��\�z����.
	 * 
	 * @param slip �`�[
	 * @throws TException
	 */
	public void setupDetails(Slip slip) throws TException {
		if (slip == null) {
			return;
		}

		List<Slip> slipList = new ArrayList<Slip>();
		slipList.add(slip);
		setupDetails(slipList, true);
	}

	/**
	 * �`�[���ׂ��\�z����.
	 * 
	 * @param slipList �`�[���X�g
	 * @param includeBalance true:AP/AR/BS�c���Z�b�g�A�b�v
	 * @throws TException
	 */
	public void setupDetails(List<Slip> slipList, boolean includeBalance) throws TException {

		if (slipList == null || slipList.isEmpty()) {
			return;
		}

		CompanyManager compMng = (CompanyManager) getComponent(CompanyManager.class); // ���
		DepartmentManager deptMng = (DepartmentManager) getComponent(DepartmentManager.class);// ����
		ItemManager itemMng = (ItemManager) getComponent(ItemManager.class); // �Ȗ�
		ConsumptionTaxManager taxMng = (ConsumptionTaxManager) getComponent(ConsumptionTaxManager.class);// �����
		CurrencyManager curMng = (CurrencyManager) getComponent(CurrencyManager.class);// �ʉ�

		CompanySearchCondition compCon = new CompanySearchCondition();
		DepartmentSearchCondition deptCon = new DepartmentSearchCondition();
		ItemSearchCondition kmkCon = new ItemSearchCondition();
		SubItemSearchCondition hkmCon = new SubItemSearchCondition();
		DetailItemSearchCondition ukmCon = new DetailItemSearchCondition();
		ConsumptionTaxSearchCondition taxCon = new ConsumptionTaxSearchCondition();
		CurrencySearchCondition curCon = new CurrencySearchCondition();

		Map<String, Company> compMap = new TreeMap<String, Company>();
		Map<String, Department> deptMap = new TreeMap<String, Department>();
		Map<String, Item> itemMap = new TreeMap<String, Item>();
		Map<String, ConsumptionTax> taxMap = new TreeMap<String, ConsumptionTax>();
		Map<String, Currency> curMap = new TreeMap<String, Currency>();

		String keyCur = getCompany().getAccountConfig().getKeyCurrency().getCode();

		for (Slip slip : slipList) {

			List<SWK_DTL> dtlList = slip.getDetails();

			// ����
			for (SWK_DTL dtl : dtlList) {

				// �v����
				String kCompany = dtl.getSWK_K_KAI_CODE();

				if (!Util.isNullOrEmpty(kCompany)) {
					Company company = compMap.get(kCompany);
					if (company == null) {
						compCon.setCode(kCompany);
						List<Company> companys = compMng.get(compCon);

						if (!companys.isEmpty()) {
							company = companys.get(0);
							compMap.put(kCompany, company);
						}
					}
					dtl.setAppropriateCompany(company);
				}

				if (isGroupAccounting()) {
					if (dtl.getSWK_TUKE_KBN() == 2) {
						// 2
					} else {
						// 0, 1
						kCompany = dtl.getKAI_CODE();
						if (Util.isNullOrEmpty(kCompany)) {
							kCompany = getCompanyCode();
							dtl.setKAI_CODE(getCompanyCode());
						}
					}
				}

				// �v�㕔��
				String dept = dtl.getSWK_DEP_CODE();

				if (!Util.isNullOrEmpty(dept)) {
					Department department = deptMap.get(kCompany + KEY_SEP + dept);
					if (department == null) {
						deptCon.setCompanyCode(kCompany);
						deptCon.setCode(dept);
						List<Department> depts = deptMng.get(deptCon);
						if (!depts.isEmpty()) {
							department = depts.get(0);
							deptMap.put(kCompany + KEY_SEP + dept, department);
						}
					}
					dtl.setDepartment(department);
				}

				// �Ȗ�
				String kmk = dtl.getSWK_KMK_CODE();
				String hkm = dtl.getSWK_HKM_CODE();
				String ukm = dtl.getSWK_UKM_CODE();

				if (!Util.isNullOrEmpty(kmk)) {
					Item item;
					if (!Util.isNullOrEmpty(ukm)) {
						// ����܂ł���
						item = itemMap.get(kCompany + KEY_SEP + kmk + KEY_SEP + hkm + KEY_SEP + ukm);

						if (item == null) {
							ukmCon.setCompanyCode(kCompany);
							ukmCon.setItemCode(kmk);
							ukmCon.setSubItemCode(hkm);
							ukmCon.setCode(ukm);
							List<Item> items = itemMng.get(ukmCon);

							if (!items.isEmpty()) {
								item = items.get(0);
								itemMap.put(kCompany + KEY_SEP + kmk + KEY_SEP + hkm + KEY_SEP + ukm, item);
							}
						}

					} else if (!Util.isNullOrEmpty(hkm)) {
						// �⏕�܂ł���
						item = itemMap.get(kCompany + KEY_SEP + kmk + KEY_SEP + hkm);

						if (item == null) {
							hkmCon.setCompanyCode(kCompany);
							hkmCon.setItemCode(kmk);
							hkmCon.setCode(hkm);
							List<Item> items = itemMng.get(hkmCon);

							if (!items.isEmpty()) {
								item = items.get(0);
								itemMap.put(kCompany + KEY_SEP + kmk + KEY_SEP + hkm, item);
							}
						}

					} else {
						// �Ȗڂ̂�
						item = itemMap.get(kCompany + KEY_SEP + kmk);

						if (item == null) {
							kmkCon.setCompanyCode(kCompany);
							kmkCon.setCode(kmk);
							List<Item> items = itemMng.get(kmkCon);

							if (!items.isEmpty()) {
								item = items.get(0);
								itemMap.put(kCompany + KEY_SEP + kmk, item);
							}
						}
					}
					// �Ȗڏ�񂪖�����Δ�����
					if (Util.isNullOrEmpty(item)) {
						continue;
					}
					dtl.setItem(item);

					// �œK�Ȗڂ��擾
					Item pItem = item.getPreferedItem();

					// �����
					String zei = dtl.getSWK_ZEI_CODE();
					if (!Util.isNullOrEmpty(zei)) {
						ConsumptionTax tax = taxMap.get(kCompany + KEY_SEP + zei);
						if (tax == null) {
							taxCon.setCompanyCode(kCompany);
							taxCon.setCode(zei);
							List<ConsumptionTax> taxs = taxMng.get(taxCon);
							if (!taxs.isEmpty()) {
								tax = taxs.get(0);
								taxMap.put(kCompany + KEY_SEP + zei, tax);
							}
						}
						// �ېŃt���O�`�F�b�N
						if (tax != null && (tax.getTaxType() == TaxType.NOT || tax.getTaxType() == TaxType.PURCHAESE)
							&& pItem.isUsePurchaseTaxation()) {
							dtl.setTax(tax);
						} else if (tax != null && (tax.getTaxType() == TaxType.NOT || tax.getTaxType() == TaxType.SALES)
							&& pItem.isUseSalesTaxation()) {
								dtl.setTax(tax);
							} else {
								dtl.setSWK_ZEI_CODE(null);
							}
					}

					// �ʉ�
					String cur = dtl.getSWK_CUR_CODE();

					if (!Util.isNullOrEmpty(cur)) {
						Currency currency = curMap.get(kCompany + KEY_SEP + cur);
						if (currency == null) {
							curCon.setCompanyCode(kCompany);
							curCon.setCode(cur);
							List<Currency> curs = curMng.get(curCon);
							if (!curs.isEmpty()) {
								currency = curs.get(0);
								curMap.put(kCompany + KEY_SEP + cur, currency);
							}
						}
						if (pItem.isUseForeignCurrency()) {
							dtl.setCurrency(currency);
						} else if (!pItem.isUseForeignCurrency() && Util.equals(keyCur, cur)) {
							dtl.setCurrency(currency);
						} else {
							dtl.setSWK_CUR_CODE(null);
						}

					}
				}
			}

			// �`�[�ԍ����ݒ肳��Ă��Ȃ��ꍇ�A�����I��
			if (Util.isNullOrEmpty(slip.getSlipNo())) {
				return;
			}

			// �c���G���e�B�e�B���Z�b�g
			BalanceManager blanceMng = (BalanceManager) getComponent(BalanceManager.class);

			BalanceCondition blCon = new BalanceCondition();
			blCon.setCompanyCode(slip.getCompanyCode()); // ��ЃR�[�h
			blCon.setEraseSlipNo(slip.getSlipNo()); // �����`�[�ԍ�

			// �s�ԍ���KeySet���쐬
			Map<Integer, SWK_DTL> indexMap = new HashMap<Integer, SWK_DTL>(slip.getDetailCount());

			for (SWK_DTL dtl : slip.getDetails()) {
				indexMap.put(dtl.getSWK_GYO_NO(), dtl);
			}

			if (includeBalance) {

				// ��
				List<AP_ZAN> apList = blanceMng.getApBalance(blCon);

				for (AP_ZAN zan : apList) {
					int lineNo = zan.getZAN_SIHA_GYO_NO();
					if (indexMap.get(lineNo) != null) {
						indexMap.get(lineNo).setAP_ZAN(zan);
					}
				}

				// ��
				List<AR_ZAN> arList = blanceMng.getArBalance(blCon);

				for (AR_ZAN zan : arList) {
					int lineNo = zan.getZAN_KESI_GYO_NO();
					if (indexMap.get(lineNo) != null) {
						indexMap.get(lineNo).setAR_ZAN(zan);
					}
				}

				if (isUseBS) {
					// BS��������d��̐ݒ�
					setupBsDetails(slip);
				}
			}
		}

	}

	/**
	 * �`�[���ׂ��\�z����<br>
	 * ���ڐ���Ɋ֌W�̂Ȃ��Ǘ��P�`�Ǘ��U��A���������Z�b�g<br>
	 * �܂��A�t���O�ɂ���ď���Ŋz��M�݋��z����̏ꍇ���̒l���₤
	 * 
	 * @param slip
	 * @param recalc �K�v�ȏꍇ�̂ݍČv�Z
	 * @throws TException
	 */
	public void setupDetailsOptional(Slip slip, boolean recalc) throws TException {
		setupDetailsOptional(slip, recalc, true);
	}

	/**
	 * �`�[���ׂ��\�z����<br>
	 * ���ڐ���Ɋ֌W�̂Ȃ��Ǘ��P�`�Ǘ��U��A���������Z�b�g<br>
	 * �܂��A�t���O�ɂ���ď���Ŋz��M�݋��z����̏ꍇ���̒l���₤
	 * 
	 * @param slip
	 * @param recalc �K�v�ȏꍇ�̂ݍČv�Z
	 * @param includeBalance true:AP/AR/BS�c���Z�b�g�A�b�v
	 * @throws TException
	 */
	public void setupDetailsOptional(Slip slip, boolean recalc, boolean includeBalance) throws TException {
		if (slip == null) {
			return;
		}

		List<Slip> slipList = new ArrayList<Slip>();
		slipList.add(slip);
		setupDetailsOptional(slipList, recalc, includeBalance);
	}

	/**
	 * �`�[���ׂ��\�z����<br>
	 * ���ڐ���Ɋ֌W�̂Ȃ��Ǘ��P�`�Ǘ��U��A���������Z�b�g<br>
	 * �܂��A�t���O�ɂ���ď���Ŋz��M�݋��z����̏ꍇ���̒l���₤
	 * 
	 * @param slipList
	 * @param recalc �K�v�ȏꍇ�̂ݍČv�Z
	 * @param includeBalance true:AP/AR/BS�c���Z�b�g�A�b�v
	 * @throws TException
	 */
	public void setupDetailsOptional(List<Slip> slipList, boolean recalc, boolean includeBalance) throws TException {

		if (slipList == null || slipList.isEmpty()) {
			return;
		}

		Management1Manager knr1Mng = get(Management1Manager.class);
		Management2Manager knr2Mng = get(Management2Manager.class);
		Management3Manager knr3Mng = get(Management3Manager.class);
		Management4Manager knr4Mng = get(Management4Manager.class);
		Management5Manager knr5Mng = get(Management5Manager.class);
		Management6Manager knr6Mng = get(Management6Manager.class);
		RemarkManager remMng = get(RemarkManager.class);
		EmployeeManager empMana = get(EmployeeManager.class);
		CustomerManager cusMng = get(CustomerManager.class);
		RateManager rateMgr = get(RateManager.class);
		TCalculator calc = TCalculatorFactory.getCalculator();
		TExchangeAmount exc = TCalculatorFactory.createExchangeAmount();
		TTaxCalculation taxCalc = TCalculatorFactory.createTaxCalculation();

		Management1SearchCondition k1Con = new Management1SearchCondition();
		Management2SearchCondition k2Con = new Management2SearchCondition();
		Management3SearchCondition k3Con = new Management3SearchCondition();
		Management4SearchCondition k4Con = new Management4SearchCondition();
		Management5SearchCondition k5Con = new Management5SearchCondition();
		Management6SearchCondition k6Con = new Management6SearchCondition();
		RemarkSearchCondition remCon = new RemarkSearchCondition();
		EmployeeSearchCondition empCon = new EmployeeSearchCondition();
		CustomerSearchCondition cusCon = new CustomerSearchCondition();

		Map<String, Management1> k1Map = new TreeMap<String, Management1>();
		Map<String, Management2> k2Map = new TreeMap<String, Management2>();
		Map<String, Management3> k3Map = new TreeMap<String, Management3>();
		Map<String, Management4> k4Map = new TreeMap<String, Management4>();
		Map<String, Management5> k5Map = new TreeMap<String, Management5>();
		Map<String, Management6> k6Map = new TreeMap<String, Management6>();
		Map<String, Remark> remMap = new TreeMap<String, Remark>();
		Map<String, Employee> empMap = new TreeMap<String, Employee>();
		Map<String, Customer> cusMap = new TreeMap<String, Customer>();
		Map<String, BigDecimal> rateMap = new TreeMap<String, BigDecimal>();

		// ���������ł̍\�z�����{
		setupDetails(slipList, includeBalance);

		for (Slip slip : slipList) {

			List<SWK_DTL> dtlList = slip.getDetails();

			try {

				// ����
				for (SWK_DTL dtl : dtlList) {
					// ��� �č\�znull�̏ꍇ���O�C�����
					String kCompany = dtl.getSWK_K_KAI_CODE();
					if (Util.isNullOrEmpty(kCompany)) {
						dtl.setAppropriateCompany(getCompany());
						kCompany = getCompanyCode();
					}

					if (isGroupAccounting()) {
						if (dtl.getSWK_TUKE_KBN() == 2) {
							// 2
						} else {
							// 0, 1
							kCompany = dtl.getKAI_CODE();
						}
					}

					Company comp = dtl.getAppropriateCompany();
					AccountConfig ac = comp == null ? null : comp.getAccountConfig();
					Currency keyCur = ac == null ? null : ac.getKeyCurrency();
					Currency cur = dtl.getCurrency();
					int curDecPint = cur == null ? 0 : cur.getDecimalPoint();

					Item item = dtl.getItem();
					if (item != null) {
						item = item.getPreferedItem();
					}
					// �Ǘ��P
					String k1Code = dtl.getSWK_KNR_CODE_1();
					if (!Util.isNullOrEmpty(k1Code) && item != null && item.isUseManagement1()) {
						Management1 k1 = k1Map.get(kCompany + KEY_SEP + k1Code);
						if (k1 == null) {
							k1Con.setCompanyCode(kCompany);
							k1Con.setCode(k1Code);
							List<Management1> k1s = knr1Mng.get(k1Con);
							if (!k1s.isEmpty()) {
								k1 = k1s.get(0);
								k1Map.put(kCompany + KEY_SEP + k1Code, k1);
							}
						}
						dtl.setManagement1(k1);
					} else {
						dtl.setManagement1(null);
					}
					// �Ǘ�2
					String k2Code = dtl.getSWK_KNR_CODE_2();
					if (!Util.isNullOrEmpty(k2Code) && item != null && item.isUseManagement2()) {
						Management2 k2 = k2Map.get(kCompany + KEY_SEP + k2Code);
						if (k2 == null) {
							k2Con.setCompanyCode(kCompany);
							k2Con.setCode(k2Code);
							List<Management2> k2s = knr2Mng.get(k2Con);
							if (!k2s.isEmpty()) {
								k2 = k2s.get(0);
								k2Map.put(kCompany + KEY_SEP + k2Code, k2);
							}
						}
						dtl.setManagement2(k2);
					} else {
						dtl.setManagement2(null);
					}
					// �Ǘ�3
					String k3Code = dtl.getSWK_KNR_CODE_3();
					if (!Util.isNullOrEmpty(k3Code) && item != null && item.isUseManagement3()) {
						Management3 k3 = k3Map.get(kCompany + KEY_SEP + k3Code);
						if (k3 == null) {
							k3Con.setCompanyCode(kCompany);
							k3Con.setCode(k3Code);
							List<Management3> k3s = knr3Mng.get(k3Con);
							if (!k3s.isEmpty()) {
								k3 = k3s.get(0);
								k3Map.put(kCompany + KEY_SEP + k3Code, k3);
							}
						}
						dtl.setManagement3(k3);
					} else {
						dtl.setManagement3(null);
					}
					// �Ǘ�4
					String k4Code = dtl.getSWK_KNR_CODE_4();
					if (!Util.isNullOrEmpty(k4Code) && item != null && item.isUseManagement4()) {
						Management4 k4 = k4Map.get(kCompany + KEY_SEP + k4Code);
						if (k4 == null) {
							k4Con.setCompanyCode(kCompany);
							k4Con.setCode(k4Code);
							List<Management4> k4s = knr4Mng.get(k4Con);
							if (!k4s.isEmpty()) {
								k4 = k4s.get(0);
								k4Map.put(kCompany + KEY_SEP + k4Code, k4);
							}
						}
						dtl.setManagement4(k4);
					} else {
						dtl.setManagement4(null);
					}
					// �Ǘ�5
					String k5Code = dtl.getSWK_KNR_CODE_5();
					if (!Util.isNullOrEmpty(k5Code) && item != null && item.isUseManagement5()) {
						Management5 k5 = k5Map.get(kCompany + KEY_SEP + k5Code);
						if (k5 == null) {
							k5Con.setCompanyCode(kCompany);
							k5Con.setCode(k5Code);
							List<Management5> k5s = knr5Mng.get(k5Con);
							if (!k5s.isEmpty()) {
								k5 = k5s.get(0);
								k5Map.put(kCompany + KEY_SEP + k5Code, k5);
							}
						}
						dtl.setManagement5(k5);
					} else {
						dtl.setManagement5(null);
					}
					// �Ǘ�6
					String k6Code = dtl.getSWK_KNR_CODE_6();
					if (!Util.isNullOrEmpty(k6Code) && item != null && item.isUseManagement6()) {
						Management6 k6 = k6Map.get(kCompany + KEY_SEP + k6Code);
						if (k6 == null) {
							k6Con.setCompanyCode(kCompany);
							k6Con.setCode(k6Code);
							List<Management6> k6s = knr6Mng.get(k6Con);
							if (!k6s.isEmpty()) {
								k6 = k6s.get(0);
								k6Map.put(kCompany + KEY_SEP + k6Code, k6);
							}
						}
						dtl.setManagement6(k6);
					} else {
						dtl.setManagement6(null);
					}
					// �����
					String cusCode = dtl.getSWK_TRI_CODE();
					if (!Util.isNullOrEmpty(cusCode) && item != null && item.isUseCustomer()) {
						Customer cus = cusMap.get(kCompany + KEY_SEP + cusCode);
						if (cus == null) {
							cusCon.setCompanyCode(kCompany);
							cusCon.setCode(cusCode);
							List<Customer> customers = cusMng.get(cusCon);
							if (!customers.isEmpty()) {
								cus = customers.get(0);
								cusMap.put(kCompany + KEY_SEP + cusCode, cus);
							}
						}
						dtl.setCustomer(cus);
					} else {
						dtl.setCustomer(null);
					}
					// �Ј�
					String empCode = dtl.getSWK_EMP_CODE();
					if (!Util.isNullOrEmpty(empCode) && item != null && item.isUseEmployee()) {
						Employee emp = empMap.get(kCompany + KEY_SEP + empCode);
						if (emp == null) {
							empCon.setCompanyCode(kCompany);
							empCon.setCode(empCode);
							List<Employee> employees = empMana.get(empCon);
							if (!employees.isEmpty()) {
								emp = employees.get(0);
								empMap.put(kCompany + KEY_SEP + empCode, emp);
							}
						}
						dtl.setEmployee(emp);
					} else {
						dtl.setEmployee(null);
					}
					// �E�v
					String remCode = dtl.getSWK_GYO_TEK_CODE();
					if (!Util.isNullOrEmpty(remCode)) {
						Remark rem = remMap.get(kCompany + KEY_SEP + remCode);
						if (rem == null) {
							remCon.setCompanyCode(kCompany);
							remCon.setSlipRowRemark(true);
							remCon.setCode(remCode);
							List<Remark> remarks = remMng.get(remCon);
							if (!remarks.isEmpty()) {
								rem = remarks.get(0);
								remMap.put(kCompany + KEY_SEP + remCode, rem);
							}
						}
						if (rem == null) {
							dtl.setSWK_GYO_TEK_CODE(null);
						} else if (Util.isNullOrEmpty(dtl.getSWK_GYO_TEK())) {
							dtl.setSWK_GYO_TEK(rem.getName());
						}
					}
					// �ŃZ�b�g�A�b�v
					ConsumptionTax tx = dtl.getTax();
					if (tx != null) {
						if (DecimalUtil.isNullOrZero(tx.getRate())) {
							dtl.setTaxCalcType(TaxCalcType.NONE);
						}
					} else {
						dtl.setTaxCalcType(TaxCalcType.NONE);
						dtl.setSWK_ZEI_KIN(BigDecimal.ZERO);
						dtl.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);
					}

					if (recalc) {
						// �K�v�ȏꍇ�̂ݍČv�Z

						// ���[�g�n�Z�b�g�A�b�v
						if (DecimalUtil.isNullOrZero(dtl.getSWK_CUR_RATE())) {
							// ���[�g��0
							if (keyCur != null && Util.equals(keyCur.getCode(), dtl.getSWK_CUR_CODE())) {
								// ��ʉ݂Ɠ����ꍇ
								dtl.setSWK_CUR_RATE(BigDecimal.ONE);
							} else if (!Util.isNullOrEmpty(dtl.getSWK_CUR_CODE())) {
								// ��ʉ݈ȊO�̒ʉ݂��o�^
								Date occurDate = dtl.getHAS_DATE() == null ? getNowYMD() : dtl.getHAS_DATE();
								String key = dtl.getKAI_CODE() + "<>" + dtl.getSWK_CUR_CODE() + "<>" + occurDate;
								BigDecimal rate = rateMap.get(key);
								if (rate == null && cur != null && occurDate != null) {
									rate = rateMgr.getRate(cur, occurDate);
									rateMap.put(key, rate);
								}
								dtl.setSWK_CUR_RATE(rate);
							}
						}

						int keyCurDigit = keyCur == null ? 0 : keyCur.getDecimalPoint();

						// ���z�`�F�b�N�F��ʉ݂Œ[�������݂���ꍇ�͋����I��ZERO
						if (keyCur != null && Util.equals(keyCur.getCode(), dtl.getSWK_CUR_CODE())) {
							// ���͋��z
							if (!DecimalUtil.isNullOrZero(dtl.getSWK_IN_KIN())) {
								BigDecimal amount = dtl.getSWK_IN_KIN();
								int keyScale = amount.scale();
								if (keyScale > keyCurDigit) {
									// �����_�ȉ��̃`�F�b�N�F���ߌ��������擾��0�ȊO�Ȃ�G���[
									String valu = StringUtil.rightBX(amount.toPlainString(), keyScale - keyCurDigit);
									if (!DecimalUtil.isNullOrZero(valu)) {
										dtl.setSWK_IN_KIN(BigDecimal.ZERO);
										dtl.setSWK_KIN(BigDecimal.ZERO);
									}
								}
							}
						}
						// ���z�`�F�b�N�F�O���ʉ݂Œ[�������݂���ꍇ�͋����I��ZERO
						if (keyCur != null && !Util.equals(keyCur.getCode(), dtl.getSWK_CUR_CODE())) {
							// ���͋��z
							if (!DecimalUtil.isNullOrZero(dtl.getSWK_IN_KIN())) {
								BigDecimal amount = dtl.getSWK_IN_KIN();
								int keyScale = amount.scale();
								if (keyScale > curDecPint) {
									// �����_�ȉ��̃`�F�b�N�F���ߌ��������擾��0�ȊO�Ȃ�G���[
									String valu = StringUtil.rightBX(amount.toPlainString(), keyScale - curDecPint);
									if (!DecimalUtil.isNullOrZero(valu)) {
										dtl.setSWK_IN_KIN(BigDecimal.ZERO);
										dtl.setSWK_KIN(BigDecimal.ZERO);
									}
								}
							}
						}

						// ���z�Z�b�g�A�b�v
						if (DecimalUtil.isNullOrZero(dtl.getSWK_KIN())) {
							// ���z��0�̏ꍇ
							if (keyCur != null && Util.equals(keyCur.getCode(), dtl.getSWK_CUR_CODE())) {
								// ��ʉ݂Ɠ����ꍇ ���͋��z�Ɠ��z�����
								dtl.setSWK_KIN(dtl.getSWK_IN_KIN());
							} else if (cur != null) {
								// ��ʉ݂ƈقȂ�ꍇ
								exc.setAccountConfig(ac);
								exc.setRatePow(cur.getRatePow());
								exc.setDigit(keyCurDigit);
								exc.setForeignAmount(dtl.getSWK_IN_KIN());
								exc.setRate(dtl.getSWK_CUR_RATE());
								BigDecimal kin = calc.exchangeKeyAmount(exc);
								dtl.setSWK_KIN(kin);
							}
						}

						// �Ŋz�Z�b�g�A�b�v �F��ɏ���ł͍Čv�Z����
						if (tx != null && tx.getTaxType() != TaxType.NOT) {
							taxCalc.setAmount(dtl.getSWK_IN_KIN());
							taxCalc.setDigit(curDecPint);
							taxCalc.setInside(dtl.getSWK_ZEI_KBN() == ZEI_KBN.TAX_IN);
							taxCalc.setPaymentFunction(ac.getPaymentFunction());
							taxCalc.setReceivingFunction(ac.getReceivingFunction());
							taxCalc.setTax(tx);
							BigDecimal taxAmt = calc.calculateTax(taxCalc);
							dtl.setSWK_IN_ZEI_KIN(taxAmt);
						} else {
							dtl.setSWK_ZEI_KBN(ZEI_KBN.TAX_NONE);
							dtl.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);
						}

						// �Ŋz�Z�b�g�A�b�v
						if (keyCur != null && !Util.equals(keyCur.getCode(), dtl.getSWK_CUR_CODE())) {
							// �O�݂͏�ɍČv�Z������
							dtl.setSWK_ZEI_KIN(BigDecimal.ZERO);
						} else {
							// ��ʉ݂͂��̂܂ܓ��͐Ŋz
							dtl.setSWK_ZEI_KIN(dtl.getSWK_IN_ZEI_KIN());
						}
						if (DecimalUtil.isNullOrZero(dtl.getSWK_ZEI_KIN())) {
							if (keyCur != null && Util.equals(keyCur.getCode(), dtl.getSWK_CUR_CODE())) {
								// ��ʉ݂Ɠ����ꍇ ���͏���ŋ��z�Ɠ��z�����
								dtl.setSWK_ZEI_KIN(dtl.getSWK_IN_ZEI_KIN());
							} else {
								// ���z��0�̏ꍇ
								if (tx != null && tx.getTaxType() != TaxType.NOT) {
									taxCalc.setAmount(dtl.getSWK_KIN());
									taxCalc.setDigit(keyCurDigit);
									taxCalc.setInside(dtl.getSWK_ZEI_KBN() == ZEI_KBN.TAX_IN);
									taxCalc.setPaymentFunction(ac.getPaymentFunction());
									taxCalc.setReceivingFunction(ac.getReceivingFunction());
									taxCalc.setTax(tx);
									BigDecimal taxAmt = calc.calculateTax(taxCalc);
									dtl.setSWK_ZEI_KIN(taxAmt);
								} else {
									dtl.setSWK_ZEI_KBN(ZEI_KBN.TAX_NONE);
									dtl.setSWK_ZEI_KIN(BigDecimal.ZERO);
								}
							}
						}

						// ����Ōv�Z�敪�̕s�������������
						if (dtl.getSWK_ZEI_KBN() != ZEI_KBN.TAX_IN && dtl.getSWK_ZEI_KBN() != ZEI_KBN.TAX_OUT
							&& dtl.getSWK_ZEI_KBN() != ZEI_KBN.TAX_NONE) {
							dtl.setSWK_ZEI_KBN(ZEI_KBN.TAX_NONE);
							dtl.setSWK_ZEI_KIN(BigDecimal.ZERO);
							dtl.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);
						}

						// ���v����
						if (ac == null) {
							continue;
						}
						if (ac.getNonAccounting1() == NonAccountingDivision.NONE) {
							dtl.setSWK_HM_1(null);
						} else if (item != null && !item.isUseNonAccount1()) {
							dtl.setSWK_HM_1(null);
						} else if (ac.getNonAccounting1() == NonAccountingDivision.NUMBER) {
							if (!Util.isNullOrEmpty(dtl.getSWK_HM_1())) {
								dtl.setSWK_HM_1(NumberFormatUtil.formatNumber(dtl.getSWK_HM_1()));
							}
						} else if (ac.getNonAccounting1() == NonAccountingDivision.YMD_DATE) {
							Date val = DateUtil.toDateNE(dtl.getSWK_HM_1());
							dtl.setSWK_HM_1(DateUtil.toYMDString(val));
						} else if (ac.getNonAccounting1() == NonAccountingDivision.YMDHM_DATE) {
							Date val = DateUtil.toDateNE(dtl.getSWK_HM_1());
							dtl.setSWK_HM_1(DateUtil.toYMDHMString(val));
						}
						if (ac.getNonAccounting2() == NonAccountingDivision.NONE) {
							dtl.setSWK_HM_2(null);
						} else if (item != null && !item.isUseNonAccount2()) {
							dtl.setSWK_HM_2(null);
						} else if (ac.getNonAccounting2() == NonAccountingDivision.NUMBER) {
							if (!Util.isNullOrEmpty(dtl.getSWK_HM_2())) {
								dtl.setSWK_HM_2(NumberFormatUtil.formatNumber(dtl.getSWK_HM_2()));
							}
						} else if (ac.getNonAccounting2() == NonAccountingDivision.YMD_DATE) {
							Date val = DateUtil.toDateNE(dtl.getSWK_HM_2());
							dtl.setSWK_HM_2(DateUtil.toYMDString(val));
						} else if (ac.getNonAccounting2() == NonAccountingDivision.YMDHM_DATE) {
							Date val = DateUtil.toDateNE(dtl.getSWK_HM_2());
							dtl.setSWK_HM_2(DateUtil.toYMDHMString(val));
						}
						if (ac.getNonAccounting3() == NonAccountingDivision.NONE) {
							dtl.setSWK_HM_3(null);
						} else if (item != null && !item.isUseNonAccount3()) {
							dtl.setSWK_HM_3(null);
						} else if (ac.getNonAccounting3() == NonAccountingDivision.NUMBER) {
							if (!Util.isNullOrEmpty(dtl.getSWK_HM_3())) {
								dtl.setSWK_HM_3(NumberFormatUtil.formatNumber(dtl.getSWK_HM_3()));
							}
						} else if (ac.getNonAccounting3() == NonAccountingDivision.YMD_DATE) {
							Date val = DateUtil.toDateNE(dtl.getSWK_HM_3());
							dtl.setSWK_HM_3(DateUtil.toYMDString(val));
						} else if (ac.getNonAccounting3() == NonAccountingDivision.YMDHM_DATE) {
							Date val = DateUtil.toDateNE(dtl.getSWK_HM_3());
							dtl.setSWK_HM_3(DateUtil.toYMDHMString(val));
						}
					}
				}
			} catch (Exception e) {
				//
				throw new TException(e);
			}

		}
	}

	/**
	 * BS��������d��̐ݒ�
	 * 
	 * @param slip
	 * @throws TException
	 */
	public void setupBsDetails(Slip slip) throws TException {
		// BS��������d��̐ݒ�
		BSEraseManager bsManager = (BSEraseManager) getComponent(BSEraseManager.class);
		BSEraseCondition condition = new BSEraseCondition();
		if (!isBsUseKCompany || !getCompany().getAccountConfig().isUseGroupAccount()) {
			// �v���З��p���ĂȂ��A�܂��̓O���[�v��v�ł͂Ȃ��ꍇ�A��ЃR�[�h�����t��
			condition.setCompanyCode(slip.getCompanyCode());
		}
		condition.setSlipNo(slip.getSlipNo()); // ������̓`�[�ԍ�
		condition.setForSlipModify(true); // �������擾�t���O

		List<SWK_DTL> list = bsManager.get(condition);
		if (list == null || list.isEmpty()) {
			return;
		}

		for (SWK_DTL forward : slip.getDetails(CurrencyType.KEY)) {

			SWK_DTL base = null;
			for (int i = 0; i < list.size(); i++) {
				if (isPair(list.get(i), forward)) {
					base = list.get(i);
					break;
				}
			}

			if (base != null) {
				// BS�d��
				forward.setBsDetail(base);
				list.remove(base);
			}
		}
	}

	/**
	 * �������Ə����悪��v���邩�ǂ���
	 * 
	 * @param base ������
	 * @param forward ������
	 * @return true:���E��v�d��
	 */
	protected boolean isPair(SWK_DTL base, SWK_DTL forward) {
		if (base == null || forward == null) {
			return false;
		}

		if (Util.equals(base.getSWK_SOUSAI_DEN_NO(), forward.getSWK_DEN_NO())
			&& Util.avoidNullAsInt(base.getSWK_SOUSAI_GYO_NO()) == Util.avoidNullAsInt(forward.getSWK_GYO_NO())) {
			return true;
		}

		return false;
	}

	/**
	 * Slip��SlipBook�ɕϊ�����
	 * 
	 * @param slip
	 * @return SlipBook
	 */
	public SlipBook toBook(Slip slip) {
		SlipToBookConverter converter = getSlipToBookConverter();
		if (converter != null) {
			converter.setCompany(getCompany());
			converter.setUser(getUser());
			converter.setProgramCode(getProgramCode());
			converter.setNow(getNow());
		}
		SlipBook book = converter.toBook(slip);
		return book;
	}

	/**
	 * Slip��SlipBook�ɕϊ�����N���X��Ԃ��B
	 * 
	 * @return Slip��SlipBook�ɕϊ�����N���X
	 */
	public abstract SlipToBookConverter getSlipToBookConverter();

	/**
	 * ���C�A�E�g�N���X�̃t�@�N�g�����\�b�h
	 * 
	 * @param langCode
	 * @return ���C�A�E�g�̎����N���X
	 * @throws TException
	 */
	public abstract SlipLayout getLayout(String langCode) throws TException;

	/**
	 * �`�[���F�N���X�̃t�@�N�g�����\�b�h
	 * 
	 * @return �`�[���F�̎����N���X
	 */
	public abstract SlipApprove getSlipApprove();
}
