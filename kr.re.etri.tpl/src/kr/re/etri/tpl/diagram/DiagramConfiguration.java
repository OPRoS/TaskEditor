package kr.re.etri.tpl.diagram;

import java.util.HashMap;

import kr.re.etri.tpl.diagram.util.ItemState;
import kr.re.etri.tpl.taskmodel.RelationShip;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;

/**
 * ���̾�׷��� ���� ������ ����ִ� Ŭ�����̴�.
 * �� Ŭ������ ������ instance ���� �����ȴ�. 
 * @author sfline
 *
 */
public class DiagramConfiguration {

	/** ��ȿ���� ���� ���� */
	public static final int NONE = 0;
	/** ��ȿ ���� */
	public static final int VALID = 1;
	/** �𵨺� Line Color �� */
	private HashMap<Long, Color> lineColorMap;
	/** �𵨺� Forground Color �� */
	private HashMap<Long, Color> fgColorMap;
	/** �𵨺� Background Color �� */
	private HashMap<Long, Color> bgColorMap;
	/** Connection �� Relation �� Color ��*/
	private HashMap<Integer, Color> relationColorMap;
	/** �𵨺� Font �� */
	private HashMap<Long, Font> fontMap;
	/** �𵨺� Background Color �� */
	private HashMap<Long, Color> labelColorMap;
	/** �� Ŭ������ �̱� instance */
	private static DiagramConfiguration instance;
	
	/** ���̾�׷��� ���� ���� instance �� �����Ѵ�. */
	private DiagramConfiguration() {
		lineColorMap = new HashMap<Long, Color>();
		fgColorMap = new HashMap<Long, Color>();
		bgColorMap = new HashMap<Long, Color>();
		relationColorMap = new HashMap<Integer, Color>();
		fontMap = new HashMap<Long, Font>();
		labelColorMap = new HashMap<Long, Color>();
	}

	/**
	 * DiagramConfiguration �� instance �� �����Ѵ�.
	 * @return
	 */
	public static DiagramConfiguration getInstance() {
		if(instance == null) {
			instance = new DiagramConfiguration();
		}
		
		return instance;
	}

	/**
	 * �𵨺� Line Color �� �����Ѵ�. 
	 * @param item �� Id
	 * @param option ���� ����
	 * @return Color
	 */
	public Color getItemLineColor(int item, int option) {
		Color color = null;
		long key = ((((long)item) & 0xFFFFFFFF)<< 32 | (option & 0xFFFFFFFF));
		switch(item) {
		case TaskModelPackage.TASK_ELEMENT:
		case TaskModelPackage.CONNECTOR_ELEMENT:
		case TaskModelPackage.WORKER_ELEMENT:
			color = lineColorMap.get(key);
			if(color == null) {
				if((option & ItemState.ITEM_INCLUDED) == ItemState.ITEM_INCLUDED) {
					color = new Color(null, 102,102,102);
				}
				else {
					color= new Color(null, 0x00, 0x7f, 0xc6);
				}
				lineColorMap.put(key, color);
			}
			break;
		case TaskModelPackage.STATE_ELEMENT:
			color = lineColorMap.get(key);
			if(color == null) {
				if((option & ItemState.ITEM_INCLUDED) == ItemState.ITEM_INCLUDED) {
					color = new Color(null, 229, 195, 165);
				}
				else {
					color = new Color(null, 0x35, 0x64, 0xbb);
				}
				lineColorMap.put(key, color);
			}
			break;
		case TaskModelPackage.ACTION_ELEMENT:
			color = lineColorMap.get(key);
			if(color == null) {
				if((option & ItemState.ITEM_INCLUDED) == ItemState.ITEM_INCLUDED) {
					color = new Color(null, 229, 195, 165);
				}
				else {
					color = new Color(null, 165, 195, 229);
				}
				lineColorMap.put(key, color);
			}
			break;
		case TaskModelPackage.WITH_ELEMENT:
			color = lineColorMap.get(key);
			if (color == null) {
				color = new Color(null, 0x4e, 0x7b, 0xcc);
				lineColorMap.put(key, color);
			}
			break;
		case TaskModelPackage.EXPAND_TRANS_ELEMENT:
			color = lineColorMap.get(key);
			if (color == null) {
				color = new Color(null, 0x00, 0xc1, 0x6e);
				lineColorMap.put(key, color);
			}
			break;
		default:
			color = lineColorMap.get(-1L);
			if(color == null) {
				color = new Color(null, 0, 0, 0);
				lineColorMap.put(-1L, color);
			}
		}

		return color;
	}

