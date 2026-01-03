package paperka20.instruction.paperka20.quiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.instruction.paperka20.R;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    
    private Context context;
    private List<Category> categories;
    private OnCategoryClickListener listener;
    
    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
    }
    
    public CategoryAdapter(Context context, List<Category> categories, OnCategoryClickListener listener) {
        this.context = context;
        this.categories = categories;
        this.listener = listener;
    }
    
    @Override
    public int getCount() {
        return categories.size();
    }
    
    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }
    
    @Override
    public long getItemId(int position) {
        return categories.get(position).getId();
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        }
        
        Category category = categories.get(position);
        
        TextView categoryName = convertView.findViewById(R.id.categoryName);
        TextView categoryDescription = convertView.findViewById(R.id.categoryDescription);
        CardView cardView = convertView.findViewById(R.id.categoryCard);
        
        categoryName.setText(category.getName());
        categoryDescription.setText(category.getDescription());
        
        cardView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCategoryClick(category);
            }
        });
        
        return convertView;
    }
}

