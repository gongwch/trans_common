package jp.co.ais.trans2.model.slip;

import jp.co.ais.trans2.model.*;

/**
 * Slipo^
 */
public class SlipEntry extends TModel {

	/** wb_Dao */
	public SWK_HDRDao headerDao;

	/** ΎΧDao */
	public SWK_DTLDao detailDao;

	/**
	 * wb_[Dao
	 * 
	 * @param headerDao wb_[Dao
	 */
	public void setHeaderDao(SWK_HDRDao headerDao) {
		this.headerDao = headerDao;
	}

	/**
	 * ΎΧDao
	 * 
	 * @param detailDao ΎΧDao
	 */
	public void setDetailDao(SWK_DTLDao detailDao) {
		this.detailDao = detailDao;
	}

	/**
	 * o^
	 * 
	 * @param slip `[
	 */
	public void entry(Slip slip) {
		entryHeader(headerDao, slip);
		entryDetails(detailDao, slip);
	}

	/**
	 * wb_o^
	 * 
	 * @param dao ΞΫe[u(DAO)
	 * @param slip `[
	 */
	protected void entryHeader(SWK_HDRDao dao, Slip slip) {
		dao.insert(slip.getHeader());
	}

	/**
	 * ΎΧo^
	 * 
	 * @param dao ΞΫe[u(DAO)
	 * @param slip `[
	 */
	protected void entryDetails(SWK_DTLDao dao, Slip slip) {
		dao.insert(slip.getDetails());
	}
}
