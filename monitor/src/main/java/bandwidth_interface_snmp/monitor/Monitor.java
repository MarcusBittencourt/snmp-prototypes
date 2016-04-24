package bandwidth_interface_snmp.monitor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Monitor {

	private int interfaceIndex = -1;
	private SNMPClient client;
	
	public Monitor() throws IOException {
		client = new SNMPClient("127.0.0.1", "161", "abcbolinhas", 1500, 2);
	}
	
	/*public static void main(String[] args) {
		System.out.println(traffic(84919907, 
								   84919907, 
								   ifOutOctetsBegin, 
								   ifOutOctetsEnd, 
								   speed, 
								   time, 
								   DataUnit.BYTE.value));
	}*/

    public static double traffic(
    		double ifInOctetsBegin, 
    		double ifInOctetsEnd, 
    		double ifOutOctetsBegin, 
    		double ifOutOctetsEnd, 
    		double speed, 
    		int time,
    		DataUnit dataUnit) {
    	double ifInOctets = ifInOctetsEnd - ifInOctetsBegin;
    	double ifOutOctets = ifOutOctetsEnd - ifOutOctetsBegin;
    	double deltaTime = time * speed;
    	double traffic = ((ifInOctets + ifOutOctets) / deltaTime) * ( 8 * 100);    	
    	return traffic;
    }
    
    public void synchronize(final int time, int forInterface) {
//    	if(thread != null) thread.stop()
    	Runnable thread = new Runnable() {
			public void run() {
				try {
					String ifSpeedTargetOID = InterfacePoint.IF_SPEED.SNMPbranch + forInterface; 
					Double ifSpeed = Double.valueOf(client.access(ifSpeedTargetOID));
					
					String ifInOctetsBeginTargetOID = InterfacePoint.IF_IN_OCTETS.SNMPbranch + forInterface;
					Double ifInOctetsBegin = Double.valueOf(client.access(ifInOctetsBeginTargetOID));
					
					String ifOutOctetsBeginTargetOID = InterfacePoint.IF_OUT_OCTETS.SNMPbranch + forInterface;
					Double ifOutOctetsBegin = Double.valueOf(client.access(ifOutOctetsBeginTargetOID));
					
					TimeUnit.SECONDS.sleep(time);
					
					String ifInOctetsEndTargetOID = InterfacePoint.IF_IN_OCTETS.SNMPbranch + forInterface;
					Double ifInOctetsEnd = Double.valueOf(client.access(ifInOctetsEndTargetOID));
					
					String ifOutOctetsEndTargetOID = InterfacePoint.IF_OUT_OCTETS.SNMPbranch + forInterface;
					Double ifOutOctetsEnd = Double.valueOf(client.access(ifOutOctetsEndTargetOID));
					
					double traffic = Monitor.traffic(ifInOctetsBegin, ifInOctetsEnd, ifOutOctetsBegin, ifOutOctetsEnd, ifSpeed, time, DataUnit.BYTE);
					System.out.println(traffic);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
    	thread.run();
    }

}
