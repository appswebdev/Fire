package college.minhal.fire.activities;

import android.os.Bundle;

import college.minhal.fire.R;
import college.minhal.fire.fragments.ShoppingListFragment;

public class ShoppingListsActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_lists);


        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, new ShoppingListFragment()).
                commit();
    }

}
