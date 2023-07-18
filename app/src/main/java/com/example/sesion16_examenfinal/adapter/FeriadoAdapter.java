package com.example.sesion16_examenfinal.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sesion16_examenfinal.R;
import com.example.sesion16_examenfinal.model.Feriado;

import java.util.List;

public class FeriadoAdapter extends RecyclerView.Adapter<FeriadoAdapter.MyViewHolder> {
    private final Context context;
    private final List<Feriado> feriados;
    private final Activity activity;
    private Feriado feriado;

    public FeriadoAdapter(Activity activity, Context context, List<Feriado> feriados) {
        this.activity = activity;
        this.context = context;
        this.feriados = feriados;
    }

    @NonNull
    @Override
    public FeriadoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_holiday, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeriadoAdapter.MyViewHolder holder, int position) {
        if (feriados.size() > 0) {
            feriado = feriados.get(position);
            holder.idTextView.setText(String.valueOf(feriado.getIdFeriado()));
            holder.fechaTextView.setText(feriado.getFecha());
            holder.paisTextView.setText(feriado.getPais());
            holder.nombreTextView.setText(feriado.getNombreFeriado());
        }
    }

    @Override
    public int getItemCount() {
        return feriados.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView idTextView, fechaTextView, paisTextView, nombreTextView;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.idFeriadoTxt);
            fechaTextView = itemView.findViewById(R.id.fechaFeriadoTxt);
            paisTextView = itemView.findViewById(R.id.paisFeriadoTxt);
            nombreTextView = itemView.findViewById(R.id.nombreFeriadoTxt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
