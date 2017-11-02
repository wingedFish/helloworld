package github.wingedfish.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by lixiuhai on 2017/11/2.
 */
public class FileOperation {


    private static final String FILE_PATH = "" ;
    private static final int BUFFER_SIZE = 1000 ;

    /**
     * 方案1：通过文件通道以流的形式读取文件,每次读取一定大小的字节到缓冲区中,统计字符符中大写字符总数
     * 方案2：通过内存映射的方式,每次把文件中的一部分数据映射到内存中,统计字符中大写字符总数
     * 方案3：把内存分成多块,把文件映射到多个区域中,每块区域分配一个线程负责统计本区域内的大写字符总数,
     *         最后汇总每块区域内的大写字符数.每个线程统计大写字符数同方案一.
     *
     * 本次文件只有4GB,采用方案1的形式
     * @return
     */
    public long countUpperWord(){
        byte[] bytes = new byte[BUFFER_SIZE] ;
        ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        long upperWord = 0L ;
        try {
            FileChannel fileChannel = new RandomAccessFile(FILE_PATH,"r").getChannel();
            while (fileChannel.read(byteBuffer)!=-1){
                int size = byteBuffer.position();
                byteBuffer.rewind();
                byteBuffer.get(bytes);
                upperWord+=doCountUpperWordLine(new String(bytes,0,size));
                byteBuffer.clear();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return upperWord ;
    }

    private long doCountUpperWordLine(String lineString){
        char[] chars= lineString.toCharArray();
        long count = 0 ;
        for (int i =0,len = chars.length; i<len;i++){
            if (Character.isUpperCase(chars[i])){
                count++;
            }
        }
        return count;
    }



}
