package bandwidth_interface_snmp.monitor;

public enum DataUnit {

	BIT(1L),
	BYTE(8L),
	KILOBYTE(1000L),
	MEGABYTE(1000000000L),
	GIGABYTE(1000000000000L);
	
	final long value;

	private DataUnit(long value) {
		this.value = value;
	}

}
