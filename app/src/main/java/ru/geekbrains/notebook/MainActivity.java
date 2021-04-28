package ru.geekbrains.notebook;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.navigation.NavigationView;
import ru.geekbrains.notebook.ui.NoteListFragment;
import ru.geekbrains.notebook.ui.CardDataPublisher;

public class MainActivity extends AppCompatActivity implements DialogFragmentInterface {

    private CardDataPublisher cardDataPublisher = new CardDataPublisher();
    private Navigation navigation = new Navigation(getSupportFragmentManager());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        Fragment fragment = new NoteListFragment();
        navigation.addFragment(fragment);

    }

    private void initView() {
        Toolbar toolbar = initToolbar();
        DrawerLayout drawer = initDrawer(toolbar);
        initNavigation(drawer);
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        return toolbar;
    }

    private DrawerLayout initDrawer(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //добавляем сэндвич кнопку
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.nav_open_drawer,
                R.string.nav_close_drawer);
        //слушаем кнопку
        drawer.addDrawerListener(toggle);
        //синхронизируем вид кнопки
        toggle.syncState();

        return drawer;
    }

    private void initNavigation(DrawerLayout drawer) {
        NavigationView navigationView = findViewById(R.id.nav_view);
        //слушаем навигационное меню
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (navigateFragment(id)) {
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            return false;
        });
    }


    //обработка щелчка пользователя
    private boolean navigateFragment(int id) {
        Log.i("PLACEHOLDER", "selected option");
        return true;
    }


    public Navigation getNavigation() {
        return navigation;
    }

    public CardDataPublisher getCardDataPublisher() {
        return cardDataPublisher;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public int onYesButtonClick() {
        return 0;
    }

    @Override
    public int onCancelButtonClick() {
        return -1;
    }
}