package weakReference;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class WeakReference {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Map map = null;

		/**
		 * 1 1
		 */
		map = new HashMap<>();

		/**
		 * {weakReference.City@7852e922=Ind} {}
		 */
		//map = new WeakHashMap();
		City city = new City("Mum", "Ind");
		map.put(city, city.country);

		System.out.println(map.toString());
		city = null;
		System.gc();
		System.out.println(map.toString());

	}

}
