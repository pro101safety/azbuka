package paperka20.instruction.paperka20.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.instruction.paperka20.R;

import java.util.Collections;
import java.util.List;

public class QuizGameActivity extends AppCompatActivity {
    
    private TextView questionText;
    private Button option1, option2, option3, option4;
    private TextView questionNumber;
    private TextView scoreText;
    private ProgressBar progressBar;
    private Button nextButton;
    private View correctAnswerCard;
    private TextView correctAnswerText;
    private ScrollView scrollView;
    
    private QuizDatabase database;
    private int categoryId;
    private String categoryName;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int correctAnswers = 0;
    private int selectedAnswer = 0;
    private boolean answerSelected = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_game);
        
        categoryId = getIntent().getIntExtra("categoryId", 1);
        categoryName = getIntent().getStringExtra("categoryName");
        
        // Используем стандартный ActionBar вместо Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(categoryName);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        database = QuizDatabase.getDatabase(this);
        
        questionText = findViewById(R.id.questionText);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        questionNumber = findViewById(R.id.questionNumber);
        scoreText = findViewById(R.id.scoreText);
        progressBar = findViewById(R.id.progressBar);
        nextButton = findViewById(R.id.nextButton);
        correctAnswerCard = findViewById(R.id.correctAnswerCard);
        correctAnswerText = findViewById(R.id.correctAnswerText);
        scrollView = findViewById(R.id.scrollView);
        
        // Получаем вопросы для категории (21 вопрос, как в QuizzLand)
        questions = database.questionDao().getQuestionsByCategory(categoryId, 21);
        
        if (questions.isEmpty()) {
            Toast.makeText(this, "Вопросы для этой категории пока не добавлены", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        
        // Перемешиваем вопросы
        Collections.shuffle(questions);
        
        setupClickListeners();
        loadQuestion();
    }
    
    private void setupClickListeners() {
        option1.setOnClickListener(v -> selectAnswer(1));
        option2.setOnClickListener(v -> selectAnswer(2));
        option3.setOnClickListener(v -> selectAnswer(3));
        option4.setOnClickListener(v -> selectAnswer(4));
        
        nextButton.setOnClickListener(v -> {
            if (answerSelected) {
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    loadQuestion();
                } else {
                    finishQuiz();
                }
            }
        });
    }
    
    private void selectAnswer(int answer) {
        if (answerSelected) return;
        
        selectedAnswer = answer;
        answerSelected = true;
        
        Question currentQuestion = questions.get(currentQuestionIndex);
        boolean isCorrect = (answer == currentQuestion.getCorrectAnswer());
        
        // Отключаем все кнопки
        setButtonsEnabled(false);
        
        // Подсвечиваем правильный и неправильный ответы
        highlightAnswer(answer, isCorrect);
        if (!isCorrect) {
            highlightAnswer(currentQuestion.getCorrectAnswer(), true);
            // Показываем правильный ответ
            showCorrectAnswer(currentQuestion);
        } else {
            // Скрываем карточку с правильным ответом при правильном ответе
            correctAnswerCard.setVisibility(View.GONE);
        }
        
        if (isCorrect) {
            correctAnswers++;
            score += 10; // Очки за правильный ответ
        }
        
        updateScore();
        nextButton.setVisibility(View.VISIBLE);
        
        // Прокручиваем к кнопке "Следующий вопрос"
        scrollToNextButton();
    }
    
    private void scrollToNextButton() {
        if (scrollView != null && nextButton != null) {
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    int scrollAmount = nextButton.getTop() - scrollView.getHeight() + nextButton.getHeight() + 100;
                    scrollView.smoothScrollTo(0, Math.max(0, scrollAmount));
                }
            });
        }
    }
    
    private void highlightAnswer(int answer, boolean isCorrect) {
        Button button = getButtonByNumber(answer);
        if (isCorrect) {
            button.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light, null));
        } else {
            button.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light, null));
        }
    }
    
    private Button getButtonByNumber(int number) {
        switch (number) {
            case 1: return option1;
            case 2: return option2;
            case 3: return option3;
            case 4: return option4;
            default: return option1;
        }
    }
    
    private void setButtonsEnabled(boolean enabled) {
        option1.setEnabled(enabled);
        option2.setEnabled(enabled);
        option3.setEnabled(enabled);
        option4.setEnabled(enabled);
    }
    
    private void showCorrectAnswer(Question question) {
        String correctAnswerTextValue = "";
        switch (question.getCorrectAnswer()) {
            case 1:
                correctAnswerTextValue = question.getOption1();
                break;
            case 2:
                correctAnswerTextValue = question.getOption2();
                break;
            case 3:
                correctAnswerTextValue = question.getOption3();
                break;
            case 4:
                correctAnswerTextValue = question.getOption4();
                break;
        }
        correctAnswerText.setText(correctAnswerTextValue);
        correctAnswerCard.setVisibility(View.VISIBLE);
    }
    
    private void loadQuestion() {
        if (currentQuestionIndex >= questions.size()) {
            finishQuiz();
            return;
        }
        
        Question question = questions.get(currentQuestionIndex);
        
        questionText.setText(question.getQuestionText());
        option1.setText(question.getOption1());
        option2.setText(question.getOption2());
        option3.setText(question.getOption3());
        option4.setText(question.getOption4());
        
        questionNumber.setText("Вопрос " + (currentQuestionIndex + 1) + " из " + questions.size());
        progressBar.setProgress((currentQuestionIndex + 1) * 100 / questions.size());
        
        // Сброс состояния
        answerSelected = false;
        selectedAnswer = 0;
        nextButton.setVisibility(View.GONE);
        setButtonsEnabled(true);
        
        // Скрываем карточку с правильным ответом
        correctAnswerCard.setVisibility(View.GONE);
        
        // Сброс цветов кнопок
        resetButtonColors();
        
        // Прокручиваем в начало при загрузке нового вопроса
        scrollToTop();
    }
    
    private void scrollToTop() {
        if (scrollView != null) {
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.smoothScrollTo(0, 0);
                }
            });
        }
    }
    
    private void resetButtonColors() {
        option1.setBackgroundColor(getResources().getColor(android.R.color.white, null));
        option2.setBackgroundColor(getResources().getColor(android.R.color.white, null));
        option3.setBackgroundColor(getResources().getColor(android.R.color.white, null));
        option4.setBackgroundColor(getResources().getColor(android.R.color.white, null));
    }
    
    private void updateScore() {
        scoreText.setText("Очки: " + score + " | Правильных: " + correctAnswers + "/" + (currentQuestionIndex + 1));
    }
    
    private void finishQuiz() {
        // Открываем экран результатов
        Intent intent = new Intent(this, QuizResultActivity.class);
        intent.putExtra("categoryId", categoryId);
        intent.putExtra("categoryName", categoryName);
        intent.putExtra("correctAnswers", correctAnswers);
        intent.putExtra("totalQuestions", questions.size());
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

