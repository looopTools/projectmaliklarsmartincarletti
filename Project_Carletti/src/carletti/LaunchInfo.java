package carletti;

public class LaunchInfo {
	
	private static String $OS;
	
	public LaunchInfo(){
		LaunchInfo.$OS = System.getProperty("os.name");
		
	}

	public static String get$OS() {
		return $OS;
	}

	public static void set$PATH(String $OS) {
		LaunchInfo.$OS = $OS;
	}

}
