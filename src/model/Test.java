package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        diogo.addConstraint("notaDeCalculoII", new RangeConstraint(0.0f, 10.0f));

        try {
            diogo.set("notaDeCalculoII", "string-qualquer");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            diogo.set("notaDeCalculoII", -5.0f);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        diogo.set("notaDeCalculoII", 5.0f);
        System.out.println("notaDeCalculoII = " + diogo.get("notaDeCalculoII"));

        String[] apelidos = {"dioguinho", "lolito", "zigoto"};
        diogo.addConstraint("apelido", new ContainsConstraint(Arrays.asList(apelidos)));

        diogo.set("apelido", "lolito");

        try {
            diogo.set("apelido", "bonitinho");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
			diogo.get("fotosNoVikings");
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
		}
	}
}
