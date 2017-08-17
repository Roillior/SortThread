/**
 * This class represent the ArrayPool
 * 
 * @author Lior Maimon 
 * mmn15 , Question 2
 */
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayPool {
	private ArrayBlockingQueue<Integer[]> buffer;
	/**
	 * construct a new arrayPool with a given size
	 */
	public ArrayPool(int n){
		buffer = new ArrayBlockingQueue<Integer[]>(n);
	}
	/**
	 * method that return pointer to buffer
	 * @return pointer to the buffer
	 */
	public ArrayBlockingQueue<Integer[]> getBuffer(){
		return buffer;
	}
	/**
	 * method to initial the arrayPool
	 * @exception InterruptedException
	 * @param n - represent the size of the array the user entered
	 */
	public void initial(int n) throws InterruptedException{
		Integer[] array = new Integer[n]; //make an array in length n
		for(int i = 0; i < n ; i++){
			array[i] = ((int)(Math.random() * 100) + 1); //initial the array with random numbers
			Integer[] arr = new Integer[1]; //make an array of length 1
			arr[0] = array[i]; 
			buffer.put(arr); //put the array of length 1 in the array Pool
		}
	}
	/**
	 * method toString
	 */
	@Override
	public String toString(){
		String res = "";
		Integer[] arr;
		try{
			arr = buffer.take();
			res = res + arr[0];
			for(int i = 1; i < arr.length; i++){
				res = res + " , " + arr[i];
			}
		}catch(InterruptedException e){}
		return res;
	}
}
