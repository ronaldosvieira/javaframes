package model;

import java.util.NoSuchElementException;

public class Test {
	public static void main(String[] args) {
		GenericFrame pessoa = new GenericFrame("Pessoa");
        GenericFrame diogo = new GenericFrame("Diogo", pessoa);

        pessoa.set("respira?", true);
        System.out.println("respira? = " + diogo.get("respira?"));

        diogo.ifAdded("idade", o -> System.out.println("Já tem " + o + "? Tem carinha de novinho..."));

        diogo.set("idade", 24);
		System.out.println("idade = " + diogo.get("idade", Integer.class));

        try {
			diogo.get("fotosNoVikings");
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
		}
	}
}
