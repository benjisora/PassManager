package com.bsaugues.passmanager.data.manager;

import com.bsaugues.passmanager.data.entity.remote.PassRemoteEntity;
import com.bsaugues.passmanager.data.entity.remote.ReservationRemoteEntity;
import com.bsaugues.passmanager.data.values.NetValues;
import com.bsaugues.passmanager.data.values.PassTypeValues;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class NetManagerImpl implements NetManager {

    private Gson gson;
    private ApiService service;

    private interface ApiService {
        @GET("reservation")
        Observable<ReservationRemoteEntity> getReservationFromId(@Query("reservation_id") String reservationId);
    }

    @Inject
    public NetManagerImpl(Gson gson) {
        this.gson = gson;
        initRetrofit();
    }

    @Override
    public Observable<ReservationRemoteEntity> getReservationFromId(String id) {
//        return service.getReservationFromId(id)
//                .map(new Function<ReservationRemoteEntity, ReservationRemoteEntity>() {
//                    @Override
//                    public ReservationRemoteEntity apply(ReservationRemoteEntity reservationRemoteEntity) {
//                        if (reservationRemoteEntity != null) {
//                            return reservationRemoteEntity;
//                        }
//                        throw new EmptyReservationEntityException();
//                    }
//                });

        ReservationRemoteEntity remote = new ReservationRemoteEntity();
        remote.setId(id);
        remote.setOwnerFirstName("Benji");
        remote.setOwnerLastName("Sora");

        List<PassRemoteEntity> passList = new ArrayList<>();
        passList.add(new PassRemoteEntity(PassTypeValues.SINGLE_DAY_PASS, 2L));
        passList.add(new PassRemoteEntity(PassTypeValues.WEEK_END_PASS, 1L));

        remote.setPassList(passList);

        return Observable.just(remote);
    }

    private void initRetrofit() {
        service = new Retrofit.Builder()
                .baseUrl(NetValues.API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService.class);
    }
}
