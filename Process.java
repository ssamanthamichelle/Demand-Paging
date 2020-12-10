//Samantha M. Garcia



public class Process {
	
	private int ID;
	private double a;
	private double b;
	private double c;
	private int totalRefs; //number of references per process
	private int refs; //number of references completed
	private int w; //word referenced
	private int pageFaults; //number of page faults
	private int residency; //sum of residencies for pages
	private int evictions;
	
	
	public Process(int id, double a, double b, double c, int n)
	{
		this.ID = id;
		this.a = a;
		this.b = b;
		this.c = c;
		this.setTotalRefs(n);
	}
	
	
	public double getA() {
		return a;
	}
	public void setA(double a) {
		this.a = a;
	}
	public double getB() {
		return b;
	}
	public void setB(double b) {
		this.b = b;
	}
	
	public double getC() {
		return c;
	}
	
	public void setC(double c) {
		this.c = c;
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int iD) {
		ID = iD;
	}

	public int getTotalRefs() {
		return totalRefs;
	}

	public void setTotalRefs(int totalRefs) {
		this.totalRefs = totalRefs;
	}

	public int getRefs() {
		return refs;
	}

	public void setRefs(int refs) {
		this.refs += refs;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}


	public int getPageFaults() {
		return pageFaults;
	}


	public void setPageFaults(int pageFaults) {
		this.pageFaults += pageFaults;
	}


	public int getResidency() {
		return residency;
	}


	public void setResidency(int residency) {
		this.residency += residency;
	}


	public int getEvictions() {
		return evictions;
	}


	public void setEvictions(int evictions) {
		this.evictions += evictions;
	}
	
	

}
