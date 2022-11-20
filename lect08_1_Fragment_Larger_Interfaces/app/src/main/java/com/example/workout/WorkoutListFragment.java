package com.example.workout;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//public class WorkoutListFragment extends Fragment {
public class WorkoutListFragment extends ListFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    static interface Listener {
        void itemClicked(long id);
    }
    private Listener listener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_workout_list, container, false);
        String[] names = new String[Workout.workouts.length];
        for (int i = 0; i < names.length; i++)
            names[i] = Workout.workouts[i].getName();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                inflater.getContext(), android.R.layout.simple_list_item_1, names
        );
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // 回调接口一般都写在Fragment的 onAttach() 方法中，
    // Fragment 3.0 的 onAttach() 方法有2个重载，其中一个被废弃，所以通常是：
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (Listener)context;
    }

    @Override
    public void onListItemClick(ListView listView, View itemView, int position, long id) {
        if (listener != null)
            listener.itemClicked(id);
    }

}