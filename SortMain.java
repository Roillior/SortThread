/**
 * This class represent the main method
 * 
 * @author Lior Maimon 
 * mmn15 , Question 2
 */

public class SortMain {

	public static void main(String[] args) {
		UserInput inp = new UserInput(); //get input from user
		ArrayPool arr = new ArrayPool(inp.getSize()); //make the arrayPool
		try{
			arr.initial(inp.getSize()); 
		}catch(InterruptedException e){}
		
		for(int i = 0; i < inp.getThreadNum(); i++){ //make the thread and make them start to run
			(new SortThread(arr , inp.getThreadNum())).start();
		}

	}

}
