package jp.co.ais.trans2.model.slip.parts;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.util.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �`�[�ԍ��̔�
 */
public class SlipNoCreator extends TModel {

	/**
	 * �`�[�ԍ�������U��
	 * 
	 * @param slip ���ƂȂ�`�[
	 * @return �`�[�ԍ�
	 */
	public String newSlipNo(Slip slip) {

		SWK_HDR header = slip.getHeader();

		String kaiCode = header.getKAI_CODE();
		String denDate = DateUtil.toYMDString(header.getSWK_DEN_DATE()); // �`�[���t
		String depCode = header.getSWK_IRAI_DEP_CODE(); // ����R�[�h
		String sysKbn = header.getSWK_SYS_KBN(); // �V�X�e���敪
		String slipType = header.getSWK_DEN_SYU(); //�`�[���

		return BizUtil.getSlipNo(kaiCode, getUserCode(), depCode, sysKbn, denDate, slipType, 1);
	}
}
