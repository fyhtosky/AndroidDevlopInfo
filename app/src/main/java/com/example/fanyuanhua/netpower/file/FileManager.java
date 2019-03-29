package com.example.fanyuanhua.netpower.file;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.IntDef;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileManager {
    public static final int MEDIA_AUDIO=0;
    public static final int MEDIA_TEXT=1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MEDIA_AUDIO, MEDIA_TEXT})
    @interface MediaType {}




    public static List<FileBean> getVideoFile(final File file, @MediaType final int type) {
        final List<FileBean> list = new ArrayList<>();
        if (null == file) {
            return list;
        }
        if (!file.exists()) {
            return list;
        }
        file.listFiles(new FileFilter() {

            @Override
            public boolean accept(File files) {
                if (!files.isDirectory()) {
                    String name = files.getName();
                    switch (type){
                        case MEDIA_AUDIO:
                            if (name.endsWith(".wav")) {
                                long time=getAudioDuration(file);
                                Log.i("TAG","音频的时间："+time);
                                list.add(new FileBean(name, files.getAbsolutePath(), 0, files.lastModified(), ShowLongFileSzie(files.length()),0l));
                            }
                            break;
                        case MEDIA_TEXT:
                            if (name.endsWith(".txt")) {
                                list.add(new FileBean(name, files.getAbsolutePath(), 1, files.lastModified(), ShowLongFileSzie(files.length())));
                            }
                            break;
                    }
                    return true;
                }else {
                    getVideoFile(files,type);
                    return false;
                }

            }


        });
        return list;
    }



    /**
     * 获取SD卡中的音乐文件
     * @param context
     * @return
     */
    public static ArrayList<FileBean> getWavFile(Context context) {
        final ArrayList<FileBean> FileBeanList = new ArrayList<>();
              //ArrayList<Music>存放音乐
        //查询媒体数据库
        ContentResolver resolver = context.getContentResolver();
        /**
         * Uri：这个Uri代表要查询的数据库名称加上表的名称。
         这个Uri一般都直接从MediaStore里取得，例如我要取所有歌的信息，
         就必须利用MediaStore.Audio.Media. EXTERNAL _CONTENT_URI这个Uri。
         *
         */
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        //遍历媒体数据库
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                //歌曲编号MediaStore.Audio.Media._ID
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));

                //歌曲标题
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));

                String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));//音频文件名

                String type=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.MIME_TYPE));
                //歌曲的专辑名MediaStore.Audio.Media.ALBUM
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));

                //歌曲的歌手名MediaStore.Audio.Media.ARTIST
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));

                //歌曲文件的路径MediaStore.Audio.Media.DATA
                String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));

                //歌曲的总播放时长MediaStore.Audio.Media.DURATION
                long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));

                //歌曲文件的大小MediaStore.Audio.Media.SIZE
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                //更新时间
                long updateTime=cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_MODIFIED));

//                if (size > 1024 * 800) {     //是否大于800K
//                    if (title.equals("<unknown>") || title.equals("")) {
//                        title = "未知";
//                    }
//                    if ("<unknown>".equals(artist) || "".equals(artist)) {
//                        artist = "未知";
//                    }
//
////                    Music music = new Music(id, title, artist,
////                            url, album, duration, size);
////                    MusicFiles.add(music);
//                }
                Log.i("TAG","音乐名称："+title+";文件类型："+type+";文件地址："+url);
                if(("audio/x-wav").equals(type)){
                    FileBeanList.add(new FileBean(displayName,url,0,updateTime,ShowLongFileSzie(size),duration));
                }
                cursor.moveToNext();
            }
        }
        return FileBeanList;
    }

    public static ArrayList<FileBean> getWavFile(Context context,String path) {
        final ArrayList<FileBean> FileBeanList = new ArrayList<>();
        //ArrayList<Music>存放音乐
        //查询媒体数据库
        ContentResolver resolver = context.getContentResolver();
        Uri uri = MediaStore.Files.getContentUri(path);

        /**
         * Uri：这个Uri代表要查询的数据库名称加上表的名称。
         这个Uri一般都直接从MediaStore里取得，例如我要取所有歌的信息，
         就必须利用MediaStore.Audio.Media. EXTERNAL _CONTENT_URI这个Uri。
         *
         */
        Cursor cursor = resolver.query(uri,
                null, "mime_type=\"audio/media\"", null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        //遍历媒体数据库
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                //歌曲编号MediaStore.Audio.Media._ID
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));

                //歌曲标题
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));

                String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));//音频文件名

                String type=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.MIME_TYPE));
                //歌曲的专辑名MediaStore.Audio.Media.ALBUM
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));

                //歌曲的歌手名MediaStore.Audio.Media.ARTIST
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));

                //歌曲文件的路径MediaStore.Audio.Media.DATA
                String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));

                //歌曲的总播放时长MediaStore.Audio.Media.DURATION
                long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));

                //歌曲文件的大小MediaStore.Audio.Media.SIZE
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                //更新时间
                long updateTime=cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_MODIFIED));

