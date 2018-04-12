package cn.dingrui.common.common.widget.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.dingrui.common.R;

/**
 * Created by ding192 on 2018/4/2.
 */

public abstract class BaseRecyclerAdapter<Data>
        extends RecyclerView.Adapter<BaseRecyclerAdapter.ViewHolder<Data>>
        implements View.OnClickListener, View.OnLongClickListener, AdapterCallBack<Data> {

    private List<Data> mDataList = new ArrayList<>();
    private AdapterListener<Data> mAdapterListener;

    /**
     * 无参，有参构造函数
     */
    public BaseRecyclerAdapter() {
        this(null);
    }

    public BaseRecyclerAdapter(AdapterListener<Data> adapterListener) {
        this(new ArrayList<Data>(), adapterListener);
    }

    public BaseRecyclerAdapter(List<Data> list, AdapterListener<Data> adapterListener) {
        this.mDataList = list;
        this.mAdapterListener = adapterListener;
    }

    /**
     * 复写默认的布局类型返回
     *
     * @param position 坐标
     * @return 类型，也就是布局类型xml的id
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));

    }

    /**
     * 得到布局类型
     *
     * @param position 坐标
     * @param data     当前数据
     * @return 类型，布局xml的id
     */
    protected abstract int getItemViewType(int position, Data data);

    /**
     * 创建一个ViewHolder
     *
     * @param parent   RecyclerView
     * @param viewType 界面的类型,约定为XML布局的Id
     * @return ViewHolder
     */
    @Override
    public ViewHolder<Data> onCreateViewHolder(ViewGroup parent, int viewType) {
        //得到LayoutInflater，将xml转化成view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(viewType, parent, false);
        //通过子类必须实现的方法创建holder
        ViewHolder<Data> holder = onCreateViewHolder(root, viewType);

        //设置点击事件
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);

        //设置view的tag为viewholder，进行双向绑定
        root.setTag(R.id.tag_recycler_holder, holder);

        //进行界面注解绑定
        holder.mUnbinder = ButterKnife.bind(this, root);
        //绑定callback
        holder.mCallBack = this;

        return holder;
    }

    /**
     * 得到一个新的ViewHolder
     *
     * @param root     跟布局
     * @param viewType 布局类型，xml的id
     * @return
     */
    protected abstract ViewHolder<Data> onCreateViewHolder(View root, int viewType);

    /**
     * 绑定数据到一个Holder上
     *
     * @param holder   ViewHolder
     * @param position 坐标
     */
    @Override
    public void onBindViewHolder(ViewHolder<Data> holder, int position) {
        //得到需要绑定的数据
        Data data = mDataList.get(position);
        //触发绑定的方法
        holder.bind(data);
    }

    /**
     * 得到当前集合数据量
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 插入一条数据并通知插入
     *
     * @param data Data
     */
    public void addData(Data data) {
        mDataList.add(data);
        notifyItemInserted(mDataList.size() - 1);
    }

    /**
     * 插入一堆数据，并通知这段集合更新
     *
     * @param dataList Data
     */
    public void addData(Data... dataList) {
        if (dataList != null && dataList.length > 0) {
            int startPos = mDataList.size();
            Collections.addAll(mDataList, dataList);
            notifyItemRangeInserted(startPos, dataList.length);
        }
    }

    /**
     * 插入一堆数据，并通知这段集合更新
     *
     * @param dataList Data
     */
    public void addData(Collection<Data> dataList) {
        if (dataList != null && dataList.size() > 0) {
            int startPos = mDataList.size();
            mDataList.addAll(dataList);
            notifyItemRangeInserted(startPos, dataList.size());
        }
    }

    /**
     * 删除操作
     */
    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 替换为一个新的集合，其中包括了清空
     *
     * @param dataList 一个新的集合
     */
    public void replace(Collection<Data> dataList) {
        mDataList.clear();
        if (dataList == null || dataList.size() == 0)
            return;
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    /**
     * 实现AdapterCallBack下的update
     * @param data
     * @param holder
     */
    @Override
    public void update(Data data, ViewHolder holder) {
        // 得到当前ViewHolder的坐标
        int pos = holder.getAdapterPosition();
        if (pos >= 0) {
            // 进行数据的移除与更新
            mDataList.remove(pos);
            mDataList.add(pos, data);
            // 通知这个坐标下的数据有更新
            notifyItemChanged(pos);
        }
    }

    @Override
    public void onClick(View v) {
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.mAdapterListener != null) {
            //得到点击viewholder的position
            int adapterPosition = viewHolder.getAdapterPosition();
            //回调
            this.mAdapterListener.onItemClick(viewHolder, mDataList.get(adapterPosition));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.mAdapterListener != null) {
            //得到点击viewholder的position
            int adapterPosition = viewHolder.getAdapterPosition();
            //回调
            this.mAdapterListener.onItemLongClick(viewHolder, mDataList.get(adapterPosition));
            return true;
        }
        return false;
    }

    /**
     * 设置适配器的监听
     *
     * @param adapterListener AdapterListener
     */
    public void setAdapterListener(AdapterListener<Data> adapterListener) {
        this.mAdapterListener = adapterListener;
    }

    /**
     * 自定义的监听器
     *
     * @param <Data> 泛型
     */
    public interface AdapterListener<Data> {
        // 当Cell点击的时候触发
        void onItemClick(BaseRecyclerAdapter.ViewHolder holder, Data data);

        // 当Cell长按时触发
        void onItemLongClick(BaseRecyclerAdapter.ViewHolder holder, Data data);
    }

    /**
     * 自定义的ViewHolder
     *
     * @param <Data> 范型类型
     */
    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder {
        private Data mData;
        private Unbinder mUnbinder;
        private AdapterCallBack<Data> mCallBack;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * 用于绑定数据额的触发
         *
         * @param data 绑定的数据
         */
        void bind(Data data) {
            this.mData = data;
            onBind(data);
        }

        /**
         * 当触发绑定数据的时候的回调，必须复写
         *
         * @param data
         */
        protected abstract void onBind(Data data);

        /**
         * Holder自己对自己对应的Data进行更新操作
         *
         * @param data Data数据
         */
        public void updateData(Data data) {
            if (mCallBack != null) {
                mCallBack.update(data, this);
            }
        }
    }

    /**
     * 对回调接口做一次实现AdapterListener
     *
     * @param <Data>
     */
    public static abstract class AdapterListenerImpl<Data> implements AdapterListener<Data> {

        @Override
        public void onItemClick(ViewHolder holder, Data data) {

        }

        @Override
        public void onItemLongClick(ViewHolder holder, Data data) {

        }
    }
}
