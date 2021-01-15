import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class start {

	public static Pattern pattern;
    public static Matcher matcher;
    
    private static final String PASSWORD_PATTERN = "((?=.*[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789])(?=.*\\\\d)(?=.*[~!@#$%^&*_-`|\\\\(){}[]:;\\\"'<>,.?/]).{8,40})";
	
	public static void main(String arg[]) throws Exception
	{
		try
		{
		    pattern = Pattern.compile(PASSWORD_PATTERN);
		    matcher = pattern.matcher("zse45rdx123@");
	        System.out.println(matcher.matches());
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	
}
