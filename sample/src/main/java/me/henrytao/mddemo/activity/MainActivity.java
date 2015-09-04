package me.henrytao.mddemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import me.henrytao.mddemo.R;

public class MainActivity extends AppCompatActivity {

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
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    //ButterKnife.inject(this);
  }

  //@OnClick(R.id.goto_button)
  //protected void onGotoButtonClicked() {
  //  startActivity(ExampleActivity.getIntent(this, ExampleMdButtonFragment.class, "Button"));
  //}
  //
  //@OnClick(R.id.goto_drawer_layout_activity)
  //protected void onGotoDrawerLayoutActivity() {
  //  startActivity(DrawerLayoutActivity.getIntent(this));
  //}
  //
  //@OnClick(R.id.goto_drawer_layout_pager_tab_activity)
  //protected void onGotoDrawerLayoutPagerTabActivity() {
  //  startActivity(DrawerLayoutPagerTabActivity.getIntent(this));
  //}
  //
  //@OnClick(R.id.goto_list_view)
  //protected void onGotoListViewClicked() {
  //  startActivity(ExampleActivity.getIntent(this, ExampleMdListFragment.class, "ListView"));
  //}
  //
  //@OnClick(R.id.goto_pager_tab_activity)
  //protected void onGotoPagerTabActivity() {
  //  startActivity(PagerTabActivity.getIntent(this));
  //}
  //
  //@OnClick(R.id.goto_textview)
  //protected void onGotoTextviewClicked() {
  //  startActivity(ExampleActivity.getIntent(this, ExampleMdTextFragment.class, "TextView"));
  //}
  //
  //@OnClick(R.id.goto_toolbar_activity)
  //protected void onGotoToolbarActivity() {
  //  startActivity(ToolbarActivity.getIntent(this));
  //}
}
