package kr.re.etri.tpl.diagram.util;

public class ItemState {
	// 모든 Item 공통 영역 : 16~31 Bit
	public final static int ITEM_CREATED	= 1 << 16;
	public final static int ITEM_INCLUDED	= 1 << 17;
	
	public final static int TYPE_MASK		= 0x03 << 20;
	public final static int TYPE_BOOLEAN	= 0x00 << 20;
	public final static int TYPE_INTEGER	= 0x01 << 20;
	public final static int TYPE_FLOAT		= 0x02 << 20;
	public final static int TYPE_STRING		= 0x03 << 20;

	// CONNECTION
	public final static int RELATION_MASK	= 0x03;
	public final static int TRANSITION		= 0x00;
	public final static int TASK_CALL		= 0x01;
	public final static int ACTION_CALL		= 0x02;

	// STATE
	public final static int STATE_MASK		= 0x03;
	public final static int NORMAL			= 0x00;
	public final static int INITIAL			= 0x01;
	public final static int TARGET			= 0x02;
}
