package com.example.android.p8inventoryappstage1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.android.p8inventoryappstage1.data.bookContract.bookEntry;
import com.example.android.p8inventoryappstage1.data.bookDbHelper;

import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    /**
     * Database helper that will provide us access to the database
     */
    private bookDbHelper mDbHelper;
    /**
     * TextView that will display the books inventory table
     */
    private TextView displayView;
    /**
     * Button that will insert the book's text values to the inventory table
     */
    private Button buttonInsertDataBook;
    /**
     * Button that will query the books inventory table and display it
     */
    private Button buttonQueryDataBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // To access our database, we instantiate our subclass of SQLiteOpenHelper and pass the context, which is the current activity.
        mDbHelper = new bookDbHelper(this);
        // Find Views in activity_main.xml
        displayView = (TextView) findViewById(R.id.text_view_book);
        buttonInsertDataBook = (Button) findViewById(R.id.insert_data_book);
        buttonQueryDataBook = (Button) findViewById(R.id.query_data_book);
        // When buttonInsertDataBook is clicked, the test values are inserted into a new book row
        buttonInsertDataBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });
        // When buttonQueryDataBook is clicked, the books inventory table is displayed
        buttonQueryDataBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryData();
            }
        });
    }

    /**
     * Helper method to insert hardcoded book data into the database. For debugging purposes only.
     */
    private void insertData() {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // Create a ContentValues object where column names are the keys, and Le Petit Prince book attributes are the values.
        ContentValues values = new ContentValues();
        // Book values for test
        values.put(bookEntry.COLUMN_PRODUCT_NAME, "Le Petit Prince");
        values.put(bookEntry.COLUMN_PRICE, 50);
        values.put(bookEntry.COLUMN_QUANTITY, 4);
        values.put(bookEntry.COLUMN_SUPPLIER_NAME, "stimatzky");
        values.put(bookEntry.COLUMN_SUPPLIER_PHONE, "+97239468899");
        // Insert a new row for Le Petit Prince in the database, returning the ID of that new row.
        // The first argument for db.insert() is the books table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Le Petit Prince.
        long newRowId = db.insert(bookEntry.TABLE_NAME, null, values);
        Toast.makeText(this, "Book data saved in row: " + newRowId, Toast.LENGTH_SHORT).show();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of the books database.
     */
    private void queryData() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database you will actually use after this query.
        String[] projection = {
                bookEntry._ID,
                bookEntry.COLUMN_PRODUCT_NAME,
                bookEntry.COLUMN_PRICE,
                bookEntry.COLUMN_QUANTITY,
                bookEntry.COLUMN_SUPPLIER_NAME,
                bookEntry.COLUMN_SUPPLIER_PHONE};
        // Perform a query on the books table
        Cursor cursor = db.query(
                bookEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order
        try {
            // Create a header in the Text View that looks like this:
            //
            // The books table contains <number of rows in Cursor> books.
            // _id - title - price - quantity - supplier name - supplier phone
            //
            // In the while loop below, iterate through the rows of the cursor and display the information from each column in this order.
            displayView.setText("The books table contains " + cursor.getCount() + " books.\n\n");
            displayView.append(bookEntry._ID + " - " +
                    bookEntry.COLUMN_PRODUCT_NAME + " - " +
                    bookEntry.COLUMN_PRICE + " - " +
                    bookEntry.COLUMN_QUANTITY + " - " +
                    bookEntry.COLUMN_SUPPLIER_NAME + " - " +
                    bookEntry.COLUMN_SUPPLIER_PHONE + "\n");
            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(bookEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(bookEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(bookEntry.COLUMN_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(bookEntry.COLUMN_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(bookEntry.COLUMN_SUPPLIER_NAME);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(bookEntry.COLUMN_SUPPLIER_PHONE);
            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSupplierName = cursor.getString(supplierNameColumnIndex);
                String currentSupplierPhone = cursor.getString(supplierPhoneColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        currentSupplierName + " - " +
                        currentSupplierPhone));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its resources and makes it invalid.
            cursor.close();
        }
    }
}