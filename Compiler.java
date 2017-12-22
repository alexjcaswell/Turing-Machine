import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;

class Compiler{
	
	//state symbol newState newSymbol move

	public static TuringMachine compile(Scanner in){
		Map<String, Map<Long, Action>> table = new HashMap<String, Map<Long, Action>>();
		int lineNumber = 0;
		while(in.hasNext()){
			String line = in.nextLine();
			lineNumber ++;
			// System.out.println("LINE: " + line);
			//	remove comments
			line = line.split("#")[0];
			// System.out.println("NC: \"" + line + "\"");
			//	parse line
			String[] bits = line.split(",");
			// System.out.println(bits.length);
			if(bits.length == 1)
				continue;
			if(bits.length != 2){
				System.out.printf("ERROR: Line %d, wrong number of arguments!\n", lineNumber);
				System.exit(-1);
			}
			String state = bits[0];
			long symbol = bits[1].charAt(0);

			line = in.nextLine();
			lineNumber ++;
			//	remove comments
			line = line.split("#")[0];
			//	parse line
			bits = line.split(",");
			// System.out.println(bits.length);
			if(bits.length == 1)
				continue;
			if(bits.length != 3){
				System.out.printf("ERROR: Line %d, wrong number of arguments!\n", lineNumber);
				System.exit(-1);
			}
			String newState = bits[0];
			long newSymbol = bits[1].charAt(0);
			String m = bits[2];
			long move = 0;
			if(m.equals("<"))
				move = -1;
			else if(m.equals(">"))
				move = 1;
			else if(m.equals("-"))
				move = 0;
			else 
				move = Long.parseLong(bits[4]);
			//	get / initialize state actions
			Map<Long, Action> stateActions = table.get(state);
			if(stateActions == null){
				stateActions = new HashMap<Long, Action>();
				table.put(state, stateActions);
			}
			//create Actions
			Action action = new Action(Action.Type.CHANGE_STATE, newState);
			action.next = new Action(Action.Type.WRITE, newSymbol);
			action.next.next = new Action(Action.Type.MOVE, move);
			if(stateActions.containsKey(symbol)){
				System.out.printf("ERROR: Line %d, duplicate action!\n", lineNumber);
				System.exit(-1);
			}
			// System.out.printf("Compiler adding action: STATE: %s READING: %d NEW_STATE: %s WRITING: %d MOVING: %d \n", state, symbol, newState, newSymbol, move);
			stateActions.put(symbol, action);
		}
		return new TuringMachine(table);
	}
}