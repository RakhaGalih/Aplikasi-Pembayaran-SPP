package com.lleans.spp_kelompok_2.ui.main.petugas.petugas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.lleans.spp_kelompok_2.R;
import com.lleans.spp_kelompok_2.domain.Utils;
import com.lleans.spp_kelompok_2.domain.model.petugas.DetailsItemPetugas;
import com.lleans.spp_kelompok_2.domain.model.petugas.PetugasSharedModel;
import com.lleans.spp_kelompok_2.ui.launcher.LauncherFragment;
import com.lleans.spp_kelompok_2.ui.utils.UtilsUI;

import java.util.List;

public class PetugasCardAdapter extends RecyclerView.Adapter<PetugasCardAdapter.PetugasCardViewHolder> {

    private final List<DetailsItemPetugas> listdata;
    private final NavController navController;

    private Context context;

    public PetugasCardAdapter(List<DetailsItemPetugas> list, NavController navController) {
        this.listdata = list;
        this.navController = navController;
    }


    @NonNull
    @Override
    public PetugasCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_main, parent, false);
        context = view.getContext();
        return new PetugasCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PetugasCardViewHolder holder, int position) {
        DetailsItemPetugas data = listdata.get(position);
        holder.name.setText(data.getNamaPetugas());
        holder.uname.setText(data.getUsername());
        UtilsUI.nicknameBuilder(context, data.getNamaPetugas(), holder.nick, holder.nickFrame);
        holder.cardView.setOnClickListener(v -> {
            PetugasSharedModel sharedModel = new ViewModelProvider((LauncherFragment) context).get(PetugasSharedModel.class);
            sharedModel.updateData(data);
            navController.navigate(R.id.action_petugas_petugas_to_detailPetugas);
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class PetugasCardViewHolder extends RecyclerView.ViewHolder {
        TextView name, uname, nick;
        CardView cardView;
        FrameLayout nickFrame;

        public PetugasCardViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.title);
            uname = itemView.findViewById(R.id.secondaryText);
            nick = itemView.findViewById(R.id.nick);

            cardView = itemView.findViewById(R.id.card);
            nickFrame = itemView.findViewById(R.id.nickFrame);
        }
    }
}
