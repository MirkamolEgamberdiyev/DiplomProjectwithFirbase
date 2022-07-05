package com.tuit.tuit.ui.student.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tuit.tuit.ui.student.quiz.FragmentQuestion
import com.tuit.tuit.ui.student.quiz.QuizHomeFragment
import com.tuit.tuit.utils.Constant

class FragmentQuestionPagerAdapter(fm: FragmentManager, var context: Context, var fragments: ArrayList<FragmentQuestion>
) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence{
        Constant.lastPosition = position
        return StringBuilder("Savol ").append(position + 1).toString()
    }
}