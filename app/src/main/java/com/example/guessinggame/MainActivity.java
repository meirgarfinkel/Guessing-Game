package com.example.guessinggame;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Snackbar mSnackBar;
    private GuessingGame mGame;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("GAME", mGame.getJSONStringFromThis());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupFAB();
        setupSnackbar();
        setupNewGame(savedInstanceState);
    }

    private void setupNewGame(Bundle savedInstanceState){
        mGame = savedInstanceState != null
                ? GuessingGame.getGuessingGameObjectFromJSONString(
                savedInstanceState.getString("GAME"))
                : new GuessingGame();
    }

    private void setupSnackbar(){
        mSnackBar =
                Snackbar.make(findViewById(android.R.id.content), getString(R.string.app_name),
                        Snackbar.LENGTH_LONG);
    }

    private void setupToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupFAB(){
        ExtendedFloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> mGame.startNewGame());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("unused")
    public void pick123(View view) {
        Button currentButton = (Button) view;
        String currentButtonText = currentButton.getText().toString();
        String resultsText = currentButtonText.equals(String.valueOf(mGame.getWinningNumber()))
                ? String.format(Locale.US, "Yes! %s is the right number.", currentButtonText)
                : String.format(Locale.US, "Sorry. %s is not the right number.", currentButtonText);

        mSnackBar.setText(resultsText);
        mSnackBar.show();
    }
}