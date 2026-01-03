package paperka20.instruction.paperka20.quiz;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface QuestionDao {
    @Query("SELECT * FROM questions WHERE categoryId = :categoryId AND level = :level ORDER BY RANDOM() LIMIT :limit")
    List<Question> getQuestionsByCategoryAndLevel(int categoryId, int level, int limit);
    
    @Query("SELECT * FROM questions WHERE categoryId = :categoryId ORDER BY RANDOM() LIMIT :limit")
    List<Question> getQuestionsByCategory(int categoryId, int limit);
    
    @Query("SELECT * FROM questions WHERE id = :id")
    Question getQuestionById(int id);
    
    @Insert
    void insertQuestion(Question question);
    
    @Insert
    void insertQuestions(List<Question> questions);
    
    @Update
    void updateQuestion(Question question);
    
    @Query("SELECT COUNT(*) FROM questions WHERE categoryId = :categoryId")
    int getQuestionCountByCategory(int categoryId);
}

