//Samantha M. Garcia



public class Page {
	
	private int pageNum; //to identify page
	private int processNum; //process which page belongs to
	private int timeLoaded; //time when page was added
	private int residency;
	private int lastRef; //time of last reference
	
	
	public Page(int page, int processNum, int time) {
		this.pageNum = page;
		this.processNum = processNum;
		this.timeLoaded = time;
	}
	
	
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getProcessNum() {
		return processNum;
	}
	public void setProcessNum(int processNum) {
		this.processNum = processNum;
	}
	public int getTimeLoaded() {
		return timeLoaded;
	}
	public void setTimeLoaded(int l) {
		this.timeLoaded = l;
	}

	public int getResidency() {
		return residency;
	}

	public void setResidency(int residency) {
		this.residency = residency;
	}


	public int getLastRef() {
		return lastRef;
	}


	public void setLastRef(int lastRef) {
		this.lastRef = lastRef;
	}
	
	
	

}
