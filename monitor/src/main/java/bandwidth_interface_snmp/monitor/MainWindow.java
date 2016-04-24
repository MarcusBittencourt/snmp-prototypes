package bandwidth_interface_snmp.monitor;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.xml.bind.DatatypeConverter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import java.awt.Label;
import java.awt.Panel;
import javax.swing.JComboBox;
import java.awt.TextArea;
import javax.swing.JPanel;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.awt.event.ItemEvent;
import java.awt.event.TextListener;
import java.awt.event.TextEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow {

	private JFrame frame;
	private JTextField txt_ip;
	private JTextField txt_porta;
	private JTextField txt_communit;
	private JTextField txt_versao;
	private JTextField txt_timeout;
	private JTextField txt_retransmissao;
	private JTextField txt_intervalo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1086, 482);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txt_ip = new JTextField();
		txt_ip.setBounds(10, 48, 86, 20);
		frame.getContentPane().add(txt_ip);
		txt_ip.setColumns(10);
		
		Label label = new Label("IP:");
		label.setBounds(10, 20, 62, 22);
		frame.getContentPane().add(label);
		
		Label label_1 = new Label("Porta:");
		label_1.setBounds(113, 20, 62, 22);
		frame.getContentPane().add(label_1);
		
		txt_porta = new JTextField();
		txt_porta.setColumns(10);
		txt_porta.setBounds(106, 48, 86, 20);
		frame.getContentPane().add(txt_porta);
		
		Label label_2 = new Label("Communit:");
		label_2.setBounds(205, 20, 62, 22);
		frame.getContentPane().add(label_2);
		
		txt_communit = new JTextField();
		txt_communit.setColumns(10);
		txt_communit.setBounds(202, 48, 86, 20);
		frame.getContentPane().add(txt_communit);
		
		Label label_3 = new Label("Versão:");
		label_3.setBounds(300, 20, 62, 22);
		frame.getContentPane().add(label_3);
		
		txt_versao = new JTextField();
		txt_versao.setColumns(10);
		txt_versao.setBounds(298, 48, 86, 20);
		frame.getContentPane().add(txt_versao);
		
		Label label_4 = new Label("Timeout:");
		label_4.setBounds(398, 20, 62, 22);
		frame.getContentPane().add(label_4);
		
		txt_timeout = new JTextField();
		txt_timeout.setColumns(10);
		txt_timeout.setBounds(394, 48, 86, 20);
		frame.getContentPane().add(txt_timeout);
		
		txt_retransmissao = new JTextField();
		txt_retransmissao.setColumns(10);
		txt_retransmissao.setBounds(496, 48, 86, 20);
		frame.getContentPane().add(txt_retransmissao);
		
		Label label_5 = new Label("Retransmissão:");
		label_5.setBounds(496, 20, 86, 22);
		frame.getContentPane().add(label_5);
		
		Label label_6 = new Label("Interface:");
		label_6.setBounds(10, 218, 62, 22);
		frame.getContentPane().add(label_6);
		
		JComboBox cb_inteface = new JComboBox();
		
		
		List<String> interfacesNames = new ArrayList<>();
		
		cb_inteface.setBounds(10, 246, 470, 20);
		frame.getContentPane().add(cb_inteface);
		
		txt_intervalo = new JTextField();
		txt_intervalo.setColumns(10);
		txt_intervalo.setBounds(496, 246, 86, 20);
		frame.getContentPane().add(txt_intervalo);
		
		Label label_7 = new Label("Intervalo:");
		label_7.setBounds(496, 218, 62, 22);
		frame.getContentPane().add(label_7);
		
		TextArea txtarea_resumoEquipamento = new TextArea();
		txtarea_resumoEquipamento.setBounds(10, 110, 572, 102);
		frame.getContentPane().add(txtarea_resumoEquipamento);
		
		Label label_8 = new Label("Resumo do equipamento:");
		label_8.setBounds(10, 82, 165, 22);
		frame.getContentPane().add(label_8);
		
		TextArea txtarea_resumoInterface = new TextArea();
		txtarea_resumoInterface.setBounds(10, 313, 572, 102);
		frame.getContentPane().add(txtarea_resumoInterface);
		
		Label label_9 = new Label("Resumo da interface:");
		label_9.setBounds(10, 287, 165, 22);
		frame.getContentPane().add(label_9);
		
		 XYDataset ds = createDataset();
         JFreeChart chart = ChartFactory.createXYLineChart("Taxa de utilização","x", "y", ds, PlotOrientation.VERTICAL, true, true,false);
         ChartPanel cp = new ChartPanel(chart);
         cp.setBounds(614, 20, 453, 395);
         frame.getContentPane().add(cp);

         try {
        	 SNMPClient client = new SNMPClient("127.0.0.1", "161", "abcbolinhas", 1500, 2);
        	 
        	 String systemDescription = "Description: " + client.access(InterfacePoint.SYSTEM_DESCRPTION.SNMPbranch) + "\n";
        	 String systemUptime= "Uptime: " + client.access(InterfacePoint.SYSTEM_UPTIME.SNMPbranch) + "\n";
        	 String systemContact = "Contact: " + client.access(InterfacePoint.SYSTEM_CONTACT.SNMPbranch) + "\n";
        	 
        	 txtarea_resumoEquipamento.setText(systemContact + systemDescription + systemUptime);
        	 
        	 int numeroInterfaces = Integer.valueOf(client.access(InterfacePoint.IF_NUMBER.SNMPbranch));
        	 List<String> interfaces = new ArrayList<>();
        	 for (int indice = 1; indice <= numeroInterfaces; indice++) {
        		 String nomeInterface = client.access(InterfacePoint.IF_DESCRIPTION.SNMPbranch + indice);
        		 byte[] textoHexadecimal = DatatypeConverter.parseHexBinary(nomeInterface.replace(":", ""));
        		 interfaces.add(new String(textoHexadecimal, "UTF-8"));
        	 }
        	 
        	 DefaultComboBoxModel model = new DefaultComboBoxModel(interfaces.toArray());
        	 cb_inteface.setModel(model);
         
        	 cb_inteface.addActionListener(new ActionListener() {
        		 public void actionPerformed(ActionEvent e) {
        			 try {
        			    int indiceSelecionado = cb_inteface.getSelectedIndex() + 1;
        			    Double ifSpeed = Double.valueOf(client.access(InterfacePoint.IF_SPEED.SNMPbranch + indiceSelecionado));
        			 	Double ifInOctetsBegin = Double.valueOf(client.access(InterfacePoint.IF_IN_OCTETS.SNMPbranch + indiceSelecionado));
						Double ifOutOctetsBegin = Double.valueOf(client.access(InterfacePoint.IF_OUT_OCTETS.SNMPbranch + indiceSelecionado));
						int time = Integer.valueOf(txt_intervalo.getText());
						TimeUnit.SECONDS.sleep(time);				
						Double ifInOctetsEnd = Double.valueOf(client.access(InterfacePoint.IF_IN_OCTETS.SNMPbranch + indiceSelecionado));
						Double ifOutOctetsEnd = Double.valueOf(client.access(InterfacePoint.IF_OUT_OCTETS.SNMPbranch + indiceSelecionado));
						double traffic = Monitor.traffic(ifInOctetsBegin, ifInOctetsEnd, ifOutOctetsBegin, ifOutOctetsEnd, ifSpeed, time, DataUnit.BYTE);
						System.out.println(traffic);
        			 } catch (Exception exception) {
        				 exception.printStackTrace();
					}
        		 }
        	 });
        	 
         } catch (IOException e) {
        	 e.printStackTrace();
         }
         
//		cb_inteface.addItemListener(new ItemListener() {
//			public void itemStateChanged(ItemEvent arg0) {
//				
//			}
//		});
	}

	private static XYDataset createDataset() {
        DefaultXYDataset ds = new DefaultXYDataset();
        double[][] data = { {0.1}, {1} };
        ds.addSeries("series1", data);
        return ds;
    }
}
