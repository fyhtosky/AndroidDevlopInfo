package com.example.fanyuanhua.netpower.mindView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class TreeView extends RelativeLayout {
    //TreeView最大最小尺寸
    private static final int min_width = 600;
    private static final int min_height = 400;

    /**
     * tree的内边距
     */
    public static final int treePadding = 100;

    private static final int AnimaDuration = 500;
    /**
     * 根节点
     */
    private NodeView nodeView;

    public TreeView(Context context) {
        this(context,null);
    }

    public TreeView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TreeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTreeViews();
    }

    private void initTreeViews() {
    }

    public void setNode(int size) {
        removeAllViews();

        nodeView = new NodeView(getContext());
        List<NodeView> nodeViews=new ArrayList<>();
        for (int i=0;i<=size;i++){
            nodeViews.add(new NodeView(getContext()));
        }
        nodeView.setNodeViews(nodeViews);
        nodeView.setLayoutType(NodeViewType.LEFTTORIGHT);
        addView(nodeView);
        scrollTo(0,0);
        requestLayout();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 所有的view添加到relativivelayout中，交给它测量
        int width = min_width;
        int height = min_height;
        if (nodeView != null ) {
            //nodeView.measure(widthMeasureSpec, heightMeasureSpec);
            int nodeAllWidth = nodeView.getAllWidth();
            int nodeAllHeight = nodeView.getAllHeight();
                if (nodeAllWidth > min_width - treePadding * 2) {
                    width = nodeAllWidth + treePadding * 2;
                }
                if (nodeAllHeight > min_height - treePadding * 2) {
                    height = nodeAllHeight + treePadding * 2;
                }

        }

        // 下面是根据父控件自己设置的宽高，测量子控件大小
        int selfWidthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int selfWidthSpecSize = MeasureSpec.getSize(widthMeasureSpec);

        int selfHeightSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int selfHeightSpecSize = MeasureSpec.getSize(widthMeasureSpec);


        int widthSpec;
        int heightSpec;
        Log.d("TreeView","计算布局的宽高："+"【"+width+","+height+"]");
        switch (width) {
            case LayoutParams.MATCH_PARENT:
                if (selfWidthSpecMode == MeasureSpec.EXACTLY || selfWidthSpecMode == MeasureSpec.AT_MOST) {
                    widthSpec = MeasureSpec.makeMeasureSpec(selfWidthSpecSize, MeasureSpec.EXACTLY);
                } else {
                    widthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                }
                break;
            case LayoutParams.WRAP_CONTENT:
                if (selfWidthSpecMode == MeasureSpec.EXACTLY || selfWidthSpecMode == MeasureSpec.AT_MOST) {
                    widthSpec = MeasureSpec.makeMeasureSpec(selfWidthSpecSize, MeasureSpec.AT_MOST);
                } else {
                    widthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                }
                break;
            default:
                widthSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.UNSPECIFIED);
                break;
        }

        switch (height) {
            case LayoutParams.MATCH_PARENT:
                if (selfHeightSpecMode == MeasureSpec.EXACTLY || selfHeightSpecMode == MeasureSpec.AT_MOST) {
                    heightSpec = MeasureSpec.makeMeasureSpec(selfHeightSpecSize, MeasureSpec.EXACTLY);
                } else {
                    heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                }
                break;
            case LayoutParams.WRAP_CONTENT:
                if (selfHeightSpecMode == MeasureSpec.EXACTLY || selfHeightSpecMode == MeasureSpec.AT_MOST) {
                    heightSpec = MeasureSpec.makeMeasureSpec(selfHeightSpecSize, MeasureSpec.AT_MOST);
                } else {
                    heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                }
                break;
            default:
                heightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.UNSPECIFIED);
                break;
        }
        setMeasuredDimension(widthSpec, heightSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int nl = 0;
        int nt = 0;
        int nr =0 ;
        int nb =0;
        if (nodeView != null) {
            switch (nodeView.getLayoutType()){
                case NodeViewType.TOPTOBOTTOM:
                    // 从上往下
                    nl = (getMeasuredWidth() - nodeView.getMeasuredWidth()) / 2;
                    nt = 0 + treePadding;
                    nr = (getMeasuredWidth() + nodeView.getMeasuredWidth()) / 2;
                    nb = nt + nodeView.getMeasuredHeight();
                    nodeView.layout(nl, nt, nr, nb);

                    break;
                case NodeViewType.LEFTTORIGHT:
                    // 从左往右
                    nl = 0 + treePadding;
                    nt = (getMeasuredHeight() - nodeView.getMeasuredHeight()) / 2;
                    nr = nl + nodeView.getMeasuredWidth();
                    nb = nt + nodeView.getMeasuredHeight();

                    nodeView.layout(nl, nt, nr, nb);
                    break;
                case NodeViewType.INTOOUT:
                    // 从中间往两边
//                    nl = 0 + treePadding + nodeView.getMaxLeftWidth();
//                    nt = (getMeasuredHeight() - nodeView.getMeasuredHeight()) / 2;
//                    nr = nl + nodeView.getMeasuredWidth();
//                    nb = nt + nodeView.getMeasuredHeight();
//
//                    nodeView.layout(nl, nt, nr, nb);
                    //animaLayout(nl,nt,nr,nb);
                    break;
            }
//            Log.d("TreeView","布局的坐标："+"【"+nl+","+nt+","+nr+","+nb+"]");
        }



    }

}
