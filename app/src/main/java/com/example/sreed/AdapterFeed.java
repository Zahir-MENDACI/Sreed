package com.example.sreed;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.sreed.MyRequest.MyRequest;

import java.util.ArrayList;


/**
 * Created by karsk on 25/04/2018.
 */

public class AdapterFeed extends RecyclerView.Adapter<AdapterFeed.MyViewHolder> {

    Context context;
    ArrayList<ModelFeed> modelFeedArrayList = new ArrayList<>();
    RequestManager glide;

    private RequestQueue queue;
    private MyRequest request;



    public AdapterFeed(Context context, ArrayList<ModelFeed> modelFeedArrayList) {

        this.context = context;
        this.modelFeedArrayList = modelFeedArrayList;
        glide = Glide.with(context);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feed, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ModelFeed modelFeed = modelFeedArrayList.get(position);

        queue = VolleySingleton.getInstance(context).getRequestQueue();
        request = new MyRequest(context, queue);

        holder.tv_name.setText(modelFeed.getName());
        holder.tv_time.setText(modelFeed.getTime());
        holder.tv_status.setText(modelFeed.getStatus());
        holder.S.setVisibility(modelFeed.getEtat());
        holder.M.setVisibility(modelFeed.getEtat());
        holder.S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(context, Settings.class);
                intent.putExtra("post", modelFeed.getStatus());
                context.startActivity(intent);*/

                request.Supprimer(modelFeed.getName(), modelFeed.getStatus(),new MyRequest.SupprimerCallBack() {
                    @Override
                    public void onSuccess(String message) {
                        Toast.makeText(context, "Supprimé", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, Admin.class);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        holder.M.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final EditText ET = new EditText(context);
                LinearLayout.LayoutParams LayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                ET.setLayoutParams(LayoutParams);
                ET.setText(modelFeed.getStatus());
                final String Temp = modelFeed.getStatus();
                builder.setView(ET);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        holder.tv_status.setText(ET.getText().toString());
                        request.Modifier(modelFeed.getName(), ET.getText().toString(), Temp, new MyRequest.ModifierCallBack() {
                            @Override
                            public void onSuccess(String message) {
                                Toast.makeText(context, "Modifié", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(String message) {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelFeedArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_time, tv_likes, tv_comments, tv_status;
        ImageView imgView_proPic, imgView_postPic;
        ImageButton S, M;

        public MyViewHolder(View itemView) {
            super(itemView);

            imgView_proPic = (ImageView) itemView.findViewById(R.id.imgView_proPic);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
            S = itemView.findViewById(R.id.S);
            M = itemView.findViewById(R.id.M);

        }
    }

}
