package theissl.stefan.picturepuzzle;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private float x1, x2, y1, y2;
    boolean gameIsRunning = false;
    static final int MIN_DISTANCE = 150;
    ImageButton[] allButtons;
    int[] images = new int[16];
    ArrayList<PictureField> allFields, availableFields;
    PictureField emptyField;
    TextView txt_info, txt_winLose;
    Button btn_newGame;
    ImageButton btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_10, btn_11, btn_12, btn_13, btn_14, btn_15, btn_16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View myView = getWindow().getDecorView();
        myView.setBackgroundColor(Color.BLACK);

        allFields = new ArrayList<PictureField>();
        availableFields = new ArrayList<PictureField>();

        txt_info = (TextView) findViewById(R.id.txt_info);
        txt_winLose = (TextView) findViewById(R.id.txt_winLose);

        txt_info.setTextColor(Color.WHITE);
        txt_winLose.setTextColor(Color.WHITE);

        btn_newGame = (Button) findViewById(R.id.btn_newGame);
        btn_newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGame();
            }
        });


        //reference the  ImageButtons
        btn_1 = (ImageButton) findViewById(R.id.btn_1);
        btn_2 = (ImageButton) findViewById(R.id.btn_2);
        btn_3 = (ImageButton) findViewById(R.id.btn_3);
        btn_4 = (ImageButton) findViewById(R.id.btn_4);
        btn_5 = (ImageButton) findViewById(R.id.btn_5);
        btn_6 = (ImageButton) findViewById(R.id.btn_6);
        btn_7 = (ImageButton) findViewById(R.id.btn_7);
        btn_8 = (ImageButton) findViewById(R.id.btn_8);
        btn_9 = (ImageButton) findViewById(R.id.btn_9);
        btn_10 = (ImageButton) findViewById(R.id.btn_10);
        btn_11 = (ImageButton) findViewById(R.id.btn_11);
        btn_12 = (ImageButton) findViewById(R.id.btn_12);
        btn_13 = (ImageButton) findViewById(R.id.btn_13);
        btn_14 = (ImageButton) findViewById(R.id.btn_14);
        btn_15 = (ImageButton) findViewById(R.id.btn_15);
        btn_16 = (ImageButton) findViewById(R.id.btn_16);

        images = new int[]{R.drawable.r0s1, R.drawable.r0s2, R.drawable.r0s3, R.drawable.r0s4, R.drawable.r1s1, R.drawable.r1s2, R.drawable.r1s3, R.drawable.r1s4, R.drawable.r2s1, R.drawable.r2s2, R.drawable.r2s3, R.drawable.r2s4, R.drawable.r3s1, R.drawable.r3s2, R.drawable.r3s3, R.drawable.r3s4};

        allButtons = new ImageButton[]{btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,btn_10, btn_11, btn_12, btn_13, btn_14, btn_15, btn_16};

        //create OnClickListeners for all Buttons
        for(ImageButton btn : allButtons)
        {
            btn.setOnTouchListener(this);
        }

        newGame();
    }


    public void newGame(){
        txt_winLose.setText("Noch nicht korrekt!");
        txt_info.setText("");
        btn_newGame.setEnabled(false);

        availableFields.clear();
        allFields.clear();

        //set EmptyField
        Random rnd = new Random();
        int r = rnd.nextInt(16);


        //set Random Order
        int[] randomNumbers = new int[16];

        for(int i = 0; i<16;i++){
                int rand = rnd.nextInt(16);
                boolean notYetUsed = true;
                for(int y = 0; y<i;y++)
                    if(randomNumbers[y] == rand)
                        notYetUsed = false;
                if(notYetUsed)
                    randomNumbers[i] = rand;
                else
                    i--;
        }

        for(int i = 0; i<4; i++)
        {
            for(int x = 0; x<4; x++)
            {
                int curNr = i*4+x;
                if(curNr != r) //check if Field is empty
                    allFields.add(new PictureField(
                            x, i,
                            (randomNumbers[curNr]%4),
                            (randomNumbers[curNr]/4),
                            images[randomNumbers[curNr]], allButtons[curNr]));
                else
                    allFields.add(emptyField = new PictureField(
                            x, i,
                            (randomNumbers[curNr]%4),
                            (randomNumbers[curNr]/4),
                            0,
                            allButtons[curNr]));
            }
        }

        checkAvailableFields();

        gameIsRunning = true;
    }

    public void checkAvailableFields(){
        availableFields.clear();
        for(PictureField p:allFields)
        {
            if((p.getCurRow() == emptyField.getCurRow() && (p.getCurCol() == emptyField.getCurCol()-1 || p.getCurCol() == emptyField.getCurCol()+1)) ||
                (p.getCurCol() == emptyField.getCurCol() && (p.getCurRow() == emptyField.getCurRow()-1 || p.getCurRow() == emptyField.getCurRow()+1)))
                availableFields.add(p);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        ImageButton btn = (ImageButton) view;
        PictureField curField = null;
        boolean isAvailabe = false;

        //get current PictureField
        for(PictureField p: availableFields){
            if(p.getCurBtn() == btn){
                isAvailabe = true;
                curField = p;
            }
        }

        if(isAvailabe && gameIsRunning)
            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    x1 = event.getX();
                    y1 = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    x2 = event.getX();
                    y2 = event.getY();
                    float deltaX = x2 - x1;
                    float deltaY = y2 - y1;
                    if (deltaX > MIN_DISTANCE && Math.abs(deltaY) < MIN_DISTANCE)
                    {
                        txt_info.setText("left2right swipe");
                        if(curField.getCurCol() == emptyField.getCurCol()-1)
                        {
//                            float x = new Float(curField.getCurBtn().getX());
//                            float y = new Float(curField.getCurBtn().getY());
//                            float emptyx = new Float(emptyField.getCurBtn().getX());
//                            float emptyy = new Float(emptyField.getCurBtn().getY());
//                            Animation animation = new TranslateAnimation(0, emptyx-x, 0, emptyy-y);
//                            animation.setDuration(500);
//                            animation.setFillAfter(true);
//                            curField.getCurBtn().startAnimation(animation);
//                            animation = new TranslateAnimation(0, x-emptyx, 0, y-emptyy);
//                            animation.setDuration(500);
//                            animation.setFillAfter(true);
//                            emptyField.getCurBtn().startAnimation(animation);
//                            curField.getCurBtn().setX(emptyx);
//                            curField.getCurBtn().setY(emptyy);
//                            curField.getCurBtn().setX(x);
//                            curField.getCurBtn().setY(y);

                            executeMove(true, 1, -1, curField);
                        }
                    }
                    else if(deltaX < -(MIN_DISTANCE) && Math.abs(deltaY) < MIN_DISTANCE)
                    {
                        txt_info.setText("right2left swipe");
                        if(curField.getCurCol() == emptyField.getCurCol()+1)
                            executeMove(true, -1, 1, curField);
                    }
                    else if (deltaY > MIN_DISTANCE && Math.abs(deltaX) < MIN_DISTANCE)
                    {
                        txt_info.setText("down swipe");
                        if(curField.getCurRow() == emptyField.getCurRow()-1)
                            executeMove(false, 1, -1, curField);
                    }
                    else if(deltaY < -(MIN_DISTANCE) && Math.abs(deltaX) < MIN_DISTANCE)
                    {
                        txt_info.setText("up swipe");
                        if(curField.getCurRow() == emptyField.getCurRow()+1)
                            executeMove(false, -1, 1, curField);
                    }
                    else
                        txt_info.setText("move undetectable");

                    break;
            }


        return false;
    }

    public void executeMove(boolean isCol, int changeCurBtn, int changeEmpty, PictureField curField){
        ImageButton curButton = curField.getCurBtn();
        ImageButton emptyButton = emptyField.getCurBtn();
        curField.setCurBtn(emptyButton);
        emptyField.setCurBtn(curButton);

        if(isCol)
        {
            curField.setCurCol(curField.getCurCol()+changeCurBtn);
            emptyField.setCurCol(emptyField.getCurCol()+changeEmpty);
        }
        else
        {
            curField.setCurRow(curField.getCurRow()+changeCurBtn);
            emptyField.setCurRow(emptyField.getCurRow()+changeEmpty);
        }

        if(!checkForWin())
            checkAvailableFields();
        else
        {
            txt_winLose.setText("Sie haben gewonnen!");
            btn_newGame.setEnabled(true);
            gameIsRunning = false;
        }
    }

    public boolean checkForWin(){
        boolean allCorrect = true;
        for(PictureField p: allFields){
            if(!(p.getCurCol() == p.getRightCol() && p.getCurRow() == p.getRightRow()))
            {
                allCorrect = false;
                break;
            }
        }

        return allCorrect;
    }
}

