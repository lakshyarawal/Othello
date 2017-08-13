package com.example.lakshya.othello;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener {
    LinearLayout mainLayout;
    LinearLayout rowLayouts[];
    MyButton[][] buttons;
    public static int n = 8;
    boolean playerBTurn ;
    boolean gameOver = false;
    public final static boolean NO_CHIP = false;
    public final static boolean CHIP = true;
    TextView blackTextView,whiteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerBTurn =true;
        blackTextView = (TextView) findViewById(R.id.blackCount);
        whiteTextView = (TextView) findViewById(R.id.whiteCount);
        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        setUpBoard();

    }
    public void setUpBoard(){
        buttons = new MyButton[n][n];
        rowLayouts = new LinearLayout[n];
        for(int i = 0; i < n; i++){
            rowLayouts[i] = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0, 1f);
            rowLayouts[i].setLayoutParams(params);
            rowLayouts[i].setOrientation(LinearLayout.HORIZONTAL);
            mainLayout.addView(rowLayouts[i]);
        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                buttons[i][j] = new MyButton(this);
                buttons[i][j].a = i;
                buttons[i][j].b = j;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
                params.setMargins(5,5,5,5);
                buttons[i][j].setLayoutParams(params);
                buttons[i][j].setOnClickListener(this);
                buttons[i][j].setOnLongClickListener(this);
                buttons[i][j].setTextSize(50);
                buttons[i][j].setBackgroundColor(Color.parseColor("#FF669900"));
                buttons[i][j].setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                rowLayouts[i].addView(buttons[i][j]);
                if((i==j&&j==3)||(i==j&&j==4)){
                       buttons[i][j].setBackgroundResource(R.drawable.whitechip);
                    buttons[i][j].chip = CHIP;
                    buttons[i][j].color ="White";

                }
                if((i==3&&j==4)||(i==4&&j==3)){
                    buttons[i][j].setBackgroundResource(R.drawable.blackchip);
                    buttons[i][j].chip = CHIP;
                    buttons[i][j].color ="Black";
                }
            }
        }
        checkOptionsBlack();
    }
    public void onClick(View view){
        if(gameOver)return;
        MyButton button = (MyButton) view;
        if(button.chip){
            Toast.makeText(this,"INVALID MOVE",Toast.LENGTH_LONG).show();
        }
if(playerBTurn){
    placeChipBlack(button);
    checkOptionsWhite();
}
else{
    placeChipWhite(button);
    checkOptionsBlack();
}
        playerBTurn = !playerBTurn;
        checkWin();


    }

    private void checkWin() {
        boolean gameOver1 = true;
        boolean gameOver2 = false;
        int blackCount = getBlackCount();
        int whiteCount = getWhiteCount();
        blackTextView.setText(blackCount+"");
        whiteTextView.setText(whiteCount+"");
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(buttons[i][j].getText().toString().equals("."))gameOver1=false;
            }
        }

        if (gameOver1){
            gameOver = checkShift();
            if(gameOver)gameOver2=true;

        }
        if(gameOver2){
            if(blackCount>whiteCount)Toast.makeText(this,"BLACK WINS",Toast.LENGTH_LONG).show();
            else if(blackCount<whiteCount)Toast.makeText(this,"WHITE WINS",Toast.LENGTH_LONG).show();
            else if(blackCount==whiteCount)Toast.makeText(this,"DRAW",Toast.LENGTH_LONG).show();

        }

    }

    private boolean checkShift() {
        gameOver  = true;
        if(playerBTurn){
            checkOptionsWhite();
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    if(buttons[i][j].getText().toString().equals(".")){
                        gameOver=false;
                    }

                }
            }
            if(!gameOver)Toast.makeText(this,"WHITE MOVES",Toast.LENGTH_LONG).show();
        }
        else {
            checkOptionsBlack();
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    if(buttons[i][j].getText().toString().equals(".")){
                        gameOver=false;
                    }
                }
            }
            if(!gameOver)Toast.makeText(this,"BLACK MOVES",Toast.LENGTH_LONG).show();
        }
      playerBTurn=!playerBTurn;
        return gameOver;
    }

    private int getWhiteCount() {
        int wc = 0;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(buttons[i][j].color.equals("White"))wc++;
            }
        }
        return wc;
    }

    private int getBlackCount() {
        int bc= 0;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(buttons[i][j].color.equals("Black"))bc++;
            }
        }
        return bc;
    }

    public boolean onLongClick(View view){

        return true;
    }
