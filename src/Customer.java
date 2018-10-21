
public class Customer {

	private int id;
	private long arrivalTime;
	private long departTime;
	private long waitTime;
	
	
	public Customer(){
		
	}
	public Customer(int id,  long arrival, long depart, long wait){
		this.id = id;
		this.arrivalTime = arrival;
		this.departTime = depart;
		this.waitTime  = wait;
	}
	
	public long getWaitTime(){
		waitTime =   this.departTime - this.arrivalTime;
		return waitTime;
	}
	
	public int getId(){
		return this.id;                                                                                                          
	}
}
