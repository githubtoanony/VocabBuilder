package com.gmail.appytalkteam.appytalkcore;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionOutroFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestionOutroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionOutroFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String CORRECT_ANSWER = "correctAnswer";

    // TODO: Rename and change types of parameters
    private Word correctAnswer;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestionOutroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionOutroFragment newInstance(Word correctAnswer) {
        QuestionOutroFragment fragment = new QuestionOutroFragment();
        Bundle args = new Bundle();
        args.putParcelable(CORRECT_ANSWER, correctAnswer);
        fragment.setArguments(args);
        return fragment;
    }

    public QuestionOutroFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            correctAnswer = getArguments().getParcelable(CORRECT_ANSWER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = getActivity();
        View view =  inflater.inflate(R.layout.question_outro_fragment, container, false);
        Button correctAnswerTextButton = (Button) view.findViewById(R.id.correctAnswerTextButton);
        ImageButton correctAnswerImageButton = (ImageButton) view.findViewById(R.id.correctAnswerImageButton);
        correctAnswerTextButton.setText(correctAnswer.getWordText());
        correctAnswerImageButton.setImageResource(correctAnswer.getImageResourceId());
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
