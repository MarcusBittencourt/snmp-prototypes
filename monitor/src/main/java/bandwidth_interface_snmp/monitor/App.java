package bandwidth_interface_snmp.monitor;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( traffic(143252, 143251111, 21433253, 21433753, 1000, 300, DataUnit.KILOBYTE));
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
//    	double traffic = ((ifInOctets + ifOutOctets) / deltaTime) * ( 8 * dataUnit.value);    	
    	double traffic = ((ifInOctets + ifOutOctets) / deltaTime) * ( DataUnit.BYTE.value * 100);    	
    	return traffic;
    }
   
}
