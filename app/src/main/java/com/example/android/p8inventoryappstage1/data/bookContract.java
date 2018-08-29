package com.example.android.p8inventoryappstage1.data;
import android.provider.BaseColumns;
/**
 * API Contract for the book store app.
 */
public final class bookContract {
    // To prevent someone from accidentally instantiating the contract class, give it an empty constructor.
    private bookContract() {
    }
    /**
     * Inner class that defines constant values for the books database table. Each entry in the table represents a single book.
     */
    public static final class bookEntry implements BaseColumns {
        /**
         * Name of database table for books
         */
        public final static String TABLE_NAME = "books_inventory";
        /**
         * Unique ID number for the book (only for use in the database table).
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;
        /**
         * Name of the book.
         * Type: TEXT
         */
        public final static String COLUMN_PRODUCT_NAME = "title";
        /**
         * Price of the book.
         * Type: INTEGER
         */
        public final static String COLUMN_PRICE = "price";
        /**
         * Quantity of the book.
         * Type: INTEGER
         */
        public final static String COLUMN_QUANTITY = "quantity";
        /**
         * Book supplier's name.
         * Type: TEXT
         */
        public final static String COLUMN_SUPPLIER_NAME = "supplier_name";
        /**
         * Book supplier's phone number.
         * Type: TEXT
         */
        public final static String COLUMN_SUPPLIER_PHONE = "supplier_phone";
    }
}


