package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import org.seasar.framework.container.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * CompanyHierarchicalMaster
 * 
 * @author AIS
 */
public class CompanyHierarchicalMasterLogicImpl implements CompanyHierarchicalMasterLogic {

	/** 会社階層マスタ */
	private EVK_MSTDao dao_;

	/** 会社コントロールマスタ */
	private ENV_MSTDao daoENV;

	/** コンテナ */
	private S2Container container;

	/**
	 * コンストラクタ.
	 * 
	 * @param container S2コンテナ
	 */
	public CompanyHierarchicalMasterLogicImpl(S2Container container) {
		this.container = container;
	}

	/**
	 * 会社階層マスタ
	 * 
	 * @param dao_ 会社階層マスタ
	 */
	public void setEVK_MSTDao(EVK_MSTDao dao_) {
		this.dao_ = dao_;
	}

	/**
	 * コントロールマスタ
	 * 
	 * @param daoENV コントロールマスタ
	 */
	public void setENV_MSTDao(ENV_MSTDao daoENV) {
		this.daoENV = daoENV;
	}

	/** 会社階層データ登録 */
	public void insert(EVK_MST dto) throws TException { // String dpkLvl0, String orgCode, String
		// dpkDepCode, String prgId, String usrId

		EVK_MST entity = this.findByKey(dto.getKAI_CODE(), dto.getDPK_SSK());
		if (entity != null) {

			// エラー通知(すでに存在します)
			throw new TException("W00005", "");
		}

		ENV_MST entity2 = daoENV.getENV_MST(dto.getDPK_LVL_0());

		if (entity2 != null) {
			dto.setSTR_DATE(entity2.getSTR_DATE());
			dto.setEND_DATE(entity2.getEND_DATE());
		}

		dao_.insert(dto);

	}

	/**
	 * @see jp.co.ais.trans.master.logic.CompanyHierarchicalMasterLogic#delete(jp.co.ais.trans.master.entity.EVK_MST)
	 */
	public void delete(EVK_MST dto) {

		dao_.delete(dto);
	}

	public void update(List listUpdate) {

		if (listUpdate == null) return;

		CompanyHOrgCodeDao dao_del = (CompanyHOrgCodeDao) container.getComponent(CompanyHOrgCodeDao.class);
		EVK_MSTDao dao = (EVK_MSTDao) container.getComponent(EVK_MSTDao.class);

		EVK_MST entity = (EVK_MST) listUpdate.get(0);
		dao_del.delete(entity.getKAI_CODE(), entity.getDPK_SSK());

		for (int i = 0; i < listUpdate.size(); i++) {
			entity = (EVK_MST) listUpdate.get(i);

			dao.insert(entity);
		}
	}

	/**
	 * CompanyHierarchicalMasterデータ検索 1行のみ
	 * 
	 * @param KAI_CODE
	 * @param DPK_SSK
	 * @param DPK_DEP_CODE
	 * @return EVK_MST
	 */
	public EVK_MST findByKey(String KAI_CODE, String DPK_SSK, String DPK_DEP_CODE) {
		return dao_.getEVK_MST(KAI_CODE, DPK_SSK, DPK_DEP_CODE);
	}

	/**
	 * 会社コード、組織コードに一致する管理会社コード一覧
	 * 
	 * @param KAI_CODE
	 * @param DPK_SSK
	 * @return EVK_MST
	 */
	public EVK_MST findByKey(String KAI_CODE, String DPK_SSK) {
		return dao_.getEVK_MSTSsk(KAI_CODE, DPK_SSK);
	}

	/**
	 * @param kaiCode
	 * @param sskCode
	 * @return List
	 */
	public List getWithOutCom(String kaiCode, String sskCode) {

		CompanyHGetENVListDao dao = (CompanyHGetENVListDao) container.getComponent(CompanyHGetENVListDao.class);

		return dao.getWithOutCom(kaiCode, sskCode);

	}

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getOrgCode(String kaiCode) {

		CompanyHOrgCodeDao dao = (CompanyHOrgCodeDao) container.getComponent(CompanyHOrgCodeDao.class);

		return dao.getAllOrgCode(kaiCode);
	}

	/**
	 * @param kaiCode
	 * @param sskCode
	 */
	public void delete(String kaiCode, String sskCode) {

		CompanyHOrgCodeDao dao = (CompanyHOrgCodeDao) container.getComponent(CompanyHOrgCodeDao.class);

		dao.delete(kaiCode, sskCode);

	}

	/**
	 * @param kaiCode
	 * @param sskCode
	 * @return List
	 */
	public List getComLvl(String kaiCode, String sskCode) {

		CompanyHLvlListDao dao = (CompanyHLvlListDao) container.getComponent(CompanyHLvlListDao.class);

		return dao.getComLvl(kaiCode, sskCode);

	}

	/**
	 * @param kaiCode
	 * @param sskCode
	 * @throws TException
	 */
	public void getSskCode(String kaiCode, String sskCode) throws TException {
		EVK_MST entity = this.findByKey(kaiCode, sskCode);

		if (entity != null) {

			// エラー通知(すでに存在します)
			throw new TException("W00005", sskCode);
		}
	}

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getENVListNewSsk(String kaiCode) {

		CompanyHGetENVListDao dao = (CompanyHGetENVListDao) container.getComponent(CompanyHGetENVListDao.class);

		return dao.getENVListNewSsk(kaiCode);
	}

	/**
	 * 会社コントロールデータ取得
	 * 
	 * @param kaiCode 会社コード
	 * @return 会社コントロールデータ
	 */
	public ENV_MST getENV_MST(String kaiCode) {

		return daoENV.getENV_MST(kaiCode);
	}

}
