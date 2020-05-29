package serial;

public class CheckSumTest {
	public static void main(String[] args) {
	String data = "G11";
	char[] data_arr = data.toCharArray();
	int sum = 0;
	for(int i = 0;i<data_arr.length; i++) {
		System.out.println(data_arr[i]);
		sum = sum + data_arr[i];
		}
	System.out.println(sum);
	sum = (sum & 0xff); // &연산
	System.out.println(sum);
	System.out.println(Integer.toBinaryString(10));//1010
	System.out.println(Integer.toHexString(10));//a
	System.out.println(Integer.toHexString(sum));//a9
	}
}