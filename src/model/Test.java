package model;

import java.util.NoSuchElementException;

public class Test {
	public static void main(String[] args) {
		GenericFrame pessoa = new GenericFrame("Pessoa");
        InstanceFrame diogo = new InstanceFrame("Diogo", pessoa);

        pessoa.set("respira?", true);

        diogo.ifAdded("idade", o -> System.out.println("Já tem " + o + "? Tem carinha de novinho..."));
        diogo.set("idade", 24);

        diogo.ifNeeded("estaNaRural?", () -> "Provavelmente não");

		System.out.println("respira? = " + diogo.get("respira?"));
		System.out.println("idade = " + diogo.get("idade", Integer.class));
        System.out.println("estaNaRural? = " + diogo.get("estaNaRural?"));

        diogo.set("estaNaRural?", true);

        System.out.println("estaNaRural? = " + diogo.get("estaNaRural?"));

        try {
			diogo.get("fotosNoVikings");
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
		}
	}
}