	/**
	 * ���� Foreground Color �� �����Ѵ�.
	 * @param item ��
	 * @param option ���� ����
	 * @return Color
	 */
	public Color getItemFGColor(int item, int option) {
		Color color = null;
		long key = ((((long)item) & 0xFFFFFFFF)<< 32 | (option & 0xFFFFFFFF));
		switch(item) {
		case TaskModelPackage.MODEL_DIAGRAM:
			color = fgColorMap.get(key);
			if(color == null) {
				color = new Color(null, 75, 88, 120);
				fgColorMap.put(key, color);
			}
			break;
		case TaskModelPackage.TASK_ELEMENT:
			color = fgColorMap.get(key);
			if(color == null) {
				color = new Color(null, 0xff, 0xff, 0xff);
				fgColorMap.put(key, color);
			}
			break;
		case TaskModelPackage.STATE_ELEMENT:
			color = fgColorMap.get(key);
			if(color == null) {
				color = new Color(null, 0x19, 0x34, 0x6b);
				fgColorMap.put(key, color);
			}
			break;
		case TaskModelPackage.ACTION_ELEMENT:
			color = fgColorMap.get(key);
			if(color == null) {
				color = new Color(null, 37, 126, 20);
				fgColorMap.put(key, color);
			}
			break;
		case TaskModelPackage.CONNECTOR_ELEMENT:
			color = fgColorMap.get(key);
			if (color == null) {
				color = new Color(null, 0xff, 0xff, 0xff);
				fgColorMap.put(key, color);
			}
			break;
		case TaskModelPackage.WORKER_ELEMENT:
			color = fgColorMap.get(key);
			if (color == null) {
				color = new Color(null, 0xff, 0xff, 0xff);
				fgColorMap.put(key, color);
			}
			break;
		case TaskModelPackage.WITH_ELEMENT:
			color = fgColorMap.get(key);
			if (color == null) {
				color = new Color(null, 0x19, 0x34, 0x6b);
				fgColorMap.put(key, color);
			}
			break;
		case TaskModelPackage.EXPAND_TRANS_ELEMENT:
			color = fgColorMap.get(key);
			if (color == null) {
				color = new Color(null, 0x06, 0x51, 0x56);
				fgColorMap.put(key, color);
			}
			break;
		default:
			color = fgColorMap.get(-1);
			if(color == null) {
				color = new Color(null, 50, 50, 50);
				fgColorMap.put(-1L, color);
			}
		}

		return color;
	}
	
