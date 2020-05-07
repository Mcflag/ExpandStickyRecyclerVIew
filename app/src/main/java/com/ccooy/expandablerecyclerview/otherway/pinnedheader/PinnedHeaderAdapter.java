package com.ccooy.expandablerecyclerview.otherway.pinnedheader;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PinnedHeaderAdapter<T, VH extends RecyclerView.ViewHolder> extends PagedListAdapter<T, VH> {

	protected PinnedHeaderAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
		super(diffCallback);
	}

	/**
	 * 判断该position对应的位置是要固定
	 *
	 * @param position adapter position
	 * @return true or false
	 */
	public abstract boolean isPinnedPosition(int position);


	public RecyclerView.ViewHolder onCreatePinnedViewHolder(ViewGroup parent, int viewType) {
		return onCreateViewHolder(parent, viewType);
	}

	public void onBindPinnedViewHolder(VH holder, int position) {
		onBindViewHolder(holder, position);
	}

}
