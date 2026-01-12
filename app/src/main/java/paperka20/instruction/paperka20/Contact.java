package paperka20.instruction.paperka20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.instruction.paperka20.R;

public class Contact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        // Enable link clicking for TextViews with autoLink
        TextView textWebsite = findViewById(R.id.textWebsite);
        TextView textEmail = findViewById(R.id.textEmail);
        TextView textPhone1 = findViewById(R.id.textPhone1);
        TextView textPhone2 = findViewById(R.id.textPhone2);
        View cardPlanner = findViewById(R.id.cardPlanner);
        View cardPapera = findViewById(R.id.cardPapera);
        
        if (textWebsite != null) {
            textWebsite.setMovementMethod(LinkMovementMethod.getInstance());
        }
        if (textEmail != null) {
            textEmail.setMovementMethod(LinkMovementMethod.getInstance());
        }
        if (textPhone1 != null) {
            textPhone1.setMovementMethod(LinkMovementMethod.getInstance());
        }
        if (textPhone2 != null) {
            textPhone2.setMovementMethod(LinkMovementMethod.getInstance());
        }

        // Play Store promos
        if (cardPlanner != null) {
            cardPlanner.setOnClickListener(v ->
                openUrl("https://play.google.com/store/apps/details?id=by.instruction.planer")
            );
        }
        if (cardPapera != null) {
            cardPapera.setOnClickListener(v ->
                openUrl("https://play.google.com/store/apps/details?id=by.instruction.papera")
            );
        }

        // Handle Telegram Channel link
        TextView textTelegramChannel = findViewById(R.id.textTelegramChannel);
        if (textTelegramChannel != null) {
            textTelegramChannel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openTelegramChannel("pro101safety");
                }
            });
        }

        // Handle Telegram Account link
        TextView textTelegramAccount = findViewById(R.id.textTelegramAccount);
        if (textTelegramAccount != null) {
            textTelegramAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openTelegramUser("Pro101");
                }
            });
        }

        // QR code ImageView is ready in layout
        // To display QR code, add qr_code.png or qr_code.jpg to app/src/main/res/drawable/
        // and set it in activity_contact.xml: android:src="@drawable/qr_code"
    }

    private void openTelegramChannel(String channelName) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/" + channelName));
        startActivity(intent);
    }

    private void openTelegramUser(String username) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/" + username));
        startActivity(intent);
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}