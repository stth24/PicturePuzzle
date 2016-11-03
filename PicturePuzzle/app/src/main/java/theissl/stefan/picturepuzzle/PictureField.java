package theissl.stefan.picturepuzzle;

import android.graphics.Color;
import android.media.Image;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Stefan Theissl on 31.10.2016.
 */

public class PictureField {
    private ImageButton curBtn;
    private int pic, curRow, curCol, rightRow, rightCol;

    public PictureField(int cCol, int cRow, int rCol, int rRow, int image, ImageButton btn) {
        curBtn = btn;
        curCol = cCol;
        curRow = cRow;
        rightCol = rCol;
        rightRow = rRow;
        pic = image;


        if(image != 0)
        {
            curBtn.setVisibility(View.VISIBLE);
            curBtn.setBackgroundResource(pic);
        }
        else{
            curBtn.setVisibility(View.INVISIBLE);
        }

    }

    public boolean checkRightPosition(){
        if(curCol == rightCol && curRow == rightRow)
            return true;
        else
            return false;
    }

    public void changeVisibility(){
        if(curBtn.getVisibility() == View.INVISIBLE)
            curBtn.setVisibility(View.VISIBLE);
        else
            curBtn.setVisibility(View.INVISIBLE);
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public ImageButton getCurBtn() {
        return curBtn;
    }

    public void setCurBtn(ImageButton curBtn) {
        this.curBtn = curBtn;
        curBtn.setBackgroundResource(pic);
        changeVisibility();
    }

    public int getCurRow() {
        return curRow;
    }

    public void setCurRow(int curRow) {
        this.curRow = curRow;
    }

    public int getCurCol() {
        return curCol;
    }

    public void setCurCol(int curCol) {
        this.curCol = curCol;
    }

    public int getRightRow() {
        return rightRow;
    }

    public void setRightRow(int rightRow) {
        this.rightRow = rightRow;
    }

    public int getRightCol() {
        return rightCol;
    }

    public void setRightCol(int rightCol) {
        this.rightCol = rightCol;
    }



}
