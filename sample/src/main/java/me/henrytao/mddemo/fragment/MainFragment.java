package me.henrytao.mddemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.henrytao.mddemo.R;
import me.henrytao.mddemo.activity.ExampleActivity;

public class MainFragment extends Fragment {

  public MainFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_main, container, false);
    ButterKnife.inject(this, view);
    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.reset(this);
  }

  @OnClick(R.id.goto_button)
  protected void onGotoButtonClicked() {
    startActivity(ExampleActivity.getIntent(getActivity(), ExampleMdButtonFragment.class, "Button"));
  }

  @OnClick(R.id.goto_listview)
  protected void onGotoListviewClicked() {
    startActivity(ExampleActivity.getIntent(getActivity(), ExampleMdListFragment.class, "ListView"));
  }

  @OnClick(R.id.goto_textview)
  protected void onGotoTextviewClicked() {
    startActivity(ExampleActivity.getIntent(getActivity(), ExampleMdTextFragment.class, "TextView"));
  }

}
