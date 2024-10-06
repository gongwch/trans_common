package jp.co.ais.trans2.model.slip;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;

/**
 * �`�[�}�l�[�W�� �`�[�֘A�̑���i�擾�E�o�^�E�폜�Ȃǁj�𐧌䂷��N���X
 */
public interface SlipManager {

	/**
	 * �`�[���\�z����.<br>
	 * �t�ցA����Ŏd��A�@�\�ʉݎd��
	 * 
	 * @param slip �`�[�N���X
	 * @return �\�z�����`�[(�t�֐�܂�)
	 * @throws TException
	 */
	public List<Slip> buildSlip(Slip slip) throws TException;

	/**
	 * �`�[���N�[����
	 * 
	 * @param slip �`�[�N���X
	 * @throws TException
	 */
	public void entry(Slip slip) throws TException;

	/**
	 * �`�[���\�z���ēo�^����.<br>
	 * �t�ցA����Ŏd��A�@�\�ʉݎd��A�폜���� ����
	 * 
	 * @param slip �`�[�N���X
	 * @return �\�z�����`�[(�t�֐�܂�)
	 * @throws TException
	 */
	public List<Slip> buildAndEntry(Slip slip) throws TException;

	/**
	 * �`�[�ԍ����̔Ԃ��A�`�[�ɃZ�b�g����
	 * 
	 * @param slip
	 * @throws TException
	 */
	public void setSlipNo(Slip slip) throws TException;

	/**
	 * �`�[�̃`�F�b�N
	 * 
	 * @param slip �`�[
	 * @return �G���[���b�Z�[�W���X�g
	 * @throws TException
	 */
	public List<Message> checkSlipError(Slip slip) throws TException;

	/**
	 * �`�[���폜����
	 * 
	 * @param slip �Ώۓ`�[
	 */
	public void delete(Slip slip);

	/**
	 * �`�[���폜����
	 * 
	 * @param slip �Ώۓ`�[
	 * @param isSaveHistory ������ۑ����邩�ǂ���
	 * @param isSaveDelHistory �폜������ۑ����邩�ǂ���
	 */
	public void delete(Slip slip, boolean isSaveHistory, boolean isSaveDelHistory);

	/**
	 * �`�[���폜����
	 * 
	 * @param slipNo �Ώۓ`�[�ԍ�
	 * @param slipType �Ώۓ`�[�ԍ��̓`�[���
	 */
	public void delete(String slipNo, String slipType);

	/**
	 * �`�[���폜����
	 * 
	 * @param slipNo �Ώۓ`�[�ԍ�
	 * @param slipType �Ώۓ`�[�ԍ��̓`�[���
	 * @param isSaveHistory ������ۑ����邩�ǂ���
	 */
	public void delete(String slipNo, String slipType, boolean isSaveHistory);

	/**
	 * �`�[���폜����
	 * 
	 * @param slipNo �Ώۓ`�[�ԍ�
	 * @param slipType �Ώۓ`�[�ԍ��̓`�[���
	 * @param isSaveHistory ������ۑ����邩�ǂ���
	 * @param isSaveDelHistory �폜������ۑ����邩�ǂ���
	 */
	public void delete(String slipNo, String slipType, boolean isSaveHistory, boolean isSaveDelHistory);

	/**
	 * �`�[�r��
	 * 
	 * @param slip �`�[
	 * @throws TException
	 */
	public void lock(Slip slip) throws TException;

	/**
	 * �`�[�֘A�e�[�u�����b�N
	 * 
	 * @throws TException
	 */
	public void lockSlipTable() throws TException;

	/**
	 * �`�[�r������
	 * 
	 * @param slip �`�[
	 * @throws TException
	 */
	public void unlock(Slip slip) throws TException;

	/**
	 * �`�[�r������
	 * 
	 * @param slipType �`�[���
	 * @throws TException
	 */
	public void unlockAll(String slipType) throws TException;

