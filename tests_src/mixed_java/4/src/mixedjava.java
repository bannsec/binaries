class MixedJava {
	
	static {
		System.loadLibrary("MixedJava");
	}
	
	//
	// Native function declarations
	//
	
	native static int sum_array(int[] arr);
	native static int get_array_length_native(int[] arr);
	
	// GetArrayElements & ReleaseArrayElements
	native static boolean boolean_arr(boolean[] arr, int idx);
	native static byte byte_arr(byte[] arr, int idx);
	native static char char_arr(char[] arr, int idx);
	native static short short_arr(short[] arr, int idx);
	native static int int_arr(int[] arr, int idx);
	native static long long_arr(long[] arr, int idx);
	
	// GetArrayElements
	native static int arr_symbolic(int[] arr, int idx);
	
	// ReleaseArrayElements
	native static int modify_int_arr(int[] arr);
	
	// GetArrayRegion
	native static int int_arr_region(int[] arr, int idx, int length);
	
	// SetArrayRegion
	native static void modify_int_arr_region(int[] arr, int start_idx, int length);
	
	// NewArray
	native static int[] new_int_array();
	
	//
	// JNI Function Tests
	//
	
	static void test_jni_newarray() {
		int[] arr = new_int_array();
		int a, b, c, d, e;
		a = arr[0];
		b = arr[1];
		c = arr[2];
		d = arr[3];
		e = arr[4];
		break_();
	}
	
	static void test_jni_setarrayregion1(){
		int[] arr = {0, 1, 2, 3, 4};
		modify_int_arr_region(arr, 1, 3);
		int a, b, c, d, e;
		a = arr[0];
		b = arr[1];
		c = arr[2];
		d = arr[3];
		e = arr[4];
		break_();
	}
	
	static void test_jni_setarrayregion2() throws Exception {
		int start_idx = System.in.read();
		int[] arr = {0, 1, 2, 3, 4};
		modify_int_arr_region(arr, start_idx, 3);
		int a, b, c, d, e;
		a = arr[0];
		b = arr[1];
		c = arr[2];
		d = arr[3];
		e = arr[4];
		break_();
	}
	
	static void test_jni_getarrayregion() throws Exception {
		//int length = System.in.read();
		int start_idx = System.in.read();
		int[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
		int a = int_arr_region(arr, start_idx, 2);		
	}
	
	static void test_jni_getarrayelements_symbolic() throws Exception {
		int idx = System.in.read();
		int length = System.in.read();
		int[] arr = new int[length];
		arr[idx] = 10;
		int i = arr_symbolic(arr, idx);
		if (i == 10){
			System.out.write('W');
			System.out.flush();
		} else {
			System.out.write('L');
			System.out.flush();
		}	
	}
	
	static void test_jni_releasearrayelments(){
		int[] arr = {0, 1, 2, 3, 4};
		modify_int_arr(arr);
		int a, b, c, d, e;
		a = arr[0];
		b = arr[1];
		c = arr[2];
		d = arr[3];
		e = arr[4];
		break_();
	}

	static void test_jni_getarrayelements_and_releasearrayelements(){
		boolean[] arr_boolean = new boolean[10];
		byte[] arr_byte = new byte[10];
		char[] arr_char = new char[10];
		short[] arr_short = new short[10];
		int[] arr_int = new int[10];
		long[] arr_long = new long[10];
		
		arr_boolean[5] = false;
		arr_byte[5] =  0x7f-1;
		arr_short[5] = 0x7fff-1;
		arr_int[5] = 0x7fffffff-1;
		arr_long[5] = 0x7fffffffffffffffL-1;
		arr_char[5] = 0xffff-1;
		
		boolean a = boolean_arr(arr_boolean, 5);
		byte b = byte_arr(arr_byte, 5);
		short c = short_arr(arr_short, 5);
		int d = int_arr(arr_int, 5);
		long e = long_arr(arr_long, 5);
		char f = char_arr(arr_char, 5);
		
		arr_boolean[5] = arr_boolean[4];
		arr_byte[5] =  arr_byte[4];
		arr_short[5] = arr_short[4];
		arr_int[5] = arr_int[4];
		arr_long[5] = arr_long[4];
		arr_char[5] = arr_char[4];
		
		a = boolean_arr(arr_boolean, 5);
		b = byte_arr(arr_byte, 5);
		c = short_arr(arr_short, 5);
		d = int_arr(arr_int, 5);
		e = long_arr(arr_long, 5);
		f = char_arr(arr_char, 5);
		break_();
	}
	
	static void test_jni_getarraylength() throws Exception {
		int[] arr = new int[10];
		int len = get_array_length_native(arr);
		
		int symbolic_len = System.in.read();
		int[] sym_arr = new int[symbolic_len];
		int sim_len = get_array_length_native(sym_arr);
		
		int a = len;
		int b = sim_len;
		break_();
	}
	
	//
	// Index out of bounds
	//
	
	static void test_index_of_of_bound0() throws Exception {
		int len = System.in.read();
		int[] arr = new int[len];
		int x = arr.length;
		break_();
	}
	
	static void test_index_of_of_bound1() throws Exception {
		int len = System.in.read();
		int[] arr = new int[len];
		arr[100] = 5;
		int x = arr.length;
		break_();
	}
	
	static void test_index_of_of_bound2() throws Exception {
		int[] arr = new int[5];
		int idx = System.in.read();
		int a = arr[0];
		int b = arr[5];
		int c = arr[-1];
		int d = arr[idx];
		int e = arr[idx+1000];
		break_();
	}
	
	static void test_index_of_of_bound3() throws Exception {
		int[] arr = new int[10000];
		int idx = System.in.read();
		int a = arr[0];
		int b = arr[999];
		int c = arr[1000];
		int d = arr[idx+500];
		int e = arr[idx+1000];
		break_();
	}
	
	static void test_index_of_of_bound4() throws Exception {
		int idx = System.in.read();
		int[] arr = new int[10000+idx];
		int a = arr[0];
		int b = arr[999];
		int c = arr[1000];
		break_();
	}
	
	static void test_index_of_of_bound5() throws Exception {
		int idx = System.in.read();
		int[] arr = new int[idx+800];
		int a = arr[0];
		int b = arr[999];
		int c = arr[1000];
		break_();
	}
	
	//
	// Symbolic Indexes and Length
	//
	
	static void test_symbolic_array_length() throws Exception{
		int length = System.in.read();
		int[] arr = new int[length];
		arr['E'] = 5;
		if (arr[arr.length-1] == 5){
			System.out.write('W');
			System.out.flush();	
		} else {
			System.out.write('L');
			System.out.flush();	
		}	
	}
		
	static void test_symbolic_array_read() throws Exception {
		int[] arr = new int[256];
		// winning idxes are 65 and 67
		int winning_idx = 'A';
		arr[winning_idx] = 1;
		arr['C'] = 1;
		// game
		int idx = System.in.read();
		if (idx < 100) {
			int elem = arr[idx];
			if (elem == 1){
				System.out.write('W');
				System.out.flush();
				return;
			}
		} 
		System.out.write('L');
		System.out.flush();	
	}
	
	static void test_symbolic_array_write() throws Exception {
		int[] arr = new int[100];
		int idx = System.in.read();
		int val = System.in.read();
		arr[idx] = '5';
		if (arr['I'] == val) {
			System.out.write('W');
			System.out.flush();
		} else {
			System.out.write('L');
			System.out.flush();		
		}
	}
	
	//
	// Basic Array
	//
	
	static void test_basic_array_operations(){		
		int[] arr = {4, 3, 2, 1, 0};
		// 0 3 2 1 0
		arr[0] = arr[4];
		// 0 1 2 1 0
		int idx = arr[3];
		arr[idx] = 1;
		// 0 1 2 1 0
		arr[2] = arr[2];
		// 0 1 2 3 0
		arr[3] = arr[3] + 2;
		// 0 1 2 3 4
		arr[4] = 4;
		// access "unitialized" elements
		int[] arr2 = new int[2];
		arr2[0] = 2;
		int a, b, c, d, e, l, g, j;
		a = arr[0];
		b = arr[1];
		c = arr[2];
		d = arr[3];
		e = arr[4];
		l = arr.length;
		g = arr2[0];
		j = arr2[1];
		break_();
	}
	
	//
	// MISC
	//
	
	public static void main(String[] args) throws Exception {}
			
	public static void break_(){
		/* Add an additional basic block to the function, so the result
		   of the last statements are not deleted, when returning.
		*/
	}
	
}