package family.ttyy.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import ttyy.family.loggerl.L;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        L.initStoredFileContext(this);

        a();
    }

    public void a() {
        b();
    }

    public void b() {
        c();

    }

    public void c() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append("TestLine" + i);
        }
        Log.i("Navive", sb.toString());

        L.d("YICHU", sb.toString());
    }
}
