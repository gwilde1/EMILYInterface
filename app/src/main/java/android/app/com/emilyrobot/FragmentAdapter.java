package android.app.com.emilyrobot;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vinay on 4/17/17.
 */

public class FragmentAdapter extends RecyclerView.Adapter<FragmentAdapter.ViewHolder> {


    private ArrayList<Model> mModals;

    Context ctx;

    public FragmentAdapter(Context context,ArrayList<Model> Modals){
        mModals = Modals;
        ctx = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Model model= mModals.get(position);

        holder.imageView.setImageResource(model.getImage());
        holder.textView.setText(model.getName());


    }

    @Override
    public int getItemCount() {
        return mModals.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView imageView;
        TextView textView;


        public ViewHolder(View itemView) {
            super(itemView);


            imageView=(ImageView)itemView.findViewById(R.id.image1);
            textView=(TextView)itemView.findViewById(R.id.test1);



        }

    }


    @Override
    public int getItemViewType(int position) {

        return position;

    }


}
