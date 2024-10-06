package simple;

import java.math.*;

import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.Message;
import jp.co.ais.trans2.model.slip.*;
import jp.co.ais.trans2.model.slip.SWK_DTL.AUTO_KBN;
import jp.co.ais.trans2.model.slip.parts.*;
import jp.co.ais.trans2.model.user.*;

/**
 * SlipChecker�e�X�g
 */
public class SlipCheckerTest {

	/** */
	private static User user = new User();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SingletonS2ContainerFactory.setConfigPath("trans-commons2.dicon");
			SingletonS2ContainerFactory.init();

			user.setCode("t");
			user.setLanguage("en");

			test();

		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	/**
	 * �e�X�g
	 * 
	 * @throws TException
	 */
	private static void test() throws TException {
		SlipChecker checker = new SlipChecker();
		checker.setUser(user);

		checker.check(createSlip1());

		for (Message msg : checker.getMessages()) {
			System.out.println(msg.getSubMessageID() + ":"
				+ MessageUtil.convertMessage(user.getLanguage(), msg.getMessageID(), msg.getBindIds()));
		}

		// checker.check(createSlip2());
		//
		// for (Message msg : checker.getMessages()) {
		// System.out.println(msg.getSubMessageID() + ":"
		// + MessageUtil.convertMessage("ja", msg.getMessageID(), msg.getBindIds()));
		// }
	}

	/**
	 * �`�[
	 * 
	 * @return �`�[
	 */
	private static Slip createSlip1() {
		Slip slip = new Slip();

		SWK_HDR hdr = new SWK_HDR();
		hdr.setKAI_CODE("000");
		hdr.setSWK_DEN_DATE(DateUtil.getDate(2000, 5, 17));

		// hdr.setSWK_DEN_NO(mainView.ctrlSlipNo.getSlipNo());// �`�[�ԍ�

		hdr.setSWK_KMK_CODE("9999");
		hdr.setSWK_HKM_CODE("999");
		hdr.setSWK_UKM_CODE("99");

		hdr.setSWK_TRI_CODE("00001");// �����R�[�h
		hdr.setSWK_TJK_CODE(null); // ��������

		hdr.setSlipState(SlipState.ENTRY);// �X�V�敪
		hdr.setSWK_SHR_KBN(0);// �r���t���O
		hdr.setSWK_KSN_KBN(0);// ���Z�敪
		hdr.setSWK_CUR_CODE("JPY");// �ʉݺ���
		hdr.setSWK_SYS_KBN("AP");// ���ы敪
		hdr.setSWK_DEN_SYU("023");
		hdr.setSWK_DATA_KBN("23");
		hdr.setSWK_UPD_CNT(0);// �`�[�C����
		hdr.setSWK_TUKE_KBN(0);// ��Њԕt�֓`�[�敪

		slip.setHeader(hdr);

		SWK_DTL dtl = new SWK_DTL();
		dtl.setDC(Dc.DEBIT);
		dtl.setSWK_K_KAI_CODE("000");// �v���к���
		dtl.setSWK_CUR_CODE("JPY");// �ʉݺ���
		dtl.setSWK_CUR_RATE(BigDecimal.ONE);// ڰ�
		dtl.setSWK_IN_KIN(new BigDecimal("1000"));// ���͋��z
		dtl.setSWK_KIN(new BigDecimal("1000"));// �M�݋��z
		dtl.setSWK_ZEI_KBN(2);// �ŋ敪
		dtl.setSWK_ZEI_RATE(BigDecimal.ZERO);// �ŗ�
		dtl.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);// ���͏���Ŋz
		dtl.setSWK_ZEI_KIN(BigDecimal.ZERO);// ����Ŋz
		dtl.setSWK_GYO_TEK("GYO TEK");// �s�E�v
		dtl.setSWK_AUTO_KBN(AUTO_KBN.NOMAL);// �����d��敪
		dtl.setSWK_TUKE_KBN(0);// ��Њԕt�֓`�[�敪
		dtl.setSWK_KESI_KBN(0);// �����敪
		dtl.setHAS_DATE(Util.getCurrentYMDDate());// ������
		slip.addDetail(dtl);

		slip.synchDetails();