	/**
	 * �`�[���\�z����.<br>
	 * �e��l�ݒ�A����Ŏd��
	 * 
	 * @param slip �`�[�N���X
	 * @return �\�z�����`�[�p�^�[��
	 * @throws TException
	 */
	public List<Slip> buildSlipPattern(Slip slip) throws TException;

	/**
	 * �`�[�p�^�[�����N�[����
	 * 
	 * @param slip �`�[�N���X
	 * @throws TException
	 */
	public void entryPattern(Slip slip) throws TException;

	/**
	 * �`�[�p�^�[�����\�z���ēo�^����.<br>
	 * ����Ŏd��
	 * 
	 * @param motoSlip �`�[�N���X
	 * @return �\�z�����`�[(�t�֐�܂�)
	 * @throws TException
	 */
	public List<Slip> buildAndEntryPattern(Slip motoSlip) throws TException;

	/**
	 * �`�[�p�^�[�����폜����
	 * 
	 * @param slip �Ώۓ`�[
	 * @throws TException
	 */
	public void deletePattern(Slip slip) throws TException;

	/**
	 * �`�[�p�^�[���r��
	 * 
	 * @param slip �`�[
	 * @throws TException
	 */
	public void lockPattern(Slip slip) throws TException;

	/**
	 * �`�[�p�^�[���r������
	 * 
	 * @param slip �`�[
	 * @throws TException
	 */
	public void unlockPattern(Slip slip) throws TException;

	/**
	 * �`�[�p�^�[���r������
	 * 
	 * @param slipType �`�[���
	 * @throws TException
	 */
	public void unlockPatternAll(String slipType) throws TException;

	/**
	 * �w�b�_�f�[�^�擾
	 * 
	 * @param param ����
	 * @return �w�b�_���X�g
	 */
	public List<SWK_HDR> getHeader(SlipCondition param);

	/**
	 * �w�b�_�f�[�^�̃G�N�Z���擾
	 * 
	 * @param param ����
	 * @return �w�b�_���X�g�̃G�N�Z��
	 * @throws TException
	 */
	public byte[] getHeaderExcel(SlipCondition param) throws TException;

	/**
	 * ���׃f�[�^�擾
	 * 
	 * @param param ����
	 * @return �d�󃊃X�g
	 */
	public List<SWK_DTL> getDetails(SlipCondition param);

	/**
	 * �w�b�_�f�[�^�擾
	 * 
	 * @param param ����
	 * @return �w�b�_���X�g
	 */
	public List<SWK_HDR> getPatternHeader(SlipCondition param);

	/**
	 * ���׃f�[�^�擾
	 * 
	 * @param param ����
	 * @return �d�󃊃X�g
	 */
	public List<SWK_DTL> getPatternDetails(SlipCondition param);

	/**
	 * �w�b�_�f�[�^�擾
	 * 
	 * @param param ����
	 * @return �w�b�_���X�g
	 */
	public List<SWK_HDR> getHistoryHeader(SlipCondition param);

	/**
	 * ���׃f�[�^�擾
	 * 
	 * @param param ����
	 * @return �d�󃊃X�g
	 */
	public List<SWK_DTL> getHistoryDetails(SlipCondition param);

	/**
	 * �`�[�擾
	 * 
	 * @param param ����
	 * @return �`�[���X�g
	 */
	public List<Slip> getSlip(SlipCondition param);

	/**
	 * �w��w�b�_���`�[���擾����.
	 * 
	 * @param hdr �`�[
	 * @param condition
	 * @return �`�[
	 * @throws TException
	 */
	public Slip getSlip(SWK_HDR hdr, SlipCondition condition) throws TException;

	/**
	 * �w������̒�����擾����.
	 * 
	 * @param condition ����
	 * @return ���냊�X�g
	 */
	public List<SlipBooks> getSlipBooks(SlipCondition condition);

	/**
	 * �w��ԍ��̒�����擾����.
	 * 
	 * @param compCode ��ЃR�[�h
	 * @param slipNo �`�[�ԍ�
	 * @return ����
	 */
	public SlipBooks getSlipBooks(String compCode, String slipNo);

