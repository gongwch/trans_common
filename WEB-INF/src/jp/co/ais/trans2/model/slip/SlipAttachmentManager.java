package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * “`•[“Y•tƒCƒ“ƒ^[ƒtƒF[ƒXB
 * 
 * @author AIS
 */
public interface SlipAttachmentManager {

	/**
	 * “Á’è‚Ì“`•[‚Ì“Y•tî•ñ‚ğİ’è‚·‚é
	 * 
	 * @param slip “`•[
	 * @return “Y•tî•ñ<ƒtƒ@ƒCƒ‹–¼, ƒoƒCƒiƒŠ>
	 * @throws TException
	 */
	public List<SWK_ATTACH> get(Slip slip) throws TException;

	/**
	 * “Á’è‚Ì“`•[‚Ì“Y•tî•ñ‚ğİ’è‚·‚é
	 * 
	 * @param companyCode
	 * @param slipNo
	 * @return “Y•tî•ñ<ƒtƒ@ƒCƒ‹–¼, ƒoƒCƒiƒŠ>
	 * @throws TException
	 */
	public List<SWK_ATTACH> get(String companyCode, String slipNo) throws TException;

	/**
	 * “`•[“Y•t‚Ì“o˜^
	 * 
	 * @param slip “`•[
	 */
	public void entry(Slip slip);

	/**
	 * “Y•t‚Ì“o˜^
	 * 
	 * @param entity “Y•t
	 */
	public void entry(SWK_ATTACH entity);

	/**
	 * “Y•tî•ñ‚Ìíœ
	 * 
	 * @param list List<SWK_ATTACH>
	 */
	public void delete(List<SWK_ATTACH> list);

	/**
	 * “Á’è‚Ì“`•[‚Ì“Y•tî•ñ‚Ìíœ
	 * 
	 * @param companyCode
	 * @param slipNo
	 */
	public void delete(String companyCode, String slipNo);

	/**
	 * “Á’è‚Ì“`•[‚Ì“Y•tî•ñ‚Ìíœ
	 * 
	 * @param companyCode
	 * @param slipNo
	 * @param sEQ SEQ
	 */
	public void delete(String companyCode, String slipNo, Integer sEQ);
}
