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
 * A representation of the literals of the enumeration '<em><b>Join Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see kr.re.etri.tpl.taskmodel.TaskModelPackage#getJoinType()
 * @model
 * @generated
 */
public enum JoinType implements Enumerator {
	/**
	 * The '<em><b>Andjoin</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ANDJOIN_VALUE
	 * @generated
	 * @ordered
	 */
	ANDJOIN(0, "andjoin", "andjoin"),

	/**
	 * The '<em><b>Orjoin</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ORJOIN_VALUE
	 * @generated
	 * @ordered
	 */
	ORJOIN(1, "orjoin", "orjoin");

	/**
	 * The '<em><b>Andjoin</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Andjoin</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ANDJOIN
	 * @model name="andjoin"
	 * @generated
	 * @ordered
	 */
	public static final int ANDJOIN_VALUE = 0;

	/**
	 * The '<em><b>Orjoin</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Orjoin</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ORJOIN
	 * @model name="orjoin"
	 * @generated
	 * @ordered
	 */
	public static final int ORJOIN_VALUE = 1;

	/**
	 * An array of all the '<em><b>Join Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final JoinType[] VALUES_ARRAY =
		new JoinType[] {
			ANDJOIN,
			ORJOIN,
		};

	/**
	 * A public read-only list of all the '<em><b>Join Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<JoinType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Join Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static JoinType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			JoinType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Join Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static JoinType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			JoinType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Join Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static JoinType get(int value) {
		switch (value) {
			case ANDJOIN_VALUE: return ANDJOIN;
			case ORJOIN_VALUE: return ORJOIN;
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
	private JoinType(int value, String name, String literal) {
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
	
	// KJH 20101203, to string
	public static String[] getJoinTypes() {
		return new String[] {ANDJOIN.name, ORJOIN.name};
	};
} //JoinType
