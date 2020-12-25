package com.example.lesson

import com.example.core.http.EntityCallback
import com.example.core.http.HttpClient.Companion.INSTANCE
import com.example.lesson.entity.Lesson
import com.google.gson.reflect.TypeToken
import java.util.*

class LessonPresenter(val activity: LessonActivity){

    private var lessons: MutableList<Lesson?> = ArrayList()

    private val type = object : TypeToken<List<Lesson?>?>() {}.type

    fun fetchData() {
        INSTANCE.get(LESSON_PATH, type, object : EntityCallback<MutableList<Lesson?>> {
            override fun onSuccess(entity: MutableList<Lesson?>) {
                this@LessonPresenter.lessons = entity
                activity.runOnUiThread { activity.showResult(entity) }
            }

            override fun onFailure(message: String?) {
                activity.runOnUiThread { }
            }
        })
    }

    fun showPlayback() {
        val playbackLessons: MutableList<Lesson> = ArrayList()
        for (lesson in lessons) {
            if (lesson?.state === Lesson.State.PLAYBACK) {
                playbackLessons.add(lesson)
            }
        }
        activity.showResult(playbackLessons)
    }

    companion object {
        private const val LESSON_PATH = "lessons"
    }
}