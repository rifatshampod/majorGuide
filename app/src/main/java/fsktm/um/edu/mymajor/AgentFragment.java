package fsktm.um.edu.mymajor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AgentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private TextView txtAgentName;
    private TextView txtAgentDescription;
    private TextView txtAgentLocation;
    private TextView txtAgentPhoneNumber;
    private TextView txtAgentEmail;

    public AgentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AgentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AgentFragment newInstance(String param1, String param2) {
        AgentFragment fragment = new AgentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        View view = inflater.inflate(R.layout.fragment_agent, container, false);

        final String agent_name = getArguments().getString("agent_title");

        txtAgentName = view.findViewById(R.id.txtAgentName);
        txtAgentDescription = view.findViewById(R.id.txtAgentDescription);
        txtAgentLocation = view.findViewById(R.id.txtAgentLocation);
        txtAgentPhoneNumber = view.findViewById(R.id.txtAgentPhoneNumber);
        txtAgentEmail = view.findViewById(R.id.txtAgentEmail);

        myRef.child("agents").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    if(dataSnapshot1.child("name").getValue().toString().equals(agent_name)){
                        txtAgentName.setText(dataSnapshot1.child("name").getValue().toString());
                        txtAgentDescription.setText(dataSnapshot1.child("description").getValue().toString());
                        txtAgentLocation.setText(dataSnapshot1.child("location").getValue().toString());
                        txtAgentPhoneNumber.setText(dataSnapshot1.child("phoneNumber").getValue().toString());
                        txtAgentEmail.setText(dataSnapshot1.child("email").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Database error!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}