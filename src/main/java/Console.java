
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCallLibrary;

/**
 * For unicode output on windows platform
 * 
 * @author Evan
 *
 */
public class Console {
	private static Kernel32 INSTANCE = null;

	public interface Kernel32 extends StdCallLibrary {

		public Pointer GetStdHandle(int nStdHandle);

		public boolean WriteConsoleW(Pointer hConsoleOutput, char[] lpBuffer, int nNumberOfCharsToWrite, IntByReference lpNumberOfCharsWritten, Pointer lpReserved);

		public boolean ReadConsoleW(Pointer hConsoleOutput, char[] lpBuffer, int nNumberOfCharsToRead, IntByReference lpNumberOfCharsRead, Pointer pInputControl);
	}

	static {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.startsWith("win")) {
			INSTANCE = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class);
		}
	}

	public static void println(String message) {
		if (INSTANCE != null) {
			Pointer handle = INSTANCE.GetStdHandle(-11);
			char[] buffer = message.toCharArray();
			IntByReference lpNumberOfCharsWritten = new IntByReference();
			if (INSTANCE.WriteConsoleW(handle, buffer, buffer.length, lpNumberOfCharsWritten, null)) {
				System.out.println();
			} else {
				System.out.println(message);
			}
		}
	}

	public static String readln() {
		Pointer handle = INSTANCE.GetStdHandle(-10);
		StringBuilder line = new StringBuilder();
		char[] lpBuffer = new char[128];
		IntByReference lpNumberOfCharsRead = new IntByReference();
		while (true) {
			if (!INSTANCE.ReadConsoleW(handle, lpBuffer, lpBuffer.length, lpNumberOfCharsRead, null)) {
				throw new IllegalStateException();
			}
			int len = lpNumberOfCharsRead.getValue();
			if (len == 0) {
				return null;
			}
			line.append(lpBuffer, 0, len);
			if (lpBuffer[len - 1] == '\n') {
				break;
			}
		}
		return line.toString();
	}
}