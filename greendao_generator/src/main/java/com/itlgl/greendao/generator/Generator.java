package com.itlgl.greendao.generator;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class Generator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.hengbao.hdwallet.ethereum.storage.greendao");

        addNote(schema);

//        addNote(schema);
//        File file = new File("./app/src/main/java");
//        System.out.println(file.getAbsolutePath());
//        System.out.println(file.exists());

        new DaoGenerator().generateAll(schema, "./app/src/main/java");
    }

    private static void addNote(Schema schema) {
        Entity note = schema.addEntity("Note");
        note.addIdProperty();
        note.addStringProperty("text").notNull();
        note.addStringProperty("comment");
        note.addDateProperty("date");
    }
}
