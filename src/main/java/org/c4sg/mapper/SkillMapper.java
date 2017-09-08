package org.c4sg.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.c4sg.dto.SkillDTO;
import org.c4sg.dto.SkillUserCountDTO;
import org.c4sg.entity.Skill;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SkillMapper extends ModelMapper {

	public SkillDTO getSkillDtoFromEntity(Skill skill){
		SkillDTO skillDTO = map(skill, SkillDTO.class);
		return skillDTO;
	}
	
	public Skill getSkillEntityFromDto(SkillDTO skillDTO){
		Skill skill = map(skillDTO, Skill.class);
		return skill;
	}
	
	public SkillUserCountDTO getSkillUserCountDto(Map<String,Object> skillMap){
		SkillUserCountDTO SkillUserCountDTO = new SkillUserCountDTO();
		SkillUserCountDTO.setSkillName((String)skillMap.get("skillName"));
		SkillUserCountDTO.setUserCount((long)skillMap.get("userCount"));
		return SkillUserCountDTO;
	}
	
	public List<SkillDTO> getSkillDTOs(List<Object[]> skills) {
		
		List<SkillDTO> skillList = new ArrayList<>();
		Iterator<Object[]> iter = skills.iterator();
		while (iter.hasNext()) {
			Object[] o = iter.next();
			SkillDTO skill = new SkillDTO();
			skill.setId((Integer)o[0]);
			skill.setSkillName((String)o[1]);
			skillList.add(skill);
		}
		return skillList;
	}
}
