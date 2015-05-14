package me.henrytao.mddemo.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.InjectView;
import me.henrytao.mddemo.R;


public class MainActivity extends MdDrawerLayoutActivity {

  @InjectView(R.id.content)
  View vDrawerContent;

  @InjectView(R.id.container)
  DrawerLayout vDrawerLayout;

  @InjectView(R.id.drawer)
  View vDrawerNavigation;

  @Override
  public View getDrawerContent() {
    return vDrawerContent;
  }

  @Override
  public DrawerLayout getDrawerLayout() {
    return vDrawerLayout;
  }

  @Override
  public View getDrawerNavigation() {
    return vDrawerNavigation;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }
}
