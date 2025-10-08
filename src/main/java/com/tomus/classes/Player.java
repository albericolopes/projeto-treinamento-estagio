package com.tomus.classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Jogador")
public class Player {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Column(name = "Nome", length = 50, nullable = false)
	private String nome;

	@Column(name = "Altura", length = 10, nullable = false)
	private Double height;

	@ManyToOne
	@JoinColumn(name = "team_id", nullable = true)
	private Team team;

	public Player() {
	}

	public Player(String nome, Double height) {
		this.nome = nome;
		this.height = height;
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Integer getId() {
		return id;
	}

}
