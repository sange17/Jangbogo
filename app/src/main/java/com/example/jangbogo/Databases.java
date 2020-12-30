package com.example.jangbogo;

import android.provider.BaseColumns;

final class Databases {
    public static final class CreateDB implements BaseColumns{
        public static final String NAME = "name";
        public static final String ORDERDATE = "orderdate";
        public static final String numbers = "numbers";
        public static final String _TABLENAME0 = "refrigerator";

        public static final String _CREATE0= "CREATE TABLE IF NOT EXISTS "+_TABLENAME0
                +"("
                +_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +NAME + " TEXT NOT NULL, "
                +ORDERDATE + " TEXT NOT NULL, "
                +numbers + " INTEGER NOT NULL );";

    }
}
