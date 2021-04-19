package fr.leo.bdd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final String PREFS_NAME = "preferences_file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "planetesDB").build();

        PlaneteDao planeteDao = db.planeteDao();

        loadData(planeteDao);
    }

    private void loadData(final PlaneteDao planeteDao) {

        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        new Thread(new Runnable() {
            @Override
            public void run() {

                if (settings.getBoolean("is_data_loaded", true)) {
                    initData(planeteDao);
                    settings.edit().putBoolean("is_data_loaded", false).commit();
                }

                final List<Planete> planetes = planeteDao.getAll();

            }
        }).start();

    }

    private void initData(PlaneteDao planeteDao) {

        ArrayList<Planete> planetes = new ArrayList<>();

        planetes.add(new Planete(1,"Mercure","4900", R.drawable.ic_baseline_brightness_1_24));
        planetes.add(new Planete(2,"Venus","12000", R.drawable.ic_baseline_brightness_1_24));
        planetes.add(new Planete(3,"Terre","12800", R.drawable.ic_baseline_brightness_1_24));
        planetes.add(new Planete(4,"Mars","6800", R.drawable.ic_baseline_brightness_1_24));
        planetes.add(new Planete(5,"Jupiter","144000", R.drawable.ic_baseline_brightness_1_24));
        planetes.add(new Planete(6,"Saturne","120000", R.drawable.ic_baseline_brightness_1_24));
        planetes.add(new Planete(7,"Uranus","52000", R.drawable.ic_baseline_brightness_1_24));
        planetes.add(new Planete(8,"Neptune","50000", R.drawable.ic_baseline_brightness_1_24));
        planetes.add(new Planete(9,"Pluton","2300", R.drawable.ic_baseline_brightness_1_24));

        for (int index = 0; index < planetes.size(); index++) {
            Planete planete = planetes.get(index);
            planeteDao.insert(planete);
        }
    }
}