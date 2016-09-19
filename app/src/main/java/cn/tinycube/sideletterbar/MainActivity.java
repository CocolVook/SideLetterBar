package cn.tinycube.sideletterbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import cn.tinycube.widget.SideLetterBar;

public class MainActivity extends AppCompatActivity {

    private SideLetterBar letterBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        letterBar = (SideLetterBar) findViewById(R.id.letterBar);
        assert letterBar != null;
        letterBar.setLetters(new String[] {"定", "热", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"});
        letterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                Toast.makeText(MainActivity.this, letter, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
