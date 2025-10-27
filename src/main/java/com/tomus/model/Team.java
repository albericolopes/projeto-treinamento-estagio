package com.tomus.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Time")
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Column(name = "nome", length = 50, nullable = false)
	private String name;

	@Column(name = "cidade", length = 50, nullable = false)
	private String city;

	@Column(name = "estadio", length = 50, nullable = true)
	private String stadium;

	@OneToMany(mappedBy = "team")
	private List<Player> players = new ArrayList<>();

	public Team() {
	}

	public Team(String name, String city, String stadium) {
		this.name = name;
		this.city = city;
		this.stadium = stadium;
	}

	public void addPlayer(Player player) {
		players.add(player);
		player.setTeam(this);
	}
	
	@Override  
    public int hashCode() {  
        return (id == null) ? 0 : id;
    }  
  
    @Override  
    public boolean equals(Object obj) {  
        if (obj == null)  return false;  
        if (obj instanceof Team){
            return ((Team)obj).getId().equals(this.id);  
        }
        return false;  
    } 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStadium() {
		return stadium;
	}

	public void setStadium(String stadium) {
		this.stadium = stadium;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	

}
