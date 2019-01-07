import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.WeakHashMap;

//TODO: Error handling for assembly code

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
		declarative_stmt.put("DS", "01");
		declarative_stmt.put("DC", "02");
		assembler_dir = new HashMap();
		assembler_dir.put("START", "01");
		assembler_dir.put("END", "02");
		assembler_dir.put("ORIGIN", "03");
		assembler_dir.put("EQU", "04");
		assembler_dir.put("LTORG", "05");
		assembler_dir.put("STOP", "06");
		registers.add("AREG,");
		registers.add("BREG,");
		
		//Add 0th index to pooltab
		pooltab.add(new Integer(0));
		
	}
	
	private void print(Object obj) { System.out.println(obj); }
	
	public void print_tables() {
		//Symtab
		
		print("\n\nSymbol table: \n");
		
		if(!symtab.isEmpty()) {
			for(int i = 0;i < symtab.size();i++) {
				print(symtab.get(i) + "\t" + symAddr.get(i));
			}
		}
		
		print("\n\nLiteral table: \n");
		
		if(!literals.isEmpty()) {
			for(int i = 0;i < literals.size();i ++) {
				print(literals.get(i) + "\t" + litAddr.get(i));
			}
		}
	}
	
	private void print(int obj) { System.out.println(obj); }
	
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
	
	private String parse_operand(String operand) {
		
		
		
		//If the operand is literal
		if(operand.matches("=?'[0-9]*'")) {
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
	
	private void parse_line(String[] words, PrintWriter pr) throws Exception {
		// Case for if the opcode is not EQU or ORIGIN
		if(!words[1].equalsIgnoreCase("") && !words[1].matches("(EQU|ORIGIN)")) {
			System.out.print(parse_opcode(words[1]) + "\t");
			pr.print(parse_opcode(words[1]) + "\t");
		}
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
		
		
	}
	
	public void parse_file(BufferedReader br) throws Exception {
		// Declare the data structures
		String words[];
		PrintWriter pr = new PrintWriter("intermediate_code", "utf-8");
		
		// Validate the start and set the location counter
		if((words = br.readLine().split("\t")) != null) {
			if(!words[1].equalsIgnoreCase("START")) {
				throw new Exception("Invalid code");
			}
			lc = Integer.parseInt(words[2]);
		}
		
		//Main loop to parse the file
		String line;
		while((line = br.readLine()) != null) {
			
			words = line.split("\t");
			// Scan the literals
			
			//If symbol is detected
			if(!words[0].equalsIgnoreCase("")) {
				
				// Case for equ
				
				if(words[1].equalsIgnoreCase("EQU")) {
					String[] scan_line = words[2].split("\\+");
					String symbol = scan_line[0];
					int ptr = symtab.indexOf(symbol);
					int address = Integer.parseInt(scan_line[1]) +  ((Integer)symAddr.get(ptr)).intValue();
					
					// If symtab contains the address of symbol
					if(symtab.contains(symbol)) {
						symtab.add(words[0]);
						symAddr.add(new Integer(address));
					}
					//else add -1 for the time
					else {
						symtab.add(words[0]); 
						symAddr.add(new Integer(-1));
					}
				}
				
				else {
					symtab.add(words[0]); 
					symAddr.add(new Integer(lc));
				}
			}
			
			
			//If literal is detected
			if(words[1].equalsIgnoreCase("")) {
				literals.add(words[2]);
				litAddr.add(new Integer(lc));
				
			}
			
			// Incrementing the location counter
			
			// If ORIGIN occurs
			if(words[1].matches("(ORIGIN)")) {
				String[] scan_line = words[2].split("\\+");
				String symbol = scan_line[0];
				int ptr = symtab.indexOf(symbol);
				int address = Integer.parseInt(scan_line[1]) +  ((Integer)symAddr.get(ptr)).intValue();
				lc = address;
			}
			
			// If DS occurs
			else if(words[1].equals("DS")) {
				lc += Integer.parseInt(words[2]);
			}
			
			// If Assembler directive occurs
			else if(assembler_dir.containsKey(words[1])) {
				continue;
			}
			
			// Else increment the location counter by 1
			else {
				lc += 1;
			}
			
		}
		br.close();
		
		// For generating the intermediate code
		
		br = new BufferedReader(new FileReader("code"));
		
		while((line = br.readLine()) != null) {
			words = line.split("\t");
			parse_line(words, pr);
		}
		pr.close();
		
	}
	
	public static void main(String args[]) throws Exception {
		
		BufferedReader br = new BufferedReader(new FileReader("code"));
		main_assembler ma = new main_assembler();
		ma.parse_file(br);
		ma.print_tables();
	}
}