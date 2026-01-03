package paperka20.instruction.paperka20.quiz;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "questions",
    foreignKeys = @ForeignKey(
        entity = Category.class,
        parentColumns = "id",
        childColumns = "categoryId",
        onDelete = ForeignKey.CASCADE
    ),
    indices = {@Index("categoryId")}
)
public class Question {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private int categoryId;
    private int level; // Уровень сложности (1-5)
    private String questionText;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int correctAnswer; // 1-4
    private String explanation; // Объяснение правильного ответа
    
    public Question() {
    }
    
    public Question(int categoryId, int level, String questionText, 
                   String option1, String option2, String option3, String option4, 
                   int correctAnswer, String explanation) {
        this.categoryId = categoryId;
        this.level = level;
        this.questionText = questionText;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
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
    
    public int getLevel() {
        return level;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
    
    public String getQuestionText() {
        return questionText;
    }
    
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
    
    public String getOption1() {
        return option1;
    }
    
    public void setOption1(String option1) {
        this.option1 = option1;
    }
    
    public String getOption2() {
        return option2;
    }
    
    public void setOption2(String option2) {
        this.option2 = option2;
    }
    
    public String getOption3() {
        return option3;
    }
    
    public void setOption3(String option3) {
        this.option3 = option3;
    }
    
    public String getOption4() {
        return option4;
    }
    
    public void setOption4(String option4) {
        this.option4 = option4;
    }
    
    public int getCorrectAnswer() {
        return correctAnswer;
    }
    
    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    
    public String getExplanation() {
        return explanation;
    }
    
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}

