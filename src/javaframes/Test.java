package javaframes;

import java.util.NoSuchElementException;

public class Test {
	public static void main(String[] args) {
		GenericFrame diogo = new GenericFrame();
		
		diogo.setProperty("nome", "Diogo");
		
		System.out.println(diogo.getProperty("nome", String.class));
		
		try {
			diogo.getProperty("sabeJulia?");
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
		}
	}
}
