import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;



public class main_assembler {
	
	//Data structures
	ArrayList literals;
	ArrayList litAddr;
	ArrayList symtab;
	ArrayList symAddr;
	ArrayList pooltab;
	ArrayList registers;
	HashMap imperative_stmt, declarative_stmt, assembler_dir;
	int lc;
	
	public main_assembler() {
		lc = 0;
		literals = new ArrayList();
		litAddr = new ArrayList();
		symtab = new ArrayList();
		symAddr = new ArrayList();
		pooltab = new ArrayList();
		registers = new ArrayList();
		imperative_stmt = new HashMap();
		imperative_stmt.put("STOP", "00");
		imperative_stmt.put("ADD", "01");
		imperative_stmt.put("SUB", "02");
		imperative_stmt.put("MUL", "03");
		imperative_stmt.put("MOVER", "04");
		imperative_stmt.put("MOVEM", "05");
		imperative_stmt.put("COMP", "06");
		imperative_stmt.put("BC", "07");
		imperative_stmt.put("DIV", "08");
		imperative_stmt.put("READ", "09");
		imperative_stmt.put("PRINT", "10");
		declarative_stmt = new HashMap();
		declarative_stmt.put("DS", "02");
		declarative_stmt.put("DC", "01");
		assembler_dir = new HashMap();
		assembler_dir.put("START", "01");
		assembler_dir.put("END", "02");
		assembler_dir.put("ORIGIN", "03");
		assembler_dir.put("EQU", "04");
		assembler_dir.put("LTORG", "05");
		registers.add("AREG,");
		registers.add("BREG,");
		
		//Add 0th index to pooltab
		pooltab.add(new Integer(0));
		
	}
	
	private void print(Object obj) { System.out.println(obj); }
	
	public void print_tables() throws Exception{
		//Symtab
		
		print("\n\nSymbol table: \n");
		PrintWriter pr = new PrintWriter("symbol", "utf-8");
		
		if(!symtab.isEmpty()) {
			for(int i = 0;i < symtab.size();i++) {
				print(symtab.get(i) + "\t" + symAddr.get(i));
				pr.write(symtab.get(i) + "\t" + symAddr.get(i));
				pr.write("\n");
			}
		}
		pr.close();
		
		//Littab
		
		pr = new PrintWriter("literal", "utf-8");
		print("\n\nLiteral table: \n");
		
		if(!literals.isEmpty()) {
			for(int i = 0;i < literals.size();i ++) {
				print(literals.get(i) + "\t" + litAddr.get(i));
				pr.write(literals.get(i) + "\t" + litAddr.get(i));
				pr.write("\n");
			}
		}
		pr.close();
		
		//Pooltab
		
		print("\n\nPool table: \n");
		
		if(!pooltab.isEmpty()){
			for(int i = 0;i < pooltab.size();i ++){
				print(pooltab.get(i));
			}
		}
	}
	
	private String parse_opcode(String stmt) throws Exception {
		if(imperative_stmt.containsKey(stmt)) {
			return "(IS,"+imperative_stmt.get(stmt)+")";
		}
		else if(declarative_stmt.containsKey(stmt)) {
			return "(DS,"+declarative_stmt.get(stmt)+")";
		}
		else if(assembler_dir.containsKey(stmt)) {
			return "(AD,"+assembler_dir.get(stmt)+")";
		}
		else {
			throw new Exception("Error while parsing");
		}
	}
	
	
	//DONOT DELETE: Required for variant 1  
	/*
	private String parse_operand(String operand) {
		
		
		
		//If the operand is literal
		if(operand.matches("='[0-9]*'")) {
			operand = operand.replace("'", "");
			operand = operand.replace("=", "");
			return "(L," + operand+")";
		}
		
		//If the operand is a register
		if(registers.contains(operand)) {
			if(operand.equals(registers.get(0))) {
				return "(RG,01)";
			}
			else {
				return "(RG,02)";
			}
		}
		
		//If the operand is a constant
		if(operand.matches("[0-9]+")) {
			return "(C," + operand + ")";
		}
		
		//If the operand is a symbol
		if(operand.matches("[a-zA-Z]+")) {
			int constant = symtab.indexOf(operand);
			return "(S,"+Integer.toString(constant)+")";
		}
		
		//Error statement
		return "Error";
		
	}
	*/
	
	private void parse_line(String[] words, PrintWriter pr) throws Exception {
		// Case for if the opcode is not EQU or ORIGIN
		if(!words[1].equalsIgnoreCase("")) {
			System.out.print(parse_opcode(words[1]) + "\t");
			pr.print(parse_opcode(words[1]) + "\t");
		}
		/*Variant I
		
		if(!words[1].matches("(EQU|ORIGIN)")) {
			for(int i = 2;i < words.length;i ++) {
				System.out.print(parse_operand(words[i]) + "\t");
				pr.print(parse_operand(words[i]) + "\t");
			}
			System.out.println();
			pr.println();
		}
		
		// Case for if the opcode is EQU or ORIGIN
		if(words[1].matches("(EQU|ORIGIN)")){
			System.out.print(parse_opcode(words[1]) + "\t");
			pr.print(parse_opcode(words[1]) + "\t");
			
			String operand[] = words[2].split("\\+");
			int ptr = ((Integer)symAddr.get(symtab.indexOf(operand[0]))).intValue();
			int address = ptr + Integer.parseInt(operand[1]);
			System.out.print("(S," + address + ")");
			pr.print("(S," + address + ")");
			System.out.println();
			pr.println();
			
		}
		*/
		
		/* Variant II */
		for(int i = 2;i < words.length;i ++) {
			System.out.print(words[i] + "\t");
			pr.print(words[i] + "\t");
		}
		System.out.println();
		pr.println();
		
		
	}
	
