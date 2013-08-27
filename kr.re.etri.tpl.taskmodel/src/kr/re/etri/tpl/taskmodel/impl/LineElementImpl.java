/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kr.re.etri.tpl.taskmodel.impl;

import java.util.Collection;

import kr.re.etri.tpl.taskmodel.LineElement;
import kr.re.etri.tpl.taskmodel.LineEndPoint;
import kr.re.etri.tpl.taskmodel.LineStyle;
import kr.re.etri.tpl.taskmodel.TaskModelPackage;

import org.eclipse.draw2d.geometry.Point;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Line Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.LineElementImpl#getLineStyle <em>Line Style</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.LineElementImpl#getSourceEndPoint <em>Source End Point</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.LineElementImpl#getTargetEndPoint <em>Target End Point</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.impl.LineElementImpl#getBendPoints <em>Bend Points</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LineElementImpl extends ItemElementImpl implements LineElement {
	/**
	 * The default value of the '{@link #getLineStyle() <em>Line Style</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLineStyle()
	 * @generated
	 * @ordered
	 */
	protected static final LineStyle LINE_STYLE_EDEFAULT = LineStyle.LINE_SOLID;

	/**
	 * The cached value of the '{@link #getLineStyle() <em>Line Style</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLineStyle()
	 * @generated
	 * @ordered
	 */
	protected LineStyle lineStyle = LINE_STYLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSourceEndPoint() <em>Source End Point</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceEndPoint()
	 * @generated
	 * @ordered
	 */
	protected static final LineEndPoint SOURCE_END_POINT_EDEFAULT = LineEndPoint.NONE;

	/**
	 * The cached value of the '{@link #getSourceEndPoint() <em>Source End Point</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceEndPoint()
	 * @generated
	 * @ordered
	 */
	protected LineEndPoint sourceEndPoint = SOURCE_END_POINT_EDEFAULT;

	/**
	 * The default value of the '{@link #getTargetEndPoint() <em>Target End Point</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetEndPoint()
	 * @generated
	 * @ordered
	 */
	protected static final LineEndPoint TARGET_END_POINT_EDEFAULT = LineEndPoint.NONE;

	/**
	 * The cached value of the '{@link #getTargetEndPoint() <em>Target End Point</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetEndPoint()
	 * @generated
	 * @ordered
	 */
	protected LineEndPoint targetEndPoint = TARGET_END_POINT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getBendPoints() <em>Bend Points</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBendPoints()
	 * @generated
	 * @ordered
	 */
	protected EList<Point> bendPoints;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LineElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TaskModelPackage.Literals.LINE_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LineStyle getLineStyle() {
		return lineStyle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLineStyle(LineStyle newLineStyle) {
		LineStyle oldLineStyle = lineStyle;
		lineStyle = newLineStyle == null ? LINE_STYLE_EDEFAULT : newLineStyle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.LINE_ELEMENT__LINE_STYLE, oldLineStyle, lineStyle));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LineEndPoint getSourceEndPoint() {
		return sourceEndPoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceEndPoint(LineEndPoint newSourceEndPoint) {
		LineEndPoint oldSourceEndPoint = sourceEndPoint;
		sourceEndPoint = newSourceEndPoint == null ? SOURCE_END_POINT_EDEFAULT : newSourceEndPoint;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.LINE_ELEMENT__SOURCE_END_POINT, oldSourceEndPoint, sourceEndPoint));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LineEndPoint getTargetEndPoint() {
		return targetEndPoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTargetEndPoint(LineEndPoint newTargetEndPoint) {
		LineEndPoint oldTargetEndPoint = targetEndPoint;
		targetEndPoint = newTargetEndPoint == null ? TARGET_END_POINT_EDEFAULT : newTargetEndPoint;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaskModelPackage.LINE_ELEMENT__TARGET_END_POINT, oldTargetEndPoint, targetEndPoint));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Point> getBendPoints() {
		if (bendPoints == null) {
			bendPoints = new EDataTypeUniqueEList<Point>(Point.class, this, TaskModelPackage.LINE_ELEMENT__BEND_POINTS);
		}
		return bendPoints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TaskModelPackage.LINE_ELEMENT__LINE_STYLE:
				return getLineStyle();
			case TaskModelPackage.LINE_ELEMENT__SOURCE_END_POINT:
				return getSourceEndPoint();
			case TaskModelPackage.LINE_ELEMENT__TARGET_END_POINT:
				return getTargetEndPoint();
			case TaskModelPackage.LINE_ELEMENT__BEND_POINTS:
				return getBendPoints();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TaskModelPackage.LINE_ELEMENT__LINE_STYLE:
				setLineStyle((LineStyle)newValue);
				return;
			case TaskModelPackage.LINE_ELEMENT__SOURCE_END_POINT:
				setSourceEndPoint((LineEndPoint)newValue);
				return;
			case TaskModelPackage.LINE_ELEMENT__TARGET_END_POINT:
				setTargetEndPoint((LineEndPoint)newValue);
				return;
			case TaskModelPackage.LINE_ELEMENT__BEND_POINTS:
				getBendPoints().clear();
				getBendPoints().addAll((Collection<? extends Point>)newValue);
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
			case TaskModelPackage.LINE_ELEMENT__LINE_STYLE:
				setLineStyle(LINE_STYLE_EDEFAULT);
				return;
			case TaskModelPackage.LINE_ELEMENT__SOURCE_END_POINT:
				setSourceEndPoint(SOURCE_END_POINT_EDEFAULT);
				return;
			case TaskModelPackage.LINE_ELEMENT__TARGET_END_POINT:
				setTargetEndPoint(TARGET_END_POINT_EDEFAULT);
				return;
			case TaskModelPackage.LINE_ELEMENT__BEND_POINTS:
				getBendPoints().clear();
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
			case TaskModelPackage.LINE_ELEMENT__LINE_STYLE:
				return lineStyle != LINE_STYLE_EDEFAULT;
			case TaskModelPackage.LINE_ELEMENT__SOURCE_END_POINT:
				return sourceEndPoint != SOURCE_END_POINT_EDEFAULT;
			case TaskModelPackage.LINE_ELEMENT__TARGET_END_POINT:
				return targetEndPoint != TARGET_END_POINT_EDEFAULT;
			case TaskModelPackage.LINE_ELEMENT__BEND_POINTS:
				return bendPoints != null && !bendPoints.isEmpty();
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
		result.append(" (lineStyle: ");
		result.append(lineStyle);
		result.append(", sourceEndPoint: ");
		result.append(sourceEndPoint);
		result.append(", targetEndPoint: ");
		result.append(targetEndPoint);
		result.append(", bendPoints: ");
		result.append(bendPoints);
		result.append(')');
		return result.toString();
	}

} //LineElementImpl
