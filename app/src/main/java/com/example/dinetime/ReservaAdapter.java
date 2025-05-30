// ReservaAdapter.java
package com.example.dinetime;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dinetime.database.Reserva;
import com.example.dinetime.database.Restaurante;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder> {
    private final List<Reserva> reservas;
    private final Map<Integer, Restaurante> restauranteMap;
    private final Map<String, Integer> imagenesRestaurantes;

    public ReservaAdapter(List<Reserva> reservas, Map<Integer, Restaurante> restauranteMap, Map<String, Integer> imagenesRestaurantes) {
        this.reservas = reservas;
        this.restauranteMap = restauranteMap;
        this.imagenesRestaurantes = imagenesRestaurantes;
    }

    @NonNull
    @Override
    public ReservaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reserva, parent, false);
        return new ReservaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaViewHolder holder, int position) {
        Reserva reserva = reservas.get(position);
        Restaurante restaurante = restauranteMap.get(reserva.restauranteId);
        if (restaurante != null) {
            holder.tvNombreRestaurante.setText(restaurante.nombre);
            holder.tvDireccionRestaurante.setText(restaurante.direccion);
            Integer imgRes = imagenesRestaurantes.get(restaurante.nombre);
            if (imgRes != null) {
                holder.ivRestaurante.setImageResource(imgRes);
            }
        }
        holder.tvFechaReserva.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(reserva.fechaReserva));
        holder.tvEstadoReserva.setText(reserva.estado);
    }

    @Override
    public int getItemCount() {
        return reservas.size();
    }

    static class ReservaViewHolder extends RecyclerView.ViewHolder {
        ImageView ivRestaurante;
        TextView tvNombreRestaurante, tvDireccionRestaurante, tvFechaReserva, tvEstadoReserva;

        ReservaViewHolder(View itemView) {
            super(itemView);
            ivRestaurante = itemView.findViewById(R.id.ivRestaurante);
            tvNombreRestaurante = itemView.findViewById(R.id.tvNombreRestaurante);
            tvDireccionRestaurante = itemView.findViewById(R.id.tvDireccionRestaurante);
            tvFechaReserva = itemView.findViewById(R.id.tvFechaReserva);
            tvEstadoReserva = itemView.findViewById(R.id.tvEstadoReserva);
        }
    }
}
