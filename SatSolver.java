import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.InstanceReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;
import org.sat4j.tools.ModelIterator;

public class SATSolver {
public static void main ( String [] args ) {
ISolver solver = SolverFactory . newDefault ();
solver . setTimeout (3600); // 1 hour timeout
Reader reader = new DimacsReader ( solver );
// CNF filename is given on the command line

//--------------------Declaration---------------------------

Base_Connaissance Sigma = new Base_Connaissance();

//--------------------Recuperer les poids---------------------------
File File = new File("base_connaissance.cnf");
BufferedReader reader1 = null;
//textArea.setText(t);(sCurrentLine);
String ls = System.getProperty("line.separator");
	String d;
	float f;
	int check=0;
	String clause;
	String FirstLine = null;
	String[] parts =null;
	int nb_var;

	try (BufferedReader br = new BufferedReader(new FileReader(File)))
{

	String sCurrentLine;

	while ((sCurrentLine = br.readLine()) != null) {
		
			check=0;
		//System.out.println(sCurrentLine);
		if(sCurrentLine.startsWith("p") )
		{
			FirstLine=sCurrentLine;
			parts=FirstLine.split(" ");
			Sigma.nb_variable=Integer.parseInt(parts[2]);
			//System.out.println(Sigma.nb_variable);
			
			
			
		//	System.out.println(sCurrentLine);
			//textArea.append(sCurrentLine);
			//textArea.append(ls);
		}
		else{
			
			//float f = Float.valueOf(sCurrentLine.replaceAll("[^(\\.\\d)]", ""));
			clause= sCurrentLine.replaceAll("0.*", "");
			clause=clause+" 0";
			d= sCurrentLine.replaceAll(".*0", "");
			f = Float.valueOf(d.replaceAll("[^(\\.\\d)]", ""));
			//System.out.println(clause);

			//float x = f - (int) f;
			//float number_dec =() f%1;
			//number_dec=(float) (number_dec-Math.floor(number_dec));
			//System.out.println(f);
			
			

			if(Sigma.Liste_strat.size()==0)
			{
				Strat St = new Strat();
				St.poid=f;
				St.Liste_Clause.add(clause);
				
				Sigma.add_strat(St);			
				Sigma.nb_clause+=1;
			}
			else{
				
			for(int i=0 ;i<Sigma.Liste_strat.size();i++)
			{
				
				if(Sigma.Liste_strat.get(i).poid==f)
				{
					check=1;
				}
			}
			
			
			if(check==0)
			{
				
				Strat St = new Strat();
				St.poid=f;
				St.Liste_Clause.add(clause);
				Sigma.add_strat(St);
				Sigma.nb_clause+=1;

			}
			else{
				
				for(int i=0 ;i<Sigma.Liste_strat.size();i++)
				{
					
					if(Sigma.Liste_strat.get(i).poid==f)
					{
						
						
						Sigma.Liste_strat.get(i).add_Clause(clause);
						Sigma.nb_clause+=1;
						break;
						
						
					}
				}	
				
			}
			
			
			}

			
			
			
		}
		
		

		
	}
	} 

catch (IOException e) {
		e.printStackTrace();
	}
    
    
for(int i=0 ;i<Sigma.Liste_strat.size();i++)
{
	
	//System.out.println("--"+Sigma.Liste_strat.get(i).poid);
	
	for(int k=0 ;k<Sigma.Liste_strat.get(i).Liste_Clause.size();k++)
	{
		
	//	System.out.println("clause **"+Sigma.Liste_strat.get(i).Liste_Clause.get(k));
	}
	
	
	
	
}
	
//System.out.println("--"+Sigma.Liste_strat.size());
    
//--------------------Partie recuperation non phi---------------------------
    
Scanner sc = new Scanner(System.in);
System.out.println("Bonjour Veuillez Siaisir le Non_Phi");
int L,U,R = 0,delet_var=0;

while(sc.hasNextLine()) 
{
	
	Sigma.Non_but=sc.nextLine();
	if(!Sigma.Non_but.equals("0"))
	{
	Sigma.Non_but=Sigma.Non_but+" 0";
	System.out.println(Sigma.Non_but);	
	
	
	
	
L=0;
U=Sigma.Liste_strat.size()-1;

while(L<U)
{
	
	System.out.println("L : "+L+"| U : "+U);	

	delet_var=0;
	
	
	if((L+U)%2==0)
		{
		R=((L+U)/2);
		
		}else{
			
		R=(int) Math.round(((L+U)/2)+0.5);		
		}
	
	try {
		//---------Var supprimer Count-------------------------------------------------------------
		for (int indice=0; indice<L  ;indice++)
		{
			for(int strt=0;strt<Sigma.Liste_strat.get(indice).Liste_Clause.size();strt++)
			{
			
			delet_var+=1;
			
			}	
		}
		
		
		for (int indice=Sigma.Liste_strat.size()-1; indice>U   ;indice--)
		{
			for(int strt=0;strt<Sigma.Liste_strat.get(indice).Liste_Clause.size();strt++)
			{
			
			delet_var+=1;
			
			}	
		}
		
		//---------Var supprimer count-------------------------------------------------------------
		
		PrintWriter writer;
		writer = new PrintWriter("temp.cnf", "UTF-8");
		writer.println("p cnf "+Sigma.nb_variable+" "+(Sigma.nb_clause-delet_var+1));
		
		for (int indice=L; indice<=U  ;indice++)
		{

			
			for(int strt=0;strt<Sigma.Liste_strat.get(indice).Liste_Clause.size();strt++)
			{
			
			writer.println(Sigma.Liste_strat.get(indice).Liste_Clause.get(strt));
			
			}
			
			
			
		}
		writer.println(Sigma.Non_but);

		
		

		writer.close();	
		

		
			
		
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	
	
	
	
	
	try {
		IProblem problem = reader . parseInstance ( "temp.cnf");

			//System.out.println();
			
				if ( problem . isSatisfiable ()) {
				//----Satisfiable
				//System . out . println (" Satisfiable !");
				//System . out . println ( reader . decode ( problem . model ()));
				
				
				U=R-1;
				
				
				
				} else {
				//----Satisfiable
				//System . out . println (" Unsatisfiable !");
				
				L=R;
				
				}
		} catch ( FileNotFoundException e) {
		// TODO Auto - generated catch block
		} catch ( ParseFormatException e) {
		// TODO Auto - generated catch block
		} catch ( IOException e) {
		// TODO Auto - generated catch block
		} catch ( ContradictionException e) {
		//System .out . println (" Unsatisfiable ( trivial )!");
		L=R;
		} catch ( TimeoutException e) {
		System .out . println (" Timeout , sorry !");
		}
	
	
	
	
}	



System.out.println(R);
System.out.println(Sigma.Liste_strat.get(R).poid);
	}
}
//--------------------Fin de la lecture de non phi---------------------------




/*
try {
IProblem problem = reader . parseInstance ( "temp.cnf");

	System.out.println();
	
	
	
		if ( problem . isSatisfiable ()) {
			
			
			
		System . out . println (" Satisfiable !");
		System . out . println ( reader . decode ( problem . model ()));
		
		
		
		
		
		
		} else {
			
			
			
			
		System . out . println (" Unsatisfiable !");
		
		
		
		}
} catch ( FileNotFoundException e) {
// TODO Auto - generated catch block
} catch ( ParseFormatException e) {
// TODO Auto - generated catch block
} catch ( IOException e) {
// TODO Auto - generated catch block
} catch ( ContradictionException e) {
System .out . println (" Unsatisfiable ( trivial )!");
} catch ( TimeoutException e) {
System .out . println (" Timeout , sorry !");
}

*/
}
}
