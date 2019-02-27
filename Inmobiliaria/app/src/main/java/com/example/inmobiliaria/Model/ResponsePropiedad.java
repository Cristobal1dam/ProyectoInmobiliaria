package com.example.inmobiliaria.Model;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Objects;

public class ResponsePropiedad {
    private int count;
    private PropiedadFoto rows;

    public ResponsePropiedad(int count, PropiedadFoto rows) {
        this.count = count;
        this.rows = rows;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public PropiedadFoto getRows() {
        return rows;
    }

    public void setRows(PropiedadFoto rows) {
        this.rows = rows;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponsePropiedad)) return false;
        ResponsePropiedad that = (ResponsePropiedad) o;
        return count == that.count &&
                Objects.equals(rows, that.rows);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(count, rows);
    }

    @Override
    public String toString() {
        return "ResponsePropiedad{" +
                "count=" + count +
                ", rows=" + rows +
                '}';
    }
}
