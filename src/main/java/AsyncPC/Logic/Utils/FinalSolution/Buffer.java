package AsyncPC.Logic.Utils.FinalSolution;

import java.util.ArrayList;
import java.util.List;

public class Buffer {
    private List<Integer> buffer;

    public Buffer(int size){
        this.buffer = new ArrayList<>();
        for(int i = 0; i< size; i++){
            this.buffer.add(0);
        }
    }

    public Integer takeElement(Integer element){
        this.buffer.set(element, 0);
        return this.buffer.get(element);
    }
    public void setElement(Integer value, Integer position){
        this.buffer.set(position, value);
    }
}