//                if (size > 1024 * 800) {     //是否大于800K
//                    if (title.equals("<unknown>") || title.equals("")) {
//                        title = "未知";
//                    }
//                    if ("<unknown>".equals(artist) || "".equals(artist)) {
//                        artist = "未知";
//                    }
//
////                    Music music = new Music(id, title, artist,
////                            url, album, duration, size);
////                    MusicFiles.add(music);
//                }
                Log.i("TAG","音乐名称："+title+";文件类型："+type+";文件地址："+url);
                if(("audio/x-wav").equals(type)){
                    FileBeanList.add(new FileBean(displayName,url,0,updateTime,ShowLongFileSzie(size),duration));
                }
                cursor.moveToNext();
            }
        }
        return FileBeanList;
    }







    /****
     * 计算文件大小
     *
     * @param length
     * @return
     */
    public static String ShowLongFileSzie(Long length) {
        if (length >= 1048576) {
            return (length / 1048576) + "MB";
        } else if (length >= 1024) {
            return (length / 1024) + "KB";
        } else if (length < 1024) {
            return length + "B";
        } else {
            return "0KB";
        }
    }

    /**
     * 转成固定格式的时间
     * @param time
     * @return
     */
    public static String getFormatDate(Long time,String pattern){
        Date date=new Date();
        date.setTime(time);
       return  new SimpleDateFormat(pattern)
                .format(time);

    }


    /**
     * 将字符串写入到文本文件中 .txt
     * @param strcontent
     * @param filePath
     * @param fileName
     */
    public static void writeTxtToFile(String strcontent, String filePath, String fileName) {
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);

        String strFilePath = filePath + fileName+".txt";
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
        }
    }


    /**
     * 生成文件
     * @param filePath
     * @param fileName
     * @return
     */
    public static File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }


    /**
     * 生成文件夹
     * @param filePath
     */
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }


    /**
     * 读取指定目录下的所有TXT文件的文件内容
     * @param file
     * @return
     */
    public static String getFileContent(File file) {
        String content = "";
        if (!file.isDirectory()) {  //检查此路径名的文件是否是一个目录(文件夹)
            if (file.getName().endsWith("txt")) {//文件格式为""文件
                try {
                    InputStream instream = new FileInputStream(file);
                    if (instream != null) {
                        InputStreamReader inputreader
                                = new InputStreamReader(instream, "UTF-8");
                        BufferedReader buffreader = new BufferedReader(inputreader);
                        String line = "";
                        //分行读取
                        while ((line = buffreader.readLine()) != null) {
                            content += line + "\n";
                        }
                        instream.close();//关闭输入流
                    }
                } catch (java.io.FileNotFoundException e) {
                    Log.d("TestFile", "The File doesn't not exist.");
                } catch (IOException e) {
                    Log.d("TestFile", e.getMessage());
                }
            }
        }
        return content;
    }






    /**
     * 获取指定位置视屏的时长
     * @param file
     * @return
     */
    private static   MediaMetadataRetriever mmr = new MediaMetadataRetriever();
    public static long getAudioDuration(File file){
        try {
            mmr.setDataSource(file.getAbsolutePath());
            String strDuration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            return  Long.valueOf(strDuration);
        }catch (Exception e){
           return 0l;
        }
    }
    /**
     * 通知android媒体库更新文件夹
     *
     * @param filePath ilePath 文件绝对路径，、/sda/aaa/jjj.jpg
     */
    public  static void scanFile(Context context, String filePath) {
        try {
            MediaScannerConnection.scanFile(context, new String[]{filePath}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("*******", "Scanned " + path + ":");
                            Log.i("*******", "-> uri=" + uri);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
