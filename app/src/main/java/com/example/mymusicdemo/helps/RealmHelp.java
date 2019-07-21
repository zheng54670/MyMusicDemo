package com.example.mymusicdemo.helps;

import android.content.Context;

import com.example.mymusicdemo.migration.Migration;
import com.example.mymusicdemo.models.AlbumModel;
import com.example.mymusicdemo.models.MusicModel;
import com.example.mymusicdemo.models.MusicSourceModel;
import com.example.mymusicdemo.models.UserModel;
import com.example.mymusicdemo.utils.DataUtils;
import com.example.mymusicdemo.utils.UserUtils;

import java.io.FileNotFoundException;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmHelp {

    private Realm mRealm;

    public RealmHelp() {
        mRealm = Realm.getDefaultInstance();
    }


    /**
     * Realm发生结构性变化 (模型中的字段发生了新增/修改/删除) 的时候, 我们就需要对数据库进行迁移
     */


    /**
     * 告诉Realm数据需要迁移,并且为Realm设置最新的配置
     */
    public static void migration(){
        RealmConfiguration configuration = getRealmConfiguration();
        //Realm 设置最新的配置
        Realm.setDefaultConfiguration(configuration);

//        告诉Realm数据需要迁移
        try {
            Realm.migrateRealm(configuration);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * 返回RealmConfiguration
     * @return
     */
    private static RealmConfiguration getRealmConfiguration(){
        return new RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration(new Migration())
                .build();
    }

    /**
     * 关闭数据库
     */
    public void close() {
        if (mRealm != null && !mRealm.isClosed()) {
            mRealm.close();
        }
    }

    /**
     * 保存用户信息
     */
    public void saveUser(UserModel userModel) {
        mRealm.beginTransaction();
        mRealm.insert(userModel);
        mRealm.commitTransaction();
    }


    /**
     * 返回所有的用户
     */
    public List<UserModel> getAllUser() {
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        RealmResults<UserModel> results = query.findAll();
        return results;
    }

    /**
     * 验证用户信息
     */
    public boolean validateUser(String phone, String password) {

        boolean result = false;
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        query.equalTo("phone", phone)
                .equalTo("password", password);
        UserModel userModel = query.findFirst();
        if (userModel != null) {
            result = true;
        }
        return result;
    }


    /**
     * 获取当前用户
     */

    public UserModel getUser() {
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        UserModel userModel = query.equalTo("phone", UserHelp.getInstance().getPhone()).findFirst();
        return userModel;
    }

    /**
     * 修改密码
     */
    public void changePasswod(String password) {
        UserModel userModel = getUser();
        mRealm.beginTransaction();

        userModel.setPassword(password);
        mRealm.commitTransaction();
    }

    /**
     * 1.用户登录后,存放数据
     * 2.用户退出,删除数据
     */

    /**
     * 保存音乐源数据
     *
     * @param context
     */
    public void setMusicSource(Context context) {
//        拿到资源文件中的数据
        String musicSourceJson = DataUtils.getJsonFromAssets(context, "DataSource.json");

        mRealm.beginTransaction();
        mRealm.createObjectFromJson(MusicSourceModel.class,musicSourceJson);
        mRealm.commitTransaction();
    }

    /**
     * 删除音乐源数据
     * 1.RealmResult delete方法
     * 2.Realm delete 删除模型下所有数据
     */
    public void removeMusicSource(){
        mRealm.beginTransaction();
        mRealm.delete(MusicSourceModel.class);
        mRealm.delete(MusicModel.class);
        mRealm.delete(AlbumModel.class);
        mRealm.commitTransaction();
    }

    /**
     * 返回音乐源数据到屏幕
     */
    public MusicSourceModel getMusicSource(){
        return mRealm.where(MusicSourceModel.class).findFirst();
    }

    /**
     * 返回歌单
     */
    public AlbumModel getAlbum(String albumID){
        return mRealm.where(AlbumModel.class).equalTo("albumId",albumID).findFirst();

    }

    /**
     * 返回音乐
     */
    public MusicModel getMusic (String musicID){
        return  mRealm.where(MusicModel.class).equalTo("musicId",musicID).findFirst();
    }

}
