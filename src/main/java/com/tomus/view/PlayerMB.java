package com.tomus.view;

import java.io.Serializable;
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

@Named("playerMB")
@ViewScoped
public class PlayerMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PlayerDataProvider playerProvider;

	private Player player;
	private List<Player> players;

	private String searchName;
	private String searchTeam;
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

	public void deletePlayer(Player chosenPlayer) {
		try {
			playerProvider.deletePlayer(chosenPlayer);
			showInfo("Jogador removido com sucesso.");
			loadAllPlayers();
		} catch (Exception e) {
			showError("Erro ao remover jogador: " + e.getMessage());
		}
	}

	public void findPlayerByName() {
		players = playerProvider.findPlayerByName(searchName);
		if (players == null || players.isEmpty()) {
			showInfo("Nenhum jogador encontrado com o nome informado.");
		}
	}

	public void findPlayersByTeam() {
		players = playerProvider.findPlayersByTeam(searchTeam);
		if (players == null || players.isEmpty()) {
			showInfo("Nenhum jogador encontrado para o time informado.");
		}
	}

	public void findPlayersByPosition() {
		if (searchPosition == null) {
			showInfo("Selecione uma posição para buscar.");
			return;
		}

		players = playerProvider.findPlayersByPosition(searchPosition);
		if (players == null || players.isEmpty()) {
			showInfo("Nenhum jogador encontrado para a posição informada.");
		}
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

	public String getSearchTeam() {
		return searchTeam;
	}

	public void setSearchTeam(String searchTeam) {
		this.searchTeam = searchTeam;
	}

	public Position getSearchPosition() {
		return searchPosition;
	}

	public void setSearchPosition(Position searchPosition) {
		this.searchPosition = searchPosition;
	}

}
