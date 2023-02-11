package com.exam.service;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.model.Category;
import com.exam.repo.CategoryRepo;

@Service
public class CatogeriesServices {

	@Autowired
	private CategoryRepo categoryReoi;

	public Category addCategory(Category ct) {
		return this.categoryReoi.save(ct);
	}

	public Category updateCategory(Category ct) {
		return this.categoryReoi.save(ct);
	}

	public Set<Category> getCategories() {
		return new LinkedHashSet<Category>(this.categoryReoi.findAll());
	}

	public Category getCategory(Long ct) {
		return this.categoryReoi.findById(ct).get();
	}

	public void deleteCategory(Long cId) {
		Category ct = new Category();
		ct.setCid(cId);
		this.categoryReoi.delete(ct);
	}
}