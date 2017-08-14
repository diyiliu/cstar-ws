import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

/**
 * Description: TestMain
 * Author: DIYILIU
 * Update: 2017-08-11 14:00
 */


public class TestMain {


    @Test
    public void test(){

        byte[] bytes = new byte[0];

        ByteBuf buf = Unpooled.buffer(bytes.length);
        buf.writeBytes(bytes);

        System.out.println(buf.readableBytes());
    }

}
