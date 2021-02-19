package com.abasynuniversity.TransportSystem.forStudents.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import com.abasynuniversity.TransportSystem.forStudents.R;

import static android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD;

public class About_us extends AppCompatActivity {
    TextView tv_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        tv_about = findViewById(R.id.tv_about_us);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        String about =" <p>Abasyn University Peshawar is chartered by the Government of Khyber Pakhtunkhwa (KPK) and recognized by the Higher Education Commission (HEC), Pakistan. Abasyn University was the only university in KPK which was awarded category <b>\"W\"</b> at the inception which was the highest category to be awarded to any institution in the old ranking system by HEC. By the Grace of ALLAH and the support of sponsors and staff, according to the recent Higher Education Commission (HEC) ranking in 2014 announced in May 2015, Abasyn University is:</p>\n" +
                "    <p><b><i>Ranked #1 in Khyber Pakhtunkhwa in Private Sector Universities (General Category).</i></b></p>\n" +
                "     <p><b><i>Ranked #6 in the all Private Sector Universities (General Category).</i></b></p>\n" +
                "    <b><i>Ranked #25 in the all Private and Public Universities of Pakistan (General Category).</i></b>\n" +
                "    <p>Abasyn University offers degree programs in various disciplines including Engineering, Computer Science, Business Administration, Pharmacy, Microbiology, Education and Technology. National Computing Education Accreditation Council (NCEAC), Pakistan has accredited BS in Computer Science and BS in Software Engineering programs offered by the University. The University has been accredited by the Pharmacy Council of Pakistan. The University is also allowed by the Pakistan Engineering Council to offer BE in Electrical Engineering and BE in Civil Engineering programs.</p>\n" +
                "    ";

        tv_about.setText(Html.fromHtml(about));
        tv_about.setMovementMethod(new ScrollingMovementMethod());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tv_about.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}