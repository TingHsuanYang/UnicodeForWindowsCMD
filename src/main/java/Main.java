import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Main {

	public static void showHexCode(String s) {
		String[] sa = s.split("");
		try {
			for (String ss : sa) {
				Console.println(ss);
				byte[] b = ss.getBytes("UTF-8");
				for (byte bb : b) {
					System.out.printf("%x ", bb);
				}
				System.out.println();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static void goJavaWay() {
		Scanner sc = new Scanner(System.in);
		System.out.println("請輸入你的名字：");
		String s = sc.nextLine();
		System.out.println("Scanner & System.out.println(): Hi~" + s);
		sc.close();
		showHexCode(s);
	}

	public static void goSystemApiWay() {
		System.out.println("請輸入你的名字：");
		String s = Console.readln();
		Console.println("ReadConsoleW() & WriteConsoleW(): Hi~" + s);
		showHexCode(s);
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Console.println("Try input some unicode words like \"あらがき ゆい\"、\"陶喆\"、\"游錫堃\"");
		System.out.println("choose JavaWay or SystemApiWay (1 or 2)：");
		String s = sc.nextLine();
		if ("1".equals(s)) {
			goJavaWay();
		} else if ("2".equals(s)) {
			goSystemApiWay();
		}
		sc.close();
	}

}
