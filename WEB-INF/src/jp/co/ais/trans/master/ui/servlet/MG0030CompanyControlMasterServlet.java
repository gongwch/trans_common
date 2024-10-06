package jp.co.ais.trans.master.ui.servlet;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * ��ЃR���g���[���}�X�^���Servlet (MG0030)
 * 
 * @author ISFnet China
 */
public class MG0030CompanyControlMasterServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = 2216242694312678519L;

	@Override
	protected String getLogicClassName() {
		return "CompanyControlLogic";
	}

	protected String getREFKeyFields() {
		return null;
	}

	/** ��L�[�̎擾 */
	protected Map getPrimaryKeys(HttpServletRequest req) {
		// map�̏�����
		Map map = new HashMap();
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", req.getParameter("kaiCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** ���������̎擾 */
	protected Map getFindConditions(HttpServletRequest req) {
		// map�̏�����
		Map map = new HashMap();
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", req.getParameter("kaiCode"));
		// �v���O�����R�[�h
		map.put("prgCode", req.getParameter("prgCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** �G���e�B�e�B�̎擾 */
	protected Object getEntity(HttpServletRequest req) {
		// ���̂̏�����
		CMP_MST cmpMST = new CMP_MST();
		// ��ЃR�[�h�̐ݒ�
		cmpMST.setKAI_CODE(req.getParameter("kaiCode"));
		// �Ȗږ��̂̐ݒ�
		cmpMST.setCMP_KMK_NAME(req.getParameter("cmpKmkName"));
		// �⏕�Ȗږ��̂̐ݒ�
		cmpMST.setCMP_HKM_NAME(req.getParameter("cmpHkmName"));
		// ����Ȗږ��̂̐ݒ�
		cmpMST.setCMP_UKM_NAME(req.getParameter("cmpUkmName"));
		// �Ǘ�����1�̐ݒ�
		cmpMST.setKNR_NAME_1(req.getParameter("knrName1"));
		// �Ǘ�����2�̐ݒ�
		cmpMST.setKNR_NAME_2(req.getParameter("knrName2"));
		// �Ǘ�����3�̐ݒ�
		cmpMST.setKNR_NAME_3(req.getParameter("knrName3"));
		// �Ǘ�����4�̐ݒ�
		cmpMST.setKNR_NAME_4(req.getParameter("knrName4"));
		// �Ǘ�����5�̐ݒ�
		cmpMST.setKNR_NAME_5(req.getParameter("knrName5"));
		// �Ǘ�����6�̐ݒ�
		cmpMST.setKNR_NAME_6(req.getParameter("knrName6"));
		// ���v���ז���1�̐ݒ�
		cmpMST.setCMP_HM_NAME_1(req.getParameter("cmpHmName1"));
		// ���v���ז���2�̐ݒ�
		cmpMST.setCMP_HM_NAME_2(req.getParameter("cmpHmName2"));
		// ���v���ז���3�̐ݒ�
		cmpMST.setCMP_HM_NAME_3(req.getParameter("cmpHmName3"));
		// �����ݒ荀�ڂP�̐ݒ�
		cmpMST.setJID_1(req.getParameter("jid1"));
		// �����ݒ荀��2�̐ݒ�
		cmpMST.setJID_2(req.getParameter("jid2"));
		// �����ݒ荀��3�̐ݒ�
		cmpMST.setJID_3(req.getParameter("jid3"));
		// �{�M�ʉ݃R�[�h�̐ݒ�
		cmpMST.setCUR_CODE(req.getParameter("curCode"));
		// ����ȖڊǗ��̎擾
		int cmpUkmKbn = Integer.parseInt(req.getParameter("cmpUkmKbn"));
		// �Ǘ��敪1�̎擾
		int knrKbn1 = Integer.parseInt(req.getParameter("knrKbn1"));
		// �Ǘ��敪2�̎擾
		int knrKbn2 = Integer.parseInt(req.getParameter("knrKbn2"));
		// �Ǘ��敪3�̎擾
		int knrKbn3 = Integer.parseInt(req.getParameter("knrKbn3"));
		// �Ǘ��敪4�̎擾
		int knrKbn4 = Integer.parseInt(req.getParameter("knrKbn4"));
		// �Ǘ��敪5�̎擾
		int knrKbn5 = Integer.parseInt(req.getParameter("knrKbn5"));
		// �Ǘ��敪6�̎擾
		int knrKbn6 = Integer.parseInt(req.getParameter("knrKbn6"));
		// ���v���׋敪1�̎擾
		int cmpHmKbn1 = Integer.parseInt(req.getParameter("cmpHmKbn1"));
		// ���v���׋敪2�̎擾
		int cmpHmKbn2 = Integer.parseInt(req.getParameter("cmpHmKbn2"));
		// ���v���׋敪3�̎擾
		int cmpHmKbn3 = Integer.parseInt(req.getParameter("cmpHmKbn3"));
		// ���񌎂̎擾
		Integer cmpKisyu = new Integer(Integer.parseInt(req.getParameter("cmpKisyu")));
		// �����̔ԕ������̎擾
		int autoNoKeta = Integer.parseInt(req.getParameter("autoNoKeta"));
		// �`�[����敪�̎擾
		int printKbn = Integer.parseInt(req.getParameter("printKbn"));
		// ������̏����l�̎擾
		int printDef = Integer.parseInt(req.getParameter("printDef"));
		// ���ꏳ�F�׸ނ̎擾
		int cmpGShoninFlg = Integer.parseInt(req.getParameter("cmpGShoninFlg"));
		// �o�����F�׸ނ̎擾
		int cmpKShoninFlg = Integer.parseInt(req.getParameter("cmpKShoninFlg"));
		// ���[�g���Z�[�������̎擾
		int rateKbn = Integer.parseInt(req.getParameter("rateKbn"));
		// �������Œ[�������̎擾
		int kuKbn = Integer.parseInt(req.getParameter("kuKbn"));
		// ��������Œ[�������̎擾
		int kbKbn = Integer.parseInt(req.getParameter("kbKbn"));
		// ���ڈ���敪
		int dirKbn = Util.avoidNullAsInt(req.getParameter("directKbn"));
		// ����ȖڊǗ��̐ݒ�
		cmpMST.setCMP_UKM_KBN(cmpUkmKbn);
		// �Ǘ��敪1�̐ݒ�
		cmpMST.setKNR_KBN_1(knrKbn1);
		// �Ǘ��敪2�̐ݒ�
		cmpMST.setKNR_KBN_2(knrKbn2);
		// �Ǘ��敪3�̐ݒ�
		cmpMST.setKNR_KBN_3(knrKbn3);
		// �Ǘ��敪4�̐ݒ�
		cmpMST.setKNR_KBN_4(knrKbn4);
		// �Ǘ��敪5�̐ݒ�
		cmpMST.setKNR_KBN_5(knrKbn5);
		// �Ǘ��敪6�̐ݒ�
		cmpMST.setKNR_KBN_6(knrKbn6);
		// ���v���׋敪1�̐ݒ�
		cmpMST.setCMP_HM_KBN_1(cmpHmKbn1);
		// ���v���׋敪2�̐ݒ�
		cmpMST.setCMP_HM_KBN_2(cmpHmKbn2);
		// ���v���׋敪3�̐ݒ�
		cmpMST.setCMP_HM_KBN_3(cmpHmKbn3);
		// ���񌎂̐ݒ�
		cmpMST.setCMP_KISYU(cmpKisyu);
		// �����̔ԕ������̐ݒ�
		cmpMST.setAUTO_NO_KETA(autoNoKeta);
		// �`�[����敪�̐ݒ�
		cmpMST.setPRINT_KBN(printKbn);
		// ������̏����l�̐ݒ�
		cmpMST.setPRINT_DEF(printDef);
		// ���ꏳ�F�׸ނ̐ݒ�
		cmpMST.setCMP_G_SHONIN_FLG(cmpGShoninFlg);
		// �o�����F�׸ނ̐ݒ�
		cmpMST.setCMP_K_SHONIN_FLG(cmpKShoninFlg);
		// ���[�g���Z�[�������̐ݒ�
		cmpMST.setRATE_KBN(rateKbn);
		// �������Œ[�������̐ݒ�
		cmpMST.setKU_KBN(kuKbn);
		// ��������Œ[�������̐ݒ�
		cmpMST.setKB_KBN(kbKbn);
		// �v���O����ID�̐ݒ�
		cmpMST.setPRG_ID(req.getParameter("prgID"));
		// ���ʂ�Ԃ�
		return cmpMST;
	}

	/** ���X�g�o�͂�Excel��` */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excel��Ԃ�
		return new CompanyControlMasterReportExcelDefine("");
	}

	/**
	 * Excel���X�g�쐬
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 * @throws IOException
	 * @throws TException
	 */
	protected void report(HttpServletRequest req, HttpServletResponse resp) throws ParseException, IOException,
		TException {

		String langCode = req.getParameter("langCode");

		S2Container container = SingletonS2ContainerFactory.getContainer();
		CommonLogic logic = (CommonLogic) container.getComponent(getLogicClassName());

		Map conditions = this.getFindConditions(req);
		List list = logic.find(conditions);

		if (list == null || list.isEmpty()) {
			// �f�[�^������܂���B
			throw new TException("W00100");
		}

		if (list.size() >= 65535) {
			throw new TException("W00213");
		}

		ReportExcelDefine red = new CompanyControlMasterReportExcelDefine(Util.avoidNull(req.getParameter("prgCode")));

		// ��ЃR�[�h���Z�b�g
		red.setKaiCode(req.getParameter("kaiCode"));
		// ����R�[�h���Z�b�g
		red.setLangCode(langCode);

		OutputUtil util = new OutputUtil(red, langCode);
		util.createExcel(list);
		byte[] bytes = util.getBinary();

		super.dispatchExcel(req, resp, red.getFileName(), bytes);
	}

	/**
	 * ���̑��̏ꍇ
	 */
	@Override
	protected void other(HttpServletRequest req, HttpServletResponse resp) {
		// ����pflag
		String flag = req.getParameter("flag");
		// ����
		if ("refControl".equals(flag)) {
			searchRefControl(req, resp);

			// �R���g���[�����擾
		} else if ("searchByKaiCode".equals(flag)) {
			searchByKaiCode(req, resp);
		}
	}

	/**
	 * ��ЃR�[�h�ŃR���g���[�������擾(LIST)
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchRefControl(HttpServletRequest req, HttpServletResponse resp) {
		// container�̏�����
		S2Container container = null;

		// flag: "checkNaiCode"
		try {

			container = SingletonS2ContainerFactory.getContainer();
			// logic�̏�����
			CompanyControlLogic logic = (CompanyControlLogic) container.getComponent(CompanyControlLogic.class);
			// keys�̏�����
			Map keys = new HashMap();
			keys.put("kaiCode", req.getParameter("kaiCode"));
			// ���̂̏�����
			List list = logic.getREFItems(keys);

			super.dispatchResultList(req, resp, list);

		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}

	/**
	 * ��ЃR�[�h�ŃR���g���[�������擾(DTO)
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchByKaiCode(HttpServletRequest req, HttpServletResponse resp) {
		// container�̏�����
		S2Container container = null;

		try {

			container = SingletonS2ContainerFactory.getContainer();
			// logic�̏�����
			CompanyControlLogic logic = (CompanyControlLogic) container.getComponent(CompanyControlLogic.class);
			// keys�̏�����
			Map keys = new HashMap();
			keys.put("kaiCode", req.getParameter("kaiCode"));
			// ���̂̏�����
			CMP_MST cmp = (CMP_MST) logic.findOne(keys);

			super.dispatchResultDto(req, resp, cmp);

		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}

}
