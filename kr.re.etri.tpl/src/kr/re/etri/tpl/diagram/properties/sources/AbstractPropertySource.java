
package kr.re.etri.tpl.diagram.properties.sources;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource2;

/**
 * ���� Properties �� �����Ѵ�. �� Ŭ������ �� ���� PropertySource ��
 * ���� Ŭ������ Subclass �� createPropertyDescriptors(List) �� �����Ͽ��� �Ѵ�.
 * DiagramEditor �Ǵ� View ���� ���õ� �𵨿� ���Ͽ� Properties View ��
 * ���� Propertis �� �����Ѵ�.
 * 
 * @see org.eclipse.ui.views.properties.IPropertySource
 * @see org.eclipse.ui.views.properties.IPropertySource2
 * 
 * @author sfline
 *
 */
public abstract class AbstractPropertySource implements IPropertySource2 {
	/** �� */
	private Object model;
	/** �𵨿� ���� Property ���� Descriptor */
	private IPropertyDescriptor[] descriptors;
	
	/**
	 * �־��� model�� ���� PropertySoruce �� �����Ѵ�.
	 * @param model ��
	 */
	public AbstractPropertySource(Object model) {
		this.model = model;
	}
	
	/**
	 * �𵨿� ���� Property �鿡 ���� Descriptor �� �����Ͽ� �����Ѵ�.
	 * subclass �� �� �޼ҵ带 �����Ͽ��� �Ѵ�.
	 * @param descriptors PropertyDescriptor ���
	 */
	public abstract void createPropertyDescriptors(List descriptors);
	
	/**
	 * ���� ��ȯ�Ѵ�.
	 * @return ��
	 */
	public Object getModel() {
		return model;
	}	

	/**
	 * �𵨿� ���� Property �� ���� ����� ����ϴ� �ν��Ͻ��� �����Ѵ�.
	 * 
	 * @see org.eclipse.ui.views.properties.IPropertySource2#getEditableValue()
	 */
	public Object getEditableValue() {
		return this;
	}

	/**
	 * �𵨿� ���� Property �鿡 ���� Descriptor �� �����Ѵ�.
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		List list = new ArrayList();
		createPropertyDescriptors(list);
		descriptors = new IPropertyDescriptor[list.size()];
		list.toArray(descriptors);
		return descriptors;
	}
	
	/**
	 * �־��� id �� �ش��ϴ� Property ���� set �� �������� ���θ� ��ȯ�Ѵ�.
	 * @param Property Id
	 */
	public boolean isPropertySet(Object id) {
		return false;
	}
	
	/**
	 * �־��� id �� �ش��ϴ� Property �� reset �� �������� ���θ� ��ȯ�Ѵ�.
	 * @param Property Id
	 */
	public boolean isPropertyResettable(Object id) {
		return false;
	}
	
	/**
	 * �־��� id �� �ش��ϴ� Property ���� reset �Ѵ�.
	 * @param Property Id
	 */
	public void resetPropertyValue(Object id) {
	}

}
