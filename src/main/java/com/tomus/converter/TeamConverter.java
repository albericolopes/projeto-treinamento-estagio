package com.tomus.converter;

import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.tomus.dataprovider.TeamDataProvider;
import com.tomus.model.Team;


@FacesConverter(value = "teamConverter", managed = true)
public class TeamConverter implements Converter {

	@Override
	public Team getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.trim().isEmpty() || !value.matches("\\d+")) {
			return null;
		}

		try {
			TeamDataProvider teamProvider = CDI.current().select(TeamDataProvider.class).get();
			Integer teamId = Integer.valueOf(value);
			return teamProvider.findTeamById(teamId);

		} catch (NumberFormatException e) {
			System.err.println("Erro de formatação de número no TeamConverter para o valor: " + value);
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null || value instanceof String) {
			return "";
		}

		if (value instanceof Team) {
			Team team = (Team) value;
			return team.getId() != null ? team.getId().toString() : "";
		}

		return "";
	}
}