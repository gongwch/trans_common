package jp.co.ais.trans.master.ui.servlet;

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
 * ����K�w�}�X�^���Servlet
 * 
 * @author AIS
 */
public class MG0050DepartmentHierarchyMasterServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = 7691999673652241786L;

	@Override
	protected String getLogicClassName() {
		return "DepartmentHierarchyLogic";
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
		// �g�D�R�[�h�̐ݒ�
		map.put("dpkSsk", req.getParameter("dpkSsk"));
		// ����R�[�h�̐ݒ�
		map.put("dpkDepCode", req.getParameter("dpkDepCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** ���������̎擾 */
	protected Map getFindConditions(HttpServletRequest req) {
		// map�̏�����
		Map map = new HashMap();
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", req.getParameter("kaiCode"));
		// �g�D�R�[�h�̐ݒ�
		map.put("dpkSsk", req.getParameter("dpkSsk"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** �G���e�B�e�B�̎擾 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// ���̂̏�����
		DPK_MST dpkMST = new DPK_MST();
		// ��ЃR�[�h�̐ݒ�
		dpkMST.setKAI_CODE(req.getParameter("kaiCode"));
		// �g�D�R�[�h�̐ݒ�
		dpkMST.setDPK_SSK(req.getParameter("dpkSsk"));
		// ����R�[�h�̐ݒ�
		dpkMST.setDPK_DEP_CODE(req.getParameter("dpkDepCode"));
		// ���x���̎擾
		int dpkLvl = Integer.parseInt(req.getParameter("dpkLvl"));
		// ���x���̐ݒ�
		dpkMST.setDPK_LVL(dpkLvl);
		// �Ǘ�����R�[�h���x���O�̐ݒ�
		dpkMST.setDPK_LVL_0(req.getParameter("dpkLvl0"));
		// �Ǘ�����R�[�h���x��1�̐ݒ�
		dpkMST.setDPK_LVL_1(req.getParameter("dpkLvl1"));
		// �Ǘ�����R�[�h���x��2�̐ݒ�
		dpkMST.setDPK_LVL_2(req.getParameter("dpkLvl2"));
		// �Ǘ�����R�[�h���x��3�̐ݒ�
		dpkMST.setDPK_LVL_3(req.getParameter("dpkLvl3"));
		// �Ǘ�����R�[�h���x��4�̐ݒ�
		dpkMST.setDPK_LVL_4(req.getParameter("dpkLvl4"));
		// �Ǘ�����R�[�h���x��5�̐ݒ�
		dpkMST.setDPK_LVL_5(req.getParameter("dpkLvl5"));
		// �Ǘ�����R�[�h���x��6�̐ݒ�
		dpkMST.setDPK_LVL_6(req.getParameter("dpkLvl6"));
		// �Ǘ�����R�[�h���x��7�̐ݒ�
		dpkMST.setDPK_LVL_7(req.getParameter("dpkLvl7"));
		// �Ǘ�����R�[�h���x��8�̐ݒ�
		dpkMST.setDPK_LVL_8(req.getParameter("dpkLvl8"));
		// �Ǘ�����R�[�h���x��9�̐ݒ�
		dpkMST.setDPK_LVL_9(req.getParameter("dpkLvl9"));
		// �J�n�N�����̏�����
		Date strDate = null;
		// �I���N�����̏�����
		Date endDate = null;
		// �J�n�N�����̎擾
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// �I���N�����̎擾
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// �J�n�N�����̐ݒ�
		dpkMST.setSTR_DATE(strDate);
		// �I���N�����̐ݒ�
		dpkMST.setEND_DATE(endDate);
		// �v���O����ID�̐ݒ�
		dpkMST.setPRG_ID(req.getParameter("prgID"));
		// ���ʂ�Ԃ�
		return dpkMST;
	}

	/** ���̑��̏ꍇ */
	protected void other(HttpServletRequest req, HttpServletResponse resp) {
		// ������ʂ̎擾
		String flag = req.getParameter("flag");
		// container�̏�����
		S2Container container = null;
		try {

			container = SingletonS2ContainerFactory.getContainer();
			// �t�@�C���̎擾
			// DepartmentHierarchyLogicImpl logic =
			// (DepartmentHierarchyLogicImpl) container
			// .getComponent("logic");
			DepartmentHierarchyLogic logic = (DepartmentHierarchyLogic) container.getComponent(getLogicClassName());
			// ���[�U�R�[�h�ݒ�
			logic.setUserCode(refTServerUserInfo(req).getUserCode());
			if ("getorganizations".equals(flag)) {
				// ��ЃR�[�h�̎擾
				String kaiCode = req.getParameter("kaiCode");
				// ���ʂ̎擾
				List list = logic.getOrganizations(kaiCode);
				// ���ʂ̐ݒ�
				super.dispatchResultListObject(req, resp, list);
			}
			// "createOrganization"
			else if ("createorganization".equals(flag)) {
				// ��ЃR�[�h�̎擾
				String kaiCode = req.getParameter("kaiCode");
				// �g�D�R�[�h�̎擾
				String dpkSsk = req.getParameter("dpkSsk");
				// ����R�[�h�̎擾
				String dpkDepCode = req.getParameter("dpkDepCode");
				// ���̂̏�����
				DPK_MST dpkMST = new DPK_MST();
				// ��ЃR�[�h�̐ݒ�
				dpkMST.setKAI_CODE(kaiCode);
				// �g�D�R�[�h�̐ݒ�
				dpkMST.setDPK_SSK(dpkSsk);
				// ����R�[�h�̐ݒ�
				dpkMST.setDPK_DEP_CODE(dpkDepCode);
				// ���x���̐ݒ�
				dpkMST.setDPK_LVL(0);
				// �Ǘ�����R�[�h���x���O�̐ݒ�
				dpkMST.setDPK_LVL_0(dpkDepCode);
				// �Ǘ�����R�[�h���x��1�̐ݒ�
				dpkMST.setDPK_LVL_1(null);
				// �Ǘ�����R�[�h���x��2�̐ݒ�
				dpkMST.setDPK_LVL_2(null);
				// �Ǘ�����R�[�h���x��3�̐ݒ�
				dpkMST.setDPK_LVL_3(null);
				// �Ǘ�����R�[�h���x��4�̐ݒ�
				dpkMST.setDPK_LVL_4(null);
				// �Ǘ�����R�[�h���x��5�̐ݒ�
				dpkMST.setDPK_LVL_5(null);
				// �Ǘ�����R�[�h���x��6�̐ݒ�
				dpkMST.setDPK_LVL_6(null);
				// �Ǘ�����R�[�h���x��7�̐ݒ�
				dpkMST.setDPK_LVL_7(null);
				// �Ǘ�����R�[�h���x��8�̐ݒ�
				dpkMST.setDPK_LVL_8(null);
				// �Ǘ�����R�[�h���x��9�̐ݒ�
				dpkMST.setDPK_LVL_9(null);

				// Date strDate = null;
				// Date endDate = null;
				// strDate = Util
				// .convertToDate(req.getParameter("strDate"));
				// endDate = Util
				// .convertToDate(req.getParameter("endDate"));
				// dpkMST.setSTR_DATE(strDate);
				// dpkMST.setEND_DATE(endDate);
				// �v���O����ID�̐ݒ�
				dpkMST.setPRG_ID(req.getParameter("prgID"));
				try {
					// ���ʂ̎擾
					logic.insert(dpkMST);
					// ���ʂ̐ݒ�
					super.dispatchResultSuccess(req, resp);
				} catch (TException ex) {
					// ����ɏ�������܂���ł���
					super.dispatchError(req, resp, ex.getMessage(), ex.getMessageArgs());
				}
			}
			// checkSskCode
			else if ("checksskcode".equals(flag)) {
				// ��ЃR�[�h�̎擾
				String kaiCode = req.getParameter("kaiCode");
				// �g�D�R�[�h�̎擾
				String dpkSsk = req.getParameter("dpkSsk");
				// map�̏�����
				Map conditions = new HashMap();
				// ��ЃR�[�h�̐ݒ�
				conditions.put("kaiCode", kaiCode);
				// �g�D�R�[�h�̐ݒ�
				conditions.put("dpkSsk", dpkSsk);
				// ���ʂ̎擾
				List list = logic.find(conditions);
				// �f�[�^�W�̏�����
				List result = new ArrayList(1);
				if (list != null && list.size() > 0) {
					// sto�̏�����
					StringTransferObject sto = new StringTransferObject();
					// sto�̐ݒ�
					sto.setValue("true");
					// ���ʂ̒ǉ�
					result.add(sto);
				}
				// ���ʂ̐ݒ�
				super.dispatchResultListObject(req, resp, result);
			} else if ("deleteorganization".equals(flag)) {
				// ��ЃR�[�h�̎擾
				String kaiCode = req.getParameter("kaiCode");
				// �g�D�R�[�h�̎擾
				String dpkSsk = req.getParameter("dpkSsk");
				// map�̏�����
				Map conditions = new HashMap();
				// ��ЃR�[�h�̐ݒ�
				conditions.put("kaiCode", kaiCode);
				// �g�D�R�[�h�̐ݒ�
				conditions.put("dpkSsk", dpkSsk);
				// ���ʂ̎擾
				List list = logic.find(conditions);
				if (list != null) {
					// ���ʂ̓��e���擾����
					Iterator ite = list.iterator();
					while (ite.hasNext()) {
						// entity�̏�����
						DPK_MST entity = (DPK_MST) ite.next();
						// �f�[�^���폜����
						logic.delete(entity);
					}
				}
				// ���ʂ̐ݒ�
				super.dispatchResultSuccess(req, resp);
			}
			// "save"
			else if ("savessk".equals(flag)) {
				// valid�̏�����
				boolean valid = true;
				// �f�[�^�̎擾
				String[] data = req.getParameterValues("data");
				if (data.length > 0) {
					// �f�[�^�̎擾
					String[] record = StringUtil.toArrayFromHTMLEscapeString(data[0]);
					// ��ЃR�[�h�̎擾
					String kaiCode = record[0];
					// �g�D�R�[�h�̎擾
					String dpkSsk = record[1];
					// map�̏�����
					Map conditions = new HashMap();
					// ��ЃR�[�h�̐ݒ�
					conditions.put("kaiCode", kaiCode);
					// �g�D�R�[�h�̐ݒ�
					conditions.put("dpkSsk", dpkSsk);
					// ���ʂ̎擾
					List list = logic.find(conditions);
					// ���ʂ̓��e���擾����
					Iterator ite = list.iterator();
					while (ite.hasNext()) {
						// entity�̏�����
						DPK_MST entity = (DPK_MST) ite.next();
						if (entity.getDPK_LVL() != 0) {
							// �f�[�^���폜����
							logic.delete(entity);
						} else {
							// the root department
							logic.update(entity);
						}
					}
				}
				for (int i = 1; i < data.length; i++) {
					// �f�[�^�̎擾
					String[] record = StringUtil.toArrayFromHTMLEscapeString(data[i]);
					// ���̂̏�����
					DPK_MST dpkMST = new DPK_MST();
					// ��ЃR�[�h�̐ݒ�
					dpkMST.setKAI_CODE(record[0]);
					// �g�D�R�[�h�̐ݒ�
					dpkMST.setDPK_SSK(record[1]);
					// ����R�[�h�̐ݒ�
					dpkMST.setDPK_DEP_CODE(record[2]);
					// ���x���̎擾
					int dpkLvl = Integer.parseInt(record[3]);
					// ���x���̐ݒ�
					dpkMST.setDPK_LVL(dpkLvl);
					// �Ǘ�����R�[�h���x���O�̐ݒ�
					dpkMST.setDPK_LVL_0(record[4]);
					// �Ǘ�����R�[�h���x��1�̐ݒ�
					dpkMST.setDPK_LVL_1(record[5]);
					// �Ǘ�����R�[�h���x��2�̐ݒ�
					dpkMST.setDPK_LVL_2(record[6]);
					// �Ǘ�����R�[�h���x��3�̐ݒ�
					dpkMST.setDPK_LVL_3(record[7]);
					// �Ǘ�����R�[�h���x��4�̐ݒ�
					dpkMST.setDPK_LVL_4(record[8]);
					// �Ǘ�����R�[�h���x��5�̐ݒ�
					dpkMST.setDPK_LVL_5(record[9]);
					// �Ǘ�����R�[�h���x��6�̐ݒ�
					dpkMST.setDPK_LVL_6(record[10]);
					// �Ǘ�����R�[�h���x��7�̐ݒ�
					dpkMST.setDPK_LVL_7(record[11]);
					// �Ǘ�����R�[�h���x��8�̐ݒ�
					dpkMST.setDPK_LVL_8(record[12]);
					// �Ǘ�����R�[�h���x��9�̐ݒ�
					dpkMST.setDPK_LVL_9(record[13]);

					// Date strDate = null;
					// Date endDate = null;
					// strDate = Util
					// .convertToDate(record[14]);
					// endDate = Util
					// .convertToDate(record[15]);
					// dpkMST.setSTR_DATE(strDate);
					// dpkMST.setEND_DATE(endDate);
					// �v���O����ID�̐ݒ�
					dpkMST.setPRG_ID(record[18]);
					// ���[�UID�̐ݒ�
					dpkMST.setUSR_ID(record[19]);
					try {
						// �f�[�^��o�^����
						logic.insert(dpkMST);
					} catch (TException ex) {
						// ���ʂ̐ݒ�
						super.dispatchError(req, resp, ex.getMessage(), ex.getMessageArgs());
						// valid�̐ݒ�
						valid = false;
						// �������I������
						break;
					}
				}
				// ���ʂ̐ݒ�
				if (valid) super.dispatchResultSuccess(req, resp);
				// ��ʕ��僌�x������
			} else if ("refHierarchy".equals(flag)) {
				searchRefHierarchy(req, resp);
			}
		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}

	/** ���X�g�o�͂�Excel��` */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excel��Ԃ�
		return new DepartmentHierarchyMasterReportExcelDefine();
	}

	/**
	 * ���僌�x������
	 * 
	 * @param req
	 * @param resp
	 */
	protected void searchRefHierarchy(HttpServletRequest req, HttpServletResponse resp) {
		// container�̏�����
		S2Container container = null;

		try {

			container = SingletonS2ContainerFactory.getContainer();
			// logic�̏�����
			DepartmentHierarchyLogic logic = (DepartmentHierarchyLogic) container
				.getComponent(DepartmentHierarchyLogic.class);
			// keys�̏�����
			Map keys = new HashMap();
			keys.put("kaiCode", req.getParameter("kaiCode"));
			keys.put("dpkSsk", req.getParameter("dpkSsk"));
			keys.put("dpkDepCode", req.getParameter("dpkDepCode"));

			// ���̂̏�����
			List list = logic.getREFItems(keys);

			super.dispatchResultList(req, resp, list);

		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}
}
