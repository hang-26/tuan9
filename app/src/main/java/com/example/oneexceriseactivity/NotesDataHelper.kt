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


     fun getTimeOld(): MutableList<NoteData> {
         val notesList: MutableList<NoteData> = mutableListOf()
         val notesQuery = "SELECT * FROM $TABLE_NAME ORDER BY $COLUM_DATE ASC "
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

    fun getTimeNew(): MutableList<NoteData> {
        val notesList: MutableList<NoteData> = mutableListOf()
        val notesQuery = "SELECT * FROM $TABLE_NAME ORDER BY $COLUM_DATE DESC "
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

    fun getDataAZ(): MutableList<NoteData> {
        val notesList: MutableList<NoteData> = mutableListOf()
        val notesQuery = "SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_TITLE ASC "
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

    fun getTitleZA(): MutableList<NoteData> {
        val notesList: MutableList<NoteData> = mutableListOf()
        val notesQuery = "SELECT * FROM $TABLE_NAME ORDER BY $COLUM_DATE DESC "
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


    fun getTitle(tittle: String): MutableList<NoteData> {

        val notesList: MutableList<NoteData> = mutableListOf()
        val noteQuery ="SELECT *FROM $TABLE_NAME WHERE $COLUMN_TITLE LIKE %$tittle% "
        val noteData = readableDatabase
        val notesCursor = noteData.rawQuery(noteQuery, null)
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

    

}