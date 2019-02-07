import java.awt.BorderLayout;
import java.util.Scanner;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;


public class mainFile {
	public static void main (String args[])
	{
		Scanner sc=new Scanner(System.in);
		System.out.println ("enter no of process:");
		int n= sc.nextInt();
		int pid[] = new int[n]; // it takes pid of process
		int at[] = new int[n];  // at means arrival time
		int bt[] = new int[n];  // bt means burst time
		int ct[] = new int[n];  // ct means complete time
		int ta[] = new int[n];  // ta means turn around time
		int wt[] = new int[n];  // wt means waiting time
		int f[] = new int[n];   // f means it is flag it checks process is completed or not
		int k[]= new int[n];    // it is also stores burst time
	    int st=0, tot=0;
	    float avgwt=0, avgta=0;
 
	    for (int i=0;i<n;i++)
	    {
	    	pid[i]= i+1;
	    	System.out.println ("enter process " +(i+1)+ " arrival time:");
	    	at[i]= sc.nextInt();
	    	System.out.println("enter process " +(i+1)+ " burst time:");
	    	bt[i]= sc.nextInt();
	    	k[i]= bt[i];
	    	f[i]= 0;
	    }
	    
	    while(true){
	    	int min=99,c=n;
	    	if (tot==n)
	    		break;
	    	
	    	for (int i=0;i<n;i++)
	    	{
	    		if ((at[i]<=st) && (f[i]==0) && (bt[i]<min))
	    		{	
	    			min=bt[i];
	    			c=i;
	    		}
	    	}
	    	
	    	if (c==n)
	    		st++;
	    	else
	    	{
	    		bt[c]--;
	    		st++;
	    		if (bt[c]==0)
	    		{
	    			ct[c]= st;
	    			f[c]=1;
	    			tot++;
	    		}
	    	}
	    }
	    
	    for(int i=0;i<n;i++)
	    {
	    	ta[i] = ct[i] - at[i];
	    	wt[i] = ta[i] - k[i];
	    	avgwt+= wt[i];
	    	avgta+= ta[i];
	    }
	    
	    System.out.println("pid  arrival  burst  complete turn waiting");
	    for(int i=0;i<n;i++)
	    {
	    	System.out.println(pid[i] +"\t"+ at[i]+"\t"+ k[i] +"\t"+ ct[i] +"\t"+ ta[i] +"\t"+ wt[i]);
	    }
	    
	    System.out.println("\naverage tat is "+ (float)(avgta/n));
	    System.out.println("average wt is "+ (float)(avgwt/n));
	    sc.close();
	    
	    // Gantt Chart
		
 		// Define task series
 		
 		final TaskSeries s1 = new TaskSeries("Processes");
 		
 		for(int i = 0;i < n;i ++){
  			if(i > 0)
  				s1.add(new Task(Integer.toString(pid[i]), new SimpleTimePeriod(ct[i-1], ct[i])));
  			else
  				s1.add(new Task(Integer.toString(pid[i]), new SimpleTimePeriod(at[i], ct[i])));
  		}
 		
 		final TaskSeriesCollection collection = new TaskSeriesCollection();
 		collection.add(s1);
 		
 		// Make a dataset
 		final IntervalCategoryDataset dataset = collection;
 		
 		// Create a chart
 		final JFreeChart chart = ChartFactory.createGanttChart(
 				"Process Chart",	//chart name
 				"Process",	//domain axis label
 				"Time",		//range axis label
 				dataset,	//data
 				true,	//Include legend
 				true,	//Tooltips
 				false	//urls
 				);
 		
 		// Create a panel
 		
 		final ChartPanel chartPanel = new ChartPanel(chart);
 		chartPanel.setPreferredSize(new java.awt.Dimension(800,800));
 		
 		// Add panel to the frame
 		JFrame frame = new JFrame();
 		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setLayout(new BorderLayout());
 		frame.setContentPane(chartPanel);
 		frame.setSize(900, 900);
 		//frame.pack();
 		frame.setVisible(true);
	}
}
