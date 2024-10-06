package jp.co.ais.trans.common.server.servlet;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.server.di.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ���ʃT�[�u���b�g
 */
public class TReferenceAuthorityServlet extends TServletBase {

	/** UID */
	private static final long serialVersionUID = -5319125982776071994L;

	/**
	 * ���N�G�X�g����������
	 * 
	 * @param req HttpServletRequest
	 * @param resp HttpServletResponse
	 * @throws ServletException
	 */
	protected void doMethodPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

		try {
			// ����pflag
			String flag = req.getParameter("FLAG");

			// �������擾
			if ("INIT".equals(flag)) {
				init(req, resp);
			} else if ("DEPARTMENT".equals(flag)) {
				departmentName(req, resp);
			} else if ("PERSON".equals(flag)) {
				// ���͎җ��̎擾����
				this.empName(req, resp);
			}

		} catch (Exception e) {
			handledException(e, req, resp);
		}
	}

	/**
	 * ���͎җ��̌���
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 */
	private void empName(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		TReferenceAuthorityLogic logic = (TReferenceAuthorityLogic) container
			.getComponent(TReferenceAuthorityLogic.class);

		// ��ЃR�[�h
		String kaiCode = Util.avoidNull(req.getParameter("KAI_CODE"));
		// ��������R�[�h
		String depCode = Util.avoidNull(req.getParameter("BMN_CODE"));
		// �e�L�X�g�t�B�[���h�ɕ�����
		String empCode = Util.avoidNull(req.getParameter("EMP_CODE"));

		// ��ЃR�[�h�A�e�L�X�g�t�B�[���h�ɕ����񂩂�f�[�^�����邩������
		EMP_MST cur = logic.getEMP_MSTByKaiCodeEmpCode(kaiCode, empCode, depCode);

		Map<String, Object> map = new HashMap<String, Object>();
		// �f�[�^������ꍇ
		if (cur != null) {
			map.put("existFlag", "1");
			map.put("EMP_NAME_S", Util.avoidNull(cur.getEMP_NAME_S()));
		} else {
			map.put("existFlag", "0");
			map.put("EMP_NAME_S", "");
		}

		dispatchResultMap(req, resp, map);
	}

	/**
	 * ���͕��嗪�̌���
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 */
	private void departmentName(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		TReferenceAuthorityLogic logic = (TReferenceAuthorityLogic) container
			.getComponent(TReferenceAuthorityLogic.class);

		// ��ЃR�[�h
		String kaiCode = Util.avoidNull(req.getParameter("KAI_CODE"));
		// �e�L�X�g�t�B�[���h�ɕ�����
		String bmnCode = Util.avoidNull(req.getParameter("BMN_CODE"));

		// ��ЃR�[�h�A�e�L�X�g�t�B�[���h�ɕ����񂩂�f�[�^�����邩������
		BMN_MST bmnMst = logic.getBMN_MSTByKaicodeDepcode(kaiCode, bmnCode);

		Map<String, Object> map = new HashMap<String, Object>();

		// �f�[�^������ꍇ
		if (bmnMst != null) {
			map.put("existFlag", "1");
			map.put("DEP_NAME_S", Util.avoidNull(bmnMst.getDEP_NAME_S()));
		} else {
			map.put("existFlag", "0");
			map.put("DEP_NAME_S", "");
		}
		dispatchResultMap(req, resp, map);

	}

	/**
	 * �����f�[�^���擾����
	 * 
	 * @param req
	 * @param resp
	 */
	private void init(HttpServletRequest req, HttpServletResponse resp) {
		TUserInfo userInfo = this.refTServerUserInfo(req);

		// ��ЃR�[�h
		String kaiCode = userInfo.getCompanyCode();
		String usrCode = userInfo.getUserCode();

		Map<String, Object> map = new TreeMap<String, Object>();

		// �������̃f�[�^�[���擾����
		S2Container s2Container = SingletonS2ContainerFactory.getContainer();

		TReferenceAuthorityLogic logic = (TReferenceAuthorityLogic) s2Container
			.getComponent(TReferenceAuthorityLogic.class);

		String depCode = "";
		String empCode = "";

		// ���[�U�[�}�X�^���擾
		USR_MST usrMst = logic.getUSR_MSTByKaicodeUsercode(kaiCode, usrCode);

		if (usrMst != null) {

			// �X�V�������x��
			map.put("updKen", Util.avoidNull(usrMst.getUPD_KEN()));
			// ���[�U�[�̏�������R�[�h
			depCode = Util.avoidNull(usrMst.getDEP_CODE());
			// ���O�C�����[�U�[�̎Ј��R�[�h
			empCode = Util.avoidNull(usrMst.getEMP_CODE());

			// ����R�[�h
			map.put("bmnCode", Util.avoidNull(depCode));
			// �Ј��R�[�h
			map.put("empCode", Util.avoidNull(empCode));

		}

		// �Ј��}�X�^���擾
		EMP_MST empMst = logic.getEMP_MSTByKaiCodeEmpCode(kaiCode, empCode, depCode);

		if (empMst != null) {
			// �Ј�����
			map.put("empNameS", Util.avoidNull(empMst.getEMP_NAME_S()));
		} else {
			// �Ј�����
			map.put("empNameS", "");
		}

		// ����}�X�^���擾
		BMN_MST bmnMst = logic.getBMN_MSTByKaicodeDepcode(kaiCode, depCode);

		if (bmnMst != null) {
			// ���嗪��
			map.put("bmnNameS", Util.avoidNull(bmnMst.getDEP_NAME_S()));
		} else {
			map.put("bmnNameS", "");
		}

		dispatchResultMap(req, resp, map);
	}
}
