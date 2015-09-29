package tr.org.mmo.asansor.test;

public class JavaTest {

	public static void main(String[] args) {
		int diff=625;
		  
		int diffYear = diff / 365;       
		int diffMonth = (diff - (diffYear*365)) / 30;    
		int diffDay = (diff - ((diffYear*365) + (diffMonth*30))); 
		System.out.println("Time in Year: " + diffYear + " Years.");     
		System.out.println("Time in Month: " + diffMonth + " Months.");      
		System.out.println("Time in Day: " + diffDay + " Days.");
		String a="Erişim -Engelli kullanımına uygunluk- (EN 81-70);Kasıtlı tahribatlara karşı tedbirler (EN 81-71);Yangın durumunda çalışmaya yönelik tedbirler (EN 81-73);İtfaiyeci asansörü (EN 81-72)";
		String[] s=a.split(";");
		String kapsam_="";
		if (s!=null && s.length>0){
			for (String s1:s){
				String ss=s1.substring(s1.indexOf("(")+1,s1.indexOf(")"));
				kapsam_+=kapsam_.trim().length()>0?";"+ss:ss;
			}
		}
		System.out.println(kapsam_);
	}

}
