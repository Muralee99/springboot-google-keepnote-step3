package com.stackroute.keepnote.controller;

import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */

@RestController
@RequestMapping(consumes = "application/json", produces = "application/json")
public class CategoryController {

	/*
     * Autowiring should be implemented for the CategoryService. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword
	 */

    @Autowired
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

	/*
	 * Define a handler method which will create a category by reading the
	 * Serialized category object from request body and save the category in
	 * category table in database. Please note that the careatorId has to be unique
	 * and the loggedIn userID should be taken as the categoryCreatedBy for the
	 * category. This handler method should return any one of the status messages
	 * basis on different situations: 1. 201(CREATED - In case of successful
	 * creation of the category 2. 409(CONFLICT) - In case of duplicate categoryId
	 * 3. 401(UNAUTHORIZED) - If the user trying to perform the action has not
	 * logged in.
	 * 
	 * This handler method should map to the URL "/category" using HTTP POST
	 * method".
	 */

    @RequestMapping(value = "/category", method = RequestMethod.POST)
    public ResponseEntity<Category> createCategory(@RequestBody Category category, HttpSession session) {
        boolean isCreated = false;
        boolean isValidUser = validateUser(session);

        if (isValidUser) {
            isCreated = categoryService.createCategory(category);
        } else {
            return new ResponseEntity<Category>(HttpStatus.UNAUTHORIZED);
        }

        if (isCreated) {
            return new ResponseEntity<Category>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Category>(HttpStatus.CONFLICT);
        }
    }

    /*
     * Define a handler method which will delete a category from a database.
     *
     * This handler method should return any one of the status messages basis on
     * different situations: 1. 200(OK) - If the category deleted successfully from
     * database. 2. 404(NOT FOUND) - If the category with specified categoryId is
     * not found. 3. 401(UNAUTHORIZED) - If the user trying to perform the action
     * has not logged in.
     *
     * This handler method should map to the URL "/category/{id}" using HTTP Delete
     * method" where "id" should be replaced by a valid categoryId without {}
     */
    @RequestMapping(value = "/category/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Category> deleteCategory(@PathVariable int id, HttpSession session) {
        boolean isValidUser = validateUser(session);
        boolean isDeleted = false;

        if (isValidUser) {
            isDeleted = categoryService.deleteCategory(id);
            if (isDeleted) {
                return new ResponseEntity<Category>(HttpStatus.OK);
            } else {
                return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<Category>(HttpStatus.UNAUTHORIZED);
        }
    }

	/*
	 * Define a handler method which will update a specific category by reading the
	 * Serialized object from request body and save the updated category details in
	 * a category table in database handle CategoryNotFoundException as well. please
	 * note that the loggedIn userID should be taken as the categoryCreatedBy for
	 * the category. This handler method should return any one of the status
	 * messages basis on different situations: 1. 200(OK) - If the category updated
	 * successfully. 2. 404(NOT FOUND) - If the category with specified categoryId
	 * is not found. 3. 401(UNAUTHORIZED) - If the user trying to perform the action
	 * has not logged in.
	 * 
	 * This handler method should map to the URL "/category/{id}" using HTTP PUT
	 * method.
	 */

    @RequestMapping(value = "/category/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Category> updateCategory(@PathVariable int id, @RequestBody Category category, HttpSession session) {
        boolean isValidUser = validateUser(session);

        Category categoryUpdated = null;

        try {
            if (isValidUser) {
                categoryUpdated = categoryService.updateCategory(category, id);
            } else {
                return new ResponseEntity<Category>(HttpStatus.UNAUTHORIZED);
            }
        } catch (CategoryNotFoundException e) {
            e.printStackTrace();
        }

        if (categoryUpdated != null) {
            return new ResponseEntity<Category>(category, HttpStatus.OK);
        } else {
            return new ResponseEntity<Category>(category, HttpStatus.NOT_FOUND);
        }
    }
	
	/*
	 * Define a handler method which will get us the category by a userId.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the category found successfully. 2.
	 * 401(UNAUTHORIZED) -If the user trying to perform the action has not logged
	 * in.
	 * 
	 * 
	 * This handler method should map to the URL "/category" using HTTP GET method
	 */

    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public ResponseEntity<List<Category>> getCategoryByUserId(HttpSession session) {
        boolean isValidUser = validateUser(session);

        List<Category> categories = null;

        if (isValidUser) {
            categories = categoryService.getAllCategoryByUserId(session.getAttribute("loggedInUserId").toString());
        } else {
            return new ResponseEntity<List<Category>>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
    }

    private boolean validateUser(HttpSession session) {
        if (session.getAttribute("loggedInUserId") != null) {
            return true;
        }
        return false;
    }

}