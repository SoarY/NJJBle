package com.njj.njjsdk.utils;



import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

public class BleBeaconUtil {

    /**
     * 解析ble广播
     * 1、蓝牙广播长度62  前31定义位广播数据 后31位响应数据
     * 2、有效数据部分 ：包含若干个广播数据单元，称为 AD Structure，每个单元可能存放电量，厂商名，蓝牙名，server id 和数据 ，根据数据部分第一个字节确定类型。
     * 3、无效数据部分 ：因为广播包的长度必须是 31 个 byte，如果有效数据部分不到 31 自己，剩下的就用 0 补全。
     * 4、AD Structure 的组成是：长度（第一个字节）+ 数据（数据部分第一个字节表示数据类型）
     * 5、数据类型定义：
     * 6、ble 广播单元解析例子：
     02 01   00 // 长度：2   类型： 01       数据 ：00
     10 ff   63 00  0b  0b  0b  0b  0b  0b  0b  0b  0b  0b  0b  0b  0b
     //10为长度    ff类型即厂商 63...0b为真实数据
     06 16  f4   18 01   02 03      //server uuid 携带参数
     0a 09  42  6c  65  53  65  72  76  65  72      //蓝牙完整名称ascll码对照
     02 0a  eb                       //电量
     03 02  f4  18                  //16位server uuid
     00 00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  00  //补位
     多个广播单元组成ble广播
     * @param adv_data      62bite广播
     * @return  Map
     */
    public static Map<String, String>  parseData(byte[] adv_data) {
        ByteBuffer buffer = ByteBuffer.wrap(adv_data).order(ByteOrder.LITTLE_ENDIAN);    //创建缓冲byte，一个个取出

        Map<String, String> map = new HashMap<>(); //存放数据单元的map
        try {

            while (true) {       //每次读一个单元
                byte length = buffer.get();      //获取缓冲数据中的当前即第一个数即数据部分的长度
                if (length == 0){return map;}   //长度为0即没有数据 返回map退出循环
                byte type = buffer.get();      //广播单元：长度+数据len， 数据第一个数为类型  真实数据部分len-1
//            Log.i(TAG, toHexString(type)); //toHexString（byte） 是Integer.toHex..将16的byte转字符串
                length -= 1;                   //去掉第一个数（类型）才是真实数据
                byte[] data = new byte[length];//存放真实数据的数组
                buffer.get(data, 0, length);  //取真实数据存放与data
                switch (type) {
                    case 0x01: // Flags
                        map.put("flag", ByteAndStringUtil.bytesToHexString(data));
                        break;
                    case 0x02: // Partial list of 16-bit UUIDs
                    case 0x03: // Complete list of 16-bit UUIDs
                        map.put("type", ByteAndStringUtil.bytesToHexString(data));
                        break;
                    case 0x14: // List of 16-bit Service Solicitation UUIDs
                        map.put("uuid_16",  ByteAndStringUtil.bytesToHexString(data));
                        break;
                    case 0x08: // Short local device name
                    case 0x09: // Complete local device name
                        map.put("localName",  new String(data));   //解出来的数对应ascll码
                        break;

                    case 0x16:
                        map.put("company",  ByteAndStringUtil.bytesToHexString(data));
                        break;
                    case (byte) 0xFF: // Manufacturer Specific Data
                        map.put("ID",  ByteAndStringUtil.bytesToHexString(data));
                        break;
                    default: // skip
                        break;
                }
            }
        }catch (Exception e){

        }
        return map;
    }

}
