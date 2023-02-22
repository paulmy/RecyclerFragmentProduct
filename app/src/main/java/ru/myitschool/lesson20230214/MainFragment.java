package ru.myitschool.lesson20230214;


import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import ru.myitschool.lesson20230214.databinding.FragmentMainBinding;


public class MainFragment extends Fragment {
    private final ProductRepository repository = ProductRepository.getInstance(getContext());

    private DataBaseHelper dataBaseHelper;

    ProductAdapter.OnProductDataClickListener productClickListener = new ProductAdapter.OnProductDataClickListener() {

        @Override
        public void onProductClick(ProductAdapter.ViewHolder holder) {
            Toast.makeText(getContext(), "Был выбран пункт " + repository.getProducts().get(holder.getAdapterPosition()).getName(),
                    Toast.LENGTH_SHORT).show();
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.rootContainer, DetailFragment.newInstance(repository, holder.getAdapterPosition()))
                    .commit();

        }


    };


    private final ProductAdapter adapter = new ProductAdapter(productClickListener);
    private final ItemTouchHelper.SimpleCallback swipeToDelete = new ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.ACTION_STATE_IDLE,
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
    ) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            repository.removeByPosition(viewHolder.getAdapterPosition());
            adapter.removeItemByPosition(viewHolder.getAdapterPosition());
            //        dataBaseHelper.removeProduct(viewHolder.getAdapterPosition());
        }
    };
    private static int count;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (dataBaseHelper == null) dataBaseHelper = new DataBaseHelper(getContext());

        Log.d("DB", "OPEN DB MAINFARGMENT");
        FragmentMainBinding binding = FragmentMainBinding.inflate(inflater, container, false);
        binding.container.setAdapter(adapter);
        new ItemTouchHelper(swipeToDelete).attachToRecyclerView(binding.container);
        binding.add.setOnClickListener(v -> {


            //repository.addProduct(new ProductData("Product " + count++, count % 2 == 0 ? "test description\nmultiLine\nexample\ntest" : "", (int) (Math.random() * 100)));
            repository.addProduct(new ProductData("Product " + count++, count % 2 == 0 ? "test description\nmultiLine\nexample\ntest" : "", (int) (Math.random() * 100)));

            Log.d("Add", "" + count);
            adapter.setData(repository.getProducts());
        });
        adapter.setData(repository.getProducts());


        return binding.getRoot();
        // return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentManager fm = getParentFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.rootContainer);
        if (fragment == null) {
            fragment = new MainFragment();
            fm.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.rootContainer, fragment, String.valueOf(false))
                    .commit();
        }


    }


}
