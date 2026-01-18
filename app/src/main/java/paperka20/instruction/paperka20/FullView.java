package paperka20.instruction.paperka20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.instruction.paperka20.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FullView extends AppCompatActivity {

    String fileName;
    Intent intent;
    RecyclerView recyclerView;
    PdfRenderer pdfRenderer;
    ParcelFileDescriptor parcelFileDescriptor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view);

        intent=getIntent();
        fileName=intent.getStringExtra("fileName");

        recyclerView = findViewById(R.id.pdfRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (fileName != null) {
            try {
                openRendererFromAsset(fileName + ".pdf");
                recyclerView.setAdapter(new PdfPageAdapter(pdfRenderer));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private void openRendererFromAsset(String assetName) throws IOException {
        File cacheFile = new File(getCacheDir(), assetName);
        if (!cacheFile.exists()) {
            copyAssetToFile(assetName, cacheFile);
        }
        parcelFileDescriptor = ParcelFileDescriptor.open(cacheFile, ParcelFileDescriptor.MODE_READ_ONLY);
        pdfRenderer = new PdfRenderer(parcelFileDescriptor);
    }

    private void copyAssetToFile(String assetName, File dest) throws IOException {
        try (InputStream inputStream = getAssets().open(assetName);
             FileOutputStream outputStream = new FileOutputStream(dest)) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (pdfRenderer != null) {
                pdfRenderer.close();
            }
            if (parcelFileDescriptor != null) {
                parcelFileDescriptor.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class PdfPageAdapter extends RecyclerView.Adapter<PdfPageAdapter.PageViewHolder> {
        private final PdfRenderer renderer;

        PdfPageAdapter(PdfRenderer renderer) {
            this.renderer = renderer;
        }

        @Override
        public int getItemCount() {
            return renderer != null ? renderer.getPageCount() : 0;
        }

        @Override
        public PageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pdf_page, parent, false);
            return new PageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PageViewHolder holder, int position) {
            if (renderer == null) {
                return;
            }
            PdfRenderer.Page page = null;
            try {
                page = renderer.openPage(position);
                DisplayMetrics metrics = holder.imageView.getResources().getDisplayMetrics();
                int targetWidth = metrics.widthPixels;
                int targetHeight = (int) ((float) targetWidth * page.getHeight() / page.getWidth());

                Bitmap bitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
                holder.imageView.setImageBitmap(bitmap);
            } finally {
                if (page != null) {
                    page.close();
                }
            }
        }

        static class PageViewHolder extends RecyclerView.ViewHolder {
            final ImageView imageView;

            PageViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.pdfPageImage);
            }
        }
    }
}