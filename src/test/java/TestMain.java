import com.tiza.common.util.JacksonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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


    @Test
    public void test1(){

        List list = new ArrayList(){
            {
                this.add(new HashMap(){
                    {
                        this.put("a", 1);
                        this.put("b", 2);
                        this.put("c", 3);
                    }
                });
                this.add(new HashMap(){
                    {
                        this.put("a", 11);
                        this.put("b", 22);
                        this.put("c", 33);
                    }
                });

            }
        };
        String json = JacksonUtil.toJson(list);

        System.out.println(json);

        try {
            List l = JacksonUtil.toList(json, HashMap.class);

            System.out.println(l.get(1));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
