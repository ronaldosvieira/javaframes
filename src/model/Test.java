package model;

import java.util.NoSuchElementException;

public class Test {
	public static void main(String[] args) {
		GenericFrame pessoa = new GenericFrame("Pessoa");
        GenericFrame diogo = new GenericFrame("Diogo", pessoa);

        pessoa.add("respira?", true);
		diogo.add("idade", 24);
		
		System.out.println(diogo.get("idade", Integer.class));
		System.out.println(diogo.get("respira?"));

        try {
			diogo.get("fotosNoVikings");
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
		}
	}
}
