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
        private  const val  COLUMN_TITLE = "title"
        private  const val  COLUMN_CONTENT = "content"
        private  const val  COLUM_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUM_ID INTEGER PRIMARY KEY AUTOINCREMENT,  $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT, $COLUM_DATE LONG)"
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
        Log.d("sql", "tên bảng: $TABLE_NAME ")
        Log.d("sql", "tên tiêu đề: $COLUMN_TITLE ${note.title} ")
        db.close()
    }

    fun getAll(): MutableList<NoteData> {
        val notesList: MutableList<NoteData> = mutableListOf()
        val notes = "SELECT * FROM $TABLE_NAME"
        val noteData = readableDatabase
        val noteQuery = noteData.rawQuery(notes, null)
        while (noteQuery.moveToNext()) {
            val  id = noteQuery.getInt(noteQuery.getColumnIndexOrThrow(COLUM_ID))
            val  noteTitle = noteQuery.getString(noteQuery.getColumnIndexOrThrow(COLUMN_TITLE))
            val noteContent = noteQuery.getString(noteQuery.getColumnIndexOrThrow(COLUMN_CONTENT))
            val  noteTime = noteQuery.getLong(noteQuery.getColumnIndexOrThrow(COLUM_DATE))
            val notes = NoteData(id, noteTitle, noteContent,noteTime)
            notesList.add(notes)
        }

        noteQuery.close()
        noteData.close()
        return notesList
    }

    fun updateNote(note: NoteData) {
        val noteData = writableDatabase
        val noteValues = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
        }

        val noteId = "${COLUM_ID} = ?"
        val selectionArgs = arrayOf(note.id.toString())
        noteData.update(
            TABLE_NAME,
            noteValues,
            noteId,
            selectionArgs)
        noteData.close()
    }

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


}