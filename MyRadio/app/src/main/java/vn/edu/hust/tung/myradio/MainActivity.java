package vn.edu.hust.tung.myradio;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import vn.edu.hust.tung.myradio.Modules.DatabaseHelper;
import vn.edu.hust.tung.myradio.Modules.ListRadioChannel;
import vn.edu.hust.tung.myradio.Modules.MyMediaPlayer;
import vn.edu.hust.tung.myradio.Objects.FavoriteChannel;
import vn.edu.hust.tung.myradio.Objects.RadioChannel;

public class MainActivity extends AppCompatActivity {

    static SQLiteDatabase db_write, db_read;
    static DatabaseHelper databaseHelper;
    static Play play;
    static Library library;
    static Favorite favorite;
    static Handler handler;
    static Runnable runnable;
    static Handler handlerMedia;
    static Runnable runnableMedia;
    static MyMediaPlayer mediaPlayer;

    static int time_fading = 4000;  //thời gian để cho cái seekbar volume biến mất kể từ khi bấm vào nó rồi thả tay ra (?)
    static int count1 = 0;
    static int id_lastChannel = 0;  //biến này lưu trữ vị trí kênh cuối cùng của lần mở app trước đó
    static int volume = 100;
    static ArrayList<FavoriteChannel> listFavoriteChannel;  //ds kênh yêu thích
    static RadioChannel radioChannel;       //kênh đang đc phát
    static ArrayList<RadioChannel> listRadio;       //ds các kênh radio
    static ArrayList<RadioChannel> listFavorite;    //ds kênh yêu thích
    static boolean isPlaying = false;
    static MediaManager mediaManager;
    static SeekBar seekBar;     //thanh âm lượng

    private SectionsPagerAdapter mSectionsPagerAdapter;
    static Button button_play, button_next, button_previous, button_favorite, button_volume;
    private ViewPager mViewPager;
    static TextView textView_loading;
    static TextView textView_radio_name;    //chứa tên của kênh ở tab playing
    static ImageView imageView_radio;   //ảnh của kênh ở tab playing

    Button btTimer, btRecord;
    boolean isTimerRunning = false;
    TimeTask timeTask = new TimeTask();
    int time=0;     //biến này đếm thời gian hẹn giờ
    int countDown=0;  //biến này cho biết khoảng thời gian cho biến time. CHÚ Ý: 2 BIẾN NÀY LUÔN CÓ ĐƠN VỊ LÀ GIÂY
    boolean isCancelTimer = false;

    public static MyAdapter myAdapterFavorite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        radioChannel = listRadio.get(id_lastChannel);
        mediaManager = new MediaManager(radioChannel);
        mediaManager.setVolume(volume / (float) 100);

        this.setTitle(radioChannel.getName());
        changeButtonFavorite();

        Log.i("main","on create");
        Log.i("main","radiochannel = "+radioChannel.getName());

