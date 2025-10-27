package com.tomus.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.tomus.dataprovider.TeamDataProvider;
import com.tomus.model.Team;

@Named("teamMB")
@ViewScoped
public class TeamMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TeamDataProvider teamProvider;

	private Team team;
	private List<Team> teams;
	
	private Team teamToDelete;
	private String searchName;
	private String searchCity;

	@PostConstruct
	public void init() {
		team = new Team();
		loadAllTeams();
	}

	public void saveTeam() {
		try {
			Team existingTeam = teamProvider.findTeamByAttributes(team.getName(), team.getCity());

			if (existingTeam != null) {
				if (team.getId() == null || !existingTeam.getId().equals(team.getId())) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Erro de Duplicidade", "Já existe um time cadastrado com o mesmo nome e cidade."));
					return;
				}
			}

			if (team.getId() == null) {
				teamProvider.saveTeam(team);
				showInfo("Time cadastrado com sucesso!");
			} else {
				teamProvider.updateTeam(team);
				showInfo("Time atualizado com sucesso!");
			}

			loadAllTeams();
			team = new Team();

		} catch (Exception e) {
			showError("Erro ao salvar time: " + e.getMessage());
		}
	}

	public void editTeam(Team chosenTeam) {
		this.team = chosenTeam;
	}

	public void deleteTeam() {
        if (teamToDelete != null) {
            try {
                teamProvider.deleteTeam(teamToDelete);
                showInfo("Time removido com sucesso!");
                loadAllTeams();
            } catch (Exception e) {
                showError("Erro ao remover time: " + e.getMessage());
            } finally {
                teamToDelete = null;
            }
        } else {
            showError("Erro: Nenhum time selecionado para exclusão.");
        }
    }
	
	public void findTeams() {
		try {
			List<Team> allTeams = teamProvider.findAllTeams();
			List<Team> filteredTeams = new ArrayList<>();
			
			for (Team searchedTeam : allTeams) {
				boolean matchesName = searchName == null || searchName.trim().isEmpty() 
						|| searchedTeam.getName().toLowerCase().contains(searchName.toLowerCase());
				
				boolean matchesCity = searchCity == null || searchCity.trim().isEmpty() 
						|| searchedTeam.getCity().toLowerCase().contains(searchCity.toLowerCase());
				
				if (matchesName && matchesCity) {
					filteredTeams.add(searchedTeam);
				}
			}
			
			teams = filteredTeams;
			
			if (teams.isEmpty()) {
				showInfo("Nenhum time encontrado com os filtros informados.");
			} else {
				showInfo(teams.size() + " time(s) encontrado(s).");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			showError("Erro ao buscar times: " + e.getMessage());
		}
	}
	
	public void clearFilters() {
		searchName = null;
		searchCity = null;
		loadAllTeams();
		showInfo("Filtros limpos. Exibindo todos os times.");
	}

	public void findTeamByName() {
		teams = teamProvider.findTeamByName(searchName);
		if (teams == null || teams.isEmpty()) {
			showInfo("Nenhum time encontrado com o nome informado.");
		}
	}

	public void findTeamByCity() {
		teams = teamProvider.findTeamByCity(searchCity);
		if (teams == null || teams.isEmpty()) {
			showInfo("Nenhum time encontrado na cidade informada.");
		}
	}

	public void loadAllTeams() {
		teams = teamProvider.findAllTeams();
	}

	public void prepareNewTeam() {
		team = new Team();
	}

	private void showInfo(String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
	}

	private void showError(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public List<Team> getTeams() {
		if (teams == null) {
			loadAllTeams();
		}
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getSearchCity() {
		return searchCity;
	}

	public void setSearchCity(String searchCity) {
		this.searchCity = searchCity;
	}

	public Team getTeamToDelete() {
		return teamToDelete;
	}

	public void setTeamToDelete(Team teamToDelete) {
		this.teamToDelete = teamToDelete;
	}
	
	
}
