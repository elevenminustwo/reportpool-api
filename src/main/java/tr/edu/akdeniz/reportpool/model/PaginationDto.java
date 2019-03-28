package tr.edu.akdeniz.reportpool.model;

import java.lang.reflect.Array;
import java.util.List;

public class PaginationDto {
    public int draw;
    public int recordsTotal;
    public int recordsFiltered;
    public List<Object[]> data;

    public PaginationDto(){

    }

    public PaginationDto(int draw, int recordsFiltered, int recordsTotal, List<Object[]> data) {
        this.draw = draw;
        this.recordsFiltered = recordsFiltered;
        this.recordsTotal = recordsTotal;
        this.data = data;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public List<Object[]> getArray() {
        return data;
    }

    public void setArray(List<Object[]> data) {
        this.data = data;
    }
}