	/**
	 * �w��w�b�_���`�[�p�^�[�����擾����.
	 * 
	 * @param hdr �`�[
	 * @return �`�[
	 * @throws TException
	 */
	public Slip getPatternSlip(SWK_HDR hdr) throws TException;

	/**
	 * �`�[�p�^�[���擾
	 * 
	 * @param condition ����
	 * @return �`�[�p�^�[�����X�g
	 */
	public List<Slip> getPatternSlip(SlipCondition condition);

	/**
	 * �폜�`�[�擾
	 * 
	 * @param condition ����
	 * @return �폜�`�[���X�g
	 */
	public List<Slip> getHistorySlip(SlipCondition condition);

	/**
	 * �w������̒�����擾����.(����p)
	 * 
	 * @param condition ����
	 * @return ���냊�X�g
	 */
	public List<SlipBooks> getHistorySlipBooks(SlipCondition condition);

	/**
	 * �ꎞ�`�[���[��Ԃ�
	 * 
	 * @param tempSlip �ꎞ�`�[
	 * @return �ꎞ�`�[���[
	 * @throws TException
	 */
	public byte[] getTempSlipReport(Slip tempSlip) throws TException;

	/**
	 * �`�[���[��Ԃ�
	 * 
	 * @param slips �`�[���X�g(�t�֐���܂ޓ`�[�f�[�^)
	 * @return �`�[���[
	 * @throws TException
	 */
	public byte[] getReport(List<Slip> slips) throws TException;

	/**
	 * �`�[���[��Ԃ�
	 * 
	 * @param slip �`�[
	 * @return �`�[���[
	 * @throws TException
	 */
	public byte[] getReport(Slip slip) throws TException;

	/**
	 * �w���ЁA�`�[�ԍ��̓`�[���[��Ԃ��B<BR>
	 * �������������Ԃ����A�������낪�����ꍇ��IFRS����`�[��Ԃ��B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param slipNo �`�[�ԍ�
	 * @return �`�[���[
	 * @throws TException
	 */
	public byte[] getReport(String companyCode, String slipNo) throws TException;

	/**
	 * �w���ЁA�`�[�ԍ��̓`�[���[��Ԃ��B<BR>
	 * �������������Ԃ����A�������낪�����ꍇ��IFRS����`�[��Ԃ��B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param slipNoList �`�[�ԍ����X�g
	 * @return �`�[���[
	 * @throws TException
	 */
	public byte[] getReport(String companyCode, List<String> slipNoList) throws TException;

	/**
	 * �w��`�[�ԍ�(����)�̓`�[���[��Ԃ��B<BR>
	 * �������������Ԃ����A�������낪�����ꍇ��IFRS����`�[��Ԃ��B �t�֎d��̏ꍇ�A���ЁE�����З����̓`�[���o�͂���B
	 * 
	 * @param slipNoList �`�[�ԍ����X�g
	 * @return �`�[���[
	 * @throws TException
	 */
	public byte[] getReportBySlipNos(List<String> slipNoList) throws TException;

	/**
	 * �`�[Book��Ԃ�
	 * 
	 * @param slip
	 * @return �`�[Book
	 * @throws TException
	 */
	public SlipBook getReportBook(Slip slip) throws TException;

	/**
	 * �`�[�����F����
	 * 
	 * @param list ���F����`�[�̃��X�g
	 * @return ���F��̓`�[
	 * @throws TException
	 */
	public List<SlipDen> approveSlip(List<SlipDen> list) throws TException;

	/**
	 * �`�[��۔F����
	 * 
	 * @param list ���F����`�[�̃��X�g
	 * @return �۔F��̓`�[
	 * @throws TException
	 */
	public List<SlipDen> denySlip(List<SlipDen> list) throws TException;

	/**
	 * �`�[���F�i�o�����F�j��������
	 * 
	 * @param list ���F���������`�[�̃��X�g
	 * @return ���F�����̓`�[
	 * @throws TException
	 */
	public List<SlipDen> cancelApproveSlip(List<SlipDen> list) throws TException;

