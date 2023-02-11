package com.exam.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.model.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

}
