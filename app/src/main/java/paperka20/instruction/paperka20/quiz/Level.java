package paperka20.instruction.paperka20.quiz;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "levels",
    foreignKeys = @ForeignKey(
        entity = Category.class,
        parentColumns = "id",
        childColumns = "categoryId",
        onDelete = ForeignKey.CASCADE
    ),
    indices = {@Index("categoryId")}
)
public class Level {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private int categoryId;
    private int levelNumber; // Номер уровня (1, 2, 3...)
    private int questionsPerLevel; // Количество вопросов на уровне
    private boolean isCompleted; // Пройден ли уровень
    private int score; // Очки за уровень
    private int correctAnswers; // Количество правильных ответов
    
    public Level() {
    }
    
    @Ignore
    public Level(int categoryId, int levelNumber, int questionsPerLevel) {
        this.categoryId = categoryId;
        this.levelNumber = levelNumber;
        this.questionsPerLevel = questionsPerLevel;
        this.isCompleted = false;
        this.score = 0;
        this.correctAnswers = 0;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    public int getLevelNumber() {
        return levelNumber;
    }
    
    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }
    
    public int getQuestionsPerLevel() {
        return questionsPerLevel;
    }
    
    public void setQuestionsPerLevel(int questionsPerLevel) {
        this.questionsPerLevel = questionsPerLevel;
    }
    
    public boolean isCompleted() {
        return isCompleted;
    }
    
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public int getCorrectAnswers() {
        return correctAnswers;
    }
    
    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }
}

