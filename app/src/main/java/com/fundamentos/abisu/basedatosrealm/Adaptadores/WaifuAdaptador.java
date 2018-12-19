package com.fundamentos.abisu.basedatosrealm.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fundamentos.abisu.basedatosrealm.Modelos.WaifuModelo;
import com.fundamentos.abisu.basedatosrealm.R;

import java.util.List;

public class WaifuAdaptador extends RecyclerView.Adapter<WaifuAdaptador.ViewHolderWaifu> implements
        View.OnClickListener, View.OnLongClickListener {

    private Context context;
    private List<WaifuModelo> modeloList;
    private View.OnClickListener listener;
    private View.OnLongClickListener longClickListener;

    public WaifuAdaptador(Context context, List<WaifuModelo> modeloList) {
        this.context = context;
        this.modeloList = modeloList;
    }

    @NonNull
    @Override
    public WaifuAdaptador.ViewHolderWaifu onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_waifu_recycler,viewGroup, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new ViewHolderWaifu(view);

    }

    @Override
    public void onBindViewHolder(WaifuAdaptador.ViewHolderWaifu viewHolderWaifu, int i) {
        viewHolderWaifu.txtNombre.setText(modeloList.get(i).getNombre());
        viewHolderWaifu.txtDescripcion.setText(modeloList.get(i).getDescripcion());
        Glide.with(context).load(modeloList.get(i).getImagen()).into(viewHolderWaifu.imgWaifu);
    }

    @Override
    public int getItemCount() {
        return modeloList.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }
    }

    public void setOnLongClickListener(View.OnLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    @Override
    public boolean onLongClick(View v) {
        if (longClickListener != null){
            longClickListener.onLongClick(v);
        }
        return false;
    }

    public class ViewHolderWaifu extends RecyclerView.ViewHolder {
        TextView txtNombre,txtDescripcion;
        ImageView imgWaifu;

        public ViewHolderWaifu(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            imgWaifu = itemView.findViewById(R.id.imgWaifu);
        }
    }
}
