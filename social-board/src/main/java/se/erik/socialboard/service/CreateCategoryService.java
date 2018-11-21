package se.erik.socialboard.service;

import se.erik.socialboard.entity.Category;

public interface CreateCategoryService {

	Category createNewCategory(String categoryName, String description, long creatorId);

}