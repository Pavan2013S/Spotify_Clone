package Admin;

public class Admin {

	private static final String username = "admin";
	private static final String password = "admin123";
	public static boolean isAuthenticate(String user, String pass) {
		if(username.equals(user)&&password.equals(pass)) {
			return true;
		}
		return false;
	}
	
	

}
