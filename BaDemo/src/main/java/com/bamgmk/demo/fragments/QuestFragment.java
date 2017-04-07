package com.bamgmk.demo.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bamgmk.demo.Quest;
import com.bamgmk.demo.QuestSave;
import com.bamgmk.demo.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestFragment.OnQuestFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestFragment extends Fragment {


    public Quest major;
    public Quest minor;
    public TextView majorQuestText;
    public TextView minorQuestText;




    private OnQuestFragmentInteractionListener mListener;

    public QuestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment QuestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestFragment newInstance() {
        QuestFragment fragment = new QuestFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quest, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        majorQuestText = (TextView) getActivity().findViewById(R.id.textView3);
        minorQuestText = (TextView) getActivity().findViewById(R.id.textView5);
        mListener.questFragmentReady(this);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnQuestFragmentInteractionListener) {
            mListener = (OnQuestFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnQuestFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void createMinorQuest() {

        minor = new Quest(Quest.typeMinor);
        minorQuestText.setText(minor.questText);
    }

    public void createMajorQuest() {

        major = new Quest(Quest.typeMajor);
        majorQuestText.setText(major.questText);
    }

    public void increaseCounters(int counterType){
        minor.increase(counterType);
        major.increase(counterType);
        minorQuestText.setText(minor.questText);
        majorQuestText.setText(major.questText);
    }

    public void loadSave(QuestSave qs) {
        major = new Quest(Quest.typeMajor,qs.majorQuestNumber,qs.majorQuestCurrent);
        minor = new Quest(Quest.typeMinor,qs.minorQuestNumber,qs.minorQuestCurrent);
        minorQuestText.setText(minor.questText);
        majorQuestText.setText(major.questText);
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnQuestFragmentInteractionListener {
        public void questFragmentReady(QuestFragment qf);
        public void generateReward (boolean major);
    }
}
