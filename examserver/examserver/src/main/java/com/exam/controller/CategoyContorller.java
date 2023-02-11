package com.exam.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.model.Category;
import com.exam.service.CatogeriesServices;

@RestController
@RequestMapping(value = "/category")
@CrossOrigin
public class CategoyContorller {
	
	@Autowired
	private CatogeriesServices catoService;
	
	@PostMapping(value = "/addCategory")
	public Category addCategory(@RequestBody Category ct)
	{
		return this.catoService.addCategory(ct);
	}
	
	@PostMapping(value = "/updateCategory")
	public Category updateCategory(@RequestBody Category ct)
	{
		return this.catoService.updateCategory(ct);
	}
	
	@PostMapping(value = "/getCategories")
	public Set<Category> getCategories()
	{
		return this.catoService.getCategories();
	}
	
	@PostMapping(value = "/getCategory")
	public Category getCategory(@RequestBody Long id)
	{
		return this.catoService.getCategory(id);
	}
	
	@PostMapping(value = "/deleteCategory")
	public void deleteCategory(@RequestBody Long id)
	{
		this.catoService.deleteCategory(id);
	}

}
