package com.example.historyandculture;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryStoriesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StoryAdapter adapter;
    private List<Story> storyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history_stories);

        recyclerView = findViewById(R.id.storyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        storyList = new ArrayList<>();
        storyList.add(new Story("SARNATH","(5th centuary BCE -6th centure CE)","UttarPradesh,India,approximately 10 kilometers northeast of the city of Varanasi",
                "About: The sacred site where Lord Buddha delivered his first sermon, " +
                        "the Dharmachakra Pravartana (Turning of the Wheel of Law), to his first disciples," +
                        " thus marking the beginning of the Buddhist Sangha. It is a major Buddhist pilgrimage center known for the ancient Deer" +
                        "Park,significant stupas like Dhamek Stupa,the ruins of the Mulagandha Kuti Vihara, and " +
                        "the Ashoka Pillar whose Lion Capital is the national emblem of India\n\n Empire: Maurya(c.322-185BCE)andGupta(c.320-550CE)empires." +
                        "\nType: Archaeological site(Buddhist stupa & monastery).",R.drawable.ic_sarnath));

        storyList.add(new Story("SUN TEMPLE","13th century (1250 CE)","Location: Konark , Odisha",
                "About: The Konark Sun Temple was built in the 13th century by King Narasimhadeva 1 of the Eastern Ganga dynasty. " +
                        "It is designed in the form of a giant stone chariot of the Sun God, with 24 carved wheels and seven horses" +
                        " The temple is a UNESCO World Heritage Site,famous\n\nThe temple originally had thress parts:\n" +
                        "a.Deul(Sanctum)-housed the main idol of Surya(now destroyed.\nb.Jagamohana(Audience Hall)richly decorated and still standing." +
                        "\nc.Nata Mandir(Dancing Hall- where ritual dances were performed.",R.drawable.ic_suntemple));

        storyList.add(new Story("ASHOKA PILLAR","Around 3rd century BCE(during the reign of Emperor Ashoka,c.268-232 BCE","",
                "About:Built by Emperor Ashoka of the Maurya dynasty to spread the message of Buddhism and moral values.\n" +
                        "Made of polished sandstones,finely carved and highly durable.Each pillar is about 40-50 feet tall and weighs several tons." +
                        "Erected at important places like Sarnath,Vaishali Nandangarh,Rampurva,andAllahabad.Inscription on the pillars(Ashoka edits)" +
                        "are written in Brahmi script,Prakrit language.Spread message of dhamma,non-violence,tolerance,and compassion." +
                        "The lion capital has four lions back-to-back,standing on a circular abacus decorated with animals like bull,elephant,horseand lion.",R.drawable.ic_ashokapillar1));

        storyList.add(new Story("MAURYA","(322 BCE)","", "About:The Mauryan Empire, founded in 322 BCE by Chandragupta Maurya with the help" +
                        "of his advisor Kautily, was ancient India's first large empire, uniting vast regions from" +
                        " Afghanistan to Bengal under a centralized administration. It grew under Chandragupta\nEmpire : Chandragupta Maurya" +
                              "\nType: Empire",R.drawable.ic_maurya1));

        storyList.add(new Story("HATHIGHUMPA","(Between the second century BCE and the first century CE)","Udayagiri Hills near Bhubaneswar in Odisha,India",
                "About:The Hathigumpha (\"Elephant Cave\") is a natural cavern in the Udayagiri hills near" +
                        "Bhubaneswar, Odisha, that holds a 17-line inscription of immense historical importance.Carved in the Brahmi script and Prakrit language," +
                        "the inscription chronicles the life andmilitary exploits of Jain King Kharavela of the Kalinga kingdom," +
                        "who likely ruled during the 1st or 2nd century BCE.\nEmpire : Kalinga kingdom\nType: Prakrit inscription\n" +
                        "Current Coordinates: 20°15'46.8\"N 85°47'08.4\"E",R.drawable.ic_hathighumpa));

        storyList.add(new Story("ELLORA CAVE","(6th–10th century CE)","Location: Konark , Odisha",
                "About: The sacred site where Lord Buddha delivered his first sermon, " +
                        "the Dharmachakra Pravartana (Turning of the Wheel of Law), to his first disciples," +
                        " thus marking the beginning of the Buddhist Sangha.",R.drawable.ic_elloracave));

        storyList.add(new Story("NATARAJA","9th century CE se 13th century CE","Location: Konark , Odisha",
                "About: The sacred site where Lord Buddha delivered his first sermon, " +
                        "the Dharmachakra Pravartana (Turning of the Wheel of Law), to his first disciples," +
                        " thus marking the beginning of the Buddhist Sangha.",R.drawable.ic_natraja1));

        storyList.add(new Story("JUNAGARH ROCK","3rd century BCE (around 250 BCE)","near Girnar hill, in the city of Junagarh, Gujarat, India",
                "About: The Junagadh Rock is a boulder in Gujarat, India, that contains a series of" +
                        "inscriptions from three major ancient Indian dynasties spanning hundreds of years. The site" +
                        " near Girnar Hill is a vital source for understanding ancient Indian history, governance, and linguistics." +
                        "\nEmpire : Mauryan Empire\nType: Rock Inscription\nCurrent Coordinates: 21.52°N 70.47°E",R.drawable.ic_junagarhrock));

        storyList.add(new Story("HARAPPA","(2600 BCE to 1900 BCE)","Sahiwal District of Punjab,Pakistan,situated on the banks of the Ravi River",
                "About: Harappa was a mojor city of the Indus Valley Civilization,located in modern-day Punjab,Pakistan. Discovered in the 1920s " +
                        ",this Bronze Age urban centre is significant for revealing an advanced civilization that existed thousand of years ago. Harappa cities" +
                        " featured sophisticated urban planning,including grid-patterned streets,well-planned houses with bathrooms," +
                        " and elaborate underground drainage systems\n\n Type:Archaeological site",R.drawable.ic_harappa));

        storyList.add(new Story("PALA DYNASTY","8th century CE se 12th century CE","Location: Konark , Odisha",
                "About: The sacred site where Lord Buddha delivered his first sermon, " +
                        "the Dharmachakra Pravartana (Turning of the Wheel of Law), to his first disciples," +
                        " thus marking the beginning of the Buddhist Sangha.",R.drawable.ic_pala));


        adapter  = new StoryAdapter(this,storyList);
        recyclerView.setAdapter(adapter);


    }
}