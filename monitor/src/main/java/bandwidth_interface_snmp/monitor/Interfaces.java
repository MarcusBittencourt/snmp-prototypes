package bandwidth_interface_snmp.monitor;

public enum Interfaces {

	Item_1("Item de configuração 1"),
	Item_2("Item de configuração 2"),
	Item_3("Item de configuração 3"),
	Item_4("Item de configuração 4");
	
	private String text;
	
	Interfaces(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return this.getText();
	}
}

