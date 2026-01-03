package paperka20.instruction.paperka20.quiz;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM categories")
    List<Category> getAllCategories();
    
    @Query("SELECT * FROM categories WHERE id = :id")
    Category getCategoryById(int id);
    
    @Insert
    void insertCategory(Category category);
    
    @Insert
    void insertCategories(List<Category> categories);
    
    @Update
    void updateCategory(Category category);
    
    @Query("DELETE FROM categories")
    void deleteAllCategories();
    
    @Query("DELETE FROM categories WHERE name = :name")
    void deleteCategoryByName(String name);
}

