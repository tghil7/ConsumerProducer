import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;

public class ConsumerProducer {
private static Buffer buffer = new Buffer();
static int original_number = 21;



public static void main (String [] args){
	
	//Initialize the linked list with elements.
	for (int i = 1; i <21; i++){
		buffer.queue.add(new Customer (i+1, System.nanoTime(), 0, 0));
	}
	//Create a thread pool with two threads
	ExecutorService executor  = Executors.newFixedThreadPool(10);
	executor.execute(new ProducerTask());
	executor.execute(new ConsumerTask());
	executor.shutdown();
}

private static class ProducerTask implements Runnable{
	public void run(){
		try {
			
			while (true){
				System.out.println("Client " + (original_number += 1) + " was added.");
				buffer.write( new Customer(original_number,System.nanoTime(), 0, 0));
				//Put the thread into sleep
				Thread.sleep((int)(Math.random() * 10000));
			}
		}
		catch(InterruptedException ex){
			ex.printStackTrace();
		}
	}
}

//A task for reading and deleting an int from the buffer
private static class ConsumerTask implements Runnable{
	public void run() {
		try {
			while (true){
				System.out.println("\t\t\tCashier processed " + buffer.read());
				//Put the thread into sleep
				Thread.sleep((int) (Math.random() * 10000));
			     }
		    }
		catch(InterruptedException ex){
			ex.printStackTrace();
		}
	}
}




//An inner class for buffer
 private static class Buffer{
	 private static final int CAPACITY = 30; //buffer size
	 public LinkedList <Customer> queue = new LinkedList<Customer>();
	
	 //Create  a new lock
	 private static Lock lock = new ReentrantLock();
	 
	 //Create two conditions
	 private static Condition notEmpty = lock.newCondition();
	 private static Condition notFull =  lock.newCondition();
	
	 public void write (Customer value){
		 lock.lock();
		 try {
			 while (queue.size() == CAPACITY){
				 System.out.println("Wait for notFull condition");
				 notFull.await();
			 }
			 
			 queue.offer(value);
			 notEmpty.signal();
			 System.out.println("The queue size is now " + queue.size() + " after a customer joined the line");
		 }
		 
		 catch(InterruptedException ex){
			 ex.printStackTrace();
		 }
		 finally{
			 lock.unlock();
		 }
	 }
	 
	 public int read() {
		 lock.lock();
		 Customer customerRemoved = new Customer();
		
		 try {
			 while(queue.isEmpty()){
				 System.out.println("\t\t\tWait for notEmpty condition");
				 notEmpty.await();
			 	}
			 
			 
			 notFull.signal();
			 customerRemoved =  queue.remove();
			System.out.println("The queue size is now " + queue.size() + " after checking out a customer");
			
			
			
		 }
		 catch(InterruptedException ex){
			 ex.printStackTrace();
		 }
		 finally{
			 lock.unlock();	
			 
		 }
		
		 return customerRemoved.getId();
	 }
 }
}
