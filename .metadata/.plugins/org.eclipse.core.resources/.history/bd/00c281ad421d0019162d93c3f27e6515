import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;


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

public class MacroPass2{
	
	ArrayList<Pair> MDT;
	ArrayList<Triplet> MNT;
	
	public MacroPass2(){
		MDT = new ArrayList<Pair>();
		MNT = new ArrayList<Triplet>();
	}
	
	public boolean check_macro(String opcode){
		for(Triplet tr: MNT){
			if(tr.name.equals(opcode)){
				return true;
			}
		}
		return false;
	}
	
	public int ret_macro_line(String macro){
		
		for(Triplet tr: MNT){
			if(tr.name.equals(macro)){
				return tr.line;
			}
		}
		return -1;
		
	}
	
	public String expand_macro(String macro_call, ArrayList<Pair> avsp){
		
		StringBuilder expansion = new StringBuilder();
		
		try{
			int line = ret_macro_line(macro_call);
			for(int i = line; i < MDT.size();i ++){
				if(MDT.get(i).line.equals("MEND")){
					break;
				}
				expansion.append(MDT.get(i).line + "\n");
			}
		}
		catch (Exception e){
			System.out.println(e);
		}
		
		
		// Replace positional parameters by actual parameters
		String replaced_expansion = expansion.toString();
		
		for(Pair pair: avsp){
			replaced_expansion = replaced_expansion.replaceAll(Integer.toString(pair.line_no), pair.line);
		}
		
		return replaced_expansion;
		
	}
	
	public void print_MDT_MNT(){
		
		//Print MDT
		
		for(Pair pair: MDT){
			System.out.println(pair.line_no + "\t" + pair.line + "\n");
		}
		
		//Print MNT
		
		for(Triplet tr : MNT){
			System.out.println(tr.name + "\t" + tr.args + "\t" + tr.line + "\n");
		}
	}
	
	public void fill_MNT_MDT() throws Exception{
		
		BufferedReader mnt_reader = new BufferedReader(new FileReader("../A3/MNT"));
		BufferedReader mdt_reader = new BufferedReader(new FileReader("../A3/MDT"));
		
		//Fill MNT
		String line;
		
		while((line = mnt_reader.readLine()) != null){
			String words[] = line.split("\t");
			
			MNT.add(new Triplet(words[0], Integer.parseInt(words[1]), Integer.parseInt(words[2])));
		}
		
		//Fill MDT
		
		while((line = mdt_reader.readLine()) != null){
			String words[] = line.split("\t");
			if(words[1].equals("MEND")){
				MDT.add(new Pair(Integer.parseInt(words[0]), words[1]));
			}
			else{
				StringBuilder temp = new StringBuilder();
				for(int i = 2; i < words.length;i ++){
					temp.append("\t" + words[i]);
				}
				MDT.add(new Pair(Integer.parseInt(words[0]), temp.toString()));
			}
		}
		mnt_reader.close();
		mdt_reader.close();
	}
	
	public void parseIC(BufferedReader br) throws Exception{
		
		PrintWriter pr = new PrintWriter("output", "utf-8");
		
		String line;
		
		while((line = br.readLine()) != null){
			
			String words[] = line .split("\t");
			
			//Check whether if opcode is a macro call
			if(check_macro(words[1])){
				//Create array list of actual vs positional parameters
				
				ArrayList<Pair> avsp = new ArrayList<Pair>();
				
				for(int i = 2; i < words.length; i++){
					avsp.add(new Pair(i - 1, words[i].split(",")[0]));
				}
				
				//Test ----->> Success
				/*
				for(Pair pair: avsp){
					System.out.println(pair.line_no + " : " + pair.line);
				}
				*/
				
				//Replace the macro call with macro expansion
				String expansion = expand_macro(words[1], avsp);
				System.out.println(expansion);
				pr.write(expansion);
			}
			else{
				System.out.println(line);
				pr.write(line);
			}
			pr.write("\n");
			
		}
		pr.close();
		
	}
	
	public static void main(String args[]) throws Exception{
		
		MacroPass2 mp2 = new MacroPass2();
		mp2.fill_MNT_MDT();
		mp2.print_MDT_MNT();
		mp2.parseIC(new BufferedReader(new FileReader("../A3/inter_macro")));
	}
}