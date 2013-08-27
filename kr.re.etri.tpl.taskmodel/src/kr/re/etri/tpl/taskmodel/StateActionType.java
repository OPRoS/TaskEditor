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
 * A representation of the literals of the enumeration '<em><b>State Action Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getStateActionType()
 * @model
 * @generated
 */
public enum StateActionType implements Enumerator {
	/**
	 * The '<em><b>Entry</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ENTRY_VALUE
	 * @generated
	 * @ordered
	 */
	ENTRY(0, "Entry", "entry"),

	/**
	 * The '<em><b>Stay</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STAY_VALUE
	 * @generated
	 * @ordered
	 */
	STAY(1, "Stay", "stay"),

	/**
	 * The '<em><b>Exit</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXIT_VALUE
	 * @generated
	 * @ordered
	 */
	EXIT(2, "Exit", "exit");

	/**
	 * The '<em><b>Entry</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Entry</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ENTRY
	 * @model name="Entry" literal="entry"
	 * @generated
	 * @ordered
	 */
	public static final int ENTRY_VALUE = 0;

	/**
	 * The '<em><b>Stay</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Stay</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #STAY
	 * @model name="Stay" literal="stay"
	 * @generated
	 * @ordered
	 */
	public static final int STAY_VALUE = 1;

	/**
	 * The '<em><b>Exit</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Exit</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #EXIT
	 * @model name="Exit" literal="exit"
	 * @generated
	 * @ordered
	 */
	public static final int EXIT_VALUE = 2;

	/**
	 * An array of all the '<em><b>State Action Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final StateActionType[] VALUES_ARRAY =
		new StateActionType[] {
			ENTRY,
			STAY,
			EXIT,
		};

	/**
	 * A public read-only list of all the '<em><b>State Action Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<StateActionType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>State Action Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static StateActionType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			StateActionType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>State Action Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static StateActionType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			StateActionType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>State Action Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static StateActionType get(int value) {
		switch (value) {
			case ENTRY_VALUE: return ENTRY;
			case STAY_VALUE: return STAY;
			case EXIT_VALUE: return EXIT;
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
	private StateActionType(int value, String name, String literal) {
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
	
} //StateActionType
