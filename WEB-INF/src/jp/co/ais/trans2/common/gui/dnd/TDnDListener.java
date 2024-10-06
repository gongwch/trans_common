package jp.co.ais.trans2.common.gui.dnd;

import java.awt.*;
import java.awt.dnd.*;

import javax.swing.tree.*;

/**
 * Object�󂯓n���p���X�i�[
 */
public interface TDnDListener {

	/**
	 * �h���b�O���̃I�u�W�F�N�g��
	 * 
	 * @return �n���I�u�W�F�N�g
	 */
	public Object getDragData();

	/**
	 * �h���b�O���̃I�u�W�F�N�g��
	 * 
	 * @param evt �h���b�O�^�[�Q�b�g�C�x���g
	 * @return �n���I�u�W�F�N�g
	 */
	public Object getDragData(DragGestureEvent evt);

	/**
	 * �I�u�W�F�N�g�h���b�v������
	 * 
	 * @param component �������R���|�[�l���g
	 * @param point �h���b�v�|�C���g
	 * @param obj �h���b�v�I�u�W�F�N�g
	 */
	public void dropData(Component component, Point point, Object obj);

	/**
	 * �I�u�W�F�N�g�h���b�v������
	 * 
	 * @param evt �h���b�v�^�[�Q�b�g�C�x���g
	 * @param obj �h���b�v�I�u�W�F�N�g
	 */
	public void dropData(DropTargetDropEvent evt, Object obj);

	/**
	 * �h���b�v��m�[�h�̃^�C�v�m�F
	 * 
	 * @param node �m�[�h
	 * @return true:�q���ڂȂ��Afalse:�q���ڂ���
	 */
	public boolean isLeaf(TreeNode node);
}
