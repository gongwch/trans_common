package jp.co.ais.trans2.model.company;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * ‰ïĞƒRƒ“ƒgƒ[ƒ‹ƒ}ƒXƒ^
 * 
 * @author AIS
 */
public interface CompanyOrganizationManager {

	/**
	 * w’èğŒ‚ÉŠY“–‚·‚é‰ïĞî•ñ‚ğ•Ô‚·B
	 * 
	 * @param condition ŒŸõğŒ
	 * @return w’èğŒ‚ÉŠY“–‚·‚é‰ïĞî•ñ
	 * @throws TException
	 */
	public List<Company> get(CompanySearchCondition condition) throws TException;

	/**
	 * w’èğŒ‚ÉŠY“–‚·‚é‰ïĞ‘gDî•ñ‚ğ•Ô‚·
	 * 
	 * @param condition ŒŸõğŒ
	 * @return w’èğŒ‚ÉŠY“–‚·‚é‰ïĞ‘gDî•ñ
	 * @throws TException
	 */
	public List<CompanyOrganization> getCompanyOrganization(CompanyOrganizationSearchCondition condition)
		throws TException;

	/**
	 * w’èğŒ‚ÉŠY“–‚·‚é‰ïĞ‘gDî•ñ‚ğ•Ô‚·
	 * 
	 * @param condition ŒŸõğŒ
	 * @return w’èğŒ‚ÉŠY“–‚·‚é‰ïĞ‘gDî•ñ(‰ïĞŠK‘wƒ}ƒXƒ^—p)
	 * @throws TException
	 */
	public List<CompanyOrganization> getCompanyOrganizationData(CompanyOrganizationSearchCondition condition)
		throws TException;

	/**
	 * w’èğŒ‚ÉŠY“–‚·‚é‰ïĞ‘gDî•ñ‚ğ•Ô‚·
	 * 
	 * @param condition ŒŸõğŒ
	 * @return w’èğŒ‚ÉŠY“–‚·‚é‰ïĞ‘gDî•ñ(‰ïĞŠK‘wƒ}ƒXƒ^—p)
	 * @throws TException
	 */
	public CompanyOrganization getCompanyOrganizationName(CompanyOrganizationSearchCondition condition)
		throws TException;

	/**
	 * ‰ïĞŠK‘w–¼Ì‚ğ“o˜^‚·‚éB
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entryCompanyNameOrganization(CompanyOrganization bean) throws TException;

	/**
	 * ‰ïĞŠK‘wLEVEL0“o˜^(V‹K)
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entryCompanyOrganization(CompanyOrganization bean) throws TException;

	/**
	 * ‰ïĞŠK‘wíœ
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void deleteCompanyOrganization(CompanyOrganization bean) throws TException;

	/**
	 * ‰ïĞŠK‘w–¼Ìˆê——‚ğƒGƒNƒZƒ‹Œ`®‚Å•Ô‚·
	 * 
	 * @param condition ŒŸõğŒ
	 * @return ƒGƒNƒZƒ‹Œ`®‚Ì•”–åŠK‘wˆê——
	 * @throws TException
	 */
	public byte[] getCompanyOrganizationNameExcel(CompanyOrganizationSearchCondition condition) throws TException;

	/**
	 * ‰ïĞŠK‘w‚ğ“o˜^‚·‚éB
	 * 
	 * @param sskCode
	 * @param sskName
	 * @param list ‰ïĞŠK‘w
	 * @throws TException
	 */
	public void entryCompanyOrganization(String sskCode, String sskName, List<CompanyOrganization> list)
		throws TException;

	/**
	 * w’èğŒ‚ÉŠY“–‚·‚é‰ïĞî•ñ‚ğ•Ô‚·B
	 * 
	 * @param condition ŒŸõğŒ
	 * @return w’èğŒ‚ÉŠY“–‚·‚é‰ïĞî•ñ
	 * @throws TException
	 */
	public List<String> getCompanyCodeList(CompanyOutputCondition condition) throws TException;

}
