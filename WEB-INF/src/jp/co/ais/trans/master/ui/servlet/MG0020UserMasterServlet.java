package jp.co.ais.trans.master.ui.servlet;

import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.system.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * ���[�U�[�}�X�^���Servlet (MG0020)
 * 
 * @author ISFnet China
 */
public class MG0020UserMasterServlet extends MasterServletBase {

	/** �V���A��UID */
	static final long serialVersionUID = 3686838932095874002L;

	@Override
	protected String getLogicClassName() {
		return "UserLogic";
	}

	protected String getREFKeyFields() {
		return "kaiCode";
	}

	/** ��L�[�̎擾 */
	protected Map getPrimaryKeys(HttpServletRequest req) {
		// map�̏�����
		Map map = new HashMap();
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", req.getParameter("kaiCode"));
		// ���[�U�[�R�[�h�̐ݒ�
		map.put("usrCode", req.getParameter("usrCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** ���������̎擾 */
	protected Map getFindConditions(HttpServletRequest req) {
		// map�̏�����
		Map map = new HashMap();
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", req.getParameter("kaiCode"));
		// �J�n�R�[�h�̐ݒ�
		map.put("beginUsrCode", req.getParameter("beginUsrCode"));
		// �I���R�[�h�̐ݒ�
		map.put("endUsrCode", req.getParameter("endUsrCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** �G���e�B�e�B�̎擾 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// ���̂̏�����
		USR_MST usrMST = new USR_MST();

		//
		usrMST.setKAI_CODE(req.getParameter("kaiCode"));
		// ��ЃR�[�h�̐ݒ�
		usrMST.setUSR_CODE(req.getParameter("usrCode"));
		// ���[�U�[�R�[�h�̐ݒ�
		usrMST.setPASSWORD(req.getParameter("password"));
		// �p�X���[�h�̐ݒ�
		usrMST.setLNG_CODE(req.getParameter("lngCode"));
		// ����R�[�h�̐ݒ�
		usrMST.setUSR_NAME(req.getParameter("usrName"));
		// ���[�U�[���̂̐ݒ�
		usrMST.setUSR_NAME_S(req.getParameter("usrName_S"));
		// ���[�U�[���̂̐ݒ�
		usrMST.setUSR_NAME_K(req.getParameter("usrName_k"));
		// �V�X�e���敪�̎擾
		String sysKbn = req.getParameter("sysKbn");
		// �V�X�e���敪�̐ݒ�
		usrMST.setSYS_KBN(sysKbn);
		// �����������x���̎擾
		int prcKen = Integer.parseInt(req.getParameter("prcKen"));
		// �����������x���̐ݒ�
		usrMST.setPRC_KEN(prcKen);
		// �Ј��R�[�h�̐ݒ�
		usrMST.setEMP_CODE(req.getParameter("empCode"));
		// ��������R�[�h�̐ݒ�
		usrMST.setDEP_CODE(req.getParameter("depCode"));
		// �o���S���ҋ敪�̎擾
		int keiriKbn = Integer.parseInt(req.getParameter("keiriKbn"));
		// �o���S���ҋ敪�̐ݒ�
		usrMST.setKEIRI_KBN(keiriKbn);
		// �X�V�������x���̎擾
		int updKen = Integer.parseInt(req.getParameter("updKen"));
		// �X�V�������x���̐ݒ�
		usrMST.setUPD_KEN(updKen);
		// �J�n�N�����̏�����
		Date strDate = null;
		// �I���N�����̏�����
		Date endDate = null;
		// �J�n�N�����̎擾
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// �I���N�����̎擾
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// �J�n�N�����̐ݒ�
		usrMST.setSTR_DATE(strDate);
		// �I���N�����̐ݒ�
		usrMST.setEND_DATE(endDate);
		// �v���O����ID�̐ݒ�
		usrMST.setPRG_ID(req.getParameter("prgID")); // �v���O����ID
		// �p�X���[�h�X�V���t�ݒ�
		Date pwdDate = null;
		pwdDate = DateUtil.toYMDDate(req.getParameter("pwdDate"));
		usrMST.setPWD_UPD_DATE(pwdDate);
		// ���ʂ�Ԃ�
		return usrMST;
	}

	/**
	 * ���̑��̏ꍇ
	 * 
	 * @throws ParseException
	 */
	@Override
	protected void other(HttpServletRequest req, HttpServletResponse resp) throws ParseException {

		String flag = req.getParameter("flag");

		// ���[�U����
		if ("refEmployee".equals(flag)) {
			searchRefUser(req, resp);

			// �p�X���[�h�o�^�y�эX�V
		} else if ("passwordupdate".equals(flag)) {
			updatePassword(req, resp);

			// �p�X���[�h�̃`�F�b�N�������s��
		} else if ("passCheck".equals(flag)) {
			passwordCheck(req, resp);

		} else if ("insertPwd".equals(flag)) {
			insertPwdHistory(req, resp);

		} else if ("searchUsrList".equals(flag)) {
			searchWithKaiCode(req, resp);

		} else if ("updateUsrData".equals(flag)) {
			updateUsrData(req, resp);
		}

	}

	/** ���X�g�o�͂�Excel��` */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excel��Ԃ�
		return new UserMasterReportExcelDefine();
	}

	/**
	 * �X�V���[�U�}�X�^
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */
	private void updateUsrData(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		// old��ЃR�[�h
		String oldKaiCode = req.getParameter("oldKaiCode");

		USR_MST entity = (USR_MST) this.getEntity(req);
		entity.setUPD_DATE(Util.getCurrentDate());
		entity.setUSR_ID(this.refTServerUserInfo(req).getUserCode());

		S2Container container = SingletonS2ContainerFactory.getContainer();
		UserLogic logic = (UserLogic) container.getComponent(UserLogic.class);

		logic.updateUsrMst(entity, oldKaiCode);

		// ���ʂ̐ݒ�
		super.dispatchResultSuccess(req, resp);
	}

	/**
	 * �p�X���[�h�X�V
	 * 
	 * @param req
	 * @param resp
	 */
	private void updatePassword(HttpServletRequest req, HttpServletResponse resp) {

		String newPassword = req.getParameter("newPassword"); // �V�����p�X���[�h
		String prgID = req.getParameter("prgID"); // �v���O����ID

		S2Container container = SingletonS2ContainerFactory.getContainer();

		TUserInfo userInfo = refTServerUserInfo(req);
		UserPassword logic = (UserPassword) container.getComponent(UserPassword.class);
		logic.setCode(userInfo.getCompanyCode(), userInfo.getUserCode());

		// ���G���x���A�y�уp�X���[�h���������𖞂����Ă��Ȃ��ꍇ
		boolean assertPwd = !logic.isPasswordManaged() || logic.assertPassword(newPassword);

		if (!assertPwd) {
			dispatchError(req, resp, logic.getErrorMessageId(), logic.getErrorArgs());
			return;
		}

		// �f�[�^���X�V����
		logic.update(newPassword, prgID);

		// ���ʂ̐ݒ�
		super.dispatchResultSuccess(req, resp);
	}

	/**
	 * ���[�U���� <br>
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchRefUser(HttpServletRequest req, HttpServletResponse resp) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		UserLogic logic = (UserLogic) container.getComponent(UserLogic.class);

		// keys�̏�����
		Map keys = new HashMap();
		keys.put("kaiCode", req.getParameter("kaiCode"));
		keys.put("usrCode", req.getParameter("usrCode"));

		// ���[�U���X�g�K��
		List list = logic.getREFItems(keys);

		super.dispatchResultList(req, resp, list);

	}

	/**
	 * �p�X���[�h������n���A���؂��s��<br>
	 * ���ʂƂ��ăG���[���b�Z�[�W��ԐM���邩�A���ؐ�����m�点��
	 * 
	 * @param req
	 * @param resp
	 */
	private void passwordCheck(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		TUserInfo userInfo = refTServerUserInfo(req);
		UserPassword logic = (UserPassword) container.getComponent(UserPassword.class);
		logic.setCode(userInfo.getCompanyCode(), userInfo.getUserCode());

		// �p�X���[�h�̎擾
		String newPassword = req.getParameter("password");

		// ���G���x���A�y�уp�X���[�h���������𖞂����Ă��Ȃ��ꍇ
		boolean assertPwd = !logic.isPasswordManaged() || logic.assertPassword(newPassword);

		if (!assertPwd) {
			dispatchError(req, resp, logic.getErrorMessageId(), logic.getErrorArgs());
			return;
		}

		super.dispatchResultSuccess(req, resp);
	}

	/**
	 * �p�X���[�h����V�K�쐬
	 * 
	 * @param req
	 * @param resp
	 */
	private void insertPwdHistory(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		PasswordHistoryLogic logic = (PasswordHistoryLogic) container.getComponent(PasswordHistoryLogic.class);
		UserAuthLogic usrAuthLogic = (UserAuthLogic) container.getComponent(UserAuthLogic.class);
		TUserInfo usrInfo = refTServerUserInfo(req);
		USR_AUTH_MST usrDto = usrAuthLogic.find(usrInfo.getCompanyCode());
		// ���[�U�F�؃}�X�^�����݂���Ə�������������
		if (usrDto != null) {
			PWD_HST_TBL pwdDto = new PWD_HST_TBL();
			pwdDto.setKAI_CODE(req.getParameter("kaiCode"));
			pwdDto.setUSR_CODE(req.getParameter("usrCode"));
			pwdDto.setPASSWORD(req.getParameter("password"));
			pwdDto.setINP_DATE(Util.getCurrentDate());

			logic.insert(pwdDto);
		}

		super.dispatchResultSuccess(req, resp);

	}

	/**
	 * ��Еʃ��[�U���X�g�擾
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchWithKaiCode(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		UserLogic logic = (UserLogic) container.getComponent(UserLogic.class);
		TUserInfo userInfo = refTServerUserInfo(req);

		// ��ЃR�[�h�Ń��[�U���X�g���K��
		List dtoList = logic.searchUsrList(userInfo.getCompanyCode());
		super.dispatchResultDtoList(req, resp, dtoList);

	}
}
