package bandwidth_interface_snmp.monitor;

public enum InterfacePoint {

	EXEMPLE_OID_INTERFACE_POINT(".1.3.6.1.2.1.1.1.0"),
	Item_2("Item de configuração 2"),
	Item_3("Item de configuração 3"),
	Item_4("Item de configuração 4");
	
	String text;
	
	InterfacePoint(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return this.getText();
		
	}
	
//	public List<String> literalValues() {
//		return new ArrayList<String>(EXEMPLE_OID_INTERFACE_POINT.text));
//	}
}

