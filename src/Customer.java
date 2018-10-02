
public class Customer {

	private int id;
	private long arrivalTime;
	private long departTime;
	private long waitTime;
	
	public long getWaitTime(){
		waitTime =   this.departTime - this.arrivalTime;
		return waitTime;
	}
}
