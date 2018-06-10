package main.model;

import java.util.ArrayList;
import java.util.List;

public class CanvasHistory {
    private List<Paint> paints = new ArrayList<>();
    private List<Paint> previousPaints = new ArrayList<>();

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
        if (!paints.isEmpty()) {
            previousPaints.add(paints.get(paints.size() - 1));
            paints.remove(paints.size() - 1);
            
            for (Paint paint : paints){
                paint.execute();
            }
        }
    }

    public void redo(){
        if (!previousPaints.isEmpty()) {
            paints.add(previousPaints.get(previousPaints.size() - 1));
            previousPaints.remove(previousPaints.size() - 1);

            for (Paint paint : paints) {
                paint.execute();
            }
        }
    }

    public void clear(){
        paints = new ArrayList<>();
        previousPaints = new ArrayList<>();
    }
}
