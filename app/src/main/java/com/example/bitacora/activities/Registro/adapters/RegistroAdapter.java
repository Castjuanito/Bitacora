package com.example.bitacora.activities.Registro.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bitacora.R;
import com.example.bitacora.activities.Registro.SliderAdapterExample;
import com.example.bitacora.models.Registro;
import com.example.bitacora.models.Viaje;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

public class RegistroAdapter extends BaseAdapter {


    Context context;
    List<Registro> rowItems;

    public RegistroAdapter(Context context, List<Registro> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int i) {
        return rowItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        Log.d("->>>>>>>>>>>>>", "entro registro: ");

        view= LayoutInflater.from(context).inflate(R.layout.item_record,viewGroup,false);

        TextView fechaTxt= (TextView) view.findViewById(R.id.textViewTituloRecord);



        final Registro s= (Registro) this.getItem(position);

        fechaTxt.setText(Integer.toString(s.getId()));

        final SliderView sliderView;

        sliderView = view.findViewById(R.id.imageSlider);

        final SliderAdapterExample adapter = new SliderAdapterExample(context);
        adapter.setCount(5);

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.startAutoCycle();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderView.setCurrentPagePosition(position);
            }
        });


        return view;
    }
}
