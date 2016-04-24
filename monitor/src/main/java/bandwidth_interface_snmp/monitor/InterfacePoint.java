package bandwidth_interface_snmp.monitor;

public enum InterfacePoint {

	IF_IN_OCTETS("IfInOctets", "%s"),
	IF_OUT_OCTETS("IfOutOctets", "%s"),
	IF_SPEED("IfSpeed","1.3.6.1.2.1.2.2.1.5.%s"),
	IF_NUMBER("IfNumber", "1.3.6.1.2.1.2.1.0"),
	SYSTEM_DESCRPTION("SysDesc", "1.3.6.1.2.1.1.1.0"),
	SYSTEM_UPTIME("Uptime", "1.3.6.1.2.1.1.3.0"),
	SYSTEM_CONTACT("SysContac" , "1.3.6.1.2.1.1.4.0"), 
	IF_DESCRIPTION("IfDescr", "1.3.6.1.2.1.2.2.1.2."),
	;
	
	String description;
	String SNMPbranch;
	
	InterfacePoint(String description, String SNMPbranch) {
		this.description = description;
		this.SNMPbranch = SNMPbranch;
	}
	
	public String getText() {
		return description;
	}

	public String getSNMPbranch() {
		return SNMPbranch;
	}

	@Override
	public String toString() {
		return this.getText();
		
	}
	
}

