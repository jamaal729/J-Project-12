package com.recipesite.recipe;

import com.recipesite.category.Category;
import com.recipesite.category.CategoryRepository;
import com.recipesite.category.CategoryService;
import com.recipesite.category.CategoryServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService = new CategoryServiceImpl();

    @Before
    public void setUp() throws Exception {}

    @Test
    public void findOneReturnsOne() throws Exception {
        when(categoryRepository.findOne(1L)).thenReturn(test);

        assertThat("Category Service returned one Category", categoryService.findOne(1L).equals(test));
        verify(categoryRepository).findOne(1L);
    }

    @Test
    public void findByName() throws Exception {
        when(categoryRepository.findByName("category")).thenReturn(test);

        assertThat("Category Service found Category by name", categoryService.findByName("category").equals(test));
        verify(categoryRepository).findByName("category");
    }

    @Test
    public void findAll() throws Exception {
        when(categoryRepository.findAll()).thenReturn(testCategories);

        assertThat("Category Service returned all categories", categoryService.findAll().equals(testCategories));
        verify(categoryRepository).findAll();
    }

    @Test
    public void save() throws Exception {
        List<Category> categories = new ArrayList<>(testCategories);
        doAnswer(answer -> {
            categories.add(new Category());
            return true;
        }).when(categoryRepository).save(any(Category.class));

        categoryService.save(new Category());

        assertThat("Categories increased on save", categories.size() == 3);
        verify(categoryRepository).save(any(Category.class));
    }

    private Category test = new Category("category");
    private List<Category> testCategories = Arrays.asList(test, new Category("category2"));
}
