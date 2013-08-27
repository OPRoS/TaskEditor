/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kr.re.etri.tpl.taskmodel.impl;

import kr.re.etri.tpl.taskmodel.ShapeElement;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Shape Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ShapeElementImpl#getX <em>X</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ShapeElementImpl#getY <em>Y</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ShapeElementImpl#getWidth <em>Width</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ShapeElementImpl#getHeight <em>Height</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ShapeElementImpl#isCollapsed <em>Collapsed</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ShapeElementImpl#getX2 <em>X2</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ShapeElementImpl#getY2 <em>Y2</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ShapeElementImpl#getWidth2 <em>Width2</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.ShapeElementImpl#getHeight2 <em>Height2</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ShapeElementImpl extends ItemElementImpl implements ShapeElement {
	/**
	 * The default value of the '{@link #getX() <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getX()
	 * @generated
	 * @ordered
	 */
	protected static final int X_EDEFAULT = 5;

	/**
	 * The cached value of the '{@link #getX() <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getX()
	 * @generated
	 * @ordered
	 */
	protected int x = X_EDEFAULT;

	/**
	 * The default value of the '{@link #getY() <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getY()
	 * @generated
	 * @ordered
	 */
	protected static final int Y_EDEFAULT = 5;

	/**
	 * The cached value of the '{@link #getY() <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getY()
	 * @generated
	 * @ordered
	 */
	protected int y = Y_EDEFAULT;

	/**
	 * The default value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected static final int WIDTH_EDEFAULT = 100;

	/**
	 * The cached value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected int width = WIDTH_EDEFAULT;

	/**
	 * The default value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
	protected static final int HEIGHT_EDEFAULT = 70;

	/**
	 * The cached value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
	protected int height = HEIGHT_EDEFAULT;

	/**
	 * The default value of the '{@link #isCollapsed() <em>Collapsed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCollapsed()
	 * @generated
	 * @ordered
	 */
	protected static final boolean COLLAPSED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCollapsed() <em>Collapsed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCollapsed()
	 * @generated
	 * @ordered
	 */
	protected boolean collapsed = COLLAPSED_EDEFAULT;

	/**
	 * The default value of the '{@link #getX2() <em>X2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getX2()
	 * @generated
	 * @ordered
	 */
	protected static final int X2_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getX2() <em>X2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getX2()
	 * @generated
	 * @ordered
	 */
	protected int x2 = X2_EDEFAULT;

	/**
	 * The default value of the '{@link #getY2() <em>Y2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getY2()
	 * @generated
	 * @ordered
	 */
	protected static final int Y2_EDEFAULT = 5;

	/**
	 * The cached value of the '{@link #getY2() <em>Y2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getY2()
	 * @generated
	 * @ordered
	 */
	protected int y2 = Y2_EDEFAULT;

	/**
	 * The default value of the '{@link #getWidth2() <em>Width2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth2()
	 * @generated
	 * @ordered
	 */
	protected static final int WIDTH2_EDEFAULT = 100;

	/**
	 * The cached value of the '{@link #getWidth2() <em>Width2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth2()
	 * @generated
	 * @ordered
	 */
	protected int width2 = WIDTH2_EDEFAULT;

	/**
	 * The default value of the '{@link #getHeight2() <em>Height2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeight2()
	 * @generated
	 * @ordered
	 */
	protected static final int HEIGHT2_EDEFAULT = 70;

	/**
	 * The cached value of the '{@link #getHeight2() <em>Height2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeight2()
	 * @generated
	 * @ordered
	 */
	protected int height2 = HEIGHT2_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ShapeElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TaskModelPackage.Literals.SHAPE_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getX() {
		return x;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setX(int newX) {
		int oldX = x;
		x = newX;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.SHAPE_ELEMENT__X, oldX, x));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getY() {
		return y;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setY(int newY) {
		int oldY = y;
		y = newY;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.SHAPE_ELEMENT__Y, oldY, y));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWidth(int newWidth) {
		int oldWidth = width;
		width = newWidth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.SHAPE_ELEMENT__WIDTH, oldWidth, width));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHeight(int newHeight) {
		int oldHeight = height;
		height = newHeight;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.SHAPE_ELEMENT__HEIGHT, oldHeight, height));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isCollapsed() {
		return collapsed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCollapsed(boolean newCollapsed) {
		boolean oldCollapsed = collapsed;
		collapsed = newCollapsed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.SHAPE_ELEMENT__COLLAPSED, oldCollapsed, collapsed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getX2() {
		return x2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setX2(int newX2) {
		int oldX2 = x2;
		x2 = newX2;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.SHAPE_ELEMENT__X2, oldX2, x2));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getY2() {
		return y2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setY2(int newY2) {
		int oldY2 = y2;
		y2 = newY2;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.SHAPE_ELEMENT__Y2, oldY2, y2));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getWidth2() {
		return width2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWidth2(int newWidth2) {
		int oldWidth2 = width2;
		width2 = newWidth2;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.SHAPE_ELEMENT__WIDTH2, oldWidth2, width2));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getHeight2() {
		return height2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHeight2(int newHeight2) {
		int oldHeight2 = height2;
		height2 = newHeight2;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.SHAPE_ELEMENT__HEIGHT2, oldHeight2, height2));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TaskModelPackage.SHAPE_ELEMENT__X:
				return getX();
			case TaskModelPackage.SHAPE_ELEMENT__Y:
				return getY();
			case TaskModelPackage.SHAPE_ELEMENT__WIDTH:
				return getWidth();
			case TaskModelPackage.SHAPE_ELEMENT__HEIGHT:
				return getHeight();
			case TaskModelPackage.SHAPE_ELEMENT__COLLAPSED:
				return isCollapsed();
			case TaskModelPackage.SHAPE_ELEMENT__X2:
				return getX2();
			case TaskModelPackage.SHAPE_ELEMENT__Y2:
				return getY2();
			case TaskModelPackage.SHAPE_ELEMENT__WIDTH2:
				return getWidth2();
			case TaskModelPackage.SHAPE_ELEMENT__HEIGHT2:
				return getHeight2();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TaskModelPackage.SHAPE_ELEMENT__X:
				setX((Integer)newValue);
				return;
			case TaskModelPackage.SHAPE_ELEMENT__Y:
				setY((Integer)newValue);
				return;
			case TaskModelPackage.SHAPE_ELEMENT__WIDTH:
				setWidth((Integer)newValue);
				return;
			case TaskModelPackage.SHAPE_ELEMENT__HEIGHT:
				setHeight((Integer)newValue);
				return;
			case TaskModelPackage.SHAPE_ELEMENT__COLLAPSED:
				setCollapsed((Boolean)newValue);
				return;
			case TaskModelPackage.SHAPE_ELEMENT__X2:
				setX2((Integer)newValue);
				return;
			case TaskModelPackage.SHAPE_ELEMENT__Y2:
				setY2((Integer)newValue);
				return;
			case TaskModelPackage.SHAPE_ELEMENT__WIDTH2:
				setWidth2((Integer)newValue);
				return;
			case TaskModelPackage.SHAPE_ELEMENT__HEIGHT2:
				setHeight2((Integer)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TaskModelPackage.SHAPE_ELEMENT__X:
				setX(X_EDEFAULT);
				return;
			case TaskModelPackage.SHAPE_ELEMENT__Y:
				setY(Y_EDEFAULT);
				return;
			case TaskModelPackage.SHAPE_ELEMENT__WIDTH:
				setWidth(WIDTH_EDEFAULT);
				return;
			case TaskModelPackage.SHAPE_ELEMENT__HEIGHT:
				setHeight(HEIGHT_EDEFAULT);
				return;
			case TaskModelPackage.SHAPE_ELEMENT__COLLAPSED:
				setCollapsed(COLLAPSED_EDEFAULT);
				return;
			case TaskModelPackage.SHAPE_ELEMENT__X2:
				setX2(X2_EDEFAULT);
				return;
			case TaskModelPackage.SHAPE_ELEMENT__Y2:
				setY2(Y2_EDEFAULT);
				return;
			case TaskModelPackage.SHAPE_ELEMENT__WIDTH2:
				setWidth2(WIDTH2_EDEFAULT);
				return;
			case TaskModelPackage.SHAPE_ELEMENT__HEIGHT2:
				setHeight2(HEIGHT2_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TaskModelPackage.SHAPE_ELEMENT__X:
				return x != X_EDEFAULT;
			case TaskModelPackage.SHAPE_ELEMENT__Y:
				return y != Y_EDEFAULT;
			case TaskModelPackage.SHAPE_ELEMENT__WIDTH:
				return width != WIDTH_EDEFAULT;
			case TaskModelPackage.SHAPE_ELEMENT__HEIGHT:
				return height != HEIGHT_EDEFAULT;
			case TaskModelPackage.SHAPE_ELEMENT__COLLAPSED:
				return collapsed != COLLAPSED_EDEFAULT;
			case TaskModelPackage.SHAPE_ELEMENT__X2:
				return x2 != X2_EDEFAULT;
			case TaskModelPackage.SHAPE_ELEMENT__Y2:
				return y2 != Y2_EDEFAULT;
			case TaskModelPackage.SHAPE_ELEMENT__WIDTH2:
				return width2 != WIDTH2_EDEFAULT;
			case TaskModelPackage.SHAPE_ELEMENT__HEIGHT2:
				return height2 != HEIGHT2_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (x: ");
		result.append(x);
		result.append(", y: ");
		result.append(y);
		result.append(", width: ");
		result.append(width);
		result.append(", height: ");
		result.append(height);
		result.append(", collapsed: ");
		result.append(collapsed);
		result.append(", x2: ");
		result.append(x2);
		result.append(", y2: ");
		result.append(y2);
		result.append(", width2: ");
		result.append(width2);
		result.append(", height2: ");
		result.append(height2);
		result.append(')');
		return result.toString();
	}

} //ShapeElementImpl
