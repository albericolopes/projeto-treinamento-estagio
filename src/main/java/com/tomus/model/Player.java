package com.tomus.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "jogador")
public class Player {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Column(name = "nome", length = 50, nullable = false)
	private String name;

	@Column(name = "altura", length = 10, nullable = false)
	private Double height;

	@Column(name = "data_nascimento", nullable = false)
	private Date birthDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "posicao")
	private Position position;

	@ManyToOne
	@JoinColumn(name = "team_id", nullable = true)
	private Team team;

	public Player() {
	}

	public Player(String name, Double height, Position position, Date birthDate, Team team) {
		this.name = name;
		this.height = height;
		this.position = position;
		this.birthDate = birthDate;
		this.team = team;
	}

	@Override
	public int hashCode() {
		return Objects.hash(birthDate, height, id, name, position, team);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return Objects.equals(birthDate, other.birthDate) && Objects.equals(height, other.height)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(position, other.position) && Objects.equals(team, other.team);
	}

	public String getName() {
		return name;
	}

	public void setName(String nome) {
		this.name = nome;
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
	
	public void setId(Integer id) {
		this.id = id;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}
