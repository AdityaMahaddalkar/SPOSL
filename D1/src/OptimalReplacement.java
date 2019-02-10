import java.util.ArrayList;
import java.util.LinkedList;

public class OptimalReplacement {
	
	public static void optimalReplacement(ArrayList<Integer> pages, int capacity) {
		
		LinkedList<Integer> queue = new LinkedList<Integer>();
		int iteration = 0, page_fault = 0;
		
		for(int i = 0;i < pages.size();i ++) {
			
			if(queue.size() < capacity) {
				
				if(!queue.contains(pages.get(i))) {
					queue.add(pages.get(i));
					page_fault ++;
				}
			}
			else {
				if(!queue.contains(pages.get(i))) {
					
					//Find the page that will be accessed at the largest duration of time in the future
					int farthest_page_index = 0;
					int time = get_future_time(pages, i, queue.get(farthest_page_index)), temp_time;
					for(int index = 0;index < queue.size(); index ++) {
						temp_time = get_future_time(pages, i, queue.get(index));
						if(temp_time == -1) {
							time = temp_time;
							farthest_page_index = index;
						}
						else if(time != -1 && temp_time > time) {
							time = temp_time;
							farthest_page_index = index;
						}
					}
					queue.remove(farthest_page_index);
					queue.add(pages.get(i));
					page_fault ++;
				}
			}
			
			//For debugging
			System.out.println("Contents of queue at iteration " + iteration);
			for(int page: queue) {
				System.out.println(page);
			}
			iteration ++;
		}
		
		System.out.println("Page faults: " + page_fault);
	}

	private static int get_future_time(ArrayList<Integer> pages, int current_page_index, int farthest_page) {
		
		for(int i = current_page_index;i < pages.size();i ++) {
			if(pages.get(i) == farthest_page) {
				return i - current_page_index;
			}
		}
		
		return -1;
	}
	
	public static void main(String args[]) {
		ArrayList<Integer> pages = new ArrayList<Integer>();
		int [] elements = {2, 3, 4, 2, 1, 3, 7, 5, 4, 3};
		for(int i = 0;i < elements.length;i ++) {
			pages.add(elements[i]);
		}
		int capacity = 3;
		optimalReplacement(pages, capacity);
	}
}
