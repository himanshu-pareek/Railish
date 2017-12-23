package in.railish.railish.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import in.railish.railish.R;
import in.railish.railish.models.Choice;

public class ChoiceAdapter extends ArrayAdapter<Choice> {

    private boolean mRightArrow = false;

    public ChoiceAdapter(Context context, ArrayList<Choice> choices) {
        super(context, 0, choices);
        mRightArrow = false;
    }

    public ChoiceAdapter(Context context, ArrayList<Choice> choices, boolean rightArrow) {
        super(context, 0, choices);
        mRightArrow = rightArrow;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.choice_item_view, parent, false
            );
        }

        Choice currentChoice = getItem(position);

        ImageView choiceImageView = (ImageView) listItemView.findViewById(R.id.choice_iamge_view);
        TextView choiceTextView = (TextView) listItemView.findViewById(R.id.choice_text_view);
        ImageView rightArrowImage = (ImageView) listItemView.findViewById(R.id.right_arrow_image_view);

        if (!mRightArrow) {
            rightArrowImage.setVisibility(View.GONE);
        } else {
            rightArrowImage.setVisibility(View.VISIBLE);
        }

        choiceImageView.setImageResource(currentChoice.getImageResId());
        choiceTextView.setText(currentChoice.getName());

        return listItemView;
    }
}
