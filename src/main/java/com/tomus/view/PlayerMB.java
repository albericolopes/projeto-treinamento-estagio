package com.tomus.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.tomus.dataprovider.PlayerDataProvider;
import com.tomus.model.Player;
import com.tomus.model.Position;
import com.tomus.model.Team;

@Named("playerMB")
@ViewScoped
public class PlayerMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PlayerDataProvider playerProvider;

	private Player player;
	private List<Player> players;
	
	private Player playerToDelete;
	private String searchName;
	private Team searchTeam;
	private Position searchPosition;

	@PostConstruct
	public void init() {
		player = new Player();
		loadAllPlayers();
	}

	public void savePlayer() {
		try {
			Player existingPlayer = playerProvider.findPlayerByAttributes(player.getName(), player.getHeight(),
					player.getBirthDate());

			if (existingPlayer != null) {
				if (player.getId() == null || !existingPlayer.getId().equals(player.getId())) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Erro de Duplicidade",
							"Já existe um jogador cadastrado com o mesmo nome, altura e data de nascimento. Time: "
									+ (existingPlayer.getTeam() != null ? existingPlayer.getTeam().getName()
											: "Sem time")));
					return;
				}
			}

			if (player.getId() == null) {
				playerProvider.savePlayer(player);
				showInfo("Jogador cadastrado com sucesso!");
			} else {
				playerProvider.updatePlayer(player);
				showInfo("Jogador atualizado com sucesso!");
			}

			prepareNewPlayer();
			loadAllPlayers();

		} catch (Exception e) {
			e.printStackTrace();
			showError("Erro ao salvar jogador: " + e.getMessage());
		}
	}

	public void editPlayer(Player chosenPlayer) {
		this.player = chosenPlayer;
	}
	
	public void prepareDelete(Player selectedPlayer) {
	    this.playerToDelete = selectedPlayer;
	}

	public void deletePlayer() {
	    if (playerToDelete != null) {
	        try {
	            playerProvider.deletePlayer(playerToDelete);
	            showInfo("Jogador removido com sucesso!");
	            loadAllPlayers();
	        } catch (Exception e) {
	            showError("Erro ao remover jogador: " + e.getMessage());
	        } finally {
	            playerToDelete = null;
	        }
	    }
	}

	public void findPlayers() {
	    try {
	        List<Player> allPlayers = playerProvider.findAllPlayers();
	        List<Player> filteredPlayers = new ArrayList<>();
	        
	        for (Player searchedPlayer : allPlayers) {
	            boolean matchesName = searchName == null || searchName.trim().isEmpty() || 
	                                 searchedPlayer.getName().toLowerCase().contains(searchName.toLowerCase());
	            
	            boolean matchesTeam = searchTeam == null 
						|| (searchedPlayer.getTeam() != null && searchedPlayer.getTeam().equals(searchTeam));
	            
	            boolean matchesPosition = searchPosition == null || searchedPlayer.getPosition().equals(searchPosition);
	            
	            if (matchesName && matchesTeam && matchesPosition) {
	                filteredPlayers.add(searchedPlayer);
	            }
	        }
	        
	        players = filteredPlayers;
	        
	        if (players.isEmpty()) {
	            showInfo("Nenhum jogador encontrado com os filtros informados.");
	        } else {
	            showInfo(players.size() + " jogador(es) encontrado(s).");
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        showError("Erro ao buscar jogadores: " + e.getMessage());
	    }
	}
	
	public void clearFilters() {
		searchName = null;
		searchTeam = null;
		searchPosition = null;
		loadAllPlayers();
		showInfo("Filtros limpos. Exibindo todos os jogadores.");
	}

	public void loadAllPlayers() {
		players = playerProvider.findAllPlayers();
	}

	public void prepareNewPlayer() {
		player = new Player();
	}

	private void showInfo(String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
	}

	private void showError(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
	}

	// Getters e Setters
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public List<Position> getPositions() {
		return Arrays.asList(Position.values());
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public Team getSearchTeam() {
		return searchTeam;
	}

	public void setSearchTeam(Team searchTeam) {
		this.searchTeam = searchTeam;
	}

	public Position getSearchPosition() {
		return searchPosition;
	}

	public void setSearchPosition(Position searchPosition) {
		this.searchPosition = searchPosition;
	}

}
