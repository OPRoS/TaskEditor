package kr.re.etri.tpl.diagram.properties.sources;

import java.util.List;

import kr.re.etri.tpl.diagram.util.PropertyContainer;
import kr.re.etri.tpl.taskmodel.ItemElement;
import kr.re.etri.tpl.taskmodel.LinkedElement;
import kr.re.etri.tpl.taskmodel.ReferElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

public class ReferElementPropertySource extends LinkedElementPropertySource {

	public ReferElementPropertySource(LinkedElement model) {
		super(model);
	}

	private ReferElement getShape() {
		return (ReferElement)getModel();
	}
	
	@Override
	public void createPropertyDescriptors(List descriptors) {
		super.createPropertyDescriptors(descriptors);
	}

	@Override
	public boolean isPropertySet(Object id) {
		if (super.isPropertySet(id))
			return true;
		
		Integer propId = (Integer)id;
		
		if (TaskModelPackage.REFER_ELEMENT__REAL_MODEL == propId.intValue())
			return true;
		
		return false;
	}

	@Override
	public boolean isPropertyResettable(Object id) {
		Integer propId = (Integer)id;
		
		if (TaskModelPackage.REFER_ELEMENT__REAL_MODEL == propId.intValue())
			return true;

		return super.isPropertyResettable(id);
	}

	@Override
	public void resetPropertyValue(Object id) {
		super.resetPropertyValue(id);
	}

	@Override
	public Object getPropertyValue(Object id) {
		Integer propId = (Integer)id;
		ReferElement item = getShape();
		
		if (TaskModelPackage.REFER_ELEMENT__REAL_MODEL == propId.intValue())
			return item.getRealModel();
		
		return super.getPropertyValue(id);
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		Integer propId = (Integer)id;
		ReferElement item = getShape();
		
		if (TaskModelPackage.REFER_ELEMENT__REAL_MODEL == propId.intValue())
			item.setRealModel((ItemElement)value);
		
		super.setPropertyValue(id, value);
	}

	
}
