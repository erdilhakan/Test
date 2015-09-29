package tr.org.mmo.asansor.util;


public class TCKimlikKontrol {
public static boolean tcKimlikDogruMu(String tcKimlikNo){
	//TC Kimlik numarası temin edilemeyen başvurularda başvuru girebilmek için koyduk. //27.05.2014
	if (tcKimlikNo.equals("99999999999")) return true;
	
    if (tcKimlikNo == null || tcKimlikNo.equals(""))

               return false;

    if (tcKimlikNo.length() != 11 || tcKimlikNo.equals("00000000000"))

               return false;

    char[] d = tcKimlikNo.toCharArray();

    for (int i = 0; i < d.length; i++)

               if (!Character.isDigit(d[i]))

                           return false;



    String sonuc;

    int digit1, digit2, oddSum = 0, evenSum = 0;

    int[] digits = new int[9];

    String p2 = tcKimlikNo.substring(0, 9);

    

    for (int i = 0; i < 9; i++) {

               digits[i] = Integer.parseInt(tcKimlikNo.substring(i, i + 1));

    }

    for (int i = 0; i < 9; i += 2) {

               oddSum += digits[i];

    }

    for (int i = 1; i < 9; i += 2) {

               evenSum += digits[i];

    }

    int temp = (oddSum * 3) + evenSum;

    digit1 = (temp % 100) % 10;

    if (digit1 != 0)

               digit1 = 10 - digit1;

    String d1 = String.valueOf(digit1);

    int t = oddSum;

    oddSum = digit1 + evenSum;

    evenSum = t;

    temp = (oddSum * 3) + evenSum;

    digit2 = (temp % 100) % 10;

    if (digit2 != 0)

               digit2 = 10 - digit2;

    String d2 = String.valueOf(digit2);

    sonuc = p2 + d1 + d2;

    if (sonuc.equals(tcKimlikNo)) {

               return true;

    } else {

               return false;

    }


}
}

