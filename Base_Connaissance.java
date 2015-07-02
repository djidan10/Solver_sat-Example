import java.util.ArrayList;


public class Base_Connaissance {

	String Non_but;
	int nb_clause=0;
	int nb_variable=0;
	ArrayList<Strat> Liste_strat =new ArrayList<Strat>(); 
	
	
	
	public void add_strat(Strat value)
	{
		this.Liste_strat.add( value);
	}
	
	
	
}
