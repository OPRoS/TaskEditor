/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kr.re.etri.tpl.taskmodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Shape Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ShapeElement#getX <em>X</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ShapeElement#getY <em>Y</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ShapeElement#getWidth <em>Width</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ShapeElement#getHeight <em>Height</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ShapeElement#isCollapsed <em>Collapsed</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ShapeElement#getX2 <em>X2</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ShapeElement#getY2 <em>Y2</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ShapeElement#getWidth2 <em>Width2</em>}</li>
 *   <li>{@link kr.re.etri.tpl.taskmodel.ShapeElement#getHeight2 <em>Height2</em>}</li>
 * </ul>
 * </p>
 *
 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getShapeElement()
 * @model
 * @generated
 */
public interface ShapeElement extends ItemElement {
	/**
	 * Returns the value of the '<em><b>X</b></em>' attribute.
	 * The default value is <code>"5"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>X</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>X</em>' attribute.
	 * @see #setX(int)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getShapeElement_X()
	 * @model default="5" required="true" derived="true"
	 * @generated
	 */
	int getX();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ShapeElement#getX <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>X</em>' attribute.
	 * @see #getX()
	 * @generated
	 */
	void setX(int value);

	/**
	 * Returns the value of the '<em><b>Y</b></em>' attribute.
	 * The default value is <code>"5"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Y</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Y</em>' attribute.
	 * @see #setY(int)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getShapeElement_Y()
	 * @model default="5" required="true" derived="true"
	 * @generated
	 */
	int getY();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ShapeElement#getY <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Y</em>' attribute.
	 * @see #getY()
	 * @generated
	 */
	void setY(int value);

	/**
	 * Returns the value of the '<em><b>Width</b></em>' attribute.
	 * The default value is <code>"100"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Width</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Width</em>' attribute.
	 * @see #setWidth(int)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getShapeElement_Width()
	 * @model default="100" required="true" derived="true"
	 * @generated
	 */
	int getWidth();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ShapeElement#getWidth <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Width</em>' attribute.
	 * @see #getWidth()
	 * @generated
	 */
	void setWidth(int value);

	/**
	 * Returns the value of the '<em><b>Height</b></em>' attribute.
	 * The default value is <code>"70"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Height</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Height</em>' attribute.
	 * @see #setHeight(int)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getShapeElement_Height()
	 * @model default="70" required="true" derived="true"
	 * @generated
	 */
	int getHeight();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ShapeElement#getHeight <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Height</em>' attribute.
	 * @see #getHeight()
	 * @generated
	 */
	void setHeight(int value);

	/**
	 * Returns the value of the '<em><b>Collapsed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Collapsed</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Collapsed</em>' attribute.
	 * @see #setCollapsed(boolean)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getShapeElement_Collapsed()
	 * @model required="true" derived="true"
	 * @generated
	 */
	boolean isCollapsed();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ShapeElement#isCollapsed <em>Collapsed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Collapsed</em>' attribute.
	 * @see #isCollapsed()
	 * @generated
	 */
	void setCollapsed(boolean value);

	/**
	 * Returns the value of the '<em><b>X2</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>X2</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>X2</em>' attribute.
	 * @see #setX2(int)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getShapeElement_X2()
	 * @model default="0" required="true"
	 * @generated
	 */
	int getX2();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ShapeElement#getX2 <em>X2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>X2</em>' attribute.
	 * @see #getX2()
	 * @generated
	 */
	void setX2(int value);

	/**
	 * Returns the value of the '<em><b>Y2</b></em>' attribute.
	 * The default value is <code>"5"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Y2</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Y2</em>' attribute.
	 * @see #setY2(int)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getShapeElement_Y2()
	 * @model default="5" required="true"
	 * @generated
	 */
	int getY2();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ShapeElement#getY2 <em>Y2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Y2</em>' attribute.
	 * @see #getY2()
	 * @generated
	 */
	void setY2(int value);

	/**
	 * Returns the value of the '<em><b>Width2</b></em>' attribute.
	 * The default value is <code>"100"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Width2</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Width2</em>' attribute.
	 * @see #setWidth2(int)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getShapeElement_Width2()
	 * @model default="100" required="true" derived="true"
	 * @generated
	 */
	int getWidth2();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ShapeElement#getWidth2 <em>Width2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Width2</em>' attribute.
	 * @see #getWidth2()
	 * @generated
	 */
	void setWidth2(int value);

	/**
	 * Returns the value of the '<em><b>Height2</b></em>' attribute.
	 * The default value is <code>"70"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Height2</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Height2</em>' attribute.
	 * @see #setHeight2(int)
	 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getShapeElement_Height2()
	 * @model default="70" required="true" derived="true"
	 * @generated
	 */
	int getHeight2();

	/**
	 * Sets the value of the '{@link kr.re.etri.tpl.taskmodel.ShapeElement#getHeight2 <em>Height2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Height2</em>' attribute.
	 * @see #getHeight2()
	 * @generated
	 */
	void setHeight2(int value);

} // ShapeElement
