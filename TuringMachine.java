import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class TuringMachine{
	
	public static void main(String[] args)throws FileNotFoundException{
		if(args.length != 2 || 
			args[0].substring(args[0].length() - 3, args[0].length()).equals(".tmc")){
			System.out.println("Useage: java TuringMachine [FileName].tmc [Input]");
			System.exit(-1);
		}
		TuringMachine machine = Compiler.compile(new Scanner(new File(args[0])));
		machine.tape = machine.tape.write(args[1]);
		// System.out.println(machine.tape);
		machine.run();
	}

	Tape tape;
	String state;
	Map<String, Map<Long, Action>> table;

	public TuringMachine(Map<String, Map<Long, Action>> table){
		tape = new Tape();
		state = "START";
		this.table = table;
	}

	public void run(){
		// for(int i = 0; i < 10; i ++){
		while(true){
			step();
		}
	}

	public void step(){
		
		Map<Long, Action> stateTable = table.get(state);
		if(stateTable == null){
			System.out.println("STATE NOT RECOGNIZED. HALTING");
			System.exit(0);
		}
		Action action = stateTable.get(tape.read());
		while(action != null){
			action.act(this);
			if(state.contains("HALT")){
				System.out.println(state);
				System.exit(0);
			}
			action = action.next;
		}
	}

	public String toString(){
		return String.format("POS: %d STATE: %s OVER: %d \n", tape.headIndex, state, tape.read());
	}
}