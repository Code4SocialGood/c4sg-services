package org.c4sg.dao;

import org.c4sg.entity.Story;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StoryDAO extends CrudRepository<Story, Long> {

    List<Story> findAllByOrderByIdDesc();
}