public void checkOptionsBlack(){
for(int i=0;i<n;i++){
    for(int j=0;j<n;j++){
        int m = i+j;
        int l = i-j;
        if (((buttons[i][j].color)).equals("White")){
            for(int k=i;k>=0;k--){
                if(((buttons[k][j].color)).equals("noColor"))break;
                if(((buttons[k][j].color)).equals("Black")&&i+1<n){
                   if(buttons[i+1][j].chip==NO_CHIP)buttons[i+1][j].setText(".");
                }
            }
            for(int k=j;k>=0;k--){
                if(((buttons[i][k].color)).equals("noColor"))break;
                if(((buttons[i][k].color)).equals("Black")&&j+1<n){
                    if(buttons[i][j+1].chip==NO_CHIP)buttons[i][j+1].setText(".");
                }
            }
            for(int k=j;k<n;k++){
                if((buttons[i][k].color).equals("noColor"))break;
                if((buttons[i][k].color).equals("Black")&&j-1>=0){
                    if(buttons[i][j-1].chip==NO_CHIP)buttons[i][j-1].setText(".");
                }
            }
            for(int k=i;k<n;k++){
                if((buttons[k][j].color).equals("noColor"))break;
                if((buttons[k][j].color).equals("Black")&&i-1>=0){
                    if(buttons[i-1][j].chip==NO_CHIP)buttons[i-1][j].setText(".");
                }
            }
            outerloop1:
            for(int p=i;p>=0;p--){
                for(int q=j;q<n;q++){
                    if ((p + q) == m){
                        if((buttons[p][q].color).equals("noColor"))break outerloop1;
                        if((buttons[p][q].color).equals("Black")&&i+1<n&&j-1>=0){
                            if(buttons[i+1][j-1].chip==NO_CHIP)buttons[i+1][j-1].setText(".");
                        }
                    }
                }
            }
            outerloop2:
            for(int p=i;p>=0;p--){
                for(int q=j;q>=0;q--){
                    if((p-q)==l){
                        if((buttons[p][q].color).equals("noColor"))break outerloop2;
                        if((buttons[p][q].color).equals("Black")&&i+1<n&&j+1<n){
                            if(buttons[i+1][j+1].chip==NO_CHIP)buttons[i+1][j+1].setText(".");
                        }
                    }
                }
            }
            outerloop3:
            for(int p=i;p<n;p++){
                for(int q=j;q>=0;q--){
                    if((p+q)==m){
                        if((buttons[p][q].color).equals("noColor"))break outerloop3;
                        if((buttons[p][q].color).equals("Black")&&i-1>=0&&j+1<n){
                            if(buttons[i-1][j+1].chip==NO_CHIP)buttons[i-1][j+1].setText(".");
                        }
                    }
                }
            }
            outerloop4:
            for(int p=i;p<n;p++){
                for(int q=j;q<n;q++){
                    if((p-q)==l){
                        if((buttons[p][q].color).equals("noColor"))break outerloop4;
                        if((buttons[p][q].color).equals("Black")&&i-1>=0&&j-1>=0){
                            if(buttons[i-1][j-1].chip==NO_CHIP)buttons[i-1][j-1].setText(".");
                        }
                    }
                }
            }

        }
    }
}

}
public void placeChipBlack(MyButton b){

    if((b.getText().toString()).equals(".")){
        b.setBackgroundResource(R.drawable.blackchip);
        b.chip = CHIP;
        b.color ="Black";
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                buttons[i][j].setText("");
            }
        }
        reverseBlack(b);
    }
    else{
        Toast.makeText(this,"INVALID MOVE",Toast.LENGTH_LONG).show();
    }
}
public void reverseBlack(MyButton button){
    int i=button.a;
    int j=button.b;
    int m = i+j;
    int l = i-j;
    boolean placeable;
    outerLoop:
    for(int k=i;k>=0;k--){
        placeable = true;
        if(buttons[k][j].color.equals("noColor")&&button.a!=k)break;
        if(buttons[k][j].color.equals("Black") && button.a!=k){
            for(int s=button.a;s>k;s--){
                if((buttons[s][j].color).equals("noColor"))placeable=false;

            }
            if(placeable){
              for(int q=button.a;q>k;q--){
                  buttons[q][j].setBackgroundResource(R.drawable.blackchip);
                  buttons[q][j].chip = CHIP;
                  buttons[q][j].color = "Black";
              }
              break outerLoop;
            }
        }

    }
    outerLoop:
    for(int k=j;k>=0;k--){
        placeable = true;
        if(buttons[i][k].color.equals("noColor")&&button.b!=k)break;
        if(buttons[i][k].color.equals("Black")&&button.b!=k){
            for(int s=button.b;s>k;s--){
                if((buttons[i][s].color).equals("noColor"))placeable=false;

            }
            if(placeable){
                for(int q=button.b;q>k;q--){
                    buttons[i][q].setBackgroundResource(R.drawable.blackchip);
                    buttons[i][q].chip = CHIP;
                    buttons[i][q].color = "Black";
                }
                break outerLoop;
            }
        }
    }
    outerLoop:
    for(int k=j;k<n;k++){
        placeable =true;
        if(buttons[i][k].color.equals("noColor")&&button.b!=k)break;
        if(buttons[i][k].color.equals("Black")&&button.b!=k){
            for(int s=button.b;s<k;s++){
                if((buttons[i][s].color).equals("noColor"))placeable=false;

            }
            if(placeable){
                for(int q=button.b;q<k;q++){
                    buttons[i][q].setBackgroundResource(R.drawable.blackchip);
                    buttons[i][q].chip = CHIP;
                    buttons[i][q].color = "Black";
                }
                break outerLoop;
            }
        }
    }
    outerLoop:
    for(int k=i;k<n;k++){
        placeable = true;
        if(buttons[k][j].color.equals("noColor")&&button.a!=k)break;
        if(buttons[k][j].color.equals("Black")&&button.a!=k){
            for(int s=button.a;s<k;s++){
                if((buttons[s][j].color).equals("noColor"))placeable=false;
            }
            if(placeable){
                for(int q=button.a;q<k;q++){
                    buttons[q][j].setBackgroundResource(R.drawable.blackchip);
                    buttons[q][j].chip = CHIP;
                    buttons[q][j].color = "Black";
                }
                break outerLoop;
            }
        }
    }
    outerLoop:
    for(int p=i;p>=0;p--){
        for(int q=j;q<n;q++) {
            if ((p + q) == m) {
                placeable = true;
                if (buttons[p][q].color.equals("noColor") && button.a != p && button.b != q) break outerLoop;
                if (buttons[p][q].color.equals("Black") && button.a != p && button.b != q) {
                    for(int s1 = i;s1>p;s1--){
                        for(int s2  = j;s2<q;s2++){
                            if((s1+s2)==m){
                                if((buttons[s1][s2].color).equals("noColor"))placeable=false;
                            }
                        }
                    }
                    if(placeable){
                        for(int q1 = i;q1>p;q1--){
                            for(int q2  = j;q2<q;q2++){
                                if((q1+q2)==m){
                                    buttons[q1][q2].setBackgroundResource(R.drawable.blackchip);
                                    buttons[q1][q2].chip = CHIP;
                                    buttons[q1][q2].color = "Black";
                                }
                            }
                        }
                        break outerLoop;
                    }

                }
            }
        }
        }
    outerLoop:
    for(int p=i;p>=0;p--){
        for(int q=j;q>=0;q--){
            if((p-q)==l){
                placeable = true;
                if (buttons[p][q].color.equals("noColor") && button.a != p && button.b != q) break outerLoop;
                if (buttons[p][q].color.equals("Black") && button.a != p && button.b != q) {
                    for(int s1 = i;s1>p;s1--){
                        for(int s2  = j;s2>q;s2--){
                            if((s1-s2)==l){
                                if((buttons[s1][s2].color).equals("noColor"))placeable=false;
                            }
                        }
                    }
                    if(placeable){
                        for(int q1 = i;q1>p;q1--){
                            for(int q2  = j;q2>q;q2--){
                                if((q1-q2)==l){
                                    buttons[q1][q2].setBackgroundResource(R.drawable.blackchip);
                                    buttons[q1][q2].chip = CHIP;
                                    buttons[q1][q2].color = "Black";
                                }
                            }
                        }
                        break outerLoop;
                    }
                }
            }
        }
    }
    outerLoop:
    for(int p=i;p<n;p++){
        for(int q=j;q>=0;q--){
            if((p+q)==m){
                placeable = true;
                if (buttons[p][q].color.equals("noColor") && button.a != p && button.b != q) break outerLoop;
                if (buttons[p][q].color.equals("Black") && button.a != p && button.b != q){
                    for(int s1 = i;s1<p;s1++){
                        for(int s2  = j;s2>q;s2--){
                            if((s1+s2)==m){
                                if((buttons[s1][s2].color).equals("noColor"))placeable=false;
                            }
                        }
                    }
                    if(placeable){
                        for(int q1 = i;q1<p;q1++){
                            for(int q2  = j;q2>q;q2--){
                                if((q1+q2)==m){
                                    buttons[q1][q2].setBackgroundResource(R.drawable.blackchip);
                                    buttons[q1][q2].chip = CHIP;
                                    buttons[q1][q2].color = "Black";
                                }
                            }
                        }
                        break outerLoop;
                    }
                }
            }
        }
    }
    outerLoop:
    for(int p=i;p<n;p++){
        for(int q=j;q<n;q++){
            if((p-q)==l){
               placeable = true;
                if (buttons[p][q].color.equals("noColor") && button.a != p && button.b != q) break outerLoop;
                if (buttons[p][q].color.equals("Black") && button.a != p && button.b != q) {
                    for(int s1 = i;s1<p;s1++){
                        for(int s2  = j;s2<q;s2++){
                            if((s1-s2)==l){
                                if((buttons[s1][s2].color).equals("noColor"))placeable=false;
                            }
                        }
                    }
                    if(placeable){
                        for(int q1 = i;q1<p;q1++){
                            for(int q2  = j;q2<q;q2++){
                                if((q1-q2)==l){
                                    buttons[q1][q2].setBackgroundResource(R.drawable.blackchip);
                                    buttons[q1][q2].chip = CHIP;
                                    buttons[q1][q2].color = "Black";
                                }
                            }
                        }
                        break outerLoop;
                    }
                }
            }
        }
    }

}
    public void checkOptionsWhite(){
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                int m = i+j;
                int l = i-j;
                if (((buttons[i][j].color)).equals("Black")){
                    for(int k=i;k>=0;k--){
                        if (((buttons[k][j].color)).equals("noColor"))break;
                        if(((buttons[k][j].color)).equals("White")&&i+1<n){
                            if(buttons[i+1][j].chip==NO_CHIP)buttons[i+1][j].setText(".");
                        }
                    }
                    for(int k=j;k>=0;k--){
                        if (((buttons[i][k].color)).equals("noColor"))break;
                        if(((buttons[i][k].color)).equals("White")&&j+1<n){
                            if(buttons[i][j+1].chip==NO_CHIP)buttons[i][j+1].setText(".");
                        }
                    }
                    for(int k=j;k<n;k++){
                        if (((buttons[i][k].color)).equals("noColor"))break;
                        if((buttons[i][k].color).equals("White")&&j-1>=0){
                            if(buttons[i][j-1].chip==NO_CHIP)buttons[i][j-1].setText(".");
                        }
                    }
                    for(int k=i;k<n;k++){
                        if (((buttons[k][j].color)).equals("noColor"))break;
                        if((buttons[k][j].color).equals("White")&&i-1>=0){
                            if(buttons[i-1][j].chip==NO_CHIP)buttons[i-1][j].setText(".");
                        }
                    }
                    outerloop5:
                    for(int p=i;p>=0;p--){
                        for(int q=j;q<n;q++){
                            if ((p + q) == m){
                                if((buttons[p][q].color).equals("noColor"))break outerloop5;
                                if((buttons[p][q].color).equals("White")&&i+1<n&&j-1>=0){
                                    if(buttons[i+1][j-1].chip==NO_CHIP)buttons[i+1][j-1].setText(".");
                                }
                            }
                        }
                    }
                    outerloop6:
                    for(int p=i;p>=0;p--){
                        for(int q=j;q>=0;q--){
                            if((p-q)==l){
                                if((buttons[p][q].color).equals("noColor"))break outerloop6;
                                if((buttons[p][q].color).equals("White")&&i+1<n&&j+1<n){
                                    if(buttons[i+1][j+1].chip==NO_CHIP)buttons[i+1][j+1].setText(".");
                                }
                            }
                        }
                    }
                    outerloop7:
                    for(int p=i;p<n;p++){
                        for(int q=j;q>=0;q--){
                            if((p+q)==m){
                                if((buttons[p][q].color).equals("noColor"))break outerloop7;
                                if((buttons[p][q].color).equals("White")&&i-1>=0&&j+1<n){
                                    if(buttons[i-1][j+1].chip==NO_CHIP)buttons[i-1][j+1].setText(".");
                                }
                            }
                        }
                    }
                    outerloop8:
                    for(int p=i;p<n;p++){
                        for(int q=j;q<n;q++){
                            if((p-q)==l){
                                if((buttons[p][q].color).equals("noColor"))break outerloop8;
                                if((buttons[p][q].color).equals("White")&&i-1>=0&&j-1>=0){
                                    if(buttons[i-1][j-1].chip==NO_CHIP)buttons[i-1][j-1].setText(".");
                                }
                            }
                        }
                    }

                }
            }
        }

    }
    public void placeChipWhite(MyButton b){

        if((b.getText().toString()).equals(".")){
            b.setBackgroundResource(R.drawable.whitechip);
            b.chip = CHIP;
            b.color = "White";
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    buttons[i][j].setText("");
                }
            }
            reverseWhite(b);
        }
        else{
            Toast.makeText(this,"INVALID MOVE",Toast.LENGTH_LONG).show();
        }
}

    private void reverseWhite(MyButton button) {
        int i=button.a;
        int j=button.b;
        int m = i+j;
        int l = i-j;
        boolean placeable;
        outerLoop:
        for(int k=i;k>=0;k--){
            placeable = true;
            if(buttons[k][j].color.equals("noColor")&&button.a!=k)break;
            if(buttons[k][j].color.equals("White")&&button.a!=k){
                for(int s=button.a;s>k;s--){
                    if((buttons[s][j].color).equals("noColor"))placeable=false;
                }
                if(placeable){
                    for(int q=button.a;q>k;q--){
                        buttons[q][j].setBackgroundResource(R.drawable.whitechip);
                        buttons[q][j].chip = CHIP;
                        buttons[q][j].color = "White";
                    }
                    break outerLoop;
                }
            }

        }
        outerLoop:
        for(int k=j;k>=0;k--){
          placeable = true;
            if(buttons[i][k].color.equals("noColor")&&button.b!=k)break;
            if(buttons[i][k].color.equals("White")&&button.b!=k){
                for(int s=button.b;s>k;s--){
                    if((buttons[i][s].color).equals("noColor"))placeable=false;

                }
                if(placeable){
                    for(int q=button.b;q>k;q--){
                        buttons[i][q].setBackgroundResource(R.drawable.whitechip);
                        buttons[i][q].chip = CHIP;
                        buttons[i][q].color = "White";
                    }
                    break outerLoop;
                }
            }
        }
        outerLoop:
        for(int k=j;k<n;k++){
          placeable = true;
            if(buttons[i][k].color.equals("noColor")&&button.b!=k)break;
            if(buttons[i][k].color.equals("White")&&button.b!=k){
                for(int s=button.b;s<k;s++){
                    if((buttons[i][s].color).equals("noColor"))placeable=false;

                }
                if(placeable){
                    for(int q=button.b;q<k;q++){
                        buttons[i][q].setBackgroundResource(R.drawable.whitechip);
                        buttons[i][q].chip = CHIP;
                        buttons[i][q].color = "White";
                    }
                    break outerLoop;
                }
            }
        }
        outerLoop:
        for(int k=i;k<n;k++){
          placeable = true;
            if(buttons[k][j].color.equals("noColor")&&button.a!=k)break;
            if(buttons[k][j].color.equals("White")&&button.a!=k){
                for(int s=button.a;s<k;s++){
                    if((buttons[s][j].color).equals("noColor"))placeable=false;
                }
                if(placeable){
                    for(int q=button.a;q<k;q++){
                        buttons[q][j].setBackgroundResource(R.drawable.whitechip);
                        buttons[q][j].chip = CHIP;
                        buttons[q][j].color = "White";
                    }
                    break outerLoop;
                }
            }
        }
        outerLoop:
        for(int p=i;p>=0;p--){
            for(int q=j;q<n;q++) {
                if ((p + q) == m) {
               placeable = true;
                    if (buttons[p][q].color.equals("noColor") && button.a != p && button.b != q) break outerLoop;
                    if (buttons[p][q].color.equals("White") && button.a != p && button.b != q) {
                        for(int s1 = i;s1>p;s1--){
                            for(int s2  = j;s2<q;s2++){
                                if((s1+s2)==m){
                                    if((buttons[s1][s2].color).equals("noColor"))placeable=false;
                                }
                            }
                        }
                        if(placeable){
                            for(int q1 = i;q1>p;q1--){
                                for(int q2  = j;q2<q;q2++){
                                    if((q1+q2)==m){
                                        buttons[q1][q2].setBackgroundResource(R.drawable.whitechip);
                                        buttons[q1][q2].chip = CHIP;
                                        buttons[q1][q2].color = "White";
                                    }
                                }
                            }
                            break outerLoop;
                        }
                    }
                }
            }
        }
        outerLoop:
        for(int p=i;p>=0;p--){
            for(int q=j;q>=0;q--){
                if((p-q)==l){
                 placeable = true;
                    if (buttons[p][q].color.equals("noColor") && button.a != p && button.b != q) break outerLoop;
                    if (buttons[p][q].color.equals("White") && button.a != p && button.b != q) {
                        for(int s1 = i;s1>p;s1--){
                            for(int s2  = j;s2>q;s2--){
                                if((s1-s2)==l){
                                    if((buttons[s1][s2].color).equals("noColor"))placeable=false;
                                }
                            }
                        }
                        if(placeable){
                            for(int q1 = i;q1>p;q1--){
                                for(int q2  = j;q2>q;q2--){
                                    if((q1-q2)==l){
                                        buttons[q1][q2].setBackgroundResource(R.drawable.whitechip);
                                        buttons[q1][q2].chip = CHIP;
                                        buttons[q1][q2].color = "White";
                                    }
                                }
                            }
                            break outerLoop;
                        }
                    }
                }
            }
        }
        outerLoop:
        for(int p=i;p<n;p++){
            for(int q=j;q>=0;q--){
                if((p+q)==m){
               placeable = true;
                    if (buttons[p][q].color.equals("noColor") && button.a != p && button.b != q) break outerLoop;
                    if (buttons[p][q].color.equals("White") && button.a != p && button.b != q){
                        for(int s1 = i;s1<p;s1++){
                            for(int s2  = j;s2>q;s2--){
                                if((s1+s2)==m){
                                    if((buttons[s1][s2].color).equals("noColor"))placeable=false;
                                }
                            }
                        }
                        if(placeable){
                            for(int q1 = i;q1<p;q1++){
                                for(int q2  = j;q2>q;q2--){
                                    if((q1+q2)==m){
                                        buttons[q1][q2].setBackgroundResource(R.drawable.whitechip);
                                        buttons[q1][q2].chip = CHIP;
                                        buttons[q1][q2].color = "White";
                                    }
                                }
                            }
                            break outerLoop;
                        }
                    }
                }
            }
        }
        outerLoop:
        for(int p=i;p<n;p++){
            for(int q=j;q<n;q++){
                if((p-q)==l){
                    placeable = true;
                    if (buttons[p][q].color.equals("noColor") && button.a != p && button.b != q) break outerLoop;
                    if (buttons[p][q].color.equals("White") && button.a != p && button.b != q) {
                        for(int s1 = i;s1<p;s1++){
                            for(int s2  = j;s2<q;s2++){
                                if((s1-s2)==l){
                                    if((buttons[s1][s2].color).equals("noColor"))placeable=false;
                                }
                            }
                        }
                        if(placeable){
                            for(int q1 = i;q1<p;q1++){
                                for(int q2  = j;q2<q;q2++){
                                    if((q1-q2)==l){
                                        buttons[q1][q2].setBackgroundResource(R.drawable.whitechip);
                                        buttons[q1][q2].chip = CHIP;
                                        buttons[q1][q2].color = "White";
                                    }
                                }
                            }
                            break outerLoop;
                        }
                    }
                }
            }
        }

    }
    }
