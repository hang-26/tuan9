package com.example.oneexceriseactivity

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class NotesDataHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private  const val  DATABASE_NAME = "notesapp.db"
        private  const val  DATABASE_VERSION = 1
        private  const val  TABLE_NAME = "allnotes"
        private  const val  COLUM_ID = "id"
        private  const val  COLUM_TITLE = "title"
        private  const val  COLUM_CONTENT = "content"
        private  const val  COLUM_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUM_ID INTEGER PRIMARY KEY AUTOINCREMENT,  $COLUM_TITLE TEXT, $COLUM_CONTENT TEXT, $COLUM_DATE LONG)"
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
            put(COLUM_TITLE, note.title)
            put(COLUM_CONTENT, note.content)
            put(COLUM_DATE, note.editTime)
        }
        db.insert(TABLE_NAME, null, values)
        Log.d("sql", "tên bảng: $TABLE_NAME ")
        Log.d("sql", "tên tiêu đề: $COLUM_TITLE ${note.title} ")
        db.close()
    }

    fun getAll(): MutableList<NoteData> {
        val notesList: MutableList<NoteData> = mutableListOf()
        val notes = "SELECT * FROM $TABLE_NAME"
        val noteData = readableDatabase
        val noteQuery = noteData.rawQuery(notes, null)
        while (noteQuery.moveToNext()) {
            val  id = noteQuery.getInt(noteQuery.getColumnIndexOrThrow(COLUM_ID))
            val  noteTitle = noteQuery.getString(noteQuery.getColumnIndexOrThrow(COLUM_TITLE))
            val noteContent = noteQuery.getString(noteQuery.getColumnIndexOrThrow(COLUM_CONTENT))
            val  noteTime = noteQuery.getLong(noteQuery.getColumnIndexOrThrow(COLUM_DATE))
            val notes = NoteData(id, noteTitle, noteContent,noteTime)
            notesList.add(notes)
        }

        noteQuery.close()
        noteData.close()
        return notesList
    }

}