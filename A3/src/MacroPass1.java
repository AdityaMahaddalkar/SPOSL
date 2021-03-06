import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

//TODO: Nesting macros

class Pair{
	Integer line_no;
	String line;
	
	public Pair(Integer l, String sl){
		line_no = l;
		line = sl;
	}
	
	public void print(){
		System.out.println(line_no+" : "+line+"\n");
	}
}

class Triplet{
	// To store MNT macro name, number of parameters and line number
	public String name;
	public Integer args, line;
	
	public Triplet(String s, Integer a, Integer l){
		name = s;
		args = a;
		line = l;
	}
	
	public void print(){
		System.out.println("Name: "+name+"\tArgs:"+args+"\tline:"+line+"\n");
	}
	
}

class Arguments{
	// For actual vs positional and actual vs formal parameters
	// Create object for each macro
	HashMap<String, Integer> avsp;
	HashMap<String, Integer> fvsp;
	
	public Arguments(){
		avsp = new HashMap<String, Integer>();
		fvsp = new HashMap<String, Integer>();
	}
	
	public HashMap<String, Integer> ret_avsp(){
		return avsp;
	}
	
	
	
}

public class MacroPass1 {
	
	// Main class to parse the macros
	
	ArrayList<Pair> MDT;
	ArrayList<Triplet> MNT;
	HashMap<String, Arguments> arg_list;
	int lc;
	
	public MacroPass1(){
		MDT = new ArrayList<Pair>();
		MNT = new ArrayList<Triplet>();
		lc = 0;
		arg_list = new HashMap<String, Arguments>();
	}
	
	public void print(Object o){ System.out.println(o); }
	
	public void print_tables() {
		// Print MNT
		print("\n\tMNT\n");
		for(Triplet tr: MNT){
			tr.print();
		}
		
		// Print MDT
		print("\n\tMDT\n");
		for(Pair pr: MDT){
			pr.print();
		}
	}
	
	public void parse_file(BufferedReader br) throws Exception{
		// Main file parser and write the intermediate code in other file
		
		//TODO: To write to a intermediate file
		
		PrintWriter pr = new PrintWriter("inter_macro", "utf-8");
		PrintWriter mnt_writer = new PrintWriter("MNT", "utf-8");
		PrintWriter mdt_writer = new PrintWriter("MDT", "utf-8");
		
		boolean macro_flag = false;
		String line;
		String current_macro_name = null;
		
		while((line = br.readLine()) != null){
			
			String words[] = line.split("\t");
			
			// If macro keyword is detected
			if(words[1].equalsIgnoreCase("MACRO")){
				
				// Set the macro_flag true and set the current macro name
				macro_flag = true;
				current_macro_name = words[2];
				
				//Create an object of actual vs positional parameters and add it to arg_list
				Arguments ar = new Arguments();
				int parameters = 0;
				for(int i = 3;i < words.length; i++){
					ar.avsp.put(words[i].split(",")[0], i-2);
					parameters += 1;
				}
				arg_list.put(words[2], ar);
				
				//Create a Triplet and Add name and parameters and line number to MNT
				int line_no = 0;
				if(!MDT.isEmpty()){
					line_no = MDT.size();
				}
				Triplet tr = new Triplet(words[2], new Integer(parameters), new Integer(line_no));
				MNT.add(tr);
				
				//TODO: Replace print with pr.write
				//print(line);
			}
			
			// If macro flag is set, means we are inside the macro
			else if(macro_flag){
				
				//If MEND occurs then write it to MDT and reset macro_flag
				if(words[1].equalsIgnoreCase("MEND")){
					MDT.add(new Pair(lc, words[1]));
					macro_flag = false;
				}
				
				//If general instruction occurs then replace the actual arguments
				// with formal arguments
				else{
					
					// Get the formal vs positional arguments table for current macro
					HashMap<String, Integer> cur_avsp = arg_list.get(current_macro_name).ret_avsp();
					
					//Loop through the avsp to replace each actual argument with positional argument
					for (Entry<String, Integer> entry : cur_avsp.entrySet()) {
					    // Replace all occurrences of actual to positional
						line = line.replaceAll(entry.getKey(), Integer.toString(entry.getValue()));
					}
					
					//write the line in the MDT
					MDT.add(new Pair(lc, line));
				}
				lc += 1;
				
				//TODO: replace print with pr.write()
				//print(line);
				
			}
			
			// If we are not inside a macro
			else if(!macro_flag){
				//print(line);
				
				pr.write(line + "\n");
				//If any macro name occurs
				if(arg_list.containsKey(words[1])){
					
					int line_no = -1;
					//TODO: Logic to expand the macro
					//Find the line number of macro from MNT
					for(Triplet tr: MNT){
						if(tr.name.equalsIgnoreCase(words[1])){
							line_no = tr.line;
						}
					}
					
					// Prepare formal vs positional parameters
					HashMap<String, String> fvsp = new HashMap<String, String>();
					for(int i = 2;i < words.length;i ++){
						fvsp.put(Integer.toString(i-1), words[i].split(",")[0]);
					}
					
					//Start printing the macro definition from line _no
					if(line_no != -1){
						for(int i = line_no;;i++){
							if(MDT.get(i).line.equalsIgnoreCase("MEND")){
								break;
							}
							else{
								String temp_line = MDT.get(i).line;
								for(Entry<String, String> entry: fvsp.entrySet()){
									temp_line = temp_line.replaceAll(entry.getKey(), entry.getValue());
								}
								print(temp_line);
							}
						}
					}
				}
			}
			
			
		}
		
		//Write to mnt_writer and mdt_writer
		
		for(Triplet tr : MNT){
			mnt_writer.write(tr.name + "\t" + tr.args + "\t" + tr.line + "\n");
		}
		
		for(Pair pair : MDT){
			mdt_writer.write(pair.line_no + "\t" + pair.line + "\n");
		}
		
		pr.close();
		mnt_writer.close();
		mdt_writer.close();
		
	}
	
	public static void main(String args[]) throws Exception{
		
		MacroPass1 mp = new MacroPass1();
		mp.parse_file(new BufferedReader(new FileReader("macro")));
		
	}
}
