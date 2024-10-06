package jp.co.ais.trans2.model.slip.parts;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.util.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * “`•[”Ô†Ì”Ô
 */
public class SlipNoCreator extends TModel {

	/**
	 * “`•[”Ô†‚ğŠ„‚èU‚é
	 * 
	 * @param slip Œ³‚Æ‚È‚é“`•[
	 * @return “`•[”Ô†
	 */
	public String newSlipNo(Slip slip) {

		SWK_HDR header = slip.getHeader();

		String kaiCode = header.getKAI_CODE();
		String denDate = DateUtil.toYMDString(header.getSWK_DEN_DATE()); // “`•[“ú•t
		String depCode = header.getSWK_IRAI_DEP_CODE(); // •”–åƒR[ƒh
		String sysKbn = header.getSWK_SYS_KBN(); // ƒVƒXƒeƒ€‹æ•ª
		String slipType = header.getSWK_DEN_SYU(); //“`•[í•Ê

		return BizUtil.getSlipNo(kaiCode, getUserCode(), depCode, sysKbn, denDate, slipType, 1);
	}
}
