package bandwidth_interface_snmp.monitor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.DatatypeConverter;

public class Monitor {

	private int interfaceIndex = -1;
	private SNMPClient client;
	
	public Monitor(String ip, String port, String communit, String timeout, String retransmition) throws IOException {
		client = new SNMPClient(ip, port, communit, Integer.valueOf(timeout), Integer.valueOf(retransmition));
		client.start();
	}

    public static double traffic(
    		double ifInOctetsBegin, 
    		double ifInOctetsEnd, 
    		double ifOutOctetsBegin, 
    		double ifOutOctetsEnd, 
    		double speed, 
    		double time,
    		DataUnit dataUnit) {
    	 double ifInOctets = ifInOctetsEnd - ifInOctetsBegin;
    	 double ifOutOctets = ifOutOctetsEnd - ifOutOctetsBegin;
    	 double deltaTime = time * speed;
    	 double traffic = (new Double(ifInOctets + ifOutOctets).intValue() / deltaTime) * (8 * 100);    	
    	return traffic;
    }
    
    /**
     * @param time
     * @param forInterface
     * @return
     */
    public double synchronize(final int time, int forInterface) {
		try {
			String ifSpeedTargetOID = InterfacePoint.IF_SPEED.SNMPbranch + forInterface; 
			double ifSpeed = (double) Integer.valueOf(client.access(ifSpeedTargetOID));
			
			String ifInOctetsBeginTargetOID = InterfacePoint.IF_IN_OCTETS.SNMPbranch + forInterface;
			double ifInOctetsBegin = (double) Integer.valueOf(client.access(ifInOctetsBeginTargetOID));
			
			String ifOutOctetsBeginTargetOID = InterfacePoint.IF_OUT_OCTETS.SNMPbranch + forInterface;
			double ifOutOctetsBegin = (double) Integer.valueOf(client.access(ifOutOctetsBeginTargetOID));
			
			TimeUnit.SECONDS.sleep(time);
			
			String ifInOctetsEndTargetOID = InterfacePoint.IF_IN_OCTETS.SNMPbranch + forInterface;
			double ifInOctetsEnd = (double) Integer.valueOf(client.access(ifInOctetsEndTargetOID));
			
			String ifOutOctetsEndTargetOID = InterfacePoint.IF_OUT_OCTETS.SNMPbranch + forInterface;
			double ifOutOctetsEnd = (double) Integer.valueOf(client.access(ifOutOctetsEndTargetOID));
			
			double traffic = Monitor.traffic(ifInOctetsBegin, ifInOctetsEnd, ifOutOctetsBegin, ifOutOctetsEnd, ifSpeed, time, DataUnit.BYTE);
			return traffic * 100.00;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1.0;
    }
}
