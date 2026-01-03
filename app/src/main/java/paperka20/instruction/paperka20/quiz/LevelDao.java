package paperka20.instruction.paperka20.quiz;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LevelDao {
    @Query("SELECT * FROM levels WHERE categoryId = :categoryId ORDER BY levelNumber")
    List<Level> getLevelsByCategory(int categoryId);
    
    @Query("SELECT * FROM levels WHERE categoryId = :categoryId AND levelNumber = :levelNumber")
    Level getLevelByCategoryAndNumber(int categoryId, int levelNumber);
    
    @Query("SELECT * FROM levels WHERE id = :id")
    Level getLevelById(int id);
    
    @Insert
    void insertLevel(Level level);
    
    @Insert
    void insertLevels(List<Level> levels);
    
    @Update
    void updateLevel(Level level);
    
    @Query("SELECT MAX(levelNumber) FROM levels WHERE categoryId = :categoryId AND isCompleted = 1")
    Integer getLastCompletedLevel(int categoryId);
}

