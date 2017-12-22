class Action{

	enum Type{
		WRITE,
		MOVE,
		CHANGE_STATE
	}
	
	Action next;

	Type type;
	long data1;
	String data2;

	public Action(Type type, long data1){
		this.type = type;
		this.data1 = data1;
		assert(type == Type.WRITE || type == Type.MOVE);
	}

	public Action(Type type, String data2){
		this.type = type;
		this.data2 = data2;
		assert(type == Type.CHANGE_STATE);
	}

	public void act(TuringMachine t){
		switch (type){
			case WRITE:
			t.tape.write(data1);
			break;
			case MOVE:
			t.tape.move(data1);
			break;
			case CHANGE_STATE:
			t.state = data2;
			break;
		}
	}
}