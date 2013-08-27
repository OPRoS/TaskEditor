package kr.re.etri.tpl.script.emulator.dialogs;

public enum EType {
	VALUE(0, "value"),
	UNIFURM(1, "uniform"),
	GAUSSIAN(2, "gaussian"),
	ENUM(3, "enum"),
	;
	
	private final static EType VALUES[] = new EType[] {
		VALUE, UNIFURM, GAUSSIAN, ENUM,
	};
	
	private final int value;
	private final String name;
	private EType(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}
	
	public static EType get(int value) {
		for (EType etype : VALUES) {
			if (etype.value == value) {
				return etype;
			}
		}
		return null;
	}
	
	public static EType get(String name) {
		for (EType etype : VALUES) {
			if (etype.name.equals(name)) {
				return etype;
			}
		}
		return null;
	}
	
	public static String[] getETypes() {
		String[] names = new String[] {
				VALUE.name,
				UNIFURM.name,
				GAUSSIAN.name,
				ENUM.name,
		};
		return names;
	}
}
