/*
*
*	Priority Non Preemptive
*/

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
	
	public static void main(String args[]){
		
		int n; 
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter number of processes: ");
		n = sc.nextInt();
			
		int pid[] = new int[n]; // process id
		int priority[] = new int[n];// priority
		int ar[] = new int[n];     // arrival times
		int bt[] = new int[n];     // burst or execution times
		int ct[] = new int[n];     // completion times
		int ta[] = new int[n];     // turn around times
		int wt[] = new int[n];
		int avgwt = 0, avgta = 0;
		
		for(int i = 0; i < n; i++)
		{
			System.out.println("enter process " + (i+1) + " arrival time: ");
			ar[i] = sc.nextInt();
			System.out.println("enter process " + (i+1) + " brust time: ");
			bt[i] = sc.nextInt();
			System.out.println("enter process " + (i+1) + " priority : ");
			priority[i] = sc.nextInt();
			pid[i] = i+1;
		}
		
		// Sorting according to arrival time
		int temp;
		for(int i = 0 ; i <n; i++)
		{
			for(int  j=0;  j < n-(i+1) ; j++)
			{
				if( ar[j] > ar[j+1] )
				{
					temp = ar[j];
					ar[j] = ar[j+1];
					ar[j+1] = temp;
					temp = bt[j];
					bt[j] = bt[j+1];
					bt[j+1] = temp;
					temp = pid[j];
					pid[j] = pid[j+1];
					pid[j+1] = temp;
					temp = priority[j];
					priority[j] = priority[j+1];
					priority[j+1] = temp;
					
				}
			}
		}
		
		// Sorting according to priority if arrival time is same
		for(int i = 0;i < n;i ++){
			
			for(int  j=0;  j < n-(i+1) ; j++)
			{
				if( ar[j] == ar[j+1] && priority[j] > priority[j+1] )
				{
					temp = ar[j];
					ar[j] = ar[j+1];
					ar[j+1] = temp;
					temp = bt[j];
					bt[j] = bt[j+1];
					bt[j+1] = temp;
					temp = pid[j];
					pid[j] = pid[j+1];
					pid[j+1] = temp;
					temp = priority[j];
					priority[j] = priority[j+1];
					priority[j+1] = temp;
				}
			}
			
		}
		
		// finding completion times
		for(int  i = 0 ; i < n; i++)
		{
			if( i == 0)
			{	
				ct[i] = ar[i] + bt[i];
			}
			else
			{
				if( ar[i] > ct[i-1])
				{
					ct[i] = ar[i] + bt[i];
				}
				else
					ct[i] = ct[i-1] + bt[i];
			}
			ta[i] = ct[i] - ar[i] ;          // turn around time= completion time- arrival time
			wt[i] = ta[i] - bt[i] ;          // waiting time= turn around time- burst time
			avgwt += wt[i] ;               // total waiting time
			avgta += ta[i] ;               // total turn around time
		}
		
		System.out.println("\npid  priority  arrival  brust  complete turn waiting");
		for(int  i = 0 ; i< n;  i++)
		{
			System.out.println(pid[i] + "  \t " + priority[i] + " \t " + ar[i] + "\t" + bt[i] + "\t" + ct[i] + "\t" + ta[i] + "\t"  + wt[i] ) ;
		}
		sc.close();
		System.out.println("\naverage waiting time: "+ (avgwt/n));     // printing average waiting time.
		System.out.println("average turnaround time:"+(avgta/n));    // printing average turn around time.
		
		
		// Gantt Chart
		
 		// Define task series
 		
 		final TaskSeries s1 = new TaskSeries("Processes");
 		
 		for(int i = 0;i < n;i ++){
 			if(i > 0)
 				s1.add(new Task(Integer.toString(i), new SimpleTimePeriod(ct[i-1], ct[i])));
 			else
 				s1.add(new Task(Integer.toString(i), new SimpleTimePeriod(ar[i], ct[i])));
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
 		chartPanel.setPreferredSize(new java.awt.Dimension(500,500));
 		
 		// Add panel to the frame
 		JFrame frame = new JFrame();
 		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
 		frame.setContentPane(chartPanel);
 		frame.pack();
 		frame.setVisible(true);
	}
}
