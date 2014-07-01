/**
 * 
 */
package in.anandm.android.apps.thoughts.util;

/**
 * @author anandm
 *
 */
public final class SecurityContextHolder {

	private static String principal = null;;
	
	private SecurityContextHolder(){
		
	}
	public static final String getAuthenticatedUser(){
		return principal;
	}
	
	public static final boolean isAuthenticated(){
		return principal != null;
	}
	
	public static final void setPrincipal(String username){
		principal = username;
	}
}
