package cn.dingrui.common.common.widget.recycler;

/**
 * Created by ding192 on 2018/4/2.
 */

public interface AdapterCallBack<Data> {
    void update(Data data,BaseRecyclerAdapter.ViewHolder holder);
}
