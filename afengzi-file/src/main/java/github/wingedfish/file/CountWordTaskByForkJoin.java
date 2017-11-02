package github.wingedfish.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Created by lixiuhai on 2017/11/2.
 */
public class CountWordTaskByForkJoin extends RecursiveTask<Long> {
    private String filePath;
    private long start;
    private long end;

    private static final long THRESHOLD = 1024*1024;
    private static final int BUFFER_SIZE = 1024;
    private long MAP_SIZE = 500;


    public CountWordTaskByForkJoin(String filePath, long start, long end) {
        this.filePath = filePath;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {


        long length = end - start;
        if (length <= THRESHOLD) {
            return doCount(start, end);
        }

        CountWordTaskByForkJoin leftTask = new CountWordTaskByForkJoin(filePath,start, start+length / 2);
        leftTask.fork();

        CountWordTaskByForkJoin rightTask = new CountWordTaskByForkJoin(filePath,start+length / 2, end);
        long rightResult = rightTask.compute();
        long leftResult = leftTask.join();

        return rightResult + leftResult;
    }

    private long doCount(long blockStart, long blockEnd) {
        long start = System.currentTimeMillis();
        long upperCase = 0;

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r");
            FileChannel fileChannel = randomAccessFile.getChannel();
            long mapStart = blockStart;
            while (mapStart < blockEnd) {

                if (fileChannel.size() - mapStart < MAP_SIZE) {
                    MAP_SIZE = fileChannel.size() - mapStart;
                }

                MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, mapStart, MAP_SIZE);

                byte[] dst = new byte[BUFFER_SIZE];

                for (int position = 0; position < mappedByteBuffer.capacity(); position += BUFFER_SIZE) {
                    if (mappedByteBuffer.capacity() - position >= BUFFER_SIZE) {
                        for (int i = 0; i < BUFFER_SIZE; i++) {
                            dst[i] = mappedByteBuffer.get(position + i);
                        }

                    } else {
                        for (int i = 0; i < mappedByteBuffer.capacity() - position; i++) {
                            dst[i] = mappedByteBuffer.get(position + i);
                        }
                    }
                    String line = new String(dst, 0, dst.length);
                    upperCase += doCountUpperWordLine(line);
                }
                mapStart += MAP_SIZE;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long time = (System.currentTimeMillis() - start) / 1000;
        System.out.println("consumeTime : " + time);

        return upperCase;
    }


    private long doCountUpperWordLine(String lineString) {
        System.out.println("==============="+start/1024+" , end : "+end/1024);
        char[] chars = lineString.toCharArray();
        long count = 0;
        for (int i = 0, len = chars.length; i < len; i++) {
            if (Character.isUpperCase(chars[i])) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {

        String path = "D:\\充值组系统\\虚拟监控平台\\test.txt";
        CountWordTaskByForkJoin countWordTask = new CountWordTaskByForkJoin(path,0,4*THRESHOLD);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Long result =forkJoinPool.invoke(countWordTask);
        System.out.println("result = [" + result + "]");
    }
}
