package bandwidth_interface_snmp.monitor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Monitor {
	
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
    
//    public void synchronize(final int withTime) {
//    	new Runnable() {
//			public void run() {
//				try {
//					TimeUnit.SECONDS.sleep(withTime);
//					traffic(Double.valueOf(SNMPClient.access(InterfacePoint.IF_IN_OCTETS_BEGIN.B)),
//							Double.valueOf(SNMPClient.access(InterfacePoint.IF_IN_OCTETS_END)),
//							Double.valueOf(SNMPClient.access(InterfacePoint.IF_OUT_OCTETS_BEGIN)),
//							Double.valueOf(SNMPClient.access(InterfacePoint.IF_OUT_OCTETS_END)),
//							Double.valueOf(SNMPClient.access(InterfacePoint.IF_SPEED_BEGIN)),
//							withTime,
//							DataUnit.MEGABYTE);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				} catch (NumberFormatException e) {
//					e.printStackTrace();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}.run();
//    }

}
