package com.exam.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.model.Quiz;

@Repository
public interface QuizRepo extends JpaRepository<Quiz, Long> {

}
