package com.tomus.dataprovider;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;

import com.tomus.model.Team;

@ApplicationScoped
public class TeamDataProvider extends DataProvider implements Serializable {

	private static final long serialVersionUID = 1L;

	public void saveTeam(Team team) {
		try {
			beginTransaction();
			if (team.getId() == null) {
				entityManager.persist(team);
			} 
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
			e.printStackTrace();
		}
	}
	
	public void updateTeam(Team team) {
		try {
			beginTransaction();
			if (team.getId() != null) {
				entityManager.merge(team);
			}
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
			e.printStackTrace();
		}
	}

	public void deleteTeam(Team team) {
		try {
			beginTransaction();
			Team chosenTeam = entityManager.find(Team.class, team.getId());
			if (chosenTeam != null) {
				entityManager.remove(chosenTeam);
			}
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
			e.printStackTrace();
		}
	}

	public List<Team> findTeamByName(String name) {
		TypedQuery<Team> query = entityManager.createQuery("SELECT t FROM Team t WHERE LOWER(t.name) LIKE LOWER(:name)",
				Team.class);
		query.setParameter("name", '%' + name + '%');
		return query.getResultList();
	}

	public List<Team> findTeamByCity(String city) {
		TypedQuery<Team> query = entityManager.createQuery("SELECT t FROM Team t WHERE LOWER(t.city) = LOWER(:city)",
				Team.class);
		query.setParameter("city", city);
		return query.getResultList();
	}

	public Team findTeamById(Integer id) {
		try {
			return entityManager.find(Team.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Team findTeamByAttributes(String name, String city) {
		try {
			TypedQuery<Team> query = entityManager.createQuery("SELECT t FROM Team t WHERE "
					+ "LOWER(t.name) = LOWER(:name) AND " + "LOWER(t.city) = LOWER(:city)",
					Team.class);

			query.setParameter("name", name);
			query.setParameter("city", city);

			List<Team> results = query.getResultList();

			return results.isEmpty() ? null : results.get(0);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Team> findAllTeams() {
		TypedQuery<Team> query = entityManager.createQuery("SELECT t FROM Team t", Team.class);
		return query.getResultList();
	}
}