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
 * A representation of the literals of the enumeration '<em><b>Floating Literal</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getFloatingLiteral()
 * @model
 * @generated
 */
public enum FloatingLiteral implements Enumerator {
	/**
	 * The '<em><b>State Time</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STATE_TIME_VALUE
	 * @generated
	 * @ordered
	 */
	STATE_TIME(0, "stateTime", "stateTime"),

	/**
	 * The '<em><b>Task Time</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TASK_TIME_VALUE
	 * @generated
	 * @ordered
	 */
	TASK_TIME(1, "taskTime", "taskTime"),

	/**
	 * The '<em><b>Real Float Literal</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #REAL_FLOAT_LITERAL_VALUE
	 * @generated
	 * @ordered
	 */
	REAL_FLOAT_LITERAL(2, "RealFloatLiteral", "RealFloatLiteral");

	/**
	 * The '<em><b>State Time</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>State Time</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #STATE_TIME
	 * @model name="stateTime"
	 * @generated
	 * @ordered
	 */
	public static final int STATE_TIME_VALUE = 0;

	/**
	 * The '<em><b>Task Time</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Task Time</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TASK_TIME
	 * @model name="taskTime"
	 * @generated
	 * @ordered
	 */
	public static final int TASK_TIME_VALUE = 1;

	/**
	 * The '<em><b>Real Float Literal</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Real Float Literal</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #REAL_FLOAT_LITERAL
	 * @model name="RealFloatLiteral"
	 * @generated
	 * @ordered
	 */
	public static final int REAL_FLOAT_LITERAL_VALUE = 2;

	/**
	 * An array of all the '<em><b>Floating Literal</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final FloatingLiteral[] VALUES_ARRAY =
		new FloatingLiteral[] {
			STATE_TIME,
			TASK_TIME,
			REAL_FLOAT_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Floating Literal</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<FloatingLiteral> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Floating Literal</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FloatingLiteral get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			FloatingLiteral result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Floating Literal</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FloatingLiteral getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			FloatingLiteral result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Floating Literal</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FloatingLiteral get(int value) {
		switch (value) {
			case STATE_TIME_VALUE: return STATE_TIME;
			case TASK_TIME_VALUE: return TASK_TIME;
			case REAL_FLOAT_LITERAL_VALUE: return REAL_FLOAT_LITERAL;
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
	private FloatingLiteral(int value, String name, String literal) {
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
	
} //FloatingLiteral
