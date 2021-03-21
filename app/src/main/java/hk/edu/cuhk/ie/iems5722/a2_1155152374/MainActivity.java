package hk.edu.cuhk.ie.iems5722.a2_1155152374;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView lv = findViewById(R.id.main_lv);
        ChatRoomAdapter mchatroomadapter;
        ArrayList<Util.chatroom> chatrooms = new ArrayList<>();
        mchatroomadapter = new ChatRoomAdapter(this, chatrooms);
        lv.setAdapter(mchatroomadapter);

        get_ChatroomsTask c = new get_ChatroomsTask(mchatroomadapter, chatrooms);
        c.execute();
              /*  new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String s = Util.get_chatrooms();
                        JSONObject json = null;
                        try {
                            json = new JSONObject(s);
                            JSONArray array = json.getJSONArray("data");
                            for(int i=0; i<array.length(); i++){
                                String name = array.getJSONObject(i).getString("name");
                                int id = array.getJSONObject(i).getInt("id");
                                chatrooms.add(new chatroom(id, name));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //String status = json.getString("status" ) ;

                        lv.post(new Runnable() {
                            @Override
                            public void run() {
                                mchatroomadapter.notifyDataSetChanged();
                                //test_tv.setText(s);
                            }
                        });
                    }
                }).start();*/

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(MainActivity.this, ChatActivity.class);
                        i.putExtra("id", chatrooms.get(position).id);
                        i.putExtra("name", chatrooms.get(position).name);
                        startActivity(i);
                    }
                });


    }



    public class ChatRoomAdapter  extends BaseAdapter{

        private Context mContext;
        private ArrayList<Util.chatroom> chatrooms;
        private LayoutInflater layoutInflater;

        public ChatRoomAdapter(Context mContext, ArrayList<Util.chatroom> chatrooms){
            this.chatrooms = chatrooms;
            this.mContext = mContext;
            layoutInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return chatrooms.size();
        }

        @Override
        public Object getItem(int position) {
            return chatrooms.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            viewholder holder;
            if(convertView==null){
                holder = new viewholder();
                convertView = layoutInflater.inflate(R.layout.chatroomlistview, null);
                holder.tv = (TextView) convertView.findViewById(R.id.chatroomlist_tv);
                convertView.setTag(holder);
            }
            else{
                holder = (viewholder) convertView.getTag();
            }
            Util.chatroom c = chatrooms.get(position);
            holder.tv.setText(c.getName());
            return convertView;
        }

        private class viewholder{
            TextView tv;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}