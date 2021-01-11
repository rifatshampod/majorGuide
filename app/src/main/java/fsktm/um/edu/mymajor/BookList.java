package fsktm.um.edu.mymajor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookList extends Fragment implements BookSearchRecViewAdapter.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private ArrayList<BookModel> list = new ArrayList<>();
    private RecyclerView txtBooksView;

    public BookList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookList.
     */
    // TODO: Rename and change types and number of parameters
    public static BookList newInstance(String param1, String param2) {
        BookList fragment = new BookList();
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
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        txtBooksView = view.findViewById(R.id.txtBooksView);

        final BookSearchRecViewAdapter adapter = new BookSearchRecViewAdapter(list, this);

        txtBooksView.setAdapter(adapter);
        txtBooksView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        txtBooksView.setItemAnimator(new DefaultItemAnimator());

        myRef.child("books").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    list.add(dataSnapshot1.getValue(BookModel.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Database error!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("books", list);
        bundle.putInt("index", position);

        BookDetails bookDetails = new BookDetails();
        bookDetails.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.frameContainer, bookDetails).commit();
    }
}