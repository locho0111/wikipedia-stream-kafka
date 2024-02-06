package com.rick.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rick.springboot.entity.WikimediaEntity;

public interface WikimediaDataRepository extends JpaRepository<WikimediaEntity, Long> {

}
