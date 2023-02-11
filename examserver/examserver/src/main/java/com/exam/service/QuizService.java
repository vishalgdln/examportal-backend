package com.exam.service;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.model.Quiz;
import com.exam.repo.QuizRepo;

@Service
public class QuizService {

	@Autowired
	private QuizRepo quizRepo;

	public Quiz addQuiz(Quiz qz) {
		return this.quizRepo.save(qz);
	}

	public Quiz updateQuiz(Quiz qz) {
		return this.quizRepo.save(qz);
	}

	public Set<Quiz> getQuizzes() {
		return new LinkedHashSet<Quiz>(this.quizRepo.findAll());
	}

	public Quiz getQuiz(Long qId) {
		return this.quizRepo.findById(qId).get();
	}

	public void deleteQuiz(Long quizId) {
		Quiz qz = new Quiz();
		qz.setqId(quizId);
		this.quizRepo.delete(qz);
	}

}
