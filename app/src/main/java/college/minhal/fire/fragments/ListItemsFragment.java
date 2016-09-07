package college.minhal.fire.fragments;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import college.minhal.fire.R;
import college.minhal.fire.models.ShoppingList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListItemsFragment extends Fragment {

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
        return inflater.inflate(R.layout.fragment_list_items, container, false);
    }

}
