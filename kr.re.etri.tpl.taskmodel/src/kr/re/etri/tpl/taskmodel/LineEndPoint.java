/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kr.re.etri.tpl.taskmodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Line End Point</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getLineEndPoint()
 * @model
 * @generated
 */
public enum LineEndPoint implements Enumerator {
	/**
	 * The '<em><b>NONE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NONE_VALUE
	 * @generated
	 * @ordered
	 */
	NONE(0, "NONE", "NONE"),

	/**
	 * The '<em><b>OPENED ARROW</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OPENED_ARROW_VALUE
	 * @generated
	 * @ordered
	 */
	OPENED_ARROW(1, "OPENED_ARROW", "OPENED_ARROW"),

	/**
	 * The '<em><b>OPENED TRIANGLE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OPENED_TRIANGLE_VALUE
	 * @generated
	 * @ordered
	 */
	OPENED_TRIANGLE(2, "OPENED_TRIANGLE", ""),

	/**
	 * The '<em><b>OPENED SQUARE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OPENED_SQUARE_VALUE
	 * @generated
	 * @ordered
	 */
	OPENED_SQUARE(3, "OPENED_SQUARE", "OPENED_SQUARE"),

	/**
	 * The '<em><b>OPENED CIRCLE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OPENED_CIRCLE_VALUE
	 * @generated
	 * @ordered
	 */
	OPENED_CIRCLE(4, "OPENED_CIRCLE", "OPENED_CIRCLE"),

	/**
	 * The '<em><b>CLOSED TRIANGLE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CLOSED_TRIANGLE_VALUE
	 * @generated
	 * @ordered
	 */
	CLOSED_TRIANGLE(5, "CLOSED_TRIANGLE", "CLOSED_TRIANGLE"),

	/**
	 * The '<em><b>CLOSED SQUARE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CLOSED_SQUARE_VALUE
	 * @generated
	 * @ordered
	 */
	CLOSED_SQUARE(6, "CLOSED_SQUARE", "CLOSED_SQUARE"),

	/**
	 * The '<em><b>CLOSED CIRCLE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CLOSED_CIRCLE_VALUE
	 * @generated
	 * @ordered
	 */
	CLOSED_CIRCLE(7, "CLOSED_CIRCLE", "CLOSED_CIRCLE");

	/**
	 * The '<em><b>NONE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>NONE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NONE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int NONE_VALUE = 0;

	/**
	 * The '<em><b>OPENED ARROW</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>OPENED ARROW</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #OPENED_ARROW
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int OPENED_ARROW_VALUE = 1;

	/**
	 * The '<em><b>OPENED TRIANGLE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>OPENED TRIANGLE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #OPENED_TRIANGLE
	 * @model literal=""
	 * @generated
	 * @ordered
	 */
	public static final int OPENED_TRIANGLE_VALUE = 2;

	/**
	 * The '<em><b>OPENED SQUARE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>OPENED SQUARE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #OPENED_SQUARE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int OPENED_SQUARE_VALUE = 3;

	/**
	 * The '<em><b>OPENED CIRCLE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>OPENED CIRCLE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #OPENED_CIRCLE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int OPENED_CIRCLE_VALUE = 4;

	/**
	 * The '<em><b>CLOSED TRIANGLE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CLOSED TRIANGLE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CLOSED_TRIANGLE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CLOSED_TRIANGLE_VALUE = 5;

	/**
	 * The '<em><b>CLOSED SQUARE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CLOSED SQUARE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CLOSED_SQUARE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CLOSED_SQUARE_VALUE = 6;

	/**
	 * The '<em><b>CLOSED CIRCLE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CLOSED CIRCLE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CLOSED_CIRCLE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CLOSED_CIRCLE_VALUE = 7;

	/**
	 * An array of all the '<em><b>Line End Point</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final LineEndPoint[] VALUES_ARRAY =
		new LineEndPoint[] {
			NONE,
			OPENED_ARROW,
			OPENED_TRIANGLE,
			OPENED_SQUARE,
			OPENED_CIRCLE,
			CLOSED_TRIANGLE,
			CLOSED_SQUARE,
			CLOSED_CIRCLE,
		};

	/**
	 * A public read-only list of all the '<em><b>Line End Point</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<LineEndPoint> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Line End Point</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LineEndPoint get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			LineEndPoint result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Line End Point</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LineEndPoint getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			LineEndPoint result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Line End Point</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LineEndPoint get(int value) {
		switch (value) {
			case NONE_VALUE: return NONE;
			case OPENED_ARROW_VALUE: return OPENED_ARROW;
			case OPENED_TRIANGLE_VALUE: return OPENED_TRIANGLE;
			case OPENED_SQUARE_VALUE: return OPENED_SQUARE;
			case OPENED_CIRCLE_VALUE: return OPENED_CIRCLE;
			case CLOSED_TRIANGLE_VALUE: return CLOSED_TRIANGLE;
			case CLOSED_SQUARE_VALUE: return CLOSED_SQUARE;
			case CLOSED_CIRCLE_VALUE: return CLOSED_CIRCLE;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private LineEndPoint(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //LineEndPoint
