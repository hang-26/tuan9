package com.example.oneexceriseactivity

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NotesDataHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "notesapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allnotes"
        private const val COLUM_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
        private const val COLUM_DATE = "date"

        // Singleton instance
        // Khi một biến được đánh dấu là @Volatile,
        // nó đảm bảo rằng tất cả các luồng đều nhìn thấy giá trị mới nhất của biến khi nó được cập nhật.
        @Volatile
        private var instance: NotesDataHelper? = null

        @Synchronized
        fun getInstance(context: Context): NotesDataHelper {
            if (instance == null) {
                instance = NotesDataHelper(context.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME (" +
                "$COLUM_ID INTEGER PRIMARY KEY AUTOINCREMENT,  " +
                "$COLUMN_TITLE TEXT," +
                " $COLUMN_CONTENT TEXT, " +
                "$COLUM_DATE LONG)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }


    fun insertNotes(note: NoteData) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
            put(COLUM_DATE, note.editTime)

        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAll(): MutableList<NoteData> {
        val notesList: MutableList<NoteData> = mutableListOf()
        val notesQuery = "SELECT * FROM $TABLE_NAME"
        val noteData = readableDatabase
        val notesCursor = noteData.rawQuery(notesQuery, null)
        while (notesCursor.moveToNext()) {
            val id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(COLUM_ID))
            val noteTitle = notesCursor.getString(notesCursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val noteContent = notesCursor.getString(notesCursor.getColumnIndexOrThrow(COLUMN_CONTENT))
            val noteTime = notesCursor.getLong(notesCursor.getColumnIndexOrThrow(COLUM_DATE))
            val notes = NoteData(id, noteTitle, noteContent,noteTime)
            notesList.add(notes)
        }
        notesCursor.close()
        noteData.close()
        return notesList
    }

    fun searchNotes(text: String): MutableList<NoteData> {
        val noteList: MutableList<NoteData> = mutableListOf()
        val notes = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_TITLE = $text OR $COLUMN_CONTENT = $text"
        val noteData = readableDatabase
        val noteQuery = noteData.rawQuery(notes, null)
        while (noteQuery.moveToNext()) {
            val id = noteQuery.getInt(noteQuery.getColumnIndexOrThrow(COLUM_ID))
            val noteTitle = noteQuery.getString(noteQuery.getColumnIndexOrThrow(COLUMN_TITLE))
            val noteContent = noteQuery.getString(noteQuery.getColumnIndexOrThrow(COLUMN_CONTENT))
            val noteTime = noteQuery.getLong(noteQuery.getColumnIndexOrThrow(COLUM_DATE))
            val notes = NoteData(id, noteTitle, noteContent,noteTime)
            noteList.add(notes)
        }
        noteQuery.close()
        noteData.close()
        return noteList
    }

//    fun updateNote(note: NoteData) {
//        val noteData = writableDatabase
//        val noteValues = ContentValues().apply {
//            put(COLUMN_TITLE, note.title)
//            put(COLUMN_CONTENT, note.content)
//        }
//
//        val noteId = "${COLUM_ID} = ?"
//        val selectionArgs = arrayOf(note.id.toString())
//        noteData.update(
//            TABLE_NAME,
//            noteValues,
//            noteId,
//            selectionArgs)
//        noteData.close()
//    }

    fun editNote(title: String, content: String, id: Int) {
        val noteData = writableDatabase
        val noteValues = ContentValues().apply {
            put(COLUMN_TITLE, title)
            put(COLUMN_CONTENT, content)
        }

        val noteId = "${COLUM_ID} = ?"
        val selectionArgs = arrayOf(id.toString())
        noteData.update(
            TABLE_NAME,
            noteValues,
            noteId,
            selectionArgs)
        noteData.close()


    }

    fun deleteNote(id: Int) {
        val noteData = readableDatabase
        val whereClause = "${COLUM_ID} = ?"
        val whereArgs = arrayOf(id.toString())
        noteData.delete(
            TABLE_NAME,
            whereClause,
            whereArgs)
        noteData.close()
    }


 fun readNotesItem() {
     val db = readableDatabase

     val projection = arrayOf(COLUM_ID, COLUMN_TITLE, COLUMN_CONTENT)

     val selection = "${COLUMN_TITLE} = ?"
     val selectionArgs = arrayOf("My Title")

     val sortOrder = "${COLUM_ID} DESC"

     db.query(
         TABLE_NAME,   // The table to query
         projection,             // The array of columns to return (pass null to get all)
         selection,              // The columns for the WHERE clause
         selectionArgs,          // The values for the WHERE clause
         null,                   // don't group the rows
         null,                   // don't filter by row groups
         sortOrder               // The sort order
     )
 }

}