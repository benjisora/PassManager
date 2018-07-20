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



        ReservationRemoteEntity remote;
        String json;

        if (id.equals("1234")) {
            json = "{\"id\":123456789,\"first_name\":\"Tyrion\",\"last_name\":\"Lannister\",\"last_scan\":123456789,\"pass_list\":[{\"type\":\"WEEK_END_PASS\",\"amount\":2},{\"type\":\"SINGLE_DAY_PASS\",\"amount\":1}]}";
            remote = gson.fromJson(json, ReservationRemoteEntity.class);
        } else {
            json = "{\"id\":1234,\"first_name\":\"Jon\",\"last_name\":\"Snow\",\"pass_list\":[{\"type\":\"WEEK_END_PASS\",\"amount\":2},{\"type\":\"SINGLE_DAY_PASS\",\"amount\":1}]}";
            remote = gson.fromJson(json, ReservationRemoteEntity.class);
        }
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
