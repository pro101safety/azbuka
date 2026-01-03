package paperka20.instruction.paperka20;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.instruction.paperka20.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //объявляем переменные для кнопки назад
    private long backPressedTime;
    private Toast backToast;
    //конец объявления переменных для кнопки назад

    ExpandableListView expandableListView;
    CustomAdapter customAdapter;
    List<Chapter>chapterList;
    List<Chapter>originalChapterList; // Оригинальный список для восстановления
    List<Topics>topicsList;
    AlertDialog searchDialog;
    SearchView dialogSearchView; // Сохраняем ссылку на SearchView для очистки

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Opt-in to edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        setContentView(R.layout.activity_main);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        if (expandableListView == null) {
            // Если элемент не найден, завершаем Activity
            finish();
            return;
        }
        
        // Apply system bar insets to the root container so all content is shifted
        View rootView = findViewById(R.id.root);
        if (rootView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
                Insets sysBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                float density = getResources().getDisplayMetrics().density;
                int extraTop = (int) Math.round(46f * density); // add 46dp visual gap
                v.setPadding(sysBars.left, sysBars.top + extraTop, sysBars.right, sysBars.bottom);
                return insets;
            });
        }
        
        // Настройка обработки кнопки "Назад" для Android 13+
        setupBackPressedHandler();
        
        addData();

    }
    
    private void setupBackPressedHandler() {
        // Для Android 13+ используем OnBackPressedDispatcher
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            OnBackPressedCallback callback = new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    if (backPressedTime + 2000 > System.currentTimeMillis()) {
                        if (backToast != null) {
                            backToast.cancel();
                        }
                        finish();
                    } else {
                        backToast = Toast.makeText(getBaseContext(), 
                            "Работайте безопасно и возвращайтесь к своим детям!", 
                            Toast.LENGTH_SHORT);
                        backToast.show();
                        backPressedTime = System.currentTimeMillis();
                    }
                }
            };
            getOnBackPressedDispatcher().addCallback(this, callback);
        }
    }

    void addData()
    {
        //single time
        chapterList=new ArrayList<>();

        //multiple time
        topicsList=new ArrayList<>();

        //chapter 1 - добавлять по ходу сколько надо t01~09
        topicsList.add(new Topics("1.ЧЕК-ЛИСТ", "t01"));
        topicsList.add(new Topics("2.ПЕРЕД НАЧАЛОМ РАБОТЫ", "t02"));
        topicsList.add(new Topics("3.ПРИ ВЫПОЛНЕНИИ РАБОТЫ", "t03"));
        topicsList.add(new Topics("4.ПО ОКОНЧАНИИ РАБОТЫ", "t04"));
        topicsList.add(new Topics("5.АВАРИЙНЫЕ СИТУАЦИИ", "t05"));
        chapterList.add(new Chapter("1. РАБОТА НА ВЫСОТЕ",topicsList));

        //chapter 2 t21~29
        topicsList=new ArrayList<>();
        topicsList.add(new Topics("1.ЧЕК-ЛИСТ", "t21"));
        topicsList.add(new Topics("2.ПЕРЕД НАЧАЛОМ РАБОТЫ", "t22"));
        topicsList.add(new Topics("3.ПРИ ВЫПОЛНЕНИИ РАБОТЫ", "t23"));
        topicsList.add(new Topics("4.ПО ОКОНЧАНИИ РАБОТЫ", "t24"));
        topicsList.add(new Topics("5.АВАРИЙНЫЕ СИТУАЦИИ", "t25"));
        chapterList.add(new Chapter("2. ДЕРЕВООБРАБОТКА",topicsList));

        //chapter 3 t31~39
        topicsList=new ArrayList<>();
        topicsList.add(new Topics("1.ЧЕК-ЛИСТ", "t31"));
        topicsList.add(new Topics("2.ПЕРЕД НАЧАЛОМ РАБОТЫ", "t32"));
        topicsList.add(new Topics("3.ПРИ ВЫПОЛНЕНИИ РАБОТЫ", "t33"));
        topicsList.add(new Topics("4.ПО ОКОНЧАНИИ РАБОТЫ", "t34"));
        topicsList.add(new Topics("5.АВАРИЙНЫЕ СИТУАЦИИ", "t35"));
        chapterList.add(new Chapter("3. ПЕРЕМЕЩЕНИЕ ГРУЗОВ",topicsList));

        //chapter 4 t41~49
        topicsList=new ArrayList<>();
        topicsList.add(new Topics("1.ЧЕК-ЛИСТ", "t41"));
        topicsList.add(new Topics("2.ПЕРЕД НАЧАЛОМ РАБОТЫ", "t42"));
        topicsList.add(new Topics("3.ПРИ ВЫПОЛНЕНИИ РАБОТЫ", "t43"));
        topicsList.add(new Topics("4.ПО ОКОНЧАНИИ РАБОТЫ", "t44"));
        topicsList.add(new Topics("5.АВАРИЙНЫЕ СИТУАЦИИ", "t45"));
        chapterList.add(new Chapter("4. ДВИЖУЩИЙСЯ ТРАНСПОРТ",topicsList));

        //chapter 5 t51~59
        topicsList=new ArrayList<>();
        topicsList.add(new Topics("1.ЧЕК-ЛИСТ", "t51"));
        topicsList.add(new Topics("2.ПЕРЕД НАЧАЛОМ РАБОТЫ", "t52"));
        topicsList.add(new Topics("3.ПРИ ВЫПОЛНЕНИИ РАБОТЫ", "t53"));
        topicsList.add(new Topics("4.ПО ОКОНЧАНИИ РАБОТЫ", "t54"));
        topicsList.add(new Topics("5.АВАРИЙНЫЕ СИТУАЦИИ", "t55"));
        chapterList.add(new Chapter("5. ЗЕМЛЯНЫЕ РАБОТЫ",topicsList));

        //chapter 6 t61~69
        topicsList=new ArrayList<>();
        topicsList.add(new Topics("1.ЧЕК-ЛИСТ", "t61"));
        topicsList.add(new Topics("2.ПЕРЕД НАЧАЛОМ РАБОТЫ", "t62"));
        topicsList.add(new Topics("3.ПРИ ВЫПОЛНЕНИИ РАБОТЫ", "t63"));
        topicsList.add(new Topics("4.ПО ОКОНЧАНИИ РАБОТЫ", "t64"));
        topicsList.add(new Topics("5.АВАРИЙНЫЕ СИТУАЦИИ", "t65"));
        chapterList.add(new Chapter("6. ЭЛЕКТРОГАЗОСВАРОЧНЫЕ РАБОТЫ",topicsList));

        //chapter 7 t71~79
        topicsList=new ArrayList<>();
        topicsList.add(new Topics("1.ЧЕК-ЛИСТ", "t71"));
        topicsList.add(new Topics("2.ПЕРЕД НАЧАЛОМ РАБОТЫ", "t72"));
        topicsList.add(new Topics("3.ПРИ ВЫПОЛНЕНИИ РАБОТЫ", "t73"));
        topicsList.add(new Topics("4.ПО ОКОНЧАНИИ РАБОТЫ", "t74"));
        topicsList.add(new Topics("5.АВАРИЙНЫЕ СИТУАЦИИ", "t75"));
        chapterList.add(new Chapter("7. ЭЛЕКТРОИНСТРУМЕНТ",topicsList));

        //chapter 8 t81~89
        topicsList=new ArrayList<>();
        topicsList.add(new Topics("1.ЧЕК-ЛИСТ", "t81"));
        topicsList.add(new Topics("2.ПЕРЕД НАЧАЛОМ РАБОТЫ", "t82"));
        topicsList.add(new Topics("3.ПРИ ВЫПОЛНЕНИИ РАБОТЫ", "t83"));
        topicsList.add(new Topics("4.ПО ОКОНЧАНИИ РАБОТЫ", "t84"));
        topicsList.add(new Topics("5.АВАРИЙНЫЕ СИТУАЦИИ", "t85"));
        chapterList.add(new Chapter("8. ГАЗООПАСНЫЕ РАБОТЫ",topicsList));

        //chapter 9 t91~99
        topicsList=new ArrayList<>();
        topicsList.add(new Topics("1.ЧЕК-ЛИСТ", "t91"));
        topicsList.add(new Topics("2.ПЕРЕД НАЧАЛОМ РАБОТЫ", "t92"));
        topicsList.add(new Topics("3.ПРИ ВЫПОЛНЕНИИ РАБОТЫ", "t93"));
        topicsList.add(new Topics("4.ПО ОКОНЧАНИИ РАБОТЫ", "t94"));
        topicsList.add(new Topics("5.АВАРИЙНЫЕ СИТУАЦИИ", "t95"));
        chapterList.add(new Chapter("9. ЛЕСТНИЦА",topicsList));

        //chapter 10 t100~101
        topicsList=new ArrayList<>();
        topicsList.add(new Topics("1.ЧЕК-ЛИСТ", "t100"));
        topicsList.add(new Topics("2.УНИВЕРСАЛЬНЫЙ АЛГОРИТМ ОКАЗАНИЯ ПЕРВОЙ ПОМОЩИ", "t101"));
        topicsList.add(new Topics("3.ПОРЯДОК ОКАЗАНИЯ ПЕРВОЙ ПОМОЩИ, РФ 2025", "t102"));
        topicsList.add(new Topics("4.ПЕРВАЯ ПОМОЩЬ Белорусское Общество Красного Креста", "t103"));
        chapterList.add(new Chapter("10. АЛГОРИТМ ОКАЗАНИЯ ПЕРВОЙ ПОМОЩИ",topicsList));

        //chapter 11 t110~114
        topicsList=new ArrayList<>();
        topicsList.add(new Topics("1.ЧЕК-ЛИСТ", "t110"));
        topicsList.add(new Topics("2.ПЕРЕД НАЧАЛОМ РАБОТЫ", "t111"));
        topicsList.add(new Topics("3.ПРИ ВЫПОЛНЕНИИ РАБОТЫ", "t112"));
        topicsList.add(new Topics("4.ПО ОКОНЧАНИИ РАБОТЫ", "t113"));
        topicsList.add(new Topics("5.АВАРИЙНЫЕ СИТУАЦИИ", "t114"));
        chapterList.add(new Chapter("11. УШМ (БОЛГАРКА)" ,topicsList));

        //chapter 12 t120~124
        topicsList=new ArrayList<>();
        topicsList.add(new Topics("1.ЧЕК-ЛИСТ", "t120"));
        topicsList.add(new Topics("2.ПЕРЕД НАЧАЛОМ РАБОТЫ", "t121"));
        topicsList.add(new Topics("3.ПРИ ВЫПОЛНЕНИИ РАБОТЫ", "t122"));
        topicsList.add(new Topics("4.ПО ОКОНЧАНИИ РАБОТЫ", "t123"));
        topicsList.add(new Topics("5.АВАРИЙНЫЕ СИТУАЦИИ", "t124"));
        chapterList.add(new Chapter("12. МОТОКОСА (триммер)" ,topicsList));

        //chapter 13 t130~134
        topicsList=new ArrayList<>();
        topicsList.add(new Topics("1.ЧЕК-ЛИСТ", "t130"));
        topicsList.add(new Topics("2.ПЕРЕД НАЧАЛОМ РАБОТЫ", "t131"));
        topicsList.add(new Topics("3.ПРИ ВЫПОЛНЕНИИ РАБОТЫ", "t132"));
        topicsList.add(new Topics("4.ПО ОКОНЧАНИИ РАБОТЫ", "t133"));
        topicsList.add(new Topics("5.АВАРИЙНЫЕ СИТУАЦИИ", "t134"));
        chapterList.add(new Chapter("13. БЕНЗИНОМОТОРНАЯ ПИЛА" ,topicsList));

        //chapter 14 t140~
        topicsList=new ArrayList<>();
        topicsList.add(new Topics("1.ЧЕК-ЛИСТ", "t140"));
        chapterList.add(new Chapter("14. ПРАВИЛА ПОЛЬЗОВАНИЯ ОГНЕТУШИТЕЛЕМ",topicsList));

        //chapter 15 t150~
        topicsList=new ArrayList<>();
        topicsList.add(new Topics("1.ЧЕК-ЛИСТ", "t150"));
        chapterList.add(new Chapter("15. ПРАВИЛА ПОВЕДЕНИЯ В ТОЛПЕ",topicsList));

        //chapter 16 t160~
        topicsList=new ArrayList<>();
        topicsList.add(new Topics("1.ЧЕК-ЛИСТ", "t160"));
        chapterList.add(new Chapter("16. РАЗБИЛСЯ ГРАДУСНИК",topicsList));

        //chapter 17 t170~
        topicsList=new ArrayList<>();
        topicsList.add(new Topics("1.ЧЕК-ЛИСТ", "t170"));
        chapterList.add(new Chapter("17. ПРИЗНАКИ ИНФАРКТА",topicsList));

        //chapter 18 t180~
        topicsList=new ArrayList<>();
        topicsList.add(new Topics("1.ЧЕК-ЛИСТ", "t180"));
        chapterList.add(new Chapter("18. ПРИЗНАКИ ИНСУЛЬТА",topicsList));

        // бери за основу блока газоопасные

        //chapter 9 t91~99
        //topicsList=new ArrayList<>();
        //topicsList.add(new Topics("1.ПЕРВООЧЕРЕДНЫЕ ДЕЙСТВИЯ", "t91"));
        //topicsList.add(new Topics("2.МИКРОТРАВМА", "t92"));
        //topicsList.add(new Topics("3.НЕ ОТНОСЯЩИЙСЯ К ТЯЖЕЛОМУ НС", "t93"));
        //topicsList.add(new Topics("4.ОТНОСЯЩИЙСЯ К ТЯЖЕЛОМУ НС", "t94"));
        //topicsList.add(new Topics("5.СМЕРТЕЛЬНЫЙ НС", "t95"));
        //topicsList.add(new Topics("6.ГРУППОВОЙ НС", "t105"));
        //chapterList.add(new Chapter("10. ДЕЙСТВИЯ ПРИ НС",topicsList));

        sendData();

    }

    void sendData()
    {
        // Сохраняем оригинальный список
        originalChapterList = new ArrayList<>();
        for (Chapter chapter : chapterList) {
            List<Topics> originalTopics = new ArrayList<>(chapter.getTopicsList());
            originalChapterList.add(new Chapter(chapter.getChapterName(), originalTopics));
        }
        
        customAdapter=new CustomAdapter(chapterList, MainActivity.this);
        expandableListView.setAdapter(customAdapter);
    }

    private void showSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_search, null);
        
        dialogSearchView = dialogView.findViewById(R.id.dialogSearchView);
        
        builder.setView(dialogView);
        builder.setTitle("Поиск по разделам");
        
        // Кнопка "Найти" (положительная)
        builder.setPositiveButton("Найти", (dialog, which) -> {
            // Поиск уже выполняется в реальном времени, но можно оставить для подтверждения
            String query = dialogSearchView != null ? dialogSearchView.getQuery().toString() : "";
            filterData(query);
        });
        
        // Кнопка "Очистить" (нейтральная)
        builder.setNeutralButton("Очистить", (dialog, which) -> {
            if (dialogSearchView != null) {
                dialogSearchView.setQuery("", false);
            }
            filterData("");
        });
        
        // Кнопка "Отменить" (отрицательная)
        builder.setNegativeButton("Отменить", (dialog, which) -> {
            // Восстанавливаем оригинальный список при отмене
            filterData("");
            dialog.dismiss();
        });
        
        searchDialog = builder.create();
        
        // Восстанавливаем список при закрытии диалога
        searchDialog.setOnDismissListener(dialog -> {
            filterData("");
        });
        
        // Настройка поиска в диалоге
        if (dialogSearchView != null) {
            dialogSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    filterData(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filterData(newText);
                    return false;
                }
            });
        }
        
        searchDialog.show();
    }

    private void filterData(String query) {
        if (originalChapterList == null) {
            return;
        }

        if (TextUtils.isEmpty(query)) {
            // Восстанавливаем оригинальный список
            chapterList = new ArrayList<>();
            for (Chapter chapter : originalChapterList) {
                List<Topics> originalTopics = new ArrayList<>(chapter.getTopicsList());
                chapterList.add(new Chapter(chapter.getChapterName(), originalTopics));
            }
        } else {
            // Фильтруем данные
            chapterList = new ArrayList<>();
            String lowerQuery = query.toLowerCase().trim();

            for (Chapter chapter : originalChapterList) {
                String chapterName = chapter.getChapterName().toLowerCase();
                
                // Проверяем, содержит ли название главы запрос
                boolean chapterMatches = chapterName.contains(lowerQuery);
                
                // Фильтруем темы внутри главы
                List<Topics> filteredTopics = new ArrayList<>();
                for (Topics topic : chapter.getTopicsList()) {
                    String topicName = topic.getTopicName().toLowerCase();
                    if (topicName.contains(lowerQuery)) {
                        filteredTopics.add(topic);
                    }
                }

                // Добавляем главу, если она или её темы соответствуют запросу
                if (chapterMatches || !filteredTopics.isEmpty()) {
                    // Если глава соответствует, добавляем все темы
                    if (chapterMatches) {
                        chapterList.add(new Chapter(chapter.getChapterName(), new ArrayList<>(chapter.getTopicsList())));
                    } else {
                        // Иначе добавляем только отфильтрованные темы
                        chapterList.add(new Chapter(chapter.getChapterName(), filteredTopics));
                    }
                }
            }
        }

        // Обновляем адаптер
        if (customAdapter != null) {
            customAdapter.updateData(chapterList);
            // Раскрываем все группы после фильтрации
            for (int i = 0; i < chapterList.size(); i++) {
                expandableListView.expandGroup(i);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.search) {
            showSearchDialog();
            return true;
        } else if (id == R.id.privacy) {
            startActivity(new Intent(MainActivity.this, Privacy.class));
            return true;
        } else if (id == R.id.contact) {
            startActivity(new Intent(MainActivity.this, Contact.class));
            return true;
        } else if (id == R.id.quiz) {
            startActivity(new Intent(MainActivity.this, paperka20.instruction.paperka20.quiz.QuizMainActivity.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

//начало кода системной кнопки назад

    @Override
    public void onBackPressed() {
        // Для Android 13+ обработка через OnBackPressedDispatcher (настроено в setupBackPressedHandler)
        // Для старых версий используем стандартный подход
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.TIRAMISU) {
            if (backPressedTime + 2000 > System.currentTimeMillis()){
                if (backToast != null) {
                    backToast.cancel();
                }
                super.onBackPressed();
                return;
            } else {
                backToast = Toast.makeText(getBaseContext(), 
                    "Работайте безопасно и возвращайтесь к своим детям!", 
                    Toast.LENGTH_SHORT);
                backToast.show();
                backPressedTime = System.currentTimeMillis();
            }
        } else {
            // Для Android 13+ вызываем стандартную обработку через dispatcher
            super.onBackPressed();
        }
    }

    //конец кода системной кнопки назад
}