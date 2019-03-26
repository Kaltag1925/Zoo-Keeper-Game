package OldMenu.Navigation.Functions;

import CustomSwing.LabelResize;
import OldMenu.Navigation.Navigator;

public class ChangeIntInLabel extends Navigator{

    public void action(){

    }

    public void action(LabelResize label){

    }

    public void action(LabelResize label, boolean add, double changeAmount){
        if(add) {
            if(label.getAmount() + changeAmount <= label.getMax()) {
                label.setAmount(label.getAmount() + 1);
            }
        } else {
            if(label.getAmount() - changeAmount >= label.getMin()) {
                label.setAmount(label.getAmount() - 1);
                label.setActualText(label.getFirstText() + " " + label.getAmount() + " " + label.getLastText());
            }
        }
    }
}
