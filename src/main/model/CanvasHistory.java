package main.model;

import java.util.ArrayList;
import java.util.List;

public class CanvasHistory {
    private List<Paint> paints = new ArrayList<>();
    private List<Paint> previousPaints;

    public List<Paint> getPaints() {
        return paints;
    }

    public void setPaints(List<Paint> paints) {
        this.paints = paints;
    }

    public List<Paint> getHistory(){
        return paints;
    }

    public void addPaint(Paint paint){
        paints.add(paint);
    }

    public void undo(){
        previousPaints = new ArrayList<>(paints);
        paints.remove(paints.size() - 1);

        for (Paint paint : paints){
            paint.execute();
        }
    }

    public void redo(){
        paints = new ArrayList<>(previousPaints);
        for (Paint paint : previousPaints){
            paint.execute();
        }
    }
}