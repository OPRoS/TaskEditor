package kr.re.etri.tpl.grammar;



public class Notify {

	public enum Type {
		SET(1, "Set"),
		UNSET(2, "Unset"),
		ADD(3, "Add"),
		REMOVE(4, "Remove"),
		ADD_MANY(5, "AddMany"),
		REMOVE_MANY(6, "RemoveMany");

		private int m_value;
		private String m_name;

		private Type(int value, String name) {
			m_value = value;
			m_name = name;
		}
		
		public int getValue() {
			return m_value;
		}
		
		public String getName() {
			return m_name;
		}
		
		private static final Type[] VALUES = new Type[] {
			SET, UNSET, ADD, REMOVE, ADD_MANY, REMOVE_MANY
		};

		public static Type get(String name) {
			for (int i = 0; i < VALUES.length; ++i) {
				if (VALUES[i].m_name.equals(name)) {
					return VALUES[i];
				}
			}
			return null;
		}
	}
	
	private Object m_notifier;
	
	private Type m_type;
	
	private Object m_newValue;
	
	private Object m_oldValue;
	
	private Object m_data;
	
	public Notify(Object notifier, Type type, Object newValue, Object oldValue) {
		m_notifier = notifier;
		m_type = type;
		m_newValue = newValue;
		m_oldValue = oldValue;
	}
	
	public Type getType() {
		return m_type;
	}
	
	public Object getNotifier() {
		return m_notifier;
	}
	
	public Object getNewValue() {
		return m_newValue;
	}
	
	public Object getOldValue() {
		return m_oldValue;
	}
	
	public void setData(Object data) {
		m_data = data;
	}

	public Object getData() {
		return m_data;
	}
}
