package com.bamgmk.demo.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

import com.bamgmk.demo.GameItem;
import com.bamgmk.demo.ImageAdapter;
import com.bamgmk.demo.PlayerCharacter;
import com.bamgmk.demo.R;
import com.bamgmk.demo.activities.CreateCharActivity;
import com.bamgmk.demo.activities.StatScreenActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CharFrag.OnCharFragInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CharFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CharFrag extends Fragment {


    public ImageAdapter adapter;
    public List<PlayerCharacter> heroTeam;
    public ArrayList<GameItem> gameItems;


    private OnCharFragInteractionListener mListener;

    public CharFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CharFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static CharFrag newInstance(List<PlayerCharacter> pcl,ArrayList<GameItem> gil) {
        CharFrag fragment = new CharFrag();
        fragment.heroTeam = pcl;
        fragment.gameItems = gil;
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridView gridview = (GridView) getActivity().findViewById(R.id.gridview);
        Log.d("test",""+(gridview == null));
        adapter = new ImageAdapter(getActivity(),heroTeam);
        gridview.setAdapter(adapter);


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private int selected = -1;
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if (position <=2){
                    if (selected!=-1){
                        if (position == selected){
                            adapter.mItems.get(position).drawableId = adapter.mItems.get(position).pc.getImageId();
                            for (int i = 3; i< adapter.mItems.size(); i++){
                                if (adapter.mItems.get(i).pc.isActive)
                                    adapter.mItems.get(i).drawableId= adapter.mItems.get(i).pc.getImageId();
                            }
                            adapter.notifyDataSetChanged();
                            selected = -1;
                        }
                        else {
                            adapter.mItems.get(selected).drawableId = adapter.mItems.get(selected).pc.getImageId();
                            adapter.mItems.get(position).drawableId=adapter.mItems.get(position).pc.getSelectedImageId();
                            adapter.notifyDataSetChanged();
                            selected = position;
                        }

                    }
                    else{
                        adapter.mItems.get(position).drawableId=adapter.mItems.get(position).pc.getSelectedImageId();
                        for (int i = 3; i< adapter.mItems.size(); i++){
                            if (adapter.mItems.get(i).pc.isActive)
                                adapter.mItems.get(i).drawableId= adapter.mItems.get(i).pc.getGreyedOutImageId();
                        }
                        selected = position;
                        adapter.notifyDataSetChanged();
                    }
                }
                else
                if(selected!=-1){
                    if (!adapter.mItems.get(position).pc.isActive){
                        for (int i = 3; i< adapter.mItems.size();i++){
                            if(adapter.mItems.get(i).pc == adapter.mItems.get(selected).pc){
                                adapter.mItems.get(i).drawableId= adapter.mItems.get(i).pc.getImageId();
                                adapter.mItems.get(i).pc.isActive = false;
                            }
                        }
                        adapter.mItems.get(position).pc.isActive = true;
                        adapter.mItems.get(selected).drawableId = adapter.mItems.get(position).drawableId;
                        adapter.mItems.get(selected).name = adapter.mItems.get(position).name;
                        adapter.mItems.get(selected).pc = adapter.mItems.get(position).pc;
                        selected = -1;
                        for (int i = 3; i< adapter.mItems.size(); i++){
                            if (adapter.mItems.get(i).pc.isActive)
                                adapter.mItems.get(i).drawableId= adapter.mItems.get(i).pc.getImageId();
                        }
                        mListener.saveHeroes();
                        adapter.notifyDataSetChanged();
                    }
                }
                else {
                    Intent intent = new Intent(getActivity(),StatScreenActivity.class);
                    intent.putExtra("Character",adapter.mItems.get(position).pc);
                    intent.putExtra("Items",gameItems);
                    intent.putExtra("Position",position);
                    startActivity(intent);
                }

                Log.d("test",""+position+" "+selected);

            }
        });
        ImageButton Ib = (ImageButton) getActivity().findViewById(R.id.createCharacterButton);
        Ib.setImageResource(R.drawable.button_create_charakter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_char, container, false);

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCharFragInteractionListener) {
            mListener = (OnCharFragInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCharFragInteractionListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnCharFragInteractionListener {
        public void saveHeroes();
    }
}
