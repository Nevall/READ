package com.interest.myapplication.Dao;

import java.util.List;

import com.interest.myapplication.entity.StoriesEntity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.Where;

import android.content.Context;
import android.database.SQLException;

/**
 * Created by Android on 2016/4/18.
 */
public class StoriesEntityDao {
    private Context context;
    private Dao<StoriesEntity, Integer> storiesEntityIntegerDao;
    private DatabaseHelper helper;

    public StoriesEntityDao(Context context) {
        this.context = context;
        try {
            helper = DatabaseHelper.getHelper(context);
            storiesEntityIntegerDao = helper.getDao(StoriesEntity.class);
        } catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
    }
    public boolean add(StoriesEntity storiesEntity) {
        try {
        	if (queryById(storiesEntity.getId())) {
        		//数据库已经存在该数据
        		return false;//没插入数�?
			}
        	//数据库不存在该数�?
        	storiesEntityIntegerDao.create(storiesEntity);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return true;
    }



    public boolean queryById(int id) {
        List<StoriesEntity> list =  null;
        try {
            list = storiesEntityIntegerDao.queryBuilder().where().eq("id", id).query();
            if (!list.isEmpty()) {
            	//数据库已存在该数�?
				return true;
			}
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<StoriesEntity> listAll() {
        List<StoriesEntity> list = null;
        try {
            list = storiesEntityIntegerDao.queryForAll();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void delete(int id) {
        try {
             DeleteBuilder<StoriesEntity, Integer> builder = storiesEntityIntegerDao.deleteBuilder();
             builder.where().eq("id", id);
             builder.delete();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

}
