package com.triskelapps.alcalasuena.interactor;

import android.content.Context;

import com.triskelapps.alcalasuena.R;
import com.triskelapps.alcalasuena.api.Api;
import com.triskelapps.alcalasuena.base.BaseInteractor;
import com.triskelapps.alcalasuena.base.BaseView;
import com.triskelapps.alcalasuena.model.Band;
import com.triskelapps.alcalasuena.model.Tag;
import com.triskelapps.alcalasuena.util.Util;

import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by julio on 14/02/16.
 */
public class BandInteractor extends BaseInteractor {



    public interface BandsCallback {

        void onResponse(List<Band> bands);

        void onError(String error);
    }

    public BandInteractor(Context context, BaseView baseView) {
        this.baseView = baseView;
        this.context = context;

    }

    public static void initializeBands() {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                try {
                    Tag tag1 = realm.createObject(Tag.class, 1);
                    tag1.setName("Jazz");

                    Tag tag2 = realm.createObject(Tag.class, 2);
                    tag2.setName("Blues");


                    Band band1 = realm.createObject(Band.class, 1);
                    band1.setName("Los Pepes");
                    band1.setTag(tag1);


                    Band band2 = realm.createObject(Band.class, 2);
                    band2.setName("Las Juanas");
                    band2.setTag(tag2);

                } catch (RealmPrimaryKeyConstraintException e) {

                }

            }
        });

    }


    public List<Band> getBandsDB() {
        return Realm.getDefaultInstance().where(Band.class).findAll();
    }

    public List<Band> getBandsDB(String nameFilter) {
        return Realm.getDefaultInstance().where(Band.class).contains(Band.NAME, nameFilter, Case.INSENSITIVE).findAll();
    }

    public void getBands(final BandsCallback callback) {

        if (!Util.isConnected(context)) {
            baseView.toast(R.string.no_connection);
            return;
        }

        baseView.setRefresing(true);

        getApi().getBands()
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).doOnTerminate(actionTerminate)
                .subscribe(new Observer<List<Band>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Band> bands) {

                        baseView.setRefresing(false);

                        storeBands(bands);

                        callback.onResponse(bands);


                    }
                });


    }

    private void storeBands(List<Band> bands) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insert(bands);
        realm.commitTransaction();
    }


    private Api getApi() {
        return getApi(Api.class);
    }


}
