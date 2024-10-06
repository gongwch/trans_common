package jp.co.ais.trans2.model.department;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * •”–åŠK‘wƒCƒ“ƒ^[ƒtƒF[ƒXB
 * 
 * @author AIS
 */
public interface DepartmentOrganizationManager {

	/**
	 * w’èğŒ‚ÉŠY“–‚·‚é•”–åî•ñ‚ğ•Ô‚·
	 * 
	 * @param condition ŒŸõğŒ
	 * @return w’èğŒ‚ÉŠY“–‚·‚é•”–åî•ñ
	 * @throws TException
	 */
	public List<Department> get(DepartmentSearchCondition condition) throws TException;

	/**
	 * w’èğŒ‚ÉŠY“–‚·‚é•”–å‘gDî•ñ‚ğ•Ô‚·
	 * 
	 * @param condition ŒŸõğŒ
	 * @return w’èğŒ‚ÉŠY“–‚·‚é•”–å‘gDî•ñ
	 * @throws TException
	 */
	public List<DepartmentOrganization> getDepartmentOrganization(DepartmentOrganizationSearchCondition condition)
		throws TException;

	/**
	 * w’èğŒ‚ÉŠY“–‚·‚é•”–å‘gDî•ñ‚ğ•Ô‚·
	 * 
	 * @param condition ŒŸõğŒ
	 * @return w’èğŒ‚ÉŠY“–‚·‚é•”–å‘gDî•ñ(•”–åŠK‘wƒ}ƒXƒ^—p)
	 * @throws TException
	 */
	public List<DepartmentOrganization> getDepartmentOrganizationData(DepartmentOrganizationSearchCondition condition)
		throws TException;

	/**
	 * w’èğŒ‚ÉŠY“–‚·‚é•”–å‘gDî•ñ‚ğ•Ô‚·
	 * 
	 * @param condition ŒŸõğŒ
	 * @return w’èğŒ‚ÉŠY“–‚·‚é•”–å‘gDî•ñ(•”–åŠK‘wƒ}ƒXƒ^—p)
	 * @throws TException
	 */
	public DepartmentOrganization getDepartmentOrganizationName(DepartmentOrganizationSearchCondition condition)
		throws TException;

	/**
	 * •”–åŠK‘wLEVEL0‚ğ“o˜^‚·‚éB(V‹K)
	 * 
	 * @param departmentOrganization •”–åŠK‘wLEVEL0
	 * @throws TException
	 */
	public void entryDepartmentOrganization(DepartmentOrganization departmentOrganization) throws TException;

	/**
	 * •”–åŠK‘w‚Æ‘gD–¼Ì‚ğ“o˜^‚·‚éB
	 * 
	 * @param sskCode
	 * @param sskName
	 * @param list •”–åŠK‘w
	 * @throws TException
	 */
	public void entryDepartmentOrganization(String sskCode, String sskName, List<DepartmentOrganization> list)
		throws TException;

	/**
	 * •”–åŠK‘wLEVEL–¼Ì“o˜^(V‹K)
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entryDepartmentOrganizationName(DepartmentOrganization bean) throws TException;

	/**
	 * •”–åŠK‘w‚ğíœ‚·‚éB
	 * 
	 * @param departmentOrganization •”–åŠK‘w
	 * @throws TException
	 */
	public void deleteDepartmentOrganization(DepartmentOrganization departmentOrganization) throws TException;

	/**
	 * •”–åŠK‘w–¼Ìˆê——‚ğƒGƒNƒZƒ‹Œ`®‚Å•Ô‚·
	 * 
	 * @param condition ŒŸõğŒ
	 * @return ƒGƒNƒZƒ‹Œ`®‚Ì•”–åŠK‘wˆê——
	 * @throws TException
	 */
	public byte[] getDepartmentOrganizationNameExcel(DepartmentOrganizationSearchCondition condition) throws TException;

	/**
	 * •”–åŠK‘w–¼Ì‚ğ“o˜^‚·‚éB
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entryDepartmentNameOrganization(DepartmentOrganization bean) throws TException;
}
