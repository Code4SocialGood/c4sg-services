package org.c4sg.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.c4sg.dto.HeroDTO;
import org.c4sg.entity.Application;
import org.c4sg.entity.Badge;
import org.c4sg.entity.User;
import org.c4sg.service.BadgeService;
import org.c4sg.service.SkillService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BadgeMapper extends ModelMapper {

	@Autowired
	private SkillService skillService;
	
	@Autowired
	private BadgeService badgeService;

	public List<HeroDTO> getHeroDTOs(List<Object[]> heroes) {	
		List<HeroDTO> heroList = new ArrayList<>();
		Iterator<Object[]> iter = heroes.iterator();
		while (iter.hasNext()) {
			Object[] o = iter.next();
			HeroDTO hero = new HeroDTO();
			
			User user = (User)o[0];
			hero.setUserId(user.getId());	
			hero.setSkill(skillService.findSkillsForUser(user.getId()));
			hero.setFirstName(user.getFirstName());
			hero.setLastName(user.getLastName());
			hero.setTitle(user.getTitle());
			hero.setAvatarUrl(user.getAvatarUrl());
			hero.setState(user.getState());
			hero.setCountry(user.getCountry());
			hero.setPublishFlag(user.getPublishFlag());
			hero.setBadgeCount((int)(long)o[1]); //Number of Badges 
			hero.setProject(badgeService.findProjectsForHero(user.getId()));
			
			heroList.add(hero);
		}
		return heroList;
	}
	
}
