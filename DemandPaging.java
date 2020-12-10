//Samantha M. Garcia
//Program simulating demand paging using FIFO, Random, and LRU page replacement algorithms.


import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class DemandPaging {
	
	public static void main(String [] args) throws Exception{
		
		File file = new File("random-numbers.txt");
		Scanner random = new Scanner(file);
		
		Random randNum = new Random();
		
		//get values for m,p,s,j,n,r
		int machineSize = Integer.parseInt(args[0]); //machine size in words
		int pageSize = Integer.parseInt(args[1]); //page size in words
		int processSize = Integer.parseInt(args[2]); //size of each process, references are to virtual addresses 0...s-1
		int jobNumber = Integer.parseInt(args[3]); //job mix, >> a,b,c
		int numRefs = Integer.parseInt(args[4]); //number of references for each process
		String replacement = args[5]; //replacement algorith FIFO, RANDOM, LRU
		
		
		ArrayList<Process> processes = new ArrayList<Process>();
		
		//to keep track of all evicted pages (pageNumber, processID)
		//HashMap<Integer, Page> evicted = new HashMap<Integer, Page>();
		
		ArrayList<Page> evictedPages = new ArrayList<Page>(); //trying something else
		
		
		//processes.add(null); //idk why i did this
		
		//create processes based on job switch, add to processes arraylist
		switch(jobNumber)
		{
			case 1: 
				Process p = new Process(1, 1, 0, 0, numRefs);
				processes.add(p);
				break;
			
			case 2:
				Process q = new Process(1, 1, 0, 0, numRefs);
				Process r = new Process(2, 1, 0, 0, numRefs);
				Process s = new Process(3, 1, 0, 0, numRefs);
				Process t = new Process(4, 1, 0, 0, numRefs);
				processes.add(q);
				processes.add(r);
				processes.add(s);
				processes.add(t);
				break;
				
			case 3:
				Process w = new Process(1, 0, 0, 0, numRefs);
				Process x = new Process(2, 0, 0, 0, numRefs);
				Process y = new Process(3, 0, 0, 0, numRefs);
				Process z = new Process(4, 0, 0, 0, numRefs);
				processes.add(w);
				processes.add(x);
				processes.add(y);
				processes.add(z);
				break;
				
			case 4:
				Process e = new Process(1, 0.75, 0.25, 0, numRefs);
				Process f = new Process(2, 0.75, 0, 0.25, numRefs);
				Process g = new Process(3, 0.75, 0.125, 0.125, numRefs);
				Process h = new Process(4, 0.5, 0.125, 0.125, numRefs);
				processes.add(e);
				processes.add(f);
				processes.add(g);
				processes.add(h);
				break;
		}
		
		
		//frame table
		Page[] frameTable = new Page[machineSize/pageSize];
		
		for (int i = 0; i < frameTable.length; i++)
			frameTable[i] = null;
		
		int faults = 0; //to keep track of number of faults
		
		//int totalTime = (processes.size() -1) * numRefs;
		int totalTime = processes.size() * numRefs;
		
		//System.out.println("total time = process.size * numRefs " + totalTime);
		
		int time = 0;
		
		int p = 3; //pointer to processes arraylist
		

		//System.out.println("number of processes * num refs" + totalTime);
		
		//remember that processes arraylist is (null, process1, ...)
		//System.out.println(processes.size());
		/*
		for (Process pp : processes)
		{
			if (!(pp == null))
			{
				System.out.println(pp.getID());
			}
		}
		
		*/
		
		while (time < totalTime)
		{
			p++;
			p = p % processes.size(); // idk if this is right
			//System.out.println(p);
			
			//figure out a better way to do this
			Process currProcess = processes.get(p);
			
			int wordReferenced = 0;

			//three refs for currProcess
			for (int q = 0; q<3 ; q++)
			{
				time += 1;
				
				//is first reference ((111*k)%s)
				if (currProcess.getRefs() == 0)
				{
					wordReferenced = (111 * currProcess.getID()) % processSize;
					currProcess.setW(wordReferenced);
					currProcess.setRefs(1);
				}
				
				else //not first reference
				{
					currProcess.setRefs(1);
					int ycase = 0;
					int r = random.nextInt();
					double y = r / (Integer.MAX_VALUE + 1d);
					
					//System.out.println("random number used " + r);
					
					if (y < currProcess.getA())
					{
						//System.out.println("w + 1 + process size" + (currProcess.getW() + 1 + processSize));
						
						wordReferenced = (currProcess.getW() + 1 + processSize) % processSize;
						currProcess.setW(wordReferenced);
						
						ycase = 1;
					}
					
					else if (y < (currProcess.getA() + currProcess.getB()))
					{
						wordReferenced = (currProcess.getW() -5 + processSize) % processSize;
						currProcess.setW(wordReferenced);
						ycase = 2;
					}
						
					else if (y < (currProcess.getA() + currProcess.getB() + currProcess.getC()))
					{
						wordReferenced = (currProcess.getW() + 4 + processSize) % processSize;
						currProcess.setW(wordReferenced);
						ycase = 3;
					}
					
					else //y >= A+B+C
					{
						//idk what to do here	
						//System.out.println("random reference ...");
					}
					
					//wordReferenced = ((currProcess.getW()+ 1 + processSize) % processSize); why did i do this???
					
					//System.out.println("\t\t\t\t W " + currProcess.getW() + " processSize " + processSize);
					//System.out.println("\t\t\t\t Y value " + y + "  y case " + ycase);
					
					currProcess.setRefs(1);
				}
				
				
				//System.out.println(currProcess.getID() + " referenced word " + wordReferenced + " at time " + time);
				
				//currProcess.setW(wordReferenced);
				
				
				int pageReferenced = wordReferenced/pageSize;
				
				
				//System.out.println("\t\t\t\t word referenced " + wordReferenced);
				//System.out.println("\t\t\t\t page referenced " + pageReferenced);
				
				
				//check if page is in frameTable
				
				boolean fault = true;
				
				int tempCount = 0;
				for (Page pp : frameTable)
				{
					if ((pp != null) && pp.getPageNum() == pageReferenced && pp.getProcessNum() == currProcess.getID())
					{
						fault = false;
						//System.out.println("hit in frame " + tempCount);
						tempCount += 1;
					}
				}
				
				if (fault)
				{
					currProcess.setPageFaults(1);
					//System.out.println("FAULT");
					faults += 1;
					
					//check if frame table has an empty frame
					boolean full = true;
					int maxEmpty = 0; //max index which is empty
					
					for (int i = 0; i < frameTable.length; i++)
					{
						//if a frame is empty
						if (frameTable[i] == null)
						{
							full = false;
							maxEmpty = i;
						}
					}
					
					Page toLoad = null;
					
					//tried another thing
					for (Page prevEvicted : evictedPages)
					{
						if (prevEvicted != null && prevEvicted.getPageNum() == pageReferenced && prevEvicted.getProcessNum() == currProcess.getID())
						{
							toLoad = prevEvicted;
						}
					}
					
					//was previously evicted, so remove from evicted
					if (toLoad != null)
					{
						evictedPages.remove(toLoad);
					}
					
					else
					{
						toLoad = new Page(pageReferenced, currProcess.getID(), time);
					}
					//end
					
					
					/* // add this back
					
					//if page was previously evicted... look for it in evicted hashmap
					if (evicted.containsKey(Integer.valueOf(pageReferenced)))
					{
						toLoad = evicted.get(Integer.valueOf(pageReferenced));
						evicted.remove(toLoad.getPageNum());
					}
					
					else //create a new page
						toLoad = new Page(pageReferenced, currProcess.getID(), time);
					
					*/ //add this back, end
/////////////////////////////////////////////////////// loading the page
					
					if (!full) //frameTable is not full
						frameTable[maxEmpty] = toLoad;
					
					
					else //frameTable is full
					{
						int evictedIndex = evict(frameTable, replacement, time, randNum);
						Page evictedPage = frameTable[evictedIndex];
						Process evictedProcess = processes.get(evictedPage.getProcessNum() - 1);
						evictedProcess.setResidency(time - evictedPage.getTimeLoaded());
						evictedProcess.setEvictions(1);
						//evicted.put(Integer.valueOf(evictedPage.getPageNum()), evictedPage); add this back
						evictedPages.add(evictedPage);
						frameTable[evictedIndex] = null;
						frameTable[evictedIndex] = toLoad;
						
						//System.out.println("evicted page " + evictedPage.getPageNum() + " of " + evictedProcess.getID() + " from " + evictedIndex);
						//System.out.println("replacing w page " + toLoad.getPageNum() + " of " + toLoad.getProcessNum() + " in " + evictedIndex);
					}
					
					toLoad.setLastRef(time);
					
				}//if fault
				
				else //no fault, page was found in frame table
				{
					//currProcess.setW(pageReferenced);
					
					for (Page pp : frameTable)
					{
						if (pp != null && pp.getPageNum() == pageReferenced)
						{
							pp.setLastRef(time);
							currProcess.setRefs(1);
						}
					}
				
					//System.out.println("\t\t\t\t hit.... page referenced " + pageReferenced);
				}
				
				
				
				//System.out.println("-------------------------------------------------------------");			
			}//for each reference
			
			
			
			
		}//while time < totalTime
		

		
		System.out.println("The machine size is " + machineSize + ".");
		System.out.println("The page size is " + pageSize + ".");
		System.out.println("The process size is "+ processSize + ".");
		System.out.println("The job mix number is " + jobNumber + ".");
		System.out.println("The number of references per process is " + numRefs + ".");
		System.out.println("The replacement algorithm is " + replacement + ".");
		
		System.out.println();
		
		
		/////////////////////////////////////// finishing stats
		int pFaults;
		double avgResidency;
		
		int sumResidency = 0;
		int sumFaults = 0;
		int sumEvictions = 0;
		
		for (Process pp : processes)
		{	
			if (pp.getEvictions() == 0)
			{
				System.out.println("Process " + pp.getID() + " had " + pp.getPageFaults() + " faults.");
				System.out.println("\t With no evictions, its average residency is undefined.");
			}
				
			
			else
			{
				avgResidency = pp.getResidency()/pp.getEvictions(); ///fix this
				System.out.println("Process " + pp.getID() + " had " + pp.getPageFaults() + " faults and " + avgResidency + " average residency.");
			}
			
			sumFaults += pp.getPageFaults();
			sumEvictions += pp.getEvictions();
			sumResidency += pp.getResidency();
		}//each process
		
		System.out.println();
		
		double totalAvgResidency;
		
		if (sumEvictions == 0)
		{
			System.out.println("The total number of faults is " + sumFaults);
			System.out.println("\t With no evictions, the overall average residency is undefined.");
			
		}
		else
		{
			totalAvgResidency = sumResidency/sumEvictions;
			System.out.println("The total number of faults is " + sumFaults + " and the overall average residency is " + totalAvgResidency);
		}
		
		random.close();
		
	}//main
	
	
	public static int evict(Page[] frameTable, String algo, int time, Random randNum) {
		
		int index = 0;
		
		//fifo, remove page with earliest loaded time
		if (algo.equals("fifo"))
		{
			int minTime = Integer.MAX_VALUE;
			
			for (int i = 0; i < frameTable.length; i++)
			{
				if (frameTable[i].getTimeLoaded() < minTime)
				{
					minTime = frameTable[i].getTimeLoaded();
					index = i;
				}
			}
		}
		
		else if (algo.equals("random"))
		{
			index = randNum.nextInt(frameTable.length);
			
			while (frameTable[index] == null)
			{
				index = randNum.nextInt(frameTable.length);
			}
		}
		
		else if (algo.equals("lru"))
		{
			int last = time;
			
			for (int i = 0; i < frameTable.length; i++)
			{
				if (frameTable[i].getLastRef() < last)
				{
					last = frameTable[i].getLastRef();
					index = i;
				}
			}
		}
		
		
	
		return index;
		
	}//evict
	
	
	
	
	
	
	
} //class DemandPaging