        addEvents();
    }

    private void addEvents() {

        button_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    pauseMode();
                } else {
                    playMode();
                }
                mediaManager.setVolume(volume / (float) 100);
            }
        });
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if (radioChannel.getId() >= listRadio.size() - 1) {
                        return;
                    } else {
                        radioChannel = listRadio.get(radioChannel.getId() + 1);
                        changeRadio();
                        setTitle(radioChannel.getName());
                    }
                }catch (Exception e){
                    Log.i("main", "button next "+e.toString());
                }

            }
        });
        button_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioChannel.getId() <= 0) {
                    return;
                } else {
                    radioChannel = listRadio.get(radioChannel.getId() - 1);
                    changeRadio();
                    setTitle(radioChannel.getName());
                }
            }
        });
        button_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite()) {
                    setFavorite(false);
                } else {
                    setFavorite(true);
                }
            }
        });
        button_volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seekBar.isShown()) {
                    seekBar.setVisibility(View.GONE);
                    try {
                        handler.removeCallbacks(runnable);
                    } catch (Exception e) {

                    }
                } else {
                    seekBar.setVisibility(View.VISIBLE);
                    hideSeekBar();
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                count1 = 0;
                mediaManager.setVolume(progress / (float) 100);
                volume = progress;
                resetVolumeButton();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }


            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeTask=null;
        isCancelTimer = true;   //khi thoát app thì phải tắt timer đi, nếu ko nó cứ chạy mãi!
        //Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("main"," main on resume");
        if(!mediaPlayer.isPlaying()||!preparing)
            try{
                mediaManager.onPrepare();
            }catch (Exception e){
                Log.i("main","onresume "+e.toString());
            }
    }

    @Override //lưu trạng thái isPlaying và lưu mediaPlayer
    public void onSaveInstanceState(Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        instanceState.putBoolean("isPlaying", isPlaying);
        instanceState.putSerializable("mediaPlayer", mediaPlayer);
        Log.i("main","on save");
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("main","on restore");
        isPlaying = savedInstanceState.getBoolean("isPlaying");
        mediaPlayer = (MyMediaPlayer) savedInstanceState.getSerializable("mediaPlayer");
        textView_radio_name = (TextView) findViewById(R.id.textView_radio_name);
        if(isPlaying){
            playMode();
        }else{
            pauseMode();
        }
    }

    static void resetVolumeButton() {
        if (volume >= 60) {
            button_volume.setBackgroundResource(R.drawable.volume_up);
        } else if (volume < 60 && volume >= 30) {
            button_volume.setBackgroundResource(R.drawable.volume_down);
        } else if (volume < 30 && volume > 0) {
            button_volume.setBackgroundResource(R.drawable.volume_toolow);
        }
        if (volume == 0) {
            button_volume.setBackgroundResource(R.drawable.volume_off);
        }
    }

    static void hideSeekBar() {
        count1 = 0;
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                count1 = count1 + 1000;
                if (count1 >= time_fading) {
                    seekBar.setVisibility(View.GONE);
                    handler.removeCallbacks(this);
                    return;
                }
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnable);
    }

    static boolean isFavorite() {   //kiểm tra xem kênh đang đc phát radioChannel có phải là 1 kênh yêu thích ko
        Cursor cursor = null;
        boolean check = false;
        try {
            String query = "select * from favorite where id_radio = " + radioChannel.getId() + ";";
            cursor = db_read.rawQuery(query, null);
            if (cursor.moveToNext()) {
                check = true;
            } else {
                check = false;
            }
        } catch (Exception e) {
            Log.i("main", "error" + e.toString());
        }
        return check;
    }
    static void setFavorite(boolean set_favorite) {
        //nếu set_favorite = true thì thêm kênh radioChannel vào database,
        //nếu set_favorite = false thì xóa bỏ radioChannel khỏi database, chú ý là biến radioChannel là biến static

        if (set_favorite) { //thêm kênh đang phát vào ds yêu thích
            String query = "insert into favorite (id_radio) values(" + radioChannel.getId() + ");";
            db_write.execSQL(query);
            button_favorite.setBackgroundResource(R.drawable.favorite);
        } else {    //xóa kênh đang phát khỏi ds yêu thích
            String query = "select * from favorite where id_radio = " + radioChannel.getId() + ";";
            Cursor cursor = db_read.rawQuery(query, null);
            cursor.moveToNext();
            int temp_id = cursor.getInt(cursor.getColumnIndex("_id"));
            String query2 = "delete from favorite where _id = " + temp_id + ";";
            db_write.execSQL(query2);
            button_favorite.setBackgroundResource(R.drawable.ic_favorite_border_white_48dp);
        }
        listFavoriteChannel = getListFavoriteChannel();     //lấy lại ds yêu thích từ database, chú ý là database vừa đc update ở trên (?)
        listFavorite = ListRadioChannel.getListFavorite(listFavoriteChannel);   //(?)
        try {
            myAdapterFavorite.clear();
            myAdapterFavorite.addAll(listFavorite);
            myAdapterFavorite.notifyDataSetChanged();
        } catch (Exception e) {
            Log.i("main", "my adapter f " + e.toString());
        }
    }
    static void changeButtonFavorite() {
        if (isFavorite()) {
            button_favorite.setBackgroundResource(R.drawable.favorite);
        } else {
            button_favorite.setBackgroundResource(R.drawable.ic_favorite_border_white_48dp);
        }

    }

    static void changeRadio() {
        try{
            mediaPlayer.reset();
            handlerMedia.removeCallbacks(runnableMedia);
        }catch (Exception e){
            Log.i("main",e.toString());
        }
        mediaManager.releaseRadio();
        mediaManager = new MediaManager(radioChannel);
        mediaManager.setVolume(volume / (float) 100);
        playMode();
        play.reset();
        changeButtonFavorite();
    }

    void pauseMode() {
        isPlaying = false;
        mediaManager.pauseRadio();
        button_play.setBackgroundResource(R.drawable.ic_play_circle_outline_white_48dp);
    }

    static void playMode() {
        isPlaying = true;
        mediaManager.startRadio();
        button_play.setBackgroundResource(R.drawable.ic_pause_circle_outline_white_48dp);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.item_schedule) {
            Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
            startActivity(intent);
        }

        if(id == R.id.item_about) {
            AlertDialog.Builder aBuilder = new AlertDialog.Builder(MainActivity.this);
            aBuilder.setTitle("About")
                    .setIcon(R.drawable.like)
                    .setMessage("This app was created by group 12");

            aBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = aBuilder.create();
            alertDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    int count = 0;

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            count++;
            if (position == 0) {
                return play;

            } else if (position == 1) {
                return library;

            } else if (position == 2) {
                return favorite;

            } else {
                return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Playing";
                case 1:
                    return "Library";
                case 2:
                    return "Favorite";
            }
            return null;
        }
    }

    static public ArrayList<FavoriteChannel> getListFavoriteChannel() {
        ArrayList<FavoriteChannel> list = new ArrayList<>();
        FavoriteChannel favoriteChannel;

        String query = "select * from favorite order by id_radio;";
        Cursor cursor = db_read.rawQuery(query, null);
        while (cursor.moveToNext()) {
            favoriteChannel = new FavoriteChannel(cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getInt(cursor.getColumnIndex("id_radio")));
            list.add(favoriteChannel);
        }

        return list;
    }

    void init() {

        databaseHelper = new DatabaseHelper(this);
        db_write = databaseHelper.getWritableDatabase();
        db_read = databaseHelper.getReadableDatabase();

        play = new Play();
        library = new Library();
        favorite = new Favorite();

        listRadio = ListRadioChannel.getListRadio();
        listFavoriteChannel = getListFavoriteChannel();
        listFavorite = ListRadioChannel.getListFavorite(listFavoriteChannel);

        button_favorite = (Button) findViewById(R.id.button_favorite);
        button_next = (Button) findViewById(R.id.button_next);
        button_play = (Button) findViewById(R.id.button_play);
        button_previous = (Button) findViewById(R.id.button_previous);
        button_volume = (Button) findViewById(R.id.button_volume);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        seekBar = (SeekBar) findViewById(R.id.seekbar_volume);
        seekBar.setVisibility(View.GONE);
        textView_loading = (TextView) findViewById(R.id.textView_loading);

        myAdapterFavorite = new MyAdapter(this, listFavorite);

        try {
            String query = "select * from last_state;";
            Cursor cursor = db_read.rawQuery(query, null);
            if (cursor.moveToLast()) {
                id_lastChannel = cursor.getInt(cursor.getColumnIndex("id_radio"));
                volume = cursor.getInt(cursor.getColumnIndex("volume"));
            }
            Log.i("main", "last state = " + id_lastChannel + " " + volume + " cursor size = " + cursor.getCount());

        } catch (Exception e) {
            Log.i("main", e.toString());
        }
        seekBar.setProgress(volume);
        resetVolumeButton();

        btTimer= (Button) findViewById(R.id.btTimer);
        btTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isTimerRunning) {   //Nếu chưa thiết lập hẹn giờ (timer):
                    //cho phép thiết lập hẹn giờ:

                    final Dialog customDialog = new Dialog(MainActivity.this);
                    customDialog.setTitle("Sleep timer");
                    customDialog.setContentView(R.layout.sleep_timer);

                    final EditText txtTimer = (EditText) customDialog.findViewById(R.id.txtTimer);
                    Button btOK = (Button) customDialog.findViewById(R.id.btOK);
                    Button btCancel = (Button) customDialog.findViewById(R.id.btCancel);
                    final Spinner spinnerUnit = (Spinner) customDialog.findViewById(R.id.spinnerUnit);
                    ArrayList<String> listUnit = new ArrayList<String>();
                    listUnit.add("Seconds");
                    listUnit.add("Minutes");
                    ArrayAdapter<String> adapterUnit = new ArrayAdapter<String>(customDialog.getContext(), android.R.layout.simple_spinner_item, listUnit);
                    adapterUnit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerUnit.setAdapter(adapterUnit);

                    btOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            timeTask = new TimeTask();
                            String timerStr = txtTimer.getText().toString().trim();
                            if(!timerStr.equals("")) {
                                if (spinnerUnit.getSelectedItemPosition() == 1)
                                    countDown = Integer.valueOf(timerStr) * 60;  //don vi la phut
                                else countDown = Integer.valueOf(timerStr);     //don vi la giay

                                if (countDown > 0) {
                                    timeTask.execute(countDown);
                                    isCancelTimer = false;
                                    customDialog.dismiss();
                                    btTimer.setBackgroundResource(R.drawable.alarm_off);
                                    isTimerRunning = true;
                                } else  Toast.makeText(MainActivity.this, "must be greater 0", Toast.LENGTH_SHORT).show();
                            } else  Toast.makeText(MainActivity.this, "Please enter a number", Toast.LENGTH_SHORT).show();
                        }
                    });
                    btCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            customDialog.dismiss();
                        }
                    });
                    customDialog.show();

                } else {    //nếu đang có hẹ giờ (timer) mà lại ấn vào button này thì:
                    //ko cho phép thiết lập timer và hỏi xem người dùng muốn hủy timer hay ko:
                    AlertDialog.Builder aBuilder = new AlertDialog.Builder(MainActivity.this);
                    aBuilder.setTitle("Timer is running")
                            .setMessage("Time left: "+convertSecondTimeToMinute(countDown - time)+"\nDo you want to cancel?")
                            .setIcon(R.drawable.alarm2)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(timeTask != null && timeTask.getStatus() != AsyncTask.Status.FINISHED) {
                                        isTimerRunning = false;
                                        isCancelTimer=true;
                                        Toast.makeText(MainActivity.this, "Canceled timer", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        btTimer.setBackgroundResource(R.drawable.alarm_on);
                                    }
                                }
                            })
                            .setNegativeButton("No, continue timer", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alertDialog = aBuilder.create();
                    alertDialog.show();

                }
            }
        });
        btTimer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast timerToast = Toast.makeText(MainActivity.this, "set sleep timer", Toast.LENGTH_SHORT);
