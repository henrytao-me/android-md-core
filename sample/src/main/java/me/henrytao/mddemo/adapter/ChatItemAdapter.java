/*
 * Copyright 2015 "Henry Tao <hi@henrytao.me>"
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.henrytao.mddemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.henrytao.mdcore.adapter.RecyclerViewAdapter;
import me.henrytao.mdcore.config.Constants;
import me.henrytao.mddemo.R;

/**
 * Created by henrytao on 5/21/15.
 */
public class ChatItemAdapter extends RecyclerViewAdapter<RecyclerView.ViewHolder> {

  Context mContext;

  List<Integer> mData;

  View mFooterView;

  View mHeaderView;

  public ChatItemAdapter(Context context, View headerView, View footerView, List<Integer> data) {
    mContext = context;

    mHeaderView = headerView;
    mFooterView = footerView;
    mData = data;
    if (data == null) {
      mData = new ArrayList<>();
      // Todo: need to remove this sample data
      for (int i = 0; i < 50; i++) {
        mData.add(i);
      }
    }
    addSection(0, "Recent chat");
    addSection(3, "Previous chat");
  }

  @Override
  public int getDataItemCount() {
    return mData.size();
  }

  @Override
  public boolean hasFooter() {
    return mFooterView != null;
  }

  @Override
  public boolean hasHeader() {
    return mHeaderView != null;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, int dataPosition) {
    if (holder instanceof SectionHolder) {
      ((SectionHolder) holder).bind(getSections().get(dataPosition));
    } else if (holder instanceof ItemHolder) {
      ((ItemHolder) holder).bind(mData.get(dataPosition));
    }
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, Constants.ItemViewType itemViewType) {
    if (itemViewType == Constants.ItemViewType.HEADER) {
      return new HeaderHolder(mHeaderView);
    } else if (itemViewType == Constants.ItemViewType.FOOTER) {
      return new FooterHolder(mFooterView);
    } else if (itemViewType == Constants.ItemViewType.SECTION) {
      return SectionHolder.newInstance(getContext(), parent);
    } else if (itemViewType == Constants.ItemViewType.ITEM) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chat_item, parent, false);
      ItemHolder itemHolder = new ItemHolder(view);
      return itemHolder;
    }
    return BlankHolder.newInstance(getContext());
  }

  public Context getContext() {
    return mContext;
  }

  public class FooterHolder extends RecyclerView.ViewHolder {

    public FooterHolder(View itemView) {
      super(itemView);
    }
  }

  public class HeaderHolder extends RecyclerView.ViewHolder {

    public HeaderHolder(View itemView) {
      super(itemView);
    }
  }

  public class ItemHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.title)
    TextView vTitle;

    public ItemHolder(View itemView) {
      super(itemView);
      ButterKnife.inject(this, itemView);
    }

    public void bind(int data) {
      vTitle.setText("Hello moto " + String.valueOf(data));
    }
  }
}
