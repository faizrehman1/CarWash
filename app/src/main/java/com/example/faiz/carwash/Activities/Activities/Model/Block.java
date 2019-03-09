package com.example.faiz.carwash.Activities.Activities.Model;

/**
 * Created by AST on 11/8/2017.
 */

public class Block {

    public String block_title;
    public String block_uid;

    public Block() {
    }

    public Block(String block_title, String block_uid) {
        this.block_title = block_title;
        this.block_uid = block_uid;
    }

    public String getBlock_title() {
        return block_title;
    }

    public void setBlock_title(String block_title) {
        this.block_title = block_title;
    }

    public String getBlock_uid() {
        return block_uid;
    }

    public void setBlock_uid(String block_uid) {
        this.block_uid = block_uid;
    }
}