	/**
	 * �`�[�����F�i���ꏳ�F�j����
	 * 
	 * @param list ���F����`�[�̃��X�g
	 * @return ���F��̓`�[
	 * @throws TException
	 */
	public List<SlipDen> approveSlipForFieldState(List<SlipDen> list) throws TException;

	/**
	 * �`�[��۔F�i����۔F�j����
	 * 
	 * @param list ���F����`�[�̃��X�g
	 * @return �۔F��̓`�[
	 * @throws TException
	 */
	public List<SlipDen> denySlipForFieldState(List<SlipDen> list) throws TException;

	/**
	 * �`�[���F��������
	 * 
	 * @param list ���F���������`�[�̃��X�g
	 * @return ���F�����̓`�[
	 * @throws TException
	 */
	public List<SlipDen> cancelApproveSlipForFieldState(List<SlipDen> list) throws TException;

	/**
	 * �`�[���W�b�N�̎擾
	 * 
	 * @param slipType �`�[���
	 * @return �`�[���W�b�N
	 */
	public SlipLogic getSlipLogic(String slipType);

	/**
	 * �`�[���ׂ��\�z����.
	 * 
	 * @param slip �`�[
	 * @return �`�[
	 * @throws TException
	 */
	public Slip setupDetails(Slip slip) throws TException;

	/**
	 * �`�[���ׂ��\�z����.
	 * 
	 * @param slipList �����`�[��ʑz��
	 * @param includeBalance true:AP/AR/BS�c���Z�b�g�A�b�v
	 * @return �`�[
	 * @throws TException
	 */
	public List<Slip> setupDetails(List<Slip> slipList, boolean includeBalance) throws TException;

	/**
	 * �`�[���ׂ��\�z����<br>
	 * ���ڐ���Ɋ֌W�̂Ȃ��Ǘ��P�`�Ǘ��U��A���������Z�b�g<br>
	 * �܂��A����Ŋz��M�݋��z����̏ꍇ���̒l���₤
	 * 
	 * @param slip �`�[
	 * @return �`�[
	 * @throws TException
	 */
	public Slip setupDetailsOptional(Slip slip) throws TException;

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
	public Slip setupDetailsOptional(Slip slip, boolean recalc) throws TException;

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
	public Slip setupDetailsOptional(Slip slip, boolean recalc, boolean includeBalance) throws TException;

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
		throws TException;

	/**
	 * �����d��Ȗڂ̎擾
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param kind �Ȗڐ���敪
	 * @return �����d��Ȗ�
	 * @throws TException
	 */
	public AutoJornalAccount getAutoJornalAccount(String companyCode, AutoJornalAccountType kind) throws TException;

	/**
	 * �����d��Ȗڂ̎擾
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param type �Ȗڐ���敪
	 * @return �����d��Ȗ�
	 * @throws TException
	 */
	public AutoJornalAccount getAutoJornalAccount(String companyCode, int type) throws TException;

	/**
	 * �����d��Ȗڂ̎擾(�����w��)
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param kinds �Ȗڐ���敪
	 * @return �����d��Ȗ�
	 * @throws TException
	 */
	public List<AutoJornalAccount> getAutoJornalAccounts(String companyCode, AutoJornalAccountType... kinds)
		throws TException;

	/**
	 * �����d��Ȗڂ̎擾(�����w��)
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param types �Ȗڐ���敪
	 * @return �����d��Ȗ�
	 * @throws TException
	 */
	public List<AutoJornalAccount> getAutoJornalAccounts(String companyCode, int... types) throws TException;

	/**
	 * �폜�`�[���X�g�f�[�^��Ԃ�
	 * 
	 * @param condition ��������
	 * @return DeleteSlipListGetterBook
	 * @throws TException
	 */
	public DeleteSlipListBook getDeletedSlipListBook(SlipCondition condition) throws TException;

