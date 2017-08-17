/**
 * This class represent the sort thread
 * extends Thread
 * 
 * @author Lior Maimon 
 * mmn15 , Question 2
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SortThread extends Thread {
	static Lock lock = new ReentrantLock();
	static Condition cond = lock.newCondition();
	private ArrayPool arrayPool;
	static int counter = 0;
	private final int totalThreads;
	/**
	 * construct a new sort Thread
	 */
	public SortThread(ArrayPool arr , int totalT ){
		arrayPool = arr;
		totalThreads = totalT;
	}
	/**
	 * Method run 
	 */
	@Override
	public void run(){
		Integer[] arr;
		Integer[] arr1;
		while(true){
			try{
				lock.lock(); //enter critical section
					while(arrayPool.getBuffer().size() < 2){
						counter++;
						if(counter == totalThreads){
							printResult();
						}
						cond.await();
						counter--;
					}
						arr = arrayPool.getBuffer().take();
						arr1 = arrayPool.getBuffer().take();
						lock.unlock(); //out of critical section
						Integer[] mix = new Integer[(arr.length + arr1.length)];
						
						mergeSort(mix, arr, arr1);
						
						arrayPool.getBuffer().put(mix);
						lock.lock(); //enter critical section
						if(counter > 0){
							cond.signalAll();
						}
						lock.unlock(); //out of critical section
			}catch(InterruptedException e){}
		}
	}
	//private method print result - print the result array
	private void printResult() {
		System.out.println("The Sorted array is:\n" + arrayPool);
		
	}
	//private method mergeSort - make regular merge sort
	private void mergeSort(Integer[] res, Integer[] arr, Integer[] arr1){
		int arrPos = 0;
		int arr1Pos = 0;
		int arrSize = arr.length;
		int arr1Size = arr1.length;
		
		for(int i = 0; i < res.length ; i++){
			if(arrPos >= arrSize){
				res[i] = arr1[arr1Pos];
				arr1Pos++;	
			}
			else if(arr1Pos >= arr1Size){
				res[i] = arr[arrPos];
				arrPos++;
			}
			else if(arr[arrPos] <= arr1[arr1Pos]){
				res[i] = arr[arrPos];
				arrPos++;
			}
			else{ //arr1[arr1Pos] < arr[arrPos]
				res[i] = arr1[arr1Pos];
				arr1Pos++;
			}
		}
		
	}
}
