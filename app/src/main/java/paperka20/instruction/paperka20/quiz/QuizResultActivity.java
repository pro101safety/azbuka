package paperka20.instruction.paperka20.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.instruction.paperka20.R;

public class QuizResultActivity extends AppCompatActivity {
    
    private TextView categoryNameText;
    private TextView correctAnswersText;
    private TextView totalQuestionsText;
    private TextView percentageText;
    private Button backToCategoriesButton;
    private Button playAgainButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        
        // Используем стандартный ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Результаты викторины");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        // Получаем данные из Intent
        String categoryName = getIntent().getStringExtra("categoryName");
        int correctAnswers = getIntent().getIntExtra("correctAnswers", 0);
        int totalQuestions = getIntent().getIntExtra("totalQuestions", 0);
        int score = getIntent().getIntExtra("score", 0);
        
        categoryNameText = findViewById(R.id.categoryNameText);
        correctAnswersText = findViewById(R.id.correctAnswersText);
        totalQuestionsText = findViewById(R.id.totalQuestionsText);
        percentageText = findViewById(R.id.percentageText);
        backToCategoriesButton = findViewById(R.id.backToCategoriesButton);
        playAgainButton = findViewById(R.id.playAgainButton);
        
        // Устанавливаем данные
        categoryNameText.setText(categoryName);
        correctAnswersText.setText(String.valueOf(correctAnswers));
        totalQuestionsText.setText(String.valueOf(totalQuestions));
        
        // Вычисляем процент
        int percentage = totalQuestions > 0 ? (correctAnswers * 100 / totalQuestions) : 0;
        percentageText.setText(percentage + "%");
        
        // Настройка кнопок
        backToCategoriesButton.setOnClickListener(v -> {
            finish(); // Возвращаемся к списку категорий
        });
        
        playAgainButton.setOnClickListener(v -> {
            // Запускаем викторину заново
            Intent intent = new Intent(this, QuizGameActivity.class);
            intent.putExtra("categoryId", getIntent().getIntExtra("categoryId", 1));
            intent.putExtra("categoryName", categoryName);
            startActivity(intent);
            finish(); // Закрываем экран результатов
        });
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

