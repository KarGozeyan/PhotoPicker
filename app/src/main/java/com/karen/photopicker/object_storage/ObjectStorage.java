package com.karen.photopicker.object_storage;


import android.content.Context;
import android.util.Log;

import com.karen.photopicker.App;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

public class ObjectStorage {

    public static final String TAG = "ObjectStorage";

    private static ObjectStorage instance;

    public static ObjectStorage getInstance() {
        if (instance == null) {
            instance = new ObjectStorage();
        }
        return instance;
    }

    public static void clear() {
        instance = null;
    }

    private ObjectStorageCache cache;

    private ObjectStorage() {
        cache = new ObjectStorageCache();
    }

    private static HashMap<String, StorageKey> keysMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> StorageKey<T> key(String identifier, Class<T> type) {
        if (keysMap.containsKey(identifier)) {
            return keysMap.get(identifier);
        }

        StorageKey key = new StorageKey<>(identifier, type);
        keysMap.put(identifier, key);

        return key;
    }


    public <T extends Serializable> void put(StorageKey<T> key, T value) {
        serialize(key.identifier, value);
    }

    public <T extends Serializable> void deleteSerializable(StorageKey<T> key) throws FileNotFoundException {

        FileOutputStream fileOut = App.getInstance().openFileOutput(key.identifier, Context.MODE_PRIVATE);
        try {
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private <T extends Serializable> void serialize(String fileName, T obj) {
        try {
            FileOutputStream fileOut = App.getInstance().openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(obj);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            Log.e(TAG, "serialize: " + e.getMessage());
        }
        int a=0;
    }

    private Object deserialize(String fileName) {
        Object obj = null;

        try {
            FileInputStream fileIn = App.getInstance().openFileInput(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            obj = in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {

        }
        return obj;
    }

    public <T extends Serializable> T get(StorageKey<T> key) {

        Object obj = cache.get(key);

        if (obj == null) {
            obj = deserialize(key.identifier);

            if (obj != null) {
                cache.put(key, obj);
            }
        }

        if (obj == null) {
            return null;
        }

        return key.type.cast(obj);
    }
}
