package com.linkbubble.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.linkbubble.R;

public class BubbleFlowActivity extends Activity {

    BubbleFlowView mBubbleFlowView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bubble_flow);

        final TextView debugText = (TextView) findViewById(R.id.debug_text);

        final LayoutInflater inflater = LayoutInflater.from(this);
        mBubbleFlowView = (BubbleFlowView) findViewById(R.id.bubble_flow);
        mBubbleFlowView.configure(getResources().getDimensionPixelSize(R.dimen.bubble_pager_width),
                getResources().getDimensionPixelSize(R.dimen.bubble_pager_item_width),
                getResources().getDimensionPixelSize(R.dimen.bubble_pager_item_height));
        for (int i = 0; i < 19; i++) {
            BubbleFlowItemView bubble = (BubbleFlowItemView) inflater.inflate(R.layout.view_bubble_flow_item, null);
            mBubbleFlowView.add(bubble);
        }
        mBubbleFlowView.setBubbleFlowViewListener(new BubbleFlowView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(BubbleFlowView bubbleFlowView, int x, int y, int oldx, int oldy) {
                debugText.setText(mBubbleFlowView.getDebugString());
            }
        });
        debugText.setText(mBubbleFlowView.getDebugString());

        findViewById(R.id.add_bubble_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BubbleFlowItemView bubble = (BubbleFlowItemView) inflater.inflate(R.layout.view_bubble_flow_item, null);
                mBubbleFlowView.add(bubble);
            }
        });

        findViewById(R.id.remove_bubble_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int centerIndex = mBubbleFlowView.getCenterIndex();
                int count = mBubbleFlowView.getCount();
                if (centerIndex+1 < count) {
                    mBubbleFlowView.remove(centerIndex + 1);
                } else if (count > 2) {
                    mBubbleFlowView.remove(centerIndex - 1);
                }
            }
        });

        final Button animateButton = (Button) findViewById(R.id.animate_bubble_button);
        animateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBubbleFlowView.isExpanded()) {
                    mBubbleFlowView.shrink();
                    animateButton.setText("Expand");
                } else {
                    mBubbleFlowView.expand();
                    animateButton.setText("Shrink");
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        mBubbleFlowView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBubbleFlowView.setCenterIndex(6);
            }
        }, 100);
    }
}