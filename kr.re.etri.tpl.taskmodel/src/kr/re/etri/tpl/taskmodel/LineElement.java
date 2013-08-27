/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kr.re.etri.tpl.taskmodel;

import org.eclipse.draw2d.geometry.Point;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Line Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.LineElement#getLineStyle <em>Line Style</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.LineElement#getSourceEndPoint <em>Source End Point</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.LineElement#getTargetEndPoint <em>Target End Point</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.LineElement#getBendPoints <em>Bend Points</em>}</li>
 * </ul>
 * </p>
 *
 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getLineElement()
 * @model
 * @generated
 */
public interface LineElement extends ItemElement {
	/**
	 * Returns the value of the '<em><b>Line Style</b></em>' attribute.
	 * The default value is <code>"1"</code>.
	 * The literals are from the enumeration {@link kr.re.etri.tpl.taskmodel.LineStyle}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Line Style</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Line Style</em>' attribute.
	 * @see kr.re.etri.tpl.taskmodel.LineStyle
	 * @see #setLineStyle(LineStyle)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getLineElement_LineStyle()
	 * @model default="1" required="true" derived="true"
	 * @generated
	 */
	LineStyle getLineStyle();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.LineElement#getLineStyle <em>Line Style</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Line Style</em>' attribute.
	 * @see kr.re.etri.tpl.taskmodel.LineStyle
	 * @see #getLineStyle()
	 * @generated
	 */
	void setLineStyle(LineStyle value);

	/**
	 * Returns the value of the '<em><b>Source End Point</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * The literals are from the enumeration {@link kr.re.etri.tpl.taskmodel.LineEndPoint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source End Point</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source End Point</em>' attribute.
	 * @see kr.re.etri.tpl.taskmodel.LineEndPoint
	 * @see #setSourceEndPoint(LineEndPoint)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getLineElement_SourceEndPoint()
	 * @model default="" required="true" derived="true"
	 * @generated
	 */
	LineEndPoint getSourceEndPoint();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.LineElement#getSourceEndPoint <em>Source End Point</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source End Point</em>' attribute.
	 * @see kr.re.etri.tpl.taskmodel.LineEndPoint
	 * @see #getSourceEndPoint()
	 * @generated
	 */
	void setSourceEndPoint(LineEndPoint value);

	/**
	 * Returns the value of the '<em><b>Target End Point</b></em>' attribute.
	 * The literals are from the enumeration {@link kr.re.etri.tpl.taskmodel.LineEndPoint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target End Point</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target End Point</em>' attribute.
	 * @see kr.re.etri.tpl.taskmodel.LineEndPoint
	 * @see #setTargetEndPoint(LineEndPoint)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getLineElement_TargetEndPoint()
	 * @model required="true" derived="true"
	 * @generated
	 */
	LineEndPoint getTargetEndPoint();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.LineElement#getTargetEndPoint <em>Target End Point</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target End Point</em>' attribute.
	 * @see kr.re.etri.tpl.taskmodel.LineEndPoint
	 * @see #getTargetEndPoint()
	 * @generated
	 */
	void setTargetEndPoint(LineEndPoint value);

	/**
	 * Returns the value of the '<em><b>Bend Points</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.draw2d.geometry.Point}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bend Points</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bend Points</em>' attribute list.
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getLineElement_BendPoints()
	 * @model default="" dataType="kr.re.etri.tpl.taskmodel.Point" derived="true"
	 * @generated
	 */
	EList<Point> getBendPoints();

} // LineElement
