package application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Test {

	public static void main(String[] args) {
		List<String> imena = new ArrayList<>();
		List<String> jedinstvanaImena = new ArrayList<String>();
		imena.add("ana");
		imena.add("ana");
		imena.add("bla");
		
		jedinstvanaImena = imena.stream().distinct().collect(Collectors.toList());
		
		for(String s: imena) {
			System.out.println(s);
		}
		System.out.println("aaaaaaaaaa");
		
		for(String s: jedinstvanaImena) {
			System.out.println(s);
		}

	}

}
