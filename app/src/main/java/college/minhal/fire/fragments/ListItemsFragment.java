package college.minhal.fire.fragments;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import college.minhal.fire.R;
import college.minhal.fire.adapters.ShoppingListsItemsAdapter;
import college.minhal.fire.models.ShoppingList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListItemsFragment extends Fragment implements View.OnClickListener {

    private static String ARG_KEY = "Key";
    private static String ARG_SHOPPING_LIST = "ShoppingList";
    private ShoppingList shoppingList;
    private String key;

    public static ListItemsFragment newInstance(String key, ShoppingList shoppingList) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);
        args.putParcelable(ARG_SHOPPING_LIST, shoppingList);
        ListItemsFragment fragment = new ListItemsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.shoppingList = getArguments().getParcelable(ARG_SHOPPING_LIST);
        this.key = getArguments().getString(ARG_KEY);
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_list_items, container, false);

        RecyclerView rvShoppingListItems = (RecyclerView) v.findViewById(R.id.rvShoppingListItems);
        rvShoppingListItems.setLayoutManager(new LinearLayoutManager(getContext()));
        rvShoppingListItems.setAdapter(new ShoppingListsItemsAdapter(getActivity(), key));


        FloatingActionButton btnAddShoppingListItem =
                (FloatingActionButton) v.findViewById(R.id.btnAddShoppingListItem);

        btnAddShoppingListItem.setOnClickListener(this);
        return v;
    }

    //Add an Item to the shopping List:
    @Override
    public void onClick(View view) {
        AddNewListItemFragment dialog = AddNewListItemFragment.newInstance(key);
        dialog.show(getFragmentManager(), "Add");
    }
}
