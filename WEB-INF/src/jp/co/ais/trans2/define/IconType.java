package jp.co.ais.trans2.define;

/** Icon�摜 */
public enum IconType {

	/** ���� */
	COPY,

	/** �폜 */
	DELETE,

	/** �C�� */
	EDIT,

	/** �G�N�Z�� */
	EXCEL,

	/** �V�K */
	NEW,

	/** �����i�p�^�[���j */
	PATTERN,

	/** �v���r���[ */
	PREVIEW,

	/** ���� */
	SEARCH,

	/** �m�� */
	SETTLE,

	/** �擪��(���[���) */
	ALLOW_FIRST,

	/** �O��(�����) */
	ALLOW_PREVIOUS,

	/** ����(�E���) */
	ALLOW_NEXT,

	/** �Ō��(�E�[���) */
	ALLOW_LAST,

	/** �Y�t�t�@�C�� */
	ATTACHMENT,

	/** �Y�t���� */
	ATTACHMENT_COMPLETE,

	/** ���� */
	HISTORY,

	/** �w���v */
	HELP;

	/**
	 * Icon����Ԃ�
	 * 
	 * @return Icon��
	 */
	public String getIconName() {
		return getIconName(this);
	}

	/**
	 * Icon����Ԃ�
	 * 
	 * @param it Icon
	 * @return Icon��
	 */
	public static String getIconName(IconType it) {

		switch (it) {
			case COPY: // ����
				return "images/copy.png";

			case DELETE: // �폜
				return "images/delete.png";

			case EDIT: // �C��
				return "images/edit.png";

			case EXCEL: // �G�N�Z��
				return "images/excel.png";

			case NEW: // �V�K
				return "images/new.png";

			case PATTERN: // �����i�p�^�[���j
				return "images/pattern.png";

			case PREVIEW: // �v���r���[
				return "images/preview.png";

			case SEARCH: // ����
				return "images/search.png";

			case SETTLE: // �m��
				return "images/settle.png";

			case ALLOW_FIRST: // ���[���
				return "images/allow_first.png";

			case ALLOW_PREVIOUS: // �����
				return "images/allow_previos.png";

			case ALLOW_NEXT: // �E���
				return "images/allow_next.png";

			case ALLOW_LAST: // �E�[���
				return "images/allow_last.png";

			case ATTACHMENT: // �Y�t
				return "images/attachment.png";

			case ATTACHMENT_COMPLETE: // �Y�t����
				return "images/attachment_complete.png";
			case HISTORY: // ����
				return "images/history.png";

			case HELP: // �w���v
				return "images/help.png";
			default:
				return null;
		}
	}
}