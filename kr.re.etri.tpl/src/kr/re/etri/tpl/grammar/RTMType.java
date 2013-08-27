package kr.re.etri.tpl.grammar;

public enum RTMType {
	VOID(0, "void"),
	BOOL(1, "bool"),
	CHAR(2, "char"),
	BYTE(3, "byte"),
	SHORT(4,"short"),
	INT(5, "int"),
	LONG(6, "long"),
	FLOAT(7, "float"),
	DOUBLE(8, "double"),
	STRING(9, "string");

	private static final RTMType VALUES[] = new RTMType[]{
			VOID, BOOL, CHAR, BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, STRING
	};
	
	private final int value;
	private final String name;
	private RTMType(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public String getName() {
		return this.name;
	}

	public String toString() {
		return this.name;
	}
	
	public static RTMType get(String name) {
		for(int idx = 0; idx < VALUES.length;  idx += 1) {
			if(VALUES[idx].name.equals(name)) {
				return VALUES[idx];
			}
		}
		
		return null;
	}
	
	public static RTMType get(int value) {
		for(int idx = 0; idx < VALUES.length;  idx += 1) {
			if(VALUES[idx].value == value) {
				return VALUES[idx];
			}
		}
		
		return null;
	}
	
	public static String[] getInputTypes() {
		String [] names = new String[] {
				BOOL.name, CHAR.name, BYTE.name,
				SHORT.name, INT.name, LONG.name,
				FLOAT.name, DOUBLE.name, STRING.name
		};

		return names;
	}
	
	public static String[] getReturnTypes() {
		String [] names = new String[] {
				VOID.name, BOOL.name, CHAR.name,
				BYTE.name, SHORT.name, INT.name,
				LONG.name, FLOAT.name, DOUBLE.name, STRING.name
		};

		return names;
	}
}
