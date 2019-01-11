package com.example.fanyuanhua.netpower.mindView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.example.fanyuanhua.netpower.R;

import java.util.ArrayList;
import java.util.List;

public class NodeView extends RelativeLayout implements NodeViewType {
    /**
     * 横向布局x间距
     */
    private static final int hIntervalX = 90;
    /**
     * 横向布局Y间距
     */
    private static final int hIntervalY = 10;

    /**
     * 纵向布局x间距
     */
    private static final int vIntervalX = 8;
    /**
     * 纵向布局Y间距
     */
    private static final int vIntervalY = 90;

    private List<NodeView> nodeViews = new ArrayList<>();
    private int lineType;
    private int layoutType;



    public NodeView(Context context) {
        this(context,null);
    }

    public NodeView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initNodeViews();
    }

    private void initNodeViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.node_view, this);
    }

    public List<NodeView> getNodeViews() {
        return nodeViews;
    }

    public void setNodeViews(List<NodeView> nodeViews) {
        this.nodeViews = nodeViews;
    }

    @Override
    public int getLayoutType() {
        return layoutType;
    }

    @Override
    public void setLayoutType(int layoutType) {
     this.layoutType=layoutType;
    }

    @Override
    public int getLineType() {
        return lineType;
    }

    @Override
    public void setLineType(int lineType) {
     this.lineType=lineType;
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        switch (getLayoutType()){
            case NodeViewType.TOPTOBOTTOM:
                int lleft = getRight() - getMeasuredWidth() / 2 - getAllWidth() / 2;

                //父节点比子节点还大
                int cAllWidth = 0;
                for (int i = 0; i < nodeViews.size(); i++) {
                    cAllWidth += nodeViews.get(i).getAllWidth();
                }
                if (getMeasuredWidth() + vIntervalX * 2 >= cAllWidth) {
                    lleft += (getMeasuredWidth() + vIntervalX * 2 - cAllWidth) / 2;
                }

                for (int i = 0; i < nodeViews.size(); i++) {
                    NodeView nv = nodeViews.get(i);
                    int nvAllWidth = nv.getAllWidth();
                    int cleft = lleft + (nvAllWidth - nv.getMeasuredWidth()) / 2;
                    int ctop = getBottom() + vIntervalY;
                    int cright = cleft + nv.getMeasuredWidth();
                    int cbottom = ctop + nv.getMeasuredHeight();

                    nv.layout(cleft, ctop, cright, cbottom);

                    lleft += nvAllWidth;

                }
                break;
            case NodeViewType.LEFTTORIGHT:
                int ltop = getBottom() - getMeasuredHeight() / 2 - getAllHeight() / 2;

                //父节点比子节点还大
                int cAllHeight = 0;
                for (int i = 0; i < nodeViews.size(); i++) {
                    cAllHeight += nodeViews.get(i).getAllHeight();
                }
                if (getMeasuredHeight() + hIntervalY * 2 >= cAllHeight) {
                    ltop += (getMeasuredHeight() + hIntervalY * 2 - cAllHeight) / 2;
                }
                //add

                for (int i = 0; i < nodeViews.size(); i++) {
                    NodeView nv = nodeViews.get(i);

                    int nvAllHeight = nv.getAllHeight();
                    int cleft = getRight() + hIntervalX;
                    int ctop = ltop + (nvAllHeight - nv.getMeasuredHeight()) / 2;
                    int cright = cleft + nv.getMeasuredWidth();
                    int cbottom = ctop + nv.getMeasuredHeight();

                    //nv.layout(cleft, ctop, cright, cbottom);
                    nv.layout(cleft, ctop, cright, cbottom);
                    ltop += nvAllHeight;
                }
                break;
            case NodeViewType.INTOOUT:
//                if (node.getNodeType() == NodeDModel.NODETYPE_ROOT) {
//                    int lefttop = getTop() + getMeasuredHeight() / 2 - getMaxLeftHeight() / 2;
//                    int righttop = getTop() + getMeasuredHeight() / 2 - getMaxRightHeight() / 2;
//                    for (int i = 0; i < nodeViews.size(); i++) {
//
//                        NodeView nv = nodeViews.get(i);
//                        if (nodeViews.get(i).getNode().getDirection() == NodeDModel.DIRECTION_LEFT) {
//                            int nvAllHeight = nv.getAllHeight();
//                            int cleft = getLeft() - hIntervalX - nv.getMeasuredWidth();
//                            int ctop = lefttop + (nvAllHeight - nv.getMeasuredHeight()) / 2;
//                            int cright = cleft + nv.getMeasuredWidth();
//                            int cbottom = ctop + nv.getMeasuredHeight();
//
//                            nv.layout(cleft, ctop, cright, cbottom);
//
//
//                            //nv.newLayout();
//                            lefttop += nvAllHeight;
//                        } else {
//                            int nvAllHeight = nv.getAllHeight();
//                            int cleft = getRight() + hIntervalX;
//                            int ctop = righttop + (nvAllHeight - nv.getMeasuredHeight()) / 2;
//                            int cright = cleft + nv.getMeasuredWidth();
//                            int cbottom = ctop + nv.getMeasuredHeight();
//
//                            nv.layout(cleft, ctop, cright, cbottom);
//
//
//                            //nv.newLayout();
//                            righttop += nvAllHeight;
//                        }
//                    }
//                } else {
//
//                    int lltop = getBottom() - getMeasuredHeight() / 2 - getAllHeight() / 2;
//
//                    //父节点比子节点还大
//                    int ccAllHeight = 0;
//                    for (int i = 0; i < nodeViews.size(); i++) {
//                        ccAllHeight += nodeViews.get(i).getAllHeight();
//                    }
//                    if (getMeasuredHeight() + hIntervalY * 2 >= ccAllHeight) {
//                        lltop += (getMeasuredHeight() + hIntervalY * 2 - ccAllHeight) / 2;
//                    }
//                    //add
//
//                    for (int i = 0; i < nodeViews.size(); i++) {
//                        if (nodeViews.get(i).getNode().getDirection() == NodeDModel.DIRECTION_LEFT) {
//                            NodeView nv = nodeViews.get(i);
//
//                            int nvAllHeight = nv.getAllHeight();
//                            int cleft = getLeft() - hIntervalX - nv.getMeasuredWidth();
//                            int ctop = lltop + (nvAllHeight - nv.getMeasuredHeight()) / 2;
//                            int cright = cleft + nv.getMeasuredWidth();
//                            int cbottom = ctop + nv.getMeasuredHeight();
//
//                            nv.layout(cleft, ctop, cright, cbottom);
//
//
//                            //nv.newLayout();
//
//
//                            lltop += nvAllHeight;
//
//                        } else {
//                            NodeView nv = nodeViews.get(i);
//
//                            int nvAllHeight = nv.getAllHeight();
//                            int cleft = getRight() + hIntervalX;
//                            int ctop = lltop + (nvAllHeight - nv.getMeasuredHeight()) / 2;
//                            int cright = cleft + nv.getMeasuredWidth();
//                            int cbottom = ctop + nv.getMeasuredHeight();
//
//                            nv.layout(cleft, ctop, cright, cbottom);
//
//                            //nv.newLayout();
//
//
//                            lltop += nvAllHeight;
//                        }
//                    }
//                }


                break;
        }
    }

    protected int getAllWidth() {
        int width = 0;
            switch (getLayoutType()) {
                case NodeViewType.TOPTOBOTTOM:
                    if (nodeViews.size() > 0 ) {
                        int nodeViewsAllWidth = 0;
                        for (int i = 0; i < nodeViews.size(); i++) {
                            nodeViewsAllWidth += nodeViews.get(i).getAllWidth();
                        }
                        if (getMeasuredWidth() + vIntervalX * 2 >= nodeViewsAllWidth) {
                            width += getMeasuredWidth() + vIntervalX * 2;
                        } else {
                            width += nodeViewsAllWidth;
                        }
                    } else {
                        width += getMeasuredWidth() + vIntervalX * 2;
                    }
                    break;
                case NodeViewType.LEFTTORIGHT:
                    if (nodeViews.size() > 0 ) {
                        int maxwidth = 0;
                        // 只加最长的那个
                        for (int i = 0; i < nodeViews.size(); i++) {
                            int cAllWidth = nodeViews.get(i).getAllWidth();
                            if (cAllWidth > maxwidth) {
                                maxwidth = cAllWidth;
                            }
                        }
                        width += maxwidth;
                    }

                    width += getMeasuredWidth();
                    break;
                case NodeViewType.INTOOUT:
                    if (nodeViews.size() > 0) {

                        int maxwidth = 0;
                        // 只加最长的那个
                        for (int i = 0; i < nodeViews.size(); i++) {
                            int cAllWidth = nodeViews.get(i).getAllWidth();
                                if (cAllWidth > maxwidth) {
                                    maxwidth = cAllWidth;
                                }
                        }
                        width += maxwidth;

                    }
                    width += getMeasuredWidth();
                    break;

        }
        return width;
    }


    public int getAllHeight() {
        int height = 0;


            switch (getLayoutType()) {
                case NodeViewType.TOPTOBOTTOM:
                    if (nodeViews.size() > 0) {

                        int maxheight = 0;
                        // 只加最长的那个
                        for (int i = 0; i < nodeViews.size(); i++) {
                            int cAllHeight = nodeViews.get(i).getAllHeight();
                            if (cAllHeight > maxheight) {
                                maxheight = cAllHeight;
                            }
                        }

                        height += maxheight;

                    }

                    height += getMeasuredHeight();
                    break;
                case NodeViewType.LEFTTORIGHT:
                    if (nodeViews.size() > 0 ) {
                        int nodeViewsAllHeight = 0;
                        for (int i = 0; i < nodeViews.size(); i++) {
                            nodeViewsAllHeight += nodeViews.get(i).getAllHeight();
                        }
                        if (getMeasuredHeight() + hIntervalY * 2 >= nodeViewsAllHeight) {
                            height += getMeasuredHeight() + hIntervalY * 2;
                        } else {
                            height += nodeViewsAllHeight;
                        }
                    } else {
                        height += getMeasuredHeight() + hIntervalY * 2;
                    }
                    break;
                case NodeViewType.INTOOUT:
                    if (nodeViews.size() > 0 ) {

                        int nodeViewsAllHeight = 0;
                        for (int i = 0; i < nodeViews.size(); i++) {
                            int cAllHeight = nodeViews.get(i).getAllHeight();
                            nodeViewsAllHeight += cAllHeight;

                        }

                        if (getMeasuredHeight() + hIntervalY * 2 >= nodeViewsAllHeight) {
                            height += getMeasuredHeight() + hIntervalY * 2;
                        } else {
                            height += nodeViewsAllHeight;
                        }
                    } else {
                        height += getMeasuredHeight() + hIntervalY * 2;

                    }
                    break;

        }

        return height;
    }
}
