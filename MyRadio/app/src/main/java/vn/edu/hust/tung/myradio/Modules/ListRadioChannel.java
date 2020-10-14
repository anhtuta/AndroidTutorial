package vn.edu.hust.tung.myradio.Modules;

import java.util.ArrayList;

import vn.edu.hust.tung.myradio.Objects.FavoriteChannel;
import vn.edu.hust.tung.myradio.Objects.RadioChannel;
import vn.edu.hust.tung.myradio.R;

/**
 * Created by tung on 3/14/17.
 */

public class ListRadioChannel {
    private  static ArrayList<RadioChannel> listRadio;

    private static String name[] = {"Magic 80s Florida",
            "BBC Radio 1",
            "BBC Radio 2",
            "Box UK Radio danceradiouk",
            "Smash! - 90s & 00s Hits Non Stop!",
            "Coast TWO - Tenerife - Non Stop Dance",
            "1FM - Music From The 1960s - Today!",
            "Stoke Mandeville Hospital Radio",
            "First Step Radio Live",
            "Magic 60s Florida",
            "Magic 70s Florida",
            "Play 105",
            "Classic Rock Florida - SHE Radio",
            "1POWER",
            "108.fm The Office"};

    private static int id_image[] = {R.drawable.us_radio,
            R.drawable.bbc1, R.drawable.bbc2, R.drawable.danceuk, R.drawable.hit90,
            R.drawable.coasttwo, R.drawable.onefm, R.drawable.stoke,
            R.drawable.firststep, R.drawable.us_radio, R.drawable.us_radio, R.drawable.play105,
            R.drawable.classicrockfloridalogo, R.drawable.us_radio, R.drawable.us_radio
    };
    private static String url[] = {"http://airspectrum.cdnstream1.com:8018/1606_192",
            "http://bbcmedia.ic.llnwd.net/stream/bbcmedia_radio1_mf_p",
            "http://bbcmedia.ic.llnwd.net/stream/bbcmedia_radio2_mf_p",
            "http://uk2.internet-radio.com:8145",
            "http://uk6.internet-radio.com:8433",
            "http://uk1.internet-radio.com:15614",
            "http://uk2.internet-radio.com:8008",
            "http://uk5.internet-radio.com:8237",
            "http://uk5.internet-radio.com:8085/live",
            "http://airspectrum.cdnstream1.com:8008/1604_128",
            "http://airspectrum.cdnstream1.com:8012/1605_192",
            "http://us4.internet-radio.com:8122",
            "http://us1.internet-radio.com:8105",
            "http://powerhitz.powerhitz.com:5040",
            "http://powerhitz.powerhitz.com:2228/"
    };
    public static ArrayList<RadioChannel> getListRadio(){
        listRadio = new ArrayList<>();
        for(int i=0; i<name.length ; i++){
            listRadio.add(new RadioChannel(i, name[i], url[i], id_image[i]));
        }
        return listRadio;
    }
    public static ArrayList<RadioChannel> getListFavorite(ArrayList<FavoriteChannel> listFavorite){
        listRadio = new ArrayList<>();
        for(int i=0; i<listFavorite.size() ; i++){
            int index = listFavorite.get(i).getId_radio();
            listRadio.add(new RadioChannel(index, name[index], url[index], id_image[index]));
        }
        return listRadio;
    }
}
