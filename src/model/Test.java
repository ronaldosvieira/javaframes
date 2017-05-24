package model;

import com.google.gson.Gson;
import model.constraint.ContainsConstraint;
import model.constraint.RangeConstraint;
import model.constraint.TypeConstraint;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class Test {
	public static void main(String[] args) {
		GenericFrame pessoa = new GenericFrame("Pessoa");
        InstanceFrame diogo = new InstanceFrame("Diogo", pessoa);

        KnowledgeBase.register(pessoa);
        KnowledgeBase.register(diogo);

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

        System.out.println("'diogo' isA 'diogo'? " + diogo.isA(diogo.name()));
        System.out.println("'diogo' isA 'pessoa'? " + diogo.isA(pessoa));
        System.out.println("'diogo' isA 'coelho'? " +
                diogo.isA(new GenericFrame("Coelho")));

        InstanceFrame diogo2 = new InstanceFrame(diogo);
        diogo2.set("notaDeCalculoII", 10.0f);

        System.out.println("diogo's notaDeCalculoII: " + diogo.get("notaDeCalculoII"));
        System.out.println("diogo2's notaDeCalculoII: " + diogo2.get("notaDeCalculoII"));
        System.out.println("diogo3's notaDeCalculoII: " + diogo2.clone().get("notaDeCalculoII"));

        String json = diogo.toJson();
        System.out.println(json);

        System.out.println(Frame.fromJson(json));
    }
}
