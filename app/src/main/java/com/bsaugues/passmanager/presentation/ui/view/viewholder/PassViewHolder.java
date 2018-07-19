package com.bsaugues.passmanager.presentation.ui.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.bsaugues.passmanager.R;
import com.bsaugues.passmanager.presentation.ui.view.viewdatawrapper.PassEntityDataWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PassViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.recyclerview_pass_item_content)
    TextView content;

    public PassViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindPass(final PassEntityDataWrapper passEntity) {
        content.setText(Html.fromHtml(passEntity.getFullPassDetailString()));
    }
}
