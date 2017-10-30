package org.c4sg.service;

import java.util.List;
import java.util.Map;

import org.c4sg.dto.HeroDTO;
import org.c4sg.dto.ProjectDTO;
import org.c4sg.entity.Badge;

public interface BadgeService {
	Badge saveBadge(Integer userId, Integer projectId);
	List<HeroDTO> getBadges();
	Map<Integer, String> getApplicantIdsWithHeroFlagMap(Integer projectId);
	List<ProjectDTO> findProjectsForHero(Integer userId);
}
