package jp.co.ais.trans2.model.slip;

import jp.co.ais.trans2.model.*;

/**
 * Slip“o˜^
 */
public class SlipEntry extends TModel {

	/** ƒwƒbƒ_Dao */
	public SWK_HDRDao headerDao;

	/** –¾×Dao */
	public SWK_DTLDao detailDao;

	/**
	 * ƒwƒbƒ_[Dao
	 * 
	 * @param headerDao ƒwƒbƒ_[Dao
	 */
	public void setHeaderDao(SWK_HDRDao headerDao) {
		this.headerDao = headerDao;
	}

	/**
	 * –¾×Dao
	 * 
	 * @param detailDao –¾×Dao
	 */
	public void setDetailDao(SWK_DTLDao detailDao) {
		this.detailDao = detailDao;
	}

	/**
	 * “o˜^
	 * 
	 * @param slip “`•[
	 */
	public void entry(Slip slip) {
		entryHeader(headerDao, slip);
		entryDetails(detailDao, slip);
	}

	/**
	 * ƒwƒbƒ_“o˜^
	 * 
	 * @param dao ‘ÎÛƒe[ƒuƒ‹(DAO)
	 * @param slip “`•[
	 */
	protected void entryHeader(SWK_HDRDao dao, Slip slip) {
		dao.insert(slip.getHeader());
	}

	/**
	 * –¾×“o˜^
	 * 
	 * @param dao ‘ÎÛƒe[ƒuƒ‹(DAO)
	 * @param slip “`•[
	 */
	protected void entryDetails(SWK_DTLDao dao, Slip slip) {
		dao.insert(slip.getDetails());
	}
}
