import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
public class projectmain 
{
	public static void main (String[] args) throws java.io.IOException
	{
		
		String query=null, input=null;
		int noOfClauses=0,Qtrue=0;
		ArrayList<String> sentences = new ArrayList<String>(); //Clauses
		ArrayList<String> lhs = new ArrayList<String>();
		ArrayList<String> rhs = new ArrayList<String>();
		ArrayList<String> facts = new ArrayList<String>();
		ArrayList<String> constants = new ArrayList<String>();
		
		System.out.println("Starting to read Input");
		
		//BufferedReader br = new BufferedReader(new FileReader("C:\\USC\\AI\\HW3\\input.txt"));
		BufferedReader br = new BufferedReader(new FileReader(new File ("input.txt").getAbsolutePath()));
		//BufferedWriter bw = new BufferedWriter(new FileWriter(new File ("C:\\USC\\AI\\HW3\\output.txt")));
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File ("output.txt").getAbsolutePath()));
		  
	/*	if((input = br.readLine()) != null)
			query = input;
		  
		if((input = br.readLine()) != null)
			noOfClauses = Integer.valueOf(input);
		
		for(int i=0;i<noOfClauses;i++)
		{
			if((input = br.readLine()) != null)
				sentences.add(input);
		}
		*/
		
		//Reading the input
		String[] tokens = new String[15]; 
		int s=0;
		while((input = br.readLine()) != null)
		{
			StringTokenizer st = new StringTokenizer(input," ",true);
			tokens[s] = st.nextToken();
			s++;
			
		}
		

		//Extracting the queries and tokens
		query = tokens[0];
		noOfClauses = Integer.valueOf(tokens[1]);
		for(int i=2;i<(tokens.length -2) ;i++)
			sentences.add(tokens[i]);
			 
		
		
		 
		br.close();
		
		System.out.println("The query is - " +query);
		System.out.println();
		System.out.println("The Clauses are:");
		for(String str: sentences)
			System.out.println(str);
		
		//Splitting clauses based on 'Implication' symbol into LHS & RHS
		for(int i=0;i<noOfClauses;i++)
		{
			if(sentences.get(i).contains("=>"))
			{
				String str[] = sentences.get(i).split("=>");
				lhs.add(str[0]);
				rhs.add(str[1]);
			}
			else
			{
				facts.add(sentences.get(i));
			}
			
		}
		
		//Finding all constants

		//Query - Constants
		if(query.contains(","))
		{
			String a[] = query.split("\\(");
			//	System.out.println("a[0]=" +a[0]+ " a[1]=" +a[1]);
			String b[] = a[1].split(",");
			constants.add(b[0]);
			String c[] = b[1].split("\\)");
			constants.add(c[0]);
		}
		else
		{
			String a2[] = query.split("\\(");
			String b2[] = a2[1].split("\\)");
			constants.add(b2[0]);
		}
		
		//Facts - Constants
		for(int i=0;i<facts.size();i++)
		{
			if(facts.get(i).contains(","))
			{
				String a1[] = facts.get(i).split("\\(");
				String b1[] = a1[1].split(",");
				constants.add(b1[0]);
				String c1[] = b1[1].split("\\)");
				constants.add(c1[0]);	
			}
			else
			{	
				String a2[] = facts.get(i).split("\\(");
				String b2[] = a2[1].split("\\)");
				constants.add(b2[0]);
			}
			
		}
		
		//Implication:LHS - Constants
		for(int i=0;i<lhs.size();i++)
		{
			if(lhs.get(i).contains("&"))
			{
				String predicates[] = lhs.get(i).split("&");
				//System.out.println("predicates.length = " +predicates.length);
				for(int j=0;j<predicates.length;j++)
				{
					if(predicates[j].contains(","))
					{
						String a1[] = predicates[j].split("\\(");
						String b1[] = a1[1].split(",");
						constants.add(b1[0]);
						String c1[] = b1[1].split("\\)");
						constants.add(c1[0]);	
					}
					else
					{	
						String a2[] = predicates[j].split("\\(");
						String b2[] = a2[1].split("\\)");
						constants.add(b2[0]);
					}
				}
			}
			else
			{	
				if(lhs.get(i).contains(","))
				{
					String a1[] = lhs.get(i).split("\\(");
					String b1[] = a1[1].split(",");
					constants.add(b1[0]);
					String c1[] = b1[1].split("\\)");
					constants.add(c1[0]);	
				}
				else
				{	
					String a2[] = lhs.get(i).split("\\(");
					String b2[] = a2[1].split("\\)");
					constants.add(b2[0]);
				}
			}
				
			
		}
		
		//Implication:RHS - Constants
		for(int i=0;i<rhs.size();i++)
		{
			if(rhs.get(i).contains(","))
			{
				String a1[] = rhs.get(i).split("\\(");
				String b1[] = a1[1].split(",");
				constants.add(b1[0]);
				String c1[] = b1[1].split("\\)");
				constants.add(c1[0]);	
			}
			else
			{	
				String a2[] = rhs.get(i).split("\\(");
				String b2[] = a2[1].split("\\)");
				constants.add(b2[0]);
			}
			
		}
		
		System.out.println();

		//System.out.println("constats.size = " +constants.size());
		//System.out.println("The Constants are:");
		//for(String str: constants)
			//System.out.println(str);

		//Unique constants
		Set<String> uniqueConstants = new HashSet<String>(constants);
		System.out.println("uniqueConstants.size = " +uniqueConstants.size());
		System.out.println("The Unique Constants are:");
		for(String str: uniqueConstants)
			System.out.println(str);
		ArrayList<String> uConst = new ArrayList<String>(uniqueConstants);
		
		//Apply unification for each constant
		for(int i=0;i<uConst.size();i++)
		{
			String s1= "(" +uConst.get(i) +",";
			String s2= "," +uConst.get(i) +")";
			String s3= "(" +uConst.get(i) +")";
			
			ArrayList<String> lhsCopy = new ArrayList<String>();
			ArrayList<String> rhsCopy = new ArrayList<String>();
			
			for(int j=0;j<lhs.size();j++)
			{
				String str = lhs.get(j);//replacedStr=null;
				if(str.contains("(x,"))
					str = str.replace("(x,",s1);
				if(str.contains(",x)"))
					str = str.replace(",x)",s2);
				if(str.contains("(x)"))
					str = str.replace("(x)",s3);
				
				lhsCopy.add(str);
			}
			for(int j=0;j<rhs.size();j++)
			{
				String str = rhs.get(j);//replacedStr=null;
				if(str.contains("(x,"))
					str = str.replace("(x,",s1);
				if(str.contains(",x)"))
					str = str.replace(",x)",s2);
				if(str.contains("(x)"))
					str = str.replace("(x)",s3);
				
				rhsCopy.add(str);
			}
			
		/*	System.out.println("LHS after applying unification constant =" +uConst.get(i));
			for(String s:lhsCopy)
				System.out.println(s);
			
			System.out.println("RHS after applying unification constant =" +uConst.get(i));
			for(String s:rhsCopy)
				System.out.println(s);
		*/
			
			//Test the query for all Facts and RHS
			
			//Testing with Facts
			for(int j=0;j<facts.size();j++)
			{
				if(query.equals(facts.get(j)))
				{
					System.out.println("Query TRUE for Fact = " +facts.get(j));
					Qtrue=1;
					break;
				}
			}
			
			ArrayList<String> result = new ArrayList<String>();

			//Testing with RHS
			for(int j=0;j<rhs.size();j++)
			{
				result = new ArrayList<String>();
				if(query.equals(rhsCopy.get(j)))
				{
					//Split the LHS into single predicates and add them to result
					System.out.println("Query equalled RHS=" +rhsCopy.get(j));
					String[] predicates = SplitLHS(lhsCopy.get(j));
					for(int l=0;l<predicates.length;l++)
					{
						result.add(predicates[l]);
						System.out.println("Added predicate=" +predicates[l]);
					}
					
					int factmatch=0,rhsmatch=0;

					//While result arraylist not empty - *Break cond would be - if it doesnt match with any rhs or any facts
					while(!result.isEmpty())
					{
						System.out.println("In while Loop");
						factmatch=0;
						rhsmatch=0;
						int cnt = 0;

						//Matching with Facts
						for(int m=0;m<facts.size();m++)
						{
							if(!result.isEmpty())
							{
								if(result.get(cnt).equals(facts.get(m)))
								{
									
									String rr=result.remove(cnt);
									System.out.println("Deleted predicate="+rr);
									//cnt++;
									factmatch = 1;
								}
							}
						}

						//Matching with RHS
						for(int m=0;m<rhsCopy.size();m++)
						{
							if(!result.isEmpty())
							{
								//System.out.println("result.get(cnt)="+result.get(cnt));
								//System.out.println("rhsCopy.get(m)="+rhsCopy.get(m));
								//String f=result.get(cnt);
								//String g=rhsCopy.get(m);
								if((result.get(cnt)).equals(rhsCopy.get(m)))
								{
									//Split the LHS into single predicates and add them to result
									String[] pred = SplitLHS(lhsCopy.get(m));
									for(int l=0;l<pred.length;l++)
									{
										result.add(pred[l]);
										System.out.println("Added predicate=" +pred[l]);
									}
								
									String ss=result.remove(cnt);
									System.out.println("Deleted predicate="+ss);
									//cnt++;
									rhsmatch=1;
								}
							}
							
						}
						if(factmatch==0 && rhsmatch ==0)
							break;	
					}
					if(result.isEmpty())
					{
						System.out.println("Query is TRUE");
						Qtrue=1;
					}
					else
					{
						System.out.println("Query is FALSE");
					}
				}
			}
			
			
			
		}
	/*	
		String s1= "(" +uConst.get(2) +",";
		String s2= "," +uConst.get(2) +")";
		String s3= "(" +uConst.get(2) +")";
		
		String str = lhs.get(1),replacedStr=null;
		if(str.contains("(x,"))
			str = str.replace("(x,",s1);
			//System.out.println("Found - (x,");
		System.out.println("Replaced Str= " +replacedStr);
		if(str.contains(",x)"))
			str = str.replace(",x)",s2);
			//System.out.println("Found - ,x)");
		System.out.println("Replaced Str= " +replacedStr);
		if(str.contains("(x)"))
			str = str.replace("(x)",s3);
			//System.out.println("Found - (x)");
			//replacedStr = str.replace("(x)",s3);
		  
		System.out.println("Replaced Str= " +str);	*/
		
		
		
		System.out.println("******************");
		if(Qtrue==1)
		{
			System.out.println("TRUE");
			bw.write("TRUE");
		}
		if(Qtrue==0)
		{
			System.out.println("FALSE");
			bw.write("FALSE");
		}
		System.out.println("******************");
		bw.close();
		
		
	}
	
	public static String[] SplitLHS(String LHS)
	{
		//String[] pred = new String[30];
		//int k=0;
		if(LHS.contains("&"))
		{
			String[] pred = LHS.split("&");
			return pred;
		}
		else
		{
			String[] pred = new String[1];
			pred[0] = LHS;
			return pred;
		}
		
	}
}

