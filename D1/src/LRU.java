import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class LRU {
	public static void leastRecentlyUsed(ArrayList<Integer> pages, int capacity) {
		
		LinkedList<Integer> queue = new LinkedList<Integer>();
		HashMap<Integer, Integer> leastRecentIndex = new HashMap<Integer, Integer>();
		int page_faults = 0, iterator = 0;
		for(int i : pages) {
			if( queue.size() < capacity ) {
				if(! queue.contains(i)) {
					queue.add(i);
					page_faults++;
				}
				leastRecentIndex.put(i, iterator);
			}
			else {
				if(! queue.contains(i)) {
					// Remove the least recently used page from the set
					int lru = Integer.MAX_VALUE, val = Integer.MIN_VALUE;
					for(int page : queue) {
						if( leastRecentIndex.get(page) < lru) {
							lru = leastRecentIndex.get(page);
							val = page;
						}
					}
					queue.remove(val);
					queue.add(i);
					page_faults++;
				}
				leastRecentIndex.put(i, iterator);
			}
			iterator ++;
			
			// For debugging print contents of queue
			System.out.println("Elements of queue for iteration " + iterator);
			for(int element : queue) {
				System.out.println(element + " ");
			}
		}
		System.out.println("Page faults: " + page_faults);
	}
	
	public static void main(String args[]) {
		int[] elements = {1, 2, 3, 3, 2, 0, 7, 7, 4, 3, 4, 6, 3, 5};
		ArrayList<Integer> pages = new ArrayList<Integer>();
		for(int i = 0; i < elements.length;i ++) {
			pages.add(elements[i]);
		}
		int capacity = 4;
		leastRecentlyUsed(pages, capacity);
	}
}
