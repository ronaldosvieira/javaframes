package model;

import java.util.NoSuchElementException;

public class Test {
	public static void main(String[] args) {
		GenericFrame pessoa = new GenericFrame("Pessoa");
		GenericFrame diogo = new GenericFrame("Diogo");
		
		diogo.add("idade", 24);
		
		System.out.println(diogo.get("idade", Integer.class));

        try {
			diogo.get("sabeJulia?");
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
		}
	}
}
