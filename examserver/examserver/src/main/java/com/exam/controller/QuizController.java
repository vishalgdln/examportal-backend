package com.exam.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.model.Question;
import com.exam.model.Quiz;
import com.exam.service.QuizService;

@RestController
@RequestMapping(value = "/quiz")
public class QuizController {

	@Autowired
	private QuizService quizService;
	
	@PostMapping(value = "/addQuiz")
	public Quiz addQuiz(@RequestBody Quiz ct)
	{
		return this.quizService.addQuiz(ct);
	}
	
	@PostMapping(value = "/updateQuiz")
	public Quiz updateQuiz(@RequestBody Quiz ct)
	{
		return this.quizService.updateQuiz(ct);
	}
	
	@PostMapping(value = "/addgetQuizzes")
	public Set<Quiz> getQuizzes()
	{
		return this.quizService.getQuizzes();
	}
	
	@PostMapping(value = "/quiz/{qid}")
	public ResponseEntity<?> getQuestionofQuiz(@PathVariable("qid") Long qid)
	{
		Quiz quiz = this.quizService.getQuiz(qid);
		Set<Question> questions = quiz.getQuestion();
		List list =new ArrayList(questions);
		if(list.size()>Integer.parseInt(quiz.getNumberOfQuestions()))
		{
			list=list.subList(0, Integer.parseInt(quiz.getNumberOfQuestions()+1));
		}
		Collections.shuffle(list);
		return ResponseEntity.ok(list);
	}
	
	@PostMapping(value = "/deleteCategory")
	public void deleteQuiz(@RequestBody Long id)
	{
		this.quizService.deleteQuiz(id);
	}
}
