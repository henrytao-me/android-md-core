package me.henrytao.mddemo.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.henrytao.mdwidget.activity.MdDrawerLayoutActivity;
import me.henrytao.mddemo.R;

public class MainActivity extends MdDrawerLayoutActivity {

  @InjectView(R.id.toolbar)
  Toolbar vToolbar;

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_navigation_drawer_right:
        openDrawer(NAVIGATION_DRAWER_TYPE.RIGHT);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected int getDrawerContentResource() {
    return R.id.content;
  }

  @Override
  protected int getDrawerLayoutResource() {
    return R.id.container;
  }

  @Override
  protected int getDrawerNavigationResource() {
    return R.id.navigation;
  }

  @Override
  protected int getDrawerNavigationRightResource() {
    return R.id.navigation_right;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);

    setSupportActionBar(vToolbar);
    vToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        openDrawer();
      }
    });
  }
}
