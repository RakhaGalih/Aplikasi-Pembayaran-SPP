package com.lleans.spp_kelompok_2.ui.main.petugas.aktivitas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lleans.spp_kelompok_2.Abstract;
import com.lleans.spp_kelompok_2.databinding.AktivitasPetugasBinding;
import com.lleans.spp_kelompok_2.domain.model.pembayaran.PembayaranDataList;
import com.lleans.spp_kelompok_2.network.ApiClient;
import com.lleans.spp_kelompok_2.network.ApiInterface;
import com.lleans.spp_kelompok_2.ui.session.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Aktivitas extends Fragment implements Abstract {

    private AktivitasPetugasBinding binding;
    private SessionManager sessionManager;
    private NavController nav;

    public Aktivitas() {
        // Required empty public constructor
    }

    private void setAdapter(List<DetailsItemPembayaran> data) {
        AktivitasCardAdapter cardAdapter = new AktivitasCardAdapter(data, nav);
        binding.rvKelas.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvKelas.setAdapter(cardAdapter);
    }

    private void getAktivitas() {
        Call<PembayaranDataList> pembayaranDataListCall;
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        pembayaranDataListCall = apiInterface.getPembayaran(
                "Bearer " + sessionManager.getUserDetail().get(SessionManager.TOKEN),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        pembayaranDataListCall.enqueue(new Callback<PembayaranDataList>() {
            @Override
            public void onResponse(Call<PembayaranDataList> call, Response<PembayaranDataList> response) {
                if (response.body() != null && response.isSuccessful()) {
                    isLoading(false);
                    setAdapter(response.body().getDetails());
                } else {
                    isLoading(false);
                    toaster(response.message());
                }
            }

            @Override
            public void onFailure(Call<PembayaranDataList> call, Throwable t) {

            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nav = Navigation.findNavController(view);
        binding.refresher.setOnRefreshListener(() -> {
            getAktivitas();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = AktivitasPetugasBinding.inflate(inflater, container, false);
        sessionManager = new SessionManager(getContext());
        getAktivitas();
        return binding.getRoot();
    }

    @Override
    public void isLoading(Boolean isLoading) {

    }

    @Override
    public void toaster(String text) {

    }
}