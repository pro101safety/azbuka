package paperka20.instruction.paperka20.quiz;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Category.class, Question.class, Level.class}, version = 2, exportSchema = false)
public abstract class QuizDatabase extends RoomDatabase {
    public abstract CategoryDao categoryDao();
    public abstract QuestionDao questionDao();
    public abstract LevelDao levelDao();
    
    private static QuizDatabase INSTANCE;
    
    public static synchronized QuizDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                context.getApplicationContext(),
                QuizDatabase.class,
                "quiz_database"
            )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries() // Для упрощения, в продакшене лучше использовать корутины/AsyncTask
            .build();
        }
        return INSTANCE;
    }
}

