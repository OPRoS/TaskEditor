package kr.re.etri.tpl.diagram.util;

public class PropertyContainer
{
	public boolean isUndo;
	public final static int SET = 1;
	public final static int UNSET = 2;
	public final static int ADD = 3;
	public final static int REMOVE = 4;
	public final static int ADDALL = 5;
	public final static int REMOVEALL = 6;
	public final static int MOVE = 7;
	public final static int REPLACEALL = 11;

	public int containerType;
	public int propertyFeatureId;
	public Object newValue;
	public Object oldValue;

	/**
	 * REMOVEALL을 위한 생성함수
	 * @param type
	 * @param featureId
	 */
	public PropertyContainer(int type, int featureId)
	{
		containerType =  type;
		propertyFeatureId = featureId;
		this.newValue = null;
		this.oldValue = null;
		this.isUndo = false;
	}

	/**
	 * ADD, ADDLL를 위한 생성함수
	 * @param type
	 * @param featureId
	 * @param newValue
	 */
	public PropertyContainer(int type, int featureId, Object newValue)
	{
		containerType =  type;
		propertyFeatureId = featureId;
		this.newValue = newValue;
		this.oldValue = null;
		this.isUndo = false;
	}

	/**
	 * SET, REMOVE을 위한 생성함수
	 * @param type
	 * @param featureId
	 * @param newValue
	 * @param oldValue
	 */
	public PropertyContainer(int type, int featureId, Object newValue, Object oldValue)
	{
		containerType =  type;
		propertyFeatureId = featureId;
		this.newValue = newValue;
		this.oldValue = oldValue;
		this.isUndo = false;
	}
	
	public int getType() {
		return this.containerType;
	}
	
	public int getFeatureId() {
		return this.propertyFeatureId;
	}
	
	public Object getNewValue() {
		return this.newValue;
	}
	
	public Object getOldValue() {
		return this.oldValue;
	}
	
	public boolean isUndo() {
		return this.isUndo;
	}
	public void setUndo(boolean isUndo) {
		this.isUndo = isUndo;
	}
}
