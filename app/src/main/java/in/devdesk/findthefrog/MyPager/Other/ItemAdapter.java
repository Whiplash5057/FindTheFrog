package in.devdesk.findthefrog.MyPager.Other;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.devdesk.findthefrog.MyPager.Other.pojo.AllFrogsResponse;
import in.devdesk.findthefrog.R;

/**
 * Created by richardandrews on 11/07/17.
 */

public class ItemAdapter  extends RecyclerView.Adapter<ItemAdapter.Holder> {


    //-- blind strings
     public static List<AllFrogsResponse.Response> responsePojoInnerSecret;

    //-- onClicks


    //-- methods

    public ItemAdapter(List<AllFrogsResponse.Response> responsePojo) {
        responsePojoInnerSecret = responsePojo;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, null, false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        AllFrogsResponse.Response currentItem = responsePojoInnerSecret.get(position);
        String locationName = currentItem.getLocationName();
        String locationNameTrunk = locationName;
        if(locationName.length() >= 30)
        {
            locationNameTrunk = locationName.substring(0, Math.min(locationName.length(), 30)) + "...";
        }

        holder.frogName.setText(locationNameTrunk);
        if(currentItem.getIsComplete())
        {
            holder.mPhoto.setImageResource(R.drawable.frog_found);
            holder.getItemViewType();
            holder.daysCount.setText("FOUND");
        }
        else
        {
            holder.mPhoto.setImageResource(R.drawable.frog_unfound);
            holder.daysCount.setText("UNFOUND");
        }

    }


    public void addItem(AllFrogsResponse.Response item) {
        responsePojoInnerSecret.add(item);
        notifyDataSetChanged();
    }

    public AllFrogsResponse.Response getSelectedItem(int position) {
        return responsePojoInnerSecret.get(position);
    }

    @Override
    public int getItemCount() {
        return responsePojoInnerSecret.size();
    }

    public class Holder extends RecyclerView.ViewHolder
    {

        //-- data binding
        @BindView(R.id.itemPhoto)
        ImageView mPhoto;

        @BindView(R.id.lbl_itemName_row)
        TextView frogName;

        @BindView(R.id.lbl_itemDaysLeft_row)
        TextView daysCount;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}
