import java.io.*;
import java.util.*;

public final class traceroute_analysis {
	public static void main(String args[]) throws IOException { 
		

		FileInputStream fs = new FileInputStream(args[0]);
		BufferedReader br = new BufferedReader(new InputStreamReader(fs));

		String trace_txt;
		String ip_addr  = "ip";
		String tcp_key  = "tcp";
		String icmp_key  = "icmp";
		String dot_sep  = ".";

		Vector<String>  vs1 = new Vector<String>(10);
		Vector<String>  vs2 = new Vector<String>(10);
		
		while ((trace_txt = br.readLine()) != null)   {

			if ( trace_txt.toLowerCase().indexOf(ip_addr.toLowerCase()) != -1 ) {
				if ( trace_txt.toLowerCase().indexOf(dot_sep.toLowerCase()) != -1 ) {
					if ( trace_txt.toLowerCase().indexOf(tcp_key.toLowerCase()) != -1 ) {
						vs1.add(trace_txt);
					}
				} 
			} 

			if( trace_txt.toLowerCase().indexOf(icmp_key.toLowerCase()) != -1 ) {
				vs2.add(trace_txt);
				trace_txt = br.readLine();
				vs2.add(trace_txt);
				trace_txt = br.readLine();
				vs2.add(trace_txt);
			
			}
			
		}

		br.close();
		
		String[][] darr1= new String[vs1.size()][3]; 
		String[][] darr2= new String[vs2.size()][3];

		for( int s=0; s<vs1.size(); s++ ){

			String str = vs1.get(s);
			int index = str.indexOf(' ');
			String ttl = str.substring(str.indexOf("l") + 2, str.indexOf("i")-2);
			String id = str.substring(str.indexOf("d") + 2, str.indexOf("f")-3);
			String time = str.substring(0, index);
			darr1[s][0] = time;
			darr1[s][1] = id;
			darr1[s][2] = ttl;
			
		}
		
		int counter = 0;
		for( int h=0; h<vs2.size(); h++ ){

			String str = vs2.get(h);
			int index = str.indexOf(' ');
			String time = str.substring(0, index);
			
			darr2[counter][0] = time;
			h++;
			
			if(h == vs2.size())
				break;
			str = vs2.get(h);
			index = str.indexOf(' ');
			String IP = str.substring(4, str.indexOf(">"));
			
			darr2[counter][2] = IP;
			h++;

			if(h == vs2.size())
				break;
			str = vs2.get(h);
			String id = str.substring(str.indexOf("d") + 2, str.indexOf("f")-3);
			darr2[counter][1] = id;
			counter++;
			
		}

		System.out.println("The Computations Results are:");

		for( int r=0; r<vs1.size(); r++ ){

			int id1 = Integer.parseInt(darr1[r][1]);

			for( int e=0; e<counter ;e++ ){
				int id2 =  Integer.parseInt(darr2[e][1]);

				if(id1 == id2){

					if (r%3 == 0){
						System.out.println ();
						System.out.println ("TTL " + darr1[r][2]);
						System.out.println("Router IP: " + darr2[e][2]);
					}

					double t1 = Double.parseDouble(darr1[r][0]);
					double t2 = Double.parseDouble(darr2[e][0]);

					double fin_time = (t2 - t1)*1000;

					System.out.printf("%.3f", fin_time);
					System.out.println(" ms");
				}
			}
			
		}
	}
}
