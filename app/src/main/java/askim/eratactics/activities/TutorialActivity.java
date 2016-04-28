package askim.eratactics.activities;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

import askim.eratactics.R;

public class TutorialActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        Button back = (Button) findViewById(R.id.tutorialBackButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
