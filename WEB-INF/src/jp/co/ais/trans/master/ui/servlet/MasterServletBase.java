package jp.co.ais.trans.master.ui.servlet;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.logic.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * master base class. <br>
 */
public abstract class MasterServletBase extends TServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = 219250234324041314L;

	/** DI class interface. */
	protected abstract String getLogicClassName();

	/**
	 * doPost()���\�b�h��POST�`���ő��M���ꂽ�f�[�^������
	 */
	public void doMethodPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		try {
			// ����pflag
			String flag = (String) req.getParameter("flag");
			if (flag != null) {
				flag = flag.toLowerCase();
			}

			// ����
			if ("find".equals(flag)) {
				find(req, resp);
			}
			// ����̃��R�[�h���擾
			else if ("findone".equals(flag)) {
				findOne(req, resp);
			}
			// REF�_�C�A���O�p�̌���
			else if ("findref".equals(flag)) {
				findREF(req, resp);
			}
			// ButtonField�ŃR�[�h����͎��A���̂̌���
			else if ("findname".equals(flag)) {
				findName(req, resp);
			}
			// �R�[�h�`�F�b�N
			else if ("checkcode".equals(flag)) {
				checkCode(req, resp);
			}
			// �폜
			else if ("delete".equals(flag)) {
				delete(req, resp);
			}
			// �V�K�i���ʁj
			else if ("insert".equals(flag)) {
				insert(req, resp);
			}
			// �ύX
			else if ("update".equals(flag)) {
				update(req, resp);
			}
			// �V�K�����͕ύX
			else if ("save".equals(flag)) {
				save(req, resp);
			}
			// Excel���X�g�擾
			else if ("report".equals(flag)) {
				report(req, resp);
			}
			// ���̑��̉�ʌʂ̃��W�b�N
			else {
				other(req, resp);
			}

		} catch (Exception e) {
			handledException(e, req, resp);
		}
	}

	/** ��L�[�̎擾 */
	protected abstract Map getPrimaryKeys(HttpServletRequest req) throws ParseException;

	/** ���������̎擾 */
	protected abstract Map getFindConditions(HttpServletRequest req) throws ParseException;

	/** �G���e�B�e�B�̎擾 */
	protected abstract Object getEntity(HttpServletRequest req) throws ParseException;

	/** ���X�g�o�͂�Excel��` */
	protected abstract ReportExcelDefine getReportExcelDefine();

	/** �����_�C�A���O�L�[�擾 */
	protected abstract String getREFKeyFields();

	/**
	 * ����
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ParseException
	 */
	protected void find(HttpServletRequest req, HttpServletResponse resp) throws IOException, ParseException {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CommonLogic logic = (CommonLogic) container.getComponent(getLogicClassName());
		Map conditions = this.getFindConditions(req);
		List list = logic.find(conditions);

		super.dispatchResultListObject(req, resp, list);
	}

	protected void findOne(HttpServletRequest req, HttpServletResponse resp) throws IOException, ParseException {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CommonLogic logic = (CommonLogic) container.getComponent(getLogicClassName());
		Map keys = this.getPrimaryKeys(req);
		Object entity = logic.findOne(keys);

		List result = new ArrayList(1);
		if (entity != null) {
			result.add(entity);
		}
		super.dispatchResultListObject(req, resp, result);
	}

	private Map getPrimaryKeyMap(HttpServletRequest req) {
		String keyFields = getREFKeyFields();
		String[] fields = keyFields.split(",");

		Map keys = new HashMap();

		for (int i = 0; i < fields.length; i++) {
			String field = fields[i];
			if (!Util.isNullOrEmpty(field)) {
				keys.put(field, req.getParameter(field));
			}
		}
		keys.put("kind", req.getParameter("kind"));
		keys.put("beginCode", req.getParameter("beginCode"));
		keys.put("endCode", req.getParameter("endCode"));
		keys.put("langCode", req.getParameter("langCode"));

		return keys;
	}

	private void findREF(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		S2Container container = null;

		try {

			container = SingletonS2ContainerFactory.getContainer();

			CommonLogic logic = (CommonLogic) container.getComponent(getLogicClassName());
			Map conditions = getPrimaryKeyMap(req);

			String code = req.getParameter("code");
			String name_S = req.getParameter("name_S");
			String name_K = req.getParameter("name_K");

			// Like�𗘗p���邽��
			if (!Util.isNullOrEmpty(code)) {
				code = DBUtil.getLikeStatement(code, DBUtil.NORMAL_CHAR);
			}
			if (!Util.isNullOrEmpty(name_S)) {
				name_S = DBUtil.getLikeStatement(name_S, DBUtil.UNICODE_CHAR);
			}
			if (!Util.isNullOrEmpty(name_K)) {
				name_K = DBUtil.getLikeStatement(name_K, DBUtil.UNICODE_CHAR);
			}

			conditions.put("code", code);
			conditions.put("name_S", name_S);
			conditions.put("name_K", name_K);
			conditions.put("refFlg", "1");

			List list = logic.findREF(conditions);
			super.dispatchResultListObject(req, resp, list);
		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}

	protected void findName(HttpServletRequest req, HttpServletResponse resp) throws IOException, ParseException {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CommonLogic logic = (CommonLogic) container.getComponent(getLogicClassName());
		Map keys = getPrimaryKeyMap(req);

		String code = (String) req.getParameter("code");
		keys.put("code", code);

		String name_S = logic.findName(keys);

		List result = new ArrayList(1);
		if (name_S != null) {
			StringTransferObject sto = new StringTransferObject();
			sto.setValue(name_S);
			result.add(sto);
		}
		super.dispatchResultListObject(req, resp, result);
	}

	/**
	 * �R�[�h�`�F�b�N
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	protected void checkCode(HttpServletRequest req, HttpServletResponse resp) throws IOException, ParseException {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CommonLogic logic = (CommonLogic) container.getComponent(getLogicClassName());
		Map keys = this.getPrimaryKeys(req);
		boolean exists = logic.checkCode(keys);

		StringTransferObject sto = new StringTransferObject();
		sto.setValue(exists ? "true" : "false");

		List list = new ArrayList(1);
		if (exists) {
			list.add(sto);
		}
		super.dispatchResultListObject(req, resp, list);
	}

	/**
	 * �폜
	 * 
	 * @param req
	 */
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ParseException {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CommonLogic logic = (CommonLogic) container.getComponent(getLogicClassName());
		Map keys = this.getPrimaryKeys(req);
		logic.delete(keys);

		super.dispatchResultSuccess(req, resp);
	}

	/**
	 * �V�K
	 * 
	 * @param req
	 * @param resp
	 * @throws TException
	 */
	protected void insert(HttpServletRequest req, HttpServletResponse resp) throws IOException, ParseException,
		TException {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CommonLogic logic = (CommonLogic) container.getComponent(getLogicClassName());
		Object entity = this.getEntity(req);
		logic.setUserCode(refTServerUserInfo(req).getUserCode());
		logic.insert(entity);

		super.dispatchResultSuccess(req, resp);
	}

	/**
	 * �X�V
	 * 
	 * @param req
	 * @param resp
	 * @throws TException
	 */
	protected void update(HttpServletRequest req, HttpServletResponse resp) throws IOException, ParseException,
		TException {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CommonLogic logic = (CommonLogic) container.getComponent(getLogicClassName());
		Object entity = this.getEntity(req);
		logic.setUserCode(refTServerUserInfo(req).getUserCode());
		logic.update(entity);

		super.dispatchResultSuccess(req, resp);
	}

	/**
	 * �V�K�����͍X�V
	 * 
	 * @param req
	 * @param resp
	 * @throws TException
	 */
	protected void save(HttpServletRequest req, HttpServletResponse resp) throws IOException, ParseException,
		TException {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CommonLogic logic = (CommonLogic) container.getComponent(getLogicClassName());
		Object entity = this.getEntity(req);
		logic.setUserCode(refTServerUserInfo(req).getUserCode());
		logic.save(entity);

		super.dispatchResultSuccess(req, resp);
	}

	/**
	 * Excel���X�g�쐬
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
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

		ReportExcelDefine red = this.getReportExcelDefine();

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
	 * ���̑��̉�ʌʂ̃��W�b�N
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	protected void other(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException,
		ParseException {
		super.dispatchResultSuccess(req, resp);
	}
}
