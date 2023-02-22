package ru.myitschool.lesson20230214;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import ru.myitschool.lesson20230214.databinding.DetailMainBinding;

public class DetailFragment extends Fragment {
    private final ProductRepository repository =  ProductRepository.getInstance(getContext());
    private static ProductData data;

    public static DetailFragment newInstance(ProductRepository repository, int position) {
        data = repository.getProducts().get(position);
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", data.getName());
        bundle.putString("description", data.getDescription());
        bundle.putString("count", String.valueOf(data.getCount()));
        fragment.setArguments(bundle);
        return fragment;
    }

    private DetailMainBinding binding;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DetailMainBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String titleText = getArguments().getString("title");
        String descriptionText = getArguments().getString("description");
        String countText = getArguments().getString("count");

        binding.title.setText(titleText);
        binding.description.setText(descriptionText);
        binding.count.setText(countText);


        binding.back.setOnClickListener(view1 -> {

                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.rootContainer, new MainFragment())
                            .commit();

                }
        );
        binding.save.setOnClickListener(view1 -> {
                    ProductData temp = new ProductData(binding.title.getText().toString(), binding.description.getText().toString(), Integer.valueOf(binding.count.getText().toString()));
                    repository.replaceProduct(temp,repository.getPos(data));

                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.rootContainer, new MainFragment())
                            .commit();

                }
        );


    }

}