//                timerToast.setMargin(25, 25);
//                timerToast.setGravity(Gravity.CENTER, 50, -50);
                timerToast.show();

                return false;
            }
        });

        btRecord= (Button) findViewById(R.id.btRecord);
        btRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, RecordActivity.class);
                startActivity(intent);

            }
        });
        btRecord.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "record playing channel", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    @Override
    protected void onStop() {   //lưu radioChannel vào database: nghĩa là lưu kênh đang phát vào để sau này mở lại app thì bắt đầu từ kênh đó
        try {
            String q1 = "delete from last_state;";
            db_write.execSQL(q1);
        } catch (Exception e) {
            Log.i("main", "onDestroy " + e.toString());
        }
        try {
            String query = "insert into last_state (id_radio,volume) values (" + radioChannel.getId() + "," + volume + ");";
            db_write.execSQL(query);
        } catch (Exception e) {
            Log.i("main", "onDestroy " + e.toString());
        }
        super.onStop();
    }

    ////3 class sau có sẵn khi tạo project, vì ban đầu ta ko chọn empty project,
    ////sau đó chỉnh sửa thêm để có đc 3 tab như trong layout...
    ///mỗi class có layout tương ứng là 1 tab của main_layout

    public static class Play extends Fragment{

        public void reset() {
            textView_radio_name.setText(radioChannel.getName());
            imageView_radio.setImageResource(radioChannel.getIdImage());
            getActivity().setTitle(radioChannel.getName());
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.play, container, false);
            textView_radio_name = (TextView) view.findViewById(R.id.textView_radio_name);
            imageView_radio = (ImageView) view.findViewById(R.id.imageView_radio);
            textView_radio_name.setText(radioChannel.getName());
            imageView_radio.setImageResource(radioChannel.getIdImage());
            Log.i("main","play on create view ");
            return view;
        }
    }

    public static class Library extends Fragment implements Serializable {
        private ListView listView_radio;
        private ArrayAdapter<RadioChannel> listAdapter;
        private MyAdapter myAdapter;

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            listView_radio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (radioChannel.getId() == listRadio.get(position).getId()) {
                        return;
                    }
                    radioChannel = listRadio.get(position);
                    changeRadio();
                    getActivity().setTitle(radioChannel.getName());
                }
            });
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.library, container, false);

