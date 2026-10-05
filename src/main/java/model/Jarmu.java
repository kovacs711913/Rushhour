package model;

public record Jarmu (char id, Kordinata startPos, int length, boolean isHorizontal) {
    public Jarmu move(int offset) {
        if (isHorizontal) {
            return new Jarmu(id, new Kordinata(startPos.row(), startPos.col() + offset), length, isHorizontal);
        } else {
            return new Jarmu(id, new Kordinata(startPos.row() + offset, startPos.col()), length, isHorizontal);
        }
    }
}