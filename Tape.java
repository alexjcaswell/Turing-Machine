class Tape{
	public static final int TAPE_SIZE = 1024;
	public static final long STARTING_VALUE = '_';

	public Tape next, prev;
	public long[] data;

	long headIndex = 0;

	public Tape(){
		data = new long[TAPE_SIZE];
		for(int i = 0; i < data.length; i ++){
			data[i] = STARTING_VALUE;
		}
	}

	public long read(){
		return data[(int)headIndex];
	}

	public void write(long c){
		data[(int)headIndex] = c;
	}

	public Tape write(String s){
		Tape t = this;
		for(char c: s.toCharArray()){
			t.write(c);
			t = t.move(1);
		}
		t = t.move(-s.length());
		return t;
	}

	public Tape move(long d){
		headIndex += d;
		if(headIndex > TAPE_SIZE){
			if(next == null)
				next = new Tape();
			return next.move(d - TAPE_SIZE);
		}
		if(headIndex < 0){
			if(prev == null)
				prev = new Tape();
			return prev.move(d + TAPE_SIZE);
		}
		return this;
	}

	public String toString(){
		if(data[0] != STARTING_VALUE && prev != null){
			return prev.toString();
		}
		StringBuilder b = new StringBuilder();
		toString(b);
		return b.toString();
	}

	private void toString(StringBuilder b){
		int i = 0;
		while(i < TAPE_SIZE){
			if(data[i] != STARTING_VALUE){
				if(b.length() == 0)
					b.append(i + ": ");
				b.append(data[i] + " ");
			}
			i ++;
		}
		if(i == TAPE_SIZE && null != null){//If data continues beyond this tape
			next.toString(b);
		}
	}
}