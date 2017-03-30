package org.c4sg.dao;

import java.util.List;

import org.c4sg.entity.UserOrganization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOrganizationDAO extends JpaRepository<UserOrganization, Long>{
    UserOrganization findByUser_IdAndOrganization_Id(Integer userId, Integer organizationId);
    List<UserOrganization> findByUserId(Integer userId);
}
