package com.tomus.classes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DAO {
	public static void main(String[] args) {

		Team sport = new Team("Sport Club do Recife", "Recife", "Ilha do Retiro");

		Player jogador1 = new Player("Gabriel Vasconcelos", 1.90);
		Player jogador2 = new Player("Matheus Alexandre", 1.85);
		Player jogador3 = new Player("Rafael Thiery", 1.88);
		Player jogador4 = new Player("Ramon", 1.87);
		Player jogador5 = new Player("Luan Candido", 1.78);
		Player jogador6 = new Player("Cristian Rivera", 1.85);
		Player jogador7 = new Player("Ze Lucas", 1.70);
		Player jogador8 = new Player("Lucas Lima", 1.74);
		Player jogador9 = new Player("Matheusinho", 1.67);
		Player jogador10 = new Player("Derick", 1.89);
		Player jogador11 = new Player("Leo Pereira", 1.69);
		
		sport.addPlayer(jogador1);
		sport.addPlayer(jogador2);
		sport.addPlayer(jogador3);
		sport.addPlayer(jogador4);
		sport.addPlayer(jogador5);
		sport.addPlayer(jogador6);
		sport.addPlayer(jogador7);
		sport.addPlayer(jogador8);
		sport.addPlayer(jogador9);
		sport.addPlayer(jogador10);
		sport.addPlayer(jogador11);
		
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("estoquePU");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.persist(sport);
		em.getTransaction().commit();


		em.close();
		emf.close();
	}
}

