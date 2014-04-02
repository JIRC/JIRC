

public class FizzBuzz
{

	public static void main( String args[] )
	{
		String out = "";
		
		for ( int i = 0; i < 100; i++ )
		{
			if ( i % 15 == 0 )
			{
				out = "FizzBuzz";
			}
			else if ( i % 5 == 0 )
			{
				out = "Buzz";
			}
			else if ( i % 3 == 0 )
			{
				out = "Fizz";
			}
			else
			{
				out = "" + i;
			}
			
			System.out.println( out );
		}
	}
	
	
	/*
	 * Here is an example of using comments
	 * Below is a long if statement split
	 * into two lines
	*/
	private int doNothing( int foo, int bar )
	{
		if ( ( foo == 1 && bar == 1 ) 
			|| ( foo == 2 && bar == 2 ) )
		{
			return -1;
		}
		else
		{
			return 1;
		}
	}
}