	public void parse_file(BufferedReader br) throws Exception {
		// Declare the data structures
		String words[];
		PrintWriter pr = new PrintWriter("intermediate_code", "utf-8");
		
		// Validate the start and set the location counter
		if((words = br.readLine().split("\t")) != null) {
			if(!words[1].equalsIgnoreCase("START")) {
				pr.close();
				throw new Exception("Invalid code");
			}
			lc = Integer.parseInt(words[2]);
			parse_line(words, pr);
		}
		
		//Main loop to parse the file
		String line;
		while((line = br.readLine()) != null) {
			
			words = line.split("\t");
			// Scan the literals
			
			//Error checking
			if(!words[1].equalsIgnoreCase("")){
				if(! (assembler_dir.containsKey(words[1]) || declarative_stmt.containsKey(words[1]) || imperative_stmt.containsKey(words[1]) )) {
					throw new Exception("Syntax error: Opcode not recognized");
				}
			}
			
			//If symbol is detected
			if(!words[0].equalsIgnoreCase("")) {
				
				//Error Checking for label
				if(symtab.contains(words[0])) {
					throw new Exception("Syntax Error: Label Duplication");
				}
				
				// Case for equ
				if(words[1].equalsIgnoreCase("EQU")) {
					String[] scan_line = words[2].split("\\+");
					String symbol = scan_line[0];
					if(symtab.contains(symbol)){
						int ptr = symtab.indexOf(symbol);
						int address = Integer.parseInt(scan_line[1]) +  ((Integer)symAddr.get(ptr)).intValue();
						symtab.add(words[0]);
						symAddr.add(new Integer(address));
					}
					//else add -1 for the time
					else {
						symtab.add(words[0]); 
						symAddr.add(words[2]);
					}
				}
				
				else {
					symtab.add(words[0]); 
					symAddr.add(new Integer(lc));
				}
			}
			
			
			//If literal is detected
			if(words.length == 4 && words[3].matches("='[0-9]*'")) {
				literals.add(words[3].split("=")[1]);
				litAddr.add(new Integer(-1));
				
				
			}
			/*
			if(words.length == 3 && words[2].matches("='[0-9]*'")){
				try{
					int ptr = literals.indexOf(words[2].split("=")[1]);
					litAddr.set(ptr, new Integer(lc));
				}catch(Exception e){
					throw e;
				}
				
			}
			*/
			
			// Incrementing the location counter
			
			// If ORIGIN occurs
			if(words[1].matches("(ORIGIN)")) {
				String[] scan_line = words[2].split("\\+");
				String symbol = scan_line[0];
				int ptr = symtab.indexOf(symbol);
				int address = Integer.parseInt(scan_line[1]) +  ((Integer)symAddr.get(ptr)).intValue();
				lc = address;
			}
			// If ltorg or end occurs fill the addresses of literals
			if(words[1].equalsIgnoreCase("LTORG")) {
				pooltab.add(new Integer(literals.size()));
				int counter = lc;
				for(int i = 0;i < litAddr.size();i ++) {
					if(((Integer)litAddr.get(i)).intValue() == -1) {
						litAddr.set(i, new Integer(counter));
						counter += 1;
					}
				}
			}
			else if(words[1].equalsIgnoreCase("END")) {
				int counter = lc;
				for(int i = 0;i < litAddr.size();i ++) {
					if(((Integer)litAddr.get(i)).intValue() == -1) {
						litAddr.set(i, new Integer(counter));
						counter += 1;
					}
				}
			}
			
			// If DS occurs
			else if(words[1].equals("DS")) {
				lc += Integer.parseInt(words[2]);
			}
			
			// If Assembler directive occurs
			else if(assembler_dir.containsKey(words[1])) {
				
			}
			
			// Else increment the location counter by 1
			else {
				lc += 1;
			}
			
			parse_line(words, pr);
			
		}
		br.close();
		pr.close();
		
		
		//TODO: Filling all the forward references
		//Filling all the forward references
		
		for(int i = 0;i < symtab.size();i ++){
			if(symAddr.get(i) instanceof String){
				if(((String)symAddr.get(i)).matches("[a-zA-Z][+-]?[1-9]?")){
					String to_parse = (String)symAddr.get(i);
					
					if(to_parse.contains("+")){
						String p_words[] = to_parse.split("\\+");
						int ptr = symtab.indexOf(p_words[0]);
						int addr = ((Integer)symAddr.get(ptr)).intValue() + Integer.parseInt(p_words[1]);
						
						symAddr.set(i, new Integer(addr));
					}
					
					else if(to_parse.contains("-")){
						String p_words[] = to_parse.split("\\-");
						int ptr = symtab.indexOf(p_words[0]);
						int addr = ((Integer)symAddr.get(ptr)).intValue() - Integer.parseInt(p_words[1]);
						
						symAddr.set(i, new Integer(addr));
					}
					
					else {
						throw new Exception("Same address for multiple literals");
					}
					
				}
				else{
					throw new Exception("Invalid operands");
				}
			}
		}
		
	}
	
	public static void main(String args[]) throws Exception {
		
		BufferedReader br = new BufferedReader(new FileReader("code"));
		main_assembler ma = new main_assembler();
		ma.parse_file(br);
		ma.print_tables();
	}
}