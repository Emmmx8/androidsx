package cn.mmvtc.shop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cn.mmvtc.shop.database.SQLiteHelper;

public class ShoppingActivity extends Activity{
    private ListView mListView;
    SQLiteHelper mSQLiteHelper;
    String id;
    TextView Shopping_name,content;
    private String[] titles={"Redmi k30", "小米CC9", "小米9", "Redmi 8", "Redmi note8"};
    private  String[] prices={"1599元", "2599元", "3699元", "699元", "1199元"};
    //图片数据集合
    private int[] icons={R.drawable.k30, R.drawable.cc9, R.drawable.mi9, R.drawable.mi8, R.drawable.note8};
    private android.view.LayoutInflater LayoutInflater;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        Button shoppingcar=(Button) findViewById(R.id.shoppingcar);
        mListView=(ListView) findViewById(R.id.lv);//初始化ListView控件
        MybaseAdapter mAdapter =new MybaseAdapter();
        mSQLiteHelper = new SQLiteHelper(this);
        mListView.setAdapter(mAdapter);
        shoppingcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShoppingActivity.this,ShoppinglistActivity.class);
                startActivityForResult(intent,1);
            }
        });
        initData();
    }
    protected void initData(){
//        mSQLiteHelper=new SQLiteHelper(this);
//        Shopping_name.setText("购物车");
}
    class MybaseAdapter extends BaseAdapter {
            @Override
            public int getCount() { //获取item的总数
                return titles.length;//返回ListView Item条目的总数
            }
            @Override
            public Object getItem(int position) {
                return titles[position];//返回Item的数据对象
            }
            @Override
            public long getItemId(int position) {
                return position; //返回Item的id
            }
            //得到Item的View视图
            class ViewHolder {
                TextView title, price;
                ImageView iv;
                Button addshop;

            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                ViewHolder holder = null;
                if (convertView == null) {
                    convertView = View.inflate(ShoppingActivity.this, R.layout.list_item, null);
                    holder = new ViewHolder();
                    holder.title =  convertView.findViewById(R.id.title);
                    holder.price = convertView.findViewById(R.id.price);
                    holder.iv =  convertView.findViewById(R.id.iv);
                    holder.addshop = convertView.findViewById(R.id.addshop);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.addshop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean n = mSQLiteHelper.insertData(titles[position],prices[position],icons[position]);
                        if(n){
                            Toast.makeText(ShoppingActivity.this,"已经加入购物车",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ShoppingActivity.this,"噢，出错了哦~",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                holder.title.setText(titles[position]);
                holder.price.setText(prices[position]);
                holder.iv.setBackgroundResource(icons[position]);
                return convertView;
            }
        }
    }
