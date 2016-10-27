package model;

import java.util.NoSuchElementException;

public class Test {
	public static void main(String[] args) {
		GenericFrame diogo = new GenericFrame();
		
		diogo.add("nome", "Diogo");
		
		System.out.println(diogo.get("nome", String.class));
		
		try {
			diogo.get("sabeJulia?");
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
		}
	}
}
