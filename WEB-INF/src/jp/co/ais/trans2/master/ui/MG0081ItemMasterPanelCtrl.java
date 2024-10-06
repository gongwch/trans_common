package jp.co.ais.trans2.master.ui;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.model.ui.*;
import jp.co.ais.trans2.model.item.*;

/**
 * �Ȗڃ}�X�^�R���g���[��<br>
 * �q�C���x�v�Z�t���O�ǉ���
 */
public class MG0081ItemMasterPanelCtrl extends MG0080ItemMasterPanelCtrl {

	/**
	 * �w����ʂ̃t�@�N�g���B�V�K�Ɏw����ʂ𐶐����A�C�x���g���`����B
	 */
	@Override
	protected void createMainView() {
		mainView = new MG0081ItemMasterPanel(getCompany());
		addMainViewEvent();
	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	@Override
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MG0081ItemMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent();
		addSubViewEvent();
	}

	/**
	 * �ҏW��ʂ�����������
	 * 
	 * @param mode_ ���샂�[�h�B
	 * @param bean �ȖځB�C���A���ʂ̏ꍇ�͓��Y�Ȗڏ���ҏW��ʂɃZ�b�g����B
	 */
	@Override
	protected void initEditView(Mode mode_, Item bean) {
		super.initEditView(mode_, bean);

		switch (mode_) {
			case COPY:
			case MODIFY:
				// �ҏW

				if (bean.getItemSumType() == ItemSumType.INPUT) {
					((TItemStatusVoyageUnit) editView.chk).chkVoyage.setSelected(bean.isUseVoyageCalculation());
				}
		}
	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ�Ȗڂ�Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ�Ȗ�
	 */
	@Override
	protected Item getInputedItem() {
		Item item = super.getInputedItem();

		if (item.getItemSumType() == ItemSumType.INPUT) {
			item.setUseVoyageCalculation(((TItemStatusVoyageUnit) editView.chk).chkVoyage.isSelected());
		}

		return item;
	}

	/**
	 * �ҏW���[�W�v][���o]�{�^������
	 */
	@Override
	protected void btnSum_Click() {
		super.btnSum_Click();

		((TItemStatusVoyageUnit) editView.chk).chkVoyage.setEnabled(false);
		((TItemStatusVoyageUnit) editView.chk).chkVoyage.setSelected(false);
	}

	/**
	 * �Ȗڏ����ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param item �Ȗڏ��
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ�Ȗڏ��
	 */
	@Override
	protected List<Object> getRowData(Item item) {
		List<Object> list = new ArrayList<Object>();

		list.add(item.getCode()); // �ȖڃR�[�h
		list.add(item.getName()); // �Ȗږ���
		list.add(item.getNames()); // �Ȗڗ���
		list.add(item.getNamek()); // �Ȗڌ�������
		list.add(getWord(ItemSumType.getName(item.getItemSumType()))); // �W�v�敪
		list.add(getWord(ItemType.getName(item.getItemType()))); // �Ȗڎ��
		list.add(getWord(Dc.getName(item.getDc()))); // �ݎ؋敪

		if (item.getItemSumType() == ItemSumType.INPUT) {
			list.add(item.hasSubItem() ? getWord("C00006") : getWord("C00412")); // �⏕�敪
			list.add(item.getFixedDepartmentCode()); // �Œ蕔�庰��
			list.add(item.getConsumptionTax().getCode());// ����ŃR�[�h
			list.add(getWord(GLType.getName(item.getGlType()))); // GL�Ȗڐ���敪
			list.add(getWord(APType.getName(item.getApType()))); // AP�Ȗڐ���敪
			list.add(getWord(ARType.getName(item.getArType()))); // AR�Ȗڐ���敪
			list.add(getWord(BGType.getName(item.getBgType()))); // BG�Ȗڐ���敪
			list.add(getWord((CustomerType.getName(item.getClientType())))); // �������̓t���O
			list.add(getWord(getDivisionName(item.isDoesOffsetItem()))); // ���E�Ȗڐ���敪
			list.add(getWord(getDivisionName(item.isDoesBsOffset()))); // BS��������敪
			list.add(getWord(EvaluationMethod.getName(item.getEvaluationMethod()))); // �]���֑Ώۃt���O
			list.add(getWord(getBoo(item.isUseInputCashFlowSlip()))); // �����`�[���̓t���O
			list.add(getWord(getBoo(item.isUseOutputCashFlowSlip()))); // �o���`�[���̓t���O
			list.add(getWord(getBoo(item.isUseTransferSlip()))); // �U�֓`�[���̓t���O
			list.add(getWord(getBoo(item.isUseExpenseSettlementSlip()))); // �o��Z�`�[���̓t���O
			list.add(getWord(getBoo(item.isUsePaymentAppropriateSlip()))); // ���v��`�[���̓t���O
			list.add(getWord(getBoo(item.isUseReceivableAppropriateSlip()))); // ���v��`�[���̓t���O
			list.add(getWord(getBoo(item.isUseReceivableErasingSlip()))); // �������`�[���̓t���O
			list.add(getWord(getBoo(item.isUseAssetsEntrySlip()))); // ���Y�v��`�[���̓t���O
			list.add(getWord(getBoo(item.isUsePaymentRequestSlip()))); // �x���˗��`�[���̓t���O
			list.add(getWord(getBoo(item.isUseForeignCurrency()))); // ���ʉݓ��̓t���O
			list.add(getWord(getBoo1(item.isUseEmployee()))); // �Ј����̓t���O
			list.add(getWord(getBoo1(item.isUseManagement1()))); // �Ǘ��P���̓t���O
			list.add(getWord(getBoo1(item.isUseManagement2()))); // �Ǘ�2���̓t���O
			list.add(getWord(getBoo1(item.isUseManagement3()))); // �Ǘ�3���̓t���O
			list.add(getWord(getBoo1(item.isUseManagement4()))); // �Ǘ�4���̓t���O
			list.add(getWord(getBoo1(item.isUseManagement5()))); // �Ǘ�5���̓t���O
			list.add(getWord(getBoo1(item.isUseManagement6()))); // �Ǘ�6���̓t���O
			list.add(getWord(getBoo(item.isUseNonAccount1()))); // ���v�P���̓t���O
			list.add(getWord(getBoo(item.isUseNonAccount2()))); // ���v2���̓t���O
			list.add(getWord(getBoo(item.isUseNonAccount3()))); // ���v3���̓t���O
			list.add(getWord(getBoo(item.isUseSalesTaxation()))); // ����ېœ��̓t���O
			list.add(getWord(getBoo(item.isUsePurchaseTaxation()))); // �d���ېœ��̓t���O
			list.add(getWord(getDivisionName(item.isUseVoyageCalculation()))); // �q�C���x�v�Z�t���O
			list.add(getWord(getBoo(item.isUseOccurDate()))); // �������t���O
			list.add(DateUtil.toYMDString(item.getDateFrom())); // �J�n�N����
			list.add(DateUtil.toYMDString(item.getDateTo())); // �I���N����
		} else {
			list.add(""); // �⏕�敪
			list.add(""); // �Œ蕔�庰��
			list.add(""); // ����ŃR�[�h
			list.add(""); // GL�Ȗڐ���敪
			list.add(""); // AP�Ȗڐ���敪
			list.add(""); // AR�Ȗڐ���敪
			list.add(""); // BG�Ȗڐ���敪
			list.add(""); // �������̓t���O
			list.add(""); // ���E�Ȗڐ���敪
			list.add(""); // BS��������敪
			list.add(""); // �]���֑Ώۃt���O
			list.add(""); // �����`�[���̓t���O
			list.add(""); // �o���`�[���̓t���O
			list.add(""); // �U�֓`�[���̓t���O
			list.add(""); // �o��Z�`�[���̓t���O
			list.add(""); // ���v��`�[���̓t���O
			list.add(""); // ���v��`�[���̓t���O
			list.add(""); // �������`�[���̓t���O
			list.add(""); // ���Y�v��`�[���̓t���O
			list.add(""); // �x���˗��`�[���̓t���O
			list.add(""); // ���ʉݓ��̓t���O
			list.add(""); // �Ј����̓t���O
			list.add(""); // �Ǘ��P���̓t���O
			list.add(""); // �Ǘ�2���̓t���O
			list.add(""); // �Ǘ�3���̓t���O
			list.add(""); // �Ǘ�4���̓t���O
			list.add(""); // �Ǘ�5���̓t���O
			list.add(""); // �Ǘ�6���̓t���O
			list.add(""); // ���v�P���̓t���O
			list.add(""); // ���v2���̓t���O
			list.add(""); // ���v3���̓t���O
			list.add(""); // ����ېœ��̓t���O
			list.add(""); // �d���ېœ��̓t���O
			list.add(""); // �q�C���x�v�Z�t���O
			list.add(""); // �������t���O
			list.add(DateUtil.toYMDString(item.getDateFrom())); // �J�n�N����
			list.add(DateUtil.toYMDString(item.getDateTo())); // �I���N����

		}

		return list;
	}
}
