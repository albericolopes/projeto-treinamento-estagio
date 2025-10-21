package com.tomus.dataprovider;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;

import com.tomus.model.Player;
import com.tomus.model.Position;

@ApplicationScoped
public class PlayerDataProvider extends DataProvider implements Serializable {

	private static final long serialVersionUID = 1L;

	public void savePlayer(Player player) {
		try {
			beginTransaction();
			if (player.getId() == null) {
				entityManager.persist(player);
			}
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
			e.printStackTrace();
		}
	}

	public void updatePlayer(Player player) {
		try {
			beginTransaction();
			if (player.getId() != null) {
				entityManager.merge(player);
			}
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
			e.printStackTrace();
		}
	}

	public void deletePlayer(Player player) {
		try {
			beginTransaction();
			Player chosenPlayer = entityManager.find(Player.class, player.getId());
			if (chosenPlayer != null) {
				entityManager.remove(chosenPlayer);
			}
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
			e.printStackTrace();
		}
	}

	public Player findPlayerById(Integer id) {
		try {
			return entityManager.find(Player.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Player> findPlayerByName(String name) {
		TypedQuery<Player> query = entityManager
				.createQuery("SELECT p FROM Player p WHERE LOWER(p.name) LIKE LOWER(:name)", Player.class);
		query.setParameter("name", '%' + name + '%');
		return query.getResultList();
	}

	public List<Player> findPlayersByTeam(String teamName) {
		TypedQuery<Player> query = entityManager
				.createQuery("SELECT p FROM Player p WHERE LOWER(p.team.name) LIKE LOWER(:teamName)", Player.class);
		query.setParameter("teamName", '%' + teamName + '%');
		return query.getResultList();
	}

	public List<Player> findPlayersByPosition(Position position) {
		TypedQuery<Player> query = entityManager.createQuery("SELECT p FROM Player p WHERE p.position = :position",
				Player.class);
		query.setParameter("position", position);
		return query.getResultList();
	}

	public Player findPlayerByAttributes(String name, Double height, Date birthDate) {
		try {
			TypedQuery<Player> query = entityManager.createQuery("SELECT p FROM Player p WHERE "
					+ "LOWER(p.name) = LOWER(:name) AND " + "p.height = :height AND " + "p.birthDate = :birthDate",
					Player.class);

			query.setParameter("name", name);
			query.setParameter("height", height);
			query.setParameter("birthDate", birthDate);

			List<Player> results = query.getResultList();

			return results.isEmpty() ? null : results.get(0);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Player> findAllPlayers() {
		TypedQuery<Player> query = entityManager.createQuery("SELECT p FROM Player p", Player.class);
		return query.getResultList();
	}
}