		return slip;
	}

	/**
	 * �`�[
	 * 
	 * @return �`�[
	 */
	private static Slip createSlip2() {
		Slip slip = new Slip();

		SWK_HDR hdr = new SWK_HDR();
		hdr.setKAI_CODE("000");
		hdr.setSWK_DEN_DATE(DateUtil.getDate(2099, 5, 17));
		// hdr.setSWK_DEN_NO(mainView.ctrlSlipNo.getSlipNo());// �`�[�ԍ�
		hdr.setSWK_TRI_CODE("00001");// �����R�[�h
		// hdr.setSWK_TEK("TEK");// �`�[�E�v
		hdr.setSlipState(SlipState.ENTRY);// �X�V�敪
		hdr.setSWK_SHR_KBN(0);// �r���t���O
		hdr.setSWK_KSN_KBN(0);// ���Z�敪
		hdr.setSWK_CUR_CODE("JPY");// �ʉݺ���
		hdr.setSWK_SYS_KBN("GL");// ���ы敪
		hdr.setSWK_UPD_CNT(0);// �`�[�C����
		hdr.setSWK_TUKE_KBN(0);// ��Њԕt�֓`�[�敪

		slip.setHeader(hdr);

		SWK_DTL dtl = new SWK_DTL();
		dtl.setDC(Dc.DEBIT);
		dtl.setSWK_K_KAI_CODE("000");// �v���к���
		dtl.setSWK_CUR_CODE("JPY");// �ʉݺ���
		dtl.setSWK_CUR_RATE(BigDecimal.ONE);// ڰ�
		dtl.setSWK_IN_KIN(new BigDecimal("1000"));// ���͋��z
		dtl.setSWK_KIN(new BigDecimal("1000"));// �M�݋��z
		dtl.setSWK_ZEI_KBN(2);// �ŋ敪
		dtl.setSWK_ZEI_RATE(BigDecimal.ZERO);// �ŗ�
		dtl.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);// ���͏���Ŋz
		dtl.setSWK_ZEI_KIN(BigDecimal.ZERO);// ����Ŋz
		dtl.setSWK_GYO_TEK("GYO TEK");// �s�E�v
		dtl.setSWK_AUTO_KBN(AUTO_KBN.NOMAL);// �����d��敪
		dtl.setSWK_TUKE_KBN(0);// ��Њԕt�֓`�[�敪
		dtl.setSWK_KESI_KBN(0);// �����敪
		dtl.setHAS_DATE(Util.getCurrentYMDDate());// ������
		slip.addDetail(dtl);

		slip.synchDetails();

		return slip;
	}

	/**
	 * �`�[
	 * 
	 * @return �`�[
	 */
	private static Slip createSlip3() {
		Slip slip = new Slip();

		SWK_HDR hdr = new SWK_HDR();
		hdr.setKAI_CODE("000");
		hdr.setSWK_DEN_DATE(DateUtil.getDate(2099, 5, 17));
		// hdr.setSWK_DEN_NO(mainView.ctrlSlipNo.getSlipNo());// �`�[�ԍ�
		hdr.setSWK_TRI_CODE("00001");// �����R�[�h
		// hdr.setSWK_TEK("TEK");// �`�[�E�v
		hdr.setSlipState(SlipState.ENTRY);// �X�V�敪
		hdr.setSWK_SHR_KBN(0);// �r���t���O
		hdr.setSWK_KSN_KBN(0);// ���Z�敪
		hdr.setSWK_CUR_CODE("JPY");// �ʉݺ���
		hdr.setSWK_SYS_KBN("GL");// ���ы敪
		hdr.setSWK_UPD_CNT(0);// �`�[�C����
		hdr.setSWK_TUKE_KBN(0);// ��Њԕt�֓`�[�敪

		slip.setHeader(hdr);

		SWK_DTL dtl = new SWK_DTL();
		dtl.setDC(Dc.DEBIT);
		dtl.setSWK_K_KAI_CODE("000");// �v���к���
		dtl.setSWK_CUR_CODE("JPY");// �ʉݺ���
		dtl.setSWK_CUR_RATE(BigDecimal.ONE);// ڰ�
		dtl.setSWK_IN_KIN(new BigDecimal("1000"));// ���͋��z
		dtl.setSWK_KIN(new BigDecimal("1000"));// �M�݋��z
		dtl.setSWK_ZEI_KBN(2);// �ŋ敪
		dtl.setSWK_ZEI_RATE(BigDecimal.ZERO);// �ŗ�
		dtl.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);// ���͏���Ŋz
		dtl.setSWK_ZEI_KIN(BigDecimal.ZERO);// ����Ŋz
		dtl.setSWK_GYO_TEK("GYO TEK");// �s�E�v
		dtl.setSWK_AUTO_KBN(AUTO_KBN.NOMAL);// �����d��敪
		dtl.setSWK_TUKE_KBN(0);// ��Њԕt�֓`�[�敪
		dtl.setSWK_KESI_KBN(0);// �����敪
		dtl.setHAS_DATE(Util.getCurrentYMDDate());// ������
		slip.addDetail(dtl);

		slip.synchDetails();

		return slip;
	}

}
