package tr.edu.akdeniz.reportpool.model;

import java.util.List;

public class PaginationDto {
    public int draw;
    public int recordsFiltered;
    public int recordsTotal;
    public List array;

    public PaginationDto(){

    }

    public PaginationDto(int draw, int recordsFiltered, int recordsTotal, List array) {
        this.draw = draw;
        this.recordsFiltered = recordsFiltered;
        this.recordsTotal = recordsTotal;
        this.array = array;
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

    public List getArray() {
        return array;
    }

    public void setArray(List array) {
        this.array = array;
    }
}