//            listView_radio = (ListView) view.findViewById(R.id.listView_radio);
//            listAdapter = new ArrayAdapter<RadioChannel>(getActivity(), R.layout.list_radio_template, R.id.textView_radio, listRadio);
//            listView_radio.setAdapter(listAdapter);
//
//            return view;
            listView_radio = (ListView) view.findViewById(R.id.listView_radio);
            myAdapter = new MyAdapter(this.getActivity(), listRadio);
            listView_radio.setAdapter(myAdapter);

            return view;
        }
    }

    public static class Favorite extends Fragment implements Serializable {
        private ListView listView_radio;
        private ArrayAdapter<RadioChannel> listAdapter;

        public void reset() {
            myAdapterFavorite = new MyAdapter(this.getActivity(), listFavorite);
            listView_radio.setAdapter(myAdapterFavorite);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            listView_radio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (radioChannel.getId() == listFavorite.get(position).getId()) {
                        return;
                    }
                    radioChannel = listFavorite.get(position);
                    changeRadio();
                    getActivity().setTitle(radioChannel.getName());
                }
            });
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.library, container, false);

            listView_radio = (ListView) view.findViewById(R.id.listView_radio);
            listView_radio.setAdapter(myAdapterFavorite);
            return view;
        }
    }

    static int check = 0;
    static boolean preparing = false;

    public static class MediaManager implements Serializable {
        boolean prepared = false;
        float volume = 0;

        public MediaManager(RadioChannel radioChannel) {
            prepared = false;
            try {
                mediaPlayer = new MyMediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(radioChannel.getURL());

            } catch (Exception e) {

            }
        }
        public void onPrepare(){
            mediaPlayer.prepareAsync();
        }

        public void setVolume(float volume) {
            this.volume = volume;
            try {
                mediaPlayer.setVolume(volume, volume);
            } catch (Exception e) {

            }
        }

        public void startRadio() {
            mediaPlayer.setVolume(volume, volume);
            if (prepared) {
                mediaPlayer.start();
                return;
            }
            check = 0;
            handlerMedia = new Handler();
            runnableMedia = new Runnable() {
                @Override
                public void run() {
                    preparing = true;
                    check += 100;
                    if (check == 100) textView_loading.setText("Loading");
                    else if (check == 200) textView_loading.setText("Loading.");
                    else if (check == 300) textView_loading.setText("Loading..");
                    else {
                        textView_loading.setText("Loading...");
                        check = 0;
                    }
                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                    } else {
                        textView_loading.setText("");
                        handlerMedia.removeCallbacks(this);
                        prepared = true;
                        preparing = false;
                        return;
                    }
                    handlerMedia.postDelayed(this, 200);
                }
            };
            handlerMedia.post(runnableMedia);
        }

        public void pauseRadio() {
            mediaPlayer.pause();
        }

        public void releaseRadio() {
            mediaPlayer.release();
        }
    }

    class TimeTask extends AsyncTask<Integer, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            time = 0;
            Toast.makeText(MainActivity.this, "Starting timer", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            try {
                if (aBoolean == true) {
                    Toast.makeText(MainActivity.this, "time's up", Toast.LENGTH_SHORT).show();
                    isTimerRunning = false;
                    if(isPlaying) pauseMode();
                    //android.os.Process.killProcess(android.os.Process.myPid());     //chú ý: dùng lệnh này thì ko thể thực hiện các lệnh ở trên trước mà nó kill app luôn
//                    finish();
//                    finishAffinity();
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.putExtra("EXIT", true);
//                    startActivity(intent);
//
//                    if(getIntent().getBooleanExtra("EXIT", false)) {
//                        finish();
//                        android.os.Process.killProcess(android.os.Process.myPid());
//                    }
                    finish();
                    System.exit(0);     //Thoát cả cái app luôn, kill ngay lập tức
                }
            } catch (Exception e) {
                Log.i("main", e.getMessage());
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(Integer... params) {   //đơn vị của params là giây nhé, ko phải phút hay mini giây
            if(params[0] > 0) {
                int i;
                int limitTime = params[0];
                try{
                    for (i = 0; i < limitTime; i++) {
                        time++;
                        Thread.sleep(1000);
                        if(isCancelTimer) {
                            this.cancel(true);
                            break;
                        }
                        publishProgress(time);
                    }
                } catch (Exception e){
                    Log.i("main","" + e.toString());
                }
                return true;
            }
            return false;
        }
    }

    private String convertSecondTimeToMinute(int second) {
        int min = second/60;
        int sec = second - min*60;

        return min+":"+sec;
    }

    public Activity getThis() {
        return this;
    }
}
