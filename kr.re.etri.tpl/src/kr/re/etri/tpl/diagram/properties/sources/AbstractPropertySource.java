
package kr.re.etri.tpl.diagram.properties.sources;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource2;

/**
 * 모델의 Properties 를 제공한다. 이 클래스는 각 모델의 PropertySource 의
 * 상위 클래스로 Subclass 는 createPropertyDescriptors(List) 를 구현하여야 한다.
 * DiagramEditor 또는 View 에서 선택된 모델에 대하여 Properties View 에
 * 모델의 Propertis 를 제공한다.
 * 
 * @see org.eclipse.ui.views.properties.IPropertySource
 * @see org.eclipse.ui.views.properties.IPropertySource2
 * 
 * @author sfline
 *
 */
public abstract class AbstractPropertySource implements IPropertySource2 {
	/** 모델 */
	private Object model;
	/** 모델에 대한 Property 들의 Descriptor */
	private IPropertyDescriptor[] descriptors;
	
	/**
	 * 주어진 model에 대한 PropertySoruce 를 생성한다.
	 * @param model 모델
	 */
	public AbstractPropertySource(Object model) {
		this.model = model;
	}
	
	/**
	 * 모델에 대한 Property 들에 대한 Descriptor 를 생성하여 제공한다.
	 * subclass 는 이 메소드를 구현하여야 한다.
	 * @param descriptors PropertyDescriptor 목록
	 */
	public abstract void createPropertyDescriptors(List descriptors);
	
	/**
	 * 모델을 반환한다.
	 * @return 모델
	 */
	public Object getModel() {
		return model;
	}	

	/**
	 * 모델에 대한 Property 값 변경 기능을 재공하는 인스턴스를 제공한다.
	 * 
	 * @see org.eclipse.ui.views.properties.IPropertySource2#getEditableValue()
	 */
	public Object getEditableValue() {
		return this;
	}

	/**
	 * 모델에 대한 Property 들에 대한 Descriptor 를 제공한다.
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		List list = new ArrayList();
		createPropertyDescriptors(list);
		descriptors = new IPropertyDescriptor[list.size()];
		list.toArray(descriptors);
		return descriptors;
	}
	
	/**
	 * 주어진 id 에 해당하는 Property 값을 set 이 가능한지 여부를 반환한다.
	 * @param Property Id
	 */
	public boolean isPropertySet(Object id) {
		return false;
	}
	
	/**
	 * 주어진 id 에 해당하는 Property 가 reset 이 가능한지 여부를 반환한다.
	 * @param Property Id
	 */
	public boolean isPropertyResettable(Object id) {
		return false;
	}
	
	/**
	 * 주어진 id 에 해당하는 Property 값을 reset 한다.
	 * @param Property Id
	 */
	public void resetPropertyValue(Object id) {
	}

}
