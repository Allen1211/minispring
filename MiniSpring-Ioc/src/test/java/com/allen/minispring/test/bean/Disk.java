package com.allen.minispring.test.bean;

/**
 * @ClassName Disk
 * @Description
 * @Author XianChuLun
 * @Date 2020/9/8
 * @Version 1.0
 */
public class Disk {

    private String author;
    private String albumName;

    public Disk(String author, String albumName) {
        this.author = author;
        this.albumName = albumName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    @Override
    public String toString() {
        return "Disk{" +
                "author='" + author + '\'' +
                ", albumName='" + albumName + '\'' +
                '}';
    }
}