	/**
	 * ���� Background Color �� �����Ѵ�.
	 * @param item ��
	 * @param option ���� ����
	 * @return Color
	 */
	public Color getItemBGColor(int item, int option) {
		Color color = null;
		long key = ((((long)item) & 0xFFFFFFFF)<< 32 | (option & 0xFFFFFFFF));
		switch(item) {
		case TaskModelPackage.MODEL_DIAGRAM:
			color = bgColorMap.get(key);
			if(color == null) {
				color = new Color(null, 250, 250, 235);
				bgColorMap.put(key, color);
			}
			break;
		case TaskModelPackage.TASK_ELEMENT:
		case TaskModelPackage.CONNECTOR_ELEMENT:
		case TaskModelPackage.WORKER_ELEMENT:
			color = bgColorMap.get(key);
			if(color == null) {
				if ((option & ItemState.ITEM_INCLUDED) == ItemState.ITEM_INCLUDED) {
					color = new Color(null, 0xf0, 0xf0, 0xf0);
				}
				else {
					color = new Color(null, 0x59, 0xb9, 0xee);
				}
				bgColorMap.put(key, color);
			}
			break;
		case TaskModelPackage.STATE_ELEMENT:
			color = bgColorMap.get(key);
			if(color == null) {
				if ((option & ItemState.ITEM_INCLUDED) == ItemState.ITEM_INCLUDED) {
					color = new Color(null, 0xff, 0xff, 0xff);
				}
				else {
					color = new Color(null, 0xff, 0xff, 0xff);
				}
				bgColorMap.put(key, color);
			}
			break;
		case TaskModelPackage.ACTION_ELEMENT:
			color = bgColorMap.get(key);
			if(color == null) {
				color = new Color(null, 217, 238, 169);
				bgColorMap.put(key, color);
			}
			break;
		case TaskModelPackage.WITH_ELEMENT:
			color = bgColorMap.get(key);
			if (color == null) {
				color = new Color(null, 0xff, 0xff, 0xff);
				bgColorMap.put(key, color);
			}
			break;
		case TaskModelPackage.EXPAND_TRANS_ELEMENT:
			color = bgColorMap.get(key);
			if (color == null) {
				color = new Color(null, 0xff, 0xff, 0xff);
				bgColorMap.put(key, color);
			}
			break;
		default:
			color = bgColorMap.get(-1L);
			if(color == null) {
				color = new Color(null, 255, 255, 255);
				bgColorMap.put(-1L, color);
			}
		}

		return color;
	}

	/**
	 * relation �� Connection �� Color �� �����Ѵ�. 
	 * @param relation Relation
	 * @return Color
	 */
	public Color getRelationLineColor(int relation) {
		Color color = null;
		switch(relation) {
		case RelationShip.TASK_CALL_VALUE:
			color = relationColorMap.get(relation);
			if(color == null) {
				color = new Color(null, 18, 95, 247);
				relationColorMap.put(relation, color);
			}
			break;
		case RelationShip.ACTION_CALL_VALUE:
			color = relationColorMap.get(relation);
			if(color == null) {
				color = new Color(null, 97, 97, 97);
				relationColorMap.put(relation, color);
			}
			break;
		case RelationShip.TRANSITION_VALUE:
			color = relationColorMap.get(relation);
			if(color == null) {
				color = new Color(null, 97, 97, 97);
				relationColorMap.put(relation, color);
			}
			break;
		case RelationShip.INCLUDE_VALUE:
			color = relationColorMap.get(relation);
			if(color == null) {
				color = new Color(null, 0, 0, 0);
				relationColorMap.put(relation, color);
			}
			break;
		default:
			color = relationColorMap.get(-1);
			if(color == null) {
				color = new Color(null, 0, 0, 0);
				relationColorMap.put(-1, color);
			}
			break;
		}

		return color;
	}