	/**
	 * �폜�`�[���X�g�f�[�^(���[)��Ԃ�
	 * 
	 * @param condition ��������
	 * @return byte
	 * @throws TException
	 */
	public byte[] getDeletedSlipListReport(SlipCondition condition) throws TException;

	/**
	 * �폜�`�[���X�g�f�[�^(�G�N�Z��)��Ԃ�
	 * 
	 * @param condition ��������
	 * @return byte
	 * @throws TException
	 */
	public byte[] getDeletedSlipListExcel(SlipCondition condition) throws TException;

	/**
	 * �폜�����̍폜
	 * 
	 * @param slip
	 * @throws TException
	 */
	public void deleteDelHistory(Slip slip) throws TException;

	/**
	 * �폜�����̓o�^
	 * 
	 * @param slip
	 * @throws TException
	 */
	public void entryDelHistory(Slip slip) throws TException;

	/**
	 * AP/AR�������̍X�V
	 * 
	 * @param slip
	 * @param kesiKbn
	 * @throws TException
	 */
	public void updateAPARInfo(Slip slip, int kesiKbn) throws TException;

	/**
	 * AP/AR�����d��̕�������
	 * 
	 * @param condition
	 * @throws TException
	 */
	public void restoreAPAR(SlipCondition condition) throws TException;

	/**
	 * ���׃��X�g����G�N�Z�����擾
	 * 
	 * @param list
	 * @return ���׃��X�g�G�N�Z��
	 * @throws TException
	 */
	public byte[] getDetailExcel(List<SWK_DTL> list) throws TException;

	/**
	 * �G�N�Z�����׃��X�g��bean�ɕϊ����ĕԋp
	 * 
	 * @param file
	 * @param slipType
	 * @return ����bean���X�g
	 * @throws TException
	 */
	public List<SWK_DTL> convertExcelToDetails(File file, SlipType slipType) throws TException;

	/**
	 * �ؓ����̍X�V
	 * 
	 * @param slip
	 * @param status
	 * @throws TException
	 */
	public void updateLMInfo(Slip slip, int status) throws TException;

	/**
	 * �`�[�G�N�X�|�[�g�G�N�Z���𐶐����擾
	 * 
	 * @param companyCode
	 * @param slipNoList
	 * @return ���׃��X�g�G�N�Z��
	 * @throws TException
	 */
	public byte[] getExportSlipExcel(String companyCode, List<String> slipNoList) throws TException;

	/**
	 * �`�[�G�N�X�|�[�g�G�N�Z���𐶐����擾(�t�֎d��̏ꍇ�A���ЁE�����З����̓`�[���o�͂���B)
	 * 
	 * @param slipNoList
	 * @return ���׃��X�g�G�N�Z��
	 * @throws TException
	 */
	public byte[] getExportSlipExcelBySlipNos(List<String> slipNoList) throws TException;

	/**
	 * �`�[���m�F����
	 * 
	 * @param prgCode
	 * @param denSyuMst
	 * @return Class
	 * @throws TException
	 */
	public Class getSlipPanel(String prgCode, String denSyuMst) throws TException;

	/**
	 * �`�[�ԍ�/�C���񐔂œ`�[�����݂��Ă��邩�`�F�b�N
	 * 
	 * @param kaiCode
	 * @param slipNo
	 * @param slipUpdCnt
	 * @throws TException
	 */
	public void checkSlipInfo(String kaiCode, String slipNo, int slipUpdCnt) throws TException;

	/**
	 * �`�[���`�F�b�N���āA���F
	 * 
	 * @param den
	 * @param isAsMuchAsPossible
	 * @return ���F��̓`�[
	 * @throws TException
	 */
	public SlipDen checkAndApproveSlip(SlipDen den, Boolean isAsMuchAsPossible) throws TException;

	/**
	 * �`�[���`�F�b�N���āA���
	 * 
	 * @param den
	 * @return ���F�����̓`�[
	 * @throws TException
	 */
	public SlipDen checkAndCancelApprovedSlip(SlipDen den) throws TException;
}
