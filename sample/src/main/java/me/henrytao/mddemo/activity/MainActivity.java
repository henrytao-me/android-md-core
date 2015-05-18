package me.henrytao.mddemo.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.henrytao.mddemo.R;
import me.henrytao.mddemo.fragment.ExampleMdButtonFragment;
import me.henrytao.mddemo.fragment.ExampleMdListFragment;
import me.henrytao.mddemo.fragment.ExampleMdTextFragment;
import me.henrytao.mdwidget.activity.MdToolbarActivity;

public class MainActivity extends MdToolbarActivity {

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected int getToolbarResource() {
    return R.id.toolbar;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);
  }

  @OnClick(R.id.goto_button)
  protected void onGotoButtonClicked() {
    startActivity(ExampleActivity.getIntent(this, ExampleMdButtonFragment.class, "Button"));
  }

  @OnClick(R.id.goto_drawer_layout_activity)
  protected void onGotoDrawerLayoutActivity() {
    startActivity(DrawerLayoutActivity.getIntent(this));
  }

  @OnClick(R.id.goto_listview)
  protected void onGotoListviewClicked() {
    startActivity(ExampleActivity.getIntent(this, ExampleMdListFragment.class, "ListView"));
  }

  @OnClick(R.id.goto_textview)
  protected void onGotoTextviewClicked() {
    startActivity(ExampleActivity.getIntent(this, ExampleMdTextFragment.class, "TextView"));
  }

  @OnClick(R.id.goto_toolbar_activity)
  protected void onGotoToolbarActivity() {
    startActivity(ToolbarActivity.getIntent(this));
  }
}