	public Font getItemFont(int item, int option) {
		FontData[] fd = null;
		Font font = null;
		
		long key = ((((long)item) & 0xFFFFFFFF)<< 32 | (option & 0xFFFFFFFF));
		switch(item) {
		case TaskModelPackage.TASK_ELEMENT:
		case TaskModelPackage.CONNECTOR_ELEMENT:
		case TaskModelPackage.WORKER_ELEMENT:
			font = fontMap.get(key);
			if(font == null) {
				fd = new FontData[]{new FontData("Candara", 11, SWT.BOLD)};
				font = new Font(null, fd);
				fontMap.put(key, font);
			}
			break;
		case TaskModelPackage.STATE_ELEMENT:
			font = fontMap.get(key);
			if(font == null) {
				fd = new FontData[]{new FontData("Candara", 10, SWT.BOLD)};
				font = new Font(null, fd);
				fontMap.put(key, font);
			}
			break;
		case TaskModelPackage.ACTION_ELEMENT:
			font = fontMap.get(key);
			if(font == null) {
				fd = new FontData[]{new FontData("Candara", 10, SWT.BOLD)};
				font = new Font(null, fd);
				fontMap.put(key, font);
			}
			break;
		case TaskModelPackage.WITH_ELEMENT:
			font = fontMap.get(key);
			if(font == null) {
				fd = new FontData[]{new FontData("Candara", 10, SWT.BOLD)};
				font = new Font(null, fd);
				fontMap.put(key, font);
			}
			break;
		case TaskModelPackage.EXPAND_TRANS_ELEMENT:
			font = fontMap.get(key);
			if(font == null) {
				fd = new FontData[]{new FontData("Candara", 10, SWT.BOLD)};
				font = new Font(null, fd);
				fontMap.put(key, font);
			}
			break;
		case TaskModelPackage.CONNECTION_ELEMENT:
			font = fontMap.get(key);
			if(font == null) {
				fd = new FontData[]{new FontData("Candara", 10, SWT.BOLD)};
				font = new Font(null, fd);
				fontMap.put(key, font);
			}
			break;
		default:
			font = fontMap.get(key);
			if(font == null) {
				fd = new FontData[]{new FontData("Candara", 10, SWT.BOLD)};
				font = new Font(null, fd);
				fontMap.put(key, font);
			}
		}
		
		return font;
	}
	
	/**
	 * ���� Background Color �� �����Ѵ�.
	 * @param item ��
	 * @param option ���� ����
	 * @return Color
	 */
	public Color getItemLabelColor(int item, int option) {
		Color color = null;
		long key = ((((long)item) & 0xFFFFFFFF)<< 32 | (option & 0xFFFFFFFF));
		switch(item) {
		case TaskModelPackage.TASK_ELEMENT:
		case TaskModelPackage.CONNECTOR_ELEMENT:
		case TaskModelPackage.WORKER_ELEMENT:
			color = labelColorMap.get(key);
			if(color == null) {
				if((option & ItemState.ITEM_INCLUDED) == ItemState.ITEM_INCLUDED) {
					color = new Color(null, 112,112,112);
				}
				else {
					color= new Color(null, 0x00, 0x94, 0xe6);
				}
				labelColorMap.put(key, color);
			}
			break;
		case TaskModelPackage.STATE_ELEMENT:
			color = labelColorMap.get(key);
			if(color == null) {
				if((option & ItemState.ITEM_INCLUDED) == ItemState.ITEM_INCLUDED) {
					color = new Color(null, 229, 195, 165);
				}
				else {
					color = new Color(null, 0x4e, 0x7b, 0xcc);
				}
				labelColorMap.put(key, color);
			}
			break;
		case TaskModelPackage.ACTION_ELEMENT:
			color = labelColorMap.get(key);
			if(color == null) {
				if((option & ItemState.ITEM_INCLUDED) == ItemState.ITEM_INCLUDED) {
					color = new Color(null, 229, 195, 165);
				}
				else {
					color = new Color(null, 165, 195, 229);
				}
				labelColorMap.put(key, color);
			}
			break;
		case TaskModelPackage.WITH_ELEMENT:
			color = labelColorMap.get(key);
			if (color == null) {
				color = new Color(null, 0x4e, 0x7b, 0xcc);
				labelColorMap.put(key, color);
			}
			break;
		case TaskModelPackage.EXPAND_TRANS_ELEMENT:
			color = labelColorMap.get(key);
			if (color == null) {
				color = new Color(null, 0x00, 0xc1, 0x6e);
				labelColorMap.put(key, color);
			}
			break;
		default:
			color = labelColorMap.get(-1L);
			if(color == null) {
				color = new Color(null, 0, 0, 0);
				labelColorMap.put(-1L, color);
			}
		}

		return color;
	}

}
