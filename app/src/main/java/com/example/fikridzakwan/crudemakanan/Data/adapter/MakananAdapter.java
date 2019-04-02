package com.example.fikridzakwan.crudemakanan.Data.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.fikridzakwan.crudemakanan.Model.Makanan.MakananData;
import com.example.fikridzakwan.crudemakanan.R;
import com.example.fikridzakwan.crudemakanan.UI.DetailMakanan.DetailMakananActivity;
import com.example.fikridzakwan.crudemakanan.UI.MakananByKategori.MakananByKategoriActivity;
import com.example.fikridzakwan.crudemakanan.UI.detailbyuser.DetailByUserActivity;
import com.example.fikridzakwan.crudemakanan.UI.uploadmakanan.UploadMakananActivity;
import com.example.fikridzakwan.crudemakanan.Utilts.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MakananAdapter extends RecyclerView.Adapter<MakananAdapter.ViewHolder> {

    // Type 1 untuk makana baru
    public static final int TYPE_1 = 1;
    // Tyep 2 untuk makanan popler
    public static final int TYPE_2 = 2;
    // Tyep 3 untuk kategori
    public static final int TYPE_3 = 3;
    // Type 4 unutk makanan by kategori
    public static final int TYPE_4 = 4;
    // Type 5 untuk makanan by user
    public static final int TYPE_5 = 5;

    Integer viewType;
    private final Context context;
    private final List<MakananData> makananDataList;

    public MakananAdapter(Integer viewType, Context context, List<MakananData> makananDataList) {
        this.viewType = viewType;
        this.context = context;
        this.makananDataList = makananDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        switch (i) {
            case TYPE_1:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_terbaru, null);
                return new FoodNewsViewHolder(view);
            case TYPE_2:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_populer, null);
                return new FoodPopulerViewHolder(view);
            case TYPE_3:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_kategori, null);
                return new FoodKategoriViewHolder(view);
            case TYPE_4:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_by_kategori, null);
                return new FoodNewsViewHolder(view);
            case TYPE_5:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_by_kategori, null);
                return new FoodByUserViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        // Mengambil data lalu memasukan ke dalam model
        final MakananData makananData = makananDataList.get(i);

        // Mengambil view Ttyoe dari ser atau constractor
        int mViewType = viewType;
        switch (mViewType) {
            case TYPE_1:
                // Membuat holder untuk dapat mengakses widget
                FoodNewsViewHolder foodNewsViewHolder = (FoodNewsViewHolder) viewHolder;

                // RequestOptions untuk erro dan placeholder gambar
                RequestOptions options = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananData.getUrlMakanan()).apply(options).into(foodNewsViewHolder.imgMakanan);

                // Menampilkan title dan jumlah view
                foodNewsViewHolder.txtTitle.setText(makananData.getNamaMakanan());
                foodNewsViewHolder.txtView.setText(makananData.getView());

                // Menampilkan data time
                foodNewsViewHolder.txtTime.setText(newDate(makananData.getInsertTime()));

                // Membat onClick
                foodNewsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Berpindah halaman
                        context.startActivity(new Intent(context, DetailMakananActivity.class).putExtra(Constants.KEY_EXTRA_ID_MAKANAN, makananData.getIdMakanan()));
                    }
                });

                break;
            case TYPE_2:
                // Membuat holder untuk dapat mengakses widget
                FoodPopulerViewHolder foodPopulerViewHolder = (FoodPopulerViewHolder) viewHolder;

                // RequestOptions untuk erro dan placeholder gambar
                RequestOptions options2 = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananData.getUrlMakanan()).apply(options2).into(foodPopulerViewHolder.imgMakanan);

                // Menampilkan title dan jumlah view
                foodPopulerViewHolder.txtTitle.setText(makananData.getNamaMakanan());
                foodPopulerViewHolder.txtView.setText(makananData.getView());

                // Menampilkan data time
                foodPopulerViewHolder.txtTime.setText(newDate(makananData.getInsertTime()));

                foodPopulerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, DetailMakananActivity.class).putExtra(Constants.KEY_EXTRA_ID_MAKANAN, makananData.getIdMakanan()));
                    }
                });

                break;
            case TYPE_3:
                // Membuat holder untuk dapat mengakses widget
                FoodKategoriViewHolder foodKategoriViewHolder = (FoodKategoriViewHolder) viewHolder;

                // RequestOptions untuk erro dan placeholder gambar
                RequestOptions options3 = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananData.getUrlMakanan()).apply(options3).into(foodKategoriViewHolder.image);

                foodKategoriViewHolder.txtNamaKategory.setText(makananData.getNamaKategori());

                foodKategoriViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, MakananByKategoriActivity.class).putExtra(Constants.KEY_EXTRA_ID_KATEGORI, makananData.getIdKategori()));
                    }
                });

                break;

            case TYPE_4:
                // Membuat holder untuk dapat mengakses widget
                FoodNewsViewHolder foodNewsViewHolder1 = (FoodNewsViewHolder) viewHolder;

                // RequestOptions untuk erro dan placeholder gambar
                RequestOptions options4 = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananData.getUrlMakanan()).apply(options4).into(foodNewsViewHolder1.imgMakanan);

                // Menampilkan title dan jumlah view
                foodNewsViewHolder1.txtTitle.setText(makananData.getNamaMakanan());
                foodNewsViewHolder1.txtView.setText(makananData.getView());

                // Menampilkan data time
                foodNewsViewHolder1.txtTime.setText(newDate(makananData.getInsertTime()));

                // Membat onClick
                foodNewsViewHolder1.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Berpindah halaman
                        context.startActivity(new Intent(context, MakananByKategoriActivity.class).putExtra(Constants.KEY_EXTRA_ID_MAKANAN, makananData.getIdKategori()));
                    }
                });

                break;

            case TYPE_5:
                // Membuat holder untuk dapat mengakses widget
                FoodByUserViewHolder foodByUserViewHolder = (FoodByUserViewHolder) viewHolder;

                // RequestOptions untuk erro dan placeholder gambar
                RequestOptions options5 = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananData.getUrlMakanan()).apply(options5).into(foodByUserViewHolder.imgMakanan);

                // Menampilkan title dan jumlah view
                foodByUserViewHolder.txtTitle.setText(makananData.getNamaMakanan());
                foodByUserViewHolder.txtView.setText(makananData.getView());

                // Menampilkan data time
                foodByUserViewHolder.txtTime.setText(newDate(makananData.getInsertTime()));

                // Membat onClick
                foodByUserViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Berpindah halaman
                        context.startActivity(new Intent(context, DetailByUserActivity.class).putExtra(Constants.KEY_EXTRA_ID_MAKANAN, makananData.getIdMakanan()));
                    }
                });

                break;
        }
    }

    private String newDate(String insertTime) {
        // Membuat variable penampung tanngal
        Date date = null;

        // Membuat penampung date degan format yang baru
        String newDate = insertTime;

        // Membuat format sesusai degan tanggal yang sudah dimiliki
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd:mm:ss");
        // Mengubah tanngal yang dimiliki menjadi tipe data
        try {
            date = sdf.parse(insertTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Cek formate date yang kita miliki sesuai degan yang kita inginkan
        if (date != null) {
            // Mengubah date yang dimiliki menjadi format date yang baru
            newDate = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss").format(date);
        }
        return newDate;
    }

    @Override
    public int getItemCount() {
        return makananDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class FoodNewsViewHolder extends ViewHolder {
        @BindView(R.id.img_makanan)
        ImageView imgMakanan;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.txt_view)
        TextView txtView;
        @BindView(R.id.txt_time)
        TextView txtTime;

        public FoodNewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class FoodPopulerViewHolder extends ViewHolder {
        @BindView(R.id.img_makanan)
        ImageView imgMakanan;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_view)
        TextView txtView;
        @BindView(R.id.txt_time)
        TextView txtTime;

        public FoodPopulerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class FoodKategoriViewHolder extends ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.txt_nama_kategory)
        TextView txtNamaKategory;

        public FoodKategoriViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public class FoodByUserViewHolder extends ViewHolder {
        @BindView(R.id.img_makanan)
        ImageView imgMakanan;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.txt_view)
        TextView txtView;
        public FoodByUserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
