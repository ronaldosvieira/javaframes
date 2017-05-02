package model;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Test {
	public static void main(String[] args) {
		GenericFrame pessoa = new GenericFrame("Pessoa");
        InstanceFrame diogo = new InstanceFrame("Diogo", pessoa);

        pessoa.set("respira?", true);
        System.out.println("respira? = " + diogo.get("respira?"));

        diogo.ifAdded("idade", o -> System.out.println("Já tem " + o + "? Tem carinha de novinho..."));
        diogo.set("idade", 24);

        System.out.println("idade = " + diogo.get("idade", Integer.class));

        diogo.ifNeeded("estaNaRural?", () -> "Provavelmente não");
        System.out.println("estaNaRural? = " + diogo.get("estaNaRural?"));

        diogo.set("estaNaRural?", true);

        System.out.println("estaNaRural? = " + diogo.get("estaNaRural?"));

        diogo.addConstraint("notaDeCalculoII", new TypeConstraint(Number.class));

        try {
            diogo.set("notaDeCalculoII", "string");
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

        diogo.set("notaDeCalculoII", 5.0f);
        System.out.println("notaDeCalculoII = " + diogo.get("notaDeCalculoII"));

        try {
			diogo.get("fotosNoVikings");
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
		}
	}
}
