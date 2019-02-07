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
	
	public static void main(String args[]) throws Exception{
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter number of processes: ");
		int n = sc.nextInt();
		int bt[] = new int[n];
		int wt[] = new int[n];
		int rem_bt[] = new int[n];
		int t = 0;
		int quantum = 10;
		
		//Input burst time
		for(int i = 0;i < n;i ++){
			System.out.println("Process" + i + " enter burst time: ");
			bt[i] = sc.nextInt();
		}
		
		// Fill rem_bt with bt
		for(int i = 0;i < n;i ++){
			rem_bt[i] = bt[i];
		}
		
		//Loop until all the processes are not finished
		while(true){
			
			boolean done = true;
			
			for(int i = 0;i < n;i ++){
				
				if(rem_bt[i] > 0){
					done = false;
					
					if(rem_bt[i] > quantum){
						
						t += quantum;
						rem_bt[i] -= quantum;
						
					}
					else{
						
						t += rem_bt[i];
						wt[i] = t - bt[i];
						rem_bt[i] = 0;
					}
				}
				
			}
			if(done == true){
				break;	//All process are finished
			}
		}
		
		System.out.println("Pid    Burst Time   Waiting time");
		for(int i = 0;i < n; i++){
			System.out.println(i + " \t " + bt[i] + " \t\t " + wt[i]);
		}
		
		
		// Gantt Chart
		
 		// Define task series
 		
 		final TaskSeries s1 = new TaskSeries("Processes");
 		
 		/*
 		 * 
 		 *Changes required
 		 *-Round Robin doesnot get wait time 
 		 *modify this 
 		
 		for(int i = 0;i < n;i ++){
  			if(i > 0)
  				s1.add(new Task(Integer.toString(i), new SimpleTimePeriod(ct[i-1], ct[i])));
  			else
  				s1.add(new Task(Integer.toString(i), new SimpleTimePeriod(at[i], ct[i])));
  		}
  		*/
 		
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
