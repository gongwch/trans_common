package jp.co.ais.trans2.define;

/**
 * �`�[�`�F�b�N �G���[���ڕ���
 */
public enum SlipError {

	// �G���[���

	/** �f�[�^���Z�b�g����Ă��Ȃ� */
	NULL,

	/** �Y���f�[�^�����݂��Ȃ� */
	NOT_FOUND,

	/** ����OUT */
	TERM_OUT,

	/** �f�[�^�s�� */
	CLOSED,

	// �f�[�^���

	/** �`�[���t */
	SLIP_DATE,

	/** ����R�[�h */
	DEP_CODE,

	/** ����R�[�h(�w�b�_�[) */
	HDR_DEP_CODE,

	/** �ȖڃR�[�h */
	KMK_CODE,

	/** �ȖڃR�[�h(�w�b�_�[) */
	HDR_KMK_CODE,

	/** �⏕�ȖڃR�[�h */
	HKM_CODE,

	/** �⏕�ȖڃR�[�h(�w�b�_�[) */
	HDR_HKM_CODE,

	/** ����ȖڃR�[�h */
	UKM_CODE,

	/** ����ȖڃR�[�h(�w�b�_�[) */
	HDR_UKM_CODE,

	/** �ʉ݃R�[�h */
	CUR_CODE,

	/** �ʉ݃R�[�h(�w�b�_�[) */
	HDR_CUR_CODE,

	/** �����敪(�w�b�_�[) */
	HDR_SEI_KBN,

	/** �`�[�E�v�R�[�h */
	TEK_CODE,

	/** �s�E�v�R�[�h */
	GYO_TEK_CODE,

	/** �Ј��R�[�h */
	EMP_CODE,

	/** �Ј��R�[�h(�w�b�_�[) */
	HDR_EMP_CODE,

	/** �����R�[�h */
	TRI_CODE,

	/** �����R�[�h(�w�b�_�[) */
	HDR_TRI_CODE,

	/** �x�����@�R�[�h */
	HOH_CODE,

	/** �����x�������R�[�h */
	TRI_SJ_CODE,

	/** ��s�����R�[�h */
	CBK_CODE,

	/** �����敪 */
	SEI_KBN,

	/** �v���ЃR�[�h */
	K_KAI_CODE,

	/** ����ŃR�[�h */
	ZEI_CODE,

	/** �Ǘ�1�R�[�h */
	KNR_CODE_1,

	/** �Ǘ�2�R�[�h */
	KNR_CODE_2,

	/** �Ǘ�3�R�[�h */
	KNR_CODE_3,

	/** �Ǘ�4�R�[�h */
	KNR_CODE_4,

	/** �Ǘ�5�R�[�h */
	KNR_CODE_5,

	/** �Ǘ�6�R�[�h */
	KNR_CODE_6,

	/** ���v����1 */
	HM_1,

	/** ���v����2 */
	HM_2,

	/** ���v����3 */
	HM_3,

	/** ������ */
	HAS_DATE,

	/** ���[�g */
	RATE,

	/** ���͋��z */
	FOREIGN_AMOUNT,

	/** �M�݋��z */
	BASE_AMOUNT,

	/** ����Ŋz */
	TAX_AMOUNT,
	
	/** �Ȗڂ̌Œ蕔��O */
	ITEM_FIXED_OUT
}
