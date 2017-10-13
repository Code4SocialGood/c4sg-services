package org.c4sg.service;

import java.util.List;

import org.c4sg.dto.HeroDTO;
import org.c4sg.entity.Badge;

public interface BadgeService {
	Badge saveBadge(Integer userId, Integer projectId);
	List<HeroDTO> getBadges();
}
