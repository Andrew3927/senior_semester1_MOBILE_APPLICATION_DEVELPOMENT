package com.example.bitanpizzas;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {
    private String[] captions;
    private int[] imageIds;
    private Listener listener;
    interface Listener {
        void onClick(int position);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public CaptionedImagesAdapter(String[] captions, int[] imageIds) {
        this.captions = captions;
        this.imageIds = imageIds;
    }

    /**
     * The onCreateViewHolder() method gets called when the
     * recycler view requires a new view holder. The recycler view
     * calls the method repeatedly when the recycler view is first
     * constructed to build the set of view holders that will be
     * displayed on the scree
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_captioned_image, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.info_image);
        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), imageIds[position]);
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(captions[position]);
        TextView textView = (TextView) cardView.findViewById(R.id.info_text);
        textView.setText(captions[position]);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onClick(position);
            }
        });
    }


    /**
     * The length of the captions array equals the number of data itmes in the recycler view.
     *
     * @return the length of the captions arrays
     */
    @Override
    public int getItemCount() {
        return captions.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        /**
         * Our recycler view needs to displays CardViews, so we specify
         * that our ViewHolder contains CardViews. If you want to display
         * another type of data in the recycler view, you define it here.
         *
         * @param v
         */
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }
}
