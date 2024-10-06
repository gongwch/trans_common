package jp.co.ais.trans2.model.bs;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * B/SŠ¨’èÁƒ}ƒl[ƒWƒƒ
 */
public interface BSEraseManager {

	/**
	 * ”r‘¼‚ğŠ|‚¯‚é
	 * 
	 * @param condition ‘ÎÛBSŠ¨’è(ğŒ)
	 * @throws TException ”r‘¼¸”s
	 */
	public void lock(BSEraseCondition condition) throws TException;

	/**
	 * ”r‘¼‰ğœ
	 * 
	 * @param condition ‘ÎÛBSŠ¨’è(ğŒ)
	 * @throws TException ”r‘¼¸”s
	 */
	public void unlock(BSEraseCondition condition) throws TException;

	/**
	 * B/SŠ¨’èƒf[ƒ^‚Ìæ“¾
	 * 
	 * @param condition ğŒ
	 * @return List ƒf[ƒ^
	 * @throws TException æ“¾¸”s
	 */
	public List<SWK_DTL> get(BSEraseCondition condition) throws TException;

	/**
	 * B/SŠ¨’èÁ‚Ì•œŒ³
	 * 
	 * @param condition ğŒ(‰ïĞƒR[ƒh, “`•[”Ô†)
	 * @throws TRuntimeException •œŒ³¸”s
	 */
	public void restoreBsBalance(SlipCondition condition);

	/**
	 * B/SŠ¨’èÁ‚ÌXV
	 * 
	 * @param slip “`•[
	 */
	public void updateBsBalance(Slip slip);

}